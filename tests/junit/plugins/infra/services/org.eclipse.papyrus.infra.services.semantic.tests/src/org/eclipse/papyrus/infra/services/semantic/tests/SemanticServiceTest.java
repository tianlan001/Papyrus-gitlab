/*****************************************************************************
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
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.semantic.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.services.semantic.service.SemanticService;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Tests for the Semantic Service.
 */
@PluginResource("resources/model.di")
public class SemanticServiceTest {

	@ClassRule
	public static final ModelSetFixture fixture = new ServiceRegistryModelSetFixture();

	@Test
	public void getSemanticRoots() {
		SemanticService service = fixture.requireService(SemanticService.class);
		List<EObject> roots = Arrays.asList(service.getSemanticRoots());

		// This is semantic content
		assertThat(roots, hasItem(instanceOf(Model.class)));
		assertThat(roots, hasItem(instanceOf(Profile.class)));

		// Stereotype applications are semantic content, but not roots
		assertThat(roots, not(hasItem(isStereotypeApplication())));

		// Notation views are not semantic content
		assertThat(roots, not(hasItem(instanceOf(Diagram.class))));
		assertThat(roots, not(hasItem(instanceOf(Table.class))));
	}

	@Test
	public void getSemanticIModels() {
		SemanticService service = fixture.requireService(SemanticService.class);
		List<String> modelIDs = Stream.of(service.getSemanticIModels())
				.map(IModel::getIdentifier)
				.collect(Collectors.toList());

		assertThat(modelIDs, hasItem(UmlModel.MODEL_ID));

		assertThat(modelIDs, not(hasItem(NotationModel.MODEL_ID)));
	}

	//
	// Test framework
	//

	static Matcher<EObject> isStereotypeApplication() {
		return new BaseMatcher<EObject>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("is stereotype application");
			}

			@Override
			public boolean matches(Object item) {
				return (item instanceof EObject) && (UMLUtil.getStereotype((EObject) item) != null);
			}
		};
	}

}
