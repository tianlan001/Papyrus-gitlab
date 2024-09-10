/*****************************************************************************
 * Copyright (c) 2012 Atos.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Devernay (Atos) laurent.devernay@atos.net
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.internal.ui.strategy;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.services.resourceloading.ILoadingStrategyExtension;

public class PreferenceLoadingStrategyExtension implements ILoadingStrategyExtension {

	public boolean loadResource(ModelSet modelSet, URI uri) {
		Set<URI> loadedAuthorizedResourcesSet = LoadedAuthorizedResourceManager.getInstance().getLoadedAuthorizedResourcesSet(modelSet);

		if (loadedAuthorizedResourcesSet.contains(uri.trimFileExtension())) {
			return true;
		}
		return false;
	}

}
