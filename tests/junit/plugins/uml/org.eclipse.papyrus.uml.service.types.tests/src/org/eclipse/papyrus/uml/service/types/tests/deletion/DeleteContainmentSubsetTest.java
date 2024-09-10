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

package org.eclipse.papyrus.uml.service.types.tests.deletion;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.junit.Rule;
import org.junit.Test;

/**
 * Regression tests for deletion of model elements contained in references that are
 * subsets of other mutable (superset) references.
 */
@PluginResource("resource/bug478314/model.di")
public class DeleteContainmentSubsetTest extends AbstractPapyrusTest {

	@Rule
	public final ModelSetFixture fixture = new ServiceRegistryModelSetFixture();

	public DeleteContainmentSubsetTest() {
		super();
	}

	@Test
	public void deleteFromContainmentSubset() {
		Activity activity = (Activity) fixture.getModel().getOwnedType("activity");
		assumeThat(activity, notNullValue());
		ActivityNode ownedNode = activity.getOwnedNode("action");
		assumeThat(ownedNode, notNullValue());

		IElementEditService editService = ElementEditServiceUtils.getCommandProvider(activity);
		ICommand command = editService.getEditCommand(new DestroyElementRequest(ownedNode, false));
		fixture.execute(command);

		assertThat(ownedNode.eResource(), nullValue());
		assertThat(activity.getOwnedNodes(), not(hasItem(ownedNode)));
		assertThat(activity.getNodes(), not(hasItem(ownedNode)));

		fixture.undo();

		assertThat(ownedNode.eResource(), notNullValue());
		assertThat(activity.getOwnedNodes(), hasItem(ownedNode));
		assertThat(activity.getNodes(), hasItem(ownedNode));

		fixture.redo();

		assertThat(ownedNode.eResource(), nullValue());
		assertThat(activity.getOwnedNodes(), not(hasItem(ownedNode)));
		// Properly deleted from the superset again, too
		assertThat(activity.getNodes(), not(hasItem(ownedNode)));
	}

	@Test
	public void deleteFromTwoContainmentSupersetsAtOnce() {
		Activity activity = (Activity) fixture.getModel().getOwnedType("activity");
		assumeThat(activity, notNullValue());
		StructuredActivityNode structuredNode = activity.getStructuredNode("loop");
		assumeThat(structuredNode, notNullValue());

		IElementEditService editService = ElementEditServiceUtils.getCommandProvider(activity);
		ICommand command = editService.getEditCommand(new DestroyElementRequest(structuredNode, false));
		fixture.execute(command);

		assertThat(structuredNode.eResource(), nullValue());
		assertThat(activity.getStructuredNodes(), not(hasItem(structuredNode)));
		assertThat(activity.getNodes(), not(hasItem(structuredNode)));
		assertThat(activity.getGroups(), not(hasItem(structuredNode)));

		fixture.undo();

		assertThat(structuredNode.eResource(), notNullValue());
		assertThat(activity.getStructuredNodes(), hasItem(structuredNode));
		assertThat(activity.getNodes(), hasItem(structuredNode));
		assertThat(activity.getGroups(), hasItem(structuredNode));

		fixture.redo();

		assertThat(structuredNode.eResource(), nullValue());
		assertThat(activity.getStructuredNodes(), not(hasItem(structuredNode)));
		// Properly deleted from both of the supersets again, too
		assertThat(activity.getNodes(), not(hasItem(structuredNode)));
		assertThat(activity.getGroups(), not(hasItem(structuredNode)));
	}
}
