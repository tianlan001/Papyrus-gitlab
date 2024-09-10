/*****************************************************************************
 * Copyright (c) 2011, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
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
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.common.modelresource;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource;
import org.eclipse.papyrus.infra.core.resource.BadArgumentExcetion;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;



/**
 * A model used to save data from the {@link DefaultNattableEditor}
 *
 * @author cedric dumoulin
 *
 */
public class PapyrusNattableModel extends AbstractModelWithSharedResource<Table> implements IModel {

	/**
	 * Model ID.
	 */
	public static final String MODEL_ID = "org.eclipse.papyrus.infra.nattable.resource.NattableModel"; //$NON-NLS-1$

	/**
	 * the file extension where table are stored
	 */
	public static final String TABLE_MODEL_FILE_EXTENSION = "notation"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public PapyrusNattableModel() {

	}

	// Initialize stuff in the model.
	@Override
	public void init(ModelSet modelManager) {
		super.init(modelManager);
		// nothing to do now
	}

	/**
	 * Get the file extension used for this model.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getModelFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getModelFileExtension() {
		return TABLE_MODEL_FILE_EXTENSION;
	}

	/**
	 * Get the identifier used to register this model.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getIdentifier()
	 *
	 * @return
	 */
	@Override
	public String getIdentifier() {
		return MODEL_ID;
	}

	/**
	 * Add a new initialized {@link PapyrusTableInstance} to the model.
	 *
	 * @param tableInstance
	 *            The tableInstance to add.
	 */
	public void addPapyrusTable(Table tableInstance) {
		EObject context = tableInstance.getContext();
		if (context != null) { // we check the resource for control mode feature
			Resource targetResource;
			Resource contextResource = context.eResource();
			if (!contextResource.getURI().trimFileExtension().equals(getResource().getURI().trimFileExtension())) {
				URI uri = contextResource.getURI();
				uri = uri.trimFileExtension();
				uri = uri.appendFileExtension(getModelFileExtension());
				ResourceSet set = contextResource.getResourceSet();
				targetResource = set.getResource(uri, true);
			} else {
				targetResource = getResource();
			}
			if (targetResource != null) {
				targetResource.getContents().add(tableInstance);
			}
		}

		// it doesn't work when we call this method from the Create Project/Model wizard, because the file is not yet in the workspace
		// see bug 470299: [Table] impossible to create new table from the creation wizard https://bugs.eclipse.org/bugs/show_bug.cgi?id=470299
		// try {
		// TransactionalEditingDomain editingDomain = ServiceUtilsForResourceSet.getInstance().getTransactionalEditingDomain(modelSet);
		// Resource notationResource = NotationUtils.getNotationResourceForDiagram(tableInstance.getContext(), editingDomain);
		// if (notationResource != null) {
		// notationResource.getContents().add(tableInstance);
		// }
		// } catch (ServiceException ex) {
		// Activator.log.error(ex);
		// }
	}

