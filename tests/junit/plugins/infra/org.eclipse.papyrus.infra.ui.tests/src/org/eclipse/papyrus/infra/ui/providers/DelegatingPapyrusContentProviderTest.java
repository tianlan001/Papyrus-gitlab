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

package org.eclipse.papyrus.infra.ui.providers;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests for the {@link DelegatingPapyrusContentProvider} class.
 */
public class DelegatingPapyrusContentProviderTest {

	private final DelegatingPapyrusContentProvider fixture = new DelegatingPapyrusContentProvider(TestContentProvider.example1());

	/**
	 * Test for {@link DelegatingPapyrusContentProvider#getElements(java.lang.Object)}.
	 */
	@Test
	public void getElements_object() {
		assertThat(fixture.getElements("foo"), is(new Object[] { "root1", "root2", "root3" }));
	}

	/**
	 * Test method for {@link DelegatingPapyrusContentProvider#dispose()}.
	 */
	@Test
	public void dispose() {
		assertThat(fixture.getElements().length, greaterThan(0));
		fixture.dispose();
		assertThat(fixture.getElements().length, is(0));
	}

	/**
	 * Test method for {@link DelegatingPapyrusContentProvider#getChildren(java.lang.Object)}.
	 */
	@Test
	public void getChildren() {
		assertThat(fixture.getChildren("root2"), is(new Object[] { "child2.1", "child2.2", "child2.3" }));
		assertThat(fixture.getChildren("child2.2"), is(new Object[] { "child2.2.1", "child2.2.2" }));
		assertThat(fixture.getChildren("bogus"), is(new Object[] {}));
	}

	/**
	 * Test method for {@link DelegatingPapyrusContentProvider#getParent(Object)}.
	 */
	@Test
	public void getParent() {
		assertThat(fixture.getParent("root2"), nullValue()); // It's a root
		assertThat(fixture.getParent("child2.2"), is("root2"));
		assertThat(fixture.getParent("child2.2.2"), is("child2.2"));
		assertThat(fixture.getParent("bogus"), nullValue()); // It's not an element
	}

	/**
	 * Test method for {@link DelegatingPapyrusContentProvider#hasChildren(java.lang.Object)}.
	 */
	@Test
	public void hasChildren() {
		assertThat(fixture.hasChildren("root2"), is(true));
		assertThat(fixture.hasChildren("child2.2"), is(true));
		assertThat(fixture.hasChildren("child2.2.3"), is(false));
		assertThat(fixture.hasChildren("bogus"), is(false)); // It's not an element
	}

	/**
	 * Test method for {@link DelegatingPapyrusContentProvider#getAdaptedValue(java.lang.Object)}.
	 */
	@Test
	public void getAdaptedValue() {
		assertThat(fixture.getAdaptedValue("root1"), notNullValue());
		assertThat(fixture.getAdaptedValue("root1"), sameInstance(fixture.getAdaptedValue("root1")));
		assertThat(fixture.getAdaptedValue("child1.2"), notNullValue());
		assertThat(fixture.getAdaptedValue("child1.2"), not(fixture.getAdaptedValue("root1")));
		assertThat(fixture.getAdaptedValue("bogus"), nullValue());
	}

	/**
	 * Test method for {@link DelegatingPapyrusContentProvider#isValidValue(java.lang.Object)}.
	 */
	@Test
	public void isValidValue() {
		assertThat(fixture.isValidValue("root1"), is(true));
		assertThat(fixture.isValidValue("child1.2"), is(true));
		assertThat(fixture.isValidValue("bogus"), is(false));
	}

	/**
	 * Test method for {@link DelegatingPapyrusContentProvider#getElements()}.
	 */
	@Test
	public void getElements() {
		assertThat(fixture.getElements(), is(new Object[] { "root1", "root2", "root3" }));
	}

}
