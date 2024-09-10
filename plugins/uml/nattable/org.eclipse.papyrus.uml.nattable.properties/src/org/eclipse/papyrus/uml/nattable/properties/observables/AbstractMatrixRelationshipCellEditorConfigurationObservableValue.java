/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.properties.observables;

import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


/**
 * Abstract Observable for {@link ICellEditorConfiguration}
 */
public abstract class AbstractMatrixRelationshipCellEditorConfigurationObservableValue extends AbstractObservableValue implements Listener {

	/**
	 * The current editing domain.
	 */
	private EditingDomain domain;

	/**
	 * The managed table.
	 */
	private ICellEditorConfiguration matrixRelationshipCellEditorConfiguration;

	/**
	 * The managed feature.
	 */
	private EStructuralFeature managedFeature;

	/**
	 * The last value of the managed feature.
	 */
	private Object oldValue;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The managed table.
	 * @param managedFeature
	 *            The managed feature.
	 */
	public AbstractMatrixRelationshipCellEditorConfigurationObservableValue(final EditingDomain domain, final Table table, final EStructuralFeature managedFeature) {
		this.domain = domain;
		this.matrixRelationshipCellEditorConfiguration = table.getOwnedCellEditorConfigurations();
		this.managedFeature = managedFeature;
		this.oldValue = doGetValue();
	}

	/**
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 *
	 * @return
	 * 		the current value
	 */
	@Override
	protected Object doGetValue() {
		EObject editedObject = getEditedEObject();
		if (null != editedObject) {
			return editedObject.eGet(getManagedFeature());
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	protected void doSetValue(Object value) {
		final Command setCommand = SetCommand.create(this.domain, getEditedEObject(), getManagedFeature(), value);
		if (setCommand.canExecute()) {
			this.domain.getCommandStack().execute(setCommand);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	@Override
	public Object getValueType() {
		return getManagedFeature().getEType();
	}

	/**
	 * Get the managed feature.
	 * 
	 * @return
	 * 		The managed feature.
	 */
	public EStructuralFeature getManagedFeature() {
		return managedFeature;
	}

	/**
	 * Get the matrix relationship cell editor configuration.
	 * 
	 * @return
	 * 		The matrix relationship cell editor configuration.
	 */
	protected EObject getEditedEObject() {
		return matrixRelationshipCellEditorConfiguration;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(Event event) {
		final Object newValue = doGetValue();
		// big test required to avoid NPE when we receive event during the exchange of the axis
		if ((newValue != oldValue) && ((newValue != null && oldValue == null) || newValue == null && oldValue != null || (oldValue != null && !oldValue.equals(newValue)))) {
			final Object localOldValue = oldValue;
			oldValue = newValue;
			fireValueChange(new ValueDiff() {

				@Override
				public Object getOldValue() {
					return localOldValue;
				}

				@Override
				public Object getNewValue() {
					return newValue;
				}
			});
		}
	}
}
