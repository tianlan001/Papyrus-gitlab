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

package org.eclipse.papyrus.infra.tools.util;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Like an {@link Optional} except that when the value is not present, it can
 * provide a reason why. Also, because the alternative to success an explicit
 * failure with some kind of reason, a {@linkplain #isSuccess() successful Try}
 * may have a {@code null} {@linkplain #get() value}.
 *
 * @since 4.2
 */
public abstract class Try<T> implements Serializable {

	private static final long serialVersionUID = 2007056722268683220L;

	private static final Try<?> EMPTY = Try.success(null);

	/**
	 * Not instantiable by clients.
	 */
	Try() {
		super();
	}

	/**
	 * Queries whether the {@code Try} has a successful value, even if that value is {@code null}.
	 *
	 * @return whether I am a success
	 */
	public abstract boolean isSuccess();

	/**
	 * Obtains the value of a {@linkplain #isSuccess() successful} {@code Try}.
	 *
	 * @return my value, which may be {@code null}
	 * @throws NoSuchElementException
	 *             if I am a {@linkplain #isFailure() failure}
	 */
	public abstract T get() throws NoSuchElementException;

	/**
	 * Coerce me to an optional value, eliding any failure information.
	 *
	 * @return an optional encapsulating my value, which will {@linkplain Optional#isEmpty() be empty}
	 *         if either I am a failure or if I am a success that happens to be a {@code null} value
	 */
	public abstract Optional<T> toOptional();

	/**
	 * Queries whether the {@code Try} failed to provide value.
	 *
	 * @return whether I am a failure
	 */
	public final boolean isFailure() {
		return !isSuccess();
	}

	/**
	 * Query whether the {@code Try} provided a successful {@code null} value.
	 *
	 * @return whether I am an empty success
	 */
	public final boolean isEmpty() {
		return this.equals(EMPTY);
	}

	/**
	 * Query whether the {@code Try} provided a successful non-{@code null} value.
	 *
	 * @return whether I am a present success
	 */
	public final boolean isPresent() {
		return isSuccess() && !isEmpty();
	}

	/**
	 * Queries the reason for the failure to provide a value.
	 *
	 * @return the reason, which will never be {@code null} but may not be helpful
	 */
	public abstract String reason();

	/**
	 * Queries the exception, if any, that caused failure to provide a value.
	 *
	 * @return the exception, or {@code null} if the failure to provide a value was not
	 *         caused by an exception but just has some ad hoc {@link #reason() reason}
	 */
	public abstract Throwable failure();

	/**
	 * Encapsulate the result of a {@code callable}.
	 *
	 * @param <T>
	 *            the callable result type
	 * @param callable
	 *            a computation to call
	 * @return the result of {@linkplain Callable#call() calling} the {@code callable}
	 */
	public static <T> Try<T> call(Callable<? extends T> callable) {
		try {
			return Try.success(callable.call());
		} catch (Exception e) {
			return Try.failure(e);
		}
	}

	/**
	 * Create a success.
	 *
	 * @param <T>
	 *            the type of {@code value}
	 * @param value
	 *            the value to provide
	 * @return a successful provision of the {@code value}
	 */
	public static <T> Try<T> success(T value) {
		return new Success<>(value);
	}

	/**
	 * Obtain an empty success: that is, a successful result that happens
	 * to be a {@code null} value.
	 *
	 * @param <T>
	 *            the type of value expected
	 * @return the empty success
	 */
	@SuppressWarnings("unchecked")
	public static <T> Try<T> empty() {
		return (Try<T>) EMPTY;
	}

	/**
	 * Create a failure caused by an exception.
	 *
	 * @param <T>
	 *            the type of value that was not provided
	 * @param failure
	 *            an exception that caused the failure
	 * @return a failed provision of a value
	 */
	public static <T> Try<T> failure(Throwable failure) {
		return new Failure<>(failure);
	}

	/**
	 * Create a failure with an ad hoc reason.
	 *
	 * @param <T>
	 *            the type of value that was not provided
	 * @param reason
	 *            an ad hoc description of the reason why the value was not provided
	 * @return a failed provision of a value
	 */
	public static <T> Try<T> failure(String reason) {
		return new Failure<>(reason);
	}

	/**
	 * Process my value if {@linkplain #isSuccess() successful}.
	 *
	 * @param onSuccess
	 *            a consumer of the successful {@linkplain #get() value}
	 */
	public final void ifSuccess(Consumer<? super T> onSuccess) {
		if (isSuccess()) {
			onSuccess.accept(get());
		}
	}

	/**
	 * Process my value if {@linkplain #isPresent() present}.
	 *
	 * @param onPresent
	 *            a consumer of the present {@linkplain #get() value}
	 */
	public final void ifPresent(Consumer<? super T> onPresent) {
		if (isPresent()) {
			onPresent.accept(get());
		}
	}

	/**
	 * Process the reason for a {@linkplain #isFailure() failure}.
	 *
	 * @param onSuccess
	 *            a consumer of the failure reason and exception
	 */
	public final void ifFailure(BiConsumer<? super String, ? super Throwable> onFailure) {
		if (isFailure()) {
			onFailure.accept(reason(), failure());
		}
	}

	/**
	 * Process the reason for a {@linkplain #isFailure() failure}.
	 *
	 * @param onSuccess
	 *            a consumer of the failure reason
	 */
	public final void ifFailure(Consumer<? super String> onFailure) {
		ifFailure((reason, __) -> onFailure.accept(reason));
	}

	/**
	 * Obtain my {@linkplain #isSuccess() successful} {@linkplain #get() value} or
	 * else the given value {@link #isFailure() on failure}.
	 *
	 * @param failureValue
	 *            the result in case of failure
	 * @return the result
	 */
	public final T orElse(T failureValue) {
		return isSuccess() ? get() : failureValue;
	}

	/**
	 * Obtain my {@linkplain #isSuccess() successful} {@linkplain #get() value} or
	 * else compute an alternative based on the failure reason.
	 *
	 * @param failureFunction
	 *            the computation of an alternative in case of failure
	 * @return the result
	 */
	public final T orElseApply(BiFunction<? super String, ? super Throwable, ? extends T> failureFunction) {
		return isSuccess() ? get() : failureFunction.apply(reason(), failure());
	}

	/**
	 * Obtain my {@linkplain #isSuccess() successful} {@linkplain #get() value} or
	 * else compute an alternative based on the failure reason.
	 *
	 * @param failureFunction
	 *            the computation of an alternative in case of failure
	 * @return the result
	 */
	public final T orElseApply(Function<? super String, ? extends T> failureFunction) {
		return orElseApply((reason, __) -> failureFunction.apply(reason));
	}

	/**
	 * Obtain my {@linkplain #isSuccess() successful} {@linkplain #get() value} or
	 * else {@code null} with a side-effect based on the failure reason.
	 *
	 * @param onFailure
	 *            a side-effect to run in case of failure
	 * @return the result, or {@code null} if failed
	 */
	public final T orElseAccept(BiConsumer<? super String, ? super Throwable> onFailure) {
		if (isFailure()) {
			onFailure.accept(reason(), failure());
		}
		return orElse(null);
	}

	/**
	 * Obtain my {@linkplain #isSuccess() successful} {@linkplain #get() value} or
	 * else {@code null} with a side-effect based on the failure reason.
	 *
	 * @param onFailure
	 *            a side-effect to run in case of failure
	 * @return the result, or {@code null} if failed
	 */
	public final T orElseAccept(Consumer<? super String> onFailure) {
		return orElseAccept((reason, __) -> onFailure.accept(reason));
	}

	/**
	 * Map my value, if any, under the given function.
	 *
	 * @param <U>
	 *            the result type of the mapping
	 * @param mapping
	 *            the mapping function. It may return {@code null}
	 * @return a successful value of the {@code mapping} if I am a successful value, otherwise myself if I am a failure
	 */
	public abstract <U> Try<U> map(Function<? super T, U> mapping);

	/**
	 * Map my value, if any, under the given function.
	 *
	 * @param <U>
	 *            the result type of the optional mapping
	 * @param mapping
	 *            the mapping function. It must not return {@code null}
	 * @return a successful value of the {@code mapping} if I am a successful value, which may be empty if
	 *         either I am empty or the {@code mapping} result is empty, otherwise myself if I am a failure
	 */
	public abstract <U> Try<U> flatMap(Function<? super T, Optional<U>> mapping);

	/**
	 * Map my value, if any, under the given function.
	 *
	 * @param <U>
	 *            the result type of the attempted mapping
	 * @param mapping
	 *            the mapping function. It must not return {@code null}
	 * @return a successful value of the {@code mapping} if I am a successful value and it is also,
	 *         otherwise myself if I am a failure or the mapping failure if it is a failure
	 */
	public abstract <U> Try<U> tryMap(Function<? super T, Try<U>> mapping);

	/**
	 * Filter my value, if any, under the given {@code test}.
	 *
	 * @param test
	 *            the filter predicate function
	 * @return a successful value if I am a successful value, which may be empty if
	 *         either I am empty or the {@code test} fails, otherwise myself if I am a failure
	 */
	public abstract Try<T> filter(Predicate<? super T> test);

}

/**
 * The success case of a {@link Try}.
 *
 * @param <T>
 *            the value type
 */
final class Success<T> extends Try<T> {
	private static final long serialVersionUID = -6184897525442822648L;

	private final T value;

	Success(T value) {
		super();

		this.value = value;
	}

	@Override
	public Optional<T> toOptional() {
		return Optional.ofNullable(value);
	}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public T get() {
		return value;
	}

	@Override
	public String reason() {
		return null;
	}

	@Override
	public Throwable failure() {
		return null;
	}

	@Override
	public <U> Try<U> map(Function<? super T, U> mapping) {
		return isEmpty() ? empty() : success(mapping.apply(value));
	}

	@Override
	public <U> Try<U> flatMap(Function<? super T, Optional<U>> mapping) {
		return isEmpty() ? empty() : success(mapping.apply(value).orElse(null));
	}

	@Override
	public <U> Try<U> tryMap(Function<? super T, Try<U>> mapping) {
		return isEmpty() ? empty() : Objects.requireNonNull(mapping.apply(value));
	}

	@Override
	public Try<T> filter(Predicate<? super T> test) {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Success)) {
			return false;
		}

		Success<?> other = (Success<?>) obj;
		return Objects.equals(this.value, other.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Success.class, value);
	}

	@Override
	public String toString() {
		return isEmpty()
				? "Empty()" //$NON-NLS-1$
				: String.format("Success(%s)", value); //$NON-NLS-1$
	}
}

