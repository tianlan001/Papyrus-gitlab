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
package org.eclipse.papyrus.sirius.uml.diagram.common.services.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.uml2.uml.PrimitiveType;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link CommonDiagramServices} class.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class CommonDiagramServicesTest extends AbstractServicesTest {

	private CommonDiagramServices commonDiagramServices;

	@Before
	public void setUp() {
		this.commonDiagramServices = new CommonDiagramServices();
	}

	/**
	 * Tests {@code isTypeOf} with a different type than the provided element.
	 */
	@Test
	public void testIsTypeOfDifferentTypes() {
		PrimitiveType primitiveType = create(PrimitiveType.class);
		assertFalse(this.commonDiagramServices.isTypeOf(primitiveType, "Comment")); //$NON-NLS-1$
	}

	/**
	 * Tests {@code isTypeOf} with the exact type of the provided element.
	 */
	@Test
	public void testIsTypeOfSameTypes() {
		PrimitiveType primitiveType = create(PrimitiveType.class);
		assertTrue(this.commonDiagramServices.isTypeOf(primitiveType, "PrimitiveType")); //$NON-NLS-1$
	}

	/**
	 * Tests {@code isTypeOf} with the super type of the provided element.
	 */
	@Test
	public void testIsTypeOfSuperType() {
		PrimitiveType primitiveType = create(PrimitiveType.class);
		assertFalse(this.commonDiagramServices.isTypeOf(primitiveType, "DataType")); //$NON-NLS-1$
	}

}
