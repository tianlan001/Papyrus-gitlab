/*****************************************************************************
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.junit.matchers;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.CombinableMatcher;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

/**
 * Some useful matchers that Hamcrest doesn't provide.
 */
public class MoreMatchers {

	private MoreMatchers() {
		super();
	}

	/**
	 * Obtain a matcher for numbers greater than a {@code minimum}.
	 *
	 * @param minimum
	 *            the lower bound (exclusive) to match against
	 * @return the matcher
	 */
	public static <N extends Number & Comparable<N>> Matcher<N> greaterThan(final N minimum) {
		return comparesAs(minimum, +1, false);
	}

	/**
	 * Obtain a matcher for numbers less than a {@code maximum}.
	 *
	 * @param maximum
	 *            the upper bound (exclusive) to match against
	 * @return the matcher
	 */
	public static <N extends Number & Comparable<N>> Matcher<N> lessThan(final N maximum) {
		return comparesAs(maximum, -1, false);
	}

	/**
	 * Obtain a matcher for numbers greater or equal to a {@code minimum}.
	 *
	 * @param minimum
	 *            the lower bound (inclusive) to match against
	 * @return the matcher
	 * @since 2.2
	 */
	public static <N extends Number & Comparable<N>> Matcher<N> greaterThanOrEqual(final N minimum) {
		return comparesAs(minimum, +1, true);
	}

	/**
	 * Obtain a matcher for numbers less or equal to a {@code maximum}.
	 *
	 * @param maximum
	 *            the upper bound (inclusive) to match against
	 * @return the matcher
	 * @since 2.2
	 */
	public static <N extends Number & Comparable<N>> Matcher<N> lessThanOrEqual(final N maximum) {
		return comparesAs(maximum, -1, true);
	}

	/**
	 * Obtain a matcher for comparables that matches comparisons yielding the given
	 * {@code sign}.
	 *
	 * @param compareTo
	 *            the value to compare with
	 * @param sign
	 *            the sign of the comparison result, either negative for less
	 *            than {@code compareTo}, positive for greater than {@code compareTo},
	 *            or zero for equal to {@code compareTo}
	 * @param orEqual
	 *            in the case of non-zero {@code sign}, whether to match equality also
	 *
	 * @return the matcher
	 *
	 * @since 2.2
	 */
	public static <C extends Comparable<C>> Matcher<C> comparesAs(final C compareTo, final int sign, final boolean orEqual) {
		final int normalizedSign = Integer.signum(sign);
		return new TypeSafeMatcher<>() {
			@Override
			public void describeTo(Description description) {
				switch (normalizedSign) {
				case -1:
					description.appendText(orEqual ? "≤ " : "< ");
					break;
				case +1:
					description.appendText(orEqual ? "≥ " : "> ");
					break;
				default:
					description.appendText("= ");
					break;
				}
				description.appendValue(compareTo);
			}

			@Override
			protected boolean matchesSafely(C item) {
				int comparison = item.compareTo(compareTo);
				switch (normalizedSign) {
				case -1:
					return orEqual ? comparison <= 0 : comparison < 0;
				case +1:
					return orEqual ? comparison >= 0 : comparison > 0;
				default:
					return comparison == 0;
				}
			}
		};
	}

	/**
	 * Match empty iterables of any kind.
	 *
	 * @see #emptyIterable()
	 */
	public static Matcher<Iterable<?>> isEmpty() {
		return new BaseMatcher<>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is empty");
			}

