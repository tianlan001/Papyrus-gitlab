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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.delete.semantic.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete nodes from model and from the root of diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/deletion/nodes/NodeDeleteSemanticTest.profile.di")
@RunWith(value = Parameterized.class)
public class TopNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String DATATYPE = "DataType1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String ENUMERATION = "Enumeration1"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PROFILE = "Profile1"; //$NON-NLS-1$

	private static final String PRIMITIVE = "PrimitiveType1"; //$NON-NLS-1$


	private static final Map<String, String> elementTypeToMapping = Map.of(DATATYPE, MappingTypes.DATATYPE_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE, ENUMERATION, MappingTypes.ENUMERATION_NODE_TYPE, STEREOTYPE,
			MappingTypes.STEREOTYPE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, PRIMITIVE, MappingTypes.PRIMITIVETYPE_NODE_TYPE, CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE);

	private static final String DIAGRAM_NAME = "NodeDeleteProfileDiagramSirius"; //$NON-NLS-1$

	private final String nodeMappingType;

	private final String nodeName;

	/**
	 * Default Constructor.
	 *
	 */
	public TopNodeDeleteSemanticTest(String nodeName, String nodeMappingType) {
		this.nodeName = nodeName;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteTopDiagramElementTest() {
		deleteNode(this.nodeName, this.nodeMappingType, false);
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		for (String sourceName : elementTypeToMapping.keySet()) {
			Object[] array = { sourceName, elementTypeToMapping.get(sourceName) };
			data.add(array);
		}
		return data;
	}
}
