/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 434993
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.dnd.tests.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.RequestUtils;
import org.eclipse.papyrus.infra.gmfdiag.dnd.policy.DropStrategyManager;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.TreeToFlatContentProvider;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.diagram.dnd.strategy.instancespecification.ui.ClassifierPropertiesContentProvider;
import org.eclipse.papyrus.uml.diagram.dnd.tests.Activator;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * A Class to test the drop of a Classifier (or a list of Classifiers)
 * on an InstanceSpecification
 *
 * @author Camille Letavernier
 *
 */
public class InstanceSpecificationTest extends AbstractPapyrusTest {

	@ClassRule
	public static final HouseKeeper.Static houseKeeper = new HouseKeeper.Static();

	private static Diagram diagram;

	private static IMultiDiagramEditor papyrusEditor;

	private static final Set<IFile> model = new HashSet<IFile>();

	private static DropStrategy strategy;

	@BeforeClass
	public static void init() {
		String projectName = "dnd.test";
		String modelName = "drop"; // drop.di, drop.notation, drop.uml

		try {
			initModel("org.eclipse.papyrus.uml.diagram.dnd.tests/model/drop", projectName, modelName);
		} catch (Exception ex) {
			Activator.log.error(ex);
			Assert.fail("Cannot load the test model");
		}

		houseKeeper.setField("strategy", DropStrategyManager.instance.findStrategy(org.eclipse.papyrus.uml.diagram.dnd.Activator.PLUGIN_ID + ".instanceSpecification"));
	}

	@After
	@Before
	public void initDefaults() {
		// Restore the default DND preferences before each test
		DropStrategyManager.instance.restoreDefaults();
	}

