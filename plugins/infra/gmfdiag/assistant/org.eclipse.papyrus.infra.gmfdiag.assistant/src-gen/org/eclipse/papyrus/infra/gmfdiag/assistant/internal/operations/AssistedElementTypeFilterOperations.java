/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.operations;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.util.ModelingAssistantUtil;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Assisted Element Type Filter</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.AssistedElementTypeFilter#getProvider() <em>Get Provider</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssistedElementTypeFilterOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AssistedElementTypeFilterOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(AssistedElementTypeFilter assistedElementTypeFilter, Object input) {
		boolean result = false;

		ModelingAssistantProvider provider = assistedElementTypeFilter.getProvider();
		if (provider != null) {
			result = matchAnyElementType(provider, input);
		}

		return result;
	}

	protected static boolean matchAnyElementType(ModelingAssistantProvider provider, final Object object) {
		boolean result;

		if (object instanceof IElementType) {
			result = provider.getElementTypes().contains(object);
		} else if ((object instanceof IEditHelperContext) && (((IEditHelperContext) object).getElementType() != null)) {
			result = provider.getElementTypes().contains(((IEditHelperContext) object).getElementType());
		} else {
			Set<IElementType> types = ImmutableSet.copyOf(ModelingAssistantUtil.getElementTypes(provider, object));
			Set<IElementType> assisted = ImmutableSet.copyOf(provider.getElementTypes());

			result = !Sets.intersection(types, assisted).isEmpty();
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static ModelingAssistantProvider getProvider(AssistedElementTypeFilter assistedElementTypeFilter) {
		ModelingAssistantProvider result = null;

		for (EObject container = assistedElementTypeFilter.eContainer(); (container != null) && (result == null); container = container.eContainer()) {
			if (container instanceof ModelingAssistantProvider) {
				result = (ModelingAssistantProvider) container;
			}
		}

		return result;
	}

} // AssistedElementTypeFilterOperations
