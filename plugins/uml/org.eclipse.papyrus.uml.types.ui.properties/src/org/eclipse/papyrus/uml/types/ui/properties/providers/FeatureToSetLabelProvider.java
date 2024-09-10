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
package org.eclipse.papyrus.uml.types.ui.properties.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ConstantValue;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet;
import org.eclipse.papyrus.uml.types.ui.properties.messages.Messages;
import org.eclipse.uml2.uml.ValueSpecification;


/**
 * A custom {@link EMFLabelProvider} for {@link FeatureToSet}.
 */
public class FeatureToSetLabelProvider extends EMFLabelProvider implements ILabelProvider {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		String text = Messages.FeatureToSetLabelProvider_undefined;
		if (element instanceof ConstantValue) {
			ValueSpecification constvalue = ((ConstantValue) element).getValueInstance();
			if (null != constvalue) {
				text = constvalue.stringValue();// $NON-NLS-1$
			}
		} else {
			text = super.getText(element);
		}
		return text;
	}
}