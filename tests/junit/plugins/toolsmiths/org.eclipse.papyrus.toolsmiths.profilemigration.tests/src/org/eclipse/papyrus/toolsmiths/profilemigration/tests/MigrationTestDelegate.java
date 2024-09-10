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
package org.eclipse.papyrus.toolsmiths.profilemigration.tests;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.factory.MigratorFactory;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;
import org.eclipse.uml2.uml.Profile;

/**
 * This class is use to be able to access to IAtomicMigrators list for tests
 */
public class MigrationTestDelegate extends MigratorProfileApplication {

	private TreeNode treeNode;

	/**
	 * Constructor.
	 *
	 * @param before
	 *            the resource before modification
	 * @param after
	 *            the resource after modification
	 * @param profile
	 *            the migrated profile
	 */
	public MigrationTestDelegate(Resource before, Resource after, Profile profile) {
		treeNode = getTreeNode(before, after);
		appliedProfile = profile;
	}

	/**
	 * Initialize the atomicMigrator list
	 */
	public void initAtomicList() {
		super.initAtomicList(treeNode, MigratorFactory.INSTANCE);
	}

	/**
	 * Get the atomicList
	 * 
	 * @return the atomicList
	 */
	public List<IAtomicMigrator> getAtomicList() {
		return atomicList;
	}

}