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
package org.eclipse.papyrus.views.properties.toolsmiths.preferences;

import java.util.stream.Stream;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.papyrus.views.properties.toolsmiths.messages.Messages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This page handles preferences for dialog boxes
 *
 * @author Camille Letavernier
 */
public class CustomizationPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 *
	 */
	public static final String OPEN_CUSTOMIZATION_PERSPECTIVE = "openCustomizationPerspective"; //$NON-NLS-1$

	/**
	 *
	 */
	public static final String ASK_FOR_CONFIRMATION = "askForConfirmation"; //$NON-NLS-1$

	public static final String ON_OPEN_CUSTOMIZATION_EDITOR = "onOpenCustomizationEditor"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public CustomizationPreferencePage() {
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param style
	 */
	public CustomizationPreferencePage(int style) {
		super(style);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param title
	 * @param style
	 */
	public CustomizationPreferencePage(String title, int style) {
		super(title, style);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param title
	 * @param image
	 * @param style
	 */
	public CustomizationPreferencePage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.CustomizationPreferencePage_PropertyViewCustomizationPreferences);
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(OPEN_CUSTOMIZATION_PERSPECTIVE, Messages.CustomizationPreferencePage_OpenCustomizationPerspective, getFieldEditorParent()));
		addField(new BooleanFieldEditor(ASK_FOR_CONFIRMATION, Messages.CustomizationPreferencePage_AskForConfirmation, getFieldEditorParent()));

		String[][] onOpenCustomizationEditor = Stream.of(CustomizationEditorActionKind.values())
				.map(v -> new String[] { v.getLabel(), v.name() })
				.toArray(String[][]::new);
		addField(new ComboFieldEditor(ON_OPEN_CUSTOMIZATION_EDITOR, Messages.CustomizationPreferencePage_0, onOpenCustomizationEditor, getFieldEditorParent()));
	}

	/**
	 * Indicates if the customization perspective should be opened
	 *
	 * @return true if the customization perspective should be opened
	 */
	public static boolean openCustomizationPerspective() {
		return Activator.getDefault().getPreferenceStore().getBoolean(OPEN_CUSTOMIZATION_PERSPECTIVE);
	}

	/**
	 * Indicates if a dialog should ask the user for a confirmation before opening
	 * the customization perspective
	 *
	 * @return true if a user confirmation is needed
	 */
	public static boolean askForConfirmation() {
		return Activator.getDefault().getPreferenceStore().getBoolean(ASK_FOR_CONFIRMATION);
	}

}
