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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 538914
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.migrators.atomic.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.property.IChangeLowerMultiplicityFromPropertyMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.AbstractChangeMultiplicityDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.ChangeLowerMultiplicityDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * If the loverValue feature of a property is modified then the migration tool shall maintain the validity of the model.
 *
 * If the lower value is changed from 0 to something else, means that as many as the lowerValue values are mandatory
 * the migration tool shall upgrade each application of the stereotype owning this property. The upgrade consist in
 * asking the designer which value he want to set on the slot corresponding of this property.
 *
 * If the lower value is changed from something to 0 the migration tool shall not do anything.
 *
 */
public class ChangeLowerMultiplicityFromPropertyMigrator extends AbstractMigrator implements IChangeLowerMultiplicityFromPropertyMigrator {

	private int newValue;

	private int oldValue;

	private Property property;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public ChangeLowerMultiplicityFromPropertyMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 *
	 * Is valid if:
	 * 1] the treeNode is a changed node
	 * 1.2] the modified element is a LiteralInteger
	 * 1.3] the changed structural feature is the feature value of a LiteralInteger
	 * 1.4] the LiteralInteger is owned by a property as a lowerValue
	 * 2] the treeNode is a added node
	 * 2.1] the added element is a LiteraglInteger
	 * 2.3] the structural feature referencing this added element is the lowerValue of a Property
	 *
	 * @param treeNode
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode treeNode) {
		if (TreeNodeUtils.isChangeType(treeNode, MigratorProfileApplication.appliedProfile)) {
			EObject element = TreeNodeUtils.getChangedElement(treeNode);
			EStructuralFeature structuralFeature = TreeNodeUtils.getChangedAttribute(treeNode);
			if (element instanceof LiteralInteger && structuralFeature == UMLPackage.eINSTANCE.getLiteralInteger_Value()) {
				Element property = ((LiteralInteger) element).getOwner();
				if (property instanceof Property) {
					// check if the added LiteralInteger is the lower multiplicity
					if (((Property) property).getLowerValue() == element) {
						Object oldElement = TreeNodeUtils.getChangedOldElement(treeNode);
						if (oldElement instanceof LiteralInteger) {
							Element oldProperty = ((LiteralInteger) oldElement).getOwner();
							if (oldProperty instanceof Property) {
								if (((Property) oldProperty).getLowerValue() == oldElement) {
									return true;
								}
							}
						}
					}
				}
			}
		} else if (TreeNodeUtils.isAddType(treeNode, MigratorProfileApplication.appliedProfile)) { // on first modification of the multiplicity, the lieteralInteget is add
			EObject element = TreeNodeUtils.getAddedElement(treeNode);
			if (element instanceof LiteralInteger) {
				Element property = ((LiteralInteger) element).getOwner();
				if (property instanceof Property) {
					EStructuralFeature structuralFeature = TreeNodeUtils.getAddedStructuralFeature(treeNode);
					if (structuralFeature == UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue()) {
						if (!(TreeNodeUtils.isAddType(treeNode.getParent(), MigratorProfileApplication.appliedProfile) && TreeNodeUtils.getAddedElement(treeNode.getParent()) instanceof Property)) { // when it is not an newly add property
							return true;
						}
					}
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
	 * Initialize attributes from the treeNode
	 */
	@Override
	public void initAttributes() {
		if (isValid()) {
			if (TreeNodeUtils.isChangeType(treeNode, MigratorProfileApplication.appliedProfile)) {
				EObject element = TreeNodeUtils.getChangedElement(treeNode);
				newValue = ((LiteralInteger) element).getValue();
				property = (Property) ((LiteralInteger) element).getOwner();
				EObject oldElement = TreeNodeUtils.getChangedOldElement(treeNode);
				oldValue = ((LiteralInteger) oldElement).getValue();
			} else if (TreeNodeUtils.isAddType(treeNode, MigratorProfileApplication.appliedProfile)) { // on first modification of the multiplicity, the lieteralInteget is add
				EObject element = TreeNodeUtils.getAddedElement(treeNode);
				newValue = ((LiteralInteger) element).getValue();
				oldValue = 1; // defaultValue
				property = (Property) ((LiteralInteger) element).getOwner();
			}
		}
	}

	/**
	 * For each stereotype application of the stereotype owning the modified property,
	 * we ask the use to add values to match with the new multiplicity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void migrationAction() {
		if (isDisplayDialogPreference()) {
			if (newValue > oldValue) { // means that the slot needs more values
				if (property.getOwner() instanceof Stereotype) {
					List<StereotypeApplicationDescriptor> list = StereotypeApplicationRegistry.getAllStereotypeApplicationDescriptors((Stereotype) property.getOwner());
					if (!list.isEmpty()) {
						// get the map of elements
						Map<Element, List<Stereotype>> mapElementToStereotype = new HashMap<>();
						for (StereotypeApplicationDescriptor descriptor : list) {
							EObject stereotypeApplication = descriptor.getOwner().getStereotypeApplication(descriptor.getStereotype());
							if (stereotypeApplication != null) {
								EStructuralFeature feature = stereotypeApplication.eClass().getEStructuralFeature(UML2Util.getValidJavaIdentifier(property.getName()));
								if (feature != null) {
									Object values = stereotypeApplication.eGet(feature);
									if (values instanceof List) {
										if (((List<Object>) values).size() < newValue) {
											if (mapElementToStereotype.get(descriptor.getOwner()) == null) {
												List<Stereotype> stereotypes = new ArrayList<>();
												stereotypes.add(descriptor.getStereotype());
												mapElementToStereotype.put(descriptor.getOwner(), stereotypes);
											} else {
												mapElementToStereotype.get(descriptor.getOwner()).add(descriptor.getStereotype());
											}
										}
									}
								}
							}
						}
						if (!mapElementToStereotype.isEmpty()) {
							AbstractChangeMultiplicityDialog dialog = new ChangeLowerMultiplicityDialog(Display.getDefault().getActiveShell(), property, oldValue, newValue, mapElementToStereotype);
							dialog.open();
						}
					}
				}
			}
		}
		// otherwise the minimum number of value decrease to the constraint is less important
	}

	/**
	 * @see org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator#getPriority()
	 *
	 */
	@Override
	public int getPriority() {
		return 49;
	}

	/**
	 * Get the value of the preference for the specific dialog
	 *
	 * @return true if the dialog should be display
	 */
	private boolean isDisplayDialogPreference() {
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		return prefStore.getBoolean(ProfileMigrationPreferenceConstants.CHANGE_LOWER_MULTIPLICITY_PROPERTY);
	}

	/**
	 * Get the new multiplicity
	 *
	 * @return the newValue
	 */
	@Override
	public int getValue() {
		return newValue;
	}

	/**
	 * Get the old multiplicity
	 *
	 * @return the oldValue
	 */
	@Override
	public int getOldValue() {
		return oldValue;
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
		return UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue();
	}

}
