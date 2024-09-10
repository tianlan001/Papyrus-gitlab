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
package org.eclipse.papyrus.uml.diagram.deployment.providers;

import org.eclipse.papyrus.uml.diagram.deployment.expressions.UMLOCLFactory;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.CommunicationPath;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
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
	public void init_Model_Shape(Model instance) {
		try {
			Object value_0 = name_Model_Shape(instance);
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
	public void init_Package_Shape(Package instance) {
		try {
			Object value_0 = name_Package_Shape(instance);
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
	public void init_ExecutionEnvironment_Shape(ExecutionEnvironment instance) {
		try {
			Object value_0 = name_ExecutionEnvironment_Shape(instance);
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
	public void init_Device_Shape(Device instance) {
		try {
			Object value_0 = name_Device_Shape(instance);
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
	public void init_Artifact_Shape(Artifact instance) {
		try {
			Object value_0 = name_Artifact_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Node_Shape(Node instance) {
		try {
			Object value_0 = name_Node_Shape(instance);
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
	public void init_DeploymentSpecification_Shape(DeploymentSpecification instance) {
		try {
			Object value_0 = name_DeploymentSpecification_Shape(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getDeploymentSpecification(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Model_Shape_CN(Model instance) {
		try {
			Object value_0 = name_Model_Shape_CN(instance);
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
	public void init_Package_Shape_CN(Package instance) {
		try {
			Object value_0 = name_Package_Shape_CN(instance);
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
	public void init_Device_Shape_CCN(Device instance) {
		try {
			Object value_0 = name_Device_Shape_CCN(instance);
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
	public void init_Device_Shape_CN(Device instance) {
		try {
			Object value_0 = name_Device_Shape_CN(instance);
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
	public void init_ExecutionEnvironment_Shape_CCN(ExecutionEnvironment instance) {
		try {
			Object value_0 = name_ExecutionEnvironment_Shape_CCN(instance);
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
	public void init_ExecutionEnvironment_Shape_CN(ExecutionEnvironment instance) {
		try {
			Object value_0 = name_ExecutionEnvironment_Shape_CN(instance);
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
	public void init_Node_Shape_CCN(Node instance) {
		try {
			Object value_0 = name_Node_Shape_CCN(instance);
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
	public void init_Node_Shape_CN(Node instance) {
		try {
			Object value_0 = name_Node_Shape_CN(instance);
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
	public void init_Artifact_Shape_CCN(Artifact instance) {
		try {
			Object value_0 = name_Artifact_Shape_CCN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Artifact_Shape_ACN(Artifact instance) {
		try {
			Object value_0 = name_Artifact_Shape_ACN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Artifact_Shape_CN(Artifact instance) {
		try {
			Object value_0 = name_Artifact_Shape_CN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getArtifact(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Comment_Shape_CN(Comment instance) {
		try {
			Object value_0 = body_Comment_Shape_CN(instance);
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
	public void init_Constraint_Shape_CN(Constraint instance) {
		try {
			Object value_0 = name_Constraint_Shape_CN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = specification_Constraint_Shape_CN(instance);
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
	public void init_DeploymentSpecification_Shape_CCN(DeploymentSpecification instance) {
		try {
			Object value_0 = name_DeploymentSpecification_Shape_CCN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getDeploymentSpecification(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DeploymentSpecification_Shape_CN(DeploymentSpecification instance) {
		try {
			Object value_0 = name_DeploymentSpecification_Shape_CN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getDeploymentSpecification(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_DeploymentSpecification_Shape_ACN(DeploymentSpecification instance) {
		try {
			Object value_0 = name_DeploymentSpecification_Shape_ACN(instance);
			if (value_0 != null) {
				instance.setName((String) value_0);
			}
			Object value_1 = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getDeploymentSpecification(), null).evaluate(instance);
			if (value_1 != null) {
				instance.setFileName((String) value_1);
			}
		} catch (RuntimeException e) {
			UMLDiagramEditorPlugin.getInstance().logError("Element initialization failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public void init_Deployment_Edge(Deployment instance) {
		try {
			Object value_0 = name_Deployment_Edge(instance);
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
	public void init_Manifestation_Edge(Manifestation instance) {
		try {
			Object value_0 = name_Manifestation_Edge(instance);
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
	public void init_Dependency_Edge(Dependency instance) {
		try {
			Object value_0 = name_Dependency_Edge(instance);
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
	public void init_Dependency_BranchEdge(Dependency instance) {
		try {
			Object value_0 = name_Dependency_BranchEdge(instance);
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
	public void init_CommunicationPath_Edge(CommunicationPath instance) {
		try {
			Object value_0 = name_CommunicationPath_Edge(instance);
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
	private String name_Model_Shape(Model it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Package_Shape(Package it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
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
		// Constraint specification init
		LiteralString value = UMLFactory.eINSTANCE.createLiteralString();
		value.setValue(""); //$NON-NLS-1$
		return value;
	}

	/**
	 * @generated
	 */
	private String body_Comment_Shape(Comment it) {
		// Comment body init
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String name_ExecutionEnvironment_Shape(ExecutionEnvironment it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Device_Shape(Device it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Artifact_Shape(Artifact it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Node_Shape(Node it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_DeploymentSpecification_Shape(DeploymentSpecification it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Model_Shape_CN(Model it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Package_Shape_CN(Package it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Device_Shape_CCN(Device it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Device_Shape_CN(Device it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_ExecutionEnvironment_Shape_CCN(ExecutionEnvironment it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_ExecutionEnvironment_Shape_CN(ExecutionEnvironment it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Node_Shape_CCN(Node it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Node_Shape_CN(Node it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Artifact_Shape_CCN(Artifact it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Artifact_Shape_ACN(Artifact it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Artifact_Shape_CN(Artifact it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String body_Comment_Shape_CN(Comment it) {
		// Comment body init
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String name_Constraint_Shape_CN(Constraint it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private ValueSpecification specification_Constraint_Shape_CN(Constraint it) {
		// Constraint specification init
		LiteralString value = UMLFactory.eINSTANCE.createLiteralString();
		value.setValue(""); //$NON-NLS-1$
		return value;
	}

	/**
	 * @generated
	 */
	private String name_DeploymentSpecification_Shape_CCN(DeploymentSpecification it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_DeploymentSpecification_Shape_CN(DeploymentSpecification it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_DeploymentSpecification_Shape_ACN(DeploymentSpecification it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Deployment_Edge(Deployment it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Manifestation_Edge(Manifestation it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Dependency_Edge(Dependency it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_Dependency_BranchEdge(Dependency it) {
		return NamedElementUtil.getDefaultNameWithIncrement(it, it.getOwner().eContents());
	}

	/**
	 * @generated
	 */
	private String name_CommunicationPath_Edge(CommunicationPath it) {
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
