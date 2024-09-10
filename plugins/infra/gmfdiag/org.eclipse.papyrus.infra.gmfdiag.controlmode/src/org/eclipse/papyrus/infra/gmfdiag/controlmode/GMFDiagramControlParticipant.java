/*******************************************************************************
 * Copyright (c) 2013, 2015 Atos, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Arthur Daussy <a href="mailto:arthur.daussy@atos.net"> - initial API and implementation
 *     Christian W. Damus - bug 480209
 ******************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.controlmode;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.commands.CreateControlResource;
import org.eclipse.papyrus.infra.services.controlmode.commands.RemoveControlResourceCommand;
import org.eclipse.papyrus.infra.services.controlmode.participants.IControlCommandParticipant;
import org.eclipse.papyrus.infra.services.controlmode.participants.IUncontrolCommandParticipant;

/**
 * Particiant that will and diagram control
 *
 * @author adaussy
 *
 */
public class GMFDiagramControlParticipant implements IControlCommandParticipant, IUncontrolCommandParticipant {

	/** The Constant PARTICIPANT_ID. */
	private static final String PARTICIPANT_ID = "org.eclipse.papyrus.infra.gmfdiag.controlmode.GMFDiagramControlParticipant"; //$NON-NLS-1$

	/**
	 * Init target request to have the DIModel resource references
	 *
	 * @param request
	 */
	protected boolean setNotationTargetRequest(ControlModeRequest request) {
		URI notationURI = request.getNewURI().trimFileExtension().appendFileExtension(NotationModel.NOTATION_FILE_EXTENSION);
		ModelSet modelSet = request.getModelSet();
		if (modelSet != null) {
			Resource notationResource = null;
			try {
				notationResource = modelSet.getResource(notationURI, true);
			} catch (Exception e) {
				notationResource = null;
			}
			if (notationResource == null) {
				return false;
			}
			request.setTargetResource(notationResource, NotationModel.NOTATION_FILE_EXTENSION);
			// Nothing to do but everything is ok
			return true;
		}
		return false;
	}

	public String getID() {
		return PARTICIPANT_ID;
	}

	public ICommand getPostControlCommand(ControlModeRequest request) {
		return new ControlDiagramsCommand(request);
	}

	public ICommand getPostUncontrolCommand(ControlModeRequest request) {
		return new RemoveControlResourceCommand(request, NotationModel.NOTATION_FILE_EXTENSION);
	}

	public ICommand getPreControlCommand(ControlModeRequest request) {
		// Create notation resource
		return new CreateControlResource(request, NotationModel.NOTATION_FILE_EXTENSION);
	}

	public ICommand getPreUncontrolCommand(ControlModeRequest request) {
		boolean result = setNotationTargetRequest(request);
		if (result) {
			return new ControlDiagramsCommand(request);
		}
		return UnexecutableCommand.INSTANCE;
	}

	public int getPriority() {
		return 60;
	}

	public boolean provideControlCommand(ControlModeRequest request) {
		return request.getTargetObject() instanceof EObject;
	}

	public boolean provideUnControlCommand(ControlModeRequest request) {
		boolean result = false;

		EObject objectToUncontrol = request.getTargetObject();
		if (objectToUncontrol != null) {
			// If there is no notation resource, then there's nothing to do
			URI notationURI = request.getNewURI().trimFileExtension().appendFileExtension(NotationModel.NOTATION_FILE_EXTENSION);
			ModelSet modelSet = request.getModelSet();
			result = (modelSet != null) && modelSet.getURIConverter().exists(notationURI, null);
		}

		return result;
	}
}
