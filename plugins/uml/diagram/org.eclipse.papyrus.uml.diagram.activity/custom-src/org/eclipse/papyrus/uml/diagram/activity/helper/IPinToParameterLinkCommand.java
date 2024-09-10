/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Arthur Daussy (Atos) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.helper;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Pin;

/**
 * Constant used to create link between {@link Pin} and {@link Parameter}
 *
 * @author arthur daussy
 *
 */
public interface IPinToParameterLinkCommand {

	/**
	 * Name of the {@link EAnnotation} use to store the link between a {@link Pin} and a {@link Parameter}
	 */
	public static final String PIN_TO_PARAMETER_LINK = "PinToParameterLink";

	/**
	 * ID use to inform the user that no link exist between a {@link Pin} and a {@link Parameter}
	 */
	public static final String NO_PARAMETER_ID = "NO_PARAMETER_ID";
}
