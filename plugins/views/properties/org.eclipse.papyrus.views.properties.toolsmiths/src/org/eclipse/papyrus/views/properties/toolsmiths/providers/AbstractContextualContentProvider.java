/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.providers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesUtil;
import org.eclipse.papyrus.infra.tools.util.ListHelper;
import org.eclipse.papyrus.infra.ui.emf.providers.strategy.SemanticEMFContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;

/**
 * An abstract implementation of {@link IStaticContentProvider} for
 * content providers which rely on the available {@link Context}s
 * to retrieve their elements.
 *
 * @author Camille Letavernier
 *
 */
public abstract class AbstractContextualContentProvider extends SemanticEMFContentProvider implements IStaticContentProvider {

	/**
	 * The list of available contexts in the current model
	 */
	protected Collection<Context> contexts;

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 *            The EObject used to retrieve the available contexts
	 */
	protected AbstractContextualContentProvider(EObject source) {
		super(findContexts(source).toArray(new Context[0]), Activator.getDefault().getCustomizationManager());
		contexts = ListHelper.asList((Context[]) roots);
	}

	private static List<Context> findContexts(EObject source) {
		List<Context> contexts = new LinkedList<>();

		Context rootContext = null;
		if (source.eResource() != null) {
			for (Resource resource : source.eResource().getResourceSet().getResources()) {
				if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof Context) {
					rootContext = (Context) resource.getContents().get(0);
					contexts.add(rootContext);
					break;
				}
			}
		}

		return PropertiesUtil.getDependencies(rootContext);
	}
}
