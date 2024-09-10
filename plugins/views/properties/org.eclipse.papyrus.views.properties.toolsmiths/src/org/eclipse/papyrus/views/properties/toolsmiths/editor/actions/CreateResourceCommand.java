/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;

/**
 * A command to create an EMF resource
 *
 * @author Camille Letavernier
 */
public class CreateResourceCommand extends AbstractCommand implements CommandActionDelegate {

	private EObject object;

	private URI uri;

	private ResourceSet resourceSet;

	private boolean fileAlreadyExists = true;

	private Resource resource;

	/**
	 *
	 * Constructor. A Command to create an EMF Resource. The command can be undone.
	 *
	 * @param object
	 *            The EObject to persist in the resource
	 * @param uri
	 *            The location of the resource
	 * @param resourceSet
	 *            The resourceSet in which the resource should be created
	 */
	public CreateResourceCommand(EObject object, URI uri, ResourceSet resourceSet) {
		super("Create new " + object.eClass().getName(), "Creates a new " + object.eClass().getName()); //$NON-NLS-1$ //$NON-NLS-2$
		this.object = object;
		this.uri = uri;
		this.resourceSet = resourceSet;
	}

	@Override
	public void execute() {
		resource = resourceSet.getResource(uri, false);
		if (resource == null) {
			Activator.log.debug("+++ Creating " + uri);
			fileAlreadyExists = false;
			resource = resourceSet.createResource(uri);
		} else {
			Activator.log.debug("+++ " + resource.getURI() + " already exists");
		}
		resource.getContents().add(object);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		// Do not unload the resource if it was created before this command was executed
		if (!fileAlreadyExists) {
			Activator.log.debug("--- Deleting " + resource.getURI());
			try {
				resourceSet.getResources().remove(resource);
				resource.delete(Collections.emptyMap());
			} catch (IOException ex) {
				Activator.log.error(ex);
			}
		} else {
			Activator.log.debug("--- " + resource.getURI() + " will not be deleted");
		}
	}

	@Override
	public Object getImage() {
		return null;
	}

	@Override
	public String getText() {
		return getLabel();
	}

	@Override
	public String getToolTipText() {
		return getDescription();
	}

	@Override
	protected boolean prepare() {
		return true;
	}
}
