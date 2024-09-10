/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 471337 : Stereotype Display Unit Tests
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.stereotype.display;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This Class tests the Structure of a Nominal Case for the Stereotype Display
 * 
 * @author Céline JANSSENS
 *
 */
@PluginResource("/resources/StereotypeDisplay/StereotypeDisplayModel.di")
public class AppliedStereotypeDisplayStructureTest extends AbstractPapyrusTest {

	/**
	 * Stereotype Name
	 */
	private static final String STEREO_A = "stereoA";//$NON-NLS-1$

	/**
	 * First Property Name
	 */
	private static final String PROPERTY1 = "propertyA1";//$NON-NLS-1$

	/**
	 * Second Property Name
	 */
	private static final String PROPERTY2 = "propertyA2";//$NON-NLS-1$

	/**
	 * Diagram Name
	 */
	private static final String CLASS_DIAGRAM_NAME = "Class Diagram"; //$NON-NLS-1$

	/**
	 * Profile Name
	 */
	private static final String PROFILE_NAME = "NominalTestProfile";//$NON-NLS-1$

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Class1";//$NON-NLS-1$


	/** Model set rule to have an editing domain. */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/**
	 * UML Element of the Class
	 */
	private Element element = null;

	/**
	 * One of the applied Stereotype
	 */
	private Stereotype stereotype = null;

	/**
	 * Notation view of the class editPart
	 */
	private View classView = null;

	/**
	 * Profile that has been applied
	 */
	private Profile profile = null;

	/**
	 * Transactional Domain
	 */
	private TransactionalEditingDomain domain = null;

	/**
	 * UML Property of the stereotype "stereoA"
	 */
	private Property prop1 = null;
	private Property prop2 = null;

	/**
	 * Helper for Stereotype Display
	 */
	private StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();
	/**
	 * Helper for the Unit Test about Stereotype Display
	 */
	private AppliedStereotypeDisplayTestUtils testHelper = AppliedStereotypeDisplayTestUtils.getInstance();


