/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 573429
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.internal;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.eclipse.papyrus.infra.editor.welcome.internal.WelcomeModelManager.become;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.TransactionHelper;
import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.SashRow;
import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeFactory;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeSection;
import org.eclipse.papyrus.infra.editor.welcome.internal.dnd.WelcomeSectionTransfer;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * @author damus
 *
 */
class WelcomeLayout {
	private static final int NUM_ROWS = 2;

	private static final int SASH_OFFEST = 5;
	private static final int SASH_MARGIN = 50;

	private static final Function<Control, Control> textLabelAccessor = createTextLabelAccessor();

	private final int rowCount = NUM_ROWS;

	private final Composite parent;
	private final FormToolkit toolkit;
	private final IWelcomePageService welcomeService;
	private final AtomicReference<DeferredLayoutUpdate> deferredLayoutUpdate = new AtomicReference<>();

	private Map<Tab, Composite> tabControls = new HashMap<>();

	private List<WelcomeTab> tabs;
	private Map<WelcomeTab, Composite> sections;
	private Sash[][] rowSashes;
	private Sash[] columnSashes;

	/**
	 * Constructor.
	 *
	 */
	WelcomeLayout(Composite parent, FormToolkit toolkit, IWelcomePageService welcomeService) {
		super();

		this.parent = parent;
		this.toolkit = toolkit;
		this.welcomeService = welcomeService;
	}

	public void dispose() {
		tabControls.values().forEach(body -> {
			if (!body.isDisposed()) {
				// The tab controls are the bodies of expandable composites
				body.getParent().dispose();
			}
		});
		tabControls.clear();

		if (rowSashes != null) {
			Stream.of(rowSashes).flatMap(Stream::of)
					.filter(Objects::nonNull)
					.filter(((Predicate<Sash>) Sash::isDisposed).negate())
					.forEach(Sash::dispose);
			rowSashes = null;
		}
		if (columnSashes != null) {
			Stream.of(columnSashes)
					.filter(((Predicate<Sash>) Sash::isDisposed).negate())
					.forEach(Sash::dispose);
			columnSashes = null;
		}

		tabs = null;
		sections = null;
	}

	void createTabs(List<WelcomeTab> tabs) {
		// First, sort the tabs into stock and/or user-customized order
		sort(tabs);
		this.tabs = tabs;

		// Then, create the sashes that will constrain our sections
		createSashes();

		// And, finally, create the tab sections, themselves
		createSections();
	}

	private void sort(List<WelcomeTab> tabs) {
		// Do we have a user-defined sort order?
		Welcome welcome = welcomeService.getWelcome();
		if (welcome.getWelcomePage() != null) {
			Collections.sort(tabs, userDefinedOrdering(welcome.getWelcomePage()));
		} else {
			Collections.sort(tabs);
		}
	}

	private Comparator<WelcomeTab> userDefinedOrdering(WelcomePage welcomePage) {
		return (tab1, tab2) -> {
			int result;

			WelcomeSection section1 = welcomePage.getSection(tab1.getID());
			WelcomeSection section2 = welcomePage.getSection(tab2.getID());
			int index1 = welcomePage.getSections().indexOf(section1);
			int index2 = welcomePage.getSections().indexOf(section2);

			if ((index1 >= 0) && (index2 >= 0)) {
				// There is a user-defined ordering for these
				result = index1 - index2;
			} else {
				// We have only the intrinsic ordering
				result = tab1.compareTo(tab2);
			}

			return result;
		};
	}

