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

import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.constraints.environment.ConstraintType;
import org.eclipse.papyrus.infra.properties.environment.LayoutType;
import org.eclipse.papyrus.infra.properties.environment.PropertyEditorType;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.widgets.CustomizablePropertyEditor;
import org.eclipse.swt.widgets.Layout;

/**
 * <p>
 * Helper methods for comparing Properties Model Element with their referenced Java Class.
 * </p>
 */
public class QueryUtil {

	/**
	 * Test if the specified {@link SimpleConstraint} descriptor uses the specified {@link Constraint} class.
	 *
	 * @param constraintDescriptor
	 * @param constraintClass
	 * @return
	 */
	public static boolean matches(SimpleConstraint constraintDescriptor, Class<? extends Constraint> constraintClass) {
		ConstraintType constraintType = constraintDescriptor.getConstraintType();
		return constraintType != null && constraintClass.getName().equals(constraintType.getConstraintClass());
	}

	/**
	 * Test if the specified {@link PropertyEditor} descriptor uses the specified {@link CustomizablePropertyEditor} class.
	 *
	 * @param propertyEditor
	 * @param propertyEditorClass
	 * @return
	 */
	public static boolean matches(PropertyEditor propertyEditor, Class<? extends CustomizablePropertyEditor> propertyEditorClass) {
		PropertyEditorType widgetType = propertyEditor.getWidgetType();
		return widgetType != null && widgetType.getNamespace() != null
				&& propertyEditorClass.getSimpleName().equals(widgetType.getWidgetClass())
				&& propertyEditorClass.getPackageName().equals(widgetType.getNamespace().getValue());
	}

	/**
	 * Test if the specified {@link org.eclipse.papyrus.infra.properties.ui.Layout} descriptor uses the specified {@link Layout} class.
	 * If the layout class is a predefined SWT Layout, use {@link #matches(org.eclipse.papyrus.infra.properties.ui.Layout, Class, false)} instead,
	 * as the SWT/JFace Controls or Layout do not use Namespaces in XWT.
	 *
	 * @param layout
	 * @param layoutClass
	 * @return
	 */
	public static boolean matches(org.eclipse.papyrus.infra.properties.ui.Layout layout, Class<? extends Layout> layoutClass) {
		return matches(layout, layoutClass, true);
	}

	/**
	 *
	 * @param layout
	 * @param layoutClass
	 * @param withNamespace
	 *            Should be <code>true</code> for all Layouts, except for predefined SWT Layouts, which shouldn't have a Namespace definition
	 * @return
	 */
	public static boolean matches(org.eclipse.papyrus.infra.properties.ui.Layout layout, Class<? extends Layout> layoutClass, boolean withNamespace) {
		LayoutType layoutType = layout.getLayoutType();
		if (layoutType == null || !layoutClass.getSimpleName().equals(layoutType.getWidgetClass())) {
			return false;
		}
		if (withNamespace) {
			return layoutType.getNamespace() != null && layoutClass.getPackageName().equals(layoutType.getNamespace().getValue());
		} else {
			return layoutType.getNamespace() == null;
		}
	}

}
