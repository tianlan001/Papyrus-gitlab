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
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.ui.IContributorResourceAdapter;
import org.eclipse.ui.ide.IContributorResourceAdapter2;

/**
 * Adapter factory to adapt {@link IPapyrusFile}
 *
 * @author tristan.faure@atosorigin.com
 */
public class ModelAdapterFactory implements IAdapterFactory {

	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (IContributorResourceAdapter.class.equals(adapterType)) {
			return adapterType.cast(new PapyrusModelContributorResourceAdapter());
		}
		if (IContributorResourceAdapter2.class.equals(adapterType)) {
			return adapterType.cast(new PapyrusModelContributorResourceAdapter());
		}
		return null;
	}

	public Class<?>[] getAdapterList() {
		return new Class[] { IContributorResourceAdapter.class, IContributorResourceAdapter2.class };
	}

}
