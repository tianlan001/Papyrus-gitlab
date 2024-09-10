/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Registration of an {@link IVisualChildrenStrategy} on the extension point.
 */
class VisualChildrenStrategyRegistration extends EditPartBasedRegistration<IVisualChildrenStrategy, VisualChildrenStrategyRegistration> {

	public VisualChildrenStrategyRegistration(IConfigurationElement config) throws CoreException {
		super(config, IVisualChildrenStrategy.class);
	}
}
