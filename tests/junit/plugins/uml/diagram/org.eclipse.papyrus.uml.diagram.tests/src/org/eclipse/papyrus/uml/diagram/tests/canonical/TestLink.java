/*****************************************************************************
 * Copyright (c) 2009, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - bug 440263
 *  Christian W. Damus - bug 459701
 *  Christian W. Damus - bug 476436
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.commands.IHandler;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForActionHandlers;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.junit.utils.DisplayUtils;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;
import org.junit.Rule;


/**
 * The Class TestTopLink to test when this is link . The link is attached to a semantic element that has to be contained by the source and the target.
 * To adapt when source owns the created link
 */
public abstract class TestLink extends AbstractPapyrusTestCase {

	protected static final String THE_LINK_RECONNECT_EXISTS = "The Link exist"; //$NON-NLS-1$

	protected static final String RECONNECTION_TARGET = "Reconnection of target"; //$NON-NLS-1$

	protected static final String LINK_EXISTS_RECONNECTION_ON_TARGET = "Link exist on the target"; //$NON-NLS-1$

	protected static final String THE_LINK_TO_RECONNECT_EXISTS = "The Link exist"; //$NON-NLS-1$

	protected static final String RECONNECTION_SOURCE = "Reconnection of source"; //$NON-NLS-1$

	protected static final String LINK_EXISTS_RECONNECTION_ON_SOURCE = "Link exist on the source"; //$NON-NLS-1$

	protected GraphicalEditPart source = null;

	protected GraphicalEditPart sourcePlayer = null;

	protected GraphicalEditPart target = null;

	protected GraphicalEditPart targetPlayer = null;

	public abstract DiagramUpdater getDiagramUpdater();

	@Rule
	public final AnnotationRule<FixtureEditPartConfigurator> sourceConfigurator = AnnotationRule.create(SourceConfigurator.class);

	@Rule
	public final AnnotationRule<FixtureEditPartConfigurator> targetConfigurator = AnnotationRule.create(TargetConfigurator.class);

	protected int rootSemanticOwnedElements = 5;

	protected int createdEdgesCount = 1;

	protected int createdChildsCount = 0;

	protected int diagramChildrenSize = 5;

	protected int initialEnvironmentChildsCount = 4;

	protected int rootSemanticOwnedElementsBeforeCreatingLink = 4;

