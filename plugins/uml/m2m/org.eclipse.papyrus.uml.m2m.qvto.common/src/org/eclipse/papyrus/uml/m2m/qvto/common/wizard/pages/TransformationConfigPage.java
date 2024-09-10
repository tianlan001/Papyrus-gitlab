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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * Page displaying the selected elements and the migration's configuration options
 * 
 * @author Quentin Le Menez
 *
 */
public class TransformationConfigPage extends WizardPage {

	protected DialogDataConfigComposite dialogDataConfigComposite;

	protected AbstractDialogData dialogData;


	/**
	 *
	 * Constructor.
	 * 
	 * @param dialogData
	 *            The instance used to get the previously selected files from both the settings file and the previous page selection,
	 *            as well as the configuration used to display the transformation options
	 */
	public TransformationConfigPage(AbstractDialogData dialogData) {
		super("Parameters selection");
		setTitle("Define the transformation parameters");
		setDescription("Select the files and the configuration options for the transformation");
		// String iconPath = "icons/import_wiz_75x66.png"; //$NON-NLS-1$
		// ImageDescriptor imgDescriptior = Activator.getDefault().getImageDescriptor(iconPath);
		// setImageDescriptor(imgDescriptior);

		this.dialogData = dialogData;
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout());

		dialogDataConfigComposite = new DialogDataConfigComposite(pageComposite, SWT.NONE, dialogData);

		setControl(pageComposite);
	}

	/**
	 * 
	 * Used to update the input of the nested composite's viewer
	 * 
	 */
	public void resetViewerInput() {
		dialogDataConfigComposite.setViewerInput(dialogData.getAllSelectedFiles());
	}

}
