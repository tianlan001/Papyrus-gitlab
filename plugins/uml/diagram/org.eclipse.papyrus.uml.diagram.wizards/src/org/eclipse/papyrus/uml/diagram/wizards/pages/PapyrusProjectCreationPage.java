/*****************************************************************************
 * Copyright (c) 2014, 2017, 2019. 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *   Christian W. Damus - bug 471453
 *   Ansgar Radermacher (CEA LIST) -  support working sets (bug 572328), bug 551952
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.pages;

import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;


public class PapyrusProjectCreationPage extends WizardNewProjectCreationPage implements INewPapyrusModelPage {

	private Text fileName;

	private boolean fileNameEdited;

	private Listener fileNameModifyListener = new Listener() {

		@Override
		public void handleEvent(Event e) {
			if (!Objects.equals(fileName.getText(), getProjectName())) {
				fileNameEdited = true;
			}

			if (wizardData != null) {
				wizardData.setModelFileName(fileName.getText());
			}

			boolean valid = validatePage();
			setPageComplete(valid);
		}
	};

	private NewModelWizardData wizardData;

	public PapyrusProjectCreationPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite composite = (Composite) getControl();
		composite.setLayoutData(new GridData(SWT.FILL, SWT.DOWN, true, false));
		GridLayout gridLayout = new GridLayout(1, false);
		composite.setLayout(gridLayout);
		setControl(composite);
		Group group = createGroup(composite, Messages.PapyrusProjectCreationPage_0);
		fileName = new Text(group, SWT.BORDER);
		fileName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		fileName.addListener(SWT.Modify, fileNameModifyListener);
		setPageComplete(false);

		String[] workingSetIds = new String[] {
				"org.eclipse.ui.resourceWorkingSetPage", //$NON-NLS-1$
				"org.eclipse.jdt.ui.JavaWorkingSetPage" }; //$NON-NLS-1$
		createWorkingSetGroup(composite, null, workingSetIds);
	}

	/**
	 * Creates the group.
	 *
	 * @param parent
	 *            the parent
	 * @param name
	 *            the name
	 * @return the group
	 */
	private static Group createGroup(Composite parent, String name) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(name);
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		group.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		group.setLayoutData(data);
		return group;
	}

	/**
	 * @since 3.0
	 */
	@Override
	public void setNewModelWizardData(NewModelWizardData wizardData) {
		this.wizardData = wizardData;
	}

	/**
	 * @since 3.0
	 */
	@Override
	public NewModelWizardData getNewModelWizardData() {
		return wizardData;
	}

	@Override
	protected boolean validatePage() {
		String projectName = getProjectName();

		if ((fileName != null) && fileNameEdited) {
			if (fileName.getText().trim().isEmpty()) {
				this.setErrorMessage(Messages.PapyrusProjectCreationPage_3);
				return false;
			}
		} else if ((fileName != null) && (projectName != null)
				&& !Objects.equals(fileName.getText(), projectName)) {

			// Default file name is the project name
			fileName.setText(getProjectName());
		}

		return super.validatePage();
	}

	public String getFileName() {
		return fileName.getText();
	}

	/**
	 * This method is used to avoid case conflicts between existing and newly created projects
	 *
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 *
	 * @return true, if user can flip to next page
	 */
	@Override
	public boolean canFlipToNextPage() {
		// retrieve the selected elements and get its children
		boolean canFlip = true;

		if (!verifyProjectName()) {
			canFlip = false;
		}

		if (!validatePage()) {
			canFlip = false;
		}

		return canFlip && super.canFlipToNextPage();
	}

	private boolean verifyProjectName() {

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject iproject : projects) {
			if (this.getProjectName() == null) {
				this.setErrorMessage(Messages.PapyrusProjectCreationPage_page_null_name_desc);
				// A conflict has been found, no need to go further
				return false;
			}
			if (this.getProjectName().equalsIgnoreCase(iproject.getName())) {
				this.setErrorMessage(Messages.PapyrusProjectCreationPage_page_same_case_desc + iproject.getName());
				// A conflict has been found, no need to go further
				return false;
			}
		}

		return true;
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.eclipse.papyrus.uml.diagram.wizards.NewProject"); //$NON-NLS-1$
	}
}
