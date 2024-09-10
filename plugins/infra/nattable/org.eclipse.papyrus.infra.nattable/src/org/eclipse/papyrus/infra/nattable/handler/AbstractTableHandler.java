/*****************************************************************************
 * Copyright (c) 2012, 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.ui.menu.MenuItemProviders;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.papyrus.infra.ui.util.WorkbenchPartHelper;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

/**
 * The abstract handler to use for the table actions
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractTableHandler extends AbstractHandler {

	/**
	 * the id used to find the NatEvent in the EclipseContext
	 *
	 * @deprecated since Papyrus 1.2 (Eclipse Neon)
	 */
	@Deprecated
	public static final String NAT_EVENT_DATA_PARAMETER_ID = "natEventParameterId"; //$NON-NLS-1$

	/**
	 * the event which have declenched the call to setEnable(Object evaluationContext. This event contains the location of the mouse pointer when
	 * the popup menu for this handler have been created
	 *
	 * we do a weak reference to fix the bug 469376: [Table] Memory Leak : (Tree)NattableWidgetManager, EObjectTreeItemAxis and others objects are not disposed when the table is closed
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=469376
	 */
	// TODO : should maybe be removed with the future usage of e4 and the Eclipse Context
	private WeakReference<NatEventData> eventDataWeakReference;

	/**
	 *
	 * @return
	 *         the current active part
	 * @deprecated since 2.0
	 */
	@Deprecated
	protected IWorkbenchPart getActivePart() {
		return WorkbenchPartHelper.getCurrentActiveWorkbenchPart();
	}

	/**
	 *
	 * @return
	 *         the TableSelectioWrapper used (according to the referenced NatEventData) or <code>null</code> if not found
	 * @since 2.0
	 */
	protected final TableSelectionWrapper getTableSelectionWrapper() {
		final INattableModelManager tableManager = getCurrentNattableModelManager();
		if (tableManager != null) {
			return tableManager.getAdapter(TableSelectionWrapper.class);
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the current table manager or <code>null</code> if not found
	 */
	protected INattableModelManager getCurrentNattableModelManager() {
		INattableModelManager manager = null;
		final NatEventData data = getNatEventData();
		if (data != null) {
			final NatTable natTable = data.getNatTable();
			if (natTable != null && natTable.getConfigRegistry() != null) {
				manager = natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			}
		}
		return manager;
	}

	/**
	 * @return
	 *         the current NatTable or <code>null</code> if not found
	 * @since 5.0
	 */
	protected NatTable getCurrentNatTable() {
		final NatEventData data = getNatEventData();
		NatTable natTable = null;
		if (null != data) {
			natTable = data.getNatTable();
		}

		return natTable;
	}

	/**
	 * Returns the EditingDomain associated to the table
	 *
	 * @return
	 */
	protected TransactionalEditingDomain getTableEditingDomain() {// duplicated code from NattableModelManager
		return TableEditingDomainUtils.getTableEditingDomain(getCurrentNattableModelManager().getTable());
	}

	/**
	 * Returns the EditingDomain associated to the context
	 *
	 * @return
	 */
	protected TransactionalEditingDomain getContextEditingDomain() {// duplicated code from NattableModelManager
		return TableEditingDomainUtils.getTableContextEditingDomain(getCurrentNattableModelManager().getTable());
	}

	/**
	 *
	 * @return
	 *         the last NatEventData received during the setEnable
	 */
	protected NatEventData getNatEventData() {
		if (this.eventDataWeakReference != null) {
			return this.eventDataWeakReference.get();
		}
		return null;
	}

	/**
	 *
	 * @param evaluationContext
	 *            the evaluation context
	 * @return
	 *         the NatEventData from this evaluation context or <code>null</code> if not found
	 */
	protected NatEventData getNatEventData(final Object evaluationContext) {
		if (evaluationContext instanceof NatEventData) {
			return (NatEventData) evaluationContext;
		}
		NatEventData eventData = null;
		if (evaluationContext instanceof IEvaluationContext) {
			Object value = ((IEvaluationContext) evaluationContext).getVariable(MenuItemProviders.NAT_EVENT_DATA_KEY);
			if (value instanceof NatEventData) {
				eventData = (NatEventData) value;
			}
		}

		// that's why we can't add the variable NAT_EVENT_DATA_PARAMETER_ID and we need to create a NatEventData instead of to get it in evaluationContext
		if (eventData == null) {
			Point cursorLocation = Display.getDefault().getCursorLocation();
			Control control = null;

			// Try to get the nattable from the multi diagram
			// We need to manage it from active editor for the sub menu items
			final IEditorPart activeEditor = EditorHelper.getCurrentEditor();
			if (null != activeEditor) {
				INattableModelManager nattableModelManager = null;
				if (activeEditor instanceof IMultiPageEditorPart && null != ((IMultiPageEditorPart) activeEditor).getActiveEditor()) {
					nattableModelManager = ((IMultiPageEditorPart) activeEditor).getActiveEditor().getAdapter(INattableModelManager.class);
				} else if (activeEditor instanceof IAdaptable) {
					nattableModelManager = ((IAdaptable) activeEditor).getAdapter(INattableModelManager.class);
				}

				if (null != nattableModelManager) {
					control = nattableModelManager.getAdapter(NatTable.class);
				}
			}

			if (control instanceof NatTable) {// : not nice, but required
				cursorLocation = control.toControl(cursorLocation);
				Event e = new Event();
				e.x = cursorLocation.x;
				e.y = cursorLocation.y;
				e.display = Display.getDefault();
				e.widget = control;
				MouseEvent event = new MouseEvent(e);
				eventData = NatEventData.createInstanceFromEvent(event);
			}
		}
		return eventData;
	}

	/**
	 *
	 * @param evaluationContext
	 * @return
	 *         the index of the rows which are fully selected
	 */
	protected List<Integer> getFullSelectedRowsIndex(Object evaluationContext) {
		final INattableModelManager manager = getCurrentNattableModelManager();
		if (manager != null) {
			final NatEventData data = getNatEventData(evaluationContext);
			if (data != null) {
				final SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
				int[] fullSelectedColumnsPosition = layer.getFullySelectedRowPositions();
				List<Integer> positions = new ArrayList<>();
				for (int i : fullSelectedColumnsPosition) {
					positions.add(layer.getRowIndexByPosition(i));
				}
				return positions;
			}
		}
		return Collections.emptyList();
	}

	/**
	 *
	 * @param evaluationContext
	 * @return
	 *         the index of the columns which are fully selected
	 */
	protected List<Integer> getFullSelectedColumnsIndex(Object evaluationContext) {
		final INattableModelManager manager = getCurrentNattableModelManager();
		if (manager != null) {
			final NatEventData data = getNatEventData(evaluationContext);
			if (data != null) {
				final SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
				int[] fullSelectedColumnsPosition = layer.getFullySelectedColumnPositions();
				List<Integer> positions = new ArrayList<>();
				for (int i : fullSelectedColumnsPosition) {
					positions.add(layer.getColumnIndexByPosition(i));
				}
				return positions;
			}
		}
		return Collections.emptyList();
	}


	/**
	 *
	 * @return
	 *         the row axis manager
	 */
	protected IAxisManager getRowAxisManager() {
		final INattableModelManager manager = getCurrentNattableModelManager();
		if (manager != null) {
			return manager.getRowAxisManager();
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the column axis manager
	 */
	protected IAxisManager getColumnAxisManager() {
		final INattableModelManager manager = getCurrentNattableModelManager();
		if (manager != null) {
			return manager.getColumnAxisManager();
		}
		return null;
	}

	/**
	 * TODO : this method should be declared as final in Papyrus 5.0 (bug 562866)
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 *      Please override {@link #computeEnable(Object)} instead of this method
	 * @param evaluationContext
	 *
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		this.eventDataWeakReference = new WeakReference<>(getNatEventData(evaluationContext));
		setBaseEnabled(computeEnable(evaluationContext));
	}

	/**
	 * This method avoid to call setBaseEnable several time in the same handler
	 * to avoid a StackOverFlow is strange cases
	 *
	 * @param evaluationContext
	 *            the evaluation context used to calculate the enablement
	 * @since 6.7
	 *
	 */
	protected boolean computeEnable(final Object evaluationContext) {
		return getCurrentNattableModelManager() != null;
	}

	/**
	 * @see org.eclipse.core.commands.AbstractHandler#setBaseEnabled(boolean)
	 *
	 * @param state
	 */
	@Override
	protected void setBaseEnabled(boolean state) {
		if (!state) {
			this.eventDataWeakReference = null;
		}
		super.setBaseEnabled(state);
	}

	/**
	 *
	 * @return
	 *         the currently managed table
	 */
	protected final Table getTable() {
		return getCurrentNattableModelManager().getTable();
	}

	/**
	 * Refreshes the table
	 */
	protected final void refreshTable() {
		((NattableModelManager) getCurrentNattableModelManager()).refreshNatTable();
	}

	/**
	 * @see org.eclipse.core.commands.AbstractHandler#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		this.eventDataWeakReference = null;
	}
}
