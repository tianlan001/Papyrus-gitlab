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

package org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.AbstractSashWindowContentProviderFacadeTest;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProviderFacade;


/**
 * @author dumoulin
 *
 */
public class SimpleSashWindowContentProviderFacadeTest2 extends AbstractSashWindowContentProviderFacadeTest {

	/**
	 * Constructor.
	 *
	 */
	public SimpleSashWindowContentProviderFacadeTest2() {
		super();
	}

	/**
	 * Return the concrete implementation under test.
	 * @return
	 */
	@Override
	public ISashWindowsContentProviderFacade createISashWindowsContentProviderFacade() {
		return new SimpleSashWindowContentProviderFacade();
	}

}
