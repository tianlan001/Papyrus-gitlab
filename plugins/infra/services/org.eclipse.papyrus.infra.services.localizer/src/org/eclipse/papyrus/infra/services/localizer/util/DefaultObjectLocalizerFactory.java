/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 488558
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.localizer.util;

import org.eclipse.papyrus.infra.core.services.SharedServiceFactory;
import org.eclipse.papyrus.infra.services.localizer.DefaultObjectLocalizer;
import org.eclipse.papyrus.infra.services.localizer.IObjectLocalizer;


/**
 * Service factory for the default object localizer.
 */
public class DefaultObjectLocalizerFactory extends SharedServiceFactory<IObjectLocalizer> {

	public DefaultObjectLocalizerFactory() {
		super(IObjectLocalizer.class, DefaultObjectLocalizer::new);
	}

}
