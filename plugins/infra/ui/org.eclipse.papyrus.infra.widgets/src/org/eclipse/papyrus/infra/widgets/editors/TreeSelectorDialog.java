/*****************************************************************************
 * Copyright (c) 2010, 2016, 2017 CEA LIST,ALL4TEC, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 408491
 *  Philip Langer (EclipseSource) planger@eclipsesource.com - bug 495394
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Adds implementation if multi tabulation.
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr, Bug 435352 (only initialize current tab)
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.IFireDoubleClick;
import org.eclipse.papyrus.infra.widgets.SelectorDialogTabReader;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IDependableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IDependableLabelProvider;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalLabelProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * A Dialog for selecting values. The values are displayed as a Tree. If only
 * some of the values of this Tree should be selectable, you should pass a {@link IHierarchicContentProvider} to this dialog.
 *
 * @author Camille Letavernier
 *
 */
public class TreeSelectorDialog extends SelectionDialog implements ITreeSelectorDialog {

	/** The icon used for the default tabulation. */
	protected static final String ICONS_TREE_VIEW = "/icons/treeView.gif"; //$NON-NLS-1$

	/** The icon papyrus. */
	private static final String ICON_PAPYRUS = "/icons/papyrus.png"; //$NON-NLS-1$

	/** The label provider. */
	private Map<String, ILabelProvider> labelProviders = new HashMap<String, ILabelProvider>();

	/** The content provider which encapsulate the real one. */
	private Map<String, ITreeContentProvider> contentProviders = new HashMap<String, ITreeContentProvider>();

	/** the viewer. */
	private Map<String, TreeViewer> treeViewers = new HashMap<String, TreeViewer>();

	/** a map of tabComposites, used for lazy instantiation */
	private Map<String, Composite> tabComposites = new HashMap<String, Composite>();

	/** The main description of the dialog. */

	private static String defaultTabId = "default"; //$NON-NLS-1$

	/** Descriptions of additional tabs. */
	private Map<String, String> descriptions = new HashMap<String, String>();

	/** The input. */
	private Object input = null;

	/** The commit listener. */
	private final Set<ICommitListener> commitListeners = new HashSet<ICommitListener>();

	/** The {@link Map} of tabulation names. The id is used a the key. */
	private Map<String, String> tabNames = new HashMap<String, String>();

	/** The {@link Map} of tabulation icons. The id is used a the key. */
	private Map<String, Image> tabIcons = new HashMap<String, Image>();

	/** The {@link List} of tabulation ids. */
	private List<String> tabIds = new ArrayList<>();

	/** The current tabulation id */
	private String currentTabId;

	/** The tab folder. */
	private TabFolder tabFolder;

	/** true when tabulations information is set */
	private boolean initialized = false;

	/** true when ok is pressed */
	private boolean okPressed = false;

	/** The dialog settings key for this class. */
	protected static final String DIALOG_SETTINGS_KEY = TreeSelectorDialog.class.getName();

	/** The dialogs settings key for last tabulation value */
	protected static final String LAST_TAB_KEY = "lastTab"; //$NON-NLS-1$


