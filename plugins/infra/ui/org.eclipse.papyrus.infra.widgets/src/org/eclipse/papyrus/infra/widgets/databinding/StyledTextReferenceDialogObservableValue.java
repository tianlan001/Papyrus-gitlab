/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.databinding;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor;
import org.eclipse.papyrus.infra.widgets.providers.UnchangedObject;
import org.eclipse.swt.custom.StyledText;

/**
 * An ObservableValue for ReferenceDialog widget (Which displays
 * the current value with a CLabel)
 */
public class StyledTextReferenceDialogObservableValue extends StyledTextObservableValue {

	/**
	 * The value editor.
	 */
	private AbstractValueEditor referenceDialog;

	/**
	 * The label provider used to get the text value.
	 */
	private ILabelProvider labelProvider;

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
	 * @param labelProvider
	 *            The label provider
	 */
	public StyledTextReferenceDialogObservableValue(final AbstractValueEditor dialog, final StyledText styledText, final IObservableValue modelObservable, final int eventType, final ILabelProvider labelProvider) {
		super(styledText, modelObservable, eventType);
		this.referenceDialog = dialog;
		this.labelProvider = labelProvider;
	}

	/**
	 * Set the label provider.
	 * 
	 * @param labelProvider
	 *            The LabelProvider used to define the CLabel's text and image,
	 *            based on the current value
	 */
	public void setLabelProvider(ILabelProvider labelProvider) {
		if (labelProvider != null) {
			this.labelProvider = labelProvider;
		} else {
			this.labelProvider = new LabelProvider();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.databinding.StyledTextObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Object value) {
		if (null != modelProperty && modelProperty.hasDifferentValues()) {
			this.text.setText(UnchangedObject.instance.toString());
			this.currentValue = UnchangedObject.instance;
		} else if (value instanceof String) {
			this.text.setText((String) value);
			this.currentValue = value;
		} else {
			this.text.setText(labelProvider.getText(value));
			this.currentValue = value;
		}
		referenceDialog.update();
	}
}
