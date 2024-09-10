/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.databinding;

import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.papyrus.infra.tools.databinding.AggregatedObservable;
import org.eclipse.papyrus.infra.widgets.providers.UnchangedObject;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * An ObservableValue for StyledText field, with support for AggregatedObservable
 *
 * @author Vincent Lorenzo
 */
public class StyledTextObservableValue extends AbstractObservableValue implements Listener {

	/**
	 * The styled text.
	 */
	protected StyledText text;

	/**
	 * The event type used by the styled text.
	 */
	protected int eventType;

	/**
	 * The current value.
	 */
	protected Object currentValue;

	/**
	 * If the Text field may represent more than one value,
	 * use an AggregatedObservable.
	 *
	 * May be null.
	 */
	protected AggregatedObservable modelProperty;

	/**
	 * Constructor
	 *
	 * @param text
	 *            The Text field to observe
	 * @param modelProperty
	 *            The model IObservableValue
	 * @param eventType
	 *            The eventType to listen to. When the event is fired by the Text
	 *            widget, this IObservableValue will fire a ChangeEvent
	 */
	public StyledTextObservableValue(final StyledText text, final IObservableValue modelProperty, final int eventType) {
		this.text = text;
		this.eventType = eventType;
		if (modelProperty instanceof AggregatedObservable) {
			this.modelProperty = (AggregatedObservable) modelProperty;
		}
		this.text.addListener(eventType, this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	@Override
	public Object getValueType() {
		return String.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		if (UnchangedObject.instance.toString().equals(text.getText())) {
			return null;
		} else {
			return text.getText();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Object value) {
		if (modelProperty != null && modelProperty.hasDifferentValues()) {
			this.text.setText(UnchangedObject.instance.toString());
			this.currentValue = UnchangedObject.instance;
		} else {
			if (value instanceof String) {
				this.text.setText((String) value);
				this.currentValue = value;
			} else if (value == null) {
				this.text.setText(""); //$NON-NLS-1$
				this.currentValue = null;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(final Event event) {

		final Object oldValue = currentValue;
		final Object newValue = getValue();
		if (newValue == null) {
			return;
		}
		currentValue = newValue;

		if ((eventType & event.type) != 0) {
			fireValueChange(new ValueDiff() {

				@Override
				public Object getOldValue() {
					return oldValue;
				}

				@Override
				public Object getNewValue() {
					return newValue;
				}

			});
		}
	}

}
