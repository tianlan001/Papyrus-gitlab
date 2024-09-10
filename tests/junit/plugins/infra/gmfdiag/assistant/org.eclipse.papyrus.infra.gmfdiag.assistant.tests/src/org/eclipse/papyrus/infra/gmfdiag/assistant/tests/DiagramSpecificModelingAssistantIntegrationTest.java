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

package org.eclipse.papyrus.infra.gmfdiag.assistant.tests;

import static org.eclipse.papyrus.infra.gmfdiag.assistant.tests.ElementTypesUtil.hasID;
import static org.eclipse.papyrus.infra.gmfdiag.assistant.tests.ElementTypesUtil.requireType;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.util.IProxyElementType;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Integration-type tests for the whole diagram assistant stack, from assistant models to the model-based assistant provider via the
 * GMF Modeling Assistant Service, for assistants generated for a particular diagram.
 */
@PluginResource("resources/model.di")
public class DiagramSpecificModelingAssistantIntegrationTest extends AbstractModelingAssistantIntegrationTest {

	@ClassRule
	public static final AssistantsFixture assistantsFixture = new AssistantsFixture("/resources/j2ee-usecase.assistants");

	public DiagramSpecificModelingAssistantIntegrationTest() {
		super();
	}

	@Test
	public void popupOnShape() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getTypesForPopupBar(usecase);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.BranchPoint_3007")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.BranchPoint_3008")).and(not(instanceOf(IProxyElementType.class)))));
	}

	@Test
	public void popupOnDiagramSurface() {
		IAdaptable diagram = getDiagramEditPartSurrogate();
		List<IElementType> types = getTypesForPopupBar(diagram);

		// Both visual presentations of a use case (as ellipse and as classifier) for the WebScenario stereotype
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario_2013")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario_2014")).and(not(instanceOf(IProxyElementType.class)))));
	}

	@Test
	public void connectionFromSource() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getRelTypesOnSource(usecase);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Web_4011")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Import_4013")).and(not(instanceOf(IProxyElementType.class)))));
	}

	@Test
	public void connectionToTarget() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getRelTypesOnTarget(usecase);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Import_4013")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Web_4011")).and(not(instanceOf(IProxyElementType.class)))));
	}

	@Test
	public void connectionFromSourceToTarget() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		IAdaptable actor = getEditPartSurrogate(getActor("User1"));
		List<IElementType> types = getRelTypesOnSourceAndTarget(usecase, actor);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Web_4011")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Import_4013")).and(not(instanceOf(IProxyElementType.class)))));
	}

	@Test
	public void targetForConnectionFromSource() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getTypesForTarget(usecase, requireType("org.eclipse.papyrus.example.j2ee.Web_4011"));

		// A couple of use case presentations (ellipse and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario_2013")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario_2014")).and(not(instanceOf(IProxyElementType.class)))));

		// A couple of actor presentations (stick-man and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User_2011")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User_2012")).and(not(instanceOf(IProxyElementType.class)))));
	}


	@Test
	public void sourceForConnectionToTarget() {
		IAdaptable actor = getEditPartSurrogate(getActor("User1"));
		List<IElementType> types = getTypesForTarget(actor, requireType("org.eclipse.papyrus.example.j2ee.Import_4013"));

		// A couple of use case presentations (ellipse and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario_2013")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario_2014")).and(not(instanceOf(IProxyElementType.class)))));

		// A couple of actor presentations (stick-man and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User_2011")).and(not(instanceOf(IProxyElementType.class)))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User_2012")).and(not(instanceOf(IProxyElementType.class)))));
	}
}
