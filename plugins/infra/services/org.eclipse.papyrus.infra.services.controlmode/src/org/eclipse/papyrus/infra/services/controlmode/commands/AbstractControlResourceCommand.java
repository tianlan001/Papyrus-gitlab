/*****************************************************************************
 * Copyright (c) 2013 Atos.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.commands;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;

public abstract class AbstractControlResourceCommand extends AbstractControlCommand {


	/** The Constant NOT_LINK_OBJECT_ERROR. */
	private static final String NOT_LINK_OBJECT_ERROR = Messages.getString("AbstractControlResourceCommand.object.error"); //$NON-NLS-1$

	/**
	 * File extension use to create the resource. If not specified then it should be already provided in the {@link URI}
	 */
	protected String newFileExtension;

	@SuppressWarnings("rawtypes")
	public AbstractControlResourceCommand(ControlModeRequest request, String label, List affectedFiles) {
		super(label, affectedFiles, request);
	}

	/**
	 * @param request
	 *            {@link CreateControlResource#request}
	 * @param newFileExtension
	 *            {@link CreateControlResource#newFileExtension}
	 */
	@SuppressWarnings("rawtypes")
	public AbstractControlResourceCommand(ControlModeRequest request, String newFileExtension, String label, List affectedFiles) {
		this(request, label, affectedFiles);
		this.newFileExtension = newFileExtension;
	}

	/**
	 * @return the {@link ResourceSet} using the request
	 */
	public ResourceSet getResourceSet() {
		Resource eResource = getRequest().getTargetObject().eResource();
		if (eResource == null) {
			throw new RuntimeException(NOT_LINK_OBJECT_ERROR);
		}
		return eResource.getResourceSet();
	}

	/**
	 * @return {@link CreateControlResource#newFileExtension}
	 */
	protected String getFileExtension() {
		if (newFileExtension == null) {
			return getTargetUri().fileExtension();
		}
		return newFileExtension;
	}

	/**
	 * Return the new URI using the request
	 */
	public URI getTargetUri() {
		if (newFileExtension == null) {
			return getRequest().getNewURI();
		}
		return getRequest().getNewURI().trimFileExtension().appendFileExtension(newFileExtension);
	}

	/**
	 * Get the URI from the source using the request and the file extension
	 *
	 * @return
	 */
	public URI getSourceUri() {
		if (newFileExtension == null) {
			return getRequest().getSourceURI();
		}
		return getRequest().getSourceURI().trimFileExtension().appendFileExtension(newFileExtension);
	}

	/**
	 * Checks if controlled resource is locked.
	 *
	 * @param resourceURI
	 *            the resource uri
	 * @return true, if control resource is locked
	 */
	public boolean isControlledResourceLocked(URI resourceURI) {
		boolean isSemanticResourceEmpty = false;

		if (resourceURI != null) {
			ModelSet modelSet = getRequest().getModelSet();
			if (modelSet != null) {

				// The resource has already removed of the resource set by other participant
				Resource resource = modelSet.getResource(resourceURI, false);
				if (resource != null && resource.isModified()) {
					isSemanticResourceEmpty = !resource.getContents().isEmpty();
				} else {
					resource = getRequest().getSourceResource(resourceURI.fileExtension());
					if (resource != null) {
						isSemanticResourceEmpty = !resource.getContents().isEmpty();
					}
				}

			}
		}

		return isSemanticResourceEmpty;
	}
}
