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
package org.eclipse.papyrus.infra.tools.notify;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class Context.
 * 
 * @since 2.0
 */
public interface IContext {

	/**
	 * This constant identifies developper strings to indicate the code location
	 */
	public static String STRING_FOR_DEVELOPER = "_please_check_IContext_Constant";

	/**
	 * This constant allows the user to retrieve the composite created if he filled a ICreationComposite
	 */
	public static String COMPOSITE_CREATED = "composite_created" + STRING_FOR_DEVELOPER;

	/**
	 * This constant allows the user to retrieve the notification if he needs to close it
	 */
	public static String NOTIFICATION_OBJECT = "notification_object" + STRING_FOR_DEVELOPER;

	/**
	 * This constant determines an action id to the current context
	 */
	public static String ACTION_ID = "action_id";

	public void put(String s, Object o);

	public Object get(String s);

	public static class Context implements IContext {

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
