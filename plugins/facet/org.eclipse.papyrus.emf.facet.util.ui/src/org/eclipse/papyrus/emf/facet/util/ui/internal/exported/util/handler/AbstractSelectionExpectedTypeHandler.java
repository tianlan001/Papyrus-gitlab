/**
 * Copyright (c) 2012 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  	Alban Ménager (Soft-Maint) - Bug 387470 - [EFacet][Custom] Editors
 *  	Grégoire Dupé (Soft-Maint) - Bug 387470 - [EFacet][Custom] Editors
 */
package org.eclipse.papyrus.emf.facet.util.ui.internal.exported.util.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.papyrus.emf.facet.util.ui.internal.exported.handler.HandlerUtils;

/**
 * Abstract handler. All handlers must extends this class.
 *
 * @see IFunctionDialogFactory
 *
 * @see CreateFacetInFacetSetHandler
 * @see CreateFacetSetInFacetSetHandler
 * @see AddAttributeInFacetHandler
 * @see AddOperationInFacetHandler
 * @see AddReferenceInFacetHandler
 * @see AddParameterInOperationHandler
 * @since 0.3
 */
public abstract class AbstractSelectionExpectedTypeHandler extends AbstractHandler {

	@Override
	public boolean isEnabled() {
		boolean result = false;
		if ((HandlerUtils.getStructuredSelection() != null && HandlerUtils
				.getStructuredSelection().size() == 1)
				&& getSelectionExpectedType().isInstance(
						HandlerUtils.getSelection())) {
			result = true;
		}
		return result;
	}

	/**
	 * Return the expected type of the selection.
	 *
	 * @return the type expected.
	 */
	protected abstract Class<?> getSelectionExpectedType();
}
