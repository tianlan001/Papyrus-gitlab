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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.List;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.util.MultiplicityConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

/**
 * The multiplicity dialog which allow to define 2 'modes' : The simple mode with a string combo editor and the advanced with 2 editors of ValueSpecification.
 */
public class MultiplicityDialog extends AbstractValueEditor implements SelectionListener, IChangeListener {

	/**
	 * The string combo index in the list of editors.
	 */
	protected static final int MULTIPLICITY_STRING_COMBO_INDEX = 0;

	/**
	 * The lower value editor index in the list of editors.
	 */
	protected static final int MULTIPLICITY_LOWER_VALUE_INDEX = 1;

	/**
	 * The upper value editor index in the list of editors.
	 */
	protected static final int MULTIPLICITY_UPPER_VALUE_INDEX = 2;


	/**
	 * The stack layout for the 'mode' of editor to display.
	 */
	protected StackLayout stackLayout;

	/**
	 * The string combo editor for the 'simple' mode.
	 */
	protected StringCombo stringComboEditor;

	/**
	 * The parent stack layout composite.
	 */
	protected Composite stackLayoutComposite;

	/**
	 * The composite which contains the 'advanced' mode editors.
	 */
	protected Composite doubleEditorsComposite;

	/**
	 * The lower value specification editor.
	 */
	protected AbstractReferenceDialog lowerValueEditor;

	/**
	 * The upper value specification editor.
	 */
	protected AbstractReferenceDialog upperValueEditor;

	/**
	 * The switch editors button.
	 */
	protected Button switchEditorsButton;

	/**
	 * Boolean to determinate if the editors are read-only.
	 */
	protected boolean readOnly;

	/**
	 * The preference store.
	 */
	protected IPreferenceStore preferenceStore;


	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style.
	 */
	public MultiplicityDialog(final Composite parent, final int style) {
		this(parent, style, null);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style.
	 * @param preferenceStore
	 *            The preference store.
	 */
	public MultiplicityDialog(final Composite parent, final int style, final IPreferenceStore preferenceStore) {
		super(parent, style);

		// Create the stack layout composite
		stackLayout = new StackLayout();
		stackLayoutComposite = new Composite(this, style);
		stackLayoutComposite.setLayout(stackLayout);
		stackLayoutComposite.setLayoutData(getDefaultLayoutData());

		// Create the string combo editor
		stringComboEditor = new StringCombo(stackLayoutComposite, style) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceCombo#doBinding()
			 */
			@Override
			protected void doBinding() {
				setWidgetObservable(getObservableValue());
				super.doBinding();
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#setModelObservable(org.eclipse.core.databinding.observable.value.IObservableValue)
			 */
			@Override
			public void setModelObservable(IObservableValue modelProperty) {
				this.modelProperty = modelProperty;
				setWidgetObservable(getObservableValue());
				super.setModelObservable(modelProperty);
				updateControls();
			}
		};
		stringComboEditor.setLayoutData(getDefaultLayoutData());

		// Create the composite which contains the lower and the upper value specification editors
		doubleEditorsComposite = new Composite(stackLayoutComposite, style);
		final GridLayout layout = new GridLayout(2, true);
		// Manage the height and the width (for a better visualization)
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		doubleEditorsComposite.setLayout(layout);
		doubleEditorsComposite.setLayoutData(getDefaultLayoutData());

		// Create the lower ValueSpecification editor
		lowerValueEditor = createLowerValueSpecificationEditor(doubleEditorsComposite, style);
		lowerValueEditor.setLayoutData(getDefaultLayoutData());

		// Create the upper ValueSpecification editor
		upperValueEditor = createUpperValueSpecificationEditor(doubleEditorsComposite, style);
		upperValueEditor.setLayoutData(getDefaultLayoutData());

		// Add a property change listener on the preference
		this.preferenceStore = preferenceStore;
		if (null != preferenceStore) {
			this.preferenceStore.addPropertyChangeListener(new IPropertyChangeListener() {

				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					displayTopControl();
				}
			});
		}

		displayTopControl();

		createButtons();
		updateControls();
	}

