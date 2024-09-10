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
package org.eclipse.papyrus.uml.diagram.statemachine.edit.parts;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit.locator.CellEditorLocatorAccess;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * @generated
 */
public class UMLEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (UMLVisualIDRegistry.getVisualID(view)) {
			case PackageEditPart.VISUAL_ID:
				return new PackageEditPart(view);
			case StateMachineEditPart.VISUAL_ID:
				return new StateMachineEditPart(view);
			case StateMachineNameEditPart.VISUAL_ID:
				return new StateMachineNameEditPart(view);
			case StateEditPartTN.VISUAL_ID:
				return new StateEditPartTN(view);
			case StateNameEditPartTN.VISUAL_ID:
				return new StateNameEditPartTN(view);
			case RegionEditPart.VISUAL_ID:
				return new RegionEditPart(view);
			case FinalStateEditPart.VISUAL_ID:
				return new FinalStateEditPart(view);
			case FinalStateFloatingLabelEditPart.VISUAL_ID:
				return new FinalStateFloatingLabelEditPart(view);
			case FinalStateStereotypeEditPart.VISUAL_ID:
				return new FinalStateStereotypeEditPart(view);
			case StateEditPart.VISUAL_ID:
				return new StateEditPart(view);
			case StateNameEditPart.VISUAL_ID:
				return new StateNameEditPart(view);
			case StateFloatingLabelEditPart.VISUAL_ID:
				return new StateFloatingLabelEditPart(view);
			case PseudostateInitialEditPart.VISUAL_ID:
				return new PseudostateInitialEditPart(view);
			case PseudostateInitialFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateInitialFloatingLabelEditPart(view);
			case PseudostateInitialStereotypeEditPart.VISUAL_ID:
				return new PseudostateInitialStereotypeEditPart(view);
			case PseudostateJoinEditPart.VISUAL_ID:
				return new PseudostateJoinEditPart(view);
			case PseudostateJoinFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateJoinFloatingLabelEditPart(view);
			case PseudostateJoinStereotypeEditPart.VISUAL_ID:
				return new PseudostateJoinStereotypeEditPart(view);
			case PseudostateForkEditPart.VISUAL_ID:
				return new PseudostateForkEditPart(view);
			case PseudostateForkNameEditPart.VISUAL_ID:
				return new PseudostateForkNameEditPart(view);
			case PseudostateForkStereotypeEditPart.VISUAL_ID:
				return new PseudostateForkStereotypeEditPart(view);
			case PseudostateChoiceEditPart.VISUAL_ID:
				return new PseudostateChoiceEditPart(view);
			case PseudostateChoiceFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateChoiceFloatingLabelEditPart(view);
			case PseudostateChoiceStereotypeEditPart.VISUAL_ID:
				return new PseudostateChoiceStereotypeEditPart(view);
			case PseudostateJunctionEditPart.VISUAL_ID:
				return new PseudostateJunctionEditPart(view);
			case PseudostateJunctionFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateJunctionFloatingLabelEditPart(view);
			case PseudostateJunctionStereotypeEditPart.VISUAL_ID:
				return new PseudostateJunctionStereotypeEditPart(view);
			case PseudostateShallowHistoryEditPart.VISUAL_ID:
				return new PseudostateShallowHistoryEditPart(view);
			case PseudostateShallowHistoryFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateShallowHistoryFloatingLabelEditPart(view);
			case PseudostateShallowHistoryStereotypeEditPart.VISUAL_ID:
				return new PseudostateShallowHistoryStereotypeEditPart(view);
			case PseudostateDeepHistoryEditPart.VISUAL_ID:
				return new PseudostateDeepHistoryEditPart(view);
			case PseudostateDeepHistoryFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateDeepHistoryFloatingLabelEditPart(view);
			case PseudostateDeepHistoryStereotypeEditPart.VISUAL_ID:
				return new PseudostateDeepHistoryStereotypeEditPart(view);
			case PseudostateTerminateEditPart.VISUAL_ID:
				return new PseudostateTerminateEditPart(view);
			case PseudostateTerminateFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateTerminateFloatingLabelEditPart(view);
			case PseudostateTerminateStereotypeEditPart.VISUAL_ID:
				return new PseudostateTerminateStereotypeEditPart(view);
			case PseudostateEntryPointEditPart.VISUAL_ID:
				return new PseudostateEntryPointEditPart(view);
			case PseudostateEntryPointFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateEntryPointFloatingLabelEditPart(view);
			case PseudostateEntryPointStereotypeEditPart.VISUAL_ID:
				return new PseudostateEntryPointStereotypeEditPart(view);
			case PseudostateExitPointEditPart.VISUAL_ID:
				return new PseudostateExitPointEditPart(view);
			case PseudostateExitPointFloatingLabelEditPart.VISUAL_ID:
				return new PseudostateExitPointFloatingLabelEditPart(view);
			case PseudostateExitPointStereotypeEditPart.VISUAL_ID:
				return new PseudostateExitPointStereotypeEditPart(view);
			case ConnectionPointReferenceEditPart.VISUAL_ID:
				return new ConnectionPointReferenceEditPart(view);
			case ConnectionPointReferenceNameEditPart.VISUAL_ID:
				return new ConnectionPointReferenceNameEditPart(view);
			case ConnectionPointReferenceStereotypeEditPart.VISUAL_ID:
				return new ConnectionPointReferenceStereotypeEditPart(view);
			case CommentEditPart.VISUAL_ID:
				return new CommentEditPart(view);
			case CommentBodyEditPart.VISUAL_ID:
				return new CommentBodyEditPart(view);
			case ConstraintEditPart.VISUAL_ID:
				return new ConstraintEditPart(view);
			case ConstraintNameLabelEditPart.VISUAL_ID:
				return new ConstraintNameLabelEditPart(view);
			case ConstraintBodyEditPart.VISUAL_ID:
				return new ConstraintBodyEditPart(view);
			case InternalTransitionEditPart.VISUAL_ID:
				return new InternalTransitionEditPart(view);
			case EntryStateBehaviorEditPart.VISUAL_ID:
				return new EntryStateBehaviorEditPart(view);
			case DoActivityStateBehaviorStateEditPart.VISUAL_ID:
				return new DoActivityStateBehaviorStateEditPart(view);
			case ExitStateBehaviorEditPart.VISUAL_ID:
				return new ExitStateBehaviorEditPart(view);
			case DeferrableTriggerEditPart.VISUAL_ID:
				return new DeferrableTriggerEditPart(view);
			case RegionCompartmentEditPart.VISUAL_ID:
				return new RegionCompartmentEditPart(view);
			case StateMachineCompartmentEditPart.VISUAL_ID:
				return new StateMachineCompartmentEditPart(view);
			case StateCompartmentEditPart.VISUAL_ID:
				return new StateCompartmentEditPart(view);
			case StateCompartmentEditPartTN.VISUAL_ID:
				return new StateCompartmentEditPartTN(view);
			case TransitionEditPart.VISUAL_ID:
				return new TransitionEditPart(view);
			case TransitionNameEditPart.VISUAL_ID:
				return new TransitionNameEditPart(view);
			case TransitionGuardEditPart.VISUAL_ID:
				return new TransitionGuardEditPart(view);
			case TransitionStereotypeEditPart.VISUAL_ID:
				return new TransitionStereotypeEditPart(view);
			case GeneralizationEditPart.VISUAL_ID:
				return new GeneralizationEditPart(view);
			case GeneralizationStereotypeEditPart.VISUAL_ID:
				return new GeneralizationStereotypeEditPart(view);
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return new CommentAnnotatedElementEditPart(view);
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return new ConstraintConstrainedElementEditPart(view);
			case ContextLinkEditPart.VISUAL_ID:
				return new ContextLinkEditPart(view);
			case ContextLinkAppliedStereotypeEditPart.VISUAL_ID:
				return new ContextLinkAppliedStereotypeEditPart(view);
			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(ITextAwareEditPart source) {
		if (source.getFigure() instanceof IMultilineEditableFigure) {
			return new MultilineCellEditorLocator((IMultilineEditableFigure) source.getFigure());
		} else {
			return CellEditorLocatorAccess.INSTANCE.getTextCellEditorLocator(source);
		}
	}

	/**
	 * @generated
	 */
	static private class MultilineCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private IMultilineEditableFigure multilineEditableFigure;

		/**
		 * @generated
		 */
		public MultilineCellEditorLocator(IMultilineEditableFigure figure) {
			this.multilineEditableFigure = figure;
		}

		/**
		 * @generated
		 */
		public IMultilineEditableFigure getMultilineEditableFigure() {
			return multilineEditableFigure;
		}

		/**
		 * @generated
		 */
		@Override
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getMultilineEditableFigure().getBounds().getCopy();
			rect.x = getMultilineEditableFigure().getEditionLocation().x;
			rect.y = getMultilineEditableFigure().getEditionLocation().y;
			getMultilineEditableFigure().translateToAbsolute(rect);
			if (getMultilineEditableFigure().getText().length() > 0) {
				rect.setSize(new Dimension(text.computeSize(rect.width, SWT.DEFAULT)));
			}
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
}
