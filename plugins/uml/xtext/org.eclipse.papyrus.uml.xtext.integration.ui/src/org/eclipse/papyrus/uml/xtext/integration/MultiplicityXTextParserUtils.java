/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.xtext.integration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * This class allow to define the multiplicity XText parser util methods which create the commands.
 */
public class MultiplicityXTextParserUtils {

	/**
	 * The character representing the unlimited number
	 */
	private static final String UNLIMITED_KEYWORD = "*"; //$NON-NLS-1$

	/**
	 * The quote character.
	 */
	private static final String QUOTE = "\""; //$NON-NLS-1$

	/**
	 * This allow to create the command for one bound of multiplicity and add it in the composite command.
	 * 
	 * @param provider
	 *            The provider.
	 * @param eObject
	 *            The object to update.
	 * @param bound
	 *            The bound filled.
	 * @return The command to update the multiplicity.
	 */
	public static ICommand updateOneMultiplicityCommand(final IElementEditService provider, final EObject eObject, final String bound) {
		final CompositeCommand compositeCommand = new CompositeCommand("Multiplicity update"); //$NON-NLS-1$
		CompositeCommand updateLower, updateUpper;
		if (UNLIMITED_KEYWORD.equals(bound)) {
			// The bound filled is the '*' character
			updateLower = updateLowerValueSpecificationMultiplicityCommand(provider, eObject, "0"); //$NON-NLS-1$
			updateUpper = updateUpperValueSpecificationMultiplicityCommand(provider, eObject, "-1"); //$NON-NLS-1$
		} else {
			updateLower = updateLowerValueSpecificationMultiplicityCommand(provider, eObject, bound);
			updateUpper = updateUpperValueSpecificationMultiplicityCommand(provider, eObject, bound);
		}
		if (!updateLower.isEmpty()) {
			compositeCommand.add(updateLower);
		}
		if (!updateUpper.isEmpty()) {
			compositeCommand.add(updateUpper);
		}
		
		if (compositeCommand.isEmpty()) {
			return null;
		}
		else {
			return compositeCommand.reduce();
		}
	}

	/**
	 * This allow to create the command for two bounds of multiplicity and add it in the composite command.
	 * 
	 * @param provider
	 *            The provider.Vc
	 * @param eObject
	 *            The object to update.
	 * @param lowerBound
	 *            The lower bound filled.
	 * @param upperBound
	 *            The upper bound filled.
	 * @return The command to update the multiplicity.
	 */
	public static ICommand updateTwoMultiplicityCommand(final IElementEditService provider, final EObject eObject, final String lowerBound, final String upperBound) {
		final CompositeCommand compositeCommand = new CompositeCommand("Multiplicity update"); //$NON-NLS-1$
		
		CompositeCommand updateLower = updateLowerValueSpecificationMultiplicityCommand(provider, eObject, lowerBound);
		if (!updateLower.isEmpty()) {
			compositeCommand.add(updateLower);
		}

		// The upper bound filled is the '*' character
		String upperBoundVal = UNLIMITED_KEYWORD.equals(upperBound) ?
				"-1" : //$NON-NLS-1$
				upperBound;

		CompositeCommand updateUpper = updateUpperValueSpecificationMultiplicityCommand(provider, eObject, upperBoundVal);
		if (!updateUpper.isEmpty()) {
			compositeCommand.add(updateUpper);
		}
		
		if (compositeCommand.isEmpty()) {
			return null;
		}
		else {
			return compositeCommand.reduce();
		}
	}