	protected static void initModel(String sourcePath, String projectName, String modelName) throws CoreException, IOException {
		IProject project = houseKeeper.createProject(projectName);

		String[] extensions = { "di", "notation", "uml" };
		for (String extension : extensions) {
			IFile targetFile = project.getFile(modelName + "." + extension);
			URL sourceURL = new URL("platform:/plugin/" + sourcePath + "." + extension);
			copyToWorkspace(sourceURL, targetFile);
		}

		final IFile modelFile = project.getFile(modelName + ".di");

		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					papyrusEditor = houseKeeper.openPapyrusEditor(modelFile);
					diagram = papyrusEditor.getAdapter(Diagram.class);
				} catch (Exception ex) {
					ex.printStackTrace(System.out);
				}
			}
		});

		Assert.assertNotNull("Cannot load the test diagram", diagram);
	}

	protected static void copyToWorkspace(URL sourceURL, IFile targetFile) throws CoreException, IOException {
		InputStream sourceStream = sourceURL.openStream();
		targetFile.create(sourceStream, true, new NullProgressMonitor());
		model.add(targetFile);
		sourceStream.close();
	}

	protected Command drop(Object source, EditPart target, boolean shouldWork) {
		return drop(Collections.singletonList(source), target, shouldWork);
	}

	protected Command drop(Object source, EditPart target) {
		return drop(source, target, true);
	}

	protected Command drop(List<? extends Object> source, EditPart target) {
		return drop(source, target, true);
	}

	protected Command drop(List<? extends Object> source, EditPart target, boolean shouldWork) {
		DropObjectsRequest request = new DropObjectsRequest();
		RequestUtils.setUseGUI(request, false);
		request.setObjects(source);
		Command command = target.getCommand(request);
		if (shouldWork) {
			Assert.assertNotNull("The drop command is null", command);
			Assert.assertTrue("The drop command cannot be executed", command.canExecute());
		} else {
			Assert.assertNull("The drop command should be null", command);
		}

		return command;
	}

	protected Command drop(EditPart target, EObject... source) {
		return drop(Arrays.asList(source), target, true);
	}

	protected Command drop(EditPart target, boolean shouldWork, EObject... source) {
		return drop(Arrays.asList(source), target, shouldWork);
	}

	protected EObject find(String name) {
		return find(name, diagram.getElement());
	}

	protected EObject find(String name, EObject context) {
		if (context instanceof NamedElement) {
			if (name.equals(((NamedElement) context).getName())) {
				return context;
			}
		}

		for (EObject element : context.eContents()) {
			EObject result = find(name, element);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	protected EditPart findEditPart(String name) {
		return findEditPart(name, papyrusEditor.getAdapter(DiagramEditPart.class));
	}

	protected EditPart findEditPart(String name, EditPart context) {
		Element element = UMLUtil.resolveUMLElement(context);
		if (element instanceof NamedElement) {
			if (name.equals(((NamedElement) element).getName())) {
				return context;
			}
		}

		for (Object editPartObject : context.getChildren()) {
			EditPart editPart = (EditPart) editPartObject;
			EditPart result = findEditPart(name, editPart);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	protected CommandStack getCommandStack(EditPart target) {
		return target.getViewer().getEditDomain().getCommandStack();
	}

	protected void execute(Command gefCommand, EditPart target) throws Exception {
		getCommandStack(target).execute(gefCommand);
		// IStatus result = new CommandProxy(gefCommand).execute(new NullProgressMonitor(), null);
		// Assert.assertEquals("The execution status should be OK", IStatus.OK, result.getSeverity());
	}

	protected void checkSlots(EditPart instanceSpecificationPart, List<Property>... expectedProperties) {
		Set<Property> allProperties = new HashSet<Property>();
		for (List<Property> properties : expectedProperties) {
			allProperties.addAll(properties);
		}

		InstanceSpecification instanceSpecification = (InstanceSpecification) UMLUtil.resolveUMLElement(instanceSpecificationPart);

		Assert.assertEquals("Slots should have been created in the InstanceSpecification", allProperties.size(), instanceSpecification.getSlots().size());

		for (Property property : allProperties) {
			Slot slot = findSlot(instanceSpecification, property);
			Assert.assertNotNull("Slot corresponding to " + property.getQualifiedName() + " not found", slot);

			ValueSpecification defaultValue = property.getDefaultValue();
			if (defaultValue != null) {
				Assert.assertEquals("The slot should have a value correspond to the Property's default value", 1, slot.getValues().size());
				ValueSpecification slotValue = slot.getValues().get(0);
				Assert.assertEquals(defaultValue.eClass(), slotValue.eClass());
				// TODO Test value (switch/case on ValueSpecifications?)
				// TODO The test model doesn't contain default values
			}
		}
	}

	protected Slot findSlot(InstanceSpecification specification, Property property) {
		for (Slot slot : specification.getSlots()) {
			if (property == slot.getDefiningFeature()) {
				return slot;
			}
		}

		return null;
	}

	protected void checkClassifiers(EditPart targetPart, EObject... classifiers) {
		InstanceSpecification instance = (InstanceSpecification) UMLUtil.resolveUMLElement(targetPart);
		Assert.assertEquals("The instance should be typed with " + classifiers.length + " classifiers", classifiers.length, instance.getClassifiers().size());
		for (EObject classifier : classifiers) {
			Assert.assertTrue("The instance specification should be typed with " + classifier, instance.getClassifiers().contains(classifier));
		}
	}

	protected void undo(EditPart target) throws Exception {
		getCommandStack(target).undo();
		// EditingDomain domain = EMFHelper.resolveEditingDomain(diagram);
		// CheckedOperationHistory.getInstance().undo(new EditingDomainUndoContext(domain), new NullProgressMonitor(), null);
		InstanceSpecification instanceSpec = (InstanceSpecification) find("InstanceSpecification1");
		Assert.assertEquals("Slots should not exist anymore", 0, instanceSpec.getSlots().size());
		Assert.assertEquals("InstanceSpecification should not be typed anymore", 0, instanceSpec.getClassifiers().size());
	}

	@Test
	public void dropClass() throws Exception {
		EObject class2 = find("Class2");
		EditPart target = findEditPart("InstanceSpecification1");
		Command command = drop(class2, target);

		execute(command, target);
		checkSlots(target, ((org.eclipse.uml2.uml.Class) class2).getAllAttributes());
		checkClassifiers(target, class2);
		undo(target);

		DropStrategyManager.instance.setActive(strategy, false);
		drop(class2, target, false);
	}

	@Test
	public void dropInterface() throws Exception {
		EObject interface1 = find("Interface1");
		EditPart target = findEditPart("InstanceSpecification1");
		Command command = drop(interface1, target);

		execute(command, target);
		checkSlots(target, ((org.eclipse.uml2.uml.Interface) interface1).getAllAttributes());
		checkClassifiers(target, interface1);
		undo(target);

		DropStrategyManager.instance.setActive(strategy, false);
		drop(interface1, target, false);
	}

	@Test
	public void dropClassAndInterface() throws Exception {
		EObject class2 = find("Class2");
		EObject interface1 = find("Interface1");

		EditPart target = findEditPart("InstanceSpecification1");
		Command command = drop(target, class2, interface1);

		execute(command, target);
		checkSlots(target, ((org.eclipse.uml2.uml.Interface) interface1).getAllAttributes(), ((org.eclipse.uml2.uml.Class) class2).getAllAttributes());
		checkClassifiers(target, class2, interface1);
		undo(target);

		DropStrategyManager.instance.setActive(strategy, false);
		drop(target, false, class2, interface1);
	}

	@Test
	public void dropClassAndSuperclass() throws Exception {
		EObject class1 = find("Class1");
		EObject class2 = find("Class2");

		EditPart target = findEditPart("InstanceSpecification1");
		Command command = drop(target, class1, class2);

		execute(command, target);
		checkSlots(target, ((org.eclipse.uml2.uml.Class) class1).getAllAttributes(), ((org.eclipse.uml2.uml.Class) class2).getAllAttributes());
		checkClassifiers(target, class1, class2);
		undo(target);

		DropStrategyManager.instance.setActive(strategy, false);
		drop(target, false, class1, class2);
	}

	@Test
	public void testPropertyContentProvider() {
		Classifier class2 = (Classifier) find("Class2");
		Property c2p1 = (Property) find("Property1", class2);
		Property c2p2 = (Property) find("Property2", class2);

		Classifier class1 = (Classifier) find("Class1");
		Property c1p1 = (Property) find("Property1", class1);
		Property c1p2 = (Property) find("Property2", class1);
		Property c1p3 = (Property) find("Property3", class1);

		Classifier interface1 = (Classifier) find("Interface1");
		Property i1p1 = (Property) find("Property1", interface1);
		Property i1p2 = (Property) find("Property2", interface1);
		Property i1p3 = (Property) find("Property3", interface1);

		ClassifierPropertiesContentProvider contentProvider = new ClassifierPropertiesContentProvider();

		EncapsulatedContentProvider encapsulatedProvider = new EncapsulatedContentProvider(contentProvider);
		TreeToFlatContentProvider provider = new TreeToFlatContentProvider(encapsulatedProvider);

		final Set<Property> expectedResult = new HashSet<Property>();

		// Test 1 : Class2 (Simple Class)

		expectedResult.add(c2p1);
		expectedResult.add(c2p2);

		contentProvider.inputChanged(null, null, Collections.singletonList(class2));
		checkProviderResult(provider, expectedResult, "Test1");

		// Clear Test1
		expectedResult.clear();

		// Test2 : Class1 (Inherited class)

		expectedResult.add(c1p1);
		expectedResult.add(c1p2);
		expectedResult.add(c1p3);
		expectedResult.add(c2p1);
		expectedResult.add(c2p2);

		contentProvider.inputChanged(null, null, Collections.singletonList(class1));
		checkProviderResult(provider, expectedResult, "Test2");

		// Test3 : Class1 + Class2 (Expected result is the same as Test2, do not clear the Set)
		contentProvider.inputChanged(null, null, Arrays.asList(new Classifier[] { class1, class2 }));
		checkProviderResult(provider, expectedResult, "Test3");


		// Test4 : Class1 + Interface1 (Previous result + properties from Interface1)
		expectedResult.add(i1p1);
		expectedResult.add(i1p2);
		expectedResult.add(i1p3);

		contentProvider.inputChanged(null, null, Arrays.asList(new Classifier[] { class1, interface1 }));
		checkProviderResult(provider, expectedResult, "Test4");

		// TODO: Test the case where a Class implements an Interface. Class#getAllAttributes() will not
		// retrieve the Interface's properties. The specification isn't clear whether we should create slots
		// for these properties or not.
	}

	protected void checkProviderResult(IStaticContentProvider provider, Set<Property> expectedResult, String testName) {
		Set<Property> propertiesResult = new HashSet<Property>();
		for (Object element : provider.getElements()) {
			// Only care about properties
			if (element instanceof Property) {
				propertiesResult.add((Property) element);
			}
		}

		Assert.assertEquals("ClassifierPropertiesContentProvider did not return the expected result for test " + testName, expectedResult, propertiesResult);
	}

}
