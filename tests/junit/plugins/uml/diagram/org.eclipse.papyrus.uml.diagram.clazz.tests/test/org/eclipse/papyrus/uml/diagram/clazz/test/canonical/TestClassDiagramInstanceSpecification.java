package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestInstanceSpecification;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.junit.Test;

public class TestClassDiagramInstanceSpecification extends TestInstanceSpecification {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return CustomUMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateClassDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IClassDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IClassDiagramTestsConstants.FILE_NAME;
	}

	@Test
	public void testInstanceSpecification() {
		testInstanceSpecification(UMLElementTypes.Class_Shape, UMLDIElementTypes.ASSOCIATION_NON_DIRECTED_EDGE, UMLElementTypes.InstanceSpecification_Shape, UMLElementTypes.InstanceSpecification_Edge);
	}

	@Override
	protected ICommand createSetupEditPartClassifierCommand(EObject source, SetRequest setRequest) {
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(source);
		return provider.getEditCommand(setRequest);
	}
}