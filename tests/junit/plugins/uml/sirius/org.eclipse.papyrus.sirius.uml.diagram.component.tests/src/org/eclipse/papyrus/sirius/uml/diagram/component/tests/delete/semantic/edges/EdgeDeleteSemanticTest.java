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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.delete.semantic.edges;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticEdgeTest;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to edge deletion for the Component Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/deletion/edges/EdgeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class EdgeDeleteSemanticTest extends AbstractDeleteSemanticEdgeTest {

	private static enum ContainerType {
		PACKAGE, SOURCE, SOURCE_PARENT, TARGET
	}

	private static final String DIAGRAM_NAME = "EdgeDeleteComponentDiagramSirius"; //$NON-NLS-1$

	private final String edgeMappingType;

	private final ContainerType containerType;

	private final EReference containmentFeature;

	public EdgeDeleteSemanticTest(String edgeMappingType, ContainerType containerType, EReference containmentFeature) {
		this.edgeMappingType = edgeMappingType;
		this.containerType = containerType;
		this.containmentFeature = containmentFeature;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteEdgeTest() {
		Optional<DEdge> optionalEdge = this.getElementsFromDiagram(null, this.edgeMappingType).stream()
				.filter(DEdge.class::isInstance)
				.map(DEdge.class::cast)
				.findFirst();
		assertTrue(optionalEdge.isPresent());
		DEdge edge = optionalEdge.get();
		final EObject semanticContainer = switch (this.containerType) {
		case PACKAGE -> this.getModel();
		case SOURCE -> ((DSemanticDecorator) edge.getSourceNode()).getTarget();
		// Use eContainer here to retrieve the parent of the source element in a generic way. Some
		// edges are contained in the parent of their source, which may not be a Package (e.g. a
		// connector edge is contained in the Component that contains its source port/property)
		case SOURCE_PARENT -> ((DSemanticDecorator) edge.getSourceNode()).getTarget().eContainer();
		case TARGET -> ((DSemanticDecorator) edge.getTargetNode()).getTarget();
		};
		this.deleteEdge(edge, semanticContainer, this.containmentFeature);
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ MappingTypes.ABSTRACTION_EDGE_TYPE, ContainerType.SOURCE, UMLPackage.eINSTANCE.getComponent_PackagedElement() },
				{ MappingTypes.COMPONENT_REALIZATION_EDGE_TYPE, ContainerType.TARGET, UMLPackage.eINSTANCE.getComponent_Realization() },
				{ MappingTypes.CONNECTOR_EDGE_TYPE, ContainerType.SOURCE_PARENT, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedConnector() },
				{ MappingTypes.DEPENDENCY_EDGE_TYPE, ContainerType.SOURCE, UMLPackage.eINSTANCE.getComponent_PackagedElement() },
				{ MappingTypes.GENERALIZATION_EDGE_TYPE, ContainerType.SOURCE, UMLPackage.eINSTANCE.getClassifier_Generalization() },
				{ MappingTypes.INTERFACE_REALIZATION_EDGE_TYPE, ContainerType.SOURCE, UMLPackage.eINSTANCE.getBehavioredClassifier_InterfaceRealization() },
				{ MappingTypes.MANIFESTATION_EDGE_TYPE, ContainerType.SOURCE, UMLPackage.eINSTANCE.getComponent_PackagedElement() },
				{ MappingTypes.SUBSTITUTION_EDGE_TYPE, ContainerType.SOURCE, UMLPackage.eINSTANCE.getClassifier_Substitution() },
				{ MappingTypes.USAGE_EDGE_TYPE, ContainerType.SOURCE, UMLPackage.eINSTANCE.getComponent_PackagedElement() }
		});
	}

}
