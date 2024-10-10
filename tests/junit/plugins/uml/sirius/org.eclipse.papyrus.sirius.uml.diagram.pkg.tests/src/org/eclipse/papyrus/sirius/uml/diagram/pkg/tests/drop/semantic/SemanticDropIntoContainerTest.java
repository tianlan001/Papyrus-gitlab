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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop into sub containers within the Package diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/drop/semantic/semanticDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoContainerTest extends AbstractSemanticDropSubNodeTests {

	private static final String FROM_MODEL = "_FromModel"; //$NON-NLS-1$

	private static final String FROM_PACKAGE = "_FromPackage"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String MODEL = "Model"; //$NON-NLS-1$

	private static final String PACKAGE = "Package"; //$NON-NLS-1$

	private static final String MODEL_CONTAINER = "Model_Container"; //$NON-NLS-1$

	private static final String PACKAGE_CONTAINER = "Package_Container"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SemanticSubNodeDropPackageDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public SemanticDropIntoContainerTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoModel() {
		this.dropElementToSubContainer(MODEL_CONTAINER, FROM_MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoPackage() {
		this.dropElementToSubContainer(PACKAGE_CONTAINER, FROM_PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE);
	}

	/**
	 * Drop the element to a sub container.
	 *
	 * @param semanticOwnerName
	 *            the name of the semantic owner
	 * @param sourceSuffix
	 *            suffix of the element to DnD
	 * @param containerMappingType
	 *            the mapping of the graphical container where the element is dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to drop the element.
	 */
	private void dropElementToSubContainer(String semanticOwnerName, String sourceSuffix, String containerMappingType, String compartmentMappingType) {
		Element rootElement = this.getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticOwner = (Namespace) ((Namespace) rootElement).getMember(semanticOwnerName);
		final Element elementToDrop;
		if (COMMENT.equals(this.elementToDropName)) {
			elementToDrop = semanticOwner.getOwnedComments().get(0);
		} else {
			elementToDrop = semanticOwner.getMember(this.elementToDropName + sourceSuffix);
		}
		this.dropElementToContainer(semanticOwner, elementToDrop, containerMappingType, compartmentMappingType, this.dropToolId, this.nodeMappingType);
	}

	@Parameters(name = "{index} drop semantic element {0} into Subject Container")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ MODEL, SemanticDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE },
				{ PACKAGE, SemanticDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE },
		});
	}

}
