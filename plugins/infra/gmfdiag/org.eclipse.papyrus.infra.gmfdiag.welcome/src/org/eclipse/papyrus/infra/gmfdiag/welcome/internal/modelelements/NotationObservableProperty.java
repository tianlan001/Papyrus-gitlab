/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus, CEA LIST, and others.
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

import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ResourceAdapter;
import org.eclipse.papyrus.infra.tools.databinding.WritableListWithIterator;
import org.eclipse.papyrus.infra.ui.util.TransactionUIHelper;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.swt.widgets.Display;

/**
 * A list property of notation-observables, tracking all of the diagrams, tables,
 * and such notations available in the resource set in user resources (not in,
 * for example, plug-in deployed models).
 */
public class NotationObservableProperty implements Supplier<IObservableList<NotationObservable>> {
	private TransactionalEditingDomain domain;
	private ResourceAdapter.Transactional diagramsListener;

	private IObservableList<NotationObservable> list;

	public NotationObservableProperty(WelcomeModelElement owner) {
		super();

		this.list = new WritableListWithIterator.Containment<>(NotationObservable.class);
		this.domain = (TransactionalEditingDomain) owner.getDomain();

		hookDiagramsListener();

		list.addDisposeListener(event -> {
			diagramsListener.uninstall(domain);
			diagramsListener = null;
			domain = null;
		});
	}

	@Override
	public IObservableList<NotationObservable> get() {
		return list;
	}

	void hookDiagramsListener() {
		diagramsListener = new ResourceAdapter.Transactional() {

			@Override
			public void resourceSetChanged(ResourceSetChangeEvent event) {
				// I have to run on the UI thread in order to operate on observables
				if (Display.getCurrent() != null) {
					basicResourceSetChanged(event);
				} else {
					// This cannot use a PrivilegedRunnable because the update needs
					// to be asynchronous to avoid deadlock (bug 568307). Use the same
					// executor for an asynchronous update on the UI thread as diagram
					// editors use for edit-part refresh
					handleResourceSetChangeEvent(event, TransactionUIHelper.getExecutor(domain));
				}
			}

			/**
			 * Captures the superclass behaviour of
			 * {@link #resourceSetChanged(ResourceSetChangeEvent)}.
			 *
			 * @param event
			 *            the change event
			 */
			final void basicResourceSetChanged(ResourceSetChangeEvent event) {
				super.resourceSetChanged(event);
			}

			@Override
			protected void handleResourceLoaded(Resource resource) {
				resource.getContents().stream()
						.filter(ViewPrototype::isViewObject)
						.filter(NotationObservableProperty.this::isUserObject)
						// In case of loading a resource, we already added its
						// notations via handleRootAdded()
						.filter(NotationObservableProperty.this::notAddedYet)
						.map(NotationObservable::new)
						.forEach(list::add);
			}

			@Override
			protected void handleRootAdded(Resource resource, EObject root) {
				if (ViewPrototype.isViewObject(root) && isUserObject(root)) {
					list.add(new NotationObservable(root));
				}
			}

			@Override
			protected void handleRootRemoved(Resource resource, EObject root) {
				if (ViewPrototype.isViewObject(root)) {
					list.removeIf(next -> next.getView().getValue() == root);
				}
			}
		};

		diagramsListener.install(domain);
	}

	/**
	 * Is a notation view or other object one that is under the user's control?
	 * That is, in a resource that is normally accessible and editable.
	 *
	 * @param object
	 *            an object in a model-set
	 * @return whether it is in a user resource
	 */
	boolean isUserObject(EObject object) {
		boolean result;

		Resource resource = object.eResource();
		ResourceSet rset = (resource == null) ? null : resource.getResourceSet();

		if (rset instanceof ModelSet) {
			result = ((ModelSet) rset).isUserModelResource(resource.getURI());
		} else {
			if (resource != null) {
				URI uri = resource.getURI();
				result = uri.isPlatformResource() || uri.isFile();
			} else {
				result = false;
			}
		}

		return result;
	}

	/**
	 * Queries whether an {@code object} has not yet been added to our
	 * observable list.
	 *
	 * @param object
	 *            an object
	 * @return {@code true} if it is not in the observable list; {@code false}, otherwise
	 */
	private boolean notAddedYet(EObject object) {
		return list.stream().allMatch(obs -> obs.getView().getValue() != object);
	}
}
