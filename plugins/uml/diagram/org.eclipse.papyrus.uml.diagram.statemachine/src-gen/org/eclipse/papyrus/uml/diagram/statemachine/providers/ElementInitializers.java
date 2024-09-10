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
package org.eclipse.papyrus.uml.diagram.statemachine.providers;

import org.eclipse.papyrus.uml.diagram.statemachine.custom.expressions.UMLAbstractExpression;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConnectionPointReference;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * @generated
 */
public class ElementInitializers {

	protected ElementInitializers() {
		// use #getInstance to access cached instance
	}

	/**
	 * @generated
	 */
	public void init_StateMachine_Shape(StateMachine instance) {
		try {
			Object value_0 = name_StateMachine_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Region newInstance_1_0 = UMLFactory.eINSTANCE.createRegion();
			instance.getRegions()
					.add(newInstance_1_0);
			Object value_1_0_0 = name_region_StateMachine_Shape(newInstance_1_0);
			if (value_1_0_0 != null) {
				newInstance_1_0.setName((String) value_1_0_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_State_Shape_TN(State instance) {
		try {
			Object value_0 = name_State_Shape_TN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Region_Shape(Region instance) {
		try {
			Object value_0 = name_Region_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_FinalState_Shape(FinalState instance) {
		try {
			Object value_0 = name_FinalState_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_State_Shape(State instance) {
		try {
			Object value_0 = name_State_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_InitialShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_InitialShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_InitialShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_JoinShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_JoinShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_JoinShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_ForkShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_ForkShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_ForkShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_ChoiceShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_ChoiceShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_ChoiceShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_JunctionShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_JunctionShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_JunctionShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_ShallowHistoryShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_ShallowHistoryShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_ShallowHistoryShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_DeepHistoryShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_DeepHistoryShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_DeepHistoryShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_TerminateShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_TerminateShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_TerminateShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_EntryPointShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_EntryPointShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_EntryPointShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Pseudostate_ExitPointShape(Pseudostate instance) {
		try {
			Object value_0 = kind_Pseudostate_ExitPointShape(instance);
			if (value_0 != null) {
				value_0 = UMLAbstractExpression.performCast(value_0, UMLPackage.eINSTANCE.getPseudostateKind());
				instance.setKind((PseudostateKind) value_0);
			}
			Object value_1 = name_Pseudostate_ExitPointShape(instance);
			if (value_1 != null) {
				instance.setName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_ConnectionPointReference_Shape(ConnectionPointReference instance) {
		try {
			Object value_0 = name_ConnectionPointReference_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Comment_Shape(Comment instance) {
		try {
			Object value_0 = body_Comment_Shape(instance);
			if (value_0 != null) {
				instance.setBody((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Constraint_Shape(Constraint instance) {
		try {
			Object value_0 = name_Constraint_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = specification_Constraint_Shape(instance);
			if (value_1 != null) {
				instance.setSpecification((ValueSpecification) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Transition_InternalTransitionLabel(Transition instance) {
		try {
			Object value_0 = name_Transition_InternalTransitionLabel(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Transition_Edge(Transition instance) {
		try {
			Object value_0 = name_Transition_Edge(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String name_StateMachine_Shape(StateMachine it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_region_StateMachine_Shape(Region it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_State_Shape_TN(State it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Region_Shape(Region it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_FinalState_Shape(FinalState it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_State_Shape(State it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_InitialShape(Pseudostate it) {
		return PseudostateKind.INITIAL_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_InitialShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_JoinShape(Pseudostate it) {
		return PseudostateKind.JOIN_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_JoinShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_ForkShape(Pseudostate it) {
		return PseudostateKind.FORK_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_ForkShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_ChoiceShape(Pseudostate it) {
		return PseudostateKind.CHOICE_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_ChoiceShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_JunctionShape(Pseudostate it) {
		return PseudostateKind.JUNCTION_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_JunctionShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_ShallowHistoryShape(Pseudostate it) {
		return PseudostateKind.SHALLOW_HISTORY_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_ShallowHistoryShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_DeepHistoryShape(Pseudostate it) {
		return PseudostateKind.DEEP_HISTORY_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_DeepHistoryShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_TerminateShape(Pseudostate it) {
		return PseudostateKind.TERMINATE_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_TerminateShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_EntryPointShape(Pseudostate it) {
		return PseudostateKind.ENTRY_POINT_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_EntryPointShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private PseudostateKind kind_Pseudostate_ExitPointShape(Pseudostate it) {
		return PseudostateKind.EXIT_POINT_LITERAL;
	}

	/**
	 * @generated
	 */
	private String name_Pseudostate_ExitPointShape(Pseudostate it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_ConnectionPointReference_Shape(ConnectionPointReference it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String body_Comment_Shape(Comment it) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String name_Constraint_Shape(Constraint it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private ValueSpecification specification_Constraint_Shape(Constraint it) {
		LiteralString literalString = UMLFactory.eINSTANCE.createLiteralString();
		literalString.setValue(""); //$NON-NLS-1$
		return literalString;
	}

	/**
	 * @generated
	 */
	private String name_Transition_InternalTransitionLabel(Transition it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Transition_Edge(Transition it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	public static ElementInitializers getInstance() {
		ElementInitializers cached = UMLDiagramEditorPlugin.getInstance().getElementInitializers();
		if (cached == null) {
			UMLDiagramEditorPlugin.getInstance().setElementInitializers(cached = new ElementInitializers());
		}
		return cached;
	}
}
