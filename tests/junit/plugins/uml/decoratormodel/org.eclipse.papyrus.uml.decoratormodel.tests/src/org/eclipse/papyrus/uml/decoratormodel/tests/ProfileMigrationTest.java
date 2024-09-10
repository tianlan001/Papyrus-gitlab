/*****************************************************************************
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.EnumSet;

import org.eclipse.papyrus.infra.core.resource.ReadOnlyAxis;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.helper.IProfileApplicationDelegate;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the migration of profiles that are applied to externalized resources.
 */
@PluginResource("/resources/testmodel.di")
public class ProfileMigrationTest extends AbstractProfileExternalizationTest {
	static final String NEW_VERSION_MARKER_PROPERTY = "newVersionMarker";

	public ProfileMigrationTest() {
		super();
	}

	@Test
	public void migrateProfile() {
		loadDecoratorModel("package2");
		assertExternalApplication(getPackage2(), getTestProfile(), "package2", 3);

		assertThat(getBean2(), isApplied(getBeanStereotype()));
		assertThat(getBean2().getValue(getBeanStereotype(), "kind"), is((Object) getMessageDrivenLiteral()));

		migrate(getPackage2(), getTestProfile());

		// The new stereotype property is available
		assertThat(getBean2(), isApplied(getBeanStereotype()));
		assertThat(getBean2().getValue(getBeanStereotype(), NEW_VERSION_MARKER_PROPERTY), is((Object) Boolean.TRUE));

		// The stereotypes are all still (or, perhaps, again) in the external resource
		assertExternalApplication(getPackage2(), getTestProfile(), "package2", 3);
	}

	//
	// Test framework
	//

	@Before
	public void defineNewProfileVersion() {
		// Referenced profiles are read-only in the context of the model being edited, so
		// disable read-only controls
		execute(new Runnable() {

			@Override
			public void run() {
				Profile profile = getTestProfile();
				Stereotype bean = getBeanStereotype();
				LiteralBoolean bool = (LiteralBoolean) bean.createOwnedAttribute(NEW_VERSION_MARKER_PROPERTY, (Type) profile.getMember("Boolean")).createDefaultValue(null, null, UMLPackage.Literals.LITERAL_BOOLEAN);
				bool.setValue(true);
				profile.define();
			}
		}, TransactionHelper.readOnlyAxisOption(EnumSet.noneOf(ReadOnlyAxis.class)));
	}

	void migrate(final Package package_, final Profile profile) {
		execute(new Runnable() {

			@Override
			public void run() {
				@SuppressWarnings("restriction")
				IProfileApplicationDelegate delegate = new org.eclipse.papyrus.uml.decoratormodel.internal.providers.ExternalizedProfileApplicationDelegate();
				delegate.reapplyProfile(package_, profile, null);
			}
		});
	}
}
