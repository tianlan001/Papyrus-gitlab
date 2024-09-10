/*******************************************************************************
 * Copyright (c) 2010, 2012 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Nicolas Bros (Mia-Software)
 *     Gregoire Dupe (Mia-Software) - Bug 364325 - [Restructuring] The user must be able to navigate into a model using the Facet.
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.efacet.ui.internal.view;

import org.eclipse.emf.ecore.EObject;

/** An interface that supports editing the query context */
// Copied from org.eclipse.papyrus.emf.facet.infra.query.ui.views.queryExecution.internal.EditableContext
public interface EditableContext {
	void add(EObject eObject);

	void remove(EObject eObject);

	void clear();

	/** must be called after editing to refresh the viewers */
	void done();
}
