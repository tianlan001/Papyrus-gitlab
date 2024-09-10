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
package org.eclipse.papyrus.emf.facet.custom.sdk.core.internal;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.facet.custom.sdk.core.ICustomizationCommandFactory;
import org.eclipse.papyrus.emf.facet.custom.sdk.core.ICustomizationCommandFactoryFactory;

public class CustomizationCommandFactoryFactory implements
		ICustomizationCommandFactoryFactory {

	public ICustomizationCommandFactory createICustomizationCommandFactory(
			final EditingDomain editingDomain) {
		return new CustomizationCommandFactory(editingDomain);
	}

}
