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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.drop.graphical;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.junit.Test;

/**
 * Groups all tests related to graphical drop in the Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
public class GraphicalDropTest extends AbstractGraphicalDropNodeTests {

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String COMPONENT_IN_MODEL = "ComponentM1"; //$NON-NLS-1$

	private static final String COMPONENT_IN_PACKAGE = "ComponentP1"; //$NON-NLS-1$

	private static final String COMPONENT_TO_DROP_ELEMENTS = "Component2"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String CONSTRAINT_IN_MODEL = "ConstraintM1"; //$NON-NLS-1$

	private static final String CONSTRAINT_IN_PACKAGE = "ConstraintP1"; //$NON-NLS-1$

	private static final String INTERFACE = "Interface1"; //$NON-NLS-1$

	private static final String INTERFACE_IN_MODEL = "InterfaceM1"; //$NON-NLS-1$

	private static final String INTERFACE_IN_PACKAGE = "InterfaceP1"; //$NON-NLS-1$

	private static final String INTERFACE_TO_DROP_ELEMENTS = "Interface2"; //$NON-NLS-1$

	private static final String MODEL = "Model1"; //$NON-NLS-1$

	private static final String MODEL_IN_MODEL = "ModelM1"; //$NON-NLS-1$

	private static final String MODEL_IN_PACKAGE = "ModelP1"; //$NON-NLS-1$

	private static final String MODEL_TO_DROP_ELEMENTS = "Model2"; //$NON-NLS-1$

	private static final String OPERATION = "Operation1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PACKAGE_IN_MODEL = "PackageM1"; //$NON-NLS-1$

	private static final String PACKAGE_IN_PACKAGE = "PackageP1"; //$NON-NLS-1$

	private static final String PACKAGE_TO_DROP_ELEMENTS = "Package2"; //$NON-NLS-1$

	private static final String PROPERTY_IN_COMPONENT = "PropertyC1"; //$NON-NLS-1$

	private static final String PROPERTY_IN_INTERFACE = "PropertyI1"; //$NON-NLS-1$

	private static final String RECEPTION = "Reception1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropComponentDiagramSirius"; //$NON-NLS-1$

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromDiagramToComponent() {
		this.dropElementIntoContainer(COMPONENT, COMPONENT_TO_DROP_ELEMENTS, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL,
				MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromDiagramToModel() {
		this.dropElementIntoContainer(COMPONENT, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromDiagramToPackage() {
		this.dropElementIntoContainer(COMPONENT, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL,
				MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromModelToComponent() {
		this.dropElementIntoContainer(COMPONENT_IN_MODEL, COMPONENT_TO_DROP_ELEMENTS, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromModelToDiagram() {
		this.dropElementToDiagram(COMPONENT_IN_MODEL, GraphicalDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromModelToModel() {
		this.dropElementIntoContainer(COMPONENT_IN_MODEL, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromModelToPackage() {
		this.dropElementIntoContainer(COMPONENT_IN_MODEL, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL,
				MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromPackageToComponent() {
		this.dropElementIntoContainer(COMPONENT_IN_PACKAGE, COMPONENT_TO_DROP_ELEMENTS, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL,
				MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromPackageToDiagram() {
		this.dropElementToDiagram(COMPONENT_IN_PACKAGE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromPackageToModel() {
		this.dropElementIntoContainer(COMPONENT_IN_PACKAGE, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropComponentFromPackageToPackage() {
		this.dropElementIntoContainer(COMPONENT_IN_PACKAGE, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__COMPONENT__TOOL,
				MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromDiagramToModel() {
		this.dropElementIntoContainer(CONSTRAINT, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromDiagramToPackage() {
		this.dropElementIntoContainer(CONSTRAINT, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromModelToDiagram() {
		this.dropElementToDiagram(CONSTRAINT_IN_MODEL, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromModelToModel() {
		this.dropElementIntoContainer(CONSTRAINT_IN_MODEL, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromModelToPackage() {
		this.dropElementIntoContainer(CONSTRAINT_IN_MODEL, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromPackageToDiagram() {
		this.dropElementToDiagram(CONSTRAINT_IN_PACKAGE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromPackageToModel() {
		this.dropElementIntoContainer(CONSTRAINT_IN_PACKAGE, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromPackageToPackage() {
		this.dropElementIntoContainer(CONSTRAINT_IN_PACKAGE, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromDiagramToModel() {
		this.dropElementIntoContainer(INTERFACE, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__INTERFACE__TOOL,
				MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromDiagramToPackage() {
		this.dropElementIntoContainer(INTERFACE, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__INTERFACE__TOOL,
				MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromModelToDiagram() {
		this.dropElementToDiagram(INTERFACE_IN_MODEL, GraphicalDropToolsIds.DROP__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromModelToModel() {
		this.dropElementIntoContainer(INTERFACE_IN_MODEL, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromModelToPackage() {
		this.dropElementIntoContainer(INTERFACE_IN_MODEL, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__INTERFACE__TOOL,
				MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromPackageToDiagram() {
		this.dropElementToDiagram(INTERFACE_IN_PACKAGE, GraphicalDropToolsIds.DROP__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromPackageToModel() {
		this.dropElementIntoContainer(INTERFACE_IN_PACKAGE, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropInterfaceFromPackageToPackage() {
		this.dropElementIntoContainer(INTERFACE_IN_PACKAGE, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__INTERFACE__TOOL,
				MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromDiagramToModel() {
		this.dropElementIntoContainer(MODEL, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__MODEL__TOOL,
				MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromDiagramToPackage() {
		this.dropElementIntoContainer(MODEL, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__MODEL__TOOL,
				MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromModelToDiagram() {
		this.dropElementToDiagram(MODEL_IN_MODEL, GraphicalDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromModelToModel() {
		this.dropElementIntoContainer(MODEL_IN_MODEL, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromModelToPackage() {
		this.dropElementIntoContainer(MODEL_IN_MODEL, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__MODEL__TOOL,
				MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromPackageToDiagram() {
		this.dropElementToDiagram(MODEL_IN_PACKAGE, GraphicalDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromPackageToModel() {
		this.dropElementIntoContainer(MODEL_IN_PACKAGE, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropModelFromPackageToPackage() {
		this.dropElementIntoContainer(MODEL_IN_PACKAGE, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__MODEL__TOOL,
				MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropOperationFromInterfaceToInterface() {
		this.dropElementIntoContainer(OPERATION, INTERFACE_TO_DROP_ELEMENTS, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_OPERATIONS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromDiagramToModel() {
		this.dropElementIntoContainer(PACKAGE, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL,
				MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromDiagramToPackage() {
		this.dropElementIntoContainer(PACKAGE, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL,
				MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromModelToDiagram() {
		this.dropElementToDiagram(PACKAGE_IN_MODEL, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromModelToModel() {
		this.dropElementIntoContainer(PACKAGE_IN_MODEL, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromModelToPackage() {
		this.dropElementIntoContainer(PACKAGE_IN_MODEL, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL,
				MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromPackageToDiagram() {
		this.dropElementToDiagram(PACKAGE_IN_PACKAGE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromPackageToModel() {
		this.dropElementIntoContainer(PACKAGE_IN_PACKAGE, MODEL_TO_DROP_ELEMENTS, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromPackageToPackage() {
		this.dropElementIntoContainer(PACKAGE_IN_PACKAGE, PACKAGE_TO_DROP_ELEMENTS, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL,
				MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPropertyFromComponentToComponent() {
		this.dropElementIntoContainer(PROPERTY_IN_COMPONENT, COMPONENT_TO_DROP_ELEMENTS, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PROPERTY__TOOL,
				MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPropertyFromInterfaceToInterface() {
		this.dropElementIntoContainer(PROPERTY_IN_INTERFACE, INTERFACE_TO_DROP_ELEMENTS, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PROPERTY__TOOL,
				MappingTypes.PROPERTY_IN_INTERFACE_NODE_TYPE);
	}



	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropReceptionFromInterfaceToInterface() {
		this.dropElementIntoContainer(RECEPTION, INTERFACE_TO_DROP_ELEMENTS, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_RECEPTION_COMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__RECEPTION__TOOL, MappingTypes.RECEPTION_NODE_TYPE);
	}
}
