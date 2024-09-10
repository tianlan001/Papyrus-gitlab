/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 539160
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.packages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.factory.AtomicMigratorsFactory;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.ProfileUtil;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.packages.IMovePackageMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.MovePackageDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 *
 * If a package is moved to another profile then the migration tool shall focus
 * on the preservation of the stereotype applications available at the profiled model.
 *
 * If the package was moved in a profile that is already applied on the profiled
 * model then the migration is trivial and every stereotype application shall remain.
 *
 * If the package was moved in a profile that is not already applied on the
 * profiled model then the migration tool asks the designer if the profile should
 * be applied. If the designer answers 'yes' every stereotype application shall remain
 * conversely if the answer is 'no' then every stereotype application shall be deleted.
 *
 */
public class MovePackageMigrator extends AbstractMigrator implements IMovePackageMigrator {

	Package movedPackage;

	EObject newContainer;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public MovePackageMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 *
	 * Is valid if:
	 * 1] the treeNode is a moved node
	 * 2] the moved element is a Package and not a Profile
	 * 3] the new container is in the currently profile use for the migration
	 * 4] the new container is different to the current container
	 *
	 * @param treeNode
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode treeNode) {
		if (TreeNodeUtils.isMoveChange(treeNode)) {
			Object element = TreeNodeUtils.getMovedElement(treeNode);
			EObject oldContainer = TreeNodeUtils.getMovedSourceContainer(treeNode, MigratorProfileApplication.comparison);
			if (element instanceof Package && !(element instanceof Profile) && oldContainer != ((Package) element).getOwner()) {
				if (oldContainer == MigratorProfileApplication.appliedProfile) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method is used to evaluate if the specified node correspond to the
	 * change that we expect to manage with
	 *
	 * @return return true if the treeNode is the expected change, false otherwise
	 */
	@Override
	public boolean isValid() {
		return isValid(treeNode);
	}

	/**
	 * If the package new container is not yet applied in the model
	 * then we ask the user if he want to apply it, if his answer is yes we reapply it
	 * otherwise we remove every stereotype application of stereotype owned by the moved package
	 */
	@Override
	public void migrationAction() {
		if (isDisplayDialogPreference()) {
			if (newContainer instanceof Package) {
				Profile nearestProfile = ProfileUtil.getNearestProfile((Package) newContainer);
				Profile profile = getAppliedProfile(nearestProfile);
				if (profile == null) { // the new container is in a profile which is not applied yet
					MovePackageDialog dialog = new MovePackageDialog(Display.getDefault().getActiveShell(), movedPackage, nearestProfile);
					dialog.open();
					if (dialog.isReapply()) { // the answer is yes: apply profile
						MigratorProfileApplication.profiledModel.applyProfile(nearestProfile);
						MigratorProfileApplication.newAppliedProfile.add(nearestProfile);
					} else { // the answer is no: delete stereotype application
						List<Stereotype> ownedStereotype = new ArrayList<>();
						ProfileUtil.findAllStereotypes(movedPackage, ownedStereotype);
						for (Stereotype stereotype : ownedStereotype) {
							List<StereotypeApplicationDescriptor> list = StereotypeApplicationRegistry.getStereotypeApplicationDescriptors(stereotype);
							for (StereotypeApplicationDescriptor descriptor : list) {
								descriptor.getOwner().unapplyStereotype(descriptor.getStereotype());
							}
							// remove the list of unapplied stereotype to avoid that later we try to migrate an unapplied stereotype
							StereotypeApplicationRegistry.stereotypeApplicationList.removeAll(list);
						}
					}
				}
			}
		}
	}

	/**
	 * Check if the profile in parameter is applied in the {@link AtomicMigratorsFactory#profiledModel}.
	 * This method check recursively if the profiled is applied. As it is says in the norm
	 * UML2.5 - 12.3.3.3 Applying a Profile means recursively applying all its nested and imported Profiles.
	 *
	 * @param profile
	 * @return return the applied profile if it is find, null otherwise
	 */
	private Profile getAppliedProfile(Profile profile) {
		Profile appliedProfile = MigratorProfileApplication.profiledModel.getAppliedProfile(profile.getQualifiedName(), true);
		if (appliedProfile == null) {
			if (profile.getOwner() != null && profile.getOwner() instanceof Package) {
				return getAppliedProfile((Profile) profile.getOwner());
			}
		}
		return appliedProfile;
	}

	/**
	 * Initialize attributes from the treeNode
	 */
	@Override
	public void initAttributes() {
		if (isValid()) {
			movedPackage = (Package) TreeNodeUtils.getMovedElement(treeNode);
			newContainer = TreeNodeUtils.getMovedTargetContainer(treeNode);
		}
	}

	/**
	 * Get the priority, this priority will define the order of execution of migrators.
	 * If the priority is 0 it will be execute last.
	 *
	 * @return the priority
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	/**
	 * Get the value of the preference for the specific dialog
	 *
	 * @return true if the dialog should be display
	 */
	private boolean isDisplayDialogPreference() {
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		return prefStore.getBoolean(ProfileMigrationPreferenceConstants.PACKAGE_MOVE);
	}

	/**
	 * Get the moved package
	 *
	 * @return the movedPackage
	 */
	@Override
	public Package getMovedPackage() {
		return movedPackage;
	}

	/**
	 * Get the new container
	 *
	 * @return the newContainer
	 */
	@Override
	public EObject getNewContainer() {
		return newContainer;
	}

	/**
	 * Get the new container of the element
	 *
	 * @return the new container of the element
	 */
	@Override
	public Element getTargetContainer() {
		return (Element) getNewContainer();
	}

	/**
	 * Get the old container of the element (before the move)
	 *
	 * @return the old container of the element
	 */
	@Override
	public Element getSourceContainer() {
		return (Element) TreeNodeUtils.getMovedSourceContainer(treeNode, MigratorProfileApplication.comparison);
	}

	/**
	 * Get the moved element
	 *
	 * @return the moved element
	 */
	@Override
	public Element getMovedElement() {
		return getMovedPackage();
	}

}
