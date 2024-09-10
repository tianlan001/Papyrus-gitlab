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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.emf.internal.resource.CrossReferenceIndex;
import org.eclipse.papyrus.infra.emf.internal.resource.OnDemandCrossReferenceIndex;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

/**
 * Test framework for cross-reference index and shards-related tests.
 */
public abstract class AbstractCrossReferenceIndexTest {

	@ClassRule
	public static final ProjectFixture project = new ProjectFixture();

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final AnnotationRule<String[]> resourcePaths = AnnotationRule.create(ProjectResource.class);

	protected ResourceSet fixture;

	@CleanUp
	protected List<Resource> initialResources;

	private final Function<? super ResourceSet, ? extends ICrossReferenceIndex> indexFunction;

	/**
	 * Initializes me.
	 */
	public AbstractCrossReferenceIndexTest() {
		this(null);
	}

	/**
	 * Initializes me.
	 * 
	 * @param onDemand
	 *            {@code true} to force the on-demand index, {@code false} to force the
	 *            full index, or {@code null} to let the framework decide the best
	 *            available index
	 */
	public AbstractCrossReferenceIndexTest(Boolean onDemand) {
		super();

		if (onDemand == null) {
			// Best available
			indexFunction = ICrossReferenceIndex::getInstance;
		} else if (onDemand) {
			// Force the on-demand index
			indexFunction = OnDemandCrossReferenceIndex::new;
		} else {
			// Force the full index
			indexFunction = __ -> CrossReferenceIndex.getInstance();
		}
	}

	public static void createProjectContents(String basePath) {
		List<String> resources = ImmutableList.of(
				"root.uml",
				"package1.uml",
				"package1/packageA.uml",
				"package1/packageA/foo.uml",
				"package2.uml",
				"package2/packageB.uml",
				"package2/packageB/bar.uml",
				"referencing.uml");

		resources.forEach(res -> {
			try {
				project.createFile(res, AbstractCrossReferenceIndexTest.class,
						String.format("%s/%s", basePath, res));
			} catch (IOException e) {
				e.printStackTrace();
				fail("Failed to create test resource: " + e.getMessage());
			}
		});
	}

	@Before
	public void createFixture() {
		fixture = housekeeper.createResourceSet();

		new ShardResourceLocator((ResourceSetImpl) fixture, index());

		// Load resources
		if (resourcePaths.get() != null) {
			URI base = baseURI();
			initialResources = Stream.of(resourcePaths.get())
					.map(URI::createURI)
					.map(uri -> uri.resolve(base))
					.map(uri -> fixture.getResource(uri, true))
					.collect(Collectors.toList());
		}
	}

	protected final ICrossReferenceIndex index() {
		return indexFunction.apply(fixture);
	}

	URI baseURI() {
		return URI.createPlatformResourceURI(project.getProject().getName() + "/", true);
	}

	protected URI uri(String path) {
		URI result = URI.createURI(path, true);
		return result.resolve(baseURI());
	}

	protected Resource requireLoaded(String path) {
		Resource result = fixture.getResource(uri(path), false);
		assertThat("resource not created: " + path, result, notNullValue());
		assertThat("resource not loaded: " + path, result.isLoaded(), is(true));
		return result;
	}

	protected Stereotype requireStereotype(Element element, String qualifiedName) {
		Stereotype result = element.getApplicableStereotype(qualifiedName);
		assertThat("stereotype not applicable: " + qualifiedName, result, notNullValue());
		assertThat("stereotype not applied: " + qualifiedName, element.isStereotypeApplied(result), is(true));
		return result;
	}

	protected Stereotype requireEClass(Classifier classifier) {
		return requireStereotype(classifier, "Ecore::EClass");
	}

	protected Stereotype requireEPackage(Package package_) {
		return requireStereotype(package_, "Ecore::EPackage");
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ProjectResource {
		String[] value();
	}
}
