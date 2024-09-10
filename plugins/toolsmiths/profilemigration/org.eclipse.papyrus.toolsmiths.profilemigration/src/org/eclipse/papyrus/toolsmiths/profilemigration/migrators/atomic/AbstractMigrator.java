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

import org.eclipse.emf.edit.tree.TreeNode;

/**
 * This abstract class is used to initialize attribute during the construction of the object
 */
public abstract class AbstractMigrator {

	/**
	 * This is the treeNode corresponding to this migrator,
	 * this TreeNode represent the differences that this migrator expect
	 */
	public TreeNode treeNode;

	/**
	 * Constructor
	 *
	 * @param treeNode
	 *            represent the differences that this migrator expect
	 */
	public AbstractMigrator(TreeNode treeNode) {
		this.treeNode = treeNode;
		initAttributes();
	}

	/**
	 * Initialize attributes from the treeNode
	 */
	public abstract void initAttributes();

};
