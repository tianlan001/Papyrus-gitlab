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
 *  	Gregoire Dupe (Mia-Software) - Bug 361794 - [Restructuring] New customization meta-model
 *      David Couvrand (Soft-Maint) - Bug 418418 - [Customization] Overlay icons not implemented
 */
package org.eclipse.papyrus.emf.facet.custom.ui.internal;

import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.ui.ICustomizedLabelProvider;
import org.eclipse.papyrus.emf.facet.custom.ui.ICustomizedLabelProviderFactory;

public class CustomizedLabelProviderFactory implements
		ICustomizedLabelProviderFactory {

	public ICustomizedLabelProvider createCustomizedLabelProvider(
			final ICustomizationManager customizationMgr) {
		return new DecoratingCustomizedLabelProvider(
				customizationMgr);
	}

}
