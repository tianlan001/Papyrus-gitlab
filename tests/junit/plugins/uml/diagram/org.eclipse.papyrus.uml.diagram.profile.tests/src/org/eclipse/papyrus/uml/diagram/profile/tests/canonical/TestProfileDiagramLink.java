package org.eclipse.papyrus.uml.diagram.profile.tests.canonical;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.profile.CreateProfileDiagramCommand;
import org.eclipse.papyrus.uml.diagram.profile.custom.edit.parts.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestLink;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.junit.Test;

public class TestProfileDiagramLink extends TestLink {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return CustomUMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateProfileDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return "ProfileDiagramTestProject";
	}

	@Override
	protected String getFileName() {
		return "ProfileDiagramTest.profile.di";
	}

	/**
	 * Test to manage Association.
	 */
	@FailingTest
	@Test
	public void testToManageAssociation() {
		testToManageLink(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLElementTypes.Association_Edge, UMLElementTypes.Package_Shape, true, "srcMulA_cla");
	}

	@Test
	public void testToManageAssociationWithoutMulti() {
		testToManageLinkWithoutMulti(UMLElementTypes.Class_Shape, UMLElementTypes.Class_Shape, UMLDIElementTypes.ASSOCIATION_NON_DIRECTED_EDGE, UMLElementTypes.Package_Shape, true, null); // name of associations are not set now
	}

	@Override
	public void testToCreateALink(IElementType linkType, String initialName) {
		testCreateLink(linkType, initialName);
	}

	private void testToManageLinkWithoutMulti(IElementType sourceType, IElementType targetType, IElementType linkType, IElementType containerType, boolean allowedOntheSame, String initialName) {
		installEnvironment(sourceType, targetType);
		testToCreateALink(linkType, initialName);
		testDestroy(linkType);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		testViewDeletion(linkType);
		testDrop(linkType);
	}
}
