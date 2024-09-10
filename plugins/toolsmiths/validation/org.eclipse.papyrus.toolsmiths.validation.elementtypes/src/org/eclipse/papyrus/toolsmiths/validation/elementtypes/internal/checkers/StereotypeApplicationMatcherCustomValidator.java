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
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Custom validation rules for stereotype application matchers and related advice.
 */
public class StereotypeApplicationMatcherCustomValidator extends CustomModelChecker.SwitchValidator {

	public StereotypeApplicationMatcherCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(StereotypeApplicationMatcherConfiguration stereotypeMatcher, DiagnosticChain diagnostics, Map<Object, Object> context) {
		String profileURI = stereotypeMatcher.getProfileUri();
		if (profileURI != null && !profileURI.isBlank()) {
			validateRequiredProfile(stereotypeMatcher, stereotypeMatcher.getProfileUri(), diagnostics, context);
		}
		for (String stereotype : stereotypeMatcher.getStereotypesQualifiedNames()) {
			validateStereotypeName(stereotypeMatcher, stereotype, diagnostics, context);
		}
	}

	private void validateRequiredProfile(StereotypeApplicationMatcherConfiguration stereotypeMatcher, String profileURI, DiagnosticChain diagnostics, Map<Object, Object> context) {
		LocalProfileIndex index = LocalProfileIndex.getInstance(stereotypeMatcher, context);
		Profile profile = index.getProfileByURI(profileURI, stereotypeMatcher);
		if (profile == null) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, stereotypeMatcher, format(Messages.StereotypeApplicationMatcherCustomValidator_0, context, stereotypeMatcher, profileURI)));
		}
	}

	private void validateStereotypeName(StereotypeApplicationMatcherConfiguration stereotypeMatcher, String qualifiedName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Stereotype stereotype = getStereotype(stereotypeMatcher, qualifiedName, context);
		if (stereotype == null) {
			diagnostics.add(createDiagnostic(Diagnostic.ERROR, stereotypeMatcher, format(Messages.StereotypeApplicationMatcherCustomValidator_1, context, stereotypeMatcher)));
		}
	}

	private Stereotype getStereotype(StereotypeApplicationMatcherConfiguration stereotypeMatcher, String qualifiedName, Map<Object, Object> context) {
		Stereotype result = null;

		LocalProfileIndex index = LocalProfileIndex.getInstance(stereotypeMatcher, context);

		String profileURI = stereotypeMatcher.getProfileUri();
		if (profileURI != null && !profileURI.isBlank()) {
			// Look in the referenced profile only
			Profile profile = index.getProfileByURI(profileURI, stereotypeMatcher);
			if (profile != null) {
				result = index.getStereotype(qualifiedName, profile, stereotypeMatcher);
			}
		} else {
			result = index.getStereotype(qualifiedName, stereotypeMatcher);
		}

		return result;
	}

}
