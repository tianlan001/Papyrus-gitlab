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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.deployment.test.canonical;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelPackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelPackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedArtifactNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedDeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeCompositeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeCompositeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackagePackageableElementCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.test.IDeploymentDiagramTestsConstants;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

public class TestSemantics extends AbstractPapyrusTestCase {

	@Override
	protected String getProjectName() {
		return IDeploymentDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IDeploymentDiagramTestsConstants.FILE_NAME;
	}

	@Test
	public void testPackageCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childPackageEP, packageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testModelCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childModelEP = createChild(ModelEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childModelEP, packageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedDeviceCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedDeviceEP = createChild(NestedDeviceEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childNestedDeviceEP, packageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedExecEnviromentCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedExecEnvirEP = createChild(NestedExecutionEnvironmentEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childNestedExecEnvirEP, packageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedArtifactCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedArtifactEP = createChild(NestedArtifactNodeEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childNestedArtifactEP, packageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedNodeCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childNodeEP, packageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testCommentCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childCommentEP = createChild(CommentEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childCommentEP, packageEP, UMLPackage.eINSTANCE.getElement_OwnedComment());
	}

	@Test
	public void testConstraintCNInPackage() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childConstraintEP = createChild(ConstraintEditPartCN.VISUAL_ID, packageCompEP);

		checkListElementReferenceSemantic(childConstraintEP, packageEP, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
	}

	@Test
	public void testPackageCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childPackageEP = createChild(PackageEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childPackageEP, modelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testModelCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childModelEP, modelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedDeviceCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedDeviceEP = createChild(NestedDeviceEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childNestedDeviceEP, modelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedExecEnviromentCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedExecEnvirEP = createChild(NestedExecutionEnvironmentEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childNestedExecEnvirEP, modelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedArtifactCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedArtifactEP = createChild(NestedArtifactNodeEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childNestedArtifactEP, modelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedNodeCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childNodeEP, modelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testCommentCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childCommentEP = createChild(CommentEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childCommentEP, modelEP, UMLPackage.eINSTANCE.getElement_OwnedComment());
	}

	@Test
	public void testConstraintCNInModel() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childConstraintEP = createChild(ConstraintEditPartCN.VISUAL_ID, modelCompEP);

		checkListElementReferenceSemantic(childConstraintEP, modelEP, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
	}

	@Test
	public void testExecEnvirCNInExecEnvir() {
		IGraphicalEditPart execEnvirEP = createChild(ExecutionEnvironmentEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart execEnvirCompEP = findChildBySemanticHint(execEnvirEP, ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childExecEnvirEP = createChild(ExecutionEnvironmentEditPartCN.VISUAL_ID, execEnvirCompEP);

		checkListElementReferenceSemantic(childExecEnvirEP, execEnvirEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testArtifactCNInExecEnvir() {
		IGraphicalEditPart execEnvirEP = createChild(ExecutionEnvironmentEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart execEnvirCompEP = findChildBySemanticHint(execEnvirEP, ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childArtifactEP = createChild(ArtifactEditPartCN.VISUAL_ID, execEnvirCompEP);

		checkListElementReferenceSemantic(childArtifactEP, execEnvirEP, UMLPackage.eINSTANCE.getClass_NestedClassifier());
	}

	@Test
	public void testExecEnvirCNInDevice() {
		IGraphicalEditPart deviceEP = createChild(DeviceEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart deviceCompEP = findChildBySemanticHint(deviceEP, DeviceCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childExecEnvirEP = createChild(ExecutionEnvironmentEditPartCN.VISUAL_ID, deviceCompEP);

		checkListElementReferenceSemantic(childExecEnvirEP, deviceEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testDeviceCNInDevice() {
		IGraphicalEditPart deviceEP = createChild(DeviceEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart deviceCompEP = findChildBySemanticHint(deviceEP, DeviceCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childDeviceEP = createChild(DeviceEditPartCN.VISUAL_ID, deviceCompEP);

		checkListElementReferenceSemantic(childDeviceEP, deviceEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testNodeCNInDevice() {
		IGraphicalEditPart deviceEP = createChild(DeviceEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart deviceCompEP = findChildBySemanticHint(deviceEP, DeviceCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNodeEP = createChild(NodeEditPartCN.VISUAL_ID, deviceCompEP);

		checkListElementReferenceSemantic(childNodeEP, deviceEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testArtifactACNInDevice() {
		IGraphicalEditPart artifactEP = createChild(ArtifactEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart artifactCompEP = findChildBySemanticHint(artifactEP, ArtifactCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childArtifactEP = createChild(ArtifactEditPartACN.VISUAL_ID, artifactCompEP);

		checkListElementReferenceSemantic(childArtifactEP, artifactEP, UMLPackage.eINSTANCE.getArtifact_NestedArtifact());
	}

	@Test
	public void testExecEnvirCNInNode() {
		IGraphicalEditPart nodeEP = createChild(NodeEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart nodeCompEP = findChildBySemanticHint(nodeEP, NodeCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childExecEnvirEP = createChild(ExecutionEnvironmentEditPartCN.VISUAL_ID, nodeCompEP);

		checkListElementReferenceSemantic(childExecEnvirEP, nodeEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testDeviceCNInNode() {
		IGraphicalEditPart nodeEP = createChild(NodeEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart nodeCompEP = findChildBySemanticHint(nodeEP, NodeCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childDeviceEP = createChild(DeviceEditPartCN.VISUAL_ID, nodeCompEP);

		checkListElementReferenceSemantic(childDeviceEP, nodeEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testNodeCNInNode() {
		IGraphicalEditPart nodeEP = createChild(NodeEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart nodeCompEP = findChildBySemanticHint(nodeEP, NodeCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNodeEP = createChild(NodeEditPartCN.VISUAL_ID, nodeCompEP);

		checkListElementReferenceSemantic(childNodeEP, nodeEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testArtifactCNInNode() {
		IGraphicalEditPart nodeEP = createChild(NodeEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart nodeCompEP = findChildBySemanticHint(nodeEP, NodeCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childArtifactEP = createChild(ArtifactEditPartCN.VISUAL_ID, nodeCompEP);

		checkListElementReferenceSemantic(childArtifactEP, nodeEP, UMLPackage.eINSTANCE.getClass_NestedClassifier());
	}

	@Test
	public void testPackageCNInPackageCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart firstLevelChildPackageCompEP = findChildBySemanticHint(firstLevelChidPackageEP, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart secondLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, firstLevelChildPackageCompEP);

		checkListElementReferenceSemantic(secondLevelChidPackageEP, firstLevelChidPackageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedDeviceCNInPackageCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart firstLevelChildPackageCompEP = findChildBySemanticHint(firstLevelChidPackageEP, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedDeviceCNEP = createChild(NestedDeviceEditPartCN.VISUAL_ID, firstLevelChildPackageCompEP);

		checkListElementReferenceSemantic(nestedDeviceCNEP, firstLevelChidPackageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedExecEnviromentCNInPackageCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart firstLevelChildPackageCompEP = findChildBySemanticHint(firstLevelChidPackageEP, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedExecEnvirEP = createChild(NestedExecutionEnvironmentEditPartCN.VISUAL_ID, firstLevelChildPackageCompEP);

		checkListElementReferenceSemantic(nestedExecEnvirEP, firstLevelChidPackageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedArtifactCNInPackageCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart firstLevelChildPackageCompEP = findChildBySemanticHint(firstLevelChidPackageEP, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedArtifactCNEP = createChild(NestedArtifactNodeEditPartCN.VISUAL_ID, firstLevelChildPackageCompEP);

		checkListElementReferenceSemantic(nestedArtifactCNEP, firstLevelChidPackageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedNodeCNInPackageCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart firstLevelChildPackageCompEP = findChildBySemanticHint(firstLevelChidPackageEP, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, firstLevelChildPackageCompEP);

		checkListElementReferenceSemantic(nestedNodeEP, firstLevelChidPackageEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testCommentCNInPackageCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart firstLevelChildPackageCompEP = findChildBySemanticHint(firstLevelChidPackageEP, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart commentCNEP = createChild(CommentEditPartCN.VISUAL_ID, firstLevelChildPackageCompEP);

		checkListElementReferenceSemantic(commentCNEP, firstLevelChidPackageEP, UMLPackage.eINSTANCE.getElement_OwnedComment());
	}

	@Test
	public void testConstraintCNInPackageCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart firstLevelChildPackageCompEP = findChildBySemanticHint(firstLevelChidPackageEP, PackagePackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart constraintCNEP = createChild(ConstraintEditPartCN.VISUAL_ID, firstLevelChildPackageCompEP);

		checkListElementReferenceSemantic(constraintCNEP, firstLevelChidPackageEP, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
	}

	@Test
	public void testPackageCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart secondLevelChidPackageEP = createChild(PackageEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(secondLevelChidPackageEP, firstLevelChidModelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testModelCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart modelCNEP = createChild(ModelEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(modelCNEP, firstLevelChildModelCompEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedDeviceCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedDeviceCNEP = createChild(NestedDeviceEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(nestedDeviceCNEP, firstLevelChidModelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedExecEnviromentCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedExecEnvirEP = createChild(NestedExecutionEnvironmentEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(nestedExecEnvirEP, firstLevelChidModelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedArtifactCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedArtifactCNEP = createChild(NestedArtifactNodeEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(nestedArtifactCNEP, firstLevelChidModelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testNestedNodeCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart nestedNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(nestedNodeEP, firstLevelChidModelEP, UMLPackage.eINSTANCE.getPackage_PackagedElement());
	}

	@Test
	public void testCommentCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart commentCNEP = createChild(CommentEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(commentCNEP, firstLevelChidModelEP, UMLPackage.eINSTANCE.getElement_OwnedComment());
	}

	@Test
	public void testConstraintCNInModelCN() {
		IGraphicalEditPart modelEP = createChild(ModelEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart modelCompEP = findChildBySemanticHint(modelEP, ModelPackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart firstLevelChidModelEP = createChild(ModelEditPartCN.VISUAL_ID, modelCompEP);
		IGraphicalEditPart firstLevelChildModelCompEP = findChildBySemanticHint(firstLevelChidModelEP, ModelPackageableElementCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart constraintCNEP = createChild(ConstraintEditPartCN.VISUAL_ID, firstLevelChildModelCompEP);

		checkListElementReferenceSemantic(constraintCNEP, firstLevelChidModelEP, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
	}

	@Test
	public void testExecEnvirCNInNestedDeviceCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedDeviceEP = createChild(NestedDeviceEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedDeviceCompEP = findChildBySemanticHint(childNestedDeviceEP, DeviceCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart childExecEnvirEP = createChild(ExecutionEnvironmentEditPartCN.VISUAL_ID, nestedDeviceCompEP);

		checkListElementReferenceSemantic(childExecEnvirEP, childNestedDeviceEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testDeviceCNInNestedDeviceCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedDeviceEP = createChild(NestedDeviceEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedDeviceCompEP = findChildBySemanticHint(childNestedDeviceEP, DeviceCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart secondLevelChidDeviceEP = createChild(DeviceEditPartCN.VISUAL_ID, nestedDeviceCompEP);

		checkListElementReferenceSemantic(secondLevelChidDeviceEP, childNestedDeviceEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testNodeCNInNestedDeviceCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childNestedDeviceEP = createChild(NestedDeviceEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedDeviceCompEP = findChildBySemanticHint(childNestedDeviceEP, DeviceCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart childNodeEP = createChild(NodeEditPartCN.VISUAL_ID, nestedDeviceCompEP);

		checkListElementReferenceSemantic(childNodeEP, childNestedDeviceEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testExecEnvirCNInNestedExecEnvirCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart nestedExecEnvirEP = createChild(NestedExecutionEnvironmentEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedExecEnvirCompEP = findChildBySemanticHint(nestedExecEnvirEP, ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart secondLevelExecEnvCNEP = createChild(ExecutionEnvironmentEditPartCN.VISUAL_ID, nestedExecEnvirCompEP);

		checkListElementReferenceSemantic(secondLevelExecEnvCNEP, nestedExecEnvirEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testArtifactCNInNestedExecEnvirCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart nestedExecEnvirEP = createChild(NestedExecutionEnvironmentEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedExecEnvirCompEP = findChildBySemanticHint(nestedExecEnvirEP, ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart secondLevelArtifactCNEP = createChild(ArtifactEditPartCN.VISUAL_ID, nestedExecEnvirCompEP);

		checkListElementReferenceSemantic(secondLevelArtifactCNEP, nestedExecEnvirEP, UMLPackage.eINSTANCE.getClass_NestedClassifier());
	}

	@Test
	public void testArtifactACNInArtifactACN() {
		IGraphicalEditPart artifactEP = createChild(ArtifactEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart artifactCompEP = findChildBySemanticHint(artifactEP, ArtifactCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childArtifactACNEP = createChild(ArtifactEditPartACN.VISUAL_ID, artifactCompEP);
		IGraphicalEditPart childArtifactCompACNEP = findChildBySemanticHint(childArtifactACNEP, ArtifactCompositeCompartmentEditPartACN.VISUAL_ID);
		IGraphicalEditPart secondLevelChidArtifactACN = createChild(ArtifactEditPartACN.VISUAL_ID, childArtifactCompACNEP);

		checkListElementReferenceSemantic(secondLevelChidArtifactACN, childArtifactACNEP, UMLPackage.eINSTANCE.getArtifact_NestedArtifact());
	}

	@Test
	public void testArtifactACNInArtifactCN() {
		IGraphicalEditPart execEnvirEP = createChild(ExecutionEnvironmentEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart execEnvirCompEP = findChildBySemanticHint(execEnvirEP, ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart childArtifactEP = createChild(ArtifactEditPartCN.VISUAL_ID, execEnvirCompEP);
		IGraphicalEditPart childArtifactCompEP = findChildBySemanticHint(childArtifactEP, ArtifactCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart secondLevelChidArtifactACN = createChild(ArtifactEditPartACN.VISUAL_ID, childArtifactCompEP);

		checkListElementReferenceSemantic(secondLevelChidArtifactACN, childArtifactEP, UMLPackage.eINSTANCE.getArtifact_NestedArtifact());
	}

	@Test
	public void testExecEnvirCNInNestedNodeCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart nestedNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedNodeCompEP = findChildBySemanticHint(nestedNodeEP, NodeCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart childExecEnvirEP = createChild(ExecutionEnvironmentEditPartCN.VISUAL_ID, nestedNodeCompEP);

		checkListElementReferenceSemantic(childExecEnvirEP, nestedNodeEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testDeviceCNInNestedNodeCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart nestedNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedNodeCompEP = findChildBySemanticHint(nestedNodeEP, NodeCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart childDeviceCNEP = createChild(DeviceEditPartCN.VISUAL_ID, nestedNodeCompEP);

		checkListElementReferenceSemantic(childDeviceCNEP, nestedNodeEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testNodeCNInNestedNodeCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart nestedNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedNodeCompEP = findChildBySemanticHint(nestedNodeEP, NodeCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart secondLevelNodeCNEP = createChild(NodeEditPartCN.VISUAL_ID, nestedNodeCompEP);

		checkListElementReferenceSemantic(secondLevelNodeCNEP, nestedNodeEP, UMLPackage.eINSTANCE.getNode_NestedNode());
	}

	@Test
	public void testArtifactCNInNestedNodeCN() {
		IGraphicalEditPart packageEP = createChild(PackageEditPart.VISUAL_ID, getDiagramEditPart());
		IGraphicalEditPart packageCompEP = findChildBySemanticHint(packageEP, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart nestedNodeEP = createChild(NestedNodeEditPartCN.VISUAL_ID, packageCompEP);
		IGraphicalEditPart nestedNodeCompEP = findChildBySemanticHint(nestedNodeEP, NodeCompositeCompartmentEditPartCN.VISUAL_ID);
		IGraphicalEditPart childExecEnvirEP = createChild(ArtifactEditPartCN.VISUAL_ID, nestedNodeCompEP);

		checkListElementReferenceSemantic(childExecEnvirEP, nestedNodeEP, UMLPackage.eINSTANCE.getClass_NestedClassifier());
	}
}
