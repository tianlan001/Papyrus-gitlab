/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.papyrus.infra.widgets.editors.IconValueEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * The property editor for Icon with popup.
 * @since 2.0
 */
public class IconPropertyEditor extends AbstractPropertyEditor {

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public IconPropertyEditor(final Composite parent, final int style) {
		setEditor(new IconValueEditor(parent, style));
	}

}
