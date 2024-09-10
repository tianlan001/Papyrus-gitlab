/*****************************************************************************
 * Copyright (c) 2012, 2017-2018, 2020 CEA LIST, Esterel Technologies SAS and others.
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
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 476618, 504077, 496905, 508175
 *  Nicolas Boulay (Esterel Technologies SAS) - Bug 497467
 *  Sebastien Bordes (Esterel Technologies SAS) - Bug 497738
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220, 526146, 515737, 516314
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 559973, 560318, 562619, 562646, 517617, 532452, 486733
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.manager.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.layer.ILayerListener;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.resize.command.InitializeAutoResizeRowsCommand;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectCellCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectColumnCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.util.GCFactory;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.command.CommandIds;
import org.eclipse.papyrus.infra.nattable.dialog.DisplayedAxisSelectorDialog;
import org.eclipse.papyrus.infra.nattable.filter.PapyrusFilterStrategy;
import org.eclipse.papyrus.infra.nattable.manager.axis.AxisManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.axis.CompositeAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.axis.ICompositeAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.refresh.CustomStructuralRefreshCommand;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.ISlaveAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.CellEditorDeclaration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.NattableconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.FeatureLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NamedStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.nattable.selection.ISelectionExtractor;
import org.eclipse.papyrus.infra.nattable.selection.ObjectsSelectionExtractor;
import org.eclipse.papyrus.infra.nattable.sort.IPapyrusSortModel;
import org.eclipse.papyrus.infra.nattable.sort.PapyrusCompositeGlazedListSortModel;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CellMapKey;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.NatTableFocusUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.StringComparator;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrus.infra.widgets.providers.FlattenableRestrictedFilteredContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.swt.IFocusService;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

/**
 * All the code concerning tree table is in the subclass {@link TreeNattableModelManager}.
 */
public class NattableModelManager extends AbstractNattableWidgetManager implements INattableModelManager, FocusListener {

	/**
	 * the column manager
	 */
	private ICompositeAxisManager columnManager;

	/**
	 * the line manager
	 */
	private ICompositeAxisManager rowManager;

	/**
	 * the model of the table on which we are working
	 */
	private List<Object> verticalElements;

	private List<Object> horizontalElements;

	protected FilterList<Object> verticalFilterList;

	protected FilterList<Object> horizontalFilterList;

	protected EventList<Object> basicVerticalList;

	protected EventList<Object> basicHorizontalList;

	private final Adapter invertAxisListener;

	private AbstractAxisProvider rowProvider;

	private AbstractAxisProvider columnProvider;

	/**
	 * this listener listen the stack events and refresh nattable
	 */
	private CommandStackListener refreshListener;

	/**
	 * the focus listener
	 */
	private FocusListener focusListener;

	/**
	 * listener on the decoration service, to be able to update the table when marker are added or removed
	 */
	private Observer decoractionServiceObserver;

	/**
	 * the resourceSet listener
	 */
	private ResourceSetListener resourceSetListener;

	/**
	 * layerListener to update the toggles
	 */
	private ILayerListener layerListener;


	protected Adapter changeAxisProvider;

	private final AdapterImpl changeAxisProviderHistory;


	private IFocusService focusService;

	/**
	 * the listener on the table cells
	 */
	private final Adapter tableCellsListener;

	private final BiMap<CellMapKey, Cell> cellsMap;

	/**
	 * the local preference store for the table
	 */
	private PreferenceStore localPreferenceStore;


	// fields used for refresh
	private Runnable refreshRunnable;

	/** Flag to avoid reentrant call to refresh. */
	private final AtomicBoolean isRefreshing = new AtomicBoolean(false);

