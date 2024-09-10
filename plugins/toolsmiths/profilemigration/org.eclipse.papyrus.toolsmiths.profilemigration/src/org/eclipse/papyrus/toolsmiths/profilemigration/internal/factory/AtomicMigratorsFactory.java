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

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.extensionPoint.AtomicMigratorRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.enumeration.DeleteEnumerationLiteralFromEnumerationMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.enumeration.MoveEnumerationLiteralFromEnumerationMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.packages.MovePackageMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.profile.MoveProfileMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.property.ChangeIsStaticFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.property.ChangeLowerMultiplicityFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.property.ChangeUpperMultiplicityFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.stereotype.AddPropertyToStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.stereotype.ChangeIsAbstractFromStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.stereotype.MoveStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;

/**
 * This factory instantiate IAtomicMigrator
 */
public class AtomicMigratorsFactory {

	public static final AtomicMigratorsFactory INSTANCE = new AtomicMigratorsFactory();

	/**
	 * Constructor
	 *
	 * @param profile
	 */
	private AtomicMigratorsFactory() {

	}

	/**
	 * Instantiate an IAtomicMigrator according to the specific TreeNode
	 * Create a list of each AtomicMigrator which are valid for this treeNode
	 *
	 * @param treeNode
	 * @return the list of IAtomicMigrator which are valid for this TreeNode
	 */
	public List<IAtomicMigrator> instantiateMigrator(TreeNode treeNode) {
		List<IAtomicMigrator> result = new ArrayList<>();
		if (ChangeLowerMultiplicityFromPropertyMigrator.isValid(treeNode)) {
			result.add(instantiateChangeLowerMultiplicityFromPropertyMigrator(treeNode));
		}
		if (ChangeUpperMultiplicityFromPropertyMigrator.isValid(treeNode)) {
			result.add(instantiateChangeUpperMultiplicityFromPropertyMigrator(treeNode));
		}
		if (ChangeIsStaticFromPropertyMigrator.isValid(treeNode)) {
			result.add(instantiateChangeIsStaticFromPropertyMigrator(treeNode));
		}
		if (ChangeIsAbstractFromStereotypeMigrator.isValid(treeNode)) {
			result.add(instantiateChangeIsAbstractFromStereotypeMigrator(treeNode));
		}
		if (AddPropertyToStereotypeMigrator.isValid(treeNode)) {
			result.add(instantiateAddPropertyToStereotypeMigrator(treeNode));
		}
		if (DeleteEnumerationLiteralFromEnumerationMigrator.isValid(treeNode)) {
			result.add(instantiateDeleteEnumerationLiteralFromEnumerationMigrator(treeNode));
		}
		if (MoveStereotypeMigrator.isValid(treeNode)) {
			result.add(instantiateMoveStereotypeMigrator(treeNode));
		}
		if (MovePackageMigrator.isValid(treeNode)) {
			result.add(instantiateMovePackageMigrator(treeNode));
		}
		if (MoveEnumerationLiteralFromEnumerationMigrator.isValid(treeNode)) {
			result.add(instantiateMoveEnumerationLiteralFromEnumerationMigrator(treeNode));
		}
		if (MoveProfileMigrator.isValid(treeNode)) {
			result.add(instantiateMoveProfileMigrator(treeNode));
		}

		// Instantiate migrator from extensionPoint
		for (AtomicMigratorRegistry.Descriptor descriptor : AtomicMigratorRegistry.INSTANCE.getRegistry()) {
			IAtomicMigrator migrator = descriptor.getInstance().instantiate(treeNode);
			if (migrator != null) {
				result.add(migrator);
			}
		}

		return result;
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the lower multiplicity of a property has been changed
	 */
	public IAtomicMigrator instantiateChangeLowerMultiplicityFromPropertyMigrator(TreeNode treeNode) {
		return new ChangeLowerMultiplicityFromPropertyMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the upper multiplicity of a property has been changed
	 */
	public IAtomicMigrator instantiateChangeUpperMultiplicityFromPropertyMigrator(TreeNode treeNode) {
		return new ChangeUpperMultiplicityFromPropertyMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the isStatic feature of a property has been changed
	 */
	public IAtomicMigrator instantiateChangeIsStaticFromPropertyMigrator(TreeNode treeNode) {
		return new ChangeIsStaticFromPropertyMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when the isAbstract feature of a stereotype has been changed
	 */
	public IAtomicMigrator instantiateChangeIsAbstractFromStereotypeMigrator(TreeNode treeNode) {
		return new ChangeIsAbstractFromStereotypeMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when a property has been added
	 */
	public IAtomicMigrator instantiateAddPropertyToStereotypeMigrator(TreeNode treeNode) {
		return new AddPropertyToStereotypeMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when an enumerationLiteral has been deleted from a enumeration
	 */
	public IAtomicMigrator instantiateDeleteEnumerationLiteralFromEnumerationMigrator(TreeNode treeNode) {
		return new DeleteEnumerationLiteralFromEnumerationMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when stereotype has been moved into a new container
	 */
	public IAtomicMigrator instantiateMoveStereotypeMigrator(TreeNode treeNode) {
		return new MoveStereotypeMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when package has been moved into a new container
	 */
	public IAtomicMigrator instantiateMovePackageMigrator(TreeNode treeNode) {
		return new MovePackageMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when an enumerationLiteral has been moved into a new enumeration
	 */
	public IAtomicMigrator instantiateMoveEnumerationLiteralFromEnumerationMigrator(TreeNode treeNode) {
		return new MoveEnumerationLiteralFromEnumerationMigrator(treeNode);
	}

	/**
	 * @param treeNode
	 * @return the migrator responsible of the migration when profile has been moved into a new container
	 */
	public IAtomicMigrator instantiateMoveProfileMigrator(TreeNode treeNode) {
		return new MoveProfileMigrator(treeNode);
	}
}
