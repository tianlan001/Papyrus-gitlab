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

package org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite;

import java.util.Map;

import org.eclipse.papyrus.infra.nattable.handler.InsertInTableHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.UserActionConstants;

/**
 * Test pastes overwrite all of selection with hidden categories.
 */
public class PasteEmptyOverwriteSkipAll_Test extends AbstractPasteEmptyOverwriteTest {

	/**
	 * Constructor.
	 */
	public PasteEmptyOverwriteSkipAll_Test() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteOverwriteTest#manageParameters(java.util.Map)
	 */
	@Override
	public void manageParameters(Map<Object, Object> parameters) {
		super.manageParameters(parameters);
		parameters.put(InsertInTableHandler.USER_ACTION__PREFERRED_USER_ACTION, UserActionConstants.SKIP_USER_ACTION);
	}
	
	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.paste.overwrite.AbstractPasteInsertTest#testUndo_Redo(org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager)
	 *
	 * @param treeManager
	 * @throws Exception
	 */
	@Override
	protected void testUndo_Redo(NattableModelManager treeManager) throws Exception {
		// Do nothing
	}
}
