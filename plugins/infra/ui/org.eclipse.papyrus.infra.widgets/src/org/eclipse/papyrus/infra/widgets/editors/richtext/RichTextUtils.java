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

package org.eclipse.papyrus.infra.widgets.editors.richtext;

import java.util.Iterator;
import java.util.List;

/**
 * @author Vincent Lorenzo
 * @since 2.0
 * 
 *        This class provides strings known by the ckeditor
 */
public class RichTextUtils {

	/**
	 * Constructor.
	 *
	 */
	private RichTextUtils() {
		// to prevent instanciation
	}

	/**
	 * all items known by the ckeditor (coming from http://s1.ckeditor.com/sites/default/files/uploads/Complete%20List%20of%20Toolbar%20Items%20for%20CKEditor.pdf)
	 */
	public static final String ITEM_SOURCE = "Source"; //$NON-NLS-1$
	public static final String ITEM_SAVE = "Save"; //$NON-NLS-1$
	public static final String ITEM_NEWPAGE = "NewPage"; //$NON-NLS-1$
	public static final String ITEM_DOCPROPS = "DocProps"; //$NON-NLS-1$
	public static final String ITEM_PREVIEW = "Preview"; //$NON-NLS-1$
	public static final String ITEM_PRINT = "Print"; //$NON-NLS-1$
	public static final String ITEM_TEMPLATES = "Templates"; //$NON-NLS-1$
	public static final String ITEM_DOCUMENT = "document"; //$NON-NLS-1$

	public static final String ITEM_CUT = "Cut"; //$NON-NLS-1$
	public static final String ITEM_COPY = "Copy"; //$NON-NLS-1$
	public static final String ITEM_PASTE = "Paste"; //$NON-NLS-1$
	public static final String ITEM_PASTETEXT = "PasteText"; //$NON-NLS-1$
	public static final String ITEM_PASTEFROMWORD = "PasteFromWord"; //$NON-NLS-1$
	public static final String ITEM_UNDO = "Undo"; //$NON-NLS-1$
	public static final String ITEM_REDO = "Redo"; //$NON-NLS-1$

	public static final String ITEM_FIND = "Find"; //$NON-NLS-1$
	public static final String ITEM_REPLACE = "Replace"; //$NON-NLS-1$
	public static final String ITEM_SELECTALL = "SelectAll"; //$NON-NLS-1$
	public static final String ITEM_SCAYT = "Scayt"; //$NON-NLS-1$

	public static final String ITEM_FORM = "Form"; //$NON-NLS-1$
	public static final String ITEM_CHECKBOX = "Checkbox"; //$NON-NLS-1$
	public static final String ITEM_RADIO = "Radio"; //$NON-NLS-1$
	public static final String ITEM_TEXTFIELD = "TextField"; //$NON-NLS-1$
	public static final String ITEM_TEXTAREA = "Textarea"; //$NON-NLS-1$
	public static final String ITEM_SELECT = "Select"; //$NON-NLS-1$
	public static final String ITEM_BUTTON = "Button"; //$NON-NLS-1$
	public static final String ITEM_IMAGEBUTTON = "ImageButton"; //$NON-NLS-1$
	public static final String ITEM_HIDDENFIELD = "HiddenField"; //$NON-NLS-1$

	public static final String ITEM_BOLD = "Bold"; //$NON-NLS-1$
	public static final String ITEM_ITALIC = "Italic"; //$NON-NLS-1$
	public static final String ITEM_UNDERLINE = "Underline"; //$NON-NLS-1$
	public static final String ITEM_STRIKE = "Strike"; //$NON-NLS-1$
	public static final String ITEM_SUBSCRIPT = "Subscript"; //$NON-NLS-1$
	public static final String ITEM_SUPERSCRIPT = "Superscript"; //$NON-NLS-1$
	public static final String ITEM_REMOVEFORMAT = "RemoveFormat"; //$NON-NLS-1$

	public static final String ITEM_NUMBEREDLIST = "NumberedList"; //$NON-NLS-1$
	public static final String ITEM_BULLETEDLIST = "BulletedList"; //$NON-NLS-1$
	public static final String ITEM_OUTDENT = "Outdent"; //$NON-NLS-1$
	public static final String ITEM_INDENT = "Indent"; //$NON-NLS-1$
	public static final String ITEM_BLOCKQUOTE = "Blockquote"; //$NON-NLS-1$
	public static final String ITEM_CREATEDIV = "CreateDiv"; //$NON-NLS-1$
	public static final String ITEM_JUSTIFYLEFT = "JustifyLeft"; //$NON-NLS-1$
	public static final String ITEM_JUSTIFYCENTER = "JustifyCenter"; //$NON-NLS-1$
	public static final String ITEM_JUSTIFYRIGHT = "JustifyRight"; //$NON-NLS-1$
	public static final String ITEM_JUSTIFYBLOCK = "JustifyBlock"; //$NON-NLS-1$
	public static final String ITEM_BIDILTR = "BidiLtr"; //$NON-NLS-1$
	public static final String ITEM_BIDIRTL = "BidiRtl"; //$NON-NLS-1$

	public static final String ITEM_LINK = "Link"; //$NON-NLS-1$
	public static final String ITEM_UNLINK = "Unlink"; //$NON-NLS-1$
	public static final String ITEM_ANCHOR = "Anchor"; //$NON-NLS-1$

