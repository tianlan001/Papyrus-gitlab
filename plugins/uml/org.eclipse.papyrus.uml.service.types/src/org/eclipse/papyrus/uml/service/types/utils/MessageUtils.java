/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Message;

/**
 * Utility class for {@link Message}.
 * 
 * @since 3.0
 *
 */
public class MessageUtils {
	@SuppressWarnings("unchecked")
	public static <T> T getContaining(final EObject element, final Class<T> clazz) {
		EObject container = element;
		while (container != null) {
			if (clazz.isInstance(container)) {
				return (T) container;
			}
			container = container.eContainer();
		}
		return null;
	}


}
