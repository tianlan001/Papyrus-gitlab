/*****************************************************************************
 * Copyright (c) 2013,2018 CEA LIST.
 *
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Port;

/**
 * 
 * Base class for {@link org.eclipse.uml2.uml.Connector} feature testing
 *
 */
public abstract class TestDiagramConnector extends TestChildNode {

	protected void testToManagePortConnection(IElementType container, IElementType port, IElementType connectorType) {
		int expectedGraphicalChildrens = 0;
		IGraphicalEditPart sourceContainer = createContainer(container, expectedGraphicalChildrens++);
		IGraphicalEditPart targetContainer = createContainer(container, expectedGraphicalChildrens++);
		IGraphicalEditPart newTargetContainer = createContainer(container, expectedGraphicalChildrens++);
		IGraphicalEditPart sourcePort = createPort(sourceContainer, port, expectedGraphicalChildrens, 0);
		IGraphicalEditPart targetPort = createPort(targetContainer, port, expectedGraphicalChildrens, 0);
		IGraphicalEditPart newTargetPort = createPort(newTargetContainer, port, expectedGraphicalChildrens, 1);
		IGraphicalEditPart connector = createConnector(sourcePort, targetPort, connectorType);
		assertTrue(connector.resolveSemanticElement() instanceof Connector);
		assertTrue(sourcePort.resolveSemanticElement() instanceof Port);
		assertTrue(targetPort.resolveSemanticElement() instanceof Port);
		assertTrue(newTargetPort.resolveSemanticElement() instanceof Port);
		testPortConnectorReorient((Connector) connector.resolveSemanticElement(), (Port) sourcePort.resolveSemanticElement(), (Port) newTargetPort.resolveSemanticElement(), ReorientRequest.REORIENT_SOURCE);
		undoOnUIThread();
		testPortConnectorReorient((Connector) connector.resolveSemanticElement(), (Port) targetPort.resolveSemanticElement(), (Port) newTargetPort.resolveSemanticElement(), ReorientRequest.REORIENT_TARGET);
	}

	private void testPortConnectorReorient(Connector connector, Port oldEnd, Port newEnd, int direction) {
		ReorientRelationshipRequest req = new ReorientRelationshipRequest(getEditingDomain(), connector, newEnd, oldEnd, direction);
		IElementEditService service = ElementEditServiceUtils.getCommandProvider(connector);
		ICommand cmd = service.getEditCommand(req);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		executeOnUIThread(GMFtoGEFCommandWrapper.wrap(cmd));
	}

	private IGraphicalEditPart createPort(IGraphicalEditPart container, IElementType port, int expectedGraphicalChildrens, int numberSemanticChildren) {
		return (IGraphicalEditPart) testToCreateANode(container, port, expectedGraphicalChildrens, 0, 0, 1, false, null, numberSemanticChildren);
	}

	private IGraphicalEditPart createContainer(IElementType container, int expectedGraphicalChildrens) {
		testToCreateANode(container, expectedGraphicalChildrens, 0, 1, 1, false, null, 0);
		return (IGraphicalEditPart) getContainerEditPart().getChildren().get(expectedGraphicalChildrens);
	}

	protected IGraphicalEditPart createConnector(IGraphicalEditPart source, IGraphicalEditPart target, IElementType connector) {
		Command command = target.getCommand(createConnectionViewRequest(connector, source, target));
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		assertEquals(1, source.getSourceConnections().size());
		assertEquals(1, target.getTargetConnections().size());
		IGraphicalEditPart sourceConnector = (IGraphicalEditPart) source.getSourceConnections().get(0);
		IGraphicalEditPart targetConnector = (IGraphicalEditPart) target.getTargetConnections().get(0);
		assertEquals(sourceConnector, targetConnector);
		return sourceConnector;
	}

	protected CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, getDiagramEditPart().getDiagramPreferencesHint());
		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		source.getCommand(connectionRequest);
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		return connectionRequest;
	}
}
