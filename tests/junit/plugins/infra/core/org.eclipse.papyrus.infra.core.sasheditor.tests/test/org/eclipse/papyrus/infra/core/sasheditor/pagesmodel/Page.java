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

package org.eclipse.papyrus.infra.core.sasheditor.pagesmodel;


/**
 * This represent a page in the Checker query.
 * 
 * @author cedric dumoulin
 *
 */
public class Page implements IModelObject {

	protected Object identifier;
	
	protected String name;

	/** To generate automatic name */
	static int count=1;
	
	/**
	 * Constructor.
	 *
	 */
	public Page() {
		// Automatic name generation
		this( "page"+count++);
	}
	
	/**
	 * Constructor.
	 *
	 * @param identifier
	 */
	public Page(Object identifier) {
		this.identifier = identifier;
		this.name = identifier.toString();
	}

	/**
	 * Constructor.
	 *
	 * @param name
	 * @param identifier
	 */
	public Page(String name) {
		this.name = name;
		this.identifier = name;
	}

	/**
	 * Constructor.
	 *
	 * @param name
	 * @param identifier
	 */
	public Page(String name, Object identifier) {
		this.name = name;
		this.identifier = identifier;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.IModelObject.sashmodel.query.IQueryTerm#accept(IPagesModelVisitor, EObject)
	 *
	 * @param visitor
	 * @throws PagesModelException 
	 */
	public <M> void accept(IPagesModelVisitor<M> visitor, M modelObject) throws PagesModelException {
		
		visitor.walk(this, modelObject);
	}

	/**
	 * @return the identifier
	 */
	public Object getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(Object identifier) {
		this.identifier = identifier;
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString() {
		
		return "Page()";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
