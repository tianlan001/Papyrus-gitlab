/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.types.core;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AdviceConfiguration;

/**
 * A partial implementation an <em>Edit Helper Advice</em> that knows its configuration model.
 *
 * @param <T>
 *            the configuration type
 */
public abstract class AbstractConfiguredEditHelperAdvice<T extends AdviceConfiguration> extends AbstractEditHelperAdvice implements IConfiguredEditHelperAdvice<T> {

	private T configuration;

	public AbstractConfiguredEditHelperAdvice() {
		super();
	}

	protected final T getConfiguration() {
		return configuration;
	}

	@Override
	public final void init(T configuration) {
		this.configuration = configuration;

		doInit(configuration);
	}

	/**
	 * Optionally overridden by subclasses to do further initialization.
	 *
	 * @param configuration
	 *            my configuration model
	 */
	protected void doInit(T configuration) {
		// Nothing here
	}

}
