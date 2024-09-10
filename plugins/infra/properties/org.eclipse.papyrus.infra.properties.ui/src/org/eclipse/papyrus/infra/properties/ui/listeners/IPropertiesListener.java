/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation, Bug 522124
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.listeners;

import java.util.Set;

import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;

/**
 * Interface that handles behavior for a specific editor.
 *
 * @since 3.1
 */
public interface IPropertiesListener {

	/**
	 * Handle the behavior required for a editor according to the input and property paths.
	 *
	 * @param editor
	 *            the property editor.
	 * @param input
	 *            the current input.
	 * @param listeningPropertyPaths
	 *            list of properties listened by the editor.
	 */
	public void handle(final AbstractPropertyEditor editor, final DataSource input, final Set<String> listeningPropertyPaths);

}
