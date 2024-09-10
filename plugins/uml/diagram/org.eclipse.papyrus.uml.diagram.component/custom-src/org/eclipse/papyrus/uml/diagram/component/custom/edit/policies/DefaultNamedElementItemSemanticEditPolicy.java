/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.component.custom.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.AbstractionCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.CommentAnnotatedElementCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.CommentAnnotatedElementReorientCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.ComponentRealizationCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.ConstraintConstrainedElementCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.ConstraintConstrainedElementReorientCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.DependencyBranchCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.DependencyCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.InterfaceRealizationCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.ManifestationCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.SubstitutionCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.commands.UsageCreateCommand;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyBranchEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ManifestationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.SubstitutionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.policies.UMLBaseItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;


/**
 * @since 5.0
 */
public class DefaultNamedElementItemSemanticEditPolicy extends UMLBaseItemSemanticEditPolicy {


	public DefaultNamedElementItemSemanticEditPolicy() {
		super(UMLElementTypes.NamedElement_DefaultShape);
	}


	@Override
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		View view = (View) getHost().getModel();
		CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(getEditingDomain(), null);
		cmd.setTransactionNestingEnabled(true);
		EAnnotation annotation = view.getEAnnotation("Shortcut");//$NON-NLS-1$
		if (annotation == null) {
			// there are indirectly referenced children, need extra commands: false
			addDestroyShortcutsCommand(cmd, view);
			// delete host element
			List<EObject> todestroy = new ArrayList<>();
			todestroy.add(req.getElementToDestroy());
			// cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand(req));
			cmd.add(new EMFtoGMFCommandWrapper(new DeleteCommand(getEditingDomain(), todestroy)));
		} else {
			cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), view));
		}
		return getGEFWrapper(cmd.reduce());
	}


	@Override
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req) : getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super.getCreateRelationshipCommand(req);
	}


	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		IElementType requestElementType = req.getElementType();
		if (requestElementType == null) {
			return null;
		}
		IElementType baseElementType = requestElementType;

		if (UMLElementTypes.Usage_Edge == baseElementType) {
			return getGEFWrapper(new UsageCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.InterfaceRealization_Edge == baseElementType) {
			return getGEFWrapper(new InterfaceRealizationCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Substitution_Edge == baseElementType) {
			return getGEFWrapper(new SubstitutionCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Manifestation_Edge == baseElementType) {
			return getGEFWrapper(new ManifestationCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.ComponentRealization_Edge == baseElementType) {
			return getGEFWrapper(new ComponentRealizationCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Abstraction_Edge == baseElementType) {
			return getGEFWrapper(new AbstractionCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if ((UMLElementTypes.Comment_AnnotatedElementEdge == baseElementType) || (UMLElementTypes.Constraint_ConstrainedElementEdge == baseElementType)) {
			return null;
		}
		if (UMLElementTypes.Dependency_Edge == baseElementType) {
			return getGEFWrapper(new DependencyCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Dependency_BranchEdge == baseElementType) {
			return getGEFWrapper(new DependencyBranchCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		return null;
	}


	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		IElementType requestElementType = req.getElementType();
		if (requestElementType == null) {
			return null;
		}
		IElementType baseElementType = requestElementType;

		if (UMLElementTypes.Usage_Edge == baseElementType) {
			return getGEFWrapper(new UsageCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.InterfaceRealization_Edge == baseElementType) {
			return null;
		}
		if (UMLElementTypes.Substitution_Edge == baseElementType) {
			return getGEFWrapper(new SubstitutionCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Manifestation_Edge == baseElementType) {
			return getGEFWrapper(new ManifestationCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.ComponentRealization_Edge == baseElementType) {
			return getGEFWrapper(new ComponentRealizationCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Abstraction_Edge == baseElementType) {
			return getGEFWrapper(new AbstractionCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Comment_AnnotatedElementEdge == baseElementType) {
			return getGEFWrapper(new CommentAnnotatedElementCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Constraint_ConstrainedElementEdge == baseElementType) {
			return getGEFWrapper(new ConstraintConstrainedElementCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Dependency_Edge == baseElementType) {
			return getGEFWrapper(new DependencyCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		if (UMLElementTypes.Dependency_BranchEdge == baseElementType) {
			return getGEFWrapper(new DependencyBranchCreateCommand(req,
					req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 */
	@Override
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case UsageEditPart.VISUAL_ID:
		case InterfaceRealizationEditPart.VISUAL_ID:
		case SubstitutionEditPart.VISUAL_ID:
		case ManifestationEditPart.VISUAL_ID:
		case ComponentRealizationEditPart.VISUAL_ID:
		case AbstractionEditPart.VISUAL_ID:
		case DependencyEditPart.VISUAL_ID:
		case DependencyBranchEditPart.VISUAL_ID:
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(req.getRelationship());
			if (provider == null) {
				return UnexecutableCommand.INSTANCE;
			}
			// Retrieve re-orient command from the Element Edit service
			ICommand reorientCommand = provider.getEditCommand(req);
			if (reorientCommand == null) {
				return UnexecutableCommand.INSTANCE;
			}
			return getGEFWrapper(reorientCommand.reduce());
		}
		return super.getReorientRelationshipCommand(req);
	}

	/**
	 * Returns command to reorient EReference based link. New link target or source
	 * should be the domain model element associated with this node.
	 */
	@Override
	protected Command getReorientReferenceRelationshipCommand(ReorientReferenceRelationshipRequest req) {
		switch (getVisualID(req)) {
		case CommentAnnotatedElementEditPart.VISUAL_ID:
			return getGEFWrapper(new CommentAnnotatedElementReorientCommand(req));
		case ConstraintConstrainedElementEditPart.VISUAL_ID:
			return getGEFWrapper(new ConstraintConstrainedElementReorientCommand(req));
		}
		return super.getReorientReferenceRelationshipCommand(req);
	}
}
