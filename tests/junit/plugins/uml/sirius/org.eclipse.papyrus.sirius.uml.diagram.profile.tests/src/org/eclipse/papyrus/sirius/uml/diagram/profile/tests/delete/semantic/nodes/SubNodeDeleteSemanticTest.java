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

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete sub-nodes from model and from container of diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/deletion/nodes/SubNodeDeleteSemanticTest.profile.di")
@RunWith(value = Parameterized.class)
public class SubNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String PROFILE = "Profile1"; //$NON-NLS-1$

	private static final String ENUMERATION = "Enumeration1"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype1"; //$NON-NLS-1$

	private static final String DATATYPE = "DataType1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PRIMITIVETYPE = "PrimitiveType1"; //$NON-NLS-1$

	private static final String PROPERTY = "Property1"; //$NON-NLS-1$

	private static final String OPERATION = "Operation1"; //$NON-NLS-1$

	private static final String ENUMERATIONLITERAL = "EnumerationLiteral1"; //$NON-NLS-1$


	private static final String DIAGRAM_NAME = "SubNodeDeleteProfileDiagramSirius"; //$NON-NLS-1$

	private final String subNodeName;

	private final String subNodeMappingType;

	private final String containerName;

	private final String containerMappingTypeName;

	private final String compartmentMappingTypeName;

	/**
	 * Constructor.
	 *
	 */
	public SubNodeDeleteSemanticTest(String subNodeName, String subNodeMappingType, String containerName, String containerMappingTypeName, String compartmentMappingTypeName) {
		this.subNodeName = subNodeName;
		this.subNodeMappingType = subNodeMappingType;
		this.containerName = containerName;
		this.containerMappingTypeName = containerMappingTypeName;
		this.compartmentMappingTypeName = compartmentMappingTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteSubDiagramElementTest() {
		DDiagramElement compartment = getContainer();
		deleteNode(this.subNodeName, this.subNodeMappingType, compartment, false);
	}

	private DDiagramElement getContainer() {
		DNodeContainer container = (DNodeContainer) getNodeFromDiagram(this.containerName, this.containerMappingTypeName);
		final DDiagramElement compartment;
		if (this.compartmentMappingTypeName != null) {
			compartment = container.getOwnedDiagramElements().stream().filter(element -> this.compartmentMappingTypeName.equals(element.getDiagramElementMapping().getName())).findFirst().orElse(null);
		} else {
			compartment = container.getOwnedDiagramElements().get(0);
		}
		return compartment;
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ENUMERATION, MappingTypes.ENUMERATION_NODE_TYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, null },
				{ PROFILE, MappingTypes.PROFILE_NODE_TYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, null },
				{ STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, null },
				{ DATATYPE, MappingTypes.DATATYPE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, null },
				{ PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, null },
				{ PRIMITIVETYPE, MappingTypes.PRIMITIVETYPE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, null },
				{ CLASS, MappingTypes.CLASS_NODE_TYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, null },
				{ PROPERTY, MappingTypes.PROPERTY_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE },
				{ OPERATION, MappingTypes.OPERATION_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE },
				{ ENUMERATIONLITERAL, MappingTypes.ENUMERATIONLITERAL_NODE_TYPE, ENUMERATION, MappingTypes.ENUMERATION_NODE_TYPE, null }
		});
	}
}
