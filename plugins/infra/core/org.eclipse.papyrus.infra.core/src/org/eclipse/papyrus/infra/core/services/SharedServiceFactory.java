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

package org.eclipse.papyrus.infra.core.services;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.tools.util.ReferenceCounted;

/**
 * A service factory that creates a reference-counted service that is shared
 * amongst all {@linkplain ServicesRegistry service registries}. If the
 * service interface {@code S} does not conform to the {@link IService} protocol
 * then it is recommended to override the following operations:
 * <ul>
 * <li>{@link #start(Object)}</li>
 * <li>{@link #dispose(Object)}</li>
 * </ul>
 * In any case, if the service is an {@link IService}, it will never be
 * {@linkplain IService#init(ServicesRegistry) initialized} with a service
 * registry because it is shared by all service registries.
 * 
 * @param <S>
 *            the shared service interface
 * 
 * @since 2.0
 */
public class SharedServiceFactory<S> implements IServiceFactory {

	private final Class<? extends S> serviceInterface;

	private final Supplier<? extends S> serviceCreator;

	private final Function<? super ServicesRegistry, Scope> scopeProvider;

	private Scope scope;

	private S serviceInstance;

	/**
	 * Creates a new shared service factory for services of the given type. Subclasses that
	 * use this constructor must override the {@link #createSharedInstance()} method to
	 * create the shared service instance. The {@link Scope} of the shared service instance
	 * is the {@linkplain Scope#GLOBAL_SCOPE global scope}.
	 *
	 * @param serviceInterface
	 *            the service type that I create
	 */
	protected SharedServiceFactory(Class<? extends S> serviceInterface) {
		this(serviceInterface, null, null);
	}

	/**
	 * Creates a new shared service factory for services of the given type with a supplier
	 * that provides the shared service instance on demand. Subclasses that
	 * use this constructor need not override the {@link #createSharedInstance()} method to
	 * create the shared service instance, unless the {@code serviceCreator} is {@code null}.
	 * The {@link Scope} of the shared service instance is the
	 * {@linkplain Scope#GLOBAL_SCOPE global scope}.
	 *
	 * @param serviceInterface
	 *            the service type that I create
	 * @param serviceCreator
	 *            a supplier that creates a new service instance on demand. This commonly
	 *            would be a zero-argument constructor of the service implementation class
	 */
	protected SharedServiceFactory(Class<? extends S> serviceInterface, Supplier<? extends S> serviceCreator) {
		this(serviceInterface, serviceCreator, null);
	}

	/**
	 * Creates a new shared service factory for services of the given type in a defined
	 * scope, with a supplier that provides the shared service instance on demand.
	 * Subclasses that use this constructor need not override the {@link #createSharedInstance()}
	 * method to create the shared service instance, unless the {@code serviceCreator} is
	 * {@code null}.
	 *
	 * @param serviceInterface
	 *            the service type that I create
	 * @param serviceCreator
	 *            a supplier that creates a new service instance on demand. This commonly
	 *            would be a zero-argument constructor of the service implementation class
	 * @param scopeProvider
	 *            a function that computes the appropriate {@link Scope} in which
	 *            a registry should find the shared instance of my service. A {@code null} scope
	 *            is equivalent to the {@linkplain Scope#GLOBAL_SCOPE global scope}
	 */
	protected SharedServiceFactory(Class<? extends S> serviceInterface, Supplier<? extends S> serviceCreator, Function<? super ServicesRegistry, Scope> scopeProvider) {
		super();

		this.serviceInterface = serviceInterface;
		this.serviceCreator = (serviceCreator != null) ? serviceCreator : this::createSharedInstance;
		this.scopeProvider = (scopeProvider != null) ? scopeProvider : __ -> Scope.GLOBAL_SCOPE;
	}

	@Override
	public final void init(ServicesRegistry servicesRegistry) throws ServiceException {
		// A shared service doesn't need to know any particular registry, but I
		// need to create my scope
		scope = scopeProvider.apply(servicesRegistry);
	}

	@Override
	public final void startService() throws ServiceException {
		// It was already started on creation
	}

	/**
	 * Releases my service instance, thereby reducing its reference count and potentially
	 * actually disposing of it (if there are no other service registries using it).
	 */
	@Override
	public final void disposeService() {
		if (serviceInstance != null) {
			serviceInstance = null;
			scope.maybeGetService(serviceInterface).ifPresent(ReferenceCounted::release);
			scope = null;
		}
	}

