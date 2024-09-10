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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.DefaultCopyCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.DefaultPasteCommand;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link DefaultPasteCommand} class.
 */
@PluginResource("models/ExpansionModelTest.di")
public class DefaultPasteCommandTest {

	private final ModelSetFixture model = new ServiceRegistryModelSetFixture();

	@Rule
	public final CustomUMLFactoryFixture fixture = new CustomUMLFactoryFixture(model);

	@Rule
	public final PapyrusClipboardFixture clipboard = new PapyrusClipboardFixture();

	@Test
	public void pasteElement() {
		org.eclipse.uml2.uml.Class myClass = (org.eclipse.uml2.uml.Class) model.getModel().getOwnedType("MyClass");

		Command copy = new DefaultCopyCommand(model.getEditingDomain(), clipboard, singletonList(myClass));

		fixture.execute(copy);

		Command paste = new DefaultPasteCommand(model.getEditingDomain(), model.getModel(), clipboard);

		// The copied class has its name set via a structural feature setting
		fixture.assertInvocation("eSetting");

		fixture.execute(paste);

		// Find the copy
		List<String> typeNamesLikeMyClass = model.getModel().getOwnedTypes().stream()
				.map(NamedElement::getName)
				.filter(containsString(myClass.getName())::matches)
				.collect(Collectors.toList());
		assertThat(typeNamesLikeMyClass.size(), is(2));
	}

}