	/**
	 * An implementation of the double click listener.
	 */
	private final class DoubleClickListenerImplementation implements IDoubleClickListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
		 */
		@Override
		public void doubleClick(final DoubleClickEvent event) {
			if (getOkButton().isEnabled()) {
				okPressed();
			}
		}
	}

	/**
	 * An implementation of Selection Changed Listener.
	 */
	private final class SelectionChangedListenerImplementation implements ISelectionChangedListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			if (!okPressed) {
				ISelection selection = event.getSelection();

				Object selectedElement = null;
				if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
					IStructuredSelection sSelection = (IStructuredSelection) selection;
					selectedElement = sSelection.getFirstElement();
				}

				ITreeContentProvider currentContentProvider = null;
				for (Entry<String, TreeViewer> entry : treeViewers.entrySet()) {
					if (entry.getValue().equals(event.getSource())) {
						currentContentProvider = contentProviders.get(entry.getKey());
					}
				}

				if (currentContentProvider instanceof IHierarchicContentProvider) {
					boolean isValidValue = ((IHierarchicContentProvider) currentContentProvider).isValidValue(selectedElement);
					if (currentContentProvider instanceof IAdaptableContentProvider) {
						selectedElement = ((IAdaptableContentProvider) currentContentProvider).getAdaptedValue(selectedElement);
					}
					if (isValidValue) {
						setResult(Collections.singletonList(selectedElement));
					} else {
						setResult(Collections.EMPTY_LIST);
					}
					getOkButton().setEnabled(isValidValue);

				}
			}
		}
	}

	/**
	 * A TreeViewer which can be double click programmatically.
	 */
	private class ExtendedTreeViewer extends TreeViewer implements IFireDoubleClick {
		/**
		 * Constructor.
		 */
		public ExtendedTreeViewer(final Composite parent, final int style) {
			super(parent, style);
		}

		/**
		 * {@inheritDoc}<br>
		 * Override to change the method as public
		 * 
		 * @see org.eclipse.jface.viewers.StructuredViewer#fireDoubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
		 */
		@Override
		public void fireDoubleClick(final DoubleClickEvent event) {
			super.fireDoubleClick(event);
		}
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parentShell
	 *            The parent shell in which this dialog will be opened
	 */
	public TreeSelectorDialog(final Shell parentShell) {
		super(parentShell);
		currentTabId = defaultTabId;
	}

	/**
	 * Initialize tabulation information from {@link SelectorDialogTabReader}.
	 */
	protected void initializeTabulationInfo() {
		SelectorDialogTabReader instance = SelectorDialogTabReader.getInstance();

		// Add default tab
		tabIds.add(defaultTabId);
		tabNames.put(defaultTabId, Messages.TreeSelectorDialog_defaultTabLabel);
		Image treeIcon = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(Activator.PLUGIN_ID, ICONS_TREE_VIEW);
		tabIcons.put(defaultTabId, treeIcon);

		List<String> ids = instance.getIds();
		for (String id : ids) {

			ILabelProvider tabLabelProvider = instance.getLabelProviders().get(id);
			labelProviders.put(id, tabLabelProvider);
			if (tabLabelProvider instanceof IDependableLabelProvider) {
				((IDependableLabelProvider) tabLabelProvider).setLabelProvider(labelProviders.get(defaultTabId));
			}

			ITreeContentProvider tabContentProvider = instance.getContentProviders().get(id);
			contentProviders.put(id, tabContentProvider);
			if (tabContentProvider instanceof IDependableContentProvider) {
				((IDependableContentProvider) tabContentProvider).setContentProvider(contentProviders.get(defaultTabId));
			}

			Constraint constraint = instance.getConstraints().get(id);
			if (!tabIds.contains(id) && (null == constraint || constraint.match(Collections.singleton(id)))) {
				tabIds.addAll(ids);
				tabNames.put(id, instance.getTabNames().get(id));
				tabIcons.put(id, instance.getTabIcons().get(id));
				descriptions.put(id, instance.getDescriptions().get(id));
			}
		}

		// Refresh tree viewer to reorder if necessary
		for (Entry<String, ILabelProvider> entry : labelProviders.entrySet()) {
			entry.getValue().addListener(event -> {
				Object key = entry.getKey();
				TreeViewer treeViewer = treeViewers.get(key);
				if (null != treeViewer && !treeViewer.getTree().isDisposed()) {
					treeViewer.refresh();
				}
			});
		}

		initialized = true;
	}

	/**
	 * Set a label provider for a tabulation.
	 * 
	 * @param tabId
	 *            The id of the tabulation (must not be null)
	 * @param provider
	 *            the {@link ILabelProvider} (must not be null)
	 */
	public void setLabelProvider(final String tabId, final ILabelProvider provider) {
		TreeViewer viewer = treeViewers.get(tabId);
		if (null != viewer) {
			if (provider instanceof IStyledLabelProvider) {
				viewer.setLabelProvider(new DelegatingStyledCellLabelProvider((IStyledLabelProvider) provider));
			} else {
				viewer.setLabelProvider(provider);
			}
		}
		provider.addListener(event -> {
			TreeViewer currentTreeViewer = treeViewers.get(currentTabId);
			if (null != currentTreeViewer && !currentTreeViewer.getTree().isDisposed()) {
				currentTreeViewer.refresh();
			}
		});
	}

	/**
	 * Sets the label provider for the default tab of this dialog.
	 *
	 * @param provider
	 *            the {@link ILabelProvider}. (must not be null)
	 */
	@Override
	public void setLabelProvider(final ILabelProvider provider) {
		labelProviders.put(defaultTabId, provider);
		setLabelProvider(defaultTabId, provider);
	}

	/**
	 * Sets the ContentProvider for this dialog
	 * The ContentProvider may be a {@link IHierarchicContentProvider}
	 *
	 * @param provider
	 *            The content provider for this dialog. May be a {@link IHierarchicContentProvider}
	 */
	@Override
	public void setContentProvider(final ITreeContentProvider provider) {
		contentProviders.put(defaultTabId, provider);
		setContentProvider(defaultTabId, provider);
	}

	/**
	 * Set a content provider for a tabulation.
	 * 
	 * @param tabId
	 *            The id of the tabulation (must not be null)
	 * @param provider
	 *            the {@link ITreeContentProvider} (must not be null)
	 */
	public void setContentProvider(final String tabId, final ITreeContentProvider provider) {
		if (null != treeViewers.get(tabId)) {
			initViewerAndProvider(tabId);
		}
		if (provider instanceof ICommitListener) {
			commitListeners.add((ICommitListener) provider);
		}
	}

	/**
	 * initialize the viewer and provider for a particular tabulation.
	 * Only call setInput, if for currentTab
	 * 
	 * @param tabId
	 *            The tabulation id.
	 */
	private void initViewerAndProvider(final String tabId) {
		treeViewers.get(tabId).setContentProvider(contentProviders.get(tabId));
		if (null == treeViewers.get(tabId).getInput()) {
			if (tabId.equals(currentTabId)) {
				doSetInput(tabId);
			}
		}
	}

	/**
	 * Initialize the viewer and the provider for the default tabulation.
	 */
	protected void initViewerAndProvider() {
		initViewerAndProvider(defaultTabId);
	}

	/**
	 * {@inheritDoc}<br>
	 * 
	 * override to return the dialog as a composite.
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#getDialogArea()
	 */
	@Override
	protected Composite getDialogArea() {
		return (Composite) super.getDialogArea();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		okPressed = false;
		createTabs();

		// Select the last opened tab if many
		if (1 < tabIds.size()) {
			String lastSelectedTabId = getDialogSettings().get(LAST_TAB_KEY);
			if (null != lastSelectedTabId) {
				int indexOfLast = tabIds.indexOf(lastSelectedTabId);
				if (0 <= indexOfLast) {
					currentTabId = lastSelectedTabId;
					// no need to refresh (reduce opening time)
					tabFolder.setSelection(indexOfLast);
				}
			} else {
				currentTabId = defaultTabId;
			}
		} else {
			currentTabId = defaultTabId;
		}

		// Set the input on the currentTabId viewer to bind its content provider.
		doSetInput(currentTabId);

		// Reveal the initial element
		revealInitialElement(contentProviders.get(currentTabId), treeViewers.get(currentTabId));

		getShell().setDefaultButton(null);
		getButton(OK).setFocus();
		getShell().setImage(Activator.getDefault().getImage(ICON_PAPYRUS));
		getShell().pack();
	}

	/**
	 * Create all tabs.
	 */
	protected void createTabs() {
		if (!initialized) {
			initializeTabulationInfo();
		}
		if (tabIds.size() > 1) {
			tabFolder = new TabFolder(getDialogArea(), SWT.CLOSE);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(tabFolder);
		}

		// create tabs
		for (String tabId : tabIds) {
			String tabName = tabNames.get(tabId);
			Image tabIcon = tabIcons.get(tabId);
			String tabDescription = descriptions.get(tabId);
			ILabelProvider tabLabelProvider = labelProviders.get(tabId);
			ITreeContentProvider tabContentProvider = contentProviders.get(tabId);

			Composite tabComposite = null;
			if (tabIds.size() > 1) {

				TabItem item = new TabItem(tabFolder, SWT.NONE);
				item.setText(tabName);
				item.setImage(tabIcon);

				tabComposite = new Composite(tabFolder, SWT.NONE);
				GridLayoutFactory.swtDefaults().applyTo(tabComposite);
				item.setControl(tabComposite);

			} else {
				tabComposite = new Composite(getDialogArea(), SWT.NONE);
				GridLayoutFactory.swtDefaults().applyTo(tabComposite);
			}

			// Message label(common for all tabs)
			if (null != getMessage() && !getMessage().isEmpty()) {
				Label messageLabel = new Label(tabComposite, SWT.WRAP);
				messageLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				messageLabel.setText(getMessage());
			}
			// Description Label
			if (null != descriptions.get(tabId) && !descriptions.get(tabId).isEmpty()) {
				Label descriptionLabel = new Label(tabComposite, SWT.WRAP);
				descriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				descriptionLabel.setText(tabDescription);
			}


			// create tree viewer
			ExtendedTreeViewer tabTreeViewer = new ExtendedTreeViewer(tabComposite, SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, true).hint(300, 300).applyTo(tabTreeViewer.getTree());
			treeViewers.put(tabId, tabTreeViewer);

			// Set label Provider
			if (null != tabLabelProvider) {
				if (tabLabelProvider instanceof IStyledLabelProvider) {
					tabTreeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider((IStyledLabelProvider) tabLabelProvider));
				} else {
					tabTreeViewer.setLabelProvider(tabLabelProvider);
				}
			}

			// Set content provider
			if (null != tabContentProvider) {
				initViewerAndProvider(tabId);
			}

			// listeners on viewer
			tabTreeViewer.addSelectionChangedListener(new SelectionChangedListenerImplementation());
			tabTreeViewer.addDoubleClickListener(new DoubleClickListenerImplementation());

			// initialize content, either
			// (1) directly, if for current tab
			// (2) later: store composite reference, initialize in selection listener
			// This reduces the initialization delay, see bug 435352, attribute selection takes way too long
			if (tabId.equals(currentTabId)) {
				createContent(tabContentProvider, tabLabelProvider, tabComposite, tabTreeViewer);
			}
			else {
				tabComposites.put(tabId, tabComposite);
			}

			if (tabIds.size() > 1) {
				// Add selection change listener.
				tabFolder.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent e) {
						currentTabId = tabIds.get(tabFolder.getSelectionIndex());
						getDialogSettings().put(LAST_TAB_KEY, currentTabId);// update last selected tab preference
						if (tabComposites.containsKey(currentTabId)) {
							doSetInput(currentTabId);// refresh the input of the selected viewer
							// initialize the tab contents
							createContent(contentProviders.get(currentTabId), labelProviders.get(currentTabId),
									tabComposites.get(currentTabId), treeViewers.get(currentTabId));
							revealInitialElement(contentProviders.get(currentTabId), treeViewers.get(currentTabId));
							tabComposites.remove(currentTabId);
						}
					}

					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});
			}


		}
	}

	/**
	 * Returns the dialog settings. Returned object can't be null.
	 *
	 * @return dialog settings for this dialog
	 */
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS_KEY);
		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS_KEY);
		}
		return settings;
	}


	/**
	 * Reveal the initial element
	 * 
	 * @param contentProvider
	 *            the content provider
	 * @param treeViewer
	 *            the tree viewer
	 */
	protected void revealInitialElement(final ITreeContentProvider contentProvider, final TreeViewer treeViewer) {
		// Reveal the initial element
		List<?> initialSelection = getInitialElementSelections();
		if (contentProvider instanceof IRevealSemanticElement) {
			((IRevealSemanticElement) contentProvider).revealSemanticElement(initialSelection);
		} else if (!initialSelection.isEmpty() && null != initialSelection.get(0)) {
			// FIXME : When we use an EncapsulatedContentProvider, we'll not get into this case,
			// even if the encapsulated provider is not a IRevealSemanticElement
			treeViewer.setSelection(new StructuredSelection(initialSelection.get(0)), true);
		}
	}

	/**
	 * Create content from graphical content provider and graphical label provider.
	 * 
	 * @param contentProvider
	 *            The content provider.
	 * @param labelProvider
	 *            The label provider.
	 * @param parent
	 *            the parent.
	 * @param treeViewer
	 *            The treeViewer
	 * @return the composite containing the "after" widget for later initialization
	 */
	protected void createContent(final ITreeContentProvider contentProvider, final ILabelProvider labelProvider, final Composite parent, final TreeViewer treeViewer) {
		if (contentProvider instanceof IGraphicalContentProvider || labelProvider instanceof IGraphicalLabelProvider) {
			// create content from graphical content provider

			// The viewer toolbar
			Composite toolbar = new Composite(parent, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).spacing(2, 0).applyTo(toolbar);
			toolbar.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

			// Before the viewer
			Composite beforeTreeComposite = new Composite(toolbar, SWT.NONE);
			beforeTreeComposite.setLayout(new FillLayout(SWT.VERTICAL));
			beforeTreeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			if (contentProvider instanceof IGraphicalContentProvider) {
				((IGraphicalContentProvider) contentProvider).createBefore(beforeTreeComposite);
			}
			if (labelProvider instanceof IGraphicalLabelProvider) {
				((IGraphicalLabelProvider) labelProvider).createBefore(beforeTreeComposite);
			}
			beforeTreeComposite.moveAbove(treeViewer.getControl());

			Label separator = new Label(toolbar, SWT.VERTICAL | SWT.SEPARATOR);
			GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 10).grab(false, false).applyTo(separator);

			// gets all wanted icons
			Composite iconsComposite = new Composite(toolbar, SWT.NONE);
			FillLayout iconsLayout = new FillLayout(SWT.HORIZONTAL);
			iconsLayout.spacing = 2;
			iconsComposite.setLayout(iconsLayout);
			if (contentProvider instanceof IGraphicalContentProvider) {
				((IGraphicalContentProvider) contentProvider).createViewerToolbar(iconsComposite);
			}
			if (labelProvider instanceof IGraphicalLabelProvider) {
				((IGraphicalLabelProvider) labelProvider).createViewerToolbar(iconsComposite);
			}
			// Hide separator if no button in toolbar
			if (0 == iconsComposite.getChildren().length) {
				separator.setVisible(false);
				((GridData) separator.getLayoutData()).exclude = true;
			}

			toolbar.moveAbove(treeViewer.getControl());

			// After the Viewer
			Composite afterTreeComposite = new Composite(parent, SWT.NONE);
			afterTreeComposite.setLayout(new FillLayout(SWT.VERTICAL));
			afterTreeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			if (labelProvider instanceof IGraphicalLabelProvider) {
				((IGraphicalLabelProvider) labelProvider).createAfter(afterTreeComposite);
			}
			if (contentProvider instanceof IGraphicalContentProvider) {
				((IGraphicalContentProvider) contentProvider).createAfter(afterTreeComposite);
			}
		}
	}

	/**
	 * Sets the description for this Dialog. The description is displayed on
	 * top of the dialog
	 *
	 * @param description
	 *            The description for this dialog
	 */
	public void setDescription(final String tabId, final String description) {
		descriptions.put(tabId, description);
	}

	/**
	 * Sets the description for this Dialog. The description is displayed on
	 * top of the dialog
	 *
	 * @param description
	 *            The description for this dialog
	 */
	@Override
	public void setDescription(final String description) {
		setDescription(defaultTabId, description);
	}

	/**
	 * Get the default TreeViewer used by this dialog
	 *
	 * @return
	 * 		The default TreeViewer associated to this dialog
	 */
	protected TreeViewer getViewer() {
		return getViewer(defaultTabId);
	}

	/**
	 * Get a treeviewer for a tab Id
	 * 
	 * @param tabId
	 *            The tab id of the viewer
	 */
	protected TreeViewer getViewer(final String tabId) {
		return treeViewers.get(tabId);
	}

	/**
	 * Sets the input object for this dialog's default TreeViewer
	 *
	 * @param input
	 *            The input.
	 */
	@Override
	public void setInput(final Object input) {
		this.input = input;
	}

	/**
	 * Sets the input object for this dialog's TreeViewer for an tab id.
	 *
	 * @param input
	 *            The input.
	 */
	private void doSetInput(final String TabId) {
		if (null != treeViewers.get(TabId)) {
			if (null == input) {
				treeViewers.get(TabId).setInput("");//$NON-NLS-1$
			} else {
				treeViewers.get(TabId).setInput(input);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	public void okPressed() {
		okPressed = true;// Fix the deselect when used of filter when we press ok.
		for (ICommitListener listener : commitListeners) {
			listener.commit(null);
		}
		super.okPressed();
	}

}
