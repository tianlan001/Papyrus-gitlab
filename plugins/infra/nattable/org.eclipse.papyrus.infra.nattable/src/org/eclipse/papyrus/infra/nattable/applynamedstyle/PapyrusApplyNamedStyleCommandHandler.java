/*******************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 515737
 ******************************************************************************/
package org.eclipse.papyrus.infra.nattable.applynamedstyle;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StyledElement;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;

/**
 * Class to handle the {@link PapyrusApplyNamedStyleCommand}.
 * The named style will be applied or unapplied to the selected element in the Papyrus NatTable.
 * 
 * @since 5.0
 */
public class PapyrusApplyNamedStyleCommandHandler extends AbstractLayerCommandHandler<PapyrusApplyNamedStyleCommand> {

	/**
	 * Default constructor.
	 */
	public PapyrusApplyNamedStyleCommandHandler() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean doCommand(final PapyrusApplyNamedStyleCommand command) {

		final StyledElement element = command.getElement();
		final String namedStyle = command.getNamedStyle();
		final TransactionalEditingDomain editingDomain = command.getEditingDomain();

		// Get the named style from the selected element
		final BooleanValueStyle booleanValue = (BooleanValueStyle) element.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), namedStyle);

		// If the named style value exists, it must be true, just delete it,
		if (null != booleanValue) {
			// Remove it from the selected axis
			StyleUtils.deleteBooleanNamedStyle(editingDomain, element, namedStyle);
		} else {
			// Otherwise, currently it is false, initialise the boolean named value in the enable mode
			StyleUtils.initBooleanNamedStyle(editingDomain, element, namedStyle, true);
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<PapyrusApplyNamedStyleCommand> getCommandClass() {
		return PapyrusApplyNamedStyleCommand.class;
	}

}
