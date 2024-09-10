/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) - mickael.adam@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.databinding.TextObservableValue;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.selectors.StringSelector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * A String editor with a clear button. Useful for filter filed.
 */
public class StringWithClearEditor extends AbstractValueEditor implements KeyListener, ModifyListener {

	/**
	 * The text box for editing this editor's value
	 */
	protected final Text text;

	private int delay = 600;

	private boolean validateOnDelay = false;

	private Timer timer;

	private TimerTask currentValidateTask;

	private TimerTask changeColorTask;

	/** The clear icon when enable. */
	private static final String CLEAR_ENABLED_ICON = "/icons/clear_enabled.gif";//$NON-NLS-1$

	/** The clear icon when disable. */
	private static final String CLEAR_DISABLED_ICON = "/icons/clear_disabled.gif";//$NON-NLS-1$ .

	/** The asterisk constant */
	protected static final String ASTERISK = "*"; //$NON-NLS-1$

	/** The empty string. */
	protected static final String EMPTY = ""; //$NON-NLS-1$

	/** The map of string which have to be replaced in the text field during the user is typing. */
	private Map<String, String> stringReplacementMap = new HashMap<>();

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 */
	public StringWithClearEditor(Composite parent, int style) {
		this(parent, style, null);

	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed
	 * @param style
	 *            The style for this editor's text box
	 * @param label
	 *            The label for this editor
	 */
	public StringWithClearEditor(Composite parent, int style, String label) {
		super(parent, label);

		// Create the filter composite
		final Composite filterComposite = new Composite(this, style);
		filterComposite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		// Initialize the layout of the filter composite to display the widgets on 2 columns
		final GridLayout filterLayout = new GridLayout(2, false);
		filterLayout.marginHeight = 0;
		filterLayout.marginWidth = 0;

		filterComposite.setLayout(filterLayout);
		filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		text = new Text(filterComposite, SWT.NONE);
		text.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		text.setFont(parent.getFont());

		Label clearButton = new Label(filterComposite, SWT.NONE);
		clearButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		clearButton.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		clearButton.setToolTipText(Messages.StringWithClearEditor_ClearFilter);

		// Add listener to clear the filter
		clearButton.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseUp(MouseEvent e) {
				text.setText(EMPTY);
				notifyChange();
			}
		});

		// Set the clear icon
		ImageDescriptor imageDescriptor = Activator.getDefault().getImageDescriptor(CLEAR_DISABLED_ICON);
		clearButton.setImage(Activator.getDefault().getImage(imageDescriptor));

		clearButton.addMouseTrackListener(new MouseTrackAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseEnter(final MouseEvent pEvent) {
				ImageDescriptor imageDescriptor = Activator.getDefault().getImageDescriptor(CLEAR_ENABLED_ICON);
				clearButton.setImage(Activator.getDefault().getImage(imageDescriptor));
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseExit(final MouseEvent pEvent) {
				ImageDescriptor imageDescriptor = Activator.getDefault().getImageDescriptor(CLEAR_DISABLED_ICON);
				clearButton.setImage(Activator.getDefault().getImage(imageDescriptor));
			}
		});

