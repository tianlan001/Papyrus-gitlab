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
import org.eclipse.papyrus.infra.emf.types.advices.values.FeatureToSet;
import org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;

/**
 * The factory for {@link SetValuesModelElementFactory}.
 */
public class SetValuesModelElementFactory extends EMFModelElementFactory {

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected EMFModelElement doCreateFromSource(final Object sourceElement, final DataContextElement context) {
		EMFModelElement modelElement = null;

		// Get the domain
		EditingDomain domain = EMFHelper.resolveEditingDomain(sourceElement);

		if ("SetValuesAdviceConfiguration".equals(context.getName()) && sourceElement instanceof SetValuesAdviceConfiguration) {//$NON-NLS-1$
			// Create the modelElement
			modelElement = new SetValuesModelElement(((EObject) sourceElement), domain);
		} else if ("FeatureToSet".equals(context.getName()) && sourceElement instanceof FeatureToSet) {//$NON-NLS-1$
			// Create the modelElement
			modelElement = new SetValuesModelElement(((EObject) sourceElement), domain);
		} else {
			modelElement = super.doCreateFromSource(sourceElement, context);
		}
		return modelElement;
	}
}
