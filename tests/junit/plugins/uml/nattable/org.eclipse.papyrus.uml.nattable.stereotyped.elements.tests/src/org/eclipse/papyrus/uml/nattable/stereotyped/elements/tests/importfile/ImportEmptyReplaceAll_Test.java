/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.importfile;

import java.util.Map;

import org.eclipse.papyrus.infra.nattable.handler.ImportTableHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.UserActionConstants;

/**
 * Test import all of selection with hidden categories.
 */
public class ImportEmptyReplaceAll_Test extends AbstractImportEmptyTest {

	/**
	 * Constructor.
	 */
	public ImportEmptyReplaceAll_Test() {
		super();
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.insert.AbstractInsertTest#manageParameters(java.util.Map)
	 *
	 * @param parameters
	 */
	@Override
	public void manageParameters(final Map<Object, Object> parameters, final INattableModelManager manager) {
		super.manageParameters(parameters, manager);
		parameters.put(ImportTableHandler.USER_ACTION__PREFERRED_USER_ACTION, UserActionConstants.REPLACE_USER_ACTION);
	}
}
