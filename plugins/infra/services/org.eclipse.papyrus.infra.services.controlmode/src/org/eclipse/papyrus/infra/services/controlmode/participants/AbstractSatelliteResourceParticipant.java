/*******************************************************************************
 * Copyright (c) 2013 Atos.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Arthur Daussy <a href="mailto:arthur.daussy@atos.net"> - initial API and implementation
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.participants;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.commands.CreateControlResource;
import org.eclipse.papyrus.infra.services.controlmode.commands.RemoveControlResourceCommand;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;

/**
 * Abstract implementation for {@link IControlCommandParticipant} and {@link IUncontrolCommandParticipant} for satellite resources such as notation,
 * di.
 * NOTE : This abstract is not used for now
 *
 * @author adaussy
 *
 */
public abstract class AbstractSatelliteResourceParticipant implements IControlCommandParticipant, IUncontrolCommandParticipant {

	/** The Constant POST_CONTROL_COMMAND_TITLE. */
	private static final String POST_CONTROL_COMMAND_TITLE = Messages.getString("AbstractSatelliteResourceParticipant.control.post.label"); //$NON-NLS-1$

	/** The Constant PRE_CONTROL_COMMAND_TITLE. */
	private static final String PRE_CONTROL_COMMAND_TITLE = Messages.getString("AbstractSatelliteResourceParticipant.control.pre.label"); //$NON-NLS-1$

	/** The Constant POST_UNCONTROL_COMMAND_TITLE. */
	private static final String POST_UNCONTROL_COMMAND_TITLE = Messages.getString("AbstractSatelliteResourceParticipant.uncontrol.post.label"); //$NON-NLS-1$

	/** The Constant PRE_UNCONTROL_COMMAND_TITLE. */
	private static final String PRE_UNCONTROL_COMMAND_TITLE = Messages.getString("AbstractSatelliteResourceParticipant.uncontrol.pre.label"); //$NON-NLS-1$

	/** The Constant COMMAND_LABEL. */
	private static final String COMMAND_LABEL = Messages.getString("AbstractSatelliteResourceParticipant.command.title"); //$NON-NLS-1$

	/**
	 * Get the satellite resource file extension
	 *
	 * @return
	 */
	protected abstract String getResourceFileExtension();

	/**
	 * Get a correct label of the participant?
	 * Used for label of commands
	 *
	 * @return
	 */
	protected abstract String getParticipantLabel();

	/**
	 * @return true if this participant should handle creation of the resource
	 */
	protected abstract boolean createNewResource();

	/**
	 * @return true if this participant should handle removal of the resource
	 */
	protected abstract boolean deleteOldResource();


	public boolean provideUnControlCommand(ControlModeRequest request) {
		return request.getTargetObject() != null;
	}

	/**
	 * Create a label for composite command
	 *
	 * @param type
	 * @return
	 */
	protected String getCorrectCompositeLabel(String type) {
		return Messages.getString(COMMAND_LABEL, type, getParticipantLabel());
	}

	/**
	 * Create a composite command
	 *
	 * @param request
	 * @param type
	 * @return
	 */
	protected CompositeTransactionalCommand createCompositeTransactionalCommand(ControlModeRequest request, String type) {
		return new CompositeTransactionalCommand(request.getEditingDomain(), getCorrectCompositeLabel(type));
	}

	public ICommand getPreUncontrolCommand(ControlModeRequest request) {
		/*
		 * Fill the request with target resource. This is deduce from the request
		 */
		boolean result = setSatelliteTargetRequest(request);
		if (result) {
			CompositeTransactionalCommand cc = createCompositeTransactionalCommand(request, PRE_UNCONTROL_COMMAND_TITLE);
			for (ICommand cmd : getPreUncontrolCommands(request)) {
				cc.compose(cmd);
			}
			if (!cc.isEmpty()) {
				return cc;
			}
			return null;
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Return the list of the {@link ICommand} for Pre Uncontrol command
	 *
	 * @param request
	 * @return
	 */
	protected abstract List<ICommand> getPreUncontrolCommands(ControlModeRequest request);

	public ICommand getPostUncontrolCommand(ControlModeRequest request) {
		CompositeTransactionalCommand cc = createCompositeTransactionalCommand(request, POST_UNCONTROL_COMMAND_TITLE);
		for (ICommand cmd : getPostUncontrolCommands(request)) {
			cc.compose(cmd);
		}
		if (deleteOldResource()) {
			cc.compose(new RemoveControlResourceCommand(request, getResourceFileExtension()));
		}
		if (!cc.isEmpty()) {
			return cc;
		}
		return null;
	}

	/**
	 * Return the list of the {@link ICommand} for Post Uncontrol command
	 *
	 * @param request
	 * @return
	 */
	protected abstract List<ICommand> getPostUncontrolCommands(ControlModeRequest request);

	public boolean provideControlCommand(ControlModeRequest request) {
		return request.getTargetObject() != null;
	}

	public ICommand getPreControlCommand(ControlModeRequest request) {
		CompositeTransactionalCommand cc = createCompositeTransactionalCommand(request, PRE_CONTROL_COMMAND_TITLE);
		for (ICommand cmd : getPreControlCommands(request)) {
			cc.compose(cmd);
		}
		if (!cc.isEmpty()) {
			return cc;
		}
		return null;
	}

	/**
	 * Return the list of the {@link ICommand} for Pre Control command
	 *
	 * @param request
	 * @return
	 */
	protected abstract List<ICommand> getPreControlCommands(ControlModeRequest request);

	public ICommand getPostControlCommand(ControlModeRequest request) {
		CompositeTransactionalCommand cc = createCompositeTransactionalCommand(request, POST_CONTROL_COMMAND_TITLE);
		if (createNewResource()) {
			cc.compose(new CreateControlResource(request, getResourceFileExtension()));
		}
		for (ICommand cmd : getPostControlCommands(request)) {
			cc.compose(cmd);
		}
		if (!cc.isEmpty()) {
			return cc;
		}
		return null;
	}

	/**
	 * Return the list of the {@link ICommand} for Post Control command
	 *
	 * @param request
	 * @return
	 */
	protected abstract List<ICommand> getPostControlCommands(ControlModeRequest request);

	protected boolean setSatelliteTargetRequest(ControlModeRequest request) {
		URI satelliteResourceURI = request.getNewURI().trimFileExtension().appendFileExtension(getResourceFileExtension());
		ModelSet modelSet = request.getModelSet();
		if (modelSet != null) {
			Resource satelliteResource = null;
			try {
				satelliteResource = modelSet.getResource(satelliteResourceURI, true);
			} catch (Exception e) {
				satelliteResource = null;
			}
			if (satelliteResource == null) {
				return false;
			}
			request.setTargetResource(satelliteResource, getResourceFileExtension());
			// Nothing to do but everything went fine
			return true;
		}
		return false;
	}
}
