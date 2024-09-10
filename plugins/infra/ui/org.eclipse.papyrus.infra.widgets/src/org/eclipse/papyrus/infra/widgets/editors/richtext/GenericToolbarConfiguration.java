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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors.richtext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.nebula.widgets.richtext.toolbar.ToolbarButton;
import org.eclipse.nebula.widgets.richtext.toolbar.ToolbarConfiguration;
import org.eclipse.swt.browser.BrowserFunction;

/**
 * This configuration removes several items of the ckeditor and add default Papyrus button
 * 
 * 
 * @since 2.0
 *
 */
public class GenericToolbarConfiguration extends ToolbarConfiguration {

	/**
	 * the button to initialize when the editor whill be created
	 */
	private List<AbstractToolbarButton> buttons = new ArrayList<AbstractToolbarButton>();

	/**
	 * Constructor.
	 *
	 */
	public GenericToolbarConfiguration() {
		removeDefaultToolbarButton(RichTextUtils.ITEM_FLASH);
		removeDefaultToolbarButton(RichTextUtils.ITEM_TABLE);
		removeDefaultToolbarButton(RichTextUtils.ITEM_HORIZONTALRULE);
		removeDefaultToolbarButton(RichTextUtils.ITEM_SMILEY);
		removeDefaultToolbarButton(RichTextUtils.ITEM_SPECIALCHAR);
		removeDefaultToolbarButton(RichTextUtils.ITEM_PAGEBREAK);
		removeDefaultToolbarButton(RichTextUtils.ITEM_IFRAME);

		// we add default button
		registerDefaultButton();
	}

	/**
	 * this method allows to register default Papyrus button
	 */
	protected void registerDefaultButton() {
		addSpellCheckToolbarButton();
	}

	/**
	 * create and add the spell check button
	 */
	protected void addSpellCheckToolbarButton() {
		SpellCheckToolbarButton action = new SpellCheckToolbarButton();
		addToolbarButton(action);
	}

	/**
	 * @see org.eclipse.nebula.widgets.richtext.toolbar.ToolbarConfiguration#addToolbarButton(org.eclipse.nebula.widgets.richtext.toolbar.ToolbarButton, org.eclipse.swt.browser.BrowserFunction)
	 *
	 * @param button
	 * @param function
	 */
	@Override
	public void addToolbarButton(ToolbarButton button, BrowserFunction function) {
		if (button instanceof AbstractToolbarButton) {
			this.buttons.add((AbstractToolbarButton) button);
		}
		super.addToolbarButton(button, function);
	}

