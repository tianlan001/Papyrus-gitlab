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
import org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AbstractionNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAppliedStereotypeEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAsRectangleNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorFloatingLabelEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorQualifiedNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorQualifiedNameInCEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorQualifiedNameInPEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AppliedStereotypeAbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AppliedStereotypePackageMergeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AppliedStereotypeUsageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentBodyEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentBodyEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInPackageFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentUsecases2EditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentUsecases3EditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintBodyEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintBodyInCEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintBodyInPEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DefaultNamedElementEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DefaultNamedElementNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DependencyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DependencyNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DiagramNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtendAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtendEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtendsLink_fixedEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointInRectangleEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.GeneralizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.IncludeAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.IncludeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.IncludeLink_fixedEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageImportAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageImportEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageMergeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageNameEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackagePackageableElementCompartment2EditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.RealizationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.RealizationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.RealizationNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ShortCutDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectClassifierEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectClassifierFloatingLabelEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectClassifierNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectComponentUsecasesEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UsageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseAsRectangleNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseFloatingLabelEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageNameEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsInRectangleEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.part.UMLVisualIDRegistry;

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
		return UseCaseDiagramEditPart.MODEL_ID;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateDiagramViewOperation op) {
		return UseCaseDiagramEditPart.MODEL_ID.equals(op.getSemanticHint()) && UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())) != null
				&& !UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())).isEmpty();
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
				if (!UseCaseDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(op.getContainerView()))) {
					return false; // foreign diagram
				}
				if (visualID != null) {
					switch (visualID) {
					case ActorEditPartTN.VISUAL_ID:
					case UseCaseEditPartTN.VISUAL_ID:
					case SubjectClassifierEditPartTN.VISUAL_ID:
					case DefaultNamedElementEditPartTN.VISUAL_ID:
					case ShortCutDiagramEditPart.VISUAL_ID:
					case ExtensionPointEditPart.VISUAL_ID:
					case CommentEditPartCN.VISUAL_ID:
					case ConstraintInComponentEditPart.VISUAL_ID:
					case ActorAsRectangleEditPartTN.VISUAL_ID:
					case UseCaseAsRectangleEditPartTN.VISUAL_ID:
					case PackageEditPartTN.VISUAL_ID:
					case ConstraintEditPartTN.VISUAL_ID:
					case CommentEditPartTN.VISUAL_ID:
					case ExtensionPointInRectangleEditPart.VISUAL_ID:
					case UseCaseInComponentEditPart.VISUAL_ID:
					case ComponentInComponentEditPart.VISUAL_ID:
					case ActorInComponentEditPart.VISUAL_ID:
					case ConstraintInPackageEditPart.VISUAL_ID:
					case ActorInPackageEditPart.VISUAL_ID:
					case UseCaseInPackageEditPart.VISUAL_ID:
					case ComponentInPackageEditPart.VISUAL_ID:
					case PackageEditPartCN.VISUAL_ID:
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
		diagram.setType(UseCaseDiagramEditPart.MODEL_ID);
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
			case ActorEditPartTN.VISUAL_ID:
				return createActor_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ActorAsRectangleEditPartTN.VISUAL_ID:
				return createActor_ClassifierShape(domainElement, containerView, index, persisted, preferencesHint);
			case UseCaseEditPartTN.VISUAL_ID:
				return createUseCase_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case UseCaseAsRectangleEditPartTN.VISUAL_ID:
				return createUseCase_ClassifierShape(domainElement, containerView, index, persisted, preferencesHint);
			case SubjectClassifierEditPartTN.VISUAL_ID:
				return createClassifier_SubjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case PackageEditPartTN.VISUAL_ID:
				return createPackage_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintEditPartTN.VISUAL_ID:
				return createConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPartTN.VISUAL_ID:
				return createComment_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DefaultNamedElementEditPartTN.VISUAL_ID:
				return createNamedElement_DefaultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return createDiagram_ShortcutShape(domainElement, containerView, index, persisted, preferencesHint);
			case ExtensionPointEditPart.VISUAL_ID:
				return createExtensionPoint_ExtensionPointLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ExtensionPointInRectangleEditPart.VISUAL_ID:
				return createExtensionPoint_ClassifierExtensionPointLabel(domainElement, containerView, index, persisted, preferencesHint);
			case UseCaseInComponentEditPart.VISUAL_ID:
				return createUseCase_Shape_CCN(domainElement, containerView, index, persisted, preferencesHint);
			case ComponentInComponentEditPart.VISUAL_ID:
				return createComponent_Shape_CCN(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPartCN.VISUAL_ID:
				return createComment_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintInComponentEditPart.VISUAL_ID:
				return createConstraint_Shape_CCN(domainElement, containerView, index, persisted, preferencesHint);
			case ActorInComponentEditPart.VISUAL_ID:
				return createActor_Shape_CCN(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintInPackageEditPart.VISUAL_ID:
				return createConstraint_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ActorInPackageEditPart.VISUAL_ID:
				return createActor_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case UseCaseInPackageEditPart.VISUAL_ID:
				return createUseCase_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ComponentInPackageEditPart.VISUAL_ID:
				return createComponent_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case PackageEditPartCN.VISUAL_ID:
				return createPackage_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
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
			case IncludeEditPart.VISUAL_ID:
				return createInclude_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ExtendEditPart.VISUAL_ID:
				return createExtend_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case GeneralizationEditPart.VISUAL_ID:
				return createGeneralization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case AssociationEditPart.VISUAL_ID:
				return createAssociation_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return createConstraint_ConstrainedElementEdge(containerView, index, persisted, preferencesHint);
			case DependencyEditPart.VISUAL_ID:
				return createDependency_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return createComment_AnnotatedElementEdge(containerView, index, persisted, preferencesHint);
			case AbstractionEditPart.VISUAL_ID:
				return createAbstraction_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case UsageEditPart.VISUAL_ID:
				return createUsage_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case RealizationEditPart.VISUAL_ID:
				return createRealization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case PackageMergeEditPart.VISUAL_ID:
				return createPackageMerge_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case PackageImportEditPart.VISUAL_ID:
				return createPackageImport_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			}
		}
		// can never happen, provided #provides(CreateEdgeViewOperation) is correct
		return null;
	}

	/**
	 * @generated
	 */
	public Node createActor_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActorEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		Node actor_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActorNameEditPartTN.VISUAL_ID));
		actor_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_NameLabel_Location = (Location) actor_NameLabel.getLayoutConstraint();
		actor_NameLabel_Location.setX(0);
		actor_NameLabel_Location.setY(15);
		Node actor_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActorAppliedStereotypeEditPartTN.VISUAL_ID));
		actor_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_StereotypeLabel_Location = (Location) actor_StereotypeLabel.getLayoutConstraint();
		actor_StereotypeLabel_Location.setX(20);
		actor_StereotypeLabel_Location.setY(100);
		Node actor_QualifiedNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActorQualifiedNameEditPartTN.VISUAL_ID));
		actor_QualifiedNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_QualifiedNameLabel_Location = (Location) actor_QualifiedNameLabel.getLayoutConstraint();
		actor_QualifiedNameLabel_Location.setX(20);
		actor_QualifiedNameLabel_Location.setY(80);
		Node actor_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActorFloatingLabelEditPartTN.VISUAL_ID));
		actor_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_FloatingNameLabel_Location = (Location) actor_FloatingNameLabel.getLayoutConstraint();
		actor_FloatingNameLabel_Location.setX(0);
		actor_FloatingNameLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActor_ClassifierShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActorAsRectangleEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActorAsRectangleNameEditPartTN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createUseCase_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(UseCaseEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(UseCaseNameEditPartTN.VISUAL_ID));
		Node useCase_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(UseCaseFloatingLabelEditPartTN.VISUAL_ID));
		useCase_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location useCase_FloatingNameLabel_Location = (Location) useCase_FloatingNameLabel.getLayoutConstraint();
		useCase_FloatingNameLabel_Location.setX(0);
		useCase_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(UseCasePointsEditPartTN.VISUAL_ID), false, false, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createUseCase_ClassifierShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(UseCaseAsRectangleEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(UseCaseAsRectangleNameEditPartTN.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(UseCasePointsInRectangleEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClassifier_SubjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SubjectClassifierEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Classifier"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SubjectClassifierNameEditPartTN.VISUAL_ID));
		Node classifier_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(SubjectClassifierFloatingLabelEditPartTN.VISUAL_ID));
		classifier_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location classifier_FloatingNameLabel_Location = (Location) classifier_FloatingNameLabel.getLayoutConstraint();
		classifier_FloatingNameLabel_Location.setX(0);
		classifier_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(SubjectComponentUsecasesEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Classifier"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPackage_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PackageEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Package"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PackageNameEditPartTN.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(PackagePackageableElementCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Package"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintNameEditPartTN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComment_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CommentEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Comment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CommentBodyEditPartTN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNamedElement_DefaultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DefaultNamedElementEditPartTN.VISUAL_ID));
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
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ShortCutDiagram"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExtensionPoint_ExtensionPointLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ExtensionPointEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExtensionPoint"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExtensionPoint_ClassifierExtensionPointLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ExtensionPointInRectangleEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExtensionPoint"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createUseCase_Shape_CCN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(UseCaseInComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(UseCaseInComponentNameEditPart.VISUAL_ID));
		Node useCase_FloatingNameLabel_CCN = createLabel(node, UMLVisualIDRegistry.getType(UseCaseInComponentFloatingLabelEditPart.VISUAL_ID));
		useCase_FloatingNameLabel_CCN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location useCase_FloatingNameLabel_CCN_Location = (Location) useCase_FloatingNameLabel_CCN.getLayoutConstraint();
		useCase_FloatingNameLabel_CCN_Location.setX(0);
		useCase_FloatingNameLabel_CCN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(UseCasePointsInComponentEditPart.VISUAL_ID), false, false, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_Shape_CCN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ComponentInComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ComponentInComponentNameEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentUsecases2EditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
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
	public Node createConstraint_Shape_CCN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintInComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintInComponentNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintBodyInCEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActor_Shape_CCN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActorInComponentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		Node actor_NameLabel_CCN = createLabel(node, UMLVisualIDRegistry.getType(ActorInComponentNameEditPart.VISUAL_ID));
		actor_NameLabel_CCN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_NameLabel_CCN_Location = (Location) actor_NameLabel_CCN.getLayoutConstraint();
		actor_NameLabel_CCN_Location.setX(20);
		actor_NameLabel_CCN_Location.setY(60);
		Node actor_StereotypeLabel_CCN = createLabel(node, UMLVisualIDRegistry.getType(ActorInComponentAppliedStereotypeEditPart.VISUAL_ID));
		actor_StereotypeLabel_CCN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_StereotypeLabel_CCN_Location = (Location) actor_StereotypeLabel_CCN.getLayoutConstraint();
		actor_StereotypeLabel_CCN_Location.setX(20);
		actor_StereotypeLabel_CCN_Location.setY(100);
		Node actor_QualifiedNameLabel_CCN = createLabel(node, UMLVisualIDRegistry.getType(ActorQualifiedNameInCEditPart.VISUAL_ID));
		actor_QualifiedNameLabel_CCN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_QualifiedNameLabel_CCN_Location = (Location) actor_QualifiedNameLabel_CCN.getLayoutConstraint();
		actor_QualifiedNameLabel_CCN_Location.setX(20);
		actor_QualifiedNameLabel_CCN_Location.setY(80);
		Node actor_FloatingNameLabel_CCN = createLabel(node, UMLVisualIDRegistry.getType(ActorInComponentFloatingLabelEditPart.VISUAL_ID));
		actor_FloatingNameLabel_CCN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_FloatingNameLabel_CCN_Location = (Location) actor_FloatingNameLabel_CCN.getLayoutConstraint();
		actor_FloatingNameLabel_CCN_Location.setX(0);
		actor_FloatingNameLabel_CCN_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintInPackageEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintInPackageNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintBodyInPEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActor_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActorInPackageEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		Node actor_NameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ActorInPackageNameEditPart.VISUAL_ID));
		actor_NameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_NameLabel_CN_Location = (Location) actor_NameLabel_CN.getLayoutConstraint();
		actor_NameLabel_CN_Location.setX(0);
		actor_NameLabel_CN_Location.setY(15);
		Node actor_StereotypeLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ActorInPackageAppliedStereotypeEditPart.VISUAL_ID));
		actor_StereotypeLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_StereotypeLabel_CN_Location = (Location) actor_StereotypeLabel_CN.getLayoutConstraint();
		actor_StereotypeLabel_CN_Location.setX(20);
		actor_StereotypeLabel_CN_Location.setY(100);
		Node actor_QualifiedNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ActorQualifiedNameInPEditPart.VISUAL_ID));
		actor_QualifiedNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_QualifiedNameLabel_CN_Location = (Location) actor_QualifiedNameLabel_CN.getLayoutConstraint();
		actor_QualifiedNameLabel_CN_Location.setX(20);
		actor_QualifiedNameLabel_CN_Location.setY(80);
		Node actor_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ActorInPackageFloatingLabelEditPart.VISUAL_ID));
		actor_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_FloatingNameLabel_CN_Location = (Location) actor_FloatingNameLabel_CN.getLayoutConstraint();
		actor_FloatingNameLabel_CN_Location.setX(0);
		actor_FloatingNameLabel_CN_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createUseCase_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(UseCaseInPackageEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(UseCaseInPackageNameEditPart.VISUAL_ID));
		Node useCase_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(UseCaseInPackageFloatingLabelEditPart.VISUAL_ID));
		useCase_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location useCase_FloatingNameLabel_CN_Location = (Location) useCase_FloatingNameLabel_CN.getLayoutConstraint();
		useCase_FloatingNameLabel_CN_Location.setX(0);
		useCase_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(UseCasePointsInPackageEditPart.VISUAL_ID), false, false, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ComponentInPackageEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ComponentInPackageNameEditPart.VISUAL_ID));
		Node component_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ComponentInPackageFloatingLabelEditPart.VISUAL_ID));
		component_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location component_FloatingNameLabel_Location = (Location) component_FloatingNameLabel.getLayoutConstraint();
		component_FloatingNameLabel_Location.setX(0);
		component_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentUsecases3EditPart.VISUAL_ID), true, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
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
		createCompartment(node, UMLVisualIDRegistry.getType(PackagePackageableElementCompartment2EditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Package"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createInclude_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(IncludeEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Include"); //$NON-NLS-1$
		Node include_KeywordLabel = createLabel(edge, UMLVisualIDRegistry.getType(IncludeLink_fixedEditPart.VISUAL_ID));
		include_KeywordLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location include_KeywordLabel_Location = (Location) include_KeywordLabel.getLayoutConstraint();
		include_KeywordLabel_Location.setX(0);
		include_KeywordLabel_Location.setY(20);
		Node include_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(IncludeAppliedStereotypeEditPart.VISUAL_ID));
		include_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location include_StereotypeLabel_Location = (Location) include_StereotypeLabel.getLayoutConstraint();
		include_StereotypeLabel_Location.setX(0);
		include_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Include"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createExtend_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ExtendEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Extend"); //$NON-NLS-1$
		Node extend_KeywordLabel = createLabel(edge, UMLVisualIDRegistry.getType(ExtendsLink_fixedEditPart.VISUAL_ID));
		extend_KeywordLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location extend_KeywordLabel_Location = (Location) extend_KeywordLabel.getLayoutConstraint();
		extend_KeywordLabel_Location.setX(0);
		extend_KeywordLabel_Location.setY(20);
		Node extend_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(ExtendAppliedStereotypeEditPart.VISUAL_ID));
		extend_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location extend_StereotypeLabel_Location = (Location) extend_StereotypeLabel.getLayoutConstraint();
		extend_StereotypeLabel_Location.setX(0);
		extend_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Extend"); //$NON-NLS-1$
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
		Node generalization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(GeneralizationAppliedStereotypeEditPart.VISUAL_ID));
		generalization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location generalization_StereotypeLabel_Location = (Location) generalization_StereotypeLabel.getLayoutConstraint();
		generalization_StereotypeLabel_Location.setX(0);
		generalization_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Generalization"); //$NON-NLS-1$
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
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Association"); //$NON-NLS-1$
		Node association_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationNameEditPart.VISUAL_ID));
		association_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_NameLabel_Location = (Location) association_NameLabel.getLayoutConstraint();
		association_NameLabel_Location.setX(0);
		association_NameLabel_Location.setY(40);
		Node association_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AssociationAppliedStereotypeEditPart.VISUAL_ID));
		association_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location association_StereotypeLabel_Location = (Location) association_StereotypeLabel.getLayoutConstraint();
		association_StereotypeLabel_Location.setX(0);
		association_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Association"); //$NON-NLS-1$
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
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Dependency"); //$NON-NLS-1$
		Node dependency_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(DependencyNameEditPart.VISUAL_ID));
		dependency_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_NameLabel_Location = (Location) dependency_NameLabel.getLayoutConstraint();
		dependency_NameLabel_Location.setX(0);
		dependency_NameLabel_Location.setY(40);
		Node dependency_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(DependencyAppliedStereotypeEditPart.VISUAL_ID));
		dependency_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_StereotypeLabel_Location = (Location) dependency_StereotypeLabel.getLayoutConstraint();
		dependency_StereotypeLabel_Location.setX(0);
		dependency_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Dependency"); //$NON-NLS-1$
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
		abstraction_StereotypeLabel_Location.setY(-20);
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
		usage_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Usage"); //$NON-NLS-1$
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
		Node realization_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(RealizationNameEditPart.VISUAL_ID));
		realization_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location realization_NameLabel_Location = (Location) realization_NameLabel.getLayoutConstraint();
		realization_NameLabel_Location.setX(0);
		realization_NameLabel_Location.setY(40);
		Node realization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(RealizationAppliedStereotypeEditPart.VISUAL_ID));
		realization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location realization_StereotypeLabel_Location = (Location) realization_StereotypeLabel.getLayoutConstraint();
		realization_StereotypeLabel_Location.setX(0);
		realization_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Realization"); //$NON-NLS-1$
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
		packageMerge_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "PackageMerge"); //$NON-NLS-1$
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
		Node packageImport_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(PackageImportAppliedStereotypeEditPart.VISUAL_ID));
		packageImport_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location packageImport_StereotypeLabel_Location = (Location) packageImport_StereotypeLabel.getLayoutConstraint();
		packageImport_StereotypeLabel_Location.setX(0);
		packageImport_StereotypeLabel_Location.setY(-20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "PackageImport"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	protected void stampShortcut(View containerView, Node target) {
		if (!UseCaseDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put("modelID", UseCaseDiagramEditPart.MODEL_ID); //$NON-NLS-1$
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
