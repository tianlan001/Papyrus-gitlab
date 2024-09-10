/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.migrators;

import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;

/**
 * This interface define manager for the creation of migrators
 */
public interface IAtomicMigratorManager {

	/**
	 * Instantiate Atomic migrator if the treeNode correspond to its migrator
	 * 
	 * @param treeNode
	 *            the treeNode supposing matching with the migrator
	 * @return an atomic migrator if the treeNode correspond to its migrator, null otherwise
	 */
	public IAtomicMigrator instantiate(TreeNode treeNode);

}
