/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.architecture.api;

import java.util.Collection;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.architecture.internal.customizationconfiguration.comparators.CustomizationReferenceMerger;

/**
 * this factory is used to provide an instance of ICustomizationReferenceMerger
 */
public class CustomizationFacetFactory {

	public static ICustomizationReferenceMerger getCustomizationReferenceMerger(final Collection<EMFFacetTreeViewerConfiguration> references) {
		return new CustomizationReferenceMerger(references);
	}
}
