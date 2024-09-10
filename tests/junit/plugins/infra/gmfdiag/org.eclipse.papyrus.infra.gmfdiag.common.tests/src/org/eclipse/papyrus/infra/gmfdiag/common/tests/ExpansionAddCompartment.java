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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.ChildrenListRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionSingleton;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.DiagramExpansionsRegistry;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering.InducedRepresentationCreationEditPolicy;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.Activator;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * this Test is used to test if it is possible to add compartment
 * see #Test T002-Add a compartment
 *
 *
 */
public class ExpansionAddCompartment extends AbstractEditorTest {

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
		DiagramExpansionsRegistry diagramExpansionsRegistry = loadXMIExpansionModel("AddCompartment.expansionmodel");
		Assert.assertEquals("Size ot the registry must be equals to 1", 1, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 1", 1, diagramExpansionsRegistry.mapChildreen.size());

		// test the data structure that is interpreted by the framework
		ChildrenListRepresentation childrenListRepresentation = diagramExpansionsRegistry.mapChildreen.get(CLASS_DIAGRAM_TYPE);
		Assert.assertNotNull("A usage contex has been defined for " + CLASS_DIAGRAM_TYPE, childrenListRepresentation);
		Assert.assertNotNull("The class has been redefined", childrenListRepresentation.IDMap.get(CLASS_VISUALID));
		Assert.assertNotNull("The compartment of class has been added", childrenListRepresentation.IDMap.get(IMPLEMENTED_INTERFACES_HINT));
		List<String> the_class_shape_Children = childrenListRepresentation.parentChildrenRelation.get(CLASS_VISUALID);
		Assert.assertEquals("class shape can have a new compartment", 1, the_class_shape_Children.size());
		Assert.assertEquals("class shape has to contain " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, the_class_shape_Children.get(0));

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
			Assert.assertEquals("the Type of class editpart is not correct", classNotationView.getType(), ClassEditPart.VISUAL_ID);
			Assert.assertEquals("the class editpart must contain 2 labels and 4 compartments", classNotationView.getPersistedChildren().size(), 6);
			View compartment = (View) classNotationView.getPersistedChildren().get(5);
			Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartment.getType());

