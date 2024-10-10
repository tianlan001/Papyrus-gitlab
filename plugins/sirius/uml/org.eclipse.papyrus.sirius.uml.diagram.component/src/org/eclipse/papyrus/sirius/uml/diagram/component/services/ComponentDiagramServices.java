/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.component.services;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.RepresentationQuerier;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.UMLLabelServices;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.uml.domain.services.create.ElementDomainBasedEdgeCreationChecker;
import org.eclipse.papyrus.uml.domain.services.edges.diagrams.ComponentDomainBasedEdgeContainerProvider;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeList;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services for the Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ComponentDiagramServices extends AbstractDiagramServices {

	private static final String CONNECTOR = "uml::Connector"; //$NON-NLS-1$

	private static final String OWNED_ATTRIBUTE = "ownedAttribute"; //$NON-NLS-1$

	private static final String OWNED_CONNECTOR = "ownedConnector"; //$NON-NLS-1$

	/**
	 * Returns the {@link Component} instances contained in {@code parent}.
	 *
	 * @param parent
	 *            the parent element to retrieve the {@link Component} instances from
	 * @return the {@link Component} instances contained in {@code parent}
	 */
	public Collection<Component> getComponentCandidates(Element parent) {
		Collection<Component> result = Collections.emptyList();
		if (parent instanceof Package) {
			result = ((Package) parent).getPackagedElements().stream()
					.filter(Component.class::isInstance)
					.map(Component.class::cast)
					.collect(Collectors.toList());
		} else if (parent instanceof Component) {
			result = ((Component) parent).getPackagedElements().stream()
					.filter(Component.class::isInstance)
					.map(Component.class::cast)
					.collect(Collectors.toList());
		}
		return result;
	}

	/**
	 * Returns the {@link Port} instances contained in {@code parent}.
	 * <p>
	 * The returned ports are retrieved from the {@link Classifier#getAllAttributes()} reference if the provided
	 * {@code parent} is an instance of {@link Classifier}, or from the {@link TypedElement#getType()}'s
	 * all attributes reference if the provided {@code parent} is a {@link TypedElement}.
	 * </p>
	 *
	 * @param parent
	 *            the parent element to retrieve the {@link Port} instances from
	 * @return the {@link Port} instances contained in {@code parent}
	 */
	public Collection<Port> getPortCandidates(Element parent) {
		Collection<Port> result;
		if (parent instanceof Classifier) {
			result = ((Classifier) parent).getAllAttributes().stream()
					.filter(Port.class::isInstance)
					.map(Port.class::cast)
					.toList();
		} else if (parent instanceof TypedElement) {
			Type parentType = ((TypedElement) parent).getType();
			if (parentType instanceof Classifier) {
				result = ((Classifier) parentType).getAllAttributes().stream()
						.filter(Port.class::isInstance)
						.map(Port.class::cast)
						.toList();
			} else {
				result = Collections.emptyList();
			}
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	/**
	 * Checks if a {@link Component} can be created in {@code parent}.
	 *
	 * @param parent
	 *            the parent element to check
	 * @return {@code true} if a {@link Component} can be created in {@code parent}, {@code false} otherwise
	 *
	 * @see #createComponentCPD(Element, DSemanticDecorator)
	 */
	public boolean canCreateComponentCPD(Element parent) {
		boolean canCreate = false;
		if (parent instanceof Package) {
			canCreate = super.canCreate(parent, UMLPackage.eINSTANCE.getComponent(), UMLPackage.eINSTANCE.getPackage_PackagedElement());
		} else if (parent instanceof Component) {
			canCreate = super.canCreate(parent, UMLPackage.eINSTANCE.getComponent(), UMLPackage.eINSTANCE.getComponent_PackagedElement());
		}
		return canCreate;
	}

	/**
	 * Checks if a {@link Connector} edge can be created between {@code source} and {@code target}.
	 *
	 * @param source
	 *            the source of the connector edge
	 * @param target
	 *            the target of the connector edge
	 * @param sourceView
	 *            the graphical element representing the source of the connector edge
	 * @param targetView
	 *            the graphical element representing the target of the connector edge
	 * @return {@code true} if the connector edge can be created, {@code false} otherwise
	 * @see #createConnectorCPD(EObject, EObject, DSemanticDecorator, DSemanticDecorator)
	 */
	public boolean canCreateConnectorCPD(EObject source, EObject target, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		boolean canCreate = true;

		// check if edge can be semantically created
		RepresentationQuerier representationQuery = new RepresentationQuerier(((DDiagramElement) sourceView).getParentDiagram());
		CheckStatus canCreateStatus = new ElementDomainBasedEdgeCreationChecker().canCreate(source, target, CONNECTOR, OWNED_CONNECTOR, representationQuery, sourceView, targetView);
		canCreate = canCreateStatus.isValid();

		if (canCreate) {
			// check if a container exist
			ComponentDomainBasedEdgeContainerProvider containerProvider = ComponentDomainBasedEdgeContainerProvider.buildDefault(new EditableChecker());
			EClass eClass = UMLHelper.toEClass(CONNECTOR);
			if (eClass != null) {
				EObject newInstance = UMLFactory.eINSTANCE.create(eClass);
				EObject container = containerProvider.getContainer(source, target, newInstance, representationQuery, sourceView, targetView);
				canCreate = container != null;
			}
		}

		return canCreate;
	}

	/**
	 * Creates a {@link Connector} edge between {@code source} and {@code target}.
	 *
	 * @param source
	 *            the source of the connector edge
	 * @param target
	 *            the target of the connector edge
	 * @param sourceView
	 *            the graphical element representing the source of the connector edge
	 * @param targetView
	 *            the graphical element representing the target of the connector edge
	 * @return the created {@link Connector}
	 * @see #canCreateConnectorCPD(EObject, EObject, DSemanticDecorator, DSemanticDecorator)
	 */
	public EObject createConnectorCPD(EObject source, EObject target, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
		return domainBasedEdgeServices.createDomainBasedEdge(source, target, CONNECTOR, OWNED_CONNECTOR, sourceView, targetView, ComponentDomainBasedEdgeContainerProvider.buildDefault(new EditableChecker()));
	}

	/**
	 * Creates a {@link Component} in {@code parent}.
	 *
	 * @param parent
	 *            the semantic parent element where the {@link Component} is added
	 * @param targetView
	 *            the graphical container where the {@link Component} is added
	 * @return the created {@link Component} or {@code null} if the creation failed
	 *
	 * @see #canCreateComponentCPD(Element)
	 */
	public EObject createComponentCPD(Element parent, DSemanticDecorator targetView) {
		EObject component = null;
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		if (parent instanceof Package) {
			component = commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getComponent().getName(), UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), targetView);
		} else if (parent instanceof Component) {
			component = commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getComponent().getName(), UMLPackage.eINSTANCE.getComponent_PackagedElement().getName(), targetView);
		}
		return component;
	}

	/**
	 * Renders the label of the provided {@code element}.
	 * <p>
	 * The label is rendered <i>inline</i> if the {@code view} representing the {@code element} is contained in a list ({@link DNodeList}).
	 * Otherwise it is rendered using the default rendering method.
	 * </p>
	 *
	 * @param element
	 *            the element to render the label from
	 * @param diagram
	 *            the diagram containing the element
	 * @param view
	 *            the view representing the element
	 * @return the label
	 */
	public String renderLabelCPD(Element element, DDiagram diagram, DSemanticDecorator view) {
		String label;
		UMLLabelServices umlLabelServices = new UMLLabelServices();
		if (view.eContainer() instanceof DNodeList) {
			label = umlLabelServices.renderLabelInline(element, diagram);
		} else {
			label = umlLabelServices.renderLabel(element, diagram);
		}
		return label;
	}

	/**
	 * Checks if a {@link Property} can be created in {@code parent}.
	 * <p>
	 * Properties can be created in both {@link Component} (represented as nodes) and {@link Interface} (represented as list elements).
	 * </p>
	 *
	 * @param parent
	 *            the parent element to check
	 * @param elementView
	 *            the graphical element representing the parent to check
	 * @return {@code true} if a {@link Property} can be created in {@code parent}, {@code false} otherwise
	 * @see #createPropertyCPD(EObject, DSemanticDecorator)
	 */
	public boolean canCreatePropertyCPD(EObject parent, DSemanticDecorator parentView) {
		boolean canCreate = false;
		if (parent instanceof Property property && !(parent instanceof Port)) {
			canCreate = property.getType() != null && (property.getType() instanceof Classifier);
		} else {
			if (parent instanceof Interface) {
				if (parentView instanceof DNodeList dNodeList) {
					// Check if the DNodeList can contain mappings for Property.
					// We have to do this check because this method is called from a generic tool,
					// and doesn't know about the allowed mappings.
					canCreate = dNodeList.getActualMapping().getSubNodeMappings().stream()
							.map(NodeMapping::getDomainClass)
							.map(UMLHelper::toEClass)
							.anyMatch(eClass -> eClass.equals(UMLPackage.eINSTANCE.getProperty()));
				}
			} else {
				canCreate = (parent instanceof StructuredClassifier);
			}
		}
		return canCreate;
	}

	/**
	 * Creates a {@link Property} in {@code parent}.
	 *
	 * @param parent
	 *            the semantic parent element where the {@link Property} is added
	 * @param elementView
	 *            the graphical element representing the parent where the {@link Property} is added
	 * @return the created {@link Property} or {@code null} if the creation failed
	 * @see #canCreatePropertyCPD(EObject, DSemanticDecorator)
	 */
	public EObject createPropertyCPD(EObject parent, DSemanticDecorator parentView) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		EObject result = null;
		if (parent instanceof Property property) {
			// Use a String constant for ownedAttribute because it is defined in multiple EClass.
			result = commonDiagramServices.createElement(property.getType(), UMLPackage.eINSTANCE.getProperty().getName(), OWNED_ATTRIBUTE, parentView);
		} else if (parent instanceof Element) {
			result = commonDiagramServices.createElement((Element) parent, UMLPackage.eINSTANCE.getProperty().getName(), OWNED_ATTRIBUTE, parentView);
		}
		return result;
	}
}
