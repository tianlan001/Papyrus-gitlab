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

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.enumeration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.enumeration.IDeleteEnumerationLiteralFromEnumeration;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.DeleteEnumerationLiteralFromEnumerationDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * If an enumeration literal is deleted from an enumeration in the profile specification and this
 * enumeration is used to type one or many properties of stereotypes then all stereotype
 * applications corresponding to the affected stereotype shall be upgraded. The upgrade
 * consists for each stereotype application in providing the designer with the possibility to
 * update the enumeration literal affected to the property whose specification was changed.
 */
public class DeleteEnumerationLiteralFromEnumerationMigrator extends AbstractMigrator implements IDeleteEnumerationLiteralFromEnumeration {

	protected Enumeration enumeration;

	protected EnumerationLiteral enumerationLiteral;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public DeleteEnumerationLiteralFromEnumerationMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 * 
	 * Is valid if:
	 * 1] the treeNode is a deleted node
	 * 2] the deleted element is a EnumerationLiteral
	 * 
	 * @param treeNode
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode treeNode) {
		if (TreeNodeUtils.isDeleteType(treeNode, MigratorProfileApplication.appliedProfile)) {
			EObject element = TreeNodeUtils.getDeletedElement(treeNode);
			if (element instanceof EnumerationLiteral && ((EnumerationLiteral) element).getOwner() instanceof Enumeration) {
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
	 * For each stereotype application using this enumerationLiteral as value of a slot,
	 * we propose to change the value of the slot
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void migrationAction() {
		if (isDisplayDialogPreference()) {
			Map<Element, List<Stereotype>> mapElementToStereotype = new HashMap<>();
			List<StereotypeApplicationDescriptor> descriptorList = StereotypeApplicationRegistry.stereotypeApplicationList;
			for (StereotypeApplicationDescriptor descriptor : descriptorList) {
				for (Property property : descriptor.getStereotype().getAllAttributes()) {
					if (property.getType() == enumeration) {
						if (descriptor.getMapPropertyToApplicationValue().get(property) instanceof Collection) {
							for (EObject object : ((Collection<EObject>) descriptor.getMapPropertyToApplicationValue().get(property))) {
								if (((EEnumLiteral) object).getName().equals(enumerationLiteral.getName())) {
									if (mapElementToStereotype.containsKey(descriptor.getOwner())) {
										mapElementToStereotype.get(descriptor.getOwner()).add(descriptor.getStereotype());
									} else {
										List<Stereotype> stereotypes = new ArrayList<>();
										stereotypes.add(descriptor.getStereotype());
										mapElementToStereotype.put(descriptor.getOwner(), stereotypes);
									}
								}
							}
						} else {
							if (descriptor.getMapPropertyToApplicationValue().get(property) == enumerationLiteral || descriptor.getMapPropertyToApplicationValue().get(property) == null) {
								if (mapElementToStereotype.containsKey(descriptor.getOwner())) {
									mapElementToStereotype.get(descriptor.getOwner()).add(descriptor.getStereotype());
								} else {
									List<Stereotype> stereotypes = new ArrayList<>();
									stereotypes.add(descriptor.getStereotype());
									mapElementToStereotype.put(descriptor.getOwner(), stereotypes);
								}
							}
						}
					}
				}
			}
			if (!mapElementToStereotype.isEmpty()) {
				DeleteEnumerationLiteralFromEnumerationDialog dialog = new DeleteEnumerationLiteralFromEnumerationDialog(Display.getDefault().getActiveShell(), enumeration, enumerationLiteral, mapElementToStereotype);
				dialog.open();
			}
		}
	}

	/**
	 * Initialize attributes from the treeNode
	 */
	@Override
	public void initAttributes() {
		if (isValid()) {
			enumerationLiteral = (EnumerationLiteral) TreeNodeUtils.getDeletedElement(treeNode);
			enumeration = (Enumeration) TreeNodeUtils.getDeletedElementOwner(treeNode);
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
		return 50;
	}

	/**
	 * Get the value of the preference for the specific dialog
	 *
	 * @return true if the dialog should be display
	 */
	private boolean isDisplayDialogPreference() {
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		return prefStore.getBoolean(ProfileMigrationPreferenceConstants.DELETE_ENUM_LITERAL_FROM_ENUMERATION);
	}

	/**
	 * Get the enumeration owning the deleted EnumerationLiteral
	 *
	 * @return the enumeration
	 */
	@Override
	public Enumeration getEnumeration() {
		return enumeration;
	}

	/**
	 * Get the deleted EnumerationLiteral
	 *
	 * @return the enumerationLiteral
	 */
	@Override
	public EnumerationLiteral getEnumerationLiteral() {
		return enumerationLiteral;
	}

	/**
	 * Get the deleted element
	 * 
	 * @return the deleted element
	 */
	@Override
	public Element getDeletedElement() {
		return getEnumerationLiteral();
	}

	/**
	 * Get the container of the element before its deletion
	 * 
	 * @return the container of the element before its deletion
	 */
	@Override
	public Element getDeletedElementContainer() {
		return getEnumeration();
	}

}
