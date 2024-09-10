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

package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ResourceSetFixture;
import org.eclipse.papyrus.uml.tools.providers.DelegatingItemLabelProvider;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link DelegatingItemLabelProvider} class.
 */
@PluginResource("resources/uml/testModel.uml")
public class DelegatingItemLabelProviderTest {

	@Rule
	public final HouseKeeper housekeeper = new HouseKeeper();

	@Rule
	public final ResourceSetFixture rset = new ResourceSetFixture();

	private DelegatingItemLabelProvider fixture;

	private Property property;
	private Class class_;

	public DelegatingItemLabelProviderTest() {
		super();
	}

	/**
	 * Tests that the delegating item label provider forwards notifications
	 * of label changes.
	 */
	@Test
	public void notifyChange() {
		// Have to get the label in order for the PropertyItemProvider to become
		// attached and send notifications
		assumeThat(fixture.getText(property), containsString("Property1"));

		List<Object> updates = new ArrayList<>();

		fixture.addListener(msg -> {
			if (msg instanceof ViewerNotification) {
				ViewerNotification vnot = (ViewerNotification) msg;
				if (vnot.isLabelUpdate()) {
					updates.add(vnot.getElement());
				}
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
		AdapterFactory factory = ((AdapterFactoryEditingDomain) rset.getEditingDomain()).getAdapterFactory();
		fixture = new DelegatingItemLabelProvider(factory, DelegatingItemLabelProvider.SHOW_LABEL);
		housekeeper.cleanUpLater(fixture, IDisposable::dispose);

		Class class1 = (Class) rset.getModel().getOwnedType("Class1");
		property = class1.getOwnedAttributes().get(0);
		class_ = (Class) rset.getModel().getOwnedType("Class2");
	}
}
