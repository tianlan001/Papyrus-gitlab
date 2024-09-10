/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.internal.common.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.IEMFModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;


/**
 * Insert a table with the specified owner
 */
public class InsertTableCommand extends RecordingCommand {

	/** FIXME the domain of Recording command should be accessible */
	protected TransactionalEditingDomain transactionalEditingDomain;

	/** table to be inserted */
	protected Table table;

	/** owner of the table */
	protected EObject owner;

	/**
	 * @param transactionalEditingDomain
	 * @param label
	 * @param table
	 *            to be inserted
	 * @param owner
	 *            of the table
	 */
	public InsertTableCommand(TransactionalEditingDomain transactionalEditingDomain, String label, Table table, EObject owner) {
		super(transactionalEditingDomain, label);
		this.transactionalEditingDomain = transactionalEditingDomain;
		this.owner = owner;
		this.table = table;
	}

	@Override
	protected void doExecute() {
		ResourceSet rset = transactionalEditingDomain.getResourceSet();
		IEMFModel tableModel = null;

		if (rset instanceof ModelSet) {
			ModelSet modelSet = (ModelSet) rset;
			tableModel = modelSet.getModelToPersist(table);
		}

		table.setOwner(owner);
		if (tableModel != null) {
			tableModel.persist(table);
		}
	}
}
