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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.properties.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.properties.ui.creation.EcorePropertyEditorFactory;

/**
 * This class has been created to be able to find all available BooleanEObject expressions using the EMF Mechanism, in order to be able to create
 * them easily outside of the EMF Editor. The super class EcorePropertyEditorFactory is only able to found the expressions provided by expressions.ecore and not them owned by umlexpressions.ecore
 *
 */
public class BooleanEObjectExpressionPropertyEditorFactory extends EcorePropertyEditorFactory {

	/**
	 * The editing domain
	 */
	private AdapterFactoryEditingDomain domain;

	/**
	 * an expression parameterized by Boolean and EObject (And, Or, Not, Reference, ...) could be used for this role
	 */
	private AndExpression dummyBooleanEObjectExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();

	/**
	 * Constructor.
	 *
	 * @param referenceIn
	 */
	public BooleanEObjectExpressionPropertyEditorFactory(final AdapterFactoryEditingDomain domain, final EReference referenceIn) {
		super(referenceIn);
		this.domain = domain;
	}


	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.creation.EcorePropertyEditorFactory#getAvailableEClasses()
	 *
	 * @return
	 */
	@Override
	protected List<EClass> getAvailableEClasses() {
		final List<EClass> availableEClasses = new ArrayList<EClass>();

		final Collection<?> desc = this.domain.getNewChildDescriptors(this.dummyBooleanEObjectExp, null);
		for (Object curr : desc) {
			if (curr instanceof CommandParameter) {
				EClass tmp = ((CommandParameter) curr).getEValue().eClass();
				if (null != tmp) {
					availableEClasses.add(tmp);
				}
			}
		}
		return availableEClasses;
	}
	
}
