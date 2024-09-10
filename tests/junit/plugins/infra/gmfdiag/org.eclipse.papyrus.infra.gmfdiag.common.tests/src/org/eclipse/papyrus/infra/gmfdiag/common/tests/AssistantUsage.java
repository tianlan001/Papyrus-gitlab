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

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.IModelingAssistantModelProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.DefaultModelingAssistantModelProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.ModelingAssistantModelRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.SynchronizableGmfDiagramEditor;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.ChildrenListRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionSingleton;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionsRegistry;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.InducedRepresentationCreationEditPolicy;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * this Test is used to test if it is possible to add compartment
 * see #Test T003-Add Child Label
 *
 *
 */
public class AssistantUsage extends AbstractEditorTest {

	/**
	 *
	 */
	protected static final String NESTED_INTERFACE_LABEL = "Interface_Label";

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

	/**
	 *
	 */
	protected static final String CLASS_DIAGRAM_TYPE = "Class Diagram";
	/**
	 *
	 */
	protected static final String CLASS_VISUALID = ClassEditPart.VISUAL_ID;
	/**
	 *
	 */
	protected static final String IMPLEMENTED_INTERFACES_HINT = "Implemented Interfaces";

	@Test
	public void testAssistantForLabelNode() {
		// loading
		DiagramExpansionsRegistry diagramExpansionsRegistry = loadXMIExpansionModel("AddChildLabel.expansionmodel");
		Assert.assertEquals("Size ot the registry must be equals to 1", 1, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 1", 1, diagramExpansionsRegistry.mapChildreen.size());

		// test the data structure that is interpreted by the framework
		ChildrenListRepresentation childrenListRepresentation = diagramExpansionsRegistry.mapChildreen.get(CLASS_DIAGRAM_TYPE);
		Assert.assertNotNull("A usage contex has been defined for " + CLASS_DIAGRAM_TYPE, childrenListRepresentation);
		Assert.assertNotNull("The class has been redefined", childrenListRepresentation.IDMap.get(CLASS_VISUALID));
		Assert.assertNotNull("The compartment of class has been added", childrenListRepresentation.IDMap.get(IMPLEMENTED_INTERFACES_HINT));
		List<String> theClassShapeChildren = childrenListRepresentation.parentChildrenRelation.get(CLASS_VISUALID);
		Assert.assertEquals("class shape can have a new compartment", 1, theClassShapeChildren.size());
		Assert.assertEquals("class shape has to contain " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, theClassShapeChildren.get(0));

		Assert.assertNotNull("The Nested Interface has to be added", childrenListRepresentation.IDMap.get(NESTED_INTERFACE_LABEL));
		List<String> theImplementedInterfacesChildren = childrenListRepresentation.parentChildrenRelation.get(IMPLEMENTED_INTERFACES_HINT);
		Assert.assertEquals("Nested Interface can have a new compartment", 1, theImplementedInterfacesChildren.size());
		Assert.assertEquals("Nested Interface has to contain " + NESTED_INTERFACE_LABEL, NESTED_INTERFACE_LABEL, theImplementedInterfacesChildren.get(0));
		// the model is valid
		// now launch a class diagram

		try {
			initModel("ExpansionModelProject", "ExpansionModelTest", getBundle());
			openDiagram(editor, "NewDiagram");
			SynchronizableGmfDiagramEditor diagramEditor = (SynchronizableGmfDiagramEditor) editor.getActiveEditor();
			DiagramEditPart diagramEditPart = editor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("The diagram must be opened", diagramEditPart);
			IGraphicalEditPart classEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().get(0);
			Assert.assertNotNull("A Class edit Part must exist", diagramEditPart);

			// verify editpolicy
			EditPolicy inducedRepresentationCreator = classEditPart.getEditPolicy(InducedRepresentationCreationEditPolicy.INDUCED_REPRESENTATION_CREATOR_EDITPOLICY);
			Assert.assertNotNull("A Class must have this editpolicy", inducedRepresentationCreator);


			// test in the notation
			View classNotationView = classEditPart.getNotationView();
			Assert.assertEquals("the class editpart must be correct", classNotationView.getType(), ClassEditPart.VISUAL_ID);
			Assert.assertEquals("the class editpart must contains 2 labels and 4 compartments", classNotationView.getPersistedChildren().size(), 6);
			View compartment = (View) classNotationView.getPersistedChildren().get(5);
			Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartment.getType());

			// test in the editpart is created for this notation
			Assert.assertEquals("the class editpart must contains 2 labels and 4 compartments", classEditPart.getChildren().size(), 6);
			IGraphicalEditPart compartmentEdiPart = (IGraphicalEditPart) classEditPart.getChildren().get(5);
			Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartmentEdiPart.getNotationView().getType());

			// load model assistant
			URI assistantURI = URI.createPlatformPluginURI("org.eclipse.papyrus.infra.gmfdiag.common.tests", true);
			assistantURI = assistantURI.appendSegment("models");
			assistantURI = assistantURI.appendSegment("MyChildLabelAssistant.assistants");
			// Assert.assertTrue("The assistant file exist", assistantURI.isFile());

			IModelingAssistantModelProvider result = new DefaultModelingAssistantModelProvider(assistantURI);
			Assert.assertNotNull("the assistant model must be loaded", result);
			ModelingAssistantModelRegistry.getInstance().loadModels(result);
			// try to create a label inside this compartment.
			List<?> types = ModelingAssistantService.getInstance().getTypesForPopupBar(classEditPart);
			boolean founded = false;

			// test the construction of the popupbar
			for (Object object : types) {
				if (object instanceof ISpecializationType) {
					ISpecializationType elementType = (ISpecializationType) object;
					if (("org.eclipse.papyrus.uml.diagram.testexpansion.Interface_Label".equals(elementType.getId()))) {

						// find the good short cut
						founded = true;
						// now construct the request and command to simulate the display inside popup bar
						Request req = CreateViewRequestFactory.getCreateShapeRequest(elementType,
								classEditPart.getDiagramPreferencesHint());
						EditPart targetEditpart = classEditPart.getTargetEditPart(req);
						Assert.assertEquals("the request must be redirect to compartment", compartmentEdiPart, targetEditpart);
						Command cmd = targetEditpart.getCommand(req);
						Assert.assertNotNull("The command must not be null", cmd);
						Assert.assertTrue("The command must be executable", cmd.canExecute());
						break;
					}
				}
			}
			Assert.assertTrue("the assistant must contain the shortcut  of interface label ", founded);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
