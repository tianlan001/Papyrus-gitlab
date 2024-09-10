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
 *     Juan Cadavid (CEA) juan.cadavid@cea.fr - Initial implementation and API
 *     Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug459427
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.controlmode.tests.Messages;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;

/**
 * Class for test uncontrol mode on model with applied profile.
 * 
 * @author Juan Cadavid
 */
@PluginResource("model/UncontrolModeWithProfileTest/UncontrolModeWithProfileTestModel.di")
public class UncontrolModelWithProfileTest extends AbstractUncontrolModelTest {

	/**
	 * Uncontrol model with profile test.
	 */
	@Test
	public void testUncontrolModelWithProfile() {
		UncontrolModeAssertion runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};


		runnableWithResult.assertUncontrol();

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled element.", getControlledElements().isEmpty());


	}

	/**
	 * Uncontrol model with profile test.
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testUndoUncontrolModelWithProfile() throws ServiceException {
		UncontrolModeAssertion runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		undo();
		assertThat(selectedElements.get(0), instanceOf(Package.class));
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());
		assertFalse(((Package) selectedElements.get(0)).getAppliedProfiles().isEmpty());

		save();
		assertTrue(editorFixture.getProject().getProject().getFile(selectedElements.get(0).getName() + ".uml").exists());

		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there is not controlled element.", getControlledElements().isEmpty());

	}


	/**
	 * Uncontrol model with profile test.
	 */
	@Test
	@PluginResource("model/UncontrolModeWithProfileSeveralFragments/model.di")
	public void testUncontrolSeveralModelsWithProfile() {
		UncontrolModeAssertion runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}


			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());

	}


	/**
	 * Uncontrol model with profile test.
	 * 
	 * @throws ServiceException
	 */
	@Test
	@PluginResource("model/UncontrolModeWithProfileSeveralFragments/model.di")
	public void testUndoUncontrolSeveralModelsWithProfile() throws ServiceException {
		UncontrolModeAssertion runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}


			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		undo();
		assertThat(selectedElements.get(1), instanceOf(Package.class));
		assertNotSame(model.eResource(), selectedElements.get(1).eResource());
		assertFalse(((Package) selectedElements.get(1)).getAppliedProfiles().isEmpty());

		undo();
		assertThat(selectedElements.get(0), instanceOf(Package.class));
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());
		assertFalse(((Package) selectedElements.get(0)).getAppliedProfiles().isEmpty());

		save();
		assertTrue(editorFixture.getProject().getProject().getFile(selectedElements.get(0).eResource().getURI().lastSegment()).exists());
		assertTrue(editorFixture.getProject().getProject().getFile(selectedElements.get(1).eResource().getURI().lastSegment()).exists());

		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());

	}


	/**
	 * Uncontrol model with profile test.
	 */
	@Test
	@PluginResource("model/UncontrolModeWithProfileSameResource/model.di")
	public void testUncontrolSeveralModelsWithProfileInSameResource() {
		UncontrolModeAssertion runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}


			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled elements.", getControlledElements().isEmpty());

	}


	/**
	 * Uncontrol model with profile test.
	 * 
	 * @throws ServiceException
	 */
	@Test
	@PluginResource("model/UncontrolModeWithProfileSameResource/model.di")
	public void testUndoUncontrolSeveralModelsWithProfileInSameResource() throws ServiceException {
		UncontrolModeAssertion runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		runnableWithResult = new UncontrolModeAssertion(Messages.UncontrolModelWithProfileTest_5) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}


			@Override
			protected void assertBeforeSave() {
				assertThat(getElementToUnControl(), instanceOf(Package.class));
				assertTrue(((Package) getElementToUnControl()).getAppliedProfiles().isEmpty());
			}
		};
		runnableWithResult.assertUncontrol();

		undo();
		assertThat(selectedElements.get(1), instanceOf(Package.class));
		assertNotSame(model.eResource(), selectedElements.get(1).eResource());
		assertFalse(((Package) selectedElements.get(1)).getAppliedProfiles().isEmpty());

		undo();
		assertThat(selectedElements.get(0), instanceOf(Package.class));
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());
		assertFalse(((Package) selectedElements.get(0)).getAppliedProfiles().isEmpty());

		save();
		assertTrue(editorFixture.getProject().getProject().getFile("SubModels.uml").exists());

		editorFixture.close(editor);
		openEditor();


		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());

	}


}
