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
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.language;

import java.util.EventListener;

/**
 * A protocol for receipt of {@link LanguageChangeEvent}s.
 */
public interface ILanguageChangeListener extends EventListener {
	/**
	 * A notification that languages have changed in some resource(s) within my scope.
	 * This usually should only be sent by asynchronous {@linkplain ILanguageProvider language providers}.
	 * 
	 * @param event
	 *            the language change event
	 */
	void languagesChanged(LanguageChangeEvent event);
}
