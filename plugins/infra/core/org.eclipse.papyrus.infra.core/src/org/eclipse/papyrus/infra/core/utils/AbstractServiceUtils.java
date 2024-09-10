/*****************************************************************************
 * Copyright (c) 2010, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bugs 468030, 485220, 474467
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.utils;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.services.spi.IContextualServiceRegistryTracker;
import org.eclipse.papyrus.infra.tools.util.IProgressCallable;
import org.eclipse.papyrus.infra.tools.util.IProgressRunnable;

/**
 * Set of utility methods for accessing core Services. This class provide
 * methods to access the Papyrus well known services.
 *
 * <br>
 * This is the base class for concrete classes providing these utility methods.
 * Concrete class need to provide method {@link #getServiceRegistry(Object)} implementing how the ServiceRegistry is retrieved from the provided
 * object.
 * Subclasses can also provide a Singleton pattern (getInstance()) in order to
 * allow access to the utility methods in a static way. <br>
 *
 * @author cedri dumoulin
 *
 */
public abstract class AbstractServiceUtils<T> {

	/**
	 * Get the service registry from the specified parameter.
	 *
	 * @param from
	 * @return
	 */
	public abstract ServicesRegistry getServiceRegistry(T from) throws ServiceException;

	/**
	 * Obtains the service registry determined automatically from the context of which
	 * Papyrus editor or view is active (implying the model that the user is currently editing).
	 * 
	 * @return the contextual service registry, or {@code null} if none can be determined
	 * @since 2.0
	 */
	protected ServicesRegistry getContextualServiceRegistry() {
		IContextualServiceRegistryTracker tracker = Activator.getDefault().getContextualServiceRegistryTracker();
		return (tracker == null) ? null : tracker.getServiceRegistry();
	}

	/**
	 * Gets the {@link TransactionalEditingDomain} registered in the {@link ServicesRegistry}.
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public TransactionalEditingDomain getTransactionalEditingDomain(T from) throws ServiceException {
		return getServiceRegistry(from).getService(TransactionalEditingDomain.class);
	}

	/**
	 * Gets the {@link IPageManager} registered in the {@link ServicesRegistry}.
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 * @since 2.0
	 */
	public IPageManager getIPageManager(T from) throws ServiceException {
		return getServiceRegistry(from).getService(IPageManager.class);
	}

	/**
	 * Gets the {@link IPageMngr} registered in the {@link ServicesRegistry}.
	 *
	 * @param from
	 * @return
	 * @throws ServiceException
	 *             If an error occurs while getting the requested service.
	 */
	public ModelSet getModelSet(T from) throws ServiceException {
		return getServiceRegistry(from).getService(ModelSet.class);
	}

	/**
	 * Returns an implementation of the requested service, from the specified context
	 *
	 * @param service
	 *            The service for which an implementation is requested
	 * @param from
	 *            The context from which the service should be retrieved
	 * @return
	 * 		The implementation of the requested service
	 * @throws ServiceException
	 *             If an error occurs (e.g. cannot find the ServicesRegistry or the Service)
	 *
	 */
	public <S> S getService(Class<S> service, T from) throws ServiceException {
		return getServiceRegistry(from).getService(service);
	}

	/**
	 * Returns an implementation of the requested service, from the specified context
	 *
	 * @param service
	 *            The service for which an implementation is requested
	 * @param from
	 *            The context from which the service should be retrieved
	 * @return
	 * 		The implementation of the requested service
	 * @throws ServiceException
	 *             If an error occurs (e.g. cannot find the ServicesRegistry or the Service)
	 *
	 */
	public Object getService(Object service, T from) throws ServiceException {
		return getServiceRegistry(from).getService(service);
	}

	/**
	 * Returns an implementation of the requested <em>optional</em> service, from the specified context, if it is available.
	 *
	 * @param service
	 *            The service for which an implementation is requested
	 * @param from
	 *            The context from which the service should be retrieved
	 * @param defaultImpl
	 *            A default implementation of the requested service API to return if none is available in the registry
	 *            or if the registered implementation could not be properly initialized. May be {@code null} if the
	 *            service is <em>optional</em>
	 * 
	 * @return
	 * 		The implementation of the requested service, or the {@code defaultImpl}
	 */
	public <S> S getService(Class<S> service, T from, S defaultImpl) {
		try {
			// Don't even attempt to get a registry from a null context
			return (from == null) ? defaultImpl : getServiceRegistry(from).getService(service);
		} catch (ServiceException e) {
			// That's OK. It's optional and we have a default
			return defaultImpl;
		}
	}

	/**
	 * Obtains a Papyrus callable from a plain {@code callable} with the registry
	 * context derived {@code from} the given context object.
	 * 
	 * @param callable
	 *            a callable to encapsulate
	 * @param from
	 *            the Papyrus context from which to derive the registry
	 * 
	 * @return the Papyrus callable
	 * @since 2.0
	 */
	public <V> IPapyrusCallable<V> callable(IProgressCallable<V> callable, T from) {
		return new IPapyrusCallable<V>() {
			@Override
			public V call(IProgressMonitor monitor) throws Exception {
				return callable.call(monitor);
			}

			@Override
			public ServicesRegistry getServiceRegistry() {
				ServicesRegistry result = null;

				try {
					result = AbstractServiceUtils.this.getServiceRegistry(from);
				} catch (ServiceException e) {
					Activator.log.error(e);
				}

				return result;
			}
		};
	}

	/**
	 * Obtains a Papyrus runnable from a plain {@code runnable} with the registry
	 * context derived {@code from} the given context object.
	 * 
	 * @param runnable
	 *            a runnable to encapsulate
	 * @param from
	 *            the Papyrus context from which to derive the registry
	 * 
	 * @return the Papyrus runnable
	 * @since 2.0
	 */
	public IPapyrusRunnable runnable(IProgressRunnable runnable, T from) {
		return new IPapyrusRunnable() {
			@Override
			public void run(IProgressMonitor monitor) {
				runnable.run(monitor);
			}

			@Override
			public ServicesRegistry getServiceRegistry() {
				ServicesRegistry result = null;

				try {
					result = AbstractServiceUtils.this.getServiceRegistry(from);
				} catch (ServiceException e) {
					Activator.log.error(e);
				}

				return result;
			}
		};
	}

	/**
	 * Attempts to obtain the service registry from the given context object.
	 * 
	 * @param from
	 *            the context object
	 * @return maybe the registry
	 * 
	 * @since 2.0
	 */
	protected Optional<ServicesRegistry> tryServiceRegistry(T from) {
		try {
			return Optional.ofNullable(getServiceRegistry(from));
		} catch (ServiceException e) {
			Activator.log.error(e);
			return Optional.empty();
		}
	}

	/**
	 * Attempts to obtain the requested from the registry associated with
	 * the given context object.
	 * 
	 * @param from
	 *            the context object
	 * @param serviceType
	 *            the type of service to obtain
	 * @return maybe the service
	 * 
	 * @since 2.0
	 */
	public <S> Optional<S> tryService(T from, Class<S> serviceType) {
		return tryServiceRegistry(from).map(services -> {
			try {
				return services.getService(serviceType);
			} catch (ServiceException e) {
				Activator.log.error(e);
				return (S) null;
			}
		});
	}
}
