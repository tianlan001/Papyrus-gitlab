package org.eclipse.papyrus.uml.diagram.component.test.canonical;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.component.CreateComponentDiagramCommand;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.component.test.IComponentDiagramTestsConstants;
import org.junit.Test;

public class TestPortLocation extends org.eclipse.papyrus.uml.diagram.tests.canonical.TestPortLocation {

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

	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Test
	public void testComponentPortLocation() {
		testPortLocation(UMLElementTypes.Component_PackagedElementShape);
	}

	@Test
	public void testComponentPartPortLocation() {
		testPortLocation(UMLElementTypes.Component_PackagedElementShape, ComponentCompositeCompartmentEditPart.VISUAL_ID, UMLElementTypes.Property_Shape);
	}

	@Override
	protected IElementType getPortType() {
		return UMLElementTypes.Port_Shape;
	}
}
