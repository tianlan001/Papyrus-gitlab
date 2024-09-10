/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.matchers.stereotype;

import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.types.core.factories.IMatcherFactory;



/**
 * Factory to create matcher based on stereotypes applied on the element
 */
public class StereotypeApplicationMatcherConfigurationFactory implements IMatcherFactory<StereotypeApplicationMatcherConfiguration> {

	/**
	 * {@inheritDoc}
	 */
	public IElementMatcher createElementMatcher(StereotypeApplicationMatcherConfiguration configuration) {
		return new StereotypeApplicationMatcher(configuration);
	}
}
