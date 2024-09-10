/*****************************************************************************
 * Copyright (c) 2011, 2014 CEA LIST and others.
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
 *  Thibault Le Ouay t.leouay@sherpa-eng.com - Add binding implementation
 *  Christian W. Damus (CEA) - bug 436072
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net- Bug 446865, 475369
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.papyrus.infra.tools.databinding.AggregatedObservable;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.UnchangedObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

/**
 * A Widget for editing a String with an editable combo.
 * The combo proposes a set of default values.
 *
 * @author Camille Letavernier
 */
public class StringCombo extends ReferenceCombo {

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style used.
	 */
	public StringCombo(final Composite parent, final int style) {
		super(parent, style);
		combo.setEditable(true);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style used.
	 * @param label
	 *            The initial label.
	 */
	public StringCombo(final Composite parent, final int style, final String label) {
		super(parent, style, label);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style used.
	 * @param label
	 *            The initial label.
	 * @param commitOnFocusLost
	 *            Determinate if the focus lost ill commit the value or not.
	 * @since 3.3
	 */
	public StringCombo(final Composite parent, final int style, final String label, final boolean commitOnFocusLost) {
		super(parent, style, label, commitOnFocusLost);
		combo.setEditable(true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceCombo#getEditableType()
	 */
	@Override
	public Object getEditableType() {
		return String.class;
	}

	/**
	 * Sets the content provider for this combo. The Content provider should
	 * specify the objects that can be referred by this property
	 *
	 * @param provider
	 *            The provider.
	 */
	@Override
	public void setContentProvider(IStaticContentProvider provider) {
		if (provider != null) {
			contentProvider = new EncapsulatedContentProvider(provider);
			viewer.setContentProvider(contentProvider);
			viewer.setInput(""); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceCombo#getObservableValue()
	 */
	@Override
	protected IObservableValue getObservableValue() {
		return new CComboObservableValue();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceCombo#getValue()
	 */
	@Override
	public String getValue() {
		// See Bug 359835 : The ComboViewer doesn't support custom values
		// We can't rely on the ComboViewer#getSelection() method
		return combo.getText();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceCombo#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		// See Bug 359835 : The ComboViewer doesn't support custom values
		// We can't rely on the ComboViewer#setSelection() method
		if (value instanceof String) {
			combo.setText((String) value);
		} else {
			combo.setText(""); //$NON-NLS-1$
		}
	}

	/**
	 * Updates the controls display.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceCombo#updateControls()
	 */
	@Override
	protected void updateControls() {
		// See Bug 359835 : The ComboViewer doesn't support custom values
		String value = getValue();
		super.updateControls();
		setValue(value);
	}

	class CComboObservableValue extends AbstractObservableValue implements SelectionListener, KeyListener, FocusListener {

		private String previousValue;

		public CComboObservableValue() {
			previousValue = combo.getText();
			combo.addSelectionListener(this); // Selection change
			combo.addKeyListener(this); // Enter pressed
			combo.addFocusListener(this); // Focus lost
		}

		@Override
		public Object getValueType() {
			return String.class;
		}

		@Override
		protected String doGetValue() {
			return combo.getText();
		}

		@Override
		protected void doSetValue(Object value) {
			if (modelProperty instanceof AggregatedObservable && ((AggregatedObservable) modelProperty).hasDifferentValues()) {
				combo.setText(UnchangedObject.instance.toString());
			} else if (value instanceof String) {
				// This is the new baseline value (coming from the model) against which to compare a future edit by the user
				previousValue = (String) value;
				combo.setText(previousValue);
			}
		}

		// Enter pressed
		@Override
		public void keyReleased(KeyEvent e) {
			if ((e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) && e.stateMask == SWT.NONE) {
				maybeFireChange();
				e.doit = false; // Stops the propagation of the event
			}
		}

		// Selection change
		@Override
		public void widgetSelected(SelectionEvent e) {
			maybeFireChange();
		}

		// Focus lost
		@Override
		public void focusLost(FocusEvent e) {
			maybeFireChange();
		}

		void maybeFireChange() {
			// Only report a change if there is actually a change, otherwise we get a no-op command that dirties the editor
			final String currentValue = doGetValue();
			if ((currentValue == null) ? previousValue != null : !currentValue.equals(previousValue)) {
				doFireChange();
			}
		}

		private void doFireChange() {
			final String oldValue = previousValue;
			final String currentValue = previousValue = doGetValue();
			fireValueChange(new ValueDiff() {

				@Override
				public Object getOldValue() {
					return oldValue;
				}

				@Override
				public Object getNewValue() {
					return currentValue;
				}
			});
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// Nothing
		}

		@Override
		public void focusGained(FocusEvent e) {
			// Nothing
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// Nothing
		}
	}

}
