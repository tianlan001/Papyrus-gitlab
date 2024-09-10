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
package org.eclipse.papyrus.uml.diagram.composite.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.parser.CommentParser;
import org.eclipse.papyrus.uml.diagram.common.parser.ConstraintParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.composite.custom.parsers.RoleBindingRoleNameParser;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.composite.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser activity_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_NameLabel_Parser() {
		if (activity_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			activity_NameLabel_Parser = parser;
		}
		return activity_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser activity_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_FloatingNameLabel_Parser() {
		if (activity_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			activity_FloatingNameLabel_Parser = parser;
		}
		return activity_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interaction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInteraction_NameLabel_Parser() {
		if (interaction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			interaction_NameLabel_Parser = parser;
		}
		return interaction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interaction_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInteraction_FloatingNameLabel_Parser() {
		if (interaction_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interaction_FloatingNameLabel_Parser = parser;
		}
		return interaction_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser protocolStateMachine_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProtocolStateMachine_NameLabel_Parser() {
		if (protocolStateMachine_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			protocolStateMachine_NameLabel_Parser = parser;
		}
		return protocolStateMachine_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser protocolStateMachine_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProtocolStateMachine_FloatingNameLabel_Parser() {
		if (protocolStateMachine_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			protocolStateMachine_FloatingNameLabel_Parser = parser;
		}
		return protocolStateMachine_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stateMachine_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStateMachine_NameLabel_Parser() {
		if (stateMachine_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			stateMachine_NameLabel_Parser = parser;
		}
		return stateMachine_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stateMachine_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStateMachine_FloatingNameLabel_Parser() {
		if (stateMachine_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			stateMachine_FloatingNameLabel_Parser = parser;
		}
		return stateMachine_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser functionBehavior_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getFunctionBehavior_NameLabel_Parser() {
		if (functionBehavior_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			functionBehavior_NameLabel_Parser = parser;
		}
		return functionBehavior_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser functionBehavior_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getFunctionBehavior_FloatingNameLabel_Parser() {
		if (functionBehavior_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			functionBehavior_FloatingNameLabel_Parser = parser;
		}
		return functionBehavior_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueBehavior_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueBehavior_NameLabel_Parser() {
		if (opaqueBehavior_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			opaqueBehavior_NameLabel_Parser = parser;
		}
		return opaqueBehavior_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueBehavior_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueBehavior_FloatingNameLabel_Parser() {
		if (opaqueBehavior_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			opaqueBehavior_FloatingNameLabel_Parser = parser;
		}
		return opaqueBehavior_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_NameLabel_Parser() {
		if (component_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			component_NameLabel_Parser = parser;
		}
		return component_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_FloatingNameLabel_Parser() {
		if (component_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			component_FloatingNameLabel_Parser = parser;
		}
		return component_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser device_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDevice_NameLabel_Parser() {
		if (device_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			device_NameLabel_Parser = parser;
		}
		return device_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser device_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDevice_FloatingNameLabel_Parser() {
		if (device_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			device_FloatingNameLabel_Parser = parser;
		}
		return device_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser executionEnvironment_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExecutionEnvironment_NameLabel_Parser() {
		if (executionEnvironment_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			executionEnvironment_NameLabel_Parser = parser;
		}
		return executionEnvironment_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser executionEnvironment_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExecutionEnvironment_FloatingNameLabel_Parser() {
		if (executionEnvironment_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			executionEnvironment_FloatingNameLabel_Parser = parser;
		}
		return executionEnvironment_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser node_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getNode_NameLabel_Parser() {
		if (node_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			node_NameLabel_Parser = parser;
		}
		return node_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser node_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getNode_FloatingNameLabel_Parser() {
		if (node_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			node_FloatingNameLabel_Parser = parser;
		}
		return node_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_NameLabel_Parser() {
		if (class_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			class_NameLabel_Parser = parser;
		}
		return class_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_FloatingNameLabel_Parser() {
		if (class_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			class_FloatingNameLabel_Parser = parser;
		}
		return class_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser collaboration_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCollaboration_NameLabel_Parser() {
		if (collaboration_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			collaboration_NameLabel_Parser = parser;
		}
		return collaboration_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser collaboration_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCollaboration_FloatingNameLabel_Parser() {
		if (collaboration_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			collaboration_FloatingNameLabel_Parser = parser;
		}
		return collaboration_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_NameLabel_Parser() {
		if (interface_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			interface_NameLabel_Parser = parser;
		}
		return interface_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_FloatingNameLabel_Parser() {
		if (interface_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_FloatingNameLabel_Parser = parser;
		}
		return interface_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser primitiveType_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPrimitiveType_NameLabel_Parser() {
		if (primitiveType_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			primitiveType_NameLabel_Parser = parser;
		}
		return primitiveType_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser primitiveType_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPrimitiveType_FloatingNameLabel_Parser() {
		if (primitiveType_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			primitiveType_FloatingNameLabel_Parser = parser;
		}
		return primitiveType_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumeration_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumeration_NameLabel_Parser() {
		if (enumeration_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			enumeration_NameLabel_Parser = parser;
		}
		return enumeration_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumeration_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumeration_FloatingNameLabel_Parser() {
		if (enumeration_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			enumeration_FloatingNameLabel_Parser = parser;
		}
		return enumeration_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataType_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataType_NameLabel_Parser() {
		if (dataType_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			dataType_NameLabel_Parser = parser;
		}
		return dataType_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataType_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataType_FloatingNameLabel_Parser() {
		if (dataType_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dataType_FloatingNameLabel_Parser = parser;
		}
		return dataType_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser actor_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_NameLabel_Parser() {
		if (actor_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			actor_NameLabel_Parser = parser;
		}
		return actor_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser actor_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_FloatingNameLabel_Parser() {
		if (actor_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			actor_FloatingNameLabel_Parser = parser;
		}
		return actor_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser deploymentSpecification_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_NameLabel_Parser() {
		if (deploymentSpecification_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			deploymentSpecification_NameLabel_Parser = parser;
		}
		return deploymentSpecification_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser deploymentSpecification_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_FloatingNameLabel_Parser() {
		if (deploymentSpecification_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			deploymentSpecification_FloatingNameLabel_Parser = parser;
		}
		return deploymentSpecification_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_NameLabel_Parser() {
		if (artifact_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			artifact_NameLabel_Parser = parser;
		}
		return artifact_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_FloatingNameLabel_Parser() {
		if (artifact_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			artifact_FloatingNameLabel_Parser = parser;
		}
		return artifact_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser informationItem_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInformationItem_NameLabel_Parser() {
		if (informationItem_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			informationItem_NameLabel_Parser = parser;
		}
		return informationItem_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser informationItem_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInformationItem_FloatingNameLabel_Parser() {
		if (informationItem_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			informationItem_FloatingNameLabel_Parser = parser;
		}
		return informationItem_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signal_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSignal_NameLabel_Parser() {
		if (signal_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			signal_NameLabel_Parser = parser;
		}
		return signal_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signal_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSignal_FloatingNameLabel_Parser() {
		if (signal_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			signal_FloatingNameLabel_Parser = parser;
		}
		return signal_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser useCase_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getUseCase_NameLabel_Parser() {
		if (useCase_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			useCase_NameLabel_Parser = parser;
		}
		return useCase_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser useCase_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getUseCase_FloatingNameLabel_Parser() {
		if (useCase_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			useCase_FloatingNameLabel_Parser = parser;
		}
		return useCase_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signalEvent_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSignalEvent_NameLabel_Parser() {
		if (signalEvent_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			signalEvent_NameLabel_Parser = parser;
		}
		return signalEvent_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signalEvent_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSignalEvent_FloatingNameLabel_Parser() {
		if (signalEvent_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			signalEvent_FloatingNameLabel_Parser = parser;
		}
		return signalEvent_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser callEvent_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCallEvent_NameLabel_Parser() {
		if (callEvent_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			callEvent_NameLabel_Parser = parser;
		}
		return callEvent_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser callEvent_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCallEvent_FloatingNameLabel_Parser() {
		if (callEvent_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			callEvent_FloatingNameLabel_Parser = parser;
		}
		return callEvent_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser anyReceiveEvent_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAnyReceiveEvent_NameLabel_Parser() {
		if (anyReceiveEvent_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			anyReceiveEvent_NameLabel_Parser = parser;
		}
		return anyReceiveEvent_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser anyReceiveEvent_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAnyReceiveEvent_FloatingNameLabel_Parser() {
		if (anyReceiveEvent_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			anyReceiveEvent_FloatingNameLabel_Parser = parser;
		}
		return anyReceiveEvent_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser changeEvent_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getChangeEvent_NameLabel_Parser() {
		if (changeEvent_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			changeEvent_NameLabel_Parser = parser;
		}
		return changeEvent_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser changeEvent_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getChangeEvent_FloatingNameLabel_Parser() {
		if (changeEvent_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			changeEvent_FloatingNameLabel_Parser = parser;
		}
		return changeEvent_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeEvent_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeEvent_NameLabel_Parser() {
		if (timeEvent_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			timeEvent_NameLabel_Parser = parser;
		}
		return timeEvent_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeEvent_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeEvent_FloatingNameLabel_Parser() {
		if (timeEvent_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeEvent_FloatingNameLabel_Parser = parser;
		}
		return timeEvent_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationObservation_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationObservation_NameLabel_Parser() {
		if (durationObservation_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationObservation_NameLabel_Parser = parser;
		}
		return durationObservation_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser durationObservation_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationObservation_StereotypeLabel_Parser() {
		if (durationObservation_StereotypeLabel_Parser == null) {
			durationObservation_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return durationObservation_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeObservation_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeObservation_NameLabel_Parser() {
		if (timeObservation_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeObservation_NameLabel_Parser = parser;
		}
		return timeObservation_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser timeObservation_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeObservation_StereotypeLabel_Parser() {
		if (timeObservation_StereotypeLabel_Parser == null) {
			timeObservation_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return timeObservation_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalBoolean_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralBoolean_NameLabel_Parser() {
		if (literalBoolean_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			literalBoolean_NameLabel_Parser = parser;
		}
		return literalBoolean_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalBoolean_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralBoolean_FloatingNameLabel_Parser() {
		if (literalBoolean_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			literalBoolean_FloatingNameLabel_Parser = parser;
		}
		return literalBoolean_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalInteger_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralInteger_NameLabel_Parser() {
		if (literalInteger_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			literalInteger_NameLabel_Parser = parser;
		}
		return literalInteger_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalInteger_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralInteger_FloatingNameLabel_Parser() {
		if (literalInteger_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			literalInteger_FloatingNameLabel_Parser = parser;
		}
		return literalInteger_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalNull_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralNull_NameLabel_Parser() {
		if (literalNull_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			literalNull_NameLabel_Parser = parser;
		}
		return literalNull_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalNull_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralNull_FloatingNameLabel_Parser() {
		if (literalNull_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			literalNull_FloatingNameLabel_Parser = parser;
		}
		return literalNull_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalString_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralString_NameLabel_Parser() {
		if (literalString_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			literalString_NameLabel_Parser = parser;
		}
		return literalString_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalString_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralString_FloatingNameLabel_Parser() {
		if (literalString_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			literalString_FloatingNameLabel_Parser = parser;
		}
		return literalString_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalUnlimitedNatural_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralUnlimitedNatural_NameLabel_Parser() {
		if (literalUnlimitedNatural_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			literalUnlimitedNatural_NameLabel_Parser = parser;
		}
		return literalUnlimitedNatural_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser literalUnlimitedNatural_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getLiteralUnlimitedNatural_FloatingNameLabel_Parser() {
		if (literalUnlimitedNatural_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			literalUnlimitedNatural_FloatingNameLabel_Parser = parser;
		}
		return literalUnlimitedNatural_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stringExpression_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStringExpression_NameLabel_Parser() {
		if (stringExpression_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			stringExpression_NameLabel_Parser = parser;
		}
		return stringExpression_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stringExpression_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStringExpression_FloatingNameLabel_Parser() {
		if (stringExpression_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			stringExpression_FloatingNameLabel_Parser = parser;
		}
		return stringExpression_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueExpression_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueExpression_NameLabel_Parser() {
		if (opaqueExpression_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			opaqueExpression_NameLabel_Parser = parser;
		}
		return opaqueExpression_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueExpression_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueExpression_FloatingNameLabel_Parser() {
		if (opaqueExpression_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			opaqueExpression_FloatingNameLabel_Parser = parser;
		}
		return opaqueExpression_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeExpression_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeExpression_NameLabel_Parser() {
		if (timeExpression_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			timeExpression_NameLabel_Parser = parser;
		}
		return timeExpression_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeExpression_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeExpression_FloatingNameLabel_Parser() {
		if (timeExpression_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeExpression_FloatingNameLabel_Parser = parser;
		}
		return timeExpression_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser expression_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExpression_NameLabel_Parser() {
		if (expression_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			expression_NameLabel_Parser = parser;
		}
		return expression_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser expression_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExpression_FloatingNameLabel_Parser() {
		if (expression_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			expression_FloatingNameLabel_Parser = parser;
		}
		return expression_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser duration_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDuration_NameLabel_Parser() {
		if (duration_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			duration_NameLabel_Parser = parser;
		}
		return duration_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser duration_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDuration_FloatingNameLabel_Parser() {
		if (duration_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			duration_FloatingNameLabel_Parser = parser;
		}
		return duration_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeInterval_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeInterval_NameLabel_Parser() {
		if (timeInterval_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			timeInterval_NameLabel_Parser = parser;
		}
		return timeInterval_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeInterval_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeInterval_FloatingNameLabel_Parser() {
		if (timeInterval_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeInterval_FloatingNameLabel_Parser = parser;
		}
		return timeInterval_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationInterval_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationInterval_NameLabel_Parser() {
		if (durationInterval_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			durationInterval_NameLabel_Parser = parser;
		}
		return durationInterval_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationInterval_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationInterval_FloatingNameLabel_Parser() {
		if (durationInterval_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationInterval_FloatingNameLabel_Parser = parser;
		}
		return durationInterval_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interval_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterval_NameLabel_Parser() {
		if (interval_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			interval_NameLabel_Parser = parser;
		}
		return interval_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interval_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterval_FloatingNameLabel_Parser() {
		if (interval_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interval_FloatingNameLabel_Parser = parser;
		}
		return interval_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser instanceValue_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceValue_NameLabel_Parser() {
		if (instanceValue_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			instanceValue_NameLabel_Parser = parser;
		}
		return instanceValue_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser instanceValue_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceValue_FloatingNameLabel_Parser() {
		if (instanceValue_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			instanceValue_FloatingNameLabel_Parser = parser;
		}
		return instanceValue_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private CommentParser comment_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComment_BodyLabel_Parser() {
		if (comment_BodyLabel_Parser == null) {
			comment_BodyLabel_Parser = new CommentParser();
		}
		return comment_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationConstraint_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_NameLabel_Parser() {
		if (durationConstraint_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationConstraint_NameLabel_Parser = parser;
		}
		return durationConstraint_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser durationConstraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_BodyLabel_Parser() {
		if (durationConstraint_BodyLabel_Parser == null) {
			durationConstraint_BodyLabel_Parser = new ConstraintParser();
		}
		return durationConstraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeConstraint_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_NameLabel_Parser() {
		if (timeConstraint_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeConstraint_NameLabel_Parser = parser;
		}
		return timeConstraint_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser timeConstraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_BodyLabel_Parser() {
		if (timeConstraint_BodyLabel_Parser == null) {
			timeConstraint_BodyLabel_Parser = new ConstraintParser();
		}
		return timeConstraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser intervalConstraint_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_NameLabel_Parser() {
		if (intervalConstraint_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			intervalConstraint_NameLabel_Parser = parser;
		}
		return intervalConstraint_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser intervalConstraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_BodyLabel_Parser() {
		if (intervalConstraint_BodyLabel_Parser == null) {
			intervalConstraint_BodyLabel_Parser = new ConstraintParser();
		}
		return intervalConstraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interactionConstraint_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInteractionConstraint_NameLabel_Parser() {
		if (interactionConstraint_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interactionConstraint_NameLabel_Parser = parser;
		}
		return interactionConstraint_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser interactionConstraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInteractionConstraint_BodyLabel_Parser() {
		if (interactionConstraint_BodyLabel_Parser == null) {
			interactionConstraint_BodyLabel_Parser = new ConstraintParser();
		}
		return interactionConstraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser constraint_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_NameLabel_Parser() {
		if (constraint_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			constraint_NameLabel_Parser = parser;
		}
		return constraint_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser constraint_BodyLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_BodyLabel_Parser() {
		if (constraint_BodyLabel_Parser == null) {
			constraint_BodyLabel_Parser = new ConstraintParser();
		}
		return constraint_BodyLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser port_BehaviorFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPort_BehaviorFloatingNameLabel_Parser() {
		if (port_BehaviorFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			port_BehaviorFloatingNameLabel_Parser = parser;
		}
		return port_BehaviorFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser port_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPort_NameLabel_Parser() {
		if (port_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			port_NameLabel_Parser = parser;
		}
		return port_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser port_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPort_StereotypeLabel_Parser() {
		if (port_StereotypeLabel_Parser == null) {
			port_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return port_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser parameter_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getParameter_NameLabel_Parser() {
		if (parameter_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parameter_NameLabel_Parser = parser;
		}
		return parameter_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser parameter_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getParameter_StereotypeLabel_Parser() {
		if (parameter_StereotypeLabel_Parser == null) {
			parameter_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return parameter_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_NameLabel_Parser() {
		if (property_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_NameLabel_Parser = parser;
		}
		return property_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_FloatingNameLabel_Parser() {
		if (property_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_FloatingNameLabel_Parser = parser;
		}
		return property_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser connectableElement_CollaborationRoleNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnectableElement_CollaborationRoleNameLabel_Parser() {
		if (connectableElement_CollaborationRoleNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			connectableElement_CollaborationRoleNameLabel_Parser = parser;
		}
		return connectableElement_CollaborationRoleNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser connectableElement_CollaborationRoleFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnectableElement_CollaborationRoleFloatingNameLabel_Parser() {
		if (connectableElement_CollaborationRoleFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			connectableElement_CollaborationRoleFloatingNameLabel_Parser = parser;
		}
		return connectableElement_CollaborationRoleFloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser collaborationUse_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCollaborationUse_NameLabel_Parser() {
		if (collaborationUse_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			collaborationUse_NameLabel_Parser = parser;
		}
		return collaborationUse_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser collaborationUse_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCollaborationUse_FloatingNameLabel_Parser() {
		if (collaborationUse_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			collaborationUse_FloatingNameLabel_Parser = parser;
		}
		return collaborationUse_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser activity_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_NameLabel_CN_Parser() {
		if (activity_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			activity_NameLabel_CN_Parser = parser;
		}
		return activity_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser activity_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getActivity_FloatingNameLabel_CN_Parser() {
		if (activity_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			activity_FloatingNameLabel_CN_Parser = parser;
		}
		return activity_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interaction_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInteraction_NameLabel_CN_Parser() {
		if (interaction_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			interaction_NameLabel_CN_Parser = parser;
		}
		return interaction_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interaction_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInteraction_FloatingNameLabel_CN_Parser() {
		if (interaction_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interaction_FloatingNameLabel_CN_Parser = parser;
		}
		return interaction_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser protocolStateMachine_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getProtocolStateMachine_NameLabel_CN_Parser() {
		if (protocolStateMachine_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			protocolStateMachine_NameLabel_CN_Parser = parser;
		}
		return protocolStateMachine_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser protocolStateMachine_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getProtocolStateMachine_FloatingNameLabel_CN_Parser() {
		if (protocolStateMachine_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			protocolStateMachine_FloatingNameLabel_CN_Parser = parser;
		}
		return protocolStateMachine_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stateMachine_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getStateMachine_NameLabel_CN_Parser() {
		if (stateMachine_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			stateMachine_NameLabel_CN_Parser = parser;
		}
		return stateMachine_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stateMachine_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getStateMachine_FloatingNameLabel_CN_Parser() {
		if (stateMachine_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			stateMachine_FloatingNameLabel_CN_Parser = parser;
		}
		return stateMachine_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser functionBehavior_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getFunctionBehavior_NameLabel_CN_Parser() {
		if (functionBehavior_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			functionBehavior_NameLabel_CN_Parser = parser;
		}
		return functionBehavior_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser functionBehavior_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getFunctionBehavior_FloatingNameLabel_CN_Parser() {
		if (functionBehavior_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			functionBehavior_FloatingNameLabel_CN_Parser = parser;
		}
		return functionBehavior_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueBehavior_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueBehavior_NameLabel_CN_Parser() {
		if (opaqueBehavior_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			opaqueBehavior_NameLabel_CN_Parser = parser;
		}
		return opaqueBehavior_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser opaqueBehavior_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getOpaqueBehavior_FloatingNameLabel_CN_Parser() {
		if (opaqueBehavior_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			opaqueBehavior_FloatingNameLabel_CN_Parser = parser;
		}
		return opaqueBehavior_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_NameLabel_CN_Parser() {
		if (component_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			component_NameLabel_CN_Parser = parser;
		}
		return component_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_FloatingNameLabel_CN_Parser() {
		if (component_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			component_FloatingNameLabel_CN_Parser = parser;
		}
		return component_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser device_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDevice_NameLabel_CN_Parser() {
		if (device_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			device_NameLabel_CN_Parser = parser;
		}
		return device_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser device_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDevice_FloatingNameLabel_CN_Parser() {
		if (device_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			device_FloatingNameLabel_CN_Parser = parser;
		}
		return device_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser executionEnvironment_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getExecutionEnvironment_NameLabel_CN_Parser() {
		if (executionEnvironment_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			executionEnvironment_NameLabel_CN_Parser = parser;
		}
		return executionEnvironment_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser executionEnvironment_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getExecutionEnvironment_FloatingNameLabel_CN_Parser() {
		if (executionEnvironment_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			executionEnvironment_FloatingNameLabel_CN_Parser = parser;
		}
		return executionEnvironment_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser node_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getNode_NameLabel_CN_Parser() {
		if (node_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			node_NameLabel_CN_Parser = parser;
		}
		return node_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser node_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getNode_FloatingNameLabel_CN_Parser() {
		if (node_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			node_FloatingNameLabel_CN_Parser = parser;
		}
		return node_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_NameLabel_CN_Parser() {
		if (class_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			class_NameLabel_CN_Parser = parser;
		}
		return class_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_FloatingNameLabel_CN_Parser() {
		if (class_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			class_FloatingNameLabel_CN_Parser = parser;
		}
		return class_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser collaboration_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getCollaboration_NameLabel_CN_Parser() {
		if (collaboration_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			collaboration_NameLabel_CN_Parser = parser;
		}
		return collaboration_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser collaboration_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getCollaboration_FloatingNameLabel_CN_Parser() {
		if (collaboration_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			collaboration_FloatingNameLabel_CN_Parser = parser;
		}
		return collaboration_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_NameLabel_CN_Parser() {
		if (interface_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			interface_NameLabel_CN_Parser = parser;
		}
		return interface_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_FloatingNameLabel_CN_Parser() {
		if (interface_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_FloatingNameLabel_CN_Parser = parser;
		}
		return interface_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser primitiveType_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getPrimitiveType_NameLabel_CN_Parser() {
		if (primitiveType_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			primitiveType_NameLabel_CN_Parser = parser;
		}
		return primitiveType_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser primitiveType_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getPrimitiveType_FloatingNameLabel_CN_Parser() {
		if (primitiveType_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			primitiveType_FloatingNameLabel_CN_Parser = parser;
		}
		return primitiveType_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumeration_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumeration_NameLabel_CN_Parser() {
		if (enumeration_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			enumeration_NameLabel_CN_Parser = parser;
		}
		return enumeration_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumeration_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumeration_FloatingNameLabel_CN_Parser() {
		if (enumeration_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			enumeration_FloatingNameLabel_CN_Parser = parser;
		}
		return enumeration_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataType_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDataType_NameLabel_CN_Parser() {
		if (dataType_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			dataType_NameLabel_CN_Parser = parser;
		}
		return dataType_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataType_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDataType_FloatingNameLabel_CN_Parser() {
		if (dataType_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dataType_FloatingNameLabel_CN_Parser = parser;
		}
		return dataType_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser actor_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_NameLabel_CN_Parser() {
		if (actor_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			actor_NameLabel_CN_Parser = parser;
		}
		return actor_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser actor_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_FloatingNameLabel_CN_Parser() {
		if (actor_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			actor_FloatingNameLabel_CN_Parser = parser;
		}
		return actor_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser deploymentSpecification_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_NameLabel_CN_Parser() {
		if (deploymentSpecification_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			deploymentSpecification_NameLabel_CN_Parser = parser;
		}
		return deploymentSpecification_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser deploymentSpecification_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_FloatingNameLabel_CN_Parser() {
		if (deploymentSpecification_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			deploymentSpecification_FloatingNameLabel_CN_Parser = parser;
		}
		return deploymentSpecification_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_NameLabel_CN_Parser() {
		if (artifact_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			artifact_NameLabel_CN_Parser = parser;
		}
		return artifact_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_FloatingNameLabel_CN_Parser() {
		if (artifact_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			artifact_FloatingNameLabel_CN_Parser = parser;
		}
		return artifact_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser informationItem_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInformationItem_NameLabel_CN_Parser() {
		if (informationItem_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			informationItem_NameLabel_CN_Parser = parser;
		}
		return informationItem_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser informationItem_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInformationItem_FloatingNameLabel_CN_Parser() {
		if (informationItem_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			informationItem_FloatingNameLabel_CN_Parser = parser;
		}
		return informationItem_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signal_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getSignal_NameLabel_CN_Parser() {
		if (signal_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			signal_NameLabel_CN_Parser = parser;
		}
		return signal_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signal_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getSignal_FloatingNameLabel_CN_Parser() {
		if (signal_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			signal_FloatingNameLabel_CN_Parser = parser;
		}
		return signal_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser useCase_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getUseCase_NameLabel_CN_Parser() {
		if (useCase_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			useCase_NameLabel_CN_Parser = parser;
		}
		return useCase_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser useCase_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getUseCase_FloatingNameLabel_CN_Parser() {
		if (useCase_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			useCase_FloatingNameLabel_CN_Parser = parser;
		}
		return useCase_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private CommentParser comment_BodyLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getComment_BodyLabel_CN_Parser() {
		if (comment_BodyLabel_CN_Parser == null) {
			comment_BodyLabel_CN_Parser = new CommentParser();
		}
		return comment_BodyLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationConstraint_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_NameLabel_CN_Parser() {
		if (durationConstraint_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationConstraint_NameLabel_CN_Parser = parser;
		}
		return durationConstraint_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser durationConstraint_BodyLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationConstraint_BodyLabel_CN_Parser() {
		if (durationConstraint_BodyLabel_CN_Parser == null) {
			durationConstraint_BodyLabel_CN_Parser = new ConstraintParser();
		}
		return durationConstraint_BodyLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser timeConstraint_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_NameLabel_CN_Parser() {
		if (timeConstraint_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeConstraint_NameLabel_CN_Parser = parser;
		}
		return timeConstraint_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser timeConstraint_BodyLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeConstraint_BodyLabel_CN_Parser() {
		if (timeConstraint_BodyLabel_CN_Parser == null) {
			timeConstraint_BodyLabel_CN_Parser = new ConstraintParser();
		}
		return timeConstraint_BodyLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser intervalConstraint_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_NameLabel_CN_Parser() {
		if (intervalConstraint_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			intervalConstraint_NameLabel_CN_Parser = parser;
		}
		return intervalConstraint_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser intervalConstraint_BodyLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getIntervalConstraint_BodyLabel_CN_Parser() {
		if (intervalConstraint_BodyLabel_CN_Parser == null) {
			intervalConstraint_BodyLabel_CN_Parser = new ConstraintParser();
		}
		return intervalConstraint_BodyLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interactionConstraint_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInteractionConstraint_NameLabel_CN_Parser() {
		if (interactionConstraint_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interactionConstraint_NameLabel_CN_Parser = parser;
		}
		return interactionConstraint_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser interactionConstraint_BodyLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInteractionConstraint_BodyLabel_CN_Parser() {
		if (interactionConstraint_BodyLabel_CN_Parser == null) {
			interactionConstraint_BodyLabel_CN_Parser = new ConstraintParser();
		}
		return interactionConstraint_BodyLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser constraint_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_NameLabel_CN_Parser() {
		if (constraint_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			constraint_NameLabel_CN_Parser = parser;
		}
		return constraint_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser constraint_BodyLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_BodyLabel_CN_Parser() {
		if (constraint_BodyLabel_CN_Parser == null) {
			constraint_BodyLabel_CN_Parser = new ConstraintParser();
		}
		return constraint_BodyLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_AttributeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_AttributeLabel_Parser() {
		if (property_AttributeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			property_AttributeLabel_Parser = parser;
		}
		return property_AttributeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser operation_OperationLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOperation_OperationLabel_Parser() {
		if (operation_OperationLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			operation_OperationLabel_Parser = parser;
		}
		return operation_OperationLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumerationLiteral_LiteralLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumerationLiteral_LiteralLabel_Parser() {
		if (enumerationLiteral_LiteralLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("{0}  "); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			enumerationLiteral_LiteralLabel_Parser = parser;
		}
		return enumerationLiteral_LiteralLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser componentRealization_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComponentRealization_NameLabel_Parser() {
		if (componentRealization_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			componentRealization_NameLabel_Parser = parser;
		}
		return componentRealization_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser componentRealization_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComponentRealization_StereotypeLabel_Parser() {
		if (componentRealization_StereotypeLabel_Parser == null) {
			componentRealization_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return componentRealization_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interfaceRealization_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterfaceRealization_NameLabel_Parser() {
		if (interfaceRealization_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interfaceRealization_NameLabel_Parser = parser;
		}
		return interfaceRealization_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser interfaceRealization_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterfaceRealization_StereotypeLabel_Parser() {
		if (interfaceRealization_StereotypeLabel_Parser == null) {
			interfaceRealization_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return interfaceRealization_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser substitution_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSubstitution_NameLabel_Parser() {
		if (substitution_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			substitution_NameLabel_Parser = parser;
		}
		return substitution_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser substitution_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSubstitution_StereotypeLabel_Parser() {
		if (substitution_StereotypeLabel_Parser == null) {
			substitution_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return substitution_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser realization_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getRealization_NameLabel_Parser() {
		if (realization_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			realization_NameLabel_Parser = parser;
		}
		return realization_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser realization_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getRealization_StereotypeLabel_Parser() {
		if (realization_StereotypeLabel_Parser == null) {
			realization_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return realization_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser manifestation_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getManifestation_NameLabel_Parser() {
		if (manifestation_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			manifestation_NameLabel_Parser = parser;
		}
		return manifestation_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser manifestation_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getManifestation_StereotypeLabel_Parser() {
		if (manifestation_StereotypeLabel_Parser == null) {
			manifestation_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return manifestation_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser abstraction_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAbstraction_NameLabel_Parser() {
		if (abstraction_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			abstraction_NameLabel_Parser = parser;
		}
		return abstraction_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser abstraction_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAbstraction_StereotypeLabel_Parser() {
		if (abstraction_StereotypeLabel_Parser == null) {
			abstraction_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return abstraction_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser usage_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getUsage_NameLabel_Parser() {
		if (usage_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			usage_NameLabel_Parser = parser;
		}
		return usage_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser usage_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getUsage_StereotypeLabel_Parser() {
		if (usage_StereotypeLabel_Parser == null) {
			usage_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return usage_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser deployment_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDeployment_NameLabel_Parser() {
		if (deployment_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			deployment_NameLabel_Parser = parser;
		}
		return deployment_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser deployment_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDeployment_StereotypeLabel_Parser() {
		if (deployment_StereotypeLabel_Parser == null) {
			deployment_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return deployment_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private RoleBindingRoleNameParser dependency_RoleBindingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDependency_RoleBindingNameLabel_Parser() {
		if (dependency_RoleBindingNameLabel_Parser == null) {
			dependency_RoleBindingNameLabel_Parser = new RoleBindingRoleNameParser();
		}
		return dependency_RoleBindingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser dependency_RoleBindingStereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDependency_RoleBindingStereotypeLabel_Parser() {
		if (dependency_RoleBindingStereotypeLabel_Parser == null) {
			dependency_RoleBindingStereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return dependency_RoleBindingStereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dependency_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDependency_NameLabel_Parser() {
		if (dependency_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dependency_NameLabel_Parser = parser;
		}
		return dependency_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser dependency_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDependency_StereotypeLabel_Parser() {
		if (dependency_StereotypeLabel_Parser == null) {
			dependency_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return dependency_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser connector_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnector_StereotypeLabel_Parser() {
		if (connector_StereotypeLabel_Parser == null) {
			connector_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return connector_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser connector_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnector_NameLabel_Parser() {
		if (connector_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			connector_NameLabel_Parser = parser;
		}
		return connector_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser connector_SourceMultiplicityLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnector_SourceMultiplicityLabel_Parser() {
		if (connector_SourceMultiplicityLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("sourceMul{0}"); //$NON-NLS-1$
			parser.setEditorPattern("sourceMul{0}"); //$NON-NLS-1$
			parser.setEditPattern("sourceMul{0}"); //$NON-NLS-1$
			connector_SourceMultiplicityLabel_Parser = parser;
		}
		return connector_SourceMultiplicityLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser connector_TargetMultiplicityLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnector_TargetMultiplicityLabel_Parser() {
		if (connector_TargetMultiplicityLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("targetMul{0}"); //$NON-NLS-1$
			parser.setEditorPattern("targetMul{0}"); //$NON-NLS-1$
			parser.setEditPattern("targetMul{0}"); //$NON-NLS-1$
			connector_TargetMultiplicityLabel_Parser = parser;
		}
		return connector_TargetMultiplicityLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser generalization_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getGeneralization_StereotypeLabel_Parser() {
		if (generalization_StereotypeLabel_Parser == null) {
			generalization_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return generalization_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser informationFlow_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInformationFlow_StereotypeLabel_Parser() {
		if (informationFlow_StereotypeLabel_Parser == null) {
			informationFlow_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return informationFlow_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser informationFlow_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInformationFlow_NameLabel_Parser() {
		if (informationFlow_NameLabel_Parser == null) {
			informationFlow_NameLabel_Parser = new AppliedStereotypeParser();
		}
		return informationFlow_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case ActivityCompositeNameEditPart.VISUAL_ID:
				return getActivity_NameLabel_Parser();
			case ActivityCompositeFloatingLabelEditPart.VISUAL_ID:
				return getActivity_FloatingNameLabel_Parser();
			case InteractionCompositeNameEditPart.VISUAL_ID:
				return getInteraction_NameLabel_Parser();
			case InteractionCompositeFloatingLabelEditPart.VISUAL_ID:
				return getInteraction_FloatingNameLabel_Parser();
			case ProtocolStateMachineCompositeNameEditPart.VISUAL_ID:
				return getProtocolStateMachine_NameLabel_Parser();
			case ProtocolStateMachineCompositeFloatingLabelEditPart.VISUAL_ID:
				return getProtocolStateMachine_FloatingNameLabel_Parser();
			case StateMachineCompositeNameEditPart.VISUAL_ID:
				return getStateMachine_NameLabel_Parser();
			case StateMachineCompositeFloatingLabelEditPart.VISUAL_ID:
				return getStateMachine_FloatingNameLabel_Parser();
			case FunctionBehaviorCompositeNameEditPart.VISUAL_ID:
				return getFunctionBehavior_NameLabel_Parser();
			case FunctionBehaviorCompositeFloatingLabelEditPart.VISUAL_ID:
				return getFunctionBehavior_FloatingNameLabel_Parser();
			case OpaqueBehaviorCompositeNameEditPart.VISUAL_ID:
				return getOpaqueBehavior_NameLabel_Parser();
			case OpaqueBehaviorCompositeFloatingLabelEditPart.VISUAL_ID:
				return getOpaqueBehavior_FloatingNameLabel_Parser();
			case ComponentCompositeNameEditPart.VISUAL_ID:
				return getComponent_NameLabel_Parser();
			case ComponentCompositeFloatingLabelEditPart.VISUAL_ID:
				return getComponent_FloatingNameLabel_Parser();
			case DeviceCompositeNameEditPart.VISUAL_ID:
				return getDevice_NameLabel_Parser();
			case DeviceCompositeFloatingLabelEditPart.VISUAL_ID:
				return getDevice_FloatingNameLabel_Parser();
			case ExecutionEnvironmentCompositeNameEditPart.VISUAL_ID:
				return getExecutionEnvironment_NameLabel_Parser();
			case ExecutionEnvironmentCompositeFloatingLabelEditPart.VISUAL_ID:
				return getExecutionEnvironment_FloatingNameLabel_Parser();
			case NodeCompositeNameEditPart.VISUAL_ID:
				return getNode_NameLabel_Parser();
			case NodeCompositeFloatingLabelEditPart.VISUAL_ID:
				return getNode_FloatingNameLabel_Parser();
			case ClassCompositeNameEditPart.VISUAL_ID:
				return getClass_NameLabel_Parser();
			case ClassCompositeFloatingLabelEditPart.VISUAL_ID:
				return getClass_FloatingNameLabel_Parser();
			case CollaborationCompositeNameEditPart.VISUAL_ID:
				return getCollaboration_NameLabel_Parser();
			case CollaborationCompositeFloatingLabelEditPart.VISUAL_ID:
				return getCollaboration_FloatingNameLabel_Parser();
			case InterfaceNameEditPart.VISUAL_ID:
				return getInterface_NameLabel_Parser();
			case InterfaceFloatingLabelEditPart.VISUAL_ID:
				return getInterface_FloatingNameLabel_Parser();
			case PrimitiveTypeNameEditPart.VISUAL_ID:
				return getPrimitiveType_NameLabel_Parser();
			case PrimitiveTypeFloatingLabelEditPart.VISUAL_ID:
				return getPrimitiveType_FloatingNameLabel_Parser();
			case EnumerationNameEditPart.VISUAL_ID:
				return getEnumeration_NameLabel_Parser();
			case EnumerationFloatingLabelEditPart.VISUAL_ID:
				return getEnumeration_FloatingNameLabel_Parser();
			case DataTypeNameEditPart.VISUAL_ID:
				return getDataType_NameLabel_Parser();
			case DataTypeFloatingLabelEditPart.VISUAL_ID:
				return getDataType_FloatingNameLabel_Parser();
			case ActorNameEditPart.VISUAL_ID:
				return getActor_NameLabel_Parser();
			case ActorFloatingLabelEditPart.VISUAL_ID:
				return getActor_FloatingNameLabel_Parser();
			case DeploymentSpecificationNameEditPart.VISUAL_ID:
				return getDeploymentSpecification_NameLabel_Parser();
			case DeploymentSpecificationFloatingLabelEditPart.VISUAL_ID:
				return getDeploymentSpecification_FloatingNameLabel_Parser();
			case ArtifactNameEditPart.VISUAL_ID:
				return getArtifact_NameLabel_Parser();
			case ArtifactFloatingLabelEditPart.VISUAL_ID:
				return getArtifact_FloatingNameLabel_Parser();
			case InformationItemNameEditPart.VISUAL_ID:
				return getInformationItem_NameLabel_Parser();
			case InformationItemFloatingLabelEditPart.VISUAL_ID:
				return getInformationItem_FloatingNameLabel_Parser();
			case SignalNameEditPart.VISUAL_ID:
				return getSignal_NameLabel_Parser();
			case SignalFloatingLabelEditPart.VISUAL_ID:
				return getSignal_FloatingNameLabel_Parser();
			case UseCaseNameEditPart.VISUAL_ID:
				return getUseCase_NameLabel_Parser();
			case UseCaseFloatingLabelEditPart.VISUAL_ID:
				return getUseCase_FloatingNameLabel_Parser();
			case SignalEventNameEditPart.VISUAL_ID:
				return getSignalEvent_NameLabel_Parser();
			case SignalEventFloatingLabelEditPart.VISUAL_ID:
				return getSignalEvent_FloatingNameLabel_Parser();
			case CallEventNameEditPart.VISUAL_ID:
				return getCallEvent_NameLabel_Parser();
			case CallEventFloatingLabelEditPart.VISUAL_ID:
				return getCallEvent_FloatingNameLabel_Parser();
			case AnyReceiveEventNameEditPart.VISUAL_ID:
				return getAnyReceiveEvent_NameLabel_Parser();
			case AnyReceiveEventFloatingLabelEditPart.VISUAL_ID:
				return getAnyReceiveEvent_FloatingNameLabel_Parser();
			case ChangeEventNameEditPart.VISUAL_ID:
				return getChangeEvent_NameLabel_Parser();
			case ChangeEventFloatingLabelEditPart.VISUAL_ID:
				return getChangeEvent_FloatingNameLabel_Parser();
			case TimeEventNameEditPart.VISUAL_ID:
				return getTimeEvent_NameLabel_Parser();
			case TimeEventFloatingLabelEditPart.VISUAL_ID:
				return getTimeEvent_FloatingNameLabel_Parser();
			case DurationObservationNameEditPart.VISUAL_ID:
				return getDurationObservation_NameLabel_Parser();
			case DurationObservationStereotypeLabelEditPart.VISUAL_ID:
				return getDurationObservation_StereotypeLabel_Parser();
			case TimeObservationNameEditPart.VISUAL_ID:
				return getTimeObservation_NameLabel_Parser();
			case TimeObservationStereotypeLabelEditPart.VISUAL_ID:
				return getTimeObservation_StereotypeLabel_Parser();
			case LiteralBooleanNameEditPart.VISUAL_ID:
				return getLiteralBoolean_NameLabel_Parser();
			case LiteralBooleanFloatingLabelEditPart.VISUAL_ID:
				return getLiteralBoolean_FloatingNameLabel_Parser();
			case LiteralIntegerNameEditPart.VISUAL_ID:
				return getLiteralInteger_NameLabel_Parser();
			case LiteralIntegerFloatingLabelEditPart.VISUAL_ID:
				return getLiteralInteger_FloatingNameLabel_Parser();
			case LiteralNullNameEditPart.VISUAL_ID:
				return getLiteralNull_NameLabel_Parser();
			case LiteralNullFloatingLabelEditPart.VISUAL_ID:
				return getLiteralNull_FloatingNameLabel_Parser();
			case LiteralStringNameEditPart.VISUAL_ID:
				return getLiteralString_NameLabel_Parser();
			case LiteralStringFloatingLabelEditPart.VISUAL_ID:
				return getLiteralString_FloatingNameLabel_Parser();
			case LiteralUnlimitedNaturalNameEditPart.VISUAL_ID:
				return getLiteralUnlimitedNatural_NameLabel_Parser();
			case LiteralUnlimitedNaturalFloatingLabelEditPart.VISUAL_ID:
				return getLiteralUnlimitedNatural_FloatingNameLabel_Parser();
			case StringExpressionNameEditPart.VISUAL_ID:
				return getStringExpression_NameLabel_Parser();
			case StringExpressionFloatingLabelEditPart.VISUAL_ID:
				return getStringExpression_FloatingNameLabel_Parser();
			case OpaqueExpressionNameEditPart.VISUAL_ID:
				return getOpaqueExpression_NameLabel_Parser();
			case OpaqueExpressionFloatingLabelEditPart.VISUAL_ID:
				return getOpaqueExpression_FloatingNameLabel_Parser();
			case TimeExpressionNameEditPart.VISUAL_ID:
				return getTimeExpression_NameLabel_Parser();
			case TimeExpressionFloatingLabelEditPart.VISUAL_ID:
				return getTimeExpression_FloatingNameLabel_Parser();
			case ExpressionNameEditPart.VISUAL_ID:
				return getExpression_NameLabel_Parser();
			case ExpressionFloatingLabelEditPart.VISUAL_ID:
				return getExpression_FloatingNameLabel_Parser();
			case DurationNameEditPart.VISUAL_ID:
				return getDuration_NameLabel_Parser();
			case DurationFloatingLabelEditPart.VISUAL_ID:
				return getDuration_FloatingNameLabel_Parser();
			case TimeIntervalNameEditPart.VISUAL_ID:
				return getTimeInterval_NameLabel_Parser();
			case TimeIntervalFloatingLabelEditPart.VISUAL_ID:
				return getTimeInterval_FloatingNameLabel_Parser();
			case DurationIntervalNameEditPart.VISUAL_ID:
				return getDurationInterval_NameLabel_Parser();
			case DurationIntervalFloatingLabelEditPart.VISUAL_ID:
				return getDurationInterval_FloatingNameLabel_Parser();
			case IntervalNameEditPart.VISUAL_ID:
				return getInterval_NameLabel_Parser();
			case IntervalFloatingLabelEditPart.VISUAL_ID:
				return getInterval_FloatingNameLabel_Parser();
			case InstanceValueNameEditPart.VISUAL_ID:
				return getInstanceValue_NameLabel_Parser();
			case InstanceValueFloatingLabelEditPart.VISUAL_ID:
				return getInstanceValue_FloatingNameLabel_Parser();
			case CommentBodyEditPart.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case DurationConstraintNameEditPart.VISUAL_ID:
				return getDurationConstraint_NameLabel_Parser();
			case DurationConstraintSpecificationEditPart.VISUAL_ID:
				return getDurationConstraint_BodyLabel_Parser();
			case TimeConstraintNameEditPart.VISUAL_ID:
				return getTimeConstraint_NameLabel_Parser();
			case TimeConstraintSpecificationEditPart.VISUAL_ID:
				return getTimeConstraint_BodyLabel_Parser();
			case IntervalConstraintNameEditPart.VISUAL_ID:
				return getIntervalConstraint_NameLabel_Parser();
			case IntervalConstraintSpecificationEditPart.VISUAL_ID:
				return getIntervalConstraint_BodyLabel_Parser();
			case InteractionConstraintNameEditPart.VISUAL_ID:
				return getInteractionConstraint_NameLabel_Parser();
			case InteractionConstraintSpecificationEditPart.VISUAL_ID:
				return getInteractionConstraint_BodyLabel_Parser();
			case ConstraintNameEditPart.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintSpecificationEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case BehaviorPortFloatingLabelEditPart.VISUAL_ID:
				return getPort_BehaviorFloatingNameLabel_Parser();
			case PortNameEditPart.VISUAL_ID:
				return getPort_NameLabel_Parser();
			case PortAppliedStereotypeEditPart.VISUAL_ID:
				return getPort_StereotypeLabel_Parser();
			case ParameterNameEditPart.VISUAL_ID:
				return getParameter_NameLabel_Parser();
			case ParameterAppliedStereotypeEditPart.VISUAL_ID:
				return getParameter_StereotypeLabel_Parser();
			case PropertyPartNameEditPartCN.VISUAL_ID:
				return getProperty_NameLabel_Parser();
			case PropertyPartFloatingLabelEditPartCN.VISUAL_ID:
				return getProperty_FloatingNameLabel_Parser();
			case CollaborationRoleNameEditPartCN.VISUAL_ID:
				return getConnectableElement_CollaborationRoleNameLabel_Parser();
			case CollaborationRoleFloatingLabelEditPartCN.VISUAL_ID:
				return getConnectableElement_CollaborationRoleFloatingNameLabel_Parser();
			case CollaborationUseNameEditPart.VISUAL_ID:
				return getCollaborationUse_NameLabel_Parser();
			case CollaborationUseFloatingLabelEditPartCN.VISUAL_ID:
				return getCollaborationUse_FloatingNameLabel_Parser();
			case ActivityCompositeNameEditPartCN.VISUAL_ID:
				return getActivity_NameLabel_CN_Parser();
			case ActivityCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getActivity_FloatingNameLabel_CN_Parser();
			case InteractionCompositeNameEditPartCN.VISUAL_ID:
				return getInteraction_NameLabel_CN_Parser();
			case InteractionCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getInteraction_FloatingNameLabel_CN_Parser();
			case ProtocolStateMachineCompositeNameEditPartCN.VISUAL_ID:
				return getProtocolStateMachine_NameLabel_CN_Parser();
			case ProtocolStateMachineCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getProtocolStateMachine_FloatingNameLabel_CN_Parser();
			case StateMachineCompositeNameEditPartCN.VISUAL_ID:
				return getStateMachine_NameLabel_CN_Parser();
			case StateMachineCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getStateMachine_FloatingNameLabel_CN_Parser();
			case FunctionBehaviorCompositeNameEditPartCN.VISUAL_ID:
				return getFunctionBehavior_NameLabel_CN_Parser();
			case FunctionBehaviorCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getFunctionBehavior_FloatingNameLabel_CN_Parser();
			case OpaqueBehaviorCompositeNameEditPartCN.VISUAL_ID:
				return getOpaqueBehavior_NameLabel_CN_Parser();
			case OpaqueBehaviorCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getOpaqueBehavior_FloatingNameLabel_CN_Parser();
			case ComponentCompositeNameEditPartCN.VISUAL_ID:
				return getComponent_NameLabel_CN_Parser();
			case ComponentCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getComponent_FloatingNameLabel_CN_Parser();
			case DeviceCompositeNameEditPartCN.VISUAL_ID:
				return getDevice_NameLabel_CN_Parser();
			case DeviceCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getDevice_FloatingNameLabel_CN_Parser();
			case ExecutionEnvironmentCompositeNameEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_NameLabel_CN_Parser();
			case ExecutionEnvironmentCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_FloatingNameLabel_CN_Parser();
			case NodeCompositeNameEditPartCN.VISUAL_ID:
				return getNode_NameLabel_CN_Parser();
			case NodeCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getNode_FloatingNameLabel_CN_Parser();
			case ClassCompositeNameEditPartCN.VISUAL_ID:
				return getClass_NameLabel_CN_Parser();
			case ClassCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getClass_FloatingNameLabel_CN_Parser();
			case CollaborationCompositeNameEditPartCN.VISUAL_ID:
				return getCollaboration_NameLabel_CN_Parser();
			case CollaborationCompositeFloatingLabelEditPartCN.VISUAL_ID:
				return getCollaboration_FloatingNameLabel_CN_Parser();
			case InterfaceNameEditPartCN.VISUAL_ID:
				return getInterface_NameLabel_CN_Parser();
			case InterfaceFloatingLabelEditPartCN.VISUAL_ID:
				return getInterface_FloatingNameLabel_CN_Parser();
			case PrimitiveTypeNameEditPartCN.VISUAL_ID:
				return getPrimitiveType_NameLabel_CN_Parser();
			case PrimitiveTypeFloatingLabelEditPartCN.VISUAL_ID:
				return getPrimitiveType_FloatingNameLabel_CN_Parser();
			case EnumerationNameEditPartCN.VISUAL_ID:
				return getEnumeration_NameLabel_CN_Parser();
			case EnumerationFloatingLabelEditPartCN.VISUAL_ID:
				return getEnumeration_FloatingNameLabel_CN_Parser();
			case DataTypeNameEditPartCN.VISUAL_ID:
				return getDataType_NameLabel_CN_Parser();
			case DataTypeFloatingLabelEditPartCN.VISUAL_ID:
				return getDataType_FloatingNameLabel_CN_Parser();
			case ActorNameEditPartCN.VISUAL_ID:
				return getActor_NameLabel_CN_Parser();
			case ActorFloatingLabelEditPartCN.VISUAL_ID:
				return getActor_FloatingNameLabel_CN_Parser();
			case DeploymentSpecificationNameEditPartCN.VISUAL_ID:
				return getDeploymentSpecification_NameLabel_CN_Parser();
			case DeploymentSpecificationFloatingLabelEditPartCN.VISUAL_ID:
				return getDeploymentSpecification_FloatingNameLabel_CN_Parser();
			case ArtifactNameEditPartCN.VISUAL_ID:
				return getArtifact_NameLabel_CN_Parser();
			case ArtifactFloatingLabelEditPartCN.VISUAL_ID:
				return getArtifact_FloatingNameLabel_CN_Parser();
			case InformationItemNameEditPartCN.VISUAL_ID:
				return getInformationItem_NameLabel_CN_Parser();
			case InformationItemFloatingLabelEditPartCN.VISUAL_ID:
				return getInformationItem_FloatingNameLabel_CN_Parser();
			case SignalNameEditPartCN.VISUAL_ID:
				return getSignal_NameLabel_CN_Parser();
			case SignalFloatingLabelEditPartCN.VISUAL_ID:
				return getSignal_FloatingNameLabel_CN_Parser();
			case UseCaseNameEditPartCN.VISUAL_ID:
				return getUseCase_NameLabel_CN_Parser();
			case UseCaseFloatingLabelEditPartCN.VISUAL_ID:
				return getUseCase_FloatingNameLabel_CN_Parser();
			case CommentBodyEditPartCN.VISUAL_ID:
				return getComment_BodyLabel_CN_Parser();
			case DurationConstraintNameEditPartCN.VISUAL_ID:
				return getDurationConstraint_NameLabel_CN_Parser();
			case DurationConstraintSpecificationEditPartCN.VISUAL_ID:
				return getDurationConstraint_BodyLabel_CN_Parser();
			case TimeConstraintNameEditPartCN.VISUAL_ID:
				return getTimeConstraint_NameLabel_CN_Parser();
			case TimeConstraintSpecificationEditPartCN.VISUAL_ID:
				return getTimeConstraint_BodyLabel_CN_Parser();
			case IntervalConstraintNameEditPartCN.VISUAL_ID:
				return getIntervalConstraint_NameLabel_CN_Parser();
			case IntervalConstraintSpecificationEditPartCN.VISUAL_ID:
				return getIntervalConstraint_BodyLabel_CN_Parser();
			case InteractionConstraintNameEditPartCN.VISUAL_ID:
				return getInteractionConstraint_NameLabel_CN_Parser();
			case InteractionConstraintSpecificationEditPartCN.VISUAL_ID:
				return getInteractionConstraint_BodyLabel_CN_Parser();
			case ConstraintNameEditPartCN.VISUAL_ID:
				return getConstraint_NameLabel_CN_Parser();
			case ConstraintSpecificationEditPartCN.VISUAL_ID:
				return getConstraint_BodyLabel_CN_Parser();
			case PropertyEditPartCLN.VISUAL_ID:
				return getProperty_AttributeLabel_Parser();
			case OperationEditPartCLN.VISUAL_ID:
				return getOperation_OperationLabel_Parser();
			case EnumerationLiteralEditPartCLN.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_Parser();
			case ComponentRealizationNameEditPart.VISUAL_ID:
				return getComponentRealization_NameLabel_Parser();
			case ComponentRealizationAppliedStereotypeEditPart.VISUAL_ID:
				return getComponentRealization_StereotypeLabel_Parser();
			case InterfaceRealizationNameEditPart.VISUAL_ID:
				return getInterfaceRealization_NameLabel_Parser();
			case InterfaceRealizationAppliedStereotypeEditPart.VISUAL_ID:
				return getInterfaceRealization_StereotypeLabel_Parser();
			case SubstitutionNameEditPart.VISUAL_ID:
				return getSubstitution_NameLabel_Parser();
			case SubstitutionAppliedStereotypeEditPart.VISUAL_ID:
				return getSubstitution_StereotypeLabel_Parser();
			case RealizationNameEditPart.VISUAL_ID:
				return getRealization_NameLabel_Parser();
			case RealizationAppliedStereotypeEditPart.VISUAL_ID:
				return getRealization_StereotypeLabel_Parser();
			case ManifestationNameEditPart.VISUAL_ID:
				return getManifestation_NameLabel_Parser();
			case ManifestationAppliedStereotypeEditPart.VISUAL_ID:
				return getManifestation_StereotypeLabel_Parser();
			case AbstractionNameEditPart.VISUAL_ID:
				return getAbstraction_NameLabel_Parser();
			case AbstractionAppliedStereotypeEditPart.VISUAL_ID:
				return getAbstraction_StereotypeLabel_Parser();
			case UsageNameEditPart.VISUAL_ID:
				return getUsage_NameLabel_Parser();
			case UsageAppliedStereotypeEditPart.VISUAL_ID:
				return getUsage_StereotypeLabel_Parser();
			case DeploymentNameEditPart.VISUAL_ID:
				return getDeployment_NameLabel_Parser();
			case DeploymentAppliedStereotypeEditPart.VISUAL_ID:
				return getDeployment_StereotypeLabel_Parser();
			case RoleBindingRoleNameEditPart.VISUAL_ID:
				return getDependency_RoleBindingNameLabel_Parser();
			case RoleBindingAppliedStereotypeEditPart.VISUAL_ID:
				return getDependency_RoleBindingStereotypeLabel_Parser();
			case DependencyNameEditPart.VISUAL_ID:
				return getDependency_NameLabel_Parser();
			case DependencyAppliedStereotypeEditPart.VISUAL_ID:
				return getDependency_StereotypeLabel_Parser();
			case ConnectorAppliedStereotypeEditPart.VISUAL_ID:
				return getConnector_StereotypeLabel_Parser();
			case ConnectorNameEditPart.VISUAL_ID:
				return getConnector_NameLabel_Parser();
			case ConnectorMultiplicitySourceEditPart.VISUAL_ID:
				return getConnector_SourceMultiplicityLabel_Parser();
			case ConnectorMultiplicityTargetEditPart.VISUAL_ID:
				return getConnector_TargetMultiplicityLabel_Parser();
			case GeneralizationAppliedStereotypeEditPart.VISUAL_ID:
				return getGeneralization_StereotypeLabel_Parser();
			case InformationFlowAppliedStereotypeEditPart.VISUAL_ID:
				return getInformationFlow_StereotypeLabel_Parser();
			case InformationFlowNameEditPart.VISUAL_ID:
				return getInformationFlow_NameLabel_Parser();
			}
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 *
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object, String parserHint) {
		return ParserService.getInstance().getParser(new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	@Override
	public IParser getParser(IAdaptable hint) {
		String vid = hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(UMLVisualIDRegistry.getVisualID(vid));
		}
		View view = hint.getAdapter(View.class);
		if (view != null) {
			return getParser(UMLVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (UMLElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		@Override
		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}
}
