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

import java.util.Arrays;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for the {@link ShardResourceLocator} class. These tests are run both
 * with the full cross-reference index and with the partial on-demand index that
 * only looks for shard parent relationships.
 */
@RunWith(Parameterized.class)
public class ShardResourceLocatorTest extends AbstractCrossReferenceIndexTest {

	/**
	 * Initializes me.
	 * 
	 * @param onDemand
	 *            whether to force the on-demand cross-reference index
	 * @param description
	 *            the description of the test conditions
	 */
	public ShardResourceLocatorTest(boolean onDemand, String description) {
		super(onDemand);
	}

	@Test
	@ProjectResource("package1/packageA/foo.uml")
	public void shardLoadsContainersAndDoesntLoseStereotypes() {
		Class foo = (Class) initialResources.get(0).getContents().get(0);

		// The parent chain of Foo is loaded
		requireLoaded("root.uml");
		requireLoaded("package1.uml");
		requireLoaded("package1/packageA.uml");

		// Stereotype applications were not lost
		requireEClass(foo);
		requireEPackage(foo.getNearestPackage());
		requireEPackage(foo.getNearestPackage().getNestingPackage());
	}

	@Test
	@ProjectResource("referencing.uml")
	public void proxyResolutionLoadsShardFromTheTop() {
		Package referencing = (Package) initialResources.get(0).getContents().get(0);
		Class subclass = (Class) referencing.getNestedPackage("package1")
				.getNestedPackage("packageA").getOwnedType("Subclass");

		// Get its general
		Class superclass = (Class) subclass.getGeneral("Bar");

		// The parent chain of Bar is loaded
		requireLoaded("root.uml");
		requireLoaded("package2.uml");
		requireLoaded("package2/packageB.uml");

		// Stereotype applications were not lost
		requireEClass(superclass);
		requireEPackage(superclass.getNearestPackage());
		requireEPackage(superclass.getNearestPackage().getNestingPackage());
	}

	//
	// Test framework
	//

	@Parameters(name = "{index}: {1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ false, "full index" },
				{ true, "on-demand index" },
		});
	}

	@BeforeClass
	public static void createProjectContents() {
		createProjectContents("resources/shards");
	}
}
