/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.figures.GeneralOrderingDescriptor;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.GeneralOrdering;

/**
 * Creation test for GeneralOrdering Links
 */
@PluginResource({ "resource/bugs/bug536631-durationLinksCreation.di", "resource/bugs/style.css" })
@ActiveDiagram("durationLinksCreation")
public class GeneralOrderingCreationTest extends AbstractOccurrenceLinkCreationTest<GeneralOrdering> {

	public GeneralOrderingCreationTest() {
		super(GeneralOrdering.class, "createGeneralOrdering8CreationTool", GeneralOrderingDescriptor.class);
	}

	@Override
	protected Element getSemanticSource(GeneralOrdering link) {
		return link.getBefore();
	}

	@Override
	protected Element getSemanticTarget(GeneralOrdering link) {
		return link.getAfter();
	}

}
