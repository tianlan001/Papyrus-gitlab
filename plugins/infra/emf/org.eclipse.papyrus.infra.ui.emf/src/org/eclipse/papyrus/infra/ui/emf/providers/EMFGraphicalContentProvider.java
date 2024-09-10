/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, ALL4TEC and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Added graphic contributions for the filters
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial History implementation
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - History integration
 *  Philip Langer (EclipseSource) planger@eclipsesource.com - Revealing first match of filter
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 500869
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Bug 435352 (avoid commit on dispose)
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.emf.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.infra.services.labelprovider.service.IDetailLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.ui.internal.emf.Activator;
import org.eclipse.papyrus.infra.ui.internal.emf.messages.Messages;
import org.eclipse.papyrus.infra.widgets.IFireDoubleClick;
import org.eclipse.papyrus.infra.widgets.editors.AbstractEditor;
import org.eclipse.papyrus.infra.widgets.editors.ICommitListener;
import org.eclipse.papyrus.infra.widgets.editors.StringWithClearEditor;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.PatternViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * This providers adds a text-filter and an History to EMF-based content providers
 */
// TODO : Extend (Abstract)FilteredContentProvider
public class EMFGraphicalContentProvider extends EncapsulatedContentProvider implements ISelectionChangedListener {

	private static final String DIALOG_SETTINGS = EMFGraphicalContentProvider.class.getName();

	private static final String HISTORY_SETTINGS = "History"; //$NON-NLS-1$

	private static final String PREVIOUS_SELECTION = "PreviousSelection"; //$NON-NLS-1$

	private static final int HISTORY_MAX_SIZE = 15;

	/** The default profile icon path. */
	private static final String ICONS_EXPAND_ALL = "/icons/expandAll.png";//$NON-NLS-1$

	/** The default profile icon path. */
	private static final String ICONS_COLLAPSE_ALL = "/icons/collapseAll.png";//$NON-NLS-1$

	/** the expand button */
	private ToolItem buttonExpand;

	/** the collapse button */
	private ToolItem buttonCollapse;

	/**
	 * The maximum depth for {@link #findAndRevealFirstMatchingItem() finding and revealing}
	 * items in the filtered tree {@link #viewer}.
	 */
	private static final int MAX_SEARCH_DEPTH = 15;

	protected String historyId;

	// Unused (yet)
	// TODO : Add a preference or a collapsible composite for this feature (Or both)
	//
	// /**
	// * The current metaclass viewer filter
	// */
	// protected ViewerFilter currentMetaclassViewerFilter;

	protected ViewerFilter patternFilter;

	protected List<EObject> selectionHistory;

	protected CLabel detailLabel;

	protected Object selectedObject;

	protected StructuredViewer viewer;

	protected ResourceSet resourceSet;

	private String currentFilterPattern;

	private TableViewer historyViewer;

	/** Set to true if expand and collapse buttons must be enable. */
	private boolean buttonExpandCollapseEnable = true;

