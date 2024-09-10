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

package org.eclipse.papyrus.infra.properties.ui.providers.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.papyrus.infra.properties.ui.providers.PropertiesHeaderLabelProvider;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ResourceSetFixture;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link PropertiesHeaderLabelProvider} class.
 */
@PluginResource("resources/model.uml")
public class PropertiesHeaderLabelProviderTest {

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final ResourceSetFixture rset = new ResourceSetFixture();

	private PropertiesHeaderLabelProvider fixture;

	private Property property;
	private Class class_;

	public PropertiesHeaderLabelProviderTest() {
		super();
	}


	/**
	 * Tests that the properties header label provider forwards label provider
	 * change events.
	 */
	@Test
	public void labelProviderChange() {
		// Have to get the label in order for the PropertyItemProvider to become
		// attached and send notifications
		assumeThat(fixture.getText(property), containsString("property1"));

		List<Object> updates = new ArrayList<>();

		fixture.addListener(event -> {
			if (event.getSource() == fixture) {
				updates.addAll(Arrays.asList(event.getElements()));
			}
		});

		property.setType(class_);

		assertThat("No notification received", updates, hasItem(property));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = new PropertiesHeaderLabelProvider();
		housekeeper.cleanUpLater(fixture, IBaseLabelProvider::dispose);

		Class class1 = (Class) rset.getModel().getOwnedType("Class1");
		property = class1.getOwnedAttributes().get(0);
		class_ = (Class) rset.getModel().getOwnedType("Class2");
	}

}
