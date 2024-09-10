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

package org.eclipse.papyrus.infra.ui.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Automated tests for the {@link TransactionUIHelper} class.
 */
public class TransactionUIHelperTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private TransactionalEditingDomain domain;
	private EClass eclass;

	/**
	 * Initializes me.
	 */
	public TransactionUIHelperTest() {
		super();
	}

	@Test
	public void testPrivilegedRunnableWithProgress() {
		IRunnableWithProgress runnable = monitor -> {
			monitor.beginTask("Test privileged runnable", 2);

			eclass.setAbstract(true);
			monitor.worked(1);
			eclass.setName("Foo");
			monitor.worked(1);

			monitor.done();
		};

		FailureAssertion failure = new FailureAssertion();
		domain.getCommandStack().execute(new RecordingCommand(domain, "Test") {

			@Override
			protected void doExecute() {
				IRunnableWithProgress privileged = TransactionUIHelper.createPrivilegedRunnableWithProgress(domain, runnable);

				try {
					new ProgressMonitorDialog(null).run(true, true, privileged);
				} catch (Exception e) {
					failure.accept(e);
				}
			}
		});

		failure.verify();

		assertThat(eclass.isAbstract(), is(true));
		assertThat(eclass.getName(), is("Foo"));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() throws Exception {
		domain = houseKeeper.createSimpleEditingDomain();
		Resource res = domain.createResource("file:bogus.ecore");

		TransactionHelper.run(domain, () -> {
			EPackage epackage = EcoreFactory.eINSTANCE.createEPackage();
			res.getContents().add(epackage);
			eclass = EcoreFactory.eINSTANCE.createEClass();
			epackage.getEClassifiers().add(eclass);
		});
	}

	//
	// Nested types
	//

	private static final class FailureAssertion implements Consumer<Throwable> {
		private Throwable thrown;

		@Override
		public void accept(Throwable t) {
			// Take only the first one
			if (thrown == null) {
				thrown = t;
			}
		}

		void verify() {
			if (thrown != null) {
				thrown.printStackTrace();
				fail("Synchronization on future result failed: " + thrown.getMessage());
			}
		}
	}
}
