/*****************************************************************************
 * Copyright (c) 2014, 2018 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bugs 429826, 408491, 433320
 *  Christian W. Damus - bugs 451557, 457560, 461629, 463564, 466997, 465416, 485220, 498140, 533679
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionImpl;
import org.eclipse.papyrus.infra.core.resource.ReadOnlyAxis;
import org.eclipse.papyrus.infra.tools.util.IExecutorService;
import org.eclipse.papyrus.infra.tools.util.IProgressCallable;
import org.eclipse.papyrus.infra.tools.util.IProgressRunnable;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


/**
 * This helper can be used to run (safe) transactions outside the CommandStack
 *
 * @author Camille Letavernier
 *
 */
public class TransactionHelper {

	// Refactoring needed. The sasheditor contentprovider should have dependencies to infra.EMF...

	public static final String TRANSACTION_OPTION_READ_ONLY_AXIS = "papyrus.read_only_axis"; //$NON-NLS-1$

	/**
	 * @deprecated Since 1.1 M7 read-only state of resources is always cached and the cache lifecycle is not tied to transactions.
	 */
	@Deprecated
	public static final String TRANSACTION_OPTION_NO_READ_ONLY_CACHE = "papyrus.no_read_only_cache"; //$NON-NLS-1$

	public static final String TRANSACTION_OPTION_INTERACTIVE = "papyrus.interactive"; //$NON-NLS-1$

	public static final String TRANSACTION_OPTION_MERGE_NESTED_READ = "papyrus.merge_nested_read"; //$NON-NLS-1$

	/**
	 * @since 2.0
	 */
	public static void run(EditingDomain domain, Runnable writeOperation) throws InterruptedException, RollbackException {
		if (domain instanceof TransactionalEditingDomain) {
			run((TransactionalEditingDomain) domain, writeOperation);
		} else {
			writeOperation.run();
		}
	}

