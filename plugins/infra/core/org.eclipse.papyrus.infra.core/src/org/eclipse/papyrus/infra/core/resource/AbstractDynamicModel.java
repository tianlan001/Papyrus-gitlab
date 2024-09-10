/*****************************************************************************
 * Copyright (c) 2010, 2015, 2019 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus - bug 481149
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 548973
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.resource;

import java.io.IOException;

import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;

/**
 * Base class for models that can be created dynamically. Such models do not need to exist when others models are loaded.
 * If the model is missing, it will be created.
 * Furthermore, if the model is empty, it will not be saved.
 *
 *
 * @author cedric dumoulin
 *
 * @param T
 *            Type of the roots of the model.
 */
public abstract class AbstractDynamicModel<T extends EObject> extends AbstractBaseModel {

	/**
	 * boolean indicating than the resource contains or containing something previously and must be saved
	 */
	private boolean containsSomethingInThePast = false;

	/**
	 *
	 * Constructor.
	 *
	 * @param modelKind
	 */
	public AbstractDynamicModel() {
		super();
	}

	/**
	 * Load the model if it exist, or create a new one if it doesn't exist.
	 *
	 * @param fullPathWithoutExtension
	 */
	@SuppressWarnings("restriction")
	@Override
	public void loadModel(IPath fullPathWithoutExtension) {

		// Try to load the model. It this fail, create a model.
		try {
			// Try to load the model
			super.loadModel(fullPathWithoutExtension);
			return;
		} catch (RuntimeException e) {

			// Check if the exception come from an non existing resource.
			if (!(e.getCause() instanceof ResourceException)) {
				throw e;
			}
		}
		// The resource do not exist, crate it.
		createModel(fullPathWithoutExtension);

		// call registered snippets
		startSnippets();
	}

	/**
	 * Save the model if it contains something.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#saveModel()
	 *
	 * @throws IOException
	 */
	@Override
	public void saveModel() throws IOException {
		// we check if the resource contains something
		if (false == this.containsSomethingInThePast) {
			this.containsSomethingInThePast = getResource().getContents().size() > 0;
		}

		/*
		 * the resource is empty and has always been empty
		 */
		if (false == this.containsSomethingInThePast && getResource().getContents().size() <= 0) {
			return;
		}

		// Do the save
		super.saveModel();
	}

	/**
	 * Add a root to this model.
	 *
	 * @param root
	 */
	public void addModelRoot(T root) {
		getResource().getContents().add(root);
	}
}
