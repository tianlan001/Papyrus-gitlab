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

import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.renderers.SectionRendererProvider;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.papyrus.infra.properties.ui.xwt.XWTSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISectionDescriptor;
import org.osgi.service.component.annotations.Component;

/**
 * Default renderer provider for (legacy) XWT Sections. This providers uses {@link XWTSectionDescriptor} for
 * rendering XWT Sections.
 */
@Component
public class XWTSectionProvider implements SectionRendererProvider {

	@Override
	public double getPriority(Section section, View view, DisplayEngine display) {
		// This provider is the default one; so it uses either NaN (Not supported) or MIN_VALUE (Very low priority)
		return section.getSectionFile() != null && section.getSectionFile().endsWith(".xwt") ? Double.MIN_VALUE : Double.NaN;
	}

	@Override
	public ISectionDescriptor getSectionDescriptor(Section section, View view, DisplayEngine display) {
		return new XWTSectionDescriptor(section, view, display);
	}

}
