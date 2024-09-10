/**
 *  Copyright (c) 2012 Mia-Software.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  	Gregoire Dupe (Mia-Software) - Bug 369987 - [Restructuring][Table] Switch to the new customization and facet framework
 */
package org.eclipse.papyrus.emf.facet.custom.ui.internal.exported;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.facet.custom.ui.internal.CustomizationCommandFactoryFactory;
import org.eclipse.papyrus.emf.facet.util.emf.core.internal.exported.ICommandFactory;

public interface ICustomizationCommandFactoryFactory {

	ICustomizationCommandFactoryFactory DEFAULT = new CustomizationCommandFactoryFactory();

	ICustomizationCommandFactory createCustomizationCommandFactory(
			EditingDomain editingDomain, ICommandFactory commandFactory);
}
