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

package org.eclipse.papyrus.infra.ui.emf.providers.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link EMFLabelProvider} class.
 */
public class EMFLabelProviderTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private ILabelProvider fixture;

	private Package package_;

	@Test
	public void getText() {
		assertThat(fixture.getText(package_), is("<Package> foo"));
	}

	@Test
	public void labelProviderChangeNotifications() {
		boolean[] gotEvent = { false };
		ILabelProviderListener listener = event -> gotEvent[0] = gotEvent[0] || (event.getElement() == package_);
		fixture.addListener(listener);

		fixture.getText(package_); // Access it once to hook up the item adapters
		gotEvent[0] = false;

		package_.setName("bar");

		assertThat("Label provider did not notify", gotEvent[0], is(true));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = houseKeeper.cleanUpLater(new EMFLabelProvider());

		package_ = UMLFactory.eINSTANCE.createPackage();
		package_.setName("foo");
	}

}
