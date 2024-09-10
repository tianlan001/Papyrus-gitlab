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
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation, Bug 486553
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.Activator;
import org.eclipse.papyrus.uml.tools.utils.ImageUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class validate that the icon displayed for classes with applied stereotypes is well than expected.
 */
@PluginResource("/resources/IconsAppliedStereotypes/model.di")
public class IconsStereotypesTest {

	/** Model set rule to have an editing domain. */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/** Profile Name. */
	private static final String PROFILE_NAME = "RootElement"; //$NON-NLS-1$

	/** Class1 name. */
	private static final String CLASS_1_NAME = "Class1"; //$NON-NLS-1$

	/** Class2 name. */
	private static final String CLASS_2_NAME = "Class2"; //$NON-NLS-1$

	/** Class3 name. */
	private static final String CLASS_3_NAME = "Class3"; //$NON-NLS-1$

	/** Class4 name. */
	private static final String CLASS_4_NAME = "Class4"; //$NON-NLS-1$

	/** Stereotype1 Name. */
	private static final String STEREOTYPE_1_NAME = "Stereotype1"; //$NON-NLS-1$

	/** Stereotype2 Name. */
	private static final String STEREOTYPE_2_NAME = "Stereotype2"; //$NON-NLS-1$

	/** Stereotype3 Name. */
	private static final String STEREOTYPE_3_NAME = "Stereotype3"; //$NON-NLS-1$

	/** Stereotype1 element. This stereotype contains an icon. */
	private Stereotype stereotype1 = null;

	/** Stereotype2 element. This stereotype does not contain icon. */
	private Stereotype stereotype2 = null;

	/** Stereotype3 element. This stereotype contains an icon. */
	private Stereotype stereotype3 = null;

	/** UML NamedElement of the Class1. This class contains 2 applied stereotypes (Stereotype1, Stereotype2). */
	private NamedElement class1 = null;

	/** UML NamedElement of the Class2. This class contains 2 applied stereotypes (Stereotype2, Stereotype1). */
	private NamedElement class2 = null;

	/** UML NamedElement of the Class3. This class contains 2 applied stereotypes (Stereotype1, Stereotype3). */
	private NamedElement class3 = null;

	/** UML NamedElement of the Class4. This class contains 2 applied stereotypes (Stereotype3, Stereotype1). */
	private NamedElement class4 = null;

	/** Profile that has been applied. */
	private Profile profile = null;

	/** The Image Registry. */
	private ImageRegistry imageRegistry = null;

	/** The Label Provider Service. */
	// private LabelProviderService labelProviderService = null;

	/** The Label Provider. */
	private ILabelProvider labelProvider = null;

	/**
	 * Initialization of the test cases.
	 */
	@Before
	public void init() {
		// Verify if profile is correctly imported
		Package model = editorFixture.getModel();
		assertNotNull("The model cannot be null", model); //$NON-NLS-1$
		assertFalse("The model must contain ProfileApplications references.", model.getProfileApplications().isEmpty()); //$NON-NLS-1$

		// Retrieve registry
		imageRegistry = Activator.getDefault().getImageRegistry();
		assertNotNull("The Image registry should not be null.", imageRegistry); //$NON-NLS-1$

		// Initialize data (classes, profile, stereotypes ...)
		profile = model.getAppliedProfile(PROFILE_NAME);
		assertEquals("Profile is not the one Expected", PROFILE_NAME, profile.getName()); //$NON-NLS-1$

		class1 = model.getMember(CLASS_1_NAME);
		assertTrue("The element is a Class", class1 instanceof Class); //$NON-NLS-1$
		assertEquals("Element is not the one expected", CLASS_1_NAME, class1.getName()); //$NON-NLS-1$

		class2 = model.getMember(CLASS_2_NAME);
		assertTrue("The element is a Class", class2 instanceof Class); //$NON-NLS-1$
		assertEquals("Element is not the one expected", CLASS_2_NAME, class2.getName()); //$NON-NLS-1$

		class3 = model.getMember(CLASS_3_NAME);
		assertTrue("The element is a Class", class3 instanceof Class); //$NON-NLS-1$
		assertEquals("Element is not the one expected", CLASS_3_NAME, class3.getName()); //$NON-NLS-1$

		class4 = model.getMember(CLASS_4_NAME);
		assertTrue("The element is a Class", class4 instanceof Class); //$NON-NLS-1$
		assertEquals("Element is not the one expected", CLASS_4_NAME, class4.getName()); //$NON-NLS-1$

		stereotype1 = profile.getOwnedStereotype(STEREOTYPE_1_NAME);
		assertEquals("Stereotype is not the one expected", STEREOTYPE_1_NAME, stereotype1.getName()); //$NON-NLS-1$

		stereotype2 = profile.getOwnedStereotype(STEREOTYPE_2_NAME);
		assertEquals("Stereotype is not the one expected", STEREOTYPE_2_NAME, stereotype2.getName()); //$NON-NLS-1$

		stereotype3 = profile.getOwnedStereotype(STEREOTYPE_3_NAME);
		assertEquals("Stereotype is not the one expected", STEREOTYPE_3_NAME, stereotype3.getName()); //$NON-NLS-1$
	}

