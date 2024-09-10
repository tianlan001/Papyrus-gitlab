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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.extension.diagrameditor;

/**
 * Editor was not found.
 *
 * @author dumoulin
 * @since 1.2
 *
 */
@SuppressWarnings("serial")
public class EditorNotFoundException extends MultiDiagramException {

	/**
	 *
	 */
	public EditorNotFoundException() {
	}

	/**
	 * @param arg0
	 */
	public EditorNotFoundException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public EditorNotFoundException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EditorNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
