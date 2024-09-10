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

package org.eclipse.papyrus.infra.nattable.tests.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Collections;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainPreferences;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableVersioningUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattableFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.provider.TableLabelProvider;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.nattable.representation.RepresentationFactory;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link TableLabelProvider} class.
 */
public class TableLabelProviderTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private ILabelProvider fixture;

	private Package package_;
	private PapyrusTable proto;
	private Table table;
	private ArchitectureDomainPreferences preferences;

	@Test
	public void getText_namedTable() {
		assertThat(fixture.getText(table), is("classes"));
	}

	@Test
	public void getText_unnamedTable() {
		table.setName(null);
		assertThat(fixture.getText(table), is("(Test Table of <Package> foo)"));
	}

	@Test
	public void getText_unnamedTableChangeContextName() {
		table.setName(null);
		assumeThat(fixture.getText(table), is("(Test Table of <Package> foo)"));
		package_.setName("bar");
		assertThat(fixture.getText(table), is("(Test Table of <Package> bar)"));
	}

	@Test
	public void getText_namedTableNotifications() {
		boolean[] gotEvent = { false };
		ILabelProviderListener listener = event -> gotEvent[0] = gotEvent[0] || (event.getElement() == table);
		fixture.addListener(listener);

		fixture.getText(table); // Access it once to hook up the item adapters
		gotEvent[0] = false;

		table.setName("new name");

		assertThat("Label provider did not notify", gotEvent[0], is(true));
	}

	@Test
	public void getText_unnamedTableNotifications() {
		table.setName(null);

		boolean[] gotEvent = { false };
		ILabelProviderListener listener = event -> gotEvent[0] = gotEvent[0] || (event.getElement() == table);
		fixture.addListener(listener);

		fixture.getText(table); // Access it once to hook up the item adapters
		gotEvent[0] = false;

		package_.setName("bar");

		assertThat("Label provider did not notify", gotEvent[0], is(true));
	}

	@Test
	public void getText_selectionOfMultipleTables() {
		StructuredSelection selection = new StructuredSelection(new Object[] { table, table });

		assertThat(fixture.getText(selection), is("classes, classes"));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = houseKeeper.cleanUpLater(new TableLabelProvider());

		ArchitectureDomain domain = ArchitectureFactory.eINSTANCE.createArchitectureDomain();
		domain.setName("Testing");

		ArchitectureDescriptionLanguage language = ArchitectureFactory.eINSTANCE.createArchitectureDescriptionLanguage();
		language.setId("Testing.TestTable");
		domain.getContexts().add(language);

		proto = RepresentationFactory.eINSTANCE.createPapyrusTable();
		proto.setName("Test Table");
		proto.setId("org.eclipse.papyrus.infra.nattable.tests.table.test");
		proto.setImplementationID("org.eclipse.papyrus.infra.nattable.tests.TestTable");
		// proto.setConfiguration("TestTable");
		language.getRepresentationKinds().add(proto);

		ArchitectureViewpoint viewpoint = ArchitectureFactory.eINSTANCE.createArchitectureViewpoint();
		viewpoint.setId("tesing.TestTable.Testing");
		viewpoint.getRepresentationKinds().add(proto);
		language.getViewpoints().add(viewpoint);

		ArchitectureDomainManager.getInstance().getMerger().setDynamicDomains(Collections.singleton(domain));
		preferences = new ArchitectureDomainPreferences();
		preferences.setDefaultContextId("Testing.TestTable");
		preferences.write();

		package_ = UMLFactory.eINSTANCE.createPackage();
		package_.setName("foo");

		table = NattableFactory.eINSTANCE.createTable();
		TableVersioningUtils.stampCurrentVersion(table);
		table.setName("classes");
		table.setTableKindId(proto.getId());
		table.setContext(package_);
	}

	@After
	public void teardown() {
		ArchitectureDomainManager.getInstance().getMerger().setDynamicDomains(Collections.emptyList());
		preferences.setDefaultContextId(TypeContext.getDefaultContextId());
		preferences.write();
	}
}
