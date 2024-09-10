/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Montages AG and others
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
 *  Svyatoslav Kovalsky - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.linklf.providers;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DefaultNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedArtifactNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedDeviceEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedExecutionEnvironmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedNodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.linklf.policy.graphicalnode.DeploymentLinksLFEditPolicy;

public class DeploymentLinksLFEditPolicyProvider extends LinksLFEditPolicyProvider {

	@Override
	protected void installGraphicalNodeEditPolicy(INodeEditPart nodeEP) {
		if (shouldInstallDeploymentGraphicalEP(nodeEP)) {
			nodeEP.installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
					new DeploymentLinksLFEditPolicy());
		} else {
			super.installGraphicalNodeEditPolicy(nodeEP);
		}
	}

	protected boolean shouldInstallDeploymentGraphicalEP(INodeEditPart graphicalEP) {
		return graphicalEP instanceof DependencyNodeEditPart || graphicalEP instanceof ModelEditPart
				|| graphicalEP instanceof PackageEditPart || graphicalEP instanceof ConstraintEditPart
				|| graphicalEP instanceof CommentEditPart || graphicalEP instanceof ExecutionEnvironmentEditPart
				|| graphicalEP instanceof DeviceEditPart || graphicalEP instanceof ArtifactEditPart
				|| graphicalEP instanceof NodeEditPart || graphicalEP instanceof DefaultNamedElementEditPart
				|| graphicalEP instanceof ModelEditPartCN || graphicalEP instanceof PackageEditPartCN
				|| graphicalEP instanceof DeviceEditPartCN || graphicalEP instanceof NestedDeviceEditPartCN
				|| graphicalEP instanceof ExecutionEnvironmentEditPartCN
				|| graphicalEP instanceof NestedExecutionEnvironmentEditPartCN || graphicalEP instanceof NodeEditPartCN
				|| graphicalEP instanceof NestedNodeEditPartCN || graphicalEP instanceof ArtifactEditPartCN
				|| graphicalEP instanceof ArtifactEditPartACN || graphicalEP instanceof NestedArtifactNodeEditPartCN
				|| graphicalEP instanceof CommentEditPartCN || graphicalEP instanceof ConstraintEditPartCN
				|| graphicalEP instanceof DependencyEditPart || graphicalEP instanceof DependencyBranchEditPart;
	}
}
