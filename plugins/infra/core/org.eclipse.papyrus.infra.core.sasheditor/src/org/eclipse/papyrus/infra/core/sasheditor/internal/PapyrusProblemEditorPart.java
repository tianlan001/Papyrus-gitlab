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
 *   Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.internal;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.editor.ProblemEditorPart;
import org.eclipse.papyrus.infra.core.sasheditor.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

/**
 * The class add a button to the {@link ProblemEditorPart} to be able to open the Error Log View
 */
public class PapyrusProblemEditorPart extends ProblemEditorPart {

	private static final String LOG_VIEW_ID = "org.eclipse.pde.runtime.LogView"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.emf.common.ui.editor.ProblemEditorPart#createButtons(org.eclipse.swt.widgets.Composite)
	 *
	 * @param buttonComposite
	 */
	@Override
	protected void createButtons(Composite buttonComposite) {
		editorToOpen = null; // this allow to remove the OpenInEditor button since we don't know which file to open
		super.createButtons(buttonComposite);
		createShowLogButton(buttonComposite);
	}

	/**
	 * Create the show log button
	 * 
	 * @param parent
	 *            the parent composite of the button
	 */
	private void createShowLogButton(Composite parent) {
		IViewDescriptor descriptor = PlatformUI.getWorkbench().getViewRegistry().find(LOG_VIEW_ID);
		if (descriptor == null) {
			return;
		}
		Button button = new Button(parent, SWT.PUSH);
		button.addSelectionListener(widgetSelectedAdapter(e -> {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(LOG_VIEW_ID);
			} catch (CoreException ce) {
				Activator.log.error(ce);
			}
		}));
		final Image image = descriptor.getImageDescriptor().createImage();
		button.setImage(image);
		button.setToolTipText(Messages.PapyrusProblemEditorPart_ShowErrorLogView);
		button.addDisposeListener(e -> image.dispose());
	}

}
