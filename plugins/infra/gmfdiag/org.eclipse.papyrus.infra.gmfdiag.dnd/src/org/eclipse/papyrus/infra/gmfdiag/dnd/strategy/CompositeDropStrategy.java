/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.dnd.strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Image;


public abstract class CompositeDropStrategy implements DropStrategy {

	protected final List<DropStrategy> strategies = new LinkedList<>();

	public Image getImage() {
		return null;
	}

	public void setOptions(Map<String, Object> options) {
		
	}

	public Command getCommand(Request request, EditPart targetEditPart) {
		Command command = new CompoundCommand();

		for (DropStrategy strategy : strategies) {
			command.chain(strategy.getCommand(request, targetEditPart));
		}

		return command;
	}

	public void addStrategy(DropStrategy strategy) {
		strategies.add(strategy);
	}

}
