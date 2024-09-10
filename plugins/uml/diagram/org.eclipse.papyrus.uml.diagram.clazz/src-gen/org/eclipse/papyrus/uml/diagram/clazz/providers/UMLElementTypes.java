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

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.DiagramElementTypes;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramEditorPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLElementTypes {

	/**
	 * @generated
	 */
	private UMLElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(UMLDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Package_ClassDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_ClassDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AssociationClass_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.AssociationClass_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Association_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Association_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InstanceSpecification_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InstanceSpecification_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Signal_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Signal_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Model_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Model_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InformationItem_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InformationItem_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_PackagedElementShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_PackagedElementShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Diagram_ShortcutShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Diagram_ShortcutShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationObservation_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationObservation_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeObservation_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeObservation_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType NamedElement_DefaultShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.NamedElement_DefaultShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_ClassAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_ClassAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_ComponentAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_ComponentAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_SignalAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_SignalAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_InterfaceAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_InterfaceAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_PrimitiveTypeAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_PrimitiveTypeAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_DataTypeAttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_DataTypeAttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_ClassNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_ClassNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_ComponentNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_ComponentNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_InterfaceNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_InterfaceNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_ClassOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_ClassOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_ComponentOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_ComponentOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_InterfaceOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_InterfaceOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_PrimitiveTypeOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_PrimitiveTypeOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_DataTypeOperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_DataTypeOperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ConnectableElementTemplateParameter_TemplateParameterLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ConnectableElementTemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OperationTemplateParameter_TemplateParameterLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OperationTemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ClassifierTemplateParameter_TemplateParameterLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ClassifierTemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TemplateParameter_TemplateParameterLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TemplateParameter_TemplateParameterLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType EnumerationLiteral_LiteralLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.EnumerationLiteral_LiteralLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Reception_ReceptionLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Reception_ReceptionLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Reception_InterfaceReceptionLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Reception_InterfaceReceptionLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Slot_SlotLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Slot_SlotLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType RedefinableTemplateSignature_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.RedefinableTemplateSignature_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TemplateSignature_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TemplateSignature_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InstanceSpecification_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InstanceSpecification_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Signal_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Signal_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Model_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Model_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Package_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InformationItem_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InformationItem_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_PackagedElementShape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_PackagedElementShape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_ClassNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_ClassNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_ComponentNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_ComponentNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_InterfaceNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_InterfaceNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_ClassNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_ClassNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_ComponentNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_ComponentNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_InterfaceNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_InterfaceNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_ClassNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_ClassNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_ComponentNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_ComponentNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_InterfaceNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_InterfaceNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_ClassNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_ClassNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_ComponentNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_ComponentNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_InterfaceNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_InterfaceNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Signal_ClassNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Signal_ClassNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Signal_ComponentNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Signal_ComponentNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Signal_InterfaceNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Signal_InterfaceNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_ClassNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_ClassNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_InterfaceNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_InterfaceNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_ComponentNestedClassifierLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_ComponentNestedClassifierLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AssociationClass_TetherEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.AssociationClass_TetherEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AssociationClass_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.AssociationClass_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Association_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Association_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Association_BranchEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Association_BranchEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Generalization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Generalization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InterfaceRealization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InterfaceRealization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Substitution_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Substitution_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Realization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Realization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Abstraction_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Abstraction_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Usage_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Usage_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_BranchEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_BranchEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ElementImport_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ElementImport_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PackageImport_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PackageImport_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PackageMerge_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PackageMerge_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ProfileApplication_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ProfileApplication_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_AnnotatedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_AnnotatedElementEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_ConstrainedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_ConstrainedElementEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TemplateBinding_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TemplateBinding_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType GeneralizationSet_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.GeneralizationSet_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InstanceSpecification_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InstanceSpecification_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Element_ContainmentEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Element_ContainmentEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeObservation_EventEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeObservation_EventEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationObservation_EventEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationObservation_EventEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InformationFlow_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InformationFlow_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_ContextEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_ContextEdge"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		return elementTypeImages.getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		return elementTypeImages.getImage(element);
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		return getImageDescriptor(getElement(hint));
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		return getImage(getElement(hint));
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 *
	 * @generated
	 */
	public static synchronized ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<>();
			elements.put(Package_ClassDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(Dependency_Shape, UMLPackage.eINSTANCE.getDependency());
			elements.put(AssociationClass_Shape, UMLPackage.eINSTANCE.getAssociationClass());
			elements.put(Association_Shape, UMLPackage.eINSTANCE.getAssociation());
			elements.put(InstanceSpecification_Shape, UMLPackage.eINSTANCE.getInstanceSpecification());
			elements.put(Component_Shape, UMLPackage.eINSTANCE.getComponent());
			elements.put(Signal_Shape, UMLPackage.eINSTANCE.getSignal());
			elements.put(Interface_Shape, UMLPackage.eINSTANCE.getInterface());
			elements.put(Model_Shape, UMLPackage.eINSTANCE.getModel());
			elements.put(Enumeration_Shape, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(Package_Shape, UMLPackage.eINSTANCE.getPackage());
			elements.put(InformationItem_Shape, UMLPackage.eINSTANCE.getInformationItem());
			elements.put(Class_Shape, UMLPackage.eINSTANCE.getClass_());
			elements.put(PrimitiveType_Shape, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(DataType_Shape, UMLPackage.eINSTANCE.getDataType());
			elements.put(Constraint_PackagedElementShape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(Diagram_ShortcutShape, NotationPackage.eINSTANCE.getDiagram());
			elements.put(DurationObservation_Shape, UMLPackage.eINSTANCE.getDurationObservation());
			elements.put(TimeObservation_Shape, UMLPackage.eINSTANCE.getTimeObservation());
			elements.put(NamedElement_DefaultShape, UMLPackage.eINSTANCE.getNamedElement());
			elements.put(Property_ClassAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Property_ComponentAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Property_SignalAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Property_InterfaceAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Property_PrimitiveTypeAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Property_DataTypeAttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Class_ClassNestedClassifierLabel, UMLPackage.eINSTANCE.getClass_());
			elements.put(Class_ComponentNestedClassifierLabel, UMLPackage.eINSTANCE.getClass_());
			elements.put(Class_InterfaceNestedClassifierLabel, UMLPackage.eINSTANCE.getClass_());
			elements.put(Operation_ClassOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(Operation_ComponentOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(Operation_InterfaceOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(Operation_PrimitiveTypeOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(Operation_DataTypeOperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(ConnectableElementTemplateParameter_TemplateParameterLabel, UMLPackage.eINSTANCE.getConnectableElementTemplateParameter());
			elements.put(OperationTemplateParameter_TemplateParameterLabel, UMLPackage.eINSTANCE.getOperationTemplateParameter());
			elements.put(ClassifierTemplateParameter_TemplateParameterLabel, UMLPackage.eINSTANCE.getClassifierTemplateParameter());
			elements.put(TemplateParameter_TemplateParameterLabel, UMLPackage.eINSTANCE.getTemplateParameter());
			elements.put(EnumerationLiteral_LiteralLabel, UMLPackage.eINSTANCE.getEnumerationLiteral());
			elements.put(Reception_ReceptionLabel, UMLPackage.eINSTANCE.getReception());
			elements.put(Reception_InterfaceReceptionLabel, UMLPackage.eINSTANCE.getReception());
			elements.put(Slot_SlotLabel, UMLPackage.eINSTANCE.getSlot());
			elements.put(RedefinableTemplateSignature_Shape, UMLPackage.eINSTANCE.getRedefinableTemplateSignature());
			elements.put(TemplateSignature_Shape, UMLPackage.eINSTANCE.getTemplateSignature());
			elements.put(InstanceSpecification_Shape_CN, UMLPackage.eINSTANCE.getInstanceSpecification());
			elements.put(Component_Shape_CN, UMLPackage.eINSTANCE.getComponent());
			elements.put(Signal_Shape_CN, UMLPackage.eINSTANCE.getSignal());
			elements.put(Interface_Shape_CN, UMLPackage.eINSTANCE.getInterface());
			elements.put(Model_Shape_CN, UMLPackage.eINSTANCE.getModel());
			elements.put(Enumeration_Shape_CN, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(Package_Shape_CN, UMLPackage.eINSTANCE.getPackage());
			elements.put(InformationItem_Shape_CN, UMLPackage.eINSTANCE.getInformationItem());
			elements.put(Class_Shape_CN, UMLPackage.eINSTANCE.getClass_());
			elements.put(PrimitiveType_Shape_CN, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(DataType_Shape_CN, UMLPackage.eINSTANCE.getDataType());
			elements.put(Comment_Shape_CN, UMLPackage.eINSTANCE.getComment());
			elements.put(Constraint_PackagedElementShape_CN, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Interface_ClassNestedClassifierLabel, UMLPackage.eINSTANCE.getInterface());
			elements.put(Interface_ComponentNestedClassifierLabel, UMLPackage.eINSTANCE.getInterface());
			elements.put(Interface_InterfaceNestedClassifierLabel, UMLPackage.eINSTANCE.getInterface());
			elements.put(Enumeration_ClassNestedClassifierLabel, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(Enumeration_ComponentNestedClassifierLabel, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(Enumeration_InterfaceNestedClassifierLabel, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(PrimitiveType_ClassNestedClassifierLabel, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(PrimitiveType_ComponentNestedClassifierLabel, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(PrimitiveType_InterfaceNestedClassifierLabel, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(DataType_ClassNestedClassifierLabel, UMLPackage.eINSTANCE.getDataType());
			elements.put(DataType_ComponentNestedClassifierLabel, UMLPackage.eINSTANCE.getDataType());
			elements.put(DataType_InterfaceNestedClassifierLabel, UMLPackage.eINSTANCE.getDataType());
			elements.put(Signal_ClassNestedClassifierLabel, UMLPackage.eINSTANCE.getSignal());
			elements.put(Signal_ComponentNestedClassifierLabel, UMLPackage.eINSTANCE.getSignal());
			elements.put(Signal_InterfaceNestedClassifierLabel, UMLPackage.eINSTANCE.getSignal());
			elements.put(Component_ClassNestedClassifierLabel, UMLPackage.eINSTANCE.getComponent());
			elements.put(Component_InterfaceNestedClassifierLabel, UMLPackage.eINSTANCE.getComponent());
			elements.put(Component_ComponentNestedClassifierLabel, UMLPackage.eINSTANCE.getComponent());
			elements.put(AssociationClass_Edge, UMLPackage.eINSTANCE.getAssociationClass());
			elements.put(Association_Edge, UMLPackage.eINSTANCE.getAssociation());
			elements.put(Association_BranchEdge, UMLPackage.eINSTANCE.getAssociation());
			elements.put(Generalization_Edge, UMLPackage.eINSTANCE.getGeneralization());
			elements.put(InterfaceRealization_Edge, UMLPackage.eINSTANCE.getInterfaceRealization());
			elements.put(Substitution_Edge, UMLPackage.eINSTANCE.getSubstitution());
			elements.put(Realization_Edge, UMLPackage.eINSTANCE.getRealization());
			elements.put(Abstraction_Edge, UMLPackage.eINSTANCE.getAbstraction());
			elements.put(Usage_Edge, UMLPackage.eINSTANCE.getUsage());
			elements.put(Dependency_Edge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Dependency_BranchEdge, UMLPackage.eINSTANCE.getDependency());
			elements.put(ElementImport_Edge, UMLPackage.eINSTANCE.getElementImport());
			elements.put(PackageImport_Edge, UMLPackage.eINSTANCE.getPackageImport());
			elements.put(PackageMerge_Edge, UMLPackage.eINSTANCE.getPackageMerge());
			elements.put(ProfileApplication_Edge, UMLPackage.eINSTANCE.getProfileApplication());
			elements.put(Comment_AnnotatedElementEdge, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
			elements.put(Constraint_ConstrainedElementEdge, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
			elements.put(TemplateBinding_Edge, UMLPackage.eINSTANCE.getTemplateBinding());
			elements.put(GeneralizationSet_Edge, UMLPackage.eINSTANCE.getGeneralizationSet());
			elements.put(InstanceSpecification_Edge, UMLPackage.eINSTANCE.getInstanceSpecification());
			elements.put(Element_ContainmentEdge, UMLPackage.eINSTANCE.getElement_OwnedElement());
			elements.put(TimeObservation_EventEdge, UMLPackage.eINSTANCE.getTimeObservation_Event());
			elements.put(DurationObservation_EventEdge, UMLPackage.eINSTANCE.getDurationObservation_Event());
			elements.put(InformationFlow_Edge, UMLPackage.eINSTANCE.getInformationFlow());
			elements.put(Constraint_ContextEdge, UMLPackage.eINSTANCE.getConstraint_Context());
		}
		return elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementTypeByUniqueId(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static synchronized boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<>();
			KNOWN_ELEMENT_TYPES.add(Package_ClassDiagram);
			KNOWN_ELEMENT_TYPES.add(Dependency_Shape);
			KNOWN_ELEMENT_TYPES.add(AssociationClass_Shape);
			KNOWN_ELEMENT_TYPES.add(Association_Shape);
			KNOWN_ELEMENT_TYPES.add(InstanceSpecification_Shape);
			KNOWN_ELEMENT_TYPES.add(Component_Shape);
			KNOWN_ELEMENT_TYPES.add(Signal_Shape);
			KNOWN_ELEMENT_TYPES.add(Interface_Shape);
			KNOWN_ELEMENT_TYPES.add(Model_Shape);
			KNOWN_ELEMENT_TYPES.add(Enumeration_Shape);
			KNOWN_ELEMENT_TYPES.add(Package_Shape);
			KNOWN_ELEMENT_TYPES.add(InformationItem_Shape);
			KNOWN_ELEMENT_TYPES.add(Class_Shape);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_Shape);
			KNOWN_ELEMENT_TYPES.add(DataType_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_PackagedElementShape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(Diagram_ShortcutShape);
			KNOWN_ELEMENT_TYPES.add(DurationObservation_Shape);
			KNOWN_ELEMENT_TYPES.add(TimeObservation_Shape);
			KNOWN_ELEMENT_TYPES.add(NamedElement_DefaultShape);
			KNOWN_ELEMENT_TYPES.add(Property_ClassAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Property_ComponentAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Property_SignalAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Property_InterfaceAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Property_PrimitiveTypeAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Property_DataTypeAttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Class_ClassNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Class_ComponentNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Class_InterfaceNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_ClassOperationLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_ComponentOperationLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_InterfaceOperationLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_PrimitiveTypeOperationLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_DataTypeOperationLabel);
			KNOWN_ELEMENT_TYPES.add(ConnectableElementTemplateParameter_TemplateParameterLabel);
			KNOWN_ELEMENT_TYPES.add(OperationTemplateParameter_TemplateParameterLabel);
			KNOWN_ELEMENT_TYPES.add(ClassifierTemplateParameter_TemplateParameterLabel);
			KNOWN_ELEMENT_TYPES.add(TemplateParameter_TemplateParameterLabel);
			KNOWN_ELEMENT_TYPES.add(EnumerationLiteral_LiteralLabel);
			KNOWN_ELEMENT_TYPES.add(Reception_ReceptionLabel);
			KNOWN_ELEMENT_TYPES.add(Reception_InterfaceReceptionLabel);
			KNOWN_ELEMENT_TYPES.add(Slot_SlotLabel);
			KNOWN_ELEMENT_TYPES.add(RedefinableTemplateSignature_Shape);
			KNOWN_ELEMENT_TYPES.add(TemplateSignature_Shape);
			KNOWN_ELEMENT_TYPES.add(InstanceSpecification_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Component_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Signal_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Interface_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Model_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Enumeration_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Package_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(InformationItem_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Class_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(DataType_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Constraint_PackagedElementShape_CN);
			KNOWN_ELEMENT_TYPES.add(Interface_ClassNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Interface_ComponentNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Interface_InterfaceNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Enumeration_ClassNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Enumeration_ComponentNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Enumeration_InterfaceNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_ClassNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_ComponentNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_InterfaceNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(DataType_ClassNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(DataType_ComponentNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(DataType_InterfaceNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Signal_ClassNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Signal_ComponentNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Signal_InterfaceNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Component_ClassNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Component_InterfaceNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(Component_ComponentNestedClassifierLabel);
			KNOWN_ELEMENT_TYPES.add(AssociationClass_TetherEdge);
			KNOWN_ELEMENT_TYPES.add(AssociationClass_Edge);
			KNOWN_ELEMENT_TYPES.add(Association_Edge);
			KNOWN_ELEMENT_TYPES.add(Association_BranchEdge);
			KNOWN_ELEMENT_TYPES.add(Generalization_Edge);
			KNOWN_ELEMENT_TYPES.add(InterfaceRealization_Edge);
			KNOWN_ELEMENT_TYPES.add(Substitution_Edge);
			KNOWN_ELEMENT_TYPES.add(Realization_Edge);
			KNOWN_ELEMENT_TYPES.add(Abstraction_Edge);
			KNOWN_ELEMENT_TYPES.add(Usage_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_BranchEdge);
			KNOWN_ELEMENT_TYPES.add(ElementImport_Edge);
			KNOWN_ELEMENT_TYPES.add(PackageImport_Edge);
			KNOWN_ELEMENT_TYPES.add(PackageMerge_Edge);
			KNOWN_ELEMENT_TYPES.add(ProfileApplication_Edge);
			KNOWN_ELEMENT_TYPES.add(Comment_AnnotatedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ConstrainedElementEdge);
			KNOWN_ELEMENT_TYPES.add(TemplateBinding_Edge);
			KNOWN_ELEMENT_TYPES.add(GeneralizationSet_Edge);
			KNOWN_ELEMENT_TYPES.add(InstanceSpecification_Edge);
			KNOWN_ELEMENT_TYPES.add(Element_ContainmentEdge);
			KNOWN_ELEMENT_TYPES.add(TimeObservation_EventEdge);
			KNOWN_ELEMENT_TYPES.add(DurationObservation_EventEdge);
			KNOWN_ELEMENT_TYPES.add(InformationFlow_Edge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ContextEdge);
		}

		boolean result = KNOWN_ELEMENT_TYPES.contains(elementType);

		if (!result) {
			IElementType[] supertypes = elementType.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = KNOWN_ELEMENT_TYPES.contains(supertypes[i]);
			}
		}

		return result;
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case ModelEditPart.VISUAL_ID:
				return Package_ClassDiagram;
			case DependencyNodeEditPart.VISUAL_ID:
				return Dependency_Shape;
			case AssociationClassEditPart.VISUAL_ID:
				return AssociationClass_Shape;
			case AssociationNodeEditPart.VISUAL_ID:
				return Association_Shape;
			case InstanceSpecificationEditPart.VISUAL_ID:
				return InstanceSpecification_Shape;
			case ComponentEditPart.VISUAL_ID:
				return Component_Shape;
			case SignalEditPart.VISUAL_ID:
				return Signal_Shape;
			case InterfaceEditPart.VISUAL_ID:
				return Interface_Shape;
			case ModelEditPartTN.VISUAL_ID:
				return Model_Shape;
			case EnumerationEditPart.VISUAL_ID:
				return Enumeration_Shape;
			case PackageEditPart.VISUAL_ID:
				return Package_Shape;
			case InformationItemEditPart.VISUAL_ID:
				return InformationItem_Shape;
			case ClassEditPart.VISUAL_ID:
				return Class_Shape;
			case PrimitiveTypeEditPart.VISUAL_ID:
				return PrimitiveType_Shape;
			case DataTypeEditPart.VISUAL_ID:
				return DataType_Shape;
			case ConstraintEditPart.VISUAL_ID:
				return Constraint_PackagedElementShape;
			case CommentEditPart.VISUAL_ID:
				return Comment_Shape;
			case ShortCutDiagramEditPart.VISUAL_ID:
				return Diagram_ShortcutShape;
			case DurationObservationEditPart.VISUAL_ID:
				return DurationObservation_Shape;
			case TimeObservationEditPart.VISUAL_ID:
				return TimeObservation_Shape;
			case DefaultNamedElementEditPart.VISUAL_ID:
				return NamedElement_DefaultShape;
			case PropertyForClassEditPart.VISUAL_ID:
				return Property_ClassAttributeLabel;
			case PropertyForComponentEditPart.VISUAL_ID:
				return Property_ComponentAttributeLabel;
			case PropertyForSignalEditPart.VISUAL_ID:
				return Property_SignalAttributeLabel;
			case PropertyForInterfaceEditPart.VISUAL_ID:
				return Property_InterfaceAttributeLabel;
			case PropertyforPrimitiveTypeEditPart.VISUAL_ID:
				return Property_PrimitiveTypeAttributeLabel;
			case PropertyforDataTypeEditPart.VISUAL_ID:
				return Property_DataTypeAttributeLabel;
			case NestedClassForClassEditPart.VISUAL_ID:
				return Class_ClassNestedClassifierLabel;
			case NestedClassForComponentEditPart.VISUAL_ID:
				return Class_ComponentNestedClassifierLabel;
			case NestedClassForInterfaceEditPart.VISUAL_ID:
				return Class_InterfaceNestedClassifierLabel;
			case OperationForClassEditPart.VISUAL_ID:
				return Operation_ClassOperationLabel;
			case OperationForComponentEditPart.VISUAL_ID:
				return Operation_ComponentOperationLabel;
			case OperationForInterfaceEditpart.VISUAL_ID:
				return Operation_InterfaceOperationLabel;
			case OperationForPrimitiveTypeEditPart.VISUAL_ID:
				return Operation_PrimitiveTypeOperationLabel;
			case OperationForDataTypeEditPart.VISUAL_ID:
				return Operation_DataTypeOperationLabel;
			case ConnectableElementTemplateParameterEditPart.VISUAL_ID:
				return ConnectableElementTemplateParameter_TemplateParameterLabel;
			case OperationTemplateParameterEditPart.VISUAL_ID:
				return OperationTemplateParameter_TemplateParameterLabel;
			case ClassifierTemplateParameterEditPart.VISUAL_ID:
				return ClassifierTemplateParameter_TemplateParameterLabel;
			case TemplateParameterEditPart.VISUAL_ID:
				return TemplateParameter_TemplateParameterLabel;
			case EnumerationLiteralEditPart.VISUAL_ID:
				return EnumerationLiteral_LiteralLabel;
			case ReceptionEditPart.VISUAL_ID:
				return Reception_ReceptionLabel;
			case ReceptionInInterfaceEditPart.VISUAL_ID:
				return Reception_InterfaceReceptionLabel;
			case SlotEditPart.VISUAL_ID:
				return Slot_SlotLabel;
			case RedefinableTemplateSignatureEditPart.VISUAL_ID:
				return RedefinableTemplateSignature_Shape;
			case TemplateSignatureEditPart.VISUAL_ID:
				return TemplateSignature_Shape;
			case InstanceSpecificationEditPartCN.VISUAL_ID:
				return InstanceSpecification_Shape_CN;
			case ComponentEditPartCN.VISUAL_ID:
				return Component_Shape_CN;
			case SignalEditPartCN.VISUAL_ID:
				return Signal_Shape_CN;
			case InterfaceEditPartCN.VISUAL_ID:
				return Interface_Shape_CN;
			case ModelEditPartCN.VISUAL_ID:
				return Model_Shape_CN;
			case EnumerationEditPartCN.VISUAL_ID:
				return Enumeration_Shape_CN;
			case PackageEditPartCN.VISUAL_ID:
				return Package_Shape_CN;
			case InformationItemEditPartCN.VISUAL_ID:
				return InformationItem_Shape_CN;
			case ClassEditPartCN.VISUAL_ID:
				return Class_Shape_CN;
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return PrimitiveType_Shape_CN;
			case DataTypeEditPartCN.VISUAL_ID:
				return DataType_Shape_CN;
			case CommentEditPartCN.VISUAL_ID:
				return Comment_Shape_CN;
			case ConstraintEditPartCN.VISUAL_ID:
				return Constraint_PackagedElementShape_CN;
			case NestedInterfaceForClassEditPart.VISUAL_ID:
				return Interface_ClassNestedClassifierLabel;
			case NestedInterfaceForComponentEditPart.VISUAL_ID:
				return Interface_ComponentNestedClassifierLabel;
			case NestedInterfaceForInterfaceEditPart.VISUAL_ID:
				return Interface_InterfaceNestedClassifierLabel;
			case NestedEnumerationForClassEditPart.VISUAL_ID:
				return Enumeration_ClassNestedClassifierLabel;
			case NestedEnumerationForComponentEditPart.VISUAL_ID:
				return Enumeration_ComponentNestedClassifierLabel;
			case NestedEnumerationForInterfaceEditPart.VISUAL_ID:
				return Enumeration_InterfaceNestedClassifierLabel;
			case NestedPrimitiveTypeForClassEditPart.VISUAL_ID:
				return PrimitiveType_ClassNestedClassifierLabel;
			case NestedPrimitiveTypeForComponentEditPart.VISUAL_ID:
				return PrimitiveType_ComponentNestedClassifierLabel;
			case NestedPrimitiveTypeForInterfaceEditPart.VISUAL_ID:
				return PrimitiveType_InterfaceNestedClassifierLabel;
			case NestedDataTypeForClassEditPart.VISUAL_ID:
				return DataType_ClassNestedClassifierLabel;
			case NestedDataTypeForComponentEditPart.VISUAL_ID:
				return DataType_ComponentNestedClassifierLabel;
			case NestedDataTypeForInterfaceEditPart.VISUAL_ID:
				return DataType_InterfaceNestedClassifierLabel;
			case NestedSignalForClassEditPart.VISUAL_ID:
				return Signal_ClassNestedClassifierLabel;
			case NestedSignalForComponentEditPart.VISUAL_ID:
				return Signal_ComponentNestedClassifierLabel;
			case NestedSignalForInterfaceEditPart.VISUAL_ID:
				return Signal_InterfaceNestedClassifierLabel;
			case NestedComponentForClassEditPart.VISUAL_ID:
				return Component_ClassNestedClassifierLabel;
			case NestedComponentForInterfaceEditPart.VISUAL_ID:
				return Component_InterfaceNestedClassifierLabel;
			case NestedComponentForComponentEditPart.VISUAL_ID:
				return Component_ComponentNestedClassifierLabel;
			case AssociationClassDashedLinkEditPart.VISUAL_ID:
				return AssociationClass_TetherEdge;
			case AssociationClassLinkEditPart.VISUAL_ID:
				return AssociationClass_Edge;
			case AssociationEditPart.VISUAL_ID:
				return Association_Edge;
			case AssociationBranchEditPart.VISUAL_ID:
				return Association_BranchEdge;
			case GeneralizationEditPart.VISUAL_ID:
				return Generalization_Edge;
			case InterfaceRealizationEditPart.VISUAL_ID:
				return InterfaceRealization_Edge;
			case SubstitutionEditPart.VISUAL_ID:
				return Substitution_Edge;
			case RealizationEditPart.VISUAL_ID:
				return Realization_Edge;
			case AbstractionEditPart.VISUAL_ID:
				return Abstraction_Edge;
			case UsageEditPart.VISUAL_ID:
				return Usage_Edge;
			case DependencyEditPart.VISUAL_ID:
				return Dependency_Edge;
			case DependencyBranchEditPart.VISUAL_ID:
				return Dependency_BranchEdge;
			case ElementImportEditPart.VISUAL_ID:
				return ElementImport_Edge;
			case PackageImportEditPart.VISUAL_ID:
				return PackageImport_Edge;
			case PackageMergeEditPart.VISUAL_ID:
				return PackageMerge_Edge;
			case ProfileApplicationEditPart.VISUAL_ID:
				return ProfileApplication_Edge;
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return Comment_AnnotatedElementEdge;
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return Constraint_ConstrainedElementEdge;
			case TemplateBindingEditPart.VISUAL_ID:
				return TemplateBinding_Edge;
			case GeneralizationSetEditPart.VISUAL_ID:
				return GeneralizationSet_Edge;
			case InstanceSpecificationLinkEditPart.VISUAL_ID:
				return InstanceSpecification_Edge;
			case ContainmentLinkEditPart.VISUAL_ID:
				return Element_ContainmentEdge;
			case ConnectorTimeObservationEditPart.VISUAL_ID:
				return TimeObservation_EventEdge;
			case ConnectorDurationObservationEditPart.VISUAL_ID:
				return DurationObservation_EventEdge;
			case InformationFlowEditPart.VISUAL_ID:
				return InformationFlow_Edge;
			case ContextLinkEditPart.VISUAL_ID:
				return Constraint_ContextEdge;
			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static final DiagramElementTypes TYPED_INSTANCE = new DiagramElementTypes(elementTypeImages) {

		/**
		 * @generated
		 */
		@Override
		public boolean isKnownElementType(IElementType elementType) {
			return org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes.getElement(elementTypeAdapter);
		}
	};

	/**
	 * @generated
	 */
	public static boolean isKindOf(IElementType subtype, IElementType supertype) {
		boolean result = subtype == supertype;

		if (!result) {
			IElementType[] supertypes = subtype.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = supertype == supertypes[i];
			}
		}

		return result;
	}
}
