/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus and others.
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
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - bug 569174, 570944
 *****************************************************************************/

package org.eclipse.papyrus.dev.assistants.codegen.generator;

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.uml.profile.types.generator.AbstractGenerator;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;

import com.google.inject.Inject;

/**
 * The generator facade for the UML Profile to Modeling Assistant Provider transformation.
 */
public class GMFGenToAssistantsGenerator extends AbstractGenerator<GenEditorGenerator, ModelingAssistantProvider> {

	@Inject
	private org.eclipse.papyrus.uml.profile.assistants.generator.ModelingAssistantProviderRule mainRule;

	public GMFGenToAssistantsGenerator(GenEditorGenerator editor) {
		this(createGeneratorModule(editor));
	}

	public GMFGenToAssistantsGenerator(GeneratorModule module) {
		super(module);
	}

	@Override
	protected ModelingAssistantProvider generate(GenEditorGenerator editor) {
		return ((ModelingAssistantProviderRule) mainRule).toModelingAssistantProvider(editor);
	}

	private static GeneratorModule createGeneratorModule(GenEditorGenerator editor) {
		Identifiers identifiers = new Identifiers();

		// TODO: Prompt the user for this
		identifiers.setBaseElementTypesSet(editor.getPlugin().getID() + ".elementTypeSet");
		return new GeneratorModule(identifiers);
	}
}
