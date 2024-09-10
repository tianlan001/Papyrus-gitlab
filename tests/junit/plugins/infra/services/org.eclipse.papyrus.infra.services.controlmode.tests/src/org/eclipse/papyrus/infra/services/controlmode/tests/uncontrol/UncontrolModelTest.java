/*******************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Juan Cadavid (CEA) juan.cadavid@cea.fr - Implementation initial and API 
 *     Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 459427
 *     Christian W. Damus - bugs 480209, 496299
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;
import org.eclipse.papyrus.infra.services.controlmode.tests.Messages;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.PackageableElement;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

/**
 * Test class for Uncontrol Mode with simple model.
 * 
 * @author Juan Cadavid
 *
 */
@PluginResource("model/UncontrolModeTest/ReintegrateTestModel.di")
public class UncontrolModelTest extends AbstractUncontrolModelTest {

	/**
	 * Test of uncontrol one submodel
	 */
	@Test
	public void testUncontrolOneSubmodel() {
		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4);
		uncontrolAssertion.assertUncontrol();


		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there is still controlled element.", getControlledElements().isEmpty());
	}

	/**
	 * Test of uncontrol one submodel
	 */
	@Test
	public void testDeleteSubmodel() {
		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {
			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol.AbstractUncontrolModelTest.UncontrolModeAssertion#getCommandId()
			 *
			 * @return
			 */
			@Override
			protected String getCommandId() {
				return "org.eclipse.ui.edit.delete"; // $NON-NLS-1$
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol.AbstractUncontrolModelTest.UncontrolModeAssertion#assertBeforeSave()
			 *
			 */
			@Override
			protected void assertBeforeSave() {
				assertNull("The controlled element was not deleted.", getElementToUnControl().eResource());
				assertNull("The controlled element was not deleted.", getElementToUnControl().eContainer());
			}

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol.AbstractUncontrolModelTest.UncontrolModeAssertion#assertAfterSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				assertFalse("The fragment resource was not deleted.", editorFixture.getProject().getProject().getFile("Subpackage.uml").exists());
			}

		};
		uncontrolAssertion.assertUncontrol();


		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there is still controlled element.", getControlledElements().isEmpty());
	}

	/**
	 * Test of uncontrol one submodel
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testUndoUncontrolOneSubmodel() throws ServiceException {
		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4);
		uncontrolAssertion.assertUncontrol();

		undo();
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());

		save();
		assertTrue(editorFixture.getProject().getProject().getFile(selectedElements.get(0).getName() + ".uml").exists());


		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there is not controlled element.", getControlledElements().isEmpty());
	}

	/**
	 * Test of uncontrol several submodels containing in same resource.
	 */
	@Test
	@PluginResource("model/UncontrolModeSeveralFragments/model.di")
	public void testUncontrolSeveralSubmodels() {

		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				assertFalse(editorFixture.getProject().getProject().getFile("Package1.uml").exists());
			}
		};
		uncontrolAssertion.assertUncontrol();

		uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				assertFalse(editorFixture.getProject().getProject().getFile("Package2.uml").exists());
			}
		};
		uncontrolAssertion.assertUncontrol();


		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled element.", getControlledElements().isEmpty());
	}

	/**
	 * Test of uncontrol several submodels containing in same resource.
	 * 
	 * @throws ServiceException
	 */
	@Test
	@PluginResource("model/UncontrolModeSeveralFragments/model.di")
	public void testUndoUncontrolSeveralSubmodels() throws ServiceException {

		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				assertFalse(editorFixture.getProject().getProject().getFile("Package1.uml").exists());
			}
		};
		uncontrolAssertion.assertUncontrol();

		uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				assertFalse(editorFixture.getProject().getProject().getFile("Package2.uml").exists());
			}
		};
		uncontrolAssertion.assertUncontrol();


		undo();
		assertNotSame(model.eResource(), selectedElements.get(1).eResource());

		undo();
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());

		save();
		assertTrue(editorFixture.getProject().getProject().getFile("Package1.uml").exists());
		assertTrue(editorFixture.getProject().getProject().getFile("Package2.uml").exists());


		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
	}

	/**
	 * Test of uncontrol several submodels containing in same resource.
	 */
	@Test
	@PluginResource("model/UncontrolModeSameResource/model.di")
	public void testUncontrolSeveralSubmodelsInSameResource() {

		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				assertTrue(editorFixture.getProject().getProject().getFile("SubModels.uml").exists());
			}
		};
		uncontrolAssertion.assertUncontrol();

		uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				assertFalse(editorFixture.getProject().getProject().getFile("SubModels.uml").exists());
			}
		};
		uncontrolAssertion.assertUncontrol();


		editorFixture.close(editor);
		openEditor();

		assertTrue("After have reloaded the editor, there are still controlled element.", getControlledElements().isEmpty());
	}

	/**
	 * Test of uncontrol several submodels containing in same resource.
	 * 
	 * @throws ServiceException
	 */
	@Test
	@PluginResource("model/UncontrolModeSameResource/model.di")
	public void testUndoUncontrolSeveralSubmodelsInSameResource() throws ServiceException {

		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();

				IFile fragmentResource = editorFixture.getProject().getProject().getFile("SubModels.uml");
				assertTrue(fragmentResource.exists());
				assertEquals(1, editorFixture.getResourceSet().getResource(getURIFileInProject("SubModels.uml"), false).getContents().size());
			}
		};
		uncontrolAssertion.assertUncontrol();

		uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}

			@Override
			protected void assertAfterSave() {
				super.assertAfterSave();
				assertFalse(editorFixture.getProject().getProject().getFile("SubModels.uml").exists());
			}
		};
		uncontrolAssertion.assertUncontrol();


		undo();
		assertNotSame(model.eResource(), selectedElements.get(1).eResource());

		undo();
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());

		save();
		assertTrue(editorFixture.getProject().getProject().getFile("SubModels.uml").exists());


		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
	}


	/**
	 * Test of uncontrol several submodels containing in same resource.
	 * 
	 * @throws ServiceException
	 */
	@Test
	@PluginResource("model/UncontrolModeSeveralFragments/model.di")
	public void testUndoUncontrolSeveralSubmodelsWithoutSave() throws ServiceException {

		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected void save() {
				// Not save
			}

		};
		uncontrolAssertion.assertUncontrol();

		uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {

			@Override
			protected Element getElementToUnControl() {
				return selectedElements.get(1);
			}

		};
		uncontrolAssertion.assertUncontrol();


		undo();
		assertNotSame(model.eResource(), selectedElements.get(1).eResource());

		undo();
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
		save();
		assertTrue(editorFixture.getProject().getProject().getFile("Package1.uml").exists());
		assertTrue(editorFixture.getProject().getProject().getFile("Package2.uml").exists());


		editorFixture.close(editor);
		openEditor();

		assertFalse("After have reloaded the editor, there are not controlled elements.", getControlledElements().isEmpty());
	}

	/**
	 * Test that uncontrolling an object also marks as modified any resources that contain objects
	 * that only referenced the uncontrolled object, itself (not some object properly contained
	 * by it). The particular situation is in resources that only reference, say, a package
	 * that was uncontrolled (e.g., PackageMerge relationship) but not anything contained within
	 * that package.
	 */
	@Test
	@PluginResource("model/bug480209/model.di")
	public void testUncontrolUpdatesReferencesToRootObject_bug480209() throws ServiceException {

		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion() {

			@Override
			protected Iterable<? extends PackageableElement> getElementsToSelectForUncontrol() {
				return Collections.singletonList(model.getNestedPackage("Level1").getNestedPackage("Level2"));
			}

			@Override
			protected void assertBeforeUncontrol() {
				// Ensure that everything is loaded
				EcoreUtil.resolveAll(model);

				super.assertBeforeUncontrol();
			}

			@Override
			protected void assertBeforeSave() {
				super.assertBeforeSave();

				assertThat("Referencing resource will not be saved.",
						editorFixture.getModelResource().isModified(), is(true));
			}
		};
		uncontrolAssertion.assertUncontrol();
	}

	/**
	 * Test uncontrol of a "shard" resource sub-unit.
	 */
	@Test
	public void testUncontrolShardSubunit() throws ServiceException {
		ShardResourceHelper[] helper = { null };
		EModelElement[] element = { null };

		UncontrolModeAssertion uncontrolAssertion = new UncontrolModeAssertion(Messages.UncontrolModelTest_4) {
			@Override
			protected void assertBeforeUncontrol() {
				super.assertBeforeUncontrol();

				element[0] = getElementToUnControl();
				helper[0] = new ShardResourceHelper(element[0]);
				houseKeeper.cleanUpLater(helper[0], ShardResourceHelper::close);

				editorFixture.execute(helper[0].getSetShardCommand(true));
				assumeThat("Element does not appear to be a shard", helper[0].isShard(), is(true));
			}
		};
		uncontrolAssertion.assertUncontrol();

		assertThat("Element still appears to be a shard", helper[0].isShard(), is(false));
		assertThat("Element still has an annotation", element[0].getEAnnotations(),
				not(CoreMatchers.<EAnnotation> hasItem(anything())));

		undo();
		assertNotSame(model.eResource(), selectedElements.get(0).eResource());
		assertThat("Element does not appear to be a shard", helper[0].isShard(), is(true));
		assertThat("Element does not have any annotation", element[0].getEAnnotations(),
				CoreMatchers.<EAnnotation> hasItem(anything()));
	}
}
