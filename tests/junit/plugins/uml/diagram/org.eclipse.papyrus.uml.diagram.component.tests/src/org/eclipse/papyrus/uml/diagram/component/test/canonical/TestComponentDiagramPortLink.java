package org.eclipse.papyrus.uml.diagram.component.test.canonical;

import static org.junit.Assert.assertTrue;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.component.CreateComponentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.component.test.IComponentDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLinkWithParent;
import org.junit.Before;
import org.junit.Test;

public class TestComponentDiagramPortLink extends TestLinkWithParent {

	private GraphicalEditPart targetParent;

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateComponentDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IComponentDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IComponentDiagramTestsConstants.FILE_NAME;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		parentType = UMLElementTypes.Component_PackagedElementShape;
	}

	@Override
	public void installEnvironment(IElementType sourceType, IElementType targetType) {
		super.installEnvironment(sourceType, targetType);
		// create the parent source
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(parentType, getDiagramEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(DEFAULT_PARENT_LOCATION);
		Command command = getDiagramEditPart().getCommand(requestcreation);
		assertTrue(command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		targetParent = (GraphicalEditPart) getDiagramEditPart().getChildren().get(1);
		// create the target
		requestcreation = CreateViewRequestFactory.getCreateShapeRequest(targetType, getDiagramEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(DEFAULT_SOURCE_LOCATION);
		command = targetParent.getCommand(requestcreation);
		assertTrue(command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		target = (GraphicalEditPart) targetParent.getChildBySemanticHint(((IHintedType) targetType).getSemanticHint());
	}

	/**
	 * Test to manage port connector.
	 */
	@Test
	public void testToManagePortConnector() {
		testImpossibleToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Connector_Edge);
	}

	/**
	 * Test to manage port usage.
	 */
	@Test
	public void testToManagePortUsage() {
		initConfLinkOwnedByParent();
		testToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Usage_Edge, UMLElementTypes.Component_PackagedElementShape, true);
	}

	/**
	 * Test to manage port abstraction.
	 */
	@Test
	public void testToManagePortAbstraction() {
		initConfLinkOwnedByParent();
		testToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Abstraction_Edge, UMLElementTypes.Component_PackagedElementShape, true);
	}


	/**
	 * Test to manage port generalization.
	 */
	@Test
	public void testToManagePortGeneralization() {
		testImpossibleToManageLink(UMLElementTypes.Port_Shape, UMLElementTypes.Port_Shape, UMLElementTypes.Generalization_Edge);
	}

	private void initConfLinkOwnedByParent() {
		// expected values before link creation
		beginRootSemanticOwnedElementSize = 2;
		beginDiagramEditPartChildrenSize = 2;
		beginRootEdgeSize = 0;
		beginSourceConnectionsSize = 0;
		beginRootViewChildrenSize = 2;
		// expected values after link creation
		endSourceConnectionsSize = beginSourceConnectionsSize + 1;
		endRootSemanticOwnedElementSize = beginRootSemanticOwnedElementSize + 1;
		endDiagramEditPartChildrenSize = beginDiagramEditPartChildrenSize;
		endRootEdgeSize = beginRootEdgeSize + 1;
		endRootViewChildrenSize = beginRootViewChildrenSize;
	}
}
