/*****************************************************************************
 * Copyright (c) 2008, 2023 CEA LIST.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 581660
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor;

import static org.eclipse.papyrus.infra.core.Activator.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.papyrus.infra.core.extension.ExtensionException;
import org.eclipse.papyrus.infra.core.extension.NotFoundException;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * A factory managing ActionBarContributor creation. The factory is loaded from
 * ActionBarContributor declared in Eclipse extension mechanism.
 *
 * @author dumoulin
 * @since 1.2
 *
 */
public class ActionBarContributorRegistry implements IActionBarContributorFactory, IService {

	/** ID of the editor extension (schema filename) */
	public static final String EDITOR_EXTENSION_ID = "papyrusDiagram"; //$NON-NLS-1$

	/** Namespace where to look for the extension points. */
	protected String extensionPointNamespace;

	/**
	 * Registered context descriptors.
	 */
	private Map<Object, ActionBarContributorDescriptor> editorContextDescriptors;

	/**
	 * Constructor. defaultContext, input and site are explicitly required in
	 * order be sure that they are initialized. The multiEditor should be
	 * initialized. In particular, getEditorSite(), getEditorInput() and
	 * getDefaultContext() should return initialized values.
	 *
	 * @param multiEditor
	 *            the multieditor
	 * @param extensionPointNamespace
	 */
	public ActionBarContributorRegistry(String extensionPointNamespace) {

		this.extensionPointNamespace = extensionPointNamespace;
		initializeEditorContextDescriptors();
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public EditorActionBarContributor getActionBarContributor(Object key) throws BackboneException {
		try {
			ActionBarContributorDescriptor desc = editorContextDescriptors.get(key);
			return desc.getActionBarContributor();
		} catch (NullPointerException e) {
			// no context found.
			throw new NotFoundException("No ActionBarContributor registered under id '" + key + "'."); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Get the list of descriptors.
	 *
	 * @return
	 * @throws BackboneException
	 *             If a contributor fail to be loaded.
	 */
	public List<EditorActionBarContributor> getActionBarContributors() throws BackboneException {
		List<EditorActionBarContributor> res = new ArrayList<>();
		for (ActionBarContributorDescriptor desc : editorContextDescriptors.values()) {
			res.add(desc.getActionBarContributor());
		}
		return res;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	public void registerActionBarContributor(String contextKey, EditorActionBarContributor contributor) {
		ActionBarContributorDescriptor desc = new ActionBarContributorDescriptor();
		desc.contextId = contextKey;
		desc.instance = contributor;
		desc.contextClass = contributor.getClass();

		editorContextDescriptors.put(contextKey, desc);
	}

	/**
	 * Read context descriptors from extension points.
	 */
	private void initializeEditorContextDescriptors() {

		editorContextDescriptors = new HashMap<>();
		// Reading data from plugins
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointNamespace, EDITOR_EXTENSION_ID);

		ActionBarContributorExtensionFactory extensionReader = new ActionBarContributorExtensionFactory();

		for (IConfigurationElement ele : configElements) {
			ActionBarContributorDescriptor desc;
			try {
				if (ActionBarContributorExtensionFactory.EDITOR_ACTIONBARCONTRIBUTOR_EXTENSIONPOINT.equals(ele.getName())) {
					desc = extensionReader.createActionBarContributorDescriptor(ele);
					// Check double
					if (editorContextDescriptors.get(desc.contextId) != null) {
						// Already exists. Check if it is the same
						ActionBarContributorDescriptor existingDesc = editorContextDescriptors.get(desc.contextId);
						if (desc.equals(existingDesc)) {
							log.warn("More than one ActionBarContributor is registered under the name '" + desc.contextId + "', with different parameters. Extra declaration are discarded."); //$NON-NLS-1$ //$NON-NLS-2$
						}
					} else {
						editorContextDescriptors.put(desc.contextId, desc);
					}
				}
			} catch (ExtensionException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getSimpleName() + " : contributors desc loaded  [" + editorContextDescriptors.size() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Initialize the service. Do nothing here.
	 *
	 * @see org.eclipse.papyrus.infra.core.services.IService#init(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param servicesRegistry
	 */
	@Override
	public void init(ServicesRegistry servicesRegistry) {

	}

	/**
	 * Do nothing in this implementation. {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.core.services.IService#startService()
	 */
	@Override
	public void startService() {
	}

	/**
	 * Do nothing in this implementation.
	 */
	@Override
	public void disposeService() {
		for (final ActionBarContributorDescriptor descriptor : editorContextDescriptors.values()) {
			descriptor.contextClass = null;
			descriptor.contextId = null;
			descriptor.instance = null;
		}
		editorContextDescriptors = null;
	}

}
