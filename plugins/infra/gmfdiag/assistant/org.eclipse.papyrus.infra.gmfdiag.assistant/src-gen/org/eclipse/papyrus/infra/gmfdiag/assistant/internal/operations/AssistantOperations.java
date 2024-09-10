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

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Assistant</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant#getElementType() <em>Get Element Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssistantOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AssistantOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static IElementType getElementType(Assistant assistant) {
		IElementType result = null;

		String typeID = assistant.getElementTypeID();
		if (typeID != null) {
			if (assistant.getProvider() != null) {
				result = assistant.getProvider().getElementType(typeID);
			}
			if (result == null) {
				result = ElementTypeRegistry.getInstance().getType(typeID);
			}
		}

		return result;
	}

} // AssistantOperations
