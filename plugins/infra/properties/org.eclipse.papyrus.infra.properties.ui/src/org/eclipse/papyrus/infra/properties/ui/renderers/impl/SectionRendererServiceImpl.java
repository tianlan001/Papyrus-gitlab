/*****************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Bug 572530
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.renderers.impl;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.renderers.SectionRendererProvider;
import org.eclipse.papyrus.infra.properties.ui.renderers.SectionRendererService;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.ui.views.properties.tabbed.ISectionDescriptor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

@Component
public class SectionRendererServiceImpl implements SectionRendererService {

	private final Set<SectionRendererProvider> providers = new HashSet<>();

	@Reference(cardinality = ReferenceCardinality.MULTIPLE)
	public void registerSectionProvider(SectionRendererProvider provider) {
		providers.add(provider);
	}

	public void unregisterSectionProvider(SectionRendererProvider provider) {
		providers.remove(provider);
	}

	@Override
	public ISectionDescriptor getSectionDescriptor(Section section, View view, DisplayEngine display) {
		double highestPriority = Double.MIN_VALUE;
		SectionRendererProvider currentProvider = null;
		for (SectionRendererProvider provider : providers) {
			double priority = provider.getPriority(section, view, display);
			if (Double.isNaN(priority)) {
				continue;
			}
			if (currentProvider == null || priority > highestPriority) {
				highestPriority = priority;
				currentProvider = provider;
			}
		}
		return currentProvider.getSectionDescriptor(section, view, display);
	}

}
