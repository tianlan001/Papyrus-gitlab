/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.preferences;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.papyrus.infra.ui.preferences.AbstractPreferenceGroup;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * The Plugin Builder Group
 */
public class PluginBuilderPreferenceGroup extends AbstractPreferenceGroup implements IPropertyChangeListener {

	/** The group. */
	private Group fieldGroup;

	/**
	 * editor used to activate/desactivate the Papyrus Plugin Builder globally
	 */
	private BooleanFieldEditor globalPapyrusPluginEditor;

	/*----------------------MODEL VALIDATION WIDGET----------------------*/
	private Group modelGroup;

	private Composite compositeInModelGroup;

	/**
	 * editor used to activate/desactivate the Papyrus model validation of the models during the build
	 */
	private BooleanFieldEditor mainModelCheckEditor;

	/**
	 * editor used to activate/desactivate the check of the dependencies of the models during the build
	 */
	private BooleanFieldEditor modelDependenciesEditor;

	/**
	 * editor used to activate/desactivate the validation of the models during the build
	 */
	private BooleanFieldEditor modelValidationEditor;

	/*----------------------MANIFEST VALIDATION WIDGET----------------------*/
	private Group manifestGroup;

	private Composite compositeInManifestGroup;

	/**
	 * editor used to activate/desactivate the Papyrus validation of the manifest during the build
	 */
	private BooleanFieldEditor mainManifestCheckEditor;

	/**
	 * editor used to activate/desactivate the check of the dependencies ranges in the Manifest during the build
	 */
	private BooleanFieldEditor manifestDependencyRangeEditor;

	/**
	 * editor used to activate/desactivate the check of reexport usage in the Manifest during the build
	 */
	private BooleanFieldEditor manifestDependencyReexportEditor;

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param key
	 * @param dialogPage
	 */
	public PluginBuilderPreferenceGroup(Composite parent, String key, DialogPage dialogPage) {
		super(parent, key, dialogPage);
		createContent(parent);
	}

