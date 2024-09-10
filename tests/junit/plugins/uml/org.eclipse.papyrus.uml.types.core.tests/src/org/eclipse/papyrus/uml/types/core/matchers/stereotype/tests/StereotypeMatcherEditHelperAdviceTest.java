/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.core.matchers.stereotype.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.MetamodelType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherFactory;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherEditHelperAdvice;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link StereotypeMatcherEditHelperAdvice} class.
 */
public class StereotypeMatcherEditHelperAdviceTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@CleanUp
	private ResourceSet rset = new ResourceSetImpl();

	private Profile standardProfile;
	private Package model;

	public StereotypeMatcherEditHelperAdviceTest() {
		super();
	}

	/**
	 * Verifies that the advice accounts for the inheritance of profile applications
	 * by nested packages.
	 *
	 * @see <a href="https://eclip.se/573885">bug 573885</a>
	 */
	@Test
	public void nestedPackageInProfileContext() {
		StereotypeMatcherAdviceConfiguration config = StereotypeApplicationMatcherFactory.eINSTANCE.createStereotypeMatcherAdviceConfiguration();
		config.setProfileUri(standardProfile.getURI());
		config.getStereotypesQualifiedNames().add("StandardProfile::Metaclass");

		Package nested = model.createNestedPackage("nested");

		StereotypeMatcherEditHelperAdvice advice = new StereotypeMatcherEditHelperAdvice(config);

		IElementType fakeType = new MetamodelType("metaclass", null, "Metaclass", UMLPackage.Literals.CLASS, null);
		CreateElementRequest createRequest = new CreateElementRequest(nested, fakeType);
		assertThat("Request not approved", advice.approveRequest(createRequest), is(true));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		model = UMLFactory.eINSTANCE.createModel();
		Resource resource = rset.createResource(URI.createURI("bogus:test"), UMLPackage.eCONTENT_TYPE);
		resource.getContents().add(model);

		standardProfile = UML2Util.load(rset, URI.createURI(UMLResource.STANDARD_PROFILE_URI), UMLPackage.Literals.PROFILE);
		model.applyProfile(standardProfile);
	}

}
