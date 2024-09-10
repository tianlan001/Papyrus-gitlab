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

import static java.util.stream.Collectors.toSet;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.diagramNamed;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.mnamed;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.named;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureFixture;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureResource;
import org.eclipse.papyrus.infra.architecture.tests.DomainName;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.Concern;
import org.eclipse.papyrus.infra.core.architecture.Stakeholder;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests covering the context inheritance algorithm.
 */
@ArchitectureResource("mixed/extending")
@ArchitectureResource("mixed/specific")
public class ArchitectureDomainMixedMergesTest {

	@Rule
	public final ArchitectureFixture fixture = new ArchitectureFixture();

	/**
	 * Verify that a specializing context does not inherit content contributed to a general
	 * via extensions.
	 */
	@DomainName("specific")
	@Test
	public void extensionContentNotInherited() {
		MergedArchitectureDomain domain = fixture.mergeDomain();

		assertThat("Wrong number of merged contexts", domain.getContexts().size(), is(1));
		MergedArchitectureContext context = fixture.get(domain.getContexts(), "specific");

		assertThat("Wrong number of merged viewpoints", context.getViewpoints().size(), is(2));
		MergedArchitectureViewpoint viewpoint = fixture.get(context.getViewpoints(), "viewpoint1");

		assertThat("Wrong number of merged diagrams", viewpoint.getRepresentationKinds().size(), is(1));
		assertThat(viewpoint.getRepresentationKinds(), not(hasItem(diagramNamed("diagram3"))));
	}

	/**
	 * Verify that an extended context gets content contributed to it that its extensions inherit from
	 * other contexts.
	 */
	@DomainName("extended")
	@Test
	public void inheritedContentMergedByExtension() {
		MergedArchitectureDomain domain = fixture.mergeDomain();

		assertThat("Wrong number of merged contexts", domain.getContexts().size(), is(1));
		MergedArchitectureContext context = fixture.get(domain.getContexts(), "adl");

		assertThat("Wrong number of merged viewpoints", context.getViewpoints().size(), is(2));
		MergedArchitectureViewpoint viewpoint = fixture.get(context.getViewpoints(), "viewpoint2");

		assertThat("Wrong number of merged diagrams", viewpoint.getRepresentationKinds().size(), is(2));
		assertThat(viewpoint.getRepresentationKinds(), hasItem(diagramNamed("altdiagram2")));
	}

	/**
	 * Verify that an extension context does not exist in the merge result even if its a specialization
	 * of some general context that does exist in the merge result.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void extensionContextsElided() {
		Collection<MergedArchitectureDomain> domains = fixture.mergeDomains().values();
		Collection<MergedArchitectureContext> contexts = domains.stream()
				.map(MergedArchitectureDomain::getContexts).flatMap(Collection::stream)
				.collect(Collectors.toList());

		assertThat("Wrong number of merged contexts", contexts.size(), is(3));

		assertThat(contexts, hasItems(mnamed("adl"), mnamed("general"), mnamed("specific")));
		assertThat(contexts, everyItem(not(anyOf(mnamed("intermediate"), mnamed("myadl")))));
	}

	/**
	 * Verify that the merge result does not contain any <em>architecture domains</em> that do not
	 * contain any <em>architecture contexts</em> (which usually happens because all of the contexts
	 * in it are extension contexts that {@linkplain #extensionContextsElided() are themselves elided}).
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void emptyDomainsElided() {
		Collection<MergedArchitectureDomain> domains = fixture.mergeDomains().values();
		assertThat("Wrong number of merged domains", domains.size(), is(3));

		assertThat(domains, hasItems(mnamed("extended"), mnamed("general"), mnamed("specific")));
	}

	/**
	 * Verify that in the merge result every <em>architecture domain</em> has its own
	 * closed set of stakeholders and concerns.
	 */
	@Test
	public void stakeholdersAndConcerns() {
		Collection<MergedArchitectureDomain> domains = fixture.mergeDomains().values();
		Collection<Stakeholder> allStakeholders = domains.stream()
				.map(MergedArchitectureDomain::getStakeholders).flatMap(Collection::stream)
				.collect(toSet());
		assertThat("Wrong number of merged stakeholders", allStakeholders.size(), is(3));
		assertThat(allStakeholders, everyItem(named("user")));

		Collection<Concern> allConcerns = domains.stream()
				.map(MergedArchitectureDomain::getConcerns).flatMap(Collection::stream)
				.collect(toSet());
		assertThat("Wrong number of merged concerns", allConcerns.size(), is(3));
		assertThat(allConcerns, everyItem(named("concern")));

		Collection<ArchitectureDomain> stakeholderDomains = allStakeholders.stream().map(Stakeholder::getDomain).collect(toSet());
		Collection<ArchitectureDomain> concernDomains = allStakeholders.stream().map(Stakeholder::getDomain).collect(toSet());

		assertThat("Merged stakeholders not correctly mapped to concerns", stakeholderDomains, is(concernDomains));
		assertThat("Wrong number of distinct domains containing merged stakeholders", stakeholderDomains.size(), is(3));

		Collection<Concern> concernRefs = Iterators2.stream(EcoreUtil.getAllContents(concernDomains))
				.filter(ADElement.class::isInstance).map(ADElement.class::cast)
				.map(EObject::eCrossReferences).flatMap(xrefs -> xrefs.stream())
				.filter(Concern.class::isInstance).map(Concern.class::cast)
				.collect(toSet());
		assertThat("Merge result has dangling concern references", concernRefs, is(allConcerns));
	}

}
