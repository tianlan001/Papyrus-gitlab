/*****************************************************************************
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 568853
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.matchers.stereotype;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;


/**
 * Matcher for UML elements that should be stereotyped
 */
public class StereotypeApplicationMatcher implements IElementMatcher {

	private List<String> stereotypesQualifiedNames;
	private String profileUri;

	public StereotypeApplicationMatcher(StereotypeApplicationMatcherConfiguration configuration) {
		this.profileUri = configuration.getProfileUri();
		this.stereotypesQualifiedNames = configuration.getStereotypesQualifiedNames();
	}


	@Override
	public boolean matches(EObject eObject) {
		if (!(eObject instanceof Element)) {
			return false;
		}

		Element element = (Element) eObject;

		if (profileUri != null && !isProfileApplied(element, profileUri)) {
			return false;
		}

		if (element.getAppliedStereotypes().isEmpty()) {
			return false;
		}

		for (String stereotypeQualifiedName : stereotypesQualifiedNames) {
			if (element.getAppliedStereotype(stereotypeQualifiedName) == null) {
				return false;
			}
		}

		return true;
	}

	public List<String> getStereotypesQualifiedNames() {
		return stereotypesQualifiedNames;
	}


	public void setStereotypesQualifiedNames(List<String> stereotypeQualifiedName) {
		this.stereotypesQualifiedNames = stereotypeQualifiedName;
	}

	public static boolean isProfileApplied(Element element, String profileURI) {
		org.eclipse.uml2.uml.Package package_ = element.getNearestPackage();
		if (package_ == null) {
			return false;
		}

		Set<String> appliedProfileByURI = new HashSet<>();
		for (Profile appliedProfile : package_.getAllAppliedProfiles()) {
			appliedProfileByURI.add(appliedProfile.getURI());
		}

		return appliedProfileByURI.contains(profileURI);
	}

}
