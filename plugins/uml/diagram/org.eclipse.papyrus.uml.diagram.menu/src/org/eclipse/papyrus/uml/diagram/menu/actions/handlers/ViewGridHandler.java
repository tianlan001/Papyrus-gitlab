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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.menu.actions.handlers;

import org.eclipse.gmf.runtime.diagram.ui.internal.properties.WorkspaceViewerProperties;

/**
 *
 * Handler for the View Grid Action
 *
 *
 */
@SuppressWarnings("restriction")
public class ViewGridHandler extends AbstractViewHandler {


	/**
	 *
	 * Constructor.
	 *
	 */
	public ViewGridHandler() {
		super(WorkspaceViewerProperties.VIEWGRID);
	}
}
