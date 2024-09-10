/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.core.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.junit.Test;

/**
 * Test cases for the {@link PapyrusProjectScope} class.
 * 
 * @author Christian W. Damus
 */
public class PapyrusProjectScopeTest {

	/**
	 * Initializes me.
	 */
	public PapyrusProjectScopeTest() {
		super();
	}

	/**
	 * Verify the {@link PapyrusProjectScope#equals(Object)} method.
	 */
	@Test
	public void equals() {
		PapyrusProjectScope scope1 = scope("p1", "foo");
		PapyrusProjectScope scope2 = scope("p1", "bar");
		PapyrusProjectScope scope3 = scope("p2", "foo");
		PapyrusProjectScope scope4 = scope("p1", "foo");

		assertThat(scope1, equalTo(scope1)); // Reflexive on the same instance
		assertThat(scope1, equalTo(scope4)); // Distinct instance
		assertThat(scope1, not(equalTo(scope2)));
		assertThat(scope1, not(equalTo(scope3)));

		assertThat(scope1, not(equalTo(new ProjectScope(getProject("p1")))));

		assertThat(scope1.equals(null), is(false));
	}

	/**
	 * Verify the {@link PapyrusProjectScope#hashCode()} method.
	 */
	@Test
	public void hashCode_() {
		PapyrusProjectScope scope1 = scope("p1", "foo");
		PapyrusProjectScope scope2 = scope("p1", "foo");

		assertThat(scope1.hashCode(), is(scope2.hashCode()));
		assertThat(scope1.hashCode(), not(0)); // All of the parts have non-zero hashcodes
	}

	/**
	 * Verify the {@link PapyrusProjectScope#getName()} method.
	 */
	@Test
	public void getName() {
		PapyrusProjectScope scope1 = scope("p1", "foo");

		assertThat(scope1.getName(), is(PapyrusProjectScope.SCOPE));
	}

	/**
	 * Verify the {@link PapyrusProjectScope#getLocation()} method.
	 */
	@Test
	public void getLocation() {
		PapyrusProjectScope scope1 = scope("p1", "foo");

		assertThat(scope1.getLocation(), nullValue());
	}

	/**
	 * Verify the {@link PapyrusProjectScope#getNode(String)} method.
	 */
	@Test
	public void getNode() {
		PapyrusProjectScope scope1 = scope("p1", "foo");
		IEclipsePreferences node = scope1.getNode("test");

		assertThat(node, notNullValue());
		assertThat(node.absolutePath(), is("/papyrusProject/p1/foo/test"));
	}

	//
	// Test framework
	//

	IProject getProject(String name) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(name);
	}

	PapyrusProjectScope scope(String projectName, String papyrusName) {
		return new PapyrusProjectScope(getProject(projectName), papyrusName);
	}
}
