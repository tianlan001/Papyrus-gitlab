/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.resource;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.junit.framework.classification.ClassificationRunner;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;

/**
 * Tests for the {@link CrossReferenceIndex} class, the full indexer.
 */
@RunWith(Enclosed.class)
public abstract class CrossReferenceIndexTest extends AbstractCrossReferenceIndexTest {
	private final boolean shardsOnly;

	/**
	 * Initializes me.
	 */
	CrossReferenceIndexTest(boolean shardsOnly) {
		super(false); // Always the full indexer

		this.shardsOnly = shardsOnly;
	}

	//
	// Don't need to load any resources for these tests
	//

	@Test
	public void isShard() throws Exception {
		assertThat(index().isShard(uri("package1/packageA/foo.uml")), is(shardsOnly));
		assertThat(index().isShard(uri("package1/packageA.uml")), is(shardsOnly));
		assertThat(index().isShard(uri("package1.uml")), is(shardsOnly));
		assertThat(index().isShard(uri("root.uml")), is(false));
	}

	@Test
	public void subunits() throws Exception {
		assertThat(index().getSubunits(uri("package1/packageA/foo.uml"), shardsOnly), is(emptySet()));
		assertThat(index().getSubunits(uri("package1/packageA.uml"), shardsOnly), is(singleton(uri("package1/packageA/foo.uml"))));
		assertThat(index().getSubunits(uri("package1.uml"), shardsOnly), is(singleton(uri("package1/packageA.uml"))));
		assertThat(index().getSubunits(uri("root.uml"), shardsOnly), // This one has two sub-units
				is(ImmutableSet.of(uri("package1.uml"), uri("package2.uml"))));
	}

	@Test
	public void parents() throws Exception {
		assertThat(index().getParents(uri("package1/packageA/foo.uml"), shardsOnly), is(singleton(uri("package1/packageA.uml"))));
		assertThat(index().getParents(uri("package1/packageA.uml"), shardsOnly), is(singleton(uri("package1.uml"))));
		assertThat(index().getParents(uri("package1.uml"), shardsOnly), is(singleton(uri("root.uml"))));
		assertThat(index().getParents(uri("root.uml"), shardsOnly), is(emptySet()));
	}

	@Test
	public void roots() throws Exception {
		assertThat(index().getRoots(uri("package1/packageA/foo.uml"), shardsOnly), is(singleton(uri("root.uml"))));
		assertThat(index().getRoots(uri("package1/packageA.uml"), shardsOnly), is(singleton(uri("root.uml"))));
		assertThat(index().getRoots(uri("package1.uml"), shardsOnly), is(singleton(uri("root.uml"))));

		// A root has no parents and, therefore, no root
		assertThat(index().getRoots(uri("root.uml"), shardsOnly), is(emptySet()));

		// And this one has nothing to do with shards
		assertThat(index().getRoots(uri("referencing.uml"), shardsOnly), is(emptySet()));
	}

	@Test
	public void roots_alternate() throws Exception {
		// The alternate index is the on-demand index that only works with the
		// shard annotation, so it's not applicable in the context of sub-models
		assumeThat("Test not applicable to sub-models", shardsOnly, is(true));

		ICrossReferenceIndex index = index();
		ICrossReferenceIndex alternate = ICrossReferenceIndex.getAlternate(index, fixture);

		// Trigger re-indexing
		project.getFile(project.getURI("package1/packageA/foo.uml")).touch(null);

		assertThat(index.getRoots(uri("package1/packageA/foo.uml"), shardsOnly, alternate), is(singleton(uri("root.uml"))));
		assertThat(index.getRoots(uri("package1/packageA.uml"), shardsOnly, alternate), is(singleton(uri("root.uml"))));
		assertThat(index.getRoots(uri("package1.uml"), shardsOnly, alternate), is(singleton(uri("root.uml"))));

		// A root has no parents and, therefore, no root
		assertThat(index.getRoots(uri("root.uml"), shardsOnly, alternate), is(emptySet()));

		// And this one has nothing to do with shards
		assertThat(index.getRoots(uri("referencing.uml"), shardsOnly, alternate), is(emptySet()));
	}

