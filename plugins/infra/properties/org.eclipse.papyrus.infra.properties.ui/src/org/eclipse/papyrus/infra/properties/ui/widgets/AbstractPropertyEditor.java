/*****************************************************************************
 * Copyright (c) 2010, 2016-2017,2020 CEA LIST, Christian W. Damus, Esterel Technologies SAS and others.
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
 *  Christian W. Damus (CEA) - bug 417409
 *  Christian W. Damus (CEA) - bug 443417
 *  Christian W. Damus (CEA) - bug 444227
 *  Christian W. Damus - bug 469188
 *  Sebastien Gabel (Esterel Technologies SAS) - bug 497367
 *  Sebastien Gabel (Esterel Technologies SAS) - bug 497461
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 515650
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 522124
 *  Patrick Tessier (CEA LIST) -bug 568329
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;
import org.eclipse.papyrus.infra.properties.ui.listeners.IPropertiesListener;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSourceChangedEvent;
import org.eclipse.papyrus.infra.properties.ui.modelelement.IDataSourceListener;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ILabeledModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesUtil;
import org.eclipse.papyrus.infra.widgets.editors.AbstractEditor;
import org.eclipse.papyrus.infra.widgets.editors.AbstractListEditor;
import org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor;
import org.eclipse.papyrus.infra.widgets.editors.ICommitListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.osgi.framework.Bundle;


/**
 * An Abstract class to factorize code for PropertyEditors. PropertyEditors are
 * <strong>not</strong> required to extend this class, but could benefit from
 * its methods.
 *
 * @author Camille Letavernier
 */
public abstract class AbstractPropertyEditor implements IChangeListener, CustomizablePropertyEditor {

	/**
	 * The qualified propertyPath. Represents the property edited by this widget
	 */
	protected String propertyPath; // Format : "DataContextElement:propertyName"

	/**
	 * The DataSource representing the semantic objects
	 */
	protected DataSource input;

	private IDataSourceListener dataSourceListener;

	protected boolean readOnly = false;

	protected boolean isEditable = true;

	/**
	 * The SWT Widget (For list properties only)
	 */
	protected AbstractListEditor listEditor;

	/**
	 * The SWT Widget (For single values only)
	 */
	protected AbstractValueEditor valueEditor;

	/**
	 * The IObservableList representing the semantic property
	 * (For list properties only)
	 */
	protected IObservableList observableList;

	/**
	 * The IObservableValue representing the semantic property
	 * (For single values only)
	 */
	protected IObservableValue observableValue;


	protected IValidator modelValidator;

	protected IConverter targetToModelConverter;

	/**
	 * Indicates if the editor's label should be displayed
	 */
	protected boolean showLabel = true;

	/**
	 * The custom label used by this editor. If set, it replaces the property's default label
	 */
	protected String customLabel;

	/**
	 * The maximum number of characters per line for wrapping descriptions
	 */
	public static int descriptionMaxCharPerLine = 200;

	/**
	 * List separator for listenerClasses and listeningPropertyPaths.
	 */
	private static final String LIST_SEPARATOR = ","; //$NON-NLS-1$

	/**
	 * Separator for bundle and class on the listenerClasses value.
	 */
	private static final String BUNDLE_SEPARATOR = "/"; //$NON-NLS-1$

	/**
	 * List of properties to listen.
	 *
	 * @since 3.1
	 */
	protected String listeningPropertyPaths;

	/**
	 * HashSet of Listening property paths.
	 *
	 * @since 3.1
	 */
	protected Set<String> listeningPropertyPathsSet;

	/**
	 * List of classes used to define the behaviors to be adopted when modifying the values of the properties.
	 *
	 * @since 3.1
	 */
	protected String listenerClasses;

	/**
	 * HashSet of listener classes.
	 *
	 * @since 3.1
	 */
	protected Set<IPropertiesListener> listenerClassesSet;

