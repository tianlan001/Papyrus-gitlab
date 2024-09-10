/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.ui.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.papyrus.uml.decoratormodel.internal.ui.messages.Messages;
import org.eclipse.swt.widgets.Composite;

/**
 * An enumeration of possible settings of "when" type preferences.
 */
public enum WhenKind {
	PROMPT, NEVER, ALWAYS;

	public static FieldEditor createFieldEditor(String preferenceName, String labelText, Composite parent) {
		return new RadioGroupFieldEditor(preferenceName, labelText, 3, //
				new String[][] { { Messages.WhenKind_0, ALWAYS.name() }, { Messages.WhenKind_1, NEVER.name() }, { Messages.WhenKind_2, PROMPT.name() } }, //
				parent);
	}
}
