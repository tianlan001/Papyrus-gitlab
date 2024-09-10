/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.constraints.constraints;

/**
 * Represents a Java Query
 */
public interface JavaQuery {

	/**
	 * Tests whether the query matches a given object
	 *
	 * @param selection
	 * @return
	 */
	public boolean match(Object selection);

	/**
	 * A Java query which is always false
	 *
	 * @author Camille Letavernier
	 *
	 */
	public class FalseQuery implements JavaQuery {

		public boolean match(Object selection) {
			return false;
		}
	}
}
