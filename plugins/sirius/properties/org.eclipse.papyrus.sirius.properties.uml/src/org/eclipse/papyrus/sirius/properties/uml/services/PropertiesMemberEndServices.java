/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.sirius.properties.uml.Activator;
import org.eclipse.papyrus.sirius.properties.uml.messages.Messages;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This service class includes all services used for memberEnds of an
 * {@link Association} UML model element.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesMemberEndServices {

	/**
	 * Association constant used for owner feature on {@link Property.}
	 */
	private static final String ASSOCIATION = "Association"; //$NON-NLS-1$

	/**
	 * Classifier constant used for owner feature on {@link Property.}
	 */
	private static final String CLASSIFIER = "Classifier"; //$NON-NLS-1$

	/**
	 * Message to display when ownedAttribute feature is not found.
	 */
	private static final String FEATURE_NOT_FOUND = Messages.OwnerObservableValue_FeatureNotFound;

	/**
	 * Message to display when type is null.
	 */
	private static final String NULL_TYPE = Messages.OwnerObservableValue_NullType;

	/**
	 * Owner feature name on {@link Property.}
	 */
	private static final String OWNER = "owner"; //$NON-NLS-1$

	/**
	 * Message to display when type is unknown.
	 */
	private static final String UNKNOWN_TYPE = Messages.OwnerObservableValue_UnknownType;

	/**
	 * Message to display when owner feature cannot be changed.
	 */
	private static final String UNMODIFIED_FEATURE = Messages.OwnerObservableValue_OwnerFeatureUnmodified;

	// NavigationObservableValue services

	/**
	 * Set navigable property on {@link Property}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.NavigationObservableValue.doSetValue(Object)}
	 * 
	 * @param memberEnd
	 *            the {@link Property} to update,
	 * @param isNavigable
	 *            <code>true</code> if the memberEnd is navigable,
	 *            <code>false</code> otherwise.
	 */
	public void setNavigable(Property memberEnd, boolean isNavigable) {
		if (memberEnd.isNavigable() == isNavigable) {
			throw new OperationCanceledException();
		}
		Association association = memberEnd.getAssociation();

		EList<Property> navigableOwnedEnds = association.getNavigableOwnedEnds();
		if (isNavigable) {
			navigableOwnedEnds.add(memberEnd);
		} else {
			if (memberEnd.getOwningAssociation() == null && memberEnd.getOwner() instanceof Classifier) {
				association.getOwnedEnds().add(memberEnd);
			}
			if (navigableOwnedEnds.contains(memberEnd)) {
				navigableOwnedEnds.remove(memberEnd);
			}
		}
	}

	// OwnerObservableValue services

	/**
	 * Get owner information from a given {@link Property}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.OwnerObservableValue.doGetValue()}
	 * 
	 * @param memberEnd
	 *            the {@link Property} with contains the owner,
	 * @return the owner of the {@link Property}.
	 */
	public String getOwner(Property memberEnd) {
		if (memberEnd.getAssociation().getOwnedEnds().contains(memberEnd)) {
			return ASSOCIATION;
		} else {
			return CLASSIFIER;
		}
	}

	/**
	 * Get list of owner candidates.
	 * 
	 * @param obj
	 *            A given element which contains owner information.
	 * @return list of owner candidates.
	 */
	public List<String> getOwnerEnumerations(Element obj) {
		return Arrays.asList(ASSOCIATION, CLASSIFIER);
	}

	/**
	 * Set owner information into a given {@link Property}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.OwnerObservableValue.doSetValue(Object)}
	 * 
	 * @param memberEnd
	 *            the {@link Property} which contain owner information
	 * @param owner
	 *            the owner information to set.
	 */
	@SuppressWarnings("unchecked")
	public void setOwner(Property memberEnd, String owner) {
		boolean isOwnedByAssociation = ASSOCIATION.equals(owner);

		Association association = memberEnd.getAssociation();

		if (association.getMemberEnds().size() > 2) {
			IStatus status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, UNMODIFIED_FEATURE);
			Activator.getDefault().getLog().log(status);
			throw new OperationCanceledException();
		}
		if (isOwnedByAssociation) { // Owned by Association
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(association);
			if (provider != null) {
				association.getOwnedEnds().add(memberEnd);
			}
		} else { // Owned by Classifier
			Type ownerType;
			List<Type> ownerList = association.getEndTypes();

			if (ownerList.get(0).equals(memberEnd.getType()) && ownerList.size() > 1) {
				ownerType = ownerList.get(1);
			} else {
				ownerType = ownerList.get(0);
			}

			EStructuralFeature ownedAttributeFeature = getOwnedAttributeFeatureForType(ownerType);
			if (ownedAttributeFeature != null) {
				((EList<Property>) ownerType.eGet(ownedAttributeFeature)).add(memberEnd);
			}
		}
	}

	/**
	 * Get the owned attribute feature for the given type.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.tools.databinding.ownedattributehelper.getOwnedAttributeFeatureForType(Type)}.
	 *
	 * @param type
	 *            a UML type
	 * @return the owned attribute feature for the given type, or <code>null</code>
	 *         when not found
	 * @since 4.1
	 */
	public EStructuralFeature getOwnedAttributeFeatureForType(Type type) {
		EStructuralFeature ownedAttributeFeature = null;
		if (null == type) {
			IStatus status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, NULL_TYPE);
			Activator.getDefault().getLog().log(status);
		} else {
			if (type instanceof StructuredClassifier) {
				ownedAttributeFeature = UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute();
			} else if (type instanceof Interface) {
				ownedAttributeFeature = UMLPackage.eINSTANCE.getInterface_OwnedAttribute();
			} else if (type instanceof DataType) {
				ownedAttributeFeature = UMLPackage.eINSTANCE.getDataType_OwnedAttribute();
			} else if (type instanceof Artifact) {
				ownedAttributeFeature = UMLPackage.eINSTANCE.getArtifact_OwnedAttribute();
			} else if (type instanceof Signal) {
				ownedAttributeFeature = UMLPackage.eINSTANCE.getSignal_OwnedAttribute();
			} else {
				// Unknown type : we try to find the feature reflexively
				final String eClassName = type.eClass().getName();
				IStatus status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, String.format(UNKNOWN_TYPE, eClassName));
				Activator.getDefault().getLog().log(status);
				ownedAttributeFeature = type.eClass().getEStructuralFeature("ownedAttribute"); //$NON-NLS-1$
				if (ownedAttributeFeature == null) {
					status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, String.format(FEATURE_NOT_FOUND, eClassName));
					Activator.getDefault().getLog().log(status);
				}
			}
		}
		return ownedAttributeFeature;
	}

	/**
	 * Check if owner feature is editable.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.modelelement.MemberEndModelElement.isEditable(String)}
	 * 
	 * @param prop
	 *            the {@link Property} which contains the owner feature
	 * @return <code>true</code> if the owner feature is editable,
	 *         <code>false</code> otherwise.
	 */
	public boolean isMemberEndPropertyEditable(Element source, String propertyPath) {
		boolean isEditable = true;
		if (propertyPath.equals(OWNER)) {
			List<Property> memberEnds = ((Property) source).getAssociation().getMemberEnds();
			if (memberEnds.size() == 2) {
				// Association between two associations : this doesn't make sense ?
				if (isAssociation(memberEnds.get(0)) && isAssociation(memberEnds.get(1))) {
					isEditable = false;
				}
				Property otherMemberEnd = memberEnds.stream().filter(memberEnd -> memberEnd != source).collect(Collectors.toList()).get(0);
				if (otherMemberEnd.getType().eClass() == UMLPackage.eINSTANCE.getUseCase()) {
					isEditable = false;
				}
			} else {
				isEditable = (((Property) source).getAssociation().getMemberEnds().size() <= 2)
						&& !EMFHelper.isReadOnly(source);
			}
		} else {
			isEditable = !EMFHelper.isReadOnly(source);
		}
		return isEditable;
	}

	/**
	 * Check if a given {@link Property} type is {@link Association}.
	 * 
	 * @param property
	 *            the {@link Property} to check,
	 * @return <code>true</code> if the {@link Property} type is
	 *         {@link Association}, <code>false</code> otherwise.
	 */
	private boolean isAssociation(Property property) {
		if (property.getType() == null) {
			return false;
		}
		return property.getType().eClass() == UMLPackage.eINSTANCE.getAssociation();
	}
}