	/**
	 * This allow to create the lower ValueSpecification editor.
	 * 
	 * @param parent
	 *            The parent composite
	 * @param style
	 *            The style.
	 * @return The create lower ValueSpecification editor.
	 */
	protected AbstractReferenceDialog createLowerValueSpecificationEditor(final Composite parent, final int style) {
		return createValueSpecificationEditor(parent, style);
	}

	/**
	 * This allow to create the upper ValueSpecification editor.
	 * 
	 * @param parent
	 *            The parent composite
	 * @param style
	 *            The style.
	 * @return The create upper ValueSpecification editor.
	 */
	protected AbstractReferenceDialog createUpperValueSpecificationEditor(final Composite parent, final int style) {
		return createValueSpecificationEditor(parent, style);
	}

	/**
	 * This allow to create the ValueSpecification editor.
	 * 
	 * @param parent
	 *            The parent composite
	 * @param style
	 *            The style.
	 * @return The create ValueSpecification editor.
	 */
	protected AbstractReferenceDialog createValueSpecificationEditor(final Composite parent, final int style) {
		return new ReferenceDialog(parent, style);
	}

	/**
	 * This allow to create the buttons.
	 */
	protected void createButtons() {
		((GridLayout) getLayout()).numColumns++;

		switchEditorsButton = factory.createButton(this, null, SWT.PUSH);
		switchEditorsButton.setImage(Activator.getDefault().getImage("/icons/Switch_12x12.gif")); //$NON-NLS-1$
		switchEditorsButton.setToolTipText(Messages.MultiplicityReferenceDialog_SwitchEditors);
		switchEditorsButton.addSelectionListener(this);
	}

	/**
	 * This allow to manage the stack layout top control displayed.
	 */
	protected void displayTopControl() {
		if (null != preferenceStore) {
			final String multiplicityEditorMode = preferenceStore.getString(MultiplicityConstants.MULTIPLICITY_EDITOR_MODE);
			if (null != multiplicityEditorMode) {
				// If the advanced mode is used, display the double editors composite, else use the simple mode with the string combo
				stackLayout.topControl = multiplicityEditorMode.equals(MultiplicityConstants.ADVANCED_MODE) ? doubleEditorsComposite : stringComboEditor;
			} else {
				if (null == stackLayout.topControl) {
					stackLayout.topControl = stringComboEditor;
				}
			}
		} else {
			if (null == stackLayout.topControl) {
				stackLayout.topControl = stringComboEditor;
			}
		}

		if (!stackLayoutComposite.isDisposed()) {
			stackLayoutComposite.layout();
		}
		setReadOnly(readOnly);
	}

	/**
	 * This allow to define the switch action for the switch buttons.
	 */
	protected void switchAction() {
		if (null != preferenceStore) {
			if (stackLayout.topControl.equals(stringComboEditor)) {
				preferenceStore.setValue(MultiplicityConstants.MULTIPLICITY_EDITOR_MODE, MultiplicityConstants.ADVANCED_MODE);
			} else {
				preferenceStore.setValue(MultiplicityConstants.MULTIPLICITY_EDITOR_MODE, MultiplicityConstants.SIMPLE_MODE);
			}
		} else {
			stackLayout.topControl = stackLayout.topControl.equals(stringComboEditor) ? doubleEditorsComposite : stringComboEditor;
		}
		// Refresh the read only value (because the lower and upper values must be different and multiplicity may not be update by simple editor)
		displayTopControl();
	}

	/**
	 * Updates the displayed label for the current value
	 */
	protected void updateLabels() {
		lowerValueEditor.updateLabel();
		upperValueEditor.updateLabel();
	}

