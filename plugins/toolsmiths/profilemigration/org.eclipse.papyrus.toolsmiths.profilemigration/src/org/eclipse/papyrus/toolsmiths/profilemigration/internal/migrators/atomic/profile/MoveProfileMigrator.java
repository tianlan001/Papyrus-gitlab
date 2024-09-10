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
package org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.profile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.ProfileUtil;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.profile.IMoveProfileMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.MoveProfileDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * If a profile is moved to another profile then the migration tool shall focus
 * on the preservation of the stereotype applications available at the profiled model.
 *
 * If the profile was moved in a profile that is already applied on the profiled
 * model then the migration is trivial and every stereotype application shall remain.
 *
 * If the profile was moved in a profile that is not already applied on the profiled model
 * then the migration tool asks the designer if the moved profile should be reapplied.
 * If the designer answers 'yes' every stereotype application shall remain conversely if
 * the answer is 'no' then every stereotype application shall be deleted.
 */
public class MoveProfileMigrator extends AbstractMigrator implements IMoveProfileMigrator {

	private Profile movedProfile;

	private Package newContainer;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public MoveProfileMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 *
	 * Is valid if:
	 * 1] the treeNode is a moved node
	 * 2] the moved element is a Profile
	 * 3] the moved profile is the currently profile use for the migration
	 * 4] the new container is different to the current container
	 *
	 * @param treeNode
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode treeNode) {
		if (TreeNodeUtils.isMoveChange(treeNode)) {
			Object element = TreeNodeUtils.getMovedElement(treeNode);
			EObject oldContainer = TreeNodeUtils.getMovedSourceContainer(treeNode, MigratorProfileApplication.comparison);
			if (element instanceof Profile && oldContainer != ((Profile) element).getOwner()) {
				if (element == MigratorProfileApplication.appliedProfile) {
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
	 * If the profile new container is not yet applied in the model
	 * then we ask the designer if the moved profile should be reapplied
	 * otherwise we remove every stereotype application of stereotypes owned by the moved profile
	 */
	@Override
	public void migrationAction() {
		if (isDisplayDialogPreference()) {
			Profile nearestProfile = ProfileUtil.getNearestProfile(newContainer);
			if (MigratorProfileApplication.profiledModel.getProfileApplication(nearestProfile) == null) {
				MoveProfileDialog dialog = new MoveProfileDialog(Display.getDefault().getActiveShell(), movedProfile, nearestProfile);
				dialog.open();
				if (dialog.isReapply()) {
					MigratorProfileApplication.profiledModel.applyProfile(movedProfile);
				} else { // the answer is no: delete stereotype application
					List<Stereotype> ownedStereotype = new ArrayList<>();
					ProfileUtil.findAllStereotypes(movedProfile, ownedStereotype);
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

	/**
	 * Initialize attributes from the treeNode
	 */
	@Override
	public void initAttributes() {
		if (isValid()) {
			movedProfile = (Profile) TreeNodeUtils.getMovedElement(treeNode);
			newContainer = (Package) TreeNodeUtils.getMovedTargetContainer(treeNode);
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
		return 100;
	}

	/**
	 * Get the value of the preference for the specific dialog
	 *
	 * @return true if the dialog should be display
	 */
	private boolean isDisplayDialogPreference() {
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		return prefStore.getBoolean(ProfileMigrationPreferenceConstants.PROFILE_MOVE);
	}

	/**
	 * Get the new container of the element
	 *
	 * @return the new container of the element
	 */
	@Override
	public Element getTargetContainer() {
		return newContainer;
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
		return movedProfile;
	}

	/**
	 * Get the moved profile
	 *
	 * @return the moved profile
	 */
	@Override
	public Profile getProfile() {
		return movedProfile;
	}

}