	/**
	 * This allow to update the lower value specification of the multiplicity.
	 * 
	 * @param provider
	 *            The provider.
	 * @param eObject
	 *            The object to update.
	 * @param bound
	 *            The bound string representation.
	 * @return The command to update the lower multiplicity.
	 */
	private static CompositeCommand updateLowerValueSpecificationMultiplicityCommand(final IElementEditService provider, final EObject eObject, final String bound) {
		final CompositeCommand compositeCommand = new CompositeCommand("Lower Multiplicity update"); //$NON-NLS-1$
		
		ValueSpecification newLowerValueSpecification = (ValueSpecification) eObject.eGet(UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue());

		try {
			final int newBound = Integer.parseInt(bound);
			// If the value specification exists and is a literal integer, just set the new value if different
			if (newLowerValueSpecification instanceof LiteralInteger) {
				if (newBound != ((LiteralInteger) newLowerValueSpecification).getValue()) {
					compositeCommand.add(createSetCommand(provider, newLowerValueSpecification, UMLPackage.Literals.LITERAL_INTEGER__VALUE, newBound));
					// Unset the lower value
					compositeCommand.add(createSetCommand(provider, eObject, UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), null));
					// Reset the value specification
					compositeCommand.add(createSetCommand(provider, eObject, UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), newLowerValueSpecification));
				}
			} else {
				// If the value specification is not a LiteralInteger, create the literal integer and set the value
				newLowerValueSpecification = UMLFactory.eINSTANCE.createLiteralInteger();
				((LiteralInteger) newLowerValueSpecification).setValue(newBound);
				compositeCommand.add(createSetCommand(provider, eObject, UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), newLowerValueSpecification));
			}
		} catch (final NumberFormatException e) {
			// Manage the literal String
			compositeCommand.add(updateLiteralStringMultiplicityCommand(provider, eObject, newLowerValueSpecification, UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), bound));
		}
		return compositeCommand;
	}

	/**
	 * This allow to update the upper value specification of the multiplicity.
	 * 
	 * @param provider
	 *            The provider.
	 * @param eObject
	 *            The object to update.
	 * @param bound
	 *            The bound string representation.
	 * @return The command to update the upper multiplicity.
	 */
	private static CompositeCommand updateUpperValueSpecificationMultiplicityCommand(final IElementEditService provider, final EObject eObject, final String bound) {
		final CompositeCommand compositeCommand = new CompositeCommand("Upper Multiplicity update"); //$NON-NLS-1$
		
		ValueSpecification newUpperValueSpecification = (ValueSpecification) eObject.eGet(UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue());

		try {
			final int newBound = Integer.parseInt(bound);

			// If the value specification exists and is a literal integer, just set the new value if different
			if (newUpperValueSpecification instanceof LiteralUnlimitedNatural) {
				if (newBound != ((LiteralUnlimitedNatural) newUpperValueSpecification).getValue()) {
					compositeCommand.add(createSetCommand(provider, newUpperValueSpecification, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE, newBound));
					// Unset the upper value
					compositeCommand.add(createSetCommand(provider, eObject, UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), null));
					// Set the upper value specification
					compositeCommand.add(createSetCommand(provider, eObject, UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), newUpperValueSpecification));
				}
			} else {
				// If the value specification is not a LiteralInteger, create the literal integer and set the value
				newUpperValueSpecification = UMLFactory.eINSTANCE.createLiteralUnlimitedNatural();
				((LiteralUnlimitedNatural) newUpperValueSpecification).setValue(newBound);
				compositeCommand.add(createSetCommand(provider, eObject, UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), newUpperValueSpecification));
			}
		} catch (final NumberFormatException e) {
			// Manage the literal String
			compositeCommand.add(updateLiteralStringMultiplicityCommand(provider, eObject, newUpperValueSpecification, UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), bound));
		}
		return compositeCommand;
	}

	/**
	 * Update the literal String for the multiplicity Value Specification (lower or upper).
	 * 
	 * @param provider
	 *            The provider.
	 * @param eObject
	 *            The object to update.
	 * @param initialValueSpecification
	 *            The initial value specification.
	 * @param feature
	 *            The feature to update.
	 * @param bound
	 *            The new bound value.
	 * @return The command to update the string multiplicity.
	 */
	private static ICommand updateLiteralStringMultiplicityCommand(final IElementEditService provider, final EObject eObject, final ValueSpecification initialValueSpecification, final EStructuralFeature feature,
			final String bound) {
		final CompositeCommand compositeCommand = new CompositeCommand("String Multiplicity update"); //$NON-NLS-1$
		
		ValueSpecification newValueSpecification = initialValueSpecification;

		// Extract the quote if exists
		String newStringValue = null;
		if (bound.startsWith(QUOTE) && bound.endsWith(QUOTE)) {
			newStringValue = bound.substring(1, bound.length() - 1);
		} else {
			newStringValue = bound;
		}

		// If the value specification exists and is a literal integer, just set the new value if different
		if (newValueSpecification instanceof LiteralString) {
			if (!newStringValue.equals(((LiteralString) newValueSpecification).getValue())) {
				compositeCommand.add(createSetCommand(provider, newValueSpecification, UMLPackage.Literals.LITERAL_STRING__VALUE, newStringValue));
				// Unset the bound value
				compositeCommand.add(createSetCommand(provider, eObject, feature, null));
				// Reset the value specification
				compositeCommand.add(createSetCommand(provider, eObject, feature, newValueSpecification));
			}
		} else {
			// If the value specification is not a LiteralString, create the literal string and set the value
			newValueSpecification = UMLFactory.eINSTANCE.createLiteralString();
			((LiteralString) newValueSpecification).setValue(newStringValue);
			compositeCommand.add(createSetCommand(provider, eObject, feature, newValueSpecification));
		}
		return compositeCommand;
	}

	/**
	 * This allow to create the Set command and add it in the composite command.
	 * 
	 * @param provider
	 *            The provider.
	 * @param parent
	 *            The parent object to update
	 * @param feature
	 *            The feature to update.
	 * @param value
	 *            The value to set.
	 * @return The created set command.
	 */
	private static ICommand createSetCommand(final IElementEditService provider, final EObject parent, final EStructuralFeature feature, final Object value) {
		final SetRequest setBoundRequest = new SetRequest(parent, feature, value);
		return provider.getEditCommand(setBoundRequest);
	}
}
