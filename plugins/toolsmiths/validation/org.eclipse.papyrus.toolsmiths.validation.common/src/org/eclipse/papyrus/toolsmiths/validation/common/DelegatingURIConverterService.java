/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * The delegating URI converter service implementation.
 */
class DelegatingURIConverterService implements URIConverterService {

	private final Multimap<String, URIConverterService> delegates = Multimaps.synchronizedMultimap(ArrayListMultimap.create());
	private ServiceTracker<URIConverterService, URIConverterService> delegatesTracker;

	DelegatingURIConverterService() {
		super();

		BundleContext context = Activator.getDefault().getBundle().getBundleContext();
		delegatesTracker = new ServiceTracker<>(context, URIConverterService.class, new Customizer(context));
		delegatesTracker.open();
	}

	@Override
	public URI normalize(URI uri, ResourceSet context) {
		URI result = context.getURIConverter().normalize(uri);

		Collection<URIConverterService> delegates = this.delegates.get(uri.scheme());
		if (!delegates.isEmpty()) {
			result = normalize(uri, context, delegates);
		}

		return result;
	}

	private URI normalize(URI uri, ResourceSet context, Collection<URIConverterService> delegates) {
		URI result = uri;

		for (Iterator<URIConverterService> iter = delegates.iterator(); iter.hasNext() && uri.equals(result);) {
			result = iter.next().normalize(uri, context);
		}

		return result;
	}

	//
	// Nested types
	//

	private final class Customizer implements ServiceTrackerCustomizer<URIConverterService, URIConverterService> {

		private final BundleContext context;

		Customizer(BundleContext context) {
			super();

			this.context = context;
		}

		@Override
		public URIConverterService addingService(ServiceReference<URIConverterService> reference) {
			return register(reference, context.getService(reference));
		}

		private URIConverterService register(ServiceReference<URIConverterService> reference, URIConverterService service) {
			URIConverterService result = service;

			Object schemes = reference.getProperty(SCHEME_PROPERTY);
			if (schemes instanceof String) {
				register((String) schemes, result);
			} else if (schemes instanceof String[]) {
				String[] schemeArray = (String[]) schemes;
				if (schemeArray.length == 0) {
					result = null;
				} else {
					for (String scheme : schemeArray) {
						register(scheme, result);
					}
				}
			}

			if (result == null) {
				context.ungetService(reference);
			}

			return result;
		}

		private void register(String scheme, URIConverterService service) {
			if (service instanceof DelegatingURIConverterService) {
				throw new IllegalArgumentException("attempt to register the delegating service to itself"); //$NON-NLS-1$
			}

			delegates.put(scheme, service);
		}

		private void unregister(URIConverterService service) {
			delegates.values().removeIf(Predicate.isEqual(service));
		}

		@Override
		public void modifiedService(ServiceReference<URIConverterService> reference, URIConverterService service) {
			unregister(service);
			register(reference, service);
		}

		@Override
		public void removedService(ServiceReference<URIConverterService> reference, URIConverterService service) {
			unregister(service);
			context.ungetService(reference);
		}

	}

}
