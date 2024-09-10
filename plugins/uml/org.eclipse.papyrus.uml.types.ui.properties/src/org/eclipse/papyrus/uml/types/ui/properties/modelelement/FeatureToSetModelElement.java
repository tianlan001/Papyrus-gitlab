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

package org.eclipse.papyrus.uml.types.ui.properties.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet;
import org.eclipse.papyrus.uml.types.ui.properties.providers.FeatureToSetLabelProvider;

/**
 * Custom {@link EMFModelElement} for {@link FeatureToSet}.
 *
 */
public class FeatureToSetModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 */
	public FeatureToSetModelElement(final EObject source, final EditingDomain domain) {
		super(source, domain);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 */
	@Override
	public ILabelProvider getLabelProvider(final String propertyPath) {
		ILabelProvider labelProvider = null;

		if ("value".equals(propertyPath)) {//$NON-NLS-1$
			labelProvider = new FeatureToSetLabelProvider();
		} else {
			labelProvider = super.getLabelProvider(propertyPath);
		}
		return labelProvider;
	}

}
