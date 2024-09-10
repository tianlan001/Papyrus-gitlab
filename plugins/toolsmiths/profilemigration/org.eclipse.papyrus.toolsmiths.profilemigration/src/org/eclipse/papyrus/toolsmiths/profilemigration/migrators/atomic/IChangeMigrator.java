/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.Element;

/**
 * This is a migrator for the action of changing the value of the feature of an element
 */
public interface IChangeMigrator extends IAtomicMigrator {

	/**
	 * Get the changed element
	 * 
	 * @return the changed element
	 */
	public Element getChangedElement();

	/**
	 * Get the new value of the feature
	 * 
	 * @return the new value of the feature
	 */
	public Object getNewValue();

	/**
	 * Get the structural feature which is modified
	 * 
	 * @return the structural feature which is modified
	 */
	public EStructuralFeature getChangedStructuralFeature();

}