		text.addKeyListener(this);
		text.addModifyListener(this);
		setCommitOnFocusLost(text);
		controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		controlDecoration.hide();
		pack();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#getLabelLayoutData()
	 */
	@Override
	protected GridData getLabelLayoutData() {
		GridData result = super.getLabelLayoutData();
		if (null != text) {
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
	public void keyPressed(final KeyEvent e) {
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
	public void keyReleased(final KeyEvent e) {
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
	public void setModelObservable(final IObservableValue observable) {
		setWidgetObservable(new TextObservableValue(text, observable, SWT.FocusOut), true);
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
	public String getValue() {
		return text.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(final boolean readOnly) {
		text.setEnabled(!readOnly);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnly() {
		return !text.isEnabled();
	}

	/**
	 * Notify the change.
	 */
	protected void notifyChange() {

		text.notifyListeners(SWT.FocusOut, new Event());
		commit();
		changeColorField();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setToolTipText(final String tooltip) {
		text.setToolTipText(tooltip);
		super.setLabelToolTipText(tooltip);
	}



	/**
	 * Sets the current text value for this editor
	 *
	 * @param value
	 *            The value to set.
	 */
	public void setValue(final Object value) {
		if (value instanceof String) {
			this.text.setText((String) value);
		} else {
			this.text.setText(EMPTY);
		}
	}

	/**
	 * Indicates that this editor should be automatically validated after a
	 * timer.
	 *
	 * @param validateOnDelay
	 */
	public void setValidateOnDelay(final boolean validateOnDelay) {
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
		if (0 == delay) {
			cancelCurrentTask();
		}
	}

	/**
	 * Cancel the current task.
	 */
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
	public void modifyText(final ModifyEvent e) {

		//Replace strings
		if (0 < stringReplacementMap.size()) {
			// save the selection point in the text field
			Point selection = text.getSelection();
			
			Set<Entry<String, String>> entrySet = stringReplacementMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				if (text.getText().contains(entry.getKey())) {
					text.setText(getValue().replace(entry.getKey(), entry.getValue()));
				}
			}
			
			// restore the selection in the text field
			text.setSelection(selection);
		}

		// SWT Thread
		if (validateOnDelay) {
			if (delay == 0) {
				commit(); // Direct commit on edition, to avoid creating useless
				// threads
				return;
			}

			if (null == timer) {
				timer = new Timer(true);
			}

			cancelCurrentTask();
			currentValidateTask = new TimerTask() {

				// Timer thread
				@Override
				public void run() {
					StringWithClearEditor.this.getDisplay().syncExec(new Runnable() {

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
		if (null != targetValidator) {
			IStatus status = targetValidator.validate(text.getText());
			updateStatus(status);
		}
		if (null != modelValidator) {
			IStatus status = modelValidator.validate(text.getText());
			updateStatus(status);
			if (null == binding) {
				update();
			}
		}

		if (null != modelProperty) { // Bug 433169: The widget may be used without an Observable Value (setValue + getValue)
			if (null != modelProperty.getValue()) {
				if (!isReadOnly() && !modelProperty.getValue().toString().equals(text.getText())) {
					text.setBackground(EDIT);
				} else {
					text.setBackground(DEFAULT);
				}
			} else {
				if (text.getText().equals("")) {//$NON-NLS-1$
					text.setBackground(DEFAULT);
				} else {
					text.setBackground(EDIT);
				}
			}
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		cancelCurrentTask();
		cancelChangeColorTask();
		if (null != timer) {
			timer.cancel();
			timer = null;
		}
		super.dispose();
	}

	/**
	 * Gets the text as {@link Text}.
	 */
	public Text getText() {
		return text;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateStatus(final IStatus status) {
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


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeColorField() {
		if (null != binding) {

			if (null == timer) {
				timer = new Timer(true);
			}

			cancelChangeColorTask();
			changeColorTask = new TimerTask() {

				@Override
				public void run() {
					if (StringWithClearEditor.this.isDisposed()) {
						return;
					}
					StringWithClearEditor.this.getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							text.setBackground(DEFAULT);
							text.update();
						}
					});
				}
			};
			if (errorBinding) {
				text.setBackground(ERROR);
				text.update();
			} else {
				IStatus status = (IStatus) binding.getValidationStatus().getValue();
				switch (status.getSeverity()) {
				case IStatus.OK:
				case IStatus.WARNING:
					timer.schedule(changeColorTask, 600);
					text.setBackground(VALID);
					text.update();
					break;
				case IStatus.ERROR:
					text.setBackground(ERROR);
					text.update();
					break;

				}
			}
		}
	}

	/**
	 * add a set of string to be replaced in the text field when the user is typing.
	 * 
	 * @param oldValue
	 *            The value to replace.
	 * @param newValue
	 *            The new value to be replace for.
	 */
	public void addStringToReplace(final String oldValue, final String newValue) {
		Assert.isNotNull(oldValue);
		Assert.isNotNull(newValue);

		stringReplacementMap.put(oldValue, newValue);
	}

	/**
	 * remove an value to be replaced in the text field.
	 * 
	 * @param oldValue
	 *            The old value which was replace.
	 */
	public void removeStringToReplace(String oldValue) {
		Assert.isNotNull(oldValue);

		stringReplacementMap.remove(oldValue);
	}

	/**
	 * Clear the string replacement map.
	 */
	public void clearStringToReplace() {
		stringReplacementMap.clear();
	}

	/**
	 * Cancel the change color tasks.
	 */
	private void cancelChangeColorTask() {
		if (null != changeColorTask) {
			changeColorTask.cancel();
			changeColorTask = null;
		}
	}

}
