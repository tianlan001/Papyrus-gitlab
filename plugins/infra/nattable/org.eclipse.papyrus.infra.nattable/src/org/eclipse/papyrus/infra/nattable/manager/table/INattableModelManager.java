/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 502560: add drag to diagram support
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 417095
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.manager.table;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.nattable.utils.LocationValue;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.services.IDisposable;


public interface INattableModelManager extends ITableAxisElementProvider, IDisposable, IDataProvider, IAdaptable {


	@Override
	public int getColumnCount();

	@Override
	public int getRowCount();

	public void addRows(final Collection<Object> objectsToAdd);

	public void addColumns(final Collection<Object> objectsToAdd);

	public void removeColumns(final Collection<Object> objectsToRemove);

	public void removeRows(final Collection<Object> objectsToRemove);

	public boolean canInsertRow(final Collection<Object> objectsToAdd, int index);

	public boolean canInsertColumns(final Collection<Object> objectsToAdd, int index);

	public boolean canDropColumnsElement(final Collection<Object> objectsToAdd);

	public boolean canDropRowElement(final Collection<Object> objectsToAdd);

	public void insertRows(final Collection<Object> objectsToAdd, int index);

	public void insertColumns(final Collection<Object> objectsToAdd, int index);

	public IDataProvider getBodyDataProvider();

	public boolean canMoveColumns();

	public boolean canMoveRows();

	public void moveColumnElement(final Object axisToMove, final int newIndex);

	public void moveRowElement(final Object axisToMove, final int newIndex);

	public void invertAxis();

	public boolean canInvertAxis();

	public Table getTable();

	public ITableAxisElementProvider getTableAxisElementProvider();

	public NatTable createNattable(final Composite parent, int style, IWorkbenchPartSite site);

	public LocationValue getLocationInTheTable(final Point location);

	public boolean canCreateRowElement(String elementType);

	public boolean canCreateColumnElement(String elementType);

	public Command getAddRowElementCommand(Collection<Object> objectsToAdd);

	public Command getAddColumnElementCommand(Collection<Object> objectsToAdd);

	/**
	 * Get the command to add row elements at the index in parameter.
	 *
	 * @param objectsToAdd
	 *            The row elements to add.
	 * @param index
	 *            The index where add the elements.
	 * @return The command.
	 */
	public Command getAddRowElementCommand(final Collection<Object> objectsToAdd, final int index);

	/**
	 * Get the command to add column elements at the index in parameter.
	 *
	 * @param objectsToAdd
	 *            The column elements to add.
	 * @param index
	 *            The index where add the elements.
	 * @return The command.
	 */
	public Command getAddColumnElementCommand(final Collection<Object> objectsToAdd, final int index);

	/**
	 * This allows to set the label of a table.
	 * 
	 * @param label
	 *            The new label.
	 * @since 3.0
	 */
	public void setTableLabel(final String label);

	public void setTableName(String name);

	public String getTableName();


	public void print();

	public void selectAll();


	/**
	 * This allows to export a table as image
	 * 
	 * @since 3.0
	 */
	public void exportToImage();

	public void exportToXLS();

	/**
	 * This allows to export the table contents into a file.
	 *
	 * @since 2.0
	 */
	public void exportToFile();

	/**
	 * Opens a dialog to choose the columns to display or to destroy. This action doesn't create or destroy model element. It can only create/destroy
	 * axis
	 */
	public void openCreateDestroyColumnsManagerDialog();

	/**
	 * Opens a dialog to choose the columns to display or to destroy. This action doesn't create or destroy model element. It can only create/destroy
	 * axis
	 */
	public void openCreateDestroyRowsManagerDialog();

	/**
	 * Returns <code>true</code> if columns axis can be destroyed (only are will be destroyed, the model element won't be destroyed)
	 *
	 * @return
	 */
	public boolean canCreateDestroyColumnsAxis();

	/**
	 * Returns <code>true</code> if columns axis can be destroyed (only are will be destroyed, the model element won't be destroyed)
	 *
	 * @return
	 */
	public boolean canCreateDestroyRowsAxis();

	public void sortColumnsByName(final boolean alphabeticOrder);

	public void sortRowsByName(final boolean alphabeticOrder);


	/**
	 *
	 * @return
	 * 		the "real"{@link AbstractAxisProvider}, that's to say that this method use the property {@link Table#isInvertAxis()} to return the real
	 *         vertical axis
	 */
	public AbstractAxisProvider getVerticalAxisProvider();

	/**
	 *
	 * @return
	 * 		the "real"{@link AbstractAxisProvider}, that's to say that this method use the property {@link Table#isInvertAxis()} to return the real
	 *         horizontal axis
	 */
	public AbstractAxisProvider getHorizontalAxisProvider();

	public BodyLayerStack getBodyLayerStack();

	/**
	 * Opens a dialog to edit the alias of the row
	 *
	 * @param event
	 */
	public void openEditRowAliasDialog(final NatEventData event);

	/**
	 * Opens a dialog to edit the alias of the column
	 *
	 * @param event
	 */
	public void openEditColumnAliasDialog(final NatEventData event);

	public boolean canEditColumnHeader(final NatEventData eventData);

	public boolean canEditRowHeader(final NatEventData eventData);

	/**
	 *
	 * @return
	 * 		the column axis manager, managing the axis inversion
	 */
	public IAxisManager getColumnAxisManager();

	/**
	 *
	 * @return
	 * 		the row axis manager, managing the axis inversion
	 */
	public IAxisManager getRowAxisManager();

	/**
	 *
	 * @return
	 * 		the local preference store for the table instance or <code>null</code>
	 */
	public PreferenceStore getTablePreferenceStore();

	/**
	 *
	 * @param store
	 *            the table preference store
	 */
	public void setWorkspacePreferenceStore(final PreferenceStore store);

	/**
	 * @return the selection in table.
	 *
	 * @since 3.0
	 */
	public TableStructuredSelection getSelectionInTable();
}
