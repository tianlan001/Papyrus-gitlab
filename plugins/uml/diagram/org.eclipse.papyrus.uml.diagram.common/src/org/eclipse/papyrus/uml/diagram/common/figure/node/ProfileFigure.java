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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;


public class ProfileFigure extends PackageFigure {

	protected static final String PROFILE_TAG = "Profile"; //$NON-NLS-1$

	/**
	 * Instantiates a new profile figure.
	 */
	public ProfileFigure() {
		super();
		initTagLabel(PROFILE_TAG);
	}

}
