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

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * This allows to define an abstract test class for an observable value.
 */
@SuppressWarnings({ "unchecked", "rawtypes", "nls" })
public abstract class AbstractObservableValueTest extends AbstractObservableTest {

	/**
	 * This allows to test the observable value (creation, getValue and setValue).
	 */
	@Test
	public void testObservableValue() {
		final Object object = getContextObject();

		// Create the observable
		final ModelElement modelElement = createModelElement(domain, object);
		final IObservable observable = modelElement.getObservable(getPropertyPath());
		Assert.assertTrue("The created observable must be an IObservableValue", observable instanceof IObservableValue);

		final IObservableValue observableValue = (IObservableValue) observable;

		Assert.assertEquals("The initial value type is not the expected one", expectedValueType(), observableValue.getValueType());

		// Test the initial value and value type
		checkInitialValue(observableValue);

		if (canModify()) {
			// Set a new value and test it
			observableValue.setValue(expectedValueToSet());
			checkModifiedValue(observableValue);

			if (testUndo()) {
				// Test the undo
				domain.getCommandStack().undo();
				checkInitialValue(observableValue);

				// Test the redo
				domain.getCommandStack().redo();
				checkModifiedValue(observableValue);
			}
		}
	}

	/**
	 * Define if the undo/redo should be tested.
	 * By default, return <code>true</code>.
	 * 
	 * @return <code>true</code> if the undo should be tested, <code>false</code> otherwise.
	 */
	protected boolean testUndo() {
		return true;
	}

	/**
	 * This allows to check the value at the initial state.
	 * 
	 * @param observableValue
	 *            The current observable value.
	 */
	protected void checkInitialValue(final IObservableValue observableValue) {
		Assert.assertEquals("The initial value is not the expected one", expectedBeforeValue(), observableValue.getValue());
	}

	/**
	 * This allows to check the value when it was modified.
	 * 
	 * @param observableValue
	 *            The current observable value.
	 */
	protected void checkModifiedValue(final IObservableValue observableValue) {
		Assert.assertEquals("The modified value is not the expected one", expectedAfterValue(), observableValue.getValue());
	}

	/**
	 * Define the expected value type of the observable value.
	 * 
	 * @return The expected value type.
	 */
	protected abstract Object expectedValueType();

}
