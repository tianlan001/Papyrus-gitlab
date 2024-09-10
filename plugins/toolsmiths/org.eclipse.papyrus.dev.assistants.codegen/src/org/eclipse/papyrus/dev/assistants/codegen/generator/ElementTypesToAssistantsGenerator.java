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

import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.uml.profile.types.generator.AbstractGenerator;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;

import com.google.inject.Inject;

/**
 * The generator facade for the UML Profile to Modeling Assistant Provider transformation.
 */
public class ElementTypesToAssistantsGenerator extends AbstractGenerator<ElementTypeSetConfiguration, ModelingAssistantProvider> {

	@Inject
	private org.eclipse.papyrus.uml.profile.assistants.generator.ModelingAssistantProviderRule mainRule;

	public ElementTypesToAssistantsGenerator(ElementTypeSetConfiguration elementTypeSet) {
		this(createGeneratorModule(elementTypeSet));
	}

	public ElementTypesToAssistantsGenerator(GeneratorModule module) {
		super(module);
	}

	@Override
	protected ModelingAssistantProvider generate(ElementTypeSetConfiguration elementTypeSet) {
		return ((ModelingAssistantProviderRule) mainRule).toModelingAssistantProvider(elementTypeSet);
	}

	private static GeneratorModule createGeneratorModule(ElementTypeSetConfiguration elementTypeSet) {
		Identifiers identifiers = new Identifiers();
		identifiers.setBaseElementTypesSetConfiguration(elementTypeSet);
		return new GeneratorModule(identifiers);
	}
}
