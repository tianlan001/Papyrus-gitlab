/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.ui.emf.providers;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;

import com.google.common.collect.MapMaker;
import com.google.common.collect.Sets;

/**
 * A specialized {@link EMFLabelProvider} for label providers that compute labels
 * for objects from labels of objects on which they depend. For example, in a
 * banking system, an account label may be something like "Fred's Chequing"
 * in which the label depends on the label of the account holder. If the holder's
 * name changes from "Fred" to something else, the account's label also must be
 * recomputed.
 */
public class DependentEMFLabelProvider extends EMFLabelProvider {

	private SubscriptionListener subscriptionListener;

	private final CopyOnWriteArrayList<ILabelProviderListener> listeners = new CopyOnWriteArrayList<>();

	public DependentEMFLabelProvider() {
		super(new ForwardingEMFLabelProvider());
	}

	@Override
	public void dispose() {
		if (subscriptionListener != null) {
			subscriptionListener.dispose();
		}

		super.dispose();
	}

	/**
	 * Subscribe to label changes of the given {@code object} to notify that its
	 * {@code dependent}'s label accordingly changes.
	 * 
	 * @param object
	 *            an object to listen to
	 * @param dependent
	 *            an object that depends on its labels
	 */
	protected void subscribe(EObject object, EObject dependent) {
		if (baseEMFLabelProvider instanceof ForwardingEMFLabelProvider) {
			SubscriptionListener subs = getSubscriptionListener();
			subs.add(object, dependent);
		}
	}

	/**
	 * Unsubscribe from label changes of the given {@code object} because the
	 * {@link dependent}'s labels no longer depend on it.
	 * 
	 * @param object
	 *            an object to listen to
	 * @param dependent
	 *            an object that no longer depends on its labels
	 */
	protected void unsubscribe(EObject object, EObject dependent) {
		// If we didn't create the listener, yet, then it can't be attached to anything
		if (subscriptionListener != null) {
			subscriptionListener.remove(object, dependent);
		}
	}

	private SubscriptionListener getSubscriptionListener() {
		if (subscriptionListener == null) {
			subscriptionListener = new SubscriptionListener();
			((ForwardingEMFLabelProvider) baseEMFLabelProvider).addListener(subscriptionListener);
		}

		return subscriptionListener;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		super.addListener(listener);
		listeners.addIfAbsent(listener);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
		super.removeListener(listener);
	}

	protected void fireLabelProviderChange(Object object) {
		if (!listeners.isEmpty()) {
			LabelProviderChangedEvent event = new LabelProviderChangedEvent(this, object);
			listeners.forEach(l -> l.labelProviderChanged(event));
		}
	}

	//
	// Nested types
	//

	private class SubscriptionListener implements ILabelProviderListener {
		private final Map<EObject, Set<EObject>> subscriptions = new MapMaker().weakKeys().makeMap();

		@Override
		public void labelProviderChanged(LabelProviderChangedEvent event) {
			Set<EObject> dependents = subscriptions.get(event.getElement());
			if (dependents != null) {
				dependents.forEach(DependentEMFLabelProvider.this::fireLabelProviderChange);
			}
		}

		void add(EObject subscription, EObject dependent) {
			subscriptions.computeIfAbsent(subscription, x -> Sets.newSetFromMap(new WeakHashMap<>()))
					.add(dependent);
		}

		void remove(EObject subscription, EObject dependent) {
			Set<EObject> dependents = subscriptions.get(subscription);
			if (dependents != null) {
				dependents.remove(dependent);
				if (dependents.isEmpty()) {
					subscriptions.remove(subscription);
				}
				if (subscriptions.isEmpty()) {
					this.dispose();
				}
			}
		}

		void dispose() {
			if (baseEMFLabelProvider instanceof ForwardingEMFLabelProvider) {
				((ForwardingEMFLabelProvider) baseEMFLabelProvider).removeListener(this);
			}

			subscriptions.clear();

			if (subscriptionListener == this) {
				subscriptionListener = null;
			}
		}
	}

}
