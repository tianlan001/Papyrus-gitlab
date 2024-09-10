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

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AdviceConfiguration;

/**
 * Optional protocol for an <em>Edit Helper Advice</em> to access its configuration
 * from the <em>Element Types Configurations</em> model.
 */
public interface IConfiguredEditHelperAdvice<T extends AdviceConfiguration> extends IEditHelperAdvice {

	/**
	 * Initializes me with my {@code configuration} model. This is guaranteed to be initialized
	 * before the advice is consulted in the assembly of any edit operation.
	 *
	 * @param configuration
	 *            my configuration; never {@code null}
	 */
	void init(T configuration);

}
