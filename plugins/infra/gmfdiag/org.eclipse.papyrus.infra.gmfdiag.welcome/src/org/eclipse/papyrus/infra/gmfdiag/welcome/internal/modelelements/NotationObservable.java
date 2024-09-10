/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.welcome.internal.modelelements;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.infra.gmfdiag.welcome.internal.util.UISafeAdapter;
import org.eclipse.papyrus.infra.gmfdiag.welcome.internal.util.ViewUtil;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable;
import org.eclipse.papyrus.infra.tools.databinding.TouchableValue;

/**
 * Encapsulation of a {@link Diagram}, {@link Table}, or other notation view that presents the
 * following as observable properties:
 * <ul>
 * <li>the notation view</li>
 * <li>the view's context element</li>
 * </ul>
 */
public class NotationObservable extends ReferenceCountedObservable.Abstract {
	private ViewAdapter viewAdapter;
	private ContextAdapter contextAdapter;

	private final TouchableValue<EObject> viewValue;
	private final TouchableValue<EObject> contextValue;

	public NotationObservable(EObject view) {
		this(Realm.getDefault(), view);
	}

	public NotationObservable(Realm realm, EObject view) {
		super(realm);

		EObject context = ViewUtil.getContext(view);

		this.viewValue = new TouchableValue<>(realm, EObject.class, view);
		this.contextValue = new TouchableValue<>(realm, EObject.class, context);

		this.viewAdapter = new ViewAdapter(view);
		handleContextChanged(null, context);
		// Roll up changes to my elements as changes to me
		IChangeListener rollup = new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent event) {
				NotationObservable.this.fireChange();
			}
		};
		viewValue.addChangeListener(rollup);
		contextValue.addChangeListener(rollup);
	}

	@Override
	public synchronized void dispose() {
		if (viewAdapter != null) {
			// This will nullify the 'viewAdapter' field
			viewAdapter.dispose();
		}

		if (contextAdapter != null) {
			// This will nullify the 'contextAdapter' field
			contextAdapter.dispose();
		}

		viewValue.dispose();
		contextValue.dispose();

		super.dispose();
	}

	public IObservableValue<EObject> getView() {
		return viewValue;
	}

	public IObservableValue<EObject> getContext() {
		return contextValue;
	}

	@Override
	public boolean isStale() {
		return false;
	}

	/**
	 * Handles a change in the context of a notation view.
	 * 
	 * @precondition the current thread is the UI thread
	 * 
	 * @param oldContext
	 *            the old context (may be {@code null})
	 * @param newContext
	 *            the new context (may be {@code null})
	 */
	void handleContextChanged(EObject oldContext, EObject newContext) {
		ContextAdapter adapter = this.contextAdapter;

		if (oldContext != null) {
			// This will nullify the 'contextAdapter' field
			oldContext.eAdapters().remove(contextAdapter);
		}
		if (newContext != null) {
			if (adapter == null) {
				// initial context
				contextAdapter = new ContextAdapter(newContext);
			} else {
				// reuse the adapter
				newContext.eAdapters().add(adapter);
				contextAdapter = adapter;
			}
		}

		contextValue.setValue(newContext);
	}

	//
	// Nested types
	//

	private class ViewAdapter extends UISafeAdapter {
		ViewAdapter(EObject view) {
			super();

			view.eAdapters().add(this);
		}

		void dispose() {
			if (target != null) {
				target.eAdapters().remove(this);
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (target == oldTarget) {
				// I am disposed
				viewAdapter = null;
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void doNotifyChanged(Notification msg) {
			if (!msg.isTouch()) {
				Object notifier = msg.getNotifier();

				if (notifier instanceof Diagram) {
					switch (msg.getFeatureID(Diagram.class)) {
					case NotationPackage.DIAGRAM__NAME:
					case NotationPackage.DIAGRAM__TYPE:
						viewValue.touch();
						break;
					case NotationPackage.DIAGRAM__ELEMENT:
						handleContextChanged((EObject) msg.getOldValue(), (EObject) msg.getNewValue());
						break;
					}
				} else if (notifier instanceof Table) {
					switch (msg.getFeatureID(Diagram.class)) {
					case NattablePackage.TABLE__NAME:
					case NattablePackage.TABLE__TABLE_CONFIGURATION:
						viewValue.touch();
						break;
					case NattablePackage.TABLE__CONTEXT:
						handleContextChanged((EObject) msg.getOldValue(), (EObject) msg.getNewValue());
						break;
					}
				}
			}
		}
	}

	private class ContextAdapter extends UISafeAdapter {
		ContextAdapter(EObject context) {
			super();

			context.eAdapters().add(this);
		}

		void dispose() {
			if (target != null) {
				target.eAdapters().remove(this);
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (target == oldTarget) {
				// I am disposed
				contextAdapter = null;
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void doNotifyChanged(Notification msg) {
			if (!msg.isTouch()) {
				// Can't interpret the change, so just assume that the label needs to change
				contextValue.touch();
			}
		}
	}
}
