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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.delete.semantic.edges;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticEdgeTest;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.MappingTypes;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete edges from model and from the root of Communication diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/deletion/edges/edgesDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class EdgeDeleteSemanticTest extends AbstractDeleteSemanticEdgeTest {

	private static final String INTERACTION = "Interaction1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "DeleteEdgesCommunicationDiagramSirius"; //$NON-NLS-1$

	private final String edgeMappingType;

	private final EReference containmentFeature;


	/**
	 * Default Constructor.
	 *
	 */
	public EdgeDeleteSemanticTest(String edgeMappingType, String containerType, EReference containmentFeature) {
		this.edgeMappingType = edgeMappingType;
		this.containmentFeature = containmentFeature;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteEdgeTest() {
		Optional<DEdge> optionalEdge = getElementsFromDiagram(null, this.edgeMappingType).stream()//
				.filter(DEdge.class::isInstance)//
				.map(DEdge.class::cast)//
				.findFirst();
		assertTrue(optionalEdge.isPresent());
		DEdge edge = optionalEdge.get();
		final EObject semanticInteractionContainer = ((DSemanticDecorator) edge.getSourceNode()).getTarget().eContainer();
		deleteEdge(edge, semanticInteractionContainer, this.containmentFeature);
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ MappingTypes.MESSAGE_EDGE_TYPE, INTERACTION, UMLPackage.eINSTANCE.getInteraction_Message() }
		});
	}
}
