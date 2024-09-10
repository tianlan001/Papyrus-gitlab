/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * This class provides useful methods for StyledText
 *
 * @since 3.6
 */
public class StyledTextUtils {

	// TODO : use GUI helper
	/**
	 * The color used as background (a light blue)
	 */
	private static final Color STYLED_TEXT_BACKGROUND_COLOR = new Color(Display.getDefault(), new RGB(232, 242, 254));

	/**
	 * the color used as foreground
	 */
	private static final Color STYLED_TEXT_FOREGROUND = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);

	/**
	 * This method add line number support for StyledText used in Xtext context
	 * (usage of {@link StyleRange} instead of {@link LineStyleListener}
	 *
	 * @param styledText
	 *            a styled text editor
	 */
	public static final void addLineNumberSupportForXtext(final StyledText styledText) {
		configureLineNumberDisplay(styledText);
		styledText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				configureLineNumberDisplay(styledText);
			}
		});
	}

	/**
	 * This method configure the editor to display the line numbers
	 *
	 * @param styledText
	 *            a styled text editor
	 */
	private static void configureLineNumberDisplay(final StyledText styledText) {
		int maxLine1 = styledText.getLineCount();

		StyleRange style = new StyleRange();
		style.foreground = StyledTextUtils.STYLED_TEXT_FOREGROUND;
		style.background = StyledTextUtils.STYLED_TEXT_BACKGROUND_COLOR;

		// 3. get the width required to display the last line number
		int bulletLength = Integer.toString(maxLine1).length();

		// Width of numbers character is half the height in monospaced font, add 1 character width for right padding.
		int bulletWidth = (bulletLength + 1) * styledText.getLineHeight() / 2;
		style.metrics = new GlyphMetrics(0, 0, bulletWidth);
		Bullet bullet1 = new Bullet(ST.BULLET_NUMBER, style);
		styledText.setLineBullet(0, styledText.getLineCount(), null);
		styledText.setLineBullet(0, styledText.getLineCount(), bullet1);
	}


	/**
	 * This method allows to display the line number in the text editor.
	 * This method breaks Xtext editor (lost of red underline and others in case of errors), because XText uses {@link StyleRange}
	 * and not {@link LineStyleListener}
	 *
	 * @see StyledText documentation
	 *
	 * @since 1.4
	 */
	public static final void addLineNumberSupport(final StyledText styledText) {
		styledText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				// required to update properly line number display
				styledText.redraw();
			}

		});

		styledText.addLineStyleListener(new LineStyleListener() {
			@Override
			public void lineGetStyle(LineStyleEvent event) {
				// 1. create a new style range
				StyleRange styleRange = new StyleRange();

				// 2. define colors
				styleRange.foreground = STYLED_TEXT_FOREGROUND;
				styleRange.background = STYLED_TEXT_BACKGROUND_COLOR;


				// 3. get the width required to display the last line number
				int maxLine = styledText.getLineCount();
				int bulletLength = Integer.toString(maxLine).length();

				// Width of numbers character is half the height in monospaced font, add 1 character width for right padding.
				int bulletWidth = (bulletLength + 1) * styledText.getLineHeight() / 2;
				styleRange.metrics = new GlyphMetrics(0, 0, bulletWidth);
				event.bullet = new Bullet(ST.BULLET_TEXT, styleRange);

				int bulletLine = styledText.getLineAtOffset(event.lineOffset) + 1;
				event.bullet.text = String.format("%" + bulletLength + "s", bulletLine); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});
	}


}