	@Test
	public void outgoingReferences_givenURI() throws Exception {
		// Shard relationship (cross-resource containment) is not a cross-reference
		assertThat(index().getOutgoingCrossReferences(uri("package1.uml")), is(emptySet()));

		// We find cross-references to non-workspace resources, though those aren't indexed
		assertThat(index().getOutgoingCrossReferences(uri("root.uml")),
				is(singleton(URI.createURI(UMLResource.ECORE_PROFILE_URI))));

		// This has a cross-reference in the class generalization
		assertThat(index().getOutgoingCrossReferences(uri("referencing.uml")),
				is(singleton(uri("package2/packageB/bar.uml"))));

		// This API is generalized for the Papyrus one-file
		assertThat(index().getOutgoingCrossReferences(uri("referencing.di")),
				is(singleton(uri("package2/packageB/bar.di"))));
	}

	@Test
	public void incomingReferences_givenURI() throws Exception {
		// Parent pointer in shard annotation is not a cross-reference
		assertThat(index().getIncomingCrossReferences(uri("root.uml")), is(emptySet()));
		assertThat(index().getIncomingCrossReferences(uri("package1.uml")), is(emptySet()));

		// This has a cross-reference in the class generalization
		assertThat(index().getIncomingCrossReferences(uri("package2/packageB/bar.uml")),
				is(singleton(uri("referencing.uml"))));

		// This API is generalized for the Papyrus one-file
		assertThat(index().getIncomingCrossReferences(uri("package2/packageB/bar.di")),
				is(singleton(uri("referencing.di"))));
	}

	@Test
	public void outgoingReferences() throws Exception {
		SetMultimap<URI, URI> xrefs = index().getOutgoingCrossReferences();

		// Shard relationship (cross-resource containment) is not a cross-reference
		assertThat(xrefs.get(uri("package1.uml")), is(emptySet()));

		// We find cross-references to non-workspace resources, though those aren't indexed
		assertThat(xrefs.get(uri("root.uml")),
				is(singleton(URI.createURI(UMLResource.ECORE_PROFILE_URI))));

		// This has a cross-reference in the class generalization
		assertThat(xrefs.get(uri("referencing.uml")),
				is(singleton(uri("package2/packageB/bar.uml"))));

		// This API is *not* generalized for the Papyrus one-file
		assertThat(xrefs.get(uri("referencing.di")), is(emptySet()));
	}

	@Test
	public void incomingReferences() throws Exception {
		SetMultimap<URI, URI> xrefs = index().getIncomingCrossReferences();

		// Parent pointer in shard annotation is not a cross-reference
		assertThat(xrefs.get(uri("root.uml")), is(emptySet()));
		assertThat(xrefs.get(uri("package1.uml")), is(emptySet()));

		// This has a cross-reference in the class generalization
		assertThat(xrefs.get(uri("package2/packageB/bar.uml")),
				is(singleton(uri("referencing.uml"))));

		// This API is *not* generalized for the Papyrus one-file
		assertThat(xrefs.get(uri("package2/packageB/bar.di")), is(emptySet()));
	}

	//
	// Nested types
	//

	// Need to be explicit so that we don't inherit the Enclosed for a recursion error
	@RunWith(ClassificationRunner.class)
	public static class SubmodelsTest extends CrossReferenceIndexTest {
		public SubmodelsTest() {
			super(false);
		}

		@BeforeClass
		public static void createProjectContents() {
			createProjectContents("resources/submodels");
		}
	}

	// Need to be explicit so that we don't inherit the Enclosed for a recursion error
	@RunWith(ClassificationRunner.class)
	public static class ShardsTest extends CrossReferenceIndexTest {
		public ShardsTest() {
			super(true);
		}

		@BeforeClass
		public static void createProjectContents() {
			createProjectContents("resources/shards");
		}
	}
}
