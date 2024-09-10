/*****************************************************************************
 * Copyright (c) 2013 CEA
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
 *   Vincent Lorenzo (CEA LIST)
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors.richtext;

import java.net.URL;

import org.eclipse.nebula.widgets.richtext.toolbar.ToolbarButton;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

/**
 * 
 * Abstract action for the richtext editor
 * 
 * @since 2.0
 */
public abstract class AbstractToolbarButton extends ToolbarButton implements DisposeListener {

	/**
	 * the referenced richtext editor
	 */
	protected GenericRichTextEditor richTextEditor;

	/**
	 * 
	 * Constructor.
	 * @param buttonName
	 *            the name of the button
	 * @param commandName
	 *            the name of the command
	 * @param buttonLabel
	 *            the label of the button
	 * @param toolbar
	 *            the id of the toolbar
	 * @param iconURL
	 *            the url of the icon
	 */
	public AbstractToolbarButton(final String buttonName, final String commandName, final String buttonLabel, final String toolbar, final URL iconURL) {
		super(buttonName, commandName, buttonLabel, toolbar, iconURL);
	}

	/**
	 * 
	 * @param editor
	 *            the richtext editor used to apply the action
	 */
	public void setRichTextEditor(final GenericRichTextEditor editor) {
		this.richTextEditor = editor;
		this.richTextEditor.addDisposeListener(this);
	}

	/**
	 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
	 *
	 * @param e
	 */
	@Override
	public void widgetDisposed(DisposeEvent e) {
		this.richTextEditor.removeDisposeListener(this);
		this.richTextEditor = null;
	}
}
