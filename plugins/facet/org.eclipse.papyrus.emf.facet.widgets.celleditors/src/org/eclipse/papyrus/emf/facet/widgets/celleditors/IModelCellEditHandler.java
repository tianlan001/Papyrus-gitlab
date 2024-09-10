/*******************************************************************************
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
 *    Nicolas Bros (Mia-Software) - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.widgets.celleditors;

public interface IModelCellEditHandler {
	/**
	 * Commit the value currently in the cell editor. This will get the value
	 * through {@link IModelCellEditor#getValue()} and set it on the model
	 * element under the cell.
	 */
	void commit();
}
