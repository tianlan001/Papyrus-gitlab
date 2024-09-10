/*******************************************************************************
 * Copyright (c) 2008 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jerome Benois (Obeo) jerome.benois@obeo.fr - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.commands;

import java.util.Collection;

import org.eclipse.papyrus.infra.core.extension.NotFoundException;

/**
 * Registry containing CreationCommand registered by Eclipse extension.
 *
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public interface ICreationCommandRegistry {

	/**
	 * Get all registered creation command descriptors
	 *
	 * @return collection of command descriptors
	 */
	public Collection<CreationCommandDescriptor> getCommandDescriptors();

	/**
	 * Get registered creation with given identifier
	 *
	 * @param commandId
	 *            the command id
	 * @return the registered command
	 * @throws NotFoundException
	 *             if command not registered
	 */
	ICreationCommand getCommand(String commandId) throws NotFoundException;
}
