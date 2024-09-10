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
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactFloatingLabelEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactFloatingLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactNameEditPartACN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ArtifactNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommentBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommunicationPathAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.CommunicationPathNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ConstraintSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DefaultNamedElementNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DependencyNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsClassifierFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsClassifierNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsNestedArtifactFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsNestedArtifactNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsPackageableElFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecAsPackageableElNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecificationFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeploymentSpecificationNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.DeviceNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ExecutionEnvironmentNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.GeneralizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ManifestationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ManifestationNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.ModelNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.MultiDependencyLabelEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedArtifactNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedDeviceNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedExecutionEnvironmentNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NestedNodeNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.NodeNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.deployment.edit.parts.PackageNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.deployment.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.deployment.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser dependency_MultiNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDependency_MultiNameLabel_Parser() {
		if (dependency_MultiNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dependency_MultiNameLabel_Parser = parser;
		}
		return dependency_MultiNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser model_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getModel_NameLabel_Parser() {
		if (model_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			model_NameLabel_Parser = parser;
		}
		return model_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser package_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPackage_NameLabel_Parser() {
		if (package_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			package_NameLabel_Parser = parser;
		}
		return package_NameLabel_Parser;
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
			executionEnvironment_NameLabel_Parser = parser;
		}
		return executionEnvironment_NameLabel_Parser;
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
			device_NameLabel_Parser = parser;
		}
		return device_NameLabel_Parser;
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
			node_NameLabel_Parser = parser;
		}
		return node_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser namedElement_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getNamedElement_NameLabel_Parser() {
		if (namedElement_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			namedElement_NameLabel_Parser = parser;
		}
		return namedElement_NameLabel_Parser;
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
	private IParser model_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getModel_NameLabel_CN_Parser() {
		if (model_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			model_NameLabel_CN_Parser = parser;
		}
		return model_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser package_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getPackage_NameLabel_CN_Parser() {
		if (package_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			package_NameLabel_CN_Parser = parser;
		}
		return package_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser device_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getDevice_NameLabel_CCN_Parser() {
		if (device_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			device_NameLabel_CCN_Parser = parser;
		}
		return device_NameLabel_CCN_Parser;
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
			device_NameLabel_CN_Parser = parser;
		}
		return device_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser executionEnvironment_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getExecutionEnvironment_NameLabel_CCN_Parser() {
		if (executionEnvironment_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			executionEnvironment_NameLabel_CCN_Parser = parser;
		}
		return executionEnvironment_NameLabel_CCN_Parser;
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
			executionEnvironment_NameLabel_CN_Parser = parser;
		}
		return executionEnvironment_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser node_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getNode_NameLabel_CCN_Parser() {
		if (node_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			node_NameLabel_CCN_Parser = parser;
		}
		return node_NameLabel_CCN_Parser;
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
			node_NameLabel_CN_Parser = parser;
		}
		return node_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_NameLabel_CCN_Parser() {
		if (artifact_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			artifact_NameLabel_CCN_Parser = parser;
		}
		return artifact_NameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_FloatingNameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_FloatingNameLabel_CCN_Parser() {
		if (artifact_FloatingNameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			artifact_FloatingNameLabel_CCN_Parser = parser;
		}
		return artifact_FloatingNameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_NameLabel_ACN_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_NameLabel_ACN_Parser() {
		if (artifact_NameLabel_ACN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			artifact_NameLabel_ACN_Parser = parser;
		}
		return artifact_NameLabel_ACN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser artifact_FloatingNameLabel_ACN_Parser;

	/**
	 * @generated
	 */
	private IParser getArtifact_FloatingNameLabel_ACN_Parser() {
		if (artifact_FloatingNameLabel_ACN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			artifact_FloatingNameLabel_ACN_Parser = parser;
		}
		return artifact_FloatingNameLabel_ACN_Parser;
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
			artifact_NameLabel_CN_Parser = parser;
		}
		return artifact_NameLabel_CN_Parser;
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
	private IParser deploymentSpecification_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_NameLabel_CCN_Parser() {
		if (deploymentSpecification_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			deploymentSpecification_NameLabel_CCN_Parser = parser;
		}
		return deploymentSpecification_NameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser deploymentSpecification_FloatingNameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_FloatingNameLabel_CCN_Parser() {
		if (deploymentSpecification_FloatingNameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			deploymentSpecification_FloatingNameLabel_CCN_Parser = parser;
		}
		return deploymentSpecification_FloatingNameLabel_CCN_Parser;
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
	private IParser deploymentSpecification_NameLabel_ACN_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_NameLabel_ACN_Parser() {
		if (deploymentSpecification_NameLabel_ACN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			deploymentSpecification_NameLabel_ACN_Parser = parser;
		}
		return deploymentSpecification_NameLabel_ACN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser deploymentSpecification_FloatingNameLabel_ACN_Parser;

	/**
	 * @generated
	 */
	private IParser getDeploymentSpecification_FloatingNameLabel_ACN_Parser() {
		if (deploymentSpecification_FloatingNameLabel_ACN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			deploymentSpecification_FloatingNameLabel_ACN_Parser = parser;
		}
		return deploymentSpecification_FloatingNameLabel_ACN_Parser;
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
	private IParser communicationPath_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCommunicationPath_NameLabel_Parser() {
		if (communicationPath_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			communicationPath_NameLabel_Parser = parser;
		}
		return communicationPath_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser communicationPath_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getCommunicationPath_StereotypeLabel_Parser() {
		if (communicationPath_StereotypeLabel_Parser == null) {
			communicationPath_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return communicationPath_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case MultiDependencyLabelEditPart.VISUAL_ID:
				return getDependency_MultiNameLabel_Parser();
			case ModelNameEditPart.VISUAL_ID:
				return getModel_NameLabel_Parser();
			case PackageNameEditPart.VISUAL_ID:
				return getPackage_NameLabel_Parser();
			case ConstraintNameEditPart.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintSpecificationEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case CommentBodyEditPart.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case ExecutionEnvironmentNameEditPart.VISUAL_ID:
				return getExecutionEnvironment_NameLabel_Parser();
			case DeviceNameEditPart.VISUAL_ID:
				return getDevice_NameLabel_Parser();
			case ArtifactNameEditPart.VISUAL_ID:
				return getArtifact_NameLabel_Parser();
			case ArtifactFloatingLabelEditPart.VISUAL_ID:
				return getArtifact_FloatingNameLabel_Parser();
			case NodeNameEditPart.VISUAL_ID:
				return getNode_NameLabel_Parser();
			case DefaultNamedElementNameEditPart.VISUAL_ID:
				return getNamedElement_NameLabel_Parser();
			case DeploymentSpecificationNameEditPart.VISUAL_ID:
				return getDeploymentSpecification_NameLabel_Parser();
			case DeploymentSpecificationFloatingLabelEditPart.VISUAL_ID:
				return getDeploymentSpecification_FloatingNameLabel_Parser();
			case ModelNameEditPartCN.VISUAL_ID:
				return getModel_NameLabel_CN_Parser();
			case PackageNameEditPartCN.VISUAL_ID:
				return getPackage_NameLabel_CN_Parser();
			case DeviceNameEditPartCN.VISUAL_ID:
				return getDevice_NameLabel_CCN_Parser();
			case NestedDeviceNameEditPartCN.VISUAL_ID:
				return getDevice_NameLabel_CN_Parser();
			case ExecutionEnvironmentNameEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_NameLabel_CCN_Parser();
			case NestedExecutionEnvironmentNameEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_NameLabel_CN_Parser();
			case NodeNameEditPartCN.VISUAL_ID:
				return getNode_NameLabel_CCN_Parser();
			case NestedNodeNameEditPart.VISUAL_ID:
				return getNode_NameLabel_CN_Parser();
			case ArtifactNameEditPartCN.VISUAL_ID:
				return getArtifact_NameLabel_CCN_Parser();
			case ArtifactFloatingLabelEditPartCN.VISUAL_ID:
				return getArtifact_FloatingNameLabel_CCN_Parser();
			case ArtifactNameEditPartACN.VISUAL_ID:
				return getArtifact_NameLabel_ACN_Parser();
			case ArtifactFloatingLabelEditPartACN.VISUAL_ID:
				return getArtifact_FloatingNameLabel_ACN_Parser();
			case NestedArtifactNameEditPart.VISUAL_ID:
				return getArtifact_NameLabel_CN_Parser();
			case CommentBodyEditPartCN.VISUAL_ID:
				return getComment_BodyLabel_CN_Parser();
			case ConstraintNameEditPartCN.VISUAL_ID:
				return getConstraint_NameLabel_CN_Parser();
			case ConstraintSpecificationEditPartCN.VISUAL_ID:
				return getConstraint_BodyLabel_CN_Parser();
			case DeploymentSpecAsClassifierNameEditPart.VISUAL_ID:
				return getDeploymentSpecification_NameLabel_CCN_Parser();
			case DeploymentSpecAsClassifierFloatingLabelEditPart.VISUAL_ID:
				return getDeploymentSpecification_FloatingNameLabel_CCN_Parser();
			case DeploymentSpecAsPackageableElNameEditPart.VISUAL_ID:
				return getDeploymentSpecification_NameLabel_CN_Parser();
			case DeploymentSpecAsPackageableElFloatingLabelEditPart.VISUAL_ID:
				return getDeploymentSpecification_FloatingNameLabel_CN_Parser();
			case DeploymentSpecAsNestedArtifactNameEditPart.VISUAL_ID:
				return getDeploymentSpecification_NameLabel_ACN_Parser();
			case DeploymentSpecAsNestedArtifactFloatingLabelEditPart.VISUAL_ID:
				return getDeploymentSpecification_FloatingNameLabel_ACN_Parser();
			case DeploymentNameEditPart.VISUAL_ID:
				return getDeployment_NameLabel_Parser();
			case DeploymentAppliedStereotypeEditPart.VISUAL_ID:
				return getDeployment_StereotypeLabel_Parser();
			case ManifestationNameEditPart.VISUAL_ID:
				return getManifestation_NameLabel_Parser();
			case ManifestationAppliedStereotypeEditPart.VISUAL_ID:
				return getManifestation_StereotypeLabel_Parser();
			case GeneralizationAppliedStereotypeEditPart.VISUAL_ID:
				return getGeneralization_StereotypeLabel_Parser();
			case DependencyNameEditPart.VISUAL_ID:
				return getDependency_NameLabel_Parser();
			case DependencyAppliedStereotypeEditPart.VISUAL_ID:
				return getDependency_StereotypeLabel_Parser();
			case CommunicationPathNameEditPart.VISUAL_ID:
				return getCommunicationPath_NameLabel_Parser();
			case CommunicationPathAppliedStereotypeEditPart.VISUAL_ID:
				return getCommunicationPath_StereotypeLabel_Parser();
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
