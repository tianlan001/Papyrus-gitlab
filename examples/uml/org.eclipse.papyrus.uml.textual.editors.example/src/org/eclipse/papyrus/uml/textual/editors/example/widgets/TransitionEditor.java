/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.textual.editors.example.widgets;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.uml.properties.xtext.widget.property.AbstractXtextPropertyEditor;
import org.eclipse.papyrus.uml.textedit.transition.xtext.ui.contributions.TransitionEditorConfigurationContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * An xtext transition editor within the property view
 *
 * @see AbstractXtextPropertyEditor
 */
@SuppressWarnings("nls")
public class TransitionEditor extends AbstractXtextPropertyEditor {

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget is created
	 * @param style
	 *            The style for the {@link XtextValueEditor}
	 */
	public TransitionEditor(Composite parent, int style) {
		// 1st parameter is name of language associated with xtext editor (see language
		// attribute in extension point org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.DirectEditor).
		// In this case, we added the language constant to the editor contribution, but this is
		// not the case for all Papyrus xtext editors
		super(TransitionEditorConfigurationContribution.LANGUAGE, parent, style);

		// add a small label above the editor
		Label editorLabel = new Label(parent, SWT.NONE);
		editorLabel.setText("Edit transition:");
		editorLabel.moveAbove(null);
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 150).applyTo(xtextEditor.getTextControl());
	}

	@Override
	protected void registerChangeListeners(ModelElement element) {
		registerObservable(element, "name");
		registerObservable(element, "guard");
		registerObservable(element, "kind");
		registerObservable(element, "effect");
	}
}
