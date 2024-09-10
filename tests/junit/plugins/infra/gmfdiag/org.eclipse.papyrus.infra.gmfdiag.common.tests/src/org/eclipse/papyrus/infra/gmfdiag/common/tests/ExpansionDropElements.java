/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 485220
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.common.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.SynchronizableGmfDiagramEditor;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.ChildrenListRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionSingleton;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionsRegistry;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * this Test is used to test if it is possible to drop element from the model explorer
 * see #Test T006-Drop of Elements
 *
 *
 */
public class ExpansionDropElements extends AbstractEditorTest {


	public void openDiagram(IMultiDiagramEditor editor, final String name) {

		try {
			ModelSet modelSet = ServiceUtils.getInstance().getModelSet(editor.getServicesRegistry());
			NotationModel notation = (NotationModel) modelSet.getModel(NotationModel.MODEL_ID);
			Diagram diagram = notation.getDiagram(name);
			ServiceUtils.getInstance().getService(IPageManager.class, editor.getServicesRegistry()).openPage(diagram);
			flushDisplayEvents();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot initialize test", e);
		}

	}

	protected static final String CLASS_DIAGRAM_TYPE = "Class Diagram";
	protected static final String IMPLEMENTED_INTERFACES_HINT = "Implemented Interfaces";

