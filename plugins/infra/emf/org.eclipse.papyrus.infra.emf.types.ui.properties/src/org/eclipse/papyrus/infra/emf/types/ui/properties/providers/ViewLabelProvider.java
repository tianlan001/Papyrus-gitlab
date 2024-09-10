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
 *	Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.ui.properties.providers;

import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;
import org.eclipse.papyrus.infra.emf.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;

/**
 * An {@link EMFLabelProvider} for {@link View};
 */
public class ViewLabelProvider extends EMFLabelProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		String text = Messages.undefinedValue;

		if (element instanceof Context) {
			text = ((Context) element).getName();
		} else if (element instanceof View) {
			text = ((View) element).getName();
		} else if (element instanceof ViewToDisplay) {
			View view = ((ViewToDisplay) element).getView();
			if (null != view) {
				text = view.getName();
			}
		} else {
			text = super.getText(element);
		}

		return text;
	}
}