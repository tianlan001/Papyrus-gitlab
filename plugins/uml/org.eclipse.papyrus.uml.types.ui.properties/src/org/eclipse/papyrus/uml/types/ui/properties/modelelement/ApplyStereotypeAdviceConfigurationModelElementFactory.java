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
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElementFactory;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;

/**
 * The factory for {@link ApplyStereotypeAdviceConfigurationModelElementFactory}.
 */
public class ApplyStereotypeAdviceConfigurationModelElementFactory extends EMFModelElementFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EMFModelElement doCreateFromSource(final Object sourceElement, final DataContextElement context) {
		EMFModelElement modelElement = null;

		// Get editing domain
		EditingDomain domain = EMFHelper.resolveEditingDomain(sourceElement);

		// Create the modelElement
		if ("StereotypeToApply".equals(context.getName()) && sourceElement instanceof StereotypeToApply) {//$NON-NLS-1$
			modelElement = new StereotypeToApplyModelElement(((EObject) sourceElement), domain);
		} else if ("FeatureToSet".equals(context.getName()) && sourceElement instanceof FeatureToSet) {//$NON-NLS-1$
			modelElement = new FeatureToSetModelElement(((EObject) sourceElement), domain);
		}

		return null != modelElement ? modelElement : super.doCreateFromSource(sourceElement, context);
	}
}