	@Test
	public void testDropInsidecontainer() {
		// loading
		DiagramExpansionsRegistry diagramExpansionsRegistry = loadXMIExpansionModel("AddChildLabel.expansionmodel");
		Assert.assertEquals("Size ot the registry must be equals to 1", 1, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 1", 1, diagramExpansionsRegistry.mapChildreen.size());

		// test the data structure that is interpreted by the framework
		ChildrenListRepresentation childrenListRepresentation = diagramExpansionsRegistry.mapChildreen.get(CLASS_DIAGRAM_TYPE);
		Assert.assertNotNull("A usage contex has been defined for " + CLASS_DIAGRAM_TYPE, childrenListRepresentation);



		// the model is valid
		// now launch a class diagram

		try {
			initModel("ExpansionModelProject", "ExpansionModelTest", getBundle());
			openDiagram(editor, "NewDiagram");
			SynchronizableGmfDiagramEditor diagramEditor = (SynchronizableGmfDiagramEditor) editor.getActiveEditor();
			DiagramEditPart diagramEditPart = editor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("A Class edit Part must exist", diagramEditPart);
			Assert.assertNotNull("The diagram must be opened", diagramEditPart);
			Assert.assertEquals("The class diagram has to contain two class representation", 2, diagramEditPart.getChildren().size());
			IGraphicalEditPart myclassEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().get(0);
			IGraphicalEditPart myOtherclassEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().get(1);
			Assert.assertNotNull("myclassEditPart edit Part must exist", myclassEditPart);
			Assert.assertNotNull("myOtherclassEditPart edit Part must exist", myOtherclassEditPart);
			// get Child of myclassEditPart
			Class myclass = (Class) myclassEditPart.resolveSemanticElement();
			Assert.assertEquals("The class  must be called", "MyClass", myclass.getName());
			Classifier nestedInterface = myclass.getNestedClassifier("MyNestedInterface");
			Assert.assertNotNull("MyClass must have a nested Interface called --MyNestedInterface--", nestedInterface);

			// try to drop on my class
			Assert.assertEquals("the class editpart must contain 2 labels and 4 compartments", myclassEditPart.getChildren().size(), 6);
			IGraphicalEditPart compartmentEdiPartMyClass = (IGraphicalEditPart) myclassEditPart.getChildren().get(5);
			Assert.assertEquals("the compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartmentEdiPartMyClass.getNotationView().getType());

			DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
			ArrayList<Element> list = new ArrayList<>();
			list.add(nestedInterface);
			dropObjectsRequest.setObjects(list);
			dropObjectsRequest.setLocation(new Point(40, 40));
			Command command = compartmentEdiPartMyClass.getCommand(dropObjectsRequest);
			assertNotNull("the command of drop must not be null", command);
			assertTrue("The command of drop must be executable", command != UnexecutableCommand.INSTANCE);
			assertTrue("The command of drop must be executable", command.canExecute());
			diagramEditor.getDiagramEditDomain().getDiagramCommandStack().execute(command);
			Assert.assertEquals("The representation of the child must be dropped", 1, compartmentEdiPartMyClass.getChildren().size());


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testDropInsideBadcontainer() {
		// loading
		DiagramExpansionsRegistry diagramExpansionsRegistry = loadXMIExpansionModel("AddChildLabel.expansionmodel");
		Assert.assertEquals("Size ot the registry must be equals to 1", 1, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 1", 1, diagramExpansionsRegistry.mapChildreen.size());

		// test the data structure that is interpreted by the framework
		ChildrenListRepresentation childrenListRepresentation = diagramExpansionsRegistry.mapChildreen.get(CLASS_DIAGRAM_TYPE);
		Assert.assertNotNull("A usage contex has been defined for " + CLASS_DIAGRAM_TYPE, childrenListRepresentation);



		// the model is valid
		// now launch a class diagram

		try {
			initModel("ExpansionModelProject", "ExpansionModelTest", getBundle());
			openDiagram(editor, "NewDiagram");
			DiagramEditPart diagramEditPart = editor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("A Class edit Part must exist", diagramEditPart);
			Assert.assertNotNull("The diagram must be opened", diagramEditPart);
			Assert.assertEquals("The class diagram has to contain two class representation", 2, diagramEditPart.getChildren().size());
			IGraphicalEditPart myclassEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().get(0);
			IGraphicalEditPart myOtherclassEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().get(1);
			Assert.assertNotNull("myclassEditPart edit Part must exist", myclassEditPart);
			Assert.assertNotNull("myOtherclassEditPart edit Part must exist", myOtherclassEditPart);
			// get Child of myclassEditPart
			Class myclass = (Class) myclassEditPart.resolveSemanticElement();
			Assert.assertEquals("The class  must be called", "MyClass", myclass.getName());
			Classifier nestedInterface = myclass.getNestedClassifier("MyNestedInterface");
			Assert.assertNotNull("MyClass must have a nested Interface called --MyNestedInterface--", nestedInterface);

			// try to drop on my class
			Assert.assertEquals("the class editpart must contains 2 labels and 4 compartments", myclassEditPart.getChildren().size(), 6);
			IGraphicalEditPart compartmentEdiPartMyOtherClass = (IGraphicalEditPart) myOtherclassEditPart.getChildren().get(5);
			Assert.assertEquals("the compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartmentEdiPartMyOtherClass.getNotationView().getType());

			DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
			ArrayList<Element> list = new ArrayList<>();
			list.add(nestedInterface);
			dropObjectsRequest.setObjects(list);
			dropObjectsRequest.setLocation(new Point(40, 40));
			Command command = compartmentEdiPartMyOtherClass.getCommand(dropObjectsRequest);
			Assert.assertNull("the command of drop must  be null", command);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target, DiagramEditPart diagramEditPart) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, diagramEditPart.getDiagramPreferencesHint());
		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		source.getCommand(connectionRequest);
		// Now, setup the request in preparation to get the
		// connection end
		// command.
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		return connectionRequest;
	}

	protected DiagramExpansionsRegistry loadXMIExpansionModel(String filename) {
		DiagramExpansionsRegistry diagramExpansionsRegistry = DiagramExpansionSingleton.getInstance().getDiagramExpansionRegistry();
		Assert.assertEquals("Size ot the registry must be equals to 0", 0, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 0", 0, diagramExpansionsRegistry.mapChildreen.size());
		URI badContextExpansion = URI.createPlatformPluginURI("org.eclipse.papyrus.infra.gmfdiag.common.tests", true);
		badContextExpansion = badContextExpansion.appendSegment("models");
		badContextExpansion = badContextExpansion.appendSegment(filename);

		diagramExpansionsRegistry.loadExpansion(badContextExpansion);

		return diagramExpansionsRegistry;
	}

	/**
	 * @see org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest#getSourcePath()
	 *
	 * @return
	 */
	@Override
	protected String getSourcePath() {
		return "models/";
	}

	@Override
	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(getClass());
	}

	@After
	@Before
	public void clearExpansionRegistry() {
		DiagramExpansionSingleton.getInstance().getDiagramExpansionRegistry().clear();
	}
}
