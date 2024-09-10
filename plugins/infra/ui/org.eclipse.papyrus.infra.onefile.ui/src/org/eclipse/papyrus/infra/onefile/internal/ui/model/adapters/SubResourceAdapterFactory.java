/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos Origin Integration, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (Atos Origin Integration) tristan.faure@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.onefile.internal.ui.model.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.papyrus.infra.onefile.model.ISubResourceFile;
import org.eclipse.ui.IContributorResourceAdapter;

/**
 * Adapter Factory for {@link ISubResourceFile}
 *
 * @author tristan.faure@atosorigin.com
 *
 */
public class SubResourceAdapterFactory implements IAdapterFactory {

	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (IContributorResourceAdapter.class.equals(adapterType)) {
			if (adaptableObject instanceof ISubResourceFile) {
				return adapterType.cast(new PapyrusModelContributorResourceAdapter());
			}
		}
		return null;
	}

	public Class<?>[] getAdapterList() {
		return new Class[] { IContributorResourceAdapter.class };
	}

}