			// test in the editpart is created for this notation
			Assert.assertEquals("the class editpart must contains 2 labels and 4 compartments", classEditPart.getChildren().size(), 6);
			IGraphicalEditPart compartmentEdiPart = (IGraphicalEditPart) classEditPart.getChildren().get(5);
			if (compartmentEdiPart.getNotationView().equals(compartment)) {
				Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartmentEdiPart.getNotationView().getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Test
	public void verifyClassCreationWithCompartment() {
		// loading
		DiagramExpansionsRegistry diagramExpansionsRegistry = loadXMIExpansionModel("AddCompartment.expansionmodel");
		Assert.assertEquals("Size ot the registry must be equals to 1", 1, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 1", 1, diagramExpansionsRegistry.mapChildreen.size());

		// test the data structure that is interpreted by the framework
		ChildrenListRepresentation childrenListRepresentation = diagramExpansionsRegistry.mapChildreen.get(CLASS_DIAGRAM_TYPE);
		Assert.assertNotNull("A usage contex has been defined for " + CLASS_DIAGRAM_TYPE, childrenListRepresentation);
		Assert.assertNotNull("The class has been redefined", childrenListRepresentation.IDMap.get(CLASS_VISUALID));
		Assert.assertNotNull("The compartment of class has been added", childrenListRepresentation.IDMap.get(IMPLEMENTED_INTERFACES_HINT));
		List<String> the_class_shape_Children = childrenListRepresentation.parentChildrenRelation.get(CLASS_VISUALID);
		Assert.assertEquals("class shape can have a new compartment", 1, the_class_shape_Children.size());
		Assert.assertEquals("class shape has to contain " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, the_class_shape_Children.get(0));

		// the model is valid
		// now launch a class diagram

		try {
			initModel("ExpansionModelProject", "ExpansionModelTest", getBundle());
			openDiagram(editor, "NewDiagram");
			DiagramEditPart diagramEditPart = editor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("The diagram must be opened", diagramEditPart);
			Package root = (Package) diagramEditPart.getDiagramView().getElement();
			List<PackageableElement> initialChildren = new ArrayList<>(root.getPackagedElements());

			Command command = getNodeCreationCommand(diagramEditPart, root, ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.umldi.Class_Shape"));
			assertTrue(command != null && command.canExecute());

			getTransactionalEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
			List<PackageableElement> newElements = new ArrayList<>(root.getPackagedElements()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
			assertThat("There is not one and only one class added: " + newElements, newElements.size(), equalTo(1));
			assertThat(newElements.get(0), instanceOf(Class.class));
			Class newClass = (Class) newElements.get(0);
			// test in the notation
			IGraphicalEditPart classEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().stream().filter(ep -> ((IGraphicalEditPart) ep).resolveSemanticElement().equals(newClass)).findAny().orElse(null);
			assertNotNull(classEditPart);
			View classNotationView = classEditPart.getNotationView();
			Assert.assertEquals("the Type of class editpart is not correct", classNotationView.getType(), ClassEditPart.VISUAL_ID);
			Assert.assertEquals("the class editpart must contain 2 labels and 4 compartments", classNotationView.getPersistedChildren().size(), 6);
			View compartment = (View) classNotationView.getPersistedChildren().get(5);
			Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartment.getType());

			// test in the editpart is created for this notation
			Assert.assertEquals("the class editpart must contains 2 labels and 4 compartments", classEditPart.getChildren().size(), 6);
			IGraphicalEditPart compartmentEdiPart = (IGraphicalEditPart) classEditPart.getChildren().get(5);
			if (compartmentEdiPart.getNotationView().equals(compartment)) {
				Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartmentEdiPart.getNotationView().getType());
			}


			getTransactionalEditingDomain().getCommandStack().undo();
			List<PackageableElement> afterUndo = new ArrayList<>(root.getPackagedElements()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
			assertThat(afterUndo.size(), equalTo(0));

			getTransactionalEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
			List<PackageableElement> afterRedo = new ArrayList<>(root.getPackagedElements()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
			assertThat(afterRedo.size(), equalTo(1));
			assertThat(afterRedo.get(0), instanceOf(Class.class));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	public void verifyMetaclassCreationWithCompartment() {
		// loading
		DiagramExpansionsRegistry diagramExpansionsRegistry = loadXMIExpansionModel("AddCompartment.expansionmodel");
		Assert.assertEquals("Size ot the registry must be equals to 1", 1, diagramExpansionsRegistry.getDiagramExpansions().size());
		Assert.assertEquals("Size ot the map childreen must be equals to 1", 1, diagramExpansionsRegistry.mapChildreen.size());

		// test the data structure that is interpreted by the framework
		ChildrenListRepresentation childrenListRepresentation = diagramExpansionsRegistry.mapChildreen.get(CLASS_DIAGRAM_TYPE);
		Assert.assertNotNull("A usage contex has been defined for " + CLASS_DIAGRAM_TYPE, childrenListRepresentation);
		Assert.assertNotNull("The class has been redefined", childrenListRepresentation.IDMap.get(CLASS_VISUALID));
		Assert.assertNotNull("The compartment of class has been added", childrenListRepresentation.IDMap.get(IMPLEMENTED_INTERFACES_HINT));
		List<String> the_class_shape_Children = childrenListRepresentation.parentChildrenRelation.get(CLASS_VISUALID);
		Assert.assertEquals("class shape can have a new compartment", 1, the_class_shape_Children.size());
		Assert.assertEquals("class shape has to contain " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, the_class_shape_Children.get(0));

		// the model is valid
		// now launch a class diagram

		try {
			initModel("ExpansionModelProject", "ExpansionModelTest", getBundle());
			openDiagram(editor, "NewDiagram");
			DiagramEditPart diagramEditPart = editor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("The diagram must be opened", diagramEditPart);
			Package root = (Package) diagramEditPart.getDiagramView().getElement();
			List<PackageableElement> initialChildren = new ArrayList<>(root.getPackagedElements());
			loadElementTypeSetConfiguration(ElementTypeUtils.getDefaultClientContext().getId(), "/org.eclipse.papyrus.infra.gmfdiag.common.tests/models/Standard.elementtypesconfigurations");
			IElementType type = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.infra.gmfdiag.common.tests.StandardTest");
			Command command = getNodeCreationCommand(diagramEditPart, root, type);
			assertTrue(command != null && command.canExecute());

			getTransactionalEditingDomain().getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
			List<PackageableElement> newElements = new ArrayList<>(root.getPackagedElements()).stream().filter(v -> !initialChildren.contains(v)).collect(Collectors.toList());
			assertThat("There is not one and only one class added: " + newElements, newElements.size(), equalTo(1));
			assertThat(newElements.get(0), instanceOf(Class.class));
			Class newClass = (Class) newElements.get(0);
			assertNotNull(newClass.getAppliedStereotype("StandardProfile::Metaclass"));
			
			// test in the notation
			IGraphicalEditPart classEditPart = (IGraphicalEditPart) diagramEditPart.getChildren().stream().filter(ep -> ((IGraphicalEditPart) ep).resolveSemanticElement().equals(newClass)).findAny().orElse(null);
			assertNotNull(classEditPart);
			View classNotationView = classEditPart.getNotationView();
			Assert.assertEquals("the Type of class editpart is not correct", classNotationView.getType(), ClassEditPart.VISUAL_ID);
			Assert.assertEquals("the class editpart must contain 2 labels and 6 compartments", classNotationView.getPersistedChildren().size(), 8);
			View compartment = (View) classNotationView.getPersistedChildren().get(7);
			Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartment.getType());

			// test in the editpart is created for this notation
			Assert.assertEquals("the class editpart must contains 2 labels and 5 compartments", 7, classEditPart.getChildren().size());
			IGraphicalEditPart compartmentEdiPart = (IGraphicalEditPart) classEditPart.getChildren().get(6);
			if (compartmentEdiPart.getNotationView().equals(compartment)) {
				Assert.assertEquals("The last compartment must have the type " + IMPLEMENTED_INTERFACES_HINT, IMPLEMENTED_INTERFACES_HINT, compartmentEdiPart.getNotationView().getType());
			}

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	protected Command getNodeCreationCommand(EditPart editPart, EObject owner, IElementType elementType) {
		CreateElementRequestAdapter adapter = new CreateElementRequestAdapter(new CreateElementRequest(owner, elementType));
		String hint = (elementType instanceof IHintedType) ? ((IHintedType) elementType).getSemanticHint() : null;
		CreateViewAndElementRequest.ViewAndElementDescriptor descriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(adapter, Node.class, hint, PreferencesHint.USE_DEFAULTS);
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(descriptor);

		EditPart targetEditPart = editPart.getTargetEditPart(request);
		return targetEditPart.getCommand(request);
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
	
	
	/**
	 * Loads a given elementType set from a given identifier
	 */
	public static boolean loadElementTypeSetConfiguration(String contextId, String path) throws Exception {

		if (path == null) {
			Activator.log.warn("Path must not be null" + path);
			return false;
		}

		if (contextId == null) {
			Activator.log.warn("contextId must not be null" + path);
			return false;
		}
		URI localURI = URI.createPlatformPluginURI(path, true);
		Field set = ElementTypeSetConfigurationRegistry.class.getDeclaredField("elementTypeSetConfigurationResourceSet");
		set.setAccessible(true);
		ResourceSet resourceSet = (ResourceSet)set.get(ElementTypeSetConfigurationRegistry.getInstance()); 
		Resource resource = resourceSet.createResource(localURI);
		try {
			resource.load(null);
			EObject content = resource.getContents().get(0);
			if (content instanceof ElementTypeSetConfiguration) {
				return ElementTypeSetConfigurationRegistry.getInstance().loadElementTypeSetConfiguration(contextId, (ElementTypeSetConfiguration) content);
			}
		} catch (IOException e) {
			Activator.log.error(e);
		}

		return false;

	}

}
