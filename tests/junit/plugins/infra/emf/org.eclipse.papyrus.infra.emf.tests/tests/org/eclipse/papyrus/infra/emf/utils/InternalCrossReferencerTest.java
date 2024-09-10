/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.utils;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for the {@link InternalCrossReferencer} class.
 */
@RunWith(Parameterized.class)
public class InternalCrossReferencerTest {

	private EObject referent;
	private EObject referrer;
	private EReference reference;

	private Object scope;
	private boolean expectedToFind;

	/**
	 * Initializes me.
	 *
	 * @param label
	 *            the test label (unused)
	 * @param referent
	 *            the object for which to find an internal reference to it
	 * @param referrer
	 *            the object that has a reference to the {@code referent}
	 * @param reference
	 *            the feature by which the {@link referrer} references the {@code referent}
	 * @param scope
	 *            the scope to search
	 * @param expectedToFind
	 *            whether the search is expected to find the <em>internal</em> cross-reference or not
	 */
	public InternalCrossReferencerTest(String label, EObject referent, EObject referrer, EReference reference, Object scope, boolean expectedToFind) {
		super();

		this.referent = referent;
		this.referrer = referrer;
		this.reference = reference;
		this.scope = scope;
		this.expectedToFind = expectedToFind;
	}

	/**
	 * Verify that an internal cross-reference search in the test's scope finds
	 * or does not find the internal cross-reference as indicated. In any case it is
	 * asserted that the cross-reference <em>does actually exist</em> even if only as
	 * an <em>external</em> reference.
	 */
	@Test
	public void testFind() {
		checkExternalCrossReference();

		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences;
		if (scope instanceof EObject) {
			crossReferences = InternalCrossReferencer.find((EObject) scope);
		} else if (scope instanceof Resource) {
			crossReferences = InternalCrossReferencer.find((Resource) scope);
		} else if (scope instanceof ResourceSet) {
			crossReferences = InternalCrossReferencer.find((ResourceSet) scope);
		} else if (scope instanceof Collection<?>) {
			crossReferences = InternalCrossReferencer.find((Collection<?>) scope);
		} else {
			fail("Unsupported search scope type: " + scope);
			return; // Unreachable
		}

		if (expectedToFind) {
			verify(crossReferences);
		} else {
			verifyNot(crossReferences);
		}
	}

