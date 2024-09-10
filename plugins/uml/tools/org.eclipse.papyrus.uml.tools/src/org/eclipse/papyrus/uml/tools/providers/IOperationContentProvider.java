/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.providers;

import org.eclipse.jface.viewers.IStructuredContentProvider;

/**
 * StructuredContentProvider dedicated to Operations
 * 
 * @since 4.3
 */
public interface IOperationContentProvider extends IStructuredContentProvider {

	/**
	 * @param ignoreOperationsWithParameters
	 *            if <code>true</code> the content provider must not include the operation with parameters
	 */
	void setIgnoredOperationsWithParameters(final boolean ignoreOperationsWithParameters);

	/**
	 *
	 * @param ignoreVoidOperation
	 *            if <code>true</code> the concent provider must not include the operation returning void
	 */
	void setIgnoreVoidOperations(final boolean ignoreVoidOperation);

	/**
	 *
	 * @return
	 *         <code>true</code> if the content provider is ignoring operations with parameters
	 */
	boolean isIgnoringOperationsWithParameters();

	/**
	 *
	 * @return
	 *         <code>true</code> if the content provider is ignoring operations returning void
	 */
	boolean isIgnoringVoidOperations();
}
