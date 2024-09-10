/**
 * Copyright (c) 2014, 2017 CEA LIST and Others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 528502
 *
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.OrphanViewPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomRegionDeleteCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;

/**
 * this policy is used to supress orphan node view in GMF view the prolicy to
 * remove orphan connection is more complex. It is dependent of the diagram. see
 * remove OrphanConnectionView policy in clazzdiagram.
 *
 * @deprecated since 3.1. Useless. Helper Advices remove views.
 *
 */
@Deprecated
public class RemoveOrphanViewPolicy extends OrphanViewPolicy {

	public String[] notOrphanNode = {
			RegionCompartmentEditPart.VISUAL_ID,
			StateMachineCompartmentEditPart.VISUAL_ID,
			StateCompartmentEditPart.VISUAL_ID
	};

	public RemoveOrphanViewPolicy() {
		super();
		init(notOrphanNode);
	}

	@Override
	protected Command getDeleteViewCommand(View view) {
		if (Zone.isRegion(view)) {
			TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
			return new ICommandProxy(new CustomRegionDeleteCommand(editingDomain, view));
		}
		return super.getDeleteViewCommand(view);
	}
}
