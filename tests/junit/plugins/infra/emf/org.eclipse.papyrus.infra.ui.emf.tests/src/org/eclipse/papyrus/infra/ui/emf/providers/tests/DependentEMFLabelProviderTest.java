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
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.ui.emf.providers.DependentEMFLabelProvider;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.MapMaker;

/**
 * Tests for the {@link DependentEMFLabelProvider} class.
 */
public class DependentEMFLabelProviderTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private ILabelProvider fixture;

	private Package imported;
	private Package importing;

	@Test
	public void getText() {
		assertThat(fixture.getText(imported), is("<Package> foo (imported by <Package> bar)"));
	}

	@Test
	public void notificationsOnDependentWhenItChanges() {
		boolean[] gotEvent = { false };
		ILabelProviderListener listener = event -> gotEvent[0] = gotEvent[0] || (event.getElement() == imported);
		fixture.addListener(listener);

		fixture.getText(imported); // Access it once to hook up the item adapters
		gotEvent[0] = false;

		// Change the name of the package, itself
		imported.setName("quux");

		assertThat("Label provider did not notify", gotEvent[0], is(true));
	}

	@Test
	public void notificationsOnDependentWhenDependencyChanges() {
		boolean[] gotEvent = { false };
		ILabelProviderListener listener = event -> gotEvent[0] = gotEvent[0] || (event.getElement() == imported);
		fixture.addListener(listener);

		fixture.getText(imported); // Access it once to hook up the item adapters
		gotEvent[0] = false;

		// Change the name of the package that the label of our dependent package depends on
		importing.setName("quux");

		assertThat("Label provider did not notify", gotEvent[0], is(true));
	}

	/**
	 * Verify that we do not get any exception (e.g., concurrent modification) when
	 * a subscription is removed while the label provider is notifying.
	 */
	@Test
	public void safeSubscriptionRemovalOnDestroy_bug507241() {
		// This listener indirectly triggers the unsubscription
		ILabelProviderListener listener = __ -> fixture.getText(imported);
		fixture.addListener(listener);

		fixture.getText(imported); // Access it once to hook up the item adapters

		// Add another listener that will be notified after the subscription listener
		fixture.addListener(this::pass);

		try {
			// Delete the import
			importing.getPackageImport(imported).destroy();
			importing.unsetName();
		} catch (Exception e) {
			e.printStackTrace();
			fail(String.format("Got %s during edit: %s", e.getClass().getSimpleName(), e.getMessage()));
		}
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = houseKeeper.cleanUpLater(new ImportedPackageLabelProvider());

		imported = UMLFactory.eINSTANCE.createPackage();
		imported.setName("foo");

		importing = UMLFactory.eINSTANCE.createPackage();
		importing.setName("bar");

		importing.createPackageImport(imported);
	}

	void pass(Object __) {
		// Pass
	}

	// For testing purposes, a label provider for packages that decorates the label
	// with the label of one package that imports it (if any)
	private class ImportedPackageLabelProvider extends DependentEMFLabelProvider {
		private final Map<Package, Package> lastKnownImporter = new MapMaker().weakKeys().weakValues().makeMap();

		@Override
		protected String getText(EObject element) {
			// Default
			String result = super.getText(element);

			if (element instanceof Package) {
				Package package_ = (Package) element;
				Package importer = lastKnownImporter.computeIfAbsent(package_, this::getImportingPackage);

				if ((importer != null) && importer.getImportedPackages().contains(package_)) {
					// It is currently importing this package.
					// Listen for changes in the dependency to notify on the dependent
					subscribe(importer, package_);

					result = String.format("%s (imported by %s)", result, getText(importer));
				} else {
					// In case we were subscribed
					unsubscribe(importer, package_);
				}
			}

			return result;
		}

		private Package getImportingPackage(Package package_) {
			Package result = null;

			// Cheat!
			if (importing.getPackageImport(package_) != null) {
				result = importing;
			}

			return result;
		}
	}
}
