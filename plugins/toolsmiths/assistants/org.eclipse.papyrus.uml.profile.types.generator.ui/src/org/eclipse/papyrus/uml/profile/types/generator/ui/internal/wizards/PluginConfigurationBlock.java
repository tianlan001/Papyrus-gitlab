/*****************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Camille Letavernier - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.papyrus.eclipse.project.editors.project.PluginEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.xml.sax.SAXException;

/**
 * Block presenting the plug-in configuration options.
 */
public class PluginConfigurationBlock {

	private GeneratorWizardModel model;
	private Group pluginGroup;
	private Button extensionPoint;

	public PluginConfigurationBlock(GeneratorWizardModel model) {
		this.model = model;
	}

	void createControl(Composite parent) {
		pluginGroup = new Group(parent, SWT.NONE);
		pluginGroup.setText("Plug-in Configuration");
		pluginGroup.setLayout(new GridLayout(1, true));
		pluginGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());

		extensionPoint = new Button(pluginGroup, SWT.CHECK);
		extensionPoint.setText("Generate extension point");
		extensionPoint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.setGenerateExtensionPoint(extensionPoint.getSelection());
				model.validatePage();
			}
		});
		extensionPoint.setSelection(model.isGenerateExtensionPoint());
	}

	void validatePage() {
		IProject project = model.getOutputModelFile().getProject();
		boolean groupEnabled = isPlugin(project);
		pluginGroup.setEnabled(groupEnabled);
		extensionPoint.setEnabled(groupEnabled);
	}

	private boolean isPlugin(IProject project) {
		PluginEditor pluginEditor;
		try {
			pluginEditor = new PluginEditor(project);
		} catch (CoreException | IOException | SAXException | ParserConfigurationException e) {
			return false;
		}
		pluginEditor.init();
		return pluginEditor.exists();
	}
}
