/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.core.editor.ModelSetServiceFactory;
import org.eclipse.papyrus.infra.core.resource.EditingDomainServiceFactory;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelsReader;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor.ServiceTypeKind;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceStartKind;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.export.utils.SelectionHelper;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForSelection;
import org.eclipse.papyrus.uml.export.Activator;
import org.eclipse.papyrus.uml.export.HTMLExportHelper;
import org.eclipse.papyrus.uml.export.HTMLExportRunner;
import org.eclipse.papyrus.uml.export.extension.HTMLExtensionRegistry;
import org.eclipse.ui.handlers.HandlerUtil;


/**
 * Handler to export all diagrams of selected resource in Project Explorer and
 * Model Explorer.
 */
public class ExportHtmlHandler extends AbstractHandler {

	/**
	 * Execute.
	 *
	 * @param event
	 *            the event
	 * @return the object
	 * @throws ExecutionException
	 *             the execution exception
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (Activator.getDefault().isDebugging()) {
			Activator.debug("Start html export in debug mode"); //$NON-NLS-1$
		}

		// Get selection from handler event
		ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		ModelSet modelSetSelection = null;
		try {
			modelSetSelection = ServiceUtilsForSelection.getInstance().getModelSet(selection);
		} catch (ServiceException e) {
			// Do not log error the editor is simply not opened
			IFile file = SelectionHelper.convertSelection2File(selection);
			URI diFileUri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			modelSetSelection = initialiseModelSet(diFileUri);
		}

		List<Diagram> allDiagrams = getAllDiagrams(modelSetSelection);
		URI uriWithoutExtension = modelSetSelection.getURIWithoutExtension();
		URI resolvedFile = CommonPlugin.resolve(uriWithoutExtension);
		String fileString = resolvedFile.toFileString();
		Path targetDirectoryPath = new Path(fileString);
		List<String> activatePref = parseList(Activator.getDefault().getPreferenceStore().getString(HTMLExportPreferencesPage.ACTIVE_STRATEGIES));
		if (activatePref.isEmpty()) {
			activatePref = HTMLExtensionRegistry.getInstance().getAllExtension().stream().map(IExtension::getUniqueIdentifier).collect(Collectors.toList());
		}
		
		HTMLExportHelper helperFromPrefs = HTMLExtensionRegistry.getHelperFromPrefs(activatePref);
		HTMLExportRunner.generateHtmlWebSite(targetDirectoryPath, allDiagrams, helperFromPrefs); 
		refreshworkspace();

		if (Activator.getDefault().isDebugging()) {
			Activator.debug("End html export in debug mode"); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * Gets the all diagrams.
	 *
	 * @param modelSet the model set
	 * @return the all diagrams
	 */
	public static List<Diagram> getAllDiagrams(ModelSet modelSet) {
		
		Iterable<Diagram> allNotations = NotationUtils.getAllNotations(modelSet, Diagram.class);
		List<Diagram> copy = new ArrayList<>();
		Iterator<Diagram> iterator = allNotations.iterator();
		while (iterator.hasNext()) {
			copy.add(iterator.next());
		}
		return copy;
	}
	
	/**
	 * Parses the list.
	 *
	 * @param listString the list string
	 * @return the list
	 */
	public static List<String> parseList(String listString) {
		List<String> list= new ArrayList<>(10);
		StringTokenizer tokenizer= new StringTokenizer(listString, ","); //$NON-NLS-1$
		while (tokenizer.hasMoreTokens())
			list.add(tokenizer.nextToken());
		return list;
	}
	
	/**
	 * Refreshworkspace.
	 */
	private void refreshworkspace() {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			root.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			Activator.log(e);
		}
	}

	/**
	 * Create Models set from selected file.
	 *
	 * @param diFileUri the di file uri
	 * @return the model set
	 */
	private ModelSet initialiseModelSet(URI diFileUri) {
		ServicesRegistry service = null;

		try {
			service = new ExtensionServicesRegistry();
		} catch (ServiceException e) {
			Activator.log(e);
			service = new ServicesRegistry(); // This won't really work
		}

		// Override service factory for Model Set
		ServiceDescriptor descriptor = new ServiceDescriptor(ModelSet.class, ModelSetServiceFactory.class.getName(),
				ServiceStartKind.STARTUP, 10);
		descriptor.setServiceTypeKind(ServiceTypeKind.serviceFactory);
		service.add(descriptor);

		// Override factory for editing domain
		descriptor = new ServiceDescriptor(TransactionalEditingDomain.class,
				EditingDomainServiceFactory.class.getName(), ServiceStartKind.STARTUP, 10,
				Collections.singletonList(ModelSet.class.getName()));
		descriptor.setServiceTypeKind(ServiceTypeKind.serviceFactory);
		service.add(descriptor);

		try {
			service.startServicesByClassKeys(ModelSet.class, TransactionalEditingDomain.class);
		} catch (ServiceException e) {
			Activator.log(e);
		}

		ModelSet modelSet = null;
		try {
			modelSet = ServiceUtils.getInstance().getModelSet(service);
		} catch (ServiceException e) {
			// Ignore service exception
		}

		// Instantiate a Model set
		if (modelSet == null) {
			modelSet = new ModelSet();
			try {
				ModelSetServiceFactory.setServiceRegistry(modelSet, service);
			} catch (ServiceException e) {
				// Ignore service exception
			}
		}

		// Read all Model from selected file
		ModelsReader modelsReader = new ModelsReader();
		modelsReader.readModel(modelSet);
		try {
			modelSet.loadModels(diFileUri);
		} catch (ModelMultiException e) {
			Activator.log(e);
		}
		// Initialize an editing domain
		modelSet.getTransactionalEditingDomain();
		return modelSet;
	}

}
