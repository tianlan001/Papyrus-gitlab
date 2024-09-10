/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.providers;

import static org.eclipse.papyrus.infra.ui.providers.TestContentProvider.factory1;
import static org.eclipse.papyrus.infra.ui.providers.TestContentProvider.factory2;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.junit.Test;

/**
 * Test cases for the default composed semantic content provider factory.
 */
public class SemanticContentProviderFactoryTest {

	private ITreeContentProvider fixture = factory1().compose(factory2())
			.createSemanticContentProvider(null); // Doesn't actually need a resource set

	private IStaticContentProvider sFixture = (IStaticContentProvider) fixture;
	private IHierarchicContentProvider hFixture = (IHierarchicContentProvider) fixture;
	private IAdaptableContentProvider aFixture = (IAdaptableContentProvider) fixture;

	@Test
	public void getElements_Object() {
		assertThat(fixture.getElements("foo"),
				is(new Object[] { "root1", "root2", "root3", "A", "B", "C" }));
	}

	@Test
	public void dispose() {
		assertThat(sFixture.getElements().length, greaterThan(3));
		fixture.dispose();
		assertThat(sFixture.getElements().length, is(0));
	}

	@Test
	public void getChildren() {
		assertThat(fixture.getChildren("child2.2"), is(new Object[] { "child2.2.1", "child2.2.2" }));
		assertThat(fixture.getChildren("4"), is(new Object[] { "i", "ii" }));
		assertThat(fixture.getChildren("bogus"), is(new Object[] {}));
	}

	@Test
	public void getParent() {
		assertThat(fixture.getParent("root2"), nullValue()); // It's a root
		assertThat(fixture.getParent("B"), nullValue()); // It's a root
		assertThat(fixture.getParent("child2.2"), is("root2"));
		assertThat(fixture.getParent("ii"), is("4"));
		assertThat(fixture.getParent("bogus"), nullValue()); // It's not an element
	}

	@Test
	public void hasChildren() {
		assertThat(fixture.hasChildren("root2"), is(true));
		assertThat(fixture.hasChildren("C"), is(true));
		assertThat(fixture.hasChildren("child2.2"), is(true));
		assertThat(fixture.hasChildren("4"), is(true));
		assertThat(fixture.hasChildren("child2.2.3"), is(false));
		assertThat(fixture.hasChildren("i"), is(false));
		assertThat(fixture.hasChildren("bogus"), is(false)); // It's not an element
	}

	@Test
	public void getAdaptedValue() {
		assertThat(aFixture.getAdaptedValue("B"), notNullValue());
		assertThat(aFixture.getAdaptedValue("B"), sameInstance(aFixture.getAdaptedValue("B")));
		assertThat(aFixture.getAdaptedValue("4"), notNullValue());
		assertThat(aFixture.getAdaptedValue("4"), not(aFixture.getAdaptedValue("B")));
		assertThat(aFixture.getAdaptedValue("bogus"), is("bogus")); // Not recognized as an element
	}

	@Test
	public void isValidValue() {
		assertThat(hFixture.isValidValue("C"), is(true));
		assertThat(hFixture.isValidValue("5"), is(true));
		assertThat(hFixture.isValidValue("bogus"), is(false));
	}

	@Test
	public void getElements() {
		assertThat(sFixture.getElements(),
				is(new Object[] { "root1", "root2", "root3", "A", "B", "C" }));
	}

}
