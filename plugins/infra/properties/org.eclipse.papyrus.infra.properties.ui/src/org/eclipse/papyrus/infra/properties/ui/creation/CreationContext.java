/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.properties.ui.creation;


/**
 * Encapsulation of the context within which the Properties view is creating a new element.
 * Primarily, this context comprises the model element in which a new element is being created (whatever that means for the particular data model).
 */
public interface CreationContext {

	/**
	 * A null implementation of the context. It does nothing and provides no context.
	 */
	CreationContext NULL = new CreationContext() {

		public void pushCreatedElement(Object newElement) {
			// Pass
		}

		public void popCreatedElement(Object newElement) {
			// Pass
		}

		public Object getCreationContextElement() {
			return null;
		}
	};

	/**
	 * Obtains the model element in the context of which we are creating new model elements.
	 *
	 * @return the contextual model element, or {@code null} if unknown
	 */
	Object getCreationContextElement();

	/**
	 * Attaches this context to an element being newly created, in such a way (as appropriate to the particular data model) that
	 * clients would be able to retrieve this context from that element.
	 *
	 * @param newElement
	 *            an element being created within the scope of my {@linkplain #getCreationContextElement() creation context}
	 */
	void pushCreatedElement(Object newElement);

	/**
	 * Detaches this context from an newly created element for which we have completed its editing.
	 *
	 * @param newElement
	 *            the newly created element
	 */
	void popCreatedElement(Object newElement);
}
