/*****************************************************************************
 * Copyright (c) 2009, 2016, 2024 CEA LIST, Christian W. Damus, and others.
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
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Nizar GUEDIDI (CEA LIST) - update getUMLElement()
 *  Christian W. Damus (CEA) - bug 440197
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 393532
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 Stereotype Display
 *  Christian W. Damus - bug 492482
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 583091
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationPreCommitListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.GraphicalEditPolicyEx;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.listenerservice.IPapyrusListener;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.AutomaticNotationEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLVisualChildrenStrategy;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypeCompartmentCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateAppliedStereotypePropertyViewCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.CreateStereotypeLabelCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeMigrationHelper;
import org.eclipse.papyrus.uml.tools.listeners.StereotypeElementListener.StereotypeExtensionNotification;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Specific edit policy for label displaying stereotypes and their properties
 * for representing UML elements.
 */
public abstract class AbstractAppliedStereotypeDisplayEditPolicy extends GraphicalEditPolicyEx implements AutomaticNotationEditPolicy, NotificationListener, IPapyrusListener {

	/**
	 *
	 */
	private static final String VISIBLE = "visible";

	/**
	 * Feature Name for EANnotations
	 */
	private static final String E_ANNOTATIONS = "eAnnotations";

	protected String EMPTY_STRING = "";//$NON-NLS-1$

	/** constant for this edit policy role */
	public static final String STEREOTYPE_LABEL_POLICY = "AppliedStereotypeDisplayEditPolicy";//$NON-NLS-1$

	/** host semantic element */
	protected Element hostSemanticElement;

	/** Helper to manipulate applied Stereotype Display model */
	protected StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();
	protected StereotypeMigrationHelper migrationHelper = StereotypeMigrationHelper.getInstance();



	protected IGraphicalEditPart hostEditPart;

	protected View hostView = null;

	protected List<Stereotype> stereotypeList = Collections.emptyList();

	private Set<EObject> suscribedListeners = new HashSet<>();

	private final NotificationPreCommitListener precommitListener = this::handlePrecommit;

	static {
		// Tell the Canonical Edit Policy not to manage applied-stereotype views
		DefaultUMLVisualChildrenStrategy.registerExcludedViewTypes(StereotypeDisplayConstant.APPLIED_STEREOTYPE_VIEW_TYPES);
	}