	@Override
	public final Object createServiceInstance() throws ServiceException {
		if (serviceInstance == null) {
			try {
				serviceInstance = serviceInterface.cast(getCountedService().retain());
			} catch (ClassCastException e) {
				getCountedService().release(); // It's of no use to us
				throw new ServiceException("Incompatible service type", e); //$NON-NLS-1$
			}
		}

		return serviceInstance;
	}

	private ReferenceCounted<?> getCountedService() throws ServiceException {
		return scope.getService(serviceInterface, this);
	}

	/**
	 * If the factory is not {@linkplain #SharedServiceFactory(Class, Supplier) initialized}
	 * with a service creator, then this method must be overridden to create the shared
	 * service instance. The default implementation throws {@link UnsupportedOperationException}..
	 * 
	 * @return a new instance of my service, to be shared by all registries
	 */
	protected S createSharedInstance() {
		throw new UnsupportedOperationException("createSharedInstance"); //$NON-NLS-1$
	}

	/**
	 * Starts the shared instance of my service. This is only called once in the lifetime
	 * of the instance. The default implementation forwards to the {@link IService} protocol
	 * if the {@code sharedInstance} conforms to it. Otherwise, subclasses should override
	 * to start the service instance if necessary.
	 * 
	 * @param sharedInstance
	 *            the shared instance of my service
	 * 
	 * @throws ServiceException
	 *             on failure to start the instance
	 * 
	 * @see IService#startService()
	 */
	protected void start(S sharedInstance) throws ServiceException {
		if (sharedInstance instanceof IService) {
			((IService) sharedInstance).startService();
		}
	}

	/**
	 * Disposes of the shared instance of my service. This is only called once in the lifetime
	 * of the instance. The default implementation forwards to the {@link IService} protocol
	 * if the {@code sharedInstance} conforms to it. Otherwise, subclasses should override
	 * to dispose of the service instance if necessary.
	 * 
	 * @param sharedInstance
	 *            the shared instance of my service
	 * 
	 * @throws ServiceException
	 *             on failure to dispose of the instance
	 * 
	 * @see IService#disposeService()
	 */
	protected void dispose(S sharedInstance) throws ServiceException {
		if (sharedInstance instanceof IService) {
			((IService) sharedInstance).disposeService();
		}
	}

	//
	// Nested types
	//

	/**
	 * A scope in which a single instance of my service is to be shared. Any number of
	 * service registries can share the same service within a scope if they have factories
	 * that provide that scope. It is up to each factory to determine the scope appropriate
	 * for its registry. The default scope is the {@link #GLOBAL_SCOPE}. Clients may
	 * create any scopes that they may need, in addition to using the
	 * {@linkplain #GLOBAL_SCOPE global scope}.
	 * 
	 * @noextend This class is not intended to be subclassed by clients.
	 */
	public static class Scope {
		/**
		 * The default scope in which registries share service instances, being a global scope
		 * available to all registries.
		 */
		public static final Scope GLOBAL_SCOPE = new Scope();

		/**
		 * Initialize me.
		 */
		public Scope() {
			super();
		}

		private final Map<Class<?>, ReferenceCounted<?>> serviceInstances = new ConcurrentHashMap<>();

		/**
		 * Obtains the shared instance of the specified service interface if it currently
		 * exists in the scope.
		 * 
		 * @param serviceInterface
		 *            a service interface
		 * 
		 * @return the current shared instance, or {@code null} if it does not exist
		 */
		public <S> S getService(Class<S> serviceInterface) {
			return maybeGetService(serviceInterface)
					.map(serviceInterface::cast)
					.orElse(null);
		}

		Optional<ReferenceCounted<?>> maybeGetService(Class<?> serviceInterface) {
			return Optional.ofNullable(serviceInstances.get(serviceInterface));
		}

		<S> ReferenceCounted<?> getService(Class<? extends S> serviceInterface, SharedServiceFactory<S> factory) throws ServiceException {
			try {
				return serviceInstances.computeIfAbsent(serviceInterface, type -> {
					S sharedInstance = factory.serviceCreator.get();

					try {
						factory.start(sharedInstance);
					} catch (ServiceException e) {
						throw new WrappedException(e);
					}

					return new ReferenceCounted<>(sharedInstance, () -> {
						// Remove the mapping for this shared instance
						serviceInstances.remove(type);

						// And dispose of it
						try {
							factory.dispose(sharedInstance);
						} catch (Exception e) {
							Activator.log.error("Shared service instance not successfully disposed", e); //$NON-NLS-1$
						}
					});
				});
			} catch (WrappedException e) {
				throw (ServiceException) e.exception();
			}
		}
	}
}
