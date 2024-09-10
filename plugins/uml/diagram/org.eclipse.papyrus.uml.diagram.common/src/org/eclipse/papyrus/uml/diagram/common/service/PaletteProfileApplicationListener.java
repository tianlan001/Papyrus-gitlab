/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 454578, 458197, 485220
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.service;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.common.core.service.ProviderChangeEvent;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.listenerservice.IPapyrusListener;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService;
import org.eclipse.papyrus.uml.tools.listeners.ProfileApplicationListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Package;

/**
 * Listener for the palette Service that updates palette contribution in case of
 * profile application/unapplication
 */
public class PaletteProfileApplicationListener implements IPapyrusListener {

	/**
	 * Creates a new PaletteProfileApplicationListener.
	 */
	public PaletteProfileApplicationListener() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyChanged(Notification notification) {
		// check notification is relevant to us. It doesn't matter whether it was a profile-applied or a profile-unapplied notification.
		// In either case, we reload the palette
		if (ProfileApplicationListener.isProfileApplicationNotification(notification)) {
			try {
				Package package_ = (Package) notification.getNotifier();
				IEditorPart editor = ServiceUtilsForEObject.getInstance().getService(ISashWindowsContainer.class, package_).getActiveEditor();
				if (editor == null) {
					return;
				}

				Display display = editor.getSite().getWorkbenchWindow().getShell().getDisplay();
				if ((display != null) && (display != Display.getCurrent())) {
					// Update the palette service on the UI thread because it may need to add/remove palette content,
					// which implies changing SWT controls
					display.asyncExec(new Runnable() {

						@Override
						public void run() {
							updatePaletteService();
						}
					});
				} else {
					// Just do it synchronously
					updatePaletteService();
				}
			} catch (ServiceException ex) {
				// Nothing to do. The ServiceRegistry is not available or there is no active editor. Don't update the palette
			} catch (Exception ex) {
				// Bug 407849: If a listener throws an exception, the operation is rolled back. In this case, we simply want to update the palettes and exceptions should only be logged
				Activator.log.error(ex);
			}
		}
	}

	protected void updatePaletteService() {
		PapyrusPaletteService.getInstance().providerChanged(new ProviderChangeEvent(PapyrusPaletteService.getInstance()));
	}
}
