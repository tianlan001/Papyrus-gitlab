/*****************************************************************************
 * Copyright (c) 2015, 2024 Christian W. Damus and others.
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
 *   Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 583091
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.canonical.editpolicy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.util.EditPartUtil;
import org.eclipse.gmf.runtime.notation.CanonicalStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.tools.util.ReferenceCounted;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;

/**
 * A {@link DiagramEventBroker} listener that is notified when the {@link CanonicalStyle} of a view changes.
 */
public class CanonicalStateListener extends ReferenceCounted<CanonicalStateListener> implements NotificationListener {
	private static final ConcurrentMap<IGraphicalEditPart, CanonicalStateListener> listeners = new MapMaker().weakKeys().weakValues().makeMap();

	private final IGraphicalEditPart owner;

	private List<Handler> handlers = Lists.newArrayListWithExpectedSize(2);

	private CanonicalStateListener(IGraphicalEditPart owner) {
		super();

		this.owner = owner;

		install();
	}

	@Override
	protected void dispose() {
		listeners.remove(owner, this);
		uninstall();
	}

	/**
	 * <p>
	 * Obtains the shared canonical state listener for a given {@code editPart}.
	 * </p>
	 * <p>
	 * <b>Note</b> that the resulting listener is automatically {@linkplain ReferenceCounted#retain() retained} on the caller's behalf. The caller will have to {@linkplain ReferenceCounted#release() release} the listener when it is no longer needed.
	 * </p>
	 *
	 * @param editPart
	 *            an edit part
	 * @return its shared canonical state listener. Do not {@linkplain ReferenceCounted#retain() retain} it, otherwise you will
	 *         have doubly retained it
	 *
	 * @see ReferenceCounted#retain()
	 * @see ReferenceCounted#release()
	 */
	public static CanonicalStateListener getInstance(IGraphicalEditPart editPart) {
		CanonicalStateListener result = listeners.get(editPart);
		if (result == null) {
			result = new CanonicalStateListener(editPart);
			CanonicalStateListener clash = listeners.putIfAbsent(editPart, result);
			if (clash != null) {
				result.dispose();
				result = clash;
			}
		}
		return result.retain();
	}

	public void addCanonicalRefreshHandler(Handler refreshHandler) {
		handlers.add(refreshHandler);
	}

	public void removeCanonicalRefreshHandler(Handler refreshHandler) {
		handlers.remove(refreshHandler);
	}

	private void install() {
		if (owner != null) {
			View view = owner.getNotationView();

			DiagramEventBroker broker = getDiagramEventBroker();
			broker.addNotificationListener(view, NotationPackage.Literals.VIEW__STYLES, this);

			// If provided by CSS, the style could be a transient floating object. In that case, don't listen to
			// it (CSS cannot change its state). Or, if the view itself is a CanonicalStyle, then we're already
			// listening to it
			CanonicalStyle style = (CanonicalStyle) view.getStyle(NotationPackage.Literals.CANONICAL_STYLE);
			if ((style != null) && (style.eContainer() == view)) {
				install(style);
			}
		}
	}

	private IGraphicalEditPart getOwner() {
		return owner;
	}

	private void install(CanonicalStyle style) {
		DiagramEventBroker broker = getDiagramEventBroker();
		broker.addNotificationListener(style, NotationPackage.Literals.CANONICAL_STYLE__CANONICAL, this);
	}

	private void uninstall() {
		View view = getOwner().getNotationView();

		CanonicalStyle style = (CanonicalStyle) view.getStyle(NotationPackage.Literals.CANONICAL_STYLE);
		if ((style != null) && (style != view)) {
			uninstall(style);
		}

		DiagramEventBroker broker = getDiagramEventBroker();
		if (broker != null) {
			broker.removeNotificationListener(view, NotationPackage.Literals.VIEW__STYLES, this);
		}
	}

	private void uninstall(CanonicalStyle style) {
		DiagramEventBroker broker = getDiagramEventBroker();
		if (broker != null) {
			broker.removeNotificationListener(style, NotationPackage.Literals.CANONICAL_STYLE__CANONICAL, this);
		}
	}

	protected DiagramEventBroker getDiagramEventBroker() {
		IGraphicalEditPart owner = getOwner();
		TransactionalEditingDomain domain = (owner == null) ? null : owner.getEditingDomain();
		return (domain == null) ? null : DiagramEventBroker.getInstance(domain);
	}

