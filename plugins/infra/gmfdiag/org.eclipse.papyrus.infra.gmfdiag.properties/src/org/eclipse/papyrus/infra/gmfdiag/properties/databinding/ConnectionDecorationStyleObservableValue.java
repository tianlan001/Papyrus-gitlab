/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 521754
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.databinding;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStringStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.decoration.ConnectionDecorationRegistry;

/**
 * The {@link CustomStringStyleObservableValue} used for connector decoration value.
 * 
 * @author Mickaël ADAM
 * @since 3.1
 */
public final class ConnectionDecorationStyleObservableValue extends CustomStringStyleObservableValue {
	/**
	 * The string used in cased of undefined value.
	 */
	private static final String DEFAULT_VALUE = "default";//$NON-NLS-1$

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            The source.
	 * @param domain
	 *            The editing domain.
	 * @param styleName
	 *            the style name.
	 */
	public ConnectionDecorationStyleObservableValue(final View source, final EditingDomain domain, final String styleName) {
		super(source, domain, styleName);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStringStyleObservableValue#getDefaultValue()
	 */
	@Override
	protected String getDefaultValue() {
		return DEFAULT_VALUE;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.AbstractCustomStyleObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Object value) {
		String name = null;
		if (value instanceof String) {
			name = ConnectionDecorationRegistry.getInstance().getName((String) value);
		}
		super.doSetValue(null != name ? name : value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.AbstractCustomStyleObservableValue#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		Object value = super.doGetValue();
		if (value instanceof String) {
			String label = ConnectionDecorationRegistry.getInstance().getLabel((String) value);
			return null != label ? label : value;
		}
		return value;
	}
}