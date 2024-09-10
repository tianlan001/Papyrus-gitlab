/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine Janssens (All4Tec) celine.janssens@all4tec.net - Bug 472342
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.tools.commands.ApplyStereotypeCommand;
import org.eclipse.papyrus.uml.tools.commands.UnapplyStereotypeCommand;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 *
 * This Abstract class has to be extended to test application and unapplication of stereotypes.
 *
 * @author Céline JANSSENS
 *
 */
@SuppressWarnings("nls")
public abstract class AbstractAppliedStereotypeDisplayTest extends AbstractPapyrusTest {
	/**
	 * Stereotype Name
	 */
	protected static final String STEREO_A = "stereoA";//$NON-NLS-1$

	/**
	 * Stereotype Name
	 */
	protected static final String STEREO_B = "stereoB";//$NON-NLS-1$

	/**
	 * Stereotype Name
	 */
	protected static final String STEREO_C = "stereoC";//$NON-NLS-1$

	/**
	 * First Property Name
	 */
	protected static final String PROPERTY1 = "propertyA1";//$NON-NLS-1$

	/**
	 * Second Property Name
	 */
	protected static final String PROPERTY2 = "propertyA2";//$NON-NLS-1$

	/**
	 * Attribute Name
	 */
	protected static final String ATTRIBUTE_NAME = "Attribute1";//$NON-NLS-1$

	/**
	 * Operation Name
	 */
	protected static final String OPERATION_NAME = "Operation1";//$NON-NLS-1$

	/**
	 * Package Name
	 */
	protected static final String PACKAGE_NAME = "Package1";//$NON-NLS-1$

	/**
	 * Association Name
	 */
	protected static final String ASSOCIATION_NAME = "Association1";//$NON-NLS-1$

	/**
	 * Interface Name
	 */
	protected static final String INTERFACE_NAME = "Interface1";//$NON-NLS-1$

	/**
	 * Enumeration Name
	 */
	protected static final String ENUMERATION_NAME = "Enumeration1";//$NON-NLS-1$

	/**
	 * Enumeration Literal Name
	 */
	protected static final String ENUMERATION_ITEM_NAME = "EnumerationLiteral1";//$NON-NLS-1$

	/**
	 * Duration Observation Name
	 */
	protected static final String OBSERVATION_NAME = "DurationObservation1";//$NON-NLS-1$

	/**
	 * Diagram Name
	 */
	protected static final String CLASS_DIAGRAM_NAME = "Class Diagram"; //$NON-NLS-1$

	/**
	 * Profile Name
	 */
	protected static final String PROFILE_NAME = "NominalTestProfile";//$NON-NLS-1$

	/**
	 * Class name
	 */
	protected static final String CLASS_NAME = "Class1";//$NON-NLS-1$




	/** Model set rule to have an editing domain. */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/**
	 * UML Element of the Class
	 */
	protected Element element = null;

	// protected IPapyrusWrappingLabel stereotypeLabel = null;

	protected EditPart editPart = null;
	/**
	 * One of the applied Stereotype
	 */
	protected Stereotype stereotypeA = null;

	/**
	 * One of the applied Stereotype
	 */
	protected Stereotype stereotypeC = null;

	/**
	 * One of the applied Stereotype
	 */
	protected Stereotype stereotypeB = null;

	/**
	 * The main diagram ClassDiagram
	 */
	protected Diagram mainDiagram = null;



	/**
	 * Notation view of the class editPart
	 */
	protected View elementView = null;

	/**
	 * Profile that has been applied
	 */
	protected Profile profile = null;

	/**
	 * Transactional Domain
	 */
	protected TransactionalEditingDomain domain = null;

	/**
	 * UML Property of the stereotype "stereoA"
	 */
	protected Property prop1 = null;
	protected Property prop2 = null;

	/**
	 * Model
	 */
	protected Package model = null;

