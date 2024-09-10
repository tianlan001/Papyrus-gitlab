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
import org.eclipse.papyrus.infra.emf.types.advices.values.ConstantValue;
import org.eclipse.papyrus.infra.emf.types.advices.values.FeatureToSet;
import org.eclipse.papyrus.infra.emf.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * A {@link EMFModelElement} for {@link FeatureToSet}. Used to provide a custom {@link EMFLabelProvider}.
 *
 */
public class FeatureToSetModelElement extends EMFModelElement {

	class FeatureToSetLabelProvider extends EMFLabelProvider implements ILabelProvider {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(final Object element) {
			String text = Messages.undefinedValue;
			if (element instanceof ConstantValue) {
				ValueSpecification constvalue = ((ConstantValue) element).getValueInstance();
				text = constvalue.stringValue();// $NON-NLS-1$
			} else {
				text = super.getText(element);
			}
			return text;
		}
	}

	/**
	 * Constructor.
	 */
	public FeatureToSetModelElement(EObject source, EditingDomain domain) {
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
		}
		labelProvider = super.getLabelProvider(propertyPath);
		return labelProvider;
	}

}