	/**
	 *
	 * Constructor.
	 *
	 * @param rawModel
	 *
	 *            the model of the managed table
	 */
	public NattableModelManager(final Table rawModel) {
		this(rawModel, new ObjectsSelectionExtractor());
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param rawModel
	 *            the table model
	 * @param selectionExtractor
	 *            the selection extrator
	 */
	public NattableModelManager(final Table rawModel, final ISelectionExtractor selectionExtractor) {
		this(rawModel, selectionExtractor, true);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param rawModel
	 *            The table model.
	 * @param selectionExtractor
	 *            The selection extrator.
	 * @param initializeListeners
	 *            boolean to determinate if the listeners have to be initialized or not (example: properties view doesn't it).
	 * @since 3.0
	 */
	public NattableModelManager(final Table rawModel, final ISelectionExtractor selectionExtractor, final boolean initializeListeners) {
		super(rawModel, selectionExtractor);

		this.rowProvider = rawModel.getCurrentRowAxisProvider();
		this.columnProvider = rawModel.getCurrentColumnAxisProvider();
		this.verticalElements = createVerticalElementList();
		this.horizontalElements = createHorizontalElementList();

		this.cellsMap = HashBiMap.create();

		init();

		// If needed, initialize the invert axis listener and the update of cells map.
		// Other listeners can stay used in all cases.
		// For example: The table reference for properties view, these listeners are not needed and can caught exception
		if (initializeListeners) {
			this.invertAxisListener = createInvertAxisListener();

			if (this.invertAxisListener != null) {
				rawModel.eAdapters().add(this.invertAxisListener);
			}

			tableCellsListener = new AdapterImpl() {

				@Override
				public void notifyChanged(final Notification msg) {
					if (msg.getFeature() == NattablePackage.eINSTANCE.getTable_Cells()) {
						updateCellMap(msg);
					}
				}
			};
			rawModel.eAdapters().add(tableCellsListener);

			addListeners();
		} else {
			tableCellsListener = null;
			invertAxisListener = null;
		}

		changeAxisProvider = new AdapterImpl() {

			@Override
			public void notifyChanged(final Notification msg) {
				if (NattablePackage.eINSTANCE.getTable_CurrentColumnAxisProvider() == msg.getFeature() || NattablePackage.eINSTANCE.getTable_CurrentRowAxisProvider() == msg.getFeature()) {
					if (null != msg.getNewValue() && !msg.getNewValue().equals(msg.getOldValue())) {
						Display.getCurrent().asyncExec(new Runnable() {

							@Override
							public void run() {
								init();
								refreshNatTable();
							}
						});
					}
				}
			}
		};

		changeAxisProviderHistory = new AdapterImpl() {

			@Override
			public void notifyChanged(final Notification msg) {
				if (NattablePackage.eINSTANCE.getTable_ColumnAxisProvidersHistory() == msg.getFeature() || NattablePackage.eINSTANCE.getTable_RowAxisProvidersHistory() == msg.getFeature()) {
					if (null != msg.getNewValue() && !msg.getNewValue().equals(msg.getOldValue())) {
						Display.getCurrent().asyncExec(new Runnable() {

							@Override
							public void run() {
								init();
							}
						});
					}
				}
			}

		};

		rawModel.eAdapters().add(changeAxisProvider);
	}

	/**
	 * create the invert axis listener, can return <code>null</code>
	 */
	protected Adapter createInvertAxisListener() {
		return new AdapterImpl() {

			@Override
			public void notifyChanged(final Notification msg) {
				if (msg.getEventType() == Notification.SET) {
					final Object oldValue = msg.getOldValue();
					final Object newValue = msg.getNewValue();
					if (oldValue != null && newValue != null) {
						if (msg.getFeature() == NattablePackage.eINSTANCE.getTable_InvertAxis()) {
							invertJavaObject();

							// use the method to refresh/merge the table using the appointed values saved in the model.notation
							resizeAxis();
							resizeHeader();
							mergeTable();
							// for the fun of it!
							// in fact it is to fix the test org.eclipse.papyrus.sysml.nattable.requirement.tests.tests.RevealRequirementTableTest.test6SelectMultipleElementsInvertAxisAllColumns()
							// it is a work around and not really a nice fix, because I don't undestand the bug...
							getBodyLayerStack().getRowHideShowLayer().showAllRows();
							getBodyLayerStack().getColumnHideShowLayer().showAllColumns();

						}
					}
				}
			}
		};
	}


	private ListEventListener<Object> listEventListener;

	/**
	 * add required listener
	 */
	protected void addListeners() {
		this.listEventListener = new ListEventListener<Object>() {

			/**
			 *
			 * @param listChanges
			 */
			@Override
			public void listChanged(final ListEvent<Object> listChanges) {
				manageEventListChanges(listChanges);
			}

		};
		final EventList<?> rowsList = (EventList<?>) getRowElementsList();
		final EventList<?> columnsList = (EventList<?>) getColumnElementsList();
		rowsList.addListEventListener(this.listEventListener);
		columnsList.addListEventListener(this.listEventListener);
	}

	/**
	 *
	 * @param listChanges
	 *            manage the list events
	 */
	private void manageEventListChanges(final ListEvent<Object> listChanges) {
		if (natTable != null && !natTable.isDisposed()) {
			final EventList<?> sourceList = listChanges.getSourceList();
			final CellEditorDeclaration declaration = TableHelper.getCellEditorDeclaration(this);
			boolean needConfiguration = false;
			if (CellEditorDeclaration.COLUMN == declaration && sourceList == getColumnElementsList()) {
				needConfiguration = true;
			}
			if (CellEditorDeclaration.ROW == declaration && sourceList == getRowElementsList()) {
				needConfiguration = true;
			}
			if (needConfiguration) {
				// we check the events before to launch a reconfiguration, maybe it is just an update
				// bug 562619
				boolean isJustUpdate = true;
				final ListEvent<Object> copiedListChanges = listChanges.copy();
				while (copiedListChanges.hasNext() && isJustUpdate) {
					if (copiedListChanges.next()) {
						int eventType = copiedListChanges.getType();
						switch (eventType) {
						case ListEvent.UPDATE:
							if (copiedListChanges.isReordering()) {
								// in this case of update we need to reconfigure the editors and the filters
								isJustUpdate = false;
							}
							break;
						// in fact we get delete event for a cell edition,
						// because the refresh generate a structural refresh and the comparator registered on the list is updated too
						// see AbstractTableComparatorChooser.rebuildComparator()
						case ListEvent.DELETE:
						case ListEvent.INSERT:
						default:
							isJustUpdate = false;
						}
					}
				}
				if (!isJustUpdate) {
					configureCellAxisEditor();
					configureFilters();
				}

				// comment to fix the bug 469739: [Table] Infinite refresh in Tables
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=469739
				// moreover this refresh seems
				// refreshNatTable();
			}
		}
	}

	/**
	 * remove required listener
	 */
	protected void removeListeners() {
		if (null != listEventListener) {
			final EventList<?> rowsList = (EventList<?>) getRowElementsList();
			final EventList<?> columnsList = (EventList<?>) getColumnElementsList();
			rowsList.removeListEventListener(this.listEventListener);
			columnsList.removeListEventListener(this.listEventListener);
		}
	}


	protected SortedList<Object> rowSortedList;

	protected SortedList<Object> columnSortedList;

	/**
	 *
	 * @return
	 *         the new list to use for vertical element
	 */
	protected List<Object> createVerticalElementList() {
		// return Collections.synchronizedList(new ArrayList<Object>());
		this.basicVerticalList = GlazedLists.eventList(new ArrayList<>());
		// it required than vertical element is a filter list -> warning to invert axis?
		this.columnSortedList = new SortedList<>(this.basicVerticalList, null);
		this.verticalFilterList = new FilterList<>(this.columnSortedList);
		return this.verticalFilterList;
	}

	/**
	 *
	 * @return
	 *         the new list to use for horizontal element
	 */
	protected List<Object> createHorizontalElementList() {
		// return Collections.synchronizedList(new ArrayList<Object>());
		this.basicHorizontalList = GlazedLists.eventList(new ArrayList<>());
		this.rowSortedList = new SortedList<>(this.basicHorizontalList, null);
		final FilterList<Object> filteredList = new FilterList<>(this.rowSortedList);
		this.horizontalFilterList = filteredList;
		return this.horizontalFilterList;
	}

	public SortedList<Object> getRowSortedList() {
		return this.rowSortedList;
	}

	public SortedList<Object> getColumnSortedList() {
		return this.columnSortedList;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager#getRowSortModel()
	 *
	 * @return
	 */
	@Override
	protected IPapyrusSortModel getRowSortModel() {
		if (this.rowSortModel == null) {
			final boolean inverted = getTable().isInvertAxis();
			if (inverted) {
				this.rowSortModel = new PapyrusCompositeGlazedListSortModel(this, getColumnSortedList(), getRowSortedList(), inverted);
			} else {
				this.rowSortModel = new PapyrusCompositeGlazedListSortModel(this, getRowSortedList(), getColumnSortedList(), inverted);
			}
		}
		return this.rowSortModel;


	}


	@Override
	public NatTable createNattable(final Composite parent, final int style, final IWorkbenchPartSite site) {
		updateCellMap(null);
		final NatTable nattable = super.createNattable(parent, style, site);
		this.refreshListener = new CommandStackListener() {

			/**
			 *
			 * @see org.eclipse.emf.common.command.CommandStackListener#commandStackChanged(java.util.EventObject)
			 *
			 * @param event
			 */
			@Override
			public void commandStackChanged(final EventObject event) {
				// we refresh the table after each command, only when it is visible
				// its allows to refresh the text and the appearance of the table
				// this refresh doesn't manage the add/remove axis
				refreshNatTable();
			}
		};

		getContextEditingDomain().getCommandStack().addCommandStackListener(this.refreshListener);
		if (getTableEditingDomain() != null && getTableEditingDomain() != getContextEditingDomain()) {
			getTableEditingDomain().getCommandStack().addCommandStackListener(this.refreshListener);
		}
		this.focusListener = this;
		nattable.addFocusListener(this.focusListener);

		// registering to focusService allows to declare properties tester on "activeFocusControl" and "activeFocusControlId" variables
		focusService = PlatformUI.getWorkbench().getService(IFocusService.class);
		if (focusService != null) {
			String id = null;
			if (getTable().getTableConfiguration() != null) {
				id = getTable().getTableConfiguration().getType();
			}
			if (id == null) {
				id = getTableName();
			}
			focusService.addFocusTracker(nattable, id);
		}


		this.layerListener = new ILayerListener() {

			@Override
			public void handleLayerEvent(final ILayerEvent event) {
				updateToggleActionState();
			}
		};
		nattable.addLayerListener(layerListener);

		updateToggleActionState();// required, because the focus listener is not notified just after the creation of the widget

		// add the decoration service observer to be able update the table when markers changed
		this.decoractionServiceObserver = getOrCreateDecorationServiceObserver();
		getDecorationService().addListener(decoractionServiceObserver);

		return nattable;
	}

	@Override
	public void focusLost(final FocusEvent e) {
		// nothing to do
	}

	@Override
	public void focusGained(final FocusEvent e) {
		updateToggleActionState();
		NatTableFocusUtils.getInstance().setCurrentNattableModelManager(this);
	}


	/**
	 * this command update the status of the toggle actions
	 */
	@Override
	protected void updateToggleActionState() {
		super.updateToggleActionState();
		final ICommandService commandService = EclipseCommandUtils.getCommandService();
		if (commandService != null) {

			final AbstractHeaderAxisConfiguration columnAxisConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisConfigurationUsedInTable(getTable());
			final AbstractHeaderAxisConfiguration rowAxisConfiguration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisUsedInTable(getTable());
			// update the header configuration
			org.eclipse.core.commands.Command command = commandService.getCommand(CommandIds.COMMAND_COLUMN_DISPLAY_INDEX_ID);
			updateToggleCommandState(command, columnAxisConfiguration.isDisplayIndex());

			command = commandService.getCommand(CommandIds.COMMAND_COLUMN_DISPLAY_LABEL_ID);
			updateToggleCommandState(command, columnAxisConfiguration.isDisplayLabel());

			command = commandService.getCommand(CommandIds.COMMAND_COLUMN_DISPLAY_INDEX_STYLE_ID);
			updateRadioCommandState(command, columnAxisConfiguration.getIndexStyle().getLiteral());

			command = commandService.getCommand(CommandIds.COMMAND_ROW_DISPLAY_INDEX_ID);
			updateToggleCommandState(command, rowAxisConfiguration.isDisplayIndex());

			command = commandService.getCommand(CommandIds.COMMAND_ROW_DISPLAY_LABEL_ID);
			updateToggleCommandState(command, rowAxisConfiguration.isDisplayLabel());

			command = commandService.getCommand(CommandIds.COMMAND_ROW_DISPLAY_INDEX_STYLE_ID);
			updateRadioCommandState(command, rowAxisConfiguration.getIndexStyle().getLiteral());


			// update the label header configuration
			final List<ILabelProviderConfiguration> columnLabelConfigurations = columnAxisConfiguration.getOwnedLabelConfigurations();
			final List<ILabelProviderConfiguration> rowLabelConfigurations = rowAxisConfiguration.getOwnedLabelConfigurations();
			for (final ILabelProviderConfiguration current : columnLabelConfigurations) {
				if (current instanceof ObjectLabelProviderConfiguration) {
					final ObjectLabelProviderConfiguration labelConfig = (ObjectLabelProviderConfiguration) current;
					command = commandService.getCommand(CommandIds.COMMAND_COLUMN_LABEL_DISPLAY_ICON);
					updateToggleCommandState(command, labelConfig.isDisplayIcon());

					command = commandService.getCommand(CommandIds.COMMAND_COLUMN_LABEL_DISPLAY_LABEL);
					updateToggleCommandState(command, labelConfig.isDisplayLabel());

					if (labelConfig instanceof FeatureLabelProviderConfiguration) {
						final FeatureLabelProviderConfiguration labelFeatureConf = (FeatureLabelProviderConfiguration) labelConfig;
						command = commandService.getCommand(CommandIds.COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_IS_DERIVED);
						updateToggleCommandState(command, labelFeatureConf.isDisplayIsDerived());

						command = commandService.getCommand(CommandIds.COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_MULTIPLICITY);
						updateToggleCommandState(command, labelFeatureConf.isDisplayMultiplicity());

						command = commandService.getCommand(CommandIds.COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_TYPE);
						updateToggleCommandState(command, labelFeatureConf.isDisplayType());


						command = commandService.getCommand(CommandIds.COMMAND_COLUMN_LABEL_FEATURE_DISPLAY_NAME);
						updateToggleCommandState(command, labelFeatureConf.isDisplayName());
					}
				}
			}

			for (final ILabelProviderConfiguration current : rowLabelConfigurations) {
				if (current instanceof ObjectLabelProviderConfiguration) {
					final ObjectLabelProviderConfiguration labelConfig = (ObjectLabelProviderConfiguration) current;

					command = commandService.getCommand(CommandIds.COMMAND_ROW_LABEL_DISPLAY_ICON);
					updateToggleCommandState(command, labelConfig.isDisplayIcon());

					command = commandService.getCommand(CommandIds.COMMAND_ROW_LABEL_DISPLAY_LABEL);
					updateToggleCommandState(command, labelConfig.isDisplayLabel());

					if (labelConfig instanceof FeatureLabelProviderConfiguration) {
						final FeatureLabelProviderConfiguration labelFeatureConf = (FeatureLabelProviderConfiguration) labelConfig;
						command = commandService.getCommand(CommandIds.COMMAND_ROW_LABEL_FEATURE_DISPLAY_IS_DERIVED);
						updateToggleCommandState(command, labelFeatureConf.isDisplayIsDerived());

						command = commandService.getCommand(CommandIds.COMMAND_ROW_LABEL_FEATURE_DISPLAY_MULTIPLICITY);
						updateToggleCommandState(command, labelFeatureConf.isDisplayMultiplicity());

						command = commandService.getCommand(CommandIds.COMMAND_ROW_LABEL_FEATURE_DISPLAY_TYPE);
						updateToggleCommandState(command, labelFeatureConf.isDisplayType());

						command = commandService.getCommand(CommandIds.COMMAND_ROW_LABEL_FEATURE_DISPLAY_NAME);
						updateToggleCommandState(command, labelFeatureConf.isDisplayName());
					}
				}
			}

			// update the property IMasterObjectAxisProvider#disconnectslave
			if (columnProvider instanceof ISlaveAxisProvider) {
				command = commandService.getCommand(CommandIds.COMMAND_ROW_DISCONNECT_SLAVE);
				updateToggleCommandState(command, ((IMasterAxisProvider) rowProvider).isDisconnectSlave());
			}

			if (rowProvider instanceof ISlaveAxisProvider) {
				command = commandService.getCommand(CommandIds.COMMAND_COLUMN_DISCONNECT_SLAVE);
				updateToggleCommandState(command, ((IMasterAxisProvider) columnProvider).isDisconnectSlave());
			}

			// we update the state for the invert axis command
			command = commandService.getCommand(CommandIds.COMMAND_INVERT_AXIS);
			updateToggleCommandState(command, getTable().isInvertAxis());

			// updates the state of each merge actions commands
			command = commandService.getCommand(CommandIds.COMMAND_MERGE_ROWS);
			updateToggleCommandState(command, getToggleStateAllRows());

			command = commandService.getCommand(CommandIds.COMMAND_MERGE_COLUMNS);
			updateToggleCommandState(command, getToggleStateAllColumns());

			command = commandService.getCommand(CommandIds.COMMAND_MERGE_SELECTED_ROWS);
			updateToggleCommandState(command, getToggleStateSelectedRows());

			command = commandService.getCommand(CommandIds.COMMAND_MERGE_SELECTED_COLUMNS);
			updateToggleCommandState(command, getToggleStateSelectedColumns());

			// the merge of all the elements in the table is not yet supported
			// command = commandService.getCommand(CommandIds.COMMAND_MERGE_TABLE);
			// BooleanValueStyle mergeTable = (BooleanValueStyle)getTable().getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.MERGE_TABLE);
			// if(mergeTable != null) {
			// updateToggleCommandState(command, mergeTable.isBooleanValue());
			// } else {
			// updateToggleCommandState(command, false);
			// }

			// Update the wrap text command state
			command = commandService.getCommand(CommandIds.COMMAND_WRAP_TEXT);
			updateToggleCommandState(command, StyleUtils.getBooleanNamedStyleValue(getTable(), NamedStyleConstants.WRAP_TEXT));

			// Update the auto resize cell height state
			command = commandService.getCommand(CommandIds.COMMAND_AUTO_RESIZE_CELL_HEIGHT);
			updateToggleCommandState(command, StyleUtils.getBooleanNamedStyleValue(getTable(), NamedStyleConstants.AUTO_RESIZE_CELL_HEIGHT));

			// Update the display list on separated rows state
			// Get the selected axis index
			final int selectedAxisIndex = AxisUtils.getUniqueSelectedAxisIndex(this);
			// Always get the column axis provider for invert or non-invert table
			final AbstractAxisProvider axisProvider = getTable().getCurrentColumnAxisProvider();

			// If the index is in range, get the relevant axis and update its toggle command state
			if (null != axisProvider && 0 <= selectedAxisIndex && selectedAxisIndex < axisProvider.getAxis().size()) {
				final IAxis selectedAxis = axisProvider.getAxis().get(selectedAxisIndex);
				String commandID = CommandIds.COMMAND_DISPLAY_LIST_ON_SEPARATED_ROWS_COLUMNHEADER;
				if (getTable().isInvertAxis()) {
					commandID = CommandIds.COMMAND_DISPLAY_LIST_ON_SEPARATED_ROWS_ROWHEADER;
				}
				command = commandService.getCommand(commandID);
				updateToggleCommandState(command, StyleUtils.getBooleanNamedStyleValue(selectedAxis, NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS, NamedStyleConstants.ENABLE_DISPLAY_LIST_ON_SEPARATED_ROWS));
			}

		} else {
			throw new RuntimeException(String.format("The Eclipse service %s has not been found", ICommandService.class)); //$NON-NLS-1$
		}

	}

	/**
	 *
	 * @param command
	 *            an eclipse command
	 * @param newValue
	 *            the new boolean value to set to the state of this command
	 */
	private void updateToggleCommandState(final org.eclipse.core.commands.Command command, final boolean newValue) {
		EclipseCommandUtils.updateToggleCommandState(command, newValue);
	}


	/**
	 *
	 * @param command
	 *            an eclispe command
	 * @param newValue
	 *            the new value to set to the state of this command
	 */
	protected void updateRadioCommandState(final org.eclipse.core.commands.Command command, final Object newValue) {
		EclipseCommandUtils.updateRadioCommandState(command, newValue);
	}

	public void invertJavaObject() {
		getRowSortModel().clear();// we clear the sort model
		final AbstractAxisProvider newColumProvider = this.rowProvider;
		final AbstractAxisProvider newRowProvider = this.columnProvider;
		final List<Object> newVerticalElementList = this.horizontalElements;
		final List<Object> newHorizontalElementList = this.verticalElements;
		final ICompositeAxisManager newRowManager = this.columnManager;
		final ICompositeAxisManager newColumnManager = this.rowManager;

		NattableModelManager.this.columnProvider = newColumProvider;
		NattableModelManager.this.rowProvider = newRowProvider;

		NattableModelManager.this.verticalElements = newVerticalElementList;
		NattableModelManager.this.horizontalElements = newHorizontalElementList;

		final EventList<Object> newHorizontalBasicList = this.basicVerticalList;
		final EventList<Object> newVerticalBasicList = this.basicHorizontalList;

		final SortedList<Object> newHorizontalSortedList = this.columnSortedList;
		final SortedList<Object> newVerticalSortedList = this.rowSortedList;

		final FilterList<Object> newVerticalFilterLilst = this.horizontalFilterList;
		final FilterList<Object> newHorizontalFilterList = this.verticalFilterList;

		this.basicVerticalList = newVerticalBasicList;
		this.basicHorizontalList = newHorizontalBasicList;

		this.horizontalFilterList = newHorizontalFilterList;
		this.verticalFilterList = newVerticalFilterLilst;

		this.rowSortedList = newHorizontalSortedList;
		this.columnSortedList = newVerticalSortedList;

		NattableModelManager.this.columnManager = newColumnManager;
		this.rowManager.setAxisComparator(null);
		this.columnManager.setAxisComparator(null);

		getRowSortModel().setTableInverted(getTable().isInvertAxis());
		updateToggleActionState();
		// configureNatTable();

		configureCellAxisEditor();
		configureFilters();
		refreshNatTable();
	}

	public EventList<Object> getHorizontalBasicEventList() {
		return this.basicHorizontalList;
	}

	public FilterList<Object> getHorizontalFilterEventList() {
		return this.horizontalFilterList;
	}

	public FilterList<Object> getVerticalFilterList() {
		return this.verticalFilterList;
	}

	public void resizeAxis() {
		// If the columns width is managed by percentage, the table axis don't have to be recalculated
		if (!isColumnWidthAsPercentage()) {
			initTableAxis();
		}
		refreshNatTable();
	}

	public void resizeHeader() {
		initTableHeaders();
		refreshNatTable();
	}

	public void mergeTable() {
		initTableMerge();
		refreshNatTable();
	}

	/**
	 * create the line and the columns managers
	 */
	protected void init() {
		if (this.columnManager != null) {
			this.columnManager.dispose();
		}
		if (this.rowManager != null) {
			this.rowManager.dispose();
		}
		this.columnProvider = getTable().getCurrentColumnAxisProvider();
		this.rowProvider = getTable().getCurrentRowAxisProvider();
		this.columnManager = createAxisManager(getTable().getTableConfiguration().getColumnHeaderAxisConfiguration().getAxisManagers(), getTable().getCurrentColumnAxisProvider(), true);
		this.rowManager = createAxisManager(getTable().getTableConfiguration().getRowHeaderAxisConfiguration().getAxisManagers(), getTable().getCurrentRowAxisProvider(), false);

		final boolean allIsSlave = this.columnManager.isSlave() && this.rowManager.isSlave();
		Assert.isTrue(!allIsSlave, Messages.NattableModelManager_AtLeastOfOneTheAxisManagerMustBeAMaster);
	}

	/**
	 *
	 * @param ids
	 *            the ids of the axis manager to use
	 * @param contentProvider
	 *            the content provider in the model
	 * @return the created axis manager to use to manage the {@link IAxisContentsProvider}
	 */
	protected ICompositeAxisManager createAxisManager(final List<AxisManagerRepresentation> representations, final AbstractAxisProvider contentProvider, final boolean columnAxisManager) {
		final ICompositeAxisManager compositeAxisManager = new CompositeAxisManager();
		final List<IAxisManager> managers = new ArrayList<>();
		for (final AxisManagerRepresentation current : representations) {
			final IAxisManager manager = AxisManagerFactory.INSTANCE.getAxisManager(current);
			Assert.isNotNull(manager);
			manager.init(this, current, contentProvider);
			managers.add(manager);
		}
		compositeAxisManager.init(this, null, contentProvider);
		compositeAxisManager.setSubAxisManager(managers);
		return compositeAxisManager;
	}


	/**
	 *
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 *
	 */
	@Override
	public void dispose() {
		if (this.decoractionServiceObserver != null) {
			// Bug 490067: Check if the decoration service is available to avoid null pointer
			if (null != this.decorationService) {
				this.decorationService.deleteListener(this.decoractionServiceObserver);
				this.decorationService = null;
			}
			this.decoractionServiceObserver = null;
		}
		if (this.tableEditingDomain != null) {
			if (this.tableEditingDomain.getCommandStack() != null) {
				this.tableEditingDomain.getCommandStack().removeCommandStackListener(this.refreshListener);
			}
			if (this.tableEditingDomain != null) {
				this.tableEditingDomain.removeResourceSetListener(resourceSetListener);
			}

		}

		if (this.contextEditingDomain != null) {
			if (this.contextEditingDomain.getCommandStack() != null) {
				this.contextEditingDomain.getCommandStack().removeCommandStackListener(this.refreshListener);
			}
			if (this.contextEditingDomain != null) {
				this.contextEditingDomain.removeResourceSetListener(resourceSetListener);
			}
		}

		if (this.columnManager != null) {
			this.columnManager.dispose();
			this.columnManager = null;
		}

		if (this.rowManager != null) {
			this.rowManager.dispose();
			this.rowManager = null;
		}

		final Table table = getTable();
		if (table != null) {

			if (this.tableCellsListener != null) {
				table.eAdapters().remove(this.tableCellsListener);
			}
			if (this.changeAxisProvider != null) {
				table.eAdapters().remove(this.changeAxisProvider);
			}
			if (this.invertAxisListener != null) {
				table.eAdapters().remove(this.invertAxisListener);
			}
		}

		if (this.cellsMap != null) {
			this.cellsMap.clear();
		}
		if (this.natTable != null && !this.natTable.isDisposed()) {
			if (this.layerListener != null) {
				natTable.removeLayerListener(this.layerListener);
			}
			if (this.focusListener != null) {
				natTable.removeFocusListener(this.focusListener);
			}
		}

		if (focusService != null && this.natTable != null) {
			focusService.removeFocusTracker(this.natTable);
			focusService = null;
		}

		removeListeners();
		super.dispose();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#addRows(java.util.Collection)
	 *
	 * @param objectsToAdd
	 *            the list of the objects to add in rows
	 */
	@Override
	public void addRows(final Collection<Object> objectsToAdd) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final Command cmd = getAddRowElementCommand(objectsToAdd);
		if (cmd != null && cmd.canExecute()) {
			domain.getCommandStack().execute(cmd);
		}
	}

	/**
	 * called when the manager is used vertically
	 */
	@Override
	public int getColumnCount() {
		return this.getColumnElementsList().size();
	}

	/**
	 * called when the manager is used horizontally
	 */

	@Override
	public int getRowCount() {
		return this.getRowElementsList().size();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#addColumns(java.util.Collection)
	 *
	 * @param objectsToAdd
	 *            the list of the objects to add in columns
	 */
	@Override
	public void addColumns(final Collection<Object> objectsToAdd) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final Command cmd = getAddColumnElementCommand(objectsToAdd);
		if (cmd != null && cmd.canExecute()) {
			domain.getCommandStack().execute(cmd);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#removeColumns(java.util.Collection)
	 *
	 * @param objetsToRemove
	 */
	@Override
	public void removeColumns(final Collection<Object> objetsToRemove) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final Command cmd = getDestroyColumnElementCommand(objetsToRemove);
		if (cmd != null && cmd.canExecute()) {
			domain.getCommandStack().execute(cmd);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#removeRows(java.util.Collection)
	 *
	 * @param objectsToRemove
	 */
	@Override
	public void removeRows(final Collection<Object> objectsToRemove) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final Command cmd = getDestroyRowElementCommand(objectsToRemove);
		if (cmd != null && cmd.canExecute()) {
			domain.getCommandStack().execute(cmd);
		}
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getBodyDataProvider()
	 *
	 * @return the data provider for the body of the table
	 */
	@Override
	public IDataProvider getBodyDataProvider() {
		return this;
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#getDataValue(int, int)
	 *
	 * @param columnIndex
	 *            the index of the column
	 * @param rowIndex
	 *            the index of the row
	 * @return the contents to display in the cell localted to columnIndex and
	 *         rowIndex
	 */
	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		final Object row;
		final Object column;
		final Object obj1 = this.verticalElements.get(columnIndex);
		final Object obj2 = this.horizontalElements.get(rowIndex);
		if (getTable().isInvertAxis()) {
			column = obj2;
			row = obj1;
		} else {
			row = obj2;
			column = obj1;
		}
		return CellManagerFactory.INSTANCE.getCrossValue(column, row, this);
	}

	@Override
	public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
		final Object row;
		final Object column;
		final Object obj1 = this.verticalElements.get(columnIndex);
		final Object obj2 = this.horizontalElements.get(rowIndex);
		if (getTable().isInvertAxis()) {
			column = obj2;
			row = obj1;
		} else {
			row = obj2;
			column = obj1;
		}
		CellManagerFactory.INSTANCE.setCellValue(getContextEditingDomain(), column, row, newValue, this);
	}

	/**
	 * refresh NatTable (asyncExec)
	 */
	public void refreshNatTable() {
		// This refresh code has been duplicated from the refresh of the ModelExplorer (class ModelExplorerView)
		final Runnable schedule;
		if (this.natTable != null && !this.natTable.isDisposed()) {
			synchronized (this) {
				if (refreshRunnable == null) {
					// No refresh is yet pending. Schedule one
					schedule = createRefreshRunnable();
					refreshRunnable = schedule;
				} else {
					schedule = null;
				}
			}

			if (schedule != null) {
				final Control control = this.natTable;
				final Display display = ((control == null) || control.isDisposed()) ? null : control.getDisplay();

				if (display != null) {
					// Don't need to schedule a refresh if we have no control or it's disposed
					display.asyncExec(schedule);
				}
			}
		}
	}

	/**
	 *
	 * @return
	 *         a new runnable for the refreash action
	 */
	private Runnable createRefreshRunnable() {
		return new Runnable() {

			@Override
			public void run() {
				// Only run if I'm still pending
				synchronized (NattableModelManager.this) {
					if (refreshRunnable != this) {
						return;
					}

					refreshRunnable = null;
				}

				refreshInUIThread();
			}
		};
	}

	/**
	 * refresh the view.
	 */
	protected void refreshInUIThread() {
		// Need to refresh, even if (temporarily) invisible
		// (Better alternative?: store refresh event and execute once visible again)
		if (this.natTable != null && this.natTable.isDisposed()) {
			return;
		}

		// avoid reentrant call
		// Refresh only of we are not already refreshing.
		if (isRefreshing.compareAndSet(false, true)) {
			// Get the previous selection before the refresh
			final TableStructuredSelection selectionInTable = getSelectionInTable();

			// we get the current selection (reset to empty after the refresh...)
			final TableSelectionWrapper tableSelectionWrapper;
			if (selectionInTable != null) {
				// we get the current selection (reset to empty after the refresh...)
				tableSelectionWrapper = (TableSelectionWrapper) selectionInTable.getAdapter(TableSelectionWrapper.class);
			} else {
				tableSelectionWrapper = null;
			}

			// stop to update selection on selection event
			unactivateSelectionProvider();

			final SelectionLayer selectionLayer = getBodyLayerStack().getSelectionLayer();
			// useless
			// selectionLayer.doCommand(new ClearAllSelectionsCommand());

			// this.natTable.refresh();
			// custom refresh for bug 562619
			this.natTable.doCommand(new CustomStructuralRefreshCommand());

			// Refresh the nattable columns size in the case of named style
			doFillColumnsSize();

			// Resize all rows if necessary
			reinitialiseRowHeight();

			// Keep the selection after the refresh of the table

			// Keep the columns selected before the refresh
			if (tableSelectionWrapper != null) {
				// if (false || tableSelectionWrapper.isSelectAll()) {
				// natTable.doCommand(new SelectAllCommand());
				// } else {
				// update the position of the elements in the selection
				// required to avoid bad re-apply of the previous selection
				tableSelectionWrapper.updatePositions();

				// now we can take the position of element to select
				final Set<Integer> columnIndexes = tableSelectionWrapper.getFullySelectedColumns().keySet();
				final Set<Integer> rowIndexes = tableSelectionWrapper.getFullySelectedRows().keySet();
				final Set<PositionCoordinate> selectedCells = tableSelectionWrapper.getSingleSelectedCells();

				boolean ctrlMask = false;

				// Keep the columns selected before the refresh
				for (final int index : columnIndexes) {
					selectionLayer.doCommand(new SelectColumnCommand(selectionLayer, index, 0, false, ctrlMask));
					ctrlMask = true;
				}


				// Keep the rows selected before the refresh
				for (final int index : rowIndexes) {
					selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, index, false, ctrlMask));
					ctrlMask = true;
				}

				// Manage the cells not contained in the rows and columns already selected
				for (PositionCoordinate selectedCell : selectedCells) {
					selectionLayer.doCommand(new SelectCellCommand(selectionLayer, selectedCell.getColumnPosition(), selectedCell.getRowPosition(), false, ctrlMask));
					ctrlMask = true;
				}

			}
			// reactivate the selection provider
			activateSelectionProvider();

			isRefreshing.set(false);
		}
	}

	/**
	 *
	 * @param axis
	 *            the axis for which must refresh the contents
	 */
	public void updateAxisContents(final AbstractAxisProvider axis) {

		try {
			if (null != getTableEditingDomain()) {
				getTableEditingDomain().runExclusive(new Runnable() {

					@Override
					public void run() {
						Display.getDefault().syncExec(new Runnable() {

							@Override
							public void run() {
								if (NattableModelManager.this.natTable != null && !NattableModelManager.this.natTable.isDisposed()) {
									if (NattableModelManager.this.getTable() != null && NattableModelManager.this.getTable().getTableConfiguration() != null) {
										if (axis == NattableModelManager.this.columnProvider) {
											updateColumnContents();
											NattableModelManager.this.getRowSortModel().updateSort();
										} else {
											updateRowContents();
										}
									}
								}
							}
						});

					}

				});
			}
		} catch (final InterruptedException e) {
			Activator.log.error(e);
		}

	}

	/**
	 * refresh the row contents
	 */
	private void updateRowContents() {
		NattableModelManager.this.rowManager.updateAxisContents();
		final CellEditorDeclaration declaration = getCellEditorDeclarationToUse(getTable());
		if (declaration.equals(CellEditorDeclaration.ROW)) {
			// configureNatTable(); : see bug 463058: [Table 2] Invert Axis + add/remove columns break the display of the table
			refreshNatTable();
		} else {
			refreshNatTable();
		}
	}



	/**
	 * refresh the column contents
	 */
	private void updateColumnContents() {
		NattableModelManager.this.columnManager.updateAxisContents();
		final CellEditorDeclaration declaration = getCellEditorDeclarationToUse(getTable());
		if (declaration.equals(CellEditorDeclaration.COLUMN)) {
			// configureNatTable(); : see bug 463058: [Table 2] Invert Axis + add/remove columns break the display of the table
			refreshNatTable();
		} else {
			refreshNatTable();
		}
	}

	/**
	 *
	 * @param table
	 *            the table
	 * @return
	 *         the celleditor declaration to use according to the table configuration and {@link Table#isInvertAxis()}
	 */
	private CellEditorDeclaration getCellEditorDeclarationToUse(final Table table) {
		CellEditorDeclaration declaration = table.getTableConfiguration().getCellEditorDeclaration();
		if (getTable().isInvertAxis()) {
			if (declaration.equals(CellEditorDeclaration.COLUMN)) {
				declaration = CellEditorDeclaration.ROW;
			} else if (declaration.equals(CellEditorDeclaration.ROW)) {
				declaration = CellEditorDeclaration.COLUMN;
			}
		}
		return declaration;
	}

	@Override
	public List<Object> getColumnElementsList() {
		return this.verticalElements;
	}

	@Override
	public List<Object> getRowElementsList() {
		// return this.eventList;
		return this.horizontalElements;
	}


	@Override
	public boolean canInsertRow(final Collection<Object> objectsToAdd, final int index) {
		return this.rowManager.canInsertAxis(objectsToAdd, index);
	}

	@Override
	public boolean canInsertColumns(final Collection<Object> objectsToAdd, final int index) {
		return this.columnManager.canInsertAxis(objectsToAdd, index);
	}

	@Override
	public boolean canDropColumnsElement(final Collection<Object> objectsToAdd) {
		return this.columnManager.canDropAxisElement(objectsToAdd);
	}

	@Override
	public boolean canDropRowElement(final Collection<Object> objectsToAdd) {
		return this.rowManager.canDropAxisElement(objectsToAdd);
	}

	@Override
	public void insertRows(final Collection<Object> objectsToAdd, final int index) {
		this.rowManager.getInsertAxisCommand(objectsToAdd, index);

	}

	@Override
	public void insertColumns(final Collection<Object> objectsToAdd, final int index) {
		this.columnManager.getInsertAxisCommand(objectsToAdd, index);
	}

	@Override
	public Object getColumnElement(final int index) {
		if (index < 0 || index >= this.verticalElements.size()) {
			return null;
		}
		return this.verticalElements.get(index);
	}

	@Override
	public Object getRowElement(final int index) {
		if (index < 0 || index >= this.horizontalElements.size()) {
			return null;
		}
		return this.horizontalElements.get(index);
	}

	public List<Object> getElementsList(final AbstractAxisProvider axisProvider) {
		if (axisProvider == this.columnProvider) {
			return this.verticalElements;
		} else if (axisProvider == this.rowProvider) {
			return this.horizontalElements;
		}
		return null;
	}

	@Override
	public boolean canMoveRows() {
		return this.rowManager.canMoveAxis();
	}

	@Override
	public boolean canMoveColumns() {
		return this.columnManager.canMoveAxis();
	}

	@Override
	public void moveColumnElement(final Object axisToMove, final int newIndex) {
		this.columnManager.moveAxis(axisToMove, newIndex);
	}

	// not tested
	@Override
	public void moveRowElement(final Object axisToMove, final int newIndex) {
		this.rowManager.moveAxis(axisToMove, newIndex);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#invertAxis()
	 *
	 */
	@Override
	public void invertAxis() {
		final CompoundCommand cmd = new CompoundCommand(Messages.NattableModelManager_SwitchLinesAndColumns);
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final boolean oldValue = getTable().isInvertAxis();
		if (canInvertAxis()) {
			final Command tmp = new SetCommand(domain, getTable(), NattablePackage.eINSTANCE.getTable_InvertAxis(), !oldValue);
			cmd.append(tmp);
			domain.getCommandStack().execute(cmd);
		}

		// use the method to refresh the table using the appointed values saved in the model.notation
		initTableAxis();
		initTableHeaders();
		initTableMerge();
	}

	@Override
	public boolean canInvertAxis() {
		return this.columnManager.canBeUsedAsRowManager() && this.rowManager.canBeUsedAsColumnManager();
	}

	@Override
	public boolean canCreateRowElement(final String elementType) {
		return this.rowManager.canCreateAxisElement(elementType);
	}

	@Override
	public boolean canCreateColumnElement(final String elementType) {
		return this.columnManager.canCreateAxisElement(elementType);
	}

	@Override
	public Command getAddRowElementCommand(final Collection<Object> objectsToAdd) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final CompoundCommand cmd = new CompoundCommand(Messages.NattableModelManager_AddRowCommand);
		Command tmp = this.rowManager.getAddAxisCommand(domain, objectsToAdd);
		if (tmp != null) {
			cmd.append(tmp);
		}
		final AbstractAxisProvider rowsProvider = AxisUtils.getAxisProviderUsedForRows(this);
		final AbstractAxisProvider columnsProvider = AxisUtils.getAxisProviderUsedForColumns(this);
		final boolean addComplementaryAxis = rowsProvider instanceof IMasterAxisProvider && columnsProvider instanceof ISlaveAxisProvider && !((IMasterAxisProvider) rowsProvider).isDisconnectSlave();
		if (addComplementaryAxis) {
			tmp = this.columnManager.getComplementaryAddAxisCommand(domain, objectsToAdd);
			if (tmp != null) {
				cmd.append(tmp);
			}
		}
		return cmd;
	}

	@Override
	public Command getAddColumnElementCommand(final Collection<Object> objectsToAdd) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final CompoundCommand cmd = new CompoundCommand(Messages.NattableModelManager_AddColumnCommand);
		Command tmp = this.columnManager.getAddAxisCommand(domain, objectsToAdd);
		if (tmp != null) {
			cmd.append(tmp);
		}
		final AbstractAxisProvider rowsProvider = AxisUtils.getAxisProviderUsedForRows(this);
		final AbstractAxisProvider columnsProvider = AxisUtils.getAxisProviderUsedForColumns(this);
		final boolean addComplementaryAxis = columnsProvider instanceof IMasterAxisProvider && rowsProvider instanceof ISlaveAxisProvider && !((IMasterAxisProvider) columnsProvider).isDisconnectSlave();
		if (addComplementaryAxis) {
			tmp = this.rowManager.getComplementaryAddAxisCommand(domain, objectsToAdd);
			if (tmp != null) {
				cmd.append(tmp);
			}
		}
		return cmd;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getAddRowElementCommand(java.util.Collection, int)
	 */
	@Override
	public Command getAddRowElementCommand(final Collection<Object> objectsToAdd, final int index) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final CompoundCommand cmd = new CompoundCommand(Messages.NattableModelManager_AddRowCommand);
		Command tmp = this.rowManager.getAddAxisCommand(domain, objectsToAdd, index);
		if (tmp != null) {
			cmd.append(tmp);
		}
		final AbstractAxisProvider rowsProvider = AxisUtils.getAxisProviderUsedForRows(this);
		final AbstractAxisProvider columnsProvider = AxisUtils.getAxisProviderUsedForColumns(this);
		boolean addComplementaryAxis = rowsProvider instanceof IMasterAxisProvider && columnsProvider instanceof ISlaveAxisProvider && !((IMasterAxisProvider) rowsProvider).isDisconnectSlave();
		if (addComplementaryAxis) {
			tmp = this.columnManager.getComplementaryAddAxisCommand(domain, objectsToAdd);
			if (tmp != null) {
				cmd.append(tmp);
			}
		}
		return cmd;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getAddColumnElementCommand(java.util.Collection, int)
	 */
	@Override
	public Command getAddColumnElementCommand(final Collection<Object> objectsToAdd, final int index) {
		final TransactionalEditingDomain domain = getTableEditingDomain();
		final CompoundCommand cmd = new CompoundCommand(Messages.NattableModelManager_AddColumnCommand);
		Command tmp = this.columnManager.getAddAxisCommand(domain, objectsToAdd, index);
		if (tmp != null) {
			cmd.append(tmp);
		}
		final AbstractAxisProvider rowsProvider = AxisUtils.getAxisProviderUsedForRows(this);
		final AbstractAxisProvider columnsProvider = AxisUtils.getAxisProviderUsedForColumns(this);
		boolean addComplementaryAxis = columnsProvider instanceof IMasterAxisProvider && rowsProvider instanceof ISlaveAxisProvider && !((IMasterAxisProvider) columnsProvider).isDisconnectSlave();
		if (addComplementaryAxis) {
			tmp = this.rowManager.getComplementaryAddAxisCommand(domain, objectsToAdd);
			if (tmp != null) {
				cmd.append(tmp);
			}
		}
		return cmd;
	}

	public Command getDestroyColumnElementCommand(final Collection<Object> objectsToDestroy) {
		final TransactionalEditingDomain domain = getContextEditingDomain();
		final Command cmd = this.columnManager.getDestroyAxisCommand(domain, objectsToDestroy);
		return cmd;
	}

	public Command getDestroyRowElementCommand(final Collection<Object> objectsToDestroy) {
		final TransactionalEditingDomain domain = getContextEditingDomain();
		final Command cmd = this.rowManager.getDestroyAxisCommand(domain, objectsToDestroy);
		return cmd;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getTableAxisElementProvider()
	 *
	 * @return
	 */
	@Override
	public ITableAxisElementProvider getTableAxisElementProvider() {
		return this;
	}

	/**
	 * When the axis manager is dynamic, we can't destroy axis
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#canCreateDestroyColumnsAxis()
	 *
	 * @return
	 */
	@Override
	public boolean canCreateDestroyColumnsAxis() {
		return !this.columnManager.isDynamic() && this.columnManager.createPossibleAxisContentProvider(true) != null;
	}

	/**
	 * When the axis manager is dynamic, we can't destroy axis
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#canCreateDestroyRowsAxis()
	 *
	 * @return
	 */
	@Override
	public boolean canCreateDestroyRowsAxis() {
		return !this.rowManager.isDynamic() && this.rowManager.createPossibleAxisContentProvider(true) != null;
	}

	/**
	 *
	 * @param serv
	 *            the label provider service
	 * @param editedAxisManager
	 *            the manager used for the edited axis
	 * @param secondAxisManager
	 *            the manager used for the other axis
	 * @param isEditingColumns
	 *            boolean indicating if we are editing columns or rows
	 */
	private final void openCreateDestroyAxisManagerDialog(final boolean isEditingColumns) {
		final String dialogTitle;
		final String dialogCheckBoxTootip;
		final String dialogQuestion;

		final IAxisManager editedAxisManager;
		final IAxisManager secondAxisManager;
		final String checkBoxMessage = Messages.NattableModelManager_DisconnectThisAxisManager;
		if (isEditingColumns) {
			dialogTitle = Messages.NattableModelManager_SelectColumns;
			dialogCheckBoxTootip = Messages.NattableModelManager_DisableTheAutomaticAdditionOfColumnsWhenARowIsAdded;
			dialogQuestion = String.format(Messages.NattableModelManager_TheCheckBoxHasNotBeenCheckedToAvoidAutomaticColumnAddition, checkBoxMessage);

			editedAxisManager = columnManager;
			secondAxisManager = rowManager;
		} else {
			dialogTitle = Messages.NattableModelManager_SelectRows;
			dialogCheckBoxTootip = Messages.NattableModelManager_DisableTheAutomaticAdditionOfRowsWhenAColumnIsAdded;
			dialogQuestion = String.format(Messages.NattableModelManager_TheCheckBoxHasNotBeenCheckedToAvoidAutomaticRowAddition, checkBoxMessage);

			editedAxisManager = rowManager;
			secondAxisManager = columnManager;
		}
		final LabelProviderService serv = this.natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		final ILabelProvider labelProvider = serv.getLabelProvider();
		final AbstractAxisProvider editedAxisProvider = editedAxisManager.getRepresentedContentProvider();
		final AbstractAxisProvider secondAxisProvider = secondAxisManager.getRepresentedContentProvider();
		final ReferenceSelector selector = new ReferenceSelector(true) {

			@Override
			public void createControls(final Composite parent) {
				super.createControls(parent);
				this.treeViewer.setComparator(new ViewerComparator(new StringComparator()));// should always be string element
			}
		};
		selector.setLabelProvider(labelProvider);

		final IStaticContentProvider provider = editedAxisManager.createPossibleAxisContentProvider(true);
		if (provider != null) {
			selector.setContentProvider(new FlattenableRestrictedFilteredContentProvider((IRestrictedContentProvider) provider, selector));
			final boolean canBeReorder = (!editedAxisManager.isDynamic()) && editedAxisManager.canMoveAxis();
			final DisplayedAxisSelectorDialog dialog = new DisplayedAxisSelectorDialog(Display.getDefault().getActiveShell(), selector, dialogTitle, true, canBeReorder, -1);
			final boolean displayCheckBox = editedAxisProvider instanceof ISlaveAxisProvider;
			dialog.setDisplayCheckBox(displayCheckBox);
			final boolean checkboxValue = secondAxisProvider instanceof IMasterAxisProvider && ((IMasterAxisProvider) secondAxisProvider).isDisconnectSlave();
			if (displayCheckBox) {
				dialog.setCheckBoxValues(checkBoxMessage, dialogCheckBoxTootip, checkboxValue);
			}

			dialog.setInformationDialogValues(Messages.NattableModelManager_DisconnectAxisManagerInformationDialogTitle, dialogQuestion);
			dialog.setLabelProvider(labelProvider);
			final List<Object> initialSelection = ((CompositeAxisManager) editedAxisManager).getAllManagedAxis(true);
			dialog.setInitialElementSelections(new ArrayList<>(initialSelection));

			final int open = dialog.open();
			if (open == Window.OK) {
				final Collection<Object> existingColumns = initialSelection;
				final ArrayList<Object> checkedColumns = new ArrayList<>();
				final List<Object> result = Arrays.asList(dialog.getResult());
				checkedColumns.addAll(result);

				final ArrayList<Object> columnsToAdd = new ArrayList<>(checkedColumns);
				columnsToAdd.removeAll(existingColumns);
				final CompoundCommand compoundCommand = new CompoundCommand("Update Existing Axis Command"); //$NON-NLS-1$
				if (columnsToAdd.size() > 0) {
					Command addAxisElementCommand = null;
					if (isEditingColumns) {
						addAxisElementCommand = getAddColumnElementCommand(columnsToAdd);
					} else {
						addAxisElementCommand = getAddRowElementCommand(columnsToAdd);
					}
					compoundCommand.append(addAxisElementCommand);
				}

				final ArrayList<Object> axisToDestroy = new ArrayList<>(existingColumns);
				axisToDestroy.removeAll(checkedColumns);
				if (axisToDestroy.size() > 0) {
					Command destroyAxisElementCommand = null;
					if (isEditingColumns) {
						destroyAxisElementCommand = getDestroyColumnElementCommand(axisToDestroy);
					} else {
						destroyAxisElementCommand = getDestroyRowElementCommand(axisToDestroy);
					}
					compoundCommand.append(destroyAxisElementCommand);
				}

				final boolean newState = dialog.isChecked();
				if (displayCheckBox && checkboxValue != newState) {
					final TransactionalEditingDomain domain = getTableEditingDomain();
					final IEditCommandRequest request = new SetRequest(domain, secondAxisProvider, NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_DisconnectSlave(), newState);
					final IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(secondAxisProvider);
					compoundCommand.append(new GMFtoEMFCommandWrapper(commandProvider.getEditCommand(request)));
				}

				if (canBeReorder) {
					final Command setOrderCommand = ((ICompositeAxisManager) editedAxisManager).getSetNewAxisOrderCommand(result);
					if (setOrderCommand != null) {
						compoundCommand.append(setOrderCommand);
					}
				}
				if (!compoundCommand.isEmpty()) {
					getTableEditingDomain().getCommandStack().execute(compoundCommand);
					updateToggleActionState();
				}
			}
		} else {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), Messages.NattableModelManager_CreateDestroyAxis, Messages.NattableModelManager_ActionNotYetSupported);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#openCreateDestroyRowsManagerDialog()
	 *
	 */
	@Override
	public void openCreateDestroyRowsManagerDialog() {
		openCreateDestroyAxisManagerDialog(false);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#openCreateDestroyColumnsManagerDialog()
	 *
	 */
	@Override
	public void openCreateDestroyColumnsManagerDialog() {
		openCreateDestroyAxisManagerDialog(true);
	}

	@Override
	public void sortColumnsByName(final boolean alphabeticOrder) {
		this.columnManager.sortAxisByName(alphabeticOrder, this.natTable.getConfigRegistry(), false);
	}


	@Override
	public void sortRowsByName(final boolean alphabeticOrder) {
		this.rowManager.sortAxisByName(alphabeticOrder, this.natTable.getConfigRegistry(), true);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getVerticalAxisProvider()
	 *
	 * @return
	 */
	@Override
	public AbstractAxisProvider getVerticalAxisProvider() {
		if (getTable().isInvertAxis()) {
			return getTable().getCurrentRowAxisProvider();
		} else {
			return getTable().getCurrentColumnAxisProvider();
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getHorizontalAxisProvider()
	 *
	 * @return
	 */
	@Override
	public AbstractAxisProvider getHorizontalAxisProvider() {
		if (getTable().isInvertAxis()) {
			return getTable().getCurrentColumnAxisProvider();
		} else {
			return getTable().getCurrentRowAxisProvider();
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#canEditColumnHeader(int)
	 *
	 * @param evaluationContext
	 * @return
	 */
	@Override
	public boolean canEditColumnHeader(final NatEventData evaluationContext) {
		return this.columnManager.canEditAxisHeader(evaluationContext);
	}

	/***
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#canEditRowHeader(org.eclipse.nebula.widgets.nattable.ui.NatEventData)
	 *
	 * @param eventData
	 * @return
	 */
	@Override
	public boolean canEditRowHeader(final NatEventData evaluationContext) {
		return this.rowManager.canEditAxisHeader(evaluationContext);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#openEditRowAliasDialog(org.eclipse.nebula.widgets.nattable.ui.NatEventData)
	 *
	 * @param event
	 */
	@Override
	public void openEditRowAliasDialog(final NatEventData event) {
		this.rowManager.openEditAxisAliasDialog(event, event.getNatTable().getRowIndexByPosition(event.getRowPosition()));
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#openEditColumnAliasDialog(org.eclipse.nebula.widgets.nattable.ui.NatEventData)
	 *
	 * @param event
	 */
	@Override
	public void openEditColumnAliasDialog(final NatEventData event) {
		this.columnManager.openEditAxisAliasDialog(event, event.getNatTable().getColumnIndexByPosition(event.getColumnPosition()));
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getColumnAxisManager()
	 *
	 * @return
	 */
	@Override
	public IAxisManager getColumnAxisManager() {
		return this.columnManager;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getRowAxisManager()
	 *
	 * @return
	 */
	@Override
	public IAxisManager getRowAxisManager() {
		return this.rowManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#setTableLabel(java.lang.String)
	 */
	@Override
	public void setTableLabel(final String label) {
		getTableEditingDomain().getCommandStack().execute(LabelInternationalization.getInstance().getSetTableLabelCommand(getTableEditingDomain(), getTable(), label, null));
	}

	@Override
	public void setTableName(final String name) {
		final SetRequest setNameRequest = new SetRequest(getTable(), NattableconfigurationPackage.eINSTANCE.getTableNamedElement_Name(), name);
		final IElementEditService editService = ElementEditServiceUtils.getCommandProvider(getTable());
		final ICommand setNameCommand = editService.getEditCommand(setNameRequest);
		getTableEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(setNameCommand));
	}


	@Override
	public String getTableName() {
		return getTable().getName();
	}


	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		if (adapter == NatTable.class) {
			return this.natTable;
		}
		return super.getAdapter(adapter);
	}

	private void updateCellMap(final Notification notification) {
		final Job job = new Job("Update cells") { //$NON-NLS-1$

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				// Check the existence of the cellsMap
				if (null != cellsMap) {
					if (notification == null) {
						cellsMap.clear();

						// Check the existence of the table and its cells
						if (null != getTable() && null != getTable().getCells()) {
							for (final Cell current : getTable().getCells()) {
								final CellMapKey key = createCellMapKeyWaitingCellAxis(current);
								cellsMap.put(key, current);
							}
						}
					} else {
						final int eventType = notification.getEventType();
						if (eventType == Notification.ADD) {
							final Object object = notification.getNewValue();
							if (object instanceof Cell) {
								final Cell cell = (Cell) object;
								final CellMapKey key = createCellMapKeyWaitingCellAxis(cell);
								cellsMap.put(key, cell);
							}
						} else if (eventType == Notification.ADD_MANY) {
							final Object coll = notification.getNewValue();

							if (coll instanceof Collection<?>) {
								final Iterator<?> iter = ((Collection<?>) coll).iterator();
								while (iter.hasNext()) {
									final Object object = iter.next();
									if (object instanceof Cell) {
										final Cell cell = (Cell) object;
										final CellMapKey key = createCellMapKeyWaitingCellAxis(cell);
										cellsMap.put(key, cell);
									}
								}
							}
						} else if (eventType == Notification.REMOVE) {
							final Object oldCell = notification.getOldValue();
							if (oldCell instanceof Cell) {
								final CellMapKey key = cellsMap.inverse().get(oldCell);
								cellsMap.remove(key);
							}
						} else if (eventType == Notification.REMOVE_MANY) {
							final Object coll = notification.getOldValue();

							if (coll instanceof Collection<?>) {
								final Iterator<?> iter = ((Collection<?>) coll).iterator();
								while (iter.hasNext()) {
									final Object object = iter.next();
									if (object instanceof Cell) {
										final CellMapKey key = cellsMap.inverse().get(object);
										cellsMap.remove(key);
									}
								}
							}
						}
					}
				}

				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		job.setSystem(true);
		job.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(final IJobChangeEvent event) {
				refreshNatTable();
			}

		});
		job.schedule();


		// resourceSetListener used to capture the event pertaining to the merge and resize actions
		// these events can be used to undo/redo previous actions
		resourceSetListener = new ResourceSetListener() {

			@Override
			public Command transactionAboutToCommit(final ResourceSetChangeEvent event) throws RollbackException {
				return null;
			}

			@Override
			public void resourceSetChanged(final ResourceSetChangeEvent event) {

				for (final Notification notification : event.getNotifications()) {
					// filter the events to only let through the changes on the current table resource
					final Table notifiedTable = findTable(notification);
					if (getTable().equals(notifiedTable)) {

						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								if (null != natTable && !natTable.isDisposed()) {
									final Object notifier = notification.getNotifier();
									final Object feature = notification.getFeature();
									final Object newValue = notification.getNewValue();
									final Object oldValue = notification.getOldValue();
									final int type = notification.getEventType();

									// ignore all from style about sort
									if (newValue instanceof StringValueStyle && ((StringValueStyle) newValue).getName().equals(NamedStyleConstants.SORT)) {
										return; // nothing to do
									}
									if (oldValue instanceof StringValueStyle && ((StringValueStyle) oldValue).getName().equals(NamedStyleConstants.SORT)) {
										return; // nothing to do
									}

									if (notifier instanceof IAxis && notification.getFeature() == NattablestylePackage.eINSTANCE.getStyledElement_Styles()) {
										// the change is on a style of an IAxis
										final String styleName;
										if (newValue instanceof NamedStyle) {
											styleName = ((NamedStyle) newValue).getName();
										} else if (oldValue instanceof NamedStyle) {
											styleName = ((NamedStyle) oldValue).getName();
										} else {
											styleName = null;
										}

										// we are working with a filter
										if (NamedStyleConstants.FILTER_SYSTEM_ID.equals(styleName)
												|| NamedStyleConstants.FILTER_VALUE_TO_MATCH.equals(styleName)) {
											// nothing to do
											return;
										}

									}

									// already created booleanValues and intValues
									if (notification.getNotifier() instanceof BooleanValueStyle) {
										// as the filter already prevented any nonBooleanValueStyle, and therefore any non EObject, it can be cast without verification
										final EObject mergeContainer = ((EObject) notification.getNotifier()).eContainer();
										if (mergeContainer instanceof AbstractHeaderAxisConfiguration || mergeContainer instanceof IAxis) {
											mergeTable();
										}
									}
									if (notification.getNotifier() instanceof IntValueStyle) {
										final EObject resizeContainer = ((EObject) notification.getNotifier()).eContainer();
										if (resizeContainer instanceof AbstractHeaderAxisConfiguration) {
											resizeHeader();
										}
										if (resizeContainer instanceof IAxis) {
											resizeAxis();
										}
									}
									// newly created booleanValues and intValues
									if (notification.getNewValue() instanceof BooleanValueStyle) {
										mergeTable();
									}
									if (notification.getNewValue() instanceof IntValueStyle) {
										if (notification.getNotifier() instanceof IAxis) {
											resizeAxis();
										}
										if (notification.getNotifier() instanceof AbstractHeaderAxisConfiguration) {
											resizeHeader();
										}
									}
									// reset to the default state using ctrl+z
									if (notification.getNewValue() == null && notification.getOldValue() != null) {
										mergeTable();
										resizeAxis();
										resizeHeader();
									}

									/*
									 * refresh when with show/hide the column for row header label (for example)
									 */
									if (notification.getNewValue() instanceof Boolean && notification.getNotifier() instanceof AbstractHeaderAxisConfiguration) {
										resizeHeader();
									}
								}
							}
						});
					}
				}
			}

			@Override
			public boolean isPrecommitOnly() {
				return false;
			}

			@Override
			public boolean isPostcommitOnly() {
				return false;
			}

			@Override
			public boolean isAggregatePrecommitListener() {
				return false;
			}

			@Override
			public NotificationFilter getFilter() {
				// this filter only lets through the notifications pertaining to the table
				// the first three conditions handle the modification, the add and the remove of styles
				// the last seven handle the modified or created objects containing those styles
				return ((NotificationFilter.createEventTypeFilter(Notification.SET))
						.or(NotificationFilter.createEventTypeFilter(Notification.ADD))
						.or(NotificationFilter.createEventTypeFilter(Notification.REMOVE)))
								.and((NotificationFilter.createNotifierTypeFilter(BooleanValueStyle.class))
										.or(NotificationFilter.createNotifierTypeFilter(IntValueStyle.class))
										.or(NotificationFilter.createNotifierTypeFilter(IAxis.class))
										.or(NotificationFilter.createNotifierTypeFilter(LocalTableHeaderAxisConfiguration.class))
										.or(NotificationFilter.createNotifierTypeFilter(Table.class)));
				// return NotificationFilter.createNotifierTypeFilter(EObject.class);
			}
		};

		getContextEditingDomain().addResourceSetListener(resourceSetListener);
		if (getTableEditingDomain() != null && getTableEditingDomain() != getContextEditingDomain()) {
			getTableEditingDomain().addResourceSetListener(resourceSetListener);
		}

	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getCell(java.lang.Object, java.lang.Object)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @return
	 */
	@Override
	public Cell getCell(final Object columnElement, final Object rowElement) {
		// required to support axis with no associated element
		if (AxisUtils.getRepresentedElement(columnElement) != null && AxisUtils.getRepresentedElement(rowElement) != null) {
			final CellMapKey key = new CellMapKey(columnElement, rowElement);
			return this.cellsMap.get(key);
		}
		return null;
	}

	/**
	 * This method allows to create a CellMapKey object waiting that required fields in the cell have been initialized
	 *
	 * @param cell
	 *            a cell
	 */
	private CellMapKey createCellMapKeyWaitingCellAxis(final Cell cell) {
		while (cell.getColumnWrapper() == null || cell.getRowWrapper() == null) {
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
				Activator.log.error(e);
			}
		}
		while (cell.getColumnWrapper().getElement() == null || cell.getRowWrapper().getElement() == null) {
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
				Activator.log.error(e);
			}
		}
		return new CellMapKey(cell.getColumnWrapper().getElement(), cell.getRowWrapper().getElement());
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getTablePreferenceStore()
	 *
	 * @return
	 */
	@Override
	public PreferenceStore getTablePreferenceStore() {
		return this.localPreferenceStore;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#setWorkspacePreferenceStore(org.eclipse.jface.preference.PreferenceStore)
	 *
	 * @param store
	 */
	@Override
	public void setWorkspacePreferenceStore(final PreferenceStore store) {
		this.localPreferenceStore = store;
	}


	// TODO : tmp class to externalize when the filter will works fine
	public class PapyrusColumnAccesor implements IColumnAccessor<Object> {

		/**
		 * @see org.eclipse.nebula.widgets.nattable.data.IColumnAccessor#getDataValue(java.lang.Object, int)
		 *
		 * @param rowObject
		 * @param columnIndex
		 * @return
		 */
		@Override
		public Object getDataValue(final Object rowObject, final int columnIndex) {
			return CellManagerFactory.INSTANCE.getCrossValue(getColumnElement(columnIndex), rowObject, NattableModelManager.this);
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.data.IColumnAccessor#setDataValue(java.lang.Object, int, java.lang.Object)
		 *
		 * @param rowObject
		 * @param columnIndex
		 * @param newValue
		 */
		@Override
		public void setDataValue(final Object rowObject, final int columnIndex, final Object newValue) {
			// nothing to do
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.data.IColumnAccessor#getColumnCount()
		 *
		 * @return
		 */
		@Override
		public int getColumnCount() {
			return 0;
		}

	}

	/**
	 * @return
	 *         the filter strategy to use
	 *
	 */
	@Override
	protected IFilterStrategy<Object> createFilterStrategy() {
		return new PapyrusFilterStrategy(this, new PapyrusColumnAccesor());
		// TODO
		// FIXME warning to invert axis
		// return new PapyrusFilterStrategy(this, this.horizontalFilterList, new PapyrusColumnAccesor(), null);
	}

	/**
	 *
	 * @return
	 *         a new decoration service listener
	 */
	private Observer getOrCreateDecorationServiceObserver() {
		if (this.decoractionServiceObserver == null) {
			this.decoractionServiceObserver = new Observer() {
				/**
				 *
				 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
				 *
				 * @param o
				 * @param arg
				 */
				@Override
				public void update(Observable o, Object arg) {
					refreshNatTable();
				}
			};
		}
		return this.decoractionServiceObserver;
	}

	/**
	 * Method to resize rows of the NatTable if the reinitialise row height attribute is <code>true</true>.
	 */
	protected void reinitialiseRowHeight() {
		IConfigRegistry configRegistry = this.natTable.getConfigRegistry();

		final Boolean isAutoResizeRowsEnabled = configRegistry.getConfigAttribute(NattableConfigAttributes.REINITIALISE_ROW_HEIGHT, DisplayMode.NORMAL);

		if (isAutoResizeRowsEnabled) {
			// Loop to resize all rows of the NatTable
			for (int rowIndex = 0; rowIndex < natTable.getRowCount(); rowIndex++) {
				natTable.doCommand(new InitializeAutoResizeRowsCommand(natTable, rowIndex, natTable.getConfigRegistry(), new GCFactory(natTable)));
			}

			// Once it is done, reset the resize row height flag to false
			configRegistry.registerConfigAttribute(NattableConfigAttributes.REINITIALISE_ROW_HEIGHT, false);
		}
	}
}
