/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas Guyomar (Mia-Software) - initial API and implementation
 *   Nicolas Bros (Mia-Software)
 *****************************************************************************/
package org.eclipse.papyrus.emf.facet.widgets.celleditors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

/** Implemented by {@link Composite}s that are encapsulated in {@link IModelCellEditor cell editors} */
public interface IWidget<T extends Object> {
	/** @return the validator used to determine whether an entered value is accepted */
	IValidator getValidator();

	/** Add a listener for changes to the value */
	void addCommitListener(Listener listener);

	/** Remove an existing listener */
	void removeCommitListener(Listener listener);

	/** Set the given value in the widget */
	void setValue(final T value);

	/** Get the value from the widget */
	T getValue();
}
