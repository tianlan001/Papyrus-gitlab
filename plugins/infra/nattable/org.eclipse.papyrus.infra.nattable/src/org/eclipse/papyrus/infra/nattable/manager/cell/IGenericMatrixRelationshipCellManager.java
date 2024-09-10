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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.cell;

import org.eclipse.emf.ecore.EClass;

/**
 * Common interface for matrix cell manager
 * 
 * @since 3.0
 *
 */
public interface IGenericMatrixRelationshipCellManager extends ICellManager /* , IUnsetValueCellManager */ { // unset have no sense for relationship

	/**
	 * 
	 * @return
	 * 		the EClass representing the managed relationship
	 */
	public EClass getManagedRelationship();
}
