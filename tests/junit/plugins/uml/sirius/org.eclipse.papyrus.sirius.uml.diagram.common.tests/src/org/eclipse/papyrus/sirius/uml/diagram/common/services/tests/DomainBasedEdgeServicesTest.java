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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link DomainBasedEdgeServices} service.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class DomainBasedEdgeServicesTest extends AbstractServicesTest {


	private DomainBasedEdgeServices domainBasedEdgeServices;

	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.domainBasedEdgeServices = new DomainBasedEdgeServices();
	}

	/**
	 * Test that a {@link Class} from not read only resource is removed.
	 */
	@Test
	public void testGetSourceFromGeneralization() {
		Dependency dependency = create(Dependency.class);
		assertNull(domainBasedEdgeServices.getSource(dependency));

		Class class1 = create(Class.class);
		dependency.getClients().add(class1);
		assertEquals(class1, domainBasedEdgeServices.getSource(dependency));
	}

	/**
	 * Test that a {@link Class} from not read only resource is removed.
	 */
	@Test
	public void testGetTargetFromDependency() {
		Dependency dependency = create(Dependency.class);
		assertTrue(domainBasedEdgeServices.getTargets(dependency).isEmpty());

		Class class1 = create(Class.class);
		dependency.getSuppliers().add(class1);
		assertEquals(class1, domainBasedEdgeServices.getTargets(dependency).get(0));
	}

	@Test
	public void testCanCreateDependency() {
		Package pack1 = create(Package.class);
		Class class1 = create(Class.class);
		pack1.getPackagedElements().add(class1);
		Class class2 = create(Class.class);
		assertTrue(domainBasedEdgeServices.canCreateDomainBasedEdge(class1, class2, "Manifestation", UMLPackage.eINSTANCE.getManifestation().getName(), null, null)); //$NON-NLS-1$

		Comment comment = create(Comment.class);
		assertFalse(domainBasedEdgeServices.canCreateDomainBasedEdge(class1, comment, "Manifestation", UMLPackage.eINSTANCE.getManifestation().getName(), null, null)); //$NON-NLS-1$
	}
}
