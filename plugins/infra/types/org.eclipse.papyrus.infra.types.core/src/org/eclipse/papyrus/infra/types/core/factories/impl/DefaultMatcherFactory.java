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
package org.eclipse.papyrus.infra.types.core.factories.impl;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.types.MatcherConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.IMatcherFactory;

public class DefaultMatcherFactory implements IMatcherFactory<MatcherConfiguration> {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.types.core.factories.IMatcherFactory#createElementMatcher(org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration)
	 *
	 * @param matcherConfiguration
	 * @return
	 */
	@Override
	public IElementMatcher createElementMatcher(MatcherConfiguration matcherConfiguration) {
		String matcherClassName = matcherConfiguration.getMatcherClassName();
		IElementMatcher matcher = ClassLoaderHelper.newInstance(matcherClassName, IElementMatcher.class, EcoreUtil.getURI(matcherConfiguration));
		return matcher;
	}
}
