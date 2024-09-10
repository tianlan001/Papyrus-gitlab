/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.swt.widgets.Composite;

/**
 * A PropertyEditor for editing floats through a Text field
 *
 * @see org.eclipse.papyrus.infra.widgets.editors.FloatEditor
 *
 * @author Camille Letavernier
 */
public class FloatEditor extends AbstractPropertyEditor {

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public FloatEditor(Composite parent, int style) {
		super(new org.eclipse.papyrus.infra.widgets.editors.FloatEditor(parent, style));
	}

}
