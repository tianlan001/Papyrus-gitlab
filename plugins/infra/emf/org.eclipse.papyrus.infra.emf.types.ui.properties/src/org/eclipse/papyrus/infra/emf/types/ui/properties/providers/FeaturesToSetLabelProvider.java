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

package org.eclipse.papyrus.infra.emf.types.ui.properties.providers;

import org.eclipse.papyrus.infra.emf.types.advices.values.ConstantValue;
import org.eclipse.papyrus.infra.emf.types.advices.values.FeatureToSet;
import org.eclipse.papyrus.infra.emf.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * A {@link EMFLabelProvider} for {@link FeatureToSet}.
 */
public class FeaturesToSetLabelProvider extends EMFLabelProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		String text = Messages.undefinedValue;
		if (element instanceof FeatureToSet) {
			String featureName = ((FeatureToSet) element).getFeatureName();
			if (null != featureName) {
				text = featureName;
			}
		} else if (element instanceof ConstantValue) {
			ValueSpecification constvalue = ((ConstantValue) element).getValueInstance();
			text = constvalue.stringValue();
		} else {
			text = super.getText(element);
		}

		return text;
	}
}