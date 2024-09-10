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
package org.eclipse.papyrus.toolsmiths.profilemigration.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.toolsmiths.profilemigration.Messages;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.uml.tools.helper.IProfileApplicationDelegate;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;

/**
 * An implementation of the profile-application delegate protocol for externalize profile applications.
 * FIXME: Not working with external stereotype application @see{org.eclipse.papyrus.uml.decoratormodel.internal.providers#ExternalizedProfileApplicationDelegate}
 */
public class MigratorProfileApplicationDelegate implements IProfileApplicationDelegate {

	@Override
	public EList<EObject> reapplyProfile(Package package_, Profile profile, IProgressMonitor monitor) {
		EList<EObject> result = ECollections.<EObject> emptyEList();
		MigratorProfileApplication migratorProfileApplication = new MigratorProfileApplication();
		migratorProfileApplication.reapplyProfile(package_, profile);
		// TODO: return every new stereotype application
		return result;
	}

	@Override
	public boolean appliesTo(Package package_) {
		return true;
	}

	@Override
	public boolean appliesTo(ProfileApplication profileApplication) {
		return true;
	}

	@Override
	public Iterable<ProfileApplication> getProfileApplications(Package package_) {
		return package_.getProfileApplications();
	}

	@Override
	public ProfileApplication getProfileApplication(Package package_, Profile profile) {
		return package_.getProfileApplication(profile);
	}

	@Override
	public EList<EObject> applyProfile(Package package_, Profile profile, Package context, IProgressMonitor monitor) {
		return package_.applyProfile(profile);
	}

	@Override
	public Package getApplyingPackage(ProfileApplication profileApplication) {
		return profileApplication.getApplyingPackage();
	}

	@Override
	public Profile getAppliedProfile(ProfileApplication profileApplication) {
		return profileApplication.getAppliedProfile();
	}

	@Override
	public String getPreferenceConstant() {
		return "profile_migration_tool"; //$NON-NLS-1$
	}

	@Override
	public String getPreferenceLabel() {
		return Messages.MigratorProfileApplicationDelegate_PreferenceLabel;
	}

}
