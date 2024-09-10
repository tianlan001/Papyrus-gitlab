/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.toolsmiths.validation.profile.internal.checkers;

import static org.eclipse.emf.ecore.EcorePackage.Literals.EPACKAGE;
import static org.eclipse.emf.ecore.EcorePackage.Literals.EPACKAGE__NS_URI;
import static org.eclipse.uml2.uml.resource.UMLResource.ECORE_PROFILE_NS_URI;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages.Messages;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Custom validation rules for namespace URIs and possibly other details in
 * <em>UML Profiles</em>.
 */
public class ProfileCustomValidator extends CustomModelChecker.SwitchValidator {

	public ProfileCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(Package package_, DiagnosticChain diagnostics, Map<Object, Object> context) {
		validatePackageURI(package_.getURI(), package_, diagnostics, context);
	}

	public void validateDefault(EObject stereotypeApplication, DiagnosticChain diagnostics, Map<Object, Object> context) {
		EClass stereotype = stereotypeApplication.eClass();
		if (isEPackageStereotype(stereotype)) {
			String uri = (String) stereotypeApplication.eGet(stereotype.getEStructuralFeature(EPACKAGE__NS_URI.getName()));
			Element basePackage = UMLUtil.getBaseElement(stereotypeApplication);

			if (basePackage != null && !basePackage.eIsProxy()) { // Don't need to validate a dangling stereotype application
				validatePackageURI(uri, basePackage, diagnostics, context);
			}
		}
	}

	private boolean isEPackageStereotype(EClass eClass) {
		return eClass.getEPackage() != null
				&& EPACKAGE.getName().equals(eClass.getName())
				&& ECORE_PROFILE_NS_URI.equals(eClass.getEPackage().getNsURI());
	}

	private void validatePackageURI(String uri, EObject target, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (uri != null && !uri.isBlank()) {
			try {
				URI parsed = URI.createURI(uri, true);
				if (parsed.isRelative()) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, target, format(Messages.ProfileCustomValidator_0, context, target, uri)));
				}
			} catch (Exception e) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, target, format(Messages.ProfileCustomValidator_1, context, target, uri)));
			}
		}
	}

}
