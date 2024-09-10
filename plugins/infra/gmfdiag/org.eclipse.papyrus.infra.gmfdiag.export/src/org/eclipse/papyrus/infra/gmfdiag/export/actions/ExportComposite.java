/*******************************************************************************
 * Copyright (c) 2011 AtoS
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Anass RADOUANI (AtoS)
 *		Fred Eckertson (Cerner) - fred.eckertson@cerner.com - Bug 502705
 *******************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.export.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.gmfdiag.export.Activator;
import org.eclipse.papyrus.infra.gmfdiag.export.DialogDisplayUtils;
import org.eclipse.papyrus.infra.gmfdiag.export.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * ExportAll configuration windows
 */
public class ExportComposite extends Composite {

	// SWT Objects
	private Text outputPathTxt;

	private Button outputDirectoryBtn;

	private Combo outputFormatCb;

	private Button btnCheckButton;

	// Configured items
	protected IResource outputDirectory;

	protected String exporter;

	protected boolean qualifiedName;

	/**
	 * Constructor
	 *
	 * @param parent
	 * @param style
	 */
	public ExportComposite(Composite parent, int style) {
		super(parent, style);
		createDialogComposite();
		createDialogContents();
		hookListeners();
	}

	/**
	 * Create the view of the list of interaction operators
	 *
	 * @param parent
	 *            the parent composite to contain this group
	 */
	protected void createDialogContents() {
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		composite.setFont(this.getFont());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label editorConfLbl = new Label(composite, SWT.NONE);
		editorConfLbl.setText(Messages.ExportAllDiagramsDialog_0);
		outputPathTxt = new Text(composite, SWT.BORDER);
		outputPathTxt.setEnabled(false);
		outputPathTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		outputDirectoryBtn = new Button(composite, SWT.NONE);
		outputDirectoryBtn.setText(Messages.ExportAllDiagramsDialog_1);

		Label outputFormatLbl = new Label(composite, SWT.NONE);
		outputFormatLbl.setText(Messages.ExportAllDiagramsDialog_2);

		outputFormatCb = new Combo(composite, SWT.NONE);
		GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
		gridData2.horizontalSpan = 2;
		outputFormatCb.setLayoutData(gridData2);

		btnCheckButton = new Button(this, SWT.CHECK);
		btnCheckButton.setText(Messages.ExportAllDiagramsDialog_btnCheckButton_text);

	}

	/**
	 * Create the Dialog Contents
	 */
	private void createDialogComposite() {
		GridLayout dialogLayout = new GridLayout();
		GridData dialogLayoutData = new GridData(GridData.FILL_BOTH);
		this.setLayout(dialogLayout);
		this.setLayoutData(dialogLayoutData);
	}

	private void hookListeners() {
		btnCheckButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				qualifiedName = btnCheckButton.getSelection();
			}
		});

		outputDirectoryBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ContainerSelectionDialog csDialog = new ContainerSelectionDialog(DialogDisplayUtils.getActiveWorkbenchShell(), ResourcesPlugin.getWorkspace().getRoot(), true, Messages.ExportAllDiagramsDialog_3);


				if (csDialog.open() == Window.OK) {
					Object[] results = csDialog.getResult();
					if (results.length == 1 && results[0] instanceof IPath) {
						URI uri = URI.createPlatformResourceURI(((IPath) results[0]).toString(), true);
						outputDirectory = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(uri.toPlatformString(true)));
						outputPathTxt.setText(outputDirectory.getLocation().toString());
					}
				}
			}
		});

		outputFormatCb.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				exporter = outputFormatCb.getText();
			}
		});
	}

	/**
	 * set the directory to which diagrams will be exported
	 *
	 * @param outputDirectory The output directory.
	 */
	public void setOutputDirectory(IResource outputDirectory) {
		this.outputDirectory = outputDirectory;
		loadData();
	}

	private void loadData() {
		for (ImageFileFormat imageFileFormat : ImageFileFormat.VALUES) {
			outputFormatCb.add(imageFileFormat.toString());
		}
		outputFormatCb.setText(ImageFileFormat.getDefaultImageFormat().toString());
		exporter = outputFormatCb.getText();

		if (outputDirectory == null) {
			Activator.log.error("Impossible to find the output directory", null);
			return;
		}
		outputPathTxt.setText(outputDirectory.getLocation().toString());
	}

	/**
	 * return the output directory where exported diagrams will be saved
	 *
	 * @return
	 */
	public IResource getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * return the path to the output directory
	 *
	 * @return
	 */
	public String getExporter() {
		return exporter;
	}

	/**
	 * return the qualified name of the diagram
	 *
	 * @return
	 */
	public boolean getQualifiedName() {
		return qualifiedName;
	}
}