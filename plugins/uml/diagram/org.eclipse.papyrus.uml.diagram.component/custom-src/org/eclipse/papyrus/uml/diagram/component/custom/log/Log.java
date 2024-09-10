/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.log;

import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramEditorPlugin;


/**
 * Log singleton class for CSD.
 *
 * @since 3.0
 */
public class Log extends LogHelper {

	/** Singleton instance. */
	private static Log instance;

	/**
	 * Access restriction on constructors.
	 */
	private Log() {
		super(UMLDiagramEditorPlugin.getInstance());
	}

	/**
	 * Singleton access.
	 *
	 * @return single instance of Log
	 */
	public static synchronized Log getInstance() {

		if (instance == null) {
			instance = new Log();
		}
		return instance;
	}
}