	/**
	 * Validation of the applied stereotypes of the Class1.
	 */
	@Test
	public void ValidationStereotypesListClass1Test() {
		EList<Stereotype> appliedStereotypes = class1.getAppliedStereotypes();

		// This class must contain 2 stereotypes: Stereotype1, Stereotype2
		List<Stereotype> expectedStereotypes = new ArrayList<Stereotype>();
		expectedStereotypes.add(stereotype1);
		expectedStereotypes.add(stereotype2);

		assertEquals("The stereotypes applied on the class must be the same as the expected stereotypes.", appliedStereotypes, expectedStereotypes); //$NON-NLS-1$
	}

	/**
	 * Validation of the icon of Class1 via the LabelProvider.
	 *
	 * @throws ServiceException
	 */
	@Test
	public void ValidationImageClass1Test() throws ServiceException {
		NamedElement checkedClass = class1;

		LabelProviderService labelProviderService = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, checkedClass);
		assertNotNull("The service should not be null.", labelProviderService); //$NON-NLS-1$

		labelProvider = labelProviderService.getLabelProvider(checkedClass);
		assertNotNull("The Label Provider should not be null.", labelProvider); //$NON-NLS-1$

		// The expected image is the icon of the Stereotype1.
		checkExpectedImage(checkedClass, stereotype1);
	}

	/**
	 * Validation of the applied stereotypes of the Class2.
	 */
	@Test
	public void ValidationStereotypesListClass2Test() {
		EList<Stereotype> appliedStereotypes = class2.getAppliedStereotypes();

		// This class must contain 2 stereotypes: Stereotype2, Stereotype1
		List<Stereotype> expectedStereotypes = new ArrayList<Stereotype>();
		expectedStereotypes.add(stereotype2);
		expectedStereotypes.add(stereotype1);

		assertEquals("The stereotypes applied on the class must be the same as the expected stereotypes.", appliedStereotypes, expectedStereotypes); //$NON-NLS-1$
	}

	/**
	 * Validation of the icon of Class2 via the LabelProvider.
	 *
	 * @throws ServiceException
	 */
	@Test
	public void ValidationImageClass2Test() throws ServiceException {
		NamedElement checkedClass = class2;

		LabelProviderService labelProviderService = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, checkedClass);
		assertNotNull("The service should not be null.", labelProviderService); //$NON-NLS-1$

		labelProvider = labelProviderService.getLabelProvider(checkedClass);
		assertNotNull("The Label Provider should not be null.", labelProvider); //$NON-NLS-1$

		// The expected image is the icon of the Stereotype1.
		checkExpectedImage(checkedClass, stereotype1);
	}

	/**
	 * Validation of the applied stereotypes of the Class3.
	 */
	@Test
	public void ValidationStereotypesListClass3Test() {
		EList<Stereotype> appliedStereotypes = class3.getAppliedStereotypes();

		// This class must contain 2 stereotypes: Stereotype1, Stereotype3
		List<Stereotype> expectedStereotypes = new ArrayList<Stereotype>();
		expectedStereotypes.add(stereotype1);
		expectedStereotypes.add(stereotype3);

		assertEquals("The stereotypes applied on the class must be the same as the expected stereotypes.", appliedStereotypes, expectedStereotypes); //$NON-NLS-1$
	}

	/**
	 * Validation of the icon of Class3 via the LabelProvider.
	 *
	 * @throws ServiceException
	 */
	@Test
	public void ValidationImageClass3Test() throws ServiceException {
		NamedElement checkedClass = class3;

		LabelProviderService labelProviderService = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, checkedClass);
		assertNotNull("The service should not be null.", labelProviderService); //$NON-NLS-1$

		labelProvider = labelProviderService.getLabelProvider(checkedClass);
		assertNotNull("The Label Provider should not be null.", labelProvider); //$NON-NLS-1$

		// The expected image is the icon of the Stereotype1.
		checkExpectedImage(checkedClass, stereotype1);
	}

	/**
	 * Validation of the applied stereotypes of the Class4.
	 */
	@Test
	public void ValidationStereotypesListClass4Test() {
		EList<Stereotype> appliedStereotypes = class4.getAppliedStereotypes();

		// This class must contain 2 stereotypes: Stereotype3, Stereotype1
		List<Stereotype> expectedStereotypes = new ArrayList<Stereotype>();
		expectedStereotypes.add(stereotype3);
		expectedStereotypes.add(stereotype1);

		assertEquals("The stereotypes applied on the class must be the same as the expected stereotypes.", appliedStereotypes, expectedStereotypes); //$NON-NLS-1$
	}

	/**
	 * Validation of the icon of Class4 via the LabelProvider.
	 *
	 * @throws ServiceException
	 */
	@Test
	public void ValidationImageClass4Test() throws ServiceException {
		NamedElement checkedClass = class4;

		LabelProviderService labelProviderService = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, checkedClass);
		assertNotNull("The service should not be null.", labelProviderService); //$NON-NLS-1$

		labelProvider = labelProviderService.getLabelProvider(checkedClass);
		assertNotNull("The Label Provider should not be null.", labelProvider); //$NON-NLS-1$

		// The expected image is the icon of the Stereotype3.
		checkExpectedImage(checkedClass, stereotype3);
	}

	/**
	 * Comparison of the displayed icon and the expected icon for an specific element.
	 *
	 * @param namedElement
	 *            The NameElement to compare.
	 * @param stereotype
	 *            The Stereotype which contains the expected icon.
	 */
	private void checkExpectedImage(final NamedElement namedElement, final Stereotype stereotype) {
		Image image = labelProvider.getImage(namedElement);
		assertNotNull("The image must not be null.", image); //$NON-NLS-1$

		Image expectedImage = getStereotypeIcon(stereotype);
		assertNotNull("The Stereotype must contain an icon.", expectedImage); //$NON-NLS-1$

		assertEquals("The icon of the Element must be the same as the expected icon.", expectedImage, image); //$NON-NLS-1$
	}

	/**
	 * Getting of the image according to the stereotype.
	 *
	 * @param stereotype
	 *            The Stereotype.
	 * @return The expected image.
	 */
	private Image getStereotypeIcon(final Stereotype stereotype) {
		org.eclipse.uml2.uml.Image expectedImageUML = stereotype.getIcons().get(0);
		assertNotNull("The Stereotype must contains an icon.", expectedImageUML); //$NON-NLS-1$

		// Get image id for registry
		String expectedImageId = ImageUtil.getImageId(expectedImageUML);
		assertFalse("The String ID of the expected image must not be empty.", expectedImageId.isEmpty()); //$NON-NLS-1$

		return imageRegistry.get(expectedImageId);
	}
}
