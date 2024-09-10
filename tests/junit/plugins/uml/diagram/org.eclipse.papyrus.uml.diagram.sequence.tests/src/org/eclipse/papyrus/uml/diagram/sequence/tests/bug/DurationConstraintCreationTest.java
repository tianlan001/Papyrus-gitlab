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
import org.eclipse.papyrus.uml.diagram.sequence.figures.DurationLinkFigure;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.Element;

/**
 * Creation test for DurationConstraintLinks
 */
@PluginResource({ "resource/bugs/bug536631-durationLinksCreation.di", "resource/bugs/style.css" })
@ActiveDiagram("durationLinksCreation")
public class DurationConstraintCreationTest extends AbstractOccurrenceLinkCreationTest<DurationConstraint> {

	public DurationConstraintCreationTest() {
		super(DurationConstraint.class, "createDurationConstraintEdgeTool", DurationLinkFigure.class);
	}

	@Override
	protected Element getSemanticSource(DurationConstraint link) {
		return link.getConstrainedElements().get(0);
	}

	@Override
	protected Element getSemanticTarget(DurationConstraint link) {
		return link.getConstrainedElements().size() > 1 ? link.getConstrainedElements().get(1) : link.getConstrainedElements().get(0);
	}

}
