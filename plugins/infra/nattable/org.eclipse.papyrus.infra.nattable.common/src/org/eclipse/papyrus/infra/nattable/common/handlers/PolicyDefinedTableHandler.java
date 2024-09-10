/*****************************************************************************
 * Copyright (c) 2013, 2019, 2020 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 551566
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 561371
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.handlers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableVersioningUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;


/**
 * Represents the handler for the creation of table defined in a viewpoint
 *
 * @author Laurent Wouters
 */
public class PolicyDefinedTableHandler extends CreateNatTableEditorHandler {
	private TableConfiguration configuration;
	/**
	 * @since 5.5
	 */
	protected EObject context;
	/**
	 * @since 5.5
	 */
	protected String name;

	/**
	 * @since 3.0
	 */
	public PolicyDefinedTableHandler(TableConfiguration config, EObject context, String name) {
		this.configuration = config;
		this.context = context;
		this.name = name;
	}

	@Override
	protected EObject getTableContext() {
		return context;
	}

	/**
	 * The context to use for the table creation
	 *
	 * @param context
	 * @since 5.5
	 */
	public void setTableContext(final EObject context) {
		this.context = context;
	}

	@Override
	protected TableConfiguration getTableEditorConfiguration() {
		return configuration;
	}

	public boolean execute(final ViewPrototype prototype) {
		final String name = this.name != null ? this.name : askName();
		if (name == null) {
			return false;
		}
		try {
			final ServicesRegistry serviceRegistry = ServiceUtilsForEObject.getInstance().getServiceRegistry(context);
			final TransactionalEditingDomain domain = ServiceUtils.getInstance().getTransactionalEditingDomain(serviceRegistry);
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					try {
						Table table = PolicyDefinedTableHandler.this.doExecute(serviceRegistry, name, this.description, prototype.getRepresentationKind().getId());
						TableVersioningUtils.stampCurrentVersion(table);
						table.setOwner(context);
					} catch (Exception ex) {
						Activator.log.error(ex);
					}
				}
			});
			return true;
		} catch (Exception ex) {
			Activator.log.error(ex);
			return false;
		}
	}
}
