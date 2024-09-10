/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.transformation.ui;

import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.StaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.papyrus.uml.extensionpoints.IRegisteredItem;
import org.eclipse.papyrus.uml.extensionpoints.Registry;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping;
import org.eclipse.papyrus.uml.m2m.qvto.common.transformation.IDependencyAnalysisHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Opens a dialog allowing the user to manipulate/change the URIMappings of the transformation
 * (Dependency repair)
 *
 * @author Camille Letavernier
 *
 */
public class URIMappingDialog extends SelectionDialog {

	MappingParameters mappingParameters, result;

	TableViewer viewer;

	LabelProviderService labelProviderService;

	private static final int BROWSE_WORKSPACE_ID = IDialogConstants.CLIENT_ID + 1;

	private static final int BROWSE_REGISTERED_ID = IDialogConstants.CLIENT_ID + 2;

	private static final int BROWSE_PROFILES_ID = IDialogConstants.CLIENT_ID + 3;

	List<URIMapping> allMappings;
	
	final IDependencyAnalysisHelper dependencyHelper;

	public URIMappingDialog(Shell shell, MappingParameters mappingParameters, IDependencyAnalysisHelper dependencyHelper) {
		super(shell);
		this.dependencyHelper = dependencyHelper;
		this.mappingParameters = mappingParameters;
	}

	@Override
	protected void configureShell(Shell shell) {
		setTitle("Some dependencies are missing");
		super.configureShell(shell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		labelProviderService = new LabelProviderServiceImpl();
		try {
			labelProviderService.startService();
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		Label description = new Label(area, SWT.WRAP);
		description.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		description.setText("Some elements in the following resources can't be resolved. Please choose the resources to replace them.");

		Composite self = new Composite(area, SWT.NONE);
		self.setLayout(new GridLayout(1, true));
		self.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Do a copy: if Cancel is pressed, it can be discarded
		result = EcoreUtil.copy(mappingParameters);
		setResult(Collections.singletonList(mappingParameters)); // Default result (If Cancel is pressed)

		Composite buttonsBarComposite = new Composite(self, SWT.NONE);

		GridLayout buttonsLayout = new GridLayout(0, false);
		buttonsLayout.marginWidth = 0;

		buttonsBarComposite.setLayout(buttonsLayout);
		buttonsBarComposite.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		Button browseWorkspace = createButton(buttonsBarComposite, BROWSE_WORKSPACE_ID, "", false);
		browseWorkspace.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("icons/browse_12x12.gif"));
		browseWorkspace.setToolTipText("Browse Workspace");
		Button browseRegistered = createButton(buttonsBarComposite, BROWSE_REGISTERED_ID, "", false);
		browseRegistered.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("icons/Add_12x12.gif"));
		browseRegistered.setToolTipText("Browse Registered Libraries");
		Button browseProfiles = createButton(buttonsBarComposite, BROWSE_PROFILES_ID, "", false);
		browseProfiles.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("icons/AddReg.gif"));
		browseProfiles.setToolTipText("Browse Registered Profiles");

		viewer = new TableViewer(self, SWT.FULL_SELECTION | SWT.BORDER);
		Table table = viewer.getTable();
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
		GridData tableData = new GridData(SWT.FILL, SWT.FILL, true, true);

		tableData.minimumHeight = 120;
		table.setLayoutData(tableData);
		table.setHeaderVisible(true);

		TableColumn locationColumn = new TableColumn(table, SWT.NONE);
		locationColumn.setText("Location");
		layout.addColumnData(new ColumnWeightData(50, 250, true));

		TableColumn newLocationColumn = new TableColumn(table, SWT.NONE);
		newLocationColumn.setText("New Location");
		layout.addColumnData(new ColumnWeightData(50, 250, true));

		viewer.setContentProvider(CollectionContentProvider.instance);

