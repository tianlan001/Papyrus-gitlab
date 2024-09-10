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

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

/**
 * This allows to define an abstract test class for an observable (value or list).
 */
@SuppressWarnings({ "nls" })
public abstract class AbstractObservableTest extends AbstractPapyrusTest {

	/**
	 * The editor fixture to manage the opening model.
	 */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/**
	 * The editing domain.
	 */
	protected EditingDomain domain = null;

	/**
	 * The context object (object selected for the properties view in functional manuel tests).
	 */
	protected Object contextObject = null;

	/**
	 * This allows to set the variables needed for the test.
	 */
	@Before
	public void setup() {
		domain = editorFixture.getEditingDomain();
	}
	
	/**
	 * This allows to dispose the needed elements.
	 */
	@After
	public void dispose() {
		domain = null;
		contextObject = null;
	}

	/**
	 * This allows to define if the property can be modified or not.
	 * By default, return <code>true</code>.
	 * 
	 * @return <code>true</code> if the values can be set, <code>false</code> otherwise.
	 */
	protected boolean canModify() {
		return true;
	}

	/**
	 * Get the context object to modify.
	 * 
	 * @return The context object to modify.
	 */
	protected Object getContextObject() {
		// If the context is not defined, initialize it
		if (null == contextObject) {
			contextObject = initializeContextObject();
		}
		return contextObject;
	}

	/**
	 * This allows to initialize the context object.
	 * 
	 * @return The context object to modify.
	 */
	protected abstract Object initializeContextObject();

	/**
	 * This allows to create the model element which allows to get the observable value depending to the property path.
	 * 
	 * @param domain
	 *            The editing domain.
	 * @param source
	 *            The source for the model element.
	 * @return The created model element.
	 */
	protected abstract ModelElement createModelElement(final EditingDomain domain, final Object source);

	/**
	 * The property path needed to get the correct observable value from the model element.
	 * 
	 * @return The property path.
	 */
	protected abstract String getPropertyPath();

	/**
	 * Define the expected initial value.
	 * 
	 * @return The expected initial value.
	 */
	protected abstract Object expectedBeforeValue();

	/**
	 * Define the expected modified value.
	 * 
	 * @return The expected modified value.
	 */
	protected abstract Object expectedAfterValue();

	/**
	 * Define the value to set.
	 * 
	 * @return The value to set.
	 */
	protected abstract Object expectedValueToSet();

}
