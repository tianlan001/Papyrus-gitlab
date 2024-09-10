/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Remi SCHNEKENBURGER (CEA LIST) Remi.schnekenburger@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils;

import java.util.Collection;

import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Duration;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.Interval;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.StringExpression;
import org.eclipse.uml2.uml.TimeExpression;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Utility class for {@link ValueSpecification}
 */
public class ValueSpecificationUtil {

	/** The * KeyWord to represent an unlimited integer (infinite) */
	private static final String UNLIMITED_KEYWORD = "*";

	private static final String INTERVAL_FORMAT = "%1s..%2s";

	/**
	 * Get a string representing of a ValueSpecification
	 *
	 * @param specification
	 */
	public static String getSpecificationValue(ValueSpecification specification) {
		return getSpecificationValue(specification, false);
	}
	
	/**
	 * Get a string representing of a ValueSpecification
	 *
	 * @param specification The Value specification.
	 * @param useInternationalization Boolean to determinate if the internationalization must be used for the string representation.
	 * @since 3.0
	 */
	public static String getSpecificationValue(final ValueSpecification specification, final boolean useInternationalization) {
		String value = ""; //$NON-NLS-1$
		if (specification != null && specification.eClass() != null) {
			switch (specification.eClass().getClassifierID()) {
			case UMLPackage.LITERAL_STRING:
				value = ((LiteralString) specification).getValue();
				break;
			case UMLPackage.LITERAL_BOOLEAN:
				value = Boolean.toString(((LiteralBoolean) specification).booleanValue());
				break;
			case UMLPackage.LITERAL_INTEGER:
				value = Integer.toString(((LiteralInteger) specification).getValue());
				break;
			case UMLPackage.LITERAL_UNLIMITED_NATURAL:
				value = Integer.toString(((LiteralUnlimitedNatural) specification).getValue());
				if ("-1".equals(value)) { //$NON-NLS-1$
					value = UNLIMITED_KEYWORD;
				}
				break;
			case UMLPackage.LITERAL_NULL:
				break;
			case UMLPackage.OPAQUE_EXPRESSION:
				OpaqueExpression exp = (OpaqueExpression) specification;
				value = OpaqueExpressionUtil.getBodyForLanguage(exp, null);
				break;
			case UMLPackage.INSTANCE_VALUE:
				if (((InstanceValue) specification).getInstance() != null) {
					if(useInternationalization){
						value = UMLLabelInternationalization.getInstance().getLabel(((InstanceValue) specification).getInstance());
					}else{
						value = ((InstanceValue) specification).getInstance().getName();
					}
				}
				break;
			case UMLPackage.EXPRESSION:
				Expression expr = (Expression) specification;
				if (!expr.getOperands().isEmpty()) {
					StringBuffer operandsBuff = new StringBuffer(expr.getSymbol() == null ? "" : expr.getSymbol());
					operandsBuff.append("(");
					int initialLength = operandsBuff.length();
					for (ValueSpecification operand : expr.getOperands()) {
						if (operandsBuff.length() > initialLength) {
							operandsBuff.append(",");
						}
						operandsBuff.append(getSpecificationValue(operand));
					}
					operandsBuff.append(")");
					value = operandsBuff.toString();
				} else {
					value = expr.getSymbol();
				}
				break;
			case UMLPackage.STRING_EXPRESSION:
				// TODO
				break;
			case UMLPackage.DURATION:
				Duration durationExpr = (Duration) specification;
				if (durationExpr.getExpr() != null) {
					value = getSpecificationValue(durationExpr.getExpr());
				} else if (durationExpr.getObservations().size() > 0) {
					if(useInternationalization){
						value = UMLLabelInternationalization.getInstance().getLabel(durationExpr.getObservations().get(0));
					}else{
						value = durationExpr.getObservations().get(0).getName();
					}
				}
				break;
			case UMLPackage.TIME_EXPRESSION:
				TimeExpression timeExpr = (TimeExpression) specification;
				if (timeExpr.getExpr() != null) {
					value = getSpecificationValue(timeExpr.getExpr());
				} else if (timeExpr.getObservations().size() > 0) {
					if(useInternationalization){
						value = UMLLabelInternationalization.getInstance().getLabel(timeExpr.getObservations().get(0));
					}else{
						value = timeExpr.getObservations().get(0).getName();
					}
				}
				break;
			case UMLPackage.INTERVAL:
			case UMLPackage.TIME_INTERVAL:
			case UMLPackage.DURATION_INTERVAL:
				if (!(specification instanceof Interval)) { // safety test for bug 430525, but should not be necessary to perform a test here...
					break;
				}
				Interval interval = (Interval) specification;
				String min = getSpecificationValue(interval.getMin());
				String max = getSpecificationValue(interval.getMax());
				value = String.format(INTERVAL_FORMAT, min, max);
				break;
			case UMLPackage.LITERAL_REAL:
				LiteralReal real = (LiteralReal) specification;
				return Double.toString(real.getValue()); // Precision?
			default: {
				break;
			}
			}
		}
		return value;
	}


	/**
	 * Get a string representing a Constraint
	 *
	 * @param Constraint
	 */
	public static String getConstraintnValue(Constraint specification) {
		String value = ""; //$NON-NLS-1$
		if (specification != null) {
			ValueSpecification spe = specification.getSpecification();
			if (spe != null) {
				value = getSpecificationValue(spe, true);
			}
		}
		return value;
	}

