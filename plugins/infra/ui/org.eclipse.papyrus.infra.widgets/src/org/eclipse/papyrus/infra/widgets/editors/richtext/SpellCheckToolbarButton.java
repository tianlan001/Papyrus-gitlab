/*****************************************************************************
 * Copyright (c) 2013, 2018 CEA
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
 *   Soyatec - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 535055
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors.richtext;

import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.swt.widgets.Display;


/**
 * @author Nguyen
 * @since 2.0
 * 
 */
public class SpellCheckToolbarButton extends AbstractToolbarButton {

	/**
	 * the name of the button
	 */
	private static final String BUTTON_NAME = "SpellCheckButton"; //$NON-NLS-1$

	/**
	 * the name of the command
	 */
	private static final String BUTTON_COMMAND_NAME = "SpellCheckCommand"; //$NON-NLS-1$

	/**
	 * the label of the button
	 */
	private static final String BUTTON_LABEL = Messages.SpellCheckToolbarButton_CheckSpellingToolTip;

	/**
	 * the path of the icon used for the button
	 */
	private static final String ICON_PATH = "icons/SpellCheck.gif"; //$NON-NLS-1$

	/**
	 * the check spell dialog
	 */
	protected CheckSpellDialog dialog;

	/**
	 * Creates a new instance.
	 */
	public SpellCheckToolbarButton() {
		super(BUTTON_NAME, BUTTON_COMMAND_NAME, BUTTON_LABEL, RichTextUtils.TOOLBAR_GROUP_OTHER, Activator.getDefault().getURL(ICON_PATH)); // $NON-NLS-1$
	}

	/**
	 * @see org.eclipse.nebula.widgets.richtext.toolbar.ToolbarButton#execute()
	 *
	 * @return
	 */
	@Override
	public Object execute() {
		if(null==this.richTextEditor) {
			Activator.log.warn("The action can't be executed, because the richtext editor has not bee registered for this action");// $NON-NLS-1$
		}
		if (this.richTextEditor != null) {
			try {
				this.dialog = createCheckSpellDialog();
				this.dialog.open();
			} catch (Exception e) {
				Activator.log.error(e);
			}
		}

		return null;
	}

	/**
	 * 
	 * @return
	 * 		the dialog used to check text
	 */
	protected CheckSpellDialog createCheckSpellDialog() {
		return new CheckSpellDialog(Display.getCurrent().getActiveShell(), this.richTextEditor);
	}
}
