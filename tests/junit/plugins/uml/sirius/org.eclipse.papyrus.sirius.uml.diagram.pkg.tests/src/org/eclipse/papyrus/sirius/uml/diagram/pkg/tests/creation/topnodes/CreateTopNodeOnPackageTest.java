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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.creation.topnodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateTopNodeOnDiagramTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.MappingTypes;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to node creation on the root of a Package diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/creation/topNodes/TopNode_CreationTest.di")
@RunWith(value = Parameterized.class)
public class CreateTopNodeOnPackageTest extends AbstractCreateTopNodeOnDiagramTests {

	/**
	 * The name of the diagram to open.
	 */
	protected static final String DIAGRAM_NAME = "TopNode_PackageDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final List<String> nodeCompartmentTypes;

	private final EReference containmentFeature;

	private final Class<? extends Element> expectedType;

	public CreateTopNodeOnPackageTest(String creationtoolId, String nodeMappingType, List<String> nodeCompartmentTypes, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationtoolId;
		this.nodeMappingType = nodeMappingType;
		this.nodeCompartmentTypes = List.copyOf(nodeCompartmentTypes);
		this.containmentFeature = containmentFeature;
		this.expectedType = expectedType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeContainerTest() {
		final Diagram diagram = this.getDiagram();
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(this.getSemanticOwner(), this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker;
		if (MappingTypes.isNode(this.nodeMappingType)) {
			graphicalNodeCreationChecker = new DNodeCreationChecker(diagram, this.getTopGraphicalContainer(), this.nodeMappingType);
		} else {
			graphicalNodeCreationChecker = new DNodeContainerCreationChecker(diagram, this.getTopGraphicalContainer(), this.nodeMappingType, this.nodeCompartmentTypes);
		}
		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker));
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, Collections.emptyList(), UMLPackage.eINSTANCE.getElement_OwnedComment(), Comment.class },
				{ CreationToolsIds.CREATE__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, Collections.emptyList(), UMLPackage.eINSTANCE.getNamespace_OwnedRule(), Constraint.class },
				{ CreationToolsIds.CREATE__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE, List.of(MappingTypes.MODEL_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENTS_TYPE), UMLPackage.eINSTANCE.getPackage_PackagedElement(), Model.class },
				{ CreationToolsIds.CREATE__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE, List.of(MappingTypes.PACKAGE_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE), UMLPackage.eINSTANCE.getPackage_PackagedElement(), Package.class },
		});
	}
}