	private void createSashes() {
		final int rows = this.rowCount;
		final int columns = (tabs.size() + (tabs.size() % rows)) / rows;
		final int lastTab = tabs.size() - 1;

		if ((rows == 0) || (columns == 0)) {
			return; // Nothing to present
		}

		columnSashes = new Sash[columns - 1]; // Sashes between columns
		rowSashes = new Sash[columns][rows - 1]; // Sashes between rows

		int orientation = SWT.HORIZONTAL;
		int colIdx = 0;
		int rowIdx = 0;

		// There is no sash after the last section tab
		for (int i = 0; i < lastTab; i++) {
			Sash sash = new Sash(parent, orientation);
			sash.setBackground(parent.getBackground());

			switch (orientation) {
			case SWT.HORIZONTAL:
				sash.setData(new Point(colIdx, rowIdx)); // Which cell is above this sash?
				rowSashes[colIdx][rowIdx++] = sash;
				break;
			case SWT.VERTICAL:
				rowIdx = 0;
				sash.setData(new Point(colIdx, rowIdx)); // Which cell is left of this sash?
				columnSashes[colIdx++] = sash;
				break;
			}

			sash.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Point cell = (Point) sash.getData();
					if ((sash.getStyle() & SWT.VERTICAL) != 0) {
						// Constrain to neghbouring sashes or form edges
						Rectangle constraintBounds = (cell.x == 0) ? null : columnSashes[cell.x - 1].getBounds();
						int minX = (constraintBounds == null) ? SASH_MARGIN : constraintBounds.x + constraintBounds.width + SASH_MARGIN;
						constraintBounds = (cell.x >= (columnSashes.length - 1)) ? null : columnSashes[cell.x].getBounds();
						int maxX = (constraintBounds == null) ? parent.getBounds().width - SASH_MARGIN : constraintBounds.x - SASH_MARGIN;
						int newX = min(max(e.x, minX), maxX);
						e.x = newX;
						((FormData) sash.getLayoutData()).left = new FormAttachment(0, newX - SASH_OFFEST);
					} else {
						// Constrain to neghbouring sashes or form edges
						Rectangle constraintBounds = (cell.y == 0) ? null : rowSashes[cell.x][cell.y - 1].getBounds();
						int minY = (constraintBounds == null) ? SASH_MARGIN : constraintBounds.y + constraintBounds.height + SASH_MARGIN;
						constraintBounds = ((cell.y >= (rowSashes[cell.x].length - 1)) || (rowSashes[cell.x][cell.y] == null)) ? null : rowSashes[cell.x][cell.y].getBounds();
						int maxY = (constraintBounds == null) ? parent.getBounds().height - SASH_MARGIN : constraintBounds.y - SASH_MARGIN;
						int newY = min(max(e.y, minY), maxY);
						e.y = newY;
						((FormData) sash.getLayoutData()).top = new FormAttachment(0, newY - SASH_OFFEST);
					}

					parent.layout();
					deferredUpdateWelcomeLayoutModel();
				}
			});

			// Is the next a row or a column sash?
			orientation = (((rowIdx + 1) % rows) == 0)
					? SWT.VERTICAL // A new column
					: SWT.HORIZONTAL; // Another row in the same column
		}

		layoutSashes();
	}

	private void layoutSashes() {
		final int rows = this.rowCount;
		final int columns = Math.max(2, (tabs.size() + (tabs.size() % rows)) / rows);

		Welcome welcome = welcomeService.getWelcome();
		WelcomePage pageModel = welcome.getWelcomePage();

		for (int i = 0; i < columnSashes.length; i++) {
			Sash column = columnSashes[i];
			FormData fd = new FormData();

			SashColumn columnModel = (pageModel == null) ? null : pageModel.getSashColumn(i);

			fd.top = new FormAttachment(0);
			fd.bottom = new FormAttachment(100);

			if ((columnModel != null) && columnModel.isSetX()) {
				fd.left = new FormAttachment(0, columnModel.getX());
			} else {
				fd.left = new FormAttachment((100 * (i + 1)) / columns, -SASH_OFFEST);
			}

			column.setLayoutData(fd);

			// Lay out the rows in this column
			Sash[] rowSashes = this.rowSashes[i];
			for (int j = 0; j < rowSashes.length; j++) {
				Sash row = rowSashes[j];
				if (row != null) {
					fd = new FormData();

					SashRow rowModel = (columnModel == null) ? null : columnModel.getSashRow(j);

					if ((rowModel != null) && rowModel.isSetY()) {
						fd.top = new FormAttachment(0, rowModel.getY());
					} else {
						fd.top = new FormAttachment((100 * (j + 1)) / rows, -SASH_OFFEST);
					}

					fd.left = (i == 0)
							? new FormAttachment(0)
							: new FormAttachment(columnSashes[i - 1], 0, SWT.RIGHT);
					fd.right = new FormAttachment(column, 0, SWT.LEFT);

					row.setLayoutData(fd);
				}
			}
		}

		// The last column (which has no sash but binds to the right edge of the form)
		Sash[] rowSashes = this.rowSashes[columnSashes.length];
		for (int j = 0; j < rowSashes.length; j++) {
			Sash row = rowSashes[j];
			if (row != null) { // The last column is not necessarily full
				FormData fd = new FormData();

				SashRow rowModel = (pageModel == null) ? null : pageModel.getSashRow(columnSashes.length, j);

				if ((rowModel != null) && rowModel.isSetY()) {
					fd.top = new FormAttachment(0, rowModel.getY());
				} else {
					fd.top = new FormAttachment((100 * (j + 1)) / rows, -SASH_OFFEST);
				}

				fd.left = (columnSashes.length == 0)
						? new FormAttachment(0)
						: new FormAttachment(columnSashes[columnSashes.length - 1], 0, SWT.RIGHT);
				fd.right = new FormAttachment(100);

				row.setLayoutData(fd);
			}
		}
	}

	private void createSections() {
		this.sections = new HashMap<>();

		for (int i = 0; i < tabs.size(); i++) {
			WelcomeTab tab = tabs.get(i);
			ExpandableComposite control = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
			Composite body = toolkit.createComposite(control);
			body.setLayout(new GridLayout()); // XWTSection expects to fill a grid layout
			control.setClient(body);
			tab.register(body, tabControls);
			control.setData(tab);
			sections.put(tab, control);

			Transfer[] transfers = { WelcomeSectionTransfer.getInstance() };
			addDragSourceAdapter(control, transfers, new DragSourceAdapter() {

				@Override
				public void dragSetData(DragSourceEvent event) {
					event.data = control;
				}
			});
			addDropTargetAdapter(control, transfers, new DropTargetAdapter() {

				@Override
				public void drop(DropTargetEvent event) {
					if (event.data != control) {
						Composite target = (Composite) event.data;
						swapSections(tab, (WelcomeTab) target.getData());
					}
				}
			});

			control.setText(tab.getLabel());
			// TODO: tab images?
		}

		layoutTabSections();

		if (tabControls.isEmpty()) {
			// We didn't have any contributions
			ExpandableComposite messages = toolkit.createSection(parent, ExpandableComposite.NO_TITLE);
			Composite blank = toolkit.createComposite(messages);
			messages.setClient(blank);
			blank.setLayout(new GridLayout());
			toolkit.createLabel(blank, "No welcome content is configured in this workspace.");
			Hyperlink hyperlink = toolkit.createHyperlink(blank, "Open Welcome Preferences", SWT.WRAP);
			tabControls.put(null, blank); // So it can be destroyed on re-build

			hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					PreferencesUtil.createPreferenceDialogOn(hyperlink.getShell(), WelcomeConstants.WELCOME_PAGE_ID, null, null).open();
				}
			});
		}
	}

	private void layoutTabSections() {
		final int rows = this.rowCount;
		final int columns = (tabs.size() + (tabs.size() % rows)) / rows;

		int colIdx = 0;
		int rowIdx = -1; // The first thing we do is increment this

		for (int i = 0; i < tabs.size(); i++) {
			Control section = sections.get(tabs.get(i));
			FormData fd = new FormData();

			// Which cell is this?
			rowIdx++;
			if (rowIdx == rows) {
				// Actually, the next column
				colIdx++;
				rowIdx = 0;
			}

			if (rowIdx == 0) {
				// Attach first row to the top of the form
				fd.top = new FormAttachment(0);
			} else {
				// Attach it to the bottom of the previous sash
				fd.top = new FormAttachment(rowSashes[colIdx][rowIdx - 1], 0, SWT.BOTTOM);
			}

			if ((rowIdx < (rows - 1)) && (rowSashes[colIdx][rowIdx] != null)) {
				// Attach it to the top of the next sash, if there is one (the
				// last column may not have all rows)
				fd.bottom = new FormAttachment(rowSashes[colIdx][rowIdx], 0, SWT.TOP);
			} else {
				// Attach it to the bottom of the form
				fd.bottom = new FormAttachment(100);
			}

			if (colIdx == 0) {
				// Attach first column to the left of the form
				fd.left = new FormAttachment(0);
			} else {
				// Attach it to the right of the previous sash
				fd.left = new FormAttachment(columnSashes[colIdx - 1], 0, SWT.RIGHT);
			}

			if (colIdx < (columns - 1)) {
				// Attach it to the left of the next sash
				fd.right = new FormAttachment(columnSashes[colIdx], 0, SWT.LEFT);
			} else {
				// Attach it to the right of the form
				fd.right = new FormAttachment(100);
			}

			section.setLayoutData(fd);
		}
	}

	void swapSections(WelcomeTab tab1, WelcomeTab tab2) {
		Collections.swap(tabs, tabs.indexOf(tab1), tabs.indexOf(tab2));
		updateWelcomeLayoutModel();
		layoutTabSections();
		parent.layout();
	}

	Composite getTabControl(Tab tab) {
		return tabControls.get(tab);
	}

	private void addDragSourceAdapter(Control control, Transfer[] transferTypes, DragSourceListener listener) {
		Control textLabel = textLabelAccessor.apply(control);
		DragSource drag = new DragSource(textLabel, DND.DROP_MOVE);
		drag.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragStart(DragSourceEvent event) {
				event.image = Activator.getIcon(Activator.IMG_LAYOUT);
			}
		});
		drag.setTransfer(transferTypes);
		drag.addDragListener(listener);
		textLabel.addDisposeListener(event -> drag.dispose());
	}

	private void addDropTargetAdapter(Control control, Transfer[] transferTypes, DropTargetListener listener) {
		DropTarget drop = new DropTarget(control, DND.DROP_MOVE);
		drop.setTransfer(transferTypes);
		drop.addDropListener(listener);
		control.addDisposeListener(event -> drop.dispose());
	}

	private void updateWelcomeLayoutModel() {
		SashModel sashModel = SashModelUtils.getSashModel(welcomeService.getOwner());
		Resource res = sashModel.getResource();
		if (res != null) {
			Welcome welcome = welcomeService.getWelcome();
			try {
				TransactionHelper.run(EMFHelper.resolveEditingDomain(welcome), () -> {
					if (welcome.eResource() != res) {
						// Move it
						res.getContents().add(welcome);
					}

					WelcomePage page = welcome.getWelcomePage();
					if (page == null) {
						page = welcome.createWelcomePage();
					}

					recordTabSectionOrder(page);
					recordSashLayout(page);
				});
			} catch (Exception e) {
				Activator.log.error("Failed to update welcome page layout", e); //$NON-NLS-1$
			}
		}
	}

	private void recordTabSectionOrder(WelcomePage page) {
		// TODO: Hidden tab sections
		List<WelcomeSection> newSections = new ArrayList<>(tabs.size());
		for (WelcomeTab tab : tabs) {
			WelcomeSection section = page.getSection(tab.getID());
			if (section == null) {
				section = WelcomeFactory.eINSTANCE.createWelcomeSection();
				section.getIdentifiers().addAll(tab.getAllTabIDs());
			}
			newSections.add(section);
		}
		ECollections.setEList(page.getSections(), newSections);
	}

	private void recordSashLayout(WelcomePage page) {
		List<SashColumn> newColumns = new ArrayList<>(columnSashes.length + 1);
		for (int i = 0; i < columnSashes.length; i++) {
			SashColumn column = page.getSashColumn(i);
			if (column == null) {
				column = WelcomeFactory.eINSTANCE.createSashColumn();
			}
			newColumns.add(column);

			// Pin the sash location
			column.setX(columnSashes[i].getLocation().x);

			// Process the rows
			recordRowLayout(column, rowSashes[i]);
		}

		// The last column
		SashColumn column = page.getSashColumn(columnSashes.length);
		if (column == null) {
			column = WelcomeFactory.eINSTANCE.createSashColumn();
		}
		newColumns.add(column);
		column.unsetX(); // No actual sash in this column
		recordRowLayout(column, rowSashes[columnSashes.length]);

		ECollections.setEList(page.getSashColumns(), newColumns);
	}

	private void recordRowLayout(SashColumn column, Sash[] rowSashes) {
		List<SashRow> newRows = new ArrayList<>(rowSashes.length);
		for (int i = 0; i < rowSashes.length; i++) {
			SashRow row = column.getSashRow(i);
			if (row == null) {
				row = WelcomeFactory.eINSTANCE.createSashRow();
			}
			newRows.add(row);

			// Pin the sash location
			if (rowSashes[i] == null) {
				row.unsetY(); // Empty spaces in the last column
			} else {
				row.setY(rowSashes[i].getLocation().y);
			}
		}
		ECollections.setEList(column.getSashRows(), newRows);
	}

	void resetLayoutModel() {
		Welcome welcome = welcomeService.getWelcome();
		Resource res = welcomeService.getWelcomeResource();
		ModelSet modelSet = (ModelSet) res.getResourceSet();
		Resource sashResource = SashModelUtils.getSashModel(modelSet).getResource();
		Resource welcomeResource = Activator.getDefault().getWelcomeModelManager().getWelcomeResource(modelSet);

		boolean[] recompute = { false };

		try {
			TransactionHelper.run(EMFHelper.resolveEditingDomain(welcome), () -> {
				Welcome defaultWelcome = (Welcome) EcoreUtil.getObjectByType(welcomeResource.getContents(), WelcomePackage.Literals.WELCOME);

				if (res == sashResource) {
					// Move it back to the default resource
					if (defaultWelcome != null) {
						EcoreUtil.replace(defaultWelcome, welcome);
					} else {
						res.getContents().add(welcome);
					}
				}

				if (!EcoreUtil.equals(defaultWelcome, welcome)) {
					recompute[0] = true;

					// We need to keep the identity of the welcome object to maintain
					// the reference from the sash model page, so make it look like the
					// default welcome
					become(welcome, defaultWelcome);
				}
			});
		} catch (Exception e) {
			Activator.log.error("Failed to reset new welcome page layout", e); //$NON-NLS-1$
		}

		if (recompute[0]) {
			layout();
		}
	}

	void deferredUpdateWelcomeLayoutModel() {
		if (deferredLayoutUpdate.get() == null) {
			new DeferredLayoutUpdate().post();
		}
	}

	void layout() {
		sort(tabs);
		layoutSashes();
		layoutTabSections();
		parent.layout();
	}

	//
	// Reflection hacks
	//

	private static Function<Control, Control> createTextLabelAccessor() {
		Function<Control, Control> result;

		try {
			// It's protected API, so it really should be there
			Field textLabelField = ExpandableComposite.class.getDeclaredField("textLabel"); //$NON-NLS-1$
			textLabelField.setAccessible(true);
			result = section -> {
				try {
					return (Control) textLabelField.get(section);
				} catch (Exception e) {
					return section;
				}
			};
		} catch (Exception e) {
			result = control -> control;
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * A deferred update of the sash-layout model from the UI, used to ensure
	 * that we don't continuously rewrite the model as the user scrubs a sash.
	 */
	private class DeferredLayoutUpdate implements Runnable {
		private long posted;

		@Override
		public void run() {
			if (isPending(true) && !parent.isDisposed()) {
				updateWelcomeLayoutModel();
			}
		}

		@SuppressWarnings("unused")
		boolean isPending() {
			return isPending(false);
		}

		boolean isPending(boolean reset) {
			return reset
					? deferredLayoutUpdate.compareAndSet(this, null)
					: deferredLayoutUpdate.get() == this;
		}

		void post() {
			DeferredLayoutUpdate pending = deferredLayoutUpdate.get();
			if ((pending == null) || ((System.currentTimeMillis() - pending.posted) <= 100L)) {
				// Still moving sashes around? Defer again
				deferredLayoutUpdate.set(this);
				Display.getCurrent().timerExec(500, this);
				posted = System.currentTimeMillis();
			} // Otherwise, wait this out
		}
	}
}
