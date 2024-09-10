/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * A PropertyEditor for displaying strings as a CLabel
 * This editor is read-only
 *
 * @see org.eclipse.papyrus.infra.widgets.editors.StringLabel
 *
 * @author Camille Letavernier
 */
public class StringLabel extends AbstractPropertyEditor {

	private org.eclipse.papyrus.infra.widgets.editors.StringLabel editor;

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public StringLabel(Composite parent, int style) {
		editor = createStringLabel(parent, style);
		setEditor(editor);
	}

	/**
	 * Creates the string label dialog.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return The string label dialog.
	 */
	protected org.eclipse.papyrus.infra.widgets.editors.StringLabel createStringLabel(Composite parent, int style) {
		return new org.eclipse.papyrus.infra.widgets.editors.StringLabel(parent, style);
	}

	@Override
	protected void doBinding() {
		ILabelProvider labelProvider = input.getLabelProvider(propertyPath);
		editor.setLabelProvider(labelProvider);
		super.doBinding();
	}
}
