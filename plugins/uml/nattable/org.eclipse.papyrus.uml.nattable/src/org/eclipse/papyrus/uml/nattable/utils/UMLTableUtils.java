/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 435417
 *  Sebastien REVOL (CEA LIST) sebastien.revol@cea.fr - Bug 522631
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.CrossAxisWrapper;
import org.eclipse.papyrus.uml.nattable.paste.StereotypeApplicationStructure;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 *
 * This class provides useful methods to manage stereotypes in the table
 *
 */
public class UMLTableUtils {

	public static final String PROPERTY_OF_STEREOTYPE_PREFIX = "property_of_stereotype:/"; //$NON-NLS-1$

	private UMLTableUtils() {
		// to prevent instanciation
	}

	/**
	 * 
	 * @param aString
	 *            a string
	 * @return
	 *         <code>true</code> if the string start with {@link #PROPERTY_OF_STEREOTYPE_PREFIX}
	 */
	public static final boolean isStringRepresentingStereotypeProperty(String aString) {
		return aString.startsWith(PROPERTY_OF_STEREOTYPE_PREFIX);
	}

	/**
	 * 
	 * @param anAxis
	 *            an object axis
	 * @return
	 *         <code>true</code> if the axis represents a property of stereotype (so a string starting with {@link #PROPERTY_OF_STEREOTYPE_PREFIX}
	 */
	public static final boolean isStringRepresentingStereotypeProperty(Object anAxis) {
		anAxis = AxisUtils.getRepresentedElement(anAxis);
		if (anAxis instanceof String) {
			return isStringRepresentingStereotypeProperty((String) anAxis);
		}
		return false;
	}

	/**
	 *
	 * @param eobject
	 *            an element of the model (currently, if it is not an UML::Element, we can't find the property)
	 * @param id
	 *            the id used to identify the property of the stereotype
	 *
	 * @return
	 *         the UML::Property or <code>null</code> if we can't resolve it (the required profile is not applied)
	 */
	public static Property getRealStereotypeProperty(final EObject eobject, final String id) {
		return getRealStereotypeProperty(eobject, id, null);
	}

