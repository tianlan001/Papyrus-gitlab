/*****************************************************************************
 * Copyright (c) 2010, 2016 ATOS ORIGIN, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (ATOS ORIGIN INTEGRATION) tristan.faure@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.toolbox.notification.builders;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class Context.
 */
public interface IContext extends org.eclipse.papyrus.infra.tools.notify.IContext {

	class Context implements IContext {

		private Map<String, Object> objects = new HashMap<String, Object>();;

		@Override
		public void put(String s, Object o) {
			objects.put(s, o);
		}

		@Override
		public Object get(String s) {
			return objects.get(s);
		}

	}

}
