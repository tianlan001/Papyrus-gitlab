/*****************************************************************************
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.profile.assistants.generator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;
import org.eclipse.papyrus.uml.profile.types.generator.OutputModel;

/**
 * The base Guice injector module for the UML Profile to Modeling Assistants transformation.
 */
public class GeneratorModule extends org.eclipse.papyrus.uml.profile.types.generator.GeneratorModule {
	public GeneratorModule(Identifiers identifiers) {
		super(identifiers);
	}

	@Override
	protected void configure() {
		super.configure();

		bindModelingAssistantProviderRule();
		bindPopupAssistantRule();
		bindConnectionAssistantRule();
	}

	@Override
	protected void bindOutputType() {
		bind(EClass.class).annotatedWith(OutputModel.class).toInstance(AssistantPackage.Literals.MODELING_ASSISTANT_PROVIDER);
	}

	protected void bindModelingAssistantProviderRule() {
		// pass
	}

	protected void bindPopupAssistantRule() {
		// pass
	}

	protected void bindConnectionAssistantRule() {
		// pass
	}
}