	/**
	 * Creates a new AppliedStereotype display edit policy
	 */
	public AbstractAppliedStereotypeDisplayEditPolicy() {
		super();
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void activate() {

		if (getHost() instanceof IGraphicalEditPart) {
			initialisation();
			// subscribe(view);
			subscribe(hostSemanticElement);
			subscribe(hostView);
			// Create and Delete nodes if necessary
			refreshNotationStructure();


		}

	}

	/**
	 * Subscribes to a {@code notifier} for notifications from the
	 * {@link #getDiagramEventBroker() event broker}.
	 *
	 * @param notifier
	 *            a notifier to subscribe to
	 */
	protected void subscribe(EObject notifier) {
		// Subscribe to pre-commit, not post-commit events
		suscribedListeners.add(notifier);
		getDiagramEventBroker().addNotificationListener(notifier, precommitListener);
	}

	/**
	 * Unsubscribes from a {@code notifier} for notifications from the
	 * {@link #getDiagramEventBroker() event broker}.
	 *
	 * @param notifier
	 *            a notifier to unsubscribe from
	 */
	protected void unsubscribe(EObject notifier) {
		// Unsubscribe from pre-commit, not post-commit events
		suscribedListeners.remove(notifier);
		getDiagramEventBroker().removeNotificationListener(notifier, precommitListener);
	}


	/**
	 * Initialize Variables.
	 */
	private void initialisation() {
		hostEditPart = (IGraphicalEditPart) getHost();
		hostSemanticElement = getUMLElement();
		hostView = hostEditPart.getNotationView();
		if (hostSemanticElement != null) {
			stereotypeList = hostSemanticElement.getAppliedStereotypes();
		} else {
			stereotypeList = Collections.emptyList();
		}
	}



	/**
	 * @see org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.GraphicalEditPolicyEx#refresh()
	 *      This method must extend GraphicalEditPolicyEx, in order to call the edit policy refresh method when the EditPart is Refreshed
	 */
	@Override
	public void refresh() {

		initialisation();
		refreshDisplay();
	}

	/**
	 * Refreshes the display for the element controlled by the edit part with
	 * this edit policies
	 */
	public abstract void refreshDisplay();


	/**
	 * React to a transaction pre-commit notification by returning a command that
	 * encapsulates follow-up changes in the applied-stereotype visualizaiton of
	 * my host edit-part.
	 *
	 * @param notification
	 *            some model or notation change notification
	 *
	 * @return a trigger command that may or may not perform some modifications
	 *         to the applied-stereotype notation in consequence
	 */
	private Command handlePrecommit(Notification notification) {
		return new RecordingCommand(((IGraphicalEditPart) getHost()).getEditingDomain()) {

			@Override
			protected void doExecute() {
				notifyChanged(notification);
			}
		};
	}

	/**
	 *
	 * {@inheritedDoc}
	 */
	@Override
	public void notifyChanged(Notification notification) {

		// initialisation
		int eventType = notification.getEventType();
		Node notifier = null;
		if (notification.getNotifier() instanceof Node) {
			notifier = (Node) notification.getNotifier();
		}

		// Case if a stereotype is applied or unapplied
		if (eventType == StereotypeExtensionNotification.STEREOTYPE_APPLIED_TO_ELEMENT) {
			initialisation();
			refreshNotationStructure();

		} else if (eventType == StereotypeExtensionNotification.STEREOTYPE_UNAPPLIED_FROM_ELEMENT) {
			initialisation();
			refreshNotationStructure();
		}

		// Refresh editPart when concerned
		if (isConcerned(notification)) {
			if (helper.isInStereotypeComment(notifier)) {
				refreshCommentContainer();
			} else {
				refreshHost();
			}
		}
	}


	/**
	 * Refresh hostEditPart
	 */
	private void refreshHost() {
		if (null != hostEditPart) {
			hostEditPart.refresh();

		}

	}

	/**
	 * Refresh HostParent EditPart
	 */
	private void refreshCommentContainer() {
		if (null != hostEditPart && null != hostEditPart.getParent()) {
			// In case of Connection, the Comment Container is the container of the source or the target
			if (hostEditPart instanceof ConnectionEditPart) {
				((ConnectionEditPart) hostEditPart).getTarget().getParent().refresh();
				((ConnectionEditPart) hostEditPart).getSource().getParent().refresh();
			} else {
				// otherwise this is just the direct parent.
				hostEditPart.getParent().refresh();
			}
		}

	}

	/**
	 * Defined if the notification concerned the update of the visibility of the node
	 *
	 * @param notification
	 *            The tested notification
	 * @return true if the Notification is about the update of the visibility either through the visible feature or by adding the CSS Force Value with Eannotation.
	 */
	private boolean isConcerned(Notification notification) {
		boolean concerned = false;
		if (helper.isStereotypeView(notification.getNotifier())) {
			if (Notification.ADD == notification.getEventType() && notification.getFeature() instanceof EStructuralFeature) {
				concerned = ((EStructuralFeature) notification.getFeature()).getName().equals(E_ANNOTATIONS);
			} else if (Notification.SET == notification.getEventType() && notification.getFeature() instanceof EStructuralFeature) {
				concerned = ((EStructuralFeature) notification.getFeature()).getName().equals(VISIBLE);
			}
		} else if ((notification.getNotifier() instanceof NamedStyle)) {
			NamedStyle style = (NamedStyle) notification.getNotifier();
			concerned = (StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH.equals(style.getName()) && helper.isStereotypeView(style.eContainer()));

		}


		return concerned;
	}

	/**
	 * Create the Notation Structure according to the UML Model for the Stereotypes
	 */
	public void refreshNotationStructure() {

		if (hostView != null) {

			removeUnappliedStereotypes(hostView);

			if (!stereotypeList.isEmpty()) {
				refreshStereotypeStructure();
			}
		}
	}

	/**
	 * Refresh the NotationStructure of the Node for the StereotypeList
	 *
	 */
	public void refreshStereotypeStructure() {

		// rebuild the structure from the Stereotype List
		if (!stereotypeList.isEmpty()) {
			for (Stereotype stereotype : stereotypeList) {
				refreshStereotypeLabelStructure(stereotype);
				refreshStereotypeBraceStructure(stereotype);
			}
		}
	}

	/**
	 * Refresh Brace Compartment and Properties Structure into notation model.
	 *
	 * @param stereotype
	 *            The stereotype for which the Structure is Refreshed
	 */
	public void refreshStereotypeBraceStructure(Stereotype stereotype) {
		BasicCompartment compartment = helper.getStereotypeBraceCompartment(hostView, stereotype);
		if (compartment == null) { // No Compartment Exists for this Stereotype
			createAppliedStereotypeBraceCompartment(stereotype);
			subscribe(helper.getStereotypeBraceCompartment(hostView, stereotype));

		}

		createAppliedStereotypeBraceProperties(stereotype);
	}

	/**
	 * Refresh The StereotypeLabel notation structure.
	 *
	 * @param stereotype
	 *            The stereotype of which the Label has to be refreshed.
	 */
	public void refreshStereotypeLabelStructure(Stereotype stereotype) {

		DecorationNode label = helper.getStereotypeLabel(hostView, stereotype);
		if (null == label) { // No Label Exist for this Stereotype
			createAppliedStereotypeLabel(stereotype);
			subscribe(helper.getStereotypeLabel(hostView, stereotype));

		}
	}

	/**
	 * Returns the image to be displayed for the applied stereotypes.
	 *
	 * @return the image that represents the first applied stereotype or <code>null</code> if no image has to be displayed
	 */
	public Image stereotypeIconToDisplay() {
		Image icon = null;
		boolean displayIcon = NotationUtils.getBooleanValue(hostView, StereotypeDisplayConstant.DISPLAY_ICON, false);
		if (displayIcon) {
			// retrieve the first stereotype in the list of displayed stereotype
			Stereotype appliedStereotype;
			Iterator<Stereotype> stereotypeIterator = getUMLElement().getAppliedStereotypes().iterator();
			while (stereotypeIterator.hasNext() && null == icon) {
				appliedStereotype = stereotypeIterator.next();
				icon = Activator.getIconElement(getUMLElement(), appliedStereotype, false);
			}

		}
		return icon;
	}

	/**
	 * This method creates a node for the compartment of stereotype if it does not exist.
	 *
	 * @param stereotype
	 *            the stereotype of which the Label is created
	 */
	protected void createAppliedStereotypeLabel(final Stereotype stereotype) {

			final View node = hostEditPart.getNotationView();
			// create only if the Label doesn't exist yet
			if (!helper.isLabelExist(node, stereotype)) {
				// Create the Decoration Node
				executeStereotypeLabelCreation(hostEditPart, stereotype);
			}
		}


	/**
	 * This method creates a node for the compartment of stereotype if it does not exist.
	 *
	 * @param stereotypeApplication
	 *            the stereotype application
	 */
	protected void createAppliedStereotypeBraceCompartment(final Stereotype stereotype) {
			final View node = hostEditPart.getNotationView();
			// doesn't exist already
			if (!helper.isBraceCompartmentExist(node, stereotype)) {
				// Create Compartment
				executeAppliedStereotypeBraceCompartmentCreation(hostEditPart, stereotype);
			}
		}

	/**
	 * In charge of properties view creation
	 *
	 * @param stereotype
	 *            The stereotype of which the Properties should be created
	 */
	protected void createAppliedStereotypeBraceProperties(final Stereotype stereotype) {

			Node compartment = helper.getStereotypeBraceCompartment(hostEditPart.getNotationView(), stereotype);
			if (compartment != null && stereotype != null) {

				EList<Property> properties = stereotype.allAttributes();
				for (Property property : properties) {
					createAppliedStereotypeBraceProperty(compartment, property);
					subscribe(helper.getStereotypePropertyInBrace(hostView, stereotype, property));
				}
			}
		}



	/**
	 * In Charge of PropertyView Creation
	 *
	 * @param compartment
	 *            The Compartment owner of the Property that will be created
	 * @param property
	 *            The UML Property of the View to create
	 */
	protected void createAppliedStereotypeBraceProperty(Node compartment, Property property) {
		// if stereotype is null all property of stereotype has to be removed!
		if (property != null && !property.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
			if (!helper.isBracePropertyExist(compartment, property)) {
					// go through each stereotype property
					executeAppliedStereotypeBracePropertyViewCreation(hostEditPart, compartment, property);
				}
			}
		}

	/**
	 * List on all the existing node contained into the main view.
	 * For each node related to the stereotype structure, check if the related Stereotype is still applied
	 * If no, remove the non relevant node.
	 *
	 * @param view
	 *            The main parent view of the host EditPart
	 */
	protected void removeUnappliedStereotypes(View view) {

		if (null != view) {
			List<Object> children = new ArrayList<>(view.getChildren());
			Iterator<Object> iter = children.iterator();
			while (iter.hasNext()) {
				Object child = iter.next();
				if (helper.isStereotypeView(child)) {
					if (((View) child).getElement() instanceof Stereotype) {
						Stereotype childStereotype = (Stereotype) ((View) child).getElement();
						if (hostSemanticElement != null && !hostSemanticElement.isStereotypeApplied(childStereotype)) {
								executeStereotypeViewRemove(hostEditPart, (View) child);
							}
						}
					}
				}
			}
		}

	/**
	 * The goal of this method is to execute the a command to create a notation node for a compartment of stereotype
	 *
	 * @param editPart
	 *            the editPart owner of the new compartment
	 * @param appliedstereotype
	 *            the stereotype application
	 */
	protected void executeAppliedStereotypeBraceCompartmentCreation(final IGraphicalEditPart editPart, final Stereotype stereotype) {
		CreateAppliedStereotypeCompartmentCommand command = new CreateAppliedStereotypeCompartmentCommand(editPart.getEditingDomain(), editPart.getNotationView(), stereotype, StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE);
		execute(command);
	}


	/**
	 * This method is used to create a notation node for the property of the stereotype.
	 *
	 * @param editPart
	 *            the editPart container
	 * @param compartment
	 * @param stereotypeApplication
	 * @param stereotype
	 *            the stereotype associated to compartment node
	 */
	protected void executeAppliedStereotypeBracePropertyViewCreation(final IGraphicalEditPart editPart, final Node compartment, final Property stereotypeProperty) {
		CreateAppliedStereotypePropertyViewCommand command = new CreateAppliedStereotypePropertyViewCommand(editPart.getEditingDomain(), compartment, stereotypeProperty, StereotypeDisplayConstant.STEREOTYPE_PROPERTY_BRACE_TYPE);
		execute(command);
	}

	/**
	 * The goal of this method is to execute the a command to create a notation node for a stereotype Label.
	 *
	 * @param editPart
	 *            The editPart owner of the new compartment
	 * @param stereotype
	 *            The stereotype related to the Label
	 */
	protected void executeStereotypeLabelCreation(final IGraphicalEditPart editPart, final Stereotype stereotype) {
		CreateStereotypeLabelCommand command = new CreateStereotypeLabelCommand(editPart.getEditingDomain(), editPart.getNotationView(), stereotype);
		execute(command);
	}

	/**
	 * Gets the diagram event broker from the editing domain.
	 *
	 * @return the diagram event broker
	 */
	protected DiagramEventBroker getDiagramEventBroker() {
		TransactionalEditingDomain theEditingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		if (null != theEditingDomain) {
			return DiagramEventBroker.getInstance(theEditingDomain);
		}
		return null;
	}

	/**
	 * Returns the uml element controlled by the host edit part
	 *
	 * @return the uml element controlled by the host edit part
	 */
	protected Element getUMLElement() {
		EObject element = getView().getElement();
		if (element instanceof Element) {
			return (Element) element;
		}
		return null;
	}

	/**
	 * Returns the view controlled by the host edit part
	 *
	 * @return the view controlled by the host edit part
	 */
	protected View getView() {
		return (View) getHost().getModel();
	}

	/**
	 * Execute the command to remove the Stereotype.
	 *
	 * @param editPart
	 *            Edit Part on which the command is executed
	 * @param label
	 *            DecorationNode of the Stereotype Label that has to be removed
	 */
	protected void executeStereotypeViewRemove(final IGraphicalEditPart editPart, final View view) {
		DeleteCommand command = new DeleteCommand(view);
		execute(command);
	}


	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void deactivate() {
		// retrieve the view and the element managed by the edit part
		removeListener();
	}

	/**
	 * Remove Listeners added in this Edit Policy
	 */
	public void removeListener() {

		View view = getView();
		if (null != view) {
			unsubscribe(view);
			if (null != hostSemanticElement) {

				// remove listeners to applied stereotyped
				for (EObject stereotypeApplication : hostSemanticElement.getStereotypeApplications()) {
					unsubscribe(stereotypeApplication);
				}
				// remove notification on element
				unsubscribe(hostSemanticElement);
				// removes the reference to the semantic element
				hostSemanticElement = null;
				unsubscribe(hostView);
				hostView = null;
			}
		}

		// Remove listener related to Brace and Label Views
		if (!stereotypeList.isEmpty()) {
			for (Stereotype stereotype : stereotypeList) {
				// Remove label Listener
				View label = helper.getStereotypeLabel(hostView, stereotype);
				if (null != label) {
					unsubscribe(label);
				}
				// Remove Brace Compartment Listener
				BasicCompartment compartment = helper.getStereotypeBraceCompartment(hostView, stereotype);
				if (null != compartment) {
					unsubscribe(helper.getStereotypeBraceCompartment(hostView, stereotype));
				}
				// Remove Brace Properties Listener
				if (null != compartment && null != stereotype) {
					EList<Property> properties = stereotype.allAttributes();
					for (Property property : properties) {
						unsubscribe(helper.getStereotypePropertyInBrace(hostView, stereotype, property));
					}
				}

			}
		}

		HashSet<EObject> copySuscribedListeners = new HashSet<>(suscribedListeners);
		for (EObject listener : copySuscribedListeners) {
			unsubscribe(listener);
		}
		suscribedListeners.clear();

	}
}
