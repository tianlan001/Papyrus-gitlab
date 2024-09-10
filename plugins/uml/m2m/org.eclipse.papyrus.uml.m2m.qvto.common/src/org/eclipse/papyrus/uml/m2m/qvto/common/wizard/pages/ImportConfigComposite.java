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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesDisplayHelper;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * Generic and reusable composite used to display the previously selected elements and the migration options
 * 
 * @author Quentin Le Menez
 *
 */
public abstract class ImportConfigComposite extends Composite {

	protected ThreadConfig config;

	protected Collection<Object> transformationFiles;

	protected CheckboxTableViewer listViewer;

	protected ISelectionChangedListener listListener;

	protected DisplayEngine displayEngine;

	protected Collection<Object> uncheckedFiles;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite
	 * @param style
	 *            The swt style used for this ConfigurationComposite
	 * @param config
	 *            The configuration used to display the transformation options
	 */
	public ImportConfigComposite(Composite parent, int style, ThreadConfig config) {
		super(parent, style);
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.config = config;

		Composite filesComposite = new Composite(this, SWT.BORDER);
		filesComposite.setLayout(new FillLayout());
		filesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite configComposite = new Composite(this, SWT.BORDER);
		configComposite.setLayout(new FillLayout());
		configComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		createFilesComposite(filesComposite);

		createParamComposite(configComposite);
	}


	/**
	 *
	 * Fills the selection area with all the files selected previously
	 *
	 * @param parent
	 *            The parent composite
	 */
	protected void createFilesComposite(Composite parent) {
		Composite listComposite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		listComposite.setLayout(gridLayout);

		listViewer = CheckboxTableViewer.newCheckList(listComposite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData viewerGrid = new GridData(SWT.FILL, SWT.FILL, true, true);
		listViewer.getTable().setLayoutData(viewerGrid);

		listViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IFile) {
					return FileUtil.getPath((IFile) element, true);
				} else if (element instanceof File) {
					return ((File) element).getAbsolutePath();
				} else {
					return "Not an IFile, wrong type to transform";
				}
			}
		});

		listViewer.setContentProvider(new ArrayContentProvider());

		listListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionEvent(event);
			}
		};

		listViewer.addSelectionChangedListener(listListener);

		setTransformationFiles();

		createSelectionButtons(listComposite);

	}


	/**
	 * 
	 * Used to update the display from a changed selection in the ConfigPage
	 * 
	 * @param selectedFiles
	 *            The new list of selected files
	 */
	abstract void setViewerInput(Collection<Object> selectedFiles);

	/**
	 * 
	 * Abstract method to be implemented by the child in order to create the useful buttons to manipulate the tableViewer's elements
	 * 
	 * @param parent
	 *            The parent composite in which the new buttons will be created
	 */
	abstract void createSelectionButtons(Composite parent);

	/**
	 * 
	 * Abstract method to be implemented by the child in order to handle the transformation options
	 * 
	 * @param event
	 *            The event linked to the configuration's selection buttons
	 */
	abstract void fireSelectionEvent(SelectionChangedEvent event);


	/**
	 * 
	 * Updates the list of files to be transformed
	 * 
	 */
	public void setTransformationFiles() {
		transformationFiles = new LinkedList<Object>(Arrays.asList(listViewer.getCheckedElements()));
	}


	/**
	 *
	 * Fills the composite with the configuration parameters
	 *
	 * @param parent
	 *            The parent composite
	 */
	public void createParamComposite(Composite parent) {
		displayEngine = PropertiesDisplayHelper.display(config, parent);
	}


	@Override
	public void dispose() {
		if (displayEngine != null) {
			displayEngine.dispose();
		}
		if (listListener != null) {
			listViewer.removeSelectionChangedListener(listListener);
		}
		super.dispose();
	}

}
