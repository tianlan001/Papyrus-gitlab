/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.di.sashmodel.query;

import org.eclipse.emf.ecore.EObject;

/**
 *  Common ancestor of Panel (Folder and Sash) in queries structure.
 *  <br>
 *  This class can also be used a starting point of query expression (and so Folder, VSash and HSash).
 * 
 * @author cedric dumoulin
 *
 */
public abstract class PanelTerm implements IQueryExp {

	protected String name;
	
	
	/**
	 * Constructor.
	 *
	 */
	public PanelTerm() {
		
	}
	
	/**
	 * Constructor.
	 *
	 * @param name
	 */
	public PanelTerm(String name) {
		this.name = name;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.di.sashmodel.query.IQueryTerm#accept(org.eclipse.papyrus.infra.core.sasheditor.di.sashmodel.query.IQueryVisitor, org.eclipse.emf.ecore.EObject)
	 *
	 * @param visitor
	 * @param panel
	 * @throws QueryException
	 */
	public void accept(IQueryVisitor visitor, EObject panel)
			throws QueryException {
		

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
