/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos Origin Integration, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (Atos Origin Integration) tristan.faure@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Stefan Dirix (EclipseSource Muenchen GmbH) sdirix@eclipsesource.com - Remove adaption to IResource and IFile
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.onefile.model.adapters;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.papyrus.infra.onefile.model.mapping.PapyrusResourceMapping;

/**
 * Adapter factory to adapt {@link IPapyrusFile}
 *
 * @author tristan.faure@atosorigin.com
 */
public class ModelAdapterFactory implements IAdapterFactory {

	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (ResourceMapping.class.equals(adapterType)) {
			if (adaptableObject instanceof IPapyrusFile) {
				return adapterType.cast(new PapyrusResourceMapping((IPapyrusFile) adaptableObject));
			}
		}
		if (Collection.class.equals(adapterType)) {
			if (adaptableObject instanceof IPapyrusFile) {
				return adapterType.cast(Arrays.asList(((IPapyrusFile) adaptableObject).getAssociatedResources()));
			}
		}
		return null;
	}

	public Class<?>[] getAdapterList() {
		return new Class[] { ResourceMapping.class, Collection.class };
	}

}
