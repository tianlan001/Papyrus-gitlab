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
import java.util.Collections;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * This allows to define an abstract test class for an observable list.
 */
@SuppressWarnings({ "unchecked", "rawtypes", "serial", "nls" })
public abstract class AbstractObservableListTest extends AbstractObservableTest {

	/**
	 * This allows to test the observable list (creation, add, remove, retain and clear).
	 */
	@Test
	public void testObservableList() {
		final Object object = getContextObject();

		// Create the observable
		final ModelElement modelElement = createModelElement(domain, object);
		final IObservable observable = modelElement.getObservable(getPropertyPath());
		Assert.assertTrue("The created observable must be an IObservableList", observable instanceof IObservableList);

		final IObservableList observableList = (IObservableList) observable;

		// Test the initial value and value type
		checkInitialList(observableList);

		if (canModify()) {
			boolean testAdd = testAdd();
			boolean testRemove = testRemove();
			boolean testAddAll = testAddAll();
			boolean testRemoveAll = testRemoveAll();
			boolean testRetainAll = testRetainAll();
			boolean testClear = testClear();

			if (testAdd) {
				// Add an item
				observableList.add(expectedValueToSet());
				checkModifiedList(observableList);
			}

			if (testRemove && testAdd) {
				// Remove the added element
				observableList.remove(expectedValueToSet());
				checkInitialList(observableList);
			}

			if (testAddAll) {
				// Try the addAll (even if there is only one element in the list)
				observableList.addAll(new ArrayList(1) {
					{
						add(expectedValueToSet());
					}
				});
				checkModifiedList(observableList);
			}

			if (testRemoveAll && (testAddAll || testAdd)) {
				if (!testAddAll) {
					// Add an item
					observableList.add(expectedValueToSet());
					checkModifiedList(observableList);
				}
				// Try to remove the added element with removeAll (even if there is only element in the list)
				observableList.removeAll(Collections.singletonList(expectedValueToSet()));
				checkInitialList(observableList);
			}

			if (testRetainAll) {
				// Set a new value and test it
				observableList.add(expectedValueToSet());
				checkModifiedList(observableList);

				// Add an element to test the retain method
				observableList.add(expectedValueToSet());
				observableList.retainAll((Collection) expectedBeforeValue());
				checkInitialList(observableList);
			}

			if (testClear) {
				// Try to clear the list
				observableList.clear();
				checkClearedList(observableList);
			}
		}
	}

	/**
	 * This allows to check the list at the initial state.
	 * 
	 * @param observableList
	 *            The current observable list.
	 */
	protected void checkInitialList(final IObservableList observableList) {
		final Object initialValue = expectedBeforeValue();
		Assert.assertTrue("The expected initial value must be a collection", initialValue instanceof Collection);
		Assert.assertTrue("The initial list is not the expected one", observableList.containsAll((Collection) initialValue));
	}

	/**
	 * This allows to check the list when it was modified.
	 * 
	 * @param observableList
	 *            The current observable list.
	 */
	protected void checkModifiedList(final IObservableList observableList) {
		final Object modifiedValue = expectedAfterValue();
		Assert.assertTrue("The expected modified value must be a collection", modifiedValue instanceof Collection);
		Assert.assertTrue("The modified list is not the expected one", observableList.containsAll((Collection) modifiedValue));
	}

	/**
	 * This allows to check the list when it was cleared.
	 * 
	 * @param observableList
	 *            The current observable list.
	 */
	protected void checkClearedList(final IObservableList observableList) {
		Assert.assertEquals("The cleared list must be empty", 0, observableList.size());
	}

	/**
	 * This allows to define if this is needed to test the 'add' method.
	 * 
	 * @return <code>true</code> if the 'add' should be tested, <code>false</code> otherwise.
	 */
	protected boolean testAdd() {
		return true;
	}

	/**
	 * This allows to define if this is needed to test the 'remove' method.
	 * 
	 * @return <code>true</code> if the 'remove' should be tested, <code>false</code> otherwise.
	 */
	protected boolean testRemove() {
		return true;
	}

	/**
	 * This allows to define if this is needed to test the 'addAll' method.
	 * 
	 * @return <code>true</code> if the 'addAll' should be tested, <code>false</code> otherwise.
	 */
	protected boolean testAddAll() {
		return true;
	}

	/**
	 * This allows to define if this is needed to test the 'removeAll' method.
	 * 
	 * @return <code>true</code> if the 'removeAll' should be tested, <code>false</code> otherwise.
	 */
	protected boolean testRemoveAll() {
		return true;
	}

	/**
	 * This allows to define if this is needed to test the 'retainAll' method.
	 * 
	 * @return <code>true</code> if the 'retainAll' should be tested, <code>false</code> otherwise.
	 */
	protected boolean testRetainAll() {
		return true;
	}

	/**
	 * This allows to define if this is needed to test the 'clear' method.
	 * 
	 * @return <code>true</code> if the 'clear' should be tested, <code>false</code> otherwise.
	 */
	protected boolean testClear() {
		return true;
	}
}
