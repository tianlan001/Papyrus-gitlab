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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.observable;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.internationalization.InternationalizationEntry;
import org.eclipse.papyrus.infra.internationalization.InternationalizationPackage;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.properties.Activator;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable;

/**
 * The observable value for the label of the {@link Table}.
 */
@SuppressWarnings("rawtypes")
public class TableLabelObservableValue extends AbstractObservableValue implements IObserving, ReferenceCountedObservable {

	/**
	 * The support reference counting on behalf of an {@code observable}.
	 */
	private final ReferenceCountedObservable.Support refCount = new ReferenceCountedObservable.Support(this);

	/**
	 * The object instance.
	 */
	protected Table table;

	/**
	 * The editing domain on which the commands will be executed.
	 */
	protected EditingDomain domain;

	/**
	 * The listener for the entry modification.
	 */
	protected Adapter listener;

	/**
	 * The internationalization entry corresponding to the table.
	 */
	protected InternationalizationEntry entry;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The table.
	 */
	public TableLabelObservableValue(final Table table) {
		this(Realm.getDefault(), table);
	}

	/**
	 * Constructor.
	 *
	 * @param realm
	 *            The current Realm.
	 * @param table
	 *            The table.
	 */
	public TableLabelObservableValue(final Realm realm, final Table table) {
		super(realm);
		this.table = table;
		this.domain = TableEditingDomainUtils.getTableEditingDomain(table);
		this.entry = LabelInternationalizationUtils.getInternationalizationEntry(table, table);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.AbstractObservable#dispose()
	 */
	@Override
	public synchronized void dispose() {
		if (null != listener) {
			entry.eAdapters().remove(listener);
			listener = null;
		}

		entry = null;
		table = null;
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.IObserving#getObserved()
	 */
	public Object getObserved() {
		return table;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.ChangeManager#firstListenerAdded()
	 */
	@Override
	protected void firstListenerAdded() {
		if (null != entry) {
			if (null == listener) {
				listener = new AdapterImpl() {
					@Override
					public void notifyChanged(Notification notification) {
						if (InternationalizationPackage.eINSTANCE.getInternationalizationEntry_Value() == notification.getFeature() && !notification.isTouch()) {
							final ValueDiff diff = Diffs.createValueDiff(notification.getOldValue(), notification.getNewValue());
							getRealm().exec(new Runnable() {
								@SuppressWarnings("unchecked")
								public void run() {
									fireValueChange(diff);
								}
							});
						}
					}
				};
			}
			entry.eAdapters().add(listener);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		return LabelInternationalization.getInstance().getTableLabelWithoutName(table);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Object value) {
		try {

			// Remove the listener on entry
			if (null != entry && null != listener) {
				entry.eAdapters().remove(listener);
			}

			final Command emfCommand = LabelInternationalization.getInstance().getSetTableLabelCommand(domain, table, (String) value, null);
			domain.getCommandStack().execute(emfCommand);

			// Get the entry after the modification and try to apply listener if possible
			this.entry = LabelInternationalizationUtils.getInternationalizationEntry(table, table);
			firstListenerAdded();
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	public Object getValueType() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable#retain()
	 */
	public void retain() {
		refCount.retain();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable#release()
	 */
	public void release() {
		refCount.release();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable#autorelease()
	 */
	public void autorelease() {
		refCount.autorelease();
	}

}
