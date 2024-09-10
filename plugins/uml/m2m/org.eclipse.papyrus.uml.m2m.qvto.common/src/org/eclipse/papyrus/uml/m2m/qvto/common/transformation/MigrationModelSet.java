/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.transformation;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.uml.m2m.qvto.common.concurrent.ThreadSafeModelSet;


/**
 * @author Camille Letavernier
 *
 */
public class MigrationModelSet extends ThreadSafeModelSet implements MigrationResourceSet {

	private boolean frozen = false;
	
	/**
	 * @see org.eclipse.papyrus.migration.rsa.transformation.MigrationResourceSet#freeze()
	 *
	 */
	@Override
	public void freeze() {
		this.frozen = true;
	}

	/**
	 * @see org.eclipse.papyrus.migration.rsa.transformation.MigrationResourceSet#unfreeze()
	 *
	 */
	@Override
	public void unfreeze() {
		this.frozen = false;
	}
	
	/**
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#getResource(org.eclipse.emf.common.util.URI, boolean)
	 *
	 * @param uri
	 * @param loadOnDemand
	 * @return
	 */
	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		return super.getResource(uri, frozen ? false : loadOnDemand);
	}

}
