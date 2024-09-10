/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.customization.properties.generation.providers;

import java.util.List;

import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.views.properties.toolsmiths.providers.PropertyContentProvider;


public class DataContextPropertiesContentProvider extends PropertyContentProvider {

	private final List<Context> contexts;

	public DataContextPropertiesContentProvider(List<Context> contexts) {
		super(contexts.get(0));
		this.contexts = contexts;
	}

	@Override
	public Object[] getElements() {
		// We're only interested in the generated contexts ; not in their dependencies
		return contexts.toArray();
	}

}
