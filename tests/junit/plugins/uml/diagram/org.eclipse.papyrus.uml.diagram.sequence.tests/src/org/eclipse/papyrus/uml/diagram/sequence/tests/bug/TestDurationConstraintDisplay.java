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
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage.Literals;

/**
 * Test class to verify that DurationConstraint links are properly displayed, and properly
 * react to changes in the diagram around them (Visual or semantic).
 */
@PluginResource({ "resource/bugs/bug536631-durationLinks.di", "resource/bugs/style.css" })
@ActiveDiagram("durationConstraintsLinksTest")
public class TestDurationConstraintDisplay extends AbstractOccurrenceLinkTest<DurationConstraint> {

	public TestDurationConstraintDisplay() {
		super(new String[] {
				"DurationConstraint1",
				"DurationConstraint2",
				"DurationConstraint3",
				"DurationConstraint4",
				"DurationConstraint5",
				"DurationConstraint6",
				"DurationConstraint7",
		}, DurationConstraint.class, DurationLinkFigure.class);
	}

	@Override
	protected void doUnrelatedChange(DurationConstraint linkToChange) {
		DurationConstraint duration2 = (DurationConstraint) EMFHelper.getEObject(links[1]);

		List<Element> values = new ArrayList<>(linkToChange.getConstrainedElements());
		values.add(duration2.getConstrainedElements().get(1));

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(linkToChange);
		SetRequest request = new SetRequest(linkToChange, Literals.CONSTRAINT__CONSTRAINED_ELEMENT, values);
		editor.execute(provider.getEditCommand(request));
	}

	@Override
	protected void doChangeTarget(DurationConstraint linkToChange) {
		List<Element> values = new ArrayList<>(linkToChange.getConstrainedElements());
		values.remove(1);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(linkToChange);
		SetRequest request = new SetRequest(linkToChange, Literals.CONSTRAINT__CONSTRAINED_ELEMENT, values);
		editor.execute(provider.getEditCommand(request));
	}

	@Override
	protected Element getSourceElement(DurationConstraint link) {
		return link.getConstrainedElements().get(0);
	}

	@Override
	protected Element getTargetElement(DurationConstraint link) {
		return link.getConstrainedElements().size() == 1 ? link.getConstrainedElements().get(0) : link.getConstrainedElements().get(1);
	}

}
