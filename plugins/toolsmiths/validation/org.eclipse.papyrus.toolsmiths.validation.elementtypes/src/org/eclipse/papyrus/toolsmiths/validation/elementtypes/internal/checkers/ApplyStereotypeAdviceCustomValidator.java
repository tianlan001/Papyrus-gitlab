/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.LocalProfileIndex;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.messages.Messages;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Custom validation rules for "apply stereotype" advice.
 */
public class ApplyStereotypeAdviceCustomValidator extends CustomModelChecker.SwitchValidator {

	public ApplyStereotypeAdviceCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(StereotypeToApply stereotypeToApply, DiagnosticChain diagnostics, Map<Object, Object> context) {
		for (String profileQualifiedName : stereotypeToApply.getRequiredProfiles()) {
			validateRequiredProfile(stereotypeToApply, profileQualifiedName, diagnostics, context);
		}
		if (stereotypeToApply.getStereotypeQualifiedName() != null) {
			validateStereotypeName(stereotypeToApply, stereotypeToApply.getStereotypeQualifiedName(), diagnostics, context);
		}
	}

	private void validateRequiredProfile(StereotypeToApply stereotypeToApply, String profileQualifiedName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		LocalProfileIndex index = LocalProfileIndex.getInstance(stereotypeToApply, context);
		Profile profile = index.getProfile(profileQualifiedName, stereotypeToApply);
		if (profile == null) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, stereotypeToApply, format(Messages.ApplyStereotypeAdviceCustomValidator_0, context, stereotypeToApply, profileQualifiedName)));
		}
	}

	private void validateStereotypeName(StereotypeToApply stereotypeToApply, String qualifiedName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Stereotype stereotype = getStereotype(stereotypeToApply, context);
		if (stereotype == null) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, stereotypeToApply, format(Messages.ApplyStereotypeAdviceCustomValidator_1, context, stereotypeToApply)));
		}
	}

	private Stereotype getStereotype(StereotypeToApply stereotypeToApply, Map<Object, Object> context) {
		LocalProfileIndex index = LocalProfileIndex.getInstance(stereotypeToApply, context);
		return index.getStereotype(stereotypeToApply.getStereotypeQualifiedName(), stereotypeToApply.getRequiredProfiles(), stereotypeToApply);
	}

	public void validate(FeatureToSet featureToSet, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!(featureToSet.eContainer() instanceof StereotypeToApply)) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, featureToSet, format(Messages.ApplyStereotypeAdviceCustomValidator_2, context, featureToSet)));
			return;
		}

		Stereotype stereotype = getStereotype((StereotypeToApply) featureToSet.eContainer(), context);
		if (stereotype != null) {
			String featureName = featureToSet.getFeatureName();
			if (stereotype.getFeature(featureName) == null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, featureToSet, format(Messages.ApplyStereotypeAdviceCustomValidator_3, context, featureName, featureToSet)));
			}
		}
	}

}
