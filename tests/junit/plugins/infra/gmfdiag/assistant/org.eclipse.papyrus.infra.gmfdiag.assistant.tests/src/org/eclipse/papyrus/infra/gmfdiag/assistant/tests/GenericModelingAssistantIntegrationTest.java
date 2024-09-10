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
import static org.eclipse.papyrus.infra.gmfdiag.assistant.tests.ElementTypesUtil.isProxyType;
import static org.eclipse.papyrus.infra.gmfdiag.assistant.tests.ElementTypesUtil.specializes;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Integration-type tests for the whole diagram assistant stack, from assistant models to the model-based assistant provider via the
 * GMF Modeling Assistant Service, for generically defined assistants (not specific to a particular diagram).
 */
@PluginResource("resources/model.di")
public class GenericModelingAssistantIntegrationTest extends AbstractModelingAssistantIntegrationTest {

	@ClassRule
	public static final AssistantsFixture assistantsFixture = new AssistantsFixture("/resources/j2ee-all.assistants");

	public GenericModelingAssistantIntegrationTest() {
		super();
	}

	@Test
	public void popupOnShape() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getTypesForPopupBar(usecase);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.BranchPoint")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.BranchPoint"), hasID("org.eclipse.papyrus.umldi.ExtensionPoint_ExtensionPointLabel")))));
	}

	@Test
	public void popupOnDiagramSurface() {
		IAdaptable diagram = getDiagramEditPartSurrogate();
		List<IElementType> types = getTypesForPopupBar(diagram);

		// Both visual presentations of a use case (as ellipse and as classifier) for the WebScenario stereotype
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.WebScenario"), hasID("org.eclipse.papyrus.umldi.UseCase_Shape")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.WebScenario"), hasID("org.eclipse.papyrus.umldi.UseCase_ClassifierShape")))));
	}

	@Test
	public void connectionFromSource() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getRelTypesOnSource(usecase);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Web")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.Web"), hasID("org.eclipse.papyrus.umldi.Association_Edge")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Import")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.Import"), hasID("org.eclipse.papyrus.umldi.Dependency_Edge")))));
	}

	@Test
	public void connectionToTarget() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getRelTypesOnTarget(usecase);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Import")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.Import"), hasID("org.eclipse.papyrus.umldi.Dependency_Edge")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Web")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.Web"), hasID("org.eclipse.papyrus.umldi.Association_Edge")))));
	}

	@Test
	public void connectionFromSourceToTarget() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		IAdaptable actor = getEditPartSurrogate(getActor("User1"));
		List<IElementType> types = getRelTypesOnSourceAndTarget(usecase, actor);

		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Web")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.Web"), hasID("org.eclipse.papyrus.umldi.Association_Edge")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.Import")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.Import"), hasID("org.eclipse.papyrus.umldi.Dependency_Edge")))));
	}

	@Test
	@FailingTest
	public void targetForConnectionFromSource() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getTypesForTarget(usecase, getProxyType("org.eclipse.papyrus.example.j2ee.Web", "org.eclipse.papyrus.umldi.Association_Edge"));

		// A couple of use case presentations (ellipse and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.WebScenario"), hasID("org.eclipse.papyrus.umldi.UseCase_Shape")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.WebScenario"), hasID("org.eclipse.papyrus.umldi.UseCase_ClassifierShape")))));

		// A couple of actor presentations (stick-man and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.User"), hasID("org.eclipse.papyrus.umldi.Actor_Shape")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.User"), hasID("org.eclipse.papyrus.umldi.Actor_ClassifierShape")))));
	}

	@Test
	@FailingTest
	public void sourceForConnectionToTarget() {
		IAdaptable actor = getEditPartSurrogate(getActor("User1"));
		List<IElementType> types = getTypesForTarget(actor, getProxyType("org.eclipse.papyrus.example.j2ee.Import", "org.eclipse.papyrus.umldi.Dependency_Edge"));

		// A couple of use case presentations (ellipse and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.WebScenario"), hasID("org.eclipse.papyrus.umldi.UseCase_Shape")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.WebScenario")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.WebScenario"), hasID("org.eclipse.papyrus.umldi.UseCase_ClassifierShape")))));

		// A couple of actor presentations (stick-man and classifier)
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.User"), hasID("org.eclipse.papyrus.umldi.Actor_Shape")))));
		assertThat(types, hasItem(both(hasID("org.eclipse.papyrus.example.j2ee.User")).and(
				isProxyType(hasID("org.eclipse.papyrus.example.j2ee.User"), hasID("org.eclipse.papyrus.umldi.Actor_ClassifierShape")))));
	}

	@Test
	public void excludedConnectionFromSource() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getRelTypesOnSource(usecase);

		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Association_BranchEdge"))));
		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Dependency_BranchEdge"))));
	}

	@Test
	public void excludedConnectionToTarget() {
		IAdaptable actor = getEditPartSurrogate(getActor("User1"));
		List<IElementType> types = getRelTypesOnTarget(actor);

		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Association_BranchEdge"))));
		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Dependency_BranchEdge"))));
	}

	@Test
	public void excludedConnectionFromSourceToTarget() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		IAdaptable actor = getEditPartSurrogate(getActor("User1"));
		List<IElementType> types = getRelTypesOnSourceAndTarget(usecase, actor);

		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Association_BranchEdge"))));
		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Dependency_BranchEdge"))));
	}

	@Test
	public void excludedTargetForConnectionFromSource() {
		IAdaptable usecase = getEditPartSurrogate(getUseCase("WebScenario1"));
		List<IElementType> types = getTypesForTarget(usecase, getProxyType("org.eclipse.papyrus.example.j2ee.Web", "org.eclipse.papyrus.umldi.Association_Edge"));

		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Association_Shape"))));
	}

	@Test
	public void excludedSourceForConnectionToTarget() {
		IAdaptable actor = getEditPartSurrogate(getActor("User1"));
		List<IElementType> types = getTypesForTarget(actor, getProxyType("org.eclipse.papyrus.example.j2ee.Import", "org.eclipse.papyrus.umldi.Dependency_Edge"));

		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Dependency_Shape"))));
	}

	@Test
	public void excludedPopupOnDiagramSurface() {
		IAdaptable diagram = getDiagramEditPartSurrogate();
		List<IElementType> types = getTypesForPopupBar(diagram);

		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Association_Shape"))));
		assertThat(types, not(hasItem(specializes("org.eclipse.papyrus.umldi.Dependency_Shape"))));
	}
}
