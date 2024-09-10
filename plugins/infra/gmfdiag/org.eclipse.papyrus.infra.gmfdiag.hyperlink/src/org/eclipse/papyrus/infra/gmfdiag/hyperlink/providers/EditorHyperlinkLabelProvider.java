/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.object.HyperLinkEditor;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for diagram-editor hyperlinks.
 */
public class EditorHyperlinkLabelProvider extends LabelProvider implements IFilteredLabelProvider {

	public boolean accept(Object element) {
		return element instanceof HyperLinkEditor;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof HyperLinkEditor) {
			EObject editorContext = EMFHelper.getEObject(((HyperLinkEditor) element).getObject());
			if (editorContext != null) {
				try {
					return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, editorContext).getLabelProvider().getImage(editorContext);
				} catch (ServiceException ex) {
					Activator.log.error(ex);
				}
			}
		}

		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof HyperLinkEditor) {
			EObject editorContext = EMFHelper.getEObject(((HyperLinkEditor) element).getObject());
			if (editorContext != null) {
				try {
					return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, editorContext).getLabelProvider().getText(editorContext);
				} catch (ServiceException ex) {
					Activator.log.error(ex);
				}
			}
		} else {
			return super.getText(element);
		}

		return ((HyperLinkObject) element).getTooltipText();
	}

}
