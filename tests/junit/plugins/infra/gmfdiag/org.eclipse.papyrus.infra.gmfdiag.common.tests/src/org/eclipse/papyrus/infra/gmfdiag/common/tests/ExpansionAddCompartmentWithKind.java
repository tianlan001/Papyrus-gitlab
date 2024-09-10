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
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
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
 * see #Test T006-Add Compartment with Kind
 *
 *
 */
public class ExpansionAddCompartmentWithKind extends AbstractEditorTest {

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
	public void load_DiagramExpansion() {
		// loading
		DiagramExpansionsRegistry diagramExpansionsRegistry = loadXMIExpansionModel("AddCompartmentWithKind.expansionmodel");
		Assert.assertEquals("Size ot the registry must be equals to 1", 1, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 1", 1, diagramExpansionsRegistry.mapChildreen.size());

		// test the data structure that is interpreted by the framework
		ChildrenListRepresentation childrenListRepresentation = diagramExpansionsRegistry.mapChildreen.get(CLASS_DIAGRAM_TYPE);
		System.out.println(childrenListRepresentation);
		Assert.assertNotNull("A usage contex has been defined for " + CLASS_DIAGRAM_TYPE, childrenListRepresentation);
		Assert.assertNotNull("The class has been redefined", childrenListRepresentation.IDMap.get(CLASS_VISUALID));
		Assert.assertNotNull("The compartment of class has been added", childrenListRepresentation.IDMap.get(IMPLEMENTED_INTERFACES_HINT));
		List<String> the_class_shape_Children = childrenListRepresentation.parentChildrenRelation.get(CLASS_VISUALID);
		Assert.assertEquals("Class shape can have a new compartment", 1, the_class_shape_Children.size());
		Assert.assertEquals("Class shape has to contain " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, the_class_shape_Children.get(0));

		// the model is valid
		// now launch a class diagram

		try {
			initModel("ExpansionModelProject", "ExpansionModelTest", getBundle());
			openDiagram(editor, "NewDiagram");
			DiagramEditPart diagramEditPart = editor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("The diagram must be opened", diagramEditPart);
			IGraphicalEditPart classEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().get(0);
			Assert.assertNotNull("A Class edit Part must exist", diagramEditPart);

			// verify editpolicy
			EditPolicy inducedRepresentationCreator = classEditPart.getEditPolicy(InducedRepresentationCreationEditPolicy.INDUCED_REPRESENTATION_CREATOR_EDITPOLICY);
			Assert.assertNotNull("A Class must have this editpolicy", inducedRepresentationCreator);


			// test in the notation
			View classNotationView = classEditPart.getNotationView();
			Assert.assertEquals("the Type of class editpart must be correct", classNotationView.getType(), ClassEditPart.VISUAL_ID);
			Assert.assertEquals("the class editpart must contain 2 labels and 4 compartments", classNotationView.getPersistedChildren().size(), 6);
			View compartment = (View) classNotationView.getPersistedChildren().get(5);
			Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartment.getType());

			// test in the editpart is created for this notation
			Assert.assertEquals("the class editpart must contain 2 labels and 4 compartments", classEditPart.getChildren().size(), 6);
			IGraphicalEditPart compartmentEdiPart = (IGraphicalEditPart) classEditPart.getChildren().get(5);
			if (compartmentEdiPart.getNotationView().equals(compartment)) {
				Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartmentEdiPart.getNotationView().getType());
			}
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
