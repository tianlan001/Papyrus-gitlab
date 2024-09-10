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
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;

/**
 * Creation test for DurationObservationLinks
 */
@PluginResource({ "resource/bugs/bug536631-durationLinksCreation.di", "resource/bugs/style.css" })
@ActiveDiagram("durationLinksCreation")
public class DurationObservationCreationTest extends AbstractOccurrenceLinkCreationTest<DurationObservation> {

	public DurationObservationCreationTest() {
		super(DurationObservation.class, "createDurationObservationEdgeTool", DurationLinkFigure.class);
	}

	@Override
	protected Element getSemanticSource(DurationObservation link) {
		return link.getEvents().get(0);
	}

	@Override
	protected Element getSemanticTarget(DurationObservation link) {
		return link.getEvents().size() > 1 ? link.getEvents().get(1) : link.getEvents().get(0);
	}

}
