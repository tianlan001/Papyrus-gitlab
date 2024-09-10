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
 * Common ancestor of all object in the Model.
 * All object that can be used in a query should implement this interface.
 * 
 * @author cedric dumoulin
 */
public interface IModelObject {

	/**
	 * Visit the query.
	 * @param visitor
	 * @throws PagesModelException 
	 */
	public <M> void accept(IPagesModelVisitor<M> visitor, M panel) throws PagesModelException;
	
	/**
	 * Get the name associated to the query part.
	 * Can return null;
	 */
	public String getName();
}
