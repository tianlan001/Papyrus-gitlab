/*****************************************************************************
 * Copyright (c) 2011, 2017 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Amine EL KOUHEN (CEA LIST/INRIA DaRT) amine.el_kouhen@inria.fr
 *	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 515112
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CommonDiagramDragDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.helpers.ComponentLinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.helpers.ConnectorHelper;
import org.eclipse.papyrus.uml.diagram.component.custom.edit.helpers.MultiDependencyHelper;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.AbstractionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.CommentEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ComponentEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConnectorEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConstraintEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceEditPartPCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.InterfaceRealizationEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PropertyPartEditPartCN;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.SubstitutionEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.component.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;

/**
 * This class provides an implementation for specific behavior of Drag and Drop
 * in the Composite Diagram.
 *
 * @since 3.0
 */
public class CustomDiagramDragDropEditPolicy extends CommonDiagramDragDropEditPolicy {
	/**
	 * Default constructor.
	 */
	public CustomDiagramDragDropEditPolicy() {
		super(ComponentLinkMappingHelper.getInstance());
	}

	/**
	 * Gets the droppable element visual id.
	 *
	 * @return the droppable element visual id {@inheritDoc}
	 */
	@Override
	protected Set<String> getDroppableElementVisualId() {
		Set<String> droppableElementsVisualId = new HashSet<>();
		// Class CN
		droppableElementsVisualId.add(ModelEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(PackageEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(ComponentEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(ComponentEditPartPCN.VISUAL_ID);
		droppableElementsVisualId.add(InterfaceEditPartPCN.VISUAL_ID);
		droppableElementsVisualId.add(CommentEditPartPCN.VISUAL_ID);
		droppableElementsVisualId.add(ConstraintEditPartPCN.VISUAL_ID);
		droppableElementsVisualId.add(PropertyPartEditPartCN.VISUAL_ID);
		// TopLevelNodes
		droppableElementsVisualId.add(DependencyNodeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ModelEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PackageEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ComponentEditPart.VISUAL_ID);
		droppableElementsVisualId.add(InterfaceEditPart.VISUAL_ID);
		droppableElementsVisualId.add(CommentEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ConstraintEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PortEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ConnectorEditPart.VISUAL_ID);
		return droppableElementsVisualId;
	}

	/**
	 * Gets the uML element type.
	 *
	 * @param elementID
	 *            the element id
	 * @return the uML element type {@inheritDoc}
	 */
	@Override
	public IElementType getUMLElementType(String elementID) {
		return UMLElementTypes.getElementType(elementID);
	}

	/**
	 * Gets the node visual id.
	 *
	 * @param containerView
	 *            the container view
	 * @param domainElement
	 *            the domain element
	 * @return the node visual id {@inheritDoc}
	 */
	@Override
	public String getNodeVisualID(View containerView, EObject domainElement) {
		return UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
	}

	/**
	 * Gets the link with class visual id.
	 *
	 * @param domainElement
	 *            the domain element
	 * @return the link with class visual id {@inheritDoc}
	 */
	@Override
	public String getLinkWithClassVisualID(EObject domainElement) {
		return UMLVisualIDRegistry.getLinkWithClassVisualID(domainElement);
	}

	/**
	 * Gets the specific drop command.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic element
	 * @param nodeVISUALID
	 *            the node visualid
	 * @param linkVISUALID
	 *            the link visualid
	 * @return the specific drop command {@inheritDoc}
	 */
	@Override
	protected Command getSpecificDropCommand(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {
		if (linkVISUALID != null) {
			// Switch test over linkVisualID
			switch (linkVISUALID) {
			case AbstractionEditPart.VISUAL_ID:
				return dropAbstraction(dropRequest, semanticElement, linkVISUALID);
			case SubstitutionEditPart.VISUAL_ID:
				return dropAsNormalBinaryLink(dropRequest, semanticElement, linkVISUALID);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return dropAsNormalBinaryLink(dropRequest, semanticElement, linkVISUALID);
			case UsageEditPart.VISUAL_ID:
				return dropAsNormalBinaryLink(dropRequest, semanticElement, linkVISUALID);
			case ConnectorEditPart.VISUAL_ID:
				return dropConnector(dropRequest, semanticElement, linkVISUALID);
			}
		}
		if (nodeVISUALID != null) {
			// Switch test over nodeVISUALID
			switch (nodeVISUALID) {
			// Test ChildNode... Start
			case PackageEditPartCN.VISUAL_ID:
			case ModelEditPartCN.VISUAL_ID:
			case ComponentEditPartPCN.VISUAL_ID:
			case InterfaceEditPartPCN.VISUAL_ID:
				return dropChildNode(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
			case ComponentEditPartCN.VISUAL_ID:
				return dropComponentToComponent(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
			// Test ChildNode... End
			// Test TopLevelNode... Start
			case DependencyNodeEditPart.VISUAL_ID:
				return dropDependencyNode(dropRequest, semanticElement, nodeVISUALID);
			case PackageEditPart.VISUAL_ID:
			case ModelEditPart.VISUAL_ID:
			case ComponentEditPart.VISUAL_ID:
			case InterfaceEditPart.VISUAL_ID:
				return dropTopLevelNode(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
			// Test TopLevelNode... End
			case PortEditPart.VISUAL_ID:
				return dropAffixedNode(dropRequest, semanticElement, nodeVISUALID);
			case PropertyPartEditPartCN.VISUAL_ID:
				return dropProperty(dropRequest, (Property) semanticElement, nodeVISUALID);
			case CommentEditPart.VISUAL_ID:
			case CommentEditPartPCN.VISUAL_ID:
				return dropComment(dropRequest, semanticElement, nodeVISUALID);
			case ConstraintEditPart.VISUAL_ID:
			case ConstraintEditPartPCN.VISUAL_ID:
				return dropConstraint(dropRequest, semanticElement, nodeVISUALID);
			}
		}
		return super.getSpecificDropCommand(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
	}

	/**
	 * Returns the drop command for Connector links.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element to drop
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropConnector(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> connectorEnds = ComponentLinkMappingHelper.getInstance().getSource(semanticLink);
		if ((connectorEnds != null) && (connectorEnds.size() == 2)) {
			ConnectorHelper helper = new ConnectorHelper(getEditingDomain());
			return new ICommandProxy(helper.dropConnector((Connector) semanticLink, linkVISUALID, getViewer(), getHost(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView()));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * call the mechanism to drop a binary link without specific type
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element that is the interfaceRealization
	 * @param linkVISUALID
	 *            the visualID of the interfaceRealization
	 * @return the command containing the creation of the view ffor a link
	 */
	protected Command dropAsNormalBinaryLink(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sources = linkmappingHelper.getSource(semanticLink);
		Collection<?> targets = linkmappingHelper.getTarget(semanticLink);
		if (sources.isEmpty() || targets.isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}
		Element source = (Element) sources.toArray()[0];
		Element target = (Element) targets.toArray()[0];
		CompositeCommand cc = new CompositeCommand("Drop As Normal Binary Link");
		dropBinaryLink(cc, source, target, linkVISUALID, dropRequest.getLocation(), semanticLink);
		return new ICommandProxy(cc);
	}

	/**
	 * Drop child node.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic element
	 * @param nodeVISUALID
	 *            the node visualid
	 * @param linkVISUALID
	 *            the link visualid
	 * @return the command
	 */
	private Command dropChildNode(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if (graphicalParentObject instanceof org.eclipse.uml2.uml.Package) {
			return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), semanticElement));
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Drop the component into another component.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic element
	 * @param nodeVISUALID
	 *            the node visualid
	 * @param linkVISUALID
	 *            the link visualid
	 * @return the command
	 */
	private Command dropComponentToComponent(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if (graphicalParentObject instanceof Component) {
			return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), semanticElement));
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Drop dependency node.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic element
	 * @param nodeVISUALID
	 *            the node visualid
	 * @return the command
	 */
	private Command dropDependencyNode(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID) {
		Collection<?> sources = ComponentLinkMappingHelper.getInstance().getSource(semanticElement);
		Collection<?> targets = ComponentLinkMappingHelper.getInstance().getTarget(semanticElement);
		if (sources.size() == 1 && targets.size() == 1) {
			Element source = (Element) sources.toArray()[0];
			Element target = (Element) targets.toArray()[0];
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("Drop Dependency"), source, target, DependencyEditPart.VISUAL_ID, dropRequest.getLocation(), semanticElement));
		}
		if (sources.size() > 1 || targets.size() > 1) {
			MultiDependencyHelper dependencyHelper = new MultiDependencyHelper(getEditingDomain());
			return dependencyHelper.dropMutliDependency((Dependency) semanticElement, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView());
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the command to drop the Comment + the link to attach it to its annotated elements.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visual id
	 * @return the command
	 */
	protected Command dropComment(DropObjectsRequest dropRequest, Element semanticLink, String nodeVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		graphicalParentEditPart.resolveSemanticElement();
		if (CommentEditPart.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropCommentCommand((Comment) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView(), (IHintedType) UMLElementTypes.Comment_Shape,
					(IHintedType) UMLElementTypes.Comment_AnnotatedElementEdge);
		} else if (CommentEditPartPCN.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropCommentCommand((Comment) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView(), (IHintedType) UMLElementTypes.Comment_Shape_CN,
					(IHintedType) UMLElementTypes.Comment_AnnotatedElementEdge);
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the command to drop the Constraint + the link to attach it to its contrainted elements.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visual id
	 * @return the command
	 */
	protected Command dropConstraint(DropObjectsRequest dropRequest, Element semanticLink, String nodeVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		graphicalParentEditPart.resolveSemanticElement();
		if (ConstraintEditPart.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropConstraintCommand((Constraint) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView(), (IHintedType) UMLElementTypes.Constraint_Shape,
					(IHintedType) UMLElementTypes.Constraint_ConstrainedElementEdge);
		} else if (ConstraintEditPartPCN.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropConstraintCommand((Constraint) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView(), (IHintedType) UMLElementTypes.Constraint_Shape_CN,
					(IHintedType) UMLElementTypes.Constraint_ConstrainedElementEdge);
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the drop command for Dependency links.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element to drop
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropDependency(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sourceEnds = ComponentLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targetEnds = ComponentLinkMappingHelper.getInstance().getTarget(semanticLink);
		// Dependency with Unary ends
		if ((sourceEnds != null) && (targetEnds != null) && (sourceEnds.size() == 1) && (targetEnds.size() == 1)) {
			Element source = (Element) sourceEnds.toArray()[0];
			Element target = (Element) targetEnds.toArray()[0];
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("drop Dependency"), source, target, //$NON-NLS-1$
					linkVISUALID, dropRequest.getLocation(), semanticLink));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Drop abstraction.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param linkVISUALID
	 *            the link visualid
	 * @return the command
	 */
	protected Command dropAbstraction(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sourceEnds = ComponentLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targetEnds = ComponentLinkMappingHelper.getInstance().getTarget(semanticLink);
		// Dependency with Unary ends
		if ((sourceEnds != null) && (targetEnds != null) && (sourceEnds.size() == 1) && (targetEnds.size() == 1)) {
			Element source = (Element) sourceEnds.toArray()[0];
			Element target = (Element) targetEnds.toArray()[0];
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("drop Abstraction"), source, target, //$NON-NLS-1$
					linkVISUALID, dropRequest.getLocation(), semanticLink));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Returns the drop command for RoleBinding links.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element to drop
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropRoleBinding(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sourceEnds = ComponentLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targetEnds = ComponentLinkMappingHelper.getInstance().getTarget(semanticLink);
		// Dependency with Unary ends
		if ((sourceEnds != null) && (targetEnds != null) && (sourceEnds.size() == 1) && (targetEnds.size() == 1)) {
			Element source = semanticLink.getOwner();
			Element target = (Element) targetEnds.toArray()[0];
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("drop RoleBinding"), source, target, //$NON-NLS-1$
					linkVISUALID, dropRequest.getLocation(), semanticLink));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Returns the drop command for Property nodes.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropProperty(DropObjectsRequest dropRequest, Property droppedElement, String nodeVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		// Default drop location
		Point location = dropRequest.getLocation().getCopy();
		// Port inherits from Property this case should be excluded and treated
		// separately
		if (!(droppedElement instanceof Port)) {
			if ((graphicalParentObject instanceof Classifier) && (((Classifier) graphicalParentObject).getAllAttributes().contains(droppedElement))) {
				// The graphical parent is the real owner of the dropped
				// property.
				// The dropped property may also be an inherited attribute of
				// the graphical parent.
				return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, location, droppedElement));
			} else if (graphicalParentObject instanceof ConnectableElement) {
				Type type = ((ConnectableElement) graphicalParentObject).getType();
				if ((type != null) && (type instanceof Classifier) && (((Classifier) type).getAllAttributes().contains(droppedElement))) {
					// The graphical parent is a Property typed by a Classifier
					// that owns or inherits the
					// dropped property.
					return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, location, droppedElement));
				}
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * <pre>
	 * This method return a drop command for TopLevelNode.
	 * It returns an {@link org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand} in
	 * case the element is dropped on a canvas referencing a domain element that is not a Package.
	 * </pre>
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic element
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropTopLevelNode(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if (graphicalParentObject instanceof org.eclipse.uml2.uml.Package) {
			return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), semanticElement));
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Avoid dropped element to get orphaned for DND action resulting in a
	 * specific action (not a move).
	 *
	 * @param request
	 *            the request
	 * @return the drag command
	 */
	@Override
	protected Command getDragCommand(ChangeBoundsRequest request) {
		Boolean isSpecificDrag = false;
		Iterator<?> iter = request.getEditParts().iterator();
		EObject graphicalParentObject = ((GraphicalEditPart) getHost()).resolveSemanticElement();
		while ((graphicalParentObject != null) && (iter.hasNext())) {
			EObject droppedObject = null;
			EditPart droppedEditPart = (EditPart) iter.next();
			if (droppedEditPart instanceof GraphicalEditPart) {
				droppedObject = ((GraphicalEditPart) droppedEditPart).resolveSemanticElement();
			}
			isSpecificDrag = isSpecificDropActionExpected((GraphicalEditPart) getHost(), droppedObject);
		}
		if (isSpecificDrag) {
			return null;
		}
		return super.getDragCommand(request);
	}

	/**
	 * Gets the drop command.
	 *
	 * @param request
	 *            the request
	 * @return the drop command
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy#getDropCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	@Override
	protected Command getDropCommand(ChangeBoundsRequest request) {
		Boolean isSpecificDrop = false;
		Iterator<?> iter = request.getEditParts().iterator();
		EObject graphicalParentObject = ((GraphicalEditPart) getHost()).resolveSemanticElement();
		while ((graphicalParentObject != null) && (iter.hasNext())) {
			EObject droppedObject = null;
			EditPart droppedEditPart = (EditPart) iter.next();
			if (droppedEditPart instanceof GraphicalEditPart) {
				droppedObject = ((GraphicalEditPart) droppedEditPart).resolveSemanticElement();
			}
			isSpecificDrop = isSpecificDropActionExpected((GraphicalEditPart) getHost(), droppedObject);
		}
		if (isSpecificDrop) {
			return getDropObjectsCommand(castToDropObjectsRequest(request));
		}
		return super.getDropCommand(request);
	}

	/**
	 * Test if a specific drop action shall is expected.
	 *
	 * @param graphicalParent
	 *            the graphical parent
	 * @param droppedObject
	 *            the dropped object
	 * @return true, if is specific drop action expected
	 */
	protected boolean isSpecificDropActionExpected(GraphicalEditPart graphicalParent, EObject droppedObject) {
		boolean isSpecificDropActionExpected = false;
		EObject graphicalParentObject = graphicalParent.resolveSemanticElement();
		if (graphicalParentObject != null) {
			if (graphicalParentObject instanceof Collaboration) {
				if ((droppedObject instanceof Collaboration) || (droppedObject instanceof Class)) {
					isSpecificDropActionExpected = true;
				}
			} else if (graphicalParentObject instanceof StructuredClassifier) {
				if ((droppedObject instanceof Collaboration) || (droppedObject instanceof Class)) {
					isSpecificDropActionExpected = true;
				}
			} else if (graphicalParentObject instanceof TypedElement) {
				if (droppedObject instanceof Type) {
					isSpecificDropActionExpected = true;
				}
			} else if (graphicalParentObject instanceof CollaborationUse) {
				if (droppedObject instanceof Collaboration) {
					isSpecificDropActionExpected = true;
				}
			}
		}
		return isSpecificDropActionExpected;
	}
}
