/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.dev.assistants.codegen.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.dev.assistants.codegen.generator.ElementTypesToAssistantsGenerator;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.assistant.util.AssistantResource;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Handler for the "generate modeling assistants from element types configuration" command.
 */
public class GenerateModelingAssistantsFromElementTypesHandler extends AbstractHandler {

	public GenerateModelingAssistantsFromElementTypesHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sselection = (IStructuredSelection) selection;
			if (!sselection.isEmpty()) {
				IFile file = AdapterUtils.adapt(sselection.getFirstElement(), IFile.class, null);
				final ResourceSet rset = new ResourceSetImpl();
				try {
					Resource resource = rset.getResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true), true);
					Object input = EcoreUtil.getObjectByType(resource.getContents(), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);
					if (input instanceof ElementTypeSetConfiguration) {
						ElementTypeSetConfiguration elementTypeSet = (ElementTypeSetConfiguration) input;

						ElementTypesToAssistantsGenerator assistantsGenerator = new ElementTypesToAssistantsGenerator(elementTypeSet);
						URI outputURI = resource.getURI().trimFileExtension().appendFileExtension(AssistantResource.FILE_EXTENSION);
						IStatus status = assistantsGenerator.generate(elementTypeSet, outputURI);
						if (status.getSeverity() >= IStatus.ERROR) {
							StatusManager.getManager().handle(status, StatusManager.SHOW | StatusManager.LOG);
						}
					}
				} finally {
					EMFHelper.unload(rset);
				}
			}
		}

		return null;
	}

}
