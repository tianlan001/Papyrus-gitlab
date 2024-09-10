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
 * Base class for structure representing sash in Checker
 * @author cedric dumoulin
 *
 */
public abstract class AbstractSash extends PanelTerm {

	protected PanelTerm leftup;
	protected PanelTerm rightdown;
	
	/**
	 * Constructor.
	 *
	 */
	public AbstractSash(PanelTerm left, PanelTerm right) {
		this.leftup = left;
		this.rightdown = right;
	}
	
	/**
	 * Constructor.
	 *
	 * @param name
	 * @param up
	 * @param down
	 */
	public AbstractSash(String name, PanelTerm left, PanelTerm right) {
		super(name);
		this.leftup = left;
		this.rightdown = right;
	}

	/**
	 * @return the leftup
	 */
	public PanelTerm getLeftup() {
		return leftup;
	}

	/**
	 * @return the rightdown
	 */
	public PanelTerm getRightdown() {
		return rightdown;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.di.sashmodel.query.IQueryTerm#visit(org.eclipse.papyrus.infra.core.sasheditor.di.sashmodel.query.IQueryVisitor)
	 *
	 * @param visitor
	 * @throws QueryException 
	 */
	public abstract void accept(IQueryVisitor visitor, EObject modelObject) throws QueryException ;

	/**
	 * @return The name used in toString
	 */
	protected abstract String getStringName();

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString() {
		
		StringBuffer buff = new StringBuffer(getStringName());
		
		buff.append("(");
		buff.append(leftup.toString());
		buff.append(", ");
		buff.append(rightdown.toString());
		buff.append(")");
		
		return buff.toString();
	}

}
