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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.diagram;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDiagramCreationTests;
import org.junit.Test;

/**
 * Check CompositeStructure diagram creation.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/createDiagram/createDiagram.di")
public class CreateCompositeStructureDiagramTest extends AbstractDiagramCreationTests {

	@Test
	public void createCompositeStructureDiagramTest() throws Exception {
		this.checkDiagramCreationFromSiriusDiagramPrototype(this.rootModel, "newName", "org.eclipse.papyrus.sirius.uml.diagram.compositestructure"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
