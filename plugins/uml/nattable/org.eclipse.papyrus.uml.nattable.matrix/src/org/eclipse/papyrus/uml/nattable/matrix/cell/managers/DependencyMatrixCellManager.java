/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.matrix.cell.managers;

import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * The cell manager for UML Dependency
 *
 */
public class DependencyMatrixCellManager extends AbstractUMLGenericMatrixRelationshipCellManager {

	/**
	 * 
	 * Constructor.
	 *
	 */
	public DependencyMatrixCellManager() {
		super(UMLPackage.eINSTANCE.getDependency());
	}
}
