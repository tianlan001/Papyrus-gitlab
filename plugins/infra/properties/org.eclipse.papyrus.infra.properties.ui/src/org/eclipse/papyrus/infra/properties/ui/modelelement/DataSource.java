/*****************************************************************************
 * Copyright (c) 2010, 2017, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 455075, 510254, 515257
 *  Patrick Tessier (CEA LIST), bug 568329
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.modelelement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;
import org.eclipse.papyrus.infra.tools.util.CoreExecutors;
import org.eclipse.papyrus.infra.tools.util.ReferenceCounted;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.EmptyContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * A DataSource is an object encapsulating one or more {@link ModelElement}s.
 * It contains methods to resolve property paths, and forward the methods to
 * the right ModelElement.
 *
 * For example, a UML class stereotyped with the SysML::Blocks::Block will have
 * two ModelElements : one for UML, and one for the Block stereotype.
 *
 * It will be able to resolve paths such as UML:Class:name or
 * SysML:Blocks:Block:isEncapsulated
 *
 * The methods such as isUnique, isEditable or getContentProvider will be
 * delegated to the resolved ModelElement, with a truncated property path.
 *
 * For example, a call to DataSource#isEditable("UML:Class:name") will be
 * forwarded to UMLModelElement#isEditable("name")
 *
 * @author Camille Letavernier
 */
public class DataSource extends ReferenceCounted<DataSource> implements IChangeListener {

	private final ListenerList<IChangeListener> changeListeners = new ListenerList<>(ListenerList.IDENTITY);

	private final ListenerList<IDataSourceListener> dataSourceListeners = new ListenerList<>(ListenerList.IDENTITY);

	private View view;

	private IStructuredSelection selection;

	private Map<String, ModelElement> elements = new HashMap<>();

	/**
	 * Constructs a new DataSource from the given view and selection
	 *
	 * @param realm
	 * @param view
	 * @param selection
	 *
	 * @see DataSourceFactory#createDataSourceFromSelection(IStructuredSelection, View)
	 */
	protected DataSource(View view, IStructuredSelection selection) {
		super(CoreExecutors.getUIExecutorService());

		this.view = view;
		this.selection = selection;
	}

	/**
	 * Return the instance of ModelElement associated to the given path
	 *
	 * @param propertyPath
	 *            The propertyPath to lookup
	 * @return
	 *         The ModelElement associated to the given propertyPath
	 */
	public ModelElement getModelElement(String propertyPath) {
		// ConfigurationManager.instance.getProperty(propertyPath)
		String key = propertyPath.substring(0, propertyPath.lastIndexOf(":")); //$NON-NLS-1$
		if (!elements.containsKey(key)) { // Try to resolve the modelElements on-the-fly
			ModelElement element = DataSourceFactory.instance.getModelElementFromPropertyPath(this, propertyPath);
			if (element == null) {
				Activator.log.warn("Unable to find a ModelElement for " + propertyPath + ". Elements : " + elements); //$NON-NLS-1$ //$NON-NLS-2$
			}
			elements.put(key, element);
		}
		return elements.get(key);
	}

	private String getLocalPropertyPath(String propertyPath) {
		return propertyPath.substring(propertyPath.lastIndexOf(":") + 1); //$NON-NLS-1$
	}

	/**
	 * Returns an IObservable corresponding to the given property path
	 * The observable may be either an IObservableValue or an IObservableList
	 * The call to this method is delegated to the corresponding ModelElement
	 * The IObservable objects returned by this method may be shared by
	 * many instances, which means they should not be disposed directly.
	 * They will be disposed when this DataSource is disposed.
	 *
	 * @param propertyPath
	 *            The property path for which we want to retrieve an ObservableValue
	 * @return
	 *         The IObservable corresponding to the given propertyPath
	 */
	public IObservable getObservable(String propertyPath) {
		String localPropertyPath = getLocalPropertyPath(propertyPath);
		ModelElement element = getModelElement(propertyPath);

		if (element == null) {
			return null;
		}

		IObservable observable = element.getObservable(localPropertyPath);
		if (observable != null) {
			observable.addChangeListener(this);
		}

		return observable;
	}

	@Override
	public String toString() {
		return String.format("DataSource<%08x>(%s)%s", System.identityHashCode(this), view.getName(), selection.toList()); //$NON-NLS-1$
	}

	/**
	 * Returns an IStaticContentProvider corresponding to the given property path
	 * The call to this method is delegated to the corresponding ModelElement
	 *
	 * @param propertyPath
	 *            The property path for which we want to retrieve a ContentProvider
	 * @return
	 *         The IStaticContentProvider corresponding to the given propertyPath
	 */
	public IStaticContentProvider getContentProvider(final String propertyPath) {
		class Delegator extends EncapsulatedContentProvider implements IDataSourceListener {

			{
				createDelegate();
				DataSource.this.addDataSourceListener(this);
			}

			@Override
			public void dispose() {
				disposeDelegate();
				DataSource.this.removeDataSourceListener(this);
			}

			private void disposeDelegate() {
				if (encapsulated != null) {
					encapsulated.dispose();
					encapsulated = null;
				}

				// If I had any temporary elements, then they cannot now be relevant
				clearTemporaryElements();
			}

			private void createDelegate() {
				encapsulate(doGetContentProvider(propertyPath));
			}

			@Override
			public void dataSourceChanged(DataSourceChangedEvent event) {
				disposeDelegate();
				createDelegate();
			}
		}

		return new Delegator();
	}

