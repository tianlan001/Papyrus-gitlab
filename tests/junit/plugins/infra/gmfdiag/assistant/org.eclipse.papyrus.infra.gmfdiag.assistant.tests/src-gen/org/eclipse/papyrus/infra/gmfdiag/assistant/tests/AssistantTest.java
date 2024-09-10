/**
 * Copyright (c) 2014 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import junit.framework.TestCase;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Assistant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType() <em>Element Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AssistantTest extends TestCase {

	/**
	 * The fixture for this Assistant test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected Assistant fixture = null;

	/**
	 * Constructs a new Assistant test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AssistantTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Assistant test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(Assistant fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Assistant test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected Assistant getFixture() {
		return fixture;
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType() <em>Element Type</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType()
	 * @generated NOT
	 */
	public void testGetElementType() {
		assertThat(getFixture().getElementType(), nullValue());

		getFixture().setElementTypeID("org.eclipse.papyrus.uml.Class");
		assertThat(getFixture().getElementType(), is(ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Class")));
	}

} // AssistantTest
