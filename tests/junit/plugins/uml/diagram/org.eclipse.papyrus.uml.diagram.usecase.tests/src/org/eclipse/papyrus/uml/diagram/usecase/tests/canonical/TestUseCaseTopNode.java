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

import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.usecase.CreateUseCaseDiagramCommand;
import org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.usecase.tests.IUseCaseDiagramTestsConstants;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;



/**
 * The Class TestUseCaseTopNode.
 */
public class TestUseCaseTopNode extends TestSpecificTopNode {

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateUseCaseDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IUseCaseDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IUseCaseDiagramTestsConstants.FILE_NAME;
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManagePackage() {

		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Package_Shape);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageActor() {
		testToManageTopNode(UMLElementTypes.Actor_Shape, UMLElementTypes.Package_Shape);
	}

	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageComment() {
		testToManageTopNode(UMLElementTypes.Comment_Shape, UMLElementTypes.Package_Shape);
	}


	/**
	 * Test to manage component.
	 */
	@Test
	public void testToManageConstraint() {
		testToManageTopNode(UMLElementTypes.Constraint_Shape, UMLElementTypes.Package_Shape);
	}

	/**
	 * Test to manage subjects.
	 */
	@Test
	public void testToManageSubjectActivity() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getActivity());
	}


	@Test
	public void testToManageSubjectActor() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getActor());
	}


	@Test
	public void testToManageSubjectArtifact() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getArtifact());
	}


	// @Test
	// public void testToManageSubjectAssociation() {
	// testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getAssociation());
	// }


	// @Test
	// public void testToManageSubjectAssociationClass() {
	// testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getAssociationClass());
	// }


	@Test
	public void testToManageSubjectClass() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getClass_());
	}


	@Test
	public void testToManageSubjectCollaboration() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getCollaboration());
	}


	// @Test
	// public void testToManageSubjectCommunicationPath() {
	// testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getCommunicationPath());
	// }

	@Test
	public void testToManageSubjectComponent() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getComponent());
	}

	@Test
	public void testToManageSubjectDatatype() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getDataType());
	}


	@Test
	public void testToManageSubjectDeployementSpecification() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getDeploymentSpecification());
	}

	@Test
	public void testToManageSubjectDevice() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getDevice());
	}

	@Test
	public void testToManageSubjectEnumeration() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getEnumeration());
	}

	@Test
	public void testToManageSubjectExecutionEnvironment() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getExecutionEnvironment());
	}

	// @Test
	// public void testToManageSubjectExtension() {
	// testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getExtension());
	// }

	@Test
	public void testToManageSubjectFunctionBehavior() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getFunctionBehavior());
	}

	@Test
	public void testToManageSubjectInformationItem() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getInformationItem());
	}

	@Test
	public void testToManageSubjectInteraction() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getInteraction());
	}

	@Test
	public void testToManageSubjectInterface() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getInterface());
	}

	@Test
	public void testToManageSubjectNode() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getNode());
	}

	@Test
	public void testToManageSubjectOpaqueBehavior() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getOpaqueBehavior());
	}

	@Test
	public void testToManageSubjectPrimitiveType() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getPrimitiveType());
	}

	@Test
	public void testToManageSubjectProtocolStateMachine() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getProtocolStateMachine());
	}

	@Test
	public void testToManageSubjectSignal() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getSignal());
	}

	@Test
	public void testToManageSubjectStateMachine() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getStateMachine());
	}

	@Test
	public void testToManageSubjectStereotype() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getStereotype());
	}

	@Test
	public void testToManageSubjectUsecase() {
		testToManageSpecificTopNode(UMLElementTypes.Classifier_SubjectShape, UMLElementTypes.Package_Shape, UMLPackage.eINSTANCE.getUseCase());
	}
}
