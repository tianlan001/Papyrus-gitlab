/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.edit.domain;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.papyrus.infra.emf.commands.PapyrusDeleteCommand;

/**
 * A specialization of the default {@link TransactionalEditingDomain} implementation for use in Papyrus editors and
 * other Papyrus-specific contexts. Amongst possibly other things, it provides customizations of certain EMF.Edit
 * {@link Command}s tailored to the Papyrus frameworks.
 * 
 * @see #createCommand(Class, CommandParameter)
 */
public class PapyrusTransactionalEditingDomain extends TransactionalEditingDomainImpl {
	/**
	 * Static factory instance that creates {@link PapyrusTransactionalEditingDomain}s.
	 */
	public static Factory FACTORY = new FactoryImpl() {
		@Override
		public synchronized TransactionalEditingDomain createEditingDomain() {
			TransactionalEditingDomain result = new PapyrusTransactionalEditingDomain(
					new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));

			mapResourceSet(result);

			return result;
		}

		@Override
		public synchronized TransactionalEditingDomain createEditingDomain(ResourceSet rset) {
			TransactionalEditingDomain result = new PapyrusTransactionalEditingDomain(
					new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), rset);

			mapResourceSet(result);

			return result;
		}
	};

	public PapyrusTransactionalEditingDomain(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	public PapyrusTransactionalEditingDomain(AdapterFactory adapterFactory, TransactionalCommandStack stack) {
		super(adapterFactory, stack);
	}

	public PapyrusTransactionalEditingDomain(AdapterFactory adapterFactory, ResourceSet resourceSet) {
		super(adapterFactory, resourceSet);
	}

	public PapyrusTransactionalEditingDomain(AdapterFactory adapterFactory, TransactionalCommandStack stack, ResourceSet resourceSet) {
		super(adapterFactory, stack, resourceSet);
	}

	@Override
	public Command createCommand(Class<? extends Command> commandClass, CommandParameter commandParameter) {
		Command result;

		if (commandClass == DeleteCommand.class) {
			result = new PapyrusDeleteCommand(this, commandParameter.getCollection());
		} else {
			result = super.createCommand(commandClass, commandParameter);
		}

		return result;
	}


}
