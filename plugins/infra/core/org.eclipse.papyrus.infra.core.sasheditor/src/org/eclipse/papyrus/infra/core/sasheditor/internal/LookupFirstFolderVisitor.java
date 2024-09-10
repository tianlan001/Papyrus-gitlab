/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.internal;



/**
 * This visitor is used to lookup the first {@link TabFolderPart}.
 *
 * @author cedric dumoulin
 *
 */
public class LookupFirstFolderVisitor extends PartVisitor {

	private TabFolderPart result;

	public LookupFirstFolderVisitor() {
	}

	/**
	 * Get the result of the lookup.
	 *
	 * @return
	 */
	public TabFolderPart result() {
		return result;
	}

	/**
	 * Check if it is this Component
	 */
	@Override
	protected boolean acceptTabFolderPart(TabFolderPart part) {
		// We found it
		result = part;
		// stop looking
		return false;
	}

}
