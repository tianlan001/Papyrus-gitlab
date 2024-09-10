/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bugs 463564, 488791
 *
 */
package org.eclipse.papyrus.infra.emf.readonly;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.eclipse.papyrus.infra.emf.readonly.tests.PapyrusROEditingDomainFixture;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.JavaResource;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.eclipse.uml2.uml.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterators;


/**
 * This is the ReadOnlyTesterTest type. Enjoy.
 */
@JavaResource("Bug323802.uml")
public class ReadOnlyTesterTest extends AbstractPapyrusTest {

	@Rule
	public final ReadOnlyCacheRule cacheRule = new ReadOnlyCacheRule();

	@Rule
	public final PapyrusROEditingDomainFixture domain = new PapyrusROEditingDomainFixture();

	private final ProjectFixture project = domain.getProject();

	private MyReadOnlyTester fixture;

	@Test
	public void testAsBoolean() {
		assertThat(fixture.asBoolean(true), is(true));
		assertThat(fixture.asBoolean(false), is(false));
		assertThat(fixture.asBoolean("hello"), is(true));
		assertThat(fixture.asBoolean(null), is(true));
	}

	@Test
	public void testIsReadOnly() {
		assertThat(fixture.testIsReadOnly(Iterators.singletonIterator(domain.getModel()), false), is(true));

		Type string = domain.getModel().getImportedPackages().get(0).getOwnedType("String");
		assertThat(fixture.testIsReadOnly(Iterators.singletonIterator(string), true), is(true));

		project.setReadOnly(domain.getModelResource());
		cacheRule.clear(); // Clear the cache
		assertThat(fixture.testIsReadOnly(Iterators.singletonIterator(domain.getModel()), true), is(true));
	}

	@Test
	public void testCanMakeWritable() {
		// If it's already writable, well, then it's trivially easy to make it writable
		assertThat(fixture.canMakeWritable(Iterators.singletonIterator(domain.getModel()), true), is(true));

		Type string = domain.getModel().getImportedPackages().get(0).getOwnedType("String");
		assertThat(fixture.canMakeWritable(Iterators.singletonIterator(string), true), is(false));

		project.setReadOnly(domain.getModelResource());
		assertThat(fixture.canMakeWritable(Iterators.singletonIterator(domain.getModel()), true), is(true));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() throws Exception {
		fixture = new MyReadOnlyTester();
	}

	@After
	public void destroyFixture() {
		fixture = null;
	}

	private static class MyReadOnlyTester extends ReadOnlyTester {
		MyReadOnlyTester() {
			super();
		}

		// Overridden to make it accessible
		@Override
		protected Boolean asBoolean(Object expectedValue) {
			return super.asBoolean(expectedValue);
		}

		// Overridden to make it accessible
		@Override
		protected boolean canMakeWritable(Iterator<?> objects, Boolean expectedValue) {
			return super.canMakeWritable(objects, expectedValue);
		}

		// Overridden to make it accessible
		@Override
		protected boolean testIsReadOnly(Iterator<?> objects, Boolean expectedValue) {
			return super.testIsReadOnly(objects, expectedValue);
		}
	}
}
