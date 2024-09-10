/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.editor.handlers;

/**
 * The handler for the next tab command that lets the user navigate to
 * the next page of the active tab-folder with Ctrl+Tab
 * 
 * @author Shuai Li
 */
public class NextTabHandler extends TraverseTabHandler {
	public NextTabHandler() {
		super(false);
	}
}
