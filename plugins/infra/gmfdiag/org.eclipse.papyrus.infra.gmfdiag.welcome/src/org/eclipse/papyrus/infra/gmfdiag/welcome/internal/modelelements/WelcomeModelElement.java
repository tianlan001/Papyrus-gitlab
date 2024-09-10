/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.welcome.internal.modelelements;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;

/**
 * @author damus
 *
 */
public class WelcomeModelElement extends EMFModelElement {
	private final String VIEWS = "views"; //$NON-NLS-1$

	public WelcomeModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	@Override
	protected IObservable doGetObservable(String propertyPath) {
		IObservable result;

		switch (propertyPath) {
		case VIEWS:
			result = new NotationObservableProperty(this).get();
			break;
		default:
			result = super.doGetObservable(propertyPath);
			break;
		}

		return result;
	}

	@Override
	protected boolean isFeatureEditable(String propertyPath) {
		boolean result;

		switch (propertyPath) {
		case VIEWS:
			result = true;
			break;
		default:
			result = super.isFeatureEditable(propertyPath);
			break;
		}

		return result;
	}

	@Override
	protected boolean isElementEditable() {
		return true;
	}
}
