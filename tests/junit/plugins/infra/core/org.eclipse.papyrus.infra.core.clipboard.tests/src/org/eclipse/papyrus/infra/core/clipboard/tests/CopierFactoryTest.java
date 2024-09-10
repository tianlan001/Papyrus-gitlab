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

package org.eclipse.papyrus.infra.core.clipboard.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory.Configuration;
import org.eclipse.papyrus.infra.core.internal.clipboard.CopierFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Test cases for the {@link CopierFactory} class.
 */
@SuppressWarnings("restriction")
public class CopierFactoryTest {

	@Rule
	public final TestRule configRule = TestConfigurator.rule();

	/**
	 * Initializes me.
	 */
	public CopierFactoryTest() {
		super();
	}

	/**
	 * Tests the factory with the default copy behaviour.
	 */
	@Test
	public void defaultCopierFactory() {
		EClass original = createTestModel();

		EcoreUtil.Copier copier = ICopierFactory.getInstance(null).get();
		EClass copy = copy(original, copier);

		// Check that we have the copy we expect
		assertThat(copy, not(sameInstance(original)));
		assertThat(copy.getName(), is(original.getName()));
		assertThat(copy.getEReferences().size(), is(2));

		// And that the correct filtering was applied
		copy.getEReferences().forEach(ref -> assertThat(ref.getEOpposite(), nullValue()));

		// Not this
		assertThat(copy.getEReferences().stream().flatMap(ref -> ref.getEAnnotations().stream())
				.peek(a -> assertThat(a.getReferences(), not(isEmpty())))
				.count(), greaterThan(0L)); // In fact we have tested at least one
	}

	/**
	 * Tests the factory with non-default copy behaviour to verify that
	 * configurators see the correct options in in the configuration.
	 */
	@Test
	public void oneOffCopierFactory() {
		EClass original = createTestModel();

		EcoreUtil.Copier copier = ICopierFactory.getInstance(null, false).get();
		EClass copy = copy(original, copier);

		// Check that we have the copy we expect
		assertThat(copy, not(sameInstance(original)));
		assertThat(copy.getName(), is(original.getName()));
		assertThat(copy.getEReferences().size(), is(2));

		// And that the correct filtering was applied
		assertThat(copy.getEReferences().stream().flatMap(ref -> ref.getEAnnotations().stream())
				.peek(a -> assertThat(a.getReferences(), isEmpty()))
				.count(), greaterThan(0L)); // In fact we have tested at least one

		// Not this
		copy.getEReferences().forEach(ref -> assertThat(ref.getEOpposite(), notNullValue()));
	}

	//
	// Test framework
	//

	EClass createTestModel() {
		EClass result = EcoreFactory.eINSTANCE.createEClass();
		result.setName("Test");

		EReference r1 = EcoreFactory.eINSTANCE.createEReference();
		r1.setName("r1");
		result.getEStructuralFeatures().add(r1);

		EReference r2 = EcoreFactory.eINSTANCE.createEReference();
		r2.setName("r2");
		result.getEStructuralFeatures().add(r2);

		r2.setEOpposite(r1);
		r1.setEOpposite(r2);

		EOperation o1 = EcoreFactory.eINSTANCE.createEOperation();
		o1.setName("o1");
		result.getEOperations().add(o1);

		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource("test");
		r1.getEAnnotations().add(annotation);
		annotation.getReferences().add(o1);

		return result;
	}

	<T extends EObject> T copy(T original, EcoreUtil.Copier copier) {
		@SuppressWarnings("unchecked")
		T result = (T) copier.copy(original);
		copier.copyReferences();
		return result;
	}

	//
	// Nested types
	//

	public static final class TestConfigurator implements ICopierFactory.Configurator {
		static boolean isEnabled;

		@Override
		public void configureCopier(Configuration copierConfiguration) {
			if (isEnabled) {
				if (copierConfiguration.isUseOriginalReferences()) {
					filter(copierConfiguration, EcorePackage.Literals.EREFERENCE__EOPPOSITE);
				} else {
					filter(copierConfiguration, EcorePackage.Literals.EANNOTATION__REFERENCES);
				}
			}
		}

		void filter(Configuration copierConfig, EReference reference, EClass ownerType) {
			copierConfig.filterReferences((ref, owner) -> (ref == reference) && ownerType.isInstance(owner));
		}

		void filter(Configuration copierConfig, EReference reference) {
			filter(copierConfig, reference, reference.getEContainingClass());
		}

		static TestRule rule() {
			return new TestWatcher() {
				@Override
				protected void starting(Description description) {
					isEnabled = true;
				}

				@Override
				protected void finished(Description description) {
					isEnabled = false;
				}
			};
		}
	}
}
