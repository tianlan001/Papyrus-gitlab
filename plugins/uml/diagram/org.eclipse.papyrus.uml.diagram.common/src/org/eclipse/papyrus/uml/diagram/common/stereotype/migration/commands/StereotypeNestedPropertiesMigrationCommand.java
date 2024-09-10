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
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.StereotypeLocationEnum;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;

/**
 * Command in charge of applying user preferences of stereotype display from the old EANnotation structure to the new one.
 * For the Properties EAnnotation details.
 *
 * @author Céline JANSSENS
 *
 */
public class StereotypeNestedPropertiesMigrationCommand extends StereotypePropertiesMigrationCommand {

	/**
	 * Constructor.
	 *
	 * @param label
	 *            Command Label
	 * @param content
	 *            View on which the Stereotype is applied.
	 */
	public StereotypeNestedPropertiesMigrationCommand(final String label, final View content) {
		super(label, content);

	}


	/**
	 *
	 * In this case, the Old structure return "Compartment" as in the new one the properties are stored in the "Brace" location.
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypePropertiesMigrationCommand#getLocation(java.lang.String)
	 *
	 */
	@Override
	public Enum getLocation(final String oldProperties) {
		Enum location = StereotypeLocationEnum.IN_BRACE;
		if (oldProperties.equals(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_LOCATION)) {
			location = StereotypeLocationEnum.IN_BRACE;
		} else if (oldProperties.equals(StereotypeDisplayConstant.STEREOTYPE_BRACE_LOCATION)) {
			location = StereotypeLocationEnum.IN_BRACE;
		}
		return location;
	}
}
