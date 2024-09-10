/*****************************************************************************
 * Copyright (c) 2015, 2016, 2020 CEA LIST and others.
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
 *   Dirk Fauth <dirk.fauth@googlemail.com> - Bug 488234
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 508175
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 560318, 563172, 564248
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.command.MultiColumnHideCommand;
import org.eclipse.nebula.widgets.nattable.hideshow.command.MultiColumnShowCommand;
import org.eclipse.nebula.widgets.nattable.hideshow.command.ShowAllColumnsCommand;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.painter.IOverlayPainter;
import org.eclipse.nebula.widgets.nattable.painter.layer.CellLayerPainter;
import org.eclipse.nebula.widgets.nattable.resize.action.VerticalResizeCursorAction;
import org.eclipse.nebula.widgets.nattable.util.ClientAreaAdapter;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.nebula.widgets.nattable.viewport.SliderScroller;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.clientarea.ClientAreaResizeDragMode;
import org.eclipse.papyrus.infra.nattable.clientarea.ClientAreaResizeMatcher;
import org.eclipse.papyrus.infra.nattable.command.CommandIds;
import org.eclipse.papyrus.infra.nattable.configuration.TreeTableClickSortConfiguration;
import org.eclipse.papyrus.infra.nattable.configuration.TreeTablePopupMenuConfiguration;
import org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack;
import org.eclipse.papyrus.infra.nattable.layerstack.RowHeaderHierarchicalLayerStack;
import org.eclipse.papyrus.infra.nattable.layerstack.RowHeaderLayerStack;
import org.eclipse.papyrus.infra.nattable.listener.HideShowCategoriesTableListener;
import org.eclipse.papyrus.infra.nattable.manager.axis.AxisManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.axis.CompositeAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.axis.CompositeTreeAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.axis.ICompositeAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.axis.ITreeItemAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.DisplayStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.selection.ISelectionExtractor;
import org.eclipse.papyrus.infra.nattable.selection.ObjectsSelectionExtractor;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.tree.DatumExpansionModel;
import org.eclipse.papyrus.infra.nattable.tree.DatumTreeFormat;
import org.eclipse.papyrus.infra.nattable.utils.CollapseExpandActionHelper;
import org.eclipse.papyrus.infra.nattable.utils.DefaultSizeUtils;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.commands.ICommandService;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TreeList;
import ca.odell.glazedlists.TreeList.Format;

/**
 * This class allows to manage Papyrus Tree Table
 *
 */
public class TreeNattableModelManager extends NattableModelManager implements ITreeNattableModelManager {

	/**
	 * the event list used to build the tree list
	 */
	// protected EventList<Object> eventList;

	/**
	 * the managed tree list
	 */
	protected TreeList treeList;

	protected DatumTreeFormat treeFormat;

	protected DatumExpansionModel expansionModel;

	/**
	 * the height of the scrollbar
	 */
	private static final int scrollbarHeight = 17;


	/**
	 * the listener used to refresh the update the hidden rows
	 */
	protected ResourceSetListener hideShowCategoriesListener;

	/**
	 * Constructor.
	 *
	 * @param rawModel
	 *            The table model.
	 * @param selectionExtractor
	 *            The selection extrator.
	 */
	public TreeNattableModelManager(Table rawModel, ISelectionExtractor selectionExtractor) {
		this(rawModel, selectionExtractor, true);
	}