			@Override
			public boolean matches(Object item) {
				return Iterables.isEmpty((Iterable<?>) item);
			}
		};
	}

	/**
	 * The {@link CombinableMatcher}s of Hamcrest require matching generic signatures,
	 * for which the wildcard of the {@link #isEmpty()} matcher doesn't work very well,
	 * so this equivalent matcher may be used instead in those cases.
	 *
	 * @see #isEmpty()
	 */
	public static <E> Matcher<Iterable<E>> emptyIterable() {
		return new BaseMatcher<>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is empty");
			}

			@Override
			public boolean matches(Object item) {
				return Iterables.isEmpty((Iterable<?>) item);
			}
		};
	}

	public static Matcher<String> regexMatches(final String pattern) {
		return new BaseMatcher<>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("matches /").appendText(pattern).appendText("/");
			}

			@Override
			public boolean matches(Object item) {
				String string = (String) item;
				return !Strings.isNullOrEmpty(string) && string.matches(pattern);
			}
		};
	}

	public static Matcher<String> regexContains(final String pattern) {
		final Pattern regex = Pattern.compile(pattern);

		return new BaseMatcher<>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("contains /").appendText(pattern).appendText("/");
			}

			@Override
			public boolean matches(Object item) {
				String string = (String) item;
				return !Strings.isNullOrEmpty(string) && regex.matcher(string).find();
			}
		};
	}

	public static Matcher<IStatus> statusWithMessage(final Matcher<? super String> matcher) {
		return new BaseMatcher<>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("status with message ").appendDescriptionOf(matcher);
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;
				if (item instanceof IStatus) {
					IStatus status = (IStatus) item;
					result = matcher.matches(status.getMessage());
				}
				return result;
			}
		};
	}

	public static Matcher<IStatus> statusWithException(final Matcher<?> matcher) {
		return new BaseMatcher<>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("status with exception ").appendDescriptionOf(matcher);
			}

			@Override
			public boolean matches(Object item) {
				boolean result = false;
				if (item instanceof IStatus) {
					IStatus status = (IStatus) item;
					result = matcher.matches(status.getException());
				}
				return result;
			}
		};
	}

	public static Matcher<Diagnostic> diagnosticWithMessage(final Matcher<? super String> matcher) {
		return new FeatureMatcher<Diagnostic, String>(matcher, "diagnostic message", "message") {
			@Override
			protected String featureValueOf(Diagnostic actual) {
				return actual.getMessage();
			}
		};
	}

	/**
	 * Create a matcher for iterables to verify that some number of items match some criterion.
	 *
	 * @param <T>
	 *            the iterable element type
	 * @param <N>
	 *            the type of count of elements
	 * @param countMatcher
	 *            the matcher of the number of elements that must match
	 * @param elementMatcher
	 *            the matcher for elements
	 * @return a matcher for iterables that verifies a number of element matches
	 *
	 * @since 3.0
	 */
	public static <T, N extends Number> Matcher<Iterable<T>> hasCount(Matcher<N> countMatcher, Matcher<? super T> elementMatcher) {
		return new TypeSafeDiagnosingMatcher<>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("has ").appendDescriptionOf(countMatcher); //$NON-NLS-1$
				description.appendText(" items that ").appendDescriptionOf(elementMatcher); //$NON-NLS-1$
			}

			@Override
			protected boolean matchesSafely(Iterable<T> item, Description mismatchDescription) {
				int satisfiedCount = 0;

				for (T next : item) {
					if (elementMatcher.matches(next)) {
						satisfiedCount = satisfiedCount + 1;
					}
				}

				if (!countMatcher.matches(satisfiedCount)) {
					mismatchDescription.appendText("has "); //$NON-NLS-1$
					countMatcher.describeMismatch(satisfiedCount, mismatchDescription);
					mismatchDescription.appendText(" items that "); //$NON-NLS-1$
					mismatchDescription.appendDescriptionOf(elementMatcher);
				}

				return countMatcher.matches(satisfiedCount);
			}
		};
	}

	/**
	 * Create a matcher for iterables to verify that some maximum number of items match some criterion.
	 *
	 * @param <T>
	 *            the iterable element type
	 * @param max
	 *            the maximum number of elements of the iterable to match
	 * @param elementMatcher
	 *            the matcher for elements
	 * @return a matcher for iterables that verifies a maximum number of element matches
	 *
	 * @since 3.0
	 */
	public static <T> Matcher<Iterable<T>> hasAtMost(int max, Matcher<? super T> elementMatcher) {
		return hasCount(lessThanOrEqual(max), elementMatcher);
	}

	/**
	 * Create a matcher for iterables to verify that some minimum number of items match some criterion.
	 *
	 * @param <T>
	 *            the iterable element type
	 * @param min
	 *            the minimum number of elements of the iterable to match
	 * @param elementMatcher
	 *            the matcher for elements
	 * @return a matcher for iterables that verifies a minimum number of element matches
	 *
	 * @since 3.0
	 */
	public static <T> Matcher<Iterable<T>> hasAtLeast(int min, Matcher<? super T> elementMatcher) {
		return hasCount(greaterThanOrEqual(min), elementMatcher);
	}

	public static Matcher<EObject> eEqualTo(EObject eObject) {
		return new TypeSafeMatcher<>(EObject.class) {
			@Override
			public void describeTo(Description description) {
				description.appendText("is structurally equal to ").appendValue(eObject);
			}

			@Override
			protected boolean matchesSafely(EObject item) {
				return EcoreUtil.equals(item, eObject);
			}
		};
	}

}
