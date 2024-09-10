/*****************************************************************************
 * Copyright (c) 2014, 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net- bug430548
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr  - bug 530155
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.test.copyPaste;



import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.HandlerUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ShowView;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NameElementNamingStrategyPreferenceInitializer;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NamedElementIndexNamingStrategyEnum;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.uml2.uml.Model;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Tests for constraint paste strategy
 * 
 * @author Benoit Maggi
 *
 */
@PluginResource("model/copyPaste/ConstraintPasteStrategy.di")
@ShowView(value = "org.eclipse.papyrus.views.modelexplorer.modelexplorer")
public class ConstraintPasteStrategyTest extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	public static final String COPY_COMMAND_ID = "org.eclipse.ui.edit.copy"; //$NON-NLS-1$

	public static final String PASTE_COMMAND_ID = "org.eclipse.ui.edit.paste"; //$NON-NLS-1$

	public static final String CLASS1_NAME = "Class1"; //$NON-NLS-1$

	public static final String COPY_CLASS1_NAME = "CopyOf_Class1_1"; //$NON-NLS-1$;

	public static final String CONSTRAINT_NAME = "Constraint1"; //$NON-NLS-1$

	public static final String DIAGRAM_NAME = "ConstraintClassDiagram"; //$NON-NLS-1$

	/**
	 * @since 1.3
	 */
	@Before
	public void setUp() {
		// we set the default naming strategy, to preserve a previous test behavior
		org.eclipse.papyrus.uml.tools.utils.Activator.getDefault().getPreferenceStore().setValue(NameElementNamingStrategyPreferenceInitializer.NAMED_ELEMENT_INDEX_INITIALIZATION, NamedElementIndexNamingStrategyEnum.UNIQUE_INDEX_INITIALIZATION.getName());
	}

	/**
	 * Test copy constraint in class diagram.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testCopyConstraintInClassDiagram() throws Exception {

		// get all semantic element that will handled
		Model model = (Model) editorFixture.getModel();
		Assert.assertNotNull("RootModel is null", model);


		org.eclipse.uml2.uml.Class class1 = (org.eclipse.uml2.uml.Class) model.getPackagedElement(CLASS1_NAME);
		org.eclipse.uml2.uml.Constraint constraint = (org.eclipse.uml2.uml.Constraint) class1.getMember(CONSTRAINT_NAME);

		Assert.assertNotNull("Constraint is missing in the model", constraint);

		Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("current opened diagram is not the expected one", mainDiagram.getName(), DIAGRAM_NAME);

		Shape constraintView = DiagramUtils.findShape(mainDiagram, CONSTRAINT_NAME);
		Assert.assertNotNull("Constraint view not present", constraintView);

		Object defaultSelection = getSelectionLikeTestOnModelExplorer();
		Object defaultSelectionHandler = getSelectionLikeInAbstractGraphicalHandler();

		editorFixture.flushDisplayEvents();
		Assert.assertNotNull("Constraint TreeElement is null", defaultSelection); //$NON-NLS-1$
		Assert.assertEquals("TreeElement is not a model", ModelEditPart.class, defaultSelection.getClass());
		Assert.assertEquals("TreeElement is not a model", ModelEditPart.class, defaultSelectionHandler.getClass());

		EditPart constraintEP = editorFixture.findEditPart(constraint);
		editorFixture.select(constraintEP);


		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISelectionService selectionService = activeWorkbenchWindow.getSelectionService();
		Object constraintSelection = ((IStructuredSelection) selectionService.getSelection()).getFirstElement();

		// it's working on service selection
		Assert.assertEquals("TreeElement is not a constraint", CustomConstraintEditPart.class, constraintSelection.getClass());

		ISelection selection = editorFixture.getEditor().getEditorSite().getSelectionProvider().getSelection();
		Object editorSelection = ((IStructuredSelection) selection).getFirstElement();

		// it's working on editor selection
		Assert.assertEquals("TreeElement is not a constraint", CustomConstraintEditPart.class, editorSelection.getClass());

		editorFixture.flushDisplayEvents();
		Object defaultVariableFromLinkHelperSelection = getSelectionLikeInAbstractGraphicalHandler();
		Assert.assertEquals("TreeElement is not a constraint", CustomConstraintEditPart.class, defaultVariableFromLinkHelperSelection.getClass());

		// Test with OpenElementService
		OpenElementService openElementService = ServiceUtilsForEObject.getInstance().getService(OpenElementService.class, constraint);
		openElementService.openSemanticElement(constraint);

		editorFixture.flushDisplayEvents();
		Object defaultVariableFromOpenElementService = getSelectionLikeInAbstractGraphicalHandler();
		Assert.assertEquals("TreeElement is not a constraint", CustomConstraintEditPart.class, defaultVariableFromOpenElementService.getClass());

		// Copy
		IHandler copyHandler = HandlerUtils.getActiveHandlerFor(COPY_COMMAND_ID);
		Assert.assertTrue("Copy not available", copyHandler.isEnabled()); //$NON-NLS-1$
		copyHandler.execute(new ExecutionEvent());

		// Select diagram
		EditPart modelEP = editorFixture.findEditPart(model);
		editorFixture.deselect(constraintEP);
		editorFixture.select(modelEP);
		editorFixture.getPageManager().selectPage(mainDiagram);

		editorFixture.flushDisplayEvents();

		int amountRulesBeforeCopy = class1.getOwnedRules().size();

		// Paste
		IHandler pasteHandler = HandlerUtils.getActiveHandlerFor(PASTE_COMMAND_ID);
		Assert.assertTrue("Paste not available", pasteHandler.isEnabled()); //$NON-NLS-1$
		pasteHandler.execute(new ExecutionEvent());

		editorFixture.flushDisplayEvents();

		// Check that there is a copy of Constraint
		Assert.assertEquals("The copy failed", amountRulesBeforeCopy + 1, class1.getOwnedRules().size()); //$NON-NLS-1$

	}

	@Test
	public void testCopyConstraintInClassDiagramWithClass() throws Exception {

		// get all semantic element that will handled
		Model model = (Model) editorFixture.getModel();
		Assert.assertNotNull("RootModel is null", model);


		org.eclipse.uml2.uml.Class class1 = (org.eclipse.uml2.uml.Class) model.getPackagedElement(CLASS1_NAME);
		org.eclipse.uml2.uml.Constraint constraint = (org.eclipse.uml2.uml.Constraint) class1.getMember(CONSTRAINT_NAME);

		Assert.assertNotNull("Constraint is missing in the model", constraint);

		Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("current opened diagram is not the expected one", mainDiagram.getName(), DIAGRAM_NAME);

		Shape constraintView = DiagramUtils.findShape(mainDiagram, CONSTRAINT_NAME);
		Assert.assertNotNull("Constraint view not present", constraintView);

		Object defaultSelection = getSelectionLikeTestOnModelExplorer();
		Object defaultSelectionHandler = getSelectionLikeInAbstractGraphicalHandler();

		editorFixture.flushDisplayEvents();
		Assert.assertNotNull("Constraint TreeElement is null", defaultSelection); //$NON-NLS-1$
		Assert.assertEquals("TreeElement is not a model", ModelEditPart.class, defaultSelection.getClass());
		Assert.assertEquals("TreeElement is not a model", ModelEditPart.class, defaultSelectionHandler.getClass());

		EditPart constraintEP = editorFixture.findEditPart(constraint);
		editorFixture.select(constraintEP);

		EditPart classEP = editorFixture.findEditPart(class1);
		editorFixture.select(classEP);

		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISelectionService selectionService = activeWorkbenchWindow.getSelectionService();
		Object constraintSelection = ((IStructuredSelection) selectionService.getSelection()).toList().get(0);
		Object classSelection = ((IStructuredSelection) selectionService.getSelection()).toList().get(1);

		// it's working on service selection
		Assert.assertEquals("TreeElement is not a constraint", CustomConstraintEditPart.class, constraintSelection.getClass());
		Assert.assertEquals("TreeElement is not a class", ClassEditPart.class, classSelection.getClass());

		ISelection selection = editorFixture.getEditor().getEditorSite().getSelectionProvider().getSelection();
		Object editorConstraintSelection = ((IStructuredSelection) selection).toList().get(0);
		Object editorClassSelection = ((IStructuredSelection) selection).toList().get(1);

		// it's working on editor selection
		Assert.assertEquals("TreeElement is not a constraint", CustomConstraintEditPart.class, editorConstraintSelection.getClass());
		Assert.assertEquals("TreeElement is not a class", ClassEditPart.class, editorClassSelection.getClass());

		editorFixture.flushDisplayEvents();
		Object defaultConstraintFromLinkHelperSelection = getSelectionLikeInAbstractGraphicalHandler(0);
		Assert.assertEquals("TreeElement is not a constraint", CustomConstraintEditPart.class, defaultConstraintFromLinkHelperSelection.getClass());
		Object defaultClassFromLinkHelperSelection = getSelectionLikeInAbstractGraphicalHandler(1);
		Assert.assertEquals("TreeElement is not a constraint", ClassEditPart.class, defaultClassFromLinkHelperSelection.getClass());

		// Copy
		IHandler copyHandler = HandlerUtils.getActiveHandlerFor(COPY_COMMAND_ID);
		Assert.assertTrue("Copy not available", copyHandler.isEnabled()); //$NON-NLS-1$
		copyHandler.execute(new ExecutionEvent());

		// Select diagram
		EditPart modelEP = editorFixture.findEditPart(model);
		editorFixture.deselect(constraintEP);
		editorFixture.deselect(classEP);
		editorFixture.select(modelEP);
		editorFixture.getPageManager().selectPage(mainDiagram);

		editorFixture.flushDisplayEvents();

		int amountRulesBeforeCopy = class1.getOwnedRules().size();

		// Paste
		IHandler pasteHandler = HandlerUtils.getActiveHandlerFor(PASTE_COMMAND_ID);
		Assert.assertTrue("Paste not available", pasteHandler.isEnabled()); //$NON-NLS-1$
		pasteHandler.execute(new ExecutionEvent());

		editorFixture.flushDisplayEvents();

		// Check that there is a copy of Constraint
		Assert.assertEquals("The copy failed", amountRulesBeforeCopy, class1.getOwnedRules().size()); //$NON-NLS-1$

		org.eclipse.uml2.uml.Class copy_class1 = (org.eclipse.uml2.uml.Class) model.getPackagedElement(COPY_CLASS1_NAME);
		Assert.assertNotNull("Copy of class1 is missing in the model", copy_class1);
		Assert.assertEquals(copy_class1.getOwnedRules().size(), 1);
	}

	private Object getSelectionLikeInAbstractGraphicalHandler() {
		return getSelectionLikeInAbstractGraphicalHandler(0);
	}

	private Object getSelectionLikeInAbstractGraphicalHandler(int numberOfSelectedEl) {
		IEvaluationService evaluationService = PlatformUI.getWorkbench().getService(IEvaluationService.class);
		IEvaluationContext currentState = evaluationService.getCurrentState();
		Object defaultVariable = currentState.getDefaultVariable();
		if (defaultVariable instanceof List) {
			List arrayList = (List) defaultVariable;
			if (!arrayList.isEmpty()) {
				return arrayList.get(numberOfSelectedEl);
			}
		}
		return defaultVariable;
	}

	private Object getSelectionLikeTestOnModelExplorer() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISelectionService selectionService = activeWorkbenchWindow.getSelectionService();
		return ((IStructuredSelection) selectionService.getSelection()).getFirstElement();
	}

	/**
	 * 
	 * @throws Exception
	 * @since 1.3
	 */
	@After
	public void tearDown() throws Exception {
		// we reset the naming strategy to its initial value
		org.eclipse.papyrus.uml.tools.utils.Activator.getDefault().getPreferenceStore().setToDefault(NameElementNamingStrategyPreferenceInitializer.NAMED_ELEMENT_INDEX_INITIALIZATION);
	}

}
