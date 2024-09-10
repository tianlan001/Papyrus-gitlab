/*****************************************************************************
 * Copyright (c) 2016, 2020 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.documentation.widgets;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.widgets.editors.InputDialog;
import org.eclipse.papyrus.infra.widgets.editors.MultipleValueSelectionDialog;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.papyrus.views.documentation.Activator;
import org.eclipse.papyrus.views.documentation.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;


/**
 * Create a composite that manage resources through a string list.
 */
public class DocumentationResourceEditor implements SelectionListener {

	/** The icon to delete entry. */
	private static final String DELETE_ICON = "/icons/Delete_12x12.gif"; //$NON-NLS-1$

	/** The icon to add remote entry. */
	public static final String REMOTE_ICON = "icons/Remote.gif"; //$NON-NLS-1$

	/** The icon to add WS entry. */
	public static final String BROWSE_WORKSPACE_ICON = "icons/browse-workspace_12x12.png"; //$NON-NLS-1$

	/** The icon to add file system entry. */
	public static final String BROWSE_FILE_SYSTEM_ICON = "icons/browse-filesystem_12x12.png"; //$NON-NLS-1$


	/** The remote url prefix. */
	private static final String HTTP = "http://"; //$NON-NLS-1$

	/** the widget factory. */
	TabbedPropertySheetWidgetFactory widgetFactory = new TabbedPropertySheetWidgetFactory();

	/** The toolbar composite. */
	private Composite toolbar;

	/** The browse file system button. */
	private Button browseFileSystemButton;

	/** The browse workspace button. */
	private Button browseWorkspaceButton;

	/** The delete button. */
	private Button removeButton;

	/** The add button. */
	private Button addRemoteButton;

	/** The list of resources */
	private List<String> resources = new ArrayList<>();

	/** the table viewer */
	private TableViewer tableViewer;

	/** the label provider. */
	private ILabelProvider labelProvider;

