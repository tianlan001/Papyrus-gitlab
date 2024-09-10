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

package org.eclipse.papyrus.infra.gmfdiag.common.commands.tests;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.eclipse.papyrus.infra.gmfdiag.common.commands.DefaultCopyCommand;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link DefaultCopyCommand} class.
 */
@PluginResource("models/ExpansionModelTest.di")
public class DefaultCopyCommandTest {

	private final ModelSetFixture model = new ServiceRegistryModelSetFixture();

	@Rule
	public final CustomUMLFactoryFixture fixture = new CustomUMLFactoryFixture(model);

	@Rule
	public final PapyrusClipboardFixture clipboard = new PapyrusClipboardFixture();

	@Test
	public void copyElement() {
		org.eclipse.uml2.uml.Class myClass = (org.eclipse.uml2.uml.Class) model.getModel().getOwnedType("MyClass");

		fixture.reset();

		new DefaultCopyCommand(model.getEditingDomain(), clipboard, singletonList(myClass)).dispose();

		// The copied class has its name set via a structural feature setting
		fixture.assertInvocation("eSetting");

		assertThat(clipboard.getCopyFromSource(myClass), instanceOf(org.eclipse.uml2.uml.Class.class));
		org.eclipse.uml2.uml.Class copy = (org.eclipse.uml2.uml.Class) clipboard.getCopyFromSource(myClass);
		assertThat(copy.getName(), containsString(myClass.getName()));
	}

}
