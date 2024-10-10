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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.drop.semantic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticTopNodeDropTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.SemanticDropToolsIds;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop into the CompositeStructure diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/semanticTopNodes/semanticTopNodesDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoDiagramTest extends AbstractSemanticTopNodeDropTests {

	private static final String ACTIVITY = "Activity1"; //$NON-NLS-1$

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String COLLABORATION = "Collaboration1"; //$NON-NLS-1$

	private static final String COMMENT = "Comment1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR = "FunctionBehavior1"; //$NON-NLS-1$

	private static final String INFORMATIONITEM = "InformationItem1"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction1"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR = "OpaqueBehavior1"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine1"; //$NON-NLS-1$

	private static final String PROTOCOLSTATEMACHINE = "ProtocolStateMachine1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SemanticTopNodeDropCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	private final List<String> nodeCompartmentTypes;

	/**
	 * Constructor.
	 *
	 */
	public SemanticDropIntoDiagramTest(String elementToDropName, String dropToolId, String nodeMappingType, List<String> nodeCompartmentTypes) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
		this.nodeCompartmentTypes = List.copyOf(nodeCompartmentTypes);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void dropElementIntoDiagram() {
		checkSiriusDiagramSynchronization(false);
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		Assert.assertNotNull(siriusDiagram);
		Assert.assertFalse("The diagram must be unsynchronized to test drop", siriusDiagram.isSynchronized()); //$NON-NLS-1$
		final Element toDrop;
		if (COMMENT.equals(this.elementToDropName)) {
			toDrop = this.root.getOwnedComments().get(0);
		} else {
			toDrop = this.root.getMember(this.elementToDropName);
		}
		Diagram diagram = getDiagram();
		IGraphicalRepresentationElementCreationChecker checker;
		if (MappingTypes.isNode(this.nodeMappingType)) {
			checker = new DNodeCreationChecker(diagram, siriusDiagram, this.nodeMappingType);
		} else {
			checker = new DNodeContainerCreationChecker(diagram, siriusDiagram, this.nodeMappingType, this.nodeCompartmentTypes);
		}
		dropNode(this.dropToolId, checker, toDrop, siriusDiagram);
	}


	@Parameters(name = "{index} drop semantic element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY, SemanticDropToolsIds.DROP__ACTIVITY__TOOL, MappingTypes.ACTIVITY_NODE_TYPE, List.of(MappingTypes.ACTIVITY_NODE_CSD_COMPARTMENTS_TYPE) },
				{ CLASS, SemanticDropToolsIds.DROP__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE, List.of(MappingTypes.CLASS_NODE_CSD_COMPARTMENTS_TYPE) },
				{ COLLABORATION, SemanticDropToolsIds.DROP__COLLABORATION__TOOL, MappingTypes.COLLABORATION_NODE_TYPE, List.of(MappingTypes.COLLABORATION_NODE_CSD_COMPARTMENTS_TYPE) },
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, List.of() },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, List.of() },
				{ FUNCTIONBEHAVIOR, SemanticDropToolsIds.DROP__FUNCTION_BEHAVIOR__TOOL, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, List.of(MappingTypes.FUNCTION_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE) },
				{ INFORMATIONITEM, SemanticDropToolsIds.DROP__INFORMATION_ITEM__TOOL, MappingTypes.INFORMATION_ITEM_NODE_TYPE, List.of() },
				{ INTERACTION, SemanticDropToolsIds.DROP__INTERACTION__TOOL, MappingTypes.INTERACTION_NODE_TYPE, List.of(MappingTypes.INTERACTION_NODE_CSD_COMPARTMENTS_TYPE) },
				{ OPAQUEBEHAVIOR, SemanticDropToolsIds.DROP__OPAQUE_BEHAVIOR__TOOL, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, List.of(MappingTypes.OPAQUE_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE) },
				{ PROTOCOLSTATEMACHINE, SemanticDropToolsIds.DROP__PROTOCOL_STATE_MACHINE__TOOL, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE, List.of(MappingTypes.PROTOCOL_STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE) },
				{ STATEMACHINE, SemanticDropToolsIds.DROP__STATE_MACHINE__TOOL, MappingTypes.STATE_MACHINE_NODE_TYPE, List.of(MappingTypes.STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE) },
		});
	}

}
