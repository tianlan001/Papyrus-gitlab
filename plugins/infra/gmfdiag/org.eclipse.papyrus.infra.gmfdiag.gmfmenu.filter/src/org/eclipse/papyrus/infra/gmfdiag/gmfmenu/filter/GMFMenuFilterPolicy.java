/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.gmfmenu.filter;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.IPopupMenuContributionPolicy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForSelection;

/**
 *
 * Restricts the GMFMenuFilter to Papyrus or Papyrus-based diagrams
 *
 * @author Camille Letavernier
 *
 */
public class GMFMenuFilterPolicy implements IPopupMenuContributionPolicy {

	public boolean appliesTo(ISelection selection, IConfigurationElement configuration) {
		if (selection.isEmpty()) {
			return false;
		}

		try {
			return ServiceUtilsForSelection.getInstance().getServiceRegistry(selection) != null;
		} catch (ServiceException ex) {
			return false; // If there is no ServiceRegistry, then this is probably not a Papyrus diagram
		} catch (Exception ex) {
			return false; // This contribution can be called in any GMF Context (Including non-Papyrus). Let's be safe
		}
	}

}
