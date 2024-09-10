/*****************************************************************************
 * Copyright (c) 2018, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 570486
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.architecture.tests.merged;

import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.diagramNamed;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.mADLNamed;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.mAFNamed;
import static org.eclipse.papyrus.infra.architecture.tests.ArchitectureMatchers.mViewpointNamed;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.util.ArchitectureResourceFactory;
import org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests covering the legacy merge algorithm.
 */
public class MergedArchitectureDomainTest {

	private ResourceSet resourceSet;
	private ArchitectureDomain domain1;
	private ArchitectureDomain domain2;

	@Test
	public void testMergeContextSameName() {
		List<MergedArchitectureContext> mergedContexts = mergeContexts();
		assertEquals("Contexts with same name aren't merged", 2, mergedContexts.size());
		assertThat(mergedContexts, allOf(hasItem(mADLNamed("adl")), hasItem(mAFNamed("framework"))));
		
		mergedContexts.forEach(ctx -> assertThat("Context is not merged", ctx.isMerged(), is(true)));
		mergedContexts.forEach(ctx -> assertThat("Domain is not merged", ctx.getDomain().isMerged(), is(true)));
	}

	@Test
	public void testMergeContextDifferentName() {
		rename(ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT, "adl", "differentName");

		List<MergedArchitectureContext> mergedContexts = mergeContexts();
		assertEquals("Contexts with different name are merged", 3, mergedContexts.size());
		assertThat(mergedContexts,
				allOf(hasItem(mADLNamed("adl")), hasItem(mADLNamed("differentName")), hasItem(mAFNamed("framework"))));

		mergedContexts.forEach(ctx -> assertThat("Context has wrong merged result", ctx.isMerged(), is("framework".equals(ctx.getName()))));
	}

	@Test
	public void testMergeViewpointsSameName() {
		List<MergedArchitectureContext> mergedContexts = mergeContexts();
		MergedArchitectureContext mergedArchitectureContext = mergedContexts.iterator().next();
		assertEquals("Viewpoints with same name aren't merged", 2, mergedArchitectureContext.getViewpoints().size());

		assertThat(mergedArchitectureContext.getViewpoints(),
				allOf(hasItem(mViewpointNamed("viewpoint1")), hasItem(mViewpointNamed("viewpoint2"))));
	}

	@Test
	public void testMergeViewpointsDifferentName() {
		rename(ArchitecturePackage.Literals.ARCHITECTURE_VIEWPOINT, "viewpoint2", "differentName");

		List<MergedArchitectureContext> mergedContexts = mergeContexts();
		assertEquals("Contexts with same name aren't merged", 2, mergedContexts.size());
		MergedArchitectureContext mergedArchitectureContext = mergedContexts.iterator().next();
		assertEquals("Viewpoints with different names are merged", 3, mergedArchitectureContext.getViewpoints().size());

		assertThat(mergedArchitectureContext.getViewpoints(),
				allOf(hasItem(mViewpointNamed("viewpoint1")), hasItem(mViewpointNamed("viewpoint2")),
						hasItem(mViewpointNamed("differentName"))));
	}

	@Test
	public void testMergeViewpointsRepresentationKinds() {
		List<MergedArchitectureContext> mergedContexts = mergeContexts();
		assertEquals("Contexts with same name aren't merged", 2, mergedContexts.size());
		MergedArchitectureContext mergedArchitectureContext = mergedContexts.iterator().next();
		assertEquals("Viewpoints with same name aren't merged", 2, mergedArchitectureContext.getViewpoints().size());
		MergedArchitectureViewpoint mergedArchitectureViewpoint = mergedArchitectureContext.getViewpoints().iterator().next();
		assertEquals("Representation kinds not properly merged", 2, mergedArchitectureViewpoint.getRepresentationKinds().size());
		
		assertThat(mergedArchitectureViewpoint.getRepresentationKinds(),
				allOf(hasItem(diagramNamed("diagram1")), hasItem(diagramNamed("diagram3"))));
	}

	//
	// Test framework
	//

	@Before
	public void loadTestModel() {
		resourceSet = new ResourceSetImpl();

		if (!EcorePlugin.IS_ECLIPSE_RUNNING) {
			// Ensure registration of the packages we use
			ArchitecturePackage.eINSTANCE.getADElement();
			RepresentationPackage.eINSTANCE.getPapyrusDiagram();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("architecture", new ArchitectureResourceFactory());
		}
		
		URL url = MergedArchitectureDomainTest.class.getResource("domain1.architecture");
		Resource resource1 = resourceSet.getResource(URI.createURI(url.toExternalForm()), true);
		domain1 = (ArchitectureDomain) resource1.getContents().get(0);

		domain2 = EcoreUtil.copy(domain1);
		Resource resource2 = resourceSet.createResource(resource1.getURI().trimSegments(1).appendSegment("domain2.architecture"));
		resource2.getContents().add(domain2);
		
		rename(ArchitecturePackage.Literals.REPRESENTATION_KIND, "diagram1", "diagram3");
		rename(ArchitecturePackage.Literals.REPRESENTATION_KIND, "diagram2", "diagram4");
	}

	@SuppressWarnings("restriction")
	public static List<MergedArchitectureDomain> merge(ArchitectureDomain... domains) {
		List<ArchitectureDomain> input = List.of(domains);

		return Lists.newArrayList(org.eclipse.papyrus.infra.core.internal.architecture.merger.InternalArchitectureDomainMerger.newInstance()
				.mergeDomains(input));
	}

	List<MergedArchitectureContext> mergeContexts() {
		List<MergedArchitectureDomain> domains = merge(domain1, domain2);
		return domains.stream()
				.map(MergedArchitectureDomain::getContexts).flatMap(Collection::stream)
				.collect(Collectors.toUnmodifiableList());
	}

	/** Rename an element in {@code domain2}. */
	void rename(EClass eClass, String oldName, String newName) {
		if (!ArchitecturePackage.Literals.AD_ELEMENT.isSuperTypeOf(eClass)) {
			throw new IllegalArgumentException("eClass does not conform to ADElement");
		}

		for (Iterator<EObject> iter = domain2.eResource().getAllContents(); iter.hasNext();) {
			EObject next = iter.next();
			if (eClass.isInstance(next)) {
				ADElement element = (ADElement) next;
				if (oldName.equals(element.getName())) {
					element.setName(newName);
					break;
				}
			}
		}
	}

}
