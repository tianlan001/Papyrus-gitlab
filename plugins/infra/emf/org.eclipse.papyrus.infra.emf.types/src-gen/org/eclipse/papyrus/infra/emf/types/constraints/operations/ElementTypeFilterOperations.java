/**
 * Copyright (c) 2021, 2023 Christian W. Damus, CEA LIST, and others.
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
 *   Vincent Lorenzo CEA LIST - vincent.lorenzo@cea.fr - Bug 582130
 */
package org.eclipse.papyrus.infra.emf.types.constraints.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.types.Activator;
import org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter;
import org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Element Type Filter</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter#matches(java.lang.Object) <em>Matches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementTypeFilterOperations {

	private static final ThreadLocal<List<IClientContext>> CLIENT_CONTEXT_STACK = ThreadLocal.withInitial(() -> new ArrayList<>(2));

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ElementTypeFilterOperations() {
		super();
	}

	static void inClientContext(final IClientContext context, final Runnable operation) {
		List<IClientContext> stack = null;

		if (context != null) {
			stack = CLIENT_CONTEXT_STACK.get();
			stack.add(context);
		}

		try {
			operation.run();
		} finally {
			if (stack != null) {
				stack.remove(stack.size() - 1);
			}
		}
	}

	@SuppressWarnings("unchecked")
	static <T> T inClientContext(final IClientContext context, final Supplier<T> computation) {
		Object[] result = { null };

		Runnable operation = () -> result[0] = computation.get();
		inClientContext(context, operation);

		return (T) result[0];
	}

	static IClientContext getClientContext() {
		List<IClientContext> stack = CLIENT_CONTEXT_STACK.get();
		return stack.isEmpty() ? null : stack.get(stack.size() - 1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(ElementTypeFilter elementTypeFilter, Object input) {
		IElementType type = getElementType(elementTypeFilter.getElementType());
		Collection<IElementType> inputTypes = getElementTypes(input);

		return (type != null) && !inputTypes.isEmpty() && anyMatches(inputTypes, elementTypeFilter.getRelationship(), type);
	}

	private static IElementType getElementType(Object object) {
		List<IElementType> result = getElementTypes(object);
		return result.isEmpty() ? null : result.get(0);
	}

	private static List<IElementType> getElementTypes(Object object) {
		List<IElementType> result;

		if (object instanceof IElementType) {
			result = List.of((IElementType) object);
		} else if (object instanceof ElementTypeConfiguration) {
			result = getElementTypes(ElementTypeRegistry.getInstance().getType(((ElementTypeConfiguration) object).getIdentifier()));
		} else if (object instanceof EObject) {
			EObject eObject = (EObject) object;

			IClientContext context = getClientContext();
			if (context == null) {
				// see bug 582130
				context = getContext(eObject);
			}

			if (context == null) {
				result = Arrays.asList(ElementTypeRegistry.getInstance().getAllTypesMatching(eObject));
			} else {
				result = Arrays.asList(ElementTypeRegistry.getInstance().getAllTypesMatching(eObject, context));
			}
		} else {
			result = List.of();
		}

		return result;
	}

	/**
	 *
	 * @param eObject
	 *            an eObject
	 * @return
	 *         the context associated to this EObject, or <code>null</code> when not found
	 */
	private static final IClientContext getContext(final EObject eObject) {
		try {
			return TypeContext.getContext(eObject);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return null;
	}


	private static boolean anyMatches(Collection<IElementType> actual, ElementTypeRelationshipKind relationship, IElementType expected) {
		// All relationship match kinds are satisfied by an exact match
		boolean result = actual.contains(expected);

		if (!result) {
			// Can we widen the match?
			switch (relationship) {
			case SPECIALIZATION_TYPE:
				Set<IElementType> specializations = Set.of(ElementTypeRegistry.getInstance().getSpecializationsOf(expected.getId()));
				result = actual.stream().anyMatch(specializations::contains);
				break;
			case SUBTYPE:
				result = actual.stream().anyMatch(
						type -> Arrays.asList(type.getAllSuperTypes()).contains(expected));
				break;
			case SUPERTYPE:
				Set<IElementType> superTypes = Set.of(expected.getAllSuperTypes());
				result = actual.stream().anyMatch(superTypes::contains);
			default:
				// Nope
				break;
			}
		}

		return result;
	}

} // ElementTypeFilterOperations
