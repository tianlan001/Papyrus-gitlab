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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.component.services.ComponentDiagramDropBehaviorProvider;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link ComponentDiagramDropBehaviorProvider}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ComponentDiagramDropBehaviorProviderTest extends AbstractServicesTest {

	private ComponentDiagramDropBehaviorProvider componentDiagramDropBehaviorProvider;

	@Before
	public void setUp() {
		this.componentDiagramDropBehaviorProvider = new ComponentDiagramDropBehaviorProvider();
	}

	@Test
	public void testCanDragAndDropSemanticCPD() {
		Component parentComponent = this.create(Component.class);
		Component childComponent = this.create(Component.class);
		assertTrue(this.componentDiagramDropBehaviorProvider.canDragAndDropSemanticCPD(childComponent, parentComponent));
		Interface parentInterface = this.create(Interface.class);
		assertFalse(this.componentDiagramDropBehaviorProvider.canDragAndDropSemanticCPD(childComponent, parentInterface));
	}

	@Test
	public void testCanDragAndDropGraphicCPD() {
		Component parentComponent = this.create(Component.class);
		Component childComponent = this.create(Component.class);
		assertTrue(this.componentDiagramDropBehaviorProvider.canDragAndDropGraphicCPD(childComponent, parentComponent));
		Interface parentInterface = this.create(Interface.class);
		assertFalse(this.componentDiagramDropBehaviorProvider.canDragAndDropGraphicCPD(childComponent, parentInterface));
	}

}
