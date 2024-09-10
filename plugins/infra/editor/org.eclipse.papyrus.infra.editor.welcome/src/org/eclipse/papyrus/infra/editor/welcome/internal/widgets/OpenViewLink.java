/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.editor.welcome.internal.widgets;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.HyperlinkGroup;
import org.eclipse.ui.forms.HyperlinkSettings;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.views.IViewDescriptor;

/**
 * A composite that shows a hyperlink to a view to open, if that view exists.
 */
public class OpenViewLink extends Composite implements IHyperlinkListener {
	private static final String KEY_HLINK_GROUP = "hlinkGroup"; //$NON-NLS-1$

	private ImageHyperlink link;

	private Image icon = null;

	public OpenViewLink(Composite parent, int style) {
		super(parent, style);

		GridLayoutFactory.fillDefaults().applyTo(this);

		link = new ImageHyperlink(this, SWT.WRAP);
		getHyperlinkGroup(parent).add(link);
		link.addHyperlinkListener(this);
	}

	@Override
	public void dispose() {
		super.dispose();

		if (icon != null) {
			icon.dispose();
			icon = null;
		}

		link = null;
	}

	static HyperlinkGroup getHyperlinkGroup(Composite parent) {
		HyperlinkGroup result = (HyperlinkGroup) parent.getData(KEY_HLINK_GROUP);

		if (result == null) {
			result = new HyperlinkGroup(parent.getDisplay());
			result.setHyperlinkUnderlineMode(HyperlinkSettings.UNDERLINE_ALWAYS);
			parent.setData(KEY_HLINK_GROUP, result);
		}

		return result;
	}

	public String getLabel() {
		return link.getText();
	}

	public void setLabel(String label) {
		if (label == null) {
			label = "";
		}

		link.setText(label);

		layout(true);
	}

	public String getViewID() {
		return (String) link.getHref();
	}

	public void setViewID(String viewID) {
		link.setHref(viewID);

		if (icon != null) {
			link.setImage(null);
			icon.dispose();
		}

		// Look for a new icon
		IViewDescriptor[] views = PlatformUI.getWorkbench().getViewRegistry().getViews();
		for (IViewDescriptor view : views) {
			if (viewID.equals(view.getId())) {
				ImageDescriptor iconDesc = view.getImageDescriptor();
				if (iconDesc != null) {
					icon = iconDesc.createImage(getDisplay());
					link.setImage(icon);
				}
				break;
			}
		}
	}

	private IWorkbenchPage getWorkbenchPage() {
		IWorkbenchPage result = null;

		IWorkbench bench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = bench.getActiveWorkbenchWindow();
		if (window == null) {
			if (bench.getWorkbenchWindowCount() > 0) {
				window = bench.getWorkbenchWindows()[0];
			}
		}
		if (window != null) {
			result = window.getActivePage();
			if (result == null) {
				IWorkbenchPage[] pages = window.getPages();
				if (pages.length > 0) {
					result = pages[0];
				}
			}
		}

		return result;
	}

	//
	// Hyperlink listener
	//

	@Override
	public void linkActivated(HyperlinkEvent event) {
		if (event.getSource() == link) {
			String viewID = getViewID();
			if (viewID != null) {
				IWorkbenchPage page = getWorkbenchPage();
				if (page != null) {
					try {
						page.showView(viewID);
					} catch (PartInitException e) {
						StatusManager.getManager().handle(e.getStatus(), StatusManager.SHOW);
					}
				}
			}
		}
	}

	@Override
	public void linkEntered(HyperlinkEvent e) {
		// Pass
	}

	@Override
	public void linkExited(HyperlinkEvent e) {
		// Pass
	}
}