	/**
	 * Test view deletion.
	 *
	 * @param type
	 *            the type
	 */
	public void testViewDeletion(IElementType type) {
		// DELETION OF THE VIEW
		assertTrue(VIEW_DELETION + INITIALIZATION_TEST, source.getSourceConnections().size() == 1);
		assertTrue(VIEW_DELETION + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		Request deleteViewRequest = new GroupRequest(RequestConstants.REQ_DELETE);
		Command command = ((ConnectionEditPart) source.getSourceConnections().get(0)).getCommand(deleteViewRequest);
		assertNotNull(VIEW_DELETION + COMMAND_NULL, command);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(VIEW_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertTrue(VIEW_DELETION + TEST_THE_EXECUTION, source.getSourceConnections().size() == 0);
		assertTrue(VIEW_DELETION + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertTrue(VIEW_DELETION + TEST_THE_UNDO, source.getSourceConnections().size() == 1);
		assertTrue(VIEW_DELETION + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertTrue(VIEW_DELETION + TEST_THE_REDO, source.getSourceConnections().size() == 0);
		assertTrue(VIEW_DELETION + TEST_THE_REDO, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
	}

	/**
	 * Retrieves the TransactionalEditingDomain
	 *
	 * @return the editing domain (can be null)
	 */
	@Override
	protected TransactionalEditingDomain getEditingDomain() {
		ServiceUtilsForActionHandlers serviceUtils = ServiceUtilsForActionHandlers.getInstance();
		TransactionalEditingDomain editingDomain = null;
		try {
			editingDomain = serviceUtils.getTransactionalEditingDomain();
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return editingDomain;
	}

	/**
	 * Test destroy.
	 *
	 * @param type
	 *            the type
	 */
	public void testDestroy(IElementType type) {
		// DESTROY SEMANTIC+ VIEW
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, createdEdgesCount, ((Diagram) getRootView()).getEdges().size());
		assertTrue(DESTROY_DELETION + INITIALIZATION_TEST, source.getSourceConnections().size() == 1);
		assertTrue(DESTROY_DELETION + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		Request deleteViewRequest = new EditCommandRequestWrapper(new DestroyElementRequest(false));
		Command command = ((ConnectionEditPart) source.getSourceConnections().get(0)).getCommand(deleteViewRequest);
		assertNotNull(DESTROY_DELETION + COMMAND_NULL, command);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		testEnableForDeleteFromModel();
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertTrue(DESTROY_DELETION + TEST_THE_EXECUTION, ((Diagram) getRootView()).getEdges().size() == 0);
		assertTrue(DESTROY_DELETION + TEST_THE_EXECUTION, source.getSourceConnections().size() == 0);
		assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, 4, getRootSemanticModel().getOwnedElements().size());
		assertTrue(DESTROY_DELETION + TEST_THE_UNDO, diagramEditor.getDiagramEditDomain().getDiagramCommandStack().canUndo() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(DESTROY_DELETION + TEST_THE_UNDO, createdEdgesCount, ((Diagram) getRootView()).getEdges().size());
		assertTrue(DESTROY_DELETION + TEST_THE_UNDO, source.getSourceConnections().size() == 1);
		assertTrue(DESTROY_DELETION + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertTrue(DESTROY_DELETION + INITIALIZATION_TEST, ((Diagram) getRootView()).getEdges().size() == 0);
		assertTrue(DESTROY_DELETION + TEST_THE_REDO, source.getSourceConnections().size() == 0);
		assertTrue(DESTROY_DELETION + TEST_THE_REDO, getRootSemanticModel().getOwnedElements().size() == 4);
	}

	/**
	 * Test drop.
	 *
	 * @param type
	 *            the type
	 */
	public void testDrop(IElementType type) {
		// DROP
		assertEquals(DROP + INITIALIZATION_TEST, 4, getDiagramEditPart().getChildren().size());
		assertTrue(DROP + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		assertTrue(CREATION + INITIALIZATION_TEST, ((Diagram) getRootView()).getEdges().size() == 0);
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		ArrayList<Element> list = new ArrayList<Element>();
		list.add(getRootSemanticModel().getOwnedElements().get(rootSemanticOwnedElements - 1));
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(new Point(20, 20));
		Command command = getDiagramEditPart().getCommand(dropObjectsRequest);
		assertNotNull(DROP + COMMAND_NULL, command);
		assertTrue(DROP + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DROP + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(DROP + TEST_THE_EXECUTION, initialEnvironmentChildsCount + createdChildsCount, getDiagramEditPart().getChildren().size());
		assertTrue(DROP + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		assertEquals(DROP + TEST_THE_EXECUTION, createdEdgesCount, ((Diagram) getRootView()).getEdges().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertTrue(DROP + TEST_THE_UNDO, getDiagramEditPart().getChildren().size() == 4);
		assertTrue(DROP + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		assertTrue(DROP + TEST_THE_UNDO, ((Diagram) getRootView()).getEdges().size() == 0);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(DROP + TEST_THE_REDO, initialEnvironmentChildsCount + createdChildsCount, getDiagramEditPart().getChildren().size());
		assertTrue(DROP + TEST_THE_REDO, getRootSemanticModel().getOwnedElements().size() == rootSemanticOwnedElements);
		assertTrue(DROP + TEST_THE_REDO, ((Diagram) getRootView()).getEdges().size() == createdEdgesCount);
	}

	/**
	 * Test change container.
	 *
	 * @param type
	 *            the type
	 * @param containerType
	 *            the container type
	 */
	public void testChangeContainer(IElementType type, IElementType containerType) {
		// CHANGE CONTAINER
		assertTrue(CHANGE_CONTAINER + INITIALIZATION_TEST, getDiagramEditPart().getChildren().size() == 1);
		assertTrue(CHANGE_CONTAINER + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == 1);
		Request requestcreation = CreateViewRequestFactory.getCreateShapeRequest(containerType, getDiagramEditPart().getDiagramPreferencesHint());
		Command command = getDiagramEditPart().getCommand(requestcreation);
		assertNotNull(CONTAINER_CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertTrue(CONTAINER_CREATION + TEST_THE_EXECUTION, getRootView().getChildren().size() == 2);
		GraphicalEditPart containerEditPart = (GraphicalEditPart) getDiagramEditPart().getChildren().get(1);
		ChangeBoundsRequest changeBoundsRequest = new ChangeBoundsRequest(RequestConstants.REQ_ADD);
		changeBoundsRequest.setEditParts((EditPart) getDiagramEditPart().getChildren().get(0));
		changeBoundsRequest.setLocation(new Point(30, 30));
		ShapeCompartmentEditPart compartment = null;
		int index = 0;
		while (compartment == null && index < containerEditPart.getChildren().size()) {
			if ((containerEditPart.getChildren().get(index)) instanceof ShapeCompartmentEditPart) {
				compartment = (ShapeCompartmentEditPart) (containerEditPart.getChildren().get(index));
			}
			index++;
		}
		assertTrue("Container not found", compartment != null); //$NON-NLS-1$
		command = compartment.getCommand(changeBoundsRequest);
		assertNotNull(CHANGE_CONTAINER, command);
		assertTrue(CHANGE_CONTAINER + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CHANGE_CONTAINER + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertTrue(CHANGE_CONTAINER + TEST_THE_EXECUTION, getRootView().getChildren().size() == 1);
		assertTrue(CHANGE_CONTAINER + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size() == 1);
		assertTrue(CHANGE_CONTAINER + TEST_THE_EXECUTION, compartment.getChildren().size() == 1);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertTrue(CHANGE_CONTAINER + TEST_THE_UNDO, getRootView().getChildren().size() == 2);
		assertTrue(CHANGE_CONTAINER + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size() == 2);
		assertTrue(CHANGE_CONTAINER + TEST_THE_EXECUTION, compartment.getChildren().size() == 0);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertTrue(CHANGE_CONTAINER + TEST_THE_REDO, getRootView().getChildren().size() == 1);
		assertTrue(CHANGE_CONTAINER + TEST_THE_REDO, getRootSemanticModel().getOwnedElements().size() == 1);
		assertTrue(CHANGE_CONTAINER + TEST_THE_EXECUTION, compartment.getChildren().size() == 1);
	}

	/**
	 * Test to create a link.
	 *
	 * @param linkType
	 *            the type
	 */
	public void testToCreateALink(IElementType linkType, String initialName) {
		testCreateLink(linkType, initialName);

		Assert.assertEquals("Diagram updater must detect that node children has been created", 4, getDiagramUpdater().getSemanticChildren(getRootView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link has been created", 1, getDiagramUpdater().getContainedLinks(getRootView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link are incoming", 0, getDiagramUpdater().getIncomingLinks((View) ((Diagram) getRootView()).getEdges().get(0)).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link are outgoing", 0, getDiagramUpdater().getOutgoingLinks((View) ((Diagram) getRootView()).getEdges().get(0)).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no children has ben created in the new element", 0, getDiagramUpdater().getSemanticChildren(((View) ((Diagram) getRootView()).getEdges().get(0))).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link has been created in the new element", 0, getDiagramUpdater().getContainedLinks(((View) ((Diagram) getRootView()).getEdges().get(0))).size()); //$NON-NLS-1$

		Assert.assertEquals("Diagram updater must detect that no link are incoming", 1, getDiagramUpdater().getIncomingLinks(target.getNotationView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link are OutgoingLinks", 0, getDiagramUpdater().getOutgoingLinks(target.getNotationView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link are IncomingLinks", 0, getDiagramUpdater().getIncomingLinks(source.getNotationView()).size()); //$NON-NLS-1$
		Assert.assertEquals("Diagram updater must detect that no link are OutgoingLinks", 1, getDiagramUpdater().getOutgoingLinks(source.getNotationView()).size()); //$NON-NLS-1$
	}

	protected void testCreateLink(IElementType linkType, String initialName) {
		assertEquals(CREATION + INITIALIZATION_TEST, 4, getRootEditPart().getChildren().size());
		assertEquals(CREATION + INITIALIZATION_TEST, rootSemanticOwnedElementsBeforeCreatingLink, getRootSemanticModel().getOwnedElements().size());
		Command command = target.getCommand(createConnectionViewRequest(linkType, source, target));
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(CREATION + INITIALIZATION_TEST, createdEdgesCount, calculateDiagramEdgesCount());
		assertEquals(CREATION + INITIALIZATION_TEST, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		assertEquals(CREATION + INITIALIZATION_TEST, initialEnvironmentChildsCount + createdChildsCount, getRootEditPart().getChildren().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		assertEquals(CREATION + TEST_THE_UNDO, 4, getRootView().getChildren().size());
		assertEquals(CREATION + TEST_THE_UNDO, rootSemanticOwnedElementsBeforeCreatingLink, getRootSemanticModel().getOwnedElements().size());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
		assertEquals(CREATION + TEST_THE_REDO, createdEdgesCount, calculateDiagramEdgesCount());
		assertEquals(CREATION + TEST_THE_REDO, rootSemanticOwnedElements, getRootSemanticModel().getOwnedElements().size());
		ConnectionEditPart linkEditPart = (ConnectionEditPart) getDiagramEditPart().getConnections().get(0);
		testLinkEditPart(linkEditPart, initialName);
	}

	/**
	 * calculate diagram edges count
	 */
	protected int calculateDiagramEdgesCount() {
		return ((Diagram) getDiagramEditPart().getDiagramView()).getEdges().size();
	}

	/**
	 * htis method is used to test the created link editpart
	 *
	 * @param linkEditPart
	 */
	protected void testLinkEditPart(ConnectionEditPart linkEditPart, String initialName) {
		Assert.assertNotNull("the editpart of the link must exist", linkEditPart); //$NON-NLS-1$
		EditPolicy policy = linkEditPart.getEditPolicy(org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideLabelEditPolicy.SHOW_HIDE_LABEL_ROLE);
		Assert.assertNotNull("the link must have an edipolicy that to show or hide label", policy); //$NON-NLS-1$
		Assert.assertTrue("the policy of the link must be an instance of ShowHideLabelEditPolicy", policy instanceof org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideLabelEditPolicy); //$NON-NLS-1$
		// get text aware
		ITextAwareEditPart namedEditPart = null;

		// FIXME we should find here a better way to get the name label => If the name is null or empty, no edit part will match whereas one should
		if (initialName == null || initialName.isEmpty()) {
			return;
		}
		for (Iterator iteratorChildren = linkEditPart.getChildren().iterator(); iteratorChildren.hasNext();) {
			Object children = iteratorChildren.next();
			if (children instanceof ITextAwareEditPart && (((ITextAwareEditPart) children).getEditText() != null) && (!((ITextAwareEditPart) children).getEditText().trim().equals(""))) { //$NON-NLS-1$
				namedEditPart = (ITextAwareEditPart) children;
			}
		}
		if (namedEditPart != null) {
			testNameLabel(namedEditPart, initialName);
		}

	}

	protected void testNameLabel(ITextAwareEditPart namedEditPart, String initialName) {
		Assert.assertTrue("the primary editpart must be the namelabelEditpart", namedEditPart instanceof GraphicalEditPart); //$NON-NLS-1$
		EObject element = ((GraphicalEditPart) namedEditPart).resolveSemanticElement();
		String name = NamedElementUtil.isAutoNamed(element) ? element.eClass().getName() : null;
		if (initialName != null) {
			name = initialName;
		}
		if (name != null) {
			if (name.length() < namedEditPart.getEditText().length()) {
				Assert.assertEquals(" the name must contain the name of the metaclass", name, namedEditPart.getEditText().substring(0, name.length())); //$NON-NLS-1$
			} else {
				// not the same it sure but display the mistake is important
				Assert.assertEquals(" the name must contain the name of the metaclass", name, namedEditPart.getEditText()); //$NON-NLS-1$
			}
		}
	}

	public void installEnvironment(IElementType sourceType, IElementType targetType) {
		assertEquals(CREATION + INITIALIZATION_TEST, 0, getRootEditPart().getChildren().size());
		assertEquals(CREATION + INITIALIZATION_TEST, 0, getRootSemanticModel().getOwnedElements().size());
		// create the source
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(sourceType, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(new Point(100, 100));
		Command command = getRootEditPart().getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute()); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		// create the source player to test reconnect
		requestcreation = CreateViewRequestFactory.getCreateShapeRequest(sourceType, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(new Point(100, 300));
		command = getRootEditPart().getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute()); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		// create the target
		requestcreation = CreateViewRequestFactory.getCreateShapeRequest(targetType, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(new Point(300, 100));
		command = getRootEditPart().getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute()); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		// create the target player to test reconnect
		requestcreation = CreateViewRequestFactory.getCreateShapeRequest(targetType, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(new Point(300, 300));
		command = getRootEditPart().getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute()); //$NON-NLS-1$
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		source = (GraphicalEditPart) getRootEditPart().getChildren().get(0);
		sourcePlayer = (GraphicalEditPart) getRootEditPart().getChildren().get(1);
		target = (GraphicalEditPart) getRootEditPart().getChildren().get(2);
		targetPlayer = (GraphicalEditPart) getRootEditPart().getChildren().get(3);

		performAdditionalEnvironmentConfiguration(sourceType, targetType);
	}

	protected IGraphicalEditPart getRootEditPart() {
		return getDiagramEditPart();
	}

	public CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, ((IGraphicalEditPart) getDiagramEditPart()).getDiagramPreferencesHint());
		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		source.getCommand(connectionRequest);
		// Now, setup the request in preparation to get the
		// connection end
		// command.
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		return connectionRequest;
	}

	public void testTargetReconnectAMultiLink(IElementType type) {
		// target reconnection
		ReconnectRequest reconnectRequest = new ReconnectRequest();
		assertTrue(THE_LINK_RECONNECT_EXISTS, (ConnectionEditPart) target.getTargetConnections().get(0) != null);
		ConnectionEditPart binaryLink = (ConnectionEditPart) target.getTargetConnections().get(0);
		reconnectRequest.setConnectionEditPart(binaryLink);
		reconnectRequest.setTargetEditPart(targetPlayer);
		reconnectRequest.setType(RequestConstants.REQ_RECONNECT_TARGET);
		Command cmd = targetPlayer.getCommand(reconnectRequest);
		assertTrue(RECONNECTION_TARGET + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, cmd.canExecute() == true);
		getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().execute(cmd);
		// EditorUtils.getTransactionalEditingDomain().getCommandStack().execute(cmd);
		assertTrue(RECONNECTION_TARGET + TEST_THE_EXECUTION, ((Diagram) getRootView()).getEdges().size() == 1);
		assertTrue(RECONNECTION_TARGET + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size() == 5);
		assertTrue(LINK_EXISTS_RECONNECTION_ON_TARGET, binaryLink.getTarget().equals(targetPlayer));
		// undo
		getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().undo();
		assertTrue(LINK_EXISTS_RECONNECTION_ON_TARGET + TEST_THE_UNDO, binaryLink.getTarget().equals(target));
		// redo
		getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().redo();
		assertTrue(LINK_EXISTS_RECONNECTION_ON_TARGET + TEST_THE_REDO, binaryLink.getTarget().equals(targetPlayer));
	}

	public void testSourceReconnectAMultiLink(IElementType type) {
		// target reconnection
		ReconnectRequest reconnectRequest = new ReconnectRequest();
		assertTrue(THE_LINK_TO_RECONNECT_EXISTS, (ConnectionEditPart) source.getSourceConnections().get(0) != null);
		ConnectionEditPart branch = (ConnectionEditPart) source.getSourceConnections().get(0);
		reconnectRequest.setConnectionEditPart(branch);
		reconnectRequest.setTargetEditPart(sourcePlayer);
		reconnectRequest.setType(RequestConstants.REQ_RECONNECT_SOURCE);
		Command cmd = sourcePlayer.getCommand(reconnectRequest);
		assertTrue(RECONNECTION_SOURCE + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, cmd.canExecute() == true);
		getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().execute(cmd);
		assertTrue(RECONNECTION_SOURCE + TEST_THE_EXECUTION, ((Diagram) getRootView()).getEdges().size() == 1);
		assertTrue(RECONNECTION_SOURCE + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size() == 5);
		assertTrue(LINK_EXISTS_RECONNECTION_ON_SOURCE + TEST_THE_EXECUTION, branch.getSource().equals(sourcePlayer));
		getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().undo();
		assertTrue(LINK_EXISTS_RECONNECTION_ON_SOURCE + TEST_THE_UNDO, branch.getSource().equals(source));
		getDiagramEditPart().getDiagramEditDomain().getDiagramCommandStack().redo();
		assertTrue(LINK_EXISTS_RECONNECTION_ON_SOURCE + TEST_THE_REDO, branch.getSource().equals(sourcePlayer));
	}

	/**
	 * Test to manage top node.
	 *
	 * @param type
	 *            the type
	 * @param containerType
	 *            the container type
	 */
	public void testToManageLink(IElementType sourceType, IElementType targetType, IElementType linkType, IElementType containerType, boolean allowedOntheSame, String initialName) {
		installEnvironment(sourceType, targetType);
		testToCreateALink(linkType, initialName);
		testDestroy(linkType);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		testViewDeletion(linkType);
		testDrop(linkType);
		testSourceReconnectAMultiLink(linkType);
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
		testTargetReconnectAMultiLink(linkType);
		testToCreateAlinkOnTheSame(linkType, allowedOntheSame);
		testToDropAlinkOnTheSame(linkType, allowedOntheSame);
	}

	/**
	 * Test to manage top node.
	 *
	 * @param type
	 *            the type
	 * @param containerType
	 *            the container type
	 */
	public void testToManageLink(IElementType sourceType, IElementType targetType, IElementType linkType, IElementType containerType, boolean allowedOntheSame) {
		testToManageLink(sourceType, targetType, linkType, containerType, allowedOntheSame, null);
	}

	/**
	 * test the drop of a link where the source and the target are the same objects
	 *
	 * @param linkType
	 * @param allowed
	 */
	protected void testToDropAlinkOnTheSame(IElementType linkType, boolean allowed) {
		// DROP
		if (allowed) {
			assertTrue(DROP + INITIALIZATION_TEST, getDiagramEditPart().getChildren().size() == 4);
			assertTrue(DROP + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == 6);
			assertTrue(CREATION + INITIALIZATION_TEST, ((Diagram) getRootView()).getEdges().size() == 2);
			DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
			ArrayList<Element> list = new ArrayList<Element>();
			list.add(getRootSemanticModel().getOwnedElements().get(5));
			dropObjectsRequest.setObjects(list);
			dropObjectsRequest.setLocation(new Point(20, 20));
			Command command = getDiagramEditPart().getCommand(dropObjectsRequest);
			assertNotNull(DROP + COMMAND_NULL, command);
			assertTrue(DROP + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
			assertTrue(DROP + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
			assertTrue(DROP + TEST_THE_EXECUTION, getDiagramEditPart().getChildren().size() == 4);
			assertTrue(DROP + TEST_THE_EXECUTION, getRootSemanticModel().getOwnedElements().size() == 6);
			assertTrue(DROP + TEST_THE_EXECUTION, ((Diagram) getRootView()).getEdges().size() == 3);
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
			assertTrue(DROP + TEST_THE_UNDO, getDiagramEditPart().getChildren().size() == 4);
			assertTrue(DROP + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size() == 6);
			assertTrue(DROP + TEST_THE_UNDO, ((Diagram) getRootView()).getEdges().size() == 2);
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
			assertTrue(DROP + TEST_THE_REDO, getDiagramEditPart().getChildren().size() == 4);
			assertTrue(DROP + TEST_THE_REDO, getRootSemanticModel().getOwnedElements().size() == 6);
			assertTrue(DROP + TEST_THE_REDO, ((Diagram) getRootView()).getEdges().size() == 3);
		}
	}

	/**
	 * test the creation of a link where the source and the target are the same objects
	 *
	 * @param linkType
	 * @param allowed
	 */
	protected void testToCreateAlinkOnTheSame(IElementType linkType, boolean allowed) {
		assertTrue(CREATION + INITIALIZATION_TEST, getDiagramEditPart().getChildren().size() == 4);
		assertTrue(CREATION + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == 5);
		// Note: Must always ask the target edit-part to create the connection, and in this case the target is 'source'
		Command command = source.getCommand(createConnectionViewRequest(linkType, source, source));
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == allowed);
		if (allowed) {
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
			assertTrue(CREATION + INITIALIZATION_TEST, ((Diagram) getRootView()).getEdges().size() == 2);
			assertTrue(CREATION + INITIALIZATION_TEST, getRootSemanticModel().getOwnedElements().size() == 6);
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().undo();
			assertTrue(CREATION + TEST_THE_UNDO, getRootView().getChildren().size() == 4);
			assertTrue(CREATION + TEST_THE_UNDO, getRootSemanticModel().getOwnedElements().size() == 5);
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().redo();
			assertTrue(CREATION + TEST_THE_REDO, ((Diagram) getRootView()).getEdges().size() == 2);
			assertTrue(CREATION + TEST_THE_REDO, getRootSemanticModel().getOwnedElements().size() == 6);
		}
	}

	/**
	 * test id the handler delete from model is enable
	 */
	protected void testEnableForDeleteFromModel() {
		diagramEditor.setFocus();
		DisplayUtils.flushEventLoop();

		EditPart linkEditPart = (EditPart) source.getSourceConnections().get(0);
		diagramEditor.getSite().getSelectionProvider().setSelection(new StructuredSelection(linkEditPart));
		DisplayUtils.flushEventLoop();

		ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);
		org.eclipse.core.commands.Command cmd = commandService.getCommand("org.eclipse.ui.edit.delete"); //$NON-NLS-1$
		IHandler handler = cmd.getHandler();
		boolean enabled = handler != null && handler.isHandled() && handler.isEnabled();
		Assert.assertTrue("Delete from model handler must be enabled", enabled);
	}

	protected final void performAdditionalEnvironmentConfiguration(IElementType sourceType, IElementType targetType) {
		Command additionalConfig = additionalConfig(null, source, sourceType, true);
		additionalConfig = additionalConfig(additionalConfig, sourcePlayer, sourceType, true);
		additionalConfig = additionalConfig(additionalConfig, target, targetType, false);
		additionalConfig = additionalConfig(additionalConfig, targetPlayer, targetType, false);
		if (additionalConfig != null) {
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(additionalConfig);
		}
	}

	private Command additionalConfig(Command additionalConfig, IGraphicalEditPart editPart, IElementType elementType, boolean isSource) {
		FixtureEditPartConfigurator configurator = isSource ? sourceConfigurator.get() : targetConfigurator.get();
		Command config = (configurator == null) ? null : configurator.configureFixtureEditPart(editPart, elementType, isSource);
		return (additionalConfig == null) ? config : (config == null) ? additionalConfig : additionalConfig.chain(config);
	}

	//
	// Nested types
	//

	/**
	 * A configurator, declare via {@link SourceConfigurator} and/or {@link TargetConfigurator} annotation,
	 * of the source or target (respectively) edit-part on which the test will create links.
	 */
	public interface FixtureEditPartConfigurator {
		/**
		 * Obtains a command, if necessary, to configure the source or target edit-part of the test environment.
		 * 
		 * @param editPart
		 *            the source or target edit-part
		 * @param elementType
		 *            the element-type from which the edit-part was created
		 * @param isSource
		 *            whether the edit-part is the source end (not the target end) of the test environment
		 * 
		 * @return a command to further configure the edit-part, or {@code null} if not needed
		 */
		Command configureFixtureEditPart(IGraphicalEditPart editPart, IElementType elementType, boolean isSource);
	}

	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface SourceConfigurator {
		Class<? extends FixtureEditPartConfigurator> value();
	}

	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface TargetConfigurator {
		Class<? extends FixtureEditPartConfigurator> value();
	}
}
