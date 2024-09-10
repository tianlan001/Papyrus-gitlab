/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.viewlabelprovider.provider;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.viewlabelprovider.Activator;
import org.eclipse.papyrus.infra.services.viewlabelprovider.Messages;
import org.eclipse.swt.graphics.Image;

/**
 * @author Shuai Li (CEA LIST) <shuai.li@cea.fr>
 *
 */
public class DiagramOfViewLabelProvider extends ViewLabelProvider {
	/**
	 * Returns the image of the diagram of the view
	 *
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof View) {
			Diagram diagram = ((View) element).getDiagram();

			if (diagram != null) {
				try {
					LabelProviderService service = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, diagram);
					return service.getLabelProvider().getImage(diagram);
				} catch (ServiceException e) {
					Activator.log.warn(Messages.ViewLabelProvider_0 + diagram);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the text of the diagram of the view
	 *
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof View) {
			Diagram diagram = ((View) element).getDiagram();

			if (diagram != null) {
				try {
					LabelProviderService service = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, diagram);
					return service.getLabelProvider().getText(diagram);
				} catch (ServiceException e) {
					Activator.log.warn(Messages.ViewLabelProvider_0 + diagram);
				}
			}
		}
		return ""; //$NON-NLS-1$
	}
}
