/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tatiana Fesenko (CEA LIST) - Initial API and implementation
 *	Saadia Dhouib (CEA LIST) - Implementation of loading diagrams from template files  (.uml, .di , .notation)
 *  Christian W. Damus (CEA) - bug 422257
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.command;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModelUtils;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.wizards.utils.WizardsHelper;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;


/**
 * The Class InitFromTemplateCommand.
 */
public class InitFromTemplateCommand extends RecordingCommand {

	/** The my model set. */
	private final ModelSet myModelSet;

	/** The my template path. */
	private final String myUmlTemplatePath;

	/** The my di template path. */
	private final String myDiTemplatePath;

	/** The my notation template path. */
	private final String myNotationTemplatePath;

	/** The my plugin id. */
	private final String myPluginId;

	/**
	 * Instantiates a new inits the from template command.
	 *
	 * @param editingDomain
	 *        the editing domain
	 * @param diResouceSet
	 *        the di resouce set
	 * @param pluginId
	 *        the plugin id
	 * @param umlTemplatePath
	 *        the uml template path
	 * @param notationTemplatePath
	 *        the notation template path
	 * @param diTemplatePath
	 *        the di template path
	 */
	public InitFromTemplateCommand(TransactionalEditingDomain editingDomain, ModelSet modelSet, String pluginId, String umlTemplatePath, String notationTemplatePath, String diTemplatePath) {
		super(editingDomain);
		myModelSet = modelSet;
		myPluginId = pluginId;
		myUmlTemplatePath = umlTemplatePath;
		myDiTemplatePath = diTemplatePath;
		myNotationTemplatePath = notationTemplatePath;

	}


	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 */
	@Override
	protected void doExecute() {
		try {
			initializeFromTemplate();
			//verify if .di file and .notation file were filled in the org.eclipse.papyrus.uml.diagram.wizards.templates extension

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	/**
	 * Initialize from template.
	 *
	 *
	 * @throws IOException
	 *         Signals that an I/O exception has occurred.
	 */
	private void initializeFromTemplate() throws IOException {
		Resource templateDiResource = null;
		Resource templateNotationResource = null;
		Resource templateUmlResource = null;


		final ResourceSet resourceSet = new ResourceSetImpl();

		try {
			//0. initalization of the UML object
			templateUmlResource = loadTemplateResource(myUmlTemplatePath, resourceSet);
			EcoreUtil.resolveAll(templateUmlResource);

			//1. test if di and notation exist
			//verify if .di file and .notation file were filled in the org.eclipse.papyrus.uml.diagram.wizards.templates extension
			if((myDiTemplatePath != null) && (myNotationTemplatePath != null)) {
				//2.1 verify if the .di , .notation and .uml files have the same name
				String diFileName = WizardsHelper.getFileNameWithoutExtension(myDiTemplatePath);
				String umlFileName = WizardsHelper.getFileNameWithoutExtension(myUmlTemplatePath);
				String notationFileName = WizardsHelper.getFileNameWithoutExtension(myNotationTemplatePath);

				if(diFileName.contentEquals(umlFileName) && diFileName.contentEquals(notationFileName)) {

					//1.2 load  di resource
					if(myDiTemplatePath != null) {
						templateDiResource = loadTemplateResource(myDiTemplatePath, resourceSet);
						EcoreUtil.resolveAll(templateDiResource);
					}

					//1.3 load notation resource
					if(myNotationTemplatePath != null) {
						templateNotationResource = loadTemplateResource(myNotationTemplatePath, resourceSet);
						EcoreUtil.resolveAll(templateNotationResource);
					}

				}
			}

			//2. copy all elements
			EcoreUtil.Copier copier = new EcoreUtil.Copier();
			Collection<EObject> umlObjects = copier.copyAll(templateUmlResource.getContents());
			Collection<EObject> diObjects = (templateDiResource == null) ? null : copier.copyAll(templateDiResource.getContents());
			Collection<EObject> notationObjects = (templateNotationResource == null) ? null : copier.copyAll(templateNotationResource.getContents());
			copier.copyReferences();


			//3. set copied elements in goods resources
			Resource myModelUMLResource = UmlUtils.getUmlResource(myModelSet);
			myModelUMLResource.getContents().addAll(umlObjects);
			if(diObjects != null) {
				Resource myModelDiResource = DiModelUtils.getDiResource(myModelSet);
				myModelDiResource.getContents().addAll(diObjects);
				SashModel myModelSashModel = SashModelUtils.getSashModel(myModelSet);
				// reload the sash model in case the new DI content has shared sash preferences
				myModelSashModel.loadModel(myModelSet.getURIWithoutExtension());
			}
			if(notationObjects != null) {
				Resource myModelNotationResource = NotationUtils.getNotationResource(myModelSet);
				myModelNotationResource.getContents().addAll(notationObjects);
			}
		} finally {
			EMFHelper.unload(resourceSet);
		}
	}



	/**
	 * Load template resource.
	 *
	 * @param path
	 *        the path
	 * @return the resource
	 */
	private Resource loadTemplateResource(String path, ResourceSet resourceSet) {
		java.net.URL templateURL = Platform.getBundle(myPluginId).getResource(path);
		if(templateURL != null) {
			String fullUri = templateURL.getPath();
			URI uri = URI.createPlatformPluginURI(myPluginId + fullUri, true);
			Resource resource = resourceSet.getResource(uri, true);
			if(resource.isLoaded()) {
				return resource;
			}
		}
		return null;
	}




}
