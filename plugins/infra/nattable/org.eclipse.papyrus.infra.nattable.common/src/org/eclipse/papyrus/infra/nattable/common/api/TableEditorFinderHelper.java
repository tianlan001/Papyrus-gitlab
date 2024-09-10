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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource;
import org.eclipse.papyrus.infra.nattable.common.modelresource.PapyrusNattableModel;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * @author Vincent Lorenzo
 *
 */
public class TableEditorFinderHelper {

	/**
	 * the papyrus nattable model. used to find the tables
	 */
	private final PapyrusNattableModel papyrusNattableModel;

	/**
	 * Constructor.
	 * 
	 * @throws ServiceException
	 * @throws NotFoundException
	 *
	 */
	public TableEditorFinderHelper(final EObject anEObject) throws ServiceException, NotFoundException {
		// 1. find the service registry
		ServicesRegistry servicesRegistry = ServiceUtilsForResource.getInstance().getServiceRegistry(anEObject.eResource());

		// 2. find the modelset
		ModelSet modelSet = ServiceUtils.getInstance().getModelSet(servicesRegistry);

		// 3. find the papyrus nattable model
		this.papyrusNattableModel = (PapyrusNattableModel) modelSet.getModelChecked(PapyrusNattableModel.MODEL_ID);
	}

	/**
	 * 
	 * @param tableContext
	 *            an eobject used as table context, could be <code>null</code>
	 * @param tableOwner
	 *            an eobject used as table owner, could be <code>null</code>
	 * @param tableType
	 *            the type of the table, could be <code>null</code>
	 * @param tableName
	 *            the name of the table, could be <code>null</code>
	 * @return
	 */
	public List<Table> findMatchingTables(final EObject tableContext, final EObject tableOwner, final String tableType, final String tableName) {
		return this.papyrusNattableModel.findMatchingTables(tableContext, tableOwner, tableType, tableName);
	}

	/**
	 * 
	 * @param tableOwner
	 *            the owner of the table, must not be <code>null</code>
	 * @return
	 * 		the table matching the owner.
	 */
	public List<Table> findTablesByOwner(final EObject tableOwner) {
		return this.papyrusNattableModel.getTableByOwner(tableOwner);
	}

	/**
	 * 
	 * @param tableContext
	 *            the table context, must not be <code>null</code>
	 * @return
	 * 		the table matching the context.
	 */
	public List<Table> findTablesByContext(final EObject tableContext) {
		return this.papyrusNattableModel.getTableByContext(tableContext);
	}

	/**
	 * 
	 * @param tableType
	 *            the table type, must not be <code>null</code>
	 * @return
	 * 		the table matching the type.
	 */
	public List<Table> findTablesByType(final String tableType) {
		return this.papyrusNattableModel.getTableByType(tableType);
	}

	/**
	 * 
	 * @param tableName
	 *            the table name, must not be <code>null</code>
	 * @return
	 * 		the table matching the name.
	 */
	public List<Table> findTablesByName(final String tableName) {
		return this.papyrusNattableModel.getTableByName(tableName);
	}
}
