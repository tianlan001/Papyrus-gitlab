/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Remi Schnekenburger - Initial API and implementation
 *   Christian W. Damus - bugs 569357, 575376, 575725
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.DiagnosticEquivalence;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.CheckerDiagnosticChain;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.MarkersService;
import org.eclipse.uml2.common.util.CacheAdapter;

import com.google.common.collect.ListMultimap;

/**
 * A Papyrus Builder that delegates to {@link IPluginChecker2}s for its work.
 */
public class PluginCheckerBuilder extends AbstractPapyrusBuilder {

	private final Function<? super IProject, ListMultimap<IFile, ? extends EObject>> mapperFunction;
	private final List<IPluginChecker2.Factory> checkerFactories = new ArrayList<>();

	private DiagnosticEquivalence diagnosticEquivalence = DiagnosticEquivalence.DEFAULT;

	/**
	 * Initializes me with a function that maps files in the validated project to EMF model elements.
	 * By default, I create PDE Problem markers.
	 */
	public PluginCheckerBuilder(Function<? super IProject, ListMultimap<IFile, ? extends EObject>> mapperFunction) {
		this(PLUGIN_PROBLEM, mapperFunction);
	}

	/**
	 * Initializes me with a function that maps files in the validated project to EMF model elements
	 * and a specific default marker type to create.
	 */
	public PluginCheckerBuilder(String defaultMarkerType, Function<? super IProject, ListMultimap<IFile, ? extends EObject>> mapperFunction) {
		super(defaultMarkerType);

		this.mapperFunction = mapperFunction;
	}

	/**
	 * Add a factory for a checker to run on mapped resources.
	 *
	 * @param checkerFactory
	 *            the checker factory to add
	 * @return myself, for convenience of call chaining
	 */
	public PluginCheckerBuilder withChecker(IPluginChecker2.Factory checkerFactory) {
		checkerFactories.add(checkerFactory);
		return this;
	}

	/**
	 * Set the semantics for diagnostic equivalence to use in filtering out redundant diagnostics,
	 * to avoid create unnecessary markers.
	 *
	 * @param diagnosticEquivalence
	 *            the diagnostic equivalence semantics, or {@code null} for the default semantics
	 * @return myself, for convenience of call chaining
	 */
	public PluginCheckerBuilder withDiagnosticEquivalence(DiagnosticEquivalence diagnosticEquivalence) {
		this.diagnosticEquivalence = diagnosticEquivalence == null ? DiagnosticEquivalence.DEFAULT : diagnosticEquivalence;
		return this;
	}

	@Override
	public IProject[] build(IProject builtProject, PapyrusPluginBuilder papyrusBuilder, int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		if (papyrusBuilder.isInterrupted() || monitor.isCanceled()) {
			return null;
		}

		ListMultimap<IFile, ? extends EObject> sets = mapperFunction.apply(builtProject);

		if (sets.isEmpty()) {
			return null;
		}

		try {
			// Count steps for each checker once for the project as a whole and once for each model file found.
			// And one more for actually creating problem markers.
			SubMonitor subMonitor = SubMonitor.convert(monitor, (1 + sets.keySet().size()) * checkerFactories.size() + 1);

			CheckerDiagnosticChain diagnostics = new CheckerDiagnosticChain();

			// First, see about checking the project as a whole
			check(builtProject, null, null, diagnostics, subMonitor);

			for (IFile next : sets.keySet()) {
				// The map doesn't have empty collections for any key
				Resource resource = sets.get(next).get(0).eResource();
				check(builtProject, next, resource, diagnostics, subMonitor);
			}

			// Create markers if the validation is not OK
			if (diagnostics.getSeverity() > Diagnostic.OK) {
				wrap(diagnostics.stream()).distinct().forEach(this::createMarker);
			}
			subMonitor.worked(1);
		} finally {
			sets.values().forEach(this::unload);
		}

		SubMonitor.done(monitor);

		return new IProject[] { builtProject };
	}

	/**
	 * Unload a {@code model}, ensuring that everything reachable from it is purged.
	 * This applies especially to references from the UML {@link CacheAdapter}, which in a Papyrus
	 * context usually will retain a reference to the {@code model} or something that reaches it.
	 *
	 * @param model
	 *            a model to unload
	 */
	protected void unload(EObject model) {
		if (model != null && !model.eIsProxy()) {
			Resource resource = model.eResource();
			ResourceSet resourceSet = (resource == null) ? null : resource.getResourceSet();

			if (resourceSet != null) {
				EMFHelper.unload(resourceSet);
			} else if (resource != null) {
				resource.unload();
				resource.eAdapters().clear();
			} else {
				Iterators2.stream(model.eAllContents()).map(Notifier::eAdapters).forEach(Collection::clear);
				model.eAdapters().clear();
			}
		}
	}

	/**
	 * Iterate my checkers over the given project/file/resource, collecting their diagnostics.
	 *
	 * @param project
	 *            the project to check
	 * @param modelFile
	 *            the file to check, or {@code null} if a project-as-a-whole check
	 * @param resource
	 *            the EMF resource to check, or {@code null} if not an EMF resource
	 * @param diagnostics
	 *            in which to collect check results
	 * @param monitor
	 *            in which to report check progress
	 */
	private void check(IProject project, IFile modelFile, Resource resource, DiagnosticChain diagnostics, SubMonitor monitor) {
		checkerFactories.stream().map(factory -> factory.createChecker(project, modelFile, resource))
				.peek(checker -> {
					if (checker == null) {
						// Skipping it
						monitor.worked(1);
					}
				})
				.filter(Objects::nonNull)
				.forEach(checker -> checker.check(diagnostics, monitor.newChild(1)));
	}

	/**
	 * Create a marker from a diagnostic, including processing of any of the additional {@linkplain Diagnostic#getData() data tokens}.
	 */
	protected void createMarker(Diagnostic diagnostic) {
		IPluginChecker2.getFile(diagnostic)
				.map(file -> createMarker(file, diagnostic))
				.ifPresent(marker -> IPluginChecker2.setAttributes(diagnostic, marker));
	}

	private IMarker createMarker(IFile file, Diagnostic diagnostic) {
		return MarkersService.createMarker(file, IPluginChecker2.getMarkerType(diagnostic).orElse(getDefaultMarkerType()), diagnostic);
	}

	/**
	 * To filter distinct diagnostics, we need a diagnostic that implements equality.
	 * This wraps a {@code diagnostic} to implement {@link Object#hashCode() and Object#equals(Object)}
	 * on its behalf.
	 */
	private Diagnostic wrap(Diagnostic diagnostic) {
		return IPluginChecker2.getMarkerType(diagnostic).isEmpty()
				? diagnosticEquivalence.wrap(diagnostic, IPluginChecker2.markerType(getDefaultMarkerType()))
				: diagnosticEquivalence.wrap(diagnostic);
	}

	private Stream<Diagnostic> wrap(Stream<Diagnostic> diagnostics) {
		return diagnostics.map(this::wrap);
	}

}
