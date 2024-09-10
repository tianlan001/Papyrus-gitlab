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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.stereotype.display.command;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.uml2.uml.Stereotype;

/**
 * the goal of this command is to create a basic compartment in the notation that represent a compartment of stereotypes
 *
 */
public class CreateAppliedStereotypeCompartmentCommand extends RecordingCommand {

	protected StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	protected View node;

	protected Stereotype stereotype;

	protected Node parent;

	protected String type;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param node
	 *            The EditPart view of the Compartment
	 * @param StereotypeApplication
	 * @param displayit
	 */
	public CreateAppliedStereotypeCompartmentCommand(TransactionalEditingDomain domain, View node, Stereotype stereotype, String type) {
		super(domain, "CreateStereotypeCompartment");
		this.node = node;
		this.stereotype = stereotype;
		this.type = type;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doExecute() {

		// Create the Graphical Compartment
		Node compartment = NotationFactory.eINSTANCE.createBasicCompartment();
		compartment.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());

		// Create Title Style
		TitleStyle ts = NotationFactory.eINSTANCE.createTitleStyle();
		compartment.getStyles().add(ts);

		// Create Stereotype Name Style
		StringValueStyle stereotypeNameStyle = NotationFactory.eINSTANCE.createStringValueStyle();
		stereotypeNameStyle.setName(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_NAME);
		stereotypeNameStyle.setStringValue(stereotype.getQualifiedName());
		compartment.getStyles().add(stereotypeNameStyle);

		// Complete the creation
		compartment.setElement(stereotype);
		compartment.setType(type);
		ViewUtil.insertChildView(node, compartment, ViewUtil.APPEND, StereotypeDisplayConstant.PERSISTENT);
		compartment.setMutable(true);

	}



}
