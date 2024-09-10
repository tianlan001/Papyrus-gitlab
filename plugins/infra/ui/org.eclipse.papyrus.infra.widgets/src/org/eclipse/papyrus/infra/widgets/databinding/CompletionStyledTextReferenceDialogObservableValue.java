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

package org.eclipse.papyrus.infra.widgets.databinding;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.papyrus.infra.widgets.editors.CompletionStyledTextReferenceDialog;
import org.eclipse.swt.custom.StyledText;

/**
 * @author Vincent Lorenzo
 *
 */
public class CompletionStyledTextReferenceDialogObservableValue extends CompletionStyledTextObservableValue {

	/**
	 * the styled text with completion reference dialog
	 */
	private CompletionStyledTextReferenceDialog referenceDialog;

	/**
	 * @param dialog
	 *            The observed StyledTextReferenceDialog
	 * @param styledText
	 *            The observed StyledText
	 * @param modelObservable
	 *            The Model IObservable
	 * @param eventType
	 *            The eventType to listen to. When the event is fired by the Text
	 *            widget, this IObservableValue will fire a ChangeEvent
	 */
	public CompletionStyledTextReferenceDialogObservableValue(CompletionStyledTextReferenceDialog dialog, StyledText styledText, IObservableValue modelObservable, int eventType) {
		super(styledText, modelObservable, eventType);
		this.referenceDialog = dialog;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.databinding.StyledTextObservableValue#doSetValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	protected void doSetValue(Object value) {
		super.doSetValue(value);
		referenceDialog.update();
	}
	
	/**
	 * @see org.eclipse.papyrus.infra.widgets.databinding.CompletionStyledTextObservableValue#doGetValue()
	 *
	 * @return
	 */
	@Override
	protected Object doGetValue() {
		return super.doGetValue();
	}
}