	/**
	 * Add a new initialized {@link PapyrusTableInstance} to the model.
	 *
	 * @param tableInstance
	 *            The tableInstance to add.
	 */
	public void removeTable(Table tableInstance) {
		if (tableInstance.eResource() != null) {
			tableInstance.eResource().getContents().remove(tableInstance);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractModelWithSharedResource#isModelRoot(org.eclipse.emf.ecore.EObject)
	 *
	 * @param object
	 * @return
	 */
	@Override
	protected boolean isModelRoot(EObject object) {
		return object instanceof Table;
	}

	/**
	 * Get a table by its name.
	 *
	 * @param tableName
	 *            Name of the table. This is the name set by the user.
	 * @return
	 * @throws NotFoundException
	 * @throws BadArgumentExcetion
	 */
	public Table getTable(String tableName) throws NotFoundException, BadArgumentExcetion {

		if (tableName == null || tableName.length() == 0) {
			throw new BadArgumentExcetion("Table name should not be null and size should be >0."); //$NON-NLS-1$
		}

		for (Resource current : getResources()) {
			for (EObject element : current.getContents()) {
				if (element instanceof Table) {
					Table table = (Table) element;

					if (tableName.equals(table.getName())) {
						// Found
						return table;

					}
				}
			}
		}
		// not found
		throw new NotFoundException(NLS.bind("No Table named '{0}' can be found in Model.", tableName)); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param tableName
	 *            a table name, cannot be <code>null</code>
	 * @return
	 * 		the list of table with the wanted name
	 */
	public List<Table> getTableByName(final String tableName) {
		Assert.isNotNull(tableName);
		List<Table> matchingTables = new ArrayList<Table>();
		for (Resource current : getResources()) {
			for (EObject element : current.getContents()) {
				if (element instanceof Table) {
					Table table = (Table) element;
					if (tableName.equals(table.getName())) {
						matchingTables.add(table);
					}
				}
			}
		}
		return matchingTables;
	}

	/**
	 * 
	 * @param tableType
	 *            a table type, cannot be <code>null</code>
	 * @return
	 * 		the list of table with the wanted type
	 */
	public List<Table> getTableByType(final String tableType) {
		Assert.isNotNull(tableType);
		List<Table> matchingTables = new ArrayList<Table>();
		for (Resource current : getResources()) {
			for (EObject element : current.getContents()) {
				if (element instanceof Table) {
					Table table = (Table) element;
					if (tableType.equals(table.getTableConfiguration().getType())) {
						matchingTables.add(table);
					}
				}
			}
		}
		return matchingTables;
	}

	/**
	 * 
	 * @param tableContext
	 *            an Eobject used a context of the table, cannot be <code>null</code>
	 * @return
	 * 		the list of table with the wanted context
	 */
	public List<Table> getTableByContext(final EObject tableContext) {
		Assert.isNotNull(tableContext);
		List<Table> matchingTables = new ArrayList<Table>();
		for (Resource current : getResources()) {
			for (EObject element : current.getContents()) {
				if (element instanceof Table) {
					Table table = (Table) element;
					if (tableContext.equals(table.getContext())) {
						matchingTables.add(table);
					}
				}
			}
		}
		return matchingTables;
	}

	/**
	 * 
	 * @param tableOwner
	 *            an EObject used a owner of the table, cannot be <code>null</code>
	 * @return
	 * 		the list of table with the wanted owner
	 */
	public List<Table> getTableByOwner(final EObject tableOwner) {
		Assert.isNotNull(tableOwner);
		List<Table> matchingTables = new ArrayList<Table>();
		for (Resource current : getResources()) {
			for (EObject element : current.getContents()) {
				if (element instanceof Table) {
					Table table = (Table) element;
					if (tableOwner.equals(table.getOwner())) {
						matchingTables.add(table);
					}
				}
			}
		}
		return matchingTables;
	}

	/**
	 * 
	 * @param tableContext
	 *            the context of the wanted table
	 * @param tableOwner
	 *            the owner of the wanted table
	 * @param tableType
	 *            the type of the wanted table
	 * @param tableName
	 *            the name of the wanted table
	 * @return
	 * 		the list of the tables matching the parameters. <code>null</code> parameters are ignored
	 */
	public List<Table> findMatchingTables(final EObject tableContext, final EObject tableOwner, final String tableType, final String tableName) {
		final List<Table> matchingTables = new ArrayList<Table>();
		for (Resource current : getResources()) {
			for (EObject element : current.getContents()) {
				if (element instanceof Table) {
					Table table = (Table) element;
					boolean matchName = true;
					boolean matchType = true;
					boolean matchOwner = true;
					boolean matchContext = true;
					if (tableName != null) {
						matchName = tableName.equals(table.getName());
					}
					if (tableType != null) {
						matchType = tableType.equals(table.getTableConfiguration().getType());
					}
					if (tableOwner != null) {
						matchOwner = tableOwner.equals(table.getOwner());
					}
					if (tableContext != null) {
						matchContext = tableContext.equals(table.getContext());
					}

					if (matchName && matchType && matchOwner && matchContext) {
						matchingTables.add(table);
					}
				}
			}
		}
		return matchingTables;
	}
}
