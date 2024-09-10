/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.object;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.swt.widgets.Shell;

/**
 * References a specific object in a View (Diagram/Table/...)
 *
 * @author Camille
 *
 */
public class HyperLinkSpecificObject extends HyperLinkObject {

	private EObject targetElement;

	public HyperLinkSpecificObject(EObject targetElement) {
		this.targetElement = targetElement;
	}

	public HyperLinkSpecificObject() {
		// Nothing
	}

	@Override
	public void executeEditMousePressed(Shell parentShell, List<HyperLinkObject> list, EObject amodel) {
		throw new UnsupportedOperationException("Object links are not editable"); //$NON-NLS-1$
	}

	public void setTargetElement(EObject targetElement) {
		this.targetElement = targetElement;
	}

	@Override
	public boolean needsOpenCommand() {
		return false;
	}

	@Override
	public void openLink() {
		if (targetElement == null) {
			return;
		}

		OpenElementService openElementService;
		// IMultiDiagramEditor editor;
		try {
			openElementService = ServiceUtilsForEObject.getInstance().getService(OpenElementService.class, targetElement);
			// editor = ServiceUtilsForEObject.getInstance().getService(IMultiDiagramEditor.class, targetElement);
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			return;
		}

		try {
			openElementService.openElement(targetElement);
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	public EObject getTargetElement() {
		return targetElement;
	}

}
