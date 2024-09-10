/*****************************************************************************
 * Copyright (c) 2015, 2020 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.junit.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.rules.TestRule;
import org.junit.runner.Description;

import com.google.common.collect.Maps;

/**
 * Utilities for working with the JUnit data model and execution environment.
 */
public class JUnitUtils {

	/**
	 * Not instantiable by clients.
	 */
	private JUnitUtils() {
		super();
	}

	/**
	 * Obtains the test class implied by a {@code description} that is supplied to a {@link TestRule}.
	 *
	 * @param description
	 *            a rule's owning description, which generally would be a test method or a test class
	 *            (as these are the contexts in which rules are invoked)
	 *
	 * @return the test class, or {@code null} in the unlikely event that none can be found
	 */
	public static Class<?> getTestClass(Description description) {
		Class<?> result = description.getTestClass();

		if (result == null) {
			for (Description child : description.getChildren()) {
				result = getTestClass(child);
				if (result != null) {
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Obtains the annotations applied to a {@code description} that is supplied to a {@link TestRule}.
	 * If the description is for a test method, the annotations of its owning class are included,
	 * excepting annotations of the same type applied to the method.
	 *
	 * @param description
	 *            a rule's owning description, which generally would be a test method or a test class
	 *            (as these are the contexts in which rules are invoked)
	 *
	 * @return all of the annotations applied to the test description
	 */
	public static Iterable<Annotation> getAnnotations(Description description) {
		Map<Class<? extends Annotation>, Annotation> result = Maps.newLinkedHashMap();

		for (Annotation next : description.getAnnotations()) {
			result.put(next.annotationType(), next);
		}

		if (description.getTestClass() != null) {
			for (Annotation next : description.getTestClass().getAnnotations()) {
				if (!result.containsKey(next.annotationType())) {
					result.put(next.annotationType(), next);
				}
			}
		}

		return result.values();
	}

	/**
	 * Obtains the annotation of the specified {@code type} applicable to a {@code description} that is supplied to a {@link TestRule}.
	 * If the description is for a test method, then if that method doesn't have the requested annotation, its owning class is searched
	 * for the annotation.
	 *
	 * @param description
	 *            a rule's owning description, which generally would be a test method or a test class
	 *            (as these are the contexts in which rules are invoked)
	 * @param type
	 *            the annotation type to look for
	 *
	 * @return the requested annotation, or {@code null} if none was found
	 */
	public static <A extends Annotation> A getAnnotation(Description description, Class<A> type) {
		A result = description.getAnnotation(type);

		if ((result == null) && (description.getTestClass() != null)) {
			result = description.getTestClass().getAnnotation(type);
		}

		return result;
	}

	/**
	 * Obtains all of the repetitions of an annotation of the specified {@code type} applicable to a {@code description} that is supplied to a {@link TestRule}.
	 * If the description is for a test method, then the annotations on the test class (if any) are added to the method's annotations. This API
	 * may not be used to get the annotations on a test method and its class for non-repeatable annotations: for these, test method annotations
	 * strictly supersede test class annotations.
	 *
	 * @param description
	 *            a rule's owning description, which generally would be a test method or a test class
	 *            (as these are the contexts in which rules are invoked)
	 * @param type
	 *            the annotation type to look for
	 *
	 * @return the requested annotations, or an empty list if none were found
	 *
	 * @throws IllegalArgumentException
	 *             if the annotation {@code type} is not {@linkplain Repeatable repeatable}
	 *
	 * @since 3.0
	 *
	 * @see AnnotatedElement#getAnnotationsByType(Class)
	 */
	public static <A extends Annotation> List<A> getAnnotationsByType(Description description, Class<A> type) {
		if (!type.isAnnotationPresent(Repeatable.class)) {
			throw new IllegalArgumentException("not a repeatable annotation: @" + type.getName());
		}

		List<A> result = new ArrayList<>();

		Class<? extends Annotation> containerType = type.getAnnotation(Repeatable.class).value();
		Annotation container = description.getAnnotation(containerType);
		if (container != null) {
			try {
				Method method = findAnnotationContainerValue(containerType, type);
				@SuppressWarnings("unchecked") // We did check in the finder method
				A[] annotations = (A[]) method.invoke(container);
				result.addAll(Arrays.asList(annotations));
			} catch (Exception e) {
				// OK, so no annotations from this description, then
			}
		} else {
			// Maybe the annotation is present, but only once
			A annotation = description.getAnnotation(type);
			if (annotation != null) {
				result.add(annotation);
			}
		}

		if (description.getTestClass() != null) {
			result.addAll(Arrays.asList(description.getTestClass().getAnnotationsByType(type)));
		}

		return result;
	}

	private static Method findAnnotationContainerValue(Class<? extends Annotation> containerType, Class<? extends Annotation> repeatableAnnotationType) {
		Method result = null;

		for (Method next : containerType.getMethods()) {
			if (next.getParameterCount() == 0 && next.getReturnType().isArray() && next.getReturnType().getComponentType() == repeatableAnnotationType) {
				result = next;
				break;
			}
		}

		return result;
	}

	/**
	 * Obtains the annotation of any one of the specified {@code types} applicable to a {@code description} that is supplied to a {@link TestRule}.
	 * If the description is for a test method, then if that method doesn't have any of the requested annotations, its owning class is searched
	 * for the annotations.
	 *
	 * @param description
	 *            a rule's owning description, which generally would be a test method or a test class
	 *            (as these are the contexts in which rules are invoked)
	 * @param types
	 *            the annotation types to look for
	 *
	 * @return the first available of the requested annotations, or {@code null} if none was found
	 */
	@SafeVarargs
	public static Annotation getAnyAnnotation(Description description, final Class<? extends Annotation>... types) {
		Annotation result = null;

		for (Class<? extends Annotation> next : types) {
			result = description.getAnnotation(next);
			if (result != null) {
				break;
			}
		}

		if (result == null) {
			out: for (Class<?> testClass = description.getTestClass(); testClass != null; testClass = testClass.getSuperclass()) {
				for (Class<? extends Annotation> next : types) {
					result = testClass.getAnnotation(next);
					if (result != null) {
						break out;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Queries whether the current JUnit test execution is running in the automated build environment
	 * (whether actually on the build server or not; users can run local builds on their development systems, too).
	 *
	 * @return whether the tests are running in the automated build environment
	 */
	public static boolean isAutomatedBuildExecution() {
		return Activator.getDefault().getRunningApplicationID().startsWith("org.eclipse.tycho."); //$NON-NLS-1$
	}
}
