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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.delete.semantic.nodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to sub-node deletion on the root of a Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/deletion/nodes/SubNodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class SubNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String INTERFACE = "Interface1"; //$NON-NLS-1$

	private static final String MODEL = "Model1"; //$NON-NLS-1$

	private static final String OPERATION = "Operation1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PROPERTY = "Property1"; //$NON-NLS-1$

	private static final String PORT = "Port1"; //$NON-NLS-1$

	private static final String RECEPTION = "Reception1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodeDeleteComponentDiagramSirius"; //$NON-NLS-1$

	private final String subNodeName;

	private final String subNodeMappingType;

	private final String containerName;

	private final String containerMappingType;

	private final String compartmentMappingType;

	public SubNodeDeleteSemanticTest(String subNodeName, String subNodeMappingType, String containerName, String containerMappingType, String compartmentMappingType) {
		this.subNodeName = subNodeName;
		this.subNodeMappingType = subNodeMappingType;
		this.containerName = containerName;
		this.containerMappingType = containerMappingType;
		this.compartmentMappingType = compartmentMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteSubDiagramElementTest() {
		DDiagramElement compartment = this.getContainer();
		this.deleteNode(this.subNodeName, this.subNodeMappingType, compartment, false);
	}

	private DDiagramElement getContainer() {
		DDiagramElement result;
		DNodeContainer container = (DNodeContainer) this.getNodeFromDiagram(this.containerName, this.containerMappingType);
		if (MappingTypes.isBorderNode(this.subNodeMappingType)) {
			result = container;
		} else if (this.compartmentMappingType != null) {
			result = container.getOwnedDiagramElements().stream().filter(element -> this.compartmentMappingType.equals(element.getDiagramElementMapping().getName())).findFirst().orElse(null);
		} else {
			result = container.getOwnedDiagramElements().get(0);
		}
		return result;
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE },
				{ PROPERTY, MappingTypes.PROPERTY_NODE_TYPE, COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE },
				{ PORT, MappingTypes.PORT_NODE_TYPE, COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, null },
				{ PROPERTY, MappingTypes.PROPERTY_IN_INTERFACE_NODE_TYPE, INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE },
				{ OPERATION, MappingTypes.OPERATION_NODE_TYPE, INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_OPERATIONS_COMPARTMENT_TYPE },
				{ RECEPTION, MappingTypes.RECEPTION_NODE_TYPE, INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_RECEPTION_COMPARTMENT_TYPE },
				{ COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE },
				{ INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE },
				{ MODEL, MappingTypes.MODEL_NODE_TYPE, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE },
				{ PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE },
				{ COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE },
				{ INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE },
				{ MODEL, MappingTypes.MODEL_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE },
				{ PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE }
		});
	}
}
