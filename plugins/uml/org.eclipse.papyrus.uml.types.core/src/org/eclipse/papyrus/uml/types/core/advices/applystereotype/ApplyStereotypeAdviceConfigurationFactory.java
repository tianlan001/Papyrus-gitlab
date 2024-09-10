/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Add createAdviceBindingConfiguration method.
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.advices.applystereotype;

import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceBindingFactory;
import org.eclipse.papyrus.infra.types.core.impl.NullEditHelperAdvice;


public class ApplyStereotypeAdviceConfigurationFactory extends AbstractAdviceBindingFactory<AbstractAdviceBindingConfiguration> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IEditHelperAdvice getEditHelperAdvice(AbstractAdviceBindingConfiguration adviceConfiguration) {
		if (adviceConfiguration instanceof ApplyStereotypeAdviceConfiguration) {
			IEditHelperAdvice editHelperAdvice = new ApplyStereotypeAdviceEditHelperAdvice((ApplyStereotypeAdviceConfiguration) adviceConfiguration);
			return editHelperAdvice;
		}
		return NullEditHelperAdvice.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractAdviceBindingConfiguration createAdviceBindingConfiguration() {
		return ApplyStereotypeAdviceFactory.eINSTANCE.createApplyStereotypeAdviceConfiguration();
	}

	/**
	 * Create a {@link ApplyStereotypeAdviceConfiguration}.
	 * 
	 * @param stereotypesToApply
	 *            The list of stereotypes to apply.
	 * @param identifier
	 *            Its identifier.
	 * @param target
	 *            Its target.
	 * @return the {@link ApplyStereotypeAdviceConfiguration}.
	 */
	public static ApplyStereotypeAdviceConfiguration createApplyStereotypeAdvice(final List<String> stereotypesToApply, final String identifier, final SpecializationTypeConfiguration target) {

		ApplyStereotypeAdviceConfiguration applyStereotypeAdvice = ApplyStereotypeAdviceFactory.eINSTANCE.createApplyStereotypeAdviceConfiguration();

		// Configure Stereotype Advice
		applyStereotypeAdvice.setIdentifier(identifier);
		applyStereotypeAdvice.setTarget(target);

		// Create Stereotype to apply//
		for (String stereotype : stereotypesToApply) {
			StereotypeToApply stereotypeToApply = ApplyStereotypeAdviceFactory.eINSTANCE.createStereotypeToApply();

			// Configure stereotype to apply
			String requiredProfile = stereotype.substring(0, stereotype.indexOf("::"));//$NON-NLS-1$
			stereotypeToApply.getRequiredProfiles().add(requiredProfile);
			stereotypeToApply.setStereotypeQualifiedName(stereotype);
			stereotypeToApply.setUpdateName(true);

			// Add stereotyped to apply to
			applyStereotypeAdvice.getStereotypesToApply().add(stereotypeToApply);
		}

		return applyStereotypeAdvice;
	}


}
