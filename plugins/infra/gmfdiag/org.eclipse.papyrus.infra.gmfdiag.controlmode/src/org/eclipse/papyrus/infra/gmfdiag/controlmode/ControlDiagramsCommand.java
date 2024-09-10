/*******************************************************************************
 * Copyright (c) 2013, 2015 Atos, CEA List and Others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Arthur Daussy <a href="mailto:arthur.daussy@atos.net"> - initial API and implementation
 *     Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 459427
 ******************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.controlmode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.controlmode.messages.Messages;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters;
import org.eclipse.papyrus.infra.services.controlmode.commands.AbstractControlCommand;

/**
 * Command handling control/uncontrol of diagram in new resource
 *
 * @author adaussy
 *
 */
public class ControlDiagramsCommand extends AbstractControlCommand {

	/** The Constant RETRIEVE_OLD_URI_RESOURCE_ERROR. */
	private static final String RETRIEVE_OLD_URI_RESOURCE_ERROR = Messages.getString("ControlDiagramsCommand.old.uri.resource.error"); //$NON-NLS-1$

	/** The Constant RETRIEVE_OLD_RESOURCE_ERROR. */
	private static final String RETRIEVE_OLD_RESOURCE_ERROR = Messages.getString("ControlDiagramsCommand.old.resource.error"); //$NON-NLS-1$

	/** The Constant COMMAND_TITLE. */
	private static final String COMMAND_TITLE = Messages.getString("ControlDiagramsCommand.command.title"); //$NON-NLS-1$

	/** The Constant CREATION_RESOURCE_ERROR. */
	private static final String CREATION_RESOURCE_ERROR = Messages.getString("ControlDiagramsCommand.resource.creation.error"); //$NON-NLS-1$

	/** The old notation resource. */
	private Resource oldNotationResource = null;

	/**
	 * Instantiates a new control diagrams command.
	 *
	 * @param request
	 *            the request
	 */
	@SuppressWarnings("unchecked")
	public ControlDiagramsCommand(ControlModeRequest request) {
		super(COMMAND_TITLE, null, request);
		getAffectedFiles().addAll(getWorkspaceFiles(getDiagrams()));

	}


	/**
	 * Reference the diagram about to be moved into the request in order to be used by other particpants
	 *
	 * @param diags
	 */
	@SuppressWarnings("unchecked")
	protected void addMovedDiagramToRequest(List<Diagram> diags) {
		Collection<EObject> openables = (Collection<EObject>) getRequest().getParameter(ControlModeRequestParameters.MOVED_OPENABLES);
		if (null == openables) {
			openables = new ArrayList<EObject>();
		}
		openables.addAll(diags);
		getRequest().addParameters(Collections.singletonMap(ControlModeRequestParameters.MOVED_OPENABLES, openables));
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) {

		EObject objectToControl = getRequest().getTargetObject();

		// Retrieve new notation resource created previously
		Resource newNotationResource = getRequest().getTargetResource(NotationModel.NOTATION_FILE_EXTENSION);
		if (null == newNotationResource) {
			return CommandResult.newErrorCommandResult(CREATION_RESOURCE_ERROR);
		}

		// Move contained diagrams to new resource
		List<Diagram> diagrams = getDiagrams();
		if (!diagrams.isEmpty()) {
			newNotationResource.getContents().addAll(diagrams);
			addMovedDiagramToRequest(diagrams);
		}

		// Update notation resources references
		Collection<Setting> crossReferences = EMFHelper.getUsages(objectToControl);

		Set<Resource> impactedNotationResources = new HashSet<Resource>(2);
		if (!crossReferences.isEmpty()) {

			// Look for impacted notation resources
			for (Setting setting : crossReferences) {
				EObject referencedObject = setting.getEObject();
				if (referencedObject instanceof View) {
					Resource resource = referencedObject.eResource();
					if (null != resource) {
						impactedNotationResources.add(resource);
					}
				}

			}

			if (!impactedNotationResources.isEmpty()) {
				// Update resources that have references
				for (Resource resource : impactedNotationResources) {
					resource.setModified(true);
				}
			}

		}

		return CommandResult.newOKCommandResult(newNotationResource);
	}

	/**
	 * Get the list of all the diagrams to move.
	 *
	 * @return the diagrams
	 */
	protected List<Diagram> getDiagrams() {
		Resource notationResource = getOldNotationResource();

		return NotationUtils.getDiagrams(notationResource, getRequest().getTargetObject());
	}


	/**
	 * Gets the old notation resource.
	 *
	 * @return the old notation resource
	 */
	private Resource getOldNotationResource() {

		if (null == oldNotationResource) {
			try {
				oldNotationResource = getRequest().getModelSet().getResource(getOldNotationURI(), true);
			} catch (Exception e) {
				Activator.log.error(RETRIEVE_OLD_RESOURCE_ERROR, e);
			}
		}

		return oldNotationResource;
	}

	/**
	 * Get the old notation URI for request
	 *
	 * @return
	 * @throws ExecutionException
	 */
	protected URI getOldNotationURI() throws ExecutionException {
		URI uri = getRequest().getSourceURI();
		if (null != uri) {
			return uri.trimFileExtension().appendFileExtension(NotationModel.NOTATION_FILE_EXTENSION);
		}
		throw new ExecutionException(RETRIEVE_OLD_URI_RESOURCE_ERROR);
	}
}