	/**
	 * Sets the value of a specification, using a string value
	 *
	 * @param specification
	 *            the value specification to update
	 * @param value
	 *            the value to set
	 */
	public static void restoreSpecificationValue(ValueSpecification specification, String value) {
		if (value == null) {
			return;
		}

		switch (specification.eClass().getClassifierID()) {
		case UMLPackage.LITERAL_STRING:
			restoreLiteralString((LiteralString) specification, value);
			break;
		case UMLPackage.LITERAL_BOOLEAN:
			restoreLiteralBoolean((LiteralBoolean) specification, value);
			break;
		case UMLPackage.LITERAL_INTEGER:
			restoreLiteralInteger((LiteralInteger) specification, value);
			break;
		case UMLPackage.LITERAL_UNLIMITED_NATURAL:
			restoreLiteralUnlimitedNatural((LiteralUnlimitedNatural) specification, value);
			break;
		case UMLPackage.LITERAL_NULL:
			restoreLiteralNull((LiteralNull) specification, value);
			break;
		case UMLPackage.OPAQUE_EXPRESSION:
			OpaqueExpression exp = (OpaqueExpression) specification;
			if (!exp.getLanguages().isEmpty()) {
				restoreOpaqueExpression((org.eclipse.uml2.uml.OpaqueExpression) specification, exp.getLanguages().get(0), value);
			} else {
				restoreOpaqueExpression((org.eclipse.uml2.uml.OpaqueExpression) specification, value);
			}
			break;
		default: {
			break;
		}
		}
	}

	/**
	 * Sets the value of a literal string, using a string value
	 *
	 * @param specification
	 *            the literal string to update
	 * @param value
	 *            the new value
	 */
	public static void restoreLiteralString(LiteralString specification, String value) {
		specification.setValue(value);
	}

	/**
	 * Sets the value of a literal, using a string value
	 *
	 * @param specification
	 *            the literal integer to update
	 * @param value
	 *            the new value
	 */
	public static void restoreLiteralInteger(LiteralInteger specification, String value) {
		int intValue = 0;
		try {
			intValue = Integer.decode(value);
		} catch (NumberFormatException e) {
			// Do nothing, this was not a number. 0 will be the default value
		}
		specification.setValue(intValue);
	}

	/**
	 * Sets the value of a literal boolean, using a string value
	 *
	 * @param specification
	 *            the literal boolean to update
	 * @param value
	 *            the new value
	 */
	public static void restoreLiteralBoolean(LiteralBoolean specification, String value) {
		if ("true".equals(value) || "1".equals(value)) {
			specification.setValue(true);
		} else {
			specification.setValue(false);
		}
	}

	/**
	 * Sets the value of a literal unlimited natural, using a string value
	 *
	 * @param specification
	 *            the literal unlimited natural to update
	 * @param value
	 *            the new value
	 */
	public static void restoreLiteralUnlimitedNatural(LiteralUnlimitedNatural specification, String value) {
		int intValue = 0;
		try {
			intValue = Integer.decode(value);
		} catch (NumberFormatException e) {
			// Do nothing, this was not a number. 0 will be the default value
		}
		// Handle the special unlimited value separately
		if (UNLIMITED_KEYWORD.equals(value)) {
			intValue = -1;
		}
		specification.setValue(intValue);
	}

	/**
	 * Sets the value of a literal null, using a string value
	 *
	 * @param specification
	 *            the literal null to update
	 * @param value
	 *            the new value
	 */
	public static void restoreLiteralNull(LiteralNull specification, String value) {
		// nothing to set
	}

	/**
	 * Sets the value of an opaque expression, using a string value
	 *
	 * @param specification
	 *            the opaque expression to update
	 * @param value
	 *            the new value
	 */
	public static void restoreOpaqueExpression(org.eclipse.uml2.uml.OpaqueExpression specification, String value) {
		// save in "UML" language, but should be desactivable
		OpaqueExpressionUtil.setBodyForLanguage(specification, "UML", value);
	}

	/**
	 * Sets the value of an opaque expression, using a string value.
	 *
	 * @param specification
	 *            the opaque expression to update
	 * @param language
	 *            the specified language
	 * @param value
	 *            the new value
	 */
	public static void restoreOpaqueExpression(org.eclipse.uml2.uml.OpaqueExpression specification, String language, String value) {
		OpaqueExpressionUtil.setBodyForLanguage(specification, language, value);
	}

	/**
	 * Add to the collection the value specification and all its related value specifications, like min and max of an interval for example.
	 *
	 * @param spec
	 *            the value specification to add
	 * @param collection
	 *            the collection
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addEnclosedValueSpecificationToCollection(ValueSpecification spec, Collection collection) {
		if (!collection.contains(spec)) {
			collection.add(spec);
			if (spec instanceof Interval) {
				addEnclosedValueSpecificationToCollection(((Interval) spec).getMin(), collection);
				addEnclosedValueSpecificationToCollection(((Interval) spec).getMax(), collection);
			} else if (spec instanceof Duration) {
				addEnclosedValueSpecificationToCollection(((Duration) spec).getExpr(), collection);
			} else if (spec instanceof TimeExpression) {
				addEnclosedValueSpecificationToCollection(((TimeExpression) spec).getExpr(), collection);
			} else if (spec instanceof Expression) {
				for (ValueSpecification vs : ((Expression) spec).getOperands()) {
					addEnclosedValueSpecificationToCollection(vs, collection);
				}
				if (spec instanceof StringExpression) {
					for (StringExpression se : ((StringExpression) spec).getSubExpressions()) {
						addEnclosedValueSpecificationToCollection(se, collection);
					}
				}
			}
		}
	}

}