		viewer.setLabelProvider(new URIColumnsLabelProvider());
		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof URIMapping && e2 instanceof URIMapping) {
					URIMapping map1 = (URIMapping) e1;
					URIMapping map2 = (URIMapping) e2;

					return Collator.getInstance().compare(map1.getSourceURI(), map2.getSourceURI());
				}
				return 0;
			}
		});

		allMappings = dependencyHelper.flattenURIMappings(result);

		viewer.setInput(allMappings);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateControls();
			}
		});

		updateControls();

		return self;
	}

	@Override
	public boolean close() {
		try {
			labelProviderService.disposeService();
		} catch (Exception e) {
			Activator.log.error(e);
		}
		return super.close();
	}

	protected void updateControls() {
		boolean enableBrowse = !viewer.getSelection().isEmpty();

		getButton(BROWSE_REGISTERED_ID).setEnabled(enableBrowse);
		getButton(BROWSE_WORKSPACE_ID).setEnabled(enableBrowse);
		getButton(BROWSE_PROFILES_ID).setEnabled(enableBrowse);

		viewer.refresh();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case BROWSE_REGISTERED_ID:
			browseRegisteredLibraries();
			return;
		case BROWSE_WORKSPACE_ID:
			browseWorkspaceLibraries();
			return;
		case BROWSE_PROFILES_ID:
			browseRegisteredProfiles();
			return;
		}

		super.buttonPressed(buttonId);
	}

	protected URIMapping getSelectedMapping() {
		ISelection selection = viewer.getSelection();
		if (selection.isEmpty()) {
			return null;
		}

		if (selection instanceof IStructuredSelection) {
			Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
			if (selectedElement instanceof URIMapping) {
				return (URIMapping) selectedElement;
			}
		}

		return null;
	}

	protected void browseWorkspaceLibraries() {
		if (getSelectedMapping() == null) {
			return;
		}

		Map<String, String> extensionFilters = new LinkedHashMap<String, String>();
		extensionFilters.put("*.uml", "UML (*.uml)");
		extensionFilters.put("*", "All (*)");

		TreeSelectorDialog dialog = new TreeSelectorDialog(getShell());
		dialog.setTitle("Browse Workspace");
		dialog.setDescription("Select a library in the workspace.");
		WorkspaceContentProvider workspaceContentProvider = new WorkspaceContentProvider();
		workspaceContentProvider.setExtensionFilters(extensionFilters);
		dialog.setContentProvider(workspaceContentProvider);

		dialog.setLabelProvider(labelProviderService.getLabelProvider());


		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result == null || result.length == 0) {
				return;
			}

			Object selectedFile = result[0];

			if (selectedFile instanceof IFile) {
				IPath filePath = ((IFile) selectedFile).getFullPath();
				URI workspaceURI = URI.createPlatformResourceURI(filePath.toString(), true);

				replaceSelectionWith(workspaceURI);
			}
		}
	}

	protected void replaceSelectionWith(URI targetURI) {
		URIMapping mapping = getSelectedMapping();
		mapping.setTargetURI(targetURI.toString());
		updateControls();
	}

	protected void browseRegisteredLibraries() {
		browseRegisteredItems(Registry.getRegisteredLibraries(), "Browse Registered Libraries", "Select one of the registered libraries below.");
	}

	protected void browseRegisteredProfiles() {
		browseRegisteredItems(Registry.getRegisteredProfiles(), "Browse Registered Profiles", "Select one of the registered profiles below.");
	}

	protected void browseRegisteredItems(Collection<? extends IRegisteredItem> items, String dialogTitle, String dialogDescription) {
		TreeSelectorDialog dialog = new TreeSelectorDialog(getShell());
		dialog.setTitle(dialogTitle);
		dialog.setDescription(dialogDescription);
		dialog.setContentProvider(new EncapsulatedContentProvider(new StaticContentProvider(items.toArray(new IRegisteredItem[0]))));
		dialog.setLabelProvider(new RegisteredItemLabelProvider());

		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result == null || result.length == 0) {
				return;
			}

			Object selectedElement = result[0];
			if (selectedElement instanceof IRegisteredItem) {
				IRegisteredItem item = (IRegisteredItem) selectedElement;

				replaceSelectionWith(item.getUri());
			}
		}
	}

	@Override
	protected void okPressed() {
		dependencyHelper.propagateURIMappings(allMappings, result);
		setResult(Collections.singletonList(result)); // Set the new result
		super.okPressed();
	}

	

	private class URIColumnsLabelProvider extends ColumnLabelProvider {

		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();

			URIMapping mapping = (element instanceof URIMapping) ? (URIMapping) element : null;

			switch (cell.getColumnIndex()) {
			case 0:
				updateLocation(cell, mapping);
				break;
			case 1:
				updateNewLocation(cell, mapping);
				break;
			}

		}

		public void updateLocation(ViewerCell cell, URIMapping mapping) {
			String location = "Unknown";
			if (mapping != null) {
				location = mapping.getSourceURI();
			}

			cell.setText(location);
		}

		public void updateNewLocation(ViewerCell cell, URIMapping mapping) {
			String location = "";

			if (mapping.getTargetURI() != null) {
				location = mapping.getTargetURI();
			}

			cell.setText(location);
		}
	}

}
