/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.profile.assistants.generator.tests;

import java.util.Arrays;

import org.eclipse.papyrus.junit.framework.classification.ClassificationRunnerWithParametersFactory;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

/**
 * Specific regression test cases verifying that connection assistants are inferred correctly for various types of
 * UML model elements.
 */
@RunWith(Parameterized.class)
@UseParametersRunnerFactory(ClassificationRunnerWithParametersFactory.class)
@PluginResource("/resources/edges.profile.uml")
public class ConnectionTypesTest {

	@ClassRule
	public static final ModelGenFixture fixture = new ModelGenFixture();

	private final String stereotypeName;
	private final String metaclassName;

	public ConnectionTypesTest(String metaclassName, String stereotypeName) {
		super();

		this.metaclassName = metaclassName;
		this.stereotypeName = stereotypeName;
	}

	@Parameters(name = "<<{1}>> {0}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "Generalization", "Indirect" }, // As representative of Relationships
				{ "GeneralizationSet", "Indirect" }, // As representative of Relationships
				{ "ControlFlow", "Virtual" },
				{ "ObjectFlow", "Virtual" },
				{ "Transition", "Virtual" },
				{ "Message", "Virtual" },
				{ "Connector", "Indirect" },
		});
	}

	@Test
	public void connectionsGenerated() {
		Pair<Stereotype, Class> extension = fixture.getMetaclassExtension(stereotypeName, metaclassName);
		fixture.assertAllConnectionAssistants(extension);
	}

}
