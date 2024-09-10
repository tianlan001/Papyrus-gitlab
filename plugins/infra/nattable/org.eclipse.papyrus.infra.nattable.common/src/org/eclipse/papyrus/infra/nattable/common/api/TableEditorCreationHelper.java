/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.common.api;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.common.modelresource.PapyrusNattableModel;
import org.eclipse.papyrus.infra.nattable.internal.common.commands.CreateAndOpenTableEditorCommand;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * This class can be used to create a new table editor programmatically
 *
 */
public class TableEditorCreationHelper {

	/*---------the field initialized by the constructor*/

	/**
	 * the context of the table to create
	 */
	private EObject tableContext;

	/**
	 * the owner of the created table (will be used to display the table as its child in the ModelExplorer View). If <code>null</code> we use the tableContext.
	 */
	private EObject tableOwner;

	/**
	 * the type of the table to create (also called implementationID in the Viewpoint framework)
	 */
	private String tableType;

	/**
	 * the name of the new table
	 */
	private String tableName;

	/**
	 * the description to use for the new table
	 */
	private String tableDescription;

	/**
	 * a boolean to know if we must open the table editor after its creation
	 */
	private boolean openCreatedEditor;

	/*--------------the calculated field ---------------------*/
	/**
	 * the service registry of the edited model
	 */
	private ServicesRegistry servicesRegistry;

	/**
	 * the model set of the edited model
	 */
	private ModelSet modelSet;

	/**
	 * the papyrus nattable model (used to register the created table)
	 */
	private PapyrusNattableModel papyrusNattableModel;

	/**
	 * the table view prototype
	 */
	private TableViewPrototype tableViewPrototype;

	/**
	 * the table configuration used to create the table
	 */
	private TableConfiguration tableConfiguration;

	/**
	 * the editing domain used to create the command
	 */
	private TransactionalEditingDomain editingDomain;

	/**
	 * the page manager used to open the created table
	 */
	private IPageManager pageMngr;

	/**
	 * the command to use to create the table
	 */
	private Command creationCommand;

