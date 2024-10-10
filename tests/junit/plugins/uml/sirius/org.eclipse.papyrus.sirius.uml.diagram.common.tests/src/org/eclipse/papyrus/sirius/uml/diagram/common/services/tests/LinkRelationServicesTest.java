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

import org.eclipse.papyrus.sirius.uml.diagram.common.services.LinkRelationServices;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.junit.Test;

/**
 * 
 * Test the {@link LinkRelationServices}.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class LinkRelationServicesTest extends AbstractServicesTest {

	@Test
	public void canReconnectLinkSourceTest() {
		LinkRelationServices linkRelationServices = new LinkRelationServices();
		Comment comment = create(Comment.class);
		Comment comment2 = create(Comment.class);
		Constraint constraint = create(Constraint.class);
		Constraint constraint2 = create(Constraint.class);
		assertTrue(linkRelationServices.canReconnectLinkSource(constraint, constraint2));
		assertTrue(linkRelationServices.canReconnectLinkSource(comment, comment2));
		assertFalse(linkRelationServices.canReconnectLinkSource(comment, constraint2));
		assertFalse(linkRelationServices.canReconnectLinkSource(constraint, comment2));
	}
}
