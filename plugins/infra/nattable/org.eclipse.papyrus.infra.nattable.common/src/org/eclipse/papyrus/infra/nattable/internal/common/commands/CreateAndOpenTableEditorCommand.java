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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.internal.common.commands;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.common.modelresource.PapyrusNattableModel;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableVersioningUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;

/**
 * 
 * This command allows to create a new table editor for a given context.
 * It opens the new editor, if the page manager argument is not <code>null</code>
 *
 */
public   class CreateAndOpenTableEditorCommand extends RecordingCommand {

	/**
	 * the command result, will contains the created table
	 */
	private Collection<?> result;

	/**
	 * the context to use to create the table
	 */
	private final EObject context;

	/**
	 * the owner of the table (used to display the table in the ModelView)
	 */
	private final EObject owner;

	/**
	 * the table configuration to use to create the table
	 */
	private final TableConfiguration configuration;

	/**
	 * the name to set to the new table
	 */
	private final String name;

	/**
	 * the description to set to the new table
	 */
	private final String description;

	/**
	 * the page manager to use to open the created table
	 */
	private final IPageManager pageManager;

	/**
	 * the papyrus table model, used to register the created table
	 */
	private final PapyrusNattableModel papyrusNattableModel;

	/**
	 * the view prototype used to create the new table
	 */
	private final TableViewPrototype tableViewPrototype;


	/**
	 * 
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain to use for the command, it cannot be <code>null</code>
	 * @param papyrusNattableModel
	 *            the Papyrus Table Model to use to register the createed table, it cannot be <code>null</code>
	 * @param tableViewPrototype
	 *            the table view prototype used to create the table, it cannot be <code>null</code>
	 * @param configuration
	 *            the configuration used to create the new table, it cannot be <code>null</code>
	 * @param context
	 *            the context of the created table, it cannot be <code>null</code>. This value will be used for the owner of the table too.
	 * @param name
	 *            the name of the new table, it cannot be <code>null</code>
	 * @param pageManager
	 *            the page manager used to open the new table editor. If <code>null</code> the created table will not be opened
	 */
	public CreateAndOpenTableEditorCommand(final TransactionalEditingDomain domain, final PapyrusNattableModel papyrusNattableModel, final TableViewPrototype tableViewPrototype, final TableConfiguration configuration, final EObject context,
			final String name, final IPageManager pageManager) {
		this(domain, papyrusNattableModel, tableViewPrototype, configuration, context, name, null, null, pageManager);
	}




	/**
	 * 
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain to use for the command, it cannot be <code>null</code>
	 * @param papyrusNattableModel
	 *            the Papyrus Table Model to use to register the createed table, it cannot be <code>null</code>
	 * @param tableViewPrototype
	 *            the table view prototype used to create the table, it cannot be <code>null</code>
	 * @param configuration
	 *            the configuration used to create the new table, it cannot be <code>null</code>
	 * @param context
	 *            the context of the created table, it cannot be <code>null</code>. This value will be used for the owner of the table too.
	 * @param name
	 *            the name of the new table, it cannot be <code>null</code>
	 * @param description
	 *            the description of the new table
	 * @param pageManager
	 *            the page manager used to open the new table editor. If <code>null</code> the created table will not be opened
	 */
	public CreateAndOpenTableEditorCommand(final TransactionalEditingDomain domain, final PapyrusNattableModel papyrusNattableModel, final TableViewPrototype tableViewPrototype, final TableConfiguration configuration, final EObject context,
			final String name, final String description, final IPageManager pageManager) {
		this(domain, papyrusNattableModel, tableViewPrototype, configuration, context, name, null, description, pageManager);
	}


	/**
	 * 
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain to use for the command, it cannot be <code>null</code>
	 * @param papyrusNattableModel
	 *            the Papyrus Table Model to use to register the createed table, it cannot be <code>null</code>
	 * @param tableViewPrototype
	 *            the table view prototype used to create the table, it cannot be <code>null</code>
	 * @param configuration
	 *            the configuration used to create the new table, it cannot be <code>null</code>
	 * @param context
	 *            the context of the created table, it cannot be <code>null</code>
	 * @param name
	 *            the name of the new table, it cannot be <code>null</code>
	 * @param owner
	 *            the owner of the table (used to display the table in the ModelView). If <code>null</code>, we will use the context of the table
	 * @param pageManager
	 *            the page manager used to open the new table editor. If <code>null</code> the created table will not be opened
	 */
	public CreateAndOpenTableEditorCommand(final TransactionalEditingDomain domain, final PapyrusNattableModel papyrusNattableModel, final TableViewPrototype tableViewPrototype, final TableConfiguration configuration, final EObject context,
			final String name, final EObject owner, final IPageManager pageManager) {
		this(domain, papyrusNattableModel, tableViewPrototype, configuration, context, name, owner, null, pageManager);
	}



	/**
	 * 
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain to use for the command, it cannot be <code>null</code>
	 * @param papyrusNattableModel
	 *            the Papyrus Table Model to use to register the createed table, it cannot be <code>null</code>
	 * @param tableViewPrototype
	 *            the table view prototype used to create the table, it cannot be <code>null</code>
	 * @param configuration
	 *            the configuration used to create the new table, it cannot be <code>null</code>
	 * @param context
	 *            the context of the created table, it cannot be <code>null</code>
	 * @param name
	 *            the name of the new table, it cannot be <code>null</code>
	 * @param owner
	 *            the owner of the table (used to display the table in the ModelView). If <code>null</code>, we will use the context of the table
	 * @param description
	 *            the description of the new table
	 * @param pageManager
	 *            the page manager used to open the new table editor. If <code>null</code> the created table will not be opened
	 */
	public CreateAndOpenTableEditorCommand(final TransactionalEditingDomain domain, final PapyrusNattableModel papyrusNattableModel, final TableViewPrototype tableViewPrototype, final TableConfiguration configuration, final EObject context,
			final String name, final EObject owner, final String description, final IPageManager pageManager) {
		super(domain, "Create a new table editor");
		// check parameters which ca not be null
		Assert.isNotNull(tableViewPrototype);
		Assert.isNotNull(context);
		//Assert.isNotNull(owner); we allow to have a null owner
		Assert.isNotNull(configuration);
		Assert.isNotNull(name);
		Assert.isNotNull(papyrusNattableModel);

		this.context = context;
		this.owner = owner;
		this.configuration = configuration;
		this.papyrusNattableModel = papyrusNattableModel;
		this.tableViewPrototype = tableViewPrototype;
		this.name = name;
		this.description = description;
		this.pageManager = pageManager;
	}

	
	/**
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		final Table table = TableHelper.createTable(configuration, null, this.name, this.description); // context null here, see bug 410357
		TableVersioningUtils.stampCurrentVersion(table);
		
		table.setContext(this.context);
		// Save the model in the associated resource
		this.papyrusNattableModel.addPapyrusTable(table);
		
		if (this.owner == null) {
			table.setOwner(this.context);
		} else {
			table.setOwner(this.owner);
		}
		table.setTableKindId(tableViewPrototype.getRepresentationKind().getId());
		if (this.pageManager != null) {
			this.pageManager.openPage(table);
		}
		result = Collections.singleton(table);
	}

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#getResult()
	 *
	 * @return
	 */
	@Override
	public Collection<?> getResult() {
		if (this.result != null) {
			return this.result;
		}
		return super.getResult();
	}


}