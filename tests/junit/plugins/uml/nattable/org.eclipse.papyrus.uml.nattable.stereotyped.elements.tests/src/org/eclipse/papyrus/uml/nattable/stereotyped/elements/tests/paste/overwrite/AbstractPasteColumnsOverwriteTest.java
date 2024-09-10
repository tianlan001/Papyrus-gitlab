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

package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.paste.overwrite;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.selection.command.ClearAllSelectionsCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectColumnCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;

/**
 * This class allows to manage the paste with overwrite tests.
 */
public abstract class AbstractPasteColumnsOverwriteTest extends AbstractPasteOverwriteTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.overwrite.AbstractPasteOverwriteTest#manageSelection(org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager)
	 */
	@Override
	public void manageSelection(final NattableModelManager manager) throws Exception {
		final NatTable natTable = (NatTable) manager.getAdapter(NatTable.class);
		natTable.doCommand(new ClearAllSelectionsCommand());
		manager.getBodyLayerStack().getSelectionLayer().doCommand(new SelectColumnCommand(manager.getBodyLayerStack().getSelectionLayer(), 0, 0, false, false));
	}
}
