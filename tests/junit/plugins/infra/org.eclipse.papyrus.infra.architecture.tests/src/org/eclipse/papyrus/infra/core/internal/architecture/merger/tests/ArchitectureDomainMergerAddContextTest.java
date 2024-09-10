/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - bug 570486
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.internal.architecture.merger.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.eclipse.papyrus.infra.architecture.tests.ArchitectureFixture;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureResource;
import org.eclipse.papyrus.infra.architecture.tests.DomainName;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests covering the scenarios in which domain extension adds a context that is not an extension context
 * to an extended domain.
 */
public class ArchitectureDomainMergerAddContextTest {

	@Rule
	public final ArchitectureFixture fixture = new ArchitectureFixture();

	/**
	 * Verify that a domain that has any extension contexts
	 * <ul>
	 * <li>is merged into the domains that its contexts extend,</li>
	 * <li>merges its extension contexts into the extended contexts of the domains that it extends,</li>
	 * <li>adds non-extension contexts to the domains that it extends, and</li>
	 * <li>does not appear in the merge result as a domain in its own right</li>
	 * </ul>
	 */
	@ArchitectureResource("extension/add_framework")
	@DomainName("add_framework")
	@Test
	public void addContextToExtendedDomain() {
		Map<String, MergedArchitectureDomain> domains = fixture.mergeDomains();
		assertThat("Wrong number of domains in merge result", domains.size(), is(1));

		MergedArchitectureDomain mExtended = domains.get("domain");

		// This one is defined by the extended domain model
		MergedArchitectureContext adl = fixture.get(mExtended.getContexts(), "adl");

		// This one is added to the domain by the extension model
		MergedArchitectureContext addedContext = fixture.get(mExtended.getContexts(), "my_framework");

		// And it has a viewpoint
		MergedArchitectureViewpoint viewpoint = fixture.get(addedContext.getViewpoints(), "my_viewpoint");

		// In which the diagram is properly owned by the merged architecture context in the merged domain
		RepresentationKind diagram = fixture.get(viewpoint.getRepresentationKinds(), "my_diagram");

		assertThat("Diagram is not contained in the merged model", diagram.getLanguage(), notNullValue());
		assertThat("Diagram is not owned by the extended ADL", diagram.getLanguage(),
				is(adl.getAdapter(ArchitectureDescriptionLanguage.class)));
		assertThat("Diagram is not contained within the extended domain", diagram.getLanguage().getDomain(),
				is(mExtended.getAdapter(ArchitectureDomain.class)));
	}

}
