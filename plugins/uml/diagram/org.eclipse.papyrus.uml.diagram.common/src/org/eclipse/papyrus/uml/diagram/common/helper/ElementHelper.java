/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 *
 * this is an abstract class for helper
 *
 */
public abstract class ElementHelper {

	protected TransactionalEditingDomain editDomain;

	/**
	 * Gets the editing domain.
	 *
	 * @return the editing domain
	 */
	protected TransactionalEditingDomain getEditingDomain() {
		return editDomain;
	}
}
