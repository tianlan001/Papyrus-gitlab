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
package org.eclipse.papyrus.uml.diagram.profile.providers;

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
import org.eclipse.papyrus.uml.diagram.common.parser.packageimport.PackageImportVisibilityParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AppliedStereotypeElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationBranchMultiplicityEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationMultiplicitySourceEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationMultiplicityTargetEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationRoleSourceEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationRoleTargetEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassOperationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ClassPropertyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintBodyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypeOperationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DataTypePropertyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DiagramNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ElementImportAliasEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationLiteralEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.EnumerationNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.GeneralizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ModelNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MultiDependencyLabelEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageImportAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PackageNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.PrimitiveTypeNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeNameEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.StereotypeNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry;
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
	private IParser stereotype_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getStereotype_NameLabel_Parser() {
		if (stereotype_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			stereotype_NameLabel_Parser = parser;
		}
		return stereotype_NameLabel_Parser;
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
			class_NameLabel_Parser = parser;
		}
		return class_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_MetaclassNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_MetaclassNameLabel_Parser() {
		if (class_MetaclassNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			class_MetaclassNameLabel_Parser = parser;
		}
		return class_MetaclassNameLabel_Parser;
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
	private IParser profile_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProfile_NameLabel_Parser() {
		if (profile_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			profile_NameLabel_Parser = parser;
		}
		return profile_NameLabel_Parser;
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
			enumeration_NameLabel_Parser = parser;
		}
		return enumeration_NameLabel_Parser;
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
			primitiveType_NameLabel_Parser = parser;
		}
		return primitiveType_NameLabel_Parser;
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
			dataType_NameLabel_Parser = parser;
		}
		return dataType_NameLabel_Parser;
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
			primitiveType_NameLabel_CN_Parser = parser;
		}
		return primitiveType_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser operation_DataTypeOperationLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOperation_DataTypeOperationLabel_Parser() {
		if (operation_DataTypeOperationLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			operation_DataTypeOperationLabel_Parser = parser;
		}
		return operation_DataTypeOperationLabel_Parser;
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
			enumerationLiteral_LiteralLabel_Parser = parser;
		}
		return enumerationLiteral_LiteralLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_DataTypeAttributeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_DataTypeAttributeLabel_Parser() {
		if (property_DataTypeAttributeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_DataTypeAttributeLabel_Parser = parser;
		}
		return property_DataTypeAttributeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_ClassAttributeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_ClassAttributeLabel_Parser() {
		if (property_ClassAttributeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_ClassAttributeLabel_Parser = parser;
		}
		return property_ClassAttributeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser operation_ClassOperationLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOperation_ClassOperationLabel_Parser() {
		if (operation_ClassOperationLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			operation_ClassOperationLabel_Parser = parser;
		}
		return operation_ClassOperationLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser stereotype_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getStereotype_NameLabel_CN_Parser() {
		if (stereotype_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			stereotype_NameLabel_CN_Parser = parser;
		}
		return stereotype_NameLabel_CN_Parser;
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
			class_NameLabel_CN_Parser = parser;
		}
		return class_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_MetaclassNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_MetaclassNameLabel_CN_Parser() {
		if (class_MetaclassNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			class_MetaclassNameLabel_CN_Parser = parser;
		}
		return class_MetaclassNameLabel_CN_Parser;
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
	private IParser profile_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getProfile_NameLabel_CN_Parser() {
		if (profile_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			profile_NameLabel_CN_Parser = parser;
		}
		return profile_NameLabel_CN_Parser;
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
			enumeration_NameLabel_CN_Parser = parser;
		}
		return enumeration_NameLabel_CN_Parser;
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
			dataType_NameLabel_CN_Parser = parser;
		}
		return dataType_NameLabel_CN_Parser;
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
	private IParser association_TargetRoleLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_TargetRoleLabel_Parser() {
		if (association_TargetRoleLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("srcRole{0}"); //$NON-NLS-1$
			parser.setEditorPattern("srcRole{0}"); //$NON-NLS-1$
			parser.setEditPattern("srcRole{0}"); //$NON-NLS-1$
			association_TargetRoleLabel_Parser = parser;
		}
		return association_TargetRoleLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser association_SourceRoleLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_SourceRoleLabel_Parser() {
		if (association_SourceRoleLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("targMul{0}"); //$NON-NLS-1$
			parser.setEditorPattern("targMul{0}"); //$NON-NLS-1$
			parser.setEditPattern("targMul{0}"); //$NON-NLS-1$
			association_SourceRoleLabel_Parser = parser;
		}
		return association_SourceRoleLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser association_SourceMultiplicityLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_SourceMultiplicityLabel_Parser() {
		if (association_SourceMultiplicityLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("srcMul{0}"); //$NON-NLS-1$
			parser.setEditorPattern("srcMul{0}"); //$NON-NLS-1$
			parser.setEditPattern("srcMul{0}"); //$NON-NLS-1$
			association_SourceMultiplicityLabel_Parser = parser;
		}
		return association_SourceMultiplicityLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser association_TargetMultiplicityLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_TargetMultiplicityLabel_Parser() {
		if (association_TargetMultiplicityLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("srcMul{0}"); //$NON-NLS-1$
			parser.setEditorPattern("srcMul{0}"); //$NON-NLS-1$
			parser.setEditPattern("srcMul{0}"); //$NON-NLS-1$
			association_TargetMultiplicityLabel_Parser = parser;
		}
		return association_TargetMultiplicityLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser association_BranchMultiplicityLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_BranchMultiplicityLabel_Parser() {
		if (association_BranchMultiplicityLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("srcRole{0}"); //$NON-NLS-1$
			parser.setEditorPattern("srcRole{0}"); //$NON-NLS-1$
			parser.setEditPattern("srcRole{0}"); //$NON-NLS-1$
			association_BranchMultiplicityLabel_Parser = parser;
		}
		return association_BranchMultiplicityLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser generalization_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getGeneralization_StereotypeLabel_Parser() {
		if (generalization_StereotypeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getGeneralization_IsSubstitutable()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("<<{0}>>"); //$NON-NLS-1$
			parser.setEditorPattern("<<{0}>>"); //$NON-NLS-1$
			parser.setEditPattern("<<{0}>>"); //$NON-NLS-1$
			generalization_StereotypeLabel_Parser = parser;
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
	private IParser elementImport_AliasLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getElementImport_AliasLabel_Parser() {
		if (elementImport_AliasLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getElementImport_Alias()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			elementImport_AliasLabel_Parser = parser;
		}
		return elementImport_AliasLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser elementImport_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getElementImport_StereotypeLabel_Parser() {
		if (elementImport_StereotypeLabel_Parser == null) {
			elementImport_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return elementImport_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private PackageImportVisibilityParser packageImport_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPackageImport_StereotypeLabel_Parser() {
		if (packageImport_StereotypeLabel_Parser == null) {
			packageImport_StereotypeLabel_Parser = new PackageImportVisibilityParser();
		}
		return packageImport_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case MultiDependencyLabelEditPart.VISUAL_ID:
				return getDependency_MultiNameLabel_Parser();
			case StereotypeNameEditPart.VISUAL_ID:
				return getStereotype_NameLabel_Parser();
			case ClassNameEditPart.VISUAL_ID:
				return getClass_NameLabel_Parser();
			case MetaclassNameEditPart.VISUAL_ID:
				return getClass_MetaclassNameLabel_Parser();
			case CommentBodyEditPart.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case ConstraintNameEditPart.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintBodyEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case ModelNameEditPart.VISUAL_ID:
				return getModel_NameLabel_Parser();
			case ProfileNameEditPartTN.VISUAL_ID:
				return getProfile_NameLabel_Parser();
			case PackageNameEditPart.VISUAL_ID:
				return getPackage_NameLabel_Parser();
			case EnumerationNameEditPart.VISUAL_ID:
				return getEnumeration_NameLabel_Parser();
			case PrimitiveTypeNameEditPart.VISUAL_ID:
				return getPrimitiveType_NameLabel_Parser();
			case DataTypeNameEditPart.VISUAL_ID:
				return getDataType_NameLabel_Parser();
			case DiagramNameEditPart.VISUAL_ID:
				return getDiagram_NameLabel_Parser();
			case PrimitiveTypeNameEditPartCN.VISUAL_ID:
				return getPrimitiveType_NameLabel_CN_Parser();
			case DataTypeOperationEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_Parser();
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_Parser();
			case DataTypePropertyEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_Parser();
			case ClassPropertyEditPart.VISUAL_ID:
				return getProperty_ClassAttributeLabel_Parser();
			case ClassOperationEditPart.VISUAL_ID:
				return getOperation_ClassOperationLabel_Parser();
			case StereotypeNameEditPartCN.VISUAL_ID:
				return getStereotype_NameLabel_CN_Parser();
			case ClassNameEditPartCN.VISUAL_ID:
				return getClass_NameLabel_CN_Parser();
			case MetaclassNameEditPartCN.VISUAL_ID:
				return getClass_MetaclassNameLabel_CN_Parser();
			case CommentBodyEditPartCN.VISUAL_ID:
				return getComment_BodyLabel_CN_Parser();
			case ModelNameEditPartCN.VISUAL_ID:
				return getModel_NameLabel_CN_Parser();
			case ProfileNameEditPartCN.VISUAL_ID:
				return getProfile_NameLabel_CN_Parser();
			case PackageNameEditPartCN.VISUAL_ID:
				return getPackage_NameLabel_CN_Parser();
			case ConstraintNameEditPartCN.VISUAL_ID:
				return getConstraint_NameLabel_CN_Parser();
			case ConstraintBodyEditPartCN.VISUAL_ID:
				return getConstraint_BodyLabel_CN_Parser();
			case EnumerationNameEditPartCN.VISUAL_ID:
				return getEnumeration_NameLabel_CN_Parser();
			case DataTypeNameEditPartCN.VISUAL_ID:
				return getDataType_NameLabel_CN_Parser();
			case AssociationAppliedStereotypeEditPart.VISUAL_ID:
				return getAssociation_StereotypeLabel_Parser();
			case AssociationNameEditPart.VISUAL_ID:
				return getAssociation_NameLabel_Parser();
			case AssociationRoleTargetEditPart.VISUAL_ID:
				return getAssociation_TargetRoleLabel_Parser();
			case AssociationRoleSourceEditPart.VISUAL_ID:
				return getAssociation_SourceRoleLabel_Parser();
			case AssociationMultiplicitySourceEditPart.VISUAL_ID:
				return getAssociation_SourceMultiplicityLabel_Parser();
			case AssociationMultiplicityTargetEditPart.VISUAL_ID:
				return getAssociation_TargetMultiplicityLabel_Parser();
			case AssociationBranchMultiplicityEditPart.VISUAL_ID:
				return getAssociation_BranchMultiplicityLabel_Parser();
			case GeneralizationAppliedStereotypeEditPart.VISUAL_ID:
				return getGeneralization_StereotypeLabel_Parser();
			case DependencyNameEditPart.VISUAL_ID:
				return getDependency_NameLabel_Parser();
			case DependencyAppliedStereotypeEditPart.VISUAL_ID:
				return getDependency_StereotypeLabel_Parser();
			case ElementImportAliasEditPart.VISUAL_ID:
				return getElementImport_AliasLabel_Parser();
			case AppliedStereotypeElementImportEditPart.VISUAL_ID:
				return getElementImport_StereotypeLabel_Parser();
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