	/**
	 * Initialization of members and diagram
	 */
	@Before
	public void initialisation() {
		// Verify if profile is correctly imported
		Package model = editorFixture.getModel();
		Assert.assertNotNull("The model cannot be null", model);
		Assert.assertFalse(model.getProfileApplications().isEmpty());

		// Initialize data for test case
		element = model.getMember(CLASS_NAME);
		Assert.assertTrue("The element is a UML Named Element", element instanceof NamedElement);
		Assert.assertEquals("Element is not the one expected", CLASS_NAME, ((NamedElement) element).getName());

		profile = model.getAppliedProfile(PROFILE_NAME);
		Assert.assertEquals("Profile is not the one Expected", PROFILE_NAME, profile.getName());

		domain = editorFixture.getEditingDomain();
		Assert.assertNotNull("Domain should not be null", domain);

		stereotype = profile.getOwnedStereotype(STEREO_A);
		Assert.assertEquals("Stereotype is not the one expected", STEREO_A, stereotype.getName());

		// Open the diagram
		final Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), CLASS_DIAGRAM_NAME);
		Assert.assertEquals("Current opened diagram is not the expected one", CLASS_DIAGRAM_NAME, mainDiagram.getName());
		editorFixture.getPageManager().openPage(mainDiagram);
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.flushDisplayEvents();

		// Get the class view
		classView = DiagramUtils.findShape(mainDiagram, CLASS_NAME);
		Assert.assertNotNull("Class view not present", classView);

		// Check that the stereotype is applied on the element.
		Stereotype appliedStereotype = element.getAppliedStereotype(stereotype.getQualifiedName());
		Assert.assertEquals("stereoA is applied on the element", stereotype, appliedStereotype);


		// Check that the stereotype Contains the Expected Properties.
		List<Property> properties = stereotype.getAllAttributes();

		prop1 = stereotype.getAttribute(PROPERTY1, null);
		prop2 = stereotype.getAttribute(PROPERTY2, null);

		Assert.assertTrue("The Properties contains PropertyA1", properties.contains(prop1));
		Assert.assertTrue("The Properties contains PropertyA2", properties.contains(prop2));

	}

	/**
	 * Test the profile application of the Diagram
	 */
	@Test
	public void testProfileApplication() {

		// Check if the profile name is the one expected
		Assert.assertEquals("The profile is not the one expected ", PROFILE_NAME, profile.getName());

		// Check if the applied Profile is the good one
		Assert.assertTrue("The Container has to be a Package", element.eContainer() instanceof org.eclipse.uml2.uml.Package);
		Profile appliedProfile = ((org.eclipse.uml2.uml.Package) element.eContainer()).getAppliedProfile(PROFILE_NAME);
		Assert.assertNotNull("The appliedProfile should not be null", appliedProfile);
		Assert.assertEquals("The profile is not the one expected", profile, appliedProfile);
	}

	/**
	 * Test the label structure into the notation model and the depth of this label
	 */
	@Test
	public void testStereotypeLabelStructure() {

		// Check that at least one label has been found for the specific Stereotype
		View label = helper.getStereotypeLabel(classView, stereotype);
		Assert.assertNotNull("Label should not be null", label);
		Assert.assertTrue("The Label must be visible", label.isVisible());

		// Check that there is as many label as appliedStereotypes.
		List<View> list = testHelper.getAllLabel(classView);
		Assert.assertEquals("There should have as many Labels as applied Stereotypes", element.getAppliedStereotypes().size(), list.size());

		// Test the depth of the Label
		testStereotypeLabelDepth(label);
	}

	/**
	 * Test the name of the Label depending on the depth
	 * 
	 * @param label
	 *            The Label View of the related Stereotype "stereoA"
	 */
	private void testStereotypeLabelDepth(View label) {

		// test the method getDepth
		Assert.assertTrue("Label is a decoration Node", label instanceof DecorationNode);
		String depth = helper.getDepth((DecorationNode) label);
		Assert.assertEquals("The Depth is not the one expected", "none", depth);

		// test the method getMinimumDepth
		String minDepth = helper.getMinimumDepthName(stereotype.getQualifiedName());
		Assert.assertEquals("The minimum depth is not the one expected ", "stereoA", minDepth);

		// test the method getStereotypeNameWithDepth with different value
		String fullName = helper.getStereotypeNameWithDepth(stereotype.getQualifiedName(), "full");
		Assert.assertEquals("Label with depth full is not the one expected", PROFILE_NAME + "::" + STEREO_A, fullName);
		String noneDepthName = helper.getStereotypeNameWithDepth(stereotype.getQualifiedName(), "none");
		Assert.assertEquals("Label with depth none is not the one expected", "stereoA", noneDepthName);
		String minusName = helper.getStereotypeNameWithDepth(stereotype.getQualifiedName(), "-1");
		Assert.assertEquals("Label with depth -1 is not the one expected", STEREO_A, minusName);
		String outOfBoundsDepth = helper.getStereotypeNameWithDepth(stereotype.getQualifiedName(), "25");
		Assert.assertEquals("Label with depth 25 is not the one expected", PROFILE_NAME + "::" + STEREO_A, outOfBoundsDepth);
		String outOfBoundsDepth2 = helper.getStereotypeNameWithDepth(stereotype.getQualifiedName(), "-17");
		Assert.assertEquals("Label with depth -17 is not the one expected", STEREO_A, outOfBoundsDepth2);

	}

	/**
	 * Test the structure of the Brace View
	 */
	@Test
	public void testStereotypeBraceStructure() {

		// Check that there is a Brace view for the Stereotype A
		View brace = helper.getStereotypeBraceCompartment(classView, stereotype);
		Assert.assertNotNull("Brace should not be null", brace);
		Assert.assertFalse("The Brace properties must not be visible", brace.isVisible());

		// Check that there is as many Brace compartment as appliedStereotypes.
		List<View> list = testHelper.getAllBraceCompartment(classView);
		Assert.assertEquals("There should have as many Labels as applied Stereotypes", element.getAppliedStereotypes().size(), list.size());
		Assert.assertEquals("The Type of the Brace  View is not the one expected", "StereotypeBrace", brace.getType());


	}

	/**
	 * Test the Brace properties Structure
	 */
	@Test
	public void testStereotypeBracePropertyStructure() {
		// Check that there is a Brace view for the Stereotype A
		View brace = helper.getStereotypeBraceCompartment(classView, stereotype);
		Assert.assertNotNull("Brace should not be null", brace);

		// Check that there is a Property View related to the Prop1 property
		View propertyViewInBrace = helper.getStereotypePropertyInBrace(classView, stereotype, prop1);
		Assert.assertNotNull("Property View In Brace should not be null", propertyViewInBrace);
		Assert.assertTrue("PropertyView must be visible by default", propertyViewInBrace.isVisible());
		Assert.assertEquals("The Type of the Brace Property View is not the one expected", "StereotypePropertyBrace", propertyViewInBrace.getType());


		// Check that the property is a Brace child.
		Object parent = propertyViewInBrace.eContainer();
		Assert.assertEquals("The Property should be contained into the Brace View", brace, parent);
		Assert.assertFalse("The Property should be Transcient by default ", brace.getPersistedChildren().contains(propertyViewInBrace));
		Assert.assertTrue("The Property should be Transcient by default ", brace.getTransientChildren().contains(propertyViewInBrace));

	}


	/**
	 * Test The structure of the Compartment
	 */
	@Test
	public void testStereotypeCompartmentStructure() {

		// Check that there is a Compartment view for the Stereotype A
		View compartment = helper.getStereotypeCompartment(classView, stereotype);
		Assert.assertNotNull("Compartment should not be null", compartment);
		Assert.assertFalse("The Brace properties must not be visible", compartment.isVisible());

		// Check that there is as many compartment as appliedStereotypes.
		List<View> list = testHelper.getAllCompartment(classView);
		Assert.assertEquals("There should have as many Labels as applied Stereotypes", element.getAppliedStereotypes().size(), list.size());
		Assert.assertEquals("The Type of the Compartment  View is not the one expected", "StereotypeCompartment", compartment.getType());

	}


	/**
	 * Test that the Structure of the Property into the Compartment is correct
	 */
	@Test
	public void testStereotypePropertyStructure() {
		// Check that there is a compartment view for the Stereotype A
		View compartment = helper.getStereotypeCompartment(classView, stereotype);
		Assert.assertNotNull("Compartment should not be null", compartment);

		// Check that there is a Property View related to the Prop1 property
		View propertyView = helper.getStereotypePropertyInCompartment(classView, stereotype, prop1);
		Assert.assertNotNull("Property View should not be null", propertyView);
		Assert.assertTrue("Property View must be visible by default", propertyView.isVisible());
		Assert.assertEquals("The Type of the Property  View is not the one expected", "StereotypeProperty", propertyView.getType());

		// Check that the property is a compartment child.
		Object parent = propertyView.eContainer();
		Assert.assertEquals("The Property should be contained into the Compartment View", compartment, parent);
		Assert.assertFalse("The Property should be Transcient by default ", compartment.getPersistedChildren().contains(propertyView));
		Assert.assertTrue("The Property should be Transcient by default ", compartment.getTransientChildren().contains(propertyView));

	}


	/**
	 * Test the structure of the Comment
	 */
	@Test
	public void testStereotypeCommentStructure() {

		// Check that there is a Comment view for the Stereotype A
		View comment = helper.getStereotypeComment(classView);
		Assert.assertNotNull("Comment should not be null", comment);
		Assert.assertFalse("The Brace properties must not be visible", comment.isVisible());

		// Check that there is only one comment by element
		List<View> list = testHelper.getAllComment(classView);
		Assert.assertEquals("There should have as many Labels as applied Stereotypes", 1, list.size());
		Assert.assertEquals("The Type of the comment  View is not the one expected", "StereotypeComment", comment.getType());

		// Check that there is only 1 comment for the diagram.
		List<View> listOfAllComment = testHelper.getAllComment(classView.getDiagram());
		Assert.assertNotNull(listOfAllComment);
		Assert.assertEquals(1, listOfAllComment.size());


		// Check that the element on which the comment is based exists.
		for (View view : listOfAllComment) {

			EObject baseElement = NotationUtils.getEObjectValue(view, StereotypeDisplayConstant.STEREOTYPE_COMMENT_RELATION_NAME, null);
			Assert.assertNotNull(baseElement);
			Assert.assertEquals(0, view.getSourceEdges().size());
			Assert.assertEquals(1, view.getTargetEdges().size());
		}

	}

	/**
	 * Test that the Structure of the Property into the Compartment is correct
	 */
	@Test
	public void testStereotypeCompartmentInCommentStructure() {
		// Check that there is a Comment view for the Stereotype A
		View comment = helper.getStereotypeComment(classView);
		Assert.assertNotNull("Comment should not be null", comment);
		Assert.assertFalse("The Brace properties must not be visible", comment.isVisible());

		// Check that there is a Compartment view for the Stereotype A
		View compartment = helper.getStereotypeCompartment(comment, stereotype);
		Assert.assertNotNull("Compartment should not be null", compartment);
		Assert.assertFalse("The Brace properties must not be visible", compartment.isVisible());

		// Check that there is as many compartment as appliedStereotypes.
		List<View> list = testHelper.getAllCompartment(classView);
		Assert.assertEquals("There should have as many Labels as applied Stereotypes", element.getAppliedStereotypes().size(), list.size());
		Assert.assertEquals("The Type of the Compartment  View is not the one expected", "StereotypeCompartment", compartment.getType());

	}

	/**
	 * Test that the Structure of the Property into the Compartment is correct
	 */
	@Test
	public void testStereotypePropertyInCommentStructure() {
		// Check that there is a compartment view for the Stereotype A
		View comment = helper.getStereotypeComment(classView);
		View compartment = helper.getStereotypeCompartment(comment, stereotype);
		Assert.assertNotNull("Compartment should not be null", compartment);

		// Check that there is a Property View related to the Prop1 property
		View propertyView = helper.getStereotypePropertyInComment(classView, stereotype, prop1);
		Assert.assertNotNull("Property View should not be null", propertyView);
		Assert.assertTrue("Property View must be visible by default", propertyView.isVisible());
		Assert.assertEquals("The Type of the Property  View is not the one expected", "StereotypeProperty", propertyView.getType());

		// Check that the property is a compartment child.
		Object parent = propertyView.eContainer();
		Assert.assertEquals("The Property should be contained into the Compartment View", compartment, parent);
		Assert.assertFalse("The Property should be Transcient by default ", compartment.getPersistedChildren().contains(propertyView));
		Assert.assertTrue("The Property should be Transcient by default ", compartment.getTransientChildren().contains(propertyView));

	}


}
