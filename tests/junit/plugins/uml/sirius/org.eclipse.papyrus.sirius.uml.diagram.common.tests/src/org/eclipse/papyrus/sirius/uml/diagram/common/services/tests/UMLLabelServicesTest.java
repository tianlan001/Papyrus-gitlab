/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.services.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.UMLLabelServices;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link UMLLabelServices} service.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class UMLLabelServicesTest extends AbstractServicesTest {

	private static final String E_CLASS = "EClass"; //$NON-NLS-1$

	private static final String MODEL_NAME = "myModel"; //$NON-NLS-1$


	/**
	 * Multiplicity 1 on connector end.
	 */
	private static final String MULTIPLICITY_END = "1"; //$NON-NLS-1$

	/**
	 * Name of a {@link Class}.
	 */
	private static final String CLASS_NAME = "myClass"; //$NON-NLS-1$

	private static final String PROPERTY1 = "property1"; //$NON-NLS-1$

	private static final String CLASS_2 = "class2"; //$NON-NLS-1$

	private static final String CLASS_1 = "class1"; //$NON-NLS-1$

	private static final String EDITED_BODY = "editedBody"; //$NON-NLS-1$

	private static final String COMMENT_BODY = "commentBody"; //$NON-NLS-1$

	private static final String PROPERTY2 = "property2"; //$NON-NLS-1$

	private static final String EXPECTED_PROPERTY_LABEL = "+ " + PROPERTY1 + ": " + CLASS_1 + " [1]"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

	private static final String EXPECTED_PROPERTY_2_LABEL = "+ " + PROPERTY2 + ": " + CLASS_2 + " [1]"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	private static final String CLASS_QUALIFIED_NAME = MODEL_NAME + "::" + CLASS_NAME; //$NON-NLS-1$

	private static final String EXPECTED_CLASS_NAME_STEREOTYPE = UMLCharacters.ST_LEFT + E_CLASS + UMLCharacters.ST_RIGHT;

	/**
	 * Service used to display label.
	 */
	private UMLLabelServices umlLabelServices;

	/**
	 * The diagram used to render labels.
	 */
	private DDiagram dDiagram;



	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.umlLabelServices = new UMLLabelServices();
		this.dDiagram = DiagramFactory.eINSTANCE.createDDiagram();
	}

	/**
	 * Tests that the label of a {@link Class} is correctly displayed.
	 */
	@Test
	public void testRenderLabelFromClass() {
		Class class1 = create(Class.class);
		class1.setName(CLASS_NAME);
		String label = umlLabelServices.renderLabel(class1, dDiagram);
		assertEquals(CLASS_NAME, label);
	}

	/**
	 * Tests that the label of a {@link Class} is correctly displayed inline.
	 */
	@Test
	public void testRenderLabelInlineFromClass() {
		Class class1 = create(Class.class);
		class1.setName(CLASS_NAME);
		String label = umlLabelServices.renderLabelInline(class1, dDiagram);
		assertEquals(CLASS_NAME, label);
	}

	/**
	 * Tests the the labels of a {@link Class} are correctly displayed according to the {@link DDiagram} activated layers.
	 */
	@Test
	public void testRenderLabelWithDiagramFromClass() {

		Profile eCoreProfile = getECoreProfile();

		Model model = create(Model.class);
		model.setName(MODEL_NAME);
		Class class1 = create(Class.class);
		class1.setName(CLASS_NAME);
		model.getPackagedElements().add(class1);
		model.applyProfile(eCoreProfile);
		Stereotype stereotype = eCoreProfile.getOwnedStereotype(E_CLASS);
		class1.applyStereotype(stereotype);

		Layer stereotypesLayer = DescriptionFactory.eINSTANCE.createLayer();
		stereotypesLayer.setName("AppliedStereotypeLayer"); //$NON-NLS-1$
		Layer qualifiedNameLayer = DescriptionFactory.eINSTANCE.createLayer();
		qualifiedNameLayer.setName("QualifiedNameLayer"); //$NON-NLS-1$
		Layer otherLayer = DescriptionFactory.eINSTANCE.createLayer();
		otherLayer.setName("otherLayer"); //$NON-NLS-1$

		// Check that no qualified name or stereotype are displayed when no related layer is activated.
		assertEquals(CLASS_NAME, umlLabelServices.renderLabel(class1, dDiagram));

		// Check that the stereotype is displayed when the layer is activated.
		dDiagram.getActivatedLayers().add(stereotypesLayer);
		assertEquals(EXPECTED_CLASS_NAME_STEREOTYPE + UMLCharacters.EOL + CLASS_NAME, umlLabelServices.renderLabel(class1, dDiagram));

		// Check that the qualified name and the stereotype are displayed when the corresponding layer are activated.
		dDiagram.getActivatedLayers().add(qualifiedNameLayer);
		assertEquals(EXPECTED_CLASS_NAME_STEREOTYPE + UMLCharacters.EOL + CLASS_QUALIFIED_NAME, umlLabelServices.renderLabel(class1, dDiagram));

		// Check that the qualified name is displayed when the layer is activated.
		dDiagram.getActivatedLayers().remove(stereotypesLayer);
		assertEquals(CLASS_QUALIFIED_NAME, umlLabelServices.renderLabel(class1, dDiagram)); // $NON-NLS-1$


		// Check that no qualified name or stereotype are displayed when no related layer is activated.
		dDiagram.getActivatedLayers().remove(qualifiedNameLayer);
		assertEquals(CLASS_NAME, umlLabelServices.renderLabel(class1, dDiagram));

		dDiagram.getActivatedLayers().add(otherLayer);
		assertEquals(CLASS_NAME, umlLabelServices.renderLabel(class1, dDiagram));
	}

	/**
	 * Tests that a label of a {@link Class} is correctly displayed inline according to the {@link DDiagram} activated layers.
	 */
	@Test
	public void testRenderLabelInlineWithDiagramFromClass() {
		Profile eCoreProfile = getECoreProfile();

		Model model = create(Model.class);
		model.setName(MODEL_NAME);
		Class class1 = create(Class.class);
		class1.setName(CLASS_NAME);
		model.getPackagedElements().add(class1);
		model.applyProfile(eCoreProfile);
		Stereotype stereotype = eCoreProfile.getOwnedStereotype(E_CLASS);
		class1.applyStereotype(stereotype);

		Layer stereotypesLayer = DescriptionFactory.eINSTANCE.createLayer();
		stereotypesLayer.setName("AppliedStereotypeLayer"); //$NON-NLS-1$
		Layer qualifiedNameLayer = DescriptionFactory.eINSTANCE.createLayer();
		qualifiedNameLayer.setName("QualifiedNameLayer"); //$NON-NLS-1$
		Layer otherLayer = DescriptionFactory.eINSTANCE.createLayer();
		otherLayer.setName("otherLayer"); //$NON-NLS-1$

		// Check that no qualified name or stereotype are displayed when no related layer is activated.
		assertEquals(CLASS_NAME, umlLabelServices.renderLabelInline(class1, dDiagram));

		// Check that the stereotype is displayed when the layer is activated.
		dDiagram.getActivatedLayers().add(stereotypesLayer);
		assertEquals(EXPECTED_CLASS_NAME_STEREOTYPE + UMLCharacters.SPACE + CLASS_NAME, umlLabelServices.renderLabelInline(class1, dDiagram));

		// Check that the qualified name and the stereotype are displayed when the corresponding layer are activated.
		dDiagram.getActivatedLayers().add(qualifiedNameLayer);
		assertEquals(EXPECTED_CLASS_NAME_STEREOTYPE + UMLCharacters.SPACE + CLASS_QUALIFIED_NAME, umlLabelServices.renderLabelInline(class1, dDiagram));

		// Check that the qualified name is displayed when the layer is activated.
		dDiagram.getActivatedLayers().remove(stereotypesLayer);
		assertEquals(CLASS_QUALIFIED_NAME, umlLabelServices.renderLabelInline(class1, dDiagram)); // $NON-NLS-1$


		// Check that no qualified name or stereotype are displayed when no related layer is activated.
		dDiagram.getActivatedLayers().remove(qualifiedNameLayer);
		assertEquals(CLASS_NAME, umlLabelServices.renderLabel(class1, dDiagram));

		dDiagram.getActivatedLayers().add(otherLayer);
		assertEquals(CLASS_NAME, umlLabelServices.renderLabel(class1, dDiagram));
	}


	private Profile getECoreProfile() {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource profileResource = resourceSet.createResource(URI.createURI("pathmap://UML_PROFILES/Ecore.profile.uml")); //$NON-NLS-1$
		try {
			profileResource.load(Map.of());
			return (Profile) profileResource.getContents().get(0);

		} catch (IOException e) {
			fail(e.getMessage());
		}
		return null;
	}

	/**
	 * Test that a label of the source of edge representing a {@link Connector} is correctly displayed.
	 */
	@Test
	public void testRenderSourceEdgeLabelFromConnector() {
		Port source = create(Port.class);
		Port target = create(Port.class);

		Connector connector = create(Connector.class);
		ConnectorEnd conEndSource = create(ConnectorEnd.class);
		connector.getEnds().add(conEndSource);
		conEndSource.setRole(source);

		ConnectorEnd conEndTarget = create(ConnectorEnd.class);
		connector.getEnds().add(conEndTarget);
		conEndTarget.setRole(target);

		String label = umlLabelServices.renderEdgeSourceLabel(connector, source);
		assertEquals(MULTIPLICITY_END, label);
	}

	/**
	 * Test that a label of the target of edge representing a {@link Connector} is correctly displayed.
	 */
	@Test
	public void testRenderTargetEdgeLabelFromConnector() {
		Port source = create(Port.class);
		Port target = create(Port.class);

		Connector connector = create(Connector.class);
		ConnectorEnd conEndSource = create(ConnectorEnd.class);
		connector.getEnds().add(conEndSource);
		conEndSource.setRole(source);

		ConnectorEnd conEndTarget = create(ConnectorEnd.class);
		connector.getEnds().add(conEndTarget);
		conEndTarget.setRole(target);

		String label = umlLabelServices.renderEdgeTargetLabel(connector, target);
		assertEquals(MULTIPLICITY_END, label);
	}



	/**
	 * Checks the getDirectEditInputValue service.
	 */
	@Test
	public void testGetDirectEditInputValue() {
		Class class1 = create(Class.class);
		class1.setName(CLASS_1);
		Comment comment = create(Comment.class);
		comment.setBody(COMMENT_BODY);
		Property property = create(Property.class);
		property.setName(PROPERTY1);
		property.setType(class1);


		assertEquals(CLASS_1, umlLabelServices.getDirectEditInputValue(class1));
		assertEquals(COMMENT_BODY, umlLabelServices.getDirectEditInputValue(comment));
		assertEquals(EXPECTED_PROPERTY_LABEL, umlLabelServices.renderLabel(property, dDiagram));
		assertEquals(PROPERTY1, umlLabelServices.getDirectEditInputValue(property));

	}

	/**
	 * Checks the consumeNewLabel service.
	 */
	@Test
	public void testConsumeNewLabel() {
		Class class1 = create(Class.class);
		class1.setName(CLASS_1);
		Comment comment = create(Comment.class);
		comment.setBody(COMMENT_BODY);
		Property property = create(Property.class);
		property.setName(PROPERTY1);
		property.setType(class1);

		umlLabelServices.consumeNewLabel(class1, CLASS_2);
		assertEquals(CLASS_2, umlLabelServices.renderLabel(class1, dDiagram));
		umlLabelServices.consumeNewLabel(comment, EDITED_BODY);
		assertEquals(EDITED_BODY, umlLabelServices.renderLabel(comment, dDiagram));
		umlLabelServices.consumeNewLabel(property, PROPERTY2);
		assertEquals(EXPECTED_PROPERTY_2_LABEL, umlLabelServices.renderLabel(property, dDiagram));
	}

}
