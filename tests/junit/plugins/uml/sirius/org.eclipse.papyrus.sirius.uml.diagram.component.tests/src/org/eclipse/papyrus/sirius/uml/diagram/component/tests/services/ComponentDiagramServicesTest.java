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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.component.services.ComponentDiagramServices;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link ComponentDiagramServices}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ComponentDiagramServicesTest extends AbstractServicesTest {

	private ComponentDiagramServices componentDiagramServices;

	@Before
	public void setUp() {
		this.componentDiagramServices = new ComponentDiagramServices();
	}

	@Test
	public void testCanCreateComponentCPDPackageParent() {
		Package packageParent = this.create(Package.class);
		assertTrue(this.componentDiagramServices.canCreateComponentCPD(packageParent));
	}

	@Test
	public void testCanCreateComponentCPDComponentParent() {
		Component componentParent = this.create(Component.class);
		assertTrue(this.componentDiagramServices.canCreateComponentCPD(componentParent));
	}

	@Test
	public void testCanCreateComponentNonPackageNonComponentParent() {
		Comment commentParent = this.create(Comment.class);
		assertFalse(this.componentDiagramServices.canCreateComponentCPD(commentParent));
	}

	@Test
	public void testGetComponentCandidates() {
		Package pack = this.create(Package.class);
		Component component1 = this.createIn(Component.class, pack);
		Component component2 = this.createIn(Component.class, pack);
		this.createIn(Interface.class, pack);

		assertEquals(List.of(component1, component2), this.componentDiagramServices.getComponentCandidates(pack));

		Component subComponent1 = this.create(Component.class);
		Component subComponent2 = this.create(Component.class);
		component1.getPackagedElements().add(subComponent1);
		component1.getPackagedElements().add(subComponent2);
		this.createIn(Property.class, component1);

		assertEquals(List.of(subComponent1, subComponent2), this.componentDiagramServices.getComponentCandidates(component1));

		Comment comment = this.create(Comment.class);
		assertEquals(List.of(), this.componentDiagramServices.getComponentCandidates(comment));
	}

	@Test
	public void testGetPortCandidates() {
		Component component = this.create(Component.class);
		Port port1 = this.create(Port.class);
		Port port2 = this.create(Port.class);
		Property property = this.create(Property.class);
		component.getOwnedAttributes().addAll(List.of(port1, port2, property));

		assertEquals(List.of(port1, port2), this.componentDiagramServices.getPortCandidates(component));

		Property propertyContainer = this.create(Property.class);
		propertyContainer.setType(component);

		assertEquals(List.of(port1, port2), this.componentDiagramServices.getPortCandidates(propertyContainer));

		Comment comment = this.create(Comment.class);
		assertEquals(List.of(), this.componentDiagramServices.getPortCandidates(comment));
	}

}
