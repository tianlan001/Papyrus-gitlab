/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references.views;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.views.references.Activator;
import org.eclipse.papyrus.views.references.Messages;
import org.eclipse.papyrus.views.references.actions.GoToAction;
import org.eclipse.papyrus.views.references.constants.ReferencesViewConstants;
import org.eclipse.papyrus.views.references.providers.ReferencesHeaderLabelProvider;
import org.eclipse.papyrus.views.references.providers.ReferencesResultContentProvider;
import org.eclipse.papyrus.views.references.providers.ReferencesResultLabelProvider;
import org.eclipse.papyrus.views.references.utils.HandleReferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

/**
 * The 'References' view
 */
public class ReferencesView extends ViewPart {

	/**
	 * ID of this view.
	 */
	public static final String ID = "org.eclipse.papyrus.views.references.ReferencesView"; //$NON-NLS-1$

	/**
	 * The expand all image descriptor.
	 */
	private static final ImageDescriptor COLLAPSE_ALL_IMAGE_DESCRIPTOR = Activator
			.imageDescriptorFromPlugin(ReferencesViewConstants.PLUGIN_ORG_ECLIPSE_SEARCH, ReferencesViewConstants.ICON_COLLAPSE_ALL);

	/**
	 * The expand all image descriptor.
	 */
	private static final ImageDescriptor EXPAND_ALL_IMAGE_DESCRIPTOR = Activator
			.imageDescriptorFromPlugin(ReferencesViewConstants.PLUGIN_ORG_ECLIPSE_SEARCH, ReferencesViewConstants.ICON_EXPAND_ALL);

	/**
	 * The Refresh image descriptor.
	 */
	private static final ImageDescriptor REFRESH_IMAGE_DESCRIPTOR = Activator
			.imageDescriptorFromPlugin(ReferencesViewConstants.PLUGIN_ORG_ECLIPSE_SEARCH, ReferencesViewConstants.ICON_REFRESH);

	/**
	 * The Clear All image descriptor.
	 */
	private static final ImageDescriptor CLEAR_ALL_IMAGE_DESCRIPTOR = Activator
			.imageDescriptorFromPlugin(ReferencesViewConstants.PLUGIN_ORG_ECLIPSE_SEARCH, ReferencesViewConstants.ICON_CLEAR_ALL);

	/**
	 * The Link with Editor image descriptor.
	 */
	private static final ImageDescriptor LINK_WITH_EDITOR_IMAGE_DESCRIPTOR = Activator
			.imageDescriptorFromPlugin(ReferencesViewConstants.PLUGIN_ORG_ECLIPSE_UI, ReferencesViewConstants.ICON_LINK_WITH_EDITOR);

	/**
	 * TreeViewer used to display the incoming result of the search.
	 */
	private TreeViewer treeViewerReferences;

	/**
	 * The global result label provider.
	 */
	private ReferencesResultLabelProvider labelResultProvider;

	/**
	 * The label provider used to format the header.
	 */
	private ReferencesHeaderLabelProvider headerLabelProvider;

	/**
	 * Indicates whether the 'Toggle Linking' button is activated.
	 */
	private boolean linkingEnabled;

	/**
	 * The selection listener used to check the selection changed in the Papyrus
	 * Diagram Editor.
	 */
	private ISelectionListener editorSelectionListener;

	/**
	 * The FormToolkit containing on the view.
	 */
	private FormToolkit toolkit;

	/**
	 * The global scrolled form used to display the references.
	 */
	private ScrolledForm scrolledForm;

	/**
	 * Label used to display a message for a multi selection.
	 */
	private Label multiSelectionLabel;

	/**
	 * Layout set to the body of the ScrolledForm used to switch between the
	 * TreeViewer and a Label.
	 */
	private StackLayout stackLayout;

	/**
	 * The Go To action.
	 */
	private GoToAction goToAction;

	/**
	 * The Active Selection before the selection of the view References.
	 */
	private ISelection oldSelection;

	/**
	 * Constructor.
	 */
	public ReferencesView() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		super.init(site, memento);

		linkingEnabled = true;

