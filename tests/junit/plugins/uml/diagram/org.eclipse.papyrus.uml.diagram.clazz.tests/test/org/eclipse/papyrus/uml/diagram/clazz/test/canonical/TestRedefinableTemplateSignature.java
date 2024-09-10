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

package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.uml2.uml.TemplateSignature;
import org.eclipse.uml2.uml.TemplateableElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * RedefinableTemplateSignature affixed node tests
 */
public class TestRedefinableTemplateSignature extends AbstractPapyrusTestCase {

	/**
	 * Test Interface redefinable template signature
	 */
	@Test
	public void testToManageInterface() {
		testRedefinableTemplateSignature(UMLElementTypes.Interface_Shape);
	}

	/**
	 * Test Class redefinable template signature
	 */
	@Test
	public void testToManageClass() {
		testRedefinableTemplateSignature(UMLElementTypes.Class_Shape);
	}

	/**
	 * Test Signal redefinable template signature
	 */
	@Test
	public void testToManageSignal() {
		testRedefinableTemplateSignature(UMLElementTypes.Signal_Shape);
	}

	/**
	 * Test DataType redefinable template signature
	 */
	@Test
	public void testToManageDataType() {
		testRedefinableTemplateSignature(UMLElementTypes.DataType_Shape);
	}

	/**
	 * Test PrimitiveType redefinable template signature
	 */
	@Test
	public void testToManagePrimitiveType() {
		testRedefinableTemplateSignature(UMLElementTypes.PrimitiveType_Shape);
	}

	/**
	 * Test Enumeration redefinable template signature
	 */
	@Test
	public void testToManageEnumeration() {
		testRedefinableTemplateSignature(UMLElementTypes.Enumeration_Shape);
	}

	/**
	 * Test Component redefinable template signature
	 */
	@Test
	public void testToManageComponent() {
		testRedefinableTemplateSignature(UMLElementTypes.Component_Shape);
	}

	protected void testRedefinableTemplateSignature(IElementType container) {
		createAffixedChildNode(container, getTemplateableSignature());
	}

	protected IElementType getTemplateableSignature() {
		return UMLElementTypes.RedefinableTemplateSignature_Shape;
	}

	protected void createAffixedChildNode(IElementType container, IElementType child) {
		IGraphicalEditPart containerEP = createChild(getDiagramEditPart(), container);
		EObject containerSemantic = containerEP.resolveSemanticElement();
		assertTrue("Expected container as TemplateableElement.", containerSemantic instanceof TemplateableElement);
		IGraphicalEditPart affixedEP = createChild(containerEP, child);
		EObject affixedSemantic = affixedEP.resolveSemanticElement();
		TemplateSignature templateSignature = ((TemplateableElement) containerSemantic).getOwnedTemplateSignature();
		assertNotNull("Container should contains template signature.", templateSignature);
		assertEquals(affixedSemantic, templateSignature);
	}

	private IGraphicalEditPart createChild(IGraphicalEditPart container, IElementType childType) {
		Point location = new Point(0, 0);
		final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(childType, container.getDiagramPreferencesHint());
		@SuppressWarnings("unchecked")
		Map<Object, Object> params = requestcreation.getExtendedData();
		params.put(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION, location);
		requestcreation.setSize(new Dimension(10, 10));
		requestcreation.setLocation(location);
		Command command = container.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		executeOnUIThread(command);
		EditPart createdEditPart = (EditPart) container.getChildren().get((container.getChildren().size() - 1));
		Assert.assertNotNull("The editpart must be created", createdEditPart); //$NON-NLS-1$
		Assert.assertTrue(createdEditPart instanceof IGraphicalEditPart);
		return (IGraphicalEditPart) createdEditPart;
	}
}
