/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 436952
 *  Christian W. Damus - bug 481149
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Abstract Implementation of the {@link IModel} interface.
 */
public abstract class AbstractModel implements IModel {

	/** Default encoding */
	public static final String ENCODING = "UTF-8"; //$NON-NLS-1$

	/** The associated ModelManager */
	protected ModelSet modelSet;

	/** List of attached snippets */
	protected ModelSnippetList snippets = new ModelSnippetList();

	private boolean snippetsStarted;

	/** list of Models (referenced by identifiers) that should be loaded before this one can be loaded */
	protected List<String> afterLoadModelIdentifiers;

	/** list of Models (referenced by identifiers) that should not be loaded before this one is unloaded */
	protected List<String> unloadBeforeModelIdentifiers;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(ModelSet modelSet) {
		this.modelSet = modelSet;
	}

	/**
	 * Returns the ModelSet given during initialization
	 *
	 * @return the ModelSet given during initialization
	 */
	protected ModelSet getModelManager() {
		return modelSet;
	}

	/**
	 * Returns the associated ResourceSet containing the resources of this model.
	 *
	 * @return the associated ResourceSet containing the resources of this model.
	 */
	protected ResourceSet getResourceSet() {
		return modelSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String getIdentifier();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addModelSnippet(IModelSnippet snippet) {
		if (snippets.add(snippet) && snippetsStarted) {
			// Snippets have already started, so start this one, too
			snippet.start(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAfterLoadModelDependencies(List<String> afterLoadModelIdentifiers) {
		this.afterLoadModelIdentifiers = afterLoadModelIdentifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getAfterLoadModelIdentifiers() {
		return afterLoadModelIdentifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBeforeUnloadDependencies(List<String> unloadBeforeModelIdentifiers) {
		this.unloadBeforeModelIdentifiers = unloadBeforeModelIdentifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUnloadBeforeModelIdentifiers() {
		return unloadBeforeModelIdentifiers;
	}

	@Override
	public void unload() {
		this.modelSet = null;

		stopSnippets();
		snippets.clear();
	}

	@Override
	public boolean isModelFor(Object element) {
		return false;
	}


	/**
	 * @see org.eclipse.papyrus.infra.core.resource.IModel#cleanModel(java.util.Set)
	 *
	 * @param toDeleteOnSave
	 */
	@Override
	public void cleanModel(Set<URI> resourcesToDelete) {
		// Nothing to do

	}

	/**
	 * Starts my registered snippets.
	 * 
	 * @since 2.0
	 */
	protected void startSnippets() {
		if (!snippetsStarted) {
			snippetsStarted = true;
			snippets.performStart(this);
		}
	}

	/**
	 * Stops my registered snippets.
	 * 
	 * @since 2.0
	 */
	protected void stopSnippets() {
		if (snippetsStarted) {
			snippetsStarted = false;
			snippets.performDispose(this);
		}
	}
}
