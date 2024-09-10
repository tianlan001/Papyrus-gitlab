/*******************************************************************************
 * Copyright (c) 2014 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Juan Cadavid <juan.cadavid@cea.fr> - Initial implementation and API
 *     Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 459427
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.services.controlmode.tests.Messages;
import org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol.AbstractUncontrolModelTest;
import org.eclipse.papyrus.junit.utils.HandlerUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.PackageableElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * The test class for Control Mode feature.
 */
@PluginResource("model/ControlModeTest/ControlModeTestModel.di")
public class ControlModelTest extends AbstractControlModeTest {

	/**
	 * Test of controlling one sub-model with the default dialog box.
	 */
	@Test
	public void testControlOneSubmodel() throws Exception {
		desactivateDialog();
		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4);

		runnableWithResult.testControl();


		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());

	}

	/**
	 * Test of controlling one sub-model with the default dialog box.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUndoControlOneSubmodel() throws Exception {
		desactivateDialog();
		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4);

		runnableWithResult.testControl();

		Resource submodelResource = selectedElements.get(0).eResource();
		undo();

		Assert.assertNotNull(submodelResource);
		Assert.assertSame(selectedElements.get(0).eResource(), model.eResource());

		save();
		Assert.assertFalse(editorFixture.getProject().getProject().getFile(submodelResource.getURI().lastSegment()).exists());

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are not controlled element.", getControlledElements().isEmpty());

	}


	/**
	 * Test of controlling several sub-models in a common resource.
	 */
	@Test
	@PluginResource("model/ControlModelsSeveralFragments/ControlModeSameResource.di")
	public void testControlSeveralModels() throws Exception {
		desactivateDialog();
		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				Assert.assertEquals("The submodel resource doesn't contain the control element", 1, getElementToControl().eResource().getContents().size());
				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}
		};




		runnableWithResult.testControl();

		runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#getElementToControl()
			 *
			 * @return
			 */
			@Override
			protected Element getElementToControl() {
				return getSelectedElements().get(1);

			}


			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				Assert.assertEquals("The submodel resource doesn't contain the control element", 1, getElementToControl().eResource().getContents().size());
				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}

		};

		runnableWithResult.testControl();


		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
	}

	/**
	 * Test of controlling several sub-models in a common resource.
	 * 
	 * @throws Exception
	 */
	@Test
	@PluginResource("model/ControlModelsSeveralFragments/ControlModeSameResource.di")
	public void testUndoControlSeveralModels() throws Exception {
		desactivateDialog();

		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}
		};



		// Control first Package
		runnableWithResult.testControl();

		runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#getElementToControl()
			 *
			 * @return
			 */
			@Override
			protected Element getElementToControl() {
				return getSelectedElements().get(1);

			}


			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}
		};

		// Control second Package
		runnableWithResult.testControl();

		Resource firstFragment = selectedElements.get(0).eResource();
		Resource secondFragment = selectedElements.get(1).eResource();
		assertNotSame("The control was done in the same resource.", firstFragment, secondFragment);

		// Undo the last control
		undo();
		Assert.assertTrue(secondFragment.getContents().isEmpty());


		// Undo the first control
		undo();
		Assert.assertTrue(firstFragment.getContents().isEmpty());

		// Save undo action
		String firstFragmentName = firstFragment.getURI().lastSegment();
		String secondFragmentName = secondFragment.getURI().lastSegment();

		save();
		Assert.assertFalse(editorFixture.getProject().getProject().getFile(firstFragmentName).exists());
		Assert.assertFalse(editorFixture.getProject().getProject().getFile(secondFragmentName).exists());

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());
	}

	/**
	 * Test of controlling several sub-models in a common resource.
	 */
	@Test
	@PluginResource("model/ControlModelsSeveralFragments/ControlModeSameResource.di")
	public void testControlSeveralModelsInSameResource() throws Exception {
		desactivateDialog();
		final URI commonResourceURI = getURIFileInProject("CommonPackages.uml");
		setSubmodelLocation("CommonPackages.uml");
		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				Assert.assertEquals("The common resource was not used.", commonResourceURI, getElementToControl().eResource().getURI());
				Assert.assertEquals("The submodel resource doesn't contain the control element", 1, getElementToControl().eResource().getContents().size());
				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}
		};




		runnableWithResult.testControl();

		setSubmodelLocation("CommonPackages.uml");
		runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#getElementToControl()
			 *
			 * @return
			 */
			@Override
			protected Element getElementToControl() {
				return getSelectedElements().get(1);

			}


			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				Assert.assertEquals("The common resource was not used.", commonResourceURI, getElementToControl().eResource().getURI());
				Assert.assertEquals("The submodel resource doesn't contain the control element", selectedElements.size(), getElementToControl().eResource().getContents().size());
				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}

		};

		runnableWithResult.testControl();

		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
	}


	/**
	 * Test of controlling several sub-models in a common resource.
	 * 
	 * @throws Exception
	 */
	@Test
	@PluginResource("model/ControlModelsSeveralFragments/ControlModeSameResource.di")
	public void testUndoControlSeveralModelsInSameResource() throws Exception {
		desactivateDialog();
		final URI commonResourceURI = getURIFileInProject("CommonPackages.uml");
		setSubmodelLocation("CommonPackages.uml");

		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				Assert.assertEquals("The common resource was not used.", commonResourceURI, getElementToControl().eResource().getURI());
				Assert.assertEquals("The submodel resource doesn't contain the control element", 1, getElementToControl().eResource().getContents().size());

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}
		};



		// Control first Package
		runnableWithResult.testControl();

		setSubmodelLocation("CommonPackages.uml");
		runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#getElementToControl()
			 *
			 * @return
			 */
			@Override
			protected Element getElementToControl() {
				return getSelectedElements().get(1);

			}



			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				Assert.assertEquals("The common resource was not used.", commonResourceURI, getElementToControl().eResource().getURI());
				Assert.assertEquals("The submodel resource doesn't contain the control element", 2, getElementToControl().eResource().getContents().size());

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertTrue(resource.getContents().isEmpty());
			}
		};

		// Control second Package
		runnableWithResult.testControl();

		Resource submodelResource = selectedElements.get(0).eResource();

		// Undo the last control
		undo();
		Assert.assertFalse(submodelResource.getContents().isEmpty());
		Assert.assertEquals(1, submodelResource.getContents().size());

		// Undo the first control
		undo();
		Assert.assertTrue(submodelResource.getContents().isEmpty());

		// Save undo action
		save();
		Assert.assertFalse(editorFixture.getProject().getProject().getFile(submodelResource.getURI().lastSegment()).exists());

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());
	}

	/**
	 * Test for use case described below :
	 * <ol>
	 * <li>Control the package</li>
	 * <li>Undo</li>
	 * <li>Redo</li>
	 * <li>save</li>
	 * </ol>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testControlModeUseCase1() throws Exception {
		desactivateDialog();
		ControlModeAssertion fixture = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#save()
			 *
			 */
			@Override
			protected void save() {
				// No save
			}
		};

		fixture.testControl();
		assertFalse(getControlledElements().isEmpty());

		undo();
		assertTrue(getControlledElements().isEmpty());

		redo();
		assertFalse(getControlledElements().isEmpty());

		save();
		URI fragmentURi = getURIFileInProject("Package.uml");
		assertTrue("The fragmented resource was not created.", editorFixture.getProject().getFile(fragmentURi).exists());

		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());


	}

	/**
	 * Test of controlling several sub-models in a common resource.
	 * 
	 * @throws Exception
	 */
	@Test
	@PluginResource("model/ControlModelsSeveralFragments/ControlModeSameResource.di")
	public void testControlModeUseCase2() throws Exception {
		desactivateDialog();

		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#save()
			 *
			 */
			@Override
			protected void save() {
				// Not save
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, false);
				assertNotNull(resource);
			}
		};



		// Control first Package
		runnableWithResult.testControl();

		runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#getElementToControl()
			 *
			 * @return
			 */
			@Override
			protected Element getElementToControl() {
				return getSelectedElements().get(1);

			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#save()
			 *
			 */
			@Override
			protected void save() {
				// Not save
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, false);
				assertNotNull(resource);
			}
		};

		// Control second Package
		runnableWithResult.testControl();

		Resource firstFragment = selectedElements.get(0).eResource();
		Resource secondFragment = selectedElements.get(1).eResource();
		assertNotSame("The control was done in the same resource.", firstFragment, secondFragment);

		// Undo the last control
		undo();
		Assert.assertTrue(secondFragment.getContents().isEmpty());


		// Undo the first control
		undo();
		Assert.assertTrue(firstFragment.getContents().isEmpty());

		// Save undo action
		String firstFragmentName = firstFragment.getURI().lastSegment();
		String secondFragmentName = secondFragment.getURI().lastSegment();

		assertFalse(editor.isDirty());
		assertFalse(editorFixture.getProject().getProject().getFile(firstFragmentName).exists());
		assertFalse(editorFixture.getProject().getProject().getFile(secondFragmentName).exists());

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());
	}

	/**
	 * Test of controlling and uncontrolling without save between both actions.
	 * 
	 * @throws Exception
	 */
	@Test
	@PluginResource("model/ControlModelsSeveralFragments/ControlModeSameResource.di")
	public void testControlModeUseCase3() throws Exception {
		desactivateDialog();

		ControlModeAssertion runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#save()
			 *
			 */
			@Override
			protected void save() {
				// Not save
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, false);
				assertNotNull(resource);
			}
		};



		// Control first Package
		runnableWithResult.testControl();

		// Create an uncontrol assertion
		runnableWithResult = new ControlModeAssertion(Messages.ControlModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#getElementToControl()
			 *
			 * @return
			 */
			@Override
			protected Element getElementToControl() {
				return getSelectedElements().get(0);

			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeControl()
			 *
			 */
			@Override
			protected void assertBeforeControl() {
				// Before Uncontrol
				Assert.assertNotNull(getElementToControl());

				// Verify if
				Assert.assertNotSame("The controlled submodel's resource equals its parent's", model.eResource(), getElementToControl().eResource());
				Assert.assertTrue(message, HandlerUtils.getActiveHandlerFor(getCommandId()).isEnabled());
			}
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#getCommandId()
			 *
			 * @return
			 */
			@Override
			protected String getCommandId() {
				// Return Uncontrol command ID
				return AbstractUncontrolModelTest.COMMAND_ID;
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {

			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#save()
			 *
			 */
			@Override
			protected void save() {
				// Not save
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {

				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = controlElement.eResource();
				assertNotNull(resource);

				// Assert that the resource for the model and the submodel
				// are the same
				Assert.assertSame(Messages.AbstractUncontrolModelTest_1, model.eResource(), resource);

			}
		};

		// Uncontrol Package
		runnableWithResult.testControl();

		save();

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());
	}
}
