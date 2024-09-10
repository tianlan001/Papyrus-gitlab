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
 * A fake {@link TabFolderPart} for testing purpose.
 * Do not use in a SashContainer.
 * This class allows to instanciate a {@link TabFolderPart}.
 * 
 * @author cedric dumoulin
 *
 */
public class FakeTabFolderPart extends TabFolderPart {

	/**
	 * Create a fake {@link TabFolderPart}
	 * Constructor.
	 *
	 */
	public FakeTabFolderPart() {
		super( new RootPart(null), null, null);
	}
}
