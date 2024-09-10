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
import org.eclipse.papyrus.uml.diagram.component.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DefaultNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.LinkDescriptorEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ManifestationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.SubstitutionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.linklf.policy.graphicalnode.ComponentLinksLFEditPolicy;

public class ComponentLinksLFEditPolicyProvider extends LinksLFEditPolicyProvider {

	@Override
	protected void installGraphicalNodeEditPolicy(INodeEditPart nodeEP) {
		if (shouldInstallCustomComponent(nodeEP)) {
			nodeEP.installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ComponentLinksLFEditPolicy());
		} else {
			super.installGraphicalNodeEditPolicy(nodeEP);
		}
	}

	protected boolean shouldInstallCustomComponent(INodeEditPart graphicalEP) {
		return graphicalEP instanceof DependencyNodeEditPart || graphicalEP instanceof ComponentEditPart
				|| graphicalEP instanceof ModelEditPart || graphicalEP instanceof PackageEditPart
				|| graphicalEP instanceof CommentEditPart || graphicalEP instanceof ConstraintEditPart
				|| graphicalEP instanceof DefaultNamedElementEditPart || graphicalEP instanceof InterfaceEditPart
				|| graphicalEP instanceof ModelEditPartCN || graphicalEP instanceof PackageEditPartCN
				|| graphicalEP instanceof ComponentEditPartCN || graphicalEP instanceof ComponentEditPartPCN
				|| graphicalEP instanceof CommentEditPartPCN || graphicalEP instanceof ConstraintEditPartPCN
				|| graphicalEP instanceof InterfaceEditPartPCN || graphicalEP instanceof UsageEditPart
				|| graphicalEP instanceof InterfaceRealizationEditPart || graphicalEP instanceof GeneralizationEditPart
				|| graphicalEP instanceof SubstitutionEditPart || graphicalEP instanceof ManifestationEditPart
				|| graphicalEP instanceof ComponentRealizationEditPart || graphicalEP instanceof AbstractionEditPart
				|| graphicalEP instanceof LinkDescriptorEditPart
				|| graphicalEP instanceof CommentAnnotatedElementEditPart
				|| graphicalEP instanceof ConstraintConstrainedElementEditPart
				|| graphicalEP instanceof DependencyEditPart || graphicalEP instanceof DependencyBranchEditPart;
	}
}
