/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.tests.tests;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.nattable.tests.Activator;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test {@link UMLTableUtils} methods with JUnit tests.
 */
public class UMLTableUtilsTest extends AbstractPapyrusTest {

	/**
	 * The profile root element name.
	 */
	protected final static String PROFILE_ROOT_ELEMENT_NAME = "ProfileRootElement"; //$NON-NLS-1$

	/**
	 * The name of the package in the profile root element.
	 */
	protected final static String PACKAGE_IN_PROFILE_NAME = "MyPackage"; //$NON-NLS-1$

	/**
	 * The name of the stereotype.
	 */
	protected final static String STEREOTYPE_NAME = "MyStereotype"; //$NON-NLS-1$

	/**
	 * The name of the first stereotype property.
	 */
	protected final static String FIRST_PROPERTY_NAME = "MyProperty1"; //$NON-NLS-1$

	/**
	 * The name of the second stereotype property.
	 */
	protected final static String SECOND_PROPERTY_NAME = "MyProperty2"; //$NON-NLS-1$

	/**
	 * The house keeper.
	 */
	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();
	
	/**
	 * The resource set containing the needed resources for the test.
	 */
	private ResourceSet resourceSet;
	
	/**
	 * Reference to the created class in the root element.
	 */
	protected org.eclipse.uml2.uml.Class createdClass;

	/**
	 * The reference to the first created stereotype property.
	 */
	protected Property firstPropertyInStereotype;

	/**
	 * The reference to the second created stereotype property.
	 */
	protected Property secondPropertyInStereotype;

	/**
	 * Constructor.
	 */
	public UMLTableUtilsTest() {
		super();
	}

	/**
	 * This allows to create the models needed for the JUnit tests.
	 * 
	 * @throws IOException 
	 */
	@Before
	public void initModel() throws IOException {
		
		resourceSet = houseKeeper.createResourceSet();
		EMFHelper.loadEMFModel(resourceSet, URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/UMLTableUtils/model.profile.di", true)); //$NON-NLS-1$
		final Resource umlProfileModel = resourceSet.getResource(URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/UMLTableUtils/model.profile.uml", true), true); //$NON-NLS-1$

		//we inits the field of this class
		final Profile profileRootElement = (Profile) umlProfileModel.getContents().get(0);
		
		// Create the profile model
		final Package packageInProfile = UMLFactory.eINSTANCE.createPackage();
		packageInProfile.setName(PACKAGE_IN_PROFILE_NAME);
		profileRootElement.getPackagedElements().add(packageInProfile);
		final Stereotype stereotypeInPackage = UMLFactory.eINSTANCE.createStereotype();
		stereotypeInPackage.setName(STEREOTYPE_NAME);
		packageInProfile.getOwnedStereotypes().add(stereotypeInPackage);
		firstPropertyInStereotype = UMLFactory.eINSTANCE.createProperty();
		firstPropertyInStereotype.setName(FIRST_PROPERTY_NAME);
		secondPropertyInStereotype = UMLFactory.eINSTANCE.createProperty();
		secondPropertyInStereotype.setName(SECOND_PROPERTY_NAME);
		stereotypeInPackage.getOwnedAttributes().add(firstPropertyInStereotype);
		stereotypeInPackage.getOwnedAttributes().add(secondPropertyInStereotype);

		EMFHelper.loadEMFModel(resourceSet, URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/UMLTableUtils/model.di", true)); //$NON-NLS-1$
		final Resource umlModel = resourceSet.getResource(URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/UMLTableUtils/model.uml", true), true); //$NON-NLS-1$
		
		// Create another model
		final Model rootElement = (Model) umlModel.getContents().get(0);
		ProfileApplication createdProfileApplication = UMLFactory.eINSTANCE.createProfileApplication();
		createdProfileApplication.setAppliedProfile(profileRootElement);
		rootElement.getProfileApplications().add(createdProfileApplication);
		createdClass = UMLFactory.eINSTANCE.createClass();
		createdClass.setName("MyClass"); //$NON-NLS-1$
		rootElement.getPackagedElements().add(createdClass);
	}

	/**
	 * This allows to test the {@link UMLTableUtils#getRealStereotypeProperty(org.eclipse.emf.ecore.EObject, String)} function.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetRealStereotypeProperty() throws Exception {
		// Test the getRealStereotypeProperty method for the first property
		final StringBuilder firstPropertyQN = new StringBuilder(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX);
		firstPropertyQN.append(PROFILE_ROOT_ELEMENT_NAME);
		firstPropertyQN.append(NamedElement.SEPARATOR);
		firstPropertyQN.append(PACKAGE_IN_PROFILE_NAME);
		firstPropertyQN.append(NamedElement.SEPARATOR);
		firstPropertyQN.append(STEREOTYPE_NAME);
		firstPropertyQN.append(NamedElement.SEPARATOR);
		firstPropertyQN.append(FIRST_PROPERTY_NAME);
		Assert.assertEquals("The retrieve property from the UMLTableUtils.getRealStereotypeProperty function is not the correct one.", firstPropertyInStereotype, 
				UMLTableUtils.getRealStereotypeProperty(createdClass, firstPropertyQN.toString())); //$NON-NLS-1$

		// Test the getRealStereotypeProperty method for the second property
		final StringBuilder secondPropertyQN = new StringBuilder(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX);
		secondPropertyQN.append(PROFILE_ROOT_ELEMENT_NAME);
		secondPropertyQN.append(NamedElement.SEPARATOR);
		secondPropertyQN.append(PACKAGE_IN_PROFILE_NAME);
		secondPropertyQN.append(NamedElement.SEPARATOR);
		secondPropertyQN.append(STEREOTYPE_NAME);
		secondPropertyQN.append(NamedElement.SEPARATOR);
		secondPropertyQN.append(SECOND_PROPERTY_NAME);
		Assert.assertEquals("The retrieve property from the UMLTableUtils.getRealStereotypeProperty function is not the correct one.", secondPropertyInStereotype, 
				UMLTableUtils.getRealStereotypeProperty(createdClass, secondPropertyQN.toString())); //$NON-NLS-1$
	}

}
