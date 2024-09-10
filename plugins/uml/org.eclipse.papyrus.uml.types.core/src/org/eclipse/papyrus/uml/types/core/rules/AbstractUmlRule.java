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
package org.eclipse.papyrus.uml.types.core.rules;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.core.AbstractRule;
import org.eclipse.papyrus.uml.types.core.requests.ApplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.ApplyStereotypeRequest;
import org.eclipse.papyrus.uml.types.core.requests.SetStereotypeValueRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyProfileRequest;
import org.eclipse.papyrus.uml.types.core.requests.UnapplyStereotypeRequest;

public abstract class AbstractUmlRule<T extends RuleConfiguration> extends AbstractRule<T> {
	protected T invariantRuleConfiguration;

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.rulebased.core.AbstractRule#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {

		List<?> elementsToEdit = request.getElementsToEdit();
		for (Object elementToEdit : elementsToEdit) {
			if (elementToEdit instanceof EObject) {
				IElementType[] types = ElementTypeRegistry.getInstance().getAllTypesMatching((EObject) elementToEdit, request.getClientContext());

				for (IElementType type : types) {
					if (type instanceof ConfiguredHintedSpecializationElementType) {
						ConfiguredHintedSpecializationElementType configuredHintedSpecializationElementType = (ConfiguredHintedSpecializationElementType) type;
						if (request instanceof ApplyStereotypeRequest) {
							if (!approveApplyStereotypeRequest(configuredHintedSpecializationElementType, (ApplyStereotypeRequest) request)) {
								return false;
							}
						} else if (request instanceof UnapplyStereotypeRequest) {
							if (!approveUnapplyStereotypeRequest(configuredHintedSpecializationElementType, (UnapplyStereotypeRequest) request)) {
								return false;
							}
						} else if (request instanceof ApplyProfileRequest) {
							if (!approveApplyProfileRequest(configuredHintedSpecializationElementType, (ApplyProfileRequest) request)) {
								return false;
							}
						} else if (request instanceof UnapplyProfileRequest) {
							if (!approveUnapplyProfileRequest(configuredHintedSpecializationElementType, (UnapplyProfileRequest) request)) {
								return false;
							}
						} else if (request instanceof SetStereotypeValueRequest) {
							if (!approveSetStereotypeValueRequest(configuredHintedSpecializationElementType, (SetStereotypeValueRequest) request)) {
								return false;
							}
						}
					}
				}
			}
		}

		return super.approveRequest(request);
	}

	protected abstract boolean approveApplyStereotypeRequest(ConfiguredHintedSpecializationElementType type, ApplyStereotypeRequest request);

	protected abstract boolean approveUnapplyStereotypeRequest(ConfiguredHintedSpecializationElementType type, UnapplyStereotypeRequest request);

	protected abstract boolean approveApplyProfileRequest(ConfiguredHintedSpecializationElementType type, ApplyProfileRequest request);

	protected abstract boolean approveUnapplyProfileRequest(ConfiguredHintedSpecializationElementType type, UnapplyProfileRequest request);

	protected abstract boolean approveSetStereotypeValueRequest(ConfiguredHintedSpecializationElementType type, SetStereotypeValueRequest request);

	@Override
	public void init(T invariantRuleConfiguration) {
		this.invariantRuleConfiguration = invariantRuleConfiguration;
	}
}
