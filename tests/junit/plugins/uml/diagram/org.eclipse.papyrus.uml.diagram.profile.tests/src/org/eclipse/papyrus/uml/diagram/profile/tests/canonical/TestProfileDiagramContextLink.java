package org.eclipse.papyrus.uml.diagram.profile.tests.canonical;

import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.profile.CreateProfileDiagramCommand;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestContextLink;
import org.junit.Test;

public class TestProfileDiagramContextLink extends TestContextLink {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateProfileDiagramCommand();
	}

	protected String getProjectName() {
		return "ProfileDiagramTestProject";
	}

	protected String getFileName() {
		return "ProfileDiagramTest.profile.di";
	}

	@Test
	public void testToManageContextLink() {
		manageContextLink(UMLElementTypes.Constraint_PackagedElementShape, UMLElementTypes.Class_Shape, UMLElementTypes.Constraint_ContextEdge, UMLElementTypes.Package_Shape);
	}
}