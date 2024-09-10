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
package org.eclipse.papyrus.emf.facet.efacet.core;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.facet.efacet.core.internal.FacetCommandFactoryFactory;

/**
 * @since 0.3
 */
public interface IFacetCommandFactoryFactory {

	IFacetCommandFactoryFactory DEFAULT = new FacetCommandFactoryFactory();

	IFacetCommandFactory createCommandFactory(EditingDomain editingDomain);
}
