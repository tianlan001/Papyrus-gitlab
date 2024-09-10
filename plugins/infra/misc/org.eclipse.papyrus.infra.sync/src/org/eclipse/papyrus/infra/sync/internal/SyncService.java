/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus and others.
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
 *   Quentin Le Menez - bug 570177
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ResourceAdapter;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.guava.internal.CheckedFuture;
import org.eclipse.papyrus.infra.sync.Activator;
import org.eclipse.papyrus.infra.sync.EMFDispatch;
import org.eclipse.papyrus.infra.sync.EMFDispatchManager;
import org.eclipse.papyrus.infra.sync.EMFListener;
import org.eclipse.papyrus.infra.sync.SyncRegistry;
import org.eclipse.papyrus.infra.sync.policy.DefaultSyncPolicy;
import org.eclipse.papyrus.infra.sync.policy.ISyncPolicy;
import org.eclipse.papyrus.infra.sync.policy.SyncPolicyDelegate;
import org.eclipse.papyrus.infra.sync.service.ISyncAction;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrus.infra.sync.service.ISyncTrigger;
import org.eclipse.papyrus.infra.sync.service.SyncServiceRunnable;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * Default implementation of the synchronization service.
 */
public class SyncService implements ISyncService {

	private static final ThreadLocal<SyncService> currentService = new ThreadLocal<>();

	private ServicesRegistry services;

	private TransactionalEditingDomain editingDomain;

	private RootTrigger rootTrigger = new RootTrigger();

	private EMFListener emfListener;

	private SyncPolicyDelegateRegistryImpl policyDelegates;

	private ISyncPolicy policy;

	private Executor executor;

	private final Map<Class<? extends SyncRegistry<?, ?, ?>>, SyncRegistry<?, ?, ?>> syncRegistries = Maps.newHashMap();

	public SyncService() {
		super();
	}

