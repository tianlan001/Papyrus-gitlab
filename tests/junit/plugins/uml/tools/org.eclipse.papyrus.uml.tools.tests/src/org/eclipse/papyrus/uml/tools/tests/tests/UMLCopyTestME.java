/*****************************************************************************
 * Copyright (c) 2014, 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ansgar Radermacher (CEA LIST) - bug 541686 (duplicated replationships)
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.views.modelexplorer.handler.CopyHandler;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Package;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the copy command from the model explorer (hence ME in test name).
 * The CopyHandler from the modelexplorer view is used. Thus, the test corresponds
 * to the behavior experienced by the user, it complements class UMLCopyTest that
 * tests the underlying copier.
 */
@PluginResource("resources/uml/copy.di")
public class UMLCopyTestME extends AbstractPapyrusTest {


	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	/**
	 * Name of model elements
	 */
	private static final String PKG = "pkg"; //$NON-NLS-1$
	private static final String BAR = "Bar"; //$NON-NLS-1$
	private static final String FOO = "Foo"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 */
	public UMLCopyTestME() {
		super();
	}

	@Test
	public void copyingInterfaceRealizationDoesNotCorruptTheModel() {
		Package pkg = editor.getModel().getNestedPackage(PKG);

		Command cmd = CopyHandler.buildCopyCommand(editor.getEditingDomain(), Arrays.asList(pkg));
		cmd.execute();

		PapyrusClipboard<Object> papyrusClipboard = PapyrusClipboard.getInstance();
		Package pkgCopy = (Package) papyrusClipboard.getCopyFromSource(pkg);

		Interface fooCopy = (Interface) pkgCopy.getOwnedType(FOO);
		Class barCopy = (Class) pkgCopy.getOwnedType(BAR);
		InterfaceRealization rlzCopy = barCopy.getInterfaceRealization(null, fooCopy);

		// Verify the copy
		assertNotNull(rlzCopy);
		assertThat(rlzCopy.getImplementingClassifier(), both(notNullValue()).and(is(barCopy)));
		assertThat(rlzCopy.getContract(), both(notNullValue()).and(is(fooCopy)));

		// Verify the non-corruption
		assertThat(rlzCopy.getClients().size(), is(1));
	}
}
