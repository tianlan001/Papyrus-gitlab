/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 485220, 573986
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.providers;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.emf.facet.custom.ui.internal.CustomizedTreeContentProvider;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesUtil;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;

/**
 * The customization editor's content provider. Based on the EMF Facet
 * customizable content provider,
 *
 * @author Camille Letavernier
 */
public class ContextContentProvider extends CustomizedTreeContentProvider {

	/**
	 * Constructor.
	 */
	public ContextContentProvider() {
		super(Activator.getDefault().getCustomizationManager());
	}

	/**
	 * @param inputElement
	 *            : A ResourceSet
	 * @return The root EObjects from the input ResourceSet
	 */
	@Override
	public Object[] getRootElements(Object inputElement) {
		if (inputElement instanceof ResourceSet) {
			ResourceSet resourceSet = (ResourceSet) inputElement;

			if (resourceSet.getResources().isEmpty()) {
				return null;
			}

			Set<EObject> elements = new LinkedHashSet<>();

			elements.addAll(resourceSet.getResources().get(0).getContents());
			Set<Context> allContexts = new LinkedHashSet<>();
			for (EObject element : elements) {
				if (element instanceof Context) {
					allContexts.addAll(PropertiesUtil.getDependencies((Context) element));
				}
			}
			elements.addAll(allContexts);
			return elements.toArray();
		}

		return super.getRootElements(inputElement);
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}
}
