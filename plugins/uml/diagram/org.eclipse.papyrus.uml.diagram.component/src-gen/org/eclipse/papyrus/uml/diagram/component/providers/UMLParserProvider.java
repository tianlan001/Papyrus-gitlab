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
package org.eclipse.papyrus.uml.diagram.component.providers;

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
import org.eclipse.papyrus.uml.diagram.component.edit.parts.AbstractionAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.AbstractionNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentBodyEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentFloatingLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentFloatingLabelEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentNameEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentRealizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentRealizationNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConnectorAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConnectorNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintNameEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintSpecificationEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DefaultNamedElementNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyNodeFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.GeneralizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceFloatingLabelEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceNameEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceRealizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceRealizationNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ManifestationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ManifestationNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.MultiDependencyLabelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.OperationForInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PropertyForInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PropertyPartNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ReceptionInInterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceFloatingLabelEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.RectangleInterfaceNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.SubstitutionAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.SubstitutionNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.diagram.component.part.UMLVisualIDRegistry;
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
	private IParser interface_ClassifierNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_ClassifierNameLabel_Parser() {
		if (interface_ClassifierNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_ClassifierNameLabel_Parser = parser;
		}
		return interface_ClassifierNameLabel_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_ClassifierFloatingNameLabel_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_ClassifierFloatingNameLabel_Parser() {
		if (interface_ClassifierFloatingNameLabel_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_ClassifierFloatingNameLabel_Parser = parser;
		}
		return interface_ClassifierFloatingNameLabel_Parser;
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
	private IParser interface_ClassifierNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_ClassifierNameLabel_CN_Parser() {
		if (interface_ClassifierNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_ClassifierNameLabel_CN_Parser = parser;
		}
		return interface_ClassifierNameLabel_CN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser interface_ClassifierFloatingNameLabel_CN_Parser;

	/**
	 * @generated
	 */
	private IParser getInterface_ClassifierFloatingNameLabel_CN_Parser() {
		if (interface_ClassifierFloatingNameLabel_CN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			interface_ClassifierFloatingNameLabel_CN_Parser = parser;
		}
		return interface_ClassifierFloatingNameLabel_CN_Parser;
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
			parser.setViewPattern("{0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			component_NameLabel_CCN_Parser = parser;
		}
		return component_NameLabel_CCN_Parser;
	}

	/**
	 * @generated
	 */
	private IParser component_FloatingNameLabel_CCN_Parser;

	/**
	 * @generated
	 */
	private IParser getComponent_FloatingNameLabel_CCN_Parser() {
		if (component_FloatingNameLabel_CCN_Parser == null) {
			EAttribute[] features = new EAttribute[] {
					UMLPackage.eINSTANCE.getNamedElement_Name()
			};
			MessageFormatParser parser = new MessageFormatParser(features);
			component_FloatingNameLabel_CCN_Parser = parser;
		}
		return component_FloatingNameLabel_CCN_Parser;
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
			parser.setViewPattern("{0}"); //$NON-NLS-1$
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
			parser.setViewPattern("{0}"); //$NON-NLS-1$
			parser.setEditorPattern("{0}"); //$NON-NLS-1$
			parser.setEditPattern("{0}"); //$NON-NLS-1$
			property_NameLabel_Parser = parser;
		}
		return property_NameLabel_Parser;
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
	protected IParser getParser(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case MultiDependencyLabelEditPart.VISUAL_ID:
				return getDependency_MultiNameLabel_Parser();
			case DependencyNodeFloatingLabelEditPart.VISUAL_ID:
				return getDependency_FloatingNameLabel_Parser();
			case ComponentNameEditPart.VISUAL_ID:
				return getComponent_NameLabel_Parser();
			case ComponentFloatingLabelEditPart.VISUAL_ID:
				return getComponent_FloatingNameLabel_Parser();
			case ModelNameEditPart.VISUAL_ID:
				return getModel_NameLabel_Parser();
			case PackageNameEditPart.VISUAL_ID:
				return getPackage_NameLabel_Parser();
			case RectangleInterfaceNameEditPart.VISUAL_ID:
				return getInterface_ClassifierNameLabel_Parser();
			case RectangleInterfaceFloatingLabelEditPart.VISUAL_ID:
				return getInterface_ClassifierFloatingNameLabel_Parser();
			case CommentBodyEditPart.VISUAL_ID:
				return getComment_BodyLabel_Parser();
			case ConstraintNameEditPart.VISUAL_ID:
				return getConstraint_NameLabel_Parser();
			case ConstraintSpecificationEditPart.VISUAL_ID:
				return getConstraint_BodyLabel_Parser();
			case DefaultNamedElementNameEditPart.VISUAL_ID:
				return getNamedElement_NameLabel_Parser();
			case InterfaceNameEditPart.VISUAL_ID:
				return getInterface_NameLabel_Parser();
			case InterfaceFloatingLabelEditPart.VISUAL_ID:
				return getInterface_FloatingNameLabel_Parser();
			case PortNameEditPart.VISUAL_ID:
				return getPort_NameLabel_Parser();
			case PortAppliedStereotypeEditPart.VISUAL_ID:
				return getPort_StereotypeLabel_Parser();
			case ModelNameEditPartCN.VISUAL_ID:
				return getModel_NameLabel_CN_Parser();
			case PackageNameEditPartCN.VISUAL_ID:
				return getPackage_NameLabel_CN_Parser();
			case RectangleInterfaceNameEditPartCN.VISUAL_ID:
				return getInterface_ClassifierNameLabel_CN_Parser();
			case RectangleInterfaceFloatingLabelEditPartCN.VISUAL_ID:
				return getInterface_ClassifierFloatingNameLabel_CN_Parser();
			case ComponentNameEditPartCN.VISUAL_ID:
				return getComponent_NameLabel_CCN_Parser();
			case ComponentFloatingLabelEditPartCN.VISUAL_ID:
				return getComponent_FloatingNameLabel_CCN_Parser();
			case ComponentNameEditPartPCN.VISUAL_ID:
				return getComponent_NameLabel_CN_Parser();
			case ComponentFloatingLabelEditPartPCN.VISUAL_ID:
				return getComponent_FloatingNameLabel_CN_Parser();
			case CommentBodyEditPartPCN.VISUAL_ID:
				return getComment_BodyLabel_CN_Parser();
			case ConstraintNameEditPartPCN.VISUAL_ID:
				return getConstraint_NameLabel_CN_Parser();
			case ConstraintSpecificationEditPartPCN.VISUAL_ID:
				return getConstraint_BodyLabel_CN_Parser();
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return getProperty_InterfaceAttributeLabel_Parser();
			case OperationForInterfaceEditPart.VISUAL_ID:
				return getOperation_InterfaceOperationLabel_Parser();
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return getReception_InterfaceReceptionLabel_Parser();
			case InterfaceNameEditPartPCN.VISUAL_ID:
				return getInterface_NameLabel_CN_Parser();
			case InterfaceFloatingLabelEditPartPCN.VISUAL_ID:
				return getInterface_FloatingNameLabel_CN_Parser();
			case PropertyPartNameEditPartCN.VISUAL_ID:
				return getProperty_NameLabel_Parser();
			case UsageNameEditPart.VISUAL_ID:
				return getUsage_NameLabel_Parser();
			case UsageAppliedStereotypeEditPart.VISUAL_ID:
				return getUsage_StereotypeLabel_Parser();
			case InterfaceRealizationNameEditPart.VISUAL_ID:
				return getInterfaceRealization_NameLabel_Parser();
			case InterfaceRealizationAppliedStereotypeEditPart.VISUAL_ID:
				return getInterfaceRealization_StereotypeLabel_Parser();
			case GeneralizationAppliedStereotypeEditPart.VISUAL_ID:
				return getGeneralization_StereotypeLabel_Parser();
			case SubstitutionNameEditPart.VISUAL_ID:
				return getSubstitution_NameLabel_Parser();
			case SubstitutionAppliedStereotypeEditPart.VISUAL_ID:
				return getSubstitution_StereotypeLabel_Parser();
			case ManifestationNameEditPart.VISUAL_ID:
				return getManifestation_NameLabel_Parser();
			case ManifestationAppliedStereotypeEditPart.VISUAL_ID:
				return getManifestation_StereotypeLabel_Parser();
			case ComponentRealizationNameEditPart.VISUAL_ID:
				return getComponentRealization_NameLabel_Parser();
			case ComponentRealizationAppliedStereotypeEditPart.VISUAL_ID:
				return getComponentRealization_StereotypeLabel_Parser();
			case AbstractionNameEditPart.VISUAL_ID:
				return getAbstraction_NameLabel_Parser();
			case AbstractionAppliedStereotypeEditPart.VISUAL_ID:
				return getAbstraction_StereotypeLabel_Parser();
			case DependencyNameEditPart.VISUAL_ID:
				return getDependency_NameLabel_Parser();
			case DependencyAppliedStereotypeEditPart.VISUAL_ID:
				return getDependency_StereotypeLabel_Parser();
			case ConnectorAppliedStereotypeEditPart.VISUAL_ID:
				return getConnector_StereotypeLabel_Parser();
			case ConnectorNameEditPart.VISUAL_ID:
				return getConnector_NameLabel_Parser();
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
