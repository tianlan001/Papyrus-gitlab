/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;

/**
 * An index of references to tooling models from the {@link ArchitectureDomain} models available
 * in the current context (installation, target, and workspace).
 */
public abstract class AbstractIndex {

	private final CopyOnWriteArrayList<Computation<?>> computations = new CopyOnWriteArrayList<>();

	protected AbstractIndex() {
		super();
	}

	/**
	 * Registers a new {@code computation}, if it has not already been registered. This operation
	 * is idempotent.
	 *
	 * @param computation
	 *            a computation to register
	 * @return the {@code computation} that was registered
	 */
	final <T> Computation<T> addComputation(Computation<T> computation) {
		computations.addIfAbsent(computation);
		return computation;
	}

	/**
	 * Reset all pending and completed computations.
	 */
	protected final void resetComputations() {
		computations.forEach(Computation::reset);
	}

	/**
	 * Asynchronously get the result of an index {@code computation}.
	 *
	 * @param computation
	 *            an index computation, which may be deferred, pending, or ready
	 * @return the future result of the {@code computation}
	 */
	protected final <T> CompletableFuture<T> asyncGet(Computation<T> computation) {
		return computation.get();
	}

	/**
	 * Asynchronously get a transformation of an index {@code computation}.
	 *
	 * @param computation
	 *            an index computation, which may be deferred, pending, or ready
	 * @param transformation
	 *            a transformation on the {@code computation}'s value
	 * @return the future result of the transformed {@code computation}
	 */
	protected final <T, U> CompletableFuture<U> asyncTransform(Computation<T> computation, Function<? super T, ? extends U> transformation) {
		return computation.get().thenApply(transformation);
	}

	/**
	 * Synchronously get the result of an index {@code computation}, waiting for it if necessary.
	 *
	 * @param computation
	 *            an index computation, which may be deferred, pending, or ready
	 * @return the result of the {@code computation}
	 */
	protected final <T> T get(Computation<T> computation) {
		T result = null;

		try {
			CompletableFuture<T> futureResult = asyncGet(computation);
			result = futureResult.get();
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the index
			Activator.log.error("Error querying model index.", e); //$NON-NLS-1$
			throw new OperationCanceledException();
		}

		return result;
	}

	/**
	 * Synchronously get the result of an index {@code computation}, waiting for it if necessary.
	 *
	 * @param computation
	 *            an index computation, which may be deferred, pending, or ready
	 * @param defaultValue
	 *            a default result to return in case the computation fails or is cancelled
	 * @return the result of the {@code computation}, or the default if not available
	 */
	protected final <T> T get(Computation<T> computation, T defaultValue) {
		T result;

		try {
			CompletableFuture<T> futureResult = asyncGet(computation);
			result = futureResult.get();
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the index? Then we didn't find anything
			Activator.log.error("Error querying model index.", e); //$NON-NLS-1$
			result = defaultValue;
		}

		return result;
	}

	/**
	 * Synchronously get a transformation of an index {@code computation}, waiting for it if necessary.
	 *
	 * @param computation
	 *            an index computation, which may be deferred, pending, or ready
	 * @param transformation
	 *            a transformation on the {@code computation}'s value
	 * @return the result of the transformed {@code computation}
	 */
	protected final <T, U> U transform(Computation<T> computation, Function<? super T, ? extends U> transformation) {
		U result = null;

		try {
			CompletableFuture<U> futureResult = asyncTransform(computation, transformation);
			result = futureResult.get();
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the index
			Activator.log.error("Error querying model index.", e); //$NON-NLS-1$
			throw new OperationCanceledException();
		}

		return result;
	}

