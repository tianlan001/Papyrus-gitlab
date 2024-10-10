/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests;

/**
 * This class provides creation tools ids for Sirius Package Diagram odesign.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class CreationToolsIds {

	/*----------------------------------------------The Node creation tools IDs----------------------------------------------*/

	public static final String CREATE__COMMENT__TOOL = "CreateCommentTool"; //$NON-NLS-1$

	public static final String CREATE__CONSTRAINT__TOOL = "CreateConstraintTool"; //$NON-NLS-1$

	public static final String CREATE__PACKAGE__TOOL = "CreatePackageTool"; //$NON-NLS-1$

	/*----------------------------------------------The Edge creation tools IDs----------------------------------------------*/

	public static final String CREATE__ABSTRACTION__TOOL = "CreateAbstractionTool"; //$NON-NLS-1$

	public static final String CREATE__DEPENDENCY__TOOL = "CreateDependencyTool"; //$NON-NLS-1$

	public static final String CREATE__LINK__TOOL = "CreateLinkTool"; //$NON-NLS-1$

	public static final String CREATE__MODEL__TOOL = "CreateModelTool"; //$NON-NLS-1$

	public static final String CREATE__PACKAGE_IMPORT__TOOL = "CreatePackageImportTool"; //$NON-NLS-1$

	private CreationToolsIds() {
		// to prevent instantiation
	}
}
