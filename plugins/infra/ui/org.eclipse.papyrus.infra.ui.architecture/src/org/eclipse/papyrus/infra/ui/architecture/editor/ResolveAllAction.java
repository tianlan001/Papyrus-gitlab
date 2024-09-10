/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.infra.ui.architecture.ArchitectureUIPlugin;

/**
 * @author melaasar
 *
 */
public class ResolveAllAction extends Action implements ISelectionChangedListener {

	protected ISelectionProvider selectionProvider;
	protected List<EObject> selectedObjects;

	/**
	 * Constructor.
	 *
	 */
	public ResolveAllAction() {
	    super(ArchitectureUIPlugin.INSTANCE.getString("_UI_Resolve_All_menu_item"));
	    setDescription(ArchitectureUIPlugin.INSTANCE.getString("_UI_Resolve_All_simple_description"));
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 *
	 * @param event
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		selectionProvider = event.getSelectionProvider();
		if (event.getSelection() instanceof IStructuredSelection) {
			setEnabled(updateSelection((IStructuredSelection) event.getSelection()));
		} else {
			setEnabled(false);
		}
	}

	public boolean updateSelection(IStructuredSelection selection) {
		selectedObjects = new ArrayList<EObject>();
		for (Iterator<?> objects = selection.iterator(); objects.hasNext();) {
			Object object = AdapterFactoryEditingDomain.unwrap(objects.next());
			if (object instanceof EObject) {
				selectedObjects.add((EObject) object);
			} else if (object instanceof Resource) {
				selectedObjects.addAll(((Resource) object).getContents());
			} else {
				return false;
			}
		}
		selectedObjects = EcoreUtil.filterDescendants(selectedObjects);
		return !selectedObjects.isEmpty();
	}

	@Override
	public void run() {
		for (EObject obj : selectedObjects)
			EcoreUtil.resolveAll(obj);
	}
}
