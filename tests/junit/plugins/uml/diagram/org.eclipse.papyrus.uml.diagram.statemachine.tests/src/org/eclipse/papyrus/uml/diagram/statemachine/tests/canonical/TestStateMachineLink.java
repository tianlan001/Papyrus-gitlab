/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.tests.canonical;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeConnectionTool;
import org.junit.Assert;


public class TestStateMachineLink extends BaseTestCase {

	protected Command createLinkCommand(IGraphicalEditPart start, IGraphicalEditPart end, IElementType type) {
		List<IElementType> types = new LinkedList<IElementType>();
		types.add(type);

		AspectUnspecifiedTypeConnectionTool.CreateAspectUnspecifiedTypeConnectionRequest req = new AspectUnspecifiedTypeConnectionTool(types).new CreateAspectUnspecifiedTypeConnectionRequest(types, false, getDiagramEditPart().getDiagramPreferencesHint());

		req.setSourceEditPart(start);
		req.setType((RequestConstants.REQ_CONNECTION_START));

		Command startCommand = start.getCommand(req);
		Assert.assertNotNull(startCommand);

		req.setStartCommand(startCommand);

		req.setTargetEditPart(end);
		req.setType(RequestConstants.REQ_CONNECTION_END);

		Command endCommand = end.getCommand(req);
		Assert.assertNotNull(endCommand);
		Assert.assertTrue(endCommand.canExecute());
		return endCommand;
	}

	protected void ckeckCannotStartConnectionCommand(IElementType type, String... editPartIDs) {
		List<IElementType> types = Arrays.asList(type);

		for (String vid : editPartIDs) {
			IGraphicalEditPart ep = createChild(vid, getRegionCompartmentEditPart());

			AspectUnspecifiedTypeConnectionTool.CreateAspectUnspecifiedTypeConnectionRequest req = new AspectUnspecifiedTypeConnectionTool(types).new CreateAspectUnspecifiedTypeConnectionRequest(types, false, getDiagramEditPart().getDiagramPreferencesHint());

			req.setSourceEditPart(ep);
			req.setType((RequestConstants.REQ_CONNECTION_START));

			Command startCommand = ep.getCommand(req);
			Assert.assertNull("Element vid=" + vid + " should not provide ability of creation start connection commnad for " + type, startCommand);
		}
	}

	protected ReconnectRequest getReconnectSource(ConnectionEditPart connection, IGraphicalEditPart newSource) {
		return getReconnectRequest(connection, newSource, RequestConstants.REQ_RECONNECT_SOURCE);
	}

	protected ReconnectRequest getReconnectTarget(ConnectionEditPart connection, IGraphicalEditPart newTarget) {
		return getReconnectRequest(connection, newTarget, RequestConstants.REQ_RECONNECT_TARGET);
	}

	private ReconnectRequest getReconnectRequest(ConnectionEditPart connection, IGraphicalEditPart newEnd, String type) {
		ReconnectRequest reconnect = new ReconnectRequest();
		reconnect.setTargetEditPart(newEnd);
		reconnect.setConnectionEditPart((ConnectionEditPart) connection);
		reconnect.setType(type);
		return reconnect;
	}

	protected void doReconnect(ReconnectRequest req) {
		Command reorientCommand = req.getTarget().getCommand(req);
		Assert.assertNotNull(reorientCommand);
		Assert.assertTrue(reorientCommand.canExecute());

		executeOnUIThread(reorientCommand);
	}

	public void checkSourceOfModelLink(ConnectionEditPart connEP, IGraphicalEditPart expectedEndEP, EReference endFeature) {
		EditPart actualEndEP = connEP.getSource();
		checkEndOfModelLink(connEP, actualEndEP, expectedEndEP, endFeature);
	}

	public void checkTargetOfModelLink(ConnectionEditPart connEP, IGraphicalEditPart expectedEndEP, EReference endFeature) {
		EditPart actualEndEP = connEP.getTarget();
		checkEndOfModelLink(connEP, actualEndEP, expectedEndEP, endFeature);
	}

	public void checkEndOfModelLink(ConnectionEditPart connEP, EditPart actualEndEP, IGraphicalEditPart expectedEndEP, EReference endFeature) {
		Assert.assertEquals(expectedEndEP, actualEndEP);

		EObject connection = getLinkElement(connEP);
		Object semanticSource = connection.eGet(endFeature);
		Assert.assertEquals(expectedEndEP.resolveSemanticElement(), semanticSource);
	}

	private EObject getLinkElement(ConnectionEditPart connEP) {
		EObject connection = connEP.resolveSemanticElement();
		Assert.assertNotNull(connection);
		return connection;
	}

	public void checkListFeatureLinkConnection(ConnectionEditPart connEP, EReference feature) {
		EObject parent = ((IGraphicalEditPart) connEP.getSource()).resolveSemanticElement();
		EObject child = ((IGraphicalEditPart) connEP.getTarget()).resolveSemanticElement();

		List<?> children = (List<?>) parent.eGet(feature);
		Assert.assertTrue("FeatureLink " + connEP.getClass().getCanonicalName() + " does not appear semantic representation", children.contains(child));
	}

	public void checkElementFeatureLinkConnection(ConnectionEditPart connEP, EReference feature) {
		EObject parent = ((IGraphicalEditPart) connEP.getSource()).resolveSemanticElement();
		EObject child = ((IGraphicalEditPart) connEP.getTarget()).resolveSemanticElement();

		Object actualChild = parent.eGet(feature);
		Assert.assertEquals("FeatureLink " + connEP.getClass().getCanonicalName() + " does not appear semantic representation", child, actualChild);
	}
}
