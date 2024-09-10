/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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

package org.eclipse.papyrus.uml.profile.drafter.ui.model;


/**
 * Interface for visitors used to visit models.
 * 
 * @author cedric dumoulin
 *
 */
public interface IModelVisitor {

	public void visit(StereoptypeModel model);
	
	public void visit(PropertyModel model);
	
	public void visit(MetaclassModel model);
	
	public void visit(ExtendedStereotypeModel  model);
	
}