	/**
	 * 
	 * Constructor.
	 *
	 * @param tableContext
	 *            the context used to create the table, it should not be <code>null</code>. This object will be used as owner of the table too.
	 * @param tableType
	 *            the type of the table to create. It should not be <code>null</code>
	 * @param name
	 *            the name of the new created table. It should not be <code>null</code>
	 * @param openCreatedEditor
	 *            if <code>true</code>, the created table will be open
	 */
	public TableEditorCreationHelper(final EObject tableContext, final String tableType, final String name, final boolean openCreatedEditor) {
		this(tableContext, tableType, name, openCreatedEditor, null, null);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param tableContext
	 *            the context used to create the table, it should not be <code>null</code>. This object will be used as owner of the table too.
	 * @param tableType
	 *            the type of the table to create. It should not be <code>null</code>
	 * @param name
	 *            the name of the new created table. It should not be <code>null</code>
	 * @param openCreatedEditor
	 *            if <code>true</code>, the created table will be open
	 * @param description
	 *            the description of the created table
	 */
	public TableEditorCreationHelper(final EObject tableContext, final String tableType, final String name, final boolean openCreatedEditor, final String description) {
		this(tableContext, tableType, name, openCreatedEditor, null, description);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param tableContext
	 *            the context used to create the table, it should not be <code>null</code>
	 * @param tableType
	 *            the type of the table to create. It should not be <code>null</code>
	 * @param name
	 *            the name of the new created table. It should not be <code>null</code>
	 * @param openCreatedEditor
	 *            if <code>true</code>, the created table will be open
	 * @param tableOwner
	 *            the owner of the created table. If <code>null</code>, we will use the context of the table as owner
	 */
	public TableEditorCreationHelper(final EObject tableContext, final String tableType, final String name, final boolean openCreatedEditor, final EObject tableOwner) {
		this(tableContext, tableType, name, openCreatedEditor, tableOwner, null);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * the context used to create the table, it should not be <code>null</code>
	 * 
	 * @param tableType
	 *            the type of the table to create. It should not be <code>null</code>
	 * @param name
	 *            the name of the new created table. It should not be <code>null</code>
	 * @param openCreatedEditor
	 *            if <code>true</code>, the created table will be open
	 * @param tableOwner
	 *            the owner of the created table. If <code>null</code>, we will use the context of the table as owner
	 * @param description
	 *            the description of the created table
	 */
	public TableEditorCreationHelper(final EObject tableContext, final String tableType, final String name, final boolean openCreatedEditor, final EObject tableOwner, final String description) {
		this.tableContext = tableContext;
		this.tableOwner = tableOwner;
		this.tableType = tableType;
		this.tableName = name;
		this.tableDescription = description;
		this.openCreatedEditor = openCreatedEditor;
	}


	/**
	 * This method initis the fields required to create a new table and return a IStatus indicating if the table can be created with the given context
	 * 
	 * @return
	 * 		a IStatus to know if the table can be created according to the table context and the table type:
	 *         <ul>
	 *         <li>{@link #tableContext}</li>
	 *         <li>{@link #tableType}</li>
	 *         <li>{@link #tableName}</li>
	 *         </ul>
	 *         The returned IStatus provides a message for each error and a code. The possible code are described in {@link ITableEditorStatusCode}
	 * 
	 */
	public IStatus canCreateTable() {
		final String pluginID = getPluginID();
		// 0. check field
		if (this.tableContext == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.TABLE_CONTEXT_IS_NULL, "The table context is null", null); //$NON-NLS-1$
		}

		if (this.tableType == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.TABLE_TYPE_IS_NULL, "The table type is null", null); //$NON-NLS-1$
		}

		if (this.tableName == null || this.tableName.isEmpty()) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.TABLE_NAME_IS_NULL, "The name of the table to create is null or empty", null); //$NON-NLS-1$
		}
		// 1. check the service registry
		try {
			this.servicesRegistry = ServiceUtilsForResource.getInstance().getServiceRegistry(tableContext.eResource());
		} catch (ServiceException e) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.SERVICES_REGISTRY_NOT_FOUND, e.getMessage(), e);
		}


		// 2. check the model set prototype
		try {
			this.modelSet = ServiceUtils.getInstance().getModelSet(this.servicesRegistry);
		} catch (ServiceException e) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.MODEL_SET_NOT_FOUND, e.getMessage(), e);
		}

		// 3. check the editing model
		try {
			this.editingDomain = ServiceUtils.getInstance().getTransactionalEditingDomain(servicesRegistry);
		} catch (ServiceException e) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.EDITING_DOMAIN_NOT_FOUND, e.getMessage(), e);
		}

		if (editingDomain == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.EDITING_DOMAIN_NOT_FOUND, "The editing domain has not been found", null); //$NON-NLS-1$
		}

		// 4. check the page manager
		try {
			this.pageMngr = ServiceUtils.getInstance().getService(IPageManager.class, this.servicesRegistry);
		} catch (ServiceException e) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.PAGE_MANAGER_NOT_FOUND, e.getMessage(), e);
		}

		if (this.pageMngr == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.PAGE_MANAGER_NOT_FOUND, "The page manager has not be found", null); //$NON-NLS-1$
		}

		// 5. check the Papyrus Nattable Model
		try {
			this.papyrusNattableModel = (PapyrusNattableModel) modelSet.getModelChecked(PapyrusNattableModel.MODEL_ID);
		} catch (NotFoundException e) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.PAPYRUS_NATTABLE_MODEL_NOT_FOUND, e.getMessage(), e);
		}

		if (this.papyrusNattableModel == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.PAPYRUS_NATTABLE_MODEL_NOT_FOUND, "Papyrus Nattable Model not found", null); //$NON-NLS-1$
		}

		// 6. check the table view prototype
		this.tableViewPrototype = getTableViewPrototype(tableContext, tableType);
		if (this.tableViewPrototype == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.TABLE_VIEW_PROTOTYPE_NOT_FOUND, NLS.bind("The View Prototype to create a {0} table has not been found", this.tableType), null); //$NON-NLS-1$
		}

		// 7. get the nattable configuration
		this.tableConfiguration = getTableConfiguration(tableViewPrototype);

		if (this.tableConfiguration == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.TABLE_CONFIGURATION_NOT_FOUND, NLS.bind("Table configuration not found for the table type {0}", this.tableType), null); //$NON-NLS-1$
		}

		// 9. create and check the command
		this.creationCommand = getCreateTableEditorCommand();
		if (this.creationCommand == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.COMMAND_NULL, "The creation command cannot be created", null); //$NON-NLS-1$
		}
		if (!this.creationCommand.canExecute()) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.COMMAND_NOT_EXECUTABLE, "The creation command is not executable", null); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}

	/**
	 * 
	 * @return
	 * 		the activator id of the plugin
	 */
	protected String getPluginID() {
		return Activator.PLUGIN_ID;
	}

	/**
	 * 
	 * @return
	 * 		the command to create a open (if {@link #openCreatedEditor} is <code>true</code> a new table editor
	 */
	public Command getCreateTableEditorCommand() {
		EObject tableOwner = this.tableOwner;
		if (tableOwner == null) {
			this.tableOwner = this.tableContext;
		}
		if (openCreatedEditor) {
			return new CreateAndOpenTableEditorCommand(this.editingDomain, this.papyrusNattableModel, this.tableViewPrototype, this.tableConfiguration, this.tableContext, this.tableName, tableOwner, this.tableDescription, this.pageMngr);
		} else {
			return new CreateAndOpenTableEditorCommand(this.editingDomain, this.papyrusNattableModel, this.tableViewPrototype, this.tableConfiguration, this.tableContext, this.tableName, tableOwner, this.tableDescription, null);
		}
	}

	/**
	 * 
	 * @return
	 * 		the created table or <code>null</code> if it is not possible to create the command
	 */
	public Table createTableEditor() {
		if (canCreateTable().isOK()) {
			if (this.creationCommand != null && this.creationCommand.canExecute()) {
				this.editingDomain.getCommandStack().execute(creationCommand);
			}
			if (this.creationCommand != null) {
				Collection<?> result = creationCommand.getResult();
				if (result.size() > 0) {
					Object res = result.iterator().next();
					if (res instanceof Table) {
						return (Table) res;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param viewPrototype
	 *            a view {@link TableViewPrototype}, must not be <code>null</code>
	 * @return
	 * 		a TableConfiguration, or <code>null</code> if not found
	 * @since 3.0
	 */
	protected TableConfiguration getTableConfiguration(TableViewPrototype viewPrototype) {
		Assert.isNotNull(viewPrototype);
		if (viewPrototype.getRepresentationKind() instanceof PapyrusTable) {
			PapyrusTable papyrusTable = (PapyrusTable) viewPrototype.getRepresentationKind();
			return papyrusTable.getConfiguration();
		}
		return null;
	}

	/**
	 * 
	 * @param tableContext
	 *            the context to use to create the table, should not be <code>null</code>
	 * @param tableType
	 *            the type of the table (also called implementationID in the Viewpoint framework), should not be <code>null</code>
	 * @return
	 * 		the {@link TableViewPrototype} if we found it, or <code>null</code>
	 */
	protected TableViewPrototype getTableViewPrototype(final EObject tableContext, final String tableType) {
		if (tableContext == null || tableType == null) {
			return null;
		}
		// 1. get all available view prototype for the table context
		Collection<ViewPrototype> prototypes = PolicyChecker.getFor(tableContext).getPrototypesFor(tableContext);
		Iterator<ViewPrototype> iter = prototypes.iterator();

		// 2. find the view prototype allowing to create the wanted table, identified by it ViewPrototype
		while (iter.hasNext()) {
			ViewPrototype current = iter.next();
			if (current instanceof TableViewPrototype) {
				TableViewPrototype prototype = (TableViewPrototype) current;
				String implementationID = prototype.getImplementation();
				if (tableType.equals(implementationID)) {
					return prototype;
				}
				if (implementationID == null || implementationID.isEmpty()) {
					PapyrusRepresentationKind representationKind = prototype.getRepresentationKind();
					if (representationKind instanceof PapyrusTable) {
						// we need to load the real table configuration to check the type
						PapyrusTable papyrusTable = (PapyrusTable) representationKind;
						TableConfiguration configuration = papyrusTable.getConfiguration();
						if (configuration.getType().equals(tableType)) { // to check ?
							return prototype;
						}
					}
				}
			}
		}
		return null;
	}


	/**
	 * @param tableContext
	 *            the tableContext to set
	 */
	public void setTableContext(EObject tableContext) {
		this.tableContext = tableContext;
	}


	/**
	 * @param tableOwner
	 *            the tableOwner to set
	 */
	public void setTableOwner(EObject tableOwner) {
		this.tableOwner = tableOwner;
	}


	/**
	 * @param tableType
	 *            the tableType to set
	 */
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}


	/**
	 * @param name
	 *            the name to set
	 */
	public void setTableName(String name) {
		this.tableName = name;
	}


	/**
	 * @param description
	 *            the description to set
	 */
	public void setTableDescription(String description) {
		this.tableDescription = description;
	}


}
