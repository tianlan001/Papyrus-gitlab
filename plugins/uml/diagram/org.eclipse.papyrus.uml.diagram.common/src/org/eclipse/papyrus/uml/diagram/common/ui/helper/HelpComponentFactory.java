/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.ui.helper;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

/**
 * A factory for creating HelpComponent object.
 */
public class HelpComponentFactory {

	/** The help image by its image descriptor */
	protected final static ImageDescriptor helpImageDescriptor = Activator.imageDescriptorFromPlugin(Activator.ID, "icons/help/help_contents-1.gif"); //$NON-NLS-1$

	/**
	 * Creates help component that insert a new hyperlink icon to display
	 * additional help description if tooltip is null, default value is "Help"
	 *
	 * ParseTags parameter must be used if you want to define rich text content
	 * for the helpText. For example use <form> tag to format your text
	 *
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 * @param helpText
	 *            the help text
	 * @param pToolTip
	 *            the tool tip
	 * @param parseTags
	 *            enables tags parsing on the description content
	 * @return the hyperlink image
	 */
	public static ImageHyperlink createHelpComponent(final Composite parent, FormToolkit toolkit, final String helpText, String pToolTip, final boolean parseTags) {
		ImageHyperlink helpImage = toolkit.createImageHyperlink(parent, SWT.NONE);
		helpImage.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(helpImageDescriptor));
		helpImage.setBackground(null);
		final String toolTip = (pToolTip == null) ? "Help" : pToolTip;
		helpImage.setToolTipText(toolTip);
		helpImage.addHyperlinkListener(new HyperlinkAdapter() {

			// create the HelpDialog that displays the help description
			@Override
			public void linkActivated(HyperlinkEvent e) {
				HelpDialog dialog = new HelpDialog(parent.getShell(), Display.getDefault().getCursorLocation(), toolTip, helpText, parseTags);
				dialog.open();
			}

		});
		return helpImage;
	}

	/**
	 * Creates a new HelpComponent object. ParseTags parameter must be used if
	 * you want to define rich text content for the helpText. For example use
	 * <form> tag to format your text
	 *
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 * @param helpText
	 *            the help text
	 * @param parseTags
	 *            enables tags parsing on the description content
	 * @return the image hyperlink
	 */
	public static ImageHyperlink createHelpComponent(final Composite parent, FormToolkit toolkit, final String helpText, boolean parseTags) {
		return createHelpComponent(parent, toolkit, helpText, null, parseTags);
	}

	/**
	 * Creates a new HelpComponent object. You must use method with parseTags
	 * parameter if you want to define rich text content for the helpText
	 *
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 * @param helpText
	 *            the help text
	 * @return the image hyperlink
	 */
	public static ImageHyperlink createHelpComponent(final Composite parent, FormToolkit toolkit, final String helpText) {
		return createHelpComponent(parent, toolkit, helpText, null, false);
	}

}
