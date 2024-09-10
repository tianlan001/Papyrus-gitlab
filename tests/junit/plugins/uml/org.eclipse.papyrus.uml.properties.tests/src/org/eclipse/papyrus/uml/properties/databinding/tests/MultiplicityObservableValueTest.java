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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.GMFObservableValue;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.properties.databinding.MultiplicityObservableValue;
import org.eclipse.papyrus.uml.properties.modelelement.MemberEndModelElement;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;

/**
 * This allows to test the multiplicity observable value.
 */
@SuppressWarnings({ "unchecked", "rawtypes", "nls" })
@PluginResource("model/MultiplicityObservableValue.di")
public class MultiplicityObservableValueTest extends AbstractUMLObservableValueTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		final Class existingClass = (Class) model.getOwnedMember("Class1");
		Assert.assertNotNull("The class 'Class1' must be available in the model", existingClass);

		final Property existingProperty = existingClass.getOwnedAttributes().stream().filter(p -> "Property1".equals(p.getName())).findFirst().get();
		Assert.assertNotNull("The property 'Property1' must be available in the class 'Class1'", existingClass);

		return existingProperty;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new MemberEndModelElement((EObject) source, domain);
	}

	/**
	 * We need to re-implement the check of the initial value because the observable value for multiplicity is different.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#checkInitialValue(org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@Override
	protected void checkInitialValue(final IObservableValue observableValue) {
		final Object value = observableValue.getValue();
		Assert.assertTrue("The value must be a list of observable values", value instanceof List);
		Assert.assertEquals("The list of observable values must be equals to 3", 3, ((Collection) value).size());

		final Object firstObservableValue = ((List) value).get(0);
		Assert.assertTrue("The first observable value must be the multiplicity observable value", firstObservableValue instanceof MultiplicityObservableValue);
		Assert.assertEquals("The initial value type of first observable value is not the expected one", String.class, ((MultiplicityObservableValue) firstObservableValue).getValueType());
		Assert.assertEquals("The initial value of first observable value is not the expected one", expectedBeforeValue(), ((MultiplicityObservableValue) firstObservableValue).getValue());

		final Object secondObservableValue = ((List) value).get(1);
		Assert.assertTrue("The second observable value must be the multiplicity observable value", secondObservableValue instanceof IObservableValue);
		Assert.assertEquals("The initial value type of second observable value is not the expected one", UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), ((IObservableValue) secondObservableValue).getValueType());
		Assert.assertEquals("The initial value of second observable value is not the expected one", null, ((IObservableValue) secondObservableValue).getValue());

		final Object thirdObservableValue = ((List) value).get(2);
		Assert.assertTrue("The third observable value must be the multiplicity observable value", thirdObservableValue instanceof IObservableValue);
		Assert.assertEquals("The initial value type of third observable value is not the expected one", UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), ((IObservableValue) thirdObservableValue).getValueType());
		Assert.assertEquals("The initial value of third observable value is not the expected one", null, ((IObservableValue) thirdObservableValue).getValue());
	}

	/**
	 * We need to re-implement the check of the modified value because the observable value for multiplicity is different.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#checkModifiedValue(org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@Override
	protected void checkModifiedValue(final IObservableValue observableValue) {
		final Object value = observableValue.getValue();
		Assert.assertTrue("The value must be a list of observable values", value instanceof List);
		Assert.assertEquals("The size of the list of observable values is not the correct one", 3, ((Collection) value).size());

		final Object firstObservableValue = ((List) value).get(0);
		Assert.assertTrue("The first observable value must be the multiplicity observable value", firstObservableValue instanceof MultiplicityObservableValue);
		Assert.assertEquals("The modified value type of first observable value is not the expected one", String.class, ((MultiplicityObservableValue) firstObservableValue).getValueType());
		Assert.assertEquals("The modified value of first observable value is not the expected one", expectedAfterValue(), ((MultiplicityObservableValue) firstObservableValue).getValue());

		final Object secondObservableValue = ((List) value).get(1);
		Assert.assertTrue("The second observable value must be the multiplicity observable value", secondObservableValue instanceof IObservableValue);
		Assert.assertEquals("The modified value type of second observable value is not the expected one", UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), ((IObservableValue) secondObservableValue).getValueType());
		final Object secondValue = ((IObservableValue) secondObservableValue).getValue();
		Assert.assertTrue("The modified value of second observable value is not the expected one", secondValue instanceof LiteralInteger);
		Assert.assertEquals("The modified value of second observable value is not the expected one", 0, ((LiteralInteger) secondValue).getValue());

		final Object thirdObservableValue = ((List) value).get(2);
		Assert.assertTrue("The third observable value must be the multiplicity observable value", thirdObservableValue instanceof IObservableValue);
		Assert.assertEquals("The modified value type of third observable value is not the expected one", UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), ((IObservableValue) thirdObservableValue).getValueType());
		final Object thirdValue = ((IObservableValue) thirdObservableValue).getValue();
		Assert.assertTrue("The modified value of third observable value is not the expected one", thirdValue instanceof LiteralUnlimitedNatural);
		Assert.assertEquals("The modified value of third observable value is not the expected one", 2, ((LiteralUnlimitedNatural) thirdValue).getValue());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return MemberEndModelElement.MULTIPLICITY;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return "1";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableValueTest#expectedValueType()
	 */
	@Override
	protected Object expectedValueType() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		return "0..2";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		final List<IObservableValue> values = new ArrayList<IObservableValue>(3);
		IObservableValue multiplicityObservableValue = new MultiplicityObservableValue((EObject) getContextObject(), domain);
		multiplicityObservableValue.setValue(expectedAfterValue());
		values.add(multiplicityObservableValue);
		values.add(new GMFObservableValue((EObject) getContextObject(), UMLPackage.eINSTANCE.getMultiplicityElement_LowerValue(), domain));
		values.add(new GMFObservableValue((EObject) getContextObject(), UMLPackage.eINSTANCE.getMultiplicityElement_UpperValue(), domain));
		return values;
	}

}
