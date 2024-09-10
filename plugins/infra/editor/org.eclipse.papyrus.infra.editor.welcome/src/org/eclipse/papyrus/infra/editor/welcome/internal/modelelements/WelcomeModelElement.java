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

package org.eclipse.papyrus.infra.editor.welcome.internal.modelelements;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;

/**
 * @author damus
 *
 */
public class WelcomeModelElement extends EMFModelElement {
	private final String PRIVATE_LAYOUT = "privateLayout"; //$NON-NLS-1$
	private final String RESTORE_ACTIVE_PAGE = "restoreActivePage"; //$NON-NLS-1$
	private final String LANGUAGES = "languages"; //$NON-NLS-1$

	public WelcomeModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	@Override
	protected IObservable doGetObservable(String propertyPath) {
		IObservable result;

		switch (propertyPath) {
		case PRIVATE_LAYOUT:
			result = new PrivateLayoutValue(this);
			break;
		case RESTORE_ACTIVE_PAGE:
			result = new RestoreActivePageValue(this);
			break;
		case LANGUAGES:
			result = new LanguagesObservableProperty(this).get();
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
		case PRIVATE_LAYOUT:
			result = true;
			break;
		case RESTORE_ACTIVE_PAGE:
			IObservableValue<?> privateLayout = (IObservableValue<?>) getObservable(PRIVATE_LAYOUT);
			result = Boolean.TRUE.equals(privateLayout.getValue());
			break;
		case LANGUAGES:
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

	@Override
	public boolean forceRefresh(String propertyPath) {
		boolean result;

		switch (propertyPath) {
		case RESTORE_ACTIVE_PAGE:
			result = true;
			break;
		default:
			result = super.forceRefresh(propertyPath);
			break;
		}

		return result;
	}
}
