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

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Event;

public class CompletionStyledTextObservableValue extends StyledTextObservableValue implements ISetPapyrusConverter {

	/**
	 * The name resolution helper shared with the widget
	 */
	protected IPapyrusConverter parser;

	/**
	 * 
	 * Constructor.
	 *
	 * @param text
	 * @param modelProperty
	 * @param eventType
	 */
	public CompletionStyledTextObservableValue(StyledText text, IObservableValue modelProperty, int eventType) {
		super(text, modelProperty, eventType);

	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.databinding.StyledTextObservableValue#doSetValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	protected void doSetValue(Object value) {
		String editValue = parser.canonicalToEditValue(value, 0);
		if (editValue instanceof String) {
			super.doSetValue(editValue);
		} else {
			super.doSetValue(value);
		}
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.databinding.StyledTextObservableValue#doGetValue()
	 *
	 * @return
	 */
	@Override
	protected Object doGetValue() {
		Object newValue = super.doGetValue();
		if (newValue instanceof String) {
			if (IPapyrusConverter.UNDEFINED_VALUE.equals(newValue)) {
				return null;
			}
			Object result = this.parser.editToCanonicalValue((String) newValue, 0);
			return result;
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter#setPapyrusConverter(org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter)
	 *
	 * @param parser
	 */
	@Override
	public void setPapyrusConverter(IPapyrusConverter parser) {
		this.parser = parser;
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
		// if (newValue == null) {
		// return;
		// }
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
