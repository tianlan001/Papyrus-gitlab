/*****************************************************************************
 * Copyright (c) 2009 ATOS ORIGIN.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *  Tristan Faure (ATOS ORIGIN) tristan.faure@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.extension.commands;

import org.eclipse.emf.ecore.EObject;

/**
 * @since 1.2
 */
public interface ICreationCondition {

	/**
	 * This method returns true if the diagram creation is allowed
	 *
	 * @param selectedElement
	 *            the element where the diagram is provided
	 * @return true if the diagram can be created
	 */
	boolean create(EObject selectedElement);

	/**
	 * set the command ID in order to take account the environment in order to
	 * create a diagram
	 *
	 * @param commandID
	 */
	public void setCommand(String commandID);

}
