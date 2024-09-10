/*****************************************************************************
 * Copyright (c) 2013, 2016 Atos, CEA LIST and etc., Christian W. Damus, and others.
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
 *  Arthur Daussy (Atos) arthur.daussy@atos.net - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net -  Bug 436947
 *  Christian W. Damus - bug 497865
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.controlmode.profile;

import java.util.Collections;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters;
import org.eclipse.papyrus.infra.services.controlmode.commands.AskUserCommand;
import org.eclipse.papyrus.infra.services.controlmode.participants.IControlCommandApprover;
import org.eclipse.papyrus.infra.services.controlmode.participants.IControlCommandParticipant;
import org.eclipse.papyrus.infra.services.controlmode.participants.IShardModeCommandParticipant;
import org.eclipse.papyrus.infra.services.controlmode.participants.IUncontrolCommandParticipant;
import org.eclipse.papyrus.uml.controlmode.profile.commands.CopyProfileApplicationCommand;
import org.eclipse.papyrus.uml.controlmode.profile.commands.MoveProfileApplicationCommand;
import org.eclipse.papyrus.uml.controlmode.profile.commands.MoveStereotypeApplicationToControlResource;
import org.eclipse.papyrus.uml.controlmode.profile.commands.RemoveDuplicationProfileApplicationCommand;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;

/**
 * Participant to control command handling Uml element.
 * Handle Profile applications on package
 * Handle Stereotype Application on Package
 *
 * @author adaussy
 *
 */
public class UMLProfileControlParticipant implements IControlCommandParticipant,
		IUncontrolCommandParticipant, IShardModeCommandParticipant, IControlCommandApprover {

	/** The Constant PRE_UNCONTROL_COMMAND_LABEL. */
	private static final String PRE_UNCONTROL_COMMAND_LABEL = Messages.UMLProfileControlParticipant_Pre_Uncontrol_Command_Label;

	/** The Constant POST_UNCONTROL_COMMAND_LABEL. */
	private static final String POST_UNCONTROL_COMMAND_LABEL = Messages.UMLProfileControlParticipant_Post_Uncontrol_Command_Label;

	/** The Constant POST_CONTROL_COMMAND_LABEL. */
	private static final String POST_CONTROL_COMMAND_LABEL = Messages.UMLProfileControlParticipant_Post_Control_Command_Label;

	/**
	 * Return the command to copy profile application
	 *
	 * @param request
	 * @return
	 */
	protected IUndoableOperation getCopyProfileApplication(final ControlModeRequest request) {
		return new CopyProfileApplicationCommand(request);
	}

	@Override
	public String getID() {
		return "org.eclipse.papyrus.uml.controlmode.profile.UMLProfileControlParticipant"; //$NON-NLS-1$
	}

	private String getPreControlCommandMessage(Element objectToControl) {
		return Messages.UMLProfileControlParticipant_controlmode_dialog_message;
	}

	/**
	 * Get the command to move stereotype application
	 *
	 * @param request
	 * @return
	 */
	protected IUndoableOperation getMoveStereotypeCommand(final ControlModeRequest request) {
		return new MoveStereotypeApplicationToControlResource(Collections.singletonList(WorkspaceSynchronizer.getFile(request.getTargetObject().eResource())), request);
	}

	@Override
	public ICommand getPostControlCommand(ControlModeRequest request) {
		CompositeCommand cc = new CompositeCommand(POST_CONTROL_COMMAND_LABEL);

		// Do we need to manipulate profile applications? Only if it's a package
		// that is intended to be an independently openable sub-model unit
		boolean handleProfileApplications = !ControlModeRequestParameters.isCreateShard(request)
				&& (request.getTargetObject() instanceof Package);

		// Move profile applications if necessary and possible
		if (handleProfileApplications) {
			cc.compose(getMoveProfileAppliationCommand(request));
		}

		// Move stereotype application
		cc.compose(getMoveStereotypeCommand(request));

		// Copy profile applications if necessary and possible
		if (handleProfileApplications) {
			cc.compose(getCopyProfileApplication(request));
		}

		return cc;
	}

	@Override
	public ICommand getPostUncontrolCommand(ControlModeRequest request) {
		CompositeCommand cc = new CompositeCommand(POST_UNCONTROL_COMMAND_LABEL);

		if (cc.isEmpty()) {
			return null;
		}
		return cc;
	}

	/**
	 * Get the command to move profile applicaiton
	 *
	 * @param request
	 * @return
	 */
	protected ICommand getMoveProfileAppliationCommand(ControlModeRequest request) {
		return new MoveProfileApplicationCommand(request);
	}

	/**
	 * Get the command to remove profile application
	 *
	 * @param request
	 * @return
	 */
	protected ICommand getRemoveProfileApplication(final ControlModeRequest request) {
		return new RemoveDuplicationProfileApplicationCommand(request);
	}

	@Override
	public ICommand getPreControlCommand(ControlModeRequest request) {
		Element elem = (Element) request.getTargetObject();

		// Populate the source resource
		request.setSourceResource(elem.eResource(), UmlModel.UML_FILE_EXTENSION);

		// Don't need to worry about this if we're creating a 'shard' resource, because
		// it cannot be opened independently (that's the whole point of shards)
		if (!ControlModeRequestParameters.isCreateShard(request) && request.isUIAction()
				&& !(elem instanceof org.eclipse.uml2.uml.Package)) {

			return new AskUserCommand(request.getEditingDomain(), getPreControlCommandMessage(elem), getPreControlCommandDialogTitle(elem), true, "org.eclipse.papyrus.controlmode.umlprofiles.participants.UMLProfileParticipant.openstandalonemodeldialog"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public ICommand getPreUncontrolCommand(ControlModeRequest request) {
		// Populate the source resource
		request.setSourceResource(request.getTargetObject().eResource(), UmlModel.UML_FILE_EXTENSION);

		CompositeCommand cc = new CompositeCommand(PRE_UNCONTROL_COMMAND_LABEL);
		// Copy profile application
		if (request.getTargetObject() instanceof Package) {
			cc.compose(getRemoveProfileApplication(request));
		}
		cc.compose(getMoveStereotypeCommand(request));
		return cc;
	}

	@Override
	public ICommand getPostShardModeCommand(ControlModeRequest request) {
		ICommand result;

		if (ControlModeRequestParameters.isCreateShard(request)) {
			// A shard will inherit these
			result = getRemoveProfileApplication(request);
		} else {
			// A sub-model needs redundant profile applications
			result = (ICommand) getCopyProfileApplication(request);
		}

		return result;
	}

	@Override
	public int getPriority() {
		return 100;
	}

	protected String getPreControlCommandDialogTitle(Element elem) {
		return Messages.UMLProfileControlParticipant_controlmode_dialog_title;
	}

	@Override
	public boolean provideControlCommand(ControlModeRequest request) {
		return request.getTargetObject() instanceof Element;
	}

	@Override
	public boolean provideUnControlCommand(ControlModeRequest request) {
		return request.getTargetObject() instanceof Element;
	}

	@Override
	public boolean providesShardModeCommand(ControlModeRequest request) {
		return request.getTargetObject() instanceof org.eclipse.uml2.uml.Package;
	}

	@Override
	public boolean canCreateSubModel(EObject objectToControl) {
		return objectToControl instanceof org.eclipse.uml2.uml.Package;
	}
}
