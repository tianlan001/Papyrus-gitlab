/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) - Initial API and implementation
 *      
 *****************************************************************************/

package org.eclipse.papyrus.internal.infra.nattable.model.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;

/**
 * @author Vincent LORENZO
 * @since 4.1
 */
public class NattableConfigurationResourceFactory implements Factory {

	@Override
	public Resource createResource(final URI uri) {
		return new NattableConfigurationResource(uri);
	}

}
