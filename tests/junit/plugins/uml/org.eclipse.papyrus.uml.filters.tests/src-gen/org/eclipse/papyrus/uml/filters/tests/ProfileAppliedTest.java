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
package org.eclipse.papyrus.uml.filters.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.papyrus.uml.filters.ProfileApplied;
import org.eclipse.papyrus.uml.filters.UMLFiltersFactory;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Profile Applied</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.filters.ProfileApplied#resolveProfile(java.lang.Object) <em>Resolve Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfileAppliedTest extends TestCase {

	/**
	 * The fixture for this Profile Applied test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ProfileApplied fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ProfileAppliedTest.class);
	}

	/**
	 * Constructs a new Profile Applied test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ProfileAppliedTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Profile Applied test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(ProfileApplied fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Profile Applied test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ProfileApplied getFixture() {
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
		Profile profile = UMLFactory.eINSTANCE.createProfile();
		profile.setName("profile1");
		profile.define();

		ResourceSet rset = new ResourceSetImpl();
		Resource profileResource = UMLResource.Factory.INSTANCE.createResource(URI.createURI("test://profile1.uml", true));
		rset.getResources().add(profileResource);
		profileResource.getContents().add(profile);

		Resource filterResource = new XMIResourceFactoryImpl().createResource(URI.createURI("test://test.filters", true));
		rset.getResources().add(filterResource);
		ProfileApplied fixture = UMLFiltersFactory.eINSTANCE.createProfileApplied();
		filterResource.getContents().add(fixture);

		fixture.setProfileURI(EcoreUtil.getURI(profile).toString());
		fixture.setProfileQualifiedName(profile.getQualifiedName());
		setFixture(fixture);
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
		ProfileApplied fixture = getFixture();

		setFixture(null);

		// Dispose the resource set, making sure any CacheAdapters are purged
		ResourceSet rset = fixture.eResource().getResourceSet();
		for (Resource next : rset.getResources()) {
			next.unload();
			next.eAdapters().clear();
		}
		rset.getResources().clear();
		rset.eAdapters().clear();
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
		ResourceSet rset = getFixture().eResource().getResourceSet();
		Resource modelResource = UMLResource.Factory.INSTANCE.createResource(URI.createURI("test://model1.uml", true));
		rset.getResources().add(modelResource);

		Package model = UMLFactory.eINSTANCE.createPackage();
		modelResource.getContents().add(model);
		model.setName("model1");
		Class foo = model.createOwnedClass("Foo", false);

		// Profile is not applied
		assertThat(getFixture().matches(foo), is(false));

		Profile profile = UML2Util.load(rset, URI.createURI(getFixture().getProfileURI(), true).trimFragment(), UMLPackage.Literals.PROFILE);
		assumeThat(profile, notNullValue());
		model.applyProfile(profile);

		// Profile is applied
		assertThat(getFixture().matches(foo), is(true));

		// Destroy the profile resource: makes the profile an unresolvable proxy
		Resource profileResource = profile.eResource();
		profileResource.unload();
		profileResource.getResourceSet().getResources().remove(profileResource);
		profileResource.eAdapters().clear();

		// Even if profile cannot be resolved, the URIs are still known and can be compared
		assertThat(getFixture().matches(foo), is(true));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.uml.filters.ProfileApplied#resolveProfile(java.lang.Object) <em>Resolve Profile</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.uml.filters.ProfileApplied#resolveProfile(java.lang.Object)
	 * @generated NOT
	 */
	public void testResolveProfile__Object() {
		Profile profile = getFixture().resolveProfile(getFixture()); // Test uses one resource set for everything
		assertThat(profile, notNullValue());
		assertThat(profile.getQualifiedName(), is(getFixture().getProfileQualifiedName()));

		getFixture().setProfileURI("bogus://profile1.uml#_0");

		// No longer resolves
		assertThat(getFixture().resolveProfile(getFixture()), nullValue());
	}

} // ProfileAppliedTest
