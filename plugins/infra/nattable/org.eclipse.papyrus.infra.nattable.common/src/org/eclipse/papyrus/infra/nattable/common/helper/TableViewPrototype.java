/*****************************************************************************
 * Copyright (c) 2013, 2020 CEA LIST.
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
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 561371
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.helper;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.common.handlers.PolicyDefinedTableHandler;
import org.eclipse.papyrus.infra.nattable.common.internal.command.ITableCreationCommand;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;


/**
 * Represents the prototype of a table defined in a viewpoint
 *
 * @author Laurent Wouters
 */
public class TableViewPrototype extends ViewPrototype {

	/**
	 * The configuration to use to create a new table
	 */
	private TableConfiguration configuration;

	/**
	 * the command used to create a new table
	 */
	private ITableCreationCommand creationCommand;

	/**
	 *
	 * Constructor.
	 *
	 * @param prototype
	 *            the table {@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusTable} representation
	 */
	public TableViewPrototype(final PapyrusTable prototype) {
		this(prototype, null);
	}

	/**
	 * @param prototype
	 *            the {@link org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusTable} prototype used to create a new table
	 * @param creationCommand
	 *            the command to use to create a new table
	 *
	 *
	 * @since 5.5
	 */
	public TableViewPrototype(final PapyrusTable prototype, final ITableCreationCommand creationCommand) {
		super(prototype);
		this.configuration = prototype.getConfiguration();
		this.creationCommand = creationCommand;
	}

	/**
	 *
	 * @return
	 *         the table configuration to use to create a new table
	 * @since 5.5
	 */
	public TableConfiguration getTableConfiguration() {
		return this.configuration;
	}

	@Override
	public boolean isOwnerReassignable() {
		return true;
	}

	@Override
	public boolean instantiateOn(EObject owner) {
		return instantiateOn(owner, null);
	}

	@Override
	public boolean instantiateOn(EObject owner, String name) {
		if (configuration == null) {
			return false;
		}
		if (this.creationCommand != null) {
			ServicesRegistry registry;
			try {
				registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(owner);
			} catch (ServiceException ex) {
				Activator.log.error(ex);
				return false;
			}
			ModelSet modelSet;
			try {
				modelSet = registry.getService(ModelSet.class);
			} catch (ServiceException ex) {
				Activator.log.error(ex);
				return false;
			}
			Object result = this.creationCommand.createTable(modelSet, owner, owner, this, name, true);
			return result != null;
		}
		PolicyDefinedTableHandler handler = new PolicyDefinedTableHandler(configuration, owner, name);
		return handler.execute(this);
	}

	@Override
	public Command getCommandChangeOwner(EObject view, final EObject target) {
		final Table table = (Table) view;
		final EObject previous = table.getOwner();
		return new AbstractCommand("Change table owner") { //$NON-NLS-1$
			@Override
			public void execute() {
				table.setOwner(target);
			}

			@Override
			public void undo() {
				table.setOwner(previous);
			}

			@Override
			public void redo() {
				table.setOwner(target);
			}

			@Override
			protected boolean prepare() {
				return true;
			}
		};
	}

	@Override
	public Command getCommandChangeRoot(EObject view, final EObject target) {
		final Table table = (Table) view;
		final EObject previous = table.getContext();
		return new AbstractCommand("Change table root element") { //$NON-NLS-1$
			@Override
			public void execute() {
				table.setContext(target);
			}

			@Override
			public void undo() {
				table.setContext(previous);
			}

			@Override
			public void redo() {
				table.setContext(target);
			}

			@Override
			protected boolean prepare() {
				return true;
			}
		};
	}

	@Override
	public EObject getOwnerOf(EObject view) {
		return ((Table) view).getOwner();
	}

	@Override
	public EObject getRootOf(EObject view) {
		return ((Table) view).getContext();
	}
}
