/*****************************************************************************
 * Copyright (c) 2014, 2015, 2020, 2021 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Camille Letavernier - Bug 569356 Incremental Generation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * @author damus
 *
 */
public class GeneratorMainPage extends WizardNewFileCreationPage implements IGeneratorWizardPage {

	private final GeneratorWizardModel model;

	private GeneratorParametersBlock parametersBlock;
	private BaseElementTypeSetBlock diagramBlock;
	private GeneratorIncrementalBlock incrementalBlock;
	private PluginConfigurationBlock pluginBlock;

	public GeneratorMainPage(GeneratorWizardModel model, String title, String description, String fileExtension) {
		super("main", model.getDefaultContainerSelection()); //$NON-NLS-1$

		this.model = model;

		setTitle(title);
		setDescription(description);

		setAllowExistingResources(true);
		setFileExtension(fileExtension);
		setFileName(model.getDefaultFileName());
	}

	@Override
	public void createControl(Composite parent) {
		Composite self = new Composite(parent, SWT.NONE);

		self.setLayout(new GridLayout(2, false));

		parametersBlock = new GeneratorParametersBlock(model);
		parametersBlock.createControl(self);
		diagramBlock = new BaseElementTypeSetBlock(model);
		diagramBlock.createControl(self);
		incrementalBlock = new GeneratorIncrementalBlock(model);
		incrementalBlock.createControl(self);
		pluginBlock = new PluginConfigurationBlock(model);
		pluginBlock.createControl(self);

		// File selection control
		super.createControl(self);
		getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		setControl(self);

		setPageComplete(validatePage());
	}

	@Override
	public void save() {
		parametersBlock.save();
		diagramBlock.save();
	}

	@Override
	public String getFileName() {
		String fileName = super.getFileName();
		if (fileName == null || fileName.isEmpty()) {
			// Make sure we always return a valid file name, even during the Wizard
			// initialization
			return model.getDefaultFileName();
		}
		return fileName;
	}

	@Override
	public boolean validatePage() {
		model.setContainerPath(getContainerFullPath());
		model.setFileName(getFileName());
		pluginBlock.validatePage();
		incrementalBlock.validatePage();

		return super.validatePage() && model.validate();
	}
}
