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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.overwrite;

import org.eclipse.nebula.widgets.nattable.selection.command.SelectColumnCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;

/**
 * Test pastes overwrite all of selection with visible categories.
 */
public class PasteColumnsOverwriteAll_V1_V3_V1_MultiColumns_Test extends AbstractPasteColumnsOverwriteTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.overwrite.AbstractPasteOverwriteTest#removeClassName(java.lang.String)
	 */
	@Override
	public String removeClassName(final String className) throws Exception {
		return className.replaceFirst("PasteColumnsOverwriteAll_", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.overwrite.AbstractPasteOverwriteTest#manageSelection(org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager)
	 */
	@Override
	public void manageSelection(final NattableModelManager manager) throws Exception {
		super.manageSelection(manager);
		manager.getBodyLayerStack().getSelectionLayer().doCommand(new SelectColumnCommand(manager.getBodyLayerStack().getSelectionLayer(), 3, 0, true, false));
	}
}
