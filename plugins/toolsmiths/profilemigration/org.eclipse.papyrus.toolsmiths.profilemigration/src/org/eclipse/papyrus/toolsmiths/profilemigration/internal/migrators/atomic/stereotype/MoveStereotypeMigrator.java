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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 538912
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.stereotype;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.stereotype.IMoveStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.MoveStereotypeDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * If a Stereotype is moved to another profile then the migration tool shall focus
 * on the preservation of the stereotype applications available at the profiled model.
 *
 * If the stereotype was moved in a profile that is already applied on the
 * profiled model then the migration is trivial and every application of
 * this stereotype shall remain.
 *
 * If the stereotype was moved in a profile that is not already applied on the
 * profiled model then the migration tool asks the designer if the profile
 * should be applied. If the designer answers 'yes' every application of this
 * stereotype shall remain conversely if the answer is 'no' then every
 * application of this stereotype shall be deleted.
 */
public class MoveStereotypeMigrator extends AbstractMigrator implements IMoveStereotypeMigrator {

	private EObject newContainer;

	private Stereotype stereotype;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public MoveStereotypeMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 *
	 * Is valid if:
	 * 1] the treeNode is a moved node
	 * 2] the moved element is a Stereotype
	 * 3] the new container is in the currently profile use for the migration
	 *
	 * @param node
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode node) {
		if (TreeNodeUtils.isMoveChange(node)) {
			Object elt = TreeNodeUtils.getMovedElement(node);
			if (elt instanceof Stereotype) {
				if (TreeNodeUtils.getMovedSourceContainer(node, MigratorProfileApplication.comparison) == MigratorProfileApplication.appliedProfile) {
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
	 * If the new container of the stereotype is not applied in the model yet
	 * then we ask the user if he want to apply it, if his answer is yes we reapply it
	 * otherwise we remove every stereotype application of this stereotype
	 */
	@Override
	public void migrationAction() {
		if (isDisplayDialogPreference()) {
			if (newContainer instanceof Package) {
				Profile nearestProfile = getNearestProfile((Package) newContainer);
				Profile profile = getAppliedProfile(nearestProfile);
				List<StereotypeApplicationDescriptor> list = StereotypeApplicationRegistry.getStereotypeApplicationDescriptors(stereotype);
				if (profile == null && !list.isEmpty()) { // the new container is in a profile which is not applied yet
					MoveStereotypeDialog dialog = new MoveStereotypeDialog(Display.getDefault().getActiveShell(), stereotype, nearestProfile);
					dialog.open();
					if (dialog.isReapply()) { // the answer is yes: apply profile
						MigratorProfileApplication.profiledModel.applyProfile(nearestProfile);
						MigratorProfileApplication.newAppliedProfile.add(nearestProfile);
					} else { // the answer is no: delete stereotype application
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
	 * Check if the profile in parameter is applied in the {@link MigratorProfileApplication#profiledModel}.
	 *
	 * @param profile
	 * @return return the applied profile if it is find, null otherwise
	 */
	private Profile getAppliedProfile(Profile profile) {
		return MigratorProfileApplication.profiledModel.getAppliedProfile(profile.getQualifiedName(), true);
	}

	/**
	 * Retrieves the nearest profile that owns (either directly or indirectly) this element, or the element itself (if it is a profile)
	 *
	 * @param package1
	 * @return the nearest profile that owns this element
	 */
	private Profile getNearestProfile(Package package1) {
		if (package1 instanceof Profile) {
			return (Profile) package1;
		}
		if (package1.getOwner() instanceof Package) {
			return getNearestProfile((Package) package1.getOwner());
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator#initAttributes()
	 */
	@Override
	public void initAttributes() {
		if (isValid()) {
			Object elt = TreeNodeUtils.getMovedElement(treeNode);
			newContainer = TreeNodeUtils.getMovedTargetContainer(treeNode);
			stereotype = (Stereotype) elt;
		}
	}

	/**
	 * Get the value of the preference for the specific dialog
	 *
	 * @return true if the dialog should be display
	 */
	private boolean isDisplayDialogPreference() {
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		return prefStore.getBoolean(ProfileMigrationPreferenceConstants.STEREOTYPE_MOVE);
	}

	/**
	 * Get the moved stereotype
	 *
	 * @return the stereotype
	 */
	@Override
	public Stereotype getStereotype() {
		return stereotype;
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
	 * Get the new container of the element
	 *
	 * @return the new container of the element
	 */
	@Override
	public Element getTargetContainer() {
		return (Element) newContainer;
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
		return getStereotype();
	}
}
