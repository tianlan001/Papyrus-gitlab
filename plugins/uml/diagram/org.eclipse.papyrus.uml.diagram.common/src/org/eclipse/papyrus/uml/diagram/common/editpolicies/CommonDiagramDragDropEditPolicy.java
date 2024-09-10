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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - refactor common behavior between diagrams
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - add the line 	ViewServiceUtil.forceLoad();
 *  Christian W. Damus (CEA) - bug 430726
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - bug 450341
 *  Christian W. Damus - bug 450944
 *  Christian W. Damus - bug 477384
 *  Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 493430
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Bug 507037
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.internal.editparts.DefaultNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest.ConnectionViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.CommandProxyWithResult;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CommonDeferredCreateConnectionViewCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateViewCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.AbstractDiagramDragDropEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.service.visualtype.VisualTypeService;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ViewServiceUtil;
import org.eclipse.papyrus.infra.gmfdiag.dnd.policy.CustomizableDropEditPolicy;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.uml.diagram.common.commands.DeferredCreateCommand;
import org.eclipse.papyrus.uml.diagram.common.helper.Element2IAdaptableRegistryHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.ILinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.common.listeners.DropTargetListener;
import org.eclipse.papyrus.uml.diagram.common.locator.PortPositionLocator;
import org.eclipse.swt.dnd.DND;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * This class is used to execute the drag and drop from the outline. It can
 * manage the drop of nodes and binary links.
 */
public abstract class CommonDiagramDragDropEditPolicy extends AbstractDiagramDragDropEditPolicy {

	private Element2IAdaptableRegistryHelper myElement2IAdaptableRegistryHelper;



	/** The specified link mapping helper depending on the diagram */
	protected ILinkMappingHelper linkmappingHelper;

	/**
	 * Instantiates a new custom diagram drag drop edit policy.
	 *
	 * @param mappingHelper
	 *            the mapping helper
	 */
	public CommonDiagramDragDropEditPolicy(ILinkMappingHelper mappingHelper) {
		linkmappingHelper = mappingHelper;
	}


	/**
	 * Gets the UML element type for the specified
	 *
	 * @param elementID
	 *            the element id
	 *
	 * @return the uML element type
	 */
	public IElementType getUMLElementType(String elementID) {
		return VisualTypeService.getInstance().getElementType(getContextDiagram(), elementID);
	}

	public String getNodeVisualID(View containerView, EObject domainElement) {
		return VisualTypeService.getInstance().getNodeType(containerView, domainElement);
	}

	public String getLinkWithClassVisualID(EObject domainElement) {
		return VisualTypeService.getInstance().getLinkType(getContextDiagram(), domainElement);
	}

	/**
	 * Gets the notational diagram in which my host edit part is rendering a view.
	 *
	 * @return the contextual diagram
	 */
	protected Diagram getContextDiagram() {
		View view = TypeUtils.as(getHost().getModel(), View.class);
		return (view == null) ? null : view.getDiagram();
	}

