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
 *     Juan Cadavid (CEA) juan.cadavid@cea.fr -  Initial implementation and API
 *     Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 459427
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.control;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.services.controlmode.tests.Messages;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;

/**
 * Test for Control model with associate profile.
 */
@PluginResource("model/ControlModeWithProfileTest/ControlModeWithProfileTestModel.di")
public class ControlModelWithProfileTest extends AbstractControlModeTest {

	/**
	 * Control model with profile test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testControlModelWithProfile() throws Exception {
		desactivateDialog();

		ControlModeAssertion controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}
		};
		controlAssertion.testControl();

		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled element.", getControlledElements().isEmpty());
	}

	/**
	 * Control model with profile test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUndoControlModelWithProfile() throws Exception {
		desactivateDialog();
		ControlModeAssertion controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}
		};
		controlAssertion.testControl();

		assertThat("", selectedElements.get(0), instanceOf(Package.class));
		Package submodel = (Package) selectedElements.get(0);
		Resource submodelResource = submodel.eResource();

		undo();

		assertTrue(submodel.getAppliedProfiles().isEmpty());

		save();
		assertSame(model.eResource(), submodel.eResource());
		assertFalse(editorFixture.getProject().getProject().getFile(submodelResource.getURI().lastSegment()).exists());

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there is still controlled element.", getControlledElements().isEmpty());


	}

	@Test
	@PluginResource("model/ControlModelsWithProfileSeveralFragments/model.di")
	public void testControlSeveralModelsWithProfile() throws Exception {
		desactivateDialog();
		ControlModeAssertion controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}
		};
		controlAssertion.testControl();

		controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {


			@Override
			protected Element getElementToControl() {
				return selectedElements.get(1);
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertAfterSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				assertEquals("The controlled resource does not contain all elements", 1, getElementToControl().eResource().getContents().size());
			}
		};
		controlAssertion.testControl();

		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
	}

	@Test
	@PluginResource("model/ControlModelsWithProfileSeveralFragments/model.di")
	public void testUndoControlSeveralModelsWithProfile() throws Exception {
		desactivateDialog();
		ControlModeAssertion controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {


			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}
		};
		controlAssertion.testControl();
		controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {


			@Override
			protected Element getElementToControl() {
				return selectedElements.get(1);
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertAfterSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				assertEquals("The controlled resource does not contain all elements", 1, getElementToControl().eResource().getContents().size());
			}
		};

		controlAssertion.testControl();

		Resource firstFragment = selectedElements.get(0).eResource();
		Resource secondFragment = selectedElements.get(1).eResource();
		assertNotSame("The control was done in the same resource.", firstFragment, secondFragment);

		undo();

		assertThat(selectedElements.get(1), instanceOf(Package.class));
		assertTrue(((Package) selectedElements.get(1)).getAppliedProfiles().isEmpty());
		assertTrue(secondFragment.getContents().isEmpty());
		assertSame(model.eResource(), selectedElements.get(1).eResource());

		undo();

		assertTrue(firstFragment.getContents().isEmpty());
		assertThat(selectedElements.get(0), instanceOf(Package.class));
		assertTrue(((Package) selectedElements.get(0)).getAppliedProfiles().isEmpty());
		assertSame(model.eResource(), selectedElements.get(0).eResource());

		String firstFragmentName = firstFragment.getURI().lastSegment();
		String secondFragmentName = secondFragment.getURI().lastSegment();

		save();
		assertFalse(editorFixture.getProject().getProject().getFile(firstFragmentName).exists());
		assertFalse(editorFixture.getProject().getProject().getFile(secondFragmentName).exists());


		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());


	}

	@Test
	@PluginResource("model/ControlModelsWithProfileSeveralFragments/model.di")
	public void testControlSeveralModelsWithProfileInSameResource() throws Exception {
		desactivateDialog();
		setSubmodelLocation("CommonResource.uml");
		ControlModeAssertion controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}
		};
		controlAssertion.testControl();


		setSubmodelLocation("CommonResource.uml");
		controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {


			@Override
			protected Element getElementToControl() {
				return selectedElements.get(1);
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertAfterSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				assertEquals("The controlled resource does not contain all elements", selectedElements.size(), getElementToControl().eResource().getContents().size());
			}
		};
		controlAssertion.testControl();

		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
	}

	@Test
	@PluginResource("model/ControlModelsWithProfileSeveralFragments/model.di")
	public void testUndoControlSeveralModelsWithProfileInSameResource() throws Exception {
		desactivateDialog();
		setSubmodelLocation("commonResource.uml");
		ControlModeAssertion controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {


			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}
		};
		controlAssertion.testControl();

		setSubmodelLocation("commonResource.uml");
		controlAssertion = new ControlModeAssertion(Messages.ControlModelWithProfileTest_4) {


			@Override
			protected Element getElementToControl() {
				return selectedElements.get(1);
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat(getElementToControl(), instanceOf(Package.class));
				Package submodel = (org.eclipse.uml2.uml.Package) getElementToControl();
				assertFalse(submodel.getAppliedProfiles().isEmpty());
				assertSame(submodel.getAppliedProfiles().get(0), model.getAppliedProfiles().get(0));
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertAfterSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				assertEquals("The controlled resource does not contain all elements", selectedElements.size(), getElementToControl().eResource().getContents().size());
			}
		};

		controlAssertion.testControl();

		Resource submodelResource = selectedElements.get(0).eResource();
		undo();

		assertThat(selectedElements.get(1), instanceOf(Package.class));
		assertTrue(((Package) selectedElements.get(1)).getAppliedProfiles().isEmpty());
		assertEquals(1, submodelResource.getContents().size());
		assertSame(model.eResource(), selectedElements.get(1).eResource());

		undo();

		assertTrue(submodelResource.getContents().isEmpty());
		assertThat(selectedElements.get(0), instanceOf(Package.class));
		assertTrue(((Package) selectedElements.get(0)).getAppliedProfiles().isEmpty());
		assertSame(model.eResource(), selectedElements.get(0).eResource());

		save();
		assertFalse(editorFixture.getProject().getProject().getFile(getURIFileInProject("commonResource.uml").lastSegment()).exists());


		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());



	}
}