	/**
	 * 
	 * @param packages
	 *            a list of package
	 * @param propertyQN
	 *            the qualified name of the wanted property
	 * @return
	 *         the property or <code>null</code> if not found
	 */
	protected static Property getProperty(Collection<Package> packages, String propertyQN) {
		final String propertyName = NamedElementUtil.getNameFromQualifiedName(propertyQN);
		final String stereotypeQN = NamedElementUtil.getParentQualifiedName(propertyQN);
		final String stereotypeName = NamedElementUtil.getNameFromQualifiedName(stereotypeQN);
		final String profileQN = NamedElementUtil.getParentQualifiedName(stereotypeQN);
		for (Package package1 : packages) {
			for (Profile prof : package1.getAppliedProfiles()) {
				if (prof.getQualifiedName().equals(profileQN)) {
					NamedElement ste = prof.getMember(stereotypeName);
					if (ste instanceof Stereotype) {
						NamedElement prop = ((Stereotype) ste).getMember(propertyName);
						if (prop instanceof Property && prop.getQualifiedName().equals(propertyQN)) {
							return (Property) prop;
						}
					}
				}
				Property p = getProperty(package1.getNestedPackages(), propertyQN);
				if (p != null) {
					return p;
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param eobject
	 *            an element of the model (currently, if it is not an UML::Element, we can't find the property)
	 * @param id
	 *            the id used to identify the property of the stereotype
	 * @return
	 *         a list of enumerator which contains the available literal
	 */
	public static final List<Enumerator> getLiteralsToTypeProperty(final EObject modelElement, final String id) {
		Assert.isTrue(id.startsWith(PROPERTY_OF_STEREOTYPE_PREFIX));
		if (modelElement instanceof Element) {
			final Property property = UMLTableUtils.getRealStereotypeProperty(modelElement, id);
			final Stereotype current = (Stereotype) property.getOwner();

			EClass stereotypeDef = (EClass) current.getProfile().getDefinition(current);
			EStructuralFeature feature = stereotypeDef.getEStructuralFeature(property.getName());
			EEnum eenum = null;
			if (feature != null && feature.getEType() instanceof EEnum) {
				eenum = (EEnum) feature.getEType();
			}

			if (eenum != null) {
				final List<Enumerator> literals = new ArrayList<Enumerator>();
				for (EEnumLiteral literal : eenum.getELiterals()) {
					Enumerator value = literal.getInstance();
					literals.add(value);
				}
				return literals;
			}
		}
		return Collections.emptyList();

	}

	/**
	 *
	 * @param eobject
	 *            an element of the model (currently, if it is not an UML::Element, we can't find the property)
	 * @param id
	 *            the id used to identify the property of the stereotype
	 * @param sharedMap
	 *            a map owning interesting information, like {@link StereotypeApplicationStructure} which can be used to find stereotype, stereotype
	 *            application and so on
	 * @return
	 *         the UML::Property or <code>null</code> if we can't resolve it (the required profile is not applied)
	 */
	public static Property getRealStereotypeProperty(final EObject eobject, final String id, final Map<?, ?> sharedMap) {
		Property result = null;

		Assert.isTrue(id.startsWith(PROPERTY_OF_STEREOTYPE_PREFIX));
		if (eobject instanceof Element) {
			final Element element = (Element) eobject;
			final String propertyQN = id.replace(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX, ""); //$NON-NLS-1$
			
			Package nearestPackage = null;
			if (null != sharedMap) {
				final Element context = (Element) sharedMap.get(Constants.PASTED_ELEMENT_CONTAINER_KEY);
				nearestPackage = context.getNearestPackage();
			} else {
				nearestPackage = element.getNearestPackage();
			}
			
			if (null != nearestPackage) {

				result = UMLUtil.<Property>findNamedElements(nearestPackage.eResource().getResourceSet(),propertyQN , false, UMLPackage.eINSTANCE.getProperty())
						.stream()
						.findFirst()
						.orElse(null) ;
					
				
				//TODO should we still keep that? FindNamedElement browse all the resourceSet.
				// 2. if not, the profile could be applied on a sub-package of the nearest package
				/* the table can show element which are not children of its context, so the profile could not be available in its context */
				if(null == result){
					result = getProperty(element.getNearestPackage().getNestedPackages(), propertyQN);
				}
			}

		}
		return result;
	}

	/**
	 *
	 * @param element
	 *            the UML::Element on which we are looking for applied Stereotype with the property identified by its id
	 * @param id
	 *            the id used to identify the property of the stereotype
	 *
	 * @return
	 *         the list of UML::Stereotype which have the property identified by this id and which are applied on the Element or <code>null</code> if
	 *         we can't resolve it (the required profile is not applied)
	 */
	public static final List<Stereotype> getAppliedStereotypesWithThisProperty(final Element element, final String id) {
		Assert.isTrue(id.startsWith(PROPERTY_OF_STEREOTYPE_PREFIX));
		final List<Stereotype> stereotypes = new ArrayList<Stereotype>();
		if (element.eResource() != null) {
			final Object prop = getRealStereotypeProperty(element, id);
			if (prop instanceof Property) {
				for (final Stereotype current : element.getAppliedStereotypes()) {
					if (current.getAllAttributes().contains(prop)) {
						stereotypes.add(current);
					}
				}
			}
		}
		return stereotypes;
	}

	/**
	 *
	 * @param element
	 *            the UML::Element on which we are looking for applied Stereotype with the property identified by its id
	 * @param id
	 *            the id used to identify the property of the stereotype
	 *
	 * @return
	 *         the list of UML::Stereotype which have the property identified by this id and which are applied on the Element or <code>null</code> if
	 *         we can't resolve it (the required profile is not applied)
	 */
	public static final List<Stereotype> getApplicableStereotypesWithThisProperty(final Element element, final String id) {
		Assert.isTrue(id.startsWith(PROPERTY_OF_STEREOTYPE_PREFIX));
		final List<Stereotype> stereotypes = new ArrayList<Stereotype>();
		if (element.eResource() != null) {
			final Object prop = getRealStereotypeProperty(element, id);
			if (prop instanceof Property) {
				for (final Stereotype current : element.getApplicableStereotypes()) {
					if (current.getAllAttributes().contains(prop)) {
						stereotypes.add(current);
					}
				}
			}
		}
		return stereotypes;
	}

	/**
	 *
	 * @param element
	 *            the UML::Element on which we are looking for applied Stereotype with the property identified by its id
	 * @param id
	 *            the id used to identify the property of the stereotype
	 * @param sharedMap
	 *            a map owning interesting information, like {@link StereotypeApplicationStructure} which can be used to find stereotype, stereotype
	 *            application and so on
	 * @return
	 *         the list of UML::Stereotype which have the property identified by this id and which are applied on the Element or <code>null</code> if
	 *         we can't resolve it (the required profile is not applied)
	 */
	public static final List<Stereotype> getAppliedStereotypesWithThisProperty(final Element element, final String id, Map<?, ?> sharedMap) {
		Assert.isTrue(id.startsWith(PROPERTY_OF_STEREOTYPE_PREFIX));
		final List<Stereotype> stereotypes = new ArrayList<Stereotype>();
		if (sharedMap != null) {
			final List<StereotypeApplicationStructure> struct = findStereotypeApplicationDataStructure(element, id, sharedMap);
			if (struct != null) {
				for (final StereotypeApplicationStructure current : struct) {
					stereotypes.add(current.getStereotype());
				}
			}
		}
		if (element.eResource() != null) {
			final Object prop = getRealStereotypeProperty(element, id, sharedMap);
			if (prop instanceof Property) {
				for (final Stereotype current : element.getAppliedStereotypes()) {
					if (current.getAllAttributes().contains(prop)) {
						stereotypes.add(current);
					}
				}
			}
		}
		return stereotypes;
	}

	/**
	 *
	 * @param editedElement
	 *            the edited element
	 * @param id
	 *            the id of the edited stereotype feature
	 * @param sharedMap
	 *            a map owning interesting information, like {@link StereotypeApplicationStructure} which can be used to find stereotype, stereotype
	 *            application and so on
	 * @return
	 *         the list of the found data structure or <code>null</code> if not found
	 */
	public static final List<StereotypeApplicationStructure> findStereotypeApplicationDataStructure(final Element editedElement, final String id, final Map<?, ?> sharedMap) {
		// TODO : enhance the data structure to look for an editedElement + a propQn as Key!
		@SuppressWarnings("unchecked")
		List<StereotypeApplicationStructure> list = (List<StereotypeApplicationStructure>) sharedMap.get(editedElement);
		if (list != null && !list.isEmpty()) {
			final String propertyQN = id.replace(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX, ""); //$NON-NLS-1$
			for (final StereotypeApplicationStructure current : list) {
				if (current.getProperty().getQualifiedName().equals(propertyQN)) {
					return Collections.singletonList(current);// TODO : doesn't manage when several stereotype with the same property are applied
				}
			}
		}
		return null;
	}
	
		// currently consisder this method as internal
	public static CrossAxisWrapper<EObject, EStructuralFeature> getRealEditedObject(final ILayerCell layerCell, ITableAxisElementProvider manager) {
		int columnIndex = layerCell.getColumnIndex();
		int rowIndex = layerCell.getRowIndex();
		Object row = manager.getRowElement(rowIndex);
		Object column = manager.getColumnElement(columnIndex);
		row = AxisUtils.getRepresentedElement(row);
		column = AxisUtils.getRepresentedElement(column);
		Element editedElement = null;
		Object feature = null;
		if (row instanceof Element && !(column instanceof Element)) {
			editedElement = (Element) row;
			feature = column;
		} else if (column instanceof Element && !(row instanceof Element)) {
			editedElement = (Element) column;
			feature = row;
		} else {
			return null;
		}

		EStructuralFeature realFeature = null;
		EObject realEditedObject = null;
//		Stereotype stereotype = null;
		List<Stereotype> stereotypesWithEditedFeatureAppliedOnElement = null;
		if (feature instanceof EStructuralFeature) {
			realFeature = (EStructuralFeature) feature;
			realEditedObject = editedElement;
		} else if (feature instanceof String && ((String) feature).startsWith(PROPERTY_OF_STEREOTYPE_PREFIX)) {
			final String id = AxisUtils.getPropertyId(feature);
			stereotypesWithEditedFeatureAppliedOnElement = UMLTableUtils.getAppliedStereotypesWithThisProperty(editedElement, id);
//			stereotype = stereotypesWithEditedFeatureAppliedOnElement.get(0);
			realEditedObject = editedElement.getStereotypeApplication(stereotypesWithEditedFeatureAppliedOnElement.get(0));
			Property prop = UMLTableUtils.getRealStereotypeProperty(editedElement, id);
			realFeature = realEditedObject.eClass().getEStructuralFeature(prop.getName());
		}
		if (stereotypesWithEditedFeatureAppliedOnElement != null && stereotypesWithEditedFeatureAppliedOnElement.size() > 1) {
			// TODO : not yet managed
			return null;
		}

		if (realEditedObject != null && realFeature != null) {
			return new CrossAxisWrapper<EObject, EStructuralFeature>(realEditedObject, realFeature);
		}
		return null;
	}
}
