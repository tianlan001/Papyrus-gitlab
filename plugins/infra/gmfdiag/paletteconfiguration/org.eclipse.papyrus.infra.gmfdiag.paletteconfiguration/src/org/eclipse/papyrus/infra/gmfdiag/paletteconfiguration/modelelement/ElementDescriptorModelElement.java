/*****************************************************************************
 * Copyright (c) 2017, 2021 CEA LIST, ALL4TEC, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Micka�l ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.modelelement;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.ElementTypeLabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.ElementTypeSetConfigurationContentProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.ui.emf.dialog.NestedEditingDialogContext;
import org.eclipse.papyrus.infra.widgets.providers.FilteredContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * Model Element for {@link ElementDescriptor}.
 *
 */
public class ElementDescriptorModelElement extends EMFModelElement {
	/**
	 * Constructor.
	 *
	 * @param sourceElement
	 *            the palette configuration where to edit the icon descriptor
	 * @param domain
	 *            the editing domain
	 */
	public ElementDescriptorModelElement(final ElementDescriptor sourceElement, final EditingDomain domain) {
		super(sourceElement, domain);
	}

	@Override
	protected boolean isFeatureEditable(final String propertyPath) {
		boolean featureEditable = false;
		if ("elementType".equals(propertyPath)) { //$NON-NLS-1$
			featureEditable = true;
		} else {
			featureEditable = super.isFeatureEditable(propertyPath);
		}
		return featureEditable;
	}

	@Override
	public ILabelProvider getLabelProvider(final String propertyPath) {
		ILabelProvider labelProvider = null;
		if ("elementType".equals(propertyPath)) {//$NON-NLS-1$
			labelProvider = new ElementTypeLabelProvider();
		} else {
			labelProvider = super.getLabelProvider(propertyPath);
		}
		return labelProvider;
	}


	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		IStaticContentProvider contentProvider = null;
		if ("elementType".equals(propertyPath)) {//$NON-NLS-1$
			ResourceSet rset = null;
			if (domain != null) {
				rset = domain.getResourceSet();
			} else {
				rset = NestedEditingDialogContext.getInstance().getResourceSet();
			}
			contentProvider = new FilteredContentProvider(new ElementTypeSetConfigurationContentProvider(rset));
		} else {
			contentProvider = super.getContentProvider(propertyPath);
		}
		return contentProvider;
	}
}
