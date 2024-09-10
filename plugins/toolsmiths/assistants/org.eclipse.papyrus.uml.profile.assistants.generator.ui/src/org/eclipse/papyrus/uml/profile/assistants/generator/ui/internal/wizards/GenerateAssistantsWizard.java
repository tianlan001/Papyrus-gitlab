/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.profile.assistants.generator.ui.internal.wizards;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.papyrus.infra.gmfdiag.assistant.util.AssistantResource;
import org.eclipse.papyrus.uml.profile.assistants.generator.ModelingAssistantsGenerator;
import org.eclipse.papyrus.uml.profile.assistants.generator.ui.internal.Activator;
import org.eclipse.papyrus.uml.profile.types.generator.AbstractGenerator;
import org.eclipse.papyrus.uml.profile.types.generator.ElementTypesGenerator;
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers;
import org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards.GeneratorMainPage;
import org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards.GeneratorWizard;
import org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards.GeneratorWizardModel;
import org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards.IGeneratorWizardPage;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.uml2.uml.Profile;

/**
 * A wizard for generation of a new Modeling Assistants model for a UML Profile.
 */
public class GenerateAssistantsWizard extends GeneratorWizard {

	public GenerateAssistantsWizard(IWorkbenchPage page, Profile profile) {
		super(page, profile);

		setDialogSettings(DialogSettings.getOrCreateSection(Activator.getInstance().getDialogSettings(), GenerateAssistantsWizard.class.getName()));

		setWindowTitle("Generate Diagram Assistants"); //$NON-NLS-1$
	}

	@Override
	protected IGeneratorWizardPage createMainPage(GeneratorWizardModel model) {
		return new GeneratorMainPage(model, "Diagram Assistant Model", "Enter details of the diagram assistant model to generate.", AssistantResource.FILE_EXTENSION); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected void addGenerators(List<? super AbstractGenerator<Profile, ?>> generators, Identifiers identifiers, GeneratorWizardModel wizardModel) {
		super.addGenerators(generators, identifiers, wizardModel);

		generators.add(new ModelingAssistantsGenerator(identifiers));
	}

	@Override
	protected URI getOutputURI(AbstractGenerator<Profile, ?> generator, Identifiers identifiers, GeneratorWizardModel wizardModel) {
		if (generator instanceof ElementTypesGenerator) {
			return wizardModel.getOutputModelURI().trimFileExtension().appendFileExtension("typesconfigurations"); //$NON-NLS-1$
		}

		return super.getOutputURI(generator, identifiers, wizardModel);
	}
}
