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

package org.eclipse.papyrus.infra.tools.util;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * A service tracker that provides a single service as a composite of
 * registered service implementations.
 * 
 * @since 2.0
 */
public class CompositeServiceTracker<S> extends ServiceTracker<S, S> {
	private final AtomicReference<S> delegate = new AtomicReference<>();

	private final Class<S> serviceType;
	private final S identity;
	private final BinaryOperator<S> composer;

	/**
	 * Initializes me with the bundle context in which I track resolver services,
	 * an identity service that generally performs trivially (e.g., no-ops or default behaviour),
	 * and an operator that composes two service instances.
	 * 
	 * @param context
	 *            the bundle context
	 * @param serviceType
	 *            the service protocol type
	 * @param identity
	 *            the basic no-op or default service instance
	 * @param composer
	 *            an operator that composes two services instances into one
	 */
	public CompositeServiceTracker(BundleContext context, Class<S> serviceType, S identity, BinaryOperator<S> composer) {
		super(context, serviceType, null);

		this.serviceType = serviceType;
		this.identity = identity;
		this.composer = composer;
	}

	@Override
	public final S getService() {
		S result = this.delegate.get();
		if (result == null) {
			// Recompute
			@SuppressWarnings("unchecked")
			S[] services = (S[]) Array.newInstance(serviceType, getTrackingCount());
			result = Stream.of(getServices(services))
					.filter(Objects::nonNull) // If the array has more slots than we have services
					.reduce(identity, composer);
			this.delegate.set(result);
		}

		return result;
	}

	@Override
	public S addingService(ServiceReference<S> reference) {
		S result = super.addingService(reference);

		// We will have to recompute our delegates
		delegate.set(null);

		return result;
	}

	@Override
	public void removedService(ServiceReference<S> reference, S service) {
		super.removedService(reference, service);

		// We will have to recompute our delegates
		delegate.set(null);
	}
}
