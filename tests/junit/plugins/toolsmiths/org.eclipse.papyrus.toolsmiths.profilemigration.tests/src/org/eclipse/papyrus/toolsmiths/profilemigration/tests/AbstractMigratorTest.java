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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

/**
 * Abstract class for migrator tests
 */
public abstract class AbstractMigratorTest {

	protected static Profile profile;

	protected static Profile profileBefore;

	private static MigrationTestDelegate delegate;

	/**
	 * Migrate the toMigrateModel and return the corresponding list of IAtomicMigrator
	 * 
	 * @param toMigrateModelPath
	 *            the path to the model to migrate
	 * @param profileBeforePath
	 *            the path to the profile before modifications
	 * @param profileName
	 *            the name of the profile we want to migrate
	 * @return the corresponding list of IAtomicMigrator
	 */
	protected static List<IAtomicMigrator> getAtomicList(String toMigrateModelPath, String profileBeforePath, String profileName) {
		// 1] Get package to migrate
		ResourceSet packageToMigrateResourceSet = new ResourceSetImpl();
		URI uri_packageToMigrate = URI.createPlatformPluginURI(Activator.PLUGIN_ID + toMigrateModelPath, true);
		Resource packageToMigrateResource = packageToMigrateResourceSet.getResource(uri_packageToMigrate, true);
		Package packageToMigrate = (Package) packageToMigrateResource.getContents().get(0);

		// 2] Get new profile
		profile = packageToMigrate.getAppliedProfile(profileName);
		Resource profileAfterResource = profile.eResource();

		// 3] Get resource before modification
		ResourceSet profileBeforeResourceSet = new ResourceSetImpl();
		URI uri_profileBefore = URI.createPlatformPluginURI(Activator.PLUGIN_ID + profileBeforePath, true);
		final Resource profileBeforeResource = profileBeforeResourceSet.getResource(uri_profileBefore, true);
		profileBefore = (Profile) profileBeforeResource.getContents().get(0);

		// 4] Get atomic migrator list
		delegate = new MigrationTestDelegate(profileBeforeResource, profileAfterResource, profile);
		delegate.initAtomicList();
		return delegate.getAtomicList();
	}


}
