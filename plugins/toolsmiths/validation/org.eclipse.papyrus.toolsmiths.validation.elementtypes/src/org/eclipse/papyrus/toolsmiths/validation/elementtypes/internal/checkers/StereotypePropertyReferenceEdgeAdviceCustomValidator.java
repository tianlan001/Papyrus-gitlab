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
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Custom validation rules for stereotype property reference edge advice.
 */
public class StereotypePropertyReferenceEdgeAdviceCustomValidator extends CustomModelChecker.SwitchValidator {

	public StereotypePropertyReferenceEdgeAdviceCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(StereotypePropertyReferenceEdgeAdviceConfiguration stereotypeReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		String stereotypeName = stereotypeReference.getStereotypeQualifiedName();
		if (stereotypeName != null && !stereotypeName.isBlank()) {
			String featureName = stereotypeReference.getFeatureToSet();
			if (validateStereotypeName(stereotypeReference, stereotypeName, diagnostics, context) && featureName != null && !featureName.isBlank()) {
				Stereotype stereotype = getStereotype(stereotypeReference, stereotypeName, context);
				validateFeatureToSet(stereotypeReference, stereotype, featureName, diagnostics, context);
			}
		}
	}

	private boolean validateStereotypeName(StereotypePropertyReferenceEdgeAdviceConfiguration stereotypeReference, String qualifiedName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Stereotype stereotype = getStereotype(stereotypeReference, qualifiedName, context);
		boolean result = stereotype != null;

		if (!result) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, stereotypeReference, format(Messages.StereotypePropertyReferenceEdgeAdviceCustomValidator_0, context, stereotypeReference)));
		}

		return result;
	}

	private Stereotype getStereotype(StereotypePropertyReferenceEdgeAdviceConfiguration stereotypeReference, String qualifiedName, Map<Object, Object> context) {
		LocalProfileIndex index = LocalProfileIndex.getInstance(stereotypeReference, context);
		return index.getStereotype(qualifiedName, stereotypeReference);
	}

	private void validateFeatureToSet(StereotypePropertyReferenceEdgeAdviceConfiguration stereotypeReference, Stereotype stereotype, String featureName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Property property = stereotype.getAttribute(featureName, null);
		if (property == null) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, stereotypeReference, format(Messages.StereotypePropertyReferenceEdgeAdviceCustomValidator_1, context, stereotypeReference, featureName)));
		} else if (property.getType() == null) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, stereotypeReference, format(Messages.StereotypePropertyReferenceEdgeAdviceCustomValidator_2, context, stereotypeReference, featureName)));
		}
	}

}
