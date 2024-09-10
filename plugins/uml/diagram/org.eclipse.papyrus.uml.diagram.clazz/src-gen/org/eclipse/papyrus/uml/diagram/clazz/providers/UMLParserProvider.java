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
package org.eclipse.papyrus.uml.diagram.clazz.providers;

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
import org.eclipse.papyrus.uml.diagram.clazz.custom.parsers.GeneralizationSetConstraintParser;
import org.eclipse.papyrus.uml.diagram.clazz.custom.parsers.RoleInstanceSpecificationLinkParser;
import org.eclipse.papyrus.uml.diagram.clazz.custom.parsers.SlotParser;
import org.eclipse.papyrus.uml.diagram.clazz.custom.parsers.TemplateBindingParser;
import org.eclipse.papyrus.uml.diagram.clazz.custom.parsers.TemplateParameterParser;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.clazz.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.common.parser.CommentParser;
import org.eclipse.papyrus.uml.diagram.common.parser.ConstraintParser;
import org.eclipse.papyrus.uml.diagram.common.parser.ShortcutDiagramParser;
import org.eclipse.papyrus.uml.diagram.common.parser.packageimport.PackageImportVisibilityParser;
import org.eclipse.papyrus.uml.diagram.common.parser.stereotype.AppliedStereotypeParser;
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
	private IParser dependency_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDependency_FloatingNameLabel_Parser() {
		if (dependency_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dependency_FloatingNameLabel_Parser = parser;
		}
		return dependency_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser associationClass_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociationClass_NameLabel_Parser() {
		if (associationClass_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			associationClass_NameLabel_Parser = parser;
		}
		return associationClass_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser associationClass_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociationClass_FloatingNameLabel_Parser() {
		if (associationClass_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			associationClass_FloatingNameLabel_Parser = parser;
		}
		return associationClass_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser association_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociation_FloatingNameLabel_Parser() {
		if (association_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			association_FloatingNameLabel_Parser = parser;
		}
		return association_FloatingNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser instanceSpecification_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceSpecification_NameLabel_Parser() {
		if (instanceSpecification_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			instanceSpecification_NameLabel_Parser = parser;
		}
		return instanceSpecification_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser instanceSpecification_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceSpecification_FloatingNameLabel_Parser() {
		if (instanceSpecification_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			instanceSpecification_FloatingNameLabel_Parser = parser;
		}
		return instanceSpecification_FloatingNameLabel_Parser;
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
	private ShortcutDiagramParser diagram_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDiagram_NameLabel_Parser() {
		if (diagram_NameLabel_Parser == null) {
			diagram_NameLabel_Parser = new ShortcutDiagramParser();
		}
		return diagram_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser durationObservation_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDurationObservation_FloatingNameLabel_Parser() {
		if (durationObservation_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			durationObservation_FloatingNameLabel_Parser = parser;
		}
		return durationObservation_FloatingNameLabel_Parser;
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
	private IParser timeObservation_FloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTimeObservation_FloatingNameLabel_Parser() {
		if (timeObservation_FloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			timeObservation_FloatingNameLabel_Parser = parser;
		}
		return timeObservation_FloatingNameLabel_Parser;
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
	private IParser property_ComponentAttributeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_ComponentAttributeLabel_Parser() {
		if (property_ComponentAttributeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_ComponentAttributeLabel_Parser = parser;
		}
		return property_ComponentAttributeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_SignalAttributeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_SignalAttributeLabel_Parser() {
		if (property_SignalAttributeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_SignalAttributeLabel_Parser = parser;
		}
		return property_SignalAttributeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_InterfaceAttributeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_InterfaceAttributeLabel_Parser() {
		if (property_InterfaceAttributeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_InterfaceAttributeLabel_Parser = parser;
		}
		return property_InterfaceAttributeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser property_PrimitiveTypeAttributeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getProperty_PrimitiveTypeAttributeLabel_Parser() {
		if (property_PrimitiveTypeAttributeLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			property_PrimitiveTypeAttributeLabel_Parser = parser;
		}
		return property_PrimitiveTypeAttributeLabel_Parser;
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
	private IParser class_ClassNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_ClassNestedClassifierLabel_Parser() {
		if (class_ClassNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			class_ClassNestedClassifierLabel_Parser = parser;
		}
		return class_ClassNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_ComponentNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_ComponentNestedClassifierLabel_Parser() {
		if (class_ComponentNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			class_ComponentNestedClassifierLabel_Parser = parser;
		}
		return class_ComponentNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser class_InterfaceNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClass_InterfaceNestedClassifierLabel_Parser() {
		if (class_InterfaceNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			class_InterfaceNestedClassifierLabel_Parser = parser;
		}
		return class_InterfaceNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser operation_ComponentOperationLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOperation_ComponentOperationLabel_Parser() {
		if (operation_ComponentOperationLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			operation_ComponentOperationLabel_Parser = parser;
		}
		return operation_ComponentOperationLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser operation_InterfaceOperationLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOperation_InterfaceOperationLabel_Parser() {
		if (operation_InterfaceOperationLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			operation_InterfaceOperationLabel_Parser = parser;
		}
		return operation_InterfaceOperationLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser operation_PrimitiveTypeOperationLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOperation_PrimitiveTypeOperationLabel_Parser() {
		if (operation_PrimitiveTypeOperationLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			operation_PrimitiveTypeOperationLabel_Parser = parser;
		}
		return operation_PrimitiveTypeOperationLabel_Parser;
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
	private TemplateParameterParser connectableElementTemplateParameter_TemplateParameterLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getConnectableElementTemplateParameter_TemplateParameterLabel_Parser() {
		if (connectableElementTemplateParameter_TemplateParameterLabel_Parser == null) {
			connectableElementTemplateParameter_TemplateParameterLabel_Parser = new TemplateParameterParser();
		}
		return connectableElementTemplateParameter_TemplateParameterLabel_Parser;
	}

	/**
	 * @generated
	 */
	private TemplateParameterParser operationTemplateParameter_TemplateParameterLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getOperationTemplateParameter_TemplateParameterLabel_Parser() {
		if (operationTemplateParameter_TemplateParameterLabel_Parser == null) {
			operationTemplateParameter_TemplateParameterLabel_Parser = new TemplateParameterParser();
		}
		return operationTemplateParameter_TemplateParameterLabel_Parser;
	}

	/**
	 * @generated
	 */
	private TemplateParameterParser classifierTemplateParameter_TemplateParameterLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getClassifierTemplateParameter_TemplateParameterLabel_Parser() {
		if (classifierTemplateParameter_TemplateParameterLabel_Parser == null) {
			classifierTemplateParameter_TemplateParameterLabel_Parser = new TemplateParameterParser();
		}
		return classifierTemplateParameter_TemplateParameterLabel_Parser;
	}

	/**
	 * @generated
	 */
	private TemplateParameterParser templateParameter_TemplateParameterLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTemplateParameter_TemplateParameterLabel_Parser() {
		if (templateParameter_TemplateParameterLabel_Parser == null) {
			templateParameter_TemplateParameterLabel_Parser = new TemplateParameterParser();
		}
		return templateParameter_TemplateParameterLabel_Parser;
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
	private IParser reception_ReceptionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReception_ReceptionLabel_Parser() {
		if (reception_ReceptionLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("\u00ABSignal\u00BB {0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			reception_ReceptionLabel_Parser = parser;
		}
		return reception_ReceptionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser reception_InterfaceReceptionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getReception_InterfaceReceptionLabel_Parser() {
		if (reception_InterfaceReceptionLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("\u00ABSignal\u00BB {0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			reception_InterfaceReceptionLabel_Parser = parser;
		}
		return reception_InterfaceReceptionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private SlotParser slot_SlotLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSlot_SlotLabel_Parser() {
		if (slot_SlotLabel_Parser == null) {
			slot_SlotLabel_Parser = new SlotParser();
		}
		return slot_SlotLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser instanceSpecification_NameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceSpecification_NameLabel_CN_Parser() {
		if (instanceSpecification_NameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			instanceSpecification_NameLabel_CN_Parser = parser;
		}
		return instanceSpecification_NameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser instanceSpecification_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceSpecification_FloatingNameLabel_CN_Parser() {
		if (instanceSpecification_FloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			instanceSpecification_FloatingNameLabel_CN_Parser = parser;
		}
		return instanceSpecification_FloatingNameLabel_CN_Parser;
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
	private ConstraintParser constraint_FloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getConstraint_FloatingNameLabel_CN_Parser() {
		if (constraint_FloatingNameLabel_CN_Parser == null) {
			constraint_FloatingNameLabel_CN_Parser = new ConstraintParser();
		}
		return constraint_FloatingNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_ClassNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_ClassNestedClassifierLabel_Parser() {
		if (interface_ClassNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_ClassNestedClassifierLabel_Parser = parser;
		}
		return interface_ClassNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_ComponentNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_ComponentNestedClassifierLabel_Parser() {
		if (interface_ComponentNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_ComponentNestedClassifierLabel_Parser = parser;
		}
		return interface_ComponentNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_InterfaceNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_InterfaceNestedClassifierLabel_Parser() {
		if (interface_InterfaceNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_InterfaceNestedClassifierLabel_Parser = parser;
		}
		return interface_InterfaceNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumeration_ClassNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumeration_ClassNestedClassifierLabel_Parser() {
		if (enumeration_ClassNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			enumeration_ClassNestedClassifierLabel_Parser = parser;
		}
		return enumeration_ClassNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumeration_ComponentNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumeration_ComponentNestedClassifierLabel_Parser() {
		if (enumeration_ComponentNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			enumeration_ComponentNestedClassifierLabel_Parser = parser;
		}
		return enumeration_ComponentNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser enumeration_InterfaceNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getEnumeration_InterfaceNestedClassifierLabel_Parser() {
		if (enumeration_InterfaceNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			enumeration_InterfaceNestedClassifierLabel_Parser = parser;
		}
		return enumeration_InterfaceNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser primitiveType_ClassNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPrimitiveType_ClassNestedClassifierLabel_Parser() {
		if (primitiveType_ClassNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			primitiveType_ClassNestedClassifierLabel_Parser = parser;
		}
		return primitiveType_ClassNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser primitiveType_ComponentNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPrimitiveType_ComponentNestedClassifierLabel_Parser() {
		if (primitiveType_ComponentNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			primitiveType_ComponentNestedClassifierLabel_Parser = parser;
		}
		return primitiveType_ComponentNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser primitiveType_InterfaceNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getPrimitiveType_InterfaceNestedClassifierLabel_Parser() {
		if (primitiveType_InterfaceNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			primitiveType_InterfaceNestedClassifierLabel_Parser = parser;
		}
		return primitiveType_InterfaceNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataType_ClassNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataType_ClassNestedClassifierLabel_Parser() {
		if (dataType_ClassNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dataType_ClassNestedClassifierLabel_Parser = parser;
		}
		return dataType_ClassNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataType_ComponentNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataType_ComponentNestedClassifierLabel_Parser() {
		if (dataType_ComponentNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dataType_ComponentNestedClassifierLabel_Parser = parser;
		}
		return dataType_ComponentNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser dataType_InterfaceNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getDataType_InterfaceNestedClassifierLabel_Parser() {
		if (dataType_InterfaceNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			dataType_InterfaceNestedClassifierLabel_Parser = parser;
		}
		return dataType_InterfaceNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signal_ClassNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSignal_ClassNestedClassifierLabel_Parser() {
		if (signal_ClassNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			signal_ClassNestedClassifierLabel_Parser = parser;
		}
		return signal_ClassNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signal_ComponentNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSignal_ComponentNestedClassifierLabel_Parser() {
		if (signal_ComponentNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			signal_ComponentNestedClassifierLabel_Parser = parser;
		}
		return signal_ComponentNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser signal_InterfaceNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getSignal_InterfaceNestedClassifierLabel_Parser() {
		if (signal_InterfaceNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			signal_InterfaceNestedClassifierLabel_Parser = parser;
		}
		return signal_InterfaceNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_ClassNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_ClassNestedClassifierLabel_Parser() {
		if (component_ClassNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			component_ClassNestedClassifierLabel_Parser = parser;
		}
		return component_ClassNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_InterfaceNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_InterfaceNestedClassifierLabel_Parser() {
		if (component_InterfaceNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			component_InterfaceNestedClassifierLabel_Parser = parser;
		}
		return component_InterfaceNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_ComponentNestedClassifierLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_ComponentNestedClassifierLabel_Parser() {
		if (component_ComponentNestedClassifierLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			component_ComponentNestedClassifierLabel_Parser = parser;
		}
		return component_ComponentNestedClassifierLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser associationClass_SourceRoleLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociationClass_SourceRoleLabel_Parser() {
		if (associationClass_SourceRoleLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("srcRole{0}"); //$NON-NLS-1$
			parser.setEditorPattern("srcRole{0}"); //$NON-NLS-1$
			parser.setEditPattern("srcRole{0}"); //$NON-NLS-1$
			associationClass_SourceRoleLabel_Parser = parser;
		}
		return associationClass_SourceRoleLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser associationClass_TargetRoleLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getAssociationClass_TargetRoleLabel_Parser() {
		if (associationClass_TargetRoleLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			parser.setViewPattern("targetRole{0}"); //$NON-NLS-1$
			parser.setEditorPattern("targetRole{0}"); //$NON-NLS-1$
			parser.setEditPattern("targetRole{0}"); //$NON-NLS-1$
			associationClass_TargetRoleLabel_Parser = parser;
		}
		return associationClass_TargetRoleLabel_Parser;
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
	private TemplateBindingParser templateBinding_SubstitutionLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTemplateBinding_SubstitutionLabel_Parser() {
		if (templateBinding_SubstitutionLabel_Parser == null) {
			templateBinding_SubstitutionLabel_Parser = new TemplateBindingParser();
		}
		return templateBinding_SubstitutionLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser templateBinding_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getTemplateBinding_StereotypeLabel_Parser() {
		if (templateBinding_StereotypeLabel_Parser == null) {
			templateBinding_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return templateBinding_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private GeneralizationSetConstraintParser generalizationSet_ConstraintLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getGeneralizationSet_ConstraintLabel_Parser() {
		if (generalizationSet_ConstraintLabel_Parser == null) {
			generalizationSet_ConstraintLabel_Parser = new GeneralizationSetConstraintParser();
		}
		return generalizationSet_ConstraintLabel_Parser;
	}

	/**
	 * @generated
	 */
	private AppliedStereotypeParser generalizationSet_StereotypeLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getGeneralizationSet_StereotypeLabel_Parser() {
		if (generalizationSet_StereotypeLabel_Parser == null) {
			generalizationSet_StereotypeLabel_Parser = new AppliedStereotypeParser();
		}
		return generalizationSet_StereotypeLabel_Parser;
	}

	/**
	 * @generated
	 */
	private RoleInstanceSpecificationLinkParser instanceSpecification_SourceRoleLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceSpecification_SourceRoleLabel_Parser() {
		if (instanceSpecification_SourceRoleLabel_Parser == null) {
			instanceSpecification_SourceRoleLabel_Parser = new RoleInstanceSpecificationLinkParser();
		}
		return instanceSpecification_SourceRoleLabel_Parser;
	}

	/**
	 * @generated
	 */
	private RoleInstanceSpecificationLinkParser instanceSpecification_TargetRoleLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInstanceSpecification_TargetRoleLabel_Parser() {
		if (instanceSpecification_TargetRoleLabel_Parser == null) {
			instanceSpecification_TargetRoleLabel_Parser = new RoleInstanceSpecificationLinkParser();
		}
		return instanceSpecification_TargetRoleLabel_Parser;
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
	private IParser informationFlow_NameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInformationFlow_NameLabel_Parser() {
		if (informationFlow_NameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			informationFlow_NameLabel_Parser = parser;
		}
		return informationFlow_NameLabel_Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case MultiDependencyLabelEditPart.VISUAL_ID:
				return getDependency_MultiNameLabel_Parser();
			case DependencyFloatingNameEditPart.VISUAL_ID:
				return getDependency_FloatingNameLabel_Parser();
			case AssociationClassNameEditPart.VISUAL_ID:
				return getAssociationClass_NameLabel_Parser();
			case AssociationClassFloatingNameEditPart.VISUAL_ID:
				return getAssociationClass_FloatingNameLabel_Parser();
			case AssociationFloatingNameEditPart.VISUAL_ID:
				return getAssociation_FloatingNameLabel_Parser();
			case InstanceSpecificationNameEditPart.VISUAL_ID:
				return getInstanceSpecification_NameLabel_Parser();
			case InstanceSpecificationFloatingNameEditPart.VISUAL_ID:
				return getInstanceSpecification_FloatingNameLabel_Parser();
			case ComponentNameEditPart.VISUAL_ID:
				return getComponent_NameLabel_Parser();
			case ComponentFloatingNameEditPart.VISUAL_ID:
				return getComponent_FloatingNameLabel_Parser();
			case SignalNameEditPart.VISUAL_ID:
				return getSignal_NameLabel_Parser();
			case SignalFloatingNameEditPart.VISUAL_ID:
				return getSignal_FloatingNameLabel_Parser();
			case InterfaceNameEditPart.VISUAL_ID:
				return getInterface_NameLabel_Parser();
			case InterfaceFloatingNameEditPart.VISUAL_ID:
				return getInterface_FloatingNameLabel_Parser();
			case ModelNameEditPartTN.VISUAL_ID:
				return getModel_NameLabel_Parser();
			case EnumerationNameEditPart.VISUAL_ID:
				return getEnumeration_NameLabel_Parser();
			case EnumerationFloatingNameEditPart.VISUAL_ID:
				return getEnumeration_FloatingNameLabel_Parser();
			case PackageNameEditPart.VISUAL_ID:
				return getPackage_NameLabel_Parser();
			case InformationItemNameEditPart.VISUAL_ID:
				return getInformationItem_NameLabel_Parser();
			case InformationItemFloatingNameEditPart.VISUAL_ID:
				return getInformationItem_FloatingNameLabel_Parser();
			case ClassNameEditPart.VISUAL_ID:
				return getClass_NameLabel_Parser();
			case ClassFloatingNameEditPart.VISUAL_ID:
				return getClass_FloatingNameLabel_Parser();
			case PrimitiveTypeNameEditPart.VISUAL_ID:
				return getPrimitiveType_NameLabel_Parser();
			case PrimitiveTypeFloatingNameEditPart.VISUAL_ID:
				return getPrimitiveType_FloatingNameLabel_Parser();
			case DataTypeNameEditPart.VISUAL_ID:
				return getDataType_NameLabel_Parser();
			case DataTypeFloatingNameEditPart.VISUAL_ID:
				return getDataType_FloatingNameLabel_Parser();
			case ConstraintNameEditPart.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintBodyEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case CommentBodyEditPart.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case DiagramNameEditPart.VISUAL_ID:
				return getDiagram_NameLabel_Parser();
			case DurationObservationFloatingNameEditPart.VISUAL_ID:
				return getDurationObservation_FloatingNameLabel_Parser();
			case DurationObservationStereotypeLabelEditPart.VISUAL_ID:
				return getDurationObservation_StereotypeLabel_Parser();
			case TimeObservationFloatingNameEditPart.VISUAL_ID:
				return getTimeObservation_FloatingNameLabel_Parser();
			case TimeObservationStereotypeLabelEditPart.VISUAL_ID:
				return getTimeObservation_StereotypeLabel_Parser();
			case DefaultNamedElementNameEditPart.VISUAL_ID:
				return getNamedElement_NameLabel_Parser();
			case PropertyForComponentEditPart.VISUAL_ID:
				return getProperty_ComponentAttributeLabel_Parser();
			case PropertyForSignalEditPart.VISUAL_ID:
				return getProperty_SignalAttributeLabel_Parser();
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return getProperty_InterfaceAttributeLabel_Parser();
			case PropertyforPrimitiveTypeEditPart.VISUAL_ID:
				return getProperty_PrimitiveTypeAttributeLabel_Parser();
			case PropertyforDataTypeEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_Parser();
			case NestedClassForClassEditPart.VISUAL_ID:
				return getClass_ClassNestedClassifierLabel_Parser();
			case NestedClassForComponentEditPart.VISUAL_ID:
				return getClass_ComponentNestedClassifierLabel_Parser();
			case NestedClassForInterfaceEditPart.VISUAL_ID:
				return getClass_InterfaceNestedClassifierLabel_Parser();
			case OperationForComponentEditPart.VISUAL_ID:
				return getOperation_ComponentOperationLabel_Parser();
			case OperationForInterfaceEditpart.VISUAL_ID:
				return getOperation_InterfaceOperationLabel_Parser();
			case OperationForPrimitiveTypeEditPart.VISUAL_ID:
				return getOperation_PrimitiveTypeOperationLabel_Parser();
			case OperationForDataTypeEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_Parser();
			case ConnectableElementTemplateParameterEditPart.VISUAL_ID:
				return getConnectableElementTemplateParameter_TemplateParameterLabel_Parser();
			case OperationTemplateParameterEditPart.VISUAL_ID:
				return getOperationTemplateParameter_TemplateParameterLabel_Parser();
			case ClassifierTemplateParameterEditPart.VISUAL_ID:
				return getClassifierTemplateParameter_TemplateParameterLabel_Parser();
			case TemplateParameterEditPart.VISUAL_ID:
				return getTemplateParameter_TemplateParameterLabel_Parser();
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_Parser();
			case ReceptionEditPart.VISUAL_ID:
				return getReception_ReceptionLabel_Parser();
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return getReception_InterfaceReceptionLabel_Parser();
			case SlotEditPart.VISUAL_ID:
				return getSlot_SlotLabel_Parser();
			case InstanceSpecificationNameEditPartCN.VISUAL_ID:
				return getInstanceSpecification_NameLabel_CN_Parser();
			case InstanceSpecificationFloatingNameEditPartCN.VISUAL_ID:
				return getInstanceSpecification_FloatingNameLabel_CN_Parser();
			case ComponentNameEditPartCN.VISUAL_ID:
				return getComponent_NameLabel_CN_Parser();
			case ComponentFloatingNameEditPartCN.VISUAL_ID:
				return getComponent_FloatingNameLabel_CN_Parser();
			case SignalNameEditPartCN.VISUAL_ID:
				return getSignal_NameLabel_CN_Parser();
			case SignalFloatingNameEditPartCN.VISUAL_ID:
				return getSignal_FloatingNameLabel_CN_Parser();
			case InterfaceNameEditPartCN.VISUAL_ID:
				return getInterface_NameLabel_CN_Parser();
			case InterfaceFloatingNameEditPartCN.VISUAL_ID:
				return getInterface_FloatingNameLabel_CN_Parser();
			case ModelNameEditPartCN.VISUAL_ID:
				return getModel_NameLabel_CN_Parser();
			case EnumerationNameEditPartCN.VISUAL_ID:
				return getEnumeration_NameLabel_CN_Parser();
			case EnumerationFloatingNameEditPartCN.VISUAL_ID:
				return getEnumeration_FloatingNameLabel_CN_Parser();
			case PackageNameEditPartCN.VISUAL_ID:
				return getPackage_NameLabel_CN_Parser();
			case InformationItemNameEditPartCN.VISUAL_ID:
				return getInformationItem_NameLabel_CN_Parser();
			case InformationItemFloatingNameEditPartCN.VISUAL_ID:
				return getInformationItem_FloatingNameLabel_CN_Parser();
			case ClassNameEditPartCN.VISUAL_ID:
				return getClass_NameLabel_CN_Parser();
			case ClassFloatingNameEditPartCN.VISUAL_ID:
				return getClass_FloatingNameLabel_CN_Parser();
			case PrimitiveTypeNameEditPartCN.VISUAL_ID:
				return getPrimitiveType_NameLabel_CN_Parser();
			case PrimitiveTypeFloatingNameEditPartCN.VISUAL_ID:
				return getPrimitiveType_FloatingNameLabel_CN_Parser();
			case DataTypeNameEditPartCN.VISUAL_ID:
				return getDataType_NameLabel_CN_Parser();
			case DataTypeFloatingNameEditPartCN.VISUAL_ID:
				return getDataType_FloatingNameLabel_CN_Parser();
			case CommentBodyEditPartCN.VISUAL_ID:
				return getComment_BodyLabel_CN_Parser();
			case ConstraintNameEditPartCN.VISUAL_ID:
				return getConstraint_NameLabel_CN_Parser();
			case ConstraintBodyEditPartCN.VISUAL_ID:
				return getConstraint_FloatingNameLabel_CN_Parser();
			case NestedInterfaceForClassEditPart.VISUAL_ID:
				return getInterface_ClassNestedClassifierLabel_Parser();
			case NestedInterfaceForComponentEditPart.VISUAL_ID:
				return getInterface_ComponentNestedClassifierLabel_Parser();
			case NestedInterfaceForInterfaceEditPart.VISUAL_ID:
				return getInterface_InterfaceNestedClassifierLabel_Parser();
			case NestedEnumerationForClassEditPart.VISUAL_ID:
				return getEnumeration_ClassNestedClassifierLabel_Parser();
			case NestedEnumerationForComponentEditPart.VISUAL_ID:
				return getEnumeration_ComponentNestedClassifierLabel_Parser();
			case NestedEnumerationForInterfaceEditPart.VISUAL_ID:
				return getEnumeration_InterfaceNestedClassifierLabel_Parser();
			case NestedPrimitiveTypeForClassEditPart.VISUAL_ID:
				return getPrimitiveType_ClassNestedClassifierLabel_Parser();
			case NestedPrimitiveTypeForComponentEditPart.VISUAL_ID:
				return getPrimitiveType_ComponentNestedClassifierLabel_Parser();
			case NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID:
				return getPrimitiveType_InterfaceNestedClassifierLabel_Parser();
			case NestedDataTypeForClassEditPart.VISUAL_ID:
				return getDataType_ClassNestedClassifierLabel_Parser();
			case NestedDataTypeForComponentEditPart.VISUAL_ID:
				return getDataType_ComponentNestedClassifierLabel_Parser();
			case NestedDataTypeForInterfaceEditPart.VISUAL_ID:
				return getDataType_InterfaceNestedClassifierLabel_Parser();
			case NestedSignalForClassEditPart.VISUAL_ID:
				return getSignal_ClassNestedClassifierLabel_Parser();
			case NestedSignalForComponentEditPart.VISUAL_ID:
				return getSignal_ComponentNestedClassifierLabel_Parser();
			case NestedSignalForInterfaceEditPart.VISUAL_ID:
				return getSignal_InterfaceNestedClassifierLabel_Parser();
			case NestedComponentForClassEditPart.VISUAL_ID:
				return getComponent_ClassNestedClassifierLabel_Parser();
			case NestedComponentForInterfaceEditPart.VISUAL_ID:
				return getComponent_InterfaceNestedClassifierLabel_Parser();
			case NestedComponentForComponentEditPart.VISUAL_ID:
				return getComponent_ComponentNestedClassifierLabel_Parser();
			case AssociationClassRoleSourceEditPart.VISUAL_ID:
				return getAssociationClass_SourceRoleLabel_Parser();
			case AssociationClassRoleTargetEditPart.VISUAL_ID:
				return getAssociationClass_TargetRoleLabel_Parser();
			case AppliedStereotypeAssociationEditPart.VISUAL_ID:
				return getAssociation_StereotypeLabel_Parser();
			case AssociationNameEditPart.VISUAL_ID:
				return getAssociation_NameLabel_Parser();
			case AssociationTargetNameEditPart.VISUAL_ID:
				return getAssociation_TargetRoleLabel_Parser();
			case AssociationSourceNameEditPart.VISUAL_ID:
				return getAssociation_SourceRoleLabel_Parser();
			case AssociationMultiplicitySourceEditPart.VISUAL_ID:
				return getAssociation_SourceMultiplicityLabel_Parser();
			case AssociationMultiplicityTargetEditPart.VISUAL_ID:
				return getAssociation_TargetMultiplicityLabel_Parser();
			case AssociationBranchMutliplicityEditPart.VISUAL_ID:
				return getAssociation_BranchMultiplicityLabel_Parser();
			case AppliedStereotyperGeneralizationEditPart.VISUAL_ID:
				return getGeneralization_StereotypeLabel_Parser();
			case AppliedStereotypeInterfaceRealizationEditPart.VISUAL_ID:
				return getInterfaceRealization_StereotypeLabel_Parser();
			case InterfaceRealizationNameEditPart.VISUAL_ID:
				return getInterfaceRealization_NameLabel_Parser();
			case AppliedStereotypeSubstitutionEditPart.VISUAL_ID:
				return getSubstitution_StereotypeLabel_Parser();
			case SubstitutionNameEditPart.VISUAL_ID:
				return getSubstitution_NameLabel_Parser();
			case AppliedStereotypeRealizationEditPart.VISUAL_ID:
				return getRealization_StereotypeLabel_Parser();
			case RealizationNameEditPart.VISUAL_ID:
				return getRealization_NameLabel_Parser();
			case AbstractionNameEditPart.VISUAL_ID:
				return getAbstraction_NameLabel_Parser();
			case AppliedStereotypeAbstractionEditPart.VISUAL_ID:
				return getAbstraction_StereotypeLabel_Parser();
			case UsageNameEditPart.VISUAL_ID:
				return getUsage_NameLabel_Parser();
			case AppliedStereotypeUsageEditPart.VISUAL_ID:
				return getUsage_StereotypeLabel_Parser();
			case DependencyNameEditPart.VISUAL_ID:
				return getDependency_NameLabel_Parser();
			case AppliedStereotypeDependencyEditPart.VISUAL_ID:
				return getDependency_StereotypeLabel_Parser();
			case ElementImportAliasEditPart.VISUAL_ID:
				return getElementImport_AliasLabel_Parser();
			case AppliedStereotypeElementImportEditPart.VISUAL_ID:
				return getElementImport_StereotypeLabel_Parser();
			case AppliedStereotypePackageImportEditPart.VISUAL_ID:
				return getPackageImport_StereotypeLabel_Parser();
			case AppliedStereotypePackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_StereotypeLabel_Parser();
			case BindingSubstitutionEditPart.VISUAL_ID:
				return getTemplateBinding_SubstitutionLabel_Parser();
			case AppliedStereotypeTemplateBindingEditPart.VISUAL_ID:
				return getTemplateBinding_StereotypeLabel_Parser();
			case ConstraintLabelEditPart.VISUAL_ID:
				return getGeneralizationSet_ConstraintLabel_Parser();
			case AppliedStereotypeGeneralizationSetLabelEditPart.VISUAL_ID:
				return getGeneralizationSet_StereotypeLabel_Parser();
			case SourceISLinkLabelEditPart.VISUAL_ID:
				return getInstanceSpecification_SourceRoleLabel_Parser();
			case TargetISLinkLabelEditPart.VISUAL_ID:
				return getInstanceSpecification_TargetRoleLabel_Parser();
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
