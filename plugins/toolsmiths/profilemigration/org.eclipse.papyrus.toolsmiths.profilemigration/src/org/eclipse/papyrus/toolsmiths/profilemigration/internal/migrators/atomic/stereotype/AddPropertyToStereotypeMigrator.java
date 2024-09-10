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

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.stereotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.stereotype.IAddPropertyToStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.AddPropertyToStereotypeDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * If a Stereotype is updated with a new property then a slot for this
 * property shall be available for each application of the stereotype
 * in the profile model.
 *
 * If the new property has a default value then each application of the modified
 * stereotype in the profiled model has its corresponding slot set with the default value.
 *
 * If the new property has no default value but a value is required (the minimal
 * multiplicity is greater or equal than 1) then each application of the modified
 * stereotype in the profiled model has its corresponding slot, the migration tool
 * shall ask the designer to know which value he wants to set.
 *
 * If the new property has no default value and a value is optional (the minimal
 * multiplicity is equal to 0) then each application of the modified stereotype
 * in the profiled model has it corresponding slot set to null.
 */
public class AddPropertyToStereotypeMigrator extends AbstractMigrator implements IAddPropertyToStereotypeMigrator {

	Stereotype stereotype;

	Property property;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public AddPropertyToStereotypeMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 *
	 * Is valid if:
	 * 1] the treeNode is an added node
	 * 2] the added element is a Property
	 * 3] the added property has a lower multiplicity greater than one
	 * 4] the added property has no default value
	 * 
	 * @param treeNode
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode treeNode) {
		if (TreeNodeUtils.isAddType(treeNode, MigratorProfileApplication.appliedProfile)) {
			EObject element = TreeNodeUtils.getAddedElement(treeNode);
			if (element instanceof Property) {
				if (((Property) element).getLower() > 0 && ((Property) element).getDefaultValue() == null && !(((Property) element).getAssociation() instanceof Extension)) {
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
	 * For each slot corresponding to the added property we ask the user to choose a value
	 */
	@Override
	public void migrationAction() {
		List<StereotypeApplicationDescriptor> list = StereotypeApplicationRegistry.getAllStereotypeApplicationDescriptors(stereotype);
		Map<Element, List<Stereotype>> mapElementToStereotype = new HashMap<>();
		for (StereotypeApplicationDescriptor descriptor : list) {
			if (mapElementToStereotype.get(descriptor.getOwner()) == null) {
				List<Stereotype> stereotypes = new ArrayList<>();
				stereotypes.add(descriptor.getStereotype());
				mapElementToStereotype.put(descriptor.getOwner(), stereotypes);
			} else {
				mapElementToStereotype.get(descriptor.getOwner()).add(descriptor.getStereotype());
			}
		}
		if (!mapElementToStereotype.isEmpty()) {
			AddPropertyToStereotypeDialog dialog = new AddPropertyToStereotypeDialog(Display.getDefault().getActiveShell(), stereotype, property, mapElementToStereotype);
			dialog.open();
		}
	}

	/**
	 * Initialize attributes from the treeNode
	 */
	@Override
	public void initAttributes() {
		if (isValid()) {
			property = (Property) TreeNodeUtils.getAddedElement(treeNode);
			stereotype = (Stereotype) property.getOwner();
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
	 * Get the stereotype owning the new property
	 *
	 * @return the stereotype
	 */
	@Override
	public Stereotype getStereotype() {
		return stereotype;
	}

	/**
	 * Get the Added property
	 *
	 * @return the property
	 */
	@Override
	public Property getProperty() {
		return property;
	}

	/**
	 * Get the added element
	 * 
	 * @return the added element
	 */
	@Override
	public Element getAddedElement() {
		return getProperty();
	}

	/**
	 * Get the added element container
	 * 
	 * @return the added element container
	 */
	@Override
	public Element getAddedElementContainer() {
		return getStereotype();
	}

}
