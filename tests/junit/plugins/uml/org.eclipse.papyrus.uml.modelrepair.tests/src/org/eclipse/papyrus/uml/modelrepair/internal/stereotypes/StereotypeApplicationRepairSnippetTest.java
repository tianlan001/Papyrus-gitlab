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
 *   Christian W. Damus - bugs 436666, 458736, 488791
 *
 */
package org.eclipse.papyrus.uml.modelrepair.internal.stereotypes;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.modelrepair.ui.IZombieStereotypePresenter;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;


/**
 * Automated tests for the {@link StereotypeApplicationRepairSnippet} class and its dependencies.
 */
public class StereotypeApplicationRepairSnippetTest extends AbstractPapyrusTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture modelSet = new ModelSetFixture();

	private Profile profile;

	private Stereotype stereotype;

	private Class class1;

	private MyStereotypeApplicationRepairSnippet fixture;

	@CleanUp
	private ZombieStereotypesDescriptor zombies;

	public StereotypeApplicationRepairSnippetTest() {
		super();
	}

	/**
	 * Test case in which all stereotype applications are recognized and there is no corruption (a control case).
	 */
	@Test
	@PluginResource("/resources/model/model.uml")
	public void noCorruptionControlCase() {
		assertThat("Should not have found zombie stereotypes", zombies, nullValue());

		assertStereotypeApplication();
	}

	/**
	 * Test case in which the profile application is not missing, and the EPackage schema is successfully resolved by EMF, but
	 * the stereotype applications are from a different version of the profile than is currently applied.
	 */
	@Test
	@PluginResource("/resources/wrongProfileVersion/model.uml")
	public void wrongProfileVersion() {
		assertThat("Should have found zombie stereotypes", zombies, notNullValue());

		IAdaptable schema = getOnlyZombieSchema();
		EPackage ePackage = PlatformHelper.getAdapter(schema, EPackage.class);
		assertThat("Did not match schema to loaded profile", EcoreUtil.getRootContainer(ePackage), is((EObject) profile));
		assertThat("EPackage is an unknown schema", getExtendedMetadata().demandedPackages(), not(hasItem(ePackage)));

		IRepairAction action = zombies.getSuggestedRepairAction(schema);
		assertThat("Wrong suggested repair action", action.kind(), is(IRepairAction.Kind.APPLY_LATEST_PROFILE_DEFINITION));

		repair(schema, action);
		assertStereotypeApplication();
	}

	/**
	 * Test case in which the profile application is missing, but the EPackage schema is successfully resolved by EMF, so
	 * we just have to link up with the profile definition and apply it (no need to parse unknown-schema AnyTypes).
	 */
	@Test
	@PluginResource("/resources/missingProfileApplication/model.uml")
	public void missingProfileApplication() {
		assertThat("Should have found zombie stereotypes", zombies, notNullValue());

		IAdaptable schema = getOnlyZombieSchema();
		EPackage ePackage = PlatformHelper.getAdapter(schema, EPackage.class);
		assertThat("Did not match schema to loaded profile", EcoreUtil.getRootContainer(ePackage), is((EObject) profile));
		assertThat("EPackage is an unknown schema", getExtendedMetadata().demandedPackages(), not(hasItem(ePackage)));

		IRepairAction action = zombies.getSuggestedRepairAction(schema);
		assertThat("Wrong suggested repair action", action.kind(), is(IRepairAction.Kind.APPLY_LATEST_PROFILE_DEFINITION));

		repair(schema, action);
		assertStereotypeApplication();
	}

	/**
	 * Test case in which the profile application and schema location are present but reference a non-existent resource.
	 * We just have to link up with the profile definition and apply it and parse unknown-schema AnyTypes to reconstruct stereotypes.
	 */
	@Test
	@PluginResource("/resources/missingSchema/model.uml")
	public void missingSchema() {
		assertThat("Should have found zombie stereotypes", zombies, notNullValue());

		IAdaptable schema = getOnlyZombieSchema();
		EPackage ePackage = PlatformHelper.getAdapter(schema, EPackage.class);
		assertThat("EPackage is not an unknown schema", getExtendedMetadata().demandedPackages(), hasItem(ePackage));

		IRepairAction action = zombies.getSuggestedRepairAction(schema);
		assertThat("Wrong suggested repair action", action.kind(), is(IRepairAction.Kind.APPLY_LATEST_PROFILE_DEFINITION));

		repair(schema, action);
		assertStereotypeApplication();
	}

	/**
	 * Test case in which we choose to delete the stereotypes.
	 */
	@Test
	@PluginResource("/resources/wrongProfileVersion/model.uml")
	public void deleteStereotypes() {
		assertThat("Should have found zombie stereotypes", zombies, notNullValue());

		IAdaptable schema = getOnlyZombieSchema();

		IRepairAction action = zombies.getRepairAction(schema, IRepairAction.Kind.DELETE);
		assertThat("Wrong repair action", action.kind(), is(IRepairAction.Kind.DELETE));

		repair(schema, action);

		assertThat("Stereotypes not deleted.", modelSet.getModelResource().getContents().size(), is(1));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() throws Exception {
		URI profileURI = URI.createPlatformPluginURI("org.eclipse.papyrus.uml.modelrepair.tests/resources/profile/myprofile.profile.uml", true);
		profile = UML2Util.load(modelSet.getResourceSet(), profileURI, UMLPackage.Literals.PROFILE);
		stereotype = profile.getOwnedStereotype("Stereo");
		class1 = (Class) modelSet.getModel().getOwnedType("Class1");

		fixture = houseKeeper.cleanUpLater(new MyStereotypeApplicationRepairSnippet(null, Functions.constant(profile)), "dispose", modelSet.getResourceSet());
		fixture.start(modelSet.getResourceSet());
		zombies = fixture.getZombieStereotypes(modelSet.getModelResource(), modelSet.getModel());
	}

	void repair(final IAdaptable schema, final IRepairAction action) {
		try {
			TransactionHelper.run(modelSet.getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					zombies.repair(schema, action, null, null);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to run repair action: " + e.getLocalizedMessage());
		}
	}

	IAdaptable getOnlyZombieSchema() {
		Collection<? extends IAdaptable> schemata = zombies.getZombieSchemas();
		assertThat("Wrong number of zombie packages", schemata.size(), is(1));
		return schemata.iterator().next();
	}

	void assertStereotypeApplication() {
		assertThat("Stereotype not applied to the class", class1.isStereotypeApplied(stereotype));
		EObject application = class1.getStereotypeApplication(stereotype);
		EClass eclass = application.eClass();

		// Verify an Enumeration value stored as an XML attribute
		Object value = application.eGet(eclass.getEStructuralFeature("alertLevel"));
		assertThat("Alert level is not yellow", (value instanceof Enumerator) && ((Enumerator) value).getLiteral().equals("yellow"), is(true));

		// Verify a multi-valued attribute stored as XML elements
		value = application.eGet(eclass.getEStructuralFeature("tag"));
		assertThat("Wrong tags found", value, is((Object) Arrays.asList("p2", "silver")));

		// Verify a reference value stored as an XML IDREFS attribute
		value = application.eGet(eclass.getEStructuralFeature("appliesTo"));
		assertThat("Wrong appliesTo found", value, is((Object) class1.getOwnedAttributes()));

		// Verify a contained EObject that is a complex DataType value
		value = application.eGet(eclass.getEStructuralFeature("name"));
		{
			assertThat("Value is not an EList", value, instanceOf(EList.class));
			EList<?> list = (EList<?>) value;

			assertThat("List has wrong number of elements", list.size(), is(1));
			value = list.get(0);

			assertThat("Value is not an EObject", value, instanceOf(EObject.class));
			EObject name = (EObject) value;
			EClass dataType = name.eClass();

			assertThat("Wrong firstName", name.eGet(dataType.getEStructuralFeature("firstName")), is((Object) "Christian"));
			assertThat("Wrong lastName", name.eGet(dataType.getEStructuralFeature("lastName")), is((Object) "Damus"));
			assertThat("Wrong initials", name.eGet(dataType.getEStructuralFeature("initials")), is((Object) Arrays.asList("W")));
		}
	}

	ExtendedMetaData getExtendedMetadata() {
		ExtendedMetaData result = ExtendedMetaData.INSTANCE;

		Object option = ((XMLResource) modelSet.getModelResource()).getDefaultSaveOptions().get(XMLResource.OPTION_EXTENDED_META_DATA);
		if (option instanceof ExtendedMetaData) {
			result = (ExtendedMetaData) option;
		}

		return result;
	}

	//
	// Nested types
	//

	static class MyStereotypeApplicationRepairSnippet extends StereotypeApplicationRepairSnippet {

		MyStereotypeApplicationRepairSnippet() {
			super();
		}

		MyStereotypeApplicationRepairSnippet(Function<? super ModelSet, ? extends IZombieStereotypePresenter> presenterFunction, Function<? super EPackage, Profile> dynamicProfileSupplier) {
			super(presenterFunction, dynamicProfileSupplier);
		}

		MyStereotypeApplicationRepairSnippet(Function<? super ModelSet, ? extends IZombieStereotypePresenter> presenterFunction) {
			super(presenterFunction);
		}

		// Overridden to make it accessible
		@Override
		protected ZombieStereotypesDescriptor getZombieStereotypes(Resource resource) {
			return super.getZombieStereotypes(resource);
		}

		// Overridden to make it accessible
		@Override
		protected ZombieStereotypesDescriptor getZombieStereotypes(Resource resource, Element root) {
			return super.getZombieStereotypes(resource, root);
		}

		// Overridden to make it accessible
		@Override
		protected Element getRootUMLElement(Resource resource) {
			return super.getRootUMLElement(resource);
		}

		// Overridden to make it accessible
		@Override
		protected Set<EPackage> getAppliedDefinitions(Iterable<? extends ProfileApplication> profileApplications) {
			return super.getAppliedDefinitions(profileApplications);
		}
	}
}
