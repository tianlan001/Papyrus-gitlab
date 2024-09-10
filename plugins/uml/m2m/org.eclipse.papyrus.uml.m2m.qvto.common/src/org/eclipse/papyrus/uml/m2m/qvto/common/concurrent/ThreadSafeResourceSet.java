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
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A ResourceSet with thread-safe Load operations
 * 
 * Resources should be saved using {@link ResourceAccessHelper#saveResource(Resource, java.util.Map)}
 * 
 * @author Camille Letavernier
 *
 * @see {@link ResourceAccessHelper}
 */
public class ThreadSafeResourceSet extends ResourceSetImpl {
	@Override
	protected void demandLoad(Resource resource) throws IOException {
		ResourceAccessHelper.INSTANCE.loadResource(resource, getLoadOptions());
	}
}
