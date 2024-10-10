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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.drop.semantic;

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
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.SemanticDropToolsIds;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop into the diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/drop/topNodes/topNodeSemanticDrop.profile.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoDiagramTest extends AbstractSemanticTopNodeDropTests {

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String DATATYPE = "DataType1"; //$NON-NLS-1$

	private static final String ENUMERATION = "Enumeration1"; //$NON-NLS-1$

	private static final String METACLASS = "Activity"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PRIMITIVETYPE = "PrimitiveType1"; //$NON-NLS-1$

	private static final String PROFILE = "Profile1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "TopNodeSemanticDropProfileDiagramSirius"; //$NON-NLS-1$

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
		} else if (METACLASS.equals(this.elementToDropName)) {
			toDrop = this.root.getElementImports().get(0);
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
				{ CLASS, SemanticDropToolsIds.DROP__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE, List.of(MappingTypes.CLASS_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, MappingTypes.CLASS_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE) },
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, List.of() },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, List.of() },
				{ DATATYPE, SemanticDropToolsIds.DROP__DATATYPE__TOOL, MappingTypes.DATATYPE_NODE_TYPE, List.of(MappingTypes.DATATYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, MappingTypes.DATATYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE) },
				{ ENUMERATION, SemanticDropToolsIds.DROP__ENUMERATION__TOOL, MappingTypes.ENUMERATION_NODE_TYPE, List.of(MappingTypes.ENUMERATION_NODE_PRD_ENUMERATIONLITERALSCOMPARTMENT_TYPE) },
				{ METACLASS, SemanticDropToolsIds.DROP__ELEMENTIMPORT__TOOL, MappingTypes.METACLASS_NODE_TYPE, List.of() },
				{ PACKAGE, SemanticDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE, List.of(MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE) },
				{ PRIMITIVETYPE, SemanticDropToolsIds.DROP__PRIMITIVETYPE__TOOL, MappingTypes.PRIMITIVETYPE_NODE_TYPE, List.of() },
				{ PROFILE, SemanticDropToolsIds.DROP__PROFILE__TOOL, MappingTypes.PROFILE_NODE_TYPE, List.of(MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE) },
				{ STEREOTYPE, SemanticDropToolsIds.DROP__STEREOTYPE__TOOL, MappingTypes.STEREOTYPE_NODE_TYPE, List.of(MappingTypes.STEREOTYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE) },
		});
	}

}