	/**
	 * Synchronously get a transformation of an index {@code computation}, waiting for it if necessary.
	 *
	 * @param computation
	 *            an index computation, which may be deferred, pending, or ready
	 * @param defaultValue
	 *            a default result to transform in case the computation fails or is cancelled
	 * @param transformation
	 *            a transformation on the {@code computation}'s value
	 * @return the result of the transformed {@code computation}
	 */
	protected final <T, U> U transform(Computation<T> computation, T defaultValue, Function<? super T, ? extends U> transformation) {
		U result;

		try {
			CompletableFuture<U> futureResult = asyncTransform(computation, transformation);
			result = futureResult.get();
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the index
			Activator.log.error("Error querying model index.", e); //$NON-NLS-1$
			result = transformation.apply(defaultValue);
		}

		return result;
	}

	/**
	 * Asynchronously combine two index computations.
	 *
	 * @param computation1
	 *            an index computation, which may be deferred, pending, or ready
	 * @param computation2
	 *            another index computation, which similarly may be deferred, pending, or ready
	 * @param combination
	 *            a function combining the values of the computations
	 * @return the future result of the combined computations
	 */
	protected final <T, U, V> CompletableFuture<V> asyncCombine(Computation<T> computation1, Computation<U> computation2,
			BiFunction<? super T, ? super U, ? extends V> combination) {

		return computation1.get().thenCombine(computation2.get(), combination);
	}

	/**
	 * Synchronously combine two index computations.
	 *
	 * @param computation1
	 *            an index computation, which may be deferred, pending, or ready
	 * @param computation2
	 *            another index computation, which similarly may be deferred, pending, or ready
	 * @param combination
	 *            a function combining the values of the computations
	 * @return the future result of the combined computations
	 */
	protected final <T, U, V> V combine(Computation<T> computation1, Computation<U> computation2,
			BiFunction<? super T, ? super U, ? extends V> combination) {

		V result;

		try {
			CompletableFuture<V> futureResult = asyncCombine(computation1, computation2, combination);
			result = futureResult.get();
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the index
			Activator.log.error("Error querying model index.", e); //$NON-NLS-1$
			throw new OperationCanceledException();
		}

		return result;
	}

	/**
	 * Synchronously combine two index computations.
	 *
	 * @param computation1
	 *            an index computation, which may be deferred, pending, or ready
	 * @param defaultValue1
	 *            a default result to combine in case the first computation fails or is cancelled
	 * @param computation2
	 *            another index computation, which similarly may be deferred, pending, or ready
	 * @param defaultValue2
	 *            a default result to combine in case the second computation fails or is cancelled
	 * @param combination
	 *            a function combining the values of the computations
	 * @return the future result of the combined computations
	 */
	protected final <T, U, V> V combine(Computation<T> computation1, T defaultValue1,
			Computation<U> computation2, U defaultValue2,
			BiFunction<? super T, ? super U, ? extends V> combination) {

		V result;

		try {
			CompletableFuture<V> futureResult = asyncCombine(computation1, computation2, combination);
			result = futureResult.get();
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the index
			Activator.log.error("Error querying model index.", e); //$NON-NLS-1$
			result = combination.apply(defaultValue1, defaultValue2);
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * A potentially long-running computation that provides for thread-safe initiation
	 * and cancellation/reset to re-compute when inputs change.
	 *
	 * @param <T>
	 *            the type of the computation
	 */
	protected final class Computation<T> {
		private final AtomicReference<CompletableFuture<T>> computation = new AtomicReference<>();
		private final Supplier<T> computer;

		public Computation(Supplier<T> computer) {
			super();

			this.computer = computer;

			addComputation(this);
		}

		CompletableFuture<T> get() {
			CompletableFuture<T> newResult = new CompletableFuture<>();
			CompletableFuture<T> result = computation.compareAndExchange(null, newResult);

			if (result == null) {
				// We made the exchange, so should initiate the computation
				result = newResult;
				result.complete(computer.get());
			}

			return result;
		}

		void reset() {
			Optional.ofNullable(computation.getAndSet(null)).ifPresent(future -> future.cancel(false));
		}

	}

}
