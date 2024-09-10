/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 450523, 399859, 494478
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor;
import org.eclipse.papyrus.infra.core.services.ServiceStartKind;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.commands.ApplyStereotypeCommand;
import org.eclipse.papyrus.uml.tools.commands.UnapplyProfileCommand;
import org.eclipse.papyrus.uml.tools.commands.UnapplyStereotypeCommand;
import org.eclipse.papyrus.uml.tools.listeners.StereotypeElementListener.StereotypeExtensionNotification;
import org.eclipse.papyrus.uml.tools.service.StereotypeElementService;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;



/**
 *
 * Test class for stereotype actions listener of UML element.
 *
 * @author Gabriel Pascual
 *
 */
@PluginResource("/resources/stereotypeListenerTest/profileApplicationTest.di")
public class StereotypeElementListenerTest extends AbstractPapyrusTest {

	/** Model set rule to have an editing domain. */
	@Rule
	public final ModelSetFixture modelSetFixture = new ModelSetFixture();

	/** Assert for stereotype application notification. */
	private AssertNotificationAdapter assertAppliedStereotypeNotification = null;

	/** Assert for stereotype unapplication notification. */
	private AssertNotificationAdapter assertUnappliedStereotypeNotification = null;

	/** Assert for stereotype unapplication notification. */
	private AssertNotificationAdapter assertModifiedStereotypeNotification = null;

	/** UML element listen by class under test. */
	private Element element = null;

	/** Profile used to provide of stereotypes. */
	private Profile profile = null;

	/** SysML profile used to provide static stereotypes. */
	private Profile sysmlProfile = null;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		assertAppliedStereotypeNotification = new AssertNotificationAdapter(StereotypeExtensionNotification.STEREOTYPE_APPLIED_TO_ELEMENT);
		assertUnappliedStereotypeNotification = new AssertNotificationAdapter(StereotypeExtensionNotification.STEREOTYPE_UNAPPLIED_FROM_ELEMENT);
		assertModifiedStereotypeNotification = new AssertNotificationAdapter(StereotypeExtensionNotification.MODIFIED_STEREOTYPE_OF_ELEMENT);
		ServicesRegistry serviceRegistry = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(modelSetFixture.getResourceSet());

