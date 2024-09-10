/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.databinding.tests;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.junit.Assert;

/**
 * This allows to test the owned comments observable list.
 */
@SuppressWarnings({ "nls" })
@PluginResource("model/BasicObservableList.di")
public class BasicObservableListTest extends AbstractUMLObservableListTest {

	/**
	 * The existing comment to add to the existing class.
	 */
	protected OpaqueBehavior existingOpaqueBehavior = null;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		final Class existingClass = (Class) model.getOwnedMember("Class1");
		Assert.assertNotNull("The class 'Class1' must be available in the model", existingClass);

		final Operation operation = (Operation) existingClass.getOwnedMember("Operation1");
		Assert.assertNotNull("The operation 'Operation1' must be available in the model", operation);

		return operation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new UMLModelElement((EObject) source, domain);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return "method";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		if (null != existingOpaqueBehavior) {
			Collections.singletonList(existingOpaqueBehavior);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		if (null == existingOpaqueBehavior) {
			final Class existingClass = (Class) model.getOwnedMember("Class1");
			existingOpaqueBehavior = (OpaqueBehavior) existingClass.getOwnedMember("OpaqueBehavior1");
			Assert.assertNotNull("An OpaqueBehavior should exist in the model", existingOpaqueBehavior);
		}
		return existingOpaqueBehavior;
	}

}