	/**
	 * Creates the content.
	 *
	 * @param parent
	 *            the parent
	 */
	public void createContent(final Composite parent) {
		fieldGroup = new Group(parent, SWT.SCROLL_PAGE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		fieldGroup.setLayout(layout);
		fieldGroup.setText(Messages.PluginBuilderPreferenceGroup_PapyrusPluginBuilder);

		GridDataFactory.fillDefaults().grab(true, false).applyTo(fieldGroup);

		// the editor to activate Papyrus Plugin Builder
		globalPapyrusPluginEditor = new BooleanFieldEditor(PluginBuilderPreferencesConstants.ACTIVATE_MAIN_PAPYRUS_BUILDER, Messages.PluginBuilderPreferenceGroup_activateBuilder, fieldGroup);
		globalPapyrusPluginEditor.setPage(dialogPage);
		addFieldEditor(globalPapyrusPluginEditor);
		globalPapyrusPluginEditor.setPropertyChangeListener(this);
		/*----------------MODEL PREFERENCES----------------------*/
		createModelPreferenceEditors(fieldGroup);

		/*----------------MANIFEST PREFERENCES----------------------*/
		createManifestPreferenceEditors(fieldGroup);
	}

	/**
	 * Create the composites to edit the model builders preferences
	 *
	 * @param parent
	 *            the parent composite
	 */
	protected void createModelPreferenceEditors(final Composite parent) {
		// model group
		modelGroup = new Group(parent, SWT.NONE);
		modelGroup.setText(Messages.PluginBuilderPreferenceGroup_modelValidation);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(modelGroup);

		mainModelCheckEditor = new BooleanFieldEditor(PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MODEL_BUILDER, Messages.PluginBuilderPreferenceGroup_activateBuildModel, modelGroup);
		addFieldEditor(mainModelCheckEditor);

		compositeInModelGroup = new Composite(modelGroup, SWT.NONE);

		modelDependenciesEditor = new BooleanFieldEditor(PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_CHECK_MODEL_DEPENDENCIES, Messages.PluginBuilderPreferenceGroup_checkModelDependencies, compositeInModelGroup);
		addFieldEditor(modelDependenciesEditor);
		modelValidationEditor = new BooleanFieldEditor(PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_VALIDATE_MODEL, Messages.PluginBuilderPreferenceGroup_validateModels, compositeInModelGroup);
		addFieldEditor(modelValidationEditor);

		// must be set after the creation of the editor
		GridLayout layout2 = new GridLayout(1, false);
		layout2.horizontalSpacing = 15;
		layout2.marginLeft = 20;
		compositeInModelGroup.setLayout(layout2);

		// add property listener
		mainModelCheckEditor.setPropertyChangeListener(this);
	}

	protected void createManifestPreferenceEditors(final Composite parent) {
		// manifest group
		manifestGroup = new Group(parent, SWT.NONE);
		manifestGroup.setText(Messages.PluginBuilderPreferenceGroup_manifestValidation);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(manifestGroup);

		mainManifestCheckEditor = new BooleanFieldEditor(PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MANIFEST_BUILDER, Messages.PluginBuilderPreferenceGroup_validateManifest, manifestGroup);
		addFieldEditor(mainManifestCheckEditor);

		compositeInManifestGroup = new Composite(manifestGroup, SWT.NONE);

		manifestDependencyReexportEditor = new BooleanFieldEditor(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_NO_REEXPORT, Messages.PluginBuilderPreferenceGroup_checkNoReexportedDependencies, compositeInManifestGroup);
		addFieldEditor(manifestDependencyReexportEditor);
		manifestDependencyRangeEditor = new BooleanFieldEditor(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_DEPENDENCY_RANGE, Messages.PluginBuilderPreferenceGroup_checkDependenciesRange, compositeInManifestGroup);
		addFieldEditor(manifestDependencyRangeEditor);

		// must be set after the creation of the editor
		GridLayout layout2 = new GridLayout(1, false);
		layout2.horizontalSpacing = 15;
		layout2.marginLeft = 20;
		compositeInManifestGroup.setLayout(layout2);

		// add property listener
		mainManifestCheckEditor.setPropertyChangeListener(this);
	}

	/**
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 *
	 * @param event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		final Object source = event.getSource();
		if (source == globalPapyrusPluginEditor) {
			mainManifestCheckEditor.setEnabled((boolean) event.getNewValue(), manifestGroup);
			manifestDependencyReexportEditor.setEnabled((boolean) event.getNewValue() && mainManifestCheckEditor.getBooleanValue(), compositeInManifestGroup);
			manifestDependencyRangeEditor.setEnabled((boolean) event.getNewValue() && mainManifestCheckEditor.getBooleanValue(), compositeInManifestGroup);
			mainModelCheckEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue(), modelGroup);
			modelDependenciesEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue() && mainModelCheckEditor.getBooleanValue(), compositeInModelGroup);
			modelValidationEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue() && mainModelCheckEditor.getBooleanValue(), compositeInModelGroup);

		} else if (source == mainManifestCheckEditor) {
			manifestDependencyReexportEditor.setEnabled((boolean) event.getNewValue(), compositeInManifestGroup);
			manifestDependencyRangeEditor.setEnabled((boolean) event.getNewValue(), compositeInManifestGroup);
		} else if (source == mainModelCheckEditor) {
			modelDependenciesEditor.setEnabled((boolean) event.getNewValue(), compositeInModelGroup);
			modelValidationEditor.setEnabled((boolean) event.getNewValue(), compositeInModelGroup);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.preferences.AbstractPreferenceGroup#load()
	 *
	 */
	@Override
	public void load() {
		super.load();
		// init editor status (grayed or not)
		mainModelCheckEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue(), modelGroup);
		modelDependenciesEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue() && mainModelCheckEditor.getBooleanValue(), compositeInModelGroup);
		modelValidationEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue() && mainModelCheckEditor.getBooleanValue(), compositeInModelGroup);

		mainManifestCheckEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue(), manifestGroup);
		manifestDependencyReexportEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue() && mainManifestCheckEditor.getBooleanValue(), compositeInManifestGroup);
		manifestDependencyRangeEditor.setEnabled(globalPapyrusPluginEditor.getBooleanValue() && mainManifestCheckEditor.getBooleanValue(), compositeInManifestGroup);

	}
}
