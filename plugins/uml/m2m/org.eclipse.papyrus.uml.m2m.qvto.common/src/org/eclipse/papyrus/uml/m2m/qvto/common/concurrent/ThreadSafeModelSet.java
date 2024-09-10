/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.concurrent;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.utils.DiResourceSet;

/**
 * A ModelSet with thread-safe load operations
 * 
 * Note: since Save operations are delegated to IModels, save operations are <strong>thread-safe</strong>.
 * Resources of this ResourceSet should be saved by delegating to {@link ResourceAccessHelper#saveResource(Resource, Map<?, ?>)}
 * 
 * @see {@link ResourceAccessHelper}
 */
public class ThreadSafeModelSet extends DiResourceSet {
	@Override
	protected void demandLoad(Resource resource) throws IOException {
		Resource resourceWithOptions = setResourceOptions(resource);
		ResourceAccessHelper.INSTANCE.loadResource(resourceWithOptions, getLoadOptions());
	}
}
