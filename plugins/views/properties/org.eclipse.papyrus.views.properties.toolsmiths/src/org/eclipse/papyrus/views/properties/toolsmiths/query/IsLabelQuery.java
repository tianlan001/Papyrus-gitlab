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
import org.eclipse.papyrus.infra.properties.ui.StandardWidget;
import org.eclipse.swt.widgets.Label;

/**
 * <p>
 * Test if a {@link StandardWidget} is a {@link Label}.
 * </p>
 *
 * <strong>OCL</strong>:
 * <code>self.widgetType.widgetClass = 'Label' and widgetType.namespace.oclIsUndefined()</code>
 */
public class IsLabelQuery implements JavaQuery {

	@Override
	public boolean match(Object selection) {
		EObject selectedEObject = EMFHelper.getEObject(selection);
		if (!(selectedEObject instanceof StandardWidget)) {
			return false;
		}

		StandardWidget source = (StandardWidget) selectedEObject;
		return Label.class.getSimpleName().equals(source.getWidgetType().getWidgetClass())
				&& source.getWidgetType().getNamespace() == null;
	}

}