	/**
	 * Constructor.
	 */
	public DocumentationResourceEditor(final Composite parent) {

		LabelProviderService labelProviderService = new LabelProviderServiceImpl();
		try {
			labelProviderService.startService();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}

		labelProvider = labelProviderService.getLabelProvider();

		Composite composite = widgetFactory.createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(false).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		toolbar = widgetFactory.createComposite(composite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(toolbar);
		GridLayoutFactory.fillDefaults().spacing(0, 0).numColumns(5).applyTo(toolbar);

		CLabel fillerComposite = widgetFactory.createCLabel(toolbar, Messages.DocumentationResourceEditor_Message);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(fillerComposite);

		createListControls();

		Table table = new Table(composite, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(this);

		tableViewer = new TableViewer(table);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(labelProvider);

		tableViewer.setInput(resources);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableViewer.getTable());

		composite.layout(true);

		toolbar.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_CYAN));
	}


	/**
	 * Creates controls.
	 */
	protected void createListControls() {
		addRemoteButton = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImageFromPlugin(REMOTE_ICON), Messages.DocumentationResourceEditor_AddButtonTooltip);
		browseFileSystemButton = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImageFromPlugin(BROWSE_FILE_SYSTEM_ICON), Messages.DocumentationResourceEditor_BrowseSystemButtonTooltip);
		browseWorkspaceButton = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImageFromPlugin(BROWSE_WORKSPACE_ICON), Messages.DocumentationResourceEditor_BrowseWSButtonTooltip);
		removeButton = createButton(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(DELETE_ICON), Messages.DocumentationResourceEditor_RemoveButtonTootip);
	}

	protected Button createButton(final Image image, final String toolTipText) {
		Button button = new Button(toolbar, SWT.PUSH);
		button.setImage(image);
		button.addSelectionListener(this);
		button.setToolTipText(toolTipText);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(button);
		return button;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(final SelectionEvent e) {
		if (e.widget == browseFileSystemButton) {
			browseFileSystem();
		} else if (e.widget == browseWorkspaceButton) {
			browseWorkspace();
		} else if (e.widget == addRemoteButton) {
			addRemote();
		} else if (e.widget == removeButton) {
			remove();
		}
		refresh();
	}

	/**
	 * remove action
	 */
	protected void remove() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();

		if (0 < selection.size()) {
			TableItem[] selectedResources = tableViewer.getTable().getSelection();
			for (int i = 0; i < selectedResources.length; i++) {
				TableItem selectedItem = selectedResources[i];
				Table parentTable = selectedItem.getParent();
				final int index = parentTable.indexOf(selectedItem);
				parentTable.remove(index);
				resources.remove(index);
			}

		}
	}

	/**
	 * Add a resource by browse file system.
	 */
	protected void browseFileSystem() {
		FileDialog dialog = new FileDialog(toolbar.getShell(), SWT.MULTI | SWT.OPEN);
		dialog.setText(Messages.DocumentationResourceEditor_AddFileSystemDialogTitle);
		if (null != dialog.open()) {
			String filterPath = dialog.getFilterPath();
			for (String fileName : dialog.getFileNames()) {
				resources.add(filterPath + File.separator + fileName);
			}
		}
	}

	/**
	 * Add a resource by browse workspace.
	 */
	protected void browseWorkspace() {
		ReferenceSelector selector = new ReferenceSelector();
		selector.setLabelProvider(labelProvider);

		// Prepare the WorkspaceContentProvider and use the right filters
		WorkspaceContentProvider contentProvider = new WorkspaceContentProvider();
		contentProvider.setExtensionFilters(new LinkedHashMap<String, String>()); // Reset the default filters

		selector.setContentProvider(contentProvider);

		MultipleValueSelectionDialog dialog = new MultipleValueSelectionDialog(toolbar.getShell(), selector);
		dialog.setTitle(Messages.DocumentationResourceEditor_AddWSDialogTitle);
		dialog.setLabelProvider(labelProvider);
		dialog.setOrdered(true);
		dialog.setUnique(true);
		selector.setUnique(true);

		int code = dialog.open();
		if (Window.OK == code) {
			Object[] result = dialog.getResult();
			if (0 < result.length) {
				for (Object file : result) {
					if (file instanceof IFile) {
						resources.add(FileUtil.getPath((IFile) file, false));
					}
				}
			}
		}
	}

	/**
	 * Add a remote resource.
	 */
	protected void addRemote() {

		SelectionDialog dialog = new InputDialog(toolbar.getShell(), Messages.DocumentationResourceEditor_AddRemoteDialogTitle, Messages.DocumentationResourceEditor_AddRemoteDialogMessage, HTTP, null);

		if (dialog.OK == dialog.open()) {
			if (0 < dialog.getResult().length) {
				resources.add((String) dialog.getResult()[0]);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {
		edit();
	}

	/**
	 * Handle edit Action.
	 */
	protected void edit() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();

		if (1 == selection.size()) {

			TableItem selectedItem = tableViewer.getTable().getSelection()[0];
			Table parentTable = selectedItem.getParent();

			final int index = parentTable.indexOf(selectedItem);
			final Object currentValue = selection.getFirstElement();

			SelectionDialog dialog = new InputDialog(toolbar.getShell(), Messages.DocumentationResourceEditor_EditResourceDialogTitle, Messages.DocumentationResourceEditor_EditResourceDialogMessage, (String) currentValue, null);

			if (dialog.OK == dialog.open()) {
				if (0 < dialog.getResult().length) {

					String newValue = (String) dialog.getResult()[0];
					if (newValue != currentValue && newValue != null) {
						resources.remove(index);
						resources.add(index, newValue);
					}
				}
			}
		}
	}

	/**
	 * Set the list of resources.
	 */
	public void setResources(final List<String> resources) {
		this.resources = resources;
		refresh();
	}

	/**
	 * Gets the list of resources.
	 */
	public List<String> getResources() {
		return resources;
	}

	/**
	 * Refresh the resource list.
	 */
	public void refresh() {
		tableViewer.setInput(resources);
		tableViewer.refresh();
	}

}
