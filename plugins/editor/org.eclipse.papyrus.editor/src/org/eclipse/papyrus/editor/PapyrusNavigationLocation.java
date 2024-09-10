/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.editor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.NavigationLocation;

public class PapyrusNavigationLocation extends NavigationLocation {
	private URI pageURI;
	
	/**
	 * Constructor: Save editor selection, selected editor part, and selected page
	 *
	 */
	public PapyrusNavigationLocation(IEditorPart editorPart) {
		super(editorPart);
		if (editorPart instanceof PapyrusMultiDiagramEditor) {
			PapyrusMultiDiagramEditor papyrusEditor = (PapyrusMultiDiagramEditor) editorPart;
			// Important to set editorSelection and selectedEditorPart, otherwise navigation history breaks
			/*ISelection editorSelection = papyrusEditor.getEditorSite().getSelectionProvider().getSelection();//papyrusEditor.getSite().getSelectionProvider().getSelection()
			IEditorPart selectedEditorPart = papyrusEditor.getActiveEditor();*/
			IPage selectedPage = papyrusEditor.getISashWindowsContainer().getActiveSashWindowsPage();
			
			if (selectedPage != null && selectedPage.getRawModel() instanceof PageRef) {
				PageRef pageRef = (PageRef) selectedPage.getRawModel();
				if (pageRef != null && pageRef.getEmfPageIdentifier() != null) {
					pageURI = EcoreUtil.getURI(pageRef.getEmfPageIdentifier());
				}
			}
		}
	}

	/**
	 * @see org.eclipse.ui.INavigationLocation#restoreLocation()
	 *
	 */
	@Override
	public void restoreLocation() {
		IEditorPart editorPart = getEditorPart();
		if (editorPart instanceof PapyrusMultiDiagramEditor) {
			try {
				IPageManager pageManager = ServiceUtils.getInstance().getIPageManager(((PapyrusMultiDiagramEditor) editorPart).getServicesRegistry());
				EObject objectToOpen = ServiceUtils.getInstance().getModelSet(((PapyrusMultiDiagramEditor) editorPart).getServicesRegistry()).getEObject(pageURI, false);
				
				if (objectToOpen != null) {
					EObject context = EMFHelper.getEObject(objectToOpen);
					if (context != null) {
						try {
							pageManager = ServiceUtilsForEObject.getInstance().getIPageManager(context);
							if (pageManager.isOpen(objectToOpen)) {
								pageManager.selectPage(objectToOpen);
							} else {
								pageManager.openPage(objectToOpen);
							}
						} catch (Exception ex) {
							Activator.log.error(ex);
						}
					}
				}
				
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 * If the selected editor part matches, then merge.
	 * 
	 * @see org.eclipse.ui.INavigationLocation#mergeInto(org.eclipse.ui.INavigationLocation)
	 *
	 * @param currentLocation
	 * @return
	 */
	@Override
	public boolean mergeInto(INavigationLocation currentLocation) {
		if (currentLocation instanceof PapyrusNavigationLocation) {
			if (((PapyrusNavigationLocation) currentLocation).getPageURI() != null && pageURI != null) {
				return ((PapyrusNavigationLocation) currentLocation).getPageURI().equals(pageURI);
			}
		}
		return false;
	}

	public URI getPageURI() {
		return this.pageURI;
	}

	/**
	 * @see org.eclipse.ui.INavigationLocation#saveState(org.eclipse.ui.IMemento)
	 *
	 * @param memento
	 */
	@Override
	public void saveState(IMemento memento) {
		
		
	}

	/**
	 * @see org.eclipse.ui.INavigationLocation#restoreState(org.eclipse.ui.IMemento)
	 *
	 * @param memento
	 */
	@Override
	public void restoreState(IMemento memento) {
		
		
	}

	/**
	 * @see org.eclipse.ui.INavigationLocation#update()
	 *
	 */
	@Override
	public void update() {
		
		
	}
}
