/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.modelelement;

import java.util.Collections;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement;
import org.eclipse.papyrus.uml.diagram.activity.observables.SwitchOrientationObservableValue;

/**
 * This allows to define the model element for the activity notation.
 *
 * @since 3.2
 */
public class ActivityNotationModelElement extends AbstractModelElement {
	TransactionalEditingDomain domain;
	EditPart editPart;

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            The editing domain.
	 * @param source
	 *            The source edit part.
	 */
	public ActivityNotationModelElement(final TransactionalEditingDomain domain, final EditPart source) {
		super();

		this.domain = domain;
		this.editPart = source;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#doGetObservable(java.lang.String)
	 */
	@Override
	protected IObservable doGetObservable(final String propertyPath) {
		IObservable result = null;

		if (propertyPath.endsWith("switchOrientation")) { //$NON-NLS-1$
			result = new SwitchOrientationObservableValue(domain, Collections.singleton(editPart));
		}

		return result;
	}
}