	/**
	 * Gets composite command adapters
	 */
	protected Element2IAdaptableRegistryHelper getElement2IAdaptableRegistryHelper() {
		if (myElement2IAdaptableRegistryHelper == null) {
			myElement2IAdaptableRegistryHelper = new Element2IAdaptableRegistryHelper();
		}
		return myElement2IAdaptableRegistryHelper;
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	protected Command createViewsAndArrangeCommand(DropObjectsRequest dropRequest, List viewDescriptors) {
		CreateViewRequest createViewRequest = new CreateViewRequest(viewDescriptors);
		createViewRequest.setLocation(dropRequest.getLocation());
		Command createCommand = getHost().getCommand(createViewRequest);
		return createCommand;
	}

	/**
	 * the method provides command to create the binary link into the diagram.
	 * If the source and the target views do not exist, these views will be
	 * created.
	 *
	 * @param cc
	 *            the composite command that will contain the set of command to
	 *            create the binary link
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
	public CompositeCommand dropBinaryLink(CompositeCommand cc, Element source, Element target, String linkVISUALID, Point absoluteLocation, Element semanticLink) {
		IAdaptable sourceAdapter = findAdapter(cc, source, getLinkSourceDropLocation(absoluteLocation, source, target));
		IAdaptable targetAdapter = findAdapter(cc, target, getLinkTargetDropLocation(absoluteLocation, source, target));
		// descriptor of the link

		SemanticElementAdapter adapter = new SemanticElementAdapter(semanticLink, getUMLElementType(linkVISUALID));
		CreateConnectionViewRequest.ConnectionViewDescriptor linkdescriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(adapter, ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(),
				getDiagramPreferencesHint());

		CommonDeferredCreateConnectionViewCommand aLinkCommand = new CommonDeferredCreateConnectionViewCommand(getEditingDomain(), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(), sourceAdapter, targetAdapter, getViewer(),
				getDiagramPreferencesHint(), linkdescriptor, null);
		aLinkCommand.setElement(semanticLink);
		cc.compose(aLinkCommand);
		return cc;
	}

	/**
	 * This method allows to specify a location for the creation of a node at
	 * the source of a dropped link. Overriding implementations must not modify
	 * the absoluteLocation parameter (use {@link Point#getCopy()})
	 *
	 * @param absoluteLocation
	 *            the request's drop location
	 * @param source
	 *            the source of the dropped link
	 * @param target
	 *            the target of the dropped link
	 * @return the new location for the node
	 */
	protected Point getLinkSourceDropLocation(Point absoluteLocation, Element source, Element target) {
		return absoluteLocation;
	}

	/**
	 * This method allows to specify a location for the creation of a node at
	 * the target of a dropped link. Overriding implementations must not modify
	 * the absoluteLocation parameter (use {@link Point#getCopy()})
	 *
	 * @param absoluteLocation
	 *            the request's drop location
	 * @param source
	 *            the source of the dropped link
	 * @param target
	 *            the target of the dropped link
	 * @return the new location for the node
	 */
	protected Point getLinkTargetDropLocation(Point absoluteLocation, Element source, Element target) {
		if (lookForEditPart(source) == null && lookForEditPart(target) == null) {
			return absoluteLocation.getTranslated(100, 0);
		}
		return absoluteLocation;
	}

	/**
	 * Gets the diagram preferences hint.
	 *
	 * @return the diagram preferences hint
	 */
	protected PreferencesHint getDiagramPreferencesHint() {
		return ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint();
	}

	/**
	 * {@inheritedDoc}.
	 */
	@Override
	public Command getDropObjectsCommand(DropObjectsRequest dropRequest) {
		/*
		 * when it's the first action after the opening of Papyrus, the
		 * viewService is not loaded! see bug 302555
		 *
		 * Duration test for 100000 creations of DropCommand : Here 2 solutions
		 * : - call ViewServiceUtil.forceLoad(); for each drop -> ~2500ms
		 *
		 * - test if the command cc can be executed at the end of the method,
		 * and if not : - call ViewServiceUtil.forceLoad(); - and return
		 * getDropObjectsCommand(getDropObjectsCommand) -> ~4700ms
		 *
		 * - for information : without call ViewServiceUtil.forceLoad(); ->
		 * ~1600ms
		 *
		 * It's better don't test if the command is executable!
		 */
		ViewServiceUtil.forceLoad();

		if (dropRequest.getObjects().size() > 0 && dropRequest.getObjects().get(0) instanceof String) {
			return getDropFileCommand(dropRequest);
		}

		// Create a view request from the drop request and then forward getting
		// the command for that.
		CompositeCommand cc = new CompositeCommand("Drop"); //$NON-NLS-1$
		Iterator<?> iter = dropRequest.getObjects().iterator();

		while (iter.hasNext()) {
			EObject droppedObject = (EObject) iter.next();
			if (droppedObject instanceof Element && getElement2IAdaptableRegistryHelper().findAdapter((Element) droppedObject) != null) {
				continue;
			}
			cc.add(getDropObjectCommand(dropRequest, droppedObject));
		}
		getElement2IAdaptableRegistryHelper().clear();
		return new ICommandProxy(cc);
	}

	protected IUndoableOperation getDropObjectCommand(DropObjectsRequest dropRequest, EObject droppedObject) {
		Point location = dropRequest.getLocation().getCopy();
		IGraphicalEditPart parent = (IGraphicalEditPart) getHost();
		boolean isParentDiagram = getHost().getModel() instanceof Diagram;
		String nodeVISUALID = getNodeVisualID(parent.getNotationView(), droppedObject);
		String linkVISUALID = getLinkWithClassVisualID(droppedObject);
		if (getSpecificDrop().contains(nodeVISUALID) || getSpecificDrop().contains(linkVISUALID)) {
			if (!isParentDiagram && !isDropNonCanvasNodeAllowed(parent, droppedObject)) {
				return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
			}
			Command specificDropCommand = getSpecificDropCommand(dropRequest, (Element) droppedObject, nodeVISUALID, linkVISUALID);
			CompositeCommand cc = new CompositeCommand("Drop command");
			cc.compose(new CommandProxy(specificDropCommand));
			// If ctrl key activate, get the content of element dropped
			if (isCopy(dropRequest)) {
				// Check for ICommandProxy and CompoundCommand the most command type used
				if (specificDropCommand instanceof ICommandProxy) {
					ICommandProxy specificDropCommandProxy = (ICommandProxy) specificDropCommand;
					createDeferredCommandWithCommandResult(droppedObject, cc, specificDropCommandProxy);
				} else if (specificDropCommand instanceof CompoundCommand) {
					CompoundCommand specificDropCompoundCommand = (CompoundCommand) specificDropCommand;
					ICommandProxy cp = getCommandProxyFromCompoundCommand(specificDropCompoundCommand);
					if (cp != null) {
						createDeferredCommandWithCommandResult(droppedObject, cc, cp);
					}
				}
			}
			return cc;
		}

		if ((linkVISUALID == null || linkVISUALID.isEmpty()) && nodeVISUALID != null) {
			// The element to drop is a node
			// Retrieve it's expected graphical parent
			EObject graphicalParent = ((GraphicalEditPart) getHost()).resolveSemanticElement();

			// Restrict the default node creation to the following cases:
			// . Take the containment relationship into consideration
			// . Release the constraint when GraphicalParent is a diagram
			if (isParentDiagram) {
				return getDefaultDropNodeCommand(nodeVISUALID, location, droppedObject, dropRequest);
			} else if (((graphicalParent instanceof Element) && ((Element) graphicalParent).getOwnedElements().contains(droppedObject)) && isDropNonCanvasNodeAllowed(parent, droppedObject)) {
				return getDefaultDropNodeCommand(nodeVISUALID, location, droppedObject, dropRequest);
			}
			return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;

		}
		if (linkVISUALID != null && !linkVISUALID.isEmpty()) {
			Collection<?> sources = linkmappingHelper.getSource((Element) droppedObject);
			Collection<?> targets = linkmappingHelper.getTarget((Element) droppedObject);
			if (sources.size() == 0 || targets.size() == 0) {
				return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
			}
			// binary association
			Element source = (Element) sources.toArray()[0];
			Element target = (Element) targets.toArray()[0];
			CompositeCommand cc = new CompositeCommand("Add Link"); //$NON-NLS-1$
			dropBinaryLink(cc, source, target, linkVISUALID, location, (Element) droppedObject);
			return cc;
		}
		return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
	}

	/**
	 * Is dropping allowed for the non-canvas parent
	 *
	 * @param parent
	 * @param droppedObject
	 * @return true or false
	 */
	protected boolean isDropNonCanvasNodeAllowed(IGraphicalEditPart parent, EObject droppedObject) {
		if (isListCompartmentContainsDroppedObject(parent, droppedObject)) {
			return false;
		}
		if (droppedObject instanceof Property) {
			Property property = (Property) droppedObject;
			Element owner = property.getOwner();
			if (owner instanceof Association && !(owner instanceof AssociationClass)) {
				return false;
			}
		}
		return true;
	}

	private boolean isListCompartmentContainsDroppedObject(IGraphicalEditPart parent, EObject droppedObject) {
		if (false == parent instanceof ListCompartmentEditPart) {
			return false;
		}
		@SuppressWarnings("unchecked")
		List<? extends EditPart> childs = parent.getChildren();
		for (EditPart nextChild : childs) {
			if (!(nextChild instanceof GraphicalEditPart)) {
				continue;
			}
			EObject nextChildSemantic = ((GraphicalEditPart) nextChild).resolveSemanticElement();
			if (nextChildSemantic == droppedObject) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get a command proxy from the compound command
	 *
	 * @param cc
	 *            the compound command
	 * @return the command proxy found or null
	 */
	protected ICommandProxy getCommandProxyFromCompoundCommand(CompoundCommand cc) {
		if (cc != null && cc.getCommands() != null) {
			for (Object command : cc.getCommands()) {
				if (command instanceof ICommandProxy) {
					return (ICommandProxy) command;
				} else if (command instanceof CompoundCommand) {
					getCommandProxyFromCompoundCommand((CompoundCommand) command);
				}
			}
		}
		return null;
	}

	/**
	 * Create deferred command for a command proxy
	 *
	 * @param droppedObject
	 *            the dropped object
	 * @param cc
	 *            the composite command to add the deferred command
	 * @param specificDropCommandProxy
	 *            the specific drop command to get the result
	 */
	protected void createDeferredCommandWithCommandResult(EObject droppedObject, CompositeCommand cc, ICommandProxy specificDropCommandProxy) {
		if (specificDropCommandProxy != null && specificDropCommandProxy.getICommand() != null && specificDropCommandProxy.getICommand().getCommandResult() != null && specificDropCommandProxy.getICommand().getCommandResult().getReturnValue() != null) {
			Object object = specificDropCommandProxy.getICommand().getCommandResult().getReturnValue();
			if (object instanceof CreateViewRequest.ViewDescriptor) {
				cc.compose(createDeferredCreateCommand(droppedObject, (CreateViewRequest.ViewDescriptor) object));
			} else if (object instanceof Collection<?>) {
				for (Object o : (Collection<?>) object) {
					if (o instanceof CreateViewRequest.ViewDescriptor) {
						cc.compose(createDeferredCreateCommand(droppedObject, (CreateViewRequest.ViewDescriptor) o));
					}
				}
			}
		}
	}

	private DeferredCreateCommand createDeferredCreateCommand(EObject droppedObject, CreateViewRequest.ViewDescriptor viewDescriptor) {
		return new DeferredCreateCommand(getEditingDomain(), droppedObject, viewDescriptor, getHost().getViewer());
	}

	/**
	 * Get a new Point translated to relative coordinate
	 *
	 * @param absoluteLocation
	 *            the absolute point
	 * @return the relative point
	 */
	protected Point getTranslatedToRelative(Point absoluteLocation) {
		Point relativeLocation = absoluteLocation.getCopy();
		((GraphicalEditPart) getHost()).getContentPane().translateToRelative(relativeLocation);
		((GraphicalEditPart) getHost()).getContentPane().translateFromParent(relativeLocation);
		relativeLocation.translate(((GraphicalEditPart) getHost()).getContentPane().getClientArea().getLocation().getNegated());
		return relativeLocation;
	}

	/**
	 * This method returns the default drop command for node. It create the view
	 * at the specified location, using the gmf command framework so the
	 * policies are used.
	 *
	 * @param nodeVISUALID
	 *            the node visual identifier
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @param request
	 *            the drop request
	 * @return the creation node command
	 */
	protected ICommand getDefaultDropNodeCommand(String nodeVISUALID, Point absoluteLocation, EObject droppedObject, DropObjectsRequest request) {
		return getDefaultDropNodeCommand(getHost(), nodeVISUALID, absoluteLocation, droppedObject);
	}

	/**
	 * This method returns the default drop command for node. It create the view
	 * at the specified location, using the gmf command framework so the
	 * policies are used.
	 *
	 * @param nodeVISUALID
	 *            the node visual identifier
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @return the creation node command
	 */
	protected ICommand getDefaultDropNodeCommand(String nodeVISUALID, Point absoluteLocation, EObject droppedObject) {
		return getDefaultDropNodeCommand(nodeVISUALID, absoluteLocation, droppedObject, null);
	}

	/**
	 * This method returns the default drop command for node. It create the view
	 * at the specified location, using the gmf command framework so the
	 * policies are used.
	 *
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @return the creation node command
	 */
	protected ICommand getDefaultDropNodeCommand(Point absoluteLocation, EObject droppedObject) {
		return getDefaultDropNodeCommand(getHost(), null, absoluteLocation, droppedObject);
	}

	/**
	 * This method returns the default drop command for node. It create the view
	 * at the specified location, using the gmf command framework so the
	 * policies are used.
	 *
	 * @param hostEP
	 *            The host edit part which will be the parent of the new node
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @return the creation node command
	 */
	protected ICommand getDefaultDropNodeCommand(EditPart hostEP, Point absoluteLocation, EObject droppedObject) {
		return getDefaultDropNodeCommand(hostEP, null, absoluteLocation, droppedObject);
	}

	/**
	 * This method returns the default drop command for node. It create the view
	 * at the specified location, using the gmf command framework so the
	 * policies are used.
	 *
	 * @param hostEP
	 *            The host edit part which will be the parent of the new node
	 * @param semanticHint
	 *            the semantic hint of the view to create
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @return the creation node command
	 */
	protected ICommand getDefaultDropNodeCommand(EditPart hostEP, String semanticHint, Point absoluteLocation, EObject droppedObject) {
		return getDefaultDropNodeCommand(hostEP, semanticHint, absoluteLocation, droppedObject, null);
	}

	/**
	 * This method returns the default drop command for node. It create the view
	 * at the specified location, using the gmf command framework so the
	 * policies are used.
	 * If the ctrl key is pressed, do a command to add content of the new node
	 *
	 * @param hostEP
	 *            The host edit part which will be the parent of the new node
	 * @param semanticHint
	 *            the semantic hint of the view to create
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @param request
	 *            the drop request (use to test ctrl key)
	 * @return the creation node command
	 */
	protected ICommand getDefaultDropNodeCommand(EditPart hostEP, String semanticHint, Point absoluteLocation, EObject droppedObject, DropObjectsRequest request) {
		DiagramEditPartsUtil.findViews(droppedObject, getViewer());

		// only allow one view instance of a single element by diagram
		// if(existingViews.isEmpty()) {
		IAdaptable elementAdapter = new EObjectAdapter(droppedObject);

		ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, semanticHint, ViewUtil.APPEND, true, getDiagramPreferencesHint());
		CreateViewRequest createViewRequest = new CreateViewRequest(descriptor);
		createViewRequest.setLocation(absoluteLocation);

		// "ask" the host for a command associated with the
		// CreateViewRequest
		Command command = hostEP.getCommand(createViewRequest);
		if (isCopy(request) && createViewRequest.getNewObject() instanceof List) {
			for (Object object : (List<?>) createViewRequest.getNewObject()) {
				if (object instanceof IAdaptable) {
					DeferredCreateCommand createCommand2 = new DeferredCreateCommand(getEditingDomain(), droppedObject, (IAdaptable) object, getHost().getViewer());
					command.chain(new ICommandProxy(createCommand2));
				}
			}
		}
		// set the viewdescriptor as result
		// it then can be used as an adaptable to retrieve the View
		ICommand result = new CommandProxyWithResult(command, descriptor);
		if (droppedObject instanceof Element) {
			getElement2IAdaptableRegistryHelper().registerAdapter((Element) droppedObject, (IAdaptable) result.getCommandResult().getReturnValue());
		}
		return result;
	}

	/**
	 * Check if the ctrl key event is activate
	 *
	 * @param dropRequest
	 *            the request which contain the event
	 * @return true if ctrl key is activate, else return false
	 */
	public boolean isCopy(DropObjectsRequest dropRequest) {
		if (dropRequest != null && dropRequest.getExtendedData() != null && dropRequest.getExtendedData().get(DropTargetListener.EVENT_DETAIL) instanceof Integer) {
			int eventDetail = (Integer) dropRequest.getExtendedData().get(DropTargetListener.EVENT_DETAIL);
			if (((eventDetail & DND.DROP_COPY) != 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the editing domain.
	 *
	 * @return the editing domain
	 */
	protected TransactionalEditingDomain getEditingDomain() {
		return ((IGraphicalEditPart) getHost()).getEditingDomain();
	}

	/**
	 * for specific case of drop, the designers has to write the algorithm of
	 * its own drop. for example case of associationClass, multi dependency
	 * etc....
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param nodeVISUALID
	 *            the node visualid
	 * @param linkVISUALID
	 *            the link visualid
	 * @param semanticLink
	 *            the semantic link
	 *
	 * @return the specific drop command
	 */
	protected Command getSpecificDropCommand(DropObjectsRequest dropRequest, Element semanticLink, String nodeVISUALID, String linkVISUALID) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Gets the viewer.
	 *
	 * @return the viewer
	 */
	protected EditPartViewer getViewer() {
		return ((IGraphicalEditPart) getHost()).getViewer();
	}

	/**
	 * Look for editPart from its semantic.
	 *
	 * @param semantic
	 *            the semantic
	 *
	 * @return the edits the part or null if not found
	 */
	protected EditPart lookForEditPart(EObject semantic) {
		Collection<EditPart> editPartSet = getHost().getViewer().getEditPartRegistry().values();
		Iterator<EditPart> editPartIterator = editPartSet.iterator();
		EditPart existedEditPart = null;
		while (editPartIterator.hasNext() && existedEditPart == null) {
			EditPart currentEditPart = editPartIterator.next();

			if (isEditPartTypeAdapted(currentEditPart.getClass(), semantic.eClass()) && semantic.equals(((GraphicalEditPart) currentEditPart).resolveSemanticElement())) {
				existedEditPart = currentEditPart;
			}
		}
		return existedEditPart;
	}

	/**
	 * Check if the edit part type is the best one to represent an object of the
	 * given EClass type
	 *
	 * @param editPartClass
	 *            the type of EditPart which may represent a semantic element
	 * @param eClass
	 *            the EClass type of the represented semantic element
	 * @return true if an edit part of this type should be selected
	 */
	private boolean isEditPartTypeAdapted(Class<? extends EditPart> editPartClass, EClass eClass) {
		if (DefaultNodeEditPart.class.isAssignableFrom(editPartClass)) {
			return false;
		}
		if (DiagramEditPart.class.isAssignableFrom(editPartClass) || CompartmentEditPart.class.isAssignableFrom(editPartClass)) {
			// the edit part is disqualified, as a compartment or a diagram can
			// not be dropped
			return false;
		} else if (GraphicalEditPart.class.isAssignableFrom(editPartClass)) {
			// check the edit part type against advised ones
			return isEditPartTypeSuitableForEClass(editPartClass.asSubclass(GraphicalEditPart.class), eClass);
		} else {
			// only a GraphicalEditPart must be selected
			return false;
		}
	}

	/**
	 * Check if an edit part type correctly represent a semantic element of the
	 * given EClass. Subclasses should implement this method to restrict the
	 * possibilities during drop of a link. If an edit part is not of a suitable
	 * type, returning false will eliminate it to represent the element as a
	 * source or target edit part. This can be used for example to disable label
	 * edit parts, which may represent the same model element as the main node.
	 *
	 * @param editPartClass
	 *            the type of EditPart which must be checked
	 * @param eClass
	 *            the EClass type of the element which the edit part must
	 *            represent
	 * @return the only edit part type which can be selected (return a common
	 *         super type if several edit parts can be chosen)
	 */
	protected boolean isEditPartTypeSuitableForEClass(Class<? extends GraphicalEditPart> editPartClass, EClass eClass) {
		return true;
	}

	/**
	 * Returns The command to drop the {@link Constraint} and the links, if the
	 * constraints elements are on the diagram
	 *
	 * @param comment
	 *            the comment to drop
	 * @param viewer
	 *            the viewer
	 * @param diagramPreferencesHint
	 *            the diagram preference hint
	 * @param location
	 *            the location for the drop
	 * @param containerView
	 *            the container view for the drop
	 * @param commentType
	 *            the IHintedType for the {@link Comment}
	 * @param linkForComment
	 *            the IHintedType for the link which attach the {@link Comment} to the annotated Element
	 * @return The command to drop the {@link Constraint} and the links, if the
	 *         constraints elements are on the diagram
	 */
	protected Command getDropConstraintCommand(Constraint constraint, EditPartViewer viewer, PreferencesHint diagramPreferencesHint, Point location, View containerView, IHintedType commentType, IHintedType linkForComment) {
		/* for further information, see bug 302555 */
		CompositeCommand cc = new CompositeCommand("dropConstraint");
		int nbAnnotated = constraint.getConstrainedElements().size();

		// 0. Obtain list of the annotatedElement
		ArrayList<Element> endToConnect = new ArrayList<>(constraint.getConstrainedElements());
		GraphicalEditPart[] endEditPart = new GraphicalEditPart[nbAnnotated];

		// 1. Look for if each annotated element is on the diagram
		Iterator<Element> iteratorProp = endToConnect.iterator();
		int index = 0;
		while (iteratorProp.hasNext()) {
			Element currentAnnotatedElement = iteratorProp.next();
			endEditPart[index] = (GraphicalEditPart) lookForEditPart(currentAnnotatedElement);
			index += 1;
		}

		// 2. creation of the node Comment
		IAdaptable elementAdapter = new EObjectAdapter(constraint);
		ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, commentType.getSemanticHint(), ViewUtil.APPEND, true, diagramPreferencesHint);
		CreateCommand nodeCreationCommand = new CreateCommand(getEditingDomain(), descriptor, (containerView));
		cc.compose(nodeCreationCommand);

		SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), location);
		cc.compose(setBoundsCommand);

		if (nbAnnotated != 0) {
			IAdaptable sourceEventAdapter = null;
			IAdaptable targetEventAdapter = null;

			// obtain the node figure
			sourceEventAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();

			// used in the creation command of each event
			ConnectionViewDescriptor dashedLineViewDescriptor = new ConnectionViewDescriptor(linkForComment, linkForComment.getSemanticHint(), diagramPreferencesHint);

			// 3. creation of the dashed line between the Comment and the
			// annotated element
			for (GraphicalEditPart current : endEditPart) {
				if (current != null) {
					targetEventAdapter = new SemanticAdapter(null, current.getModel());
					CommonDeferredCreateConnectionViewCommand dashedLineCmd = new CommonDeferredCreateConnectionViewCommand(getEditingDomain(), linkForComment.getSemanticHint(), (sourceEventAdapter), (targetEventAdapter), viewer, diagramPreferencesHint,
							dashedLineViewDescriptor, null);
					// dashedLineCmd.setElement(constraint);
					dashedLineCmd.setElement(null);
					if (dashedLineCmd.canExecute()) {
						cc.compose(dashedLineCmd);
					}
				}
			}

		}
		return new ICommandProxy(cc);
	}

	/**
	 * Returns The command to drop the {@link Comment} and the links, if the
	 * attached elements are on the diagram
	 *
	 * @param comment
	 *            the comment to drop
	 * @param viewer
	 *            the viewer
	 * @param diagramPreferencesHint
	 *            the diagram preference hint
	 * @param location
	 *            the location for the drop
	 * @param containerView
	 *            the container view for the drop
	 * @param commentType
	 *            the IHintedType for the {@link Comment}
	 * @param linkForComment
	 *            the IHintedType for the link which attach the {@link Comment} to the annotated Element
	 * @return The command to drop the {@link Comment} and the link, if the
	 *         attached elements are on the diagram
	 */
	protected Command getDropCommentCommand(Comment comment, EditPartViewer viewer, PreferencesHint diagramPreferencesHint, Point location, View containerView, IHintedType commentType, IHintedType linkForComment) {
		/* for further information, see bug 302555 */
		ViewServiceUtil.forceLoad();
		CompositeCommand cc = new CompositeCommand("dropComment");
		int nbAnnotated = comment.getAnnotatedElements().size();

		// 0. Obtain list of the annotatedElement
		ArrayList<Element> endToConnect = new ArrayList<>(comment.getAnnotatedElements());
		GraphicalEditPart[] endEditPart = new GraphicalEditPart[nbAnnotated];

		// 1. Look for if each annotated element is on the diagram
		Iterator<Element> iteratorProp = endToConnect.iterator();
		int index = 0;
		while (iteratorProp.hasNext()) {
			Element currentAnnotatedElement = iteratorProp.next();
			endEditPart[index] = (GraphicalEditPart) lookForEditPart(currentAnnotatedElement);
			index += 1;
		}

		// 2. creation of the node Comment
		IAdaptable elementAdapter = new EObjectAdapter(comment);
		ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, commentType.getSemanticHint(), ViewUtil.APPEND, true, diagramPreferencesHint);
		CreateCommand nodeCreationCommand = new CreateCommand(getEditingDomain(), descriptor, (containerView));
		cc.compose(nodeCreationCommand);

		SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), location);
		cc.compose(setBoundsCommand);
		// obtain the node figure
		IAdaptable sourceEventAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		IAdaptable targetEventAdapter = null;
		if (nbAnnotated != 0) {

			// used in the creation command of each event
			ConnectionViewDescriptor dashedLineViewDescriptor = new ConnectionViewDescriptor(linkForComment, linkForComment.getSemanticHint(), diagramPreferencesHint);

			// 3. creation of the dashed line between the Constraint and the
			// constrained element
			for (GraphicalEditPart current : endEditPart) {
				if (current != null) {
					targetEventAdapter = new SemanticAdapter(null, current.getModel());
					CommonDeferredCreateConnectionViewCommand dashedLineCmd = new CommonDeferredCreateConnectionViewCommand(getEditingDomain(), linkForComment.getSemanticHint(), (sourceEventAdapter), (targetEventAdapter), viewer, diagramPreferencesHint,
							dashedLineViewDescriptor, null);
					// dashedLineCmd.setElement(comment);
					dashedLineCmd.setElement(null);
					if (dashedLineCmd.canExecute()) {
						cc.compose(dashedLineCmd);
					}
				}
			}

		}
		return new ICommandProxy(cc);
	}

