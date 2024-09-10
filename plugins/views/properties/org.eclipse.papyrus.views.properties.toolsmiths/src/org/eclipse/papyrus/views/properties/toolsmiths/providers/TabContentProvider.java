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
package org.eclipse.papyrus.views.properties.toolsmiths.providers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.views.properties.toolsmiths.providers.AbstractContextualContentProvider;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;

/**
 * A Content provider used to retrieve all tabs from the available contexts
 *
 * @author Camille Letavernier
 *
 */
public class TabContentProvider extends AbstractContextualContentProvider {

	private boolean editableTabsOnly;

	/**
	 * Constructor.
	 *
	 * @param source
	 *            The EObject used to retrieve the current context
	 * @param editableTabsOnly
	 *            If true, only the tabs from editable contexts will be returned,
	 *            i.e. the plug-in contexts will be ignored
	 */
	public TabContentProvider(EObject source, boolean editableTabsOnly) {
		super(source);
	}

	@Override
	public Object[] getElements() {
		List<Tab> tabs = new LinkedList<Tab>();
		for (Context context : contexts) {
			if (isValidContext(context)) {
				tabs.addAll(context.getTabs());
			}
		}
		return tabs.toArray();
	}

	private boolean isValidContext(Context context) {
		if (editableTabsOnly) {
			return !PropertiesRuntime.getConfigurationManager().isPlugin(context);
		}
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getElements();
	}

}
