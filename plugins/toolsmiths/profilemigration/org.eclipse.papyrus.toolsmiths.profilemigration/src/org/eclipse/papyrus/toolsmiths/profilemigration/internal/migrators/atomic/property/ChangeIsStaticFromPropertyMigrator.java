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
package org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.property;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.property.IChangeIsStaticFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.ChangeIsStaticFeatureDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * If the property becomes static then the migration tool shall change the
 * value of every slop corresponding to this property. To determine which
 * value the system shall set, it shall ask the designer which value he wants.
 */
public class ChangeIsStaticFromPropertyMigrator extends AbstractMigrator implements IChangeIsStaticFromPropertyMigrator {

	private Property property;

	private Stereotype stereotype;

	private boolean isStatic;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public ChangeIsStaticFromPropertyMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 *
	 * Is valid if:
	 * 1] the treeNode is a changed node
	 * 2] the modified element is a Property
	 * 3] the changed structural feature is the feature isStatic of a Property
	 * 
	 * @param treeNode
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode treeNode) {
		if (TreeNodeUtils.isChangeType(treeNode, MigratorProfileApplication.appliedProfile)) {
			EObject element = TreeNodeUtils.getChangedElement(treeNode);
			EStructuralFeature structuralFeature = TreeNodeUtils.getChangedAttribute(treeNode);
			if (element instanceof Property && structuralFeature == UMLPackage.eINSTANCE.getFeature_IsStatic()) {
				return true;
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
	 * We ask the user if he want use a unique value for each slot corresponding to this property
	 */
	@Override
	public void migrationAction() {
		if (isDisplayDialogPreference()) {
			if (isStatic) {
				List<StereotypeApplicationDescriptor> list = StereotypeApplicationRegistry.getAllStereotypeApplicationDescriptors(stereotype);
				if (!list.isEmpty()) {
					ChangeIsStaticFeatureDialog dialog = new ChangeIsStaticFeatureDialog(Display.getDefault().getActiveShell(), list.get(0).getStereotype(), property, list.get(0).getOwner());
					dialog.open();
					Object selectedValue = list.get(0).getOwner().getValue(list.get(0).getStereotype(), property.getName());
					for (int i = 1; i < list.size(); i++) {
						list.get(i).getOwner().setValue(list.get(i).getStereotype(), property.getName(), selectedValue);
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
			EObject element = TreeNodeUtils.getChangedElement(treeNode);
			property = (Property) element;
			stereotype = (Stereotype) property.getOwner();
			isStatic = property.isStatic();
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
		return 47;
	}

	/**
	 * Get the value of the preference for the specific dialog
	 * 
	 * @return true if the dialog should be display
	 */
	private boolean isDisplayDialogPreference() {
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		return prefStore.getBoolean(ProfileMigrationPreferenceConstants.CHANGE_IS_STATIC_FEATURE_PROPERTY);
	}

	/**
	 * Get the modified property
	 *
	 * @return the property
	 */
	@Override
	public Property getProperty() {
		return property;
	}

	/**
	 * Get the stereotype owning the modified property
	 *
	 * @return the stereotype
	 */
	@Override
	public Stereotype getStereotype() {
		return stereotype;
	}

	/**
	 * Get the new value of the feature isStatic
	 *
	 * @return the isStatic
	 */
	@Override
	public boolean getValue() {
		return isStatic;
	}

	/**
	 * Get the changed element
	 * 
	 * @return the changed element
	 */
	@Override
	public Element getChangedElement() {
		return getProperty();
	}

	/**
	 * Get the new value of the feature
	 * 
	 * @return the new value of the feature
	 */
	@Override
	public Object getNewValue() {
		return getValue();
	}

	/**
	 * Get the structural feature which is modified
	 * 
	 * @return the structural feature which is modified
	 */
	@Override
	public EStructuralFeature getChangedStructuralFeature() {
		return UMLPackage.eINSTANCE.getFeature_IsStatic();
	}

}
