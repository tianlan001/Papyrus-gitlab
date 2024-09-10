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

import static java.util.stream.Collectors.toList;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.diagramNamed;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.mViewpointNamed;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureFixture;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureResource;
import org.eclipse.papyrus.infra.architecture.tests.DomainName;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.Concern;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterables;

/**
 * Tests covering the legacy (implicit extension) merge into domains merged according
 * to the new (explicit extension) semantics.
 */
@ArchitectureResource("legacy/extending")
@ArchitectureResource("legacy/legacy")
@DomainName("modeling")
public class ArchitectureDomainMergerLegacyTest {

	@Rule
	public final ArchitectureFixture fixture = new ArchitectureFixture();

	/**
	 * Verify that legacy merge merged the context correctly.
	 */
	@Test
	public void contexts() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Extended domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of contexts", domain.getContexts().size(), is(1));
		MergedArchitectureContext context = Iterables.getOnlyElement(domain.getContexts());

		assertThat("Merge changed the context name", context.getName(), is("adl"));
	}

	/**
	 * Verify that legacy merge merged the viewpoints correctly.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void mergeViewpoints() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));
		assumeThat("Wrong number of contexts", domain.getContexts().size(), is(1));
		MergedArchitectureContext context = Iterables.getOnlyElement(domain.getContexts());

		assertThat("Wrong number of merged viewpoints", context.getViewpoints().size(), is(3));
		assertThat(context.getViewpoints(), hasItems(
				mViewpointNamed("viewpoint1"), mViewpointNamed("viewpoint2"), mViewpointNamed("viewpoint3")));

		MergedArchitectureViewpoint viewpoint2 = fixture.get(context.getViewpoints(), "viewpoint2");
		assertThat("Wrong number of diagrams in merged viewpoint", viewpoint2.getRepresentationKinds().size(), is(2));
		assertThat(viewpoint2.getRepresentationKinds(), hasItems(
				diagramNamed("diagram2"), diagramNamed("altdiagram2")));

		List<ArchitectureContext> owners = viewpoint2.getRepresentationKinds().stream()
				.map(EObject::eContainer)
				.filter(ArchitectureContext.class::isInstance).map(ArchitectureContext.class::cast)
				.distinct()
				.collect(toList());
		assertThat("Not an unique context owning the merged representation kinds", owners.size(), is(1));
		assertThat("Wrong owning context (by name)", owners.get(0).getName(), is("adl"));
		assertThat("wrong owning context (by identity)", owners.get(0), is(context.getAdapter(ArchitectureContext.class)));
	}

	/**
	 * Verify that legacy merge merged the default viewpoints of a context correctly.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void mergeDefaultViewpoints() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));
		assumeThat("Wrong number of contexts", domain.getContexts().size(), is(1));
		MergedArchitectureContext context = Iterables.getOnlyElement(domain.getContexts());

		assertThat("Wrong number of merged default viewpoints", context.getDefaultViewpoints().size(), is(2));
		assertThat(context.getDefaultViewpoints(), hasItems(
				mViewpointNamed("viewpoint1"), mViewpointNamed("viewpoint3")));

		List<ArchitectureContext> owners = context.getDefaultViewpoints().stream()
				.map(m -> m.getAdapter(ArchitectureViewpoint.class))
				.map(ArchitectureViewpoint::getContext)
				.distinct()
				.collect(toList());
		assertThat("Not an unique context owning the merged default viewpoints", owners.size(), is(1));
		assertThat("Wrong owning context (by name)", owners.get(0).getName(), is("adl"));
		assertThat("wrong owning context (by identity)", owners.get(0), is(context.getAdapter(ArchitectureContext.class)));
	}

	/**
	 * Verify that legacy merge merged the representation kinds correctly.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void mergeRepresentationKinds() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));
		assumeThat("Wrong number of contexts", domain.getContexts().size(), is(1));
		MergedArchitectureContext context = Iterables.getOnlyElement(domain.getContexts());
		assumeThat("Wrong number of merged viewpoints", context.getViewpoints().size(), is(3));
		assumeThat(context.getViewpoints(), hasItems(
				mViewpointNamed("viewpoint1"), mViewpointNamed("viewpoint2"), mViewpointNamed("viewpoint3")));
		MergedArchitectureViewpoint viewpoint2 = fixture.get(context.getViewpoints(), "viewpoint2");
		assumeThat("Wrong number of diagrams in merged viewpoint", viewpoint2.getRepresentationKinds().size(), is(2));
		assumeThat(viewpoint2.getRepresentationKinds(), hasItems(
				diagramNamed("diagram2"), diagramNamed("altdiagram2")));

		RepresentationKind representation = fixture.get(viewpoint2.getRepresentationKinds(), "altdiagram2");
		assertThat("Legacy-merged viewpoint does not have its concern", representation.getConcerns(), hasItem(ArchitectureMatchers.named("concern")));

		List<Concern> concerns = viewpoint2.getRepresentationKinds().stream()
				.map(RepresentationKind::getConcerns).flatMap(Collection::stream)
				.distinct()
				.collect(toList());
		assertThat("Not an unique concern linked to the merged representation kinds", concerns.size(), is(1));
		assertThat("Wrong concern (by name)", concerns.get(0).getName(), is("concern"));
		assertThat("wrong concern (by identity)", concerns.get(0).getDomain(), is(domain.getAdapter(ArchitectureDomain.class)));
	}

}
