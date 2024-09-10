/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Atos - Initial API and implementation
 *   Arthur Daussy Bug 366026 - [ActivityDiagram] Refactoring in order to try respect Generation Gap Pattern
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ControlFlowInterruptibleIconEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ObjectFlowInterruptibleIconEditPart;
import org.eclipse.papyrus.uml.diagram.activity.locator.PinPositionLocator;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper;
import org.eclipse.uml2.uml.ActionInputPin;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValuePin;

/**
 * Extends UMLViewProvider in order to provide custom node creation.
 * List of custom node (has to be updated):
 * -> ControlFlow
 * -> Object Flow
 * -> Interruptible Edge Icon (for ControlFlow and ObjectFlow)
 *
 * @author arthur daussy
 *
 */
public class CustomUMLViewProvider extends UMLViewProvider {

	@Override
	public Edge createControlFlow_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Edge edge = super.createControlFlow_Edge(domainElement, containerView, index, persisted, preferencesHint);
		/*
		 * Withdraw the view create for ControlFlowInterruptibleFigure
		 * This implementation do the following:
		 * 1. (In super imp) Create View + InterruptibleIconView
		 * 2. (Here) Withdraw InterruptibleIconView
		 * This implementation was done in purpose to keep generated code intact and keep using the next generation code
		 */
		if (edge != null) {
			EObject semanticElement = edge.getElement();
			if (semanticElement != null) {
				if (!semanticElement.eIsSet(UMLPackage.Literals.ACTIVITY_EDGE__INTERRUPTS)) {
					deleteView(edge, ControlFlowInterruptibleIconEditPart.VISUAL_ID);
				}
			} else {
				deleteView(edge, ControlFlowInterruptibleIconEditPart.VISUAL_ID);
			}
		}
		return edge;
	}

	@Override
	public Edge createObjectFlow_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Edge edge = super.createObjectFlow_Edge(domainElement, containerView, index, persisted, preferencesHint);
		/*
		 * Withdraw the view create for ControlFlowInterruptibleFigure
		 * This implementation do the following:
		 * 1. (In super imp) Create View + InterruptibleIconView
		 * 2. (Here) Withdraw InterruptibleIconView
		 * This implementation was done in purpose to keep generated code intact and keep using the next generation code
		 */
		if (edge != null) {
			EObject semanticElement = edge.getElement();
			if (semanticElement != null) {
				if (!semanticElement.eIsSet(UMLPackage.Literals.ACTIVITY_EDGE__INTERRUPTS)) {
					deleteView(edge, ObjectFlowInterruptibleIconEditPart.VISUAL_ID);
				}
			} else {
				deleteView(edge, ObjectFlowInterruptibleIconEditPart.VISUAL_ID);
			}
		}
		return edge;
	}

	/**
	 *
	 * @param edge
	 * @param semanticHint
	 */
	private void deleteView(Edge edge, String semanticHint) {
		View interruptibleIconView = ViewUtil.getChildBySemanticHint(edge, String.valueOf(semanticHint));
		if (interruptibleIconView != null) {
			ViewUtil.destroy(interruptibleIconView);
		}
	}

	/**
	 * Create an Interruptible Edge icon on Control Flow
	 *
	 * @param edge
	 * @param prefStore
	 * @param InterruptibleEdgeVisualID
	 * @param elementName
	 */
	public Node createInterruptibleEdgeIconOnControlFlow(View edge, PreferencesHint preferencesHint) {
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		Node label = createLabel(edge, UMLVisualIDRegistry.getType(ControlFlowInterruptibleIconEditPart.VISUAL_ID));
		label.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "ControlFlow");
		return label;
	}

	/**
	 * Create an Interruptible Edge icon on Object Flow
	 *
	 * @param edge
	 * @param prefStore
	 * @param InterruptibleEdgeVisualID
	 * @param elementName
	 */
	public Node createInterruptibleEdgeIconOnObjectlFlow(View containerView, PreferencesHint preferencesHint) {
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		Node label = createLabel(containerView, UMLVisualIDRegistry.getType(ObjectFlowInterruptibleIconEditPart.VISUAL_ID));
		label.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(containerView, prefStore, "ObjectFlow");
		return label;
	}

	/**
	 * Override in order to be able to accept custom node creation
	 *
	 * @see org.eclipse.papyrus.uml.diagram.activity.providers.UMLViewProvider#createNode(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.notation.View, java.lang.String, int, boolean,
	 *      org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint)
	 *
	 * @param semanticAdapter
	 * @param containerView
	 * @param semanticHint
	 * @param index
	 * @param persisted
	 * @param preferencesHint
	 * @return
	 */
	@Override
	public Node createNode(IAdaptable semanticAdapter, View containerView, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint) {
		final EObject domainElement = getSemanticElement(semanticAdapter);
		final String visualID;
		if (semanticHint == null) {
			visualID = UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		} else {
			visualID = UMLVisualIDRegistry.getVisualID(semanticHint);
		}
		/**
		 * handle specific node creation
		 */
		Node node = createCustomNode(visualID, containerView, preferencesHint);
		return (node != null) ? node : super.createNode(semanticAdapter, containerView, semanticHint, index, persisted, preferencesHint);
	}

	/**
	 * Override in order to handle custom node
	 *
	 * @see org.eclipse.papyrus.uml.diagram.activity.providers.UMLViewProvider#provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation)
	 *
	 * @param op
	 * @return
	 */
	@Override
	protected boolean provides(CreateNodeViewOperation op) {
		return super.provides(op) || isCustomNode(op);
	}

	/***
	 * return true if the operation asking a for custom node creation
	 *
	 * @param op
	 * @return
	 */
	protected boolean isCustomNode(CreateNodeViewOperation op) {
		IElementType elementType = getSemanticElementType(op.getSemanticAdapter());
		if (op.getContainerView() != null && op.getSemanticHint() != null && elementType == null) {
			String visualID = UMLVisualIDRegistry.getVisualID(op.getSemanticHint());
			return isCustomNode(visualID);
		}
		return false;
	}

	/**
	 * Return true if the node is a custom node
	 *
	 * @param visualID
	 * @return
	 */
	protected boolean isCustomNode(String visualID) {
		switch (visualID) {
		case ControlFlowInterruptibleIconEditPart.VISUAL_ID:
		case ObjectFlowInterruptibleIconEditPart.VISUAL_ID:
			return true;
		}
		return false;
	}

	/**
	 * Create specific node
	 *
	 * @param visualID
	 * @param containerView
	 * @param preferencesHint
	 * @return
	 */
	protected Node createCustomNode(String visualID, View containerView, PreferencesHint preferencesHint) {
		switch (visualID) {
		case ControlFlowInterruptibleIconEditPart.VISUAL_ID:
			return createInterruptibleEdgeIconOnControlFlow(containerView, preferencesHint);
		case ObjectFlowInterruptibleIconEditPart.VISUAL_ID:
			return createInterruptibleEdgeIconOnObjectlFlow(containerView, preferencesHint);
		}
		return null;
	}




	@Override
	public Node createOpaqueAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = super.createOpaqueAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
		adaptActionHeight(node, domainElement);
		createPins(domainElement, node, persisted, preferencesHint);
		return node;
	}

	@Override
	public Node createCallBehaviorAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = super.createCallBehaviorAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
		adaptActionHeight(node, domainElement);
		createPins(domainElement, node, persisted, preferencesHint);
		return node;
	}

	@Override
	public Node createCallOperationAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = super.createCallOperationAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
		adaptActionHeight(node, domainElement);
		createPins(domainElement, node, persisted, preferencesHint);
		return node;
	}

	private void adaptActionHeight(Node node, EObject domainElement) {
		Bounds boundsConstraint = NotationFactory.eINSTANCE.createBounds();
		PinPositionLocator.adaptActionHeight(boundsConstraint, domainElement);
		node.setLayoutConstraint(boundsConstraint);
	}

	/**
	 * Create pins in an action's node
	 *
	 * @param domainElement
	 *            the model action
	 * @param node
	 *            the action node
	 * @param persisted
	 *            the persisted property of views to create
	 * @param preferencesHint
	 *            the preference hint
	 */
	private void createPins(EObject domainElement, Node node, boolean persisted, PreferencesHint preferencesHint) {
		if (domainElement instanceof OpaqueAction) {
			// pins of an opaque action : input values and output values
			int index = 0;
			for (InputPin pin : ((OpaqueAction) domainElement).getInputValues()) {
				if (pin instanceof ValuePin) {
					createValuePin_OpaqueActionInputShape(pin, node, index, persisted, preferencesHint);
				} else if (pin instanceof ActionInputPin) {
					createActionInputPin_OpaqueActionInputShape(pin, node, index, persisted, preferencesHint);
				} else {
					createInputPin_OpaqueActionInputShape(pin, node, index, persisted, preferencesHint);
				}
				index++;
			}
			index = 0;
			for (OutputPin pin : ((OpaqueAction) domainElement).getOutputValues()) {
				createOutputPin_OpaqueActionOutputShape(pin, node, index, persisted, preferencesHint);
			}
		} else if (domainElement instanceof CallBehaviorAction) {
			// pins of a call behavior action : arguments and results
			int index = 0;
			for (InputPin pin : ((CallBehaviorAction) domainElement).getArguments()) {
				if (pin instanceof ValuePin) {
					createValuePin_CallBehaviorActionArgumentShape(pin, node, index, persisted, preferencesHint);
				} else if (pin instanceof ActionInputPin) {
					createActionInputPin_CallBehaviorActionArgumentShape(pin, node, index, persisted, preferencesHint);
				} else {
					createInputPin_CallBehaviorActionArgumentShape(pin, node, index, persisted, preferencesHint);
				}
				index++;
			}
			index = 0;
			for (OutputPin pin : ((CallBehaviorAction) domainElement).getResults()) {
				createOutputPin_CallBehaviorActionResultShape(pin, node, index, persisted, preferencesHint);
			}
		} else if (domainElement instanceof CallOperationAction) {
			// pins of a call operation action : arguments, target and results
			int index = 0;
			for (InputPin pin : ((CallOperationAction) domainElement).getArguments()) {
				if (pin instanceof ValuePin) {
					createValuePin_CallOperationActionArgumentShape(pin, node, index, persisted, preferencesHint);
				} else if (pin instanceof ActionInputPin) {
					createActionInputPin_CallOperationActionArgumentShape(pin, node, index, persisted, preferencesHint);
				} else {
					createInputPin_CallOperationActionArgumentShape(pin, node, index, persisted, preferencesHint);
				}
				index++;
			}
			InputPin target = ((CallOperationAction) domainElement).getTarget();
			if (target instanceof ValuePin) {
				createValuePin_CallOperationActionTargetShape(target, node, index, persisted, preferencesHint);
			} else if (target instanceof ActionInputPin) {
				createActionInputPin_CallOperationActionTargetShape(target, node, index, persisted, preferencesHint);
			} else if (target != null) {
				createInputPin_CallOperationActionTargetShape(target, node, index, persisted, preferencesHint);
			}
			index = 0;
			for (OutputPin pin : ((CallOperationAction) domainElement).getResults()) {
				createOutputPin_CallOperationActionResultShape(pin, node, index, persisted, preferencesHint);
			}
		}
	}
}