/**
 * The failure case of a {@link Try}.
 *
 * @param <T>
 *            the value type
 */
final class Failure<T> extends Try<T> {
	private static final long serialVersionUID = -4229519157128957990L;
	private static final String NO_REASON = "No reason provided."; //$NON-NLS-1$

	private final Throwable failure;
	private final String reason;

	Failure(String reason) {
		this(reason, null);
	}

	Failure(Throwable failure) {
		this(null, failure);
	}

	Failure(String reason, Throwable failure) {
		super();

		this.reason = (reason != null) ? reason : failure != null ? failure.getClass().getName() : NO_REASON;
		this.failure = failure;
	}

	@Override
	public Optional<T> toOptional() {
		return Optional.empty();
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public T get() throws NoSuchElementException {
		throw new NoSuchElementException();
	}

	@Override
	public String reason() {
		return reason;
	}

	@Override
	public Throwable failure() {
		return failure;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> Try<U> map(Function<? super T, U> mapping) {
		return (Try<U>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> Try<U> flatMap(Function<? super T, Optional<U>> mapping) {
		return (Try<U>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> Try<U> tryMap(Function<? super T, Try<U>> mapping) {
		return (Try<U>) this;
	}

	@Override
	public Try<T> filter(Predicate<? super T> test) {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Failure)) {
			return false;
		}

		Failure<?> other = (Failure<?>) obj;
		return Objects.equals(this.reason, other.reason)
				&& Objects.equals(this.failure, other.failure);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Failure.class, reason, failure);
	}

	@Override
	public String toString() {
		return (failure == null || Objects.equals(failure.getMessage(), reason))
				? String.format("Failure(%s)", reason) //$NON-NLS-1$
				: String.format("Failure(%s, %s: %s)", reason, failure.getClass().getSimpleName(), failure.getMessage()); //$NON-NLS-1$
	}

}
