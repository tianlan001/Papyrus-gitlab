/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 402525
 *  Vincent Lorenzo (CEA-LIST) - bug 458492
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.dataprovider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 *
 * @author Vincent Lorenzo
 *         This class provides the possibles enumeration literal for properties of stereotype typed with a UMLEnumerationLiteral
 */
public class UMLStereotypeSingleEnumerationComboBoxDataProvider implements IComboBoxDataProvider {

	/**
	 * The table axis element provider
	 */
	private ITableAxisElementProvider elementProvider;

	/**
	 * the obejct represented by the axis
	 */
	private Object axisElement;

	/**
	 *
	 * Constructor.
	 *
	 * @param axisElement
	 *            the object represented by the axis
	 * @param elementProvider
	 *            The table axis element provider
	 */
	public UMLStereotypeSingleEnumerationComboBoxDataProvider(final Object axisElement, final ITableAxisElementProvider elementProvider) {
		this.axisElement = axisElement;
		this.elementProvider = elementProvider;
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider#getValues(int, int)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 *
	 * @return
	 * 		the list of the available enumeration literal
	 */
	@Override
	public List<?> getValues(int columnIndex, int rowIndex) {
		final List<Object> literals = new ArrayList<Object>();
		Object el = this.elementProvider.getColumnElement(columnIndex);
		Object rowElement = this.elementProvider.getRowElement(rowIndex);
		el = AxisUtils.getRepresentedElement(el);
		rowElement = AxisUtils.getRepresentedElement(rowElement);
		Element modelElement = null;
		if (rowElement instanceof Element && el.equals(AxisUtils.getRepresentedElement(this.axisElement))) {
			modelElement = (Element) rowElement;
		} else if (rowElement.equals(AxisUtils.getRepresentedElement(this.axisElement)) && el instanceof Element) {
			modelElement = (Element) el;
		}
		if (modelElement != null) {
			final String id = AxisUtils.getPropertyId(this.axisElement);
			final Property property = UMLTableUtils.getRealStereotypeProperty(modelElement, id);
			final List<Stereotype> ste = UMLTableUtils.getApplicableStereotypesWithThisProperty(modelElement, id);
			if (!ste.isEmpty()) {
				Stereotype correctStereotype = null;
				if (ste.size() == 1) {
					correctStereotype = ste.get(0);
				} else {
					final Iterator<Stereotype> applicableStereotypes = ste.iterator();
					while (applicableStereotypes.hasNext() && null == correctStereotype) {
						final Stereotype applicableStereotype = applicableStereotypes.next();
						if (id.contains(applicableStereotype.getName())) {
							correctStereotype = applicableStereotype;
						}
					}
				}
				if (null != correctStereotype) {
					// the stereotype is maybe not applied on the element, but we allow to edit its values
					EObject propertyDef = correctStereotype.getProfile().getDefinition(property);
					EEnum eenum = null;
					if (propertyDef != null) {
						if (propertyDef instanceof EClass) {
							// dynamic profile
							EStructuralFeature feature = ((EClass) propertyDef).getEStructuralFeature(property.getName());
							if (feature != null && feature.getEType() instanceof EEnum) {
								eenum = (EEnum) feature.getEType();
							}

							// in case of static profile (SysML) we get an eattribute instead of an EClass
						} else if (propertyDef instanceof EAttribute) {
							EClassifier tmp = ((EAttribute) propertyDef).getEType();
							if (tmp instanceof EEnum) {
								eenum = (EEnum) tmp;
							}
						}
					}
					if (eenum != null) {
						for (EEnumLiteral literal : eenum.getELiterals()) {
							Enumerator value = literal.getInstance();
							literals.add(value);
						}
					}
				}
			}
		}
		return literals;
	}

}
