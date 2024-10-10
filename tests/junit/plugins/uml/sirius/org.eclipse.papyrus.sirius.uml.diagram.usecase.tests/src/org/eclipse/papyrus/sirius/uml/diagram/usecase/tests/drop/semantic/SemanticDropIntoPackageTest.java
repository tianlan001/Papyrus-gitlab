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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop into a Package container node.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/semanticSubNodes/semanticSubNodesDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoPackageTest extends AbstractSemanticDropSubNodeTests {

	private static final String ACTIVITY = "Activity_FromPackage"; //$NON-NLS-1$

	private static final String ACTOR = "Actor_FromPackage"; //$NON-NLS-1$

	private static final String CLASS = "Class_FromPackage"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint_FromPackage"; //$NON-NLS-1$

	private static final String COMMENT = "Comment_FromPackage"; //$NON-NLS-1$

	private static final String COMPONENT = "Component_FromPackage"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction_FromPackage"; //$NON-NLS-1$

	private static final String PACKAGE = "Package_FromPackage"; //$NON-NLS-1$

	private static final String PACKAGE_CONTAINER = "Package_Container"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine_FromPackage"; //$NON-NLS-1$

	private static final String USECASE = "UseCase_FromPackage"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SemanticSubNodeDropUseCaseDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public SemanticDropIntoPackageTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoPackage() {
		dropElementToPackageContainer();
	}

	/**
	 * Drop the element to a Package container.
	 */
	private void dropElementToPackageContainer() {
		Element rootElement = getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticOwner = (Namespace) ((Namespace) rootElement).getMember(PACKAGE_CONTAINER);
		final Element elementToDrop;
		if (COMMENT.equals(this.elementToDropName)) {
			elementToDrop = semanticOwner.getOwnedComments().get(0);
		} else {
			elementToDrop = semanticOwner.getMember(this.elementToDropName);
		}
		this.dropElementToContainer(semanticOwner, elementToDrop, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PACKAGEDELEMENTS_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Parameters(name = "{index} drop semantic element {0} into Package Container")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY, SemanticDropToolsIds.DROP__ACTIVITY__TOOL, MappingTypes.ACTIVITY_NODE_TYPE },
				{ ACTOR, SemanticDropToolsIds.DROP__ACTOR__TOOL, MappingTypes.ACTOR_NODE_TYPE },
				{ CLASS, SemanticDropToolsIds.DROP__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE },
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ COMPONENT, SemanticDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ INTERACTION, SemanticDropToolsIds.DROP__INTERACTION__TOOL, MappingTypes.INTERACTION_NODE_TYPE },
				{ PACKAGE, SemanticDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE },
				{ STATEMACHINE, SemanticDropToolsIds.DROP__STATE_MACHINE__TOOL, MappingTypes.STATE_MACHINE_NODE_TYPE },
				{ USECASE, SemanticDropToolsIds.DROP__USE_CASE__TOOL, MappingTypes.USE_CASE_NODE_TYPE },
		});
	}
}
