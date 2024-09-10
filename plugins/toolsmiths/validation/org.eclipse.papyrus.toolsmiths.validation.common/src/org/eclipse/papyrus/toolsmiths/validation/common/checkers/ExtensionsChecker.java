/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Remi Schnekenburger (EclipseSource) - Bug 568495
 *   Christian W. Damus - bugs 569357, 570097
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.MarkersService;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ProjectManagementService;
import org.eclipse.pde.internal.core.builders.DefaultSAXParser;
import org.eclipse.pde.internal.core.builders.PDEMarkerFactory;
import org.eclipse.pde.internal.core.builders.XMLErrorReporter;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Common framework for checking the extensions of the <tt>plugin.xml</tt> needed for model resources.
 */
@SuppressWarnings("restriction")
public class ExtensionsChecker<T extends EObject, P extends DefaultHandler & IPluginChecker2> extends AbstractPluginChecker {

	private final IProject project;
	private final IFile modelFile;

	/**
	 * The models to check in the EMF resource.
	 */
	private final Collection<? extends T> existingModels;

	/**
	 * Factory of <tt>plugin.xml</tt> error reporters.
	 */
	private final PluginErrorReporterFactory<? super T, ? extends P> reporterFactory;

	/**
	 * Constructor. The PDE marker type is assumed.
	 *
	 * @param project
	 *            The current project to check.
	 * @param modelFile
	 *            The model file to check.
	 * @param existingModels
	 *            The models to check in the EMF resource.
	 * @param reporterFactory
	 *            The <tt>plugin.xml</tt> error reporter factory.
	 */
	public ExtensionsChecker(final IProject project, final IFile modelFile, final Collection<? extends T> existingModels, PluginErrorReporterFactory<? super T, ? extends P> reporterFactory) {
		this(project, modelFile, existingModels, PDEMarkerFactory.MARKER_ID, reporterFactory);
	}

	/**
	 * Constructor.
	 *
	 * @param project
	 *            The current project to check.
	 * @param modelFile
	 *            The model file to check.
	 * @param existingModels
	 *            The models to check in the EMF resource.
	 * @param markerType
	 *            The marker type to create (indirectly via diagnostics, of course)
	 * @param reporterFactory
	 *            The <tt>plugin.xml</tt> error reporter factory.
	 */
	public ExtensionsChecker(final IProject project, final IFile modelFile, final Collection<? extends T> existingModels, String markerType, PluginErrorReporterFactory<? super T, ? extends P> reporterFactory) {
		super(project, modelFile, markerType);

		this.project = project;
		this.modelFile = modelFile;
		this.existingModels = existingModels;
		this.reporterFactory = reporterFactory;
	}

	@Override
	public void check(DiagnosticChain diagnostics, final IProgressMonitor monitor) {
		if (monitor != null && monitor.isCanceled()) {
			return;
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor, NLS.bind(Messages.ExtensionsChecker_0, modelFile), existingModels.size());

		final IFile pluginXML = ProjectManagementService.getPluginXMLFile(project);

		if (pluginXML == null) {
			MarkersService.createMarker(modelFile, getMarkerType(), NLS.bind(Messages.ExtensionsChecker_1, modelFile.getName()), IMarker.SEVERITY_ERROR);
			return;
		}

		for (T model : existingModels) {
			P reporter = reporterFactory.createErrorReporter(pluginXML, modelFile, model);
			DefaultSAXParser.parse(pluginXML, (XMLErrorReporter) reporter);
			reporter.check(diagnostics, monitor);

			subMonitor.worked(1);
		}

		SubMonitor.done(monitor);
	}

	//
	// Nested types
	//

	/**
	 * Protocol for creation of the plug-in error reporter that analyzes the <tt>plugin.xml</tt> DOM and reports
	 * diagnostic problems.
	 *
	 * @param <T>
	 *            the type of model element that is validated for extension content in the <tt>plugin.xml</tt>
	 * @param <P>
	 *            the type of plug-in reporter factory. It is used to parse the <tt>pluginx.xml</tt> document (hence being
	 *            a {@#link DefaultHandler} and also to produce diagnostics (hence conformance to {@link IPluginChecker2})
	 */
	@FunctionalInterface
	public interface PluginErrorReporterFactory<T extends EObject, P extends DefaultHandler & IPluginChecker2> {
		/**
		 * Create the <tt>plugin.xml</tt> parser/error-reporter.
		 *
		 * @param pluginXML
		 *            the <tt>plugin.xml</tt> file to parse
		 * @param modelFile
		 *            the model file to validate for extensions in the <tt>plugin.xml</tt>
		 * @param model
		 *            a model object loaded from the model file to be validated
		 *
		 * @return the error reporter
		 */
		P createErrorReporter(final IFile pluginXML, final IFile modelFile, final T model);
	}

}
