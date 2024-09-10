/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.transformation;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.uml.diagram.wizards.command.CopyModelCommand;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;


public class QVToGenerator implements IGenerator {

	protected ModelSet modelSet;

	protected String templateURI;

	protected String pluginID;

	// This map is to cache the executors so as to not have to calculate them every time they are needed
	protected static Map<URI, TransformationExecutor> executorMap = new HashMap<URI, TransformationExecutor>();

	protected TransformationExecutor executor;

	protected URI transformationURI;

	/**
	 * @see org.eclipse.papyrus.uml.diagram.wizards.transformation.IGenerator#execute()
	 *
	 */

	@Override
	public void execute() {

		setExecutor();
		// The TransformationExecutor was not properly set
		if (executor == null) {
			return;
		}

		ExecutionContextImpl context = new ExecutionContextImpl();
		Resource inResource = UmlUtils.getUmlModel(getOutputModelSet()).getResource();
		EList<EObject> inObjects = inResource.getContents();
		Resource inResourceTemplate = loadTemplateResource();
		EList<EObject> inObjectTemplates = inResourceTemplate.getContents();
		ModelExtent input = new BasicModelExtent(inObjects);
		ModelExtent inputTemplate = new BasicModelExtent(inObjectTemplates);
		context.setConfigProperty("keepModeling", true); //$NON-NLS-1$
		ExecutionDiagnostic result = executor.execute(context, inputTemplate, input);
		if (result.getSeverity() == Diagnostic.OK) {
			List<EObject> outObjects = input.getContents();
			getCommandStack(getOutputModelSet()).execute(new CopyModelCommand(getOutputModelSet().getTransactionalEditingDomain(),
					inResource, outObjects));
			try {
				inResource.save(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			IStatus status = BasicDiagnostic.toIStatus(result);
			org.eclipse.papyrus.uml.diagram.wizards.Activator.getDefault().getLog().log(status);
		}
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.wizards.transformation.IGenerator#setTemplateModel(java.util.List)
	 *
	 * @param targetURI
	 */
	public void setTemplateModel(String templateURI) {
		this.templateURI = templateURI;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.wizards.transformation.IGenerator#getOutputURI()
	 *
	 * @return
	 */

	public ModelSet getOutputModelSet() {
		return this.modelSet;
	}


	@Override
	public void setModelSet(ModelSet modelSet) {
		this.modelSet = modelSet;
	}


	public void setPluginId(String pluginId) {

		this.pluginID = pluginId;
	}

	private Resource loadTemplateResource() {
		java.net.URL templateURL = Platform.getBundle(pluginID).getResource(templateURI);
		if (templateURL != null) {
			String fullUri = templateURL.getPath();
			URI uri = URI.createPlatformPluginURI(pluginID + fullUri, true);
			Resource resource = modelSet.getResource(uri, true);
			if (resource.isLoaded()) {
				return resource;
			}
		}
		return null;
	}

	protected final CommandStack getCommandStack(ModelSet modelSet) {
		return modelSet.getTransactionalEditingDomain().getCommandStack();
	}

	public void setTransformationURI(URI transformationURI) {
		this.transformationURI = transformationURI;
	}

	public void setExecutor() {
		TransformationExecutor executor = executorMap.get(transformationURI);
		if (executor == null) {
			executor = new TransformationExecutor(transformationURI);
			executorMap.put(transformationURI, executor);
		}

		this.executor = executor;
	}

}
