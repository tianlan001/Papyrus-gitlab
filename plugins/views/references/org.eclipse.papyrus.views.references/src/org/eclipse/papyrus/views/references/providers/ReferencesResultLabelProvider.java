/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.ExtensibleLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.views.references.constants.ReferencesViewConstants;
import org.eclipse.papyrus.views.references.utils.HandleReferences;
import org.eclipse.swt.graphics.Image;

/**
 * Class to create the Label Provider used to set label of the references.
 */
public class ReferencesResultLabelProvider extends LabelProvider {

	/**
	 * The LabelProvider.
	 */
	private ILabelProvider labelProvider;

	/**
	 * Constructor.
	 */
	public ReferencesResultLabelProvider() {
		final LabelProviderService labelProviderService = HandleReferences.INSTANCE.getLabelProviderService();
		labelProvider = labelProviderService.getLabelProvider();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage(final Object element) {
		Object object = element;
		if (element instanceof Setting) {
			object = ((Setting) element).getEObject();
		}
		return labelProvider.getImage(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(final Object element) {
		if (element instanceof Setting) {
			final EObject eObject = ((Setting) element).getEObject();
			if (labelProvider instanceof ExtensibleLabelProvider) {
				final String qualifierText = ((ExtensibleLabelProvider) labelProvider).getQualifierText(eObject);
				if (null == qualifierText) {
					return labelProvider.getText(eObject);
				} else {
					return qualifierText + ReferencesViewConstants.SEPARATOR + labelProvider.getText(eObject);
				}
			}
		} else {
			final String text = labelProvider.getText(element);
			if (null != text) {
				return text;
			}
		}

		return ReferencesViewConstants.EMPTY_STRING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (null != labelProvider) {
			labelProvider.dispose();
		}
	}
}
