/*****************************************************************************
 * Copyright (c) 2009, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Adapted code from the class diagram
 *  Christian W. Damus - bug 433206
 *  Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 492893
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.custom.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CommonDeferredCreateConnectionViewCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CommonDiagramDragDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.strategy.paste.ShowConstraintContextLink;
import org.eclipse.papyrus.uml.diagram.common.util.AssociationUtil;
import org.eclipse.papyrus.uml.diagram.common.util.Util;
import org.eclipse.papyrus.uml.diagram.profile.custom.commands.SetStereotypeVisibleOnMetaclassCommand;
import org.eclipse.papyrus.uml.diagram.profile.custom.helper.MultiAssociationHelper;
import org.eclipse.papyrus.uml.diagram.profile.custom.helper.MultiDependencyHelper;
import org.eclipse.papyrus.uml.diagram.profile.custom.helper.ProfileLinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.DependencyNodeEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ExtensionEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassEditPart;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.MetaclassEditPartCN;
import org.eclipse.papyrus.uml.diagram.profile.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.profile.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Type;

/**
 * The Class ClassDiagramDragDropEditPolicy.
 */
public class ProfileDiagramDragDropEditPolicy extends CommonDiagramDragDropEditPolicy {
	/**
	 * Instantiates a new class diagram drag drop edit policy.
	 */
	public ProfileDiagramDragDropEditPolicy() {
		super(ProfileLinkMappingHelper.getInstance());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<String> getDroppableElementVisualId() {
		Set<String> droppableElementsVisualID = new HashSet<>();
		droppableElementsVisualID.add(DependencyNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ElementImportEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ExtensionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(AssociationNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CommentEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CommentEditPartCN.VISUAL_ID);
		droppableElementsVisualID.add(ConstraintEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ConstraintEditPartCN.VISUAL_ID);
		droppableElementsVisualID.add(MetaclassEditPart.VISUAL_ID);
		droppableElementsVisualID.add(MetaclassEditPartCN.VISUAL_ID);
		return droppableElementsVisualID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLinkWithClassVisualID(EObject domainElement) {
		return UMLVisualIDRegistry.getLinkWithClassVisualID(domainElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeVisualID(View containerView, EObject domainElement) {
		return UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IElementType getUMLElementType(String elementID) {
		return UMLElementTypes.getElementType(elementID);
	}

	/**
	 * this method has in charge to create command for create an association if the number of
	 * endtype is superior of 2 a multi association is dropped. if the number of endtype this is
	 * binary association that is dropped.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visualid
	 *
	 * @return the command
	 */
	protected Command dropAssociation(final DropObjectsRequest dropRequest, final Element semanticLink, final String nodeVISUALID) {
		final List<?> endtypes = new ArrayList<>(ProfileLinkMappingHelper.getInstance().getSource(semanticLink));
		if (endtypes.size() == 2) {
			Element source = AssociationUtil.getInitialSourceSecondEnd((Association) semanticLink).getType();
			Element target = AssociationUtil.getInitialTargetFirstEnd((Association) semanticLink).getType();
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("Drop Association"), source, target, AssociationEditPart.VISUAL_ID, dropRequest.getLocation(), semanticLink)); //$NON-NLS-1$
		}
		if (endtypes.size() > 2) {
			MultiAssociationHelper associationHelper = new MultiAssociationHelper(getEditingDomain());
			return associationHelper.dropMultiAssociation((Association) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView());
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * this method send a command to create views to display
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visualid
	 *
	 * @return the command
	 */
	protected Command dropDependency(DropObjectsRequest dropRequest, Element semanticLink, String nodeVISUALID) {
		Collection<?> sources = ProfileLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targets = ProfileLinkMappingHelper.getInstance().getTarget(semanticLink);
		if (sources.size() == 1 && targets.size() == 1) {
			Element source = (Element) sources.toArray()[0];
			Element target = (Element) targets.toArray()[0];
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("Drop Dependency"), source, target, DependencyEditPart.VISUAL_ID, dropRequest.getLocation(), semanticLink)); //$NON-NLS-1$
		}
		if (sources.size() > 1 || targets.size() > 1) {
			MultiDependencyHelper dependencyHelper = new MultiDependencyHelper(getEditingDomain());
			return dependencyHelper.dropMutliDependency((Dependency) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView());
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	protected Command getSpecificDropCommand(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {
		if (linkVISUALID != null) {
			// /!\ Warning the order is important! test on the superclass and AssociationNode is a super class for ExtensionEditPart!
			switch (linkVISUALID) {
			case ElementImportEditPart.VISUAL_ID:
				return dropElementImport(dropRequest, semanticElement, nodeVISUALID);
			case ExtensionEditPart.VISUAL_ID:
				return dropExtension(dropRequest, semanticElement, linkVISUALID);
			}
		}
		if (nodeVISUALID != null) {
			switch (nodeVISUALID) {
			case DependencyNodeEditPart.VISUAL_ID:
				return dropDependency(dropRequest, semanticElement, nodeVISUALID);
			case AssociationNodeEditPart.VISUAL_ID:
				return dropAssociation(dropRequest, semanticElement, nodeVISUALID);
			case CommentEditPart.VISUAL_ID:
			case CommentEditPartCN.VISUAL_ID:
				return dropComment(dropRequest, semanticElement, nodeVISUALID);
			case ConstraintEditPart.VISUAL_ID:
			case ConstraintEditPartCN.VISUAL_ID:
				return dropConstraint(dropRequest, (Constraint) semanticElement, nodeVISUALID);
			case MetaclassEditPart.VISUAL_ID:
			case MetaclassEditPartCN.VISUAL_ID:
				return dropMetaclass(dropRequest, semanticElement, nodeVISUALID);
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the command to drop the metaclass
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the metaclass to drop
	 * @param nodeVISUALID
	 *            the node VISUAL ID
	 * @return
	 *         the command to drop the metaclass
	 */
	protected Command dropMetaclass(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID) {
		// we test if the element to drop is a Class or a Metaclass
		if (Util.isMetaclass((Type) semanticElement)) {
			CompositeCommand cc = new CompositeCommand("Drop"); //$NON-NLS-1$
			ICommand dropCommand = super.getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), semanticElement);
			cc.compose(dropCommand);
			Object returnedValue = dropCommand.getCommandResult().getReturnValue();
			if (returnedValue instanceof ArrayList<?>) {// we look for the view descriptor of the created metaclass
				ViewDescriptor desc = (ViewDescriptor) ((ArrayList<?>) returnedValue).get(0);
				// we set the stereotype to visible
				SetStereotypeVisibleOnMetaclassCommand command = new SetStereotypeVisibleOnMetaclassCommand(getEditingDomain(), "Apply Stereotype", null, semanticElement, desc); //$NON-NLS-1$
				if (command.canExecute()) {
					cc.compose(command);
				}
			}
			return new ICommandProxy(cc);
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Returns the command to drop the Comment + the link to attach it to its annotated elements
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visual id
	 *
	 * @return the command
	 */
	protected Command dropComment(DropObjectsRequest dropRequest, Element semanticLink, String nodeVISUALID) {
		// Test canvas element
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if (!(graphicalParentObject instanceof org.eclipse.uml2.uml.Package)) {
			return UnexecutableCommand.INSTANCE;
		}
		if (CommentEditPart.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropCommentCommand((Comment) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView(), (IHintedType) UMLElementTypes.Comment_Shape,
					(IHintedType) UMLElementTypes.Comment_AnnotatedElementEdge);
		} else if (CommentEditPartCN.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropCommentCommand((Comment) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView(), (IHintedType) UMLElementTypes.Comment_Shape_CN,
					(IHintedType) UMLElementTypes.Comment_AnnotatedElementEdge);
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the command to drop the Constraint + the link to attach it to its contrainted elements
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param constraint
	 *            the dropped constraint
	 * @param nodeVISUALID
	 *            the node visual id
	 *
	 * @return the command
	 */
	protected Command dropConstraint(DropObjectsRequest dropRequest, Constraint constraint, String nodeVISUALID) {
		ICommand dropConstraintCommand = getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), constraint, dropRequest);
		if (constraint.getContext() != null) {
			ShowConstraintContextLink showConstraintContextLink = new ShowConstraintContextLink(getEditingDomain(), (GraphicalEditPart) getHost(), constraint);
			dropConstraintCommand = dropConstraintCommand.compose(showConstraintContextLink);
		}
		return GMFtoGEFCommandWrapper.wrap(dropConstraintCommand);
	}

	/**
	 * Gets the editing domain.
	 *
	 * @return the editing domain
	 */
	@Override
	protected TransactionalEditingDomain getEditingDomain() {
		return ((IGraphicalEditPart) getHost()).getEditingDomain();
	}

	/**
	 *
	 * this method send a command to create views to display to drop an extension link
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param linkVISUALID
	 *            hte link visualid
	 * @return
	 */
	protected Command dropExtension(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sources = ProfileLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targets = ProfileLinkMappingHelper.getInstance().getTarget(semanticLink);
		Element source = (Element) sources.toArray()[0];
		Element target = (Element) targets.toArray()[0];
		if (sources.size() != 1 && targets.size() != 1) {
			return UnexecutableCommand.INSTANCE;
		}
		return new ICommandProxy(dropBinaryLinkExtension(new CompositeCommand("drop Extension"), source, target, ExtensionEditPart.VISUAL_ID, dropRequest.getLocation(), semanticLink)); //$NON-NLS-1$
	}

	/**
	 * the drop is possible only if the container of the dropped element is the same that the host!!!
	 *
	 * @param dropRequest
	 * @param semanticLink
	 * @param linkVISUALID
	 * @return
	 */
	protected Command dropElementImport(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		if (semanticLink instanceof ElementImport) {
			if (((ElementImport) semanticLink).getImportedElement() instanceof Class) {
				Class metaclass = (Class) ((ElementImport) semanticLink).getImportedElement();
				/**
				 * get the container for the element
				 */
				EObject container = semanticLink.eContainer();
				/**
				 * get the view of the container
				 */
				List<View> containerView = DiagramEditPartsUtil.getEObjectViews(container);
				/**
				 * get the host's model
				 */
				CompositeCommand cc = new CompositeCommand("Drop"); //$NON-NLS-1$
				IAdaptable elementAdapter = new EObjectAdapter(metaclass);
				ViewDescriptor descriptor;
				// we create the view command
				if (containerView.get(0).equals(getHost().getModel())) {// we are on the diagram
					descriptor = new ViewDescriptor(elementAdapter, Node.class, ((IHintedType) getUMLElementType(MetaclassEditPart.VISUAL_ID)).getSemanticHint(), ViewUtil.APPEND, true, getDiagramPreferencesHint());
				} else {
					descriptor = new ViewDescriptor(elementAdapter, Node.class, ((IHintedType) getUMLElementType(MetaclassEditPartCN.VISUAL_ID)).getSemanticHint(), ViewUtil.APPEND, true, getDiagramPreferencesHint());
				}
				CreateCommand createCommand = new CreateCommand(getEditingDomain(), descriptor, ((View) (getHost().getModel())));
				cc.compose(createCommand);
				SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) createCommand.getCommandResult().getReturnValue(), dropRequest.getLocation()); //$NON-NLS-1$
				cc.compose(setBoundsCommand);
				/*
				 * we set the stereotype <<metaclass>> visible
				 * we can not call AppliedStereotypeHelper.getAddAppliedStereotypeCommand here,
				 * because createCommand.getResult( ((ViewDescriptor)createCommand.getCommandResult().getReturnValue()).getAdapter(View.class) return
				 * null :
				 * the view is not yet created!!!
				 */
				if (createCommand.canExecute()) {
					SetStereotypeVisibleOnMetaclassCommand stereotypeCommand = new SetStereotypeVisibleOnMetaclassCommand(getEditingDomain(), "Set Stereotype Visible", null, semanticLink, (ViewDescriptor) createCommand.getCommandResult().getReturnValue()); //$NON-NLS-1$
					if (stereotypeCommand.canExecute()) {
						cc.add(stereotypeCommand);
					}
				}
				return new ICommandProxy(cc);
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * this method is the equivalent to dropBinaryLink, except it uses lookForEditPartMetaclass
	 *
	 * the method provides command to create the binary link into the diagram. If the source and the
	 * target views do not exist, these views will be created.
	 *
	 * @param cc
	 *            the composite command that will contain the set of command to create the binary
	 *            link
	 * @param source
	 *            the source the element source of the link
	 * @param target
	 *            the target the element target of the link
	 * @param linkVISUALID
	 *            the link VISUALID used to create the view
	 * @param location
	 *            the location the location where the view will be be created
	 * @param semanticLink
	 *            the semantic link that will be attached to the view
	 *
	 * @return the composite command
	 */
	CompositeCommand dropBinaryLinkExtension(CompositeCommand cc, Element source, Element target, String linkVISUALID, Point location, Element semanticLink) {
		// look for editpart
		GraphicalEditPart sourceEditPart = (GraphicalEditPart) lookForEditPart(source);
		GraphicalEditPart targetEditPart = (GraphicalEditPart) lookForEditPart(target);
		// descriptor of the link
		CreateConnectionViewRequest.ConnectionViewDescriptor linkdescriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(getUMLElementType(linkVISUALID), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(),
				getDiagramPreferencesHint());
		IAdaptable sourceAdapter = null;
		IAdaptable targetAdapter = null;
		if (sourceEditPart == null) {
			// creation of the node
			ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(source), Node.class, null, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
			// get the command and execute it.
			CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, ((View) getHost().getModel()));
			cc.compose(nodeCreationCommand);
			SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y + 100)); //$NON-NLS-1$
			cc.compose(setBoundsCommand);
			sourceAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			sourceAdapter = new SemanticAdapter(null, sourceEditPart.getModel());
		}
		if (targetEditPart == null) {
			// creation of the node
			ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(target), Node.class, null, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
			// get the command and execute it.
			CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, ((View) getHost().getModel()));
			cc.compose(nodeCreationCommand);
			SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y - 100)); //$NON-NLS-1$
			cc.compose(setBoundsCommand);
			targetAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			targetAdapter = new SemanticAdapter(null, targetEditPart.getModel());
		}
		CommonDeferredCreateConnectionViewCommand aLinkCommand = new CommonDeferredCreateConnectionViewCommand(getEditingDomain(), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(), sourceAdapter, targetAdapter, getViewer(),
				getDiagramPreferencesHint(), linkdescriptor, null);
		aLinkCommand.setElement(semanticLink);
		cc.compose(aLinkCommand);
		return cc;
	}
}
