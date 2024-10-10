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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.creation.topnodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateTopNodeOnDiagramTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all Tests about allowed creations on the root of the Diagram represented by a {@link Model}.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/topNodes/TopNode_CreationTest.profile.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class CreateTopNodeContainerOnProfileTest extends AbstractCreateTopNodeOnDiagramTests {

	/**
	 * The name of the diagram to open
	 */
	protected static final String DIAGRAM_NAME = "TopNode_ProfileDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final List<String> nodeCompartmentTypes;

	private final EReference containmentFeature;

	private final Class<? extends Element> expectedType;


	/**
	 * Default Constructor.
	 *
	 */
	public CreateTopNodeContainerOnProfileTest(String creationToolId, String nodeMappingType, List<String> nodeCompartmentTypes, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.nodeCompartmentTypes = List.copyOf(nodeCompartmentTypes);
		this.containmentFeature = containmentFeature;
		this.expectedType = expectedType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeContainerTest() {
		final Diagram diagram = getDiagram();
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeContainerCreationChecker(diagram, getTopGraphicalContainer(), nodeMappingType, nodeCompartmentTypes);
		createNode(creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker));
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE, List.of(MappingTypes.CLASS_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, MappingTypes.CLASS_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE), UMLPackage.eINSTANCE.getPackage_PackagedElement(),
						org.eclipse.uml2.uml.Class.class },
				{ CreationToolsIds.CREATE__DATATYPE__TOOL, MappingTypes.DATATYPE_NODE_TYPE, List.of(MappingTypes.DATATYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, MappingTypes.DATATYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE),
						UMLPackage.eINSTANCE.getPackage_PackagedElement(), DataType.class },
				{ CreationToolsIds.CREATE__ENUMERATION__TOOL, MappingTypes.ENUMERATION_NODE_TYPE, List.of(MappingTypes.ENUMERATION_NODE_PRD_ENUMERATIONLITERALSCOMPARTMENT_TYPE),
						UMLPackage.eINSTANCE.getPackage_PackagedElement(), Enumeration.class },
				{ CreationToolsIds.CREATE__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE, List.of(MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE),
						UMLPackage.eINSTANCE.getPackage_PackagedElement(), org.eclipse.uml2.uml.Package.class },
				{ CreationToolsIds.CREATE__PROFILE__TOOL, MappingTypes.PROFILE_NODE_TYPE, List.of(MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE),
						UMLPackage.eINSTANCE.getPackage_PackagedElement(), Profile.class },
				{ CreationToolsIds.CREATE__STEREOTYPE__TOOL, MappingTypes.STEREOTYPE_NODE_TYPE, List.of(MappingTypes.STEREOTYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE),
						UMLPackage.eINSTANCE.getPackage_PackagedElement(), Stereotype.class },
		});
	}

}
