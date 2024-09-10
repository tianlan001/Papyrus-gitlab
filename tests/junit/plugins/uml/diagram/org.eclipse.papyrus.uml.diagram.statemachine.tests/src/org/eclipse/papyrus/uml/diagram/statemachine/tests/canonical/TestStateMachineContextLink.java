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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.tests.canonical;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.statemachine.CreateStateMachineDiagramCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.statemachine.tests.IStateMachineDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.statemachine.tests.StateMachineUtil;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestContextLink;
import org.junit.Test;

/**
 * State machine context link tests
 *
 */
public class TestStateMachineContextLink extends TestContextLink {

	private IGraphicalEditPart myRegionCompartmentEditPart;

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateStateMachineDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IStateMachineDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IStateMachineDiagramTestsConstants.FILE_NAME;
	}

	@Test
	public void testToManageContextLink() {
		manageContextLink(UMLElementTypes.Constraint_Shape, UMLElementTypes.State_Shape, UMLElementTypes.Constraint_ContextEdge, UMLElementTypes.StateMachine_Shape);
	}

	@Override
	public void installEnvironment(IElementType sourceType, IElementType targetType) {
		rootSemanticOwnedElementsBeforeCreatingLink = 2;
		rootSemanticOwnedElementsAfterDestroy = rootSemanticOwnedElementsBeforeCreatingLink;
		super.installEnvironment(sourceType, targetType);
		rootSemanticOwnedElements = rootSemanticOwnedElementsBeforeCreatingLink;
	}

	@Override
	protected IGraphicalEditPart getRootEditPart() {
		if (myRegionCompartmentEditPart == null) {
			myRegionCompartmentEditPart = StateMachineUtil.getRegionCompartmentEditPart(getDiagramEditPart());
		}
		return myRegionCompartmentEditPart;
	}

	/**
	 * Gets the root view.
	 *
	 * @return the root view
	 */
	@Override
	protected View getRootView() {
		return getRootEditPart().getNotationView();
	}
}