	@Override
	public void notifyChanged(Notification notification) {
		Object notifier = notification.getNotifier();

		if ((notifier instanceof CanonicalStyle) && (notification.getFeature() == NotationPackage.Literals.CANONICAL_STYLE__CANONICAL)) {
			// Already listening to CanonicalStyle::canonical, so don't use internalHandleAdd
			handleAdd((CanonicalStyle) notifier, null);
		} else if (notification.getFeature() == NotationPackage.Literals.VIEW__STYLES) {
			switch (notification.getEventType()) {
			case Notification.ADD:
				if (notification.getNewValue() instanceof CanonicalStyle) {
					internalHandleAdd((CanonicalStyle) notification.getNewValue());
				}
				break;
			case Notification.ADD_MANY:
				for (Object next : (Iterable<?>) notification.getNewValue()) {
					if (next instanceof CanonicalStyle) {
						internalHandleAdd((CanonicalStyle) next);
						break;
					}
				}
				break;
			case Notification.REMOVE:
				if (notification.getOldValue() instanceof CanonicalStyle) {
					internalHandleRemove((CanonicalStyle) notification.getOldValue());
				}
				break;
			case Notification.REMOVE_MANY:
				for (Object next : (Iterable<?>) notification.getOldValue()) {
					if (next instanceof CanonicalStyle) {
						internalHandleRemove((CanonicalStyle) next);
						break;
					}
				}
				break;
			case Notification.SET:
			case Notification.UNSET:
				CanonicalStyle added = (notification.getNewValue() instanceof CanonicalStyle) ? (CanonicalStyle) notification.getNewValue() : null;
				CanonicalStyle removed = (notification.getOldValue() instanceof CanonicalStyle) ? (CanonicalStyle) notification.getOldValue() : null;

				if (added != null) {
					if (removed != null) {
						uninstall(removed);
					}

					// Doesn't matter whether the old style was a canonical. It matters that this one is
					internalHandleAdd((CanonicalStyle) notification.getNewValue());
				} else if (notification.getOldValue() instanceof CanonicalStyle) {
					// Canonical style was replaced by a different kind
					internalHandleRemove((CanonicalStyle) notification.getOldValue());
				}
				break;
			}
		}
	}

	private void internalHandleAdd(CanonicalStyle style) {
		// If provided by CSS, the style could be a transient floating object. In that case, don't listen to
		// it (CSS cannot change its state)
		if (style.eContainer() == owner.getNotationView()) {
			install(style);
		}
		handleAdd(style, null);
	}

	void handleAdd(CanonicalStyle style, Predicate<? super Handler> filter) {
		if (!handlers.isEmpty()) {
			Iterable<Handler> filtered = (filter == null) ? handlers : Iterables.filter(handlers, filter);
			for (Handler next : filtered) {
				Runnable update = next.handleAdd(style);
				if (update != null) {
					EditPartUtil.synchronizeRunnableToMainThread(getOwner(), update);
				}
			}
		}
	}

	private void internalHandleRemove(CanonicalStyle style) {
		uninstall(style);
		handleRemove(style, null);
	}

	void handleRemove(CanonicalStyle style, Predicate<? super Handler> filter) {
		if (!handlers.isEmpty()) {
			Iterable<Handler> filtered = (filter == null) ? handlers : Iterables.filter(handlers, filter);
			for (Handler next : filtered) {
				Runnable update = next.handleRemove(style);
				if (update != null) {
					EditPartUtil.synchronizeRunnableToMainThread(getOwner(), update);
				}
			}
		}
	}

	/**
	 * Handles a change in the implicit canonical style of a notation view (such as from changes in CSS stylesheets).
	 *
	 * @param requestor
	 *            the handler of the entity making the poke request, which should be skipped in the poke
	 *            (presumably because it already knows about the canonical state change)
	 * @param enabled
	 *            whether the edit-policy has become enabled ({@code true}) or disabled ({@code false})
	 */
	void pokeHandlers(Handler requestor, boolean enabled) {
		View view = owner.getNotationView();
		if (view != null) {
			CanonicalStyle style = (CanonicalStyle) view.getStyle(NotationPackage.Literals.CANONICAL_STYLE);
			if (style == null) {
				// Only need to fake a removal if CSS told us that we're not enabled
				if (!enabled) {
					CanonicalStyle fakeStyle = NotationFactory.eINSTANCE.createCanonicalStyle();
					handleRemove(fakeStyle, not(requestor));
				}
			} else if (style.eContainer() == null) {
				// Only need to fake an addition if CSS told us that we're enabled
				if (enabled) {
					handleAdd(style, not(requestor));
				}
			}
		}
	}

	private static Predicate<Object> not(final Object excluded) {
		return (excluded == null) ? null : new Predicate<>() {
			@Override
			public boolean apply(Object input) {
				return input != excluded;
			}
		};
	}

	//
	// Nested types
	//

	public static interface Handler {

		/**
		 * Obtains a runnable that will be invoked on the display thread (for safe access to diagram edit parts)
		 * to handle the addition/update of the canonical {@code style}.
		 *
		 * @param style
		 *            the added or changed canonical style
		 *
		 * @return a runnable to run in response, or {@code null} if no response is needed
		 */
		Runnable handleAdd(CanonicalStyle style);

		/**
		 * Obtains a runnable that will be invoked on the display thread (for safe access to diagram edit parts)
		 * to handle the removal of the canonical {@code style}.
		 *
		 * @param style
		 *            the removed canonical style
		 *
		 * @return a runnable to run in response, or {@code null} if no response is needed
		 */
		Runnable handleRemove(CanonicalStyle style);

	}
}
