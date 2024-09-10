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
package org.eclipse.papyrus.infra.properties.ui.modelelement;

import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;


/**
 * This is the AbstractModelElementFactory type. Enjoy.
 */
public abstract class AbstractModelElementFactory<T extends AbstractModelElement> implements ModelElementFactory {

	protected AbstractModelElementFactory() {
		super();
	}

	public final ModelElement createFromSource(Object sourceElement, DataContextElement context) {
		AbstractModelElement result = doCreateFromSource(sourceElement, context);

		if (result != null) {
			result.setFactory(this);
		}

		return result;
	}

	protected abstract T doCreateFromSource(Object sourceElement, DataContextElement context);

	protected abstract void updateModelElement(T modelElement, Object newSourceElement);
}
