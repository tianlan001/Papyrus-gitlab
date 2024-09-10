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

import java.util.Collection;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;

/**
 * @author Vincent Lorenzo
 *
 */
public class CompletionStyledTextMultiReferenceDialogObservableValue extends CompletionStyledTextObservableValue {

	/**
	 * the styled text with completion reference dialog
	 */
	private Control referenceDialog;

	/**
	 * the IObservable list
	 */
	private IObservableList list;

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
	public CompletionStyledTextMultiReferenceDialogObservableValue(Control dialog, StyledText styledText, IObservableList modelObservable, int eventType) {
		super(styledText, null, eventType);
		this.referenceDialog = dialog;
		list = modelObservable;
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
		Object newValues = super.doGetValue();
		if (newValues instanceof Collection<?>) {
			if (!list.equals(newValues)) {
				list.clear();
				list.addAll((Collection<?>) newValues);
			}
		}else if(newValues==null){
			list.clear();
		}
		return newValues;
	}
}