	/**
	 * @since 2.0
	 */
	public static void run(TransactionalEditingDomain domain, final Runnable writeOperation) throws InterruptedException, RollbackException {
		if (domain instanceof InternalTransactionalEditingDomain) {
			run((InternalTransactionalEditingDomain) domain, writeOperation);
		} else {
			// Shouldn't happen, as all TransactionalEditingDomain implementations should also implement InternalTransactionalEditingDomain
			domain.getCommandStack().execute(new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					writeOperation.run();
				}
			});
		}
	}

	/**
	 * @since 2.0
	 */
	public static void run(InternalTransactionalEditingDomain domain, Runnable writeOperation) throws InterruptedException, RollbackException {
		Transaction transaction = domain.startTransaction(false, Collections.emptyMap());
		try {
			writeOperation.run();
		} finally {
			transaction.commit();
		}
	}

	/**
	 * Queries whether an editing {@code domain} has been disposed.
	 *
	 * @param domain
	 *            an editing domain
	 *
	 * @return whether the {@code domain} is {@code null} (which presumably implies disposed) or has been disposed
	 */
	public static boolean isDisposed(TransactionalEditingDomain domain) {
		// A disposed editing domain (and only a disposed editing domain) has no command stack
		return (domain == null) || (domain.getCommandStack() == null);
	}

	/**
	 * Merges the read-only {@code axis} option into an existing map of {@code options}.
	 *
	 * @param options
	 *            an existing (non-{@code null}) options map
	 * @param axis
	 *            the axis option to merge
	 * @return the augmented {@code options}
	 */
	public static Map<String, Object> mergeReadOnlyAxisOption(Map<String, Object> options, ReadOnlyAxis axis) {
		return mergeReadOnlyAxisOption(options, Collections.singleton(axis));
	}

	/**
	 * Merges the read-only {@code axes} option into an existing map of {@code options}.
	 *
	 * @param options
	 *            an existing (non-{@code null}) options map
	 * @param axes
	 *            the axes option to merge
	 * @return the augmented {@code options}
	 */
	public static Map<String, Object> mergeReadOnlyAxisOption(Map<String, Object> options, Set<ReadOnlyAxis> axes) {
		options.put(TRANSACTION_OPTION_READ_ONLY_AXIS, axes);
		return options;
	}

	/**
	 * Adds the read-only {@code axis} option to a transaction's {@code options}.
	 *
	 * @param options
	 *            an options map, which may be {@code null} or immutable
	 * @param axis
	 *            the axis option to add
	 * @return a new map based on the {@code options} and including the {@code axis}
	 */
	public static Map<String, Object> addReadOnlyAxisOption(Map<String, ?> options, ReadOnlyAxis axis) {
		return addReadOnlyAxisOption(options, Collections.singleton(axis));
	}

	/**
	 * Adds the read-only {@code axes} option to a transaction's {@code options}.
	 *
	 * @param options
	 *            an options map, which may be {@code null} or immutable
	 * @param axes
	 *            the axes option to add
	 * @return a new map based on the {@code options} and including the {@code axes}
	 */
	public static Map<String, Object> addReadOnlyAxisOption(Map<String, ?> options, Set<ReadOnlyAxis> axes) {
		Map<String, Object> result = (options == null) ? Maps.<String, Object> newHashMap() : Maps.newHashMap(options);
		result.put(TRANSACTION_OPTION_READ_ONLY_AXIS, axes);
		return result;
	}

	/**
	 * Creates a new mutable transaction options map with a read-only {@code axis}.
	 *
	 * @param axis
	 *            the axis option
	 * @return a new mutable map including the {@code axis}
	 */
	public static Map<String, Object> readOnlyAxisOption(ReadOnlyAxis axis) {
		return readOnlyAxisOption(Collections.singleton(axis));
	}

	/**
	 * Creates a new mutable transaction options map with a read-only {@code axes}.
	 *
	 * @param axes
	 *            the axes option
	 * @return a new mutable map including the {@code axes}
	 */
	public static Map<String, Object> readOnlyAxisOption(Set<ReadOnlyAxis> axes) {
		return addReadOnlyAxisOption(null, axes);
	}

	/**
	 * Queries the read-only axes to be enforced by a {@code transaction}.
	 *
	 * @param transaction
	 *            a transaction
	 * @return its read-only axes, which are {@linkplain ReadOnlyAxis#anyAxis() all of them} by default if the option is absent
	 */
	@SuppressWarnings("unchecked")
	public static Set<ReadOnlyAxis> getReadOnlyAxisOption(Transaction transaction) {
		Set<ReadOnlyAxis> result;

		Object value = transaction.getOptions().get(TRANSACTION_OPTION_READ_ONLY_AXIS);
		if (value instanceof Set<?>) {
			result = (Set<ReadOnlyAxis>) value;
		} else if (value instanceof Iterable<?>) {
			result = Sets.immutableEnumSet((Iterable<ReadOnlyAxis>) value);
		} else {
			result = ReadOnlyAxis.anyAxis();
		}

		return result;
	}

	/**
	 * Merges the option to disable read-only state caching into an existing map of {@code options}.
	 *
	 * @param options
	 *            an existing (non-{@code null}) options map
	 * @param disableCache
	 *            whether to disable read-only caching
	 * @return the augmented {@code options}
	 * @deprecated Since 1.1 M7 read-only state of resources is always cached and the cache lifecycle is not tied to transactions.
	 */
	@Deprecated
	public static Map<String, Object> mergeDisableReadOnlyOption(Map<String, Object> options, boolean disableCache) {
		options.put(TRANSACTION_OPTION_NO_READ_ONLY_CACHE, disableCache);
		return options;
	}

	/**
	 * Adds the option to disable read-only state caching to a transaction's {@code options}.
	 *
	 * @param options
	 *            an options map, which may be {@code null} or immutable
	 * @param disableCache
	 *            whether to disable read-only caching
	 * @return a new map based on the {@code options} and including the {@code disableCache} option
	 * @deprecated Since 1.1 M7 read-only state of resources is always cached and the cache lifecycle is not tied to transactions.
	 */
	@Deprecated
	public static Map<String, Object> addDisableReadOnlyCacheOption(Map<String, ?> options, boolean disableCache) {
		Map<String, Object> result = (options == null) ? Maps.<String, Object> newHashMap() : Maps.newHashMap(options);
		result.put(TRANSACTION_OPTION_NO_READ_ONLY_CACHE, disableCache);
		return result;
	}

	/**
	 * Creates a new mutable transaction options map with the option to disable caching of read-only state for objects and resources.
	 *
	 * @param disableCache
	 *            whether to disable read-only caching
	 * @return a new mutable map including the {@code disableCache} option
	 * @deprecated Since 1.1 M7 read-only state of resources is always cached and the cache lifecycle is not tied to transactions.
	 */
	@Deprecated
	public static Map<String, Object> disableReadOnlyCacheOption(boolean disableCache) {
		return addDisableReadOnlyCacheOption(null, disableCache);
	}

	/**
	 * Queries whether a {@code transaction} is running with caching of read-only state of objects and resources disabled.
	 *
	 * @param transaction
	 *            a transaction
	 * @return {@code true} if the {@code transaction} has the {@linkplain #TRANSACTION_OPTION_NO_READ_ONLY_CACHE interactive option} set {@code true}; {@code false}, otherwise (including the default case of no option set)
	 * @deprecated Since 1.1 M7 read-only state of resources is always cached and the cache lifecycle is not tied to transactions.
	 */
	@Deprecated
	public static boolean isReadOnlyCacheDisabled(Transaction transaction) {
		Object value = transaction.getOptions().get(TRANSACTION_OPTION_NO_READ_ONLY_CACHE);
		return (value instanceof Boolean) ? (Boolean) value : false;
	}

	/**
	 * Merges the option to merge nested read-only transactions with parent write transactions into an existing map of {@code options}.
	 * This option on a write transaction affects this behaviour of subsequent child read-only transactions.
	 *
	 * @param options
	 *            an existing (non-{@code null}) options map
	 * @param mergeReadOnly
	 *            whether to merge read-only transactions into parent write transactions
	 * @return the augmented {@code options}
	 */
	public static Map<String, Object> mergeMergeReadOnlyOption(Map<String, Object> options, boolean mergeReadOnly) {
		options.put(TRANSACTION_OPTION_MERGE_NESTED_READ, mergeReadOnly);
		return options;
	}

	/**
	 * Adds the option to merge nested read-only transactions with parent write transactions to a transaction's {@code options}.
	 * This option on a write transaction affects this behaviour of subsequent child read-only transactions.
	 *
	 * @param options
	 *            an options map, which may be {@code null} or immutable
	 * @param mergeReadOnly
	 *            whether to merge read-only transactions into parent write transactions
	 * @return a new map based on the {@code options} and including the {@code mergeReadOnly} option
	 */
	public static Map<String, Object> addMergeReadOnlyOption(Map<String, ?> options, boolean mergeReadOnly) {
		Map<String, Object> result = (options == null) ? Maps.<String, Object> newHashMap() : Maps.newHashMap(options);
		result.put(TRANSACTION_OPTION_MERGE_NESTED_READ, mergeReadOnly);
		return result;
	}

	/**
	 * Creates a new mutable transaction options map with the option to merge nested read-only transactions with parent write transactions.
	 * This option on a write transaction affects this behaviour of subsequent child read-only transactions.
	 *
	 * @param mergeReadOnly
	 *            whether to merge read-only transactions into parent write transactions
	 * @return a new mutable map including the {@code mergeReadOnly} option
	 */
	public static Map<String, Object> mergeReadOnlyOption(boolean mergeReadOnly) {
		return addMergeReadOnlyOption(null, mergeReadOnly);
	}

	/**
	 * Queries whether a {@code transaction} is running with merging of nested read-only transactions with parent write transactions enabled.
	 *
	 * @param transaction
	 *            a transaction
	 * @return {@code true} if the {@code transaction} has the {@linkplain #TRANSACTION_OPTION_MERGE_NESTED_READ interactive option} set {@code true}; {@code false}, otherwise (including the default case of no option set)
	 */
	public static boolean isMergeReadOnly(Transaction transaction) {
		Object value = transaction.getOptions().get(TRANSACTION_OPTION_MERGE_NESTED_READ);
		return (value instanceof Boolean) ? (Boolean) value : false;
	}

	/**
	 * Merges the {@code interactive} transaction option into an existing map of {@code options}.
	 *
	 * @param options
	 *            an existing (non-{@code null}) options map
	 * @param interactive
	 *            whether the transaction is in an user-interactive context
	 * @return the augmented {@code options}
	 */
	public static Map<String, Object> mergeInteractiveOption(Map<String, Object> options, boolean interactive) {
		options.put(TRANSACTION_OPTION_INTERACTIVE, interactive);
		return options;
	}

	/**
	 * Adds the {@code interactive} option option to a transaction's {@code options}.
	 *
	 * @param options
	 *            an options map, which may be {@code null} or immutable
	 * @param interactive
	 *            whether the transaction is in an user-interactive context
	 * @return a new map based on the {@code options} and including the {@code interactive} option
	 */
	public static Map<String, Object> addInteractiveOption(Map<String, ?> options, boolean interactive) {
		Map<String, Object> result = (options == null) ? Maps.<String, Object> newHashMap() : Maps.newHashMap(options);
		result.put(TRANSACTION_OPTION_INTERACTIVE, interactive);
		return result;
	}

	/**
	 * Creates a new mutable transaction options map with an {@code interactive} option.
	 *
	 * @param interactive
	 *            whether the transaction is in an user-interactive context
	 * @return a new mutable map including the {@code interactive} option
	 */
	public static Map<String, Object> interactiveOption(boolean interactive) {
		return addInteractiveOption(null, interactive);
	}

	/**
	 * Queries whether a {@code transaction} is running in a user-interactive context. In practice, this means that it would be appropriate to
	 * prompt the user to make resources/objects writable if necessary.
	 *
	 * @param transaction
	 *            a transaction
	 * @return {@code false} if the {@code transaction} has the {@linkplain #TRANSACTION_OPTION_INTERACTIVE interactive option} set {@code false}; {@code true}, otherwise (including the default case of no option set)
	 */
	public static boolean isInteractive(Transaction transaction) {
		Object value = transaction.getOptions().get(TRANSACTION_OPTION_INTERACTIVE);
		return (value instanceof Boolean) ? (Boolean) value : true;
	}

	/**
	 * Queries whether a {@code transaction} is one that is executing or that executed triggers, or is perhaps
	 * the read-only post-commit transaction that tells listeners about the changes made by a trigger transaction.
	 *
	 * @param transaction
	 *            a transaction
	 * @return whether it is a trigger transaction
	 */
	public static boolean isTriggerTransaction(Transaction transaction) {
		Object value = transaction.getOptions().get(TransactionImpl.OPTION_IS_TRIGGER_TRANSACTION);
		return (value instanceof Boolean) ? (Boolean) value : false;
	}

	/**
	 * Creates an {@link Executor} that executes {@link Runnable}s at the pre-commit phase of the active write
	 * transaction of the specified editing {@code domain} or at some other time if no write transaction is active.
	 *
	 * @param domain
	 *            a transactional editing domain. May not be {@code null}
	 * @param fallback
	 *            an executor to use for scheduling tasks when the {@code domain} does not have a
	 *            write transaction open. May not be {@code null}
	 */
	public static Executor createTransactionExecutor(TransactionalEditingDomain domain, Executor fallback) {
		return createTransactionExecutor(domain, fallback, null, null);
	}

	/**
	 * Creates an {@link Executor} that executes {@link Runnable}s at the pre-commit phase of the active write
	 * transaction of the specified editing {@code domain} or at some other time if no write transaction is active.
	 * The specified {@code policy}, if any, may determine whether for some task the {@code fallback} should be preferred
	 * over the transaction executor or vice-versa (such as to handle special requirements like tasks needing to run
	 * on the UI thread).
	 *
	 * @param domain
	 *            a transactional editing domain. May not be {@code null}
	 * @param fallback
	 *            an executor to use for scheduling tasks when the {@code domain} does not have a
	 *            write transaction open. May not be {@code null}
	 * @param policy
	 *            an optional executor selection policy (may be {@code null}). The policy is queried for ranking of both
	 *            the transaction executor and the {@code fallback}, unless there is no transaction active, which always excludes the
	 *            transaction executor. In the event of a tie, the transaction executor is always selected
	 */
	public static Executor createTransactionExecutor(TransactionalEditingDomain domain, Executor fallback, IExecutorPolicy policy) {
		return createTransactionExecutor(domain, fallback, policy, null);
	}

	/**
	 * Creates an {@link Executor} that executes {@link Runnable}s at the pre-commit phase of the active write
	 * transaction of the specified editing {@code domain} or at some other time if no write transaction is active.
	 *
	 * @param domain
	 *            a transactional editing domain. May not be {@code null}
	 * @param fallback
	 *            an executor to use for scheduling tasks when the {@code domain} does not have a
	 *            write transaction open. May not be {@code null}
	 * @param options
	 *            a map of options to apply to the nested transaction in which tasks are executed. May be {@code null} if not needed
	 */
	public static Executor createTransactionExecutor(TransactionalEditingDomain domain, Executor fallback, Map<?, ?> options) {
		return createTransactionExecutor(domain, fallback, null, options);
	}

	/**
	 * Creates an {@link Executor} that executes {@link Runnable}s at the pre-commit phase of the active write
	 * transaction of the specified editing {@code domain} or at some other time if no write transaction is active.
	 * The specified {@code policy}, if any, may determine whether for some task the {@code fallback} should be preferred
	 * over the transaction executor or vice-versa (such as to handle special requirements like tasks needing to run
	 * on the UI thread).
	 *
	 * @param domain
	 *            a transactional editing domain. May not be {@code null}
	 * @param fallback
	 *            an executor to use for scheduling tasks when the {@code domain} does not have a
	 *            write transaction open. May not be {@code null}
	 * @param policy
	 *            an optional executor selection policy (may be {@code null}). The policy is queried for ranking of both
	 *            the transaction executor and the {@code fallback}, unless there is no transaction active, which always excludes the
	 *            transaction executor. In the event of a tie, the transaction executor is always selected
	 * @param options
	 *            a map of options to apply to the nested transaction in which tasks are executed. May be {@code null} if not needed
	 */
	public static Executor createTransactionExecutor(TransactionalEditingDomain domain, Executor fallback, IExecutorPolicy policy, Map<?, ?> options) {
		if ((domain == null) || (fallback == null)) {
			throw new NullPointerException();
		}

		return new TransactionPrecommitExecutor(domain, fallback, policy, options);
	}

	/**
	 * Disposes of a {@linkplain #createTransactionExecutor(TransactionalEditingDomain, Executor) transaction executor}
	 * that is no longer needed on an editing domain that is still in use.
	 *
	 * @param executor
	 *            a transaction executor to dispose
	 *
	 * @throws IllegalArgumentException
	 *             if the {@code executor} is not a transaction executor
	 * @throws NullPointerException
	 *             if the {@code executor} is {@code null}
	 * @since 3.0
	 */
	public static void disposeTransactionExecutor(Executor executor) {
		if (!(Objects.requireNonNull(executor) instanceof TransactionPrecommitExecutor)) {
			throw new IllegalArgumentException("executor"); //$NON-NLS-1$
		}
		((TransactionPrecommitExecutor) executor).dispose();
	}

	/**
	 * Wrap a {@code read} action in exclusive read-only access on the given {@code domain}.
	 *
	 * @param domain
	 *            a transactional editing domain
	 * @param read
	 *            a runnable that will read from the {@code domain}
	 * @return a runnable that wraps the given {@code read} action in a read-only transaction on the {@code domain}
	 *
	 * @since 4.2
	 * @see #safe(TransactionalEditingDomain, Runnable)
	 */
	public static Runnable wrap(TransactionalEditingDomain domain, Runnable read) {
		return () -> SafeRunner.run(safe(domain, read));
	}

	/**
	 * Wrap a {@code read} action in exclusive read-only access on the given {@code domain}
	 * as a <em>safe runnable</em>.
	 *
	 * @param domain
	 *            a transactional editing domain
	 * @param read
	 *            a runnable that will read from the {@code domain}
	 * @return a <em>safe runnable</em> that wraps the given {@code read} action in a read-only transaction on the {@code domain}
	 *
	 * @since 4.2
	 * @see #wrap(TransactionalEditingDomain, Runnable)
	 */
	public static ISafeRunnable safe(TransactionalEditingDomain domain, Runnable read) {
		return () -> domain.runExclusive(read);
	}

	/**
	 * <p>
	 * Create a privileged progress runnable, which is like a regular {@linkplain TransactionalEditingDomain#createPrivilegedRunnable(Runnable)
	 * privileged runnable} except that it is given a progress monitor for progress reporting.
	 * </p>
	 * <b>Note</b> that a privileged progress-runnable can be used only once. Because it has the
	 * context of a borrowed transaction, repeated execution is invalid because the required
	 * transaction context will have expired. Attempting to run a privileged runnable a second
	 * time will throw an {@link IllegalStateException}.
	 * </p>
	 *
	 * @param domain
	 *            an editing domain
	 * @param runnable
	 *            a progress runnable that is to borrow the {@code domain}'s active transaction on the modal context thread
	 * @return the privileged runnable, ready to pass into the {@link IExecutorService} or other such API
	 * @since 2.0
	 */
	public static IProgressRunnable createPrivilegedRunnable(TransactionalEditingDomain domain, final IProgressRunnable runnable) {
		IProgressMonitor monitorHolder[] = { null };

		Runnable privileged = domain.createPrivilegedRunnable(() -> runnable.run(monitorHolder[0]));

		return new PrivilegedProgressRunnable(privileged, m -> monitorHolder[0] = m);
	}

	/**
	 * <p>
	 * Create a privileged progress callable, which is like a {@linkplain TransactionalEditingDomain#createPrivilegedRunnable(Runnable)
	 * privileged runnable} except that it is given a progress monitor for progress reporting and it computes a result.
	 * </p>
	 * <p>
	 * <b>Note</b> that a privileged progress-callable can be used only once. Because it has the
	 * context of a borrowed transaction, repeated execution is invalid because the required
	 * transaction context will have expired. Attempting to run a privileged callable a second
	 * time will throw an {@link IllegalStateException}.
	 * </p>
	 *
	 * @param callable
	 *            an editing domain
	 * @param callable
	 *            a progress callable that is to borrow the {@code domain}'s active transaction on the modal context thread
	 * @return the privileged callable, ready to pass into the {@link IExecutorService} or other such API
	 * @since 2.0
	 */
	public static <V> IProgressCallable<V> createPrivilegedCallable(TransactionalEditingDomain domain, final IProgressCallable<V> callable) {
		AtomicReference<V> resultHolder = new AtomicReference<>();
		Exception failHolder[] = { null };

		IProgressRunnable[] privileged = { createPrivilegedRunnable(domain, monitor -> {
			try {
				resultHolder.set(callable.call(monitor));
			} catch (Exception e) {
				failHolder[0] = e;
			}
		}) };

		return monitor -> {
			if (privileged[0] == null) {
				throw new IllegalStateException("Privileged callable was already run"); //$NON-NLS-1$
			}

			try {
				privileged[0].run(monitor);
			} finally {
				privileged[0] = null;
			}

			if (failHolder[0] != null) {
				throw failHolder[0];
			}

			return resultHolder.get();
		};
	}

	//
	// Nested types
	//

	private static final class PrivilegedProgressRunnable implements IProgressRunnable {
		private final Consumer<? super IProgressMonitor> monitorSlot;
		private Runnable privileged;

		PrivilegedProgressRunnable(Runnable privileged, Consumer<? super IProgressMonitor> monitorSlot) {
			super();

			this.monitorSlot = monitorSlot;
			this.privileged = privileged;
		}

		@Override
		public void run(IProgressMonitor monitor) {
			if (privileged == null) {
				throw new IllegalArgumentException("Privileged runnable was already run"); //$NON-NLS-1$
			}

			monitorSlot.accept(monitor);

			try {
				privileged.run();
			} finally {
				// Clear our reference to the runnable, which holds a reference
				// to the transaction which, in turn, holds monitors. If we
				// are run in the ModalContextThread and initialize an Xtext UI
				// bundle, then that thread will be retained forever in its
				// Guice injector as the creating thread, and that thread then
				// will retain me (its runnable). See bug 498140
				privileged = null;
			}
		}
	}
}
