/**
 * Copyright (c) 2012 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  	Grégoire Dupé (Mia-Software) - Bug 387470 - [EFacet][Custom] Editors
 */
package org.eclipse.papyrus.emf.facet.efacet.core.internal;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetCommandFactory;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetCommandFactoryFactory;

public class FacetCommandFactoryFactory implements IFacetCommandFactoryFactory {

	public IFacetCommandFactory createCommandFactory(
			final EditingDomain editingDomain) {
		return new FacetCommandFactoryImpl(editingDomain);
	}

}
