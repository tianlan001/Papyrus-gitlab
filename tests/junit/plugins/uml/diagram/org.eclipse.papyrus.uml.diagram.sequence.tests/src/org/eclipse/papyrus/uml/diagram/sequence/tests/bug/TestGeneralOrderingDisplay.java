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

import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.figures.GeneralOrderingDescriptor;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.UMLPackage.Literals;

/**
 * Test class to verify that DurationObservation links are properly displayed, and properly
 * react to changes in the diagram around them (Visual or semantic).
 */
@PluginResource({ "resource/bugs/bug536631-durationLinks.di", "resource/bugs/style.css" })
@ActiveDiagram("generalOrderingLinksTest")
public class TestGeneralOrderingDisplay extends AbstractOccurrenceLinkTest<GeneralOrdering> {

	public TestGeneralOrderingDisplay() {
		super(new String[] {
				"GeneralOrdering1",
				"GeneralOrdering2",
				"GeneralOrdering3",
				"GeneralOrdering4",
				"GeneralOrdering5",
				"GeneralOrdering6",
				"GeneralOrdering7"
		}, GeneralOrdering.class,
				GeneralOrderingDescriptor.class);
	}

	@Override
	protected void doUnrelatedChange(GeneralOrdering linkToChange) {
		// Rename the GeneralOrdering (This part of the test doesn't make much sense for GO; it
		// was mostly designed for DurationLinks, which use a generic list to hold their source/target)
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(linkToChange);
		SetRequest request = new SetRequest(linkToChange, Literals.NAMED_ELEMENT__NAME, linkToChange.getName() + "Renamed");
		editor.execute(provider.getEditCommand(request));
	}

	@Override
	protected void doChangeTarget(GeneralOrdering linkToChange) {
		GeneralOrdering ordering2 = (GeneralOrdering) EMFHelper.getEObject(links[1]);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(linkToChange);
		SetRequest request = new SetRequest(linkToChange, Literals.GENERAL_ORDERING__AFTER, ordering2.getAfter());
		editor.execute(provider.getEditCommand(request));
	}

	@Override
	protected Element getSourceElement(GeneralOrdering link) {
		return link.getBefore();
	}

	@Override
	protected Element getTargetElement(GeneralOrdering link) {
		return link.getAfter();
	}

}
