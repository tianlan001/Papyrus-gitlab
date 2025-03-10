/*****************************************************************************
 * Copyright (c) 2013, 2023 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.search.results;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.papyrus.views.search.scope.IScopeEntry;
import org.eclipse.ui.PartInitException;

/**
 *
 * Implements a result entry that doesn't correspond to a real match. it is used to create hierarchies
 *
 */
public class ResultEntry extends AbstractResultEntry {



	public ResultEntry(Object source, IScopeEntry scopeEntry) {
		super(UNSPECIFIED, UNSPECIFIED, source, scopeEntry);


	}

	/**
	 *
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#elementToDisplay()
	 *
	 * @return
	 */
	@Override
	public Object elementToDisplay() {
		return this.getSource();



	}

	// @Override
	// public Object getSource() {
	// if(this.uriSource != null) {
	// ResourceSet resSet = ((ScopeEntry)this.getElement()).getModelSet();
	// return resSet.getEObject(this.uriSource, true);
	// } else if(this.uriResource != null) {
	//
	// IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	//
	//
	// IPath path = new Path(this.uriResource.getPath());
	// return root.getFile(path);
	// }
	// return null;
	//
	//
	// }

	/**
	 *
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#elementToCheckFilterFor()
	 *
	 * @return
	 */
	@Override
	public Object elementToCheckFilterFor() {
		// ResourceSet resSet = new ResourceSetImpl();
		// if(this.uriSource != null) {
		// return resSet.getEObject(uriSource, true);
		// }
		return this.getSource();
	}


	/**
	 *
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#openElement()
	 *
	 * @return
	 */
	@Override
	public Object openElement(OpenElementService service) throws ServiceException, PartInitException {
		if (this.getSource() instanceof EObject) {

			return service.openSemanticElement((EObject) this.getSource());
		}
		return null;
	}



}