	public static SyncService getCurrent() {
		return currentService.get();
	}

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.services = servicesRegistry;
	}

	@Override
	public void startService() throws ServiceException {
		editingDomain = ServiceUtils.getInstance().getTransactionalEditingDomain(services);

		setAsyncExecutor(TransactionHelper.createTransactionExecutor(editingDomain, MoreExecutors.directExecutor()));

		policy = new SyncServiceOperation<ISyncPolicy>(this) {
			@Override
			protected ISyncPolicy doCall() throws Exception {
				policyDelegates = new SyncPolicyDelegateRegistryImpl(editingDomain);
				return new DefaultSyncPolicy(policyDelegates);
			}
		}.safeCall(ServiceException.class);

		rootTrigger.install(editingDomain);
	}

	@Override
	public void disposeService() throws ServiceException {
		// No disposal protocol for these
		syncRegistries.clear();

		policy = null;

		if (policyDelegates != null) {
			policyDelegates.dispose();
			policyDelegates = null;
		}

		if (emfListener != null) {
			emfListener.dispose();
			emfListener = null;
		}

		if (editingDomain != null) {
			rootTrigger.uninstall(editingDomain);
		}
		editingDomain = null;

		if (executor instanceof ExecutorService) {
			// No sense in running any pending operations because the whole editing environment is gone
			((ExecutorService) executor).shutdownNow();
		}
		executor = null;
	}

	@Override
	public boolean isActive() {
		return editingDomain != null;
	}

	EMFListener getEMFListener() {
		if (emfListener == null) {
			emfListener = new EMFListener(editingDomain);
		}
		return emfListener;
	}

	@Override
	public <D extends EMFDispatch> EMFDispatchManager<D> createSingleDispatchManager() {
		return new SyncServiceOperation<EMFDispatchManager<D>>(this) {
			@Override
			protected EMFDispatchManager<D> doCall() throws Exception {
				return EMFDispatchManager.createSingle(getEMFListener());
			}
		}.safeCall();
	}

	@Override
	public <D extends EMFDispatch> EMFDispatchManager<D> createMultipleDispatchManager() {
		return new SyncServiceOperation<EMFDispatchManager<D>>(this) {
			@Override
			protected EMFDispatchManager<D> doCall() throws Exception {
				return EMFDispatchManager.createMultiple(getEMFListener());
			}
		}.safeCall();
	}

	@Override
	public <M, T, X, R extends SyncRegistry<M, T, X>> R getSyncRegistry(final Class<R> registryType) {
		try {
			return new SyncServiceOperation<R>(this) {
				@Override
				protected R doCall() throws Exception {
					R result = TypeUtils.as(syncRegistries.get(registryType), registryType);

					if (result == null) {
						try {
							result = registryType.newInstance();
						} catch (InstantiationException e) {
							throw new ServiceException("Failed to instantiate sync registry.", e);
						} catch (IllegalAccessException e) {
							throw new ServiceException("Insufficient permission to instantiate sync registry.", e);
						}
						syncRegistries.put(registryType, result);
					}

					return result;
				}
			}.safeCall(ServiceException.class);
		} catch (ServiceException e) {
			Activator.log.error("Failed to access sync registry of type " + registryType.getName(), e);
			return null;
		}
	}

	@Override
	public IStatus evaluateTriggers(final Object object) {
		return new SyncServiceOperation<IStatus>(this) {
			@Override
			protected IStatus doCall() {
				IStatus result = Status.OK_STATUS;

				for (ISyncTrigger trigger : SyncTriggerRegistry.getInstance().getSyncTriggers(object)) {
					ISyncAction action = trigger.trigger(SyncService.this, object);
					if (action != null) {
						IStatus nextResult = action.perform(SyncService.this, object);
						if ((nextResult != null) && !nextResult.isOK()) {
							if (result.isOK()) {
								result = nextResult;
							} else if (result.isMultiStatus()) {
								((MultiStatus) result).merge(nextResult);
							} else {
								result = new MultiStatus(Activator.PLUGIN_ID, 0, new IStatus[] { result, nextResult }, "Multiple sync trigger evaluation problems occurred.", null);
							}
						}
					}
				}

				return result;
			}
		}.safeCall();
	}

	@Override
	public <V, X extends Exception> V run(final SyncServiceRunnable<V, X> operation) throws X {
		return new SyncServiceOperation<V>(this) {
			@Override
			protected V doCall() throws Exception {
				try {
					return operation.run(getCurrent());
				} catch (Throwable t) {
					throw new InvocationTargetException(t);
				}
			}
		}.safeCall(operation.getExceptionType());
	}

	@Override
	public TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}

	<V> V perform(SyncServiceOperation<V> operation) throws Exception {
		V result;

		final SyncService restore = currentService.get();
		currentService.set(this);
		try {
			result = operation.doCall();
		} finally {
			if (restore == null) {
				currentService.remove();
			} else {
				currentService.set(restore);
			}
		}

		return result;
	}

	@Override
	public void execute(Command command) {
		if (!command.canExecute()) {
			throw new IllegalArgumentException("unexecutable command"); //$NON-NLS-1$
		}

		if (command instanceof IdentityCommand) {
			return;
		}

		InternalTransactionalEditingDomain domain = TypeUtils.as(editingDomain, InternalTransactionalEditingDomain.class);
		if (domain == null) {
			// Easy
			command.execute();
		} else {
			Transaction active = domain.getActiveTransaction();
			if ((active == null) || active.isReadOnly()) {
				try {
					// Need to execute an unprotected write
					InternalTransaction transaction = domain.startTransaction(false, Collections.singletonMap(Transaction.OPTION_UNPROTECTED, true));
					try {
						command.execute();
					} finally {
						transaction.commit();
					}
				} catch (InterruptedException e) {
					// This would be weird
					Activator.log.error(e);
				} catch (RollbackException e) {
					// The only thing that can cause an unprotected write to roll back is a run-time exception
					Activator.log.error("Unprotected synchronization update rolled back. See next log entry for the cause.", null); //$NON-NLS-1$
					Activator.log.log(e.getStatus());
				}
			} else if (Boolean.TRUE.equals(active.getOptions().get(Transaction.OPTION_UNPROTECTED))) {
				// Already in an unprotected context? Just execute the command
				command.execute();
			} else {
				// Papyrus uses a nesting command stack, so just execute the command and it will happen in a nested transaction
				domain.getCommandStack().execute(command);
			}
		}
	}

	@Override
	public ISyncPolicy getSyncPolicy() {
		return policy;
	}

	@Override
	public void setSyncPolicy(ISyncPolicy syncPolicy) {
		this.policy = (syncPolicy == null) ? new NullSyncPolicy() : syncPolicy;
	}

	/**
	 * Registers a synchronization policy delegate with me.
	 *
	 * @param policyDelegate
	 *            the policy delegate to register
	 * @param featureType
	 *            the feature type on which to register it
	 *
	 * @return the listener on which the policy delegate must attach dispatchers for reacting to changes in the synchronized feature(s)
	 */
	public EMFListener register(SyncPolicyDelegate<?, ?> policyDelegate, Class<?> featureType) {
		policyDelegates.register(policyDelegate, featureType);
		return policyDelegates.getEMFListener();
	}

	/**
	 * De-registers a former synchronization policy delegate.
	 *
	 * @param policyDelegate
	 *            the policy delegate to de-register
	 * @param featureType
	 *            the feature type from which to de-register it
	 */
	public void deregister(SyncPolicyDelegate<?, ?> policyDelegate, Class<?> featureType) {
		policyDelegates.deregister(policyDelegate, featureType);
	}

	@Override
	public synchronized Executor getAsyncExecutor() {
		return executor;
	}

	@Override
	public synchronized void setAsyncExecutor(Executor executor) {
		if (executor == null) {
			throw new IllegalArgumentException("null executor");
		}

		if (executor != this.executor) {
			if (this.executor instanceof ExecutorService) {
				((ExecutorService) this.executor).shutdown();
			}
			this.executor = executor;
		}
	}

	@Override
	public <V, X extends Exception> CheckedFuture<V, X> runAsync(SyncServiceRunnable<V, X> operation) {
		CheckedFuture<V, X> result = operation.asFuture(this);
		getAsyncExecutor().execute((Runnable) result);
		return result;
	}

	//
	// Nested types
	//

	private class RootTrigger extends ResourceAdapter.Transactional {
		RootTrigger() {
			super();
		}

		@Override
		protected void handleResourceLoaded(Resource resource) {
			IStatus status = Status.OK_STATUS;

			// Process existing roots
			for (EObject root : resource.getContents()) {
				IStatus nextResult = processRoot(resource, root);
				if ((nextResult != null) && !nextResult.isOK()) {
					if (status.isOK()) {
						status = nextResult;
					} else if (status.isMultiStatus()) {
						((MultiStatus) status).merge(nextResult);
					} else {
						status = new MultiStatus(Activator.PLUGIN_ID, 0, new IStatus[] { status, nextResult }, "Multiple sync trigger evaluation problems occurred.", null);
					}
				}
			}

			if (!status.isOK()) {
				Activator.log.log(status);
			}
		}

		@Override
		protected void handleRootAdded(Resource resource, EObject root) {
			IStatus status = processRoot(resource, root);
			if (!status.isOK()) {
				Activator.log.log(status);
			}
		}

		protected IStatus processRoot(Resource resource, EObject root) {
			return evaluateTriggers(root);
		}
	}
}
