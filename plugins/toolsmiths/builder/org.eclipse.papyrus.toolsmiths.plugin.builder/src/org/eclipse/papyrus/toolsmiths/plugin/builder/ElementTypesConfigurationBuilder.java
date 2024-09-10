/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 573245
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder;

import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This class is builder dedicated to elementType
 *
 * @deprecated Since the 1.1 release of the bundle, this class is superseded by the extensible project builder and is no longer used.
 * @see <a href="https://eclip.se/573251">bug 573251</a> to watch for the removal of this API in a future release
 */
@Deprecated(since = "1.1", forRemoval = true)
public class ElementTypesConfigurationBuilder extends GenericEMFModelBuilder {

	static final String ELEMENT_TYPES_CONFIGURATION_EXTENSION = "elementtypesconfigurations";//$NON-NLS-1$

	@Override
	protected Collection<Diagnostic> validateResource(Resource resource) {
		return super.validateResource(resource);
	}

	/**
	 * @see org.eclipse.papyrus.toolsmiths.plugin.builder.GenericEMFModelBuilder#isIgnoredFileExtension(java.lang.String)
	 *
	 * @param fileExtension
	 * @return
	 */
	@Override
	protected boolean managedFileExtension(String fileExtension) {
		return ELEMENT_TYPES_CONFIGURATION_EXTENSION.equals(fileExtension);
	}

}
