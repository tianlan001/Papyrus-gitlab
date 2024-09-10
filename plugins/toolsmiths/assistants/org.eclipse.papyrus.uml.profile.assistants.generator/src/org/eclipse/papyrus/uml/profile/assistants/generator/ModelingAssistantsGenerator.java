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

import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.uml.profile.types.generator.AbstractGenerator;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;
import org.eclipse.uml2.uml.Profile;

import com.google.inject.Inject;

/**
 * The generator facade for the UML Profile to Modeling Assistant Provider transformation.
 */
public class ModelingAssistantsGenerator extends AbstractGenerator<Profile, ModelingAssistantProvider> {

	@Inject
	private ModelingAssistantProviderRule mainRule;

	public ModelingAssistantsGenerator(Identifiers identifiers) {
		this(new GeneratorModule(identifiers));
	}

	public ModelingAssistantsGenerator(GeneratorModule module) {
		super(module);
	}

	@Override
	protected ModelingAssistantProvider generate(Profile profile) {
		return mainRule.toModelingAssistantProvider(profile);
	}
}
