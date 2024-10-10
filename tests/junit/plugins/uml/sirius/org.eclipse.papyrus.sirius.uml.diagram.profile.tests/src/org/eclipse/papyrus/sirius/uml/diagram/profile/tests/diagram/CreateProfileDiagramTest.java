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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.diagram;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDiagramCreationTests;
import org.junit.Test;

/**
 * Check Profile diagram creation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/createDiagram/createDiagram.profile.di")
public class CreateProfileDiagramTest extends AbstractDiagramCreationTests {

	@Test
	public void createProfileStructureDiagramTest() throws Exception {
		checkDiagramCreationFromSiriusDiagramPrototype(this.rootModel, "newName", "org.eclipse.papyrus.sirius.uml.diagram.profile"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