	//
	// Test configurations
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				// Test that a cross-reference is found when both objects are in a forest of EObjects
				new Fixture().label("collection (internal)").scoped(collection(false)).expectToFind(true).toParameters(),
				// Test that a cross-reference is ignored when only the referring object object is in the search scope
				new Fixture().label("collection (external)").scoped(collection(true)).expectToFind(false).toParameters(),
				// Test that a cross-reference is found when both objects are in a single tree of EObjects
				new Fixture().label("single tree (internal)").scoped(container(false)).expectToFind(true).toParameters(),
				// Test that a cross-reference is ignored when only the referring object object is in the search scope
				new Fixture().label("single tree (external)").scoped(container(true)).expectToFind(false).toParameters(),
				// Test that a cross-reference is found when both objects are in a single resource
				new Fixture().label("resource (internal)").scoped(resource(false)).expectToFind(true).toParameters(),
				// Test that a cross-reference is ignored when the objects are in separate resources but only the
				// referring object's resource is in the search scope
				new Fixture().label("separate resources (external)").scoped(resource(true)).expectToFind(false).toParameters(),
				// Test that a cross-reference is found when the objects are in separate resources and both
				// resources are in the search scope
				new Fixture().label("separate resources (internal)").scoped(resources(true)).expectToFind(true).toParameters(),
				// Test that a cross-reference is found when both objects are in a single resource set
				new Fixture().label("resource set (internal)").scoped(resourceSet(true)).expectToFind(true).toParameters(),
				// Test that a cross-reference is ignored when the objects are in separate resource sets and only the
				// referring object's resource set is in the search scope
				new Fixture().label("separate resource sets (external)").scoped(resourceSets(true)).expectToFind(false).toParameters(),
				// Test that a cross-reference is found when the objects are in separate resource sets and both
				// resource sets are in the search scope
				new Fixture().label("separate resource sets (internal)").scoped(resourceSets(false)).expectToFind(true).toParameters(),
		});
	}

	/**
	 * Scope function that puts objects into some collection. The result is the collection as scope.
	 *
	 * @param referrerOnly
	 *            whether to put only the referring object into the collection
	 */
	static BiFunction<EObject, EObject, Collection<? super EObject>> collection(boolean referrerOnly) {
		return aggregate(BasicEList::new, Function.identity(), !referrerOnly, true);
	}

	/**
	 * Scope function that puts objects into some container object. The result is the container object as scope.
	 *
	 * @param referrerOnly
	 *            whether to put only the referring object into the container
	 */
	static BiFunction<EObject, EObject, ? extends EObject> container(boolean referrerOnly) {
		return aggregate(EcoreFactory.eINSTANCE::createEAnnotation, EAnnotation::getContents, !referrerOnly, true);
	}

	/**
	 * Scope function that puts objects into resources.
	 *
	 * @param separate
	 *            whether the objects are put each into their own resource, or otherwise into the same resource.
	 *            In the former case, the scope will be the resource containing the referring object
	 */
	static BiFunction<EObject, EObject, Resource> resource(boolean separate) {
		// The referrer is always in the first resource in the set
		return resources(separate).andThen(c -> c.get(0));
	}

	/**
	 * Scope function that puts objects into resources.
	 *
	 * @param separate
	 *            whether the objects are put each into their own resource
	 */
	static BiFunction<EObject, EObject, List<Resource>> resources(boolean separate) {
		// The referrer is always in the first resource in the set
		return resourceSet(separate).andThen(ResourceSet::getResources);
	}

	/**
	 * Scope function that puts every object into a single resource set.
	 *
	 * @param separateResources
	 *            whether the objects are put each into their own resource
	 */
	static BiFunction<EObject, EObject, ResourceSet> resourceSet(boolean separateResources) {
		BiConsumer<ResourceSet, ? super EObject> acceptor = (rset, e) -> {
			Resource res;
			if (separateResources || rset.getResources().isEmpty()) {
				res = new ResourceImpl();
				rset.getResources().add(0, res); // Insert so that the referrer is always in the first resource
			} else {
				res = rset.getResources().get(0);
			}
			res.getContents().add(e);
		};

		return generic(ResourceSetImpl::new, acceptor, acceptor, true, true);
	}

	/**
	 * Scope function that puts every object into its own resource set.
	 *
	 * @param referrerOnly
	 *            whether the scope includes only the referring object's resource set
	 */
	static BiFunction<EObject, EObject, ?> resourceSets(boolean referrerOnly) {
		Function<EObject, ResourceSet> rsetFactory = e -> {
			ResourceSet result = new ResourceSetImpl();
			Resource res = new ResourceImpl();
			result.getResources().add(res);
			res.getContents().add(e);
			return result;
		};

		// The referrer is always in the first resource set
		BiConsumer<List<ResourceSet>, EObject> acceptor = (rsets, e) -> rsets.add(0, rsetFactory.apply(e));

		BiFunction<EObject, EObject, List<ResourceSet>> result = generic(BasicEList::new, acceptor, acceptor, true, true);
		return referrerOnly ? result.andThen(rset -> rset.get(0)) : result;
	}

	/** Scope function that aggregates the model objects in a collection or other kind of container. */
	static <T> BiFunction<EObject, EObject, T> aggregate(Supplier<T> factory, Function<? super T, ? extends Collection<? super EObject>> contentsFunction,
			boolean include1, boolean include2) {

		BiConsumer<T, ? super EObject> acceptor = (r, e) -> contentsFunction.apply(r).add(e);
		return generic(factory, acceptor, acceptor, include1, include2);
	}

	/** Generic scope function. */
	static <T> BiFunction<EObject, EObject, T> generic(Supplier<T> factory,
			BiConsumer<? super T, ? super EObject> acceptor1, BiConsumer<? super T, ? super EObject> acceptor2,
			boolean include1, boolean include2) {

		return (e1, e2) -> {
			T result = factory.get();
			if (include1) {
				acceptor1.accept(result, e1);
			}
			if (include2) {
				acceptor2.accept(result, e2);
			}
			return result;
		};
	}

	//
	// Test framework
	//

	/**
	 * Verify that the map of cross references contains the reference from our fixture
	 * 'referrer' element to our fixture 'referent' element.
	 *
	 * @param crossReferences
	 *            the map of cross-references found in the test scenario
	 */
	void verify(Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(referent);
		assertThat("EClass references not found by cross-referencer", settings, notNullValue());

		EStructuralFeature.Setting setting = settings.stream()
				.filter(s -> s.getEObject() == referrer && s.getEStructuralFeature() == reference)
				.findFirst()
				.orElse(null);
		assertThat("Reference from EReference to EClass not found by cross-referencer", setting, notNullValue());
	}

	/**
	 * Verify that the map of cross references <em>does not</em> contain the reference from our fixture
	 * 'referrer' element to our fixture 'referent' element, despite that the scan encountered the
	 * 'referrer', because the reference in the test scenario was an <em>external</em> reference.
	 *
	 * @param crossReferences
	 *            the map of cross-references found in the test scenario
	 */
	void verifyNot(Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(referent);
		if (settings == null) {
			// Good, that's what we want
			return;
		}

		EStructuralFeature.Setting setting = settings.stream()
				.filter(s -> s.getEObject() == referrer && s.getEStructuralFeature() == reference)
				.findFirst()
				.orElse(null);
		assertThat("External reference from EReference to EClass found by internal cross-referencer", setting, nullValue());
	}

	/**
	 * Verify that the map of cross references contains the reference from our fixture
	 * 'referrer' element to our fixture 'referent' element, even if only as an
	 * <em>external</em> reference (not internal).
	 *
	 * @param crossReferences
	 *            the collection of cross-references found in the test scenario
	 */
	void checkExternalCrossReference() {
		Collection<?> scope = this.scope instanceof Collection ? (Collection<?>) this.scope : Set.of(this.scope);
		Collection<EStructuralFeature.Setting> crossReferences = EcoreUtil.UsageCrossReferencer.find(referent, scope);
		assertThat("Bad test setup: the target object is not referenced at all in the scenario", crossReferences, hasItem(anything()));

		EStructuralFeature.Setting setting = crossReferences.stream()
				.filter(s -> s.getEObject() == referrer && s.getEStructuralFeature() == reference)
				.findFirst()
				.orElse(null);
		assertThat("Bad test setup: the target object is not referenced by the fixture", setting, notNullValue());
	}

	//
	// Nested types
	//

	/**
	 * A factory of fixtures on which to operate the tests.
	 */
	private static final class Fixture {
		final EPackage package1 = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage package2 = EcoreFactory.eINSTANCE.createEPackage();
		// An object for which the tests seek references to it. It is in 'package1'
		final EClass referent = EcoreFactory.eINSTANCE.createEClass();
		final EClass referencingClass = EcoreFactory.eINSTANCE.createEClass();
		// An object having a reference to the 'referent' that tests seek. It is in 'package2'
		final EReference referrer = EcoreFactory.eINSTANCE.createEReference();

		private String label;
		private Object scope;
		private boolean expectedToFind;

		{
			package1.getEClassifiers().add(referent);
			package2.getEClassifiers().add(referencingClass);
			referencingClass.getEStructuralFeatures().add(referrer);
			referrer.setEType(referent);
		}

		/** Set the test label to display in the JUnit report. */
		Fixture label(String label) {
			this.label = label;
			return this;
		}

		/**
		 * Set the scope function that determines how the packages containing (first) the referenced object and
		 * (second) the referencing object are organized relative to one another and which of them are or are
		 * not in the search scope. The result is the scope on which the cross-reference search is performed
		 * and it must be either an {@link EObject}, a {@link Resource}, a {@link ResourceSet}, or a collection
		 * of any of these types.
		 */
		Fixture scoped(BiFunction<? super EPackage, ? super EPackage, ?> scopeFunction) {
			scope = scopeFunction.apply(package1, package2);
			expectedToFind = true;
			return this;
		}

		/** Set whether the test should expect to find the cross reference, or not. */
		Fixture expectToFind(boolean expect) {
			this.expectedToFind = expect;
			return this;
		}

		/** Generate the test parameters. */
		Object[] toParameters() {
			return new Object[] { label, referent, referrer, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE, scope, expectedToFind };
		}

	}

}