	/**
	 * the constructor
	 */
	public EMFGraphicalContentProvider(IStructuredContentProvider semanticProvider, ResourceSet resourceSet, String historyId) {
		super(semanticProvider);
		this.historyId = historyId;
		this.resourceSet = resourceSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#createBefore(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createBefore(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

		// Create filter composite
		createPatternFilter(composite);

		createCaseSensitiveButton(composite);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createViewerToolbar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createViewerToolbar(Composite parent) {
		createExpandCollapseButtons(parent);
	}

	/**
	 * Create buttons to collapse and expand treeViewer.
	 */
	protected void createExpandCollapseButtons(final Composite parent) {

		ToolBar Toolbar = new ToolBar(parent, SWT.NONE);
		buttonExpand = new ToolItem(Toolbar, SWT.NONE);
		buttonExpand.setImage(Activator.getPluginIconImage(org.eclipse.papyrus.infra.widgets.Activator.PLUGIN_ID, ICONS_EXPAND_ALL));
		buttonExpand.setToolTipText(Messages.EMFGraphicalContentProvider_ExpandAllTooltip);
		buttonExpand.setEnabled(buttonExpandCollapseEnable);
		buttonExpand.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = viewer.getSelection();
				// If there are selected element
				if (selection instanceof StructuredSelection && !selection.isEmpty()) {
					// For each element
					for (Object object : ((StructuredSelection) selection).toArray()) {
						((AbstractTreeViewer) viewer).expandToLevel(object, org.eclipse.papyrus.infra.widgets.Activator.getMaxLevelToExpandValue());
					}
				} else {
					// or expand all
					((AbstractTreeViewer) viewer).expandToLevel(org.eclipse.papyrus.infra.widgets.Activator.getMaxLevelToExpandValue());
				}
				viewer.refresh();
			}
		});

		buttonCollapse = new ToolItem(Toolbar, SWT.NONE);
		buttonCollapse.setImage(Activator.getPluginIconImage(org.eclipse.papyrus.infra.widgets.Activator.PLUGIN_ID, ICONS_COLLAPSE_ALL));
		buttonCollapse.setToolTipText(Messages.EMFGraphicalContentProvider_CollapseAllTooltip);
		buttonCollapse.setEnabled(buttonExpandCollapseEnable);
		buttonCollapse.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = ((AbstractTreeViewer) viewer).getSelection();
				// If there are selected element
				if (selection instanceof StructuredSelection && !selection.isEmpty()) {
					// expand each selected element
					for (Object object : ((StructuredSelection) selection).toArray()) {
						((AbstractTreeViewer) viewer).collapseToLevel(object, AbstractTreeViewer.ALL_LEVELS);
					}

				} else {
					// or collapse all
					((AbstractTreeViewer) viewer).collapseAll();
				}
			}
		});
	}

	/**
	 * create the Case sensitive checkBox.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	protected void createCaseSensitiveButton(final Composite parent) {
		// Create the checkbox button
		Button checkBoxCaseSensitive = new Button(parent, SWT.CHECK);

		checkBoxCaseSensitive.setText(Messages.EMFGraphicalContentProvider_CaseSensitiveCheckBoxLabel);
		checkBoxCaseSensitive.setToolTipText(Messages.EMFGraphicalContentProvider_CaseSensitiveCheckBoxTooltip);
		checkBoxCaseSensitive.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (patternFilter instanceof PatternViewerFilter) {
					((PatternViewerFilter) patternFilter).setIgnoreCase(!checkBoxCaseSensitive.getSelection());
				}
				viewer.refresh();
			}
		});
	}

	/**
	 * Create the pattern filter.
	 * 
	 * @param parent
	 */
	protected void createPatternFilter(final Composite parent) {
		StringWithClearEditor editor = new StringWithClearEditor(parent, SWT.BORDER);
		editor.setToolTipText(Messages.EMFGraphicalContentProvider_FilterFieldTooltip);
		editor.setValidateOnDelay(org.eclipse.papyrus.infra.widgets.Activator.getValidationDelay());
		editor.setValidateOnDelay(org.eclipse.papyrus.infra.widgets.Activator.isFilterValidateOnDelay());

		// Set replacement of stereotype delimiters
		if (org.eclipse.papyrus.infra.widgets.Activator.isStereotypeDelimitersReplaced()) {
			editor.addStringToReplace(org.eclipse.papyrus.infra.widgets.Activator.ST_LEFT_BEFORE, org.eclipse.papyrus.infra.widgets.Activator.ST_LEFT);
			editor.addStringToReplace(org.eclipse.papyrus.infra.widgets.Activator.ST_RIGHT_BEFORE, org.eclipse.papyrus.infra.widgets.Activator.ST_RIGHT);
		} else {
			editor.clearStringToReplace();
		}

		GridLayoutFactory.fillDefaults().applyTo(editor);

		editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		patternFilter = new PatternViewerFilter();
		currentFilterPattern = "*"; //$NON-NLS-1$
		((PatternViewerFilter) patternFilter).setPattern(currentFilterPattern);
		editor.setValue(currentFilterPattern);

		editor.addCommitListener(new ICommitListener() {

			@Override
			public void commit(AbstractEditor editor) {
				String filterPattern = (String) ((StringWithClearEditor) editor).getValue();
				if (!filterPattern.equals(currentFilterPattern)) {
					((PatternViewerFilter) patternFilter).setPattern(filterPattern);

					List<ViewerFilter> filtersAsList = Arrays.asList(viewer.getFilters());
					if (!filtersAsList.contains(patternFilter)) {
						viewer.addFilter(patternFilter);
					}
					viewer.refresh();

					if (!("".equals(filterPattern) || currentFilterPattern.equals(filterPattern))) { //$NON-NLS-1$
						findAndRevealFirstMatchingItem();
						currentFilterPattern = filterPattern;
					}
				}
			}

		});

		// Focus on viewer when press the down arrow
		editor.getText().addKeyListener(new KeyAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					viewer.getControl().setFocus();
				}
			}
		});

	}


	/**
	 * Traverses to the first leaf item in the viewer tree and reveals it.
	 * <p>
	 * If this method is called after the filter has been updated and the viewer has been refreshed,
	 * this will reveal and return the first item that matches the filter. Note that this only works
	 * if {@link #viewer} is an instance of {@link TreeViewer}.
	 * </p>
	 * 
	 * @return the semantic element of the revealed item, or <code>null</code> if it could not be found.
	 */
	protected EObject findAndRevealFirstMatchingItem() {
		if (viewer instanceof TreeViewer) {
			// start to search from first root element
			final Tree tree = ((TreeViewer) viewer).getTree();
			if (tree.getItems().length > 0) {
				return revealFirstLeaf(tree.getItem(0), MAX_SEARCH_DEPTH);
			}
		}
		return null;
	}

	private EObject revealFirstLeaf(TreeItem item, int maxSearchDepth) {
		if (maxSearchDepth < 1 || !(viewer instanceof TreeViewer)) {
			return null;
		}

		// reveal to current item
		final TreeViewer treeViewer = (TreeViewer) viewer;
		final Object[] itemDataArray = Collections.singletonList(item.getData()).toArray();
		treeViewer.setExpandedElements(itemDataArray);

		if (item.getItems().length > 0) {
			// continue with children of current item
			return revealFirstLeaf(item.getItem(0), maxSearchDepth - 1);
		} else {
			// leaf item found: reveal and return it
			final Object data = item.getData();
			if (data instanceof EObjectTreeElement) {
				final EObject semanticElement = ((EObjectTreeElement) data).getEObject();
				revealSemanticElement(Collections.singletonList(semanticElement));
				return semanticElement;
			}
		}

		return null;
	}

	/**
	 * Returns the first (encapsulated) element matching the current filters
	 * 
	 * @deprecated Since 1.2.0. Use {@link #findAndRevealFirstMatchingItem(Object)} instead.
	 * @return
	 */
	@Deprecated
	protected Object getFirstMatchingElement(Object parent) {
		// Browse from the root element
		if (parent == null) {
			for (Object parentElement : getElements(viewer.getInput())) {
				Object firstMatch = getFirstMatchingElement(parentElement);
				if (firstMatch != null) {
					return firstMatch;
				}
			}
			return null;
		}

		for (ViewerFilter filter : viewer.getFilters()) {
			if (!filter.select(viewer, getParent(parent), parent)) {
				return null;
			}
		}

		// Test the current element
		if (isValidValue(parent)) {
			return parent;
		}

		// Browse the child elements
		for (Object childElement : getChildren(parent)) {
			Object firstMatch = getFirstMatchingElement(childElement);
			if (firstMatch != null) {
				return firstMatch;
			}
		}

		// No match found
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createAfter(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		// createMetaclassFilter(parent); //Disabled

		createHistory(parent);
		createDetailArea(parent);
	}

	/**
	 * Creates a widget referencing the recently selected elements
	 *
	 * @param parent
	 *            The composite in which the widget will be created
	 */
	protected void createHistory(Composite parent) {
		initSelectionHistory();

		Group historyGroup = new Group(parent, SWT.NONE);
		historyGroup.setText(Messages.EMFGraphicalContentProvider_historyGroupLabel);
		historyGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		historyGroup.setLayout(new GridLayout(1, true));

		// table
		Table historyTable = new Table(historyGroup, SWT.BORDER | SWT.SINGLE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = 70;
		historyTable.setLayoutData(data);
		historyViewer = new TableViewer(historyTable);
		historyViewer.setContentProvider(CollectionContentProvider.instance);
		historyViewer.setLabelProvider(new LabelProviderServiceImpl().getLabelProvider());
		historyViewer.setInput(selectionHistory);
		historyViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) historyViewer.getSelection();
				Object selectedObject = selection.getFirstElement();
				if (selectedObject instanceof EObject) {
					EObject eObject = ((EObject) selectedObject);
					revealSemanticElement(Collections.singletonList(eObject));
				}
			}
		});

		historyViewer.addDoubleClickListener(event -> {
			if (viewer instanceof IFireDoubleClick) {
				((IFireDoubleClick) viewer).fireDoubleClick(new DoubleClickEvent(viewer, event.getSelection()));
			}

		});

	}

	/**
	 * Inits the History
	 */
	protected void initSelectionHistory() {
		selectionHistory = new ArrayList<EObject>(HISTORY_MAX_SIZE + 1);

		IDialogSettings historySettings = getDialogSettings().getSection(HISTORY_SETTINGS);
		if (historySettings != null && resourceSet != null) {
			String[] uriHistory = historySettings.getArray(PREVIOUS_SELECTION);
			// for each element in the list, try to get the EObject by its URI
			if (uriHistory != null) {
				for (String uri : uriHistory) {
					try {
						EObject object = resourceSet.getEObject(URI.createURI(uri), true);
						if (object != null && !selectionHistory.contains(object)) {
							selectionHistory.add(object);
						}
					} catch (Exception ex) {
						// Ignore : if the resource doesn't exist anymore, we just skip it
					}
				}
			}
		}
	}

	/**
	 * Creates a widget to filter the tree according to the selected
	 * metaclass.
	 *
	 * @param parent
	 *            The Composite in which the widgets will be created
	 * @deprecated
	 */
	@Deprecated
	protected void createMetaclassFilter(Composite parent) {
		// if(semanticRoot == null) {
		// return;
		// }
		//
		// Composite container = new Composite(parent, SWT.NONE);
		// container.setLayout(new GridLayout(2, false));
		// container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//
		// ResourceSet resourceSet = semanticRoot.eResource().getResourceSet();
		// EcoreUtil.resolveAll(resourceSet);
		//
		// Label metamodelLabel = new Label(container, SWT.NONE);
		// metamodelLabel.setText("Metamodel:");
		//
		// ComboViewer metamodelViewer = new ComboViewer(container);
		// metamodelViewer.setContentProvider(getMetamodelContentProvider());
		// metamodelViewer.setLabelProvider(new EMFLabelProvider());
		// metamodelViewer.setInput(semanticRoot);
		// metamodelViewer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//
		// Label metaclassLabel = new Label(container, SWT.NONE);
		// metaclassLabel.setText("Metaclass:");
		//
		// final ComboViewer metaclassViewer = new ComboViewer(container);
		// IStructuredContentProvider metaclassProvider = getMetaclassContentProvider();
		// metaclassViewer.setContentProvider(metaclassProvider);
		// metaclassViewer.setLabelProvider(new EMFLabelProvider());
		// metaclassViewer.getCombo().setEnabled(false);
		// metaclassViewer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//
		// metamodelViewer.setFilters(new ViewerFilter[]{ new MetamodelContentFilter(metaclassProvider) });
		//
		// metamodelViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		//
		// public void selectionChanged(SelectionChangedEvent event) {
		// metaclassViewer.setInput(((IStructuredSelection)event.getSelection()).getFirstElement());
		// metaclassViewer.getCombo().setEnabled(true);
		// }
		// });
		//
		// metaclassViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		//
		// public void selectionChanged(SelectionChangedEvent event) {
		// if(!event.getSelection().isEmpty()) {
		// Object selectedObject = ((IStructuredSelection)event.getSelection()).getFirstElement();
		// List<ViewerFilter> filters = new LinkedList<ViewerFilter>(Arrays.asList(viewer.getFilters()));
		// filters.remove(currentMetaclassViewerFilter);
		// currentMetaclassViewerFilter = getMetaclassViewerFilter(selectedObject);
		// filters.add(currentMetaclassViewerFilter);
		// viewer.setFilters(filters.toArray(new ViewerFilter[filters.size()]));
		// viewer.refresh();
		// }
		// }
		// });
	}

	/**
	 * Creates a label widget to display detailed information on the
	 * current value (Such as fully qualified name, ...)
	 *
	 * @param parent
	 *            The composite in which the widget will be created
	 */
	protected void createDetailArea(Composite parent) {
		detailLabel = new CLabel(parent, SWT.BORDER);
		detailLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		updateDetailLabel();
	}

	// @Deprecated
	// protected IStructuredContentProvider getMetamodelContentProvider() {
	// return new MetamodelContentProvider();
	// }
	//
	// @Deprecated
	// protected IStructuredContentProvider getMetaclassContentProvider() {
	// return new MetaclassContentProvider((EClass)this.metaClassWanted, this.metaClassNotWantedList);
	// }
	//
	// @Deprecated
	// protected ILabelProvider getLabelProvider() {
	// return new EMFObjectLabelProvider();
	// }
	//
	// @Deprecated
	// protected ViewerFilter getMetaclassViewerFilter(Object selectedMetaClass) {
	// return new MetaclassViewerFilter(selectedMetaClass);
	// }

	/**
	 * Returns the dialog settings. Returned object can't be null.
	 *
	 * @return dialog settings for this dialog
	 */
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(getDialogSettingsIdentifier());
		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(getDialogSettingsIdentifier());
		}
		return settings;
	}

	private String getDialogSettingsIdentifier() {
		return DIALOG_SETTINGS + "_" + historyId; //$NON-NLS-1$
	}

	/**
	 * Stores dialog settings.
	 *
	 * @param settings
	 *            settings used to store dialog
	 */
	protected void storeDialog(IDialogSettings settings, Collection<EObject> newValues) {
		selectionHistory.removeAll(newValues);

		selectionHistory.addAll(0, newValues);

		// Truncate the history: only keep a sublist of size HISTORY_MAX_SIZE
		if (selectionHistory.size() > HISTORY_MAX_SIZE) {
			selectionHistory = selectionHistory.subList(0, HISTORY_MAX_SIZE);
		}

		List<String> uriList = new ArrayList<String>();

		// convert list of EObject into URI string list
		for (EObject object : selectionHistory) {
			URI uri = EcoreUtil.getURI(object);
			uriList.add(uri.toString());
		}

		IDialogSettings historySettings = settings.getSection(HISTORY_SETTINGS);
		if (historySettings == null) {
			historySettings = settings.addNewSection(HISTORY_SETTINGS);
		}
		historySettings.put(PREVIOUS_SELECTION, uriList.toArray(new String[uriList.size()]));

		historyViewer.setInput(selectionHistory);
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		encapsulated.inputChanged(viewer, oldInput, newInput);

		if (viewer instanceof StructuredViewer) {
			this.viewer = (StructuredViewer) viewer;
			if (newInput != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
				this.viewer.addSelectionChangedListener(this);
			}
		} else {
			this.viewer = null;
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		selectedObject = ((IStructuredSelection) event.getSelection()).getFirstElement();
		updateDetailLabel();
	}

	private void updateDetailLabel() {
		if (detailLabel == null || detailLabel.isDisposed()) {
			return;
		}
		if (selectedObject == null) {
			detailLabel.setText(""); //$NON-NLS-1$
			detailLabel.setImage(null);
		} else {
			ILabelProvider labelProvider = (ILabelProvider) viewer.getLabelProvider();
			String description;
			if (labelProvider instanceof IDetailLabelProvider) {
				description = ((IDetailLabelProvider) labelProvider).getDetail(selectedObject);
			} else {
				description = labelProvider.getText(selectedObject);
			}
			detailLabel.setText(description);
			detailLabel.setImage(labelProvider.getImage(selectedObject));
		}
		detailLabel.getParent().getParent().layout();
	}

	@Override
	public void commit(AbstractEditor editor) {
		Iterator<?> selectionIterator = ((IStructuredSelection) viewer.getSelection()).iterator();
		Set<EObject> eObjectsToStore = new LinkedHashSet<EObject>();
		while (selectionIterator.hasNext()) {
			Object selectedElement = selectionIterator.next();
			if (isValidValue(selectedElement)) {
				Object semanticObject = getAdaptedValue(selectedElement);
				if (semanticObject instanceof EObject) {
					eObjectsToStore.add((EObject) semanticObject);
				}
			}
		}

		if (!eObjectsToStore.isEmpty()) {
			storeDialog(getDialogSettings(), eObjectsToStore);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (viewer != null) {
			viewer.removeSelectionChangedListener(this);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#setFlat(boolean)
	 *
	 * @param isFlat
	 */
	@Override
	public void setFlat(final boolean isFlat) {
		super.setFlat(isFlat);
		if (patternFilter instanceof PatternViewerFilter) {
			((PatternViewerFilter) patternFilter).clearCache();
		}
		if (viewer != null) {
			viewer.refresh();
		}
		if (null != buttonCollapse && null != buttonExpand) {
			buttonExpandCollapseEnable = !isFlat;
			buttonExpand.setEnabled(buttonExpandCollapseEnable);
			buttonCollapse.setEnabled(buttonExpandCollapseEnable);
		}
	}
}
