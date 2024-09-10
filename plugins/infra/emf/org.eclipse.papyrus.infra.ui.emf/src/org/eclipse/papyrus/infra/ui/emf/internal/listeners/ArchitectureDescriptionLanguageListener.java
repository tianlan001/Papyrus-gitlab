/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.emf.internal.listeners;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrus.infra.architecture.listeners.IArchitectureDescriptionListener;
import org.eclipse.papyrus.infra.ui.emf.internal.facet.ArchitectureFrameworkCustomizationManagerUpdater;

/**
 * This listener is in charge to listen the change of the Architecture Framework,
 * to update the Customization to apply in the ModelExplorer
 */
public class ArchitectureDescriptionLanguageListener implements IArchitectureDescriptionListener {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.architecture.listeners.IArchitectureDescriptionListener#architectureContextChanged(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	public void architectureContextChanged(Notification notification) {
		ArchitectureFrameworkCustomizationManagerUpdater.INSTANCE.applyCustomizations();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.architecture.listeners.IArchitectureDescriptionListener#architectureViewpointsChanged(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	public void architectureViewpointsChanged(Notification notification) {
		// nothing to do
	}

}
