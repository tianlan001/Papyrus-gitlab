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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.AbstractRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralBooleanRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralIntegerOrUnlimitedNaturalRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralNullRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralRealRule;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.LiteralStringRule;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * This class allow to define the multiplicity lower ValueSpecification set command which redefine the {@link #createValueSpecification(EObject, EObject, AbstractRule)} for the specific LiteralUnlimitedNatural (or integer if lower than 0) creation when the
 * user fill an integer.
 */
public class MultiplicityUpperValueSetCommand extends AbstractMultiplicityValueSpecificationSetCommand {

	/**
	 * The instance of the class.
	 */
	private static MultiplicityUpperValueSetCommand instance = new MultiplicityUpperValueSetCommand();


	/**
	 * Constructor.
	 */
	public MultiplicityUpperValueSetCommand() {
		super();
	}

	/**
	 * Get the single instance of MultiplicityUpperValueSetCommand.
	 * 
	 * @return The single instance of MultiplicityUpperValueSetCommand.
	 */
	public static MultiplicityUpperValueSetCommand getInstance() {
		return instance;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.textedit.valuespecification.xtext.utils.commands.ValueSpecificationSetCommand#createValueSpecification(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.papyrus.uml.textedit.valuespecification.xtext.umlValueSpecification.AbstractRule)
	 */
	@Override
	protected ValueSpecification createValueSpecification(final EObject objectToEdit, final EObject initialValueSpecification, final AbstractRule abstractRule) {
		ValueSpecification createdValueSpecification = null;

		final EObject value = abstractRule.getValue();
		if (null != abstractRule.getInstanceSpecification()) {
			// Create an instance value with specification value
			createdValueSpecification = UMLFactory.eINSTANCE
					.createInstanceValue();
			((InstanceValue) createdValueSpecification)
					.setInstance(abstractRule.getInstanceSpecification());
		} else {
			if (value instanceof LiteralBooleanRule) {
				// Create a literal boolean
				createdValueSpecification = UMLFactory.eINSTANCE
						.createLiteralBoolean();
				copyFeatureValues(createdValueSpecification,
						initialValueSpecification);
				((LiteralBoolean) createdValueSpecification)
						.setValue(Boolean
								.parseBoolean(((LiteralBooleanRule) value)
										.getValue()));
			} else if (value instanceof LiteralIntegerOrUnlimitedNaturalRule) {
				final LiteralIntegerOrUnlimitedNaturalRule integerValue = (LiteralIntegerOrUnlimitedNaturalRule) value;
				
				try{
					int intValue = -1;
				
					if (null == integerValue.getUnlimited()) {
						intValue = integerValue.getValue();
					}
					
					// Always create a LiteralUnlimitedNatural for the upper value of the multiplicity
					if(-1 <= intValue){
						createdValueSpecification = UMLFactory.eINSTANCE
								.createLiteralUnlimitedNatural();
						copyFeatureValues(createdValueSpecification,
								initialValueSpecification);
						((LiteralUnlimitedNatural) createdValueSpecification)
								.setValue(intValue);
					}
				}catch(final NumberFormatException exception){
					// Just continue
				}

			} else if (value instanceof LiteralRealRule) {
				// Create a literal real
				createdValueSpecification = UMLFactory.eINSTANCE
						.createLiteralReal();
				copyFeatureValues(createdValueSpecification,
						initialValueSpecification);
				((LiteralReal) createdValueSpecification)
						.setValue(((LiteralRealRule) value).getValue());
			} else if (value instanceof LiteralNullRule) {
				// Create a literal null
				createdValueSpecification = UMLFactory.eINSTANCE
						.createLiteralNull();
				copyFeatureValues(createdValueSpecification,
						initialValueSpecification);
			} else if (value instanceof LiteralStringRule) {
				// Create a literal real
				createdValueSpecification = UMLFactory.eINSTANCE
						.createLiteralString();
				copyFeatureValues(createdValueSpecification,
						initialValueSpecification);
				((LiteralString) createdValueSpecification)
						.setValue(((LiteralStringRule) value).getValue());
			}
		}

		return createdValueSpecification;
	}

}
