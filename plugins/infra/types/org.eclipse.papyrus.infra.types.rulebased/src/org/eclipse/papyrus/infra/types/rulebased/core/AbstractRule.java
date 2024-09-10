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
package org.eclipse.papyrus.infra.types.rulebased.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

public abstract class AbstractRule<T extends RuleConfiguration> implements IRule<T> {
	protected T invariantRuleConfiguration;

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			IElementType typeToCreate = ((CreateElementRequest) request).getElementType();

			if (!approveCreationRequest(((ConfiguredHintedSpecializationElementType) typeToCreate), (CreateElementRequest) request)) {
				return false;
			}

		} else if (request instanceof SetRequest) {
			EStructuralFeature feature = ((SetRequest) request).getFeature();
			if (feature instanceof EReference) {

				// containment. Check the kind of element to edit
				Object value = ((SetRequest) request).getValue();
				List<Object> values = new ArrayList<Object>();
				// value = single object or list ?
				if (value instanceof EObject) {
					values.add(value);
				} else if (value instanceof List) {
					values.addAll((List<Object>) value);
				}

				for (Object object : values) {
					if (object instanceof EObject) {
						IElementType[] types = ElementTypeRegistry.getInstance().getAllTypesMatching((EObject) object, request.getClientContext());
						for (IElementType type : types) {
							if (type instanceof ConfiguredHintedSpecializationElementType) {
								if (!approveSetRequest((ConfiguredHintedSpecializationElementType) type, (SetRequest) request)) {
									return false;
								}
							}
						}
					}
				}
			}
		} else if (request instanceof MoveRequest) {

			Map<EObject, EReference> objectsToMove = ((MoveRequest) request).getElementsToMove();

			for (EObject movedElement : objectsToMove.keySet()) {

				IElementType[] types = ElementTypeRegistry.getInstance().getAllTypesMatching(movedElement, request.getClientContext());
				for (IElementType type : types) {
					if (type instanceof ConfiguredHintedSpecializationElementType) {
						if (!approveMoveRequest((ConfiguredHintedSpecializationElementType) type, (MoveRequest) request)) {
							return false;
						}
					}
				}
			}
			return true;
		}
		return true;
	}


	protected abstract boolean approveMoveRequest(ConfiguredHintedSpecializationElementType type, MoveRequest request);

	protected abstract boolean approveSetRequest(ConfiguredHintedSpecializationElementType type, SetRequest request);

	protected abstract boolean approveCreationRequest(ConfiguredHintedSpecializationElementType type, CreateElementRequest request);

	@Override
	public void init(T invariantRuleConfiguration) {
		this.invariantRuleConfiguration = invariantRuleConfiguration;
	}
}
