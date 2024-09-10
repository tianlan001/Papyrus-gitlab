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

package org.eclipse.papyrus.infra.gmfdiag.assistant.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.util.IProxyElementType;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.google.common.base.Objects;

/**
 * Utilities for working with element types.
 */
public class ElementTypesUtil {

	/**
	 * Not instantiable by clients.
	 */
	public ElementTypesUtil() {
		super();
	}

	/**
	 * Obtains the canonical (registered) representation of an element {@code type}. This ensures that
	 * we have the latest version of the {@code type}, in case it has been unregistered and recreated.
	 * 
	 * @param type
	 *            an element type
	 * @return its current canonical incarnation, which may be the same instance as {@code type} in the ideal case
	 */
	public static <T extends IElementType> T canonicalize(T type) {
		@SuppressWarnings("unchecked")
		T result = (T) ElementTypeRegistry.getInstance().getType(type.getId());
		return result;
	}

	public static IElementType requireType(String id) {
		IElementType result = ElementTypeRegistry.getInstance().getType(id);
		assertThat("No such element type: " + id, result, notNullValue());
		return result;
	}

	public static Matcher<IElementType> hasID(final String id) {
		return new BaseMatcher<IElementType>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is identified by ").appendText(id);
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof IElementType) && Objects.equal(((IElementType) item).getId(), id);
			}
		};
	}

	public static Matcher<IElementType> specializes(final String id) {
		return new BaseMatcher<IElementType>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("specializes ").appendText(id);
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof ISpecializationType)
						&& hasItem(hasID(id)).matches(Arrays.asList(((ISpecializationType) item).getAllSuperTypes()));
			}
		};
	}

	public static Matcher<IElementType> isProxyType(final Matcher<? super IElementType> semanticTypeMatcher, final Matcher<? super IElementType> visualTypeMatcher) {
		return new BaseMatcher<IElementType>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is proxy type(");
				if (semanticTypeMatcher == null) {
					description.appendText("?");
				} else {
					description.appendDescriptionOf(semanticTypeMatcher);
				}
				description.appendText(", ");
				if (visualTypeMatcher == null) {
					description.appendText("?");
				} else {
					description.appendDescriptionOf(visualTypeMatcher);
				}
				description.appendText(")");
			}

			@Override
			public boolean matches(Object item) {
				boolean result = (item instanceof IProxyElementType);

				if (result) {
					IProxyElementType proxy = (IProxyElementType) item;
					result = ((semanticTypeMatcher == null) || semanticTypeMatcher.matches(proxy.resolveSemanticType())) &&
							((visualTypeMatcher == null) || visualTypeMatcher.matches(proxy.resolveVisualType()));
				}

				return result;
			}
		};
	}
}
