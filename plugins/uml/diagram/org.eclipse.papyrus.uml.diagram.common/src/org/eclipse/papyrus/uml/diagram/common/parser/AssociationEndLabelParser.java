/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.MultiplicityElementUtil;
import org.eclipse.papyrus.uml.tools.utils.ValueSpecificationUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;

/**
 * Semantic Parser for {@link Property} used as {@link Association} end.
 */
public class AssociationEndLabelParser extends PropertyLabelParser {

	/** The String format for displaying a {@link Property} label with modifiers */
	protected static final String ALT_MODIFIER_FORMAT = "{%s}%s";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		Collection<String> maskValues = getMaskValues(element);

		if (maskValues.isEmpty()) {
			return MaskedLabel;
		}

		String result = "";
		EObject eObject = EMFHelper.getEObject(element);

		if ((eObject != null) && (eObject instanceof Property)) {

			Property property = (Property) eObject;

			// manage visibility
			if (maskValues.contains(ICustomAppearance.DISP_VISIBILITY)) {
				String visibility;
				switch (property.getVisibility().getValue()) {
				case VisibilityKind.PACKAGE:
					visibility = "~";
					break;
				case VisibilityKind.PUBLIC:
					visibility = "+";
					break;
				case VisibilityKind.PROTECTED:
					visibility = "#";
					break;
				case VisibilityKind.PRIVATE:
					visibility = "-";
					break;
				default:
					visibility = "+";
					break;
				}
				result = String.format(VISIBILITY_FORMAT, visibility, result);
			}

			// manage derived modifier
			if (maskValues.contains(ICustomAppearance.DISP_DERIVE) && property.isDerived()) {
				result = String.format(DERIVED_FORMAT, result);
			}

			// manage name
			if (maskValues.contains(ICustomAppearance.DISP_NAME) && property.isSetName()) {
				String name = UMLLabelInternationalization.getInstance().getLabel(property);

				// If property is owned by Association (non navigable) only show the name when explicitly asked.

				if (maskValues.contains(ILabelPreferenceConstants.DISP_NON_NAVIGABLE_ROLE) || !((property.getOwningAssociation() != null) && (property.getOwningAssociation().getOwnedEnds().contains(property)))) {

					result = String.format(NAME_FORMAT, result, name);
				}
			}

			// manage type
			if (maskValues.contains(ICustomAppearance.DISP_TYPE)) {
				String type = "<Undefined>";
				if (property.getType() != null) {
					type = UMLLabelInternationalization.getInstance().getLabel(property.getType());
				}

				// If type is undefined only show "<Undefined>" when explicitly asked.

				if (maskValues.contains(ILabelPreferenceConstants.DISP_UNDEFINED_TYPE) || (!"<Undefined>".equals(type))) {

					result = String.format(TYPE_FORMAT, result, type);
				}
			}

			// manage multiplicity
			if (maskValues.contains(ICustomAppearance.DISP_MULTIPLICITY)) {

				String multiplicity = justMultiplicityFormat(maskValues) ? MultiplicityElementUtil.formatMultiplicityNoBrackets(property) : MultiplicityElementUtil.formatMultiplicity(property);
				multiplicity = MultiplicityElementUtil.manageDefaultMultiplicity(multiplicity, !maskValues.contains(ILabelPreferenceConstants.DISP_DEFAULT_MULTIPLICITY));
				if (multiplicity != null && !multiplicity.isEmpty()) {
					result += " " + multiplicity;
				}
			}

			// manage default value
			if (maskValues.contains(ICustomAppearance.DISP_DEFAULT_VALUE) && property.getDefaultValue() != null) {
				ValueSpecification valueSpecification = property.getDefaultValue();
				result = String.format(DEFAULT_VALUE_FORMAT, result, ValueSpecificationUtil.getSpecificationValue(valueSpecification, true));
			}

			// manage modifier
			if (maskValues.contains(ICustomAppearance.DISP_MODIFIERS)) {
				StringBuffer sb = new StringBuffer();
				if (property.isReadOnly()) {
					sb.append(sb.length() == 0 ? "readOnly" : ", readOnly");
				}
				if (property.isOrdered()) {
					sb.append(sb.length() == 0 ? "ordered" : ", ordered");
				}
				if (property.isUnique()) {
					sb.append(sb.length() == 0 ? "unique" : ", unique");
				}
				if (property.isDerivedUnion()) {
					sb.append(sb.length() == 0 ? "union" : ", union");
				}
				EList<Property> redefinedProperties = property.getRedefinedProperties();
				if (redefinedProperties != null && !redefinedProperties.isEmpty()) {
					for (Property p : redefinedProperties) {
						sb.append(sb.length() == 0 ? UMLLabelInternationalization.getInstance().getLabel(p) : ", redefines " + UMLLabelInternationalization.getInstance().getLabel(p));
					}
				}
				if (sb.length() != 0) {
					result = String.format(ALT_MODIFIER_FORMAT, sb.toString(), result);
				}
			}
		}
		return result;
	}

	private boolean justMultiplicityFormat(Collection<String> maskValues) {
		boolean blockDefault = maskValues.size() == 1 && maskValues.contains(ICustomAppearance.DISP_MULTIPLICITY);
		boolean nonBlockDefault = maskValues.size() == 2 && maskValues.contains(ICustomAppearance.DISP_MULTIPLICITY) && maskValues.contains(ILabelPreferenceConstants.DISP_DEFAULT_MULTIPLICITY);
		return blockDefault || nonBlockDefault;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getMasks() {
		return super.getMasks();
	}

	@Override
	public Collection<String> getDefaultValue(IAdaptable element) {
		View view = element.getAdapter(View.class);
		if (view == null) {
			return super.getDefaultValue(element);
		}

		if (view.getType() != null && view.getType().contains("multiplicity")) {
			return Arrays.asList(ICustomAppearance.DISP_MULTIPLICITY, ILabelPreferenceConstants.DISP_DEFAULT_MULTIPLICITY);
		} else if (view.getType() != null && view.getType().contains("role")) {
			return Arrays.asList(ICustomAppearance.DISP_NAME, ICustomAppearance.DISP_VISIBILITY, ICustomAppearance.DISP_DERIVE);
		}

		return super.getDefaultValue(element);
	}
}
