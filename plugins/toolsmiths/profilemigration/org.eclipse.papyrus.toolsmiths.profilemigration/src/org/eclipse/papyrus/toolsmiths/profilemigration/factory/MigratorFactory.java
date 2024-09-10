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

package org.eclipse.papyrus.toolsmiths.profilemigration.factory;

import java.util.List;

import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;

/**
 * This factory instantiate IAtomicMigrator
 */
public class MigratorFactory implements IMigratorFactory {

	public static final MigratorFactory INSTANCE = new MigratorFactory();

	private static org.eclipse.papyrus.toolsmiths.profilemigration.internal.factory.AtomicMigratorsFactory internalMigratorFactory;

	private MigratorFactory() {
		internalMigratorFactory = org.eclipse.papyrus.toolsmiths.profilemigration.internal.factory.AtomicMigratorsFactory.INSTANCE;
	}

	@Override
	public List<IAtomicMigrator> instantiateMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateChangeLowerMultiplicityFromPropertyMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateChangeLowerMultiplicityFromPropertyMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateChangeUpperMultiplicityFromPropertyMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateChangeUpperMultiplicityFromPropertyMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateChangeIsStaticFromPropertyMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateChangeIsStaticFromPropertyMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateChangeIsAbstractFromStereotypeMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateChangeIsAbstractFromStereotypeMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateAddPropertyToStereotypeMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateAddPropertyToStereotypeMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateDeleteEnumerationLiteralFromEnumerationMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateDeleteEnumerationLiteralFromEnumerationMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateMoveStereotypeMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateMoveStereotypeMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateMovePackageMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateMovePackageMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateMoveEnumerationLiteralFromEnumerationMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateMoveEnumerationLiteralFromEnumerationMigrator(treeNode);
	}

	@Override
	public IAtomicMigrator instantiateMoveProfileMigrator(TreeNode treeNode) {
		return internalMigratorFactory.instantiateMoveProfileMigrator(treeNode);
	}

}
