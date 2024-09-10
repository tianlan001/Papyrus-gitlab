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
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.query;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.constraints.constraints.JavaQuery;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.Layout;
import org.eclipse.papyrus.infra.properties.ui.widgets.layout.PropertiesLayout;
import org.eclipse.swt.layout.GridLayout;

/**
 * <p>
 * Test if a {@link Layout} is either a {@link GridLayout} or a {@link PropertiesLayout}.
 * </p>
 *
 * <strong>OCL</strong>:
 * <code>layoutType.widgetClass = 'GridLayout' or (layoutType.widgetClass = 'PropertiesLayout' and layoutType.namespace.name = 'ppel')</code>
 */
public class IsGridLayoutQuery implements JavaQuery {

	@Override
	public boolean match(Object selection) {
		EObject selectedEObject = EMFHelper.getEObject(selection);
		if (!(selectedEObject instanceof Layout)) {
			return false;
		}
		Layout source = (Layout) selectedEObject;
		return QueryUtil.matches(source, GridLayout.class, false) || QueryUtil.matches(source, PropertiesLayout.class);
	}

}
