/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.widgets.providers.AbstractStaticContentProvider;

/**
 * A Content provider returning the list of registered contexts
 *
 * @author Camille Letavernier
 */
public class DependencyContentProvider extends AbstractStaticContentProvider {

	private Context source;

	public DependencyContentProvider(Context source) {
		this.source = source;
	}

	@Override
	public Object[] getElements() {
		List<Context> registeredContexts = new LinkedList<Context>(PropertiesRuntime.getConfigurationManager().getContexts());
		List<Context> localContexts = new LinkedList<Context>();
		for (Resource resource : source.eResource().getResourceSet().getResources()) {
			for (EObject element : resource.getContents()) {
				if (element instanceof Context && !registeredContexts.contains(element)) {
					localContexts.add((Context) element);
				}
			}
		}

		registeredContexts.addAll(localContexts);

		return registeredContexts.toArray();
	}

}
