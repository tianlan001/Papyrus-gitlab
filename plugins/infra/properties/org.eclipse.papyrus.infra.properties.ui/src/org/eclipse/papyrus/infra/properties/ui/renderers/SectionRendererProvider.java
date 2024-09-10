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
package org.eclipse.papyrus.infra.properties.ui.renderers;

import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.ui.views.properties.tabbed.ISectionDescriptor;

/**
 * A Service Provider that can create an {@link ISectionDescriptor} from a {@link Section}.
 * These providers are usually specific to a rendering framework, and may support only
 * some Sections.
 */
public interface SectionRendererProvider {

	/**
	 * This provider's priority for this section, or {@link Double#NaN} if this
	 * provider doesn't support this section.
	 *
	 * @param section
	 * @param view
	 * @param display
	 * @return
	 *         This provider's priority for this section, or {@link Double#NaN} if this
	 *         provider doesn't support this section.
	 */
	double getPriority(Section section, View view, DisplayEngine display);

	ISectionDescriptor getSectionDescriptor(Section section, View view, DisplayEngine display);
}