		editorSelectionListener = createSelectionListener();
		site.getPage().addSelectionListener(editorSelectionListener);
	}

	/**
	 * To create the selection listener to check the selection changed on the
	 * editor.
	 *
	 * @return The ISelectionListener.
	 */
	private ISelectionListener createSelectionListener() {
		return new ISelectionListener() {
			@Override
			public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
				if (!(part instanceof ReferencesView)) {
					oldSelection = selection;
					if (linkingEnabled) {
						searchSelectionReferences(selection);
					}
				}
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(final Composite parent) {
		linkingEnabled = Activator.getDefault().getToogleLinkingSetting();

		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		parent.setLayout(gridLayout);

		toolkit = new FormToolkit(parent.getDisplay());
		scrolledForm = toolkit.createScrolledForm(parent);
		scrolledForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		toolkit.decorateFormHeading(scrolledForm.getForm());

		final Composite body = scrolledForm.getBody();

		stackLayout = new StackLayout();
		stackLayout.marginHeight = 5;

		body.setLayout(stackLayout);

		createTreeViewerReferences(body);

		stackLayout.topControl = treeViewerReferences.getTree();

		multiSelectionLabel = toolkit.createLabel(body, Messages.ReferencesView_MultiSelection);

		// multiSelectionLabel.setFont(); TODO

		getSite().setSelectionProvider(treeViewerReferences);
		createActions();
		createContextMenu();

		scrolledForm.setVisible(false);
	}

	/**
	 * Create the tree viewer containing all the incoming references.
	 */
	private void createTreeViewerReferences(final Composite parent) {
		treeViewerReferences = new TreeViewer(parent, SWT.NONE);
		treeViewerReferences.setContentProvider(new ReferencesResultContentProvider());
		treeViewerReferences.setLabelProvider(getResultLabelProvider());
		treeViewerReferences.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				goToAction.run();
			}
		});
	}

	/**
	 * Get the provider that can display the name and image corresponding to the
	 * selection.
	 *
	 * @return The Result Label Provider.
	 */
	private final ReferencesResultLabelProvider getResultLabelProvider() {
		if (null == labelResultProvider) {
			labelResultProvider = new ReferencesResultLabelProvider();
		}
		return labelResultProvider;
	}

	/**
	 * Get the Label Provider for the Header.
	 *
	 * @return The Label Provider.
	 */
	private ReferencesHeaderLabelProvider getHeaderLabelProvider() {
		if (null == headerLabelProvider) {
			headerLabelProvider = new ReferencesHeaderLabelProvider();
		}
		return headerLabelProvider;
	}

	/**
	 * Create all the actions to add on the Toolbar of the view.
	 */
	private void createActions() {
		goToAction = new GoToAction();
		treeViewerReferences.addSelectionChangedListener(goToAction);

		final IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		if (null != toolBarManager) {
			toolBarManager.add(createClearAllAction());
			toolBarManager.add(new Separator());
			toolBarManager.add(createRefreshAction());
			toolBarManager.add(createToggleLinkingAction());
			toolBarManager.add(new Separator());
			toolBarManager.add(createExpandAllAction());
			toolBarManager.add(createCollapseAllAction());
		}
	}

	/**
	 * Creates the 'Link with Editor' action.
	 *
	 * @return The 'Link with Editor' action.
	 */
	private IAction createToggleLinkingAction() {
		final IAction toggleLinkingAction = new Action(Messages.ReferencesView_LinkWithEditorLabel, SWT.TOGGLE) {
			@Override
			public void run() {
				linkingEnabled = isChecked();
				Activator.getDefault().setToggleEditorSetting(linkingEnabled);
				if (isChecked()) {
					if (null != oldSelection) {
						searchSelectionReferences(oldSelection);
					}
				}
			}
		};

		toggleLinkingAction.setChecked(linkingEnabled);
		toggleLinkingAction.setImageDescriptor(LINK_WITH_EDITOR_IMAGE_DESCRIPTOR);

		return toggleLinkingAction;
	}

	/**
	 * Creates the 'Expand All' action.
	 *
	 * @return The 'Expand All' action.
	 */
	private IAction createExpandAllAction() {
		final IAction expandAllAction = new Action(Messages.ReferencesView_ExpandAllLabel, EXPAND_ALL_IMAGE_DESCRIPTOR) {
			@Override
			public void run() {
				if (null != treeViewerReferences) {
					treeViewerReferences.expandAll();
				}
			}
		};

		return expandAllAction;
	}

	/**
	 * Creates the 'Collapse All' action.
	 *
	 * @return The 'Collapse All' action.
	 */
	private IAction createCollapseAllAction() {
		final IAction collapseAllAction = new Action(Messages.ReferencesView_CollapseAllLabel, COLLAPSE_ALL_IMAGE_DESCRIPTOR) {
			@Override
			public void run() {
				if (null != treeViewerReferences) {
					treeViewerReferences.collapseAll();
				}
			}
		};

		return collapseAllAction;
	}

	/**
	 * Creates the 'Refresh' action.
	 *
	 * @return The 'Refresh' action.
	 */
	private IAction createRefreshAction() {
		final IAction refreshAction = new Action(Messages.ReferencesView_RefreshLabel, REFRESH_IMAGE_DESCRIPTOR) {
			@Override
			public void run() {
				if (null != oldSelection) {
					searchSelectionReferences(oldSelection);
				}
			}
		};

		return refreshAction;
	}

	/**
	 * Creates the 'Clear All' action.
	 *
	 * @return The 'Clear All' action.
	 */
	private IAction createClearAllAction() {
		final IAction clearAllAction = new Action(Messages.ReferencesView_ClearAllLabel, CLEAR_ALL_IMAGE_DESCRIPTOR) {
			@Override
			public void run() {
				clearView();
			}
		};

		return clearAllAction;
	}

	/**
	 * Clear the content of the view.
	 */
	private void clearView() {
		if (null != scrolledForm) {
			scrolledForm.setVisible(false);
		}

		if (null != treeViewerReferences) {
			treeViewerReferences.setInput(null);
			changeStackLayoutTopControl(treeViewerReferences.getTree());
		}
	}

	/**
	 * Change the top control of the stackLoayout of the body of the form.
	 *
	 * @param topControl
	 *            The control to show on the top.
	 */
	private void changeStackLayoutTopControl(final Control topControl) {
		if (stackLayout.topControl != topControl) {
			stackLayout.topControl = topControl;
			scrolledForm.getBody().layout(true);
		}
	}

	/**
	 * Create the context menu of the TreeViewer.
	 */
	private void createContextMenu() {
		final MenuManager contextMenu = new MenuManager();
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(goToAction);
			}
		});

		final Menu menu = contextMenu.createContextMenu(treeViewerReferences.getControl());
		treeViewerReferences.getControl().setMenu(menu);
		getSite().registerContextMenu(ReferencesView.ID, contextMenu, treeViewerReferences);
	}

	/**
	 * Show the result of the search.
	 *
	 * @param references
	 *            The references of the searched object.
	 * @param text
	 *            The name of the searched object.
	 */
	public void showResult(final Object references, final Object object) {
		if ((null != scrolledForm) && (!scrolledForm.isDisposed())) {
			final String objectName = getHeaderLabelProvider().getText(object);
			scrolledForm.setText(objectName != null ? objectName : "<empty>"); //$NON-NLS-1$
			scrolledForm.setImage(getHeaderLabelProvider().getImage(object));
			scrolledForm.setVisible(true);
		}

		if ((null != treeViewerReferences) && (!treeViewerReferences.getTree().isDisposed())) {
			treeViewerReferences.setInput(references);
			treeViewerReferences.expandAll();
			changeStackLayoutTopControl(treeViewerReferences.getTree());
		}
	}

	/**
	 * Run the search of the references corresponding to the selection.
	 *
	 * @param selection
	 */
	private void searchSelectionReferences(final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (1 == structuredSelection.size()) {
				final Object firstElement = structuredSelection.getFirstElement();
				final EObject eObjectToSearch = EMFHelper.getEObject(firstElement);
				if (null != eObjectToSearch) {
					HandleReferences.INSTANCE.getViewAndLaunch(eObjectToSearch);
				}
			} else if (1 < structuredSelection.size()) {
				scrolledForm.setText(ReferencesViewConstants.NAME_FORM_MULTI_SELECTION);
				scrolledForm.setImage(null);
				changeStackLayoutTopControl(multiSelectionLabel);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		super.dispose();

		if (null != editorSelectionListener) {
			getSite().getPage().removeSelectionListener(editorSelectionListener);
		}

		if (null != toolkit) {
			toolkit.dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		// Do Nothing
	}
}
