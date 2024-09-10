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

package org.eclipse.papyrus.infra.editor.welcome.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenable;
import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;

/**
 * Eclipse platform adapter factory for the {@linkplain WelcomePackage welcome model}.
 */
public class WelcomeAdapterFactory implements IAdapterFactory {

	public WelcomeAdapterFactory() {
		super();
	}

	@Override
	public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
		T result = null;

		if (adaptable instanceof Welcome) {
			if (adapterType == IOpenable.class) {
				result = adapterType.cast(new IOpenable.Openable(adaptable));
			}
		}

		return result;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IOpenable.class };
	}

}
