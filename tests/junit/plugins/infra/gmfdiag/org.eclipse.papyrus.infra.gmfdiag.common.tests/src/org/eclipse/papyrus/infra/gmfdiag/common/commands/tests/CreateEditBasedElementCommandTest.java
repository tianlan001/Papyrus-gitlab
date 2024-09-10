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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CreateEditBasedElementCommand;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link CreateEditBasedElementCommand} class.
 */
@PluginResource("models/ExpansionModelTest.di")
public class CreateEditBasedElementCommandTest {

	private final ModelSetFixture model = new ServiceRegistryModelSetFixture();

	@Rule
	public final CustomUMLFactoryFixture fixture = new CustomUMLFactoryFixture(model);

	@Test
	public void createElement() {
		CreateElementRequest request = new CreateElementRequest(model.getModel(), UMLElementTypes.CLASS);
		CreateEditBasedElementCommand command = new CreateEditBasedElementCommand(request);
		assertThat(command.canExecute(), is(true));

		model.execute(command);

		// A new class is always given a default name by edit advice
		fixture.assertInvocation("setName");
	}

}
