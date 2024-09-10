/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.selection;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * @author Vincent Lorenzo
 *         This interface is used to obtain real object provided by a structured selection.
 *         It allows to remove emf dependencies during the manipulation of the contents of the table
 */
public interface ISelectionExtractor {

	/**
	 * 
	 * @param structuredSelection
	 *            a structured selection
	 * @return
	 *         a collection representing the object encapsulated in the selection
	 */
	public Collection<?> extractSelectedObjects(IStructuredSelection structuredSelection);

}
