/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Esterel Technologies SAS and others.
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
 *  Sebastien Gabel (Esterel Technologies SAS) - Bug 497361
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.SelectObservableValue;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * A Property Editor representing a Boolean value
 * as a Radio, with two options (true/false).
 *
 * @author Camille Letavernier
 *
 */
public class BooleanRadio extends AbstractValueEditor {

	/**
	 * The "true" radio button
	 */
	protected Button trueRadio;

	/**
	 * The "false" radio button
	 */
	protected Button falseRadio;

	private ControlDecoration controlDecoration;


	/**
	 *
	 * Constructor. Creates a new Property Editor for a Boolean
	 * value, represented by two radio buttons.
	 *
	 * @param parent
	 *            This editor's parent composite
	 * @param style
	 *            The style applied to this editor's radio buttons
	 */
	public BooleanRadio(Composite parent, int style) {
		this(parent, style, null);
	}

	/**
	 *
	 * Constructor. Creates a new Property Editor for a Boolean
	 * value, represented by two radio buttons.
	 *
	 * @param parent
	 *            This editor's parent composite
	 * @param style
	 *            The style applied to this editor's radio buttons
	 * @param label
	 *            The label for this editor
	 */
	public BooleanRadio(Composite parent, int style, String label) {
		super(parent, label);

		((GridLayout) getLayout()).numColumns = 3;

		trueRadio = factory.createButton(this, "true", style | SWT.RADIO); //$NON-NLS-1$
		trueRadio.setBackground(this.getBackground()); // For Radio buttons, we need to force the color

		falseRadio = factory.createButton(this, "false", style | SWT.RADIO); //$NON-NLS-1$
		falseRadio.setBackground(this.getBackground()); // For Radio buttons, we need to force the color

		setWidgetObservable(getObservable(), true);
		controlDecoration = new ControlDecoration(trueRadio, SWT.TOP | SWT.LEFT);

		// Use a dedicated grid data for each radio, otherwise one of the two labels may be truncated.
		GridData trueGd = new GridData();
		trueGd.horizontalIndent = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
		trueRadio.setLayoutData(trueGd);

		GridData falseGd = new GridData();
		falseGd.horizontalIndent = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
		falseRadio.setLayoutData(falseGd);
	}

	/**
	 * Defines a single observable value, encapsulating the ones
	 * from each radio button (true / false)
	 *
	 * @return The encapsulating observable value
	 */
	private IObservableValue getObservable() {
		IObservableValue trueObservable = WidgetProperties.widgetSelection().observe(trueRadio);
		IObservableValue falseObservable = WidgetProperties.widgetSelection().observe(falseRadio);

		SelectObservableValue observable = new SelectObservableValue();
		observable.addOption(true, trueObservable);
		observable.addOption(false, falseObservable);

		return observable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getEditableType() {
		return Boolean.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getValue() {
		return trueRadio.getSelection();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		trueRadio.setEnabled(!readOnly);
		falseRadio.setEnabled(!readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return !trueRadio.isEnabled() || !falseRadio.isEnabled();
	}

	@Override
	public void setToolTipText(String text) {
		trueRadio.setToolTipText(text);
		falseRadio.setToolTipText(text);
		super.setLabelToolTipText(text);
	}

	public void setTrueLabel(String label) {
		trueRadio.setText(label);
	}

	public String getTrueLabel() {
		return trueRadio.getText();
	}

	public void setFalseLabel(String label) {
		falseRadio.setText(label);
	}

	public String getFalseLabel() {
		return falseRadio.getText();
	}
}
