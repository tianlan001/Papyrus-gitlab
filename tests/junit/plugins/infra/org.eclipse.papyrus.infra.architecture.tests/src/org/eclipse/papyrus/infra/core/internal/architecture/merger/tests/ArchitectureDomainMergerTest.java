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
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.named;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureFixture;
import org.eclipse.papyrus.infra.architecture.tests.ArchitectureResource;
import org.eclipse.papyrus.infra.architecture.tests.DomainName;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.Concern;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.Stakeholder;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterables;

/**
 * Tests covering the explicit merge algorithm.
 */
@ArchitectureResource("extension/extending")
@DomainName("extended")
public class ArchitectureDomainMergerTest {

	@Rule
	public final ArchitectureFixture fixture = new ArchitectureFixture();

	private ArchitectureDomain extendedDomain;
	private ArchitectureDomain intermediateDomain;
	private ArchitectureDomain extendingDomain;
	
	@Test
	public void isMerged() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assertThat("Domain not merged", domain.isMerged(), is(true));
	}

	@Test
	public void resources() {
		Map<String, MergedArchitectureDomain> domains = fixture.mergeDomains();
		assertThat("Wrong number of domains merged", domains.size(), is(1));
		
		MergedArchitectureDomain mExtended = domains.get(extendedDomain.getName());
		assertThat("Extended domain is not in a resource", mExtended.getAdapter(Resource.class), notNullValue());
	}

	@Test
	public void traces() {
		Map<String, MergedArchitectureDomain> domains = fixture.mergeDomains();
		assertThat("Wrong number of domains merged", domains.size(), is(1));
		
		MergedArchitectureDomain mExtended = domains.get(extendedDomain.getName());
		
		assertThat("Extended domain does not trace to source", fixture.tracesTo(mExtended, extendedDomain));
		assertThat("Extended domain does not trace to intermediate domain", fixture.tracesTo(mExtended, intermediateDomain));
		assertThat("Extended domain does not trace to extending domain", fixture.tracesTo(mExtended, extendingDomain));
		
		MergedArchitectureContext mContext = fixture.get(mExtended.getContexts(), "adl");
		ArchitectureContext adl = fixture.get(extendedDomain.getContexts(), "adl");
		ArchitectureContext midadl = fixture.get(intermediateDomain.getContexts(), "midadl");
		ArchitectureContext myadl = fixture.get(extendingDomain.getContexts(), "myadl");
		
		assertThat("Extended context does not trace to source", fixture.tracesTo(mContext, adl));
		assertThat("Extended context does not trace to intermediate context", fixture.tracesTo(mContext, midadl));
		assertThat("Extended context does not trace to extending context", fixture.tracesTo(mContext, myadl));
	}

	@Test
	public void getContexts() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Extended domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of contexts", domain.getContexts().size(), is(2));
		MergedArchitectureContext context = Iterables.getFirst(domain.getContexts(), null);

		assertThat("Merge changed the context name", context.getName(), is("adl"));
	}

	@Test
	public void mergeContextName() {
		// Remove names of the extension graph
		fixture.rename(extendedDomain, ArchitectureContext.class, "adl", null);
		fixture.rename(intermediateDomain, ArchitectureContext.class, "midadl", null);

		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of contexts", domain.getContexts().size(), is(2));
		MergedArchitectureContext context = Iterables.getFirst(domain.getContexts(), null);

		assertThat("Merge did not set the context name", context.getName(), is("myadl"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void collectViewpoints() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of contexts", domain.getContexts().size(), is(2));
		MergedArchitectureContext context = Iterables.get(domain.getContexts(), 1, null);

		assertThat("Wrong number of collected viewpoints", context.getViewpoints().size(), is(4));
		assertThat(context.getViewpoints(), hasItems(
				mViewpointNamed("viewpoint3"), mViewpointNamed("viewpoint4"), mViewpointNamed("viewpoint5"), mViewpointNamed("viewpoint6")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void mergeViewpoints() {
		// Viewpoints are merged by name in merged contexts
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of contexts", domain.getContexts().size(), is(2));
		MergedArchitectureContext context = Iterables.getFirst(domain.getContexts(), null);

		assertThat("Wrong number of merged viewpoints", context.getViewpoints().size(), is(2));
		assertThat(context.getViewpoints(), hasItems(
				mViewpointNamed("viewpoint1"), mViewpointNamed("viewpoint2")));

		MergedArchitectureViewpoint viewpoint1 = Iterables.getFirst(context.getViewpoints(), null);
		assertThat("Wrong number of diagrams in merged viewpoint", viewpoint1.getRepresentationKinds().size(), is(2));
		assertThat(viewpoint1.getRepresentationKinds(), hasItems(
				diagramNamed("diagram1"), diagramNamed("diagram3")));

		List<ArchitectureContext> owners = viewpoint1.getRepresentationKinds().stream()
				.map(EObject::eContainer)
				.filter(ArchitectureContext.class::isInstance).map(ArchitectureContext.class::cast)
				.distinct()
				.collect(toList());
		assertThat("Not an unique context owning the merged representation kinds", owners.size(), is(1));
		assertThat("Wrong owning context (by name)", owners.get(0).getName(), is("adl"));
		assertThat("wrong owning context (by identity)", owners.get(0), is(context.getAdapter(ArchitectureContext.class)));
	}
	
	@Test
	public void mergeRepresentationKinds() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of contexts", domain.getContexts().size(), is(2));
		MergedArchitectureContext context = Iterables.getFirst(domain.getContexts(), null);

		assertThat("Wrong number of merged viewpoints", context.getViewpoints().size(), is(2));
		MergedArchitectureViewpoint viewpoint1 = Iterables.getFirst(context.getViewpoints(), null);
		
		assertThat("Wrong number of diagrams in merged viewpoint", viewpoint1.getRepresentationKinds().size(), is(2));

		List<Concern> concerns = viewpoint1.getRepresentationKinds().stream()
				.map(RepresentationKind::getConcerns).flatMap(Collection::stream)
				.distinct()
				.collect(toList());
		assertThat("Not an unique concern linked to the merged representation kinds", concerns.size(), is(1));
		assertThat("Wrong concern (by name)", concerns.get(0).getName(), is("concern"));
		assertThat("wrong concern (by identity)", concerns.get(0).getDomain(), is(domain.getAdapter(ArchitectureDomain.class)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void collectStakeholdersAndConcerns() {
		// Rename some to make sure they're not merged
		fixture.rename(extendingDomain, Stakeholder.class, "user", "designer");
		fixture.rename(extendingDomain, Concern.class, "concern", "designing");

		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of collected stakeholders", domain.getStakeholders().size(), is(2));
		assertThat(domain.getStakeholders(), hasItems(named("user"), named("designer")));
		assertThat("Wrong number of collected concerns", domain.getConcerns().size(), is(2));
		assertThat(domain.getConcerns(), hasItems(named("concern"), named("designing")));

		// All references to these objects are uniquely to these four instances (nothing from the source models)
		Set<Stakeholder> allStakeholderReferences = new HashSet<>();
		Set<Concern> allConcernReferences = new HashSet<>();
		domain.getAdapter(EObject.class).eAllContents().forEachRemaining(e -> {
			if (e instanceof Stakeholder) {
				allStakeholderReferences.add((Stakeholder) e);
			} else if (e instanceof Concern) {
				allConcernReferences.add((Concern) e);
			} else {
				e.eCrossReferences().forEach(xref -> {
					if (xref instanceof Stakeholder) {
						allStakeholderReferences.add((Stakeholder) xref);
					} else if (xref instanceof Concern) {
						allConcernReferences.add((Concern) xref);
					}
				});
			}
		});

		assertThat("Wrong merging of stakeholders or leaking of source stakeholders", allStakeholderReferences, is(Set.copyOf(domain.getStakeholders())));
		assertThat("Wrong merging of concerns or leaking of source concerns", allConcernReferences, is(Set.copyOf(domain.getConcerns())));
	}

	@Test
	public void mergeDiagramParent() {
		MergedArchitectureDomain domain = fixture.mergeDomain();
		assumeThat("Domain not merged", domain.isMerged(), is(true));

		assertThat("Wrong number of contexts", domain.getContexts().size(), is(2));
		ArchitectureDescriptionLanguage adl = Iterables.getFirst(domain.getContexts(), null).getAdapter(ArchitectureDescriptionLanguage.class);

		assertThat("Wrong number of merged representation kinds", adl.getRepresentationKinds().size(), is(4));
		PapyrusRepresentationKind diagram3 = (PapyrusDiagram) adl.getRepresentationKinds().get(2);
		PapyrusRepresentationKind diagram4 = (PapyrusDiagram) adl.getRepresentationKinds().get(3);
		assertThat("Diagrams merged in the wrong order", diagram3.getName(), is("diagram3"));
		assertThat("Diagrams merged in the wrong order", diagram4.getName(), is("diagram4"));
		
		assertThat("PapyrusDiagram::parent reference not merged", diagram4.getParent(), sameInstance(diagram3));
	}

	//
	// Test framework
	//

	@Before
	public void gatherTestModel() {
		extendingDomain = fixture.getArchitectureDomain("extending");
		intermediateDomain = fixture.getArchitectureDomain("intermediate");
		extendedDomain = fixture.getArchitectureDomain("extended");
	}

}
