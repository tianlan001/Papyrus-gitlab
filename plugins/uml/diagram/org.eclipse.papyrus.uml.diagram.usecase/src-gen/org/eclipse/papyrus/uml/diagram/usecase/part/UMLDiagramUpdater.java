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
package org.eclipse.papyrus.uml.diagram.usecase.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.CommentEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentUsecases2EditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ComponentUsecases3EditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ConstraintInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DefaultNamedElementEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtendEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointInRectangleEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.IncludeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageImportEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackageMergeEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackagePackageableElementCompartment2EditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.RealizationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ShortCutDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectClassifierEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectComponentUsecasesEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsInComponentEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsInPackageEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCasePointsInRectangleEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.ExtensionPoint;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Include;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.UseCase;

/**
 * @generated
 */
public class UMLDiagramUpdater implements DiagramUpdater {

	/**
	 * @generated
	 */
	protected UMLDiagramUpdater() {
		// to prevent instantiation allowing the override
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLNodeDescriptor> getSemanticChildren(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case UseCaseDiagramEditPart.VISUAL_ID:
				return getPackage_UseCaseDiagram_SemanticChildren(view);
			case UseCasePointsEditPartTN.VISUAL_ID:
				return getUseCase_ExtensionPointCompartment_SemanticChildren(view);
			case UseCasePointsInRectangleEditPart.VISUAL_ID:
				return getUseCase_ClassifierExtensionPointCompartment_SemanticChildren(view);
			case SubjectComponentUsecasesEditPart.VISUAL_ID:
				return getClassifier_UseCaseCompartment_SemanticChildren(view);
			case UseCasePointsInComponentEditPart.VISUAL_ID:
				return getUseCase_ExtensionPointCompartment_CCN_SemanticChildren(view);
			case ComponentUsecases2EditPart.VISUAL_ID:
				return getComponent_PackagedElementCompartment_CCN_SemanticChildren(view);
			case UseCasePointsInPackageEditPart.VISUAL_ID:
				return getUseCase_ExtensionPointCompartment_CN_SemanticChildren(view);
			case ComponentUsecases3EditPart.VISUAL_ID:
				return getComponent_PackagedElementCompartment_CN_SemanticChildren(view);
			case PackagePackageableElementCompartment2EditPart.VISUAL_ID:
				return getPackage_PackagedElementCompartment_CN_SemanticChildren(view);
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				return getPackage_PackagedElementCompartment_SemanticChildren(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_UseCaseDiagram_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedTypes()
				.iterator(); it.hasNext();) {
			Type childElement = (Type) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActorEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorAsRectangleEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseAsRectangleEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SubjectClassifierEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PackageEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedMembers()
				.iterator(); it.hasNext();) {
			NamedElement childElement = (NamedElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DefaultNamedElementEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		Resource resource = modelElement.eResource();
		for (Iterator<EObject> it = getPhantomNodesIterator(resource); it.hasNext();) {
			EObject childElement = it.next();
			if (childElement == modelElement) {
				continue;
			}
			if (UMLVisualIDRegistry.getNodeVisualID(view, childElement) == ShortCutDiagramEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, ShortCutDiagramEditPart.VISUAL_ID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getUseCase_ExtensionPointCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		UseCase modelElement = (UseCase) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getExtensionPoints()
				.iterator(); it.hasNext();) {
			ExtensionPoint childElement = (ExtensionPoint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExtensionPointEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getUseCase_ClassifierExtensionPointCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		UseCase modelElement = (UseCase) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getExtensionPoints()
				.iterator(); it.hasNext();) {
			ExtensionPoint childElement = (ExtensionPoint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExtensionPointInRectangleEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClassifier_UseCaseCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Classifier modelElement = (Classifier) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getUseCases()
				.iterator(); it.hasNext();) {
			UseCase childElement = (UseCase) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (UseCaseInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getUseCase_ExtensionPointCompartment_CCN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		UseCase modelElement = (UseCase) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getExtensionPoints()
				.iterator(); it.hasNext();) {
			ExtensionPoint childElement = (ExtensionPoint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExtensionPointEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_PackagedElementCompartment_CCN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getUseCases()
				.iterator(); it.hasNext();) {
			UseCase childElement = (UseCase) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (UseCaseInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ComponentInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActorInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getUseCase_ExtensionPointCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		UseCase modelElement = (UseCase) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getExtensionPoints()
				.iterator(); it.hasNext();) {
			ExtensionPoint childElement = (ExtensionPoint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExtensionPointEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_PackagedElementCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getUseCases()
				.iterator(); it.hasNext();) {
			UseCase childElement = (UseCase) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (UseCaseInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ComponentInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActorInComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_PackagedElementCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedTypes()
				.iterator(); it.hasNext();) {
			Type childElement = (Type) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActorInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedPackages()
				.iterator(); it.hasNext();) {
			Package childElement = (Package) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_PackagedElementCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedTypes()
				.iterator(); it.hasNext();) {
			Type childElement = (Type) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActorInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentInPackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedPackages()
				.iterator(); it.hasNext();) {
			Package childElement = (Package) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Iterator<EObject> getPhantomNodesIterator(Resource resource) {
		return resource.getAllContents();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getContainedLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case UseCaseDiagramEditPart.VISUAL_ID:
				return getPackage_UseCaseDiagram_ContainedLinks(view);
			case ActorEditPartTN.VISUAL_ID:
				return getActor_Shape_ContainedLinks(view);
			case ActorAsRectangleEditPartTN.VISUAL_ID:
				return getActor_ClassifierShape_ContainedLinks(view);
			case UseCaseEditPartTN.VISUAL_ID:
				return getUseCase_Shape_ContainedLinks(view);
			case UseCaseAsRectangleEditPartTN.VISUAL_ID:
				return getUseCase_ClassifierShape_ContainedLinks(view);
			case SubjectClassifierEditPartTN.VISUAL_ID:
				return getClassifier_SubjectShape_ContainedLinks(view);
			case PackageEditPartTN.VISUAL_ID:
				return getPackage_Shape_ContainedLinks(view);
			case ConstraintEditPartTN.VISUAL_ID:
				return getConstraint_Shape_ContainedLinks(view);
			case CommentEditPartTN.VISUAL_ID:
				return getComment_Shape_ContainedLinks(view);
			case DefaultNamedElementEditPartTN.VISUAL_ID:
				return getNamedElement_DefaultShape_ContainedLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_ContainedLinks(view);
			case ExtensionPointEditPart.VISUAL_ID:
				return getExtensionPoint_ExtensionPointLabel_ContainedLinks(view);
			case ExtensionPointInRectangleEditPart.VISUAL_ID:
				return getExtensionPoint_ClassifierExtensionPointLabel_ContainedLinks(view);
			case UseCaseInComponentEditPart.VISUAL_ID:
				return getUseCase_Shape_CCN_ContainedLinks(view);
			case ComponentInComponentEditPart.VISUAL_ID:
				return getComponent_Shape_CCN_ContainedLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_ContainedLinks(view);
			case ConstraintInComponentEditPart.VISUAL_ID:
				return getConstraint_Shape_CCN_ContainedLinks(view);
			case ActorInComponentEditPart.VISUAL_ID:
				return getActor_Shape_CCN_ContainedLinks(view);
			case ConstraintInPackageEditPart.VISUAL_ID:
				return getConstraint_Shape_CN_ContainedLinks(view);
			case ActorInPackageEditPart.VISUAL_ID:
				return getActor_Shape_CN_ContainedLinks(view);
			case UseCaseInPackageEditPart.VISUAL_ID:
				return getUseCase_Shape_CN_ContainedLinks(view);
			case ComponentInPackageEditPart.VISUAL_ID:
				return getComponent_Shape_CN_ContainedLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_ContainedLinks(view);
			case IncludeEditPart.VISUAL_ID:
				return getInclude_Edge_ContainedLinks(view);
			case ExtendEditPart.VISUAL_ID:
				return getExtend_Edge_ContainedLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_ContainedLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_ContainedLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_ContainedLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_ContainedLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_ContainedLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_ContainedLinks(view);
			case PackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_Edge_ContainedLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_ContainedLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getIncomingLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case ActorEditPartTN.VISUAL_ID:
				return getActor_Shape_IncomingLinks(view);
			case ActorAsRectangleEditPartTN.VISUAL_ID:
				return getActor_ClassifierShape_IncomingLinks(view);
			case UseCaseEditPartTN.VISUAL_ID:
				return getUseCase_Shape_IncomingLinks(view);
			case UseCaseAsRectangleEditPartTN.VISUAL_ID:
				return getUseCase_ClassifierShape_IncomingLinks(view);
			case SubjectClassifierEditPartTN.VISUAL_ID:
				return getClassifier_SubjectShape_IncomingLinks(view);
			case PackageEditPartTN.VISUAL_ID:
				return getPackage_Shape_IncomingLinks(view);
			case ConstraintEditPartTN.VISUAL_ID:
				return getConstraint_Shape_IncomingLinks(view);
			case CommentEditPartTN.VISUAL_ID:
				return getComment_Shape_IncomingLinks(view);
			case DefaultNamedElementEditPartTN.VISUAL_ID:
				return getNamedElement_DefaultShape_IncomingLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_IncomingLinks(view);
			case ExtensionPointEditPart.VISUAL_ID:
				return getExtensionPoint_ExtensionPointLabel_IncomingLinks(view);
			case ExtensionPointInRectangleEditPart.VISUAL_ID:
				return getExtensionPoint_ClassifierExtensionPointLabel_IncomingLinks(view);
			case UseCaseInComponentEditPart.VISUAL_ID:
				return getUseCase_Shape_CCN_IncomingLinks(view);
			case ComponentInComponentEditPart.VISUAL_ID:
				return getComponent_Shape_CCN_IncomingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_IncomingLinks(view);
			case ConstraintInComponentEditPart.VISUAL_ID:
				return getConstraint_Shape_CCN_IncomingLinks(view);
			case ActorInComponentEditPart.VISUAL_ID:
				return getActor_Shape_CCN_IncomingLinks(view);
			case ConstraintInPackageEditPart.VISUAL_ID:
				return getConstraint_Shape_CN_IncomingLinks(view);
			case ActorInPackageEditPart.VISUAL_ID:
				return getActor_Shape_CN_IncomingLinks(view);
			case UseCaseInPackageEditPart.VISUAL_ID:
				return getUseCase_Shape_CN_IncomingLinks(view);
			case ComponentInPackageEditPart.VISUAL_ID:
				return getComponent_Shape_CN_IncomingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_IncomingLinks(view);
			case IncludeEditPart.VISUAL_ID:
				return getInclude_Edge_IncomingLinks(view);
			case ExtendEditPart.VISUAL_ID:
				return getExtend_Edge_IncomingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_IncomingLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_IncomingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_IncomingLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_IncomingLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_IncomingLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_IncomingLinks(view);
			case PackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_Edge_IncomingLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_IncomingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getOutgoingLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case ActorEditPartTN.VISUAL_ID:
				return getActor_Shape_OutgoingLinks(view);
			case ActorAsRectangleEditPartTN.VISUAL_ID:
				return getActor_ClassifierShape_OutgoingLinks(view);
			case UseCaseEditPartTN.VISUAL_ID:
				return getUseCase_Shape_OutgoingLinks(view);
			case UseCaseAsRectangleEditPartTN.VISUAL_ID:
				return getUseCase_ClassifierShape_OutgoingLinks(view);
			case SubjectClassifierEditPartTN.VISUAL_ID:
				return getClassifier_SubjectShape_OutgoingLinks(view);
			case PackageEditPartTN.VISUAL_ID:
				return getPackage_Shape_OutgoingLinks(view);
			case ConstraintEditPartTN.VISUAL_ID:
				return getConstraint_Shape_OutgoingLinks(view);
			case CommentEditPartTN.VISUAL_ID:
				return getComment_Shape_OutgoingLinks(view);
			case DefaultNamedElementEditPartTN.VISUAL_ID:
				return getNamedElement_DefaultShape_OutgoingLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_OutgoingLinks(view);
			case ExtensionPointEditPart.VISUAL_ID:
				return getExtensionPoint_ExtensionPointLabel_OutgoingLinks(view);
			case ExtensionPointInRectangleEditPart.VISUAL_ID:
				return getExtensionPoint_ClassifierExtensionPointLabel_OutgoingLinks(view);
			case UseCaseInComponentEditPart.VISUAL_ID:
				return getUseCase_Shape_CCN_OutgoingLinks(view);
			case ComponentInComponentEditPart.VISUAL_ID:
				return getComponent_Shape_CCN_OutgoingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_OutgoingLinks(view);
			case ConstraintInComponentEditPart.VISUAL_ID:
				return getConstraint_Shape_CCN_OutgoingLinks(view);
			case ActorInComponentEditPart.VISUAL_ID:
				return getActor_Shape_CCN_OutgoingLinks(view);
			case ConstraintInPackageEditPart.VISUAL_ID:
				return getConstraint_Shape_CN_OutgoingLinks(view);
			case ActorInPackageEditPart.VISUAL_ID:
				return getActor_Shape_CN_OutgoingLinks(view);
			case UseCaseInPackageEditPart.VISUAL_ID:
				return getUseCase_Shape_CN_OutgoingLinks(view);
			case ComponentInPackageEditPart.VISUAL_ID:
				return getComponent_Shape_CN_OutgoingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_OutgoingLinks(view);
			case IncludeEditPart.VISUAL_ID:
				return getInclude_Edge_OutgoingLinks(view);
			case ExtendEditPart.VISUAL_ID:
				return getExtend_Edge_OutgoingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_OutgoingLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_OutgoingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_OutgoingLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_OutgoingLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_OutgoingLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_OutgoingLinks(view);
			case PackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_Edge_OutgoingLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_OutgoingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_UseCaseDiagram_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_ContainedLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_ClassifierShape_ContainedLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_ContainedLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_ClassifierShape_ContainedLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClassifier_SubjectShape_ContainedLinks(View view) {
		Classifier modelElement = (Classifier) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDiagram_ShortcutShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtensionPoint_ExtensionPointLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtensionPoint_ClassifierExtensionPointLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CCN_ContainedLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CCN_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CCN_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CCN_ContainedLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CN_ContainedLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CN_ContainedLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInclude_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtend_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_ContainedLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageMerge_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_IncomingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_ClassifierShape_IncomingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_IncomingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Include_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Extend_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_ClassifierShape_IncomingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Include_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Extend_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClassifier_SubjectShape_IncomingLinks(View view) {
		Classifier modelElement = (Classifier) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageMerge_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_IncomingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_IncomingLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDiagram_ShortcutShape_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtensionPoint_ExtensionPointLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtensionPoint_ClassifierExtensionPointLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CCN_IncomingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Include_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Extend_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CCN_IncomingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_IncomingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CCN_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CCN_IncomingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CN_IncomingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CN_IncomingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Include_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Extend_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_IncomingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageMerge_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInclude_Edge_IncomingLinks(View view) {
		Include modelElement = (Include) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtend_Edge_IncomingLinks(View view) {
		Extend modelElement = (Extend) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_IncomingLinks(View view) {
		Generalization modelElement = (Generalization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_IncomingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_IncomingLinks(View view) {
		Abstraction modelElement = (Abstraction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_IncomingLinks(View view) {
		Usage modelElement = (Usage) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_IncomingLinks(View view) {
		Realization modelElement = (Realization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageMerge_Edge_IncomingLinks(View view) {
		PackageMerge modelElement = (PackageMerge) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_IncomingLinks(View view) {
		PackageImport modelElement = (PackageImport) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_OutgoingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_ClassifierShape_OutgoingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_OutgoingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_ClassifierShape_OutgoingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClassifier_SubjectShape_OutgoingLinks(View view) {
		Classifier modelElement = (Classifier) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_OutgoingLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDiagram_ShortcutShape_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtensionPoint_ExtensionPointLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtensionPoint_ClassifierExtensionPointLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CCN_OutgoingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CCN_OutgoingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CCN_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CCN_OutgoingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CN_OutgoingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CN_OutgoingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Include_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Extend_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_OutgoingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInclude_Edge_OutgoingLinks(View view) {
		Include modelElement = (Include) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExtend_Edge_OutgoingLinks(View view) {
		Extend modelElement = (Extend) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_OutgoingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_OutgoingLinks(View view) {
		Abstraction modelElement = (Abstraction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_OutgoingLinks(View view) {
		Usage modelElement = (Usage) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_OutgoingLinks(View view) {
		Realization modelElement = (Realization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageMerge_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Include_Edge(UseCase container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getIncludes()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Include) {
				continue;
			}
			Include link = (Include) linkObject;
			if (!IncludeEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			UseCase dst = link.getAddition();
			UseCase src = link.getIncludingCase();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Include_Edge, IncludeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Extend_Edge(UseCase container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getExtends()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Extend) {
				continue;
			}
			Extend link = (Extend) linkObject;
			if (!ExtendEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			UseCase dst = link.getExtendedCase();
			UseCase src = link.getExtension();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Extend_Edge, ExtendEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Generalization_Edge(Classifier container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getGeneralizations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) linkObject;
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier dst = link.getGeneral();
			Classifier src = link.getSpecific();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Association_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEndTypes();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Type) {
				continue;
			}
			Type dst = (Type) theTarget;
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Dependency_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Abstraction_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Abstraction) {
				continue;
			}
			Abstraction link = (Abstraction) linkObject;
			if (!AbstractionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Abstraction_Edge, AbstractionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Usage_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Usage) {
				continue;
			}
			Usage link = (Usage) linkObject;
			if (!UsageEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Usage_Edge, UsageEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Realization_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Realization) {
				continue;
			}
			Realization link = (Realization) linkObject;
			if (!RealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Realization_Edge, RealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_PackageMerge_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackageMerges()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof PackageMerge) {
				continue;
			}
			PackageMerge link = (PackageMerge) linkObject;
			if (!PackageMergeEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Package dst = link.getMergedPackage();
			Package src = link.getReceivingPackage();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.PackageMerge_Edge, PackageMergeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_PackageImport_Edge(Namespace container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackageImports()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof PackageImport) {
				continue;
			}
			PackageImport link = (PackageImport) linkObject;
			if (!PackageImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Package dst = link.getImportedPackage();
			result.add(new UMLLinkDescriptor(container, dst, link, UMLElementTypes.PackageImport_Edge, PackageImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Include_Edge(UseCase target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getInclude_Addition() || false == setting.getEObject() instanceof Include) {
				continue;
			}
			Include link = (Include) setting.getEObject();
			if (!IncludeEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			UseCase src = link.getIncludingCase();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Include_Edge, IncludeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Extend_Edge(UseCase target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getExtend_ExtendedCase() || false == setting.getEObject() instanceof Extend) {
				continue;
			}
			Extend link = (Extend) setting.getEObject();
			if (!ExtendEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			UseCase src = link.getExtension();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Extend_Edge, ExtendEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Generalization_Edge(Classifier target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getGeneralization_General() || false == setting.getEObject() instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) setting.getEObject();
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier src = link.getSpecific();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Association_Edge(Type target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getAssociation_EndType() || false == setting.getEObject() instanceof Association) {
				continue;
			}
			Association link = (Association) setting.getEObject();
			if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getConstraint_ConstrainedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Constraint_ConstrainedElementEdge, ConstraintConstrainedElementEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Dependency_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) setting.getEObject();
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getComment_AnnotatedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Comment_AnnotatedElementEdge, CommentAnnotatedElementEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Abstraction_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Abstraction) {
				continue;
			}
			Abstraction link = (Abstraction) setting.getEObject();
			if (!AbstractionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Abstraction_Edge, AbstractionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Usage_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Usage) {
				continue;
			}
			Usage link = (Usage) setting.getEObject();
			if (!UsageEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Usage_Edge, UsageEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Realization_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Realization) {
				continue;
			}
			Realization link = (Realization) setting.getEObject();
			if (!RealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Realization_Edge, RealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_PackageMerge_Edge(Package target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getPackageMerge_MergedPackage() || false == setting.getEObject() instanceof PackageMerge) {
				continue;
			}
			PackageMerge link = (PackageMerge) setting.getEObject();
			if (!PackageMergeEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Package src = link.getReceivingPackage();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.PackageMerge_Edge, PackageMergeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_PackageImport_Edge(Package target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getPackageImport_ImportedPackage() || false == setting.getEObject() instanceof PackageImport) {
				continue;
			}
			PackageImport link = (PackageImport) setting.getEObject();
			if (!PackageImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			if (false == link.eContainer() instanceof Namespace) {
				continue;
			}
			Namespace container = (Namespace) link.eContainer();
			result.add(new UMLLinkDescriptor(container, target, link, UMLElementTypes.PackageImport_Edge, PackageImportEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Include_Edge(UseCase source) {
		UseCase container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof UseCase) {
				container = (UseCase) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getIncludes()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Include) {
				continue;
			}
			Include link = (Include) linkObject;
			if (!IncludeEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			UseCase dst = link.getAddition();
			UseCase src = link.getIncludingCase();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Include_Edge, IncludeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Extend_Edge(UseCase source) {
		UseCase container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof UseCase) {
				container = (UseCase) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getExtends()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Extend) {
				continue;
			}
			Extend link = (Extend) linkObject;
			if (!ExtendEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			UseCase dst = link.getExtendedCase();
			UseCase src = link.getExtension();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Extend_Edge, ExtendEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Generalization_Edge(Classifier source) {
		Classifier container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Classifier) {
				container = (Classifier) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getGeneralizations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) linkObject;
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier dst = link.getGeneral();
			Classifier src = link.getSpecific();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Association_Edge(Type source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEndTypes();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Type) {
				continue;
			}
			Type dst = (Type) theTarget;
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(Constraint source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getConstrainedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Constraint_ConstrainedElementEdge, ConstraintConstrainedElementEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Dependency_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(Comment source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getAnnotatedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Comment_AnnotatedElementEdge, CommentAnnotatedElementEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Abstraction_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Abstraction) {
				continue;
			}
			Abstraction link = (Abstraction) linkObject;
			if (!AbstractionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Abstraction_Edge, AbstractionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Usage_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Usage) {
				continue;
			}
			Usage link = (Usage) linkObject;
			if (!UsageEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Usage_Edge, UsageEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Realization_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Realization) {
				continue;
			}
			Realization link = (Realization) linkObject;
			if (!RealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Realization_Edge, RealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_PackageMerge_Edge(Package source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackageMerges()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof PackageMerge) {
				continue;
			}
			PackageMerge link = (PackageMerge) linkObject;
			if (!PackageMergeEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Package dst = link.getMergedPackage();
			Package src = link.getReceivingPackage();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.PackageMerge_Edge, PackageMergeEditPart.VISUAL_ID));
		}
		return result;
	}
}