	protected Point getTranslatedLocation(DropObjectsRequest dropRequest) {
		Point location = dropRequest.getLocation().getCopy();
		((GraphicalEditPart) getHost()).getContentPane().translateToRelative(location);
		((GraphicalEditPart) getHost()).getContentPane().translateFromParent(location);
		location.translate(((GraphicalEditPart) getHost()).getContentPane().getClientArea().getLocation().getNegated());
		return location;
	}

	/**
	 * <pre>
	 * Returns the drop command for Affixed nodes (Parameter, Port).
	 * This method uses PortPositionLocator used by both Port and Parameter.
	 * If the dropped element is a Port, the graphical parent can be :
	 * - a Class that owns or inherits the Port
	 * - a Property which type owns or inherits the Port
	 * </pre>
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropAffixedNode(DropObjectsRequest dropRequest, Element droppedElement, String nodeVISUALID) {
		// The dropped element must be a Port or Parameter
		if (!((droppedElement instanceof Port) || (droppedElement instanceof Parameter))) {
			// Log.getInstance().error(new Exception("Incorrect parameter type (droppedElement should be a Port or Parameter)"));
			return UnexecutableCommand.INSTANCE;
		}
		// Manage Element drop in compartment
		Boolean isCompartmentTarget = false; // True if the target is a
												// ShapeCompartmentEditPart
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		// Default drop location
		Point dropLocation = dropRequest.getLocation().getCopy();
		// Detect if the drop target is a compartment
		if (graphicalParentEditPart instanceof ShapeCompartmentEditPart) {
			isCompartmentTarget = true;
			// Replace compartment edit part by its parent EditPart
			graphicalParentEditPart = (GraphicalEditPart) graphicalParentEditPart.getParent();
			// Translate Port expected location according to the compartment
			// location
			Point targetLocation = graphicalParentEditPart.getContentPane().getBounds().getLocation();
			ShapeCompartmentFigure compartmentFigure = (ShapeCompartmentFigure) getHostFigure();
			// Retrieve ViewPort location = the area where compartment children
			// are located
			// Retrieve ViewPort view location = the relative location of the
			// viewed compartment
			// depending on the current scroll bar state
			Viewport compartmentViewPort = compartmentFigure.getScrollPane().getViewport();
			Point compartmentViewPortLocation = compartmentViewPort.getLocation();
			Point compartmentViewPortViewLocation = compartmentViewPort.getViewLocation();
			// Calculate the delta between the targeted element position for
			// drop (the Composite figure)
			// and the View location with eventual scroll bar.
			Point delta = compartmentViewPortLocation.translate(targetLocation.negate());
			delta = delta.translate(compartmentViewPortViewLocation.negate());
			// Translate the requested drop location (relative to parent)
			dropLocation = dropRequest.getLocation().getTranslated(delta);
		}
		// If SideAffixedNodesCreationEditPolicy installed to the parent then delegate calculation of the real drop location to it
		if (!isSideAffixedNodesCreationPolicyInstalled(graphicalParentEditPart)) {
			Point parentLoc = graphicalParentEditPart.getFigure().getBounds().getLocation().getCopy();
			PortPositionLocator locator = new PortPositionLocator(graphicalParentEditPart.getFigure());
			Rectangle proposedBounds = new Rectangle(dropLocation, new Dimension(20, 20));
			proposedBounds = proposedBounds.getTranslated(parentLoc);
			Rectangle preferredBounds = locator.getPreferredLocation(proposedBounds);
			// Convert the calculated preferred bounds as relative to parent location
			Rectangle creationBounds = preferredBounds.getTranslated(parentLoc.getNegated());
			dropLocation = creationBounds.getLocation();
		}
		// Manage Element drop in compartment
		// Create proposed creation bounds and use the locator to find the
		// expected position
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if ((graphicalParentObject instanceof EncapsulatedClassifier) && (((EncapsulatedClassifier) graphicalParentObject).getAllAttributes().contains(droppedElement))) {
			// Drop Port on StructuredClassifier
			if (isCompartmentTarget) {
				return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
			}
			return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));
		} else if (graphicalParentObject instanceof ConnectableElement) {
			// Drop Port on Part
			Type type = ((ConnectableElement) graphicalParentObject).getType();
			if ((type != null) && (type instanceof EncapsulatedClassifier) && (((EncapsulatedClassifier) type).getAllAttributes().contains(droppedElement))) {
				if (isCompartmentTarget) {
					return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
				}
				return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));
			}
		} else if ((graphicalParentObject instanceof Behavior) && (((Behavior) graphicalParentObject).getOwnedParameters().contains(droppedElement))) {
			// Drop Parameter on Behavior
			if (isCompartmentTarget) {
				return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
			}
			return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * <pre>
	 * This method returns the drop command for AffixedNode (Port, Parameter)
	 * in case the node is dropped on a ShapeCompartmentEditPart.
	 * </pre>
	 *
	 * @param nodeVISUALID
	 *            the node visual identifier
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @return a CompositeCommand for Drop
	 */
	protected CompoundCommand getDropAffixedNodeInCompartmentCommand(String nodeVISUALID, Point location, EObject droppedObject) {
		CompoundCommand cc = new CompoundCommand("Drop");
		IAdaptable elementAdapter = new EObjectAdapter(droppedObject);
		ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, ((IHintedType) getUMLElementType(nodeVISUALID)).getSemanticHint(), ViewUtil.APPEND, true, getDiagramPreferencesHint());
		// Create the command targeting host parent (owner of the
		// ShapeCompartmentEditPart)
		CreateViewCommand createCommand = new CreateViewCommand(getEditingDomain(), descriptor, ((View) (getHost().getParent().getModel())));
		cc.add(new ICommandProxy(createCommand));
		SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) createCommand.getCommandResult().getReturnValue(), location);
		cc.add(new ICommandProxy(setBoundsCommand));
		return cc;
	}

	private boolean isSideAffixedNodesCreationPolicyInstalled(IGraphicalEditPart editPart) {
		EditPolicy policy = editPart.getEditPolicy(EditPolicyRoles.CREATION_ROLE);
		if (policy == null && false == policy instanceof CustomizableDropEditPolicy) {
			return false;
		}
		EditPolicy defaultCreationPolicy = ((CustomizableDropEditPolicy) policy).getDefaultCreationPolicy();
		return defaultCreationPolicy != null && defaultCreationPolicy instanceof SideAffixedNodesCreationEditPolicy;
	}

	/**
	 * the method provides command to create the binary link into the diagram.
	 * Find source/target adapter
	 * If the source and the target views do not exist, these views will be
	 * created.
	 *
	 * @see dropBinaryLink(CompositeCommand cc, Element source, Element target, int linkVISUALID
	 *      , Point absoluteLocation, Element semanticLink)
	 *
	 * @param cc
	 *            the composite command that will contain the set of command to
	 *            create the binary link
	 * @param source
	 *            source/target link node
	 * @param point
	 *            source/target node location
	 */
	private IAdaptable findAdapter(CompositeCommand cc, Element source, Point dropLocation) {
		IAdaptable result = getElement2IAdaptableRegistryHelper().findAdapter(source);
		if (result != null) {
			return result;
		}
		GraphicalEditPart editPart = (GraphicalEditPart) lookForEditPart(source);
		if (editPart == null) {
			ICommand createCommand = getDefaultDropNodeCommand(dropLocation, source);
			cc.add(createCommand);
			result = (IAdaptable) createCommand.getCommandResult().getReturnValue();
			return result;
		} else {
			return new SemanticAdapter(null, editPart.getModel());
		}
	}



}