		// Start service for
		ServiceDescriptor desc = new ServiceDescriptor(IService.class, StereotypeElementService.class.getName(), ServiceStartKind.STARTUP, 10, Collections.singletonList(TransactionalEditingDomain.class.getName()));
		desc.setClassBundleID(org.eclipse.papyrus.uml.tools.Activator.PLUGIN_ID);
		serviceRegistry.add(desc);
		serviceRegistry.startRegistry();

	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		assertAppliedStereotypeNotification = null;
		assertUnappliedStereotypeNotification = null;
		// underTest = null;
	}

	/**
	 * Test add stereotype.
	 */
	@Test
	public void testAddStereotype() {

		initialiseTestCase();

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		Stereotype stereotype = profile.getOwnedStereotype("First");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereotype, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);
		assertAppliedStereotypeNotification.assertNotification(element, stereotype);
		assertModifiedStereotypeNotification.assertNoNotification(element);

	}

	/**
	 * Test remove stereotype.
	 */
	@Test
	public void testRemoveStereotype() {

		initialiseTestCase();

		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype member = (Stereotype) profile.getMember("First");
		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, (Stereotype) profile.getMember("First"), editingDomain));
		editingDomain.getCommandStack().execute(new UnapplyStereotypeCommand(element, member, editingDomain));
		assertUnappliedStereotypeNotification.assertNotification(element);
		assertUnappliedStereotypeNotification.assertNotification(element, member);
		assertModifiedStereotypeNotification.assertNoNotification(element);
	}


	/**
	 * Test stereotype modification.
	 */
	@Test
	public void testModifiedStereotype() {

		initialiseTestCase();

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		final Stereotype stereotype = profile.getOwnedStereotype("First");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereotype, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);
		assertAppliedStereotypeNotification.assertNotification(element, stereotype);

		RecordingCommand setValue = new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				element.setValue(stereotype, "isModified", true);

			}
		};

		editingDomain.getCommandStack().execute(setValue);

		assertModifiedStereotypeNotification.assertNotification(element);

	}

	/**
	 * Test undo action on stereotype modification.
	 */
	@Test
	public void testUndoModifiedStereotype() {

		initialiseTestCase();

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		final Stereotype stereotype = profile.getOwnedStereotype("First");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereotype, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);
		assertAppliedStereotypeNotification.assertNotification(element, stereotype);

		RecordingCommand setValue = new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				element.setValue(stereotype, "isModified", true);

			}
		};

		editingDomain.getCommandStack().execute(setValue);
		editingDomain.getCommandStack().undo();

		assertModifiedStereotypeNotification.assertNotification(2, element);

	}

	/**
	 * Test undo action on stereotype application.
	 */
	@Test
	public void testUndoAddStereotype() {

		initialiseTestCase();

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype member = profile.getOwnedStereotype("First");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);

		editingDomain.getCommandStack().undo();
		assertUnappliedStereotypeNotification.assertNotification(element);

		assertModifiedStereotypeNotification.assertNoNotification(element);
	}

	/**
	 * Test undo action on stereotype unapplication.
	 */
	@Test
	public void testUndoRemoveStereotype() {

		initialiseTestCase();

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype member = profile.getOwnedStereotype("First");

		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);

		editingDomain.getCommandStack().execute(new UnapplyStereotypeCommand(element, member, editingDomain));
		assertUnappliedStereotypeNotification.assertNotification(element);


		editingDomain.getCommandStack().undo();
		assertAppliedStereotypeNotification.assertNotification(2, element);

		assertModifiedStereotypeNotification.assertNoNotification(element);
	}

	/**
	 * Test of unapplication Profile.
	 */
	@Test
	public void testUnapplyProfile() {

		initialiseTestCase();

		Assert.assertFalse(modelSetFixture.getModel().getProfileApplications().isEmpty());
		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype member = profile.getOwnedStereotype("First");

		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);
		assertAppliedStereotypeNotification.assertNotification(element, member);

		editingDomain.getCommandStack().execute(new UnapplyProfileCommand(modelSetFixture.getModel(), profile, editingDomain));
		assertUnappliedStereotypeNotification.assertNotification(element);
		assertUnappliedStereotypeNotification.assertNotification(element, member);

		assertModifiedStereotypeNotification.assertNoNotification(element);


	}

	/**
	 * Test of unapplication Profile.
	 */
	@Test
	public void testUndoUnapplyProfile() {

		initialiseTestCase();

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype member = profile.getOwnedStereotype("First");

		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);

		editingDomain.getCommandStack().execute(new UnapplyProfileCommand(modelSetFixture.getModel(), profile, editingDomain));
		assertUnappliedStereotypeNotification.assertNotification(element);

		editingDomain.getCommandStack().undo();
		assertAppliedStereotypeNotification.assertNotification(2, element);


		assertModifiedStereotypeNotification.assertNoNotification(element);

	}


	/**
	 * Test add several stereotypes.
	 */
	@Test
	public void testAddStereotypeOnSeveralElements() {
		initialiseTestCase();

		final Element secondElement = modelSetFixture.getModel().getMember("SecondUnderTest");
		final Stereotype stereotype = (Stereotype) profile.getMember("First");
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);
		secondElement.eAdapters().add(assertAppliedStereotypeNotification);
		secondElement.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();


		RecordingCommand command = new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				element.applyStereotype(stereotype);
				secondElement.applyStereotype(stereotype);

			}
		};

		editingDomain.getCommandStack().execute(command);

		assertAppliedStereotypeNotification.assertNotification(element);
		assertAppliedStereotypeNotification.assertNotification(secondElement);

		assertModifiedStereotypeNotification.assertNoNotification(element);
		assertModifiedStereotypeNotification.assertNoNotification(secondElement);

	}


	/**
	 * Test undo of stereotype's addition on different element.
	 */
	@Test
	public void testUndoAddStereotypeOnSeveralElements() {
		initialiseTestCase();

		final Element secondElement = modelSetFixture.getModel().getMember("SecondUnderTest");
		final Stereotype stereotype = (Stereotype) profile.getMember("First");
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);
		secondElement.eAdapters().add(assertAppliedStereotypeNotification);
		secondElement.eAdapters().add(assertUnappliedStereotypeNotification);
		secondElement.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();


		RecordingCommand command = new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				element.applyStereotype(stereotype);
				secondElement.applyStereotype(stereotype);

			}
		};

		editingDomain.getCommandStack().execute(command);

		assertAppliedStereotypeNotification.assertNotification(element);
		assertAppliedStereotypeNotification.assertNotification(secondElement);

		editingDomain.getCommandStack().undo();
		assertUnappliedStereotypeNotification.assertNotification(element);
		assertUnappliedStereotypeNotification.assertNotification(secondElement);

		assertModifiedStereotypeNotification.assertNoNotification(element);
		assertModifiedStereotypeNotification.assertNoNotification(secondElement);


	}

	/**
	 * Test add several stereotypes on one element.
	 */
	@Test
	public void testAddStereotypes() {
		initialiseTestCase();

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		Stereotype first = profile.getOwnedStereotype("First");
		Stereotype second = profile.getOwnedStereotype("Second");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, Arrays.asList(first, second), editingDomain));

		assertAppliedStereotypeNotification.assertNotification(2, element);
		assertAppliedStereotypeNotification.assertNotification(element, first, second);

		assertModifiedStereotypeNotification.assertNoNotification(element);

	}


	/**
	 * Test undo of several stereotype's application.
	 */
	@Test
	public void testUndoAddStereotypes() {
		initialiseTestCase();

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, Arrays.asList((Stereotype) profile.getMember("First"), (Stereotype) profile.getMember("Second")), editingDomain));

		assertAppliedStereotypeNotification.assertNotification(2, element);

		editingDomain.getCommandStack().undo();
		assertUnappliedStereotypeNotification.assertNotification(2, element);


		assertModifiedStereotypeNotification.assertNoNotification(element);


	}


	/**
	 * Test that changes to non-extension-end properties named <tt>base_Xyz</tt> are not
	 * interpreted as stereotype (un)applications.
	 */
	@Test
	public void testFakeBasedProperty() {
		initialiseTestCase();

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		final Stereotype testSuite = profile.getOwnedStereotype("TestSuite");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, testSuite, editingDomain));

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		// Change a property whose name starts with 'base_' that isn't a metaclass extension
		editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				element.setValue(testSuite, "base_timeout", "600");
			}
		});

		assertAppliedStereotypeNotification.assertNoNotification(element);
		assertUnappliedStereotypeNotification.assertNoNotification(element);

		assertModifiedStereotypeNotification.assertNoNotification(element);
	}


	/**
	 * Test add stereotype with static profile.
	 */
	@Test
	public void testAddSysMLStereotype() {

		initialiseTestCase();

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		Stereotype stereotype = sysmlProfile.getOwnedStereotype("Block");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereotype, editingDomain));

		assertAppliedStereotypeNotification.assertNotification(element);
		assertAppliedStereotypeNotification.assertNotification(element, stereotype);

		assertModifiedStereotypeNotification.assertNoNotification(element);
	}

	/**
	 * Test remove stereotype with static profile.
	 */
	@Test
	public void testRemoveSysMLStereotype() {

		initialiseTestCase();

		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype stereotype = (Stereotype) sysmlProfile.getMember("Block");
		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereotype, editingDomain));
		editingDomain.getCommandStack().execute(new UnapplyStereotypeCommand(element, stereotype, editingDomain));

		assertUnappliedStereotypeNotification.assertNotification(element);
		assertUnappliedStereotypeNotification.assertNotification(element, stereotype);

		assertModifiedStereotypeNotification.assertNoNotification(element);
	}

	/**
	 * Test undo action on stereotype application with static Profile.
	 */
	@Test
	public void testUndoAddSysMLStereotype() {

		initialiseTestCase();

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype member = sysmlProfile.getOwnedStereotype("Block");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);

		editingDomain.getCommandStack().undo();
		assertUnappliedStereotypeNotification.assertNotification(element);

		assertModifiedStereotypeNotification.assertNoNotification(element);
	}

	/**
	 * Test undo action on stereotype unapplication with static Profile.
	 */
	@Test
	public void testUndoRemoveSysMLStereotype() {

		initialiseTestCase();

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		Stereotype member = sysmlProfile.getOwnedStereotype("Block");

		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(element);

		editingDomain.getCommandStack().execute(new UnapplyStereotypeCommand(element, member, editingDomain));
		assertUnappliedStereotypeNotification.assertNotification(element);


		editingDomain.getCommandStack().undo();
		assertAppliedStereotypeNotification.assertNotification(2, element);

		assertModifiedStereotypeNotification.assertNoNotification(element);
	}


	/**
	 * Test modification of applied stereotype with static Profile.
	 */
	@Test
	@InvalidTest("To be reimplemented without the SysML Dependency")
	public void testModifiedSysMLStereotype() {
		Assert.fail("SysML dependency needs to be removed");
		//
		// initialiseTestCase();
		//
		// TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		// element.eAdapters().add(assertAppliedStereotypeNotification);
		// element.eAdapters().add(assertUnappliedStereotypeNotification);
		// element.eAdapters().add(assertModifiedStereotypeNotification);
		//
		// Stereotype member = sysmlProfile.getOwnedStereotype("Block");
		// editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		// assertAppliedStereotypeNotification.assertNotification(element);
		//
		// Block blockApplication = UMLUtil.getStereotypeApplication(element, Block.class);
		// SetCommand setCommand = new SetCommand(editingDomain, blockApplication, BlocksPackage.eINSTANCE.getBlock_IsEncapsulated(), true);
		// editingDomain.getCommandStack().execute(setCommand);
		//
		// assertModifiedStereotypeNotification.assertNotification(element);
		// assertUnappliedStereotypeNotification.assertNoNotification(element);

	}


	/**
	 * Test undo action on stereotype modification with static Profile.
	 */
	@Test
	@InvalidTest("To be reimplemented without the SysML Dependency")
	public void testUndoModifiedSysMLStereotype() {
		Assert.fail("SysML dependency needs to be removed");
		//
		// initialiseTestCase();
		//
		// TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		// element.eAdapters().add(assertAppliedStereotypeNotification);
		// element.eAdapters().add(assertUnappliedStereotypeNotification);
		// element.eAdapters().add(assertModifiedStereotypeNotification);
		//
		// Stereotype member = sysmlProfile.getOwnedStereotype("Block");
		// editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		// assertAppliedStereotypeNotification.assertNotification(element);
		//
		// Block blockApplication = UMLUtil.getStereotypeApplication(element, Block.class);
		// SetCommand setCommand = new SetCommand(editingDomain, blockApplication, BlocksPackage.eINSTANCE.getBlock_IsEncapsulated(), true);
		// editingDomain.getCommandStack().execute(setCommand);
		// editingDomain.getCommandStack().undo();
		//
		// assertModifiedStereotypeNotification.assertNotification(2, element);
		// assertUnappliedStereotypeNotification.assertNoNotification(element);

	}

	/**
	 * Test modification of a stereotype attribute that isn't a 'set' change, but a list change.
	 */
	@Test
	public void testStereotypeListAttributeChange() {
		initialiseTestCase();

		// First, apply a stereotype and prime a list-valued feature
		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		final Stereotype stereotype = profile.getOwnedStereotype("First");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereotype, editingDomain));
		editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				element.setValue(stereotype, "tag", ECollections.asEList("blue"));
			}
		});

		// Add a listener to detect list-value change in the stereotype
		element.eAdapters().add(assertModifiedStereotypeNotification);

		// Add a value to the list feature
		editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>) element.getValue(stereotype, "tag");
				list.add("green");
			}
		});

		assertModifiedStereotypeNotification.assertNotification(element);
	}

	/**
	 * Test modification of a stereotype attribute that isn't a 'set' change, but an unset change.
	 */
	@Test
	public void testStereotypeUnsetAttributeChange() {
		initialiseTestCase();

		// First, apply a stereotype and prime an unsettable feature
		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		final Stereotype stereotype = profile.getOwnedStereotype("First");
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, stereotype, editingDomain));
		editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				element.setValue(stereotype, "unsettableInt", 42);
			}
		});

		// Add a listener to detect the 'unset' change in the stereotype
		element.eAdapters().add(assertModifiedStereotypeNotification);

		// Add a value to the list feature
		editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {
				EObject stereo = element.getStereotypeApplication(stereotype);
				stereo.eUnset(stereo.eClass().getEStructuralFeature("unsettableInt"));
			}
		});

		assertModifiedStereotypeNotification.assertNotification(element);
	}

	/**
	 * Test relative timing of notifications when undoing and redoing
	 * stereotype application.
	 * 
	 * @see <a href="http://eclip.se/494478">bug 494478</a>
	 */
	@Test
	public void testUndoRedoAddStereotypeTiming() {
		initialiseTestCase();

		TransactionalEditingDomain editingDomain = modelSetFixture.getEditingDomain();
		final Stereotype member = profile.getOwnedStereotype("First");

		// Additionally assert the timing of notification relative to object state
		assertAppliedStereotypeNotification = new AssertNotificationAdapter(assertAppliedStereotypeNotification.eventType) {
			@Override
			protected void verifyNotification(StereotypeExtensionNotification msg, Object notifier) {
				assertThat("Stereotype not applied, yet",
						((Element) notifier).isStereotypeApplied(member),
						is(true));
			}
		};
		assertUnappliedStereotypeNotification = new AssertNotificationAdapter(assertUnappliedStereotypeNotification.eventType) {
			@Override
			protected void verifyNotification(StereotypeExtensionNotification msg, Object notifier) {
				assertThat("Stereotype not unapplied, yet",
						((Element) notifier).isStereotypeApplied(member),
						is(false));
			}
		};

		element.eAdapters().add(assertAppliedStereotypeNotification);
		element.eAdapters().add(assertUnappliedStereotypeNotification);
		element.eAdapters().add(assertModifiedStereotypeNotification);

		// Execute should apply the stereotype for the first time
		editingDomain.getCommandStack().execute(new ApplyStereotypeCommand(element, member, editingDomain));
		assertAppliedStereotypeNotification.assertNotification(1, element);

		// Undo should unapply the stereotype for the first time
		editingDomain.getCommandStack().undo();
		assertUnappliedStereotypeNotification.assertNotification(1, element);

		assertModifiedStereotypeNotification.assertNoNotification(element);

		// Redo should apply the stereotype for a second time
		editingDomain.getCommandStack().redo();
		assertAppliedStereotypeNotification.assertNotification(2, element);
	}

	//
	// Test framework
	//

	/**
	 * Load and apply profile to model resource.
	 */
	private void initialiseTestCase() {

		// Verify if profile is correctly imported
		Assert.assertFalse(modelSetFixture.getModel().getProfileApplications().isEmpty());

		// Initialise data for test case
		element = modelSetFixture.getModel().getMember("FirstUnderTest");
		profile = modelSetFixture.getModel().getAppliedProfile("Profile");
		sysmlProfile = modelSetFixture.getModel().getAppliedProfile("SysMLCopy::Blocks");

	}

	/**
	 * Notification assertion adapter.
	 * Verify if event type was notified.
	 *
	 * @author Gabriel Pascual
	 *
	 */
	public class AssertNotificationAdapter extends AdapterImpl {

		/** Event type. */
		private int eventType = -1;

		/** Map of event type and associated notifcation numbers of a notifier. */
		private Map<Object, Map<Integer, Integer>> notifiedEventList = new HashMap<Object, Map<Integer, Integer>>();

		/** Map of event type and associated stereotypes of a notifier. */
		private Map<Object, Map<Integer, List<Stereotype>>> notifiedStereotypeList = new HashMap<Object, Map<Integer, List<Stereotype>>>();


		/**
		 * Constructor.
		 *
		 * @param eventType
		 *            the event type
		 */
		public AssertNotificationAdapter(int eventType) {
			this.eventType = eventType;
		}

		/**
		 * Notify changed.
		 *
		 * @param msg
		 *            the msg
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(Notification msg) {
			int notificationEventType = msg.getEventType();

			// Ignore notifications that we are not interested in
			if (!(msg instanceof StereotypeExtensionNotification) || (notificationEventType != eventType)) {
				return;
			}

			StereotypeExtensionNotification seMsg = (StereotypeExtensionNotification) msg;
			Object eventNotifier = msg.getNotifier();

			// Check if notifier is known
			Map<Integer, Integer> notifierEventMap = notifiedEventList.get(eventNotifier);
			if (notifierEventMap == null) {
				notifierEventMap = new HashMap<Integer, Integer>();
			}
			Map<Integer, List<Stereotype>> notifierStereotypeMap = notifiedStereotypeList.get(eventNotifier);
			if (notifierStereotypeMap == null) {
				notifierStereotypeMap = new HashMap<Integer, List<Stereotype>>();
			}

			// Check if event is known
			Integer eventCount = notifierEventMap.get(notificationEventType);
			if (eventCount == null) {
				eventCount = 1;
			} else {
				eventCount = eventCount.intValue() + 1;
			}
			List<Stereotype> stereotypes = notifierStereotypeMap.get(notificationEventType);
			if (stereotypes == null) {
				stereotypes = new ArrayList<Stereotype>(3);
			}

			// Record the stereotype applied/unapplied
			switch (notificationEventType) {
			case StereotypeExtensionNotification.STEREOTYPE_APPLIED_TO_ELEMENT:
			case StereotypeExtensionNotification.STEREOTYPE_UNAPPLIED_FROM_ELEMENT:
				stereotypes.add(seMsg.getStereotype());
				break;
			}

			// Save notification
			notifierEventMap.put(notificationEventType, eventCount);
			notifiedEventList.put(eventNotifier, notifierEventMap);
			notifierStereotypeMap.put(notificationEventType, stereotypes);
			notifiedStereotypeList.put(eventNotifier, notifierStereotypeMap);

			// Additional verification
			verifyNotification(seMsg, eventNotifier);
		}

		/**
		 * Hook for assertions to be performed immediately on receipt of the notification
		 * (not later in the test script).
		 * 
		 * @param msg
		 *            the stereotype-extension notification (we ignore all others)
		 * @param notifier
		 *            the notifier that dispatched it
		 */
		protected void verifyNotification(StereotypeExtensionNotification msg, Object notifier) {
			// Pass
		}

		/**
		 * Assert no notification.
		 *
		 * @param notifier
		 *            the notifier
		 */
		public void assertNoNotification(Object notifier) {
			Map<Integer, Integer> eventTypeTimesNumberMap = notifiedEventList.get(notifier);
			Assert.assertNull("Expected no notification of " + convertEvent2String() + " stereotype", eventTypeTimesNumberMap);
		}

		/**
		 * To string event.
		 *
		 * @return the string
		 */
		private String convertEvent2String() {
			String event = "";
			switch (eventType) {
			case StereotypeExtensionNotification.STEREOTYPE_APPLIED_TO_ELEMENT:
				event = "applied";
				break;
			case StereotypeExtensionNotification.STEREOTYPE_UNAPPLIED_FROM_ELEMENT:
				event = "unapplied";
				break;
			case StereotypeExtensionNotification.MODIFIED_STEREOTYPE_OF_ELEMENT:
				event = "modified";
				break;
			default:
				// Nothing to do

			}
			return event;
		}

		/**
		 * Assert one notification for this event type.
		 *
		 * @param notifier
		 *            the notifier
		 */
		public void assertNotification(Object notifier) {
			assertNotification(1, notifier);
		}

		/**
		 * Assert several notification for this event type.
		 *
		 * @param timesNumber
		 *            Amount of notification to verify
		 * @param notifier
		 *            the notifier
		 */
		public void assertNotification(int timesNumber, Object notifier) {

			Map<Integer, Integer> eventTypeTimesNumberMap = notifiedEventList.get(notifier);
			Assert.assertNotNull(eventTypeTimesNumberMap);
			Assert.assertFalse(eventTypeTimesNumberMap.isEmpty());

			Integer eventTimesNumber = eventTypeTimesNumberMap.get(eventType);
			Assert.assertTrue(eventTimesNumber != null);
			Assert.assertEquals("Number of times that event was notified", Integer.valueOf(timesNumber), eventTimesNumber);

		}

		/**
		 * Assert the particular stereotype (un)applied.
		 *
		 * @param notifier
		 *            the notifier on which to assert
		 * @param stereotype
		 *            the stereotype to assert
		 */
		public void assertNotification(Object notifier, Stereotype stereotype) {

			Map<Integer, List<Stereotype>> stereotypeMap = notifiedStereotypeList.get(notifier);
			Assert.assertNotNull(stereotypeMap);
			Assert.assertFalse(stereotypeMap.isEmpty());

			List<Stereotype> stereotypes = stereotypeMap.get(eventType);
			Assert.assertNotNull(stereotypes);
			Assert.assertFalse(stereotypes.isEmpty());
			Assert.assertTrue("Stereotype was not " + convertEvent2String(), stereotypes.contains(stereotype));

		}

		/**
		 * Assert the particular stereotypes (un)applied, in order.
		 *
		 * @param notifier
		 *            the notifier on which to assert
		 * @param first
		 *            the first stereotype to assert
		 * @param second
		 *            the second stereotype to assert
		 * @param rest
		 *            additional stereotypes to assert, in order, if any
		 */
		public void assertNotification(Object notifier, Stereotype first, Stereotype second, Stereotype... rest) {

			Map<Integer, List<Stereotype>> stereotypeMap = notifiedStereotypeList.get(notifier);
			Assert.assertNotNull(stereotypeMap);
			Assert.assertFalse(stereotypeMap.isEmpty());

			List<Stereotype> stereotypes = stereotypeMap.get(eventType);
			Assert.assertNotNull(stereotypes);
			Assert.assertFalse(stereotypes.isEmpty());

			List<Stereotype> expected = new ArrayList<Stereotype>(2 + rest.length);
			expected.add(first);
			expected.add(second);
			expected.addAll(Arrays.asList(rest));
			Assert.assertEquals("Stereotypes were not" + convertEvent2String(), expected, stereotypes);

		}
	}

}
