/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 402525
 *  Christian W. Damus (CEA) - bug 443497
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
import org.eclipse.papyrus.infra.widgets.databinding.CLabelObservableValue;
import org.eclipse.papyrus.infra.widgets.databinding.ReferenceDialogObservableValue;
import org.eclipse.papyrus.infra.widgets.databinding.StyledTextReferenceDialogObservableValue;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
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
 * An editor representing a single reference as a Label A filtered selection
 * dialog is used to edit the value. Also offers support for unsetting the
 * value. This Editor needs a ContentProvider, and may use an optional
 * LabelProvider, describing the objects that can be referred by this property
 *
 * @author Camille Letavernier
 *
 */
public class ReferenceDialog extends AbstractReferenceDialog implements SelectionListener {

	/**
	 * The CLabel displaying the current value
	 */
	protected final CLabel currentValueLabel;

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

	private boolean directCreation;

	private ControlDecoration controlDecoration;

	protected boolean error = false;

	private Timer timer;

	private TimerTask changeColorTask;

	private boolean edit = false;

	/**
	 *
	 * Constructs a new ReferenceDialog in the given parent Composite. The style
	 * will be applied to the CLabel displaying the current value.
	 *
	 * @param parent
	 * @param style
	 */
	public ReferenceDialog(Composite parent, int style) {
		super(parent, style);
		GridData gridData = getDefaultLayoutData();

		currentValueLabel = factory.createCLabel(this, null, factory.getBorderStyle() | style);
		currentValueLabel.setLayoutData(gridData);
		currentValueLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				editAction(); // TODO : Try to determine whether the double
				// click should call the edit, create or browse
				// action
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
		controlDecoration = new ControlDecoration(currentValueLabel, SWT.TOP | SWT.LEFT);

		gridData.horizontalIndent = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
	}

	protected ITreeSelectorDialog createDialog(Shell shell) {
		return new TreeSelectorDialog(shell);
	}

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
						setValue(validatedObjects.iterator().next());
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
		currentValueLabel.setBackground(EDIT);
		edit = true;
		final Object currentValue = getValue();
		if (currentValue != null && valueFactory != null && valueFactory.canEdit()) {
			getOperationExecutor(currentValue).execute(new Runnable() {

				@Override
				public void run() {
					Object newValue = valueFactory.edit(editInstanceButton, currentValue);

					// Per the contract of ReferenceValueFactory::edit(), a null return means the object was edited "in place."
					// In that case, there is nothing further to do
					if ((newValue != null) && (newValue != currentValue)) {
						setValue(newValue);
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
				currentValueLabel.setImage(labelProvider.getImage(getValue()));
				currentValueLabel.setText(labelProvider.getText(getValue()));
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
	public void setContentProvider(IStaticContentProvider provider) {
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
	public void setLabelProvider(ILabelProvider provider) {
		if (provider == null) {
			setLabelProvider(new LabelProvider());
			return;
		}

		dialog.setLabelProvider(provider);
		this.labelProvider = provider;
		if (widgetObservable != null) {
			((CLabelObservableValue) widgetObservable).setLabelProvider(labelProvider);
		}
		updateLabel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLabel(String label) {
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
		return !currentValueLabel.isEnabled();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doBinding() {
		super.doBinding();
	}

	protected void setInitialSelection(List<?> initialValues) {
		dialog.setInitialElementSelections(initialValues);
	}

	@Override
	public void setModelObservable(IObservableValue modelProperty) {
		setWidgetObservable(createWidgetObservable(modelProperty));
		super.setModelObservable(modelProperty);
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
		return new ReferenceDialogObservableValue(this, this.currentValueLabel, modelProperty, labelProvider);
	}

	@Override
	public void setToolTipText(String text) {
		super.setLabelToolTipText(text);
		currentValueLabel.setToolTipText(text);
	}

	@Override
	public void setValueFactory(ReferenceValueFactory factory) {
		valueFactory = factory;
		updateControls();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Widget widget = e.widget;
		try {
			if (widget == browseValuesButton) {
				browseAction();
			} else if (widget == createInstanceButton) {
				createAction();
			} else if (widget == editInstanceButton) {
				editAction();
			} else if (widget == unsetButton) {
				unsetAction();
			}
		} catch (OperationCanceledException canceledException) {
			// do nothing, this exception occurs whenever one of the actions above
			// gets canceled
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
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

	@Override
	public void update() {
		super.update();
		updateControls();
	}

	@Override
	public void setDirectCreation(boolean directCreation) {
		this.directCreation = directCreation;
		updateControls();
	}

	public void setValue(Object value) {
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
	 */
	public void setInput(Object input) {
		this.dialog.setInput(input);
	}

	@Override
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Override
	public void updateStatus(IStatus status) {

		if (error) {
			FieldDecoration error = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			controlDecoration.setImage(error.getImage());
			controlDecoration.showHoverText(Messages.ReferenceDialog_0);
			controlDecoration.setDescriptionText(Messages.ReferenceDialog_1);
			controlDecoration.show();
			currentValueLabel.setBackground(ERROR);
			currentValueLabel.update();


		} else {
			controlDecoration.hide();
		}
	}

	@Override
	public void dispose() {
		if (changeColorTask != null) {
			changeColorTask.cancel();
		}
		if (timer != null) {
			timer.cancel();
		}
		super.dispose();
	}

	private void cancelCurrentTask() {
		if (changeColorTask != null) {
			changeColorTask.cancel();
		}
	}

	@Override
	public void changeColorField() {
		if (!error & !edit) {

			if (timer == null) {
				timer = new Timer(true);
			}

			cancelCurrentTask();
			changeColorTask = new TimerTask() {

				@Override
				public void run() {
					if (ReferenceDialog.this.isDisposed()) {
						return;
					}
					ReferenceDialog.this.getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							if (ReferenceDialog.this.isDisposed()) {// Bug 434787 : Shouldn't not execute the timer thread if the widget is disposed
								return;
							}
							currentValueLabel.setBackground(DEFAULT);
							currentValueLabel.update();
						}


					});
				}
			};

			if (errorBinding) {
				currentValueLabel.setBackground(ERROR);
				currentValueLabel.update();
			} else {
				IStatus status = (IStatus) binding.getValidationStatus().getValue();
				switch (status.getSeverity()) {
				case IStatus.OK:
				case IStatus.WARNING:
					timer.schedule(changeColorTask, 600);
					currentValueLabel.setBackground(VALID);
					currentValueLabel.update();
					break;
				case IStatus.ERROR:
					currentValueLabel.setBackground(ERROR);
					currentValueLabel.update();
					break;

				}
			}
		} else {
			currentValueLabel.setBackground(DEFAULT);
		}
	}

}
