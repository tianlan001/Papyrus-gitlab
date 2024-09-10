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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.rules.invariantstereotype;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.papyrus.uml.types.core.requests.ApplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.ApplyStereotypeRequest;
import org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyStereotypeRequest;
import org.eclipse.papyrus.uml.types.core.rules.AbstractUmlRule;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

public class InvariantStereotypeRule extends AbstractUmlRule<InvariantStereotypeRuleConfiguration> {

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.IRule#matches(org.eclipse.emf.ecore.EObject)
	 *
	 * @param eObject
	 * @return
	 */
	public boolean matches(EObject eObject) {
		if (!(eObject instanceof Element)) {
			return false;
		}

		String stereotypeQualifiedName = invariantRuleConfiguration.getStereotypeQualifiedName();
		if (stereotypeQualifiedName == null) { // to avoid null pointers
			return false;
		}
		Stereotype appliedStereotype = ((Element) eObject).getAppliedStereotype(stereotypeQualifiedName);
		if (appliedStereotype != null) { // one has been found, no need to get further
			return true;
		} else if (!invariantRuleConfiguration.isStrict()) { // the stereotype does not match perfectly, but one of the applied stereotypes on the element could match if not strict
			for (Stereotype stereotype : ((Element) eObject).getAppliedStereotypes()) {
				for (Stereotype superStereotype : StereotypeUtil.getAllSuperStereotypes(stereotype)) {
					if (stereotypeQualifiedName.equals(superStereotype.getQualifiedName())) {
						return true; // there is a match in the super stereotypes. Finish here, element matches
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.AbstractRule#approveMoveRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveMoveRequest(ConfiguredHintedSpecializationElementType type, MoveRequest request) {
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.AbstractRule#approveSetRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveSetRequest(ConfiguredHintedSpecializationElementType type, SetRequest request) {
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.AbstractRule#approveCreationRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveCreationRequest(ConfiguredHintedSpecializationElementType type, CreateElementRequest request) {
		// check that the required profile is present.
		String requiredProfileName = invariantRuleConfiguration.getRequiredProfile();
		if (requiredProfileName != null) {
			// check target
			EObject container = request.getContainer();
			if (!(container instanceof Element)) {
				return false;
			}
			Package nearestPackage = ((Element) container).getNearestPackage();
			if (nearestPackage == null) {
				return false;
			}
			Profile appliedProfile = nearestPackage.getAppliedProfile(requiredProfileName, true);
			return appliedProfile != null;
		}

		return true;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.types.core.rules.AbstractUmlRule#approveApplyStereotypeRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.papyrus.uml.types.core.requests.ApplyStereotypeRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveApplyStereotypeRequest(ConfiguredHintedSpecializationElementType type, ApplyStereotypeRequest request) {
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.types.core.rules.AbstractUmlRule#approveUnapplyStereotypeRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.papyrus.uml.types.core.requests.UnapplyStereotypeRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveUnapplyStereotypeRequest(ConfiguredHintedSpecializationElementType type, UnapplyStereotypeRequest request) {

		if (request.getStereotype().getQualifiedName().equals(this.invariantRuleConfiguration.getStereotypeQualifiedName())) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.types.core.rules.AbstractUmlRule#approveApplyProfileRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.papyrus.uml.types.core.requests.ApplyProfileRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveApplyProfileRequest(ConfiguredHintedSpecializationElementType type, ApplyProfileRequest request) {
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.types.core.rules.AbstractUmlRule#approveUnapplyProfileRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.papyrus.uml.types.core.requests.UnapplyProfileRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveUnapplyProfileRequest(ConfiguredHintedSpecializationElementType type, UnapplyProfileRequest request) {
		String requiredProfileName = invariantRuleConfiguration.getRequiredProfile();

		if (requiredProfileName.equals(request.getProfile().getQualifiedName())) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.types.core.rules.AbstractUmlRule#approveSetStereotypeValueRequest(org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType, org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest)
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@Override
	protected boolean approveSetStereotypeValueRequest(ConfiguredHintedSpecializationElementType type, SetStereotypeValueRequest request) {
		return true;
	}

}
