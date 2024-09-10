/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy.tests;

import java.util.Collection;

import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationFactory;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
@SuppressWarnings("nls")
public class PolicyCheckerTest {

	@Test
	public void testIsInViewpoint() {

		ArchitectureDomain architectureDomain = ArchitectureFactory.eINSTANCE.createArchitectureDomain();
		ArchitectureDescriptionLanguage architectureDescriptionLanguage = ArchitectureFactory.eINSTANCE.createArchitectureDescriptionLanguage();
		architectureDescriptionLanguage.setDomain(architectureDomain);
		ArchitectureViewpoint architectureViewpoint = ArchitectureFactory.eINSTANCE.createArchitectureViewpoint();
		architectureDescriptionLanguage.getViewpoints().add(architectureViewpoint);
		
		PapyrusDiagram knownDiagram = RepresentationFactory.eINSTANCE.createPapyrusDiagram();
		knownDiagram.setName("knownDiagram");
		architectureViewpoint.getRepresentationKinds().add(knownDiagram);

		PapyrusDiagram unknownDiagram = RepresentationFactory.eINSTANCE.createPapyrusDiagram();
		unknownDiagram.setName("unknownDiagram");

		MergedArchitectureDomain mergedArchitectureDomain = new MergedArchitectureDomain();
		mergedArchitectureDomain.merge(architectureDomain);
		
		Collection<MergedArchitectureContext> contexts = mergedArchitectureDomain.getContexts();
		MergedArchitectureContext next = contexts.iterator().next();
		PolicyChecker policyChecker = PolicyChecker.getFor(next);
		Assert.assertTrue(policyChecker.isInViewpoint(knownDiagram));
		Assert.assertFalse(policyChecker.isInViewpoint(unknownDiagram));
		Assert.assertFalse(policyChecker.isInViewpoint(null)); 
	}
	

	

}
