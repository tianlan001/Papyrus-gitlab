/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) Remi.Schnekenburger@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils;

/**
 * Ids and constants for the editors extension point plugin
 */
public interface IDirectEditorsIds {

	/** Constant for the UML language */
	public final String UML_LANGUAGE = "Papyrus UML";

	/** Constant for the SysML language */
	public final String SYSML_LANGUAGE = "Papyrus SysML";

	/** Constant for the direct editors */
	public static final String SIMPLE_DIRECT_EDITOR = "Simple Direct Editor"; //$NON-NLS-1$

	/** constant for preferences */
	public static final String EDITOR_FOR_ELEMENT = "papyrus.directeditor.";

}
