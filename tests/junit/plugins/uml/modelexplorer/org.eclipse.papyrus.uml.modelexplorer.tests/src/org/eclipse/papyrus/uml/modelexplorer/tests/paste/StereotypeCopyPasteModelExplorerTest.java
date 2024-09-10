/*****************************************************************************
 * Copyright (c) 2014, 2018 CEA LIST and others.
 *
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
 *  Gabriel Pascual (ALL4TEC gabriel.pascual@all4tec.net - bug 438511
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr  - bug 530155
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.tests.paste;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.HandlerUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NameElementNamingStrategyPreferenceInitializer;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NamedElementIndexNamingStrategyEnum;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerView;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test for copy/past of stereotype in model explorer.
 *
 * @author Benoit Maggi
 */
@PluginResource("resources/stereotypeCopyPasteMETest/ProfileApplication.di")
public class StereotypeCopyPasteModelExplorerTest extends AbstractPapyrusTest {

	public static final String COPY_COMMAND_ID = "org.eclipse.ui.edit.copy"; //$NON-NLS-1$

	public static final String PASTE_COMMAND_ID = "org.eclipse.ui.edit.paste"; //$NON-NLS-1$

	public static final String STEREOTYPED_ELEMENT_NAME = "theFirst"; //$NON-NLS-1$

	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	// information on the stereotype
	public static final String STEREOTYPE_QN = "profile::First"; //$NON-NLS-1$

	public static final String PARENT_STEREOTYPE_PROPERTY = "parentProperty"; //$NON-NLS-1$

	public static final String PARENT_STEREOTYPE_PROPERTY_VALUE = "child"; //$NON-NLS-1$

	public static final String STEREOTYPE_PROPERTY = "childProperty"; //$NON-NLS-1$

	public static final Double STEREOTYPE_PROPERTY_VALUE = 2.3; // $NON-NLS-1$

	@Before
	public void setUp() {
		// we set the default naming strategy, to preserve a previous test behavior
		org.eclipse.papyrus.uml.tools.utils.Activator.getDefault().getPreferenceStore().setValue(NameElementNamingStrategyPreferenceInitializer.NAMED_ELEMENT_INDEX_INITIALIZATION, NamedElementIndexNamingStrategyEnum.UNIQUE_INDEX_INITIALIZATION.getName());
	}

	/**
	 * Simple copy paste of a class1 with <<requirememt>> stereotype
	 *
	 * @throws Exception
	 */
	@Test
	public void copyPasteofClass1Test() throws Exception {
		// get the rootModel
		Assert.assertNotNull("RootModel is null", editorFixture.getModel()); //$NON-NLS-1$
		// get all semantic element that will handled
		Model model = (Model) editorFixture.getModel();

		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISelectionService selectionService = activeWorkbenchWindow.getSelectionService();
		ModelExplorerView modelExplorerView = editorFixture.getModelExplorerView();
		modelExplorerView.setFocus();
		List<Object> elements = new ArrayList<>();
		elements.add(model);
		modelExplorerView.revealSemanticElement(elements);

		org.eclipse.uml2.uml.Class class1 = (org.eclipse.uml2.uml.Class) model.getPackagedElement(STEREOTYPED_ELEMENT_NAME);

		elements.clear();
		elements.add(class1);
		modelExplorerView.revealSemanticElement(elements);
		Object class1TreeObject = ((IStructuredSelection) selectionService.getSelection()).getFirstElement();
		Assert.assertNotNull("Class1 TreeElement is null", class1TreeObject); //$NON-NLS-1$

		IHandler copyHandler = HandlerUtils.getActiveHandlerFor(COPY_COMMAND_ID);
		Assert.assertTrue("Copy not available", copyHandler.isEnabled()); //$NON-NLS-1$
		copyHandler.execute(new ExecutionEvent());

		// select root
		elements.clear();
		elements.add(model);
		modelExplorerView.revealSemanticElement(elements);

		// paste
		IHandler pasteHandler = HandlerUtils.getActiveHandlerFor(PASTE_COMMAND_ID);
		Assert.assertTrue("Paste not available", pasteHandler.isEnabled()); //$NON-NLS-1$
		pasteHandler.execute(new ExecutionEvent());

		// check that there is a Class1
		org.eclipse.uml2.uml.Class copyOfElement = (org.eclipse.uml2.uml.Class) model.getPackagedElement(NamedElementUtil.COPY_OF + "_" + STEREOTYPED_ELEMENT_NAME + "_1");
		Assert.assertNotNull("The copy is missing", copyOfElement); //$NON-NLS-1$

		Stereotype stereotype = copyOfElement.getAppliedStereotype(STEREOTYPE_QN);
		EObject stereotypeApplication = copyOfElement.getStereotypeApplication(stereotype);
		Assert.assertEquals("Wrong copied property " + STEREOTYPE_PROPERTY, stereotypeApplication.eGet(stereotypeApplication.eClass().getEStructuralFeature(STEREOTYPE_PROPERTY)), STEREOTYPE_PROPERTY_VALUE);
		Assert.assertEquals("Wrong copied property " + PARENT_STEREOTYPE_PROPERTY, stereotypeApplication.eGet(stereotypeApplication.eClass().getEStructuralFeature(PARENT_STEREOTYPE_PROPERTY)), PARENT_STEREOTYPE_PROPERTY_VALUE);

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
		PapyrusClipboard.getNewInstance().clear(); // must be done otherwise it failed other tests
	}


}
