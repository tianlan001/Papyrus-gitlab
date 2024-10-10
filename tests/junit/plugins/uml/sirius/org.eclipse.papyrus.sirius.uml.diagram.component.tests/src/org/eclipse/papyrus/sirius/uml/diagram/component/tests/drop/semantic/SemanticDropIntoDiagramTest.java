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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.drop.semantic;

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
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.SemanticDropToolsIds;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to semantic drop into the root of the Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/drop/topNodes/topNodeSemanticDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoDiagramTest extends AbstractSemanticTopNodeDropTests {

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String INTERFACE = "Interface1"; //$NON-NLS-1$

	private static final String MODEL = "Model1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "TopNodeSemanticDropComponentDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	private final List<String> nodeCompartmentTypes;

	public SemanticDropIntoDiagramTest(String elementToDropName, String dropToolId, String nodeMappingType, List<String> nodeCompartmentTypes) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
		this.nodeCompartmentTypes = nodeCompartmentTypes;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void dropElementIntoDiagram() {
		this.checkSiriusDiagramSynchronization(false);
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		Assert.assertNotNull(siriusDiagram);
		Assert.assertFalse("The diagram must be unsynchronized to test drop", siriusDiagram.isSynchronized()); //$NON-NLS-1$
		final Element toDrop;
		if (COMMENT.equals(this.elementToDropName)) {
			toDrop = this.root.getOwnedComments().get(0);
		} else {
			toDrop = this.root.getMember(this.elementToDropName);
		}
		Diagram diagram = this.getDiagram();
		IGraphicalRepresentationElementCreationChecker checker;
		if (MappingTypes.isNode(this.nodeMappingType)) {
			checker = new DNodeCreationChecker(diagram, siriusDiagram, this.nodeMappingType);
		} else {
			checker = new DNodeContainerCreationChecker(diagram, siriusDiagram, this.nodeMappingType, this.nodeCompartmentTypes);
		}
		this.dropNode(this.dropToolId, checker, toDrop, siriusDiagram);
	}

	@Parameters(name = "{index} drop semantic element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, List.of() },
				{ COMPONENT, SemanticDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE, List.of(MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE) },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, List.of() },
				{ INTERFACE, SemanticDropToolsIds.DROP__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE,
						List.of(MappingTypes.INTERFACE_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, MappingTypes.INTERFACE_NODE_CPD_OPERATIONS_COMPARTMENT_TYPE, MappingTypes.INTERFACE_NODE_CPD_RECEPTION_COMPARTMENT_TYPE) },
				{ MODEL, SemanticDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE, List.of(MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE) },
				{ PACKAGE, SemanticDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE, List.of(MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE) }
		});
	}
}
