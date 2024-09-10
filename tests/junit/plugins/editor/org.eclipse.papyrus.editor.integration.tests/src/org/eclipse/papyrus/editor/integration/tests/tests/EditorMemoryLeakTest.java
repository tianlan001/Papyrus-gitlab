/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 485214, 474467
 *
 */
package org.eclipse.papyrus.editor.integration.tests.tests;

import static org.eclipse.papyrus.editor.integration.tests.tests.EditingMemoryLeakFixture.PROPERTY_SHEET;
import static org.junit.Assert.fail;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.papyrus.infra.services.validation.commands.ValidateModelCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.rules.MemoryLeakRule;
import org.eclipse.papyrus.junit.framework.classification.rules.MemoryLeakRule.SoftReferenceSensitive;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Rule;
import org.junit.Test;


/**
 * Test cases checking that various different scenarios in the Papyrus Editor don't lead to memory leaks after the editor has been closed.
 */
@PluginResource("model/basic/simple_class_model.di")
public class EditorMemoryLeakTest extends AbstractPapyrusTest {

	@Rule
	public final MemoryLeakRule memory = new MemoryLeakRule();

	private final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	@Rule
	public final EditingMemoryLeakFixture fixture = new EditingMemoryLeakFixture(editor);

	public EditorMemoryLeakTest() {
		super();
	}

	/**
	 * Verify that the Model Set does not leak when closing the editor.
	 */
	@Test
	@FailingTest
	public void testModelSetDoesNotLeak() {
		memory.add(editor.getModelSet());
	}

	/**
	 * Verify that the UML Model content in the Model Explorer does not leak when closing the editor.
	 */
	@Test
	@SoftReferenceSensitive
	@FailingTest
	public void testModelExplorerContentDoesNotLeak() {
		memory.add(editor.getModel());
	}

	/**
	 * Verify that diagram view parts do not leak when closing the editor.
	 */
	@Test
	@SoftReferenceSensitive
	@FailingTest
	public void testDiagramContentDoesNotLeak() {
		DiagramEditor diagramEditor = (DiagramEditor) editor.getEditor().getActiveEditor();
		memory.add(diagramEditor.getDiagramEditPart());
	}

	/**
	 * Verify that the property sheet does not leak models when closing the editor.
	 */
	@Test
	@SoftReferenceSensitive
	@FailingTest
	public void testPropertySheetContentDoesNotLeak() {
		// Activate the Properties view
		editor.getView(PROPERTY_SHEET, true);

		// Select the Model element to show it in the Properties
		fixture.selectModelInModelExplorer();

		// Back to the Properties view
		editor.getView(PROPERTY_SHEET, false);

		memory.add(editor.getModel());
	}

	/**
	 * Verify that models with dynamic profiles applied (and the profiles and everything else) don't leak when the editor is closed
	 * (the case of profiles that do not have OCL constraints and, therefore, do not have {@link ETypedElement}s of {@link EGenericType} type).
	 */
	@Test
	@SoftReferenceSensitive
	@PluginResource("model/profile/model-no-j2ee-constraints.di")
	@FailingTest
	public void testProfileContentDoesNotLeak_noEGenericTypes() {
		// Activate the Properties view
		editor.getView(PROPERTY_SHEET, true);

		// Select the Model element to show it in the Properties
		fixture.selectModelInModelExplorer();

		// Back to the Properties view
		editor.getView(PROPERTY_SHEET, false);

		memory.add(editor.getModel());
		memory.add(editor.getModel().getAppliedProfile("j2ee"));
	}

	/**
	 * Verify that models with dynamic profiles applied (and the profiles and everything else) don't leak when when validation is run on them
	 * (the case of profiles that do not have OCL constraints and, therefore, do not have {@link ETypedElement}s of {@link EGenericType} type).
	 */
	@Test
	@SoftReferenceSensitive
	@PluginResource("model/profile/model-no-j2ee-constraints.di")
	@FailingTest
	public void testValidatedProfiledModelContentDoesNotLeak_noEGenericTypes() {
		// Validate the model
		try {
			new ValidateModelCommand(editor.getModel()).execute(new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			e.printStackTrace();
			fail("Failed to validate model: " + e.getLocalizedMessage());
		}

		// The validation command activated the Model Validation view. Return to Properties
		editor.getView(PROPERTY_SHEET, false);

		editor.flushDisplayEvents();

		memory.add(editor.getModel());
		memory.add(editor.getModel().getAppliedProfile("j2ee"));
	}

	/**
	 * Verify that models with dynamic profiles applied (and the profiles and everything else) don't leak when the editor is closed
	 * (the case of profiles that have OCL constraints and, therefore, do have {@link ETypedElement}s of {@link EGenericType} type).
	 */
	@Test
	@SoftReferenceSensitive
	@PluginResource("model/profile/model.di")
	@FailingTest
	public void testProfileContentDoesNotLeak() {
		// Activate the Properties view
		editor.getView(PROPERTY_SHEET, true);

		// Select the Model element to show it in the Properties
		fixture.selectModelInModelExplorer();

		// Back to the Properties view
		editor.getView(PROPERTY_SHEET, false);

		memory.add(editor.getModel());
		memory.add(editor.getModel().getAppliedProfile("j2ee"));
	}

	/**
	 * Verify that models with dynamic profiles applied (and the profiles and everything else) don't leak when when validation is run on them
	 * (the case of profiles that have OCL constraints and, therefore, do have {@link ETypedElement}s of {@link EGenericType} type).
	 */
	@Test
	@SoftReferenceSensitive
	@PluginResource("model/profile/model.di")
	@FailingTest
	public void testValidatedProfiledModelContentDoesNotLeak() {
		// Validate the model
		try {
			new ValidateModelCommand(editor.getModel()).execute(new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			e.printStackTrace();
			fail("Failed to validate model: " + e.getLocalizedMessage());
		}

		// The validation command activated the Model Validation view. Return to Properties
		editor.getView(PROPERTY_SHEET, false);

		editor.flushDisplayEvents();

		memory.add(editor.getModel());
		memory.add(editor.getModel().getAppliedProfile("j2ee"));
	}

	/**
	 * Verify that the attachment of listeners to the UML's shared item providers doesn't
	 * cause any leaks (all is properly cleaned up when unloading the editor).
	 */
	@Test
	@SoftReferenceSensitive
	@PluginResource("model/basic/unnamed_diagram.di")
	@FailingTest
	public void testCleanUpListenersOnItemProviders() {
		memory.add(editor.getModelSet());

		DiagramEditor diagramEditor = (DiagramEditor) editor.getEditor().getActiveEditor();
		memory.add(diagramEditor.getDiagramEditPart());
	}
}
