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
package org.eclipse.papyrus.uml.diagram.clazz.part;

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
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ClassifierTemplateParameter;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ConnectableElementTemplateParameter;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.InformationFlow;
import org.eclipse.uml2.uml.InformationItem;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.OperationTemplateParameter;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.RedefinableTemplateSignature;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.TemplateParameter;
import org.eclipse.uml2.uml.TemplateSignature;
import org.eclipse.uml2.uml.TemplateableElement;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;

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
			case ModelEditPart.VISUAL_ID:
				return getPackage_ClassDiagram_SemanticChildren(view);
			case ComponentEditPart.VISUAL_ID:
				return getComponent_Shape_SemanticChildren(view);
			case SignalEditPart.VISUAL_ID:
				return getSignal_Shape_SemanticChildren(view);
			case InterfaceEditPart.VISUAL_ID:
				return getInterface_Shape_SemanticChildren(view);
			case ModelEditPartTN.VISUAL_ID:
				return getModel_Shape_SemanticChildren(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_SemanticChildren(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_SemanticChildren(view);
			case ClassEditPart.VISUAL_ID:
				return getClass_Shape_SemanticChildren(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_SemanticChildren(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_SemanticChildren(view);
			case ComponentEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_SemanticChildren(view);
			case SignalEditPartCN.VISUAL_ID:
				return getSignal_Shape_CN_SemanticChildren(view);
			case InterfaceEditPartCN.VISUAL_ID:
				return getInterface_Shape_CN_SemanticChildren(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_SemanticChildren(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_SemanticChildren(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_SemanticChildren(view);
			case ClassEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_SemanticChildren(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_SemanticChildren(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_SemanticChildren(view);
			case ClassAttributeCompartmentEditPartCN.VISUAL_ID:
				return getClass_AttributeCompartment_CN_SemanticChildren(view);
			case ClassOperationCompartmentEditPartCN.VISUAL_ID:
				return getClass_OperationCompartment_CN_SemanticChildren(view);
			case ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID:
				return getClass_NestedClassifierCompartment_CN_SemanticChildren(view);
			case ComponentAttributeCompartmentEditPartCN.VISUAL_ID:
				return getComponent_AttributeCompartment_CN_SemanticChildren(view);
			case ComponentOperationCompartmentEditPartCN.VISUAL_ID:
				return getComponent_OperationCompartment_CN_SemanticChildren(view);
			case ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID:
				return getComponent_NestedClassifierCompartment_CN_SemanticChildren(view);
			case SignalAttributeCompartmentEditPartCN.VISUAL_ID:
				return getSignal_AttributeCompartment_CN_SemanticChildren(view);
			case InterfaceAttributeCompartmentEditPartCN.VISUAL_ID:
				return getInterface_AttributeCompartment_CN_SemanticChildren(view);
			case InterfaceOperationCompartmentEditPartCN.VISUAL_ID:
				return getInterface_OperationCompartment_CN_SemanticChildren(view);
			case InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID:
				return getInterface_NestedClassifierCompartment_CN_SemanticChildren(view);
			case PrimitiveTypeAttributeCompartmentEditPartCN.VISUAL_ID:
				return getPrimitiveType_AttributeCompartment_CN_SemanticChildren(view);
			case PrimitiveTypeOperationCompartmentEditPartCN.VISUAL_ID:
				return getPrimitiveType_OperationCompartment_CN_SemanticChildren(view);
			case DataTypeAttributeCompartmentEditPartCN.VISUAL_ID:
				return getDataType_AttributeCompartment_CN_SemanticChildren(view);
			case DataTypeOperationCompartmentEditPartCN.VISUAL_ID:
				return getDataType_OperationCompartment_CN_SemanticChildren(view);
			case ModelPackageableElementCompartmentEditPartCN.VISUAL_ID:
				return getModel_PackagedElementCompartment_CN_SemanticChildren(view);
			case PackagePackageableElementCompartmentEditPartCN.VISUAL_ID:
				return getPackage_PackagedElementCompartment_CN_SemanticChildren(view);
			case EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID:
				return getEnumeration_LiteralCompartment_CN_SemanticChildren(view);
			case InstanceSpecificationSlotCompartmentEditPartCN.VISUAL_ID:
				return getInstanceSpecification_SlotCompartment_CN_SemanticChildren(view);
			case ClassAttributeCompartmentEditPart.VISUAL_ID:
				return getClass_AttributeCompartment_SemanticChildren(view);
			case ClassOperationCompartmentEditPart.VISUAL_ID:
				return getClass_OperationCompartment_SemanticChildren(view);
			case ClassNestedClassifierCompartmentEditPart.VISUAL_ID:
				return getClass_NestedClassifierCompartment_SemanticChildren(view);
			case ComponentAttributeCompartmentEditPart.VISUAL_ID:
				return getComponent_AttributeCompartment_SemanticChildren(view);
			case ComponentOperationCompartmentEditPart.VISUAL_ID:
				return getComponent_OperationCompartment_SemanticChildren(view);
			case ComponentNestedClassifierCompartmentEditPart.VISUAL_ID:
				return getComponent_NestedClassifierCompartment_SemanticChildren(view);
			case InterfaceAttributeCompartmentEditPart.VISUAL_ID:
				return getInterface_AttributeCompartment_SemanticChildren(view);
			case InterfaceOperationCompartmentEditPart.VISUAL_ID:
				return getInterface_OperationCompartment_SemanticChildren(view);
			case InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID:
				return getInterface_NestedClassifierCompartment_SemanticChildren(view);
			case PrimitiveTypeAttributeCompartmentEditPart.VISUAL_ID:
				return getPrimitiveType_AttributeCompartment_SemanticChildren(view);
			case PrimitiveTypeOperationCompartmentEditPart.VISUAL_ID:
				return getPrimitiveType_OperationCompartment_SemanticChildren(view);
			case DataTypeAttributeCompartmentEditPart.VISUAL_ID:
				return getDataType_AttributeCompartment_SemanticChildren(view);
			case DataTypeOperationCompartmentEditPart.VISUAL_ID:
				return getDataType_OperationCompartment_SemanticChildren(view);
			case AssociationClassAttributeCompartmentEditPart.VISUAL_ID:
				return getAssociationClass_AttributeCompartment_SemanticChildren(view);
			case InstanceSpecificationSlotCompartmentEditPart.VISUAL_ID:
				return getInstanceSpecification_SlotCompartment_SemanticChildren(view);
			case SignalAttributeCompartmentEditPart.VISUAL_ID:
				return getSignal_AttributeCompartment_SemanticChildren(view);
			case ModelPackageableElementCompartmentEditPartTN.VISUAL_ID:
				return getModel_PackagedElementCompartment_SemanticChildren(view);
			case PackagePackageableElementCompartmentEditPart.VISUAL_ID:
				return getPackage_PackagedElementCompartment_SemanticChildren(view);
			case EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID:
				return getEnumeration_LiteralCompartment_SemanticChildren(view);
			case AssociationClassOperationCompartmentEditPart.VISUAL_ID:
				return getAssociationClass_OperationCompartment_SemanticChildren(view);
			case AssociationClassNestedClassifierCompartmentEditPart.VISUAL_ID:
				return getAssociationClass_NestedClassifierCompartment_SemanticChildren(view);
			case RedefinableTemplateSignatureTemplateParameterCompartmentEditPart.VISUAL_ID:
				return getRedefinableTemplateSignature_TemplateParameterCompartment_SemanticChildren(view);
			case TemplateSignatureTemplateParameterCompartmentEditPart.VISUAL_ID:
				return getTemplateSignature_TemplateParameterCompartment_SemanticChildren(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_ClassDiagram_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DependencyNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AssociationClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AssociationNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InstanceSpecificationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ModelEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DurationObservationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeObservationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedMembers()
				.iterator(); it.hasNext();) {
			NamedElement childElement = (NamedElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DefaultNamedElementEditPart.VISUAL_ID.equals(visualID)) {
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
	public List<UMLNodeDescriptor> getComponent_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getSignal_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getModel_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (TemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (TemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPrimitiveType_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getSignal_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getModel_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (TemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (TemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPrimitiveType_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			TemplateSignature childElement = modelElement.getOwnedTemplateSignature();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (RedefinableTemplateSignatureEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedReceptions()
				.iterator(); it.hasNext();) {
			Reception childElement = (Reception) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ReceptionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_NestedClassifierCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedClassForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedInterfaceForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedEnumerationForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedPrimitiveTypeForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDataTypeForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedSignalForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedComponentForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedReceptions()
				.iterator(); it.hasNext();) {
			Reception childElement = (Reception) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ReceptionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_NestedClassifierCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedClassForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedInterfaceForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedEnumerationForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedPrimitiveTypeForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDataTypeForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedSignalForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedComponentForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getSignal_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Signal modelElement = (Signal) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForSignalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForInterfaceEditpart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedReceptions()
				.iterator(); it.hasNext();) {
			Reception childElement = (Reception) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ReceptionInInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_NestedClassifierCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedClassForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedInterfaceForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedEnumerationForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDataTypeForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedSignalForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedComponentForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPrimitiveType_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		PrimitiveType modelElement = (PrimitiveType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyforPrimitiveTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPrimitiveType_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		PrimitiveType modelElement = (PrimitiveType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForPrimitiveTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyforDataTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForDataTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getModel_PackagedElementCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Model modelElement = (Model) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InstanceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InstanceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
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
	public List<UMLNodeDescriptor> getEnumeration_LiteralCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInstanceSpecification_SlotCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		InstanceSpecification modelElement = (InstanceSpecification) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getSlots()
				.iterator(); it.hasNext();) {
			Slot childElement = (Slot) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (SlotEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedReceptions()
				.iterator(); it.hasNext();) {
			Reception childElement = (Reception) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ReceptionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_NestedClassifierCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedClassForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedInterfaceForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedEnumerationForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedPrimitiveTypeForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDataTypeForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedSignalForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedComponentForClassEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedReceptions()
				.iterator(); it.hasNext();) {
			Reception childElement = (Reception) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ReceptionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_NestedClassifierCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedClassForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedInterfaceForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedEnumerationForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedPrimitiveTypeForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDataTypeForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedSignalForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedComponentForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForInterfaceEditpart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedReceptions()
				.iterator(); it.hasNext();) {
			Reception childElement = (Reception) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ReceptionInInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInterface_NestedClassifierCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interface modelElement = (Interface) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedClassForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedInterfaceForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedEnumerationForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedDataTypeForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NestedSignalForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedComponentForInterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPrimitiveType_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		PrimitiveType modelElement = (PrimitiveType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyforPrimitiveTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPrimitiveType_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		PrimitiveType modelElement = (PrimitiveType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForPrimitiveTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyforDataTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForDataTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getAssociationClass_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		AssociationClass modelElement = (AssociationClass) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInstanceSpecification_SlotCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		InstanceSpecification modelElement = (InstanceSpecification) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getSlots()
				.iterator(); it.hasNext();) {
			Slot childElement = (Slot) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (SlotEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getSignal_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Signal modelElement = (Signal) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyForSignalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getModel_PackagedElementCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Model modelElement = (Model) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InstanceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InstanceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ModelEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PackageEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
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
	public List<UMLNodeDescriptor> getEnumeration_LiteralCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getAssociationClass_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		AssociationClass modelElement = (AssociationClass) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getAssociationClass_NestedClassifierCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		AssociationClass modelElement = (AssociationClass) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (NestedClassForComponentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getRedefinableTemplateSignature_TemplateParameterCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		RedefinableTemplateSignature modelElement = (RedefinableTemplateSignature) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getParameters()
				.iterator(); it.hasNext();) {
			TemplateParameter childElement = (TemplateParameter) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassifierTemplateParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConnectableElementTemplateParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OperationTemplateParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TemplateParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getTemplateSignature_TemplateParameterCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		TemplateSignature modelElement = (TemplateSignature) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getParameters()
				.iterator(); it.hasNext();) {
			TemplateParameter childElement = (TemplateParameter) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ClassifierTemplateParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OperationTemplateParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TemplateParameterEditPart.VISUAL_ID.equals(visualID)) {
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
			case ModelEditPart.VISUAL_ID:
				return getPackage_ClassDiagram_ContainedLinks(view);
			case DependencyNodeEditPart.VISUAL_ID:
				return getDependency_Shape_ContainedLinks(view);
			case AssociationClassEditPart.VISUAL_ID:
				return getAssociationClass_Shape_ContainedLinks(view);
			case AssociationNodeEditPart.VISUAL_ID:
				return getAssociation_Shape_ContainedLinks(view);
			case InstanceSpecificationEditPart.VISUAL_ID:
				return getInstanceSpecification_Shape_ContainedLinks(view);
			case ComponentEditPart.VISUAL_ID:
				return getComponent_Shape_ContainedLinks(view);
			case SignalEditPart.VISUAL_ID:
				return getSignal_Shape_ContainedLinks(view);
			case InterfaceEditPart.VISUAL_ID:
				return getInterface_Shape_ContainedLinks(view);
			case ModelEditPartTN.VISUAL_ID:
				return getModel_Shape_ContainedLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_ContainedLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_ContainedLinks(view);
			case InformationItemEditPart.VISUAL_ID:
				return getInformationItem_Shape_ContainedLinks(view);
			case ClassEditPart.VISUAL_ID:
				return getClass_Shape_ContainedLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_ContainedLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_ContainedLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_PackagedElementShape_ContainedLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_ContainedLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_ContainedLinks(view);
			case DurationObservationEditPart.VISUAL_ID:
				return getDurationObservation_Shape_ContainedLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_ContainedLinks(view);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_ContainedLinks(view);
			case PropertyForClassEditPart.VISUAL_ID:
				return getProperty_ClassAttributeLabel_ContainedLinks(view);
			case PropertyForComponentEditPart.VISUAL_ID:
				return getProperty_ComponentAttributeLabel_ContainedLinks(view);
			case PropertyForSignalEditPart.VISUAL_ID:
				return getProperty_SignalAttributeLabel_ContainedLinks(view);
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return getProperty_InterfaceAttributeLabel_ContainedLinks(view);
			case PropertyforPrimitiveTypeEditPart.VISUAL_ID:
				return getProperty_PrimitiveTypeAttributeLabel_ContainedLinks(view);
			case PropertyforDataTypeEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_ContainedLinks(view);
			case NestedClassForClassEditPart.VISUAL_ID:
				return getClass_ClassNestedClassifierLabel_ContainedLinks(view);
			case NestedClassForComponentEditPart.VISUAL_ID:
				return getClass_ComponentNestedClassifierLabel_ContainedLinks(view);
			case NestedClassForInterfaceEditPart.VISUAL_ID:
				return getClass_InterfaceNestedClassifierLabel_ContainedLinks(view);
			case OperationForClassEditPart.VISUAL_ID:
				return getOperation_ClassOperationLabel_ContainedLinks(view);
			case OperationForComponentEditPart.VISUAL_ID:
				return getOperation_ComponentOperationLabel_ContainedLinks(view);
			case OperationForInterfaceEditpart.VISUAL_ID:
				return getOperation_InterfaceOperationLabel_ContainedLinks(view);
			case OperationForPrimitiveTypeEditPart.VISUAL_ID:
				return getOperation_PrimitiveTypeOperationLabel_ContainedLinks(view);
			case OperationForDataTypeEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_ContainedLinks(view);
			case ConnectableElementTemplateParameterEditPart.VISUAL_ID:
				return getConnectableElementTemplateParameter_TemplateParameterLabel_ContainedLinks(view);
			case OperationTemplateParameterEditPart.VISUAL_ID:
				return getOperationTemplateParameter_TemplateParameterLabel_ContainedLinks(view);
			case ClassifierTemplateParameterEditPart.VISUAL_ID:
				return getClassifierTemplateParameter_TemplateParameterLabel_ContainedLinks(view);
			case TemplateParameterEditPart.VISUAL_ID:
				return getTemplateParameter_TemplateParameterLabel_ContainedLinks(view);
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_ContainedLinks(view);
			case ReceptionEditPart.VISUAL_ID:
				return getReception_ReceptionLabel_ContainedLinks(view);
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return getReception_InterfaceReceptionLabel_ContainedLinks(view);
			case SlotEditPart.VISUAL_ID:
				return getSlot_SlotLabel_ContainedLinks(view);
			case RedefinableTemplateSignatureEditPart.VISUAL_ID:
				return getRedefinableTemplateSignature_Shape_ContainedLinks(view);
			case TemplateSignatureEditPart.VISUAL_ID:
				return getTemplateSignature_Shape_ContainedLinks(view);
			case InstanceSpecificationEditPartCN.VISUAL_ID:
				return getInstanceSpecification_Shape_CN_ContainedLinks(view);
			case ComponentEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_ContainedLinks(view);
			case SignalEditPartCN.VISUAL_ID:
				return getSignal_Shape_CN_ContainedLinks(view);
			case InterfaceEditPartCN.VISUAL_ID:
				return getInterface_Shape_CN_ContainedLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_ContainedLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_ContainedLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_ContainedLinks(view);
			case InformationItemEditPartCN.VISUAL_ID:
				return getInformationItem_Shape_CN_ContainedLinks(view);
			case ClassEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_ContainedLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_ContainedLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_ContainedLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_ContainedLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_PackagedElementShape_CN_ContainedLinks(view);
			case NestedInterfaceForClassEditPart.VISUAL_ID:
				return getInterface_ClassNestedClassifierLabel_ContainedLinks(view);
			case NestedInterfaceForComponentEditPart.VISUAL_ID:
				return getInterface_ComponentNestedClassifierLabel_ContainedLinks(view);
			case NestedInterfaceForInterfaceEditPart.VISUAL_ID:
				return getInterface_InterfaceNestedClassifierLabel_ContainedLinks(view);
			case NestedEnumerationForClassEditPart.VISUAL_ID:
				return getEnumeration_ClassNestedClassifierLabel_ContainedLinks(view);
			case NestedEnumerationForComponentEditPart.VISUAL_ID:
				return getEnumeration_ComponentNestedClassifierLabel_ContainedLinks(view);
			case NestedEnumerationForInterfaceEditPart.VISUAL_ID:
				return getEnumeration_InterfaceNestedClassifierLabel_ContainedLinks(view);
			case NestedPrimitiveTypeForClassEditPart.VISUAL_ID:
				return getPrimitiveType_ClassNestedClassifierLabel_ContainedLinks(view);
			case NestedPrimitiveTypeForComponentEditPart.VISUAL_ID:
				return getPrimitiveType_ComponentNestedClassifierLabel_ContainedLinks(view);
			case NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID:
				return getPrimitiveType_InterfaceNestedClassifierLabel_ContainedLinks(view);
			case NestedDataTypeForClassEditPart.VISUAL_ID:
				return getDataType_ClassNestedClassifierLabel_ContainedLinks(view);
			case NestedDataTypeForComponentEditPart.VISUAL_ID:
				return getDataType_ComponentNestedClassifierLabel_ContainedLinks(view);
			case NestedDataTypeForInterfaceEditPart.VISUAL_ID:
				return getDataType_InterfaceNestedClassifierLabel_ContainedLinks(view);
			case NestedSignalForClassEditPart.VISUAL_ID:
				return getSignal_ClassNestedClassifierLabel_ContainedLinks(view);
			case NestedSignalForComponentEditPart.VISUAL_ID:
				return getSignal_ComponentNestedClassifierLabel_ContainedLinks(view);
			case NestedSignalForInterfaceEditPart.VISUAL_ID:
				return getSignal_InterfaceNestedClassifierLabel_ContainedLinks(view);
			case NestedComponentForClassEditPart.VISUAL_ID:
				return getComponent_ClassNestedClassifierLabel_ContainedLinks(view);
			case NestedComponentForInterfaceEditPart.VISUAL_ID:
				return getComponent_InterfaceNestedClassifierLabel_ContainedLinks(view);
			case NestedComponentForComponentEditPart.VISUAL_ID:
				return getComponent_ComponentNestedClassifierLabel_ContainedLinks(view);
			case AssociationClassLinkEditPart.VISUAL_ID:
				return getAssociationClass_Edge_ContainedLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_ContainedLinks(view);
			case AssociationBranchEditPart.VISUAL_ID:
				return getAssociation_BranchEdge_ContainedLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_ContainedLinks(view);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return getInterfaceRealization_Edge_ContainedLinks(view);
			case SubstitutionEditPart.VISUAL_ID:
				return getSubstitution_Edge_ContainedLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_ContainedLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_ContainedLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_ContainedLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_ContainedLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_ContainedLinks(view);
			case ElementImportEditPart.VISUAL_ID:
				return getElementImport_Edge_ContainedLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_ContainedLinks(view);
			case PackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_Edge_ContainedLinks(view);
			case ProfileApplicationEditPart.VISUAL_ID:
				return getProfileApplication_Edge_ContainedLinks(view);
			case TemplateBindingEditPart.VISUAL_ID:
				return getTemplateBinding_Edge_ContainedLinks(view);
			case GeneralizationSetEditPart.VISUAL_ID:
				return getGeneralizationSet_Edge_ContainedLinks(view);
			case InstanceSpecificationLinkEditPart.VISUAL_ID:
				return getInstanceSpecification_Edge_ContainedLinks(view);
			case InformationFlowEditPart.VISUAL_ID:
				return getInformationFlow_Edge_ContainedLinks(view);
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
			case DependencyNodeEditPart.VISUAL_ID:
				return getDependency_Shape_IncomingLinks(view);
			case AssociationClassEditPart.VISUAL_ID:
				return getAssociationClass_Shape_IncomingLinks(view);
			case AssociationNodeEditPart.VISUAL_ID:
				return getAssociation_Shape_IncomingLinks(view);
			case InstanceSpecificationEditPart.VISUAL_ID:
				return getInstanceSpecification_Shape_IncomingLinks(view);
			case ComponentEditPart.VISUAL_ID:
				return getComponent_Shape_IncomingLinks(view);
			case SignalEditPart.VISUAL_ID:
				return getSignal_Shape_IncomingLinks(view);
			case InterfaceEditPart.VISUAL_ID:
				return getInterface_Shape_IncomingLinks(view);
			case ModelEditPartTN.VISUAL_ID:
				return getModel_Shape_IncomingLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_IncomingLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_IncomingLinks(view);
			case InformationItemEditPart.VISUAL_ID:
				return getInformationItem_Shape_IncomingLinks(view);
			case ClassEditPart.VISUAL_ID:
				return getClass_Shape_IncomingLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_IncomingLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_IncomingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_PackagedElementShape_IncomingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_IncomingLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_IncomingLinks(view);
			case DurationObservationEditPart.VISUAL_ID:
				return getDurationObservation_Shape_IncomingLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_IncomingLinks(view);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_IncomingLinks(view);
			case PropertyForClassEditPart.VISUAL_ID:
				return getProperty_ClassAttributeLabel_IncomingLinks(view);
			case PropertyForComponentEditPart.VISUAL_ID:
				return getProperty_ComponentAttributeLabel_IncomingLinks(view);
			case PropertyForSignalEditPart.VISUAL_ID:
				return getProperty_SignalAttributeLabel_IncomingLinks(view);
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return getProperty_InterfaceAttributeLabel_IncomingLinks(view);
			case PropertyforPrimitiveTypeEditPart.VISUAL_ID:
				return getProperty_PrimitiveTypeAttributeLabel_IncomingLinks(view);
			case PropertyforDataTypeEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_IncomingLinks(view);
			case NestedClassForClassEditPart.VISUAL_ID:
				return getClass_ClassNestedClassifierLabel_IncomingLinks(view);
			case NestedClassForComponentEditPart.VISUAL_ID:
				return getClass_ComponentNestedClassifierLabel_IncomingLinks(view);
			case NestedClassForInterfaceEditPart.VISUAL_ID:
				return getClass_InterfaceNestedClassifierLabel_IncomingLinks(view);
			case OperationForClassEditPart.VISUAL_ID:
				return getOperation_ClassOperationLabel_IncomingLinks(view);
			case OperationForComponentEditPart.VISUAL_ID:
				return getOperation_ComponentOperationLabel_IncomingLinks(view);
			case OperationForInterfaceEditpart.VISUAL_ID:
				return getOperation_InterfaceOperationLabel_IncomingLinks(view);
			case OperationForPrimitiveTypeEditPart.VISUAL_ID:
				return getOperation_PrimitiveTypeOperationLabel_IncomingLinks(view);
			case OperationForDataTypeEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_IncomingLinks(view);
			case ConnectableElementTemplateParameterEditPart.VISUAL_ID:
				return getConnectableElementTemplateParameter_TemplateParameterLabel_IncomingLinks(view);
			case OperationTemplateParameterEditPart.VISUAL_ID:
				return getOperationTemplateParameter_TemplateParameterLabel_IncomingLinks(view);
			case ClassifierTemplateParameterEditPart.VISUAL_ID:
				return getClassifierTemplateParameter_TemplateParameterLabel_IncomingLinks(view);
			case TemplateParameterEditPart.VISUAL_ID:
				return getTemplateParameter_TemplateParameterLabel_IncomingLinks(view);
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_IncomingLinks(view);
			case ReceptionEditPart.VISUAL_ID:
				return getReception_ReceptionLabel_IncomingLinks(view);
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return getReception_InterfaceReceptionLabel_IncomingLinks(view);
			case SlotEditPart.VISUAL_ID:
				return getSlot_SlotLabel_IncomingLinks(view);
			case RedefinableTemplateSignatureEditPart.VISUAL_ID:
				return getRedefinableTemplateSignature_Shape_IncomingLinks(view);
			case TemplateSignatureEditPart.VISUAL_ID:
				return getTemplateSignature_Shape_IncomingLinks(view);
			case InstanceSpecificationEditPartCN.VISUAL_ID:
				return getInstanceSpecification_Shape_CN_IncomingLinks(view);
			case ComponentEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_IncomingLinks(view);
			case SignalEditPartCN.VISUAL_ID:
				return getSignal_Shape_CN_IncomingLinks(view);
			case InterfaceEditPartCN.VISUAL_ID:
				return getInterface_Shape_CN_IncomingLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_IncomingLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_IncomingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_IncomingLinks(view);
			case InformationItemEditPartCN.VISUAL_ID:
				return getInformationItem_Shape_CN_IncomingLinks(view);
			case ClassEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_IncomingLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_IncomingLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_IncomingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_IncomingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_PackagedElementShape_CN_IncomingLinks(view);
			case NestedInterfaceForClassEditPart.VISUAL_ID:
				return getInterface_ClassNestedClassifierLabel_IncomingLinks(view);
			case NestedInterfaceForComponentEditPart.VISUAL_ID:
				return getInterface_ComponentNestedClassifierLabel_IncomingLinks(view);
			case NestedInterfaceForInterfaceEditPart.VISUAL_ID:
				return getInterface_InterfaceNestedClassifierLabel_IncomingLinks(view);
			case NestedEnumerationForClassEditPart.VISUAL_ID:
				return getEnumeration_ClassNestedClassifierLabel_IncomingLinks(view);
			case NestedEnumerationForComponentEditPart.VISUAL_ID:
				return getEnumeration_ComponentNestedClassifierLabel_IncomingLinks(view);
			case NestedEnumerationForInterfaceEditPart.VISUAL_ID:
				return getEnumeration_InterfaceNestedClassifierLabel_IncomingLinks(view);
			case NestedPrimitiveTypeForClassEditPart.VISUAL_ID:
				return getPrimitiveType_ClassNestedClassifierLabel_IncomingLinks(view);
			case NestedPrimitiveTypeForComponentEditPart.VISUAL_ID:
				return getPrimitiveType_ComponentNestedClassifierLabel_IncomingLinks(view);
			case NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID:
				return getPrimitiveType_InterfaceNestedClassifierLabel_IncomingLinks(view);
			case NestedDataTypeForClassEditPart.VISUAL_ID:
				return getDataType_ClassNestedClassifierLabel_IncomingLinks(view);
			case NestedDataTypeForComponentEditPart.VISUAL_ID:
				return getDataType_ComponentNestedClassifierLabel_IncomingLinks(view);
			case NestedDataTypeForInterfaceEditPart.VISUAL_ID:
				return getDataType_InterfaceNestedClassifierLabel_IncomingLinks(view);
			case NestedSignalForClassEditPart.VISUAL_ID:
				return getSignal_ClassNestedClassifierLabel_IncomingLinks(view);
			case NestedSignalForComponentEditPart.VISUAL_ID:
				return getSignal_ComponentNestedClassifierLabel_IncomingLinks(view);
			case NestedSignalForInterfaceEditPart.VISUAL_ID:
				return getSignal_InterfaceNestedClassifierLabel_IncomingLinks(view);
			case NestedComponentForClassEditPart.VISUAL_ID:
				return getComponent_ClassNestedClassifierLabel_IncomingLinks(view);
			case NestedComponentForInterfaceEditPart.VISUAL_ID:
				return getComponent_InterfaceNestedClassifierLabel_IncomingLinks(view);
			case NestedComponentForComponentEditPart.VISUAL_ID:
				return getComponent_ComponentNestedClassifierLabel_IncomingLinks(view);
			case AssociationClassLinkEditPart.VISUAL_ID:
				return getAssociationClass_Edge_IncomingLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_IncomingLinks(view);
			case AssociationBranchEditPart.VISUAL_ID:
				return getAssociation_BranchEdge_IncomingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_IncomingLinks(view);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return getInterfaceRealization_Edge_IncomingLinks(view);
			case SubstitutionEditPart.VISUAL_ID:
				return getSubstitution_Edge_IncomingLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_IncomingLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_IncomingLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_IncomingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_IncomingLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_IncomingLinks(view);
			case ElementImportEditPart.VISUAL_ID:
				return getElementImport_Edge_IncomingLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_IncomingLinks(view);
			case PackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_Edge_IncomingLinks(view);
			case ProfileApplicationEditPart.VISUAL_ID:
				return getProfileApplication_Edge_IncomingLinks(view);
			case TemplateBindingEditPart.VISUAL_ID:
				return getTemplateBinding_Edge_IncomingLinks(view);
			case GeneralizationSetEditPart.VISUAL_ID:
				return getGeneralizationSet_Edge_IncomingLinks(view);
			case InstanceSpecificationLinkEditPart.VISUAL_ID:
				return getInstanceSpecification_Edge_IncomingLinks(view);
			case InformationFlowEditPart.VISUAL_ID:
				return getInformationFlow_Edge_IncomingLinks(view);
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
			case DependencyNodeEditPart.VISUAL_ID:
				return getDependency_Shape_OutgoingLinks(view);
			case AssociationClassEditPart.VISUAL_ID:
				return getAssociationClass_Shape_OutgoingLinks(view);
			case AssociationNodeEditPart.VISUAL_ID:
				return getAssociation_Shape_OutgoingLinks(view);
			case InstanceSpecificationEditPart.VISUAL_ID:
				return getInstanceSpecification_Shape_OutgoingLinks(view);
			case ComponentEditPart.VISUAL_ID:
				return getComponent_Shape_OutgoingLinks(view);
			case SignalEditPart.VISUAL_ID:
				return getSignal_Shape_OutgoingLinks(view);
			case InterfaceEditPart.VISUAL_ID:
				return getInterface_Shape_OutgoingLinks(view);
			case ModelEditPartTN.VISUAL_ID:
				return getModel_Shape_OutgoingLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_OutgoingLinks(view);
			case PackageEditPart.VISUAL_ID:
				return getPackage_Shape_OutgoingLinks(view);
			case InformationItemEditPart.VISUAL_ID:
				return getInformationItem_Shape_OutgoingLinks(view);
			case ClassEditPart.VISUAL_ID:
				return getClass_Shape_OutgoingLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_OutgoingLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_OutgoingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_PackagedElementShape_OutgoingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_OutgoingLinks(view);
			case ShortCutDiagramEditPart.VISUAL_ID:
				return getDiagram_ShortcutShape_OutgoingLinks(view);
			case DurationObservationEditPart.VISUAL_ID:
				return getDurationObservation_Shape_OutgoingLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_OutgoingLinks(view);
			case DefaultNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_OutgoingLinks(view);
			case PropertyForClassEditPart.VISUAL_ID:
				return getProperty_ClassAttributeLabel_OutgoingLinks(view);
			case PropertyForComponentEditPart.VISUAL_ID:
				return getProperty_ComponentAttributeLabel_OutgoingLinks(view);
			case PropertyForSignalEditPart.VISUAL_ID:
				return getProperty_SignalAttributeLabel_OutgoingLinks(view);
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return getProperty_InterfaceAttributeLabel_OutgoingLinks(view);
			case PropertyforPrimitiveTypeEditPart.VISUAL_ID:
				return getProperty_PrimitiveTypeAttributeLabel_OutgoingLinks(view);
			case PropertyforDataTypeEditPart.VISUAL_ID:
				return getProperty_DataTypeAttributeLabel_OutgoingLinks(view);
			case NestedClassForClassEditPart.VISUAL_ID:
				return getClass_ClassNestedClassifierLabel_OutgoingLinks(view);
			case NestedClassForComponentEditPart.VISUAL_ID:
				return getClass_ComponentNestedClassifierLabel_OutgoingLinks(view);
			case NestedClassForInterfaceEditPart.VISUAL_ID:
				return getClass_InterfaceNestedClassifierLabel_OutgoingLinks(view);
			case OperationForClassEditPart.VISUAL_ID:
				return getOperation_ClassOperationLabel_OutgoingLinks(view);
			case OperationForComponentEditPart.VISUAL_ID:
				return getOperation_ComponentOperationLabel_OutgoingLinks(view);
			case OperationForInterfaceEditpart.VISUAL_ID:
				return getOperation_InterfaceOperationLabel_OutgoingLinks(view);
			case OperationForPrimitiveTypeEditPart.VISUAL_ID:
				return getOperation_PrimitiveTypeOperationLabel_OutgoingLinks(view);
			case OperationForDataTypeEditPart.VISUAL_ID:
				return getOperation_DataTypeOperationLabel_OutgoingLinks(view);
			case ConnectableElementTemplateParameterEditPart.VISUAL_ID:
				return getConnectableElementTemplateParameter_TemplateParameterLabel_OutgoingLinks(view);
			case OperationTemplateParameterEditPart.VISUAL_ID:
				return getOperationTemplateParameter_TemplateParameterLabel_OutgoingLinks(view);
			case ClassifierTemplateParameterEditPart.VISUAL_ID:
				return getClassifierTemplateParameter_TemplateParameterLabel_OutgoingLinks(view);
			case TemplateParameterEditPart.VISUAL_ID:
				return getTemplateParameter_TemplateParameterLabel_OutgoingLinks(view);
			case EnumerationLiteralEditPart.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_OutgoingLinks(view);
			case ReceptionEditPart.VISUAL_ID:
				return getReception_ReceptionLabel_OutgoingLinks(view);
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return getReception_InterfaceReceptionLabel_OutgoingLinks(view);
			case SlotEditPart.VISUAL_ID:
				return getSlot_SlotLabel_OutgoingLinks(view);
			case RedefinableTemplateSignatureEditPart.VISUAL_ID:
				return getRedefinableTemplateSignature_Shape_OutgoingLinks(view);
			case TemplateSignatureEditPart.VISUAL_ID:
				return getTemplateSignature_Shape_OutgoingLinks(view);
			case InstanceSpecificationEditPartCN.VISUAL_ID:
				return getInstanceSpecification_Shape_CN_OutgoingLinks(view);
			case ComponentEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_OutgoingLinks(view);
			case SignalEditPartCN.VISUAL_ID:
				return getSignal_Shape_CN_OutgoingLinks(view);
			case InterfaceEditPartCN.VISUAL_ID:
				return getInterface_Shape_CN_OutgoingLinks(view);
			case ModelEditPartCN.VISUAL_ID:
				return getModel_Shape_CN_OutgoingLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_OutgoingLinks(view);
			case PackageEditPartCN.VISUAL_ID:
				return getPackage_Shape_CN_OutgoingLinks(view);
			case InformationItemEditPartCN.VISUAL_ID:
				return getInformationItem_Shape_CN_OutgoingLinks(view);
			case ClassEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_OutgoingLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_OutgoingLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_OutgoingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_OutgoingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_PackagedElementShape_CN_OutgoingLinks(view);
			case NestedInterfaceForClassEditPart.VISUAL_ID:
				return getInterface_ClassNestedClassifierLabel_OutgoingLinks(view);
			case NestedInterfaceForComponentEditPart.VISUAL_ID:
				return getInterface_ComponentNestedClassifierLabel_OutgoingLinks(view);
			case NestedInterfaceForInterfaceEditPart.VISUAL_ID:
				return getInterface_InterfaceNestedClassifierLabel_OutgoingLinks(view);
			case NestedEnumerationForClassEditPart.VISUAL_ID:
				return getEnumeration_ClassNestedClassifierLabel_OutgoingLinks(view);
			case NestedEnumerationForComponentEditPart.VISUAL_ID:
				return getEnumeration_ComponentNestedClassifierLabel_OutgoingLinks(view);
			case NestedEnumerationForInterfaceEditPart.VISUAL_ID:
				return getEnumeration_InterfaceNestedClassifierLabel_OutgoingLinks(view);
			case NestedPrimitiveTypeForClassEditPart.VISUAL_ID:
				return getPrimitiveType_ClassNestedClassifierLabel_OutgoingLinks(view);
			case NestedPrimitiveTypeForComponentEditPart.VISUAL_ID:
				return getPrimitiveType_ComponentNestedClassifierLabel_OutgoingLinks(view);
			case NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID:
				return getPrimitiveType_InterfaceNestedClassifierLabel_OutgoingLinks(view);
			case NestedDataTypeForClassEditPart.VISUAL_ID:
				return getDataType_ClassNestedClassifierLabel_OutgoingLinks(view);
			case NestedDataTypeForComponentEditPart.VISUAL_ID:
				return getDataType_ComponentNestedClassifierLabel_OutgoingLinks(view);
			case NestedDataTypeForInterfaceEditPart.VISUAL_ID:
				return getDataType_InterfaceNestedClassifierLabel_OutgoingLinks(view);
			case NestedSignalForClassEditPart.VISUAL_ID:
				return getSignal_ClassNestedClassifierLabel_OutgoingLinks(view);
			case NestedSignalForComponentEditPart.VISUAL_ID:
				return getSignal_ComponentNestedClassifierLabel_OutgoingLinks(view);
			case NestedSignalForInterfaceEditPart.VISUAL_ID:
				return getSignal_InterfaceNestedClassifierLabel_OutgoingLinks(view);
			case NestedComponentForClassEditPart.VISUAL_ID:
				return getComponent_ClassNestedClassifierLabel_OutgoingLinks(view);
			case NestedComponentForInterfaceEditPart.VISUAL_ID:
				return getComponent_InterfaceNestedClassifierLabel_OutgoingLinks(view);
			case NestedComponentForComponentEditPart.VISUAL_ID:
				return getComponent_ComponentNestedClassifierLabel_OutgoingLinks(view);
			case AssociationClassLinkEditPart.VISUAL_ID:
				return getAssociationClass_Edge_OutgoingLinks(view);
			case AssociationEditPart.VISUAL_ID:
				return getAssociation_Edge_OutgoingLinks(view);
			case AssociationBranchEditPart.VISUAL_ID:
				return getAssociation_BranchEdge_OutgoingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_OutgoingLinks(view);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return getInterfaceRealization_Edge_OutgoingLinks(view);
			case SubstitutionEditPart.VISUAL_ID:
				return getSubstitution_Edge_OutgoingLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_OutgoingLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_OutgoingLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_OutgoingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_OutgoingLinks(view);
			case DependencyBranchEditPart.VISUAL_ID:
				return getDependency_BranchEdge_OutgoingLinks(view);
			case ElementImportEditPart.VISUAL_ID:
				return getElementImport_Edge_OutgoingLinks(view);
			case PackageImportEditPart.VISUAL_ID:
				return getPackageImport_Edge_OutgoingLinks(view);
			case PackageMergeEditPart.VISUAL_ID:
				return getPackageMerge_Edge_OutgoingLinks(view);
			case ProfileApplicationEditPart.VISUAL_ID:
				return getProfileApplication_Edge_OutgoingLinks(view);
			case TemplateBindingEditPart.VISUAL_ID:
				return getTemplateBinding_Edge_OutgoingLinks(view);
			case GeneralizationSetEditPart.VISUAL_ID:
				return getGeneralizationSet_Edge_OutgoingLinks(view);
			case InstanceSpecificationLinkEditPart.VISUAL_ID:
				return getInstanceSpecification_Edge_OutgoingLinks(view);
			case InformationFlowEditPart.VISUAL_ID:
				return getInformationFlow_Edge_OutgoingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_ClassDiagram_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_GeneralizationSet_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InstanceSpecification_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_ContainedLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociationClass_Shape_ContainedLinks(View view) {
		AssociationClass modelElement = (AssociationClass) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Shape_ContainedLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Shape_ContainedLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_ContainedLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_ContainedLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_ContainedLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_GeneralizationSet_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InstanceSpecification_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_GeneralizationSet_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InstanceSpecification_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_ContainedLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
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
	public List<UMLLinkDescriptor> getDurationObservation_Shape_ContainedLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_ContainedLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_ContainedLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ClassAttributeLabel_ContainedLinks(View view) {
		Property modelElement = (Property) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ComponentAttributeLabel_ContainedLinks(View view) {
		Property modelElement = (Property) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_SignalAttributeLabel_ContainedLinks(View view) {
		Property modelElement = (Property) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_InterfaceAttributeLabel_ContainedLinks(View view) {
		Property modelElement = (Property) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_PrimitiveTypeAttributeLabel_ContainedLinks(View view) {
		Property modelElement = (Property) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_DataTypeAttributeLabel_ContainedLinks(View view) {
		Property modelElement = (Property) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_ClassNestedClassifierLabel_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_ComponentNestedClassifierLabel_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_InterfaceNestedClassifierLabel_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ClassOperationLabel_ContainedLinks(View view) {
		Operation modelElement = (Operation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ComponentOperationLabel_ContainedLinks(View view) {
		Operation modelElement = (Operation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_InterfaceOperationLabel_ContainedLinks(View view) {
		Operation modelElement = (Operation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_PrimitiveTypeOperationLabel_ContainedLinks(View view) {
		Operation modelElement = (Operation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_DataTypeOperationLabel_ContainedLinks(View view) {
		Operation modelElement = (Operation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnectableElementTemplateParameter_TemplateParameterLabel_ContainedLinks(View view) {
		ConnectableElementTemplateParameter modelElement = (ConnectableElementTemplateParameter) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperationTemplateParameter_TemplateParameterLabel_ContainedLinks(View view) {
		OperationTemplateParameter modelElement = (OperationTemplateParameter) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClassifierTemplateParameter_TemplateParameterLabel_ContainedLinks(View view) {
		ClassifierTemplateParameter modelElement = (ClassifierTemplateParameter) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateParameter_TemplateParameterLabel_ContainedLinks(View view) {
		TemplateParameter modelElement = (TemplateParameter) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_ContainedLinks(View view) {
		EnumerationLiteral modelElement = (EnumerationLiteral) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReception_ReceptionLabel_ContainedLinks(View view) {
		Reception modelElement = (Reception) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReception_InterfaceReceptionLabel_ContainedLinks(View view) {
		Reception modelElement = (Reception) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSlot_SlotLabel_ContainedLinks(View view) {
		Slot modelElement = (Slot) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRedefinableTemplateSignature_Shape_ContainedLinks(View view) {
		RedefinableTemplateSignature modelElement = (RedefinableTemplateSignature) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateSignature_Shape_ContainedLinks(View view) {
		TemplateSignature modelElement = (TemplateSignature) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Shape_CN_ContainedLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_CN_ContainedLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_CN_ContainedLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_ContainedLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_GeneralizationSet_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InstanceSpecification_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_GeneralizationSet_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InstanceSpecification_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_CN_ContainedLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_CN_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_ClassNestedClassifierLabel_ContainedLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_ComponentNestedClassifierLabel_ContainedLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_InterfaceNestedClassifierLabel_ContainedLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_ClassNestedClassifierLabel_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_ComponentNestedClassifierLabel_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_InterfaceNestedClassifierLabel_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_ClassNestedClassifierLabel_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_ComponentNestedClassifierLabel_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_InterfaceNestedClassifierLabel_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_ClassNestedClassifierLabel_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_ComponentNestedClassifierLabel_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_InterfaceNestedClassifierLabel_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_ClassNestedClassifierLabel_ContainedLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_ComponentNestedClassifierLabel_ContainedLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_InterfaceNestedClassifierLabel_ContainedLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_ClassNestedClassifierLabel_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_InterfaceNestedClassifierLabel_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_ComponentNestedClassifierLabel_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociationClass_Edge_ContainedLinks(View view) {
		AssociationClass modelElement = (AssociationClass) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_ContainedLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_BranchEdge_ContainedLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_ContainedLinks(View view) {
		Generalization modelElement = (Generalization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterfaceRealization_Edge_ContainedLinks(View view) {
		InterfaceRealization modelElement = (InterfaceRealization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSubstitution_Edge_ContainedLinks(View view) {
		Substitution modelElement = (Substitution) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_ContainedLinks(View view) {
		Realization modelElement = (Realization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_ContainedLinks(View view) {
		Abstraction modelElement = (Abstraction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_ContainedLinks(View view) {
		Usage modelElement = (Usage) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_ContainedLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_BranchEdge_ContainedLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getElementImport_Edge_ContainedLinks(View view) {
		ElementImport modelElement = (ElementImport) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_ContainedLinks(View view) {
		PackageImport modelElement = (PackageImport) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageMerge_Edge_ContainedLinks(View view) {
		PackageMerge modelElement = (PackageMerge) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfileApplication_Edge_ContainedLinks(View view) {
		ProfileApplication modelElement = (ProfileApplication) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateBinding_Edge_ContainedLinks(View view) {
		TemplateBinding modelElement = (TemplateBinding) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralizationSet_Edge_ContainedLinks(View view) {
		GeneralizationSet modelElement = (GeneralizationSet) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Edge_ContainedLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationFlow_Edge_ContainedLinks(View view) {
		InformationFlow modelElement = (InformationFlow) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociationClass_Shape_IncomingLinks(View view) {
		AssociationClass modelElement = (AssociationClass) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Shape_IncomingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Shape_IncomingLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_IncomingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_IncomingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_IncomingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_IncomingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageMerge_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_IncomingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageMerge_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_IncomingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_IncomingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_IncomingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_IncomingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
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
	public List<UMLLinkDescriptor> getDurationObservation_Shape_IncomingLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_IncomingLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_IncomingLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ClassAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ComponentAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_SignalAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_InterfaceAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_PrimitiveTypeAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_DataTypeAttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_ClassNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_ComponentNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_InterfaceNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ClassOperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ComponentOperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_InterfaceOperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_PrimitiveTypeOperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_DataTypeOperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnectableElementTemplateParameter_TemplateParameterLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperationTemplateParameter_TemplateParameterLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClassifierTemplateParameter_TemplateParameterLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateParameter_TemplateParameterLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReception_ReceptionLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReception_InterfaceReceptionLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSlot_SlotLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRedefinableTemplateSignature_Shape_IncomingLinks(View view) {
		RedefinableTemplateSignature modelElement = (RedefinableTemplateSignature) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateSignature_Shape_IncomingLinks(View view) {
		TemplateSignature modelElement = (TemplateSignature) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Shape_CN_IncomingLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_IncomingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_CN_IncomingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_CN_IncomingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_IncomingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageMerge_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_IncomingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_IncomingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_PackageMerge_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_CN_IncomingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_IncomingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_IncomingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_IncomingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_CN_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_ClassNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_ComponentNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_InterfaceNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_ClassNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_ComponentNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_InterfaceNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_ClassNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_ComponentNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_InterfaceNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_ClassNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_ComponentNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_InterfaceNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_ClassNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_ComponentNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_InterfaceNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_ClassNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_InterfaceNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_ComponentNestedClassifierLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociationClass_Edge_IncomingLinks(View view) {
		AssociationClass modelElement = (AssociationClass) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_IncomingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_BranchEdge_IncomingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_AssociationClass_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Association_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_TemplateBinding_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_IncomingLinks(View view) {
		Generalization modelElement = (Generalization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_GeneralizationSet_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterfaceRealization_Edge_IncomingLinks(View view) {
		InterfaceRealization modelElement = (InterfaceRealization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSubstitution_Edge_IncomingLinks(View view) {
		Substitution modelElement = (Substitution) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_IncomingLinks(View view) {
		Realization modelElement = (Realization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_IncomingLinks(View view) {
		Abstraction modelElement = (Abstraction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_IncomingLinks(View view) {
		Usage modelElement = (Usage) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_BranchEdge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getElementImport_Edge_IncomingLinks(View view) {
		ElementImport modelElement = (ElementImport) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_IncomingLinks(View view) {
		PackageImport modelElement = (PackageImport) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageMerge_Edge_IncomingLinks(View view) {
		PackageMerge modelElement = (PackageMerge) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfileApplication_Edge_IncomingLinks(View view) {
		ProfileApplication modelElement = (ProfileApplication) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateBinding_Edge_IncomingLinks(View view) {
		TemplateBinding modelElement = (TemplateBinding) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralizationSet_Edge_IncomingLinks(View view) {
		GeneralizationSet modelElement = (GeneralizationSet) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Edge_IncomingLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationFlow_Edge_IncomingLinks(View view) {
		InformationFlow modelElement = (InformationFlow) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_BranchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ElementImport_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Shape_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociationClass_Shape_OutgoingLinks(View view) {
		AssociationClass modelElement = (AssociationClass) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Shape_OutgoingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Shape_OutgoingLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_OutgoingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_OutgoingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_OutgoingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_OutgoingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_OutgoingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_OutgoingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_OutgoingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_OutgoingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
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
	public List<UMLLinkDescriptor> getDurationObservation_Shape_OutgoingLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_OutgoingLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_OutgoingLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ClassAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_ComponentAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_SignalAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_InterfaceAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_PrimitiveTypeAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_DataTypeAttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_ClassNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_ComponentNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_InterfaceNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ClassOperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_ComponentOperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_InterfaceOperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_PrimitiveTypeOperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_DataTypeOperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnectableElementTemplateParameter_TemplateParameterLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperationTemplateParameter_TemplateParameterLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClassifierTemplateParameter_TemplateParameterLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateParameter_TemplateParameterLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReception_ReceptionLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReception_InterfaceReceptionLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSlot_SlotLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRedefinableTemplateSignature_Shape_OutgoingLinks(View view) {
		RedefinableTemplateSignature modelElement = (RedefinableTemplateSignature) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateSignature_Shape_OutgoingLinks(View view) {
		TemplateSignature modelElement = (TemplateSignature) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Shape_CN_OutgoingLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_OutgoingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_CN_OutgoingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_CN_OutgoingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getModel_Shape_CN_OutgoingLinks(View view) {
		Model modelElement = (Model) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_OutgoingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_Shape_CN_OutgoingLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_PackageMerge_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ProfileApplication_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_CN_OutgoingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_OutgoingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_OutgoingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PackagedElementShape_CN_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_ClassNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_ComponentNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_InterfaceNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_ClassNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_ComponentNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_InterfaceNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_ClassNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_ComponentNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_InterfaceNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_ClassNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_ComponentNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_InterfaceNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_ClassNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_ComponentNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_InterfaceNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_ClassNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_InterfaceNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_ComponentNestedClassifierLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociationClass_Edge_OutgoingLinks(View view) {
		AssociationClass modelElement = (AssociationClass) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_Edge_OutgoingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAssociation_BranchEdge_OutgoingLinks(View view) {
		Association modelElement = (Association) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_AssociationClass_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Association_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ElementImport_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_PackageImport_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_OutgoingLinks(View view) {
		Generalization modelElement = (Generalization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_GeneralizationSet_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterfaceRealization_Edge_OutgoingLinks(View view) {
		InterfaceRealization modelElement = (InterfaceRealization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSubstitution_Edge_OutgoingLinks(View view) {
		Substitution modelElement = (Substitution) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_OutgoingLinks(View view) {
		Realization modelElement = (Realization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_OutgoingLinks(View view) {
		Abstraction modelElement = (Abstraction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_OutgoingLinks(View view) {
		Usage modelElement = (Usage) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_BranchEdge_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getElementImport_Edge_OutgoingLinks(View view) {
		ElementImport modelElement = (ElementImport) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageImport_Edge_OutgoingLinks(View view) {
		PackageImport modelElement = (PackageImport) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackageMerge_Edge_OutgoingLinks(View view) {
		PackageMerge modelElement = (PackageMerge) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProfileApplication_Edge_OutgoingLinks(View view) {
		ProfileApplication modelElement = (ProfileApplication) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTemplateBinding_Edge_OutgoingLinks(View view) {
		TemplateBinding modelElement = (TemplateBinding) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralizationSet_Edge_OutgoingLinks(View view) {
		GeneralizationSet modelElement = (GeneralizationSet) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceSpecification_Edge_OutgoingLinks(View view) {
		InstanceSpecification modelElement = (InstanceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationFlow_Edge_OutgoingLinks(View view) {
		InformationFlow modelElement = (InformationFlow) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_AssociationClass_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof AssociationClass) {
				continue;
			}
			AssociationClass link = (AssociationClass) linkObject;
			if (!AssociationClassLinkEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.AssociationClass_Edge, AssociationClassLinkEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Association_BranchEdge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_BranchEdge, AssociationBranchEditPart.VISUAL_ID));
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
			result.add(new UMLLinkDescriptor(container, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_InterfaceRealization_Edge(BehavioredClassifier container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getInterfaceRealizations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof InterfaceRealization) {
				continue;
			}
			InterfaceRealization link = (InterfaceRealization) linkObject;
			if (!InterfaceRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Interface dst = link.getContract();
			result.add(new UMLLinkDescriptor(container, dst, link, UMLElementTypes.InterfaceRealization_Edge, InterfaceRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Substitution_Edge(Classifier container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getSubstitutions()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Substitution) {
				continue;
			}
			Substitution link = (Substitution) linkObject;
			if (!SubstitutionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier dst = link.getContract();
			Classifier src = link.getSubstitutingClassifier();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Substitution_Edge, SubstitutionEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Dependency_BranchEdge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_BranchEdge, DependencyBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ElementImport_Edge(Namespace container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getElementImports()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ElementImport) {
				continue;
			}
			ElementImport link = (ElementImport) linkObject;
			if (!ElementImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			PackageableElement dst = link.getImportedElement();
			result.add(new UMLLinkDescriptor(container, dst, link, UMLElementTypes.ElementImport_Edge, ElementImportEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ProfileApplication_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getProfileApplications()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ProfileApplication) {
				continue;
			}
			ProfileApplication link = (ProfileApplication) linkObject;
			if (!ProfileApplicationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Profile dst = link.getAppliedProfile();
			result.add(new UMLLinkDescriptor(container, dst, link, UMLElementTypes.ProfileApplication_Edge, ProfileApplicationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_TemplateBinding_Edge(TemplateableElement container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getTemplateBindings()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof TemplateBinding) {
				continue;
			}
			TemplateBinding link = (TemplateBinding) linkObject;
			if (!TemplateBindingEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			TemplateableElement dst = link.getBoundElement();
			TemplateableElement src = link.getBoundElement();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.TemplateBinding_Edge, TemplateBindingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_GeneralizationSet_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof GeneralizationSet) {
				continue;
			}
			GeneralizationSet link = (GeneralizationSet) linkObject;
			if (!GeneralizationSetEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getGeneralizations();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Generalization) {
				continue;
			}
			Generalization dst = (Generalization) theTarget;
			List<?> sources = link.getGeneralizations();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Generalization) {
				continue;
			}
			Generalization src = (Generalization) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.GeneralizationSet_Edge, GeneralizationSetEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_InstanceSpecification_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof InstanceSpecification) {
				continue;
			}
			InstanceSpecification link = (InstanceSpecification) linkObject;
			if (!InstanceSpecificationLinkEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSlots();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Slot) {
				continue;
			}
			Slot dst = (Slot) theTarget;
			List<?> sources = link.getSlots();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Slot) {
				continue;
			}
			Slot src = (Slot) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.InstanceSpecification_Edge, InstanceSpecificationLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_InformationFlow_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof InformationFlow) {
				continue;
			}
			InformationFlow link = (InformationFlow) linkObject;
			if (!InformationFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getInformationTargets();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getInformationSources();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.InformationFlow_Edge, InformationFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_AssociationClass_Edge(Type target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getAssociation_EndType() || false == setting.getEObject() instanceof AssociationClass) {
				continue;
			}
			AssociationClass link = (AssociationClass) setting.getEObject();
			if (!AssociationClassLinkEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.AssociationClass_Edge, AssociationClassLinkEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Association_BranchEdge(Type target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getAssociation_EndType() || false == setting.getEObject() instanceof Association) {
				continue;
			}
			Association link = (Association) setting.getEObject();
			if (!AssociationBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Association_BranchEdge, AssociationBranchEditPart.VISUAL_ID));
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
			if (false == link.eContainer() instanceof Classifier) {
				continue;
			}
			Classifier container = (Classifier) link.eContainer();
			result.add(new UMLLinkDescriptor(container, target, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(Interface target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getInterfaceRealization_Contract() || false == setting.getEObject() instanceof InterfaceRealization) {
				continue;
			}
			InterfaceRealization link = (InterfaceRealization) setting.getEObject();
			if (!InterfaceRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			if (false == link.eContainer() instanceof BehavioredClassifier) {
				continue;
			}
			BehavioredClassifier container = (BehavioredClassifier) link.eContainer();
			result.add(new UMLLinkDescriptor(container, target, link, UMLElementTypes.InterfaceRealization_Edge, InterfaceRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Substitution_Edge(Classifier target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getSubstitution_Contract() || false == setting.getEObject() instanceof Substitution) {
				continue;
			}
			Substitution link = (Substitution) setting.getEObject();
			if (!SubstitutionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier src = link.getSubstitutingClassifier();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Substitution_Edge, SubstitutionEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Dependency_BranchEdge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) setting.getEObject();
			if (!DependencyBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Dependency_BranchEdge, DependencyBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_ElementImport_Edge(PackageableElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getElementImport_ImportedElement() || false == setting.getEObject() instanceof ElementImport) {
				continue;
			}
			ElementImport link = (ElementImport) setting.getEObject();
			if (!ElementImportEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			if (false == link.eContainer() instanceof Namespace) {
				continue;
			}
			Namespace container = (Namespace) link.eContainer();
			result.add(new UMLLinkDescriptor(container, target, link, UMLElementTypes.ElementImport_Edge, ElementImportEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_TemplateBinding_Edge(TemplateableElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getTemplateBinding_BoundElement() || false == setting.getEObject() instanceof TemplateBinding) {
				continue;
			}
			TemplateBinding link = (TemplateBinding) setting.getEObject();
			if (!TemplateBindingEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			TemplateableElement src = link.getBoundElement();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.TemplateBinding_Edge, TemplateBindingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_GeneralizationSet_Edge(Generalization target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getGeneralizationSet_Generalization() || false == setting.getEObject() instanceof GeneralizationSet) {
				continue;
			}
			GeneralizationSet link = (GeneralizationSet) setting.getEObject();
			if (!GeneralizationSetEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getGeneralizations();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Generalization) {
				continue;
			}
			Generalization src = (Generalization) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.GeneralizationSet_Edge, GeneralizationSetEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getElement_OwnedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Element_ContainmentEdge, ContainmentLinkEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getTimeObservation_Event()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.TimeObservation_EventEdge, ConnectorTimeObservationEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getDurationObservation_Event()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.DurationObservation_EventEdge, ConnectorDurationObservationEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_InformationFlow_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getInformationFlow_InformationTarget() || false == setting.getEObject() instanceof InformationFlow) {
				continue;
			}
			InformationFlow link = (InformationFlow) setting.getEObject();
			if (!InformationFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getInformationSources();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.InformationFlow_Edge, InformationFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Constraint_ContextEdge(Namespace target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getConstraint_Context()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Constraint_ContextEdge, ContextLinkEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_AssociationClass_Edge(Type source) {
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
			if (false == linkObject instanceof AssociationClass) {
				continue;
			}
			AssociationClass link = (AssociationClass) linkObject;
			if (!AssociationClassLinkEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.AssociationClass_Edge, AssociationClassLinkEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Association_BranchEdge(Type source) {
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
			if (!AssociationBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_BranchEdge, AssociationBranchEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Substitution_Edge(Classifier source) {
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
		for (Iterator<?> links = container.getSubstitutions()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Substitution) {
				continue;
			}
			Substitution link = (Substitution) linkObject;
			if (!SubstitutionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier dst = link.getContract();
			Classifier src = link.getSubstitutingClassifier();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Substitution_Edge, SubstitutionEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Dependency_BranchEdge(NamedElement source) {
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
			if (!DependencyBranchEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
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
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_BranchEdge, DependencyBranchEditPart.VISUAL_ID));
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
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_TemplateBinding_Edge(TemplateableElement source) {
		TemplateableElement container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof TemplateableElement) {
				container = (TemplateableElement) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getTemplateBindings()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof TemplateBinding) {
				continue;
			}
			TemplateBinding link = (TemplateBinding) linkObject;
			if (!TemplateBindingEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			TemplateableElement dst = link.getBoundElement();
			TemplateableElement src = link.getBoundElement();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.TemplateBinding_Edge, TemplateBindingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_GeneralizationSet_Edge(Generalization source) {
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
			if (false == linkObject instanceof GeneralizationSet) {
				continue;
			}
			GeneralizationSet link = (GeneralizationSet) linkObject;
			if (!GeneralizationSetEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getGeneralizations();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof Generalization) {
				continue;
			}
			Generalization dst = (Generalization) theTarget;
			List<?> sources = link.getGeneralizations();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof Generalization) {
				continue;
			}
			Generalization src = (Generalization) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.GeneralizationSet_Edge, GeneralizationSetEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(Element source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getOwnedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Element_ContainmentEdge, ContainmentLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_TimeObservation_EventEdge(TimeObservation source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		NamedElement destination = source.getEvent();
		if (destination == null) {
			return result;
		}
		result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.TimeObservation_EventEdge, ConnectorTimeObservationEditPart.VISUAL_ID));
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_DurationObservation_EventEdge(DurationObservation source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getEvents()
				.iterator(); destinations.hasNext();) {
			NamedElement destination = (NamedElement) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.DurationObservation_EventEdge, ConnectorDurationObservationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_InformationFlow_Edge(NamedElement source) {
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
			if (false == linkObject instanceof InformationFlow) {
				continue;
			}
			InformationFlow link = (InformationFlow) linkObject;
			if (!InformationFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getInformationTargets();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getInformationSources();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.InformationFlow_Edge, InformationFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Constraint_ContextEdge(Constraint source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Namespace destination = source.getContext();
		if (destination == null) {
			return result;
		}
		result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Constraint_ContextEdge, ContextLinkEditPart.VISUAL_ID));
		return result;
	}
}