	/**
	 * This allow to update the controls.
	 */
	protected void updateControls() {
		if (stackLayout.topControl.equals(stringComboEditor)) {
			if (!stringComboEditor.isDisposed()) {
				stringComboEditor.updateControls();
			}
		} else {
			if (!lowerValueEditor.isDisposed()) {
				lowerValueEditor.updateControls();
			}

			if (!upperValueEditor.isDisposed()) {
				upperValueEditor.updateControls();
			}
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
		if (stackLayout.topControl.equals(stringComboEditor)) {
			if (!stringComboEditor.isDisposed()) {
				stringComboEditor.update();
			}
		} else {
			if (!lowerValueEditor.isDisposed()) {
				lowerValueEditor.update();
			}

			if (!upperValueEditor.isDisposed()) {
				upperValueEditor.update();
			}
		}
		updateControls();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractListEditor#doBinding()
	 */
	@Override
	protected void doBinding() {
		super.doBinding();
		if (null != stringComboEditor) {
			stringComboEditor.doBinding();
		}
		if (null != lowerValueEditor) {
			lowerValueEditor.doBinding();
		}
		if (null != upperValueEditor) {
			upperValueEditor.doBinding();
		}
		modelProperty.addChangeListener(this);
	}

	/**
	 * Sets the content providers.
	 * 
	 * @param providers
	 *            The content providers for each editor.
	 */
	public void setContentProviders(final List<IStaticContentProvider> providers) {
		stringComboEditor.setContentProvider(getObjectFromList(providers, MULTIPLICITY_STRING_COMBO_INDEX));
		lowerValueEditor.setContentProvider(getObjectFromList(providers, MULTIPLICITY_LOWER_VALUE_INDEX));
		upperValueEditor.setContentProvider(getObjectFromList(providers, MULTIPLICITY_UPPER_VALUE_INDEX));
	}

	/**
	 * Sets the label providers.
	 * 
	 * @param providers
	 *            The label providers for each editor.
	 */
	public void setLabelProviders(final List<ILabelProvider> providers) {
		stringComboEditor.setLabelProvider(getObjectFromList(providers, MULTIPLICITY_STRING_COMBO_INDEX));
		lowerValueEditor.setLabelProvider(getObjectFromList(providers, MULTIPLICITY_LOWER_VALUE_INDEX));
		upperValueEditor.setLabelProvider(getObjectFromList(providers, MULTIPLICITY_UPPER_VALUE_INDEX));

		updateControls();
		updateLabels();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#getEditableType()
	 */
	@Override
	public Object getEditableType() {
		return Object.class;
	}

	/**
	 * This allow to define if the string combo can be edited.
	 * 
	 * @return <code>true</code> if the string combo can be edited, <code>false</code> otherwise.
	 */
	protected boolean canEditStringCombo() {
		return true;
	}

	/**
	 * This allow to define if the lower value can be edited.
	 * 
	 * @return <code>true</code> if the lower value can be edited, <code>false</code> otherwise.
	 */
	protected boolean canEditLowerValue() {
		return true;
	}

	/**
	 * This allow to define if the upper value can be edited.
	 * 
	 * @return <code>true</code> if the upper value can be edited, <code>false</code> otherwise.
	 */
	protected boolean canEditUpperValue() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractListEditor#setModelObservable(org.eclipse.core.databinding.observable.list.IObservableList)
	 */
	@Override
	public void setModelObservable(final IObservableValue modelProperty) {
		setWidgetObservable(modelProperty);
		setEditorsModelObservable(modelProperty);
		super.setModelObservable(modelProperty);

		updateControls();
		updateLabels();
	}

	/**
	 * Sets the model properties for the editors.
	 * 
	 * @param modelProperty
	 *            The observable value.
	 */
	protected void setEditorsModelObservable(final IObservableValue modelProperty) {
		if (null != modelProperty && modelProperty.getValue() instanceof List<?>) {
			final Object stringComboObservableValue = getObjectFromList((List<?>) modelProperty.getValue(), MULTIPLICITY_STRING_COMBO_INDEX);
			if (stringComboObservableValue instanceof IObservableValue) {
				stringComboEditor.setModelObservable((IObservableValue) stringComboObservableValue);
			}
			final Object lowerValueObservableValue = getObjectFromList((List<?>) modelProperty.getValue(), MULTIPLICITY_LOWER_VALUE_INDEX);
			if (lowerValueObservableValue instanceof IObservableValue) {
				lowerValueEditor.setModelObservable((IObservableValue) lowerValueObservableValue);
			}
			final Object upperValueObservableValue = getObjectFromList((List<?>) modelProperty.getValue(), MULTIPLICITY_UPPER_VALUE_INDEX);
			if (upperValueObservableValue instanceof IObservableValue) {
				upperValueEditor.setModelObservable((IObservableValue) upperValueObservableValue);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.widgets.Control#setToolTipText(java.lang.String)
	 */
	@Override
	public void setToolTipText(String text) {
		stringComboEditor.setToolTipText(text);
		lowerValueEditor.setToolTipText(Messages.MultiplicityReferenceDialog_LowerValueToolTip);
		upperValueEditor.setToolTipText(Messages.MultiplicityReferenceDialog_UpperValueToolTip);
	}

	/**
	 * Sets the value factories.
	 * 
	 * @param factories
	 *            The Reference Value factories.
	 */
	public void setValueFactories(final List<ReferenceValueFactory> factories) {
		lowerValueEditor.setValueFactory(getObjectFromList(factories, MULTIPLICITY_LOWER_VALUE_INDEX));
		upperValueEditor.setValueFactory(getObjectFromList(factories, MULTIPLICITY_UPPER_VALUE_INDEX));
		updateControls();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(final boolean readOnly) {
		this.readOnly = readOnly;
		if (stackLayout.topControl.equals(stringComboEditor)) {
			if (!stringComboEditor.isDisposed()) {
				stringComboEditor.setReadOnly(readOnly || !canEditStringCombo());
			}
		} else {
			if (!lowerValueEditor.isDisposed()) {
				lowerValueEditor.setReadOnly(readOnly || !canEditLowerValue());
			}
			if (!upperValueEditor.isDisposed()) {
				upperValueEditor.setReadOnly(readOnly || !canEditUpperValue());
			}
		}
		updateControls();
	}

	/**
	 * This allow to set the direct creation of the lower and the upper value editors.
	 * 
	 * @param directCreation
	 *            The direct creation value.
	 */
	public void setDirectCreation(final boolean directCreation) {
		lowerValueEditor.setDirectCreation(directCreation);
		upperValueEditor.setDirectCreation(directCreation);
		updateControls();
	}

	/**
	 * This allow to set the mandatory of the lower and the upper value editors.
	 * 
	 * @param mandatory
	 *            The mandatory value.
	 */
	public void setMandatory(final boolean mandatory) {
		lowerValueEditor.setMandatory(mandatory);
		upperValueEditor.setMandatory(mandatory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(final SelectionEvent e) {
		Widget widget = e.widget;
		if (widget == switchEditorsButton) {
			switchAction();
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		if (null != stringComboEditor) {
			stringComboEditor.dispose();
		}
		if (null != lowerValueEditor) {
			lowerValueEditor.dispose();
		}
		if (null != upperValueEditor) {
			upperValueEditor.dispose();
		}
		super.dispose();
	}

	/**
	 * Get the object index from the list of objects.
	 * 
	 * @param listObjects
	 *            The list of objects.
	 * @param index
	 *            The index object to get.
	 * @return The object at the index position or <code>null</code>.
	 */
	protected <T> T getObjectFromList(final List<T> listObjects, final int index) {
		T object = null;
		if (null != listObjects && !listObjects.isEmpty()) {
			if (listObjects.size() > index) {
				object = listObjects.get(index);
			}
		}
		return object;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#getValue()
	 */
	@Override
	public Object getValue() {
		return modelProperty.getValue();
	}

	/**
	 * Redefine this method to re-affect the correct observable value to each editors.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.IChangeListener#handleChange(org.eclipse.core.databinding.observable.ChangeEvent)
	 */
	@Override
	public void handleChange(final ChangeEvent event) {
		// Only refresh the model observables and the read only value
		setEditorsModelObservable(modelProperty);
		setReadOnly(readOnly);
		// The others variables (labelProviders, contentProviders, mandatory and directCreation) don't need to change
	}

}
