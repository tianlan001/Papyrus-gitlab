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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.creation.subnodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.MappingTypes;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of sub-nodes for Communication diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class SubNodesCreationTest extends AbstractCreateNodeTests {

	private static final String INTERACTION1 = "Interaction1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodeCommunicationDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final EReference containmentFeature;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	/**
	 * Default Constructor.
	 *
	 */
	public SubNodesCreationTest(String creationToolId, String nodeMappingType, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.containmentFeature = containmentFeature;
		this.expectedType = expectedType;
	}

	@Override
	protected Element getSemanticOwner() {
		return this.root.getMember(this.semanticOwnerName);
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		return (DNodeContainer) getNodeFromDiagram(INTERACTION1, MappingTypes.INTERACTION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoInteraction() {
		createNodeIntoContainer(INTERACTION1, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_COD_COMPARTMENTS_TYPE); // $NON-NLS-1$
	}


	private void createNodeIntoContainer(final String semanticOwnerName, final String nodeContainerType, final String nodeCompartmentContainerType) {
		final String containerType = nodeCompartmentContainerType;
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = getSubNodeOfGraphicalContainer(containerType);
		Element semanticOwner = getSemanticOwner();
		if (this.creationToolId.equals(CreationToolsIds.CREATE__DURATION_OBSERVATION__TOOL) || this.creationToolId.equals(CreationToolsIds.CREATE__TIME_OBSERVATION__TOOL)) {
			// Observations are stored at the Model root, not in Interaction
			semanticOwner = this.root;
		}
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(semanticOwner, this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(getDiagram(), graphicalContainer, this.nodeMappingType);
		createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, UMLPackage.eINSTANCE.getElement_OwnedComment(), Comment.class },
				{ CreationToolsIds.CREATE__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, UMLPackage.eINSTANCE.getNamespace_OwnedRule(), Constraint.class },
				{ CreationToolsIds.CREATE__DURATION_OBSERVATION__TOOL, MappingTypes.DURATION_OBSERVATION_NODE_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), DurationObservation.class },
				{ CreationToolsIds.CREATE__LIFELINE__TOOL, MappingTypes.LIFELINE_NODE_TYPE, UMLPackage.eINSTANCE.getInteraction_Lifeline(), Lifeline.class },
				{ CreationToolsIds.CREATE__TIME_OBSERVATION__TOOL, MappingTypes.TIME_OBSERVATION_NODE_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), TimeObservation.class }
		});
	}

}