	/**
	 * Helper for Stereotype Display
	 */
	protected StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	/**
	 * Helper for the Unit Test about Stereotype Display
	 */
	protected AppliedStereotypeDisplayTestUtils testHelper = AppliedStereotypeDisplayTestUtils.getInstance();

	/**
	 * Initialization and tests of Objects
	 */
	@Before
	public void initialisation() {
		// Verify if profile is correctly imported
		model = editorFixture.getModel();
		Assert.assertNotNull("The model cannot be null", model);
		Assert.assertFalse("These is no applied profile on the model", model.getProfileApplications().isEmpty());


		profile = model.getAppliedProfile(PROFILE_NAME);
		Assert.assertEquals("Profile is not the one Expected", PROFILE_NAME, profile.getName());

		domain = editorFixture.getEditingDomain();
		Assert.assertNotNull("Domain should not be null", domain);


		stereotypeA = profile.getOwnedStereotype(STEREO_A);
		Assert.assertNotNull("Stereotype is not the one expected", stereotypeA);
		Assert.assertEquals("Stereotype is not the one expected", STEREO_A, stereotypeA.getName());

		stereotypeB = profile.getOwnedStereotype(STEREO_B);
		Assert.assertNotNull("Stereotype is not the one expected", stereotypeB);
		Assert.assertEquals("Stereotype is not the one expected", STEREO_B, stereotypeB.getName());

		stereotypeC = profile.getOwnedStereotype(STEREO_C);
		Assert.assertNotNull("Stereotype is not the one expected", stereotypeC);
		Assert.assertEquals("Stereotype is not the one expected", STEREO_C, stereotypeC.getName());



		// Open the diagram
		mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), CLASS_DIAGRAM_NAME);
		Assert.assertEquals("Current opened diagram is not the expected one", CLASS_DIAGRAM_NAME, mainDiagram.getName());


		editorFixture.getPageManager().openPage(mainDiagram);
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.flushDisplayEvents();


		// Check that the stereotype Contains the Expected Properties.
		List<Property> properties = stereotypeA.getAllAttributes();

		// Assign properties
		prop1 = stereotypeA.getAttribute(PROPERTY1, null);
		prop2 = stereotypeA.getAttribute(PROPERTY2, null);

		Assert.assertTrue("The Properties contains PropertyA1", properties.contains(prop1));
		Assert.assertTrue("The Properties contains PropertyA2", properties.contains(prop2));


	}

	/**
	 * Test the Label of the element
	 *
	 * @param expectedLabel
	 *            Expected text of the Label
	 */
	protected abstract void testLabel(String expectedLabel);

	/**
	 * Unapply and check the applied stereotypes
	 *
	 * @param stereoList
	 *            The collection of the Stereotypes to unapply.
	 */
	protected void unapplyStereotype(final Collection<Stereotype> stereoList) {

		// UnApply stereotype
		domain.getCommandStack().execute(new UnapplyStereotypeCommand(element, stereoList, domain));

		for (Stereotype stereo : stereoList) {
			Stereotype appliedStereotype = element.getAppliedStereotype(stereo.getQualifiedName());
			Assert.assertNull(NLS.bind("Stereotype {0} is still applied on the element.", stereo.getQualifiedName()), appliedStereotype);
		}

		editPart.refresh();

	}


	/**
	 * Apply and check the applied stereotype
	 *
	 * @param stereoList
	 *            The collection of the Stereotypes to apply.
	 */
	protected void applyStereotype(final Collection<Stereotype> stereoList) {
		// Apply stereotypes
		domain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereoList, domain));

		for (Stereotype stereo : stereoList) {
			// Check that the stereotype is applied on the element.
			Stereotype appliedStereotype = element.getAppliedStereotype(stereo.getQualifiedName());
			Assert.assertNotNull(NLS.bind("Stereotype {0} is not applied on the element", stereo.getQualifiedName()), appliedStereotype);

		}

		editPart.refresh();
	}

	/**
	 * Test if there is more than the expected number of Comment View into the Diagram.
	 * This is to detect the Orphan Comment view.
	 *
	 * @param expectedNumberOfCommentView
	 *            The expected number of Comment View into the entire diagram
	 *
	 */
	protected void testOrphanComment(final int expectedNumberOfCommentView) {
		List<View> listOfAllComment = testHelper.getAllComment(mainDiagram);
		Assert.assertNotNull(listOfAllComment);
		Assert.assertEquals("Only 1 comment is present in the entire Diagram", expectedNumberOfCommentView, listOfAllComment.size());
	}

	/**
	 * Test that the number of Created Views are the same as the number of applied Stereotype
	 */
	protected void testStructure(final int expectedNumbersOfLabels, final int expectedNumbersOfCompartment, final int expectedNumbersOfBrace, final int expectedNumbersOfComment) {

		// Check that there is as many label as appliedStereotypes.
		List<View> labelList = testHelper.getAllLabel(elementView);
		Assert.assertNotNull("The label list is null", labelList);
		Assert.assertEquals("There should have as many Labels as applied Stereotypes", expectedNumbersOfLabels, labelList.size());

		// Check that there is as many compartment as appliedStereotypes.
		List<View> compartmentsList = testHelper.getAllCompartment(elementView);
		Assert.assertNotNull("The compartments list is null", compartmentsList);
		Assert.assertEquals("There should have as many Comaprtment as applied Stereotypes", expectedNumbersOfCompartment, compartmentsList.size());

		// Check that there is as many brace as appliedStereotypes.
		List<View> bracesList = testHelper.getAllBraceCompartment(elementView);
		Assert.assertNotNull("The braces list is null", bracesList);
		Assert.assertEquals("There should have as many Brace Compartments as applied Stereotypes", expectedNumbersOfBrace, bracesList.size());

		// Check that there is only one comment by view.
		List<View> comments = testHelper.getAllComment(elementView);
		Assert.assertNotNull("The comments list is null", comments);

		Assert.assertEquals("There should have only one Comment shape by View", expectedNumbersOfComment, comments.size());
	}

	/**
	 * Set the different elements required for the specific test. ( Class, Enumeration, Package, ...)
	 *
	 * @param objectName
	 *            The name of the object to be retrieved
	 * @param classType
	 *            The class of the object to be retrieved
	 */
	protected void setElementAndTest(final String objectName, final Class<? extends NamedElement> classType) {

		// Initialize data for test case

		// Calculate EditPart
		editPart = editorFixture.findEditPart(objectName, classType);
		Assert.assertNotNull("The found editPart is null", editPart);


		// Get the Enumeration view
		Assert.assertTrue(NLS.bind("The model of {0} should be a View", objectName), editPart.getModel() instanceof View);
		elementView = (View) editPart.getModel();
		Assert.assertNotNull(NLS.bind("The view of the edit Part {0} is not present", objectName), elementView);

		// Get the Semantic Element
		Assert.assertTrue(elementView.getElement() instanceof Element);
		element = (Element) elementView.getElement();
		Assert.assertTrue("The element is a UML Named Element", element instanceof NamedElement);
		Assert.assertEquals("Element is not the one expected", objectName, ((NamedElement) element).getName());

	}

	/**
	 *
	 * @param stereotypeName
	 *            the name of the stereotype
	 * @return
	 * 		the name of the stereotype between << and >>
	 */
	protected final String getStereotypeNameWithItsDelimiters(final String... stereotypeName) {
		final StringBuilder builder = new StringBuilder(String.valueOf(Activator.ST_LEFT));
		for (int i = 0; i < stereotypeName.length; i++) {
			if (i != 0) {
				builder.append(", ");
			}
			builder.append(stereotypeName[i]);
		}

		builder.append(String.valueOf(Activator.ST_RIGHT));
		return builder.toString();
	}

}
