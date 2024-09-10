/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.ui.editors;

import org.eclipse.nebula.widgets.richtext.RichTextEditor;
import org.eclipse.papyrus.infra.widgets.editors.richtext.CheckSpellDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Vincent Lorenzo
 *         This dialog removes the UML references before checking the text
 */
public class UMLSpellCheckDialog extends CheckSpellDialog {

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param richText
	 */
	public UMLSpellCheckDialog(Shell parent, RichTextEditor richText) {
		super(parent, richText);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.editors.richtext.CheckSpellDialog#cleanText(java.lang.String)
	 *
	 * @param text
	 * @return
	 */
	@Override
	protected String[] cleanText(String text) {
		// remove all @link
		String withoutUMLReferences = text.replaceAll("\\{@link #_[a-zA-Z0-9-\\--_]*\\}", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return super.cleanText(withoutUMLReferences);
	}

}
