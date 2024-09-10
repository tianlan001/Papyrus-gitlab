/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.papyrus.infra.widgets.databinding.CompletionStyledTextReferenceDialogObservableValue;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Vincent Lorenzo
 *
 */
public class CompletionStyledTextReferenceDialog extends StyledTextReferenceDialog implements ISetPapyrusConverter {
	/**
	 * the content assist helper used for the completion
	 */
	protected IPapyrusConverter parser;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public CompletionStyledTextReferenceDialog(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog#createStyledTextStringEditor(org.eclipse.swt.widgets.Composite, java.lang.String, int)
	 *
	 * @param parent
	 * @param initialValue
	 * @param style
	 * @return
	 */
	protected StyledTextStringEditor createStyledTextStringEditor(Composite parent, String initialValue, int style) {
		StyledTextStringEditor editor = new CompletionStyledTextStringEditor(parent, style);
		editor.setValue(initialValue);
		return editor;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog#createWidgetObservable(org.eclipse.core.databinding.observable.value.IObservableValue)
	 *
	 * @param modelProperty
	 * @return
	 */
	protected IObservableValue createWidgetObservable(IObservableValue modelProperty) {
		CompletionStyledTextReferenceDialogObservableValue val = new CompletionStyledTextReferenceDialogObservableValue(this, this.styledTextStringEditor.getText(), modelProperty, SWT.FocusOut);
		val.setPapyrusConverter(parser);
		return val;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter#setPapyrusConverter(org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter)
	 *
	 * @param parser
	 */
	@Override
	public void setPapyrusConverter(IPapyrusConverter parser) {
		this.parser = parser;
		((CompletionStyledTextStringEditor) styledTextStringEditor).setPapyrusConverter(parser);
	}
}
