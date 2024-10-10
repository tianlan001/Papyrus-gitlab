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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop into Collaboration container from the CompositeStructure diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/semanticSubNodes/semanticSubNodesDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoCollaborationTest extends AbstractSemanticDropSubNodeTests {

	private static final String COLLABORATION_USE = "CollaborationUse"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String PROPERTY = "Property"; //$NON-NLS-1$

	private static final String COLLABORATION_CONTAINER = "Collaboration_Container"; //$NON-NLS-1$

	private static final String FROM_COLLABORATION = "_FromCollaboration"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SemanticSubNodeDropCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public SemanticDropIntoCollaborationTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoCollaboration() {
		dropElementToCollaborationContainer();
	}

	/**
	 * Drop the element to a Collaboration container.
	 */
	private void dropElementToCollaborationContainer() {
		Element rootElement = this.getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticOwner = (Namespace) ((Namespace) rootElement).getMember(COLLABORATION_CONTAINER);
		final Element elementToDrop;
		if (COMMENT.equals(this.elementToDropName)) {
			elementToDrop = semanticOwner.getOwnedComments().get(0);
		} else if (COLLABORATION_USE.equals(this.elementToDropName)) {
			elementToDrop = ((Collaboration) semanticOwner).getCollaborationUse(this.elementToDropName + FROM_COLLABORATION);
		} else {
			elementToDrop = semanticOwner.getMember(this.elementToDropName + FROM_COLLABORATION);
		}
		this.dropElementToContainer(semanticOwner, elementToDrop, MappingTypes.COLLABORATION_NODE_TYPE, MappingTypes.COLLABORATION_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}


	@Parameters(name = "{index} drop semantic element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COLLABORATION_USE, SemanticDropToolsIds.DROP__COLLABORATION_USE__TOOL, MappingTypes.COLLABORATION_USE_NODE_TYPE },
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ PROPERTY, SemanticDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE },
		});
	}

}
