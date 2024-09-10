/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.function.BiConsumer;

import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.helper.IProfileApplicationDelegate;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Test;

/**
 * Tests regressions in specific bugs.
 */
public class RegressionTest extends AbstractProfileExternalizationTest {
	public RegressionTest() {
		super();
	}

	@Test
	@PluginResource("/resources/regression/bug481302/model.di")
	public void migrateProfileExtendingDynamic_bug481302() throws Exception {
		doTestMigrateProfileExtendingOther_bug481302("p2", this::migrate);
	}

	protected void doTestMigrateProfileExtendingOther_bug481302(String profileName,
			BiConsumer<? super Package, ? super Profile> migrator) throws Exception {

		Package model = modelSet.getModel();
		org.eclipse.uml2.uml.Class req1 = (org.eclipse.uml2.uml.Class) model.getOwnedType("Requirement1");
		Profile profile = modelSet.getModel().getAllAppliedProfiles().get(0);
		Stereotype myReq = req1.getAppliedStereotype(profileName + "::MyRequirement");

		// Initial conditions check
		assumeThat((String) req1.getValue(myReq, "id"), is("R1"));

		execute(() -> migrator.accept(model, profile));

		// Verify that no data lost and new attribute is available
		assertThat((String) req1.getValue(myReq, "id"), is("R1"));
		execute(() -> req1.setValue(myReq, "tag", asList("red", "high")));
		String tag = (String) req1.getValue(myReq, "tag[1]");
		assertThat(tag, is("high"));
	}

	@Test
	@PluginResource("/resources/regression/bug481302/model2.di")
	public void migrateProfileExtendingStatic_bug481302() throws Exception {
		doTestMigrateProfileExtendingOther_bug481302("myreq", this::migrate);
	}

	@Test
	@FailingTest("Use case not supported by UML2: bug 481712")
	@PluginResource("/resources/regression/bug481302/model2.di")
	public void basicUMLMigrateProfileExtendingStatic_bug481302() throws Exception {
		doTestMigrateProfileExtendingOther_bug481302("myreq",
				(package_, profile) -> package_.applyProfile(profile));
	}

	//
	// Test framework
	//

	void migrate(Package package_, Profile profile) {
		@SuppressWarnings("restriction")
		IProfileApplicationDelegate delegate = new org.eclipse.papyrus.uml.decoratormodel.internal.providers.ExternalizedProfileApplicationDelegate();

		delegate.reapplyProfile(package_, profile, null);
	}
}
