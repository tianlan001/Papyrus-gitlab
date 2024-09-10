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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.figures.DurationLinkFigure;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage.Literals;

/**
 * Test class to verify that DurationObservation links are properly displayed, and properly
 * react to changes in the diagram around them (Visual or semantic).
 */
@PluginResource({ "resource/bugs/bug536631-durationLinks.di", "resource/bugs/style.css" })
@ActiveDiagram("durationObservationsLinksTest")
public class TestDurationObservationDisplay extends AbstractOccurrenceLinkTest<DurationObservation> {

	public TestDurationObservationDisplay() {
		super(new String[] {
				"DurationObservation1",
				"DurationObservation2",
				"DurationObservation3",
				"DurationObservation4",
				"DurationObservation5",
				"DurationObservation6",
				"DurationObservation7"
		}, DurationObservation.class,
				DurationLinkFigure.class);
	}

	@Override
	protected void doUnrelatedChange(DurationObservation linkToChange) {
		DurationObservation duration2 = (DurationObservation) EMFHelper.getEObject(links[1]);

		List<Element> values = new ArrayList<>(linkToChange.getEvents());
		values.add(duration2.getEvents().get(1));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(linkToChange);
		SetRequest request = new SetRequest(linkToChange, Literals.DURATION_OBSERVATION__EVENT, values);
		editor.execute(provider.getEditCommand(request));
	}

	@Override
	protected void doChangeTarget(DurationObservation linkToChange) {
		List<Element> values = new ArrayList<>(linkToChange.getEvents());
		values.remove(1);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(linkToChange);
		SetRequest request = new SetRequest(linkToChange, Literals.DURATION_OBSERVATION__EVENT, values);
		editor.execute(provider.getEditCommand(request));
	}

	@Override
	protected Element getSourceElement(DurationObservation link) {
		return link.getEvents().get(0);
	}

	@Override
	protected Element getTargetElement(DurationObservation link) {
		return link.getEvents().size() == 1 ? link.getEvents().get(0) : link.getEvents().get(1);
	}

}
