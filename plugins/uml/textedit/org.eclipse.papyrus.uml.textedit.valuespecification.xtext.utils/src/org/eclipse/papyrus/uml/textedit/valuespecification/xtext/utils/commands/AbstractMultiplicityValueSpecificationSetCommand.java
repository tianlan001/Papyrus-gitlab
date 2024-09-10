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

package org.eclipse.papyrus.uml.textedit.valuespecification.xtext.utils.commands;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.AbstractRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralBooleanRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralIntegerOrUnlimitedNaturalRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralNullRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralRealRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralStringRule;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * This abstract class allow to define the method {@link #getObjectToUpdate(EObject, ValueSpecification, EObject)} for the multiplicity lower and upper value.
 */
public abstract class AbstractMultiplicityValueSpecificationSetCommand extends ValueSpecificationSetCommand {

	/**
	 * Constructor.
	 */
	public AbstractMultiplicityValueSpecificationSetCommand() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.textedit.valuespecification.xtext.utils.commands.ValueSpecificationSetCommand#getParseCommand(org.eclipse.emf.ecore.EObject, org.eclipse.uml2.uml.ValueSpecification, org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecore.EObject, java.lang.String, java.util.Collection)
	 */
	protected ICommand getParseCommand(final EObject objectToEdit,
			final ValueSpecification initialValueSpecification,
			final EStructuralFeature structuralFeature,
			final EObject xtextObject, final String xtextStringValue,
			final Collection<String> defaultLanguages) {

		// Check if the object to edit is not multi-valued
		if (null != objectToEdit) {
			return manageValueSpecification(objectToEdit, structuralFeature,
					initialValueSpecification, xtextObject, xtextStringValue,
					defaultLanguages);
		} else {
			// The object is multi-valued, create an opaque expression
			return manageOpaqueExpression(
					objectToEdit, structuralFeature, initialValueSpecification, xtextStringValue,
					defaultLanguages);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.textedit.valuespecification.xtext.utils.commands.ValueSpecificationSetCommand#getObjectToUpdate(org.eclipse.emf.ecore.EObject, org.eclipse.uml2.uml.ValueSpecification, org.eclipse.emf.ecore.EObject)
	 */
	protected ValueSpecification getObjectToUpdate(final EObject objectToEdit, final ValueSpecification initialValueSpecification, final EObject xtextObject) {
		ValueSpecification result = null;
		if (null != initialValueSpecification && null != xtextObject && xtextObject instanceof AbstractRule && null == ((AbstractRule) xtextObject).getUndefined()) {
			final AbstractRule abstractRule = (AbstractRule) xtextObject;
			if (initialValueSpecification instanceof InstanceValue && null != abstractRule.getInstanceSpecification()) {
				result = (InstanceValue) initialValueSpecification;
			} else {
				final EObject xtextValue = abstractRule.getValue();
				if (initialValueSpecification instanceof LiteralBoolean && xtextValue instanceof LiteralBooleanRule) {
					result = (LiteralBoolean) initialValueSpecification;
				} else if (initialValueSpecification instanceof LiteralInteger && xtextValue instanceof LiteralIntegerOrUnlimitedNaturalRule) {
					if (null == ((LiteralIntegerOrUnlimitedNaturalRule) xtextValue).getUnlimited()) {
						result = (LiteralInteger) initialValueSpecification;
					}
				} else if (initialValueSpecification instanceof LiteralUnlimitedNatural && xtextValue instanceof LiteralIntegerOrUnlimitedNaturalRule) {
					// Check that the value if positive for the unlimited natural type
					final LiteralIntegerOrUnlimitedNaturalRule integerValue = ((LiteralIntegerOrUnlimitedNaturalRule) xtextValue);
					if (null != integerValue.getUnlimited() || 0 <= integerValue.getValue()) {
						result = (LiteralUnlimitedNatural) initialValueSpecification;
					}
				} else if (initialValueSpecification instanceof LiteralReal && xtextValue instanceof LiteralRealRule) {
					result = (LiteralReal) initialValueSpecification;
				} else if (initialValueSpecification instanceof LiteralNull && xtextValue instanceof LiteralNullRule) {
					result = (LiteralNull) initialValueSpecification;
				} else if (initialValueSpecification instanceof LiteralString && xtextValue instanceof LiteralStringRule) {
					result = (LiteralString) initialValueSpecification;
				}
			}
		}
		return result;
	}

}
