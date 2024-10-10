/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.delete.semantic;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticEdgeTest;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete edges from model and from the root of diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/deletion/nodes/nodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class EdgeDeleteSemanticTest extends AbstractDeleteSemanticEdgeTest {

	private static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "NodeDeleteActivityDiagramSirius"; //$NON-NLS-1$

	private final String edgeMappingType;

	private final EReference containmentFeature;


	/**
	 * Default Constructor.
	 *
	 */
	public EdgeDeleteSemanticTest(String edgeMappingType, EReference containmentFeature) {
		this.edgeMappingType = edgeMappingType;
		this.containmentFeature = containmentFeature;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteEdgeTest() {
		Optional<DEdge> optionalEdge = getElementsFromDiagram(null, this.edgeMappingType).stream()
				.filter(DEdge.class::isInstance)
				.map(DEdge.class::cast)
				.findFirst();
		assertTrue(optionalEdge.isPresent());
		DEdge edge = optionalEdge.get();
		Activity activity = (Activity) this.fixture.getModel().getMember(ROOT_ACTIVITY);
		deleteEdge(edge, activity, this.containmentFeature);
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ MappingTypes.CONTROL_FLOW_EDGE_TYPE, UMLPackage.eINSTANCE.getActivity_Edge() },
				{ MappingTypes.OBJECT_FLOW_EDGE_TYPE, UMLPackage.eINSTANCE.getActivity_Edge() },
		});
	}
}
