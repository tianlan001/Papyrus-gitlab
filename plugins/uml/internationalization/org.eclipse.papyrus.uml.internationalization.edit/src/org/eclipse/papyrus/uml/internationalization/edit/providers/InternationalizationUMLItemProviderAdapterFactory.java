/*****************************************************************************
 * Copyright (c) 2017, 2019 CEA LIST, Christian W. Damus and others.
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
 *   Nicolas FAUVERGUE (CEA LIST) - Bug 543494
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.edit.providers;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferenceChangeEvent;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferenceListener;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.internationalization.edit.internal.providers.InternationalizationUMLItemProviderAdapterFactoryImpl;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

/**
 * An item-provider adapter factory for UML that switches between the standard
 * UML2 item-providers and item-providers that support the <em>Papyrus Model
 * Internationalization</em> mechanism for models that do not or do have
 * internationalization enabled, respectively.
 */
public class InternationalizationUMLItemProviderAdapterFactory
		extends InternationalizationUMLItemProviderAdapterFactoryImpl {

	// Delegate for standard UML item providers. Delegate to super for i18n support
	private final UMLItemProviderAdapterFactory uml = new UMLItemProviderAdapterFactory();

	// Weakly track multiple resource sets because Papyrus uses a singleton adapter
	// factory instance in many scenarios
	private final Map<ResourceSet, Boolean> resourceSets;

	private final Adapter resourceSetAdapter;
	private final InternationalizationPreferenceListener prefListener;

	/**
	 * Constructor.
	 */
	public InternationalizationUMLItemProviderAdapterFactory() {
		super();

		resourceSets = new WeakHashMap<>();
		resourceSetAdapter = createResourceSetAdapter();
		prefListener = createPreferenceListener();

		InternationalizationPreferencesUtils.addPreferenceListener(prefListener);
	}

	@Override
	public void dispose() {
		InternationalizationPreferencesUtils.removePreferenceListener(prefListener);

		for (ResourceSet next : resourceSets.keySet()) {
			next.eAdapters().remove(resourceSetAdapter);
		}
		resourceSets.clear();

		uml.dispose();
		super.dispose();
	}

	/**
	 * Creates a listener on the internationalization preferences utility that will
	 * be notified when the settings for any resource change.
	 *
	 * @return the preference listener
	 */
	private InternationalizationPreferenceListener createPreferenceListener() {
		return new InternationalizationPreferenceListener() {
			@Override
			public void internationalizationChanged(InternationalizationPreferenceChangeEvent event) {
				switch (event.getEventType()) {
				case InternationalizationPreferenceChangeEvent.ENABLED:
					if (event.isInternationalizationEnabled() && !resourceSets.isEmpty()) {
						for (Map.Entry<ResourceSet, Boolean> entry : resourceSets.entrySet()) {
							if (entry.getKey().getResource(event.getResourceURI(), false) != null) {
								// This resource set now requires i18n
								entry.setValue(true);
								// And we don't need to track its resources any more
								entry.getKey().eAdapters().remove(resourceSetAdapter);

								// Any adapters already attached to this resource set
								// need to be purged. Unfortunately, this will also
								// remove adapters from other non-internationalized
								// models, so they will have to be rebuilt, too, as
								// a one-time cost
								uml.dispose();
							}
						}
					}
					break;
				default:
					// Pass
					break;
				}
			}
		};
	}

	/**
	 * Create an adapter that will check the internationalization status of
	 * resources as they are added to any of my contextual resource sets.
	 *
	 * @return the resource-set adapter
	 */
	private Adapter createResourceSetAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (msg.isTouch()) {
					return;
				}

				switch (msg.getFeatureID(ResourceSet.class)) {
				case ResourceSet.RESOURCE_SET__RESOURCES:
					switch (msg.getEventType()) {
					case Notification.ADD:
						checkResource((Resource) msg.getNewValue());
						break;
					case Notification.ADD_MANY:
						for (Object next : (Collection<?>) msg.getNewValue()) {
							if (checkResource((Resource) next)) {
								break; // Need look no further
							}
						}
						break;
					case Notification.SET:
						checkResource((Resource) msg.getNewValue());
						break;
					}
					break;
				default:
					// Pass
					break;
				}
			}
		};
	}

	@Override
	public Adapter createAdapter(Notifier target) {
		if (target instanceof EObject) {
			// Which delegate to use?
			return needsInterationalization((EObject) target) //
					? super.createAdapter(target) //
					: uml.createAdapter(target);
		}

		// Assume that i18n will be required
		return super.createAdapter(target);
	}

	/**
	 * Queries whether an {@code object} requires i18n support.
	 *
	 * @param object
	 *            an object
	 * @return whether it needs i18n
	 */
	protected final boolean needsInterationalization(EObject object) {
		Resource resource = object.eResource();
		if (resource != null) {
			ResourceSet rset = resource.getResourceSet();
			if (rset != null) {
				initializeI18n(rset);
				return resourceSets.get(rset);
			}
		}

		// Assume that i18n will be required
		return true;
	}

	/**
	 * Discover the internationalization status of a newly encountered resource set.
	 *
	 * @param resourceSet
	 *            a resource set now encountered
	 */
	private void initializeI18n(ResourceSet resourceSet) {
		if (!resourceSets.containsKey(resourceSet)) {
			// Is there any internationalization enabled?
			for (Resource next : resourceSet.getResources()) {
				if (checkResource(next)) {
					return; // Need look no further
				}
			}

			resourceSets.put(resourceSet, false);
			resourceSet.eAdapters().add(resourceSetAdapter);
		}
	}

	/**
	 * Discover the internationalization status of a newly encountered resource.
	 *
	 * @param resource
	 *            a resource now encountered
	 */
	boolean checkResource(Resource resource) {
		if (InternationalizationPreferencesUtils.getInternationalizationPreference(resource)) {
			// We won't need to observe this resource set any more
			ResourceSet rset = resource.getResourceSet();
			rset.eAdapters().remove(resourceSetAdapter);
			resourceSets.put(rset, true);
			return true;
		}

		return false;
	}

	@Override
	public void addListener(INotifyChangedListener notifyChangedListener) {
		super.addListener(notifyChangedListener);

		// Get notifications from the alternate's adapters, too
		uml.addListener(notifyChangedListener);
	}

	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		uml.removeListener(notifyChangedListener);
		super.removeListener(notifyChangedListener);
	}

	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		super.setParentAdapterFactory(parentAdapterFactory);

		// The alternate needs to know about this composition structure, too
		uml.setParentAdapterFactory(parentAdapterFactory);
	}

	/**
	 * We implement this method to avoid possible performance issues to adapt elements when it is not needed.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory#adapt(org.eclipse.emf.common.notify.Notifier, java.lang.Object)
	 */
	@Override
	public Adapter adapt(final Notifier notifier, final Object type) {
		if (notifier instanceof EObject && !needsInterationalization((EObject) notifier)) {
			return uml.adapt(notifier, type);
		}
		return super.adapt(notifier, this);
	}

}
