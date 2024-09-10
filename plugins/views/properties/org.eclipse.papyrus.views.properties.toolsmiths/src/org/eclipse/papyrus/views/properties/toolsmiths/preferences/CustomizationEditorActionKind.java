/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.views.properties.toolsmiths.preferences;

import static java.util.function.Predicate.not;
import static org.eclipse.papyrus.views.properties.toolsmiths.preferences.CustomizationPreferencePage.ON_OPEN_CUSTOMIZATION_EDITOR;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.util.Policy;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.papyrus.views.properties.toolsmiths.messages.Messages;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.WorkbenchException;

/**
 * Enumeration of action preferences for the opening of the customization editor.
 */
public enum CustomizationEditorActionKind {
	/** An "action" that simply does nothing when the customization editor is opened. */
	NOTHING("Do nothing"), //$NON-NLS-1$
	/** An action that switches the editor's window's perspective to the <em>Customization Perspective</em>. */
	PERSPECTIVE("Open the customization perspective"), //$NON-NLS-1$
	/** An action that shows the <em>Customization Preview</em> view in the editor's workbench page. */
	PREVIEW("Open the customization preview view"), //$NON-NLS-1$
	/**
	 * An action that prompts the user which action to take, with option to remember the decision in the
	 * preferences, and then delegates to the selected action.
	 */
	PROMPT("Ask what to do"); //$NON-NLS-1$

	private final String label;

	CustomizationEditorActionKind(String label) {
		this.label = label;
	}

	/**
	 * Obtain a label to present in the user interface, in the preference selection
	 * and dialog buttons.
	 *
	 * @return a translatable label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Perform the action that I represent. Note that all actions are no-ops if the current perspective
	 * of the given workbench page is the <em>Customization Perspective</em>, as that is the ideal scenario.
	 * Most especially, in that case the user would not be prompted for an action if the current preference
	 * is to ask each time.
	 *
	 * @param workbenchPage
	 *            the workbench page in which the customization editor is opened
	 */
	public void perform(IWorkbenchPage workbenchPage) {
		IPerspectiveDescriptor perspective = workbenchPage.getPerspective();
		if (perspective != null && Activator.CUSTOMIZATION_PERSPECTIVE_ID.equals(perspective.getId())) {
			// Nothing to do
			return;
		}

		switch (this) {
		case PERSPECTIVE:
			try {
				workbenchPage.getWorkbenchWindow().getWorkbench().showPerspective(Activator.CUSTOMIZATION_PERSPECTIVE_ID, workbenchPage.getWorkbenchWindow());
			} catch (WorkbenchException e) {
				Policy.getStatusHandler().show(e.getStatus(), Messages.CustomizationEditorActionKind_0);
			}
			break;
		case PREVIEW:
			try {
				// There's no harm in showing a view that is already present. And it would be brought
				// to the front of its stack
				workbenchPage.showView(Activator.PREVIEW_ID);
			} catch (PartInitException e) {
				Policy.getStatusHandler().show(e.getStatus(), Messages.CustomizationEditorActionKind_1);
			}
			break;
		case PROMPT:
			prompt(workbenchPage);
			break;
		default:
			// Pass
			break;
		}
	}

	/**
	 * Query the action to perform on opening the customization editor.
	 *
	 * @return the customization editor action kind
	 */
	public static CustomizationEditorActionKind getOnOpenCustomizationEditorAction() {
		String pref = Optional.ofNullable(Activator.getDefault().getPreferenceStore().getString(ON_OPEN_CUSTOMIZATION_EDITOR))
				.filter(not(String::isBlank)).orElse(getDefault().name());
		return CustomizationEditorActionKind.valueOf(pref);
	}

	/**
	 * Query the default value of the customization editor action preference.
	 *
	 * @return the default preference value, which is to {@linkplain #PROMPT prompt the user}
	 */
	public static CustomizationEditorActionKind getDefault() {
		return PROMPT;
	}

	private static void prompt(IWorkbenchPage workbenchPage) {
		String toggleMessage = Messages.CustomizationEditorActionKind_2;
		LinkedHashMap<String, Integer> buttons = new LinkedHashMap<>();
		EnumSet.complementOf(EnumSet.of(PROMPT)).forEach(v -> buttons.put(v.getLabel(), v.toButtonCode()));

		MessageDialogWithToggle dialog = new MessageDialogWithToggle(workbenchPage.getWorkbenchWindow().getShell(),
				Messages.CustomizationEditorActionKind_3, null, Messages.CustomizationEditorActionKind_4,
				MessageDialog.QUESTION, buttons, PREVIEW.ordinal(),
				toggleMessage, false);
		CustomizationEditorActionKind.forButtonCode(dialog.open()).ifPresent(result -> {
			// The result cannot be "prompt"
			result.perform(workbenchPage);
			if (dialog.getToggleState()) {
				Activator.getDefault().getPreferenceStore().setValue(ON_OPEN_CUSTOMIZATION_EDITOR, result.name());
			}
		});
	}

	int toButtonCode() {
		// Avoid the stock OK and Cancel values
		return ordinal() + 2;
	}

	static Optional<CustomizationEditorActionKind> forButtonCode(int code) {
		return code < 2 ? Optional.empty() : Optional.of(values()[code - 2]);
	}

}
