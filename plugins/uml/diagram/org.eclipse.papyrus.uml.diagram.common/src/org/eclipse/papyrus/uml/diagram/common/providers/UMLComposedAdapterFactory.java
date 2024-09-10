/*******************************************************************************
 * Copyright (c) 2010 Atos Origin.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Atos Origin - initial API and implementation
 *    Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.uml.internationalization.edit.providers.InternationalizationUMLItemProviderAdapterFactory;
import org.eclipse.uml2.uml.edit.providers.UMLResourceItemProviderAdapterFactory;

public class UMLComposedAdapterFactory extends ComposedAdapterFactory {

	private static final AdapterFactory[] factories = { new UMLResourceItemProviderAdapterFactory(), new InternationalizationUMLItemProviderAdapterFactory() };

	public UMLComposedAdapterFactory() {
		super(factories);
	}
}
