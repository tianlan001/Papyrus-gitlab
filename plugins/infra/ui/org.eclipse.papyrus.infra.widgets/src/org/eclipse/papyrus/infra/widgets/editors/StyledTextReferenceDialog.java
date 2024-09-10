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
 *  Nicolas FAUVERGUE (ALL4TEC� nicolas.fauvergue@all4tec.net - Bug 459747
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.databinding.StyledTextReferenceDialogObservableValue;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.validator.AbstractValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

/**
 * An editor representing a single reference as a String Editor. A filtered selection
 * dialog is used to edit the value. Also offers support for unsetting the
 * value. This Editor needs a ContentProvider, and may use an optional
 * LabelProvider, describing the objects that can be referred by this property
 *
 * @author Vincent Lorenzo
 *
 *         Duplicated code from {@link ReferenceDialog}, replacing CLabel by {@link StyledTextStringEditor}
 *
 */
public class StyledTextReferenceDialog extends AbstractReferenceDialog implements SelectionListener {

	/**
	 * The styled text displaying the current value
	 */
	protected final StyledTextStringEditor styledTextStringEditor;

	/**
	 * The Button used to browse the available values
	 */
	protected Button browseValuesButton;

	/**
	 * The Button used to create a new instance
	 */
	protected Button createInstanceButton;

	/**
	 * The Button used to edit the current object
	 */
	protected Button editInstanceButton;

	/**
	 * The Button used to unset the current value
	 */
	protected Button unsetButton;

	/**
	 * The label provider used to display the values in both the label and the
	 * selection dialog
	 */
	protected ILabelProvider labelProvider;

	/**
	 * The content provider, providing the different possible values for the
	 * input object
	 */
	protected IStaticContentProvider contentProvider;

	/**
	 * The dialog used to select the value
	 */
	protected final ITreeSelectorDialog dialog;

	/**
	 * The current value for this editor
	 */
	protected Object value;

	/**
	 * The factory used to create or edit objects directly from this editor
	 */
	protected ReferenceValueFactory valueFactory;

	/**
	 * Boolean to detect direct creation.
	 */
	private boolean directCreation;

	/**
	 * Determinate if an error occurred.
	 */
	protected boolean error = false;

	/**
	 * The timer.
	 */
	private Timer timer;

	/**
	 * The timer tack for the change color.
	 */
	private TimerTask changeColorTask;

	/**
	 * Determinate if an edit occurred.
	 */
	private boolean edit = false;

	/**
	 * Constructs a new ReferenceDialog in the given parent Composite. The style
	 * will be applied to the syled text displaying the current value.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style of the styled text.
	 */
	public StyledTextReferenceDialog(final Composite parent, final int style) {
		this(parent, style, null);
	}

	/**
	 * Constructs a new ReferenceDialog in the given parent Composite. The style will be applied to the styled text displaying the current value. This constructor manage the value validator.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style of the styled text.
	 * @param targetValidator
	 *            The validator used for the styled text.
	 */
	public StyledTextReferenceDialog(final Composite parent, final int style, final AbstractValidator targetValidator) {
		super(parent, style);
		this.targetValidator = targetValidator;
		GridData gridData = getDefaultLayoutData();

		styledTextStringEditor = createStyledTextStringEditor(this, null, factory.getBorderStyle() | style);
		styledTextStringEditor.setLayoutData(gridData);
		styledTextStringEditor.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				editAction(); // TODO : Try to determine whether the double
				// click should call the edit, create or browse action
				// e.g. if the value is null, try to browse. If we cannot
				// browse, try to create an instance.
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// Nothing
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// Nothing
			}

		});

		dialog = createDialog(parent.getShell());

		createButtons();
		updateControls();
		controlDecoration = new ControlDecoration(styledTextStringEditor, SWT.TOP | SWT.LEFT);

		gridData.horizontalIndent = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
	}

	/**
	 * This allow to create the styled text editor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param initialValue
	 *            The initial value of the styled text.
	 * @param style
	 *            The style of the styled text.
	 * @return The created {@link StyledTextStringEditor}.
	 */
	protected StyledTextStringEditor createStyledTextStringEditor(final Composite parent, final String initialValue, final int style) {
		StyledTextStringEditor editor = new StyledTextStringEditor(parent, style, targetValidator);
		editor.setValue(initialValue);
		return editor;
	}

	/**
	 * This allow to create the dialog.
	 *
	 * @param shell
	 *            The current shell.
	 * @return The created {@link ITreeSelectorDialog}.
	 */
	protected ITreeSelectorDialog createDialog(final Shell shell) {
		return new TreeSelectorDialog(shell);
	}

	/**
	 * This allow to create the buttons of the reference dialog (browse, create, edit and unset).
	 */
	protected void createButtons() {
		((GridLayout) getLayout()).numColumns += 4;

		browseValuesButton = factory.createButton(this, null, SWT.PUSH);
		browseValuesButton.setImage(Activator.getDefault().getImage("/icons/browse_12x12.gif")); //$NON-NLS-1$
		browseValuesButton.setToolTipText(Messages.ReferenceDialog_EditValue);
		browseValuesButton.addSelectionListener(this);

		createInstanceButton = factory.createButton(this, null, SWT.PUSH);
		createInstanceButton.setImage(Activator.getDefault().getImage("/icons/Add_12x12.gif")); //$NON-NLS-1$
		createInstanceButton.setToolTipText(Messages.ReferenceDialog_CreateANewObject);
		createInstanceButton.addSelectionListener(this);

		editInstanceButton = factory.createButton(this, null, SWT.PUSH);
		editInstanceButton.setImage(Activator.getDefault().getImage("/icons/Edit_12x12.gif")); //$NON-NLS-1$
		editInstanceButton.setToolTipText(Messages.ReferenceDialog_EditTheCurrentValue);
		editInstanceButton.addSelectionListener(this);

		unsetButton = factory.createButton(this, null, SWT.PUSH);
		unsetButton.setImage(Activator.getDefault().getImage("/icons/Delete_12x12.gif")); //$NON-NLS-1$
		unsetButton.setToolTipText(Messages.ReferenceDialog_UnsetValue);
		unsetButton.addSelectionListener(this);
	}

	/**
	 * The action executed when the "browse" button is selected Choose a value
	 * from a selection of already created objects
	 */
	protected void browseAction() {
		setInitialSelection(Collections.singletonList(getValue()));
		int result = dialog.open();
		if (result == Window.OK) {
			Object[] newValue = dialog.getResult();
			if (newValue == null) {
				return;
			}

			if (newValue.length == 0) {
				setValue(null);
			} else {
				Object value = newValue[0];
				if (contentProvider instanceof IAdaptableContentProvider) {

					value = ((IAdaptableContentProvider) contentProvider).getAdaptedValue(value);
				}
				setValue(value);
				styledTextStringEditor.setValue(labelProvider.getText(newValue));
			}
		}
	}

	/**
	 * The action executed when the "create" button is selected Create a new
	 * instance and assign it to this reference
	 */
	protected void createAction() {
		if (valueFactory != null && valueFactory.canCreateObject()) {
			final Object context = getContextElement();
			getOperationExecutor(context).execute(new Runnable() {

				@Override
				public void run() {
					Object value = valueFactory.createObject(createInstanceButton, context);
					if (value == null) {
						// Cancel the operation
						throw new OperationCanceledException();
					}
					Collection<Object> validatedObjects = valueFactory.validateObjects(Collections.singleton(value));
					if (!validatedObjects.isEmpty()) {
						final Object newValue = validatedObjects.iterator().next();
						setValue(newValue);
						styledTextStringEditor.setValue(labelProvider.getText(newValue));
					}
				}
			}, NLS.bind(Messages.ReferenceDialog_setOperation, labelText));
		}
	}

	/**
	 * The action executed when the "edit" button is selected Edits the object
	 * that is currently selected
	 */
	protected void editAction() {
		styledTextStringEditor.setBackground(EDIT);
		edit = true;
		final Object currentValue = getValue();
		if (currentValue != null && valueFactory != null && valueFactory.canEdit()) {
			getOperationExecutor(currentValue).execute(new Runnable() {

				@Override
				public void run() {
					Object newValue = valueFactory.edit(editInstanceButton, currentValue);

					// Per the contract of ReferenceValueFactory::edit(), a null return means the object was edited "in place."
					// In that case, there is nothing further to do
					if (newValue != null) {
						setValue(newValue);
						styledTextStringEditor.setValue(labelProvider.getText(newValue));
					}

					updateLabel();
				}
			}, NLS.bind(Messages.ReferenceDialog_editOperation, labelText));
		}
	}

	/**
	 * The action executed when the "unset" button is selected Sets the current
	 * reference to null
	 */
	protected void unsetAction() {
		setValue(null);
	}

	/**
	 * Updates the displayed label for the current value
	 */
	@Override
	public void updateLabel() {
		if (binding != null) {
			binding.updateModelToTarget();
		} else {
			if (null != labelProvider) {
				styledTextStringEditor.setValue(labelProvider.getText(getValue()));
			}
		}
	}

	/**
	 * Sets the Content provider for this editor
	 *
	 * @param provider
	 *            The content provider used to retrieve the possible values for
	 *            this Reference
	 */
	@Override
	public void setContentProvider(final IStaticContentProvider provider) {
		dialog.setContentProvider(new EncapsulatedContentProvider(provider));
		if (getValue() != null) {
			setInitialSelection(Collections.singletonList(getValue()));
		}

		this.contentProvider = provider;
	}

	/**
	 * Sets the Label provider for this editor If the label provider is null, a
	 * default one will be used. The same label provider is used for both the
	 * editor's label and the selection dialog.
	 *
	 * @param provider
	 *            The label provider
	 */
	@Override
	public void setLabelProvider(final ILabelProvider provider) {
		if (provider == null) {
			setLabelProvider(new LabelProvider());
			return;
		}

		dialog.setLabelProvider(provider);
		this.labelProvider = provider;
		if (widgetObservable != null) {
			((StyledTextReferenceDialogObservableValue) widgetObservable).setLabelProvider(labelProvider);
		}
		updateLabel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLabel(final String label) {
		super.setLabel(label);
		dialog.setTitle(label);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValue() {
		if (modelProperty != null) {
			return modelProperty.getValue();
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getEditableType() {
		return Object.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		updateControls();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnly() {
		return !styledTextStringEditor.isEnabled();
	}

	/**
	 * Set the initial selection.
	 *
	 * @param initialValues
	 *            The list of possible values.
	 */
	protected void setInitialSelection(final List<?> initialValues) {
		dialog.setInitialElementSelections(initialValues);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#setModelObservable(org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@Override
	public void setModelObservable(final IObservableValue modelProperty) {
		setWidgetObservable(createWidgetObservable(modelProperty));
		super.setModelObservable(modelProperty);
		this.styledTextStringEditor.setModelObservable(modelProperty);

		updateControls();
	}

	/**
	 * This allow to create the widget observable value.
	 *
	 * @param modelProperty
	 *            The current observable value.
	 * @return The created {@link StyledTextReferenceDialogObservableValue}.
	 */
	protected IObservableValue createWidgetObservable(final IObservableValue modelProperty) {
		return new StyledTextReferenceDialogObservableValue(this, this.styledTextStringEditor.getText(), modelProperty, SWT.FocusOut, labelProvider);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.widgets.Control#setToolTipText(java.lang.String)
	 */
	@Override
	public void setToolTipText(final String text) {
		super.setLabelToolTipText(text);
		styledTextStringEditor.setToolTipText(text);
	}

	/**
	 * Set the factory.
	 *
	 * @param factory
	 *            The reference value factory.
	 */
	@Override
	public void setValueFactory(final ReferenceValueFactory factory) {
		valueFactory = factory;
		updateControls();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(final SelectionEvent e) {
		Widget widget = e.widget;
		if (widget == browseValuesButton) {
			browseAction();
		} else if (widget == createInstanceButton) {
			createAction();
		} else if (widget == editInstanceButton) {
			editAction();
		} else if (widget == unsetButton) {
			unsetAction();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {
		// Nothing
	}

	/**
	 * Updates the buttons' status
	 */
	@Override
	public void updateControls() {
		// Check if the edit & create buttons should be displayed
		boolean exclude = valueFactory == null || !valueFactory.canCreateObject();
		setExclusion(editInstanceButton, exclude);
		setExclusion(createInstanceButton, exclude);

		setExclusion(browseValuesButton, directCreation);

		browseValuesButton.setEnabled(!readOnly);

		// If they are displayed, check if they should be enabled
		if (!exclude) {
			editInstanceButton.setEnabled(valueFactory != null && valueFactory.canEdit() && getValue() != null);
			createInstanceButton.setEnabled(valueFactory != null && valueFactory.canCreateObject() && !readOnly);
		}

		// Do not display unset if the value is mandatory
		setExclusion(unsetButton, mandatory);
		if (!mandatory) {
			boolean enabled = !readOnly;
			enabled = enabled && getValue() != null;

			unsetButton.setEnabled(enabled);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.widgets.Control#update()
	 */
	@Override
	public void update() {
		super.update();
		updateControls();
	}

	/**
	 * Set the direct creation value.
	 *
	 * @param directCreation
	 *            Boolean to determinate the direct creation value.
	 */
	@Override
	public void setDirectCreation(final boolean directCreation) {
		this.directCreation = directCreation;
		updateControls();
	}

	/**
	 * Set the value.
	 *
	 * @param value
	 *            The value object.
	 */
	public void setValue(final Object value) {
		this.value = value;
		try {
			if (modelProperty != null) {
				modelProperty.setValue(value);
				error = false;
			}
		} catch (Exception e) {
			error = true;

		}

		updateControls();
		updateLabel();
		commit();
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#setInput(Object)
	 * @param input
	 *            The current input;
	 */
	public void setInput(final Object input) {
		this.dialog.setInput(input);
	}

	/**
	 * Set the mandatory.
	 *
	 * @param mandatory
	 *            The mandatory boolean value.
	 */
	@Override
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#updateStatus(org.eclipse.core.runtime.IStatus)
	 */
	@Override
	public void updateStatus(final IStatus status) {

		if (error) {
			FieldDecoration error = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			controlDecoration.setImage(error.getImage());
			controlDecoration.showHoverText(Messages.ReferenceDialog_0);
			controlDecoration.setDescriptionText(Messages.ReferenceDialog_1);
			controlDecoration.show();
			styledTextStringEditor.setBackground(ERROR);
			styledTextStringEditor.update();


		} else {
			controlDecoration.hide();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		if (null != changeColorTask) {
			changeColorTask.cancel();
		}
		if (null != timer) {
			timer.cancel();
		}
		if (null != styledTextStringEditor) {
			styledTextStringEditor.dispose();
		}
		super.dispose();
	}

	/**
	 * This allow to react of cancel of the current task.
	 */
	private void cancelCurrentTask() {
		if (changeColorTask != null) {
			changeColorTask.cancel();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#changeColorField()
	 */
	@Override
	public void changeColorField() {
		if (!error & !edit) {

			if (null == timer) {
				timer = new Timer(true);
			}

			cancelCurrentTask();
			changeColorTask = new TimerTask() {

				@Override
				public void run() {
					if (StyledTextReferenceDialog.this.isDisposed()) {
						return;
					}
					StyledTextReferenceDialog.this.getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							if (StyledTextReferenceDialog.this.isDisposed()) {// Bug 434787 : Shouldn't not execute the timer thread if the widget is disposed
								return;
							}
							styledTextStringEditor.setBackground(DEFAULT);
							styledTextStringEditor.update();
						}


					});
				}
			};

			if (errorBinding) {
				styledTextStringEditor.setBackground(ERROR);
				styledTextStringEditor.update();
			} else {
				IStatus status = (IStatus) binding.getValidationStatus().getValue();
				switch (status.getSeverity()) {
				case IStatus.OK:
				case IStatus.WARNING:
					timer.schedule(changeColorTask, 600);
					styledTextStringEditor.setBackground(VALID);
					styledTextStringEditor.update();
					break;
				case IStatus.ERROR:
					styledTextStringEditor.setBackground(ERROR);
					styledTextStringEditor.update();
					break;

				}
			}
		} else {
			styledTextStringEditor.setBackground(DEFAULT);
			styledTextStringEditor.update();
		}
	}

}
