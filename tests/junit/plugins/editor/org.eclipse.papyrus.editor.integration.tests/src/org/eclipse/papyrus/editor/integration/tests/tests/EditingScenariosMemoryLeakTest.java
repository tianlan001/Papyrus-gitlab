/*
 * Copyright (c) 2014, 2015 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 433206
 *
 */
package org.eclipse.papyrus.editor.integration.tests.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.rules.MemoryLeakRule;
import org.eclipse.papyrus.junit.framework.classification.rules.MemoryLeakRule.SoftReferenceSensitive;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.nattable.menu.util.TableMenuUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.Test;


/**
 * Test cases checking slightly more complex editing scenarios for leaks of user models.
 */
@PluginResource("model/basic/simple_class_model.di")
public class EditingScenariosMemoryLeakTest extends AbstractPapyrusTest {

	@Rule
	public final MemoryLeakRule memory = new MemoryLeakRule();

	private final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	@Rule
	public final EditingMemoryLeakFixture fixture = new EditingMemoryLeakFixture(editor);

	public EditingScenariosMemoryLeakTest() {
		super();
	}

	/**
	 * Verify that models do not leak when closing the editor after creating a new element.
	 */
	@Test
	@SoftReferenceSensitive
	@FailingTest
	public void testCreateNewElement() {
		memory.add(editor.getModel());

		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Class_Shape, fixture.getDiagramEditPart().getDiagramPreferencesHint());
		Command command = fixture.getDiagramEditPart().getCommand(requestcreation);
		fixture.execute(command);
	}

	/**
	 * Verify that models do not leak when closing the editor after creating a new element in a table.
	 */
	@Test
	@SoftReferenceSensitive
	@PluginResource("model/basic/table.di")
	@FailingTest
	public void testCreateNewElement_table() {
		memory.add(editor.getModel());

		// The TableMenuUtils needs the table-editor to be active
		editor.activate();

		IEditorPart tableEditor = fixture.getActiveEditor();
		assertThat("Not a table editor", tableEditor, instanceOf(NatTableEditor.class));
		INattableModelManager manager = tableEditor.getAdapter(INattableModelManager.class);
		assertThat(manager, notNullValue());
		IAxisManager axisManager = manager.getRowAxisManager();
		final int originalAxisSize = axisManager.getAllManagedAxis().size();

		fixture.execute(getCreateClassInTableCommand());

		// Verify that the element was added
		int newAxisSize = axisManager.getAllManagedAxis().size();
		assertThat("Class not added to table", newAxisSize > originalAxisSize);

		// Close the editor and open it again, to force actions etc. to forget the previous selection
		editor.close();
		editor.open();
		fixture.selectModelInModelExplorer();

		// Do another edit ...
		try {
			fixture.execute(getCreateClassInTableCommand());
		} catch (AssertionError failure) {
			// Doesn't matter to our purposes
		}
	}

	/**
	 * Verify that diagrams having active canonical edit policies do not cause leaks.
	 */
	@Test
	@SoftReferenceSensitive
	@PluginResource("model/canonical/css_leaktest.di")
	@ActiveDiagram("main")
	@FailingTest
	public void testCanonicalEditPolicy_bug433206() {
		memory.add(editor.getModel());
		memory.add(editor.getActiveDiagramEditor().getDiagram());

		// Cause some views to be created canonically
		editor.getEditingDomain().getCommandStack().execute(new RecordingCommand(editor.getEditingDomain()) {

			@Override
			protected void doExecute() {
				final Package model = editor.getModel();

				final Class class1 = (Class) model.getOwnedType("Class1");
				class1.createOwnedAttribute("name", null);
				class1.createOwnedOperation("doIt", null, null);
				class1.createNestedClassifier("nested", UMLPackage.Literals.PRIMITIVE_TYPE);

				final Enumeration enum1 = (Enumeration) model.getOwnedType("Enumeration1");
				enum1.createOwnedLiteral("one");
				enum1.createOwnedLiteral("two");

				final Package package1 = model.getNestedPackage("Package1");
				package1.createOwnedClass("Foo", false);
			}
		});
		editor.flushDisplayEvents();

	}

	//
	// Test framework
	//

	org.eclipse.emf.common.command.Command getCreateClassInTableCommand() {
		CreateElementRequest request = new CreateElementRequest(fixture.getEditingDomain(), editor.getModel(), UMLElementTypes.Class_Shape);
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(editor.getModel());
		org.eclipse.emf.common.command.Command create = GMFtoEMFCommandWrapper.wrap(edit.getEditCommand(request));
		return TableMenuUtils.buildNattableCreationCommand(create, request);
	}
}
