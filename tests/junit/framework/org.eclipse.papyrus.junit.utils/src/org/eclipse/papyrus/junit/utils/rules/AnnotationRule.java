/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.junit.utils.rules;

import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.common.base.Defaults;
import com.google.common.base.Supplier;

/**
 * A rule that finds annotation metadata of a test. If the value of the annotation is a
 * class, then the rule instantiates that class in order to provide a value.
 */
public class AnnotationRule<T> implements TestRule, Supplier<T> {
	private final Class<? extends Annotation> annotationType;
	private final Method valueAccessor;

	private T value;

	private AnnotationRule(Class<? extends Annotation> annotationType, Method accessor, T default_) {
		super();

		this.annotationType = annotationType;
		this.valueAccessor = accessor;

		this.value = default_;
	}

	/**
	 * Creates a new rule that extracts the value of the specified annotation type from
	 * a test case.
	 * 
	 * @param annotationType
	 *            the annotation type from which to extract the value
	 * @param default_
	 *            the value to return in the case that the annotation is absent. If null
	 *            and the annotation value is of a primitive type, the appropriate primitive default is substituted
	 * 
	 * @return the rule
	 */
	@SuppressWarnings("unchecked")
	public static <T> AnnotationRule<T> create(Class<? extends Annotation> annotationType, T default_) {
		try {
			Method method = annotationType.getDeclaredMethod("value");

			Class<?> resultType = method.getReturnType();

			if ((default_ == null) && (resultType != Class.class)) {
				default_ = (T) Defaults.defaultValue(resultType);
			}

			return new AnnotationRule<T>(annotationType, method, default_);
		} catch (Exception e) {
			fail("Cannot get annotation value: " + e.getMessage());
			throw new Error();// unreachable
		}
	}

	/**
	 * Creates a new rule that extracts the value of the specified annotation type from
	 * a test case. This rule does not have a default: if the annotation is absent, it
	 * returns {@code null}.
	 * 
	 * @param annotationType
	 *            the annotation type from which to extract the value
	 * @return the rule
	 */
	public static <T> AnnotationRule<T> create(Class<? extends Annotation> annotationType) {
		return create(annotationType, null);
	}

	/**
	 * Creates a new rule that just determines whether the specified annotation is applied to
	 * a test case.
	 * 
	 * @param annotationType
	 *            the annotation type to detect
	 * 
	 * @return the rule
	 */
	public static AnnotationRule<Boolean> createExists(Class<? extends Annotation> annotationType) {
		return new AnnotationRule<Boolean>(annotationType, null, false);
	}

	@Override
	public final T get() {
		return value;
	}

	@Override
	public Statement apply(final Statement base, Description description) {
		Statement result = base;
		final Annotation annotation = JUnitUtils.getAnnotation(description, annotationType);

		if (annotation != null) {
			result = new Statement() {

				@SuppressWarnings("unchecked")
				@Override
				public void evaluate() throws Throwable {
					try {
						// If we have no accessor method, we're just checking existence of the annotation,
						// which is "extracted" as a boolean
						if (valueAccessor == null) {
							value = (T) Boolean.TRUE;
						} else {
							Object extracted = valueAccessor.invoke(annotation);
							if (extracted instanceof Class<?>) {
								// Instantiate the class
								extracted = ((Class<?>) extracted).newInstance();
							}
							value = (T) extracted;
						}
					} catch (Exception e) {
						fail("Cannot get annotation value: " + e.getMessage());
					}

					try {
						base.evaluate();
					} finally {
						if (valueAccessor != null) {
							// Clean up. Note that if the annotation value is a class, the
							// default is null anyways, so the cast doesn't matter
							value = (T) Defaults.defaultValue(valueAccessor.getReturnType());
						}
					}
				}
			};
		}

		return result;
	}
}
