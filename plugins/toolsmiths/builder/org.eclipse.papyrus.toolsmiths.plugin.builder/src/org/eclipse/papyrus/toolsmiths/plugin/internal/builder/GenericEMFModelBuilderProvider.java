/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.internal.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.GenericEMFModelBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.IPapyrusBuilderProvider;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusBuilderKind;
import org.osgi.service.component.annotations.Component;

/**
 * Provider of generic builder covering model resources not specifically handled by other providers.
 */
@Component
public class GenericEMFModelBuilderProvider implements IPapyrusBuilderProvider {

	@Override
	public String getProblemMarkerType(PapyrusBuilderKind builderKind) {
		return IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER;
	}

	@Override
	public boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI) {
		// The GenericEMFModelBuilder filters model resources for itself
		return false;
	}

	@Override
	public AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project) {
		return builderKind == PapyrusBuilderKind.MODEL_RESOURCE ? new GenericEMFModelBuilder() : null;
	}

}
