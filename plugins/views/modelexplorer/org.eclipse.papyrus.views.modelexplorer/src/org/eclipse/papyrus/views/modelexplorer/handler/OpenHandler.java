/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 576595, 576599
 *****************************************************************************/
package org.eclipse.papyrus.views.modelexplorer.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenable;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.ui.command.AbstractPapyrusHandler;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * This handler allows to Open EObject that can be adapted to IOpenable.
 */
public class OpenHandler extends AbstractPapyrusHandler implements IExecutableExtension {


	/**
	 * a boolean used to allow to open a second time the same view
	 *
	 * @since 5.0
	 */
	private static final String IS_DUPLICATE_VIEW_ALLOWED_PARAMETER = "isDuplicateViewAllowed"; //$NON-NLS-1$


	/**
	 * Return true if the open command allow to duplicate editor that are already
	 * opened.
	 * Return false if open command should not duplicate already opened editor.
	 * This property can be set from the plugin.xml.
	 *
	 * @since 5.0
	 */
	private boolean isDuplicateViewAllowed = false;

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IPageManager pageManager = getPageManager(event);
		if (pageManager == null) {
			return null;
		}

		// 1. get the selected EObject
		final List<EObject> selectedEObjects = getCurrentSelectionAdaptedToType(event, EObject.class);
		if (selectedEObjects == null || selectedEObjects.isEmpty()) {
			// nothing to do
			return null;
		}

		// 2. convert to IOpenable
		final Collection<IOpenable> openables = getIOpenables(selectedEObjects);
		if (openables.isEmpty()) {
			return null;
		}

		// Check each selected object
		final List<EObject> pagesToOpen = new LinkedList<>();
		List<EObject> pagesToSelect = new LinkedList<>();
		for (IOpenable selected : openables) {
			if (selected.getPageIdentifier() instanceof EObject) {

				// bug 571087
				// we use the IOpenable element to get the real element to open
				final EObject realObjectToOpen = (EObject) selected.getPageIdentifier();
				if (!canOpenByPolicy(realObjectToOpen)) {
					continue;
				}
				if (!pageManager.isOpen(realObjectToOpen) || this.isDuplicateViewAllowed) {
					pagesToOpen.add(realObjectToOpen);
				} else {
					pagesToSelect.add(realObjectToOpen);
				}
			}
		}

		if (!pagesToOpen.isEmpty()) {
			for (EObject page : pagesToOpen) {
				pageManager.openPage(page);
			}
		}

		for (EObject page : pagesToSelect) {
			pageManager.selectPage(page);
		}

		return null;
	}

	/**
	 *
	 * @param selectedEObject
	 *            the selected EObject
	 * @return
	 *         the IOpenanbel corresponding to these {@link EObject}
	 */
	private Collection<IOpenable> getIOpenables(final Collection<EObject> selectedEObject) {
		final Iterator<EObject> iter = selectedEObject.iterator();
		final Collection<IOpenable> openables = new ArrayList<>();
		while (iter.hasNext()) {
			final IOpenable openable = Platform.getAdapterManager().getAdapter(iter.next(), IOpenable.class);
			if (openable != null) {
				openables.add(openable);
			}
		}
		return openables;
	}

	/**
	 * Determines whether the current policy allows this object to be opened
	 *
	 * @param selection
	 *            The object to open
	 * @return <code>true</code> if the object can be opened
	 */
	private boolean canOpenByPolicy(EObject selection) {
		final ViewPrototype proto = ViewPrototype.get(selection);
		return proto != ViewPrototype.UNAVAILABLE_VIEW;
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 *
	 * @param config
	 * @param propertyName
	 * @param data
	 * @throws CoreException
	 */
	@Override
	public void setInitializationData(final IConfigurationElement config, final String propertyName, final Object data) throws CoreException {
		if (!(data instanceof Hashtable<?, ?>)) {
			return;
		}

		final Hashtable<?, ?> map = (Hashtable<?, ?>) data;
		final Object value = map.get(IS_DUPLICATE_VIEW_ALLOWED_PARAMETER);
		if (value != null) {
			this.isDuplicateViewAllowed = Boolean.parseBoolean(value.toString());
		}
	}
}