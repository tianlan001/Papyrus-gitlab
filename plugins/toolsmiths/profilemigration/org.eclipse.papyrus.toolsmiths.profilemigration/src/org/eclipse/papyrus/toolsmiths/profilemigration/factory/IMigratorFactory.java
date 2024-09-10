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
public interface IMigratorFactory {

	/**
	 * Instantiate an IAtomicMigrator according to the specific TreeNode
	 * Create a list of each AtomicMigrator which are valid for this treeNode
	 *
	 * @param treeNode
	 * @return the list of IAtomicMigrator which are valid for this TreeNode
	 */
	public List<IAtomicMigrator> instantiateMigrator(TreeNode treeNode);

	// Properties
	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the lower multiplicity of a property has been changed
	 */
	public IAtomicMigrator instantiateChangeLowerMultiplicityFromPropertyMigrator(TreeNode treeNode);

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the upper multiplicity of a property has been changed
	 */
	public IAtomicMigrator instantiateChangeUpperMultiplicityFromPropertyMigrator(TreeNode treeNode);

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the isStatic feature of a property has been changed
	 */
	public IAtomicMigrator instantiateChangeIsStaticFromPropertyMigrator(TreeNode treeNode);

	// Stereotypes
	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the isAbstract feature of a stereotype has been changed
	 */
	public IAtomicMigrator instantiateChangeIsAbstractFromStereotypeMigrator(TreeNode treeNode);

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when a property has been added
	 */
	public IAtomicMigrator instantiateAddPropertyToStereotypeMigrator(TreeNode treeNode);

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when stereotype has been moved into a new container
	 */
	public IAtomicMigrator instantiateMoveStereotypeMigrator(TreeNode treeNode);

	// Enumeration
	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when an enumerationLiteral has been deleted from a enumeration
	 */
	public IAtomicMigrator instantiateDeleteEnumerationLiteralFromEnumerationMigrator(TreeNode treeNode);

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when an enumerationLiteral has been moved into a new enumeration
	 */
	public IAtomicMigrator instantiateMoveEnumerationLiteralFromEnumerationMigrator(TreeNode treeNode);

	// Package
	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when package has been moved into a new container
	 */
	public IAtomicMigrator instantiateMovePackageMigrator(TreeNode treeNode);

	// Profile
	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when profile has been moved into a new container
	 */
	public IAtomicMigrator instantiateMoveProfileMigrator(TreeNode treeNode);

}
