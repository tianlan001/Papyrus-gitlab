/*****************************************************************************
 * Copyright (c) 2013, 2023 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.labelprovider.service.ExtensibleLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.views.search.results.AbstractResultEntry;
import org.eclipse.papyrus.views.search.results.ViewerMatch;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.NamedElement;

public class ResultLabelProvider extends LabelProvider {

	private static final String LABEL_PROVIDER_CONTEXT = "org.eclipse.papyrus.uml.search.ui.label.provider.context"; //$NON-NLS-1$

	private LabelProviderService labelProviderService;

	public ResultLabelProvider() {
		labelProviderService = new LabelProviderServiceImpl();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof AbstractResultEntry) {
			return labelProviderService.getLabelProvider().getImage(((AbstractResultEntry) element).elementToDisplay());
		}

		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof AbstractResultEntry) {
			ILabelProvider labelProvider = labelProviderService.getLabelProvider(LABEL_PROVIDER_CONTEXT);

			if (element instanceof ViewerMatch) {
				return labelProvider.getText(((AbstractResultEntry) element).elementToDisplay());
			}

			if (labelProvider instanceof ExtensibleLabelProvider) {
				String qualifierText = ((ExtensibleLabelProvider) labelProvider).getQualifierText(((AbstractResultEntry) element).elementToDisplay());
				if (qualifierText == null) {
					return labelProvider.getText(((AbstractResultEntry) element).elementToDisplay());
				} else {
					return qualifierText + NamedElement.SEPARATOR + labelProvider.getText(((AbstractResultEntry) element).elementToDisplay());
				}
			} else {
				return labelProvider.getText(((AbstractResultEntry) element).elementToDisplay());
			}
		}

		return ""; //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		super.dispose();
		try {
			labelProviderService.disposeService();
		} catch (ServiceException ex) {
			// Ignore
		}
	}

}