	public static final String ITEM_CREATEPLACEHOLDER = "CreatePlaceholder"; //$NON-NLS-1$
	public static final String ITEM_IMAGE = "Image"; //$NON-NLS-1$
	public static final String ITEM_FLASH = "Flash"; //$NON-NLS-1$
	public static final String ITEM_TABLE = "Table"; //$NON-NLS-1$
	public static final String ITEM_HORIZONTALRULE = "HorizontalRule"; //$NON-NLS-1$
	public static final String ITEM_SMILEY = "Smiley"; //$NON-NLS-1$
	public static final String ITEM_SPECIALCHAR = "SpecialChar"; //$NON-NLS-1$
	public static final String ITEM_PAGEBREAK = "PageBreak"; //$NON-NLS-1$
	public static final String ITEM_IFRAME = "Iframe"; //$NON-NLS-1$
	public static final String ITEM_INSERTPRE = "InsertPre"; //$NON-NLS-1$

	public static final String ITEM_STYLES = "Styles"; //$NON-NLS-1$
	public static final String ITEM_FORMAT = "Format"; //$NON-NLS-1$
	public static final String ITEM_FONT = "Font"; //$NON-NLS-1$
	public static final String ITEM_FONTSIZE = "FontSize"; //$NON-NLS-1$

	public static final String ITEM_TEXTCOLOR = "TextColor"; //$NON-NLS-1$
	public static final String ITEM_BGCOLOR = "BGColor"; //$NON-NLS-1$

	public static final String ITEM_UICOLOR = "UIColor"; //$NON-NLS-1$
	public static final String ITEM_MAXIMIZE = "Maximize"; //$NON-NLS-1$
	public static final String ITEM_SHOWBLOCKS = "ShowBlocks"; //$NON-NLS-1$

	public static final String ITEM_BUTTON1 = "button1"; //$NON-NLS-1$
	public static final String ITEM_BUTTON2 = "button2"; //$NON-NLS-1$
	public static final String ITEM_BUTTON3 = "button3"; //$NON-NLS-1$
	public static final String ITEM_OEMBED = "oembed"; //$NON-NLS-1$
	public static final String ITEM_MEDIAEMBED = "MediaEmbed"; //$NON-NLS-1$

	public static final String ITEM_ABOUT = "About"; //$NON-NLS-1$

	/**
	 * Toolbar groups
	 */
	public static final String TOOLBAR_GROUP_FIND = "find"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_SOURCE = "source";//$NON-NLS-1$
	public static final String TOOLBAR_GROUP_CLIPBOARD = "clipboard"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_OTHER = "other"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_PARAGRAPH = "paragraph"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_COLORS = "colors"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_LINKS = "links"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_INSERT = "insert"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_STYLES = "styles"; //$NON-NLS-1$
	public static final String TOOLBAR_GROUP_BASICSTYLES = "basicstyles"; //$NON-NLS-1$


	/**
	 * Items added by Papyrus ? (not found in the documentation of Ckeditor...
	 */
	/** mode is the button to display the html source code or the result */
	public static final String ITEM_MODE = "mode";//$NON-NLS-1$
	public static final String ITEM_BASICSTYLES = "basicstyles";//$NON-NLS-1$
	public static final String ITEM_CLEANUP = "cleanup";//$NON-NLS-1$
	public static final String ITEM_LIST = "list";//$NON-NLS-1$
	public static final String ITEM_ALIGN = "align";//$NON-NLS-1$
	public static final String ITEM_CLIPBOARD = "clipboard";//$NON-NLS-1$

	/**
	 * the strings used to create a toolbar configuration
	 */
	public static final String GROUPS = "groups"; //$NON-NLS-1$
	public static final String SPACE = " "; //$NON-NLS-1$
	public static final String COLON = ":"; //$NON-NLS-1$
	public static final String SINGLE_QUOTE = "'"; //$NON-NLS-1$
	public static final String COMMA = ","; //$NON-NLS-1$
	public static final String OPENING_BRACKET = "["; //$NON-NLS-1$
	public static final String CLOSING_BRACKET = "]"; //$NON-NLS-1$
	public static final String OPENING_BRACE = "{"; //$NON-NLS-1$
	public static final String CLOSING__BRACE = "}"; //$NON-NLS-1$
	public static final String NAME = "name"; //$NON-NLS-1$
	public static final String SEPARATOR = "-"; //$NON-NLS-1$
	public static final String NEW_LINE = "'/'"; //$NON-NLS-1$
	public static final String SEMICOLON = ";";//$NON-NLS-1$
	// public static final String create

	/**
	 * 
	 * @param groupName
	 *            the name of the group to create
	 * @param items
	 *            the items for the groups
	 * @return
	 * 		the javascript string to use to create this group
	 */
	public static final String createGroups(final String groupName, final List<String> items) {
		StringBuilder builder = new StringBuilder();
		// starts the group
		if (groupName != null && !groupName.isEmpty()) {
			builder.append(OPENING_BRACE);
			builder.append(SPACE);
			builder.append(NAME);
			builder.append(COLON);
			builder.append(SPACE);
			builder.append(SINGLE_QUOTE);
			builder.append(groupName);
			builder.append(SINGLE_QUOTE);
		}

		if (items != null && items.size() > 0) {
			builder.append(COMMA);
			builder.append(SPACE);
			builder.append(GROUPS);
			builder.append(COLON);
			builder.append(SPACE);
			builder.append(OPENING_BRACKET);
			Iterator<String> iter = items.iterator();
			while (iter.hasNext()) {
				String current = iter.next();
				builder.append(SPACE);
				builder.append(SINGLE_QUOTE);
				builder.append(current);
				builder.append(SINGLE_QUOTE);
				if (iter.hasNext()) {
					builder.append(COMMA);
				}
			}
			builder.append(SPACE);
			builder.append(CLOSING_BRACKET);
		}
		builder.append(SPACE);
		builder.append(CLOSING__BRACE);
		return builder.toString();
	}
}
