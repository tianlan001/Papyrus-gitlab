package org.eclipse.papyrus.uml.diagram.activity.tests.edit.part;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.diagram.activity.edit.part.ActivityGroup.CustomExpansionRegionStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool.CreateAspectUnspecifiedTypeRequest;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExpansionRegionCompartmentEPTest extends AbstractPapyrusTest {

	private MockExpansionRegionEditPart myParentEP;

	private MockCustomExpansionRegionCompartmentEditPart myCompartmentEP;

	@Before
	public void setUp() throws Exception {
		myParentEP = createParentEditPart();
		myCompartmentEP = createCompartmentEP(myParentEP);
		myParentEP.createDefaultEditPolicies();
		myCompartmentEP.createDefaultEditPolicies();
	}

	private MockExpansionRegionEditPart createParentEditPart() {
		EObject expansionRegionView = UMLFactory.eINSTANCE.createExpansionRegion();
		View nodeView = NotationFactory.eINSTANCE.createShape();
		nodeView.setElement(expansionRegionView);
		nodeView.setType("" + ExpansionRegionEditPart.VISUAL_ID);
		return new MockExpansionRegionEditPart(nodeView);
	}

	private MockCustomExpansionRegionCompartmentEditPart createCompartmentEP(ExpansionRegionEditPart parentEP) {
		View decoratorView = NotationFactory.eINSTANCE.createDecorationNode();
		decoratorView.setType("" + ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		parentEP.getNotationView().insertChild(decoratorView);
		MockCustomExpansionRegionCompartmentEditPart compartment = new MockCustomExpansionRegionCompartmentEditPart(decoratorView);
		compartment.setParent(parentEP);
		return compartment;
	}

	@Test
	public void testExpansionNodeCreationRequest() {
		assertEquals(myParentEP, getTestTargetEditPart(UMLElementTypes.ExpansionNode_InputShape, UMLElementTypes.ExpansionNode_OutputShape));
	}

	@Test
	public void testMixedElementTypesCreationRequest() {
		assertNotNull(getTestTargetEditPart(UMLElementTypes.ExpansionNode_InputShape, UMLElementTypes.LoopNode_Shape));
	}

	@Test
	public void testAlienCreationRequest() {
		assertEquals(myCompartmentEP, getTestTargetEditPart(UMLElementTypes.StructuredActivityNode_Shape));
	}

	private EditPart getTestTargetEditPart(IElementType... types) {
		List<IElementType> typesList = Arrays.asList(types);
		CreateAspectUnspecifiedTypeRequest req = new AspectUnspecifiedTypeCreationTool(typesList).new CreateAspectUnspecifiedTypeRequest(typesList, PreferencesHint.USE_DEFAULTS);
		return myCompartmentEP.getTargetEditPart(req);
	}

	@After
	public void tearDown() throws Exception {
		myCompartmentEP = null;
		myParentEP = null;
	}

	private static class MockCustomExpansionRegionCompartmentEditPart extends CustomExpansionRegionStructuredActivityNodeContentCompartmentEditPart {

		public MockCustomExpansionRegionCompartmentEditPart(View view) {
			super(view);
		}

		@Override
		public void createDefaultEditPolicies() {
			super.createDefaultEditPolicies();
		}
	}

	private static class MockExpansionRegionEditPart extends ExpansionRegionEditPart {

		public MockExpansionRegionEditPart(View view) {
			super(view);
		}

		@Override
		public void createDefaultEditPolicies() {
			super.createDefaultEditPolicies();
		}
	}
}
