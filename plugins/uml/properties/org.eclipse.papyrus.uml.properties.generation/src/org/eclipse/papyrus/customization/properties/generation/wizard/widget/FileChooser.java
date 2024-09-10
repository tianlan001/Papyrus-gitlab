/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 573987
 *****************************************************************************/
package org.eclipse.papyrus.customization.properties.generation.wizard.widget;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.customization.properties.generation.messages.Messages;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.WorkspaceContentProvider;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * A Widget for selecting or creating a file in the workspace
 *
 * @author Camille Letavernier
 */
public class FileChooser extends Composite implements SelectionListener, Listener {

	private Text text;

	private Button browse;

	private IFile currentFile;

	private Map<String, String> filters = new LinkedHashMap<>();

	private Set<Listener> listeners = new HashSet<>();

	private boolean newFile;

	private IObservableValue<String> textObservable;

	/**
	 * Constructs a new FileChooser in the given Composite
	 *
	 * @param parent
	 *            The composite in which the FileChooser is created
	 * @param newFile
	 *            True if the fileChooser allows the user to create a new file,
	 *            false if he should select an existing one
	 */
	public FileChooser(Composite parent, boolean newFile) {
		super(parent, SWT.NONE);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		setLayout(layout);

		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		text.addListener(SWT.FocusOut, this);
		browse = new Button(this, SWT.PUSH);
		browse.setText(Messages.FileChooser_browseWorkspace);
		browse.addSelectionListener(this);
		this.newFile = newFile;

		IWidgetValueProperty<Text, String> prop = WidgetProperties.text(SWT.Modify);
		textObservable = prop.observeDelayed(600, text);
	}

	/**
	 * @return the selected file path
	 */
	public String getFilePath() {
		if (text.isDisposed()) {
			return null;
		}
		String path = text.getText();
		if (path.trim().equals("")) { //$NON-NLS-1$
			return null;
		}
		return path.trim();
	}

	/**
	 * Sets the file extensions that this FileChooser accepts
	 * Files that don't match one of these extensions will be hidden
	 *
	 * @param extensions
	 */
	public void setFilterExtensions(String[] extensions) {
		filters.clear();
		for (String ext : extensions) {
			filters.put(ext, ext);
		}
	}

	/**
	 * Sets the file extensions that this FileChooser accepts.
	 * Files that don't match one of these extensions will be hidden.
	 *
	 * Extensions is a map in which the key is the filter (Accepting * and ? as wildcards)
	 * and value is the displayed label. The filter may contain several patterns, separated
	 * with a semi-colon (;).
	 *
	 * Example: <*.uml, UML>, <*.profile.uml;*.xmi, UML Profile>
	 *
	 * @param extensions
	 *            A map of (filter name, extension filter)
	 *
	 * @since 2.1
	 */
	public void setFilterExtensions(Map<String, String> extensions) {
		filters.clear();
		filters.putAll(extensions);
	}

	@Override
	public void handleEvent(Event event) {
		notifyChange();
	}

	/**
	 * Add a listener to this widget. The listener will be notified when the user
	 * choose a new file
	 *
	 * @param listener
	 */
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		Object[] result = new Object[0];

		if (newFile) {

			List<ViewerFilter> viewerFilter = Collections.singletonList(new ExtensionFilter(filters.keySet().toArray(new String[0])));

			IFile file = WorkspaceResourceDialog.openNewFile(getShell(), null, null, null, viewerFilter);
			if (file != null) {
				result = new IFile[] { file };
			}
		} else {
			LabelProviderService labelProviderService = new LabelProviderServiceImpl();
			try {
				labelProviderService.startService();
			} catch (ServiceException ex) {
				Activator.log.error(ex);
			}

			ILabelProvider labelProvider = labelProviderService.getLabelProvider();

			IFile currentFile = FileUtil.getIFile(text.getText());

			TreeSelectorDialog dialog = new TreeSelectorDialog(getShell());
			dialog.setTitle("File Selection");

			WorkspaceContentProvider contentProvider = new WorkspaceContentProvider();

			contentProvider.setExtensionFilters(filters);

			dialog.setContentProvider(contentProvider);
			dialog.setLabelProvider(labelProvider);


			if (currentFile != null && currentFile.exists()) {
				dialog.setInitialSelections(new IFile[] { currentFile });
			}

			int code = dialog.open();
			if (code == Window.OK) {
				result = dialog.getResult();
			}
			try {
				labelProviderService.disposeService();
			} catch (ServiceException ex) {
				Activator.log.error(ex);
			}
		}

		if (result.length > 0) {
			Object file = result[0];
			if (file instanceof IFile) {
				setFile((IFile) file);
			}
		}
	}

	private void notifyChange() {
		for (Listener listener : listeners) {
			listener.handleEvent(null);
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Nothing
	}

	public IObservableValue getObservableValue() {
		return textObservable;
	}

	public void setText(String s) {
		text.setText(s);
	}

	/**
	 * Set the currently selected file.
	 *
	 * @param file
	 *            the currently selected file. May be {@code null} to clear the selection
	 */
	public void setFile(IFile file) {
		if (!Objects.equals(file, currentFile) && !text.isDisposed()) {
			currentFile = file;
			if (file == null) {
				text.setText(""); //$NON-NLS-1$
			} else {
				text.setText(file.getFullPath().toString());
			}

			// Trigger the update immediately
			getObservableValue().getValue();

			notifyChange();
		}
	}
}
