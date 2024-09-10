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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 446865
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;


import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.papyrus.infra.widgets.databinding.StyledTextObservableValue;
import org.eclipse.papyrus.infra.widgets.selectors.StringSelector;
import org.eclipse.papyrus.infra.widgets.validator.AbstractValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * A Property Editor representing a single-line or multi-line String value as a
 * Text. This editor's content is validated when the focus is lost, or, if the
 * editor is single-line, when the Carriage Return is pressed. For a multi-line
 * editor, ctrl+enter will also validate the editor's content.
 *
 * @see SWT#MULTI
 *
 * @author Vincent Lorenzo, adapted code from {@link StringEditor}
 */
public class StyledTextStringEditor extends AbstractValueEditor implements KeyListener, ModifyListener {

	/**
	 * The text box for editing this editor's value
	 */
	protected final StyledText text;

	private int delay = 600;

	private boolean validateOnDelay = false;

	private Timer timer;

	private TimerTask currentValidateTask;

	private TimerTask changeColorTask;

	private final static int DEFAULT_HEIGHT_HINT = 55;

	private final static int DEFAULT_WIDTH_HINT = 100;


	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 */
	public StyledTextStringEditor(Composite parent, int style) {
		this(parent, style, null, DEFAULT_HEIGHT_HINT, DEFAULT_WIDTH_HINT);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 * @param targetValidator
	 *            The validator used for the styled text.
	 */
	public StyledTextStringEditor(final Composite parent, final int style, final AbstractValidator targetValidator) {
		this(parent, style, null, DEFAULT_HEIGHT_HINT, DEFAULT_WIDTH_HINT, targetValidator);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 * @param label
	 *            The label for this editor
	 */
	public StyledTextStringEditor(Composite parent, int style, String label) {
		this(parent, style, label, DEFAULT_HEIGHT_HINT, DEFAULT_WIDTH_HINT);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 * @param label
	 *            The label for this editor
	 * @param targetValidator
	 *            The validator used for the styled text.
	 */
	public StyledTextStringEditor(final Composite parent, final int style, final String label, final AbstractValidator targetValidator) {
		this(parent, style, label, DEFAULT_HEIGHT_HINT, DEFAULT_WIDTH_HINT, targetValidator);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 * @param heighHint
	 *            Height hint of the text area in multiline mode
	 * @param widthHint
	 *            Width hint of the text area in multiline mode
	 */
	public StyledTextStringEditor(Composite parent, int style, int heighHint, int widthHint) {
		this(parent, style, null, heighHint, widthHint);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 * @param label
	 *            The label for this editor
	 * @param heighHint
	 *            Height hint of the text area in multiline mode
	 * @param widthHint
	 *            Width hint of the text area in multiline mode
	 */
	public StyledTextStringEditor(Composite parent, int style, String label, int heighHint, int widthHint) {
		this(parent, style, label, heighHint, widthHint, null);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 * @param label
	 *            The label for this editor
	 * @param heighHint
	 *            Height hint of the text area in multiline mode
	 * @param widthHint
	 *            Width hint of the text area in multiline mode
	 * @param targetValidator
	 *            The validator used for the styled text.
	 */
	public StyledTextStringEditor(final Composite parent, final int style, final String label, final int heighHint, final int widthHint, final AbstractValidator targetValidator) {
		super(parent, label);

		this.targetValidator = targetValidator;

		GridData data = getDefaultLayoutData();
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;
		int styledTextStyle = style;
		if ((style & SWT.MULTI) != 0) {
			data.minimumHeight = heighHint;
			data.minimumWidth = widthHint;
			styledTextStyle = style | SWT.V_SCROLL;
		}

		text = createStyledText(this, null, styledTextStyle);
		text.setLayoutData(data);

		if (label != null) {
			super.label.setLayoutData(getLabelLayoutData());

		}
		text.addKeyListener(this);
		text.addModifyListener(this);
		setCommitOnFocusLost(text);
		controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		controlDecoration.hide();

		// we ignore the indentation
		// we remove the margin
		GridLayout layout = (GridLayout) this.getLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		pack();
	}


	/**
	 * Creates a styled text as a part of the form.
	 * 
	 * @param parent
	 *            the text parent
	 * @param value
	 *            the text initial value
	 * @param style
	 *            the text style
	 * @return the styled text widget
	 */
	public StyledText createStyledText(Composite parent, String value, int style) {
		StyledText txt = new StyledText(parent, style);
		txt.setText(value);
		return txt;
	}


	@Override
	protected GridData getLabelLayoutData() {
		GridData result = super.getLabelLayoutData();
		if (text != null) {
			if ((text.getStyle() & SWT.MULTI) != 0) {
				result.verticalAlignment = SWT.BEGINNING;
			}
		}
		return result;
	}

	/**
	 * Ignored
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Nothing
	}

	/**
	 * Validates this editor when one of the following events occur : - CR
	 * released - Keypad CR released - Ctrl + [CR | Keypad CR] released
	 *
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 *
	 * @param e
	 */
	// TODO : we should prevent the \n from being written when validating the
	// multi-line field with Ctrl + CR
	@Override
	public void keyReleased(KeyEvent e) {
		// We listen on Carriage Return or Ctrl+ Carriage return, depending on
		// whether the editor is single- or multi-line
		if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
			if ((text.getStyle() & SWT.MULTI) == 0) { // Single-line : Enter
				if (e.stateMask == SWT.NONE) {
					notifyChange();
				}
			} else { // Multi-line : Ctrl+Enter
				if (e.stateMask == SWT.CTRL) {
					String str = text.getText();
					if (str.endsWith(StringSelector.LINE_SEPARATOR)) {
						int newLength = str.length() - StringSelector.LINE_SEPARATOR.length();
						text.setText(str.substring(0, newLength));
						text.setSelection(newLength);
					}
					notifyChange();
				}
			}
		}


	}

	@Override
	public void setModelObservable(IObservableValue observable) {
		setWidgetObservable(new StyledTextObservableValue(text, observable, SWT.FocusOut), true);
		super.setModelObservable(observable);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getEditableType() {
		return String.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValue() {
		return text.getText();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		text.setEnabled(!readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return !text.isEnabled();
	}

	protected void notifyChange() {

		text.notifyListeners(SWT.FocusOut, new Event());
		commit();
		changeColorField();
	}

	@Override
	public void setToolTipText(String tooltip) {
		text.setToolTipText(tooltip);
		super.setLabelToolTipText(tooltip);
	}

	/**
	 * Sets the current text value for this editor
	 *
	 * @param value
	 */
	public void setValue(Object value) {
		if (value instanceof String) {
			this.text.setText((String) value);
		} else {
			this.text.setText(""); //$NON-NLS-1$
		}
	}

	/**
	 * Indicates that this editor should be automatically validated after a
	 * timer.
	 *
	 * @param validateOnDelay
	 */
	public void setValidateOnDelay(boolean validateOnDelay) {
		this.validateOnDelay = validateOnDelay;

		if (validateOnDelay) {
			text.addModifyListener(this);
		} else {
			text.removeModifyListener(this);
			cancelCurrentTask();
		}
	}

	/**
	 * Indicates that this editor should be automatically validated after the
	 * given timer
	 *
	 * @param millis
	 *            The delay after which the editor should be automatically
	 *            validated, in milliseconds. The default is 600ms
	 */
	public void setValidateOnDelay(int millis) {
		this.delay = millis;
		setValidateOnDelay(true);
		if (delay == 0) {
			cancelCurrentTask();
		}
	}

	private void cancelCurrentTask() {
		if (currentValidateTask != null) {
			currentValidateTask.cancel();
			currentValidateTask = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public void modifyText(ModifyEvent e) {
		// SWT Thread
		if (validateOnDelay) {
			if (delay == 0) {
				commit(); // Direct commit on edition, to avoid creating useless
				// threads

				return;
			}

			if (timer == null) {
				timer = new Timer(true);
			}

			cancelCurrentTask();
			currentValidateTask = new TimerTask() {

				// Timer thread
				@Override
				public void run() {
					StyledTextStringEditor.this.getDisplay().syncExec(new Runnable() {

						// SWT Thread
						@Override
						public void run() {

							commit();
						}
					});
				}
			};
			timer.schedule(currentValidateTask, delay);
		}
		if (targetValidator != null) {
			IStatus status = targetValidator.validate(getTextToValidate());
			updateStatus(status);
		}
		if (modelValidator != null) {
			IStatus status = modelValidator.validate(getTextToValidate());
			updateStatus(status);
			if (binding == null) {
				update();
			}
		}

		if (modelProperty != null) { // Bug 433169: The widget may be used without an Observable Value (setValue + getValue)
			if (modelProperty.getValue() != null) {
				if (!isReadOnly() && !modelProperty.getValue().toString().equals(text.getText())) {
					text.setBackground(EDIT);
				} else {
					text.setBackground(DEFAULT);
				}
			} else {
				if (text.getText().equals("")) {
					text.setBackground(DEFAULT);
				} else {
					text.setBackground(EDIT);
				}
			}
		}
	}

	/**
	 * Gets the string to validate.
	 * 
	 * @return The string text to validate.
	 */
	protected String getTextToValidate() {
		return text.getText();
	}

	@Override
	public void dispose() {
		cancelCurrentTask();
		cancelChangeColorTask();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		super.dispose();
	}

	public StyledText getText() {
		return text;
	}

	@Override
	public void updateStatus(IStatus status) {
		if(!isDisposed()){
			switch (status.getSeverity()) {
			case IStatus.OK:
				controlDecoration.hide();
				break;
			case IStatus.WARNING:
				FieldDecoration warning = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_WARNING);
				controlDecoration.setImage(warning.getImage());
				controlDecoration.showHoverText(status.getMessage());
				controlDecoration.setDescriptionText(status.getMessage());
				controlDecoration.show();
				break;
			case IStatus.ERROR:
				FieldDecoration error = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
				controlDecoration.setImage(error.getImage());
				controlDecoration.showHoverText(status.getMessage());
				controlDecoration.setDescriptionText(status.getMessage());
				controlDecoration.show();
				break;
			default:
				controlDecoration.hide();
				break;
			}
		}
	}

	@Override
	public void changeColorField() {
		if (binding != null) {

			if (timer == null) {
				timer = new Timer(true);
			}

			cancelChangeColorTask();
			changeColorTask = new TimerTask() {

				@Override
				public void run() {
					if (StyledTextStringEditor.this.isDisposed()) {
						return;
					}
					StyledTextStringEditor.this.getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							if(!text.isDisposed()){
								text.setBackground(DEFAULT);
								text.update();
							}
						}
					});
				}
			};
			if (errorBinding) {
				if(!text.isDisposed()){
					text.setBackground(ERROR);
					text.update();
				}
			} else {
				IStatus status = (IStatus) binding.getValidationStatus().getValue();
				switch (status.getSeverity()) {
				case IStatus.OK:
				case IStatus.WARNING:
					timer.schedule(changeColorTask, 600);
					if(!text.isDisposed()){
						text.setBackground(VALID);
						text.update();
					}
					break;
				case IStatus.ERROR:
					if(!text.isDisposed()){
						text.setBackground(ERROR);
						text.update();
					}
					break;

				}
			}
		}
	}

	private void cancelChangeColorTask() {
		if (changeColorTask != null) {
			changeColorTask.cancel();
			changeColorTask = null;
		}
	}

}
