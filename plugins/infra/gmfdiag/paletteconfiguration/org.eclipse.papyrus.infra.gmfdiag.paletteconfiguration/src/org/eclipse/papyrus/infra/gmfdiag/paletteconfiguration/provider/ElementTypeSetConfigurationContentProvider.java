/*****************************************************************************
 * Copyright (c) 2017, 2021 CEA LIST, ALL4TEC, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Micka�l ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * Content Provider for {@link ElementTypeSetConfiguration}.
 */
public class ElementTypeSetConfigurationContentProvider implements IStaticContentProvider, IHierarchicContentProvider {

	private final ResourceSet resourceSet;

	public ElementTypeSetConfigurationContentProvider() {
		this(null);
	}

	/**
	 * Initialize me with the resource set of the contextual editor, in which I can search for
	 * locally loaded element types configuration models.
	 *
	 * @param resourceSet
	 *            the contextual resource set, or {@code null} if none
	 */
	public ElementTypeSetConfigurationContentProvider(ResourceSet resourceSet) {
		super();

		this.resourceSet = resourceSet;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		List<ElementTypeSetConfiguration> els = new ArrayList<>();
		Collection<Map<String, ElementTypeSetConfiguration>> values = ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations().values();
		for (Iterator<Map<String, ElementTypeSetConfiguration>> iterator = values.iterator(); iterator.hasNext();) {
			Map<String, ElementTypeSetConfiguration> map = iterator.next();
			Collection<ElementTypeSetConfiguration> values2 = map.values();
			for (Iterator<ElementTypeSetConfiguration> iterator2 = values2.iterator(); iterator2.hasNext();) {
				ElementTypeSetConfiguration elementTypeSetConfiguration = iterator2.next();
				if (!elementTypeSetConfiguration.getElementTypeConfigurations().isEmpty()) {
					els.add(elementTypeSetConfiguration);
				}
			}
		}

		els.addAll(getLocalElementTypeSetConfigurations());

		return els.toArray();
	}

	private List<ElementTypeSetConfiguration> getLocalElementTypeSetConfigurations() {
		List<ElementTypeSetConfiguration> result = new ArrayList<>();
		if (resourceSet != null) {
			resourceSet.getResources().stream().map(Resource::getContents).flatMap(Collection::stream)
					.filter(ElementTypeSetConfiguration.class::isInstance).map(ElementTypeSetConfiguration.class::cast)
					.forEach(result::add);
		}

		return result;
	}

	@Override
	public Object[] getElements() {
		return getElements(null);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] children = null;
		if (parentElement instanceof ElementTypeSetConfiguration) {
			children = ((ElementTypeSetConfiguration) parentElement).getElementTypeConfigurations().toArray();
		}
		return children;
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		return null != getChildren(element);
	}

	@Override
	public boolean isValidValue(final Object element) {
		return element instanceof ElementTypeConfiguration;
	}

}
