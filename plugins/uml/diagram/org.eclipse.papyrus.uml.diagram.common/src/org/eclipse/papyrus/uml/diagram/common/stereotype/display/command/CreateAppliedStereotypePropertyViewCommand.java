/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotypes Display
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.stereotype.display.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;

/**
 * the goal of this command is to create a basic compartment in the notation that represent a compartment of stereotypes
 *
 */
public class CreateAppliedStereotypePropertyViewCommand extends RecordingCommand {

	protected View owner;

	protected Property property;

	protected EObject stereotypeApplication;

	protected Element element;

	protected String type;

	public CreateAppliedStereotypePropertyViewCommand(TransactionalEditingDomain domain, View owner, Property property, final String type) {

		super(domain, "CreateStereotypePropertyView");
		this.owner = owner;
		this.property = property;
		this.type = type;

	}

	@Override
	public void doExecute() {

		// Create Stereotype Property into Notation Structure

		// Create property Label
		DecorationNode propertyLabel = NotationFactory.eINSTANCE.createDecorationNode();
		propertyLabel.setType(type);
		propertyLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		propertyLabel.setElement(property);

		// Add the new Label to it's owner Object
		ViewUtil.insertChildView(owner, propertyLabel, ViewUtil.APPEND, StereotypeDisplayConstant.PERSISTENT);
		propertyLabel.setMutable(true);

	}
}
