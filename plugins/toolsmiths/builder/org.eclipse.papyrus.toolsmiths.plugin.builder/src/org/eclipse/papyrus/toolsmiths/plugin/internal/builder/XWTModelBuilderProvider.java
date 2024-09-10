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

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.IPapyrusBuilderProvider;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusBuilderKind;
import org.eclipse.papyrus.toolsmiths.plugin.builder.XWTModelBuilder;
import org.osgi.service.component.annotations.Component;

/**
 * Provider of the XWT model resource builder.
 */
@Component
public class XWTModelBuilderProvider implements IPapyrusBuilderProvider {

	public static final Set<String> MODEL_FILE_EXTENSIONS = Set.of("xwt"); //$NON-NLS-1$

	@Override
	public String getProblemMarkerType(PapyrusBuilderKind builderKind) {
		return IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER;
	}

	@Override
	public boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI) {
		return builderKind == PapyrusBuilderKind.MODEL_RESOURCE
				&& resourceURI.fileExtension() != null
				&& MODEL_FILE_EXTENSIONS.contains(resourceURI.fileExtension());
	}

	@Override
	public AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project) {
		return builderKind == PapyrusBuilderKind.MODEL_RESOURCE ? new XWTModelBuilder() : null;
	}

}
