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

package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityParameterNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityParameterNodeExceptionLabelEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityParameterNodeStreamLabelEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

public class TestActivityParameterNode extends AbstractPapyrusTestCase {

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	public org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Test
	public void testStreamLabel() {
		IGraphicalEditPart activityParameterNodeEP = createChild(ActivityParameterNodeEditPart.VISUAL_ID, (IGraphicalEditPart) getActivityCompartmentEditPart().getParent());
		checkStreamLabel(activityParameterNodeEP);

		ActivityParameterNode activityParameterNode = (ActivityParameterNode) activityParameterNodeEP.resolveSemanticElement();

		TransactionalEditingDomain domain = activityParameterNodeEP.getEditingDomain();
		CreateParameterInActivityParameterNode createParamterCommand = new CreateParameterInActivityParameterNode(domain, activityParameterNode);
		executeOnUIThread(GMFtoGEFCommandWrapper.wrap(createParamterCommand));
		checkStreamLabel(activityParameterNodeEP);

		Parameter parameter = activityParameterNode.getParameter();

		setBooleanValue(domain, UMLPackage.eINSTANCE.getParameter_IsStream(), parameter, Boolean.TRUE);
		checkStreamLabel(activityParameterNodeEP);

		setBooleanValue(domain, UMLPackage.eINSTANCE.getParameter_IsStream(), parameter, Boolean.FALSE);
		checkStreamLabel(activityParameterNodeEP);

		SetValueCommand removeParameter = new SetValueCommand(new SetRequest(domain, activityParameterNode, UMLPackage.eINSTANCE.getActivityParameterNode_Parameter(), null));
		executeOnUIThread(GMFtoGEFCommandWrapper.wrap(removeParameter));
		checkStreamLabel(activityParameterNodeEP);
	}

	@Test
	public void testExceptionLabel() {
		IGraphicalEditPart activityParameterNodeEP = createChild(ActivityParameterNodeEditPart.VISUAL_ID, (IGraphicalEditPart) getActivityCompartmentEditPart().getParent());
		checkExceptionLabel(activityParameterNodeEP);

		ActivityParameterNode activityParameterNode = (ActivityParameterNode) activityParameterNodeEP.resolveSemanticElement();
		TransactionalEditingDomain domain = activityParameterNodeEP.getEditingDomain();
		CreateParameterInActivityParameterNode createParamterCommand = new CreateParameterInActivityParameterNode(domain, activityParameterNode);
		executeOnUIThread(GMFtoGEFCommandWrapper.wrap(createParamterCommand));
		checkExceptionLabel(activityParameterNodeEP);

		Parameter parameter = activityParameterNode.getParameter();

		setBooleanValue(domain, UMLPackage.eINSTANCE.getParameter_IsException(), parameter, Boolean.TRUE);
		checkExceptionLabel(activityParameterNodeEP);

		setBooleanValue(domain, UMLPackage.eINSTANCE.getParameter_IsException(), parameter, Boolean.FALSE);
		checkExceptionLabel(activityParameterNodeEP);

		SetValueCommand removeParameter = new SetValueCommand(new SetRequest(domain, activityParameterNode, UMLPackage.eINSTANCE.getActivityParameterNode_Parameter(), null));
		executeOnUIThread(GMFtoGEFCommandWrapper.wrap(removeParameter));
		checkExceptionLabel(activityParameterNodeEP);
	}

	/**
	 * @param domain
	 * @param parameter
	 */
	private void setBooleanValue(TransactionalEditingDomain domain, EAttribute feature, Parameter parameter, Boolean value) {
		SetValueCommand setStreamValueToTrue = new SetValueCommand(new SetRequest(domain, parameter, feature, value));
		executeOnUIThread(GMFtoGEFCommandWrapper.wrap(setStreamValueToTrue));
	}

	protected void checkStreamLabel(IGraphicalEditPart activityParameterNodeEP) {
		checkLabel(activityParameterNodeEP, ActivityParameterNodeStreamLabelEditPart.VISUAL_ID, "stream");
	}

	protected void checkExceptionLabel(IGraphicalEditPart activityParameterNodeEP) {
		checkLabel(activityParameterNodeEP, ActivityParameterNodeExceptionLabelEditPart.VISUAL_ID, "exception");
	}

	protected void checkLabel(IGraphicalEditPart activityParameterNodeEP, String vid, String name) {
		View activityParameterNodeView = activityParameterNodeEP.getNotationView();
		View streamLabelView = null;
		for (Object child : activityParameterNodeView.getChildren()) {
			View view = (View) child;
			if (view.getType().equals(vid)) {
				streamLabelView = view;
				break;
			}
		}
		assertNotNull("ActivityParameterNode view does not contain " + name + " label view", streamLabelView);

		ActivityParameterNode activityParameterNode = (ActivityParameterNode) activityParameterNodeEP.resolveSemanticElement();
		Parameter parameter = activityParameterNode.getParameter();

		boolean expectedVisibility = parameter != null && parameter.isStream();
		assertEquals("Actual " + name + " label visible value don't equals expected value", expectedVisibility, streamLabelView.isVisible());

		IGraphicalEditPart labelEP = null;
		for (Object child : activityParameterNodeEP.getChildren()) {
			IGraphicalEditPart childEP = (IGraphicalEditPart) child;
			String type = childEP.getNotationView().getType();
			if (type != null && type.equals(vid)) {
				labelEP = childEP;
				break;
			}
		}
		if (expectedVisibility) {
			assertNotNull(name + " label editpart should be visible", labelEP);
		} else {
			assertNull(name + " label editpart should not be visible", labelEP);
		}
	}

	protected DiagramEventBroker getDiagramEventBroker() {
		TransactionalEditingDomain editingDomain = getActivityCompartmentEditPart().getEditingDomain();
		if (editingDomain != null) {
			return DiagramEventBroker.getInstance(editingDomain);
		}
		return null;
	}

	public static class CreateParameterInActivityParameterNode extends AbstractTransactionalCommand {

		private final ActivityParameterNode myActivityParameterNode;

		public CreateParameterInActivityParameterNode(TransactionalEditingDomain domain, ActivityParameterNode node) {
			super(domain, "create parameter", null);
			myActivityParameterNode = node;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			Parameter paramter = (Parameter) UMLFactory.eINSTANCE.create(UMLPackage.eINSTANCE.getParameter());
			myActivityParameterNode.getActivity().eSet(UMLPackage.eINSTANCE.getBehavior_OwnedParameter(), paramter);
			myActivityParameterNode.eSet(UMLPackage.eINSTANCE.getActivityParameterNode_Parameter(), paramter);
			return CommandResult.newOKCommandResult();
		}

	}

	@Override
	protected void execute(final Command command) {
		resetLastOperationFailedState();
		getActivityCompartmentEditPart().getViewer().getEditDomain().getCommandStack().execute(command);
		assertLastOperationSuccessful();
	}
}
