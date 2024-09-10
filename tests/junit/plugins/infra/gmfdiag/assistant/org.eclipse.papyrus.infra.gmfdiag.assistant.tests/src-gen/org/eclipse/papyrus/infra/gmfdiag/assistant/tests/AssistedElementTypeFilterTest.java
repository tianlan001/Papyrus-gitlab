/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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

import static org.eclipse.papyrus.infra.gmfdiag.assistant.tests.ElementTypesUtil.canonicalize;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Assisted Element Type Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider() <em>Provider</em>}</li>
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
public class AssistedElementTypeFilterTest extends TestCase {

	/**
	 * The fixture for this Assisted Element Type Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AssistedElementTypeFilter fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AssistedElementTypeFilterTest.class);
	}

	/**
	 * Constructs a new Assisted Element Type Filter test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AssistedElementTypeFilterTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Assisted Element Type Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(AssistedElementTypeFilter fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Assisted Element Type Filter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AssistedElementTypeFilter getFixture() {
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
		ResourceSet rset = new ResourceSetImpl();
		rset.eAdapters().add(new CacheAdapter()); // bug 541590 [CDO] - change is not required here
		Resource res = rset.getResource(URI.createPlatformPluginURI("org.eclipse.papyrus.infra.gmfdiag.assistant.tests/resources/test.assistants", true), true);

		setFixture((AssistedElementTypeFilter) ((ModelingAssistantProvider) res.getContents().get(0)).getOwnedFilter("isAssistedElementType"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated NOT
	 */
	@Override
	protected void tearDown() throws Exception {
		EMFHelper.unload(EMFHelper.getResourceSet(getFixture()));
		setFixture(null);
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider() <em>Provider</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider()
	 * @generated NOT
	 */
	public void testGetProvider() {
		ModelingAssistantProvider provider = (ModelingAssistantProvider) EcoreUtil.getRootContainer(getFixture());

		assertThat(getFixture().getProvider(), is(provider));

		try {
			EcoreUtil.remove(getFixture());
			assertThat(getFixture().getProvider(), nullValue());
		} finally {
			// Let it be properly unloaded
			provider.getOwnedFilters().add(getFixture());
		}
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object)
	 * @generated NOT
	 */
	public void testMatches__Object() {
		// Match an element type
		assertThat(getFixture().matches(canonicalize(UMLElementTypes.DATA_TYPE)), is(true));
		assertThat(getFixture().matches(canonicalize(UMLElementTypes.ENUMERATION)), is(false)); // don't match element-type subtypes
		assertThat(getFixture().matches(canonicalize(UMLElementTypes.CALL_BEHAVIOR_ACTION)), is(false));

		// Create some objects that will be cleaned up properly at tear-down
		Resource uml = UMLResource.Factory.INSTANCE.createResource(URI.createURI("test://model.uml"));
		EMFHelper.getResourceSet(getFixture()).getResources().add(uml);
		uml.getContents().addAll(Arrays.asList( //
				UMLFactory.eINSTANCE.createDataType(), //
				UMLFactory.eINSTANCE.createEnumeration(), //
				UMLFactory.eINSTANCE.createCallBehaviorAction()));

		// Match an object
		assertThat(getFixture().matches(uml.getContents().get(0)), is(true));
		assertThat(getFixture().matches(uml.getContents().get(1)), is(true)); // match object subtypes
		assertThat(getFixture().matches(uml.getContents().get(2)), is(false));
	}

} // AssistedElementTypeFilterTest
