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
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.widgets.BooleanToggle;

/**
 * <p>
 * Test if a {@link PropertyEditor} is an {@link BooleanToggle}.
 * </p>
 *
 * <strong>OCL</strong>:
 * <code>widgetType.widgetClass = 'BooleanToggle' and widgetType.namespace.name = 'ppe'</code>
 */
//
public class IsToggleButtonQuery implements JavaQuery {

	@Override
	public boolean match(Object selection) {
		EObject selectedEObject = EMFHelper.getEObject(selection);
		if (!(selectedEObject instanceof PropertyEditor)) {
			return false;
		}
		PropertyEditor source = (PropertyEditor) selectedEObject;
		return QueryUtil.matches(source, BooleanToggle.class);
	}

}
