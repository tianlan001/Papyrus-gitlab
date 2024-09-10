/*****************************************************************************
 * Copyright (c) 2011, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *      Christian W. Damus (CEA) - bug 413703
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.newchild.ui;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.newchild.CreationMenuFactory;
import org.eclipse.papyrus.infra.newchild.CreationMenuRegistry;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder;
import org.eclipse.papyrus.infra.services.edit.utils.RequestCacheEntries;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

/**
 * This class has in charge to create menu from elementCreationMenuModel
 *
 */
public class DynamicNewChild extends ContributionItem {

	protected TransactionalEditingDomain editingDomain;

	protected CreationMenuRegistry creationMenuRegistry;

	/**
	 *
	 * Constructor.
	 *
	 */
	public DynamicNewChild() {
		creationMenuRegistry = CreationMenuRegistry.getInstance();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param id
	 */
	public DynamicNewChild(String id) {
		super(id);
		creationMenuRegistry = CreationMenuRegistry.getInstance();
	}


	protected IContributionItem[] getContributionItems() {
		return new IContributionItem[0];
	}

	@Override
	public boolean isDynamic() {
		return true;
	}



	@Override
	public void fill(Menu menu, int index) {
		EObject eObject = getSelection();
		if (eObject != null && editingDomain != null) {

			// caches the advices used by the selected EObject, to avoid finding them for each menu item
			Map<?, ?> adviceCache = new HashMap<Object, Object>();
			try {
				RequestCacheEntries.initializeEObjCache(eObject, adviceCache);
			} catch (ServiceException e) {
				Activator.log.error(e);
			}

			CreationMenuFactory creationMenuFactory = new CreationMenuFactory(editingDomain);
			List<Folder> folders = creationMenuRegistry.getRootFolder();
			Iterator<Folder> iterFolder = folders.iterator();
			while (iterFolder.hasNext()) {
				Folder currentFolder = iterFolder.next();
				boolean hasbeenBuild = creationMenuFactory.populateMenu(menu, currentFolder, eObject, index, adviceCache);
				if (hasbeenBuild) {
					index++;
				}
			}
		} else {
			super.fill(menu, index);
		}
	}




	/**
	 * getSelected eObject in the model explorer
	 *
	 * @return eObject or null
	 */
	protected EObject getSelection() {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		if (selectionService == null) {
			return null;
		}
		ISelection selection = selectionService.getSelection();

		if (selection == null || selection.isEmpty()) {
			return null;
		}

		if (selection instanceof IStructuredSelection) {
			Object selectedobject = ((IStructuredSelection) selection).getFirstElement();
			EObject selectedEObject = EMFHelper.getEObject(selectedobject);
			EObject editingDomainCitizen = selectedEObject;

			if ((editingDomainCitizen instanceof EReference) && (selection instanceof ITreeSelection)) {
				// The user selected a reference in the Advanced presentation. Infer the editing domain from the parent node, which is the reference owner
				ITreeSelection treeSel = (ITreeSelection) selection;
				TreePath[] paths = treeSel.getPathsFor(selectedobject);
				if ((paths != null) && (paths.length > 0) && (paths[0].getSegmentCount() > 1)) {
					editingDomainCitizen = EMFHelper.getEObject(paths[0].getSegment(paths[0].getSegmentCount() - 2));
				}
			}

			try {
				editingDomain = ServiceUtilsForEObject.getInstance().getService(org.eclipse.emf.transaction.TransactionalEditingDomain.class, editingDomainCitizen);
			} catch (Exception ex) {
				// If the service/service registry is not available, try to retrieve directly from the EObject
				editingDomain = TransactionUtil.getEditingDomain(editingDomainCitizen);
			}
			return selectedEObject;
		}
		return null;
	}
}