	protected IStaticContentProvider doGetContentProvider(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return EmptyContentProvider.instance;
		}

		String localPropertyPath = getLocalPropertyPath(propertyPath);
		return element.getContentProvider(localPropertyPath);
	}

	/**
	 * Returns an ILabelProvider corresponding to the given property path
	 * The call to this method is delegated to the corresponding ModelElement
	 *
	 * @param propertyPath
	 *            The property path for which we want to retrieve an ILabelProvider
	 * @return
	 *         The ILabelProvider corresponding to the given propertyPath
	 */
	public ILabelProvider getLabelProvider(final String propertyPath) {
		class Delegator extends LabelProvider implements IDataSourceListener, ILabelProviderListener, IStyledLabelProvider {
			private ILabelProvider delegate;

			private final CopyOnWriteArrayList<ILabelProviderListener> listeners = new CopyOnWriteArrayList<>();

			{
				DataSource.this.addDataSourceListener(this);
			}

			@Override
			public void dispose() {
				disposeDelegate();
				super.dispose();
			}

			private void disposeDelegate() {
				if (delegate != null) {
					delegate.removeListener(this);
					delegate.dispose();
					delegate = null;
				}
			}

			@Override
			public void dataSourceChanged(DataSourceChangedEvent event) {
				disposeDelegate();
			}

			@Override
			public void addListener(ILabelProviderListener listener) {
				listeners.addIfAbsent(listener);
			}

			@Override
			public void removeListener(ILabelProviderListener listener) {
				listeners.remove(listener);
			}

			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				if (!listeners.isEmpty()) {
					LabelProviderChangedEvent forward = new LabelProviderChangedEvent(this, event.getElements());

					// Listeners will most likely need to interact with the observables provided through me,
					// so ensure that events are propagated on the UI thread
					if (Display.getCurrent() != null) {
						// Already on the UI thread
						fireLabelProviderChanged(forward);
					} else {
						// Post asynchronously on the UI thread
						CoreExecutors.getUIExecutorService().execute(() -> fireLabelProviderChanged(forward));
					}
				}
			}

			@Override
			protected void fireLabelProviderChanged(LabelProviderChangedEvent event) {
				for (ILabelProviderListener next : listeners) {
					try {
						next.labelProviderChanged(event);
					} catch (Exception e) {
						Activator.log.error("Uncaught exception in label provider listener.", e); //$NON-NLS-1$
					}
				}
			}

			ILabelProvider getDelegate() {
				if (delegate == null) {
					delegate = doGetLabelProvider(propertyPath);
					if (delegate == null) {
						delegate = new LabelProvider();
					}
					delegate.addListener(this);
				}

				return delegate;
			}

			@Override
			public Image getImage(Object element) {
				return getDelegate().getImage(element);
			}

			@Override
			public String getText(Object element) {
				return getDelegate().getText(element);
			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return getDelegate().isLabelProperty(element, property);
			}

			/**
			 * {@inhiriteDoc}
			 *
			 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
			 */
			@Override
			public StyledString getStyledText(final Object element) {
				StyledString styledText = null;
				ILabelProvider delegateProvider = getDelegate();
				if (delegateProvider instanceof IStyledLabelProvider) {
					styledText = ((IStyledLabelProvider) delegateProvider).getStyledText(element);
				} else {
					styledText = new StyledString(getText(element));
				}
				return styledText;
			}
		}

		return new Delegator();
	}

	protected ILabelProvider doGetLabelProvider(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return null;
		}
		String localPropertyPath = getLocalPropertyPath(propertyPath);
		return element.getLabelProvider(localPropertyPath);
	}

	/**
	 * Adds a change listener to this DataSource. The listener will be notified
	 * each time a change occurs on one of the IObservable produced by this DataSource
	 *
	 * @see DataSource#getObservable(String)
	 * @param listener
	 *            The Change listener
	 */
	public void addChangeListener(IChangeListener listener) {
		changeListeners.add(listener);
	}

	/**
	 * Removes a change listener from this DataSource.
	 *
	 * @param listener
	 *            The listener to remove
	 * @see DataSource#addChangeListener(IChangeListener)
	 */
	public void removeChangeListener(IChangeListener listener) {
		changeListeners.remove(listener);
	}

	public void addDataSourceListener(IDataSourceListener listener) {
		dataSourceListeners.add(listener);
	}

	public void removeDataSourceListener(IDataSourceListener listener) {
		dataSourceListeners.remove(listener);
	}

	@Override
	public synchronized void handleChange(ChangeEvent event) {
		Object[] listeners = changeListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			try {
				((IChangeListener) listeners[i]).handleChange(event);
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in observable change listener.", e); //$NON-NLS-1$
			}
		}
	}

	protected void fireDataSourceChanged() {
		Object[] listeners = dataSourceListeners.getListeners();
		if (listeners.length > 0) {
			DataSourceChangedEvent event = new DataSourceChangedEvent(this);
			for (int i = 0; i < listeners.length; i++) {
				try {
					((IDataSourceListener) listeners[i]).dataSourceChanged(event);
				} catch (Exception e) {
					Activator.log.error("Uncaught exception in data-source listener.", e); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * @return The view associated to this DataSource
	 */
	public View getView() {
		return view;
	}

	/**
	 * @return the selection associated to this DataSource
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * @param selection
	 *            the selection to set
	 */
	public void setSelection(IStructuredSelection selection) {
		if (!selection.equals(this.selection)) {
			this.selection = selection;

			fireDataSourceChanged();
		}
	}

	/**
	 * @param propertyPath
	 * @return
	 *         true if the property represented by this propertyPath is ordered
	 */
	public boolean isOrdered(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return false;
		}
		return element.isOrdered(getLocalPropertyPath(propertyPath));
	}

	/**
	 * @param propertyPath
	 * @return
	 *         true if the property represented by this propertyPath is unique
	 */
	public boolean isUnique(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return false;
		}
		return element.isUnique(getLocalPropertyPath(propertyPath));
	}

	/**
	 * @param propertyPath
	 * @return
	 *         true if the property represented by this propertyPath is mandatory
	 */
	public boolean isMandatory(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return false;
		}
		return element.isMandatory(getLocalPropertyPath(propertyPath));
	}

	/**
	 * @param propertyPath
	 * @return
	 *         true if the property represented by this propertyPath is editable
	 */
	public boolean isEditable(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return false;
		}
		return element.isEditable(getLocalPropertyPath(propertyPath));
	}

	/**
	 * Returns true if the given property should be refresh each time a change
	 * occurs in the property view. May help when the IObservable doesn't
	 * catch some change events (For example, for some Ecore derived
	 * properties).
	 *
	 * @param propertyPath
	 * @return true if the refresh should be forced
	 */
	public boolean forceRefresh(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return false;
		}
		return element.forceRefresh(getLocalPropertyPath(propertyPath));
	}

	/**
	 * Return the value factory associated to the given path. May be null
	 *
	 * @param propertyPath
	 *            The property path to lookup
	 * @return
	 *         The factory used to edit and/or instantiate values for this property path
	 */
	public ReferenceValueFactory getValueFactory(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return null;
		}
		return element.getValueFactory(getLocalPropertyPath(propertyPath));
	}

	/**
	 * Return the default value for the given property path
	 *
	 * @param propertyPath
	 * @return
	 *         The default value for the given property
	 */
	public Object getDefaultValue(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return null;
		}
		return element.getDefaultValue(getLocalPropertyPath(propertyPath));
	}

	/**
	 * Indicates if the widget should use the direct creation.
	 * The direct edition will disable the possibility to browse
	 * existing elements when the "add" button is pressed.
	 *
	 * This is essentially relevant for containment references : this method
	 * should return false if the widget should only allow creation of new
	 * elements.
	 *
	 * @param propertyPath
	 * @return
	 *         True if the widget should use the direct edition option for the given property
	 */
	public boolean getDirectCreation(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return true;
		}
		return element.getDirectCreation(getLocalPropertyPath(propertyPath));
	}

	/**
	 * Disposes this data source.
	 * This will dispose all ModelElements and IObservable created by this DataSource
	 */
	@Override
	public void dispose() {
		for (ModelElement element : elements.values()) {
			if (element != null) {
				element.dispose();
			}
		}
		elements.clear();
	}

	/**
	 * return the IValidator for a property path
	 *
	 * @param propertyPath
	 * @return
	 */
	public IValidator getValidator(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return null;
		}
		return element.getValidator(getLocalPropertyPath(propertyPath));
	}

	/**
	 * return the NameResolutionHelper to use for completion
	 *
	 * @param propertyPath
	 * @return
	 */
	public INameResolutionHelper getNameResolutionHelper(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return null;
		}
		return element.getNameResolutionHelper(getLocalPropertyPath(propertyPath));
	}

	/**
	 * return the Papyrus Converter to convert the object to edit or display string and to find the object from a string
	 *
	 * @param propertyPath
	 * @return
	 */
	public IPapyrusConverter getPapyrusConverter(String propertyPath) {
		ModelElement element = getModelElement(propertyPath);
		if (element == null) {
			return null;
		}
		return element.getPapyrusConverter(getLocalPropertyPath(propertyPath));
	}
}
