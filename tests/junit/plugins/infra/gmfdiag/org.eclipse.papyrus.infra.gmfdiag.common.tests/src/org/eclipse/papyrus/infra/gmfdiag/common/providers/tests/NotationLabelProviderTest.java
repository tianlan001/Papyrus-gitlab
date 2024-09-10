/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.common.providers.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.NotationLabelProvider;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link NotationLabelProvider} class.
 */
public class NotationLabelProviderTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private ILabelProvider fixture;

	private Package package_;
	private Diagram diagram;

	@Test
	public void getText_namedDiagram() {
		assertThat(fixture.getText(diagram), is("classes"));
	}

	@Test
	public void getText_unnamedDiagram() {
		diagram.setName(null);
		assertThat(fixture.getText(diagram), is("(Class Diagram of <Package> foo)"));
	}

	@Test
	public void getText_unnamedDiagramChangeContextName() {
		diagram.setName(null);
		assumeThat(fixture.getText(diagram), is("(Class Diagram of <Package> foo)"));
		package_.setName("bar");
		assertThat(fixture.getText(diagram), is("(Class Diagram of <Package> bar)"));
	}

	@Test
	public void getText_namedDiagramNotifications() {
		boolean[] gotEvent = { false };
		ILabelProviderListener listener = event -> gotEvent[0] = gotEvent[0] || (event.getElement() == diagram);
		fixture.addListener(listener);

		fixture.getText(diagram); // Access it once to hook up the item adapters
		gotEvent[0] = false;

		diagram.setName("new name");

		assertThat("Label provider did not notify", gotEvent[0], is(true));
	}

	@Test
	public void getText_unnamedDiagramNotifications() {
		diagram.setName(null);

		boolean[] gotEvent = { false };
		ILabelProviderListener listener = event -> gotEvent[0] = gotEvent[0] || (event.getElement() == diagram);
		fixture.addListener(listener);

		fixture.getText(diagram); // Access it once to hook up the item adapters
		gotEvent[0] = false;

		package_.setName("bar");

		assertThat("Label provider did not notify", gotEvent[0], is(true));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = houseKeeper.cleanUpLater(new NotationLabelProvider());

		package_ = UMLFactory.eINSTANCE.createPackage();
		package_.setName("foo");
		diagram = NotationFactory.eINSTANCE.createDiagram();
		diagram.setName("classes");
		diagram.setType("PapyrusUMLClassDiagram");
		diagram.setElement(package_);
	}

}
