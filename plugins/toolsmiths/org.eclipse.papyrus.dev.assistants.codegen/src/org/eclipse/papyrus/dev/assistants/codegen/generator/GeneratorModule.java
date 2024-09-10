/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.dev.assistants.codegen.generator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.uml.profile.assistants.generator.ModelingAssistantProviderRule;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;
import org.eclipse.papyrus.uml.profile.types.generator.InputModel;

/**
 * The Guice injector module for the Element Types to Modeling Assistants transformation.
 */
public class GeneratorModule extends org.eclipse.papyrus.uml.profile.assistants.generator.GeneratorModule {
	public GeneratorModule() {
		this(new Identifiers());
	}

	public GeneratorModule(Identifiers identifiers) {
		super(identifiers);
	}

	@Override
	protected void configure() {
		super.configure();

		bindElementTypeToAssistantRule();
	}

	protected void bindElementTypeToAssistantRule() {
		// Pass
	}

	@Override
	protected void bindInputType() {
		bind(EClass.class).annotatedWith(InputModel.class).toInstance(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);
	}

	@Override
	protected void bindModelingAssistantProviderRule() {
		bind(ModelingAssistantProviderRule.class).to(org.eclipse.papyrus.dev.assistants.codegen.generator.ModelingAssistantProviderRule.class);
	}
}