	/**
	 * Constructor.
	 * When using this constructor, you should explicitly call the #setEditor method.
	 */
	protected AbstractPropertyEditor() {
	}

	/**
	 * Constructor. Constructs a new PropertyEditor with the given ListEditor
	 *
	 * @param editor
	 */
	protected AbstractPropertyEditor(AbstractListEditor editor) {
		setEditor(editor);
	}

	/**
	 * Constructor. Constructs a new PropertyEditor with the given ValueEditor
	 *
	 * @param editor
	 */
	protected AbstractPropertyEditor(AbstractValueEditor editor) {
		setEditor(editor);
	}

	/**
	 * Sets the ListEditor for this PropertyEditor
	 *
	 * @param editor
	 */
	protected void setEditor(AbstractListEditor editor) {
		this.listEditor = editor;
		addDisposeListener(editor);
	}

	/**
	 * Sets the ValueEditor for this PropertyEditor
	 *
	 * @param editor
	 */
	protected void setEditor(AbstractValueEditor editor) {
		this.valueEditor = editor;
		addDisposeListener(editor);
	}

	private void addDisposeListener(AbstractEditor editor) {
		editor.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (input != null) {
					input.removeChangeListener(AbstractPropertyEditor.this);
					unhookDataSourceListener(input);
				}
			}
		});
	}

	/**
	 * Checks if this editor has all the input needed to do the dataBinding.
	 * As this editor can be instantiated through the XWT Framework, which is
	 * based on an XML parser, there is no determinism in the order in which
	 * the parameters are set.
	 */
	protected void checkInput() {
		if (propertyPath != null && input != null) {
			isEditable = input.isEditable(propertyPath);
			try {
				doBinding();
			} catch (Exception ex) {
				// TODO : Handle the exception here. Display something ?
				Activator.log.error(ex);
			}

			if (getInputObservable() instanceof ICommitListener && getEditor() != null) {
				getEditor().addCommitListener((ICommitListener) getInputObservable());
			}

			updateLabel();
			updateDescription();
		}
	}

	/**
	 * Binds the AbstractEditor (Either List or Value editor) to the semantic element
	 */
	protected void doBinding() {
		if (listEditor != null) {
			IObservableList inputObservableList = getInputObservableList();

			if (inputObservableList != null) {
				listEditor.setModelObservable(inputObservableList);
			}

		} else if (valueEditor != null) {
			IObservableValue inputObservableValue = getInputObservableValue();

			if (inputObservableValue != null) {
				valueEditor.setStrategies();



				IValidator modelVal = getValidator();
				if (modelVal != null) {

					valueEditor.setModelValidator(modelVal);
				}
				valueEditor.setModelObservable(inputObservableValue);




			}
		}

		boolean isReadOnly = getReadOnly();
		applyReadOnly(isReadOnly);

		if (input.forceRefresh(propertyPath)) {
			input.addChangeListener(this);
		}
	}

	/**
	 * Applies the readOnly state to the editor
	 *
	 * @param readOnly
	 *            Indicates if this widget should be read-only
	 */
	protected void applyReadOnly(boolean readOnly) {
		AbstractEditor editor = getEditor();
		if ((editor != null) && !editor.isDisposed()) {
			editor.setReadOnly(readOnly);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO : This method handles a change on the DataSource. This should not be a ChangeEvent, as the DataSource is not an IObservable
	// This method should be changed, and the source of the event should be checked (Otherwise, it cannot be extended).
	@Override
	public void handleChange(ChangeEvent event) {
		// Handle the "forceRefresh" behavior when the input DataSource sends a ChangeEvent
		AbstractEditor editor = getEditor();

		if (editor != null && !editor.isDisposed()) {
			editor.refreshValue();

			// And refresh the read-only state
			isEditable = input.isEditable(propertyPath);
			applyReadOnly(getReadOnly());
		}
	}

	/**
	 * Sets the property path for this PropertyEditor.
	 * The propertyPath elements should be separated by ":"
	 * e.g. UML:NamedElement:name
	 *
	 * @param path
	 */
	@Override
	public void setProperty(String path) {
		propertyPath = path;
		checkInput();
		updateLabel();
		updateDescription();

		manageObservableListeners();
	}

	/**
	 * Updates the label for this PropertyEditor.
	 */
	public void updateLabel() {
		String label = getLabel();
		// if(input != null && propertyPath != null && input.isMandatory(propertyPath)) {
		// label += " *"; //$NON-NLS-1$
		// }

		updateLabel(label);
	}

	/**
	 * Updates the label for this PropertyEditor.
	 */
	public void updateLabel(String label) {
		if (showLabel) {
			if (valueEditor != null) {
				valueEditor.setLabel(label);
			} else if (listEditor != null) {
				listEditor.setLabel(label);
			}
		}
	}

	/**
	 * @return the property path for this Property editor.
	 */
	@Override
	public String getProperty() {
		return propertyPath;
	}

	/**
	 * Sets the input DataSource for this Property editor.
	 *
	 * @param input
	 */
	@Override
	public void setInput(DataSource input) {
		final DataSource oldInput = this.input;
		if (input != oldInput) {
			if (oldInput != null) {
				unhookDataSourceListener(oldInput);
			}

			this.input = input;

			if (input != null) {
				hookDataSourceListener(input);
			}

			// Only do this after attaching our listener so that it will be ahead of
			// any ModelElements created for properties
			checkInput();

			manageObservableListeners();
		}
	}

	protected void unhookDataSourceListener(DataSource oldInput) {
		oldInput.removeDataSourceListener(getDataSourceListener());
	}

	protected void hookDataSourceListener(DataSource newInput) {
		newInput.addDataSourceListener(getDataSourceListener());
	}

	/**
	 * @return the input DataSource for this Property editor
	 */
	@Override
	public DataSource getInput() {
		return input;
	}

	/**
	 * @return the formatted property name for this Property Editor
	 */
	protected String getLabel() {
		if (customLabel != null) {
			return customLabel;
		}

		if (null != getInput()) {
			final ModelElement modelElement = getInput().getModelElement(propertyPath);
			if (modelElement instanceof ILabeledModelElement) {
				final String label = ((ILabeledModelElement) modelElement).getLabel(getLocalPropertyPath());
				if (null != label && !label.isEmpty()) {
					return label;
				}
			}
		}

		Property property = getModelProperty();
		if (property == null || property.getLabel() == null || property.getLabel().trim().equals("")) { //$NON-NLS-1$
			return PropertiesUtil.getLabel(getLocalPropertyPath());
		}

		return property.getLabel();
	}

	/**
	 * Updates the description for this PropertyEditor.
	 * The description is the widget's ToolTipText
	 */
	protected void updateDescription() {
		String description = ""; //$NON-NLS-1$
		Property property = getModelProperty();

		if (property != null) {
			description = property.getDescription();
		}

		// Append the propertyPath to the description
		if (description == null || description.trim().equals("")) { //$NON-NLS-1$
			description = getLocalPropertyPath();
		} else {
			description = PropertiesUtil.resizeString(description, descriptionMaxCharPerLine);
			description = getLocalPropertyPath() + ": " + description;
		}


		updateDescription(description);
	}

	/**
	 * Updates the description for this PropertyEditor.
	 * The description is the widget's ToolTipText
	 */
	protected void updateDescription(String description) {
		if (valueEditor != null) {
			valueEditor.setToolTipText(description);
		} else if (listEditor != null) {

			listEditor.setToolTipText(description);
		}
	}

	/**
	 * Finds the property associated to the Editor's {@link #propertyPath}
	 *
	 * @return The property associated to the Editor's {@link #propertyPath}
	 */
	protected Property getModelProperty() {
		if (propertyPath == null) {
			return null;
		}
		Context context = getContext();

		return PropertiesRuntime.getConfigurationManager().getProperty(propertyPath, context);
	}

	private Context getContext() {
		if (input == null) {
			return null;
		} else {
			return input.getView().getContext();
		}
	}

	/**
	 * Marks this editor as readOnly
	 *
	 * @param readOnly
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		if (getEditor() != null) {
			getEditor().setReadOnly(getReadOnly());
		}
	}

	/**
	 * @return the AbstractEditor for this PropertyEditor
	 */
	public AbstractEditor getEditor() {
		return valueEditor == null ? listEditor : valueEditor;
	}

	/**
	 * Tests if this editor is read-only
	 *
	 * @return
	 *         True if this editor is read-only
	 */
	@Override
	public boolean getReadOnly() {
		return readOnly || !isEditable || getInputObservable() == null;
	}

	/**
	 * @return the IObservableList for this propertyEditor, or null if it is not
	 *         available
	 */
	protected IObservableList getInputObservableList() {
		if (observableList == null) {
			try {
				observableList = (IObservableList) input.getObservable(propertyPath);
			} catch (Exception ex) {
				Activator.log.error("Cannot find a valid IObservableList for " + propertyPath, ex); //$NON-NLS-1$
			}
		}

		return observableList;
	}

	/**
	 * @return the IObservableValue for this propertyEditor, or null if it is not
	 *         available
	 */
	protected IObservableValue getInputObservableValue() {
		if (observableValue == null) {
			try {
				observableValue = (IObservableValue) input.getObservable(propertyPath);
			} catch (Exception ex) {
				Activator.log.error("Cannot find a valid IObservableValue for " + propertyPath, ex); //$NON-NLS-1$
			}
		}

		return observableValue;
	}

	/**
	 * Returns the IObservable for this propertyEditor, or null if it is
	 * not available
	 *
	 * @return The IObservable associated to this propertyEditor
	 */
	protected IObservable getInputObservable() {
		if (input == null || propertyPath == null) {
			return null;
		}
		if (listEditor != null) {
			return getInputObservableList();
		}
		if (valueEditor != null) {
			return getInputObservableValue();
		}
		return null;
	}

	/**
	 * @return the last segment of the property path (Which is the property name)
	 */
	protected String getLocalPropertyPath() {
		return propertyPath.substring(propertyPath.lastIndexOf(":") + 1); //$NON-NLS-1$
	}

	/**
	 * Sets the editor's Layout Data
	 *
	 * @param data
	 */
	public void setLayoutData(Object data) {
		if (getEditor() != null) {
			getEditor().setLayoutData(data);
		}
	}

	/**
	 * Returns the editor's Layout Data
	 *
	 * @return
	 *         The editor's layout data
	 */
	public Object getLayoutData() {
		return getEditor() == null ? null : getEditor().getLayoutData();
	}

	/**
	 * Sets the editor's Layout
	 *
	 * @param layout
	 */
	public void setLayout(Layout layout) {
		if (getEditor() != null) {
			getEditor().setLayout(layout);
		}
	}

	/**
	 * Returns the editor's Layout
	 *
	 * @return
	 *         The editor's layout
	 */
	public Layout getLayout() {
		return getEditor() == null ? null : getEditor().getLayout();
	}

	/**
	 * Indicates whether the editor's label should be displayed or not
	 *
	 * @param showLabel
	 */
	@Override
	public void setShowLabel(boolean showLabel) {
		AbstractEditor editor = getEditor();
		this.showLabel = showLabel;
		if (editor != null) {
			editor.setDisplayLabel(showLabel);
		}
	}

	/**
	 * Indicates whether the editor's label is displayed or not
	 *
	 * @return
	 *         true if the label should be displayed
	 */
	@Override
	public boolean getShowLabel() {
		return this.showLabel;
	}

	/**
	 * Sets the label for this editor. The label will replace the property's
	 * default label
	 *
	 * @param customLabel
	 *            The label to use with this property editor
	 */
	@Override
	public void setCustomLabel(String customLabel) {
		this.customLabel = customLabel;
		updateLabel();
	}

	/**
	 * @return the custom label used by this property editor. May be null
	 */
	@Override
	public String getCustomLabel() {
		return this.customLabel;
	}

	/**
	 * @return the Control defined by this Property Editor
	 */
	public Control getControl() {
		if (valueEditor == null) {
			return listEditor;
		}
		return valueEditor;
	}

	/**
	 * @return the IValidator for this property editor
	 */

	public IValidator getValidator() {
		if (modelValidator == null) {
			try {
				modelValidator = input.getValidator(propertyPath);
			} catch (Exception ex) {
				Activator.log.error("Cannot find a valid Validator for " + propertyPath, ex); //$NON-NLS-1$

			}
		}

		return modelValidator;
	}

	private IDataSourceListener getDataSourceListener() {
		if (dataSourceListener == null) {
			dataSourceListener = new IDataSourceListener() {

				@Override
				public void dataSourceChanged(DataSourceChangedEvent event) {
					// The data source's selection changed. Update my validator or clear it if now there is none
					IObservableValue observable = AbstractPropertyEditor.this.observableValue;

					if (observable != null) {
						if ((modelValidator != null) && (valueEditor != null) && !valueEditor.isDisposed()) {
							modelValidator = null;

							// First, clear the validator to disable validation
							valueEditor.setStrategies();
							valueEditor.setModelValidator(null);

							// Then re-enable to later when ready for user input
							observable.getRealm().asyncExec(new Runnable() {

								@Override
								public void run() {
									if ((valueEditor != null) && !valueEditor.isDisposed()) {
										valueEditor.setStrategies();
										valueEditor.setModelValidator(getValidator());
									}
								}
							});
						}

						// And refresh the read-only state
						if ((propertyPath != null) && (input != null)) {
							observable.getRealm().asyncExec(new Runnable() {

								@Override
								public void run() {
									isEditable = input.isEditable(propertyPath);
									applyReadOnly(getReadOnly());
								}
							});
						}
					}
				}
			};
		}

		return dataSourceListener;
	}

	/**
	 * Return the listening property paths.
	 *
	 * @return the listeningPropertyPaths The listening property paths.
	 * @since 3.1
	 */
	public String getListeningPropertyPaths() {
		return listeningPropertyPaths;
	}

	// /** FIXME: this getter introduce a regression
	// See Bug 522124 and https://dev.eclipse.org/mhonarc/lists/mdt-papyrus.dev/msg04151.html
	// * Return the HashSet of listening property paths.
	// *
	// * @return the listeningPropertyPathsSet The HashSet of listening property paths.
	// * @since 3.1
	// */
	// public Set<String> getListeningPropertyPathsSet() {
	// return listeningPropertyPathsSet;
	// }

	/**
	 * Return the listener classes.
	 *
	 * @return the listenerClasses The listener classes.
	 * @since 3.1
	 */
	public String getListenerClasses() {
		return listenerClasses;
	}

	// /**FIXME: this getter introduce a regression
	// See Bug 522124 and https://dev.eclipse.org/mhonarc/lists/mdt-papyrus.dev/msg04151.html
	// * Return the HashSet of listener classes.
	// *
	// * @return the listenerClassesSet HashSet of listener classes.
	// * @since 3.1
	// */
	// public Set<IPropertiesListener> getListenerClassesSet() {
	// return listenerClassesSet;
	// }

	/**
	 * Set the HashSet of listening property paths.
	 *
	 * @param listeningPropertyPaths
	 * @since 3.1
	 */
	public void setListeningPropertyPaths(final String listeningPropertyPaths) {
		this.listeningPropertyPaths = listeningPropertyPaths;

		if (listeningPropertyPaths.isEmpty()) {
			this.listeningPropertyPathsSet = null;
		} else {
			final String[] propertyPaths = listeningPropertyPaths.trim().split(LIST_SEPARATOR);

			if (null == this.listeningPropertyPathsSet) {
				this.listeningPropertyPathsSet = new HashSet<>();
			}

			for (int i = 0; i < propertyPaths.length; i++) {
				if (!propertyPaths[i].isEmpty()) {
					this.listeningPropertyPathsSet.add(propertyPaths[i]);
				}
			}

			manageObservableListeners();
		}
	}

	/**
	 * Set the HashSet of listener classes.
	 *
	 * @param listenerClasses
	 *            The list of listener classes.
	 * @since 3.1
	 */
	public void setListenerClasses(final String listenerClasses) {
		this.listenerClasses = listenerClasses;
		if (listenerClasses.isEmpty()) {
			this.listenerClassesSet = null;
		} else {
			final String[] propertyPaths = listenerClasses.trim().split(LIST_SEPARATOR);

			if (null == this.listenerClassesSet) {
				this.listenerClassesSet = new HashSet<>();
			}

			for (int i = 0; i < propertyPaths.length; i++) {
				final String listenerMethodPath = propertyPaths[i];
				if (!listenerMethodPath.isEmpty()) {
					try {
						final String[] splittedListenerMethodPath = listenerMethodPath.split(BUNDLE_SEPARATOR);
						final Class<?> listenerClass;
						if (splittedListenerMethodPath.length > 1) {
							Bundle bundle = Platform.getBundle(splittedListenerMethodPath[0]);
							listenerClass = bundle.loadClass(splittedListenerMethodPath[1]);
						} else {

							listenerClass = Class.forName(listenerMethodPath);
						}
						final List<Class<?>> interfacesImplemented = Arrays.asList(listenerClass.getInterfaces());
						if (interfacesImplemented.contains(IPropertiesListener.class)) {
							this.listenerClassesSet.add((IPropertiesListener) listenerClass.newInstance());
						} else {
							Activator.log.error("The listener classes defined in listenerClasses must implement 'IPropertiesListener' interface.", null); //$NON-NLS-1$
						}

					} catch (ClassNotFoundException e) {
						Activator.log.error("The listener classes defined in listenerClasses are not correct.", e); //$NON-NLS-1$
					} catch (InstantiationException | IllegalAccessException e) {
						Activator.log.error(e);
					}
				}
			}

			manageObservableListeners();
		}
	}

	/**
	 * Add listeners for observables corresponding to property paths.
	 *
	 * @since 3.1
	 */
	public void manageObservableListeners() {
		final String property = getProperty();
		final DataSource input = getInput();
		if (null != property && !property.isEmpty() && null != input) {
			Set<String> propertyPathsSet = listeningPropertyPathsSet;
			Set<IPropertiesListener> classesSet = listenerClassesSet;

			if (null != propertyPathsSet && !propertyPathsSet.isEmpty() && null != classesSet && !classesSet.isEmpty()) {
				for (String listeningPropertyPath : propertyPathsSet) {
					final IObservable observable = input.getObservable(listeningPropertyPath);
					if (null != observable) {
						observable.addChangeListener(new IChangeListener() {

							@Override
							public void handleChange(final ChangeEvent event) {
								handlePropertiesListener(classesSet, propertyPathsSet);
							}
						});

						handlePropertiesListener(classesSet, propertyPathsSet);
					}
				}
			}
		}
	}

	/**
	 * Handle the listenerClasses.
	 *
	 * @param classesSet
	 *            The HashSet of listener classes.
	 * @param propertyPathsSet
	 *            The HashSet of property paths.
	 * @since 3.1
	 *
	 */
	private void handlePropertiesListener(final Set<IPropertiesListener> classesSet, final Set<String> propertyPathsSet) {
		for (IPropertiesListener iPropertiesListener : classesSet) {
			iPropertiesListener.handle(AbstractPropertyEditor.this, input, propertyPathsSet);
		}
	}
}
