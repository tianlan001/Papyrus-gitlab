/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.commands;

import org.eclipse.gef.commands.Command;
import org.eclipse.papyrus.uml.tools.utils.ValueSpecificationUtil;
import org.eclipse.uml2.uml.ValueSpecification;

public class SetValueSpecificationValueCommand extends Command {

	private ValueSpecification targetElement;

	private String newValue;

	private String oldValue;

	public SetValueSpecificationValueCommand(ValueSpecification valueSpec, String newString) {
		super("Set value");
		targetElement = valueSpec;
		newValue = newString;
		oldValue = null;
	}

	@Override
	public boolean canExecute() {
		return targetElement != null && newValue != null;
	}

	@Override
	public void execute() {
		ValueSpecificationUtil.restoreSpecificationValue(targetElement, newValue);
	}

	@Override
	public void undo() {
		ValueSpecificationUtil.restoreSpecificationValue(targetElement, oldValue);
	}

}
