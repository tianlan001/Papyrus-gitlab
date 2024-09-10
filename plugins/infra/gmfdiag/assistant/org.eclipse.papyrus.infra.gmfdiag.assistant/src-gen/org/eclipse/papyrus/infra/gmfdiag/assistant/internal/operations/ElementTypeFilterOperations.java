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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.util.ModelingAssistantUtil;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Element Type Filter</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getElementType() <em>Get Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter#getProvider() <em>Get Provider</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementTypeFilterOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ElementTypeFilterOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(ElementTypeFilter elementTypeFilter, Object input) {
		boolean result = false;

		IElementType type = elementTypeFilter.getElementType();
		if (type != null) {
			IElementType[] types = ModelingAssistantUtil.getElementTypes(elementTypeFilter.getProvider(), input);
			for (int i = 0; !result && (i < types.length); i++) {
				result = type.equals(types[i]) || ModelingAssistantUtil.isSubtype(types[i], type);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static IElementType getElementType(ElementTypeFilter elementTypeFilter) {
		IElementType result = null;

		String typeID = elementTypeFilter.getElementTypeID();
		if (typeID != null) {
			if (elementTypeFilter.getProvider() != null) {
				result = elementTypeFilter.getProvider().getElementType(typeID);
			}
			if (result == null) {
				result = ElementTypeRegistry.getInstance().getType(typeID);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static ModelingAssistantProvider getProvider(ElementTypeFilter elementTypeFilter) {
		ModelingAssistantProvider result = null;

		for (EObject container = elementTypeFilter.eContainer(); (container != null) && (result == null); container = container.eContainer()) {
			if (container instanceof ModelingAssistantProvider) {
				result = (ModelingAssistantProvider) container;
			}
		}

		return result;
	}
} // ElementTypeFilterOperations
