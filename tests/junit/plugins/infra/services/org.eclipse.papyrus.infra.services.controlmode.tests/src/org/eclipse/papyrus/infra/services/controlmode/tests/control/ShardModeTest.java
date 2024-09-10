/*******************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Christian W. Damus - Initial implementation and API
 *     
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests.control;

import static com.google.common.collect.Iterables.getFirst;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters;
import org.eclipse.papyrus.infra.services.controlmode.IControlModeManager;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Test;

/**
 * Tests for the management of the 'shard' mode of a controlled unit.
 */
public class ShardModeTest extends AbstractControlModeTest {

	private IControlModeManager mgr = ControlModeManager.getInstance();

	@Test
	@PluginResource("model/UncontrolModeWithProfileTest/UncontrolModeWithProfileTestModel.di")
	public void convertSubmodelToShard() {
		Package submodel = editorFixture.getModel().getNestedPackage("submodel");
		ProfileApplication profile = getFirst(submodel.getProfileApplications(), null);
		Class class1 = (Class) submodel.getOwnedType("Class1");
		EObject stereo = getFirst(class1.getStereotypeApplications(), null);

		ControlModeRequest request = ControlModeRequest.createUIControlModelRequest(
				editorFixture.getEditingDomain(), submodel, submodel.eResource().getURI());
		request.setParameter(ControlModeRequestParameters.CREATE_SHARD, true);

		ShardResourceHelper helper = new ShardResourceHelper(submodel);
		houseKeeper.cleanUpLater(helper, ShardResourceHelper::close);

		ICommand changeToShard = mgr.getShardModeCommand(request);

		assumeThat("No redundant profile applications before", profile, notNullValue());
		assumeThat("No stereotype application before", stereo, notNullValue());
		assumeThat("Wrong resource for stereotype application before", stereo.eResource(), is(class1.eResource()));
		assumeThat("Sub-unit is not a submodel", helper.isShard(), is(false));

		assertThat("No command provided", changeToShard, notNullValue());
		assertThat("Command not executable", changeToShard.canExecute(), is(true));

		editorFixture.execute(changeToShard);

		assertThat("Sub-unit is not a shard", helper.isShard(), is(true));
		assertThat("Redundant profile application still exists", profile.getApplyingPackage(), nullValue());
		assertThat("Stereotype application is gone", stereo.eResource(), is(class1.eResource()));
		assertThat("Stereotype unapplied", UMLUtil.getBaseElement(stereo), is(class1));
	}

	@Test
	@PluginResource("model/ShardedWithProfileTest/ShardedWithProfileTestModel.di")
	public void convertShardToSubmodel() {
		Package submodel = editorFixture.getModel().getNestedPackage("submodel");
		ProfileApplication profile = getFirst(submodel.getProfileApplications(), null);
		Class class1 = (Class) submodel.getOwnedType("Class1");
		EObject stereo = getFirst(class1.getStereotypeApplications(), null);

		ControlModeRequest request = ControlModeRequest.createUIControlModelRequest(
				editorFixture.getEditingDomain(), submodel, submodel.eResource().getURI());
		request.setParameter(ControlModeRequestParameters.CREATE_SHARD, false);

		ShardResourceHelper helper = new ShardResourceHelper(submodel);
		houseKeeper.cleanUpLater(helper, ShardResourceHelper::close);

		ICommand changeToSubmodel = mgr.getShardModeCommand(request);

		assumeThat("Had redundant profile application before", profile, nullValue());
		assumeThat("No stereotype application before", stereo, notNullValue());
		assumeThat("Wrong resource for stereotype application before", stereo.eResource(), is(class1.eResource()));
		assumeThat("Sub-unit is not a shard", helper.isShard(), is(true));

		assertThat("No command provided", changeToSubmodel, notNullValue());
		assertThat("Command not executable", changeToSubmodel.canExecute(), is(true));

		editorFixture.execute(changeToSubmodel);

		profile = getFirst(submodel.getProfileApplications(), null);

		assertThat("Sub-unit is not a submodel", helper.isShard(), is(false));
		assertThat("No redundant profile application", profile, notNullValue());
		assertThat("Stereotype application is gone", stereo.eResource(), is(class1.eResource()));
		assertThat("Stereotype unapplied", UMLUtil.getBaseElement(stereo), is(class1));
	}
}
