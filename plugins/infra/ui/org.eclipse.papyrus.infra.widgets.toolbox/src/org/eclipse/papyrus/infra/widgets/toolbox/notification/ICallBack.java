/*****************************************************************************
 * Copyright (c) 2010 ATOS ORIGIN.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.toolbox.notification;


/**
 * A class providing services to be called back
 *
 * @author tristan faure
 *
 */
public interface ICallBack {

	/**
	 * This method is called by the callee
	 *
	 * @param element
	 */
	void callBack(Object element);
}
