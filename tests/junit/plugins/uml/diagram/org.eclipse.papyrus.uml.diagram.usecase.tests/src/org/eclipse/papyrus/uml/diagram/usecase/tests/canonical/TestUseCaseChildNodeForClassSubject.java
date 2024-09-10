/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.tests.canonical;

import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateEditBasedElementCommand;
import org.eclipse.papyrus.junit.framework.classification.InteractiveTest;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.papyrus.uml.diagram.usecase.CreateUseCaseDiagramCommand;
import org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.usecase.tests.IUseCaseDiagramTestsConstants;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;


/**
 * The Class TestUseCaseChildNodeForPackage.
 */
public class TestUseCaseChildNodeForClassSubject extends TestChildNode {

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return org.eclipse.papyrus.uml.diagram.usecase.custom.edit.parts.CustomUMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		final CreateElementRequest semanticRequest = new CreateElementRequest(UMLElementTypes.Classifier_SubjectShape);
		semanticRequest.setParameter(CreateEditBasedElementCommand.ECLASS_HINT, UMLPackage.eINSTANCE.getClass_());

		final ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor(
				new CreateElementRequestAdapter(semanticRequest),
				Node.class, ((IHintedType) UMLElementTypes.Classifier_SubjectShape).getSemanticHint(), getDiagramEditPart().getDiagramPreferencesHint());
		final CreateViewRequest requestcreation = new CreateViewAndElementRequest(viewDescriptor);
		return requestcreation;
	}



	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractTestNode#isSemanticTest()
	 *
	 * @return
	 */
	@Override
	protected boolean isSemanticTest() {
		return true;
	}

	@Override
	protected String getProjectName() {
		return IUseCaseDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IUseCaseDiagramTestsConstants.FILE_NAME;
	}

	@Test
	@InteractiveTest
	public void testToManageUseCaseForClassSubject() {
		testToManageNode(UMLElementTypes.UseCase_Shape_CCN, UMLPackage.eINSTANCE.getUseCase(), UMLElementTypes.Classifier_SubjectShape, false);
	}

	@Test
	@InteractiveTest
	public void testToManageUseCaseForComponentSubject() {
		testToManageNode(UMLElementTypes.UseCase_Shape_CCN, UMLPackage.eINSTANCE.getUseCase(), UMLElementTypes.Classifier_SubjectShape, false);
	}

	@Test
	@InteractiveTest
	public void testToManageUseCaseForInterfaceSubject() {
		testToManageNode(UMLElementTypes.UseCase_Shape_CCN, UMLPackage.eINSTANCE.getUseCase(), UMLElementTypes.Classifier_SubjectShape, false);
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateUseCaseDiagramCommand();
	}
}
