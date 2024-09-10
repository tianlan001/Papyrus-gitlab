/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.wizard.pages;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.eclipse.project.editors.project.filter.JavaPluginProjectFilter;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 *
 * This page allows to define the output of the conversion
 */
public class DefineOutputPluginWizardPage extends AbstractExportTableAsTableConfigurationWizardPage implements SelectionListener {

	/**
	 * The title of this page
	 */
	public static final String PAGE_TITLE = Messages.DefineOutputPluginWizardPage_OUTPUT_PAGE_TILE;

	/**
	 * the output folder name
	 */
	private static final String EXPORT_FOLDER_NAME = "tableConfiguration"; //$NON-NLS-1$

	/**
	 * The message for the browser button
	 */
	private static final String BROWSE_WORKSPACE = Messages.DefineOutputPluginWizardPage_Browse;

	/**
	 * the text field used to choose the java project to contribute
	 */
	private Text outputPluginNameTextField;

	/**
	 * the button used to cross the workspace and select a java project for the generated palette
	 */
	private Button outputPluginWorkspaceButton;

	/**
	 * java project filter
	 */
	private List<ViewerFilter> javaProjectFilter;

	/**
	 * the output java project
	 */
	private IJavaProject outputJavaProject;

	/**
	 * Constructor.
	 *
	 * @param pageName
	 */
	public DefineOutputPluginWizardPage() {
		super(PAGE_TITLE);
		setMessage(Messages.DefineOutputPluginWizardPage_DefineTheOuput);
		setPageComplete(false);// avoid the error message opening the page, and ensure the user fill the field before flip to next page
	}



	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(1, true));

		// the output group
		final Group outputGroup = new Group(composite, SWT.NONE);
		outputGroup.setText(Messages.DefineOutputPluginWizardPage_Output);
		outputGroup.setLayout(new GridLayout(2, false));
		outputGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridData ouputData = new GridData(GridData.FILL_HORIZONTAL);
		ouputData.horizontalSpan = 2;

		final Label outputLabel = new Label(outputGroup, SWT.NONE);
		outputLabel.setText(Messages.DefineOutputPluginWizardPage_SelectOutputPluginProject);
		outputLabel.setLayoutData(ouputData);

		ouputData = new GridData(GridData.FILL_HORIZONTAL);
		ouputData.horizontalSpan = 1;

		this.outputPluginNameTextField = new Text(outputGroup, SWT.BORDER | SWT.SINGLE);
		this.outputPluginNameTextField.setEditable(false);
		this.outputPluginNameTextField.setLayoutData(ouputData);

		this.outputPluginWorkspaceButton = new Button(outputGroup, SWT.NONE);
		this.outputPluginWorkspaceButton.setText(BROWSE_WORKSPACE);
		this.outputPluginWorkspaceButton.setLayoutData(ouputData);
		setButtonLayoutData(this.outputPluginWorkspaceButton);

		this.javaProjectFilter = Collections.singletonList(new JavaPluginProjectFilter());
		this.outputPluginWorkspaceButton.addSelectionListener(this);

		setControl(composite);
	}


	/**
	 *
	 * @return
	 *         the folder in which the file will be created
	 */
	public String getExportFolderName() {
		return EXPORT_FOLDER_NAME;
	}


	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 *
	 * @param e
	 */
	@Override
	public void widgetSelected(final SelectionEvent e) {
		if (this.outputPluginWorkspaceButton == e.widget) {
			final IContainer[] ifile = WorkspaceResourceDialog.openFolderSelection(getShell(), Messages.DefineOutputPluginWizardPage_SelectOutput, Messages.DefineOutputPluginWizardPage_SelectOutputPlugin, false, new IContainer[] {}, this.javaProjectFilter);
			if (ifile.length > 0) {
				this.outputJavaProject = (IJavaProject) JavaCore.create(ifile[0]);
				this.outputPluginNameTextField.setText(this.outputJavaProject.getPath().toString());
			}
		}
		setPageComplete();
	}

	/**
	 * Calculates if the current page is complete and update its status using {@link #setPageComplete(boolean)}
	 */
	private void setPageComplete() {
		boolean isComplete = true;

		setErrorMessage(null);
		if (null == this.outputJavaProject) {
			setErrorMessage(Messages.DefineOutputPluginWizardPage_ProjectMustBeSet);
			isComplete = false;
		}
		super.setPageComplete(isComplete);
	}


	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 *
	 * @param e
	 */
	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {
		// nothing to do
	}

	/**
	 *
	 * @return
	 *         the java output project
	 */
	public IJavaProject getOutputJavaProject() {
		return this.outputJavaProject;
	}

}