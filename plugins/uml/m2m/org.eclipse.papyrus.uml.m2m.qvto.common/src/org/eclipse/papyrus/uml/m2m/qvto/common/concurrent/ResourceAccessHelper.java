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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This class is used to synchronize access to physical EMF Resources (Load/Save)
 * 
 * @author Camille Letavernier
 *
 */
public class ResourceAccessHelper {
	public static ResourceAccessHelper INSTANCE = new ResourceAccessHelper();

	private final Map<URI, ReadWriteLock> locks = new HashMap<URI, ReadWriteLock>();

	private ResourceAccessHelper() {
		// Singleton
	}

	private Lock getSaveLock(Resource resource) {
		ReadWriteLock lock = getLock(resource);
		return lock.writeLock();
	}

	private Lock getLoadLoack(Resource resource) {
		ReadWriteLock lock = getLock(resource);
		return lock.readLock();
	}

	/**
	 * Saves a resource in a thread-safe way. Ensures that the underlying physical
	 * resource is not being read during the save action
	 * 
	 * @param resource
	 * @param options
	 * @throws IOException
	 */
	public void saveResource(Resource resource, Map<?, ?> options) throws IOException {
		Lock lock = getSaveLock(resource);
		lock.lock();
		try {
			resource.save(options);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Loads a resource in a thread-safe way. Ensures that the underlying physical
	 * resource is not being saved during the load action. Concurrent read operations
	 * may still happen
	 * 
	 * @param resource
	 * @param options
	 * @throws IOException
	 */
	public void loadResource(Resource resource, Map<?, ?> options) throws IOException {
		Lock lock = getLoadLoack(resource);
		lock.lock();
		try {
			resource.load(options);
		} finally {
			lock.unlock();
		}
	}

	

	private ReadWriteLock getLock(Resource resource) {
		URI uri = resource.getURI();
		if (uri == null) {
			return null;
		}

		synchronized (this) {
			if (!locks.containsKey(uri)) {
				locks.put(uri, new ReentrantReadWriteLock());
			}

			return locks.get(uri);
		}
	}

}
