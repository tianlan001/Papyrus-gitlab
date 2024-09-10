/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.properties.contexts.operations;

import java.util.Iterator;

import org.eclipse.papyrus.infra.properties.contexts.Annotatable;
import org.eclipse.papyrus.infra.properties.contexts.Annotation;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Annotatable</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.Annotatable#getAnnotation(java.lang.String) <em>Get Annotation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotatableOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotatableOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static Annotation getAnnotation(Annotatable annotatable, String source) {
		Annotation result = null;

		if (source != null) {
			for (Iterator<Annotation> iter = annotatable.getAnnotations().iterator(); result == null && iter.hasNext();) {
				Annotation next = iter.next();
				if (source.equals(next.getSource())) {
					result = next;
				}
			}
		}

		return result;
	}

} // AnnotatableOperations
