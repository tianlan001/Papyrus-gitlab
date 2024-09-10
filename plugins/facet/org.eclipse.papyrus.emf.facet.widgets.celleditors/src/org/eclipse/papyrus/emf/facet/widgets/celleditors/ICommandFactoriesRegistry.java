/*******************************************************************************
 * Copyright (c) 2011 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Guyomar (Mia-Software) - Bug 339554 - org.eclipse.papyrus.emf.facet.widgets.celleditors API cleaning
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.widgets.celleditors;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.facet.widgets.celleditors.internal.CommandFactoriesRegistry;

/**
 * Registry for the "commandFactories" extension point. It is used to create EMF {@link Command}s
 * compatible with a given {@link EditingDomain}.
 * <p>
 * For example, a <code>TransactionalEditingDomain</code> doesn't work with the usual EMF commands, and the model must be manipulated with {@link Command}s that use transactions.
 */
public interface ICommandFactoriesRegistry {

	/** the singleton {@link ICommandFactoriesRegistry} */
	static ICommandFactoriesRegistry INSTANCE = new CommandFactoriesRegistry();

	/** @return the list of registered command factories */
	List<ICommandFactory> getCommandFactories();

	/**
	 * @return a command factory compatible with the given {@link EditingDomain}, or <code>null</code> if none is found
	 */
	ICommandFactory getCommandFactoryFor(final EditingDomain editingDomain);
}