	/**
	 * 
	 * @param editor
	 *            the instanciaated generic richtext editor
	 */
	public void setRichTextEditor(GenericRichTextEditor editor) {
		for (AbstractToolbarButton current : this.buttons) {
			current.setRichTextEditor(editor);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 *
	 * @return
	 */
	protected String getToolbarGroupConfiguration() {

		// String configuration = "CKEDITOR.config.toolbarGroups = ["//$NON-NLS-1$
		// + "{ name: 'source', groups: [ 'mode' ] }," // we add mode//$NON-NLS-1$
		// + "{ name: 'clipboard', groups: [ 'clipboard', 'undo', 'find' ] },"//$NON-NLS-1$
		// + "{ name: 'other' },"//$NON-NLS-1$
		// + "'/',"//$NON-NLS-1$
		// + "{ name: 'paragraph', groups: [ 'list', 'indent', 'align' ] },"//$NON-NLS-1$
		// + "{ name: 'colors' },"//$NON-NLS-1$
		// + "{ name: 'links' },"// we add links//$NON-NLS-1$
		// + "{ name: 'insert' }," // we add links//$NON-NLS-1$
		// + "'/',"//$NON-NLS-1$
		// + "{ name: 'styles' },"//$NON-NLS-1$
		// + "{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] }"//$NON-NLS-1$
		// + "] ;";//$NON-NLS-1$
		//
		// return configuration;
		return createToolbarGroupConfiguration();
	}

	protected String createToolbarGroupConfiguration() {
		// 1. we start the string
		StringBuilder builder = new StringBuilder("CKEDITOR.config.toolbarGroups = ");//$NON-NLS-1$
		builder.append(RichTextUtils.OPENING_BRACKET);

		// we add the builed string in lower case, because with the first letter in upper case, it doesn't work...

		// 2. we add the source group
		builder.append(createSourceGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);
		// 3. we add the clipboard group
		builder.append(createClipboardGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);
		// 4. we add the group for others elements
		builder.append(createOtherGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);

		// 5. new line
		builder.append(RichTextUtils.NEW_LINE);
		builder.append(RichTextUtils.COMMA);

		// 5. we create the paragraph group
		builder.append(createParagraphGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);
		// 6. we create the color group
		builder.append(createColorsGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);

		// 7. we create the links group
		builder.append(createLinksGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);
		// 8. we create the insert group
		builder.append(createInsertGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);

		// 9. new line
		builder.append(RichTextUtils.NEW_LINE);
		builder.append(RichTextUtils.COMMA);

		// 10. we crete the style group
		builder.append(createStylesGroup().toLowerCase());
		builder.append(RichTextUtils.COMMA);
		// 11. we crete the basic style group
		builder.append(createBasicStylesGroup().toLowerCase());

		// we ends the declatation of the string to use to configuration the toolbar
		builder.append(RichTextUtils.CLOSING_BRACKET);
		builder.append(RichTextUtils.SPACE);
		builder.append(RichTextUtils.SEMICOLON);

		return builder.toString();
	}

	/**
	 * 
	 * @return
	 * 		the string to create the source group
	 */
	protected String createSourceGroup() {
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_SOURCE, Collections.singletonList(RichTextUtils.ITEM_MODE));
	}

	/**
	 * 
	 * @return
	 * 		the string to create the clipboard group
	 */
	protected String createClipboardGroup() {
		final List<String> clipboard = new ArrayList<String>();
		clipboard.add(RichTextUtils.ITEM_CLIPBOARD);
		clipboard.add(RichTextUtils.ITEM_UNDO);
		clipboard.add(RichTextUtils.ITEM_FIND);
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_CLIPBOARD, clipboard).toLowerCase();// doesn't work keeping the first char in upper case!
	}

	/**
	 * 
	 * @return
	 * 		the string to create the other group
	 */
	protected String createOtherGroup() {
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_OTHER, null);
	}

	/**
	 * 
	 * @return
	 * 		the string to create the paragraph group
	 */
	protected String createParagraphGroup() {
		List<String> paragraph = new ArrayList<String>();
		paragraph.add(RichTextUtils.ITEM_LIST);
		paragraph.add(RichTextUtils.ITEM_INDENT);
		paragraph.add(RichTextUtils.ITEM_ALIGN);
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_PARAGRAPH, paragraph);
	}

	/**
	 * 
	 * @return
	 * 		the string to create the colors group
	 */
	protected String createColorsGroup() {
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_COLORS, null);
	}

	/**
	 * 
	 * @return
	 * 		the string to create the links group
	 */
	protected String createLinksGroup() {
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_LINKS, null);
	}

	/**
	 * 
	 * @return
	 * 		the string to create the insert group
	 */
	protected String createInsertGroup() {
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_INSERT, null);
	}

	/**
	 * 
	 * @return
	 * 		the string to create the style group
	 */
	protected String createStylesGroup() {
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_STYLES, null);
	}

	/**
	 * 
	 * @return
	 * 		the string to create the basic style group
	 */
	protected String createBasicStylesGroup() {
		List<String> basicstyles = new ArrayList<String>();
		basicstyles.add(RichTextUtils.ITEM_BASICSTYLES);
		basicstyles.add(RichTextUtils.ITEM_CLEANUP);
		return RichTextUtils.createGroups(RichTextUtils.TOOLBAR_GROUP_BASICSTYLES, basicstyles);
	}

}
