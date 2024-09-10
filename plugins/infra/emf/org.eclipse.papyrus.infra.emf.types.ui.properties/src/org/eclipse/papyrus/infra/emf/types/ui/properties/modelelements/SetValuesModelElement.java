/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.types.ui.properties.modelelements;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.ui.properties.providers.FeaturesToSetLabelProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;

/**
 * {@link EMFModelElement} for {@link SetValuesAdviceConfiguration}.
 */
public class SetValuesModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 *
	 * @param sourceElement
	 *            The source element.
	 * @param domain
	 *            The editing domain.
	 */
	public SetValuesModelElement(final EObject sourceElement, final EditingDomain domain) {
		super(sourceElement, domain);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 */
	@Override
	public ILabelProvider getLabelProvider(final String propertyPath) {
		ILabelProvider labelProvider = null;
		if ("featuresToSet".equals(propertyPath) || "value".equals(propertyPath)) {//$NON-NLS-1$ //$NON-NLS-2$
			labelProvider = new FeaturesToSetLabelProvider();
		} else {
			labelProvider = super.getLabelProvider(propertyPath);
		}
		return labelProvider;
	}

}
