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
package org.eclipse.papyrus.uml.diagram.usecase.providers;

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
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.parser.CommentParser;
import org.eclipse.papyrus.uml.diagram.common.parser.ConstraintParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AbstractionNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAppliedStereotypeEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAsRectangleNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorFloatingLabelEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AppliedStereotypeAbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AppliedStereotypePackageMergeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AppliedStereotypeUsageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentBodyEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInPackageFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintBodyEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintBodyInCEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintBodyInPEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DefaultNamedElementNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DependencyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DependencyNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DiagramNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtendAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointInRectangleEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.GeneralizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.IncludeAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageImportAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.RealizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.RealizationNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectClassifierFloatingLabelEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectClassifierNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UsageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseAsRectangleNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseFloatingLabelEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.usecase.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLParserProvider extends AbstractProvider implements IParserProvider {

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
			actor_NameLabel_Parser = parser;
		}
		return actor_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actor_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_StereotypeLabel_Parser() {
		if (actor_StereotypeLabel_Parser == null) {
			actor_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return actor_StereotypeLabel_Parser;
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
	private IParser actor_ClassifierNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_ClassifierNameLabel_Parser() {
		if (actor_ClassifierNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			actor_ClassifierNameLabel_Parser = parser;
		}
		return actor_ClassifierNameLabel_Parser;
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
	private IParser useCase_ClassifierNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getUseCase_ClassifierNameLabel_Parser() {
		if (useCase_ClassifierNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			useCase_ClassifierNameLabel_Parser = parser;
		}
		return useCase_ClassifierNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser classifier_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClassifier_NameLabel_Parser() {
		if (classifier_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			classifier_NameLabel_Parser = parser;
		}
		return classifier_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser classifier_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClassifier_FloatingNameLabel_Parser() {
		if (classifier_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			classifier_FloatingNameLabel_Parser = parser;
		}
		return classifier_FloatingNameLabel_Parser;
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
	private IParser diagram_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDiagram_NameLabel_Parser() {
		if (diagram_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					NotationPackage.eINSTANCE.getDiagram_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			diagram_NameLabel_Parser = parser;
		}
		return diagram_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser extensionPoint_ExtensionPointLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExtensionPoint_ExtensionPointLabel_Parser() {
		if (extensionPoint_ExtensionPointLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			extensionPoint_ExtensionPointLabel_Parser = parser;
		}
		return extensionPoint_ExtensionPointLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser extensionPoint_ClassifierExtensionPointLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExtensionPoint_ClassifierExtensionPointLabel_Parser() {
		if (extensionPoint_ClassifierExtensionPointLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			extensionPoint_ClassifierExtensionPointLabel_Parser = parser;
		}
		return extensionPoint_ClassifierExtensionPointLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser useCase_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getUseCase_NameLabel_CCN_Parser() {
		if (useCase_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			useCase_NameLabel_CCN_Parser = parser;
		}
		return useCase_NameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser useCase_FloatingNameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getUseCase_FloatingNameLabel_CCN_Parser() {
		if (useCase_FloatingNameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			useCase_FloatingNameLabel_CCN_Parser = parser;
		}
		return useCase_FloatingNameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_NameLabel_CCN_Parser() {
		if (component_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			component_NameLabel_CCN_Parser = parser;
		}
		return component_NameLabel_CCN_Parser;
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
	private IParser constraint_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_NameLabel_CCN_Parser() {
		if (constraint_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			constraint_NameLabel_CCN_Parser = parser;
		}
		return constraint_NameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private ConstraintParser constraint_BodyLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_BodyLabel_CCN_Parser() {
		if (constraint_BodyLabel_CCN_Parser == null) {
			constraint_BodyLabel_CCN_Parser = new ConstraintParser();
		}
		return constraint_BodyLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser actor_NameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_NameLabel_CCN_Parser() {
		if (actor_NameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			actor_NameLabel_CCN_Parser = parser;
		}
		return actor_NameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actor_StereotypeLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_StereotypeLabel_CCN_Parser() {
		if (actor_StereotypeLabel_CCN_Parser == null) {
			actor_StereotypeLabel_CCN_Parser = new AppliedStereotypeParser();
		}
		return actor_StereotypeLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser actor_FloatingNameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_FloatingNameLabel_CCN_Parser() {
		if (actor_FloatingNameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			actor_FloatingNameLabel_CCN_Parser = parser;
		}
		return actor_FloatingNameLabel_CCN_Parser;
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
			actor_NameLabel_CN_Parser = parser;
		}
		return actor_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser actor_StereotypeLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getActor_StereotypeLabel_CN_Parser() {
		if (actor_StereotypeLabel_CN_Parser == null) {
			actor_StereotypeLabel_CN_Parser = new AppliedStereotypeParser();
		}
		return actor_StereotypeLabel_CN_Parser;
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
			component_NameLabel_CN_Parser = parser;
		}
		return component_NameLabel_CN_Parser;
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
	private AppliedStereotypeParser include_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInclude_StereotypeLabel_Parser() {
		if (include_StereotypeLabel_Parser == null) {
			include_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return include_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser extend_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getExtend_StereotypeLabel_Parser() {
		if (extend_StereotypeLabel_Parser == null) {
			extend_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return extend_StereotypeLabel_Parser;
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
	private IParser association_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_NameLabel_Parser() {
		if (association_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			association_NameLabel_Parser = parser;
		}
		return association_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser association_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_StereotypeLabel_Parser() {
		if (association_StereotypeLabel_Parser == null) {
			association_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return association_StereotypeLabel_Parser;
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
	private AppliedStereotypeParser packageMerge_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPackageMerge_StereotypeLabel_Parser() {
		if (packageMerge_StereotypeLabel_Parser == null) {
			packageMerge_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return packageMerge_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser packageImport_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPackageImport_StereotypeLabel_Parser() {
		if (packageImport_StereotypeLabel_Parser == null) {
			packageImport_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return packageImport_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case ActorNameEditPartTN.VISUAL_ID:
				return getActor_NameLabel_Parser();
			case ActorAppliedStereotypeEditPartTN.VISUAL_ID:
				return getActor_StereotypeLabel_Parser();
			case ActorFloatingLabelEditPartTN.VISUAL_ID:
				return getActor_FloatingNameLabel_Parser();
			case ActorAsRectangleNameEditPartTN.VISUAL_ID:
				return getActor_ClassifierNameLabel_Parser();
			case UseCaseNameEditPartTN.VISUAL_ID:
				return getUseCase_NameLabel_Parser();
			case UseCaseFloatingLabelEditPartTN.VISUAL_ID:
				return getUseCase_FloatingNameLabel_Parser();
			case UseCaseAsRectangleNameEditPartTN.VISUAL_ID:
				return getUseCase_ClassifierNameLabel_Parser();
			case SubjectClassifierNameEditPartTN.VISUAL_ID:
				return getClassifier_NameLabel_Parser();
			case SubjectClassifierFloatingLabelEditPartTN.VISUAL_ID:
				return getClassifier_FloatingNameLabel_Parser();
			case PackageNameEditPartTN.VISUAL_ID:
				return getPackage_NameLabel_Parser();
			case ConstraintNameEditPartTN.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintBodyEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case CommentBodyEditPartTN.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case DefaultNamedElementNameEditPart.VISUAL_ID:
				return getNamedElement_NameLabel_Parser();
			case DiagramNameEditPart.VISUAL_ID:
				return getDiagram_NameLabel_Parser();
			case ExtensionPointEditPart.VISUAL_ID:
				return getExtensionPoint_ExtensionPointLabel_Parser();
			case ExtensionPointInRectangleEditPart.VISUAL_ID:
				return getExtensionPoint_ClassifierExtensionPointLabel_Parser();
			case UseCaseInComponentNameEditPart.VISUAL_ID:
				return getUseCase_NameLabel_CCN_Parser();
			case UseCaseInComponentFloatingLabelEditPart.VISUAL_ID:
				return getUseCase_FloatingNameLabel_CCN_Parser();
			case ComponentInComponentNameEditPart.VISUAL_ID:
				return getComponent_NameLabel_CCN_Parser();
			case CommentBodyEditPartCN.VISUAL_ID:
				return getComment_BodyLabel_CN_Parser();
			case ConstraintInComponentNameEditPart.VISUAL_ID:
				return getConstraint_NameLabel_CCN_Parser();
			case ConstraintBodyInCEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_CCN_Parser();
			case ActorInComponentNameEditPart.VISUAL_ID:
				return getActor_NameLabel_CCN_Parser();
			case ActorInComponentAppliedStereotypeEditPart.VISUAL_ID:
				return getActor_StereotypeLabel_CCN_Parser();
			case ActorInComponentFloatingLabelEditPart.VISUAL_ID:
				return getActor_FloatingNameLabel_CCN_Parser();
			case ConstraintInPackageNameEditPart.VISUAL_ID:
				return getConstraint_NameLabel_CN_Parser();
			case ConstraintBodyInPEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_CN_Parser();
			case ActorInPackageNameEditPart.VISUAL_ID:
				return getActor_NameLabel_CN_Parser();
			case ActorInPackageAppliedStereotypeEditPart.VISUAL_ID:
				return getActor_StereotypeLabel_CN_Parser();
			case ActorInPackageFloatingLabelEditPart.VISUAL_ID:
				return getActor_FloatingNameLabel_CN_Parser();
			case UseCaseInPackageNameEditPart.VISUAL_ID:
				return getUseCase_NameLabel_CN_Parser();
			case UseCaseInPackageFloatingLabelEditPart.VISUAL_ID:
				return getUseCase_FloatingNameLabel_CN_Parser();
			case ComponentInPackageNameEditPart.VISUAL_ID:
				return getComponent_NameLabel_CN_Parser();
			case ComponentInPackageFloatingLabelEditPart.VISUAL_ID:
				return getComponent_FloatingNameLabel_Parser();
			case PackageNameEditPartCN.VISUAL_ID:
				return getPackage_NameLabel_CN_Parser();
			case IncludeAppliedStereotypeEditPart.VISUAL_ID:
				return getInclude_StereotypeLabel_Parser();
			case ExtendAppliedStereotypeEditPart.VISUAL_ID:
				return getExtend_StereotypeLabel_Parser();
			case GeneralizationAppliedStereotypeEditPart.VISUAL_ID:
				return getGeneralization_StereotypeLabel_Parser();
			case AssociationNameEditPart.VISUAL_ID:
				return getAssociation_NameLabel_Parser();
			case AssociationAppliedStereotypeEditPart.VISUAL_ID:
				return getAssociation_StereotypeLabel_Parser();
			case DependencyNameEditPart.VISUAL_ID:
				return getDependency_NameLabel_Parser();
			case DependencyAppliedStereotypeEditPart.VISUAL_ID:
				return getDependency_StereotypeLabel_Parser();
			case AbstractionNameEditPart.VISUAL_ID:
				return getAbstraction_NameLabel_Parser();
			case AppliedStereotypeAbstractionEditPart.VISUAL_ID:
				return getAbstraction_StereotypeLabel_Parser();
			case UsageNameEditPart.VISUAL_ID:
				return getUsage_NameLabel_Parser();
			case AppliedStereotypeUsageEditPart.VISUAL_ID:
				return getUsage_StereotypeLabel_Parser();
			case RealizationNameEditPart.VISUAL_ID:
				return getRealization_NameLabel_Parser();
			case RealizationAppliedStereotypeEditPart.VISUAL_ID:
				return getRealization_StereotypeLabel_Parser();
			case AppliedStereotypePackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_StereotypeLabel_Parser();
			case PackageImportAppliedStereotypeEditPart.VISUAL_ID:
				return getPackageImport_StereotypeLabel_Parser();
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
