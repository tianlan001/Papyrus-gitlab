/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.helper;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableReconciler;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableReconcilersReader;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableVersioningUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * Table migration between version of Papyrus.
 * @since 3.0
 */
public class TableReconcileHelper {
	
	private final TransactionalEditingDomain domain;

	/**
	 * Instantiates helper that will work with given {@link TransactionalEditingDomain}.
	 * Note that reconcile operations are performed outside the table command stack using {@link GMFUnsafe}
	 */
	public TableReconcileHelper(TransactionalEditingDomain domain) {
		this.domain = domain;
	}

	/**
	 * Process table reconcilers to migrate models. Does nothing if the table is already of the current Papyrus version based on {@link TableVersioningUtils#isOfCurrentPapyrusVersion(Table)} check.
	 * <p/>
	 * This method needs configured {@link TableEditDomain} to execute collected {@link ICommand} when needed, so it can't be called from constructor
	 *
	 * @param table
	 *            the table to reconcile
	 * @throws CoreException
	 *             subclass may throw wrapping any problem thrown from execution of reconcile using {@link GMFUnsafe}. Default implementation does not
	 *             throw it however
	 */
	public void reconcileTable(Table table) throws CoreException {
		CompositeCommand migration = buildReconcileCommand(table);
		if (migration == null) {
			return;
		}
		migration.add(TableVersioningUtils.createStampCurrentVersionCommand(table));
		try {
			GMFUnsafe.write(domain, migration);
		} catch (ExecutionException e) {
			handleReconcileException(table, e);
		} catch (InterruptedException e) {
			handleReconcileException(table, e);
		} catch (RollbackException e) {
			handleReconcileException(table, e);
		}
	}

	/**
	 * Process table reconcilers to migrate models.
	 *
	 * Returns <code>null</code> if the table is already of the current Papyrus version based on {@link TableVersioningUtils#isOfCurrentPapyrusVersion(Table)} check.
	 * <p/>
	 * If one of the reconcilers returns un-executable command, this method logs the problem and returns <code>null</code>
	 *
	 * @param table
	 *            the table to reconcile
	 */
	protected CompositeCommand buildReconcileCommand(Table table) {

		CompositeCommand reconcileCommand = new CompositeCommand("Reconciling");

		if (!TableVersioningUtils.isOfCurrentPapyrusVersion(table)) {

			String sourceVersion = TableVersioningUtils.getCompatibilityVersion(table);
			List <TableReconciler> reconcilers = TableReconcilersReader.getInstance().load();

			boolean someFailed = false;
			Iterator<TableReconciler> reconciler = reconcilers.iterator();
			while (reconciler.hasNext() && !someFailed) {
				TableReconciler next = reconciler.next();

				if (!next.canReconcileFrom(table, sourceVersion)) {
					// asked for ignore it for this instance, all fine
					continue;
				}
				ICommand nextCommand = next.getReconcileCommand(table);
				if (nextCommand == null) {
					// legitimate no-op response, all fine
					continue;
				}
				if (nextCommand.canExecute()) {
					reconcileCommand.add(nextCommand);
				} else {
					Activator.log.error("Table reconciler " + next + " failed to reconcile table : " + table, null); //$NON-NLS-1$ //$NON-NLS-2$
					someFailed = true;
				}
			}

			if (someFailed) {
				// probably better to fail the whole reconcile process as user will have a chance to reconcile later when we fix the problem with one of the reconcilers
				// executing partial reconciliation will leave the table in the state with partially current and partially outdated versions
				reconcileCommand = null;
			}

		}

		return reconcileCommand;
	}

	/**
	 * Handles exception from running the table reconciler under {@link GMFUnsafe}.
	 * At the time method is called the table is probably broken, but default implementation just logs error.
	 * <p/>
	 * This is to allow subclass to decide whether it is worth opening the problem table.
	 */
	protected void handleReconcileException(Table table, Exception e) throws CoreException {
		Activator.log.error("Reconciling the table: " + table, e); //$NON-NLS-1$
	}
}
