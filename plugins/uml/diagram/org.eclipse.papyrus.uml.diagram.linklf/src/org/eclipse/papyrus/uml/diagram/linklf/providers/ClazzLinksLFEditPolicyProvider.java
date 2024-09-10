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
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationBranchEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassDashedLinkEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationClassLinkEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationNodeEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DefaultNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InformationItemEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InformationItemEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationLinkEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartTN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageImportEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageMergeEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ProfileApplicationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.RealizationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.RedefinableTemplateSignatureEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ShortCutDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SignalEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SignalEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SubstitutionEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TemplateBindingEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.TemplateSignatureEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.linklf.policy.graphicalnode.ClassLinksLFGraphicalNodeEditPolicy;

public class ClazzLinksLFEditPolicyProvider extends LinksLFEditPolicyProvider {

	@Override
	protected void installGraphicalNodeEditPolicy(INodeEditPart nodeEP) {
		if (shouldReinstallClassCustomEditPolicy(nodeEP)) {
			nodeEP.installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ClassLinksLFGraphicalNodeEditPolicy());
		} else {
			super.installGraphicalNodeEditPolicy(nodeEP);
		}
		
	}

	protected boolean shouldReinstallClassCustomEditPolicy(INodeEditPart ep) {
		IGraphicalEditPart graphicalEP = (IGraphicalEditPart) ep;
		return graphicalEP instanceof DependencyNodeEditPart || graphicalEP instanceof AssociationClassEditPart
				|| graphicalEP instanceof AssociationNodeEditPart || graphicalEP instanceof ComponentEditPart
				|| graphicalEP instanceof SignalEditPart || graphicalEP instanceof InterfaceEditPart
				|| graphicalEP instanceof ModelEditPartTN || graphicalEP instanceof EnumerationEditPart
				|| graphicalEP instanceof PackageEditPart || graphicalEP instanceof InformationItemEditPart
				|| graphicalEP instanceof ClassEditPart || graphicalEP instanceof PrimitiveTypeEditPart
				|| graphicalEP instanceof ConstraintEditPart || graphicalEP instanceof CommentEditPart
				|| graphicalEP instanceof ShortCutDiagramEditPart || graphicalEP instanceof DefaultNamedElementEditPart
				|| graphicalEP instanceof RedefinableTemplateSignatureEditPart
				|| graphicalEP instanceof TemplateSignatureEditPart || graphicalEP instanceof ComponentEditPartCN
				|| graphicalEP instanceof SignalEditPartCN || graphicalEP instanceof InterfaceEditPartCN
				|| graphicalEP instanceof ModelEditPartCN || graphicalEP instanceof EnumerationEditPartCN
				|| graphicalEP instanceof PackageEditPartCN || graphicalEP instanceof InformationItemEditPartCN
				|| graphicalEP instanceof ClassEditPartCN || graphicalEP instanceof PrimitiveTypeEditPartCN
				|| graphicalEP instanceof DataTypeEditPartCN || graphicalEP instanceof CommentEditPartCN
				|| graphicalEP instanceof ConstraintEditPartCN
				|| graphicalEP instanceof AssociationClassDashedLinkEditPart
				|| graphicalEP instanceof AssociationClassLinkEditPart || graphicalEP instanceof AssociationEditPart
				|| graphicalEP instanceof AssociationBranchEditPart || graphicalEP instanceof GeneralizationEditPart
				|| graphicalEP instanceof InterfaceRealizationEditPart || graphicalEP instanceof SubstitutionEditPart
				|| graphicalEP instanceof RealizationEditPart || graphicalEP instanceof AbstractionEditPart
				|| graphicalEP instanceof UsageEditPart || graphicalEP instanceof DependencyEditPart
				|| graphicalEP instanceof DependencyBranchEditPart || graphicalEP instanceof ElementImportEditPart
				|| graphicalEP instanceof PackageImportEditPart || graphicalEP instanceof PackageMergeEditPart
				|| graphicalEP instanceof ProfileApplicationEditPart
				|| graphicalEP instanceof CommentAnnotatedElementEditPart
				|| graphicalEP instanceof ConstraintConstrainedElementEditPart
				|| graphicalEP instanceof TemplateBindingEditPart
				|| graphicalEP instanceof InstanceSpecificationLinkEditPart
				|| graphicalEP instanceof ContextLinkEditPart;
	}
}
