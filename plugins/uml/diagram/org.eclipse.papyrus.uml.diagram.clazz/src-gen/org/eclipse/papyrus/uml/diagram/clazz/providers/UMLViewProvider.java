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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateChildViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewOperation;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper;

/**
 * @generated
 */
public class UMLViewProvider extends AbstractProvider implements IViewProvider {

	/**
	 * @generated
	 */
	@Override
	public final boolean provides(IOperation operation) {
		if (operation instanceof CreateViewForKindOperation) {
			return provides((CreateViewForKindOperation) operation);
		}
		assert operation instanceof CreateViewOperation;
		/* we check this view provider is the good one for the currently edited diagram */
		if (operation instanceof CreateChildViewOperation) {
			View container = ((CreateChildViewOperation) operation).getContainerView();
			Diagram diagram = container.getDiagram();
			if (!getDiagramProvidedId().equals(diagram.getType())) {
				return false;
			}
		}
		if (operation instanceof CreateDiagramViewOperation) {
			return provides((CreateDiagramViewOperation) operation);
		} else if (operation instanceof CreateEdgeViewOperation) {
			return provides((CreateEdgeViewOperation) operation);
		} else if (operation instanceof CreateNodeViewOperation) {
			return provides((CreateNodeViewOperation) operation);
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateViewForKindOperation op) {
		// check Diagram Type should be the class diagram
		String modelID = UMLVisualIDRegistry.getModelID(op.getContainerView());
		if (!getDiagramProvidedId().equals(modelID)) {
			return false;
		}
		String visualID = UMLVisualIDRegistry.getVisualID(op.getSemanticHint());
		if (Node.class.isAssignableFrom(op.getViewKind())) {
			return UMLVisualIDRegistry.canCreateNode(op.getContainerView(), visualID);
		}
		return true;
	}

	/**
	 * @generated
	 */
	protected String getDiagramProvidedId() {
		/*
		 * Indicates for which diagram this provider works for.
		 * <p>
		 * This method can be overloaded when diagram editor inherits from another one, but should never be <code>null</code>
		 * </p>
		 *
		 * @return the unique identifier of the diagram for which views are provided.
		 */
		return ModelEditPart.MODEL_ID;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateDiagramViewOperation op) {
		return ModelEditPart.MODEL_ID.equals(op.getSemanticHint()) && UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())) != null && !UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())).isEmpty();
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateNodeViewOperation op) {
		if (op.getContainerView() == null) {
			return false;
		}
		IElementType elementType = getSemanticElementType(op.getSemanticAdapter());
		EObject domainElement = getSemanticElement(op.getSemanticAdapter());
		String visualID;
		if (op.getSemanticHint() == null) {
			// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
			// In this situation there should be NO elementType, visualID will be determined
			// by VisualIDRegistry.getNodeVisualID() for domainElement.
			if (elementType != null || domainElement == null) {
				return false;
			}
			visualID = UMLVisualIDRegistry.getNodeVisualID(op.getContainerView(), domainElement);
		} else {
			visualID = UMLVisualIDRegistry.getVisualID(op.getSemanticHint());
			if (elementType != null) {
				if (!UMLElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
					return false; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
				if (!op.getSemanticHint().equals(elementTypeHint)) {
					return false; // if semantic hint is specified it should be the same as in element type
				}
			} else {
				if (!ModelEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(op.getContainerView()))) {
					return false; // foreign diagram
				}
				if (visualID != null) {
					switch (visualID) {
					case DependencyNodeEditPart.VISUAL_ID:
					case AssociationClassEditPart.VISUAL_ID:
					case AssociationNodeEditPart.VISUAL_ID:
					case InstanceSpecificationEditPart.VISUAL_ID:
					case ComponentEditPart.VISUAL_ID:
					case SignalEditPart.VISUAL_ID:
					case InterfaceEditPart.VISUAL_ID:
					case ModelEditPartTN.VISUAL_ID:
					case InformationItemEditPart.VISUAL_ID:
					case ShortCutDiagramEditPart.VISUAL_ID:
					case DurationObservationEditPart.VISUAL_ID:
					case TimeObservationEditPart.VISUAL_ID:
					case DefaultNamedElementEditPart.VISUAL_ID:
					case PropertyForComponentEditPart.VISUAL_ID:
					case NestedClassForComponentEditPart.VISUAL_ID:
					case OperationForComponentEditPart.VISUAL_ID:
					case ConnectableElementTemplateParameterEditPart.VISUAL_ID:
					case OperationTemplateParameterEditPart.VISUAL_ID:
					case ClassifierTemplateParameterEditPart.VISUAL_ID:
					case TemplateParameterEditPart.VISUAL_ID:
					case EnumerationLiteralEditPart.VISUAL_ID:
					case ReceptionEditPart.VISUAL_ID:
					case ReceptionInInterfaceEditPart.VISUAL_ID:
					case SlotEditPart.VISUAL_ID:
					case RedefinableTemplateSignatureEditPart.VISUAL_ID:
					case TemplateSignatureEditPart.VISUAL_ID:
					case EnumerationEditPartCN.VISUAL_ID:
					case InformationItemEditPartCN.VISUAL_ID:
					case PrimitiveTypeEditPartCN.VISUAL_ID:
					case DataTypeEditPartCN.VISUAL_ID:
					case CommentEditPartCN.VISUAL_ID:
					case ConstraintEditPartCN.VISUAL_ID:
					case NestedInterfaceForComponentEditPart.VISUAL_ID:
					case EnumerationEditPart.VISUAL_ID:
					case PackageEditPart.VISUAL_ID:
					case ClassEditPart.VISUAL_ID:
					case PrimitiveTypeEditPart.VISUAL_ID:
					case DataTypeEditPart.VISUAL_ID:
					case ConstraintEditPart.VISUAL_ID:
					case CommentEditPart.VISUAL_ID:
					case PropertyForClassEditPart.VISUAL_ID:
					case PropertyForSignalEditPart.VISUAL_ID:
					case PropertyForInterfaceEditPart.VISUAL_ID:
					case PropertyforPrimitiveTypeEditPart.VISUAL_ID:
					case PropertyforDataTypeEditPart.VISUAL_ID:
					case NestedClassForClassEditPart.VISUAL_ID:
					case NestedClassForInterfaceEditPart.VISUAL_ID:
					case OperationForClassEditPart.VISUAL_ID:
					case OperationForInterfaceEditpart.VISUAL_ID:
					case OperationForPrimitiveTypeEditPart.VISUAL_ID:
					case OperationForDataTypeEditPart.VISUAL_ID:
					case InstanceSpecificationEditPartCN.VISUAL_ID:
					case ComponentEditPartCN.VISUAL_ID:
					case SignalEditPartCN.VISUAL_ID:
					case InterfaceEditPartCN.VISUAL_ID:
					case ModelEditPartCN.VISUAL_ID:
					case PackageEditPartCN.VISUAL_ID:
					case ClassEditPartCN.VISUAL_ID:
					case NestedInterfaceForClassEditPart.VISUAL_ID:
					case NestedInterfaceForInterfaceEditPart.VISUAL_ID:
					case NestedEnumerationForClassEditPart.VISUAL_ID:
					case NestedEnumerationForComponentEditPart.VISUAL_ID:
					case NestedEnumerationForInterfaceEditPart.VISUAL_ID:
					case NestedPrimitiveTypeForClassEditPart.VISUAL_ID:
					case NestedPrimitiveTypeForComponentEditPart.VISUAL_ID:
					case NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID:
					case NestedDataTypeForClassEditPart.VISUAL_ID:
					case NestedDataTypeForComponentEditPart.VISUAL_ID:
					case NestedDataTypeForInterfaceEditPart.VISUAL_ID:
					case NestedSignalForClassEditPart.VISUAL_ID:
					case NestedSignalForComponentEditPart.VISUAL_ID:
					case NestedSignalForInterfaceEditPart.VISUAL_ID:
					case NestedComponentForClassEditPart.VISUAL_ID:
					case NestedComponentForInterfaceEditPart.VISUAL_ID:
					case NestedComponentForComponentEditPart.VISUAL_ID:
						if (domainElement == null || !visualID.equals(UMLVisualIDRegistry.getNodeVisualID(op.getContainerView(), domainElement))) {
							return false; // visual id in semantic hint should match visual id for domain element
						}
						break;
					default:
						return false;
					}
				}
			}
		}
		return UMLVisualIDRegistry.canCreateNode(op.getContainerView(), visualID);
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateEdgeViewOperation op) {
		IElementType elementType = getSemanticElementType(op.getSemanticAdapter());
		if (!UMLElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
			return false; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null || (op.getSemanticHint() != null && !elementTypeHint.equals(op.getSemanticHint()))) {
			return false; // our hint is visual id and must be specified, and it should be the same as in element type
		}
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Diagram createDiagram(IAdaptable semanticAdapter, String diagramKind, PreferencesHint preferencesHint) {
		Diagram diagram = NotationFactory.eINSTANCE.createDiagram();
		DiagramVersioningUtils.stampCurrentVersion(diagram);
		diagram.getStyles().add(NotationFactory.eINSTANCE.createDiagramStyle());
		diagram.setType(ModelEditPart.MODEL_ID);
		diagram.setElement(getSemanticElement(semanticAdapter));
		diagram.setMeasurementUnit(MeasurementUnit.PIXEL_LITERAL);
		return diagram;
	}

	/**
	 * @generated
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
		if (visualID != null) {
			switch (visualID) {
			case DependencyNodeEditPart.VISUAL_ID:
				return createDependency_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case AssociationClassEditPart.VISUAL_ID:
				return createAssociationClass_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case AssociationNodeEditPart.VISUAL_ID:
				return createAssociation_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InstanceSpecificationEditPart.VISUAL_ID:
				return createInstanceSpecification_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ComponentEditPart.VISUAL_ID:
				return createComponent_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case SignalEditPart.VISUAL_ID:
				return createSignal_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InterfaceEditPart.VISUAL_ID:
				return createInterface_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ModelEditPartTN.VISUAL_ID:
				return createModel_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case EnumerationEditPart.VISUAL_ID:
				return createEnumeration_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case PackageEditPart.VISUAL_ID:
				return createPackage_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InformationItemEditPart.VISUAL_ID:
				return createInformationItem_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ClassEditPart.VISUAL_ID:
				return createClass_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return createPrimitiveType_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DataTypeEditPart.VISUAL_ID:
				return createDataType_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintEditPart.VISUAL_ID:
				return createConstraint_PackagedElementShape(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPart.VISUAL_ID:
				return createComment_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return createDiagram_ShortcutShape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationObservationEditPart.VISUAL_ID:
				return createDurationObservation_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeObservationEditPart.VISUAL_ID:
				return createTimeObservation_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return createNamedElement_DefaultShape(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyForClassEditPart.VISUAL_ID:
				return createProperty_ClassAttributeLabel(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyForComponentEditPart.VISUAL_ID:
				return createProperty_ComponentAttributeLabel(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyForSignalEditPart.VISUAL_ID:
				return createProperty_SignalAttributeLabel(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return createProperty_InterfaceAttributeLabel(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyforPrimitiveTypeEditPart.VISUAL_ID:
				return createProperty_PrimitiveTypeAttributeLabel(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyforDataTypeEditPart.VISUAL_ID:
				return createProperty_DataTypeAttributeLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedClassForClassEditPart.VISUAL_ID:
				return createClass_ClassNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedClassForComponentEditPart.VISUAL_ID:
				return createClass_ComponentNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedClassForInterfaceEditPart.VISUAL_ID:
				return createClass_InterfaceNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case OperationForClassEditPart.VISUAL_ID:
				return createOperation_ClassOperationLabel(domainElement, containerView, index, persisted, preferencesHint);
			case OperationForComponentEditPart.VISUAL_ID:
				return createOperation_ComponentOperationLabel(domainElement, containerView, index, persisted, preferencesHint);
			case OperationForInterfaceEditpart.VISUAL_ID:
				return createOperation_InterfaceOperationLabel(domainElement, containerView, index, persisted, preferencesHint);
			case OperationForPrimitiveTypeEditPart.VISUAL_ID:
				return createOperation_PrimitiveTypeOperationLabel(domainElement, containerView, index, persisted, preferencesHint);
			case OperationForDataTypeEditPart.VISUAL_ID:
				return createOperation_DataTypeOperationLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ConnectableElementTemplateParameterEditPart.VISUAL_ID:
				return createConnectableElementTemplateParameter_TemplateParameterLabel(domainElement, containerView, index, persisted, preferencesHint);
			case OperationTemplateParameterEditPart.VISUAL_ID:
				return createOperationTemplateParameter_TemplateParameterLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ClassifierTemplateParameterEditPart.VISUAL_ID:
				return createClassifierTemplateParameter_TemplateParameterLabel(domainElement, containerView, index, persisted, preferencesHint);
			case TemplateParameterEditPart.VISUAL_ID:
				return createTemplateParameter_TemplateParameterLabel(domainElement, containerView, index, persisted, preferencesHint);
			case EnumerationLiteralEditPart.VISUAL_ID:
				return createEnumerationLiteral_LiteralLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ReceptionEditPart.VISUAL_ID:
				return createReception_ReceptionLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return createReception_InterfaceReceptionLabel(domainElement, containerView, index, persisted, preferencesHint);
			case SlotEditPart.VISUAL_ID:
				return createSlot_SlotLabel(domainElement, containerView, index, persisted, preferencesHint);
			case RedefinableTemplateSignatureEditPart.VISUAL_ID:
				return createRedefinableTemplateSignature_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TemplateSignatureEditPart.VISUAL_ID:
				return createTemplateSignature_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InstanceSpecificationEditPartCN.VISUAL_ID:
				return createInstanceSpecification_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ComponentEditPartCN.VISUAL_ID:
				return createComponent_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case SignalEditPartCN.VISUAL_ID:
				return createSignal_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case InterfaceEditPartCN.VISUAL_ID:
				return createInterface_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ModelEditPartCN.VISUAL_ID:
				return createModel_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case EnumerationEditPartCN.VISUAL_ID:
				return createEnumeration_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case PackageEditPartCN.VISUAL_ID:
				return createPackage_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case InformationItemEditPartCN.VISUAL_ID:
				return createInformationItem_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ClassEditPartCN.VISUAL_ID:
				return createClass_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return createPrimitiveType_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case DataTypeEditPartCN.VISUAL_ID:
				return createDataType_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPartCN.VISUAL_ID:
				return createComment_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintEditPartCN.VISUAL_ID:
				return createConstraint_PackagedElementShape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case NestedInterfaceForClassEditPart.VISUAL_ID:
				return createInterface_ClassNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedInterfaceForComponentEditPart.VISUAL_ID:
				return createInterface_ComponentNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedInterfaceForInterfaceEditPart.VISUAL_ID:
				return createInterface_InterfaceNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedEnumerationForClassEditPart.VISUAL_ID:
				return createEnumeration_ClassNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedEnumerationForComponentEditPart.VISUAL_ID:
				return createEnumeration_ComponentNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedEnumerationForInterfaceEditPart.VISUAL_ID:
				return createEnumeration_InterfaceNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedPrimitiveTypeForClassEditPart.VISUAL_ID:
				return createPrimitiveType_ClassNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedPrimitiveTypeForComponentEditPart.VISUAL_ID:
				return createPrimitiveType_ComponentNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID:
				return createPrimitiveType_InterfaceNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedDataTypeForClassEditPart.VISUAL_ID:
				return createDataType_ClassNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedDataTypeForComponentEditPart.VISUAL_ID:
				return createDataType_ComponentNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedDataTypeForInterfaceEditPart.VISUAL_ID:
				return createDataType_InterfaceNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedSignalForClassEditPart.VISUAL_ID:
				return createSignal_ClassNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedSignalForComponentEditPart.VISUAL_ID:
				return createSignal_ComponentNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedSignalForInterfaceEditPart.VISUAL_ID:
				return createSignal_InterfaceNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedComponentForClassEditPart.VISUAL_ID:
				return createComponent_ClassNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedComponentForInterfaceEditPart.VISUAL_ID:
				return createComponent_InterfaceNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			case NestedComponentForComponentEditPart.VISUAL_ID:
				return createComponent_ComponentNestedClassifierLabel(domainElement, containerView, index, persisted, preferencesHint);
			}
		}
		// can't happen, provided #provides(CreateNodeViewOperation) is correct
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public Edge createEdge(IAdaptable semanticAdapter, View containerView, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		String vid = UMLVisualIDRegistry.getVisualID(elementTypeHint);
		if (vid != null) {
			switch (vid) {
			case AssociationClassDashedLinkEditPart.VISUAL_ID:
				return createAssociationClass_TetherEdge(containerView, index, persisted, preferencesHint);
			case AssociationClassLinkEditPart.VISUAL_ID:
				return createAssociationClass_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case AssociationEditPart.VISUAL_ID:
				return createAssociation_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case AssociationBranchEditPart.VISUAL_ID:
				return createAssociation_BranchEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case GeneralizationEditPart.VISUAL_ID:
				return createGeneralization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return createInterfaceRealization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case SubstitutionEditPart.VISUAL_ID:
				return createSubstitution_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case RealizationEditPart.VISUAL_ID:
				return createRealization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case AbstractionEditPart.VISUAL_ID:
				return createAbstraction_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case UsageEditPart.VISUAL_ID:
				return createUsage_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case DependencyEditPart.VISUAL_ID:
				return createDependency_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case DependencyBranchEditPart.VISUAL_ID:
				return createDependency_BranchEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ElementImportEditPart.VISUAL_ID:
				return createElementImport_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case PackageImportEditPart.VISUAL_ID:
				return createPackageImport_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case PackageMergeEditPart.VISUAL_ID:
				return createPackageMerge_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ProfileApplicationEditPart.VISUAL_ID:
				return createProfileApplication_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return createComment_AnnotatedElementEdge(containerView, index, persisted, preferencesHint);
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return createConstraint_ConstrainedElementEdge(containerView, index, persisted, preferencesHint);
			case TemplateBindingEditPart.VISUAL_ID:
				return createTemplateBinding_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case GeneralizationSetEditPart.VISUAL_ID:
				return createGeneralizationSet_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case InstanceSpecificationLinkEditPart.VISUAL_ID:
				return createInstanceSpecification_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ContainmentLinkEditPart.VISUAL_ID:
				return createElement_ContainmentEdge(containerView, index, persisted, preferencesHint);
			case ConnectorTimeObservationEditPart.VISUAL_ID:
				return createTimeObservation_EventEdge(containerView, index, persisted, preferencesHint);
			case ConnectorDurationObservationEditPart.VISUAL_ID:
				return createDurationObservation_EventEdge(containerView, index, persisted, preferencesHint);
			case InformationFlowEditPart.VISUAL_ID:
				return createInformationFlow_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ContextLinkEditPart.VISUAL_ID:
				return createConstraint_ContextEdge(containerView, index, persisted, preferencesHint);
			}
		}
		// can never happen, provided #provides(CreateEdgeViewOperation) is correct
		return null;
	}

	/**
	 * @generated
	 */
	public Node createDependency_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DependencyNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DependencyNode"); //$NON-NLS-1$
		Node dependency_MultiNameLabel = createLabel(node, UMLVisualIDRegistry.getType(MultiDependencyLabelEditPart.VISUAL_ID));
		dependency_MultiNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_MultiNameLabel_Location = (Location) dependency_MultiNameLabel.getLayoutConstraint();
		dependency_MultiNameLabel_Location.setX(0);
		dependency_MultiNameLabel_Location.setY(15);
		Node dependency_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DependencyFloatingNameEditPart.VISUAL_ID));
		dependency_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_FloatingNameLabel_Location = (Location) dependency_FloatingNameLabel.getLayoutConstraint();
		dependency_FloatingNameLabel_Location.setX(0);
		dependency_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createAssociationClass_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(AssociationClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "AssociationClass"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(AssociationClassNameEditPart.VISUAL_ID));
		Node associationClass_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(AssociationClassFloatingNameEditPart.VISUAL_ID));
		associationClass_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location associationClass_FloatingNameLabel_Location = (Location) associationClass_FloatingNameLabel.getLayoutConstraint();
		associationClass_FloatingNameLabel_Location.setX(0);
		associationClass_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(AssociationClassAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(AssociationClassOperationCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(AssociationClassNestedClassifierCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "AssociationClass"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createAssociation_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(AssociationNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "AssociationNode"); //$NON-NLS-1$
		Node association_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(AssociationFloatingNameEditPart.VISUAL_ID));
		association_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_FloatingNameLabel_Location = (Location) association_FloatingNameLabel.getLayoutConstraint();
		association_FloatingNameLabel_Location.setX(0);
		association_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInstanceSpecification_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InstanceSpecificationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InstanceSpecification"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InstanceSpecificationNameEditPart.VISUAL_ID));
		Node instanceSpecification_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InstanceSpecificationFloatingNameEditPart.VISUAL_ID));
		instanceSpecification_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location instanceSpecification_FloatingNameLabel_Location = (Location) instanceSpecification_FloatingNameLabel.getLayoutConstraint();
		instanceSpecification_FloatingNameLabel_Location.setX(0);
		instanceSpecification_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(InstanceSpecificationSlotCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "InstanceSpecification"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ComponentNameEditPart.VISUAL_ID));
		Node component_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ComponentFloatingNameEditPart.VISUAL_ID));
		component_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location component_FloatingNameLabel_Location = (Location) component_FloatingNameLabel.getLayoutConstraint();
		component_FloatingNameLabel_Location.setX(0);
		component_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentOperationCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentNestedClassifierCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignal_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SignalEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SignalNameEditPart.VISUAL_ID));
		Node signal_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(SignalFloatingNameEditPart.VISUAL_ID));
		signal_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location signal_FloatingNameLabel_Location = (Location) signal_FloatingNameLabel.getLayoutConstraint();
		signal_FloatingNameLabel_Location.setX(0);
		signal_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(SignalAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterface_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InterfaceNameEditPart.VISUAL_ID));
		Node interface_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InterfaceFloatingNameEditPart.VISUAL_ID));
		interface_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interface_FloatingNameLabel_Location = (Location) interface_FloatingNameLabel.getLayoutConstraint();
		interface_FloatingNameLabel_Location.setX(0);
		interface_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(InterfaceAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(InterfaceOperationCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createModel_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ModelEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Model"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ModelNameEditPartTN.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(ModelPackageableElementCompartmentEditPartTN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Model"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumeration_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(EnumerationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(EnumerationNameEditPart.VISUAL_ID));
		Node enumeration_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(EnumerationFloatingNameEditPart.VISUAL_ID));
		enumeration_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location enumeration_FloatingNameLabel_Location = (Location) enumeration_FloatingNameLabel.getLayoutConstraint();
		enumeration_FloatingNameLabel_Location.setX(0);
		enumeration_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPackage_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PackageEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Package"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PackageNameEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(PackagePackageableElementCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Package"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInformationItem_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InformationItemEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InformationItem"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InformationItemNameEditPart.VISUAL_ID));
		Node informationItem_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InformationItemFloatingNameEditPart.VISUAL_ID));
		informationItem_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationItem_FloatingNameLabel_Location = (Location) informationItem_FloatingNameLabel.getLayoutConstraint();
		informationItem_FloatingNameLabel_Location.setX(0);
		informationItem_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClass_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ClassNameEditPart.VISUAL_ID));
		Node class_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ClassFloatingNameEditPart.VISUAL_ID));
		class_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location class_FloatingNameLabel_Location = (Location) class_FloatingNameLabel.getLayoutConstraint();
		class_FloatingNameLabel_Location.setX(0);
		class_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassOperationCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassNestedClassifierCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPrimitiveType_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PrimitiveTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeNameEditPart.VISUAL_ID));
		Node primitiveType_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeFloatingNameEditPart.VISUAL_ID));
		primitiveType_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location primitiveType_FloatingNameLabel_Location = (Location) primitiveType_FloatingNameLabel.getLayoutConstraint();
		primitiveType_FloatingNameLabel_Location.setX(0);
		primitiveType_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(PrimitiveTypeAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(PrimitiveTypeOperationCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataType_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DataTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DataTypeNameEditPart.VISUAL_ID));
		Node dataType_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DataTypeFloatingNameEditPart.VISUAL_ID));
		dataType_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dataType_FloatingNameLabel_Location = (Location) dataType_FloatingNameLabel.getLayoutConstraint();
		dataType_FloatingNameLabel_Location.setX(0);
		dataType_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeOperationCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_PackagedElementShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComment_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CommentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Comment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CommentBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDiagram_ShortcutShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ShortCutDiagramEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ShortCutDiagram"); //$NON-NLS-1$
		Node diagram_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(DiagramNameEditPart.VISUAL_ID));
		diagram_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location diagram_NameLabel_Location = (Location) diagram_NameLabel.getLayoutConstraint();
		diagram_NameLabel_Location.setX(0);
		diagram_NameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationObservation_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationObservationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationObservation"); //$NON-NLS-1$
		Node durationObservation_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationObservationFloatingNameEditPart.VISUAL_ID));
		durationObservation_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location durationObservation_FloatingNameLabel_Location = (Location) durationObservation_FloatingNameLabel.getLayoutConstraint();
		durationObservation_FloatingNameLabel_Location.setX(25);
		durationObservation_FloatingNameLabel_Location.setY(3);
		Node durationObservation_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationObservationStereotypeLabelEditPart.VISUAL_ID));
		durationObservation_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location durationObservation_StereotypeLabel_Location = (Location) durationObservation_StereotypeLabel.getLayoutConstraint();
		durationObservation_StereotypeLabel_Location.setX(25);
		durationObservation_StereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "DurationObservation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeObservation_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeObservationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeObservation"); //$NON-NLS-1$
		Node timeObservation_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeObservationFloatingNameEditPart.VISUAL_ID));
		timeObservation_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeObservation_FloatingNameLabel_Location = (Location) timeObservation_FloatingNameLabel.getLayoutConstraint();
		timeObservation_FloatingNameLabel_Location.setX(25);
		timeObservation_FloatingNameLabel_Location.setY(3);
		Node timeObservation_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeObservationStereotypeLabelEditPart.VISUAL_ID));
		timeObservation_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeObservation_StereotypeLabel_Location = (Location) timeObservation_StereotypeLabel.getLayoutConstraint();
		timeObservation_StereotypeLabel_Location.setX(25);
		timeObservation_StereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "TimeObservation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNamedElement_DefaultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DefaultNamedElementEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DefaultNamedElement"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DefaultNamedElementNameEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_ClassAttributeLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(PropertyForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_ComponentAttributeLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(PropertyForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_SignalAttributeLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(PropertyForSignalEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_InterfaceAttributeLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(PropertyForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_PrimitiveTypeAttributeLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(PropertyforPrimitiveTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_DataTypeAttributeLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(PropertyforDataTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClass_ClassNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedClassForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClass_ComponentNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedClassForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClass_InterfaceNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedClassForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOperation_ClassOperationLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(OperationForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Operation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOperation_ComponentOperationLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(OperationForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Operation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOperation_InterfaceOperationLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(OperationForInterfaceEditpart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Operation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOperation_PrimitiveTypeOperationLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(OperationForPrimitiveTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Operation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOperation_DataTypeOperationLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(OperationForDataTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Operation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConnectableElementTemplateParameter_TemplateParameterLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ConnectableElementTemplateParameterEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ConnectableElementTemplateParameter"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOperationTemplateParameter_TemplateParameterLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(OperationTemplateParameterEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OperationTemplateParameter"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClassifierTemplateParameter_TemplateParameterLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ClassifierTemplateParameterEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ClassifierTemplateParameter"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTemplateParameter_TemplateParameterLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(TemplateParameterEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TemplateParameter"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumerationLiteral_LiteralLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(EnumerationLiteralEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "EnumerationLiteral"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReception_ReceptionLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ReceptionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Reception"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReception_InterfaceReceptionLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ReceptionInInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Reception"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSlot_SlotLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(SlotEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Slot"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createRedefinableTemplateSignature_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(RedefinableTemplateSignatureEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "RedefinableTemplateSignature"); //$NON-NLS-1$
		createCompartment(node, UMLVisualIDRegistry.getType(RedefinableTemplateSignatureTemplateParameterCompartmentEditPart.VISUAL_ID), false, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "RedefinableTemplateSignature"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTemplateSignature_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TemplateSignatureEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TemplateSignature"); //$NON-NLS-1$
		createCompartment(node, UMLVisualIDRegistry.getType(TemplateSignatureTemplateParameterCompartmentEditPart.VISUAL_ID), false, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "TemplateSignature"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInstanceSpecification_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InstanceSpecificationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InstanceSpecification"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InstanceSpecificationNameEditPartCN.VISUAL_ID));
		Node instanceSpecification_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(InstanceSpecificationFloatingNameEditPartCN.VISUAL_ID));
		instanceSpecification_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location instanceSpecification_FloatingNameLabel_CN_Location = (Location) instanceSpecification_FloatingNameLabel_CN.getLayoutConstraint();
		instanceSpecification_FloatingNameLabel_CN_Location.setX(0);
		instanceSpecification_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(InstanceSpecificationSlotCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "InstanceSpecification"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ComponentEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ComponentNameEditPartCN.VISUAL_ID));
		Node component_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ComponentFloatingNameEditPartCN.VISUAL_ID));
		component_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location component_FloatingNameLabel_CN_Location = (Location) component_FloatingNameLabel_CN.getLayoutConstraint();
		component_FloatingNameLabel_CN_Location.setX(0);
		component_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentAttributeCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentOperationCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignal_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SignalEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SignalNameEditPartCN.VISUAL_ID));
		Node signal_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(SignalFloatingNameEditPartCN.VISUAL_ID));
		signal_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location signal_FloatingNameLabel_CN_Location = (Location) signal_FloatingNameLabel_CN.getLayoutConstraint();
		signal_FloatingNameLabel_CN_Location.setX(0);
		signal_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(SignalAttributeCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterface_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InterfaceEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InterfaceNameEditPartCN.VISUAL_ID));
		Node interface_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(InterfaceFloatingNameEditPartCN.VISUAL_ID));
		interface_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interface_FloatingNameLabel_CN_Location = (Location) interface_FloatingNameLabel_CN.getLayoutConstraint();
		interface_FloatingNameLabel_CN_Location.setX(0);
		interface_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(InterfaceAttributeCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(InterfaceOperationCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createModel_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ModelEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Model"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ModelNameEditPartCN.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(ModelPackageableElementCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Model"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumeration_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(EnumerationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(EnumerationNameEditPartCN.VISUAL_ID));
		Node enumeration_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(EnumerationFloatingNameEditPartCN.VISUAL_ID));
		enumeration_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location enumeration_FloatingNameLabel_CN_Location = (Location) enumeration_FloatingNameLabel_CN.getLayoutConstraint();
		enumeration_FloatingNameLabel_CN_Location.setX(0);
		enumeration_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPackage_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PackageEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Package"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PackageNameEditPartCN.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(PackagePackageableElementCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Package"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInformationItem_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InformationItemEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InformationItem"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InformationItemNameEditPartCN.VISUAL_ID));
		Node informationItem_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(InformationItemFloatingNameEditPartCN.VISUAL_ID));
		informationItem_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationItem_FloatingNameLabel_CN_Location = (Location) informationItem_FloatingNameLabel_CN.getLayoutConstraint();
		informationItem_FloatingNameLabel_CN_Location.setX(0);
		informationItem_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClass_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ClassEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ClassNameEditPartCN.VISUAL_ID));
		Node class_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ClassFloatingNameEditPartCN.VISUAL_ID));
		class_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location class_FloatingNameLabel_CN_Location = (Location) class_FloatingNameLabel_CN.getLayoutConstraint();
		class_FloatingNameLabel_CN_Location.setX(0);
		class_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassAttributeCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassOperationCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPrimitiveType_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PrimitiveTypeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeNameEditPartCN.VISUAL_ID));
		Node primitiveType_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeFloatingNameEditPartCN.VISUAL_ID));
		primitiveType_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location primitiveType_FloatingNameLabel_CN_Location = (Location) primitiveType_FloatingNameLabel_CN.getLayoutConstraint();
		primitiveType_FloatingNameLabel_CN_Location.setX(0);
		primitiveType_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(PrimitiveTypeAttributeCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(PrimitiveTypeOperationCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataType_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DataTypeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DataTypeNameEditPartCN.VISUAL_ID));
		Node dataType_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(DataTypeFloatingNameEditPartCN.VISUAL_ID));
		dataType_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dataType_FloatingNameLabel_CN_Location = (Location) dataType_FloatingNameLabel_CN.getLayoutConstraint();
		dataType_FloatingNameLabel_CN_Location.setX(0);
		dataType_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeAttributeCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeOperationCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComment_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CommentEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Comment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CommentBodyEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_PackagedElementShape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintBodyEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterface_ClassNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedInterfaceForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterface_ComponentNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedInterfaceForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterface_InterfaceNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedInterfaceForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumeration_ClassNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedEnumerationForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumeration_ComponentNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedEnumerationForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumeration_InterfaceNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedEnumerationForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPrimitiveType_ClassNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedPrimitiveTypeForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPrimitiveType_ComponentNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedPrimitiveTypeForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPrimitiveType_InterfaceNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataType_ClassNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedDataTypeForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataType_ComponentNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedDataTypeForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataType_InterfaceNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedDataTypeForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignal_ClassNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedSignalForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignal_ComponentNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedSignalForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignal_InterfaceNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedSignalForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_ClassNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedComponentForClassEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_InterfaceNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedComponentForInterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_ComponentNestedClassifierLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(NestedComponentForComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAssociationClass_TetherEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(AssociationClassDashedLinkEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "AssociationClassDashedLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAssociationClass_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(AssociationClassLinkEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "AssociationClassLink"); //$NON-NLS-1$
		Node associationClass_SourceRoleLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationClassRoleSourceEditPart.VISUAL_ID));
		associationClass_SourceRoleLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location associationClass_SourceRoleLabel_Location = (Location) associationClass_SourceRoleLabel.getLayoutConstraint();
		associationClass_SourceRoleLabel_Location.setX(0);
		associationClass_SourceRoleLabel_Location.setY(-20);
		Node associationClass_TargetRoleLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationClassRoleTargetEditPart.VISUAL_ID));
		associationClass_TargetRoleLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location associationClass_TargetRoleLabel_Location = (Location) associationClass_TargetRoleLabel.getLayoutConstraint();
		associationClass_TargetRoleLabel_Location.setX(0);
		associationClass_TargetRoleLabel_Location.setY(20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "AssociationClassLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAssociation_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(AssociationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "AssociationLink"); //$NON-NLS-1$
		Node association_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeAssociationEditPart.VISUAL_ID));
		association_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_StereotypeLabel_Location = (Location) association_StereotypeLabel.getLayoutConstraint();
		association_StereotypeLabel_Location.setX(0);
		association_StereotypeLabel_Location.setY(-20);
		Node association_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationNameEditPart.VISUAL_ID));
		association_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_NameLabel_Location = (Location) association_NameLabel.getLayoutConstraint();
		association_NameLabel_Location.setX(0);
		association_NameLabel_Location.setY(20);
		Node association_TargetRoleLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationTargetNameEditPart.VISUAL_ID));
		association_TargetRoleLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_TargetRoleLabel_Location = (Location) association_TargetRoleLabel.getLayoutConstraint();
		association_TargetRoleLabel_Location.setX(0);
		association_TargetRoleLabel_Location.setY(-20);
		Node association_SourceRoleLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationSourceNameEditPart.VISUAL_ID));
		association_SourceRoleLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_SourceRoleLabel_Location = (Location) association_SourceRoleLabel.getLayoutConstraint();
		association_SourceRoleLabel_Location.setX(0);
		association_SourceRoleLabel_Location.setY(20);
		Node association_SourceMultiplicityLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationMultiplicitySourceEditPart.VISUAL_ID));
		association_SourceMultiplicityLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_SourceMultiplicityLabel_Location = (Location) association_SourceMultiplicityLabel.getLayoutConstraint();
		association_SourceMultiplicityLabel_Location.setX(0);
		association_SourceMultiplicityLabel_Location.setY(20);
		Node association_TargetMultiplicityLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationMultiplicityTargetEditPart.VISUAL_ID));
		association_TargetMultiplicityLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_TargetMultiplicityLabel_Location = (Location) association_TargetMultiplicityLabel.getLayoutConstraint();
		association_TargetMultiplicityLabel_Location.setX(0);
		association_TargetMultiplicityLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "AssociationLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAssociation_BranchEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(AssociationBranchEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "AssociationBranchLink"); //$NON-NLS-1$
		Node association_BranchRoleLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationBranchRoleEditPart.VISUAL_ID));
		association_BranchRoleLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_BranchRoleLabel_Location = (Location) association_BranchRoleLabel.getLayoutConstraint();
		association_BranchRoleLabel_Location.setX(0);
		association_BranchRoleLabel_Location.setY(-20);
		Node association_BranchMultiplicityLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationBranchMutliplicityEditPart.VISUAL_ID));
		association_BranchMultiplicityLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_BranchMultiplicityLabel_Location = (Location) association_BranchMultiplicityLabel.getLayoutConstraint();
		association_BranchMultiplicityLabel_Location.setX(0);
		association_BranchMultiplicityLabel_Location.setY(20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "AssociationBranchLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createGeneralization_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(GeneralizationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Generalization"); //$NON-NLS-1$
		Node generalization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotyperGeneralizationEditPart.VISUAL_ID));
		generalization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location generalization_StereotypeLabel_Location = (Location) generalization_StereotypeLabel.getLayoutConstraint();
		generalization_StereotypeLabel_Location.setX(0);
		generalization_StereotypeLabel_Location.setY(40);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Generalization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createInterfaceRealization_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(InterfaceRealizationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "InterfaceRealization"); //$NON-NLS-1$
		Node interfaceRealization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeInterfaceRealizationEditPart.VISUAL_ID));
		interfaceRealization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interfaceRealization_StereotypeLabel_Location = (Location) interfaceRealization_StereotypeLabel.getLayoutConstraint();
		interfaceRealization_StereotypeLabel_Location.setX(0);
		interfaceRealization_StereotypeLabel_Location.setY(40);
		Node interfaceRealization_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(InterfaceRealizationNameEditPart.VISUAL_ID));
		interfaceRealization_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interfaceRealization_NameLabel_Location = (Location) interfaceRealization_NameLabel.getLayoutConstraint();
		interfaceRealization_NameLabel_Location.setX(0);
		interfaceRealization_NameLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "InterfaceRealization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createSubstitution_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(SubstitutionEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Substitution"); //$NON-NLS-1$
		Node substitution_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeSubstitutionEditPart.VISUAL_ID));
		substitution_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location substitution_StereotypeLabel_Location = (Location) substitution_StereotypeLabel.getLayoutConstraint();
		substitution_StereotypeLabel_Location.setX(0);
		substitution_StereotypeLabel_Location.setY(40);
		Node substitution_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(SubstitutionNameEditPart.VISUAL_ID));
		substitution_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location substitution_NameLabel_Location = (Location) substitution_NameLabel.getLayoutConstraint();
		substitution_NameLabel_Location.setX(0);
		substitution_NameLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Substitution"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createRealization_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(RealizationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Realization"); //$NON-NLS-1$
		Node realization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeRealizationEditPart.VISUAL_ID));
		realization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location realization_StereotypeLabel_Location = (Location) realization_StereotypeLabel.getLayoutConstraint();
		realization_StereotypeLabel_Location.setX(0);
		realization_StereotypeLabel_Location.setY(40);
		Node realization_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(RealizationNameEditPart.VISUAL_ID));
		realization_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location realization_NameLabel_Location = (Location) realization_NameLabel.getLayoutConstraint();
		realization_NameLabel_Location.setX(0);
		realization_NameLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Realization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAbstraction_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(AbstractionEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Abstraction"); //$NON-NLS-1$
		Node abstraction_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(AbstractionNameEditPart.VISUAL_ID));
		abstraction_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location abstraction_NameLabel_Location = (Location) abstraction_NameLabel.getLayoutConstraint();
		abstraction_NameLabel_Location.setX(0);
		abstraction_NameLabel_Location.setY(40);
		Node abstraction_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeAbstractionEditPart.VISUAL_ID));
		abstraction_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location abstraction_StereotypeLabel_Location = (Location) abstraction_StereotypeLabel.getLayoutConstraint();
		abstraction_StereotypeLabel_Location.setX(0);
		abstraction_StereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Abstraction"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createUsage_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(UsageEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Usage"); //$NON-NLS-1$
		Node usage_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(UsageNameEditPart.VISUAL_ID));
		usage_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location usage_NameLabel_Location = (Location) usage_NameLabel.getLayoutConstraint();
		usage_NameLabel_Location.setX(0);
		usage_NameLabel_Location.setY(40);
		Node usage_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeUsageEditPart.VISUAL_ID));
		usage_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location usage_StereotypeLabel_Location = (Location) usage_StereotypeLabel.getLayoutConstraint();
		usage_StereotypeLabel_Location.setX(0);
		usage_StereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Usage"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createDependency_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(DependencyEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "DependencyLink"); //$NON-NLS-1$
		Node dependency_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(DependencyNameEditPart.VISUAL_ID));
		dependency_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_NameLabel_Location = (Location) dependency_NameLabel.getLayoutConstraint();
		dependency_NameLabel_Location.setX(0);
		dependency_NameLabel_Location.setY(40);
		Node dependency_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeDependencyEditPart.VISUAL_ID));
		dependency_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_StereotypeLabel_Location = (Location) dependency_StereotypeLabel.getLayoutConstraint();
		dependency_StereotypeLabel_Location.setX(0);
		dependency_StereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "DependencyLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createDependency_BranchEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(DependencyBranchEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "DependencyBranchLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createElementImport_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ElementImportEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ElementImport"); //$NON-NLS-1$
		Node elementImport_AliasLabel = createLabel(edge, UMLVisualIDRegistry.getType(ElementImportAliasEditPart.VISUAL_ID));
		elementImport_AliasLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location elementImport_AliasLabel_Location = (Location) elementImport_AliasLabel.getLayoutConstraint();
		elementImport_AliasLabel_Location.setX(0);
		elementImport_AliasLabel_Location.setY(40);
		Node elementImport_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeElementImportEditPart.VISUAL_ID));
		elementImport_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location elementImport_StereotypeLabel_Location = (Location) elementImport_StereotypeLabel.getLayoutConstraint();
		elementImport_StereotypeLabel_Location.setX(0);
		elementImport_StereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "ElementImport"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createPackageImport_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(PackageImportEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "PackageImport"); //$NON-NLS-1$
		Node packageImport_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypePackageImportEditPart.VISUAL_ID));
		packageImport_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location packageImport_StereotypeLabel_Location = (Location) packageImport_StereotypeLabel.getLayoutConstraint();
		packageImport_StereotypeLabel_Location.setX(0);
		packageImport_StereotypeLabel_Location.setY(40);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "PackageImport"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createPackageMerge_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(PackageMergeEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "PackageMerge"); //$NON-NLS-1$
		Node packageMerge_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypePackageMergeEditPart.VISUAL_ID));
		packageMerge_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location packageMerge_StereotypeLabel_Location = (Location) packageMerge_StereotypeLabel.getLayoutConstraint();
		packageMerge_StereotypeLabel_Location.setX(0);
		packageMerge_StereotypeLabel_Location.setY(40);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "PackageMerge"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createProfileApplication_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ProfileApplicationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ProfileApplication"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createComment_AnnotatedElementEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(CommentAnnotatedElementEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createConstraint_ConstrainedElementEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ConstraintConstrainedElementEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createTemplateBinding_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(TemplateBindingEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "TemplateBinding"); //$NON-NLS-1$
		Node templateBinding_SubstitutionLabel = createLabel(edge, UMLVisualIDRegistry.getType(BindingSubstitutionEditPart.VISUAL_ID));
		templateBinding_SubstitutionLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location templateBinding_SubstitutionLabel_Location = (Location) templateBinding_SubstitutionLabel.getLayoutConstraint();
		templateBinding_SubstitutionLabel_Location.setX(0);
		templateBinding_SubstitutionLabel_Location.setY(40);
		Node templateBinding_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeTemplateBindingEditPart.VISUAL_ID));
		templateBinding_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location templateBinding_StereotypeLabel_Location = (Location) templateBinding_StereotypeLabel.getLayoutConstraint();
		templateBinding_StereotypeLabel_Location.setX(0);
		templateBinding_StereotypeLabel_Location.setY(20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "TemplateBinding"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createGeneralizationSet_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(GeneralizationSetEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "GeneralizationSet"); //$NON-NLS-1$
		Node generalizationSet_ConstraintLabel = createLabel(edge, UMLVisualIDRegistry.getType(ConstraintLabelEditPart.VISUAL_ID));
		generalizationSet_ConstraintLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location generalizationSet_ConstraintLabel_Location = (Location) generalizationSet_ConstraintLabel.getLayoutConstraint();
		generalizationSet_ConstraintLabel_Location.setX(0);
		generalizationSet_ConstraintLabel_Location.setY(20);
		Node generalizationSet_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AppliedStereotypeGeneralizationSetLabelEditPart.VISUAL_ID));
		generalizationSet_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location generalizationSet_StereotypeLabel_Location = (Location) generalizationSet_StereotypeLabel.getLayoutConstraint();
		generalizationSet_StereotypeLabel_Location.setX(0);
		generalizationSet_StereotypeLabel_Location.setY(40);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "GeneralizationSet"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createInstanceSpecification_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(InstanceSpecificationLinkEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "InstanceSpecificationLink"); //$NON-NLS-1$
		Node instanceSpecification_SourceRoleLabel = createLabel(edge, UMLVisualIDRegistry.getType(SourceISLinkLabelEditPart.VISUAL_ID));
		instanceSpecification_SourceRoleLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location instanceSpecification_SourceRoleLabel_Location = (Location) instanceSpecification_SourceRoleLabel.getLayoutConstraint();
		instanceSpecification_SourceRoleLabel_Location.setX(0);
		instanceSpecification_SourceRoleLabel_Location.setY(20);
		Node instanceSpecification_TargetRoleLabel = createLabel(edge, UMLVisualIDRegistry.getType(TargetISLinkLabelEditPart.VISUAL_ID));
		instanceSpecification_TargetRoleLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location instanceSpecification_TargetRoleLabel_Location = (Location) instanceSpecification_TargetRoleLabel.getLayoutConstraint();
		instanceSpecification_TargetRoleLabel_Location.setX(0);
		instanceSpecification_TargetRoleLabel_Location.setY(20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "InstanceSpecificationLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createElement_ContainmentEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ContainmentLinkEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ContainmentLink"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createTimeObservation_EventEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ConnectorTimeObservationEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createDurationObservation_EventEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ConnectorDurationObservationEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createInformationFlow_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(InformationFlowEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "InformationFlow"); //$NON-NLS-1$
		Node informationFlow_ConveyedLabel = createLabel(edge, UMLVisualIDRegistry.getType(InformationFlowConveyedLabelEditPart.VISUAL_ID));
		informationFlow_ConveyedLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationFlow_ConveyedLabel_Location = (Location) informationFlow_ConveyedLabel.getLayoutConstraint();
		informationFlow_ConveyedLabel_Location.setX(0);
		informationFlow_ConveyedLabel_Location.setY(30);
		Node informationFlow_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(InformationFlowAppliedStereotypeEditPart.VISUAL_ID));
		informationFlow_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationFlow_StereotypeLabel_Location = (Location) informationFlow_StereotypeLabel.getLayoutConstraint();
		informationFlow_StereotypeLabel_Location.setX(0);
		informationFlow_StereotypeLabel_Location.setY(15);
		Node informationFlow_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(InformationFlowNameEditPart.VISUAL_ID));
		informationFlow_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationFlow_NameLabel_Location = (Location) informationFlow_NameLabel.getLayoutConstraint();
		informationFlow_NameLabel_Location.setX(0);
		informationFlow_NameLabel_Location.setY(40);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "InformationFlow"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createConstraint_ContextEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ContextLinkEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		Node constraint_KeywordLabel = createLabel(edge, UMLVisualIDRegistry.getType(ContextLinkAppliedStereotypeEditPart.VISUAL_ID));
		constraint_KeywordLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location constraint_KeywordLabel_Location = (Location) constraint_KeywordLabel.getLayoutConstraint();
		constraint_KeywordLabel_Location.setX(0);
		constraint_KeywordLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Undefined"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	protected void stampShortcut(View containerView, Node target) {
		if (!ModelEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put("modelID", ModelEditPart.MODEL_ID); //$NON-NLS-1$
			target.getEAnnotations().add(shortcutAnnotation);
		}
	}

	/**
	 * @generated
	 */
	protected Node createLabel(View owner, String hint) {
		DecorationNode rv = NotationFactory.eINSTANCE.createDecorationNode();
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		return rv;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	protected Node createCompartment(View owner, String hint, boolean canCollapse, boolean hasTitle, boolean canSort, boolean canFilter) {
		Node rv = NotationFactory.eINSTANCE.createBasicCompartment();
		rv.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		if (hasTitle) {
			TitleStyle ts = NotationFactory.eINSTANCE.createTitleStyle();
			rv.getStyles().add(ts);
		}
		if (canSort) {
			rv.getStyles().add(NotationFactory.eINSTANCE.createSortingStyle());
		}
		if (canFilter) {
			rv.getStyles().add(NotationFactory.eINSTANCE.createFilteringStyle());
		}
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		return rv;
	}

	/**
	 * @generated
	 */
	protected EObject getSemanticElement(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		EObject eObject = semanticAdapter.getAdapter(EObject.class);
		if (eObject != null) {
			return EMFCoreUtil.resolve(TransactionUtil.getEditingDomain(eObject), eObject);
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected IElementType getSemanticElementType(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return semanticAdapter.getAdapter(IElementType.class);
	}
}
