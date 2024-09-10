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
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.MigratorProfileApplication;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationDescriptor;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure.StereotypeApplicationRegistry;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.ProfileUtil;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.TreeNodeUtils;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.AbstractMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.stereotype.IChangeIsAbstractFromStereotypeMigrator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.ChangeIsAbstractFromStereotypeDialog;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * If a Stereotype becomes abstract the migration tool shall ask the designer
 * if he wants to replace applications of this stereotype by one or many
 * Stereotypes pick in a list. This list is defined by all specializations of the
 * abstract Stereotype available on applied profiles. If the answer is "no"
 * then the application of this stereotype is delete, otherwise selected
 * stereotypes are applied and inherited slot are preserved.
 *
 * If a Stereotype becomes concrete then the migration tool should not do anything.
 *
 */
public class ChangeIsAbstractFromStereotypeMigrator extends AbstractMigrator implements IChangeIsAbstractFromStereotypeMigrator {

	private Stereotype stereotype;

	private boolean isAbstract;

	/**
	 * Constructor.
	 *
	 * @param treeNode
	 *            the treeNode corresponding to this migrator
	 */
	public ChangeIsAbstractFromStereotypeMigrator(TreeNode treeNode) {
		super(treeNode);
	}

	/**
	 * This method check if the specified treeNode is a node representing the current change
	 *
	 * Is valid if:
	 * 1] the treeNode is a changed node
	 * 2] the modified element is a Stereotype
	 * 3] the changed structural feature is the feature isAbstract from a Stereotype
	 * 
	 * @param treeNode
	 * @return true if the treeNode represent the current change
	 */
	public static boolean isValid(TreeNode treeNode) {
		if (TreeNodeUtils.isChangeType(treeNode, MigratorProfileApplication.appliedProfile)) {
			EObject element = TreeNodeUtils.getChangedElement(treeNode);
			EStructuralFeature structuralFeature = TreeNodeUtils.getChangedAttribute(treeNode);
			if (element instanceof Stereotype && structuralFeature == UMLPackage.eINSTANCE.getClassifier_IsAbstract()) {
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
	 * We propose to the user to change each stereotype application of the modified stereotype
	 * by one or many stereotype application of a sub stereotype
	 */
	@Override
	public void migrationAction() {
		if (isAbstract) {
			List<Stereotype> subStereotypes = ProfileUtil.findAllSubStereotypes(stereotype, MigratorProfileApplication.profiledModel, true);
			List<StereotypeApplicationDescriptor> list = StereotypeApplicationRegistry.getStereotypeApplicationDescriptors(stereotype);
			if (!list.isEmpty()) {
				if (isDisplayDialogPreference()) {
					// 1] get the list of elements
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
					ChangeIsAbstractFromStereotypeDialog dialog = new ChangeIsAbstractFromStereotypeDialog(Display.getDefault().getActiveShell(), stereotype, subStereotypes, mapElementToStereotype);
					dialog.open();
					// 2] add sub stereotype if necessary
					for (Entry<Element, List<Stereotype>> entry : dialog.getResult().entrySet()) {
						Element element = entry.getKey();
						List<Stereotype> newStereotypeList = entry.getValue();
						StereotypeApplicationDescriptor stereotypeApplicationDescriptor = StereotypeApplicationRegistry.getStereotypeApplicationDescriptors(element, stereotype);
						if (stereotypeApplicationDescriptor != null) {
							for (Stereotype newStereotype : newStereotypeList) {
								if (stereotypeApplicationDescriptor.getOwner().isStereotypeApplicable(newStereotype)) {
									// 2.1] apply new stereotype
									EObject stereotypeApplication = stereotypeApplicationDescriptor.getOwner().applyStereotype(newStereotype);
									MigratorProfileApplication.newStereotypeApplication.add(stereotypeApplication);
									// 2.2] update descriptor registry
									StereotypeApplicationDescriptor descriptor = new StereotypeApplicationDescriptor(newStereotype, element);
									StereotypeApplicationRegistry.stereotypeApplicationList.add(descriptor);
									for (Entry<Property, Object> values : stereotypeApplicationDescriptor.getMapPropertyToApplicationValue().entrySet()) {
										// 2.3] set inherited values to the new stereotype
										stereotypeApplicationDescriptor.getOwner().setValue(newStereotype, values.getKey().getName(), values.getValue());
										// 2.4] update descriptor registry values
										for (Property attribute : newStereotype.getAttributes()) {
											Object value = descriptor.getOwner().getValue(newStereotype, attribute.getName());
											descriptor.getMapPropertyToApplicationValue().put(attribute, value);
										}
									}
								}
							}
						}
					}
				}
				// 3] remove the list of unapplied stereotype to avoid that later we try to migrate an unapplied stereotype
				StereotypeApplicationRegistry.stereotypeApplicationList.removeAll(list);
			}
		}
	}

	@Override
	public void initAttributes() {
		if (isValid()) {
			EObject element = TreeNodeUtils.getChangedElement(treeNode);
			stereotype = (Stereotype) element;
			isAbstract = stereotype.isAbstract();
		}
	}

	@Override
	public int getPriority() {
		// should be done before others because we add stereotype application to the model so migrators
		// should manage with those new stereotype applications
		return 100;
	}

	/**
	 * Get the value of the preference for the specific dialog
	 * 
	 * @return true if the dialog should be display
	 */
	private boolean isDisplayDialogPreference() {
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		return prefStore.getBoolean(ProfileMigrationPreferenceConstants.SUPER_STEREOTYPE_BECOMING_ABSTRACT);
	}

	/**
	 * Get the stereotype becoming abstract
	 *
	 * @return the stereotype
	 */
	@Override
	public Stereotype getStereotype() {
		return stereotype;
	}

	/**
	 * Get the new value of the feature isAbstract
	 *
	 * @return the isAbstract
	 */
	@Override
	public boolean getValue() {
		return isAbstract;
	}

	/**
	 * Get the changed element
	 * 
	 * @return the changed element
	 */
	@Override
	public Element getChangedElement() {
		return getStereotype();
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
		return UMLPackage.eINSTANCE.getClassifier_IsAbstract();
	}

}
