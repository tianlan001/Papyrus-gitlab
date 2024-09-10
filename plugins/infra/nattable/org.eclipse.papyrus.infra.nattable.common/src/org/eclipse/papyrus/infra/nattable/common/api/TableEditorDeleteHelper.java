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

package org.eclipse.papyrus.infra.nattable.common.api;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 * @author Vincent Lorenzo
 *
 */
public class TableEditorDeleteHelper {

	/**
	 * the table to delete
	 */
	private Table table;

	/* these fields are determined calculating calling canDeleteTableEditor */

	/**
	 * the command provider (calculated calling {@link #canDeleteTableEditor()})
	 */
	private IElementEditService commandProvider;

	/**
	 * the command to delete the table (calculated calling {@link #canDeleteTableEditor()})
	 */
	private Command deleteCommand;

	/**
	 * the editing domain used to execute the command
	 */
	private TransactionalEditingDomain editingDomain;

	/**
	 * 
	 * Constructor.
	 *
	 * @param table
	 *            the table to delete
	 */
	public TableEditorDeleteHelper(final Table table) {
		this.table = table;
	}

	/**
	 * 
	 * @return
	 * 		a IStatus indicating if the table can be destroyed
	 *         The returned IStatus provides a message for each error and a code. The possible code are described in {@link ITableEditorStatusCode}
	 */
	@SuppressWarnings("unchecked")
	public IStatus canDeleteTableEditor() {
		final String pluginID = getPluginID();
		// 0. check given parameters
		if (this.table == null) {
			return new Status(IStatus.OK, pluginID, ITableEditorStatusCode.OK_STATUS_CODE, "There is no table to delete", null); //$NON-NLS-1$
		}

		// 1. check read only
		if (EMFHelper.isReadOnly(this.table)) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.TABLE_IS_READ_ONLY, "The table to delete is read-only", null); //$NON-NLS-1$
		}

		// 2. get the command provider
		this.commandProvider = ElementEditServiceUtils.getCommandProvider(this.table);
		if (this.commandProvider == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.COMMAND_PROVIDER_NOT_FOUND, "There is no command provider to delete the table", null); //$NON-NLS-1$
		}

		// 3. build the delete request
		DestroyElementRequest request = new DestroyElementRequest(this.table, false);


		// 4. check the editing domain
		ServicesRegistry servicesRegistry;
		try {
			servicesRegistry = ServiceUtilsForResource.getInstance().getServiceRegistry(table.getContext().eResource());
		} catch (ServiceException e) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.SERVICES_REGISTRY_NOT_FOUND, e.getMessage(), e);
		}
		if (servicesRegistry == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.SERVICES_REGISTRY_NOT_FOUND, "Service Registry not found", null); //$NON-NLS-1$
		}

		try {
			this.editingDomain = ServiceUtils.getInstance().getTransactionalEditingDomain(servicesRegistry);
		} catch (ServiceException e) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.EDITING_DOMAIN_NOT_FOUND, e.getMessage(), e);
		}

		if (editingDomain == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.EDITING_DOMAIN_NOT_FOUND, "The editing domain has not been found", null); //$NON-NLS-1$
		}

		// 5. create and check the deletion command
		// Map<?, ?> parameters = new HashMap<Object, Object>();
		// request.getParameters().putAll(parameters);
		ICommand cmd = this.commandProvider.getEditCommand(request);
		if (cmd == null) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.COMMAND_NULL, "The deletion command cannot be created", null); //$NON-NLS-1$
		}
		if (!cmd.canExecute()) {
			return new Status(IStatus.ERROR, pluginID, ITableEditorStatusCode.COMMAND_NOT_EXECUTABLE, "The deletion command is not executable", null); //$NON-NLS-1$
		}

		this.deleteCommand = new GMFtoEMFCommandWrapper(cmd);
		return Status.OK_STATUS;

	}

	/**
	 * 
	 * @return
	 * 		the delete table editor command or <code>null</code> if the delete command can not be built
	 */
	public Command getDeleteTableEditorCommand() {
		if (canDeleteTableEditor().isOK()) {
			return this.deleteCommand;
		}
		return null;
	}

	/**
	 * delete the wanted table
	 */
	public void deleteTableEditor() {
		Command cmd = getDeleteTableEditorCommand();
		if (cmd != null) {
			this.editingDomain.getCommandStack().execute(cmd);
		}
	}

	/**
	 * 
	 * @param table
	 *            the table to destroy
	 */
	public void setTable(final Table table) {
		this.table = table;
	}

	/**
	 * 
	 * @return
	 * 		the activator id of the plugin
	 */
	protected String getPluginID() {
		return Activator.PLUGIN_ID;
	}
}
