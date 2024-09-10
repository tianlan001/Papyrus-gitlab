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

package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.OpaqueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * It is a FailingTest now. 
 */
public class TestActionConstraint extends AbstractPapyrusTestCase {

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}
	
	@FailingTest("Create constraint command is throwing exception")
	@Test
	public void testLocalPreCondConstraintInAction() {
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		
		IElementType constraintAsPrecondType = UMLElementTypes.Constraint_LocalPreconditionShape;
		
		Command command = opaqueEP.getCommand(constraintRequest(constraintAsPrecondType));
		Assert.assertNotNull(command);
		Assert.assertTrue(command.canExecute());
		checkGraphical(opaqueEP, getActivityCompartmentEditPart(), command);
		
		EReference containmentFeatureForConstraint = findContainmentFeatureByType(constraintAsPrecondType);
		
		checkSemantic(opaqueEP, command, containmentFeatureForConstraint);
	}
	
	public AspectUnspecifiedTypeCreationTool.CreateAspectUnspecifiedTypeRequest constraintRequest(IElementType constraintType) {
		List<IElementType> elementTypes = new LinkedList<IElementType>();
		elementTypes.add(constraintType);
		AspectUnspecifiedTypeCreationTool.CreateAspectUnspecifiedTypeRequest req = new AspectUnspecifiedTypeCreationTool(elementTypes).new CreateAspectUnspecifiedTypeRequest(elementTypes, getDiagramEditPart().getDiagramPreferencesHint());
		return req;
	}
	
	public void checkGraphical(IGraphicalEditPart actionEP, IGraphicalEditPart actionGraphicalParent, Command command) {
		int childrenBefore = actionGraphicalParent.getChildren().size();
		actionGraphicalParent.getEditingDomain().getCommandStack().flush();
		executeOnUIThread(command);
		Assert.assertEquals(actionGraphicalParent.getChildren().size(), childrenBefore + 1);
		command.undo();
		Assert.assertEquals(actionGraphicalParent.getChildren().size(), childrenBefore);
		command.redo();
		Assert.assertEquals(actionGraphicalParent.getChildren().size(), childrenBefore + 1);
		command.undo();
	}
	
	public void checkSemantic(IGraphicalEditPart actionEP, Command command, EReference expectedFeature) {
		EObject actionEObject = actionEP.resolveSemanticElement();
		
		Assert.assertNotNull(actionEObject);
		Assert.assertTrue(actionEObject instanceof Action);
		
		Action action = (Action) actionEObject;
		Object listObject = action.eGet(expectedFeature);
		
		Assert.assertTrue(listObject instanceof List);
		
		List<?> resultList;
		
		resultList = getContaimentFetureResult(action, expectedFeature);
		int childrenBefore = resultList.size();
		
		executeOnUIThread(command);
		resultList = getContaimentFetureResult(action, expectedFeature);
		Assert.assertEquals(resultList.size(), childrenBefore + 1);
		
		command.undo();
		resultList = getContaimentFetureResult(action, expectedFeature);
		Assert.assertEquals(resultList.size(), childrenBefore);
		
		command.redo();
		resultList = getContaimentFetureResult(action, expectedFeature);
		Assert.assertEquals(resultList.size(), childrenBefore + 1);
	}
	
	private List<?> getContaimentFetureResult(Action action, EReference ref) {
		Assert.assertTrue("Feature is not a containment", ref.isContainment());
		Assert.assertTrue("Feature should be a containment referance list",ref.isMany());
		List<?> list = (List<?>) action.eGet(ref);
		return list;
	}
	
	private EReference findContainmentFeatureByType(IElementType type) {
		EReference result = null;
		if (UMLElementTypes.Constraint_LocalPreconditionShape == type) {
			result = UMLPackage.eINSTANCE.getAction_LocalPrecondition();
		}
		Assert.assertNotNull("Containment feature for " + type + " type was not found", result);
		return result;
	}

}