	/**
	 * Constructor.
	 *
	 * @param rawModel
	 *            The table model.
	 * @param selectionExtractor
	 *            The selection extrator.
	 * @param initializeListeners
	 *            Boolean to determinate if the listeners have to be initialized or not (example: properties view doesn't it).
	 * @since 3.0
	 */
	public TreeNattableModelManager(final Table rawModel, final ISelectionExtractor selectionExtractor, final boolean initializeListeners) {
		super(rawModel, selectionExtractor, initializeListeners);
		Assert.isTrue(TableHelper.isTreeTable(rawModel));

		// Manage the change axis provider adapter only for the column (row cannot manage refresh)
		rawModel.eAdapters().remove(changeAxisProvider);
		changeAxisProvider = new AdapterImpl() {

			@Override
			public void notifyChanged(final Notification msg) {
				if (NattablePackage.eINSTANCE.getTable_CurrentColumnAxisProvider() == msg.getFeature()) {
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
		rawModel.eAdapters().add(changeAxisProvider);
	}

	/**
	 * Constructor.
	 *
	 * @param rawModel
	 */
	public TreeNattableModelManager(Table rawModel) {
		this(rawModel, new ObjectsSelectionExtractor());
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager#registerPopupMenuConfiguration(org.eclipse.nebula.widgets.nattable.NatTable)
	 *
	 * @param natTable
	 */
	@Override
	protected void registerPopupMenuConfiguration(final NatTable natTable) {
		natTable.addConfiguration(new TreeTablePopupMenuConfiguration(natTable));
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#createHorizontalElementList()
	 *
	 * @return
	 */
	@Override
	protected List<Object> createHorizontalElementList() {
		// super.createHorizontalElementList();
		EventList<Object> eventList = GlazedLists.eventList(new ArrayList<>());
		eventList = GlazedLists.threadSafeList(eventList);
		this.basicHorizontalList = eventList;
		// must be created before the row sort model
		this.rowSortedList = new SortedList<>(this.basicHorizontalList, null);
		treeFormat = new DatumTreeFormat(getRowSortModel());
		this.expansionModel = new DatumExpansionModel();

		this.horizontalFilterList = new FilterList<>(this.rowSortedList);
		this.treeList = new TreeList(this.horizontalFilterList, treeFormat, expansionModel);

		return this.treeList;
	}

	@Override
	public TreeList getTreeList() {
		return this.treeList;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#createNattable(org.eclipse.swt.widgets.Composite, int, org.eclipse.ui.IWorkbenchPartSite)
	 *
	 * @param parent
	 * @param style
	 * @param site
	 * @return
	 */
	@Override
	public NatTable createNattable(Composite parent, int style, IWorkbenchPartSite site) {
		// Wrap NatTable in composite so we can slap on the external horizontal
		// sliders
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		composite.setLayout(gridLayout);

		NatTable nattable = super.createNattable(composite, style, site);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		natTable.setLayoutData(gridData);

		// MULTI-VIEWPORT-CONFIGURATION
		createSplitSliders(composite, getRowHeaderLayerStack().getViewportLayer(), getBodyLayerStack().getViewportLayer());

		// as the CompositeLayer is setting a IClientAreaProvider for the
		// composition we need to set a special ClientAreaAdapter after the
		// creation of the CompositeLayer to support split viewports
		int leftWidth = getWidthSliderComposite();
		ClientAreaAdapter leftClientAreaAdapter = new ClientAreaAdapter(getRowHeaderLayerStack().getViewportLayer().getClientAreaProvider());
		leftClientAreaAdapter.setWidth(leftWidth);
		getRowHeaderLayerStack().getViewportLayer().setClientAreaProvider(leftClientAreaAdapter);

		getRowHeaderLayerStack().getViewportLayer().setVerticalScrollbarEnabled(false);

		filterColumnHeaderComposite.setLayerPainter(new CellLayerPainter(true, false));

		// add an IOverlayPainter to render the split viewport border
		natTable.addOverlayPainter(new IOverlayPainter() {

			@Override
			public void paintOverlay(GC gc, ILayer layer) {
				Color beforeColor = gc.getForeground();
				gc.setForeground(GUIHelper.COLOR_GRAY);
				int viewportBorderX = getRowHeaderLayerStack().getWidth() - 1;
				gc.drawLine(viewportBorderX, 0, viewportBorderX, layer.getHeight() - 1);
				gc.setForeground(beforeColor);
			}
		});

		// Mouse move - Show resize cursor
		natTable.getUiBindingRegistry().registerFirstMouseMoveBinding(
				new ClientAreaResizeMatcher(getRowHeaderLayerStack()),
				new VerticalResizeCursorAction());

		natTable.getUiBindingRegistry().registerFirstMouseDragMode(
				new ClientAreaResizeMatcher(getRowHeaderLayerStack()),
				new ClientAreaResizeDragMode(
						getRowHeaderLayerStack().getIndexRowHeaderLayer(),
						getRowHeaderLayerStack().getTreeLayer(),
						leftClientAreaAdapter,
						getRowHeaderLayerStack().getViewportLayer(),
						getBodyLayerStack().getViewportLayer()));

		// update the hidden categories

		List<Integer> hiddenDepth = StyleUtils.getHiddenDepths(this);

		// we always need to call this method, because we use it as hack to register
		// action on row header (bug 563172), it is used by the example examples/uml/org.eclipse.papyrus.example.uml.nattable.empty.line, showing categories in the table configuration
		hideShowCategories(hiddenDepth, null);

		this.hideShowCategoriesListener = new HideShowCategoriesTableListener(this);
		if (null != getTableEditingDomain()) {
			getTableEditingDomain().addResourceSetListener(this.hideShowCategoriesListener);
		}

		// FIX for the bug 469284: [Tree Table] empty table when a table is loaded with applied filters
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=469284
		if (StyleUtils.hasAppliedFilter(this)) {
			doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		}
		Table table = getTable();
		BooleanValueStyle expandAll = (BooleanValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.EXPAND_ALL);
		if (expandAll == null) {
			TableConfiguration config = table.getTableConfiguration();
			expandAll = (BooleanValueStyle) config.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.EXPAND_ALL);
		}
		if (null != expandAll) {
			if (expandAll.isBooleanValue()) {
				doCollapseExpandAction(org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum.EXPAND_ALL, null);
			}
		}
		return nattable;
	}

	/**
	 * Get the width of the slider composite.
	 *
	 * @return The int value corresponding to the needed row header width.
	 * @since 2.0
	 * @deprecated since 6.1. Function {@link #getWidthOfSliderComposite()} must be used instead.
	 */
	@Deprecated
	protected int getWidthSliderComposite() {
		return getWidthOfSliderComposite();
	}

	/**
	 * Get the width of the slider composite.
	 *
	 * @return The int value corresponding to the needed row header width.
	 * @since 6.1
	 */
	public int getWidthOfSliderComposite() {
		int result = 0;
		AbstractHeaderAxisConfiguration configuration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisConfigurationUsedInTable(getTable());
		if (configuration.isDisplayLabel()) {
			final IntValueStyle valueRowHeader = (IntValueStyle) getTable().getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_HEADER_WIDTH);
			if (null != valueRowHeader && valueRowHeader.getIntValue() != 0) {
				result = valueRowHeader.getIntValue();
			} else {
				result = calculateBestWidthSliderComposite();
			}
		}
		return result;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#resizeHeader()
	 *
	 */
	@Override
	public void resizeHeader() {
		ClientAreaAdapter clientAreaAdapter = new ClientAreaAdapter(getRowHeaderLayerStack().getViewportLayer().getClientAreaProvider());
		clientAreaAdapter.setWidth(getWidthOfSliderComposite());
		getRowHeaderLayerStack().getViewportLayer().setClientAreaProvider(clientAreaAdapter);
		super.resizeHeader();
		refreshNatTable();
	}

	/**
	 * This allows to calculate the initial width of the row header.
	 *
	 * @return The int value corresponding to the needed row header width.
	 * @since 2.0
	 */
	protected int calculateBestWidthSliderComposite() {

		// If non namedStyle exists for the slider composite, the initial width of the table must be an addition of:
		// - The size of the two first columns
		// - The half size of the 3rd column

		int result = 0;
		AbstractHeaderAxisConfiguration configuration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisConfigurationUsedInTable(getTable());
		if (configuration.isDisplayLabel()) {
			final AbstractHeaderAxisConfiguration rowHeader = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisConfigurationUsedInTable(getTable());

			// Get the size of the two first columns
			final IntValueStyle valueFirstRow = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_LABEL_WIDTH);
			if (null != valueFirstRow) {
				result += valueFirstRow.getIntValue();
			} else {
				result += DefaultSizeUtils.getDefaultRowHeaderWidth();
			}
			final IntValueStyle valueSecondRow = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_LABEL_POSITION_PREFIX_WIDTH + "2" + NamedStyleConstants.ROW_LABEL_POSITION_SUFFIX_WIDTH); //$NON-NLS-1$
			if (null != valueSecondRow) {
				result += valueSecondRow.getIntValue();
			} else {
				result += DefaultSizeUtils.getDefaultRowHeaderWidth();
			}

			// Get the half size of the 3rd column
			final IntValueStyle valueThirdRow = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_LABEL_POSITION_PREFIX_WIDTH + "3" + NamedStyleConstants.ROW_LABEL_POSITION_SUFFIX_WIDTH); //$NON-NLS-1$
			if (null != valueThirdRow) {
				if (1 == valueThirdRow.getIntValue() % 2) {
					result += (valueThirdRow.getIntValue() / 2) + 1;
				} else {
					result += valueThirdRow.getIntValue() / 2;
				}
			} else {
				if (1 == DefaultSizeUtils.getDefaultRowHeaderWidth() % 2) {
					result += (DefaultSizeUtils.getDefaultRowHeaderWidth() / 2) + 1;
				} else {
					result += DefaultSizeUtils.getDefaultRowHeaderWidth() / 2;
				}
			}
		}
		return result;
	}

	private void createSplitSliders(Composite natTableParent, final ViewportLayer left, final ViewportLayer right) {
		Composite sliderComposite = new Composite(natTableParent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.heightHint = scrollbarHeight;
		sliderComposite.setLayoutData(gridData);

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		sliderComposite.setLayout(gridLayout);

		// Slider Left
		// Need a composite here to set preferred size because Slider can't be
		// subclassed.
		Composite sliderLeftComposite = new Composite(sliderComposite, SWT.NONE) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				int width = ((ClientAreaAdapter) left.getClientAreaProvider()).getWidth();
				if (null != getRowHeaderLayerStack() && null != getRowHeaderLayerStack().getIndexRowHeaderLayer()) {
					width += getRowHeaderLayerStack().getIndexRowHeaderLayer().getWidth();
				}
				return new Point(width, scrollbarHeight);
			}
		};
		sliderLeftComposite.setLayout(new FillLayout());
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = GridData.BEGINNING;
		sliderLeftComposite.setLayoutData(gridData);

		addControlListener(sliderLeftComposite);

		Slider sliderLeft = new Slider(sliderLeftComposite, SWT.HORIZONTAL);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		sliderLeft.setLayoutData(gridData);

		left.setHorizontalScroller(new SliderScroller(sliderLeft));

		// Slider Right
		Slider sliderRight = new Slider(sliderComposite, SWT.HORIZONTAL);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		sliderRight.setLayoutData(gridData);

		right.setHorizontalScroller(new SliderScroller(sliderRight));
	}

	/**
	 * This allows to create the control listener when the slider composite is resized.
	 *
	 * @param leftSliderComposite
	 *            the slider composite.
	 * @since 2.0
	 */
	protected void addControlListener(final Composite leftSliderComposite) {
		leftSliderComposite.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(final ControlEvent e) {
				if (null == natTable || natTable.isDisposed()) {
					return;
				}
				super.controlResized(e);

				final CompositeCommand resizeRowHeaderCommand = new CompositeCommand("Resize Slider composite"); //$NON-NLS-1$
				TransactionalEditingDomain tableDomain = TableEditingDomainUtils.getTableEditingDomain(getTable());
				if (null == tableDomain) {
					return;
				}

				final int newHeaderWidth = getRowHeaderLayerStack().getViewportLayer().getClientAreaWidth();
				final int initialSize = calculateBestWidthSliderComposite();

				// check that the modified value is not the initial one
				if (newHeaderWidth != initialSize) {
					IntValueStyle valueIndex = (IntValueStyle) getTable().getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_HEADER_WIDTH);
					if (null != valueIndex && valueIndex.getIntValue() != newHeaderWidth) {
						SetRequest resizeRowHeader = new SetRequest(tableDomain, valueIndex, NattablestylePackage.eINSTANCE.getIntValueStyle_IntValue(), newHeaderWidth);
						resizeRowHeaderCommand.add(new SetValueCommand(resizeRowHeader));
					} else if (null == valueIndex && newHeaderWidth != DefaultSizeUtils.getDefaultRowHeaderWidth()) {
						valueIndex = NattablestyleFactory.eINSTANCE.createIntValueStyle();
						valueIndex.setIntValue(newHeaderWidth);
						valueIndex.setName(NamedStyleConstants.ROW_HEADER_WIDTH);

						SetRequest initRowHeaderSizeRequest = new SetRequest(tableDomain, getTable(), NattablestylePackage.eINSTANCE.getStyledElement_Styles(), valueIndex);
						resizeRowHeaderCommand.add(new SetValueCommand(initRowHeaderSizeRequest));
					}

					if (resizeRowHeaderCommand.canExecute() && !resizeRowHeaderCommand.isEmpty()) {
						tableDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(resizeRowHeaderCommand));
					}
				} else {
					// Remove the named style if existing and with the initial width
					final IntValueStyle valueIndex = (IntValueStyle) getTable().getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_HEADER_WIDTH);
					if (null != valueIndex) {
						DestroyElementRequest destroyRowHeaderWidthRequest = new DestroyElementRequest(tableDomain, valueIndex, false);
						new GMFtoEMFCommandWrapper(new DestroyElementCommand(destroyRowHeaderWidthRequest)).execute();
					}
				}
			}
		});
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager#addClickSortConfiguration(org.eclipse.nebula.widgets.nattable.NatTable)
	 *
	 * @param natTable
	 */
	@Override
	protected void addClickSortConfiguration(NatTable natTable) {
		natTable.addConfiguration(new TreeTableClickSortConfiguration());
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#updateToggleActionState()
	 *
	 */
	@Override
	protected void updateToggleActionState() {
		super.updateToggleActionState();
		// update hierarchic table display style
		DisplayStyle style = TableHelper.getTableDisplayStyle(this);
		final ICommandService commandService = EclipseCommandUtils.getCommandService();
		if (commandService != null) {

			Command command = commandService.getCommand(CommandIds.COMMAND_HIERARCHIC_DISPLAY_STYLE);
			if (!DisplayStyle.NORMAL.equals(style)) {
				updateRadioCommandState(command, style.getLiteral());
			}
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#createAxisManager(java.util.List, org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider, boolean)
	 *
	 * @param representations
	 * @param contentProvider
	 * @param columnAxisManager
	 * @return
	 */
	@Override
	protected ICompositeAxisManager createAxisManager(List<AxisManagerRepresentation> representations, AbstractAxisProvider contentProvider, boolean columnAxisManager) {
		if (columnAxisManager) {
			return super.createAxisManager(representations, contentProvider, columnAxisManager);
		} else {
			return createTreeAxisManager(representations, contentProvider, columnAxisManager);
		}

	}

	/**
	 *
	 * @param representations
	 * @param contentProvider
	 * @param columnAxisManager
	 * @return
	 *         the Composite axis manager used to manage Tree
	 * @since 3.0
	 */
	protected ICompositeAxisManager createTreeAxisManager(List<AxisManagerRepresentation> representations, AbstractAxisProvider contentProvider, boolean columnAxisManager) {
		CompositeTreeAxisManagerForEventList compositeAxisManager = new CompositeTreeAxisManagerForEventList(horizontalFilterList);
		final List<IAxisManagerForEventList> managers = new ArrayList<>();
		for (AxisManagerRepresentation current : representations) {
			final IAxisManager manager = AxisManagerFactory.INSTANCE.getAxisManager(current);
			Assert.isTrue(manager instanceof IAxisManagerForEventList);
			manager.init(this, current, contentProvider);
			managers.add((IAxisManagerForEventList) manager);

		}
		compositeAxisManager.init(this, null, contentProvider);
		DatumTreeFormat treeFormat = getTreeFormat();
		treeFormat.setTreeComparatorProvider(compositeAxisManager);
		this.expansionModel.setAxisManager(compositeAxisManager);
		((CompositeAxisManagerForEventList) compositeAxisManager).setSubManagers(managers);
		return compositeAxisManager;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager#getTreeFormat()
	 *
	 * @return
	 */
	@Override
	public DatumTreeFormat getTreeFormat() {
		return this.treeFormat;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager#getDepth()
	 *
	 * @return
	 */
	@Override
	public int getTreeItemDepth(final ITreeItemAxis axis) {
		Format<ITreeItemAxis> format = getTreeFormat();
		if (format != null) {
			List<ITreeItemAxis> path = new ArrayList<>();
			format.getPath(path, axis);
			return path.size() - 1;
		}
		return 0;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager#getSemanticDepth(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis)
	 *
	 * @param axis
	 * @return
	 */
	@Override
	public int getSemanticDepth(ITreeItemAxis axis) {
		Object representedObject = axis.getElement();
		if (representedObject instanceof TreeFillingConfiguration) {
			return ((TreeFillingConfiguration) representedObject).getDepth();
		} else {
			ITreeItemAxis parent = axis.getParent();
			if (parent == null) {
				return 0;
			}
			representedObject = parent.getElement();
			Assert.isTrue(representedObject instanceof TreeFillingConfiguration);
			return ((TreeFillingConfiguration) representedObject).getDepth();
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager#hideShowCategories(java.util.List, java.util.List)
	 *
	 * @param depthToHide
	 * @param depthToShow
	 */
	@Override
	public void hideShowCategories(List<Integer> depthToHide, List<Integer> depthToShow) {
		hideShowRowCategories(depthToHide, depthToShow);
		hideShowColumnCategoriesInRowHeader(depthToHide, depthToShow);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager#hideShowRowCategories(java.util.List, java.util.List)
	 *
	 * @param depthToHide
	 * @param depthToShow
	 */
	@Override
	public void hideShowRowCategories(List<Integer> depthToHide, List<Integer> depthToShow) {
		// we hide the rows representing the categories
		((ITreeItemAxisManagerForEventList) getRowAxisManager()).managedHideShowCategoriesForDepth(depthToHide, depthToShow);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager#hideShowColumnCategoriesInRowHeader(java.util.List, java.util.List)
	 *
	 * @param depthToHide
	 * @param depthToShow
	 */
	@Override
	public void hideShowColumnCategoriesInRowHeader(List<Integer> depthToHide, List<Integer> depthToShow) {
		RowHeaderLayerStack rowHeaderLayerStack = getRowHeaderLayerStack();
		if (rowHeaderLayerStack instanceof RowHeaderHierarchicalLayerStack) {
			ColumnHideShowLayer layer = ((RowHeaderHierarchicalLayerStack) rowHeaderLayerStack).getRowHeaderColumnHideShowLayer();
			ILayer subLayer = layer.getUnderlyingLayerByPosition(0, 0);
			// we hide the columns representing the categories
			if (TableHelper.isMultiColumnTreeTable(this)) {
				boolean hasRootConfif = FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(getTable(), 0);

				if (depthToHide != null && depthToHide.size() > 0) {
					int[] indexToHide = new int[depthToHide.size()];
					for (int i = 0; i < depthToHide.size(); i++) {
						Integer curr = depthToHide.get(i);
						int tmp = -1;
						if (hasRootConfif) {
							tmp = curr * 2;
						} else {
							// TODO not yet tested
							tmp = curr * 2 - 1;
						}

						// we need to convert the position
						tmp = layer.underlyingToLocalColumnPosition(subLayer, tmp);
						indexToHide[i] = tmp;
					}
					layer.doCommand(new MultiColumnHideCommand(layer, indexToHide));
				}

				if (depthToShow != null && depthToShow.size() > 0) {

					List<Integer> indexToShow = new ArrayList<>();
					for (int i = 0; i < depthToShow.size(); i++) {
						Integer curr = depthToShow.get(i);
						int tmp = -1;
						if (hasRootConfif) {
							tmp = curr * 2;
						} else {
							// TODO not yet tested
							tmp = curr * 2 - 1;
						}
						indexToShow.add(tmp);
					}
					layer.doCommand(new MultiColumnShowCommand(indexToShow));
				}
			} else {
				layer.doCommand(new ShowAllColumnsCommand());
			}
		}
	}

	/**
	 *
	 * @param bodyLayerStack
	 *            the body layer stack to use
	 *
	 * @return
	 *         the row header layer stack to use
	 */
	@Override
	protected RowHeaderLayerStack createRowHeaderLayerStack(BodyLayerStack bodyLayerStack) {
		return new RowHeaderHierarchicalLayerStack(bodyLayerStack, this);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public RowHeaderHierarchicalLayerStack getRowHeaderLayerStack() {
		return (RowHeaderHierarchicalLayerStack) super.getRowHeaderLayerStack();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager#doCollapseExpandAction(org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum, java.util.List)
	 *
	 * @param actionId
	 * @param selectedAxis
	 */
	@Override
	public void doCollapseExpandAction(CollapseAndExpandActionsEnum actionId, List<ITreeItemAxis> selectedAxis) {
		CollapseExpandActionHelper.doCollapseExpandAction(actionId, selectedAxis, getTableAxisElementProvider(), this.natTable);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#createInvertAxisListener()
	 *
	 * @return
	 */
	@Override
	protected Adapter createInvertAxisListener() {
		return null;
	}

	/**
	 * Modify the axis when it is disposed.
	 *
	 * @param iAxis
	 *            The list of axis.
	 */
	private void modifyAxisDeliver(final List<IAxis> iAxis) {
		for (IAxis axis : iAxis) {
			if (axis instanceof ITreeItemAxis) {
				boolean isDelivering = axis.eDeliver();
				if (isDelivering) {
					// I suppose than it is not necessary to send notification here
					axis.eSetDeliver(false);
				}
				((ITreeItemAxis) axis).getChildren().clear();
				if (isDelivering) {
					// I reset the initial value, because the model can be reopened
					axis.eSetDeliver(true);
				}
			}
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#dispose()
	 *
	 */
	@Override
	public void dispose() {
		if (getTableEditingDomain() != null && this.hideShowCategoriesListener != null) {
			getTableEditingDomain().removeResourceSetListener(this.hideShowCategoriesListener);
			this.hideShowCategoriesListener = null;
		}
		final List<IAxis> iAxis;
		if (null == getHorizontalAxisProvider()) {
			iAxis = null;
		} else {
			iAxis = getHorizontalAxisProvider().getAxis();
		}

		if (iAxis != null && !iAxis.isEmpty()) { // see bug 467706: [Table 2] Tree Table with no tree filling configuration on depth==0 can't be reopened
			// we need to remove the children which are not serialized from the root of the table, to be able to reopen
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					modifyAxisDeliver(iAxis);
				}
			};
			try {
				if (null != getTableEditingDomain()) {
					GMFUnsafe.write(getTableEditingDomain(), runnable);
				} else {
					modifyAxisDeliver(iAxis);
				}
			} catch (InterruptedException e) {
				Activator.log.error(e);
			} catch (RollbackException e) {
				Activator.log.error(e);
			}

		}
		if (getTableEditingDomain() != null) {
			if (this.hideShowCategoriesListener != null) {
				getTableEditingDomain().removeResourceSetListener(this.hideShowCategoriesListener);
			}
		}
		super.dispose();
	}
}

