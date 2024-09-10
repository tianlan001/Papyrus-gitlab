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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.gmf.runtime.emf.type.core.EditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Element Type Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType() <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider() <em>Provider</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElementTypeFilterTest extends TestCase {

	/**
	 * The fixture for this Element Type Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ElementTypeFilter fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ElementTypeFilterTest.class);
	}

	/**
	 * Constructs a new Element Type Filter test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ElementTypeFilterTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Element Type Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(ElementTypeFilter fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Element Type Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ElementTypeFilter getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		ModelingAssistantProvider provider = AssistantFactory.eINSTANCE.createModelingAssistantProvider();
		provider.setName("test");
		setFixture((ElementTypeFilter) provider.createOwnedFilter("elementType", AssistantPackage.Literals.ELEMENT_TYPE_FILTER));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType() <em>Element Type</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType()
	 * @generated NOT
	 */
	public void testGetElementType() {
		IElementType type = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.AssociationBase");

		assertThat(getFixture().getElementType(), nullValue());

		getFixture().setElementTypeID("org.eclipse.papyrus.uml.AssociationBase");

		assertThat(getFixture().getElementType(), is(type));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider() <em>Provider</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider()
	 * @generated NOT
	 */
	public void testGetProvider()
	{
		assertThat(getFixture().getProvider(), notNullValue());
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object)
	 * @generated NOT
	 */
	public void testMatches__Object()
	{
		IElementType type = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.AssociationBase");
		Association association = UMLFactory.eINSTANCE.createAssociation();
		IEditHelperContext editContext = new EditHelperContext(association, ElementTypeUtils.getDefaultClientContext());

		assertThat(getFixture().matches(type), is(false));
		assertThat(getFixture().matches(association), is(false));
		assertThat(getFixture().matches(editContext), is(false));

		getFixture().setElementTypeID("org.eclipse.papyrus.uml.AssociationBase");

		assertThat(getFixture().matches(type), is(true));
		assertThat(getFixture().matches(association), is(true));
		assertThat(getFixture().matches(editContext), is(true));

		getFixture().setElementTypeID("org.eclipse.papyrus.uml.Class");

		assertThat(getFixture().matches(type), is(false));
		assertThat(getFixture().matches(association), is(false));
		assertThat(getFixture().matches(editContext), is(false));
	}

} // ElementTypeFilterTest
