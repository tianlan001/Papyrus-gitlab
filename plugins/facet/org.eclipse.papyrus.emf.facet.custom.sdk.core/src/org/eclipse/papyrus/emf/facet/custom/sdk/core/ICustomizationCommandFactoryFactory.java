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
package org.eclipse.papyrus.emf.facet.custom.sdk.core;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.facet.custom.sdk.core.internal.CustomizationCommandFactory;
import org.eclipse.papyrus.emf.facet.custom.sdk.core.internal.CustomizationCommandFactoryFactory;

/**
 * This interface provide a command factory. A command modify the editingDomain.
 *
 * @since 0.3
 * @see CustomizationCommandFactory
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICustomizationCommandFactoryFactory {

	ICustomizationCommandFactoryFactory DEFAULT = new CustomizationCommandFactoryFactory();

	/**
	 * Set the editingDomain.
	 *
	 * @param editingDomain
	 *            the current editing domain.
	 */
	ICustomizationCommandFactory createICustomizationCommandFactory(
			EditingDomain editingDomain);

}
