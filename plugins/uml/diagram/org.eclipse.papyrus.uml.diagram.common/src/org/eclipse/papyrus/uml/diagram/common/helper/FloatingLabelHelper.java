/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IndirectMaskLabelEditPolicy;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.MultiplicityElementUtil;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrus.uml.tools.utils.SignalUtil;
import org.eclipse.papyrus.uml.tools.utils.TypedElementUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.TypedElement;

/**
 * Helper for labels displaying {@link NamedElement}.
 */
public class FloatingLabelHelper extends StereotypedElementLabelHelper {


	/** The Constant SLASH. */
	private static final String SLASH = "/";//$NON-NLS-1$

	/** The Constant DOUBLE_DOT. */
	private static final String DOUBLE_DOT = ": ";//$NON-NLS-1$

	/** The Constant ONE_SPACE. */
	private static final String ONE_SPACE = " ";//$NON-NLS-1$

	/** The Constant EMPTY. */
	private static final String EMPTY = ""; //$NON-NLS-1$

	/** The Constant STEREOTYPE. */
	private static final String STEREOTYPE = "stereotype"; //$NON-NLS-1$

	/** The label helper. */
	private static FloatingLabelHelper labelHelper;

	/**
	 * Gets the single instance of FloatingLabelHelper.
	 *
	 * @return single instance of FloatingLabelHelper
	 */
	public static FloatingLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new FloatingLabelHelper();
		}
		return labelHelper;
	}

	/** Map for masks. */
	protected final Map<String, String> masks = new HashMap<>();

	/**
	 * Instantiates a new floating label helper.
	 */
	protected FloatingLabelHelper() {
		// initialize the map

		/**
		 * Style constant for visibility display in labels.
		 */
		masks.put(ICustomAppearance.DISP_VISIBILITY, "Visibility");

		/**
		 * Style constant for isDerive display in labels.
		 */
		masks.put(ICustomAppearance.DISP_DERIVE, "Derived");

		/**
		 * Style constant for name display in labels.
		 */
		masks.put(ICustomAppearance.DISP_NAME, "Name");

		/**
		 * Style constant for type display in labels.
		 */
		masks.put(ICustomAppearance.DISP_TYPE, "Type");

		/**
		 * Style constant for multiplicity display in labels.
		 */
		masks.put(ICustomAppearance.DISP_MULTIPLICITY, "Multiplicity");

		/**
		 * Style constant for default value display in labels.
		 */
		masks.put(ICustomAppearance.DISP_DEFAULT_VALUE, "Default Value");

		/** Style constant for direction display in labels */
		masks.put(ICustomAppearance.DISP_DIRECTION, "Direction");

		/** Style constant for return type display in labels */
		masks.put(ICustomAppearance.DISP_RT_TYPE, "returnType");

		/** Style constant for return multiplicity display in labels */
		masks.put(ICustomAppearance.DISP_RT_MULTIPLICITY, "Return Multiplicity");

	}

	/**
	 * Computes the label that should be displayed by the figure managed by this
	 * edit part.
	 *
	 * @param editPart
	 *            the edit part that controls the {@link NamedElement} to be
	 *            displayed
	 * @return the label corresponding to the specific display of the property
	 *         ("default" display given by preferences or specific display given
	 *         by eAnnotation).
	 */
	@Override
	protected String elementLabel(GraphicalEditPart editPart) {
		IMaskManagedLabelEditPolicy policy = (IMaskManagedLabelEditPolicy) editPart.getEditPolicy(IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY);
		if (policy == null) {
			policy = (IMaskManagedLabelEditPolicy) editPart.getEditPolicy(IndirectMaskLabelEditPolicy.INDRIRECT_MASK_MANAGED_LABEL);
		}

		Collection<String> displayValue = Collections.emptySet();

		if (policy != null) {
			displayValue = policy.getCurrentDisplayValue();
		}

		return parseString(editPart, displayValue);
	}

	/**
	 * Parses the string.
	 *
	 * @param editPart
	 *            the edit part
	 * @param displayValue
	 *            the display value
	 * @return the string
	 */
	protected String parseString(GraphicalEditPart editPart, Collection<String> displayValue) {
		NamedElement namedElement = getUMLElement(editPart);
		StringBuilder buffer = new StringBuilder();

		if (displayValue.contains(STEREOTYPE)) {
			// computes the label for the stereotype (horizontal presentation)
			buffer.append(stereotypesToDisplay((GraphicalEditPart) editPart.getParent()));
		}

		if (namedElement != null) {
			buffer.append(getCustomLabel(namedElement, displayValue));
		}

		return buffer.toString();
	}


	/**
	 * Returns the map of masks used to display a {@link NamedElement}.
	 *
	 * @return the {@link Map} of masks used to display a {@link NamedElement}
	 */
	public Map<String, String> getMasks() {
		return masks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NamedElement getUMLElement(GraphicalEditPart editPart) {
		if (editPart.getModel() instanceof View) {
			View view = (View) editPart.getModel();
			if (view.getElement() instanceof NamedElement) {
				return (NamedElement) view.getElement();
			}
		}
		return null;
	}

	/**
	 * Computes the label to be displayed for the floating label
	 */
	@Override
	protected String labelToDisplay(GraphicalEditPart editPart) {
		// computes the string label to be displayed
		return elementLabel(editPart);
	}

	/**
	 * return the custom label of the property, given UML2 specification and a custom style.
	 *
	 * @param namedElement
	 *            the integer representing the style of the label
	 * @param maskValues
	 *            the mask values
	 * @return the string corresponding to the label of the property
	 */
	public String getCustomLabel(NamedElement namedElement, Collection<String> maskValues) {

		// Use of specific existing custom label

		// use Signal custom label
		if (namedElement instanceof Signal) {
			return SignalUtil.getCustomLabel((Signal) namedElement, maskValues);
		}

		// default custom label
		StringBuilder buffer = new StringBuilder();

		// visibility
		if (maskValues.contains(ICustomAppearance.DISP_VISIBILITY)) {
			buffer.append(ONE_SPACE);
			buffer.append(NamedElementUtil.getVisibilityAsSign(namedElement));
		}

		// derived property for association
		if (namedElement instanceof Association) {
			if (maskValues.contains(ICustomAppearance.DISP_DERIVE)) {
				if (((Association) namedElement).isDerived()) {
					buffer.append(SLASH);
				}
			}
		}
		// name
		if (maskValues.contains(ICustomAppearance.DISP_NAME)) {
			buffer.append(ONE_SPACE);
			final String name = UMLLabelInternationalization.getInstance().getLabel(namedElement);
			buffer.append(name != null ? name : EMPTY);
		}

		// Type of TypedElement
		if (namedElement instanceof TypedElement) {
			if (maskValues.contains(ICustomAppearance.DISP_RT_TYPE) || maskValues.contains(ICustomAppearance.DISP_TYPE)) {
				buffer.append(DOUBLE_DOT);
				buffer.append(TypedElementUtil.getTypeAsString((TypedElement) namedElement));
			}
		}

		// Multiplicity
		if (namedElement instanceof MultiplicityElement) {
			if (maskValues.contains(ICustomAppearance.DISP_MULTIPLICITY)) {
				// multiplicity -> do not display [1]
				String multiplicity = MultiplicityElementUtil.getMultiplicityAsString((MultiplicityElement) namedElement);
				buffer.append(multiplicity);
			}
		}

		return buffer.toString();
	}
}
