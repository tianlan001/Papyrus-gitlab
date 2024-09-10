/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.transformation;

import org.eclipse.core.runtime.IStatus;

/**
 * @author Vincent Lorenzo
 *
 */
public interface IImportTransformation {

	/**
	 * @return
	 */
	long getLoadingTime();

	/**
	 * @return
	 */
	long getHandleDanglingRefTime();

	/**
	 * @return
	 */
	long getImportExtensionsTime();

	/**
	 * @return
	 */
	long getExecutionTime();

	/**
	 * @return
	 */
	String getModelName();

	/**
	 * @return
	 */
	IStatus getStatus();

	/**
	 * @return
	 */
	boolean isComplete();

	/**
	 * @param b
	 */
	void run(boolean b);

	/**
	 * 
	 */
	void cancel();

}