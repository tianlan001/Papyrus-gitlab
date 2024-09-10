/*****************************************************************************
 * Copyright (c) 2011, 2016 LIFL, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   LIFL - Initial API and implementation
 *   Christian W. Damus - bugs 485220, 488791
 * 
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource;

import org.eclipse.emf.ecore.EObject;

/**
 * @author dumoulin
 *
 */
public class FakeModel extends AbstractBaseModel {

	private String identifier;

	public FakeModel(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getIdentifier()
	 *
	 * @return
	 */
	@Override
	public String getIdentifier() {
		
		return identifier;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getModelFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getModelFileExtension() {
		
		return identifier;
	}

	@Override
	public boolean canPersist(EObject object) {
		return false;
	}

	@Override
	public void persist(EObject object) {
		throw new IllegalArgumentException("cannot persist " + object); //$NON-NLS-1$
	}

	// Overridden to make it accessible
	@Override
	protected ModelSet getModelManager() {
		return super.getModelManager();
	}
}
