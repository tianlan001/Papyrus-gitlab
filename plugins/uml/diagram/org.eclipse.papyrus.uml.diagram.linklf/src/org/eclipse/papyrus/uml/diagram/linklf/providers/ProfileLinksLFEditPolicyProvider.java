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
import org.eclipse.papyrus.uml.diagram.linklf.policy.graphicalnode.ProfileLinksLFEditPolicy;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationBranchEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ShortCutDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeEditPartCN;

public class ProfileLinksLFEditPolicyProvider extends LinksLFEditPolicyProvider {

	@Override
	protected void installGraphicalNodeEditPolicy(INodeEditPart nodeEP) {
		if (shouldInstallProfileGraphicalEditPolicy(nodeEP)) {
			nodeEP.installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
					new ProfileLinksLFEditPolicy());
		} else {
			super.installGraphicalNodeEditPolicy(nodeEP);
		}
	}

	protected boolean shouldInstallProfileGraphicalEditPolicy(INodeEditPart graphicalEP) {
		return graphicalEP instanceof DependencyNodeEditPart || graphicalEP instanceof AssociationNodeEditPart
				|| graphicalEP instanceof StereotypeEditPart || graphicalEP instanceof ClassEditPart
				|| graphicalEP instanceof CommentEditPart || graphicalEP instanceof ConstraintEditPart
				|| graphicalEP instanceof ModelEditPartTN || graphicalEP instanceof ProfileEditPartTN
				|| graphicalEP instanceof PackageEditPart || graphicalEP instanceof EnumerationEditPart
				|| graphicalEP instanceof PrimitiveTypeEditPart || graphicalEP instanceof DataTypeEditPart
				|| graphicalEP instanceof ShortCutDiagramEditPart || graphicalEP instanceof PrimitiveTypeEditPartCN
				|| graphicalEP instanceof StereotypeEditPartCN || graphicalEP instanceof ClassEditPartCN
				|| graphicalEP instanceof CommentEditPartCN || graphicalEP instanceof ModelEditPartCN
				|| graphicalEP instanceof ProfileEditPartCN || graphicalEP instanceof PackageEditPartCN
				|| graphicalEP instanceof ConstraintEditPartCN || graphicalEP instanceof EnumerationEditPartCN
				|| graphicalEP instanceof DataTypeEditPartCN || graphicalEP instanceof AssociationEditPart
				|| graphicalEP instanceof AssociationBranchEditPart || graphicalEP instanceof GeneralizationEditPart
				|| graphicalEP instanceof DependencyEditPart || graphicalEP instanceof DependencyBranchEditPart
				|| graphicalEP instanceof ElementImportEditPart || graphicalEP instanceof PackageImportEditPart
				|| graphicalEP instanceof CommentAnnotatedElementEditPart
				|| graphicalEP instanceof ConstraintConstrainedElementEditPart
				|| graphicalEP instanceof ContextLinkEditPart;
	}
}
