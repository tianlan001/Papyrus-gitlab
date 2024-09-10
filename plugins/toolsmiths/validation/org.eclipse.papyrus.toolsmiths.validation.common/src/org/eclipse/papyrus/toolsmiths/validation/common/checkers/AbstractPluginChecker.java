/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.CheckerDiagnosticChain;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.MarkersService;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;

import com.google.common.collect.Lists;

/**
 * A plug-in checker that adapts the {@link IPluginChecker2} protocol to
 * {@link IPluginChecker} for independent reuse.
 */
public abstract class AbstractPluginChecker implements IPluginChecker, IPluginChecker2 {

	private final IProject project;
	private final IFile modelFile;
	private final String markerType;

	private DiagnosticEquivalence diagnosticEquivalence = DiagnosticEquivalence.DEFAULT;

	/**
	 * Initializes me with the PDE Marker type as my default marker type.
	 */
	@SuppressWarnings("restriction")
	public AbstractPluginChecker(IProject project, IFile modelFile) {
		this(project, modelFile, org.eclipse.pde.internal.core.builders.PDEMarkerFactory.MARKER_ID);
	}

	/**
	 * Initializes me with the default marker type to use for creating markers from diagnostics that do not specify a marker type, themselves.
	 */
	public AbstractPluginChecker(IProject project, IFile modelFile, String markerType) {
		super();

		this.project = project;
		this.modelFile = modelFile;
		this.markerType = markerType;
	}

	public void setDiagnosticEquivalence(DiagnosticEquivalence diagnosticEquivalence) {
		this.diagnosticEquivalence = diagnosticEquivalence == null ? DiagnosticEquivalence.DEFAULT : diagnosticEquivalence;
	}

	public final IProject getProject() {
		return project;
	}

	public final IFile getModelFile() {
		return modelFile;
	}

	public final String getMarkerType() {
		return markerType;
	}

	@Override
	public void check(IProgressMonitor monitor) {
		CheckerDiagnosticChain diagnostics = new CheckerDiagnosticChain(diagnosticEquivalence);

		check(diagnostics, monitor);

		// Create markers if the validation is not OK
		if (diagnostics.getSeverity() > Diagnostic.OK) {
			wrap(diagnostics.stream()).distinct().forEach(this::createMarker);
		}
	}

	protected Diagnostic createDiagnostic(int severity, int code, String message, Object... data) {
		return createDiagnostic(getProject(), getModelFile(), severity, code, message, data);
	}

	protected Diagnostic createDiagnostic(IFile file, int severity, int code, String message, Object... data) {
		return createDiagnostic(getProject(), file, severity, code, message, data);
	}

	protected Diagnostic createDiagnostic(IProject project, IFile file, int severity, int code, String message, Object... data) {
		List<Object> diagnosticData = file == null
				? Lists.asList(project, data)
				: Lists.asList(project, file, data);
		return new BasicDiagnostic(severity, getClass().getName(), code, message, diagnosticData.toArray());
	}

	/**
	 * Create a marker from a diagnostic, including processing of any of the additional {@linkplain Diagnostic#getData() data tokens}.
	 */
	protected void createMarker(Diagnostic diagnostic) {
		IResource resource = IPluginChecker2.getFile(diagnostic)
				.map(IResource.class::cast)
				.or(() -> Optional.ofNullable(modelFile))
				.orElse(project);

		IMarker marker = MarkersService.createMarker(resource,
				IPluginChecker2.getMarkerType(diagnostic).orElse(markerType),
				diagnostic);
		IPluginChecker2.setAttributes(diagnostic, marker);
	}

	/**
	 * To filter distinct diagnostics, we need a diagnostic that implements equality.
	 * This wraps a {@code diagnostic} to implement {@link Object#hashCode() and Object#equals(Object)}
	 * on its behalf.
	 */
	protected final Diagnostic wrap(Diagnostic diagnostic) {
		return IPluginChecker2.getMarkerType(diagnostic).isEmpty()
				? diagnosticEquivalence.wrap(diagnostic, IPluginChecker2.markerType(getMarkerType()))
				: diagnosticEquivalence.wrap(diagnostic);
	}

	private Stream<Diagnostic> wrap(Stream<Diagnostic> diagnostics) {
		return diagnostics.map(this::wrap);
	}

	/**
	 * Get the bundle symbolic name of a bundle project, or the best guest if it is not a bundle project.
	 *
	 * @param bundleProject
	 *            a project in the workspace
	 * @return the best-effort bundle name
	 */
	protected final String getBundleName(IProject bundleProject) {
		return Optional.ofNullable(PluginRegistry.findModel(bundleProject))
				.map(IPluginModelBase::getBundleDescription)
				.map(BundleDescription::getSymbolicName)
				.orElse(bundleProject.getName());
	}

}
