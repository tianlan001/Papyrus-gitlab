/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.uml.diagram.common.services.tests;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.PropertyServices;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DiagramFactory;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link PropertyServices} service.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class PropertyServicesTest extends AbstractServicesTest {

	private static final String PROPERTY1 = "property1"; //$NON-NLS-1$

	private static final String CLASS_3 = "class3"; //$NON-NLS-1$

	private static final String CLASS_2 = "class2"; //$NON-NLS-1$

	private static final String CLASS_1 = "class1"; //$NON-NLS-1$

	private PropertyServices propertyServices;

	@Before
	public void setUp() {
		this.propertyServices = new PropertyServices();
	}

	/**
	 * Test {@link CompositeStructureDiagramServices#noPortLoopDetectedCSD(org.eclipse.uml2.uml.Port)}.
	 * Initial state:
	 * Class1 (port1)
	 * Class2 (port2)
	 * Class3 ()
	 * port1->Class2
	 * port2->Class3
	 */
	@Test
	public void testNoPortLoopDetected() {
		Model model = this.create(Model.class);
		Class class1 = this.create(Class.class);
		class1.setName(CLASS_1);
		Class class2 = this.create(Class.class);
		class2.setName(CLASS_2);
		Class class3 = this.create(Class.class);
		class3.setName(CLASS_3);
		Port port1 = this.create(Port.class);
		port1.setName("port1"); //$NON-NLS-1$
		Port port2 = this.create(Port.class);
		port2.setName("port2"); //$NON-NLS-1$
		model.getPackagedElements().add(class1);
		model.getPackagedElements().add(class2);
		model.getPackagedElements().add(class3);
		class1.getOwnedAttributes().add(port1);
		class2.getOwnedAttributes().add(port2);
		port1.setType(class2);
		port2.setType(class3);
		DSemanticDecorator port1View = this.createDSemanticDecorator(port1);
		DSemanticDecorator port2View = this.createDSemanticDecorator(port2);
		// We check that no loop is detected when computing the precondition to display port2 on the port1View (port1 is typed by Class2 that owns port2)
		assertTrue(this.propertyServices.noPortLoopDetected(port2, port1View));

		// Test that the loop is detected with the port2 type equals to the port2 owner (direct loop)
		port2.setType(class2);
		// We check that a loop is detected when computing the precondition to display port2 on the port2View (port2 is typed by Class2 that owns port2 which is typed by class2 etc.)
		assertFalse(this.propertyServices.noPortLoopDetected(port2, port2View));
		// We check that a loop is detected when computing the precondition to display port2 on the port1View (port1 is typed by Class2 that owns port2 which is typed by class1 that owns port1 etc.)
		assertFalse(this.propertyServices.noPortLoopDetected(port2, port1View));

		// Test that the indirect loop is detected: port1 (owned by Class1) type references Class2 that owns port2 which is typed by Class1.
		port2.setType(class1);
		// We check that a loop is detected when computing the precondition to display port1 on the port2View (port2 is typed by Class1 that owns port1 which is typed by class2 that owns port2 etc.)
		assertFalse(this.propertyServices.noPortLoopDetected(port1, port2View));
		// We check that a loop is detected when computing the precondition to display port2 on the port1View (port1 is typed by Class2 that owns port2 which is typed by class1 that owns port1 etc.)
		assertFalse(this.propertyServices.noPortLoopDetected(port2, port1View));
	}

	/**
	 * Test {@link CompositeStructureDiagramServices#noPropertyLoopDetectedCSD(org.eclipse.uml2.uml.Property)
	 * Initial state:
	 * Class1 (property1)
	 * Class2 (property2)
	 * Class3 ()
	 * property1->Class2
	 * property2->Class3
	 */
	@Test
	public void testNoPropertyLoopDetected() {
		Model model = this.create(Model.class);
		Class class1 = this.create(Class.class);
		class1.setName(CLASS_1);
		Class class2 = this.create(Class.class);
		class2.setName(CLASS_2);
		Class class3 = this.create(Class.class);
		class3.setName(CLASS_3);
		Property property1 = this.create(Property.class);
		property1.setName(PROPERTY1);
		Property property2 = this.create(Property.class);
		property2.setName("property2"); //$NON-NLS-1$
		model.getPackagedElements().add(class1);
		model.getPackagedElements().add(class2);
		model.getPackagedElements().add(class3);
		class1.getOwnedAttributes().add(property1);
		class2.getOwnedAttributes().add(property2);
		property1.setType(class2);
		property2.setType(class3);
		DSemanticDecorator property1View = this.createDSemanticDecorator(property1);
		DSemanticDecorator property2View = this.createDSemanticDecorator(property2);
		// We check that no loop is detected when computing the precondition to display property2 on the property1View ( property1 is typed by Class2 that owns property2)
		assertTrue(this.propertyServices.noPropertyLoopDetected(property2, property1View));

		// Test that the loop is detected with the property2 type equals to the property2 owner (direct loop)
		property2.setType(class2);
		// We check that a loop is detected when computing the precondition to display property2 on the property2View (property2 is typed by Class2 that owns property2 which is typed by class2 etc.)
		assertFalse(this.propertyServices.noPropertyLoopDetected(property2, property2View));
		// We check that a loop is detected when computing the precondition to display property2 on the property1View (property1 is typed by Class2 that owns property2 which is typed by class1 that owns property1 etc.)
		assertFalse(this.propertyServices.noPropertyLoopDetected(property2, property1View));

		// Test that the indirect loop is detected: property1 (owned by Class1) type references Class2 that owns property2 which is typed by Class1.
		property2.setType(class1);
		// We check that a loop is detected when computing the precondition to display property1 on the property2View (property2 is typed by Class1 that owns property1 which is typed by class2 that owns property2 etc.)
		assertFalse(this.propertyServices.noPropertyLoopDetected(property1, property2View));
		// We check that a loop is detected when computing the precondition to display property2 on the property1View (property1 is typed by Class2 that owns property2 which is typed by class1 that owns property1 etc.)
		assertFalse(this.propertyServices.noPropertyLoopDetected(property2, property1View));
	}

	private DSemanticDecorator createDSemanticDecorator(EObject semanticTarget) {
		DNodeContainer dNodeContainer = DiagramFactory.eINSTANCE.createDNodeContainer();
		dNodeContainer.setTarget(semanticTarget);
		return dNodeContainer;
	}
}
