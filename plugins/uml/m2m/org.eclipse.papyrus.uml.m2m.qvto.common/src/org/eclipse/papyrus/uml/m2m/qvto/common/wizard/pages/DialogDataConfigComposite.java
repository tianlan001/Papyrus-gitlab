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
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.wizard.pages;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * Actual composite used to display the previously selected elements and the migration options
 * 
 * @author Quentin Le Menez
 *
 */
public class DialogDataConfigComposite extends ImportConfigComposite {

	protected AbstractDialogData dialogData;

	protected SelectionListener buttonListener;

	protected Button selectAll;

	protected Button deselectAll;

	/**
	 *
	 * Constructor used when a DialogData class is employed to store the different informations of the dialog
	 *
	 * @param parent
	 *            The parent composite
	 * @param style
	 *            The swt style used for this ConfigurationComposite
	 * @param dialogData
	 *            The DialogData in which is stored all the necessary informations
	 */
	public DialogDataConfigComposite(Composite parent, int style, AbstractDialogData dialogData) {
		super(parent, style, dialogData.getConfig());
		this.dialogData = dialogData;
		this.setViewerInput(dialogData.getSelectedFiles());
	}


	/**
	 *
	 * Fills the composite with the selection buttons
	 *
	 * @param parent
	 *            The parent composite
	 */
	@Override
	protected void createSelectionButtons(Composite parent) {
		Composite buttonsComposite = new Composite(parent, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout());

		buttonListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				fireButtonSelectionEvent(event);
			}
		};

		selectAll = new Button(buttonsComposite, SWT.PUSH);
		selectAll.setData(true);
		selectAll.setText("Select All");
		selectAll.addSelectionListener(buttonListener);
		selectAll.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		deselectAll = new Button(buttonsComposite, SWT.PUSH);
		deselectAll.setData(false);
		deselectAll.setText("Deselect All");
		deselectAll.addSelectionListener(buttonListener);
		deselectAll.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

	}

	/**
	 * 
	 * Handles the selections inside the tableViewer
	 * 
	 * @param event
	 *            The event linked to the tableViewer's selection buttons
	 */
	protected void fireButtonSelectionEvent(SelectionEvent event) {
		listViewer.setAllChecked((Boolean) ((Button) event.widget).getData());
		setTransformationFiles();
		setUncheckedFiles();

		if (dialogData != null) {
			dialogData.setTransformationFiles(transformationFiles);
			dialogData.setUncheckedFiles(uncheckedFiles);
		}
	}

	@Override
	protected void fireSelectionEvent(SelectionChangedEvent event) {
		transformationFiles = new LinkedList<Object>(Arrays.asList(listViewer.getCheckedElements()));
		setUncheckedFiles();

		if (dialogData != null) {
			dialogData.setTransformationFiles(transformationFiles);
			dialogData.setUncheckedFiles(uncheckedFiles);
		}
	}

	@Override
	public void setViewerInput(Collection<Object> selectedFiles) {
		listViewer.setInput(selectedFiles);

		if (dialogData != null) {
			if (dialogData.getUnSelectionArray() == null) {
				// Default selection when opening the viewer without previous executions
				listViewer.setAllChecked(true);
			}
			else {
				// Recall the last unselected files to update the display
				Collection<String> previousUnSelection = Arrays.asList(dialogData.getUnSelectionArray());
				for (Object object : selectedFiles) {
					if (object instanceof IFile) {
						IFile ifile = (IFile) object;
						String ifilePath = FileUtil.getPath(ifile, true);
						if (previousUnSelection.contains(ifilePath)) {
							listViewer.setChecked(object, false);
						}
						else {
							listViewer.setChecked(object, true);
						}
					}
					else if (object instanceof File) {
						File file = (File) object;
						String filePath = file.getAbsolutePath();
						if (previousUnSelection.contains(filePath)) {
							listViewer.setChecked(object, false);
						}
						else {
							listViewer.setChecked(object, true);
						}
					}
				}
			}

			setTransformationFiles();
			setUncheckedFiles();

			dialogData.setTransformationFiles(transformationFiles);
			dialogData.setUncheckedFiles(uncheckedFiles);
		}
	}

	/**
	 * 
	 * Updates the unchecked files in the wizard's dialog settings to remember the last selection when reopening the wizard
	 * 
	 */
	protected void setUncheckedFiles() {
		if (dialogData != null) {
			uncheckedFiles = new LinkedList<Object>();
			for (Object object : dialogData.getAllSelectedFiles()) {
				if (!transformationFiles.contains(object)) {
					uncheckedFiles.add(object);
				}
			}
			dialogData.setUncheckedFiles(uncheckedFiles);
		}
	}

	@Override
	public void dispose() {
		if (buttonListener != null) {
			selectAll.removeSelectionListener(buttonListener);
			deselectAll.removeSelectionListener(buttonListener);
		}

		super.dispose();
	}

}
