/*****************************************************************************
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 571630
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.rulebased.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType;
import org.eclipse.papyrus.infra.types.rulebased.Activator;
import org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;


public class DefaultRuleEditHelperAdvice extends AbstractEditHelperAdvice {


	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		List<ConfiguredHintedSpecializationElementType> types = getTypes(request);

		// Must approve from the whole hierarchy
		for (ConfiguredHintedSpecializationElementType configuredHintedSpecializationElementType : types) {
			if (!approveRequest(configuredHintedSpecializationElementType, request)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param request
	 *
	 */
	protected List<ConfiguredHintedSpecializationElementType> getTypes(IEditCommandRequest request) {
		List<ConfiguredHintedSpecializationElementType> result = new ArrayList<>();
		if (request instanceof CreateElementRequest) {
			IElementType typeToCreate = ((CreateElementRequest) request).getElementType();
			List<ConfiguredHintedSpecializationElementType> superConfiguredTypes = getAllTypes(typeToCreate);
			result.addAll(superConfiguredTypes);
		} else if (request instanceof SetRequest) {
			// check the feature to set is a containment feature and element to move is an extended element type
			EStructuralFeature feature = ((SetRequest) request).getFeature();
			if (feature instanceof EReference) {
				if (((EReference) feature).isContainment()) {

					// containment. Check the kind of element to edit
					Object value = ((SetRequest) request).getValue();
					List<Object> values = new ArrayList<>();
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
								appendConfiguredTypes(type, result);
							}
						}
					}
				}
			}
		} else if (request instanceof MoveRequest) {
			// check the feature to set is a containment feature and element to move is an extended element type
			@SuppressWarnings("unchecked")
			Map<EObject, EReference> objectsToMove = ((MoveRequest) request).getElementsToMove();
			// do not compute with reference, this can be null. This could be interesting to check...
			appendConfiguredTypes(request, objectsToMove.keySet(), result);
		} else {
			// Get types of the elements to edit
			@SuppressWarnings("unchecked")
			Collection<? extends EObject> elementsToEdit = request.getElementsToEdit();
			appendConfiguredTypes(request, elementsToEdit, result);
		}

		return result;

	}

	private void appendConfiguredTypes(IEditCommandRequest request, Collection<? extends EObject> objects, Collection<? super ConfiguredHintedSpecializationElementType> result) {
		objects.stream()
				.map(object -> ElementTypeRegistry.getInstance().getAllTypesMatching(object, request.getClientContext()))
				.flatMap(Stream::of)
				.forEach(type -> appendConfiguredTypes(type, result));
	}

	private void appendConfiguredTypes(IElementType type, Collection<? super ConfiguredHintedSpecializationElementType> result) {
		if (type instanceof ConfiguredHintedSpecializationElementType) {
			if (((ConfiguredHintedSpecializationElementType) type).getConfiguration() instanceof RuleBasedTypeConfiguration) {
				result.add((ConfiguredHintedSpecializationElementType) type);

				List<ConfiguredHintedSpecializationElementType> superConfiguredTypes = getAllSuperConfiguredTypes((ConfiguredHintedSpecializationElementType) type);
				result.addAll(superConfiguredTypes);
			}
		}
	}

	/**
	 * Returns the list of types (this one and supers) that are configuredTypes.
	 *
	 * @param type
	 *            the type from which all s are retrieved
	 * @return the list of types in the hierarchy of specified type, including type itself if matching. Returns an empty list if none is matching
	 */
	protected List<ConfiguredHintedSpecializationElementType> getAllTypes(IElementType type) {
		List<ConfiguredHintedSpecializationElementType> result = new ArrayList<>();

		if (!(type instanceof ConfiguredHintedSpecializationElementType)) {
			// no need to take care of metamodel types yet
			return result;
		}

		if (((ConfiguredHintedSpecializationElementType) type).getConfiguration() instanceof RuleBasedTypeConfiguration) {
			result.add((ConfiguredHintedSpecializationElementType) type);
		}

		IElementType[] superTypes = type.getAllSuperTypes();
		if (superTypes.length == 0) {
			return result;
		}
		// get the reverse order
		for (int i = superTypes.length - 1; i >= 0; i--) {
			if (superTypes[i] instanceof ConfiguredHintedSpecializationElementType) {
				if (((ConfiguredHintedSpecializationElementType) superTypes[i]).getConfiguration() instanceof RuleBasedTypeConfiguration) {
					result.add((ConfiguredHintedSpecializationElementType) superTypes[i]);
				}
			}
		}

		return result;
	}

	protected List<ConfiguredHintedSpecializationElementType> getAllSuperConfiguredTypes(ConfiguredHintedSpecializationElementType type) {
		IElementType[] superTypes = type.getAllSuperTypes();
		if (superTypes.length == 0) {
			return Collections.emptyList();
		}
		List<ConfiguredHintedSpecializationElementType> superElementTypes = new ArrayList<>();
		// get the reverse order
		for (int i = superTypes.length - 1; i >= 0; i--) {
			if (superTypes[i] instanceof ConfiguredHintedSpecializationElementType) {
				if (((ConfiguredHintedSpecializationElementType) superTypes[i]).getConfiguration() instanceof RuleBasedTypeConfiguration) {
					superElementTypes.add((ConfiguredHintedSpecializationElementType) superTypes[i]);
				}
			}
		}
		return superElementTypes;
	}


	protected boolean processCompositeRule(CompositeRuleConfiguration compositeRule, IEditCommandRequest request) {
		Iterator<RuleConfiguration> iterator = compositeRule.getComposedRules().iterator();
		RuleConfiguration nextComposedRuleConfiguration = iterator.next();
		boolean result = processRule(nextComposedRuleConfiguration, request);

		while (iterator.hasNext()) {
			nextComposedRuleConfiguration = iterator.next();

			boolean resultNextComposedRuleConfiguration = processRule(nextComposedRuleConfiguration, request);

			if (compositeRule instanceof OrRuleConfiguration) {
				if (result == false && resultNextComposedRuleConfiguration) {
					result = true;
				}
			} else if (compositeRule instanceof AndRuleConfiguration) {
				if (result == true && !resultNextComposedRuleConfiguration) {
					result = false;
				}
			}
		}

		return result;
	}

	protected boolean processRule(RuleConfiguration ruleConfiguration, IEditCommandRequest request) {
		if (ruleConfiguration instanceof CompositeRuleConfiguration) {
			return processCompositeRule((CompositeRuleConfiguration) ruleConfiguration, request);
		} else if (ruleConfiguration instanceof NotRuleConfiguration) {
			RuleConfiguration composedRule = ((NotRuleConfiguration) ruleConfiguration).getComposedRule();
			return !processRule(composedRule, request);
		} else {
			return RuleConfigurationTypeRegistry.getInstance().getRule(ruleConfiguration).approveRequest(request);
		}
	}

	protected boolean approveRequest(ConfiguredHintedSpecializationElementType elementType, IEditCommandRequest request) {

		ElementTypeConfiguration configuration = elementType.getConfiguration();
		if (configuration instanceof RuleBasedTypeConfiguration) {
			RuleConfiguration ruleConfiguration = ((RuleBasedTypeConfiguration) configuration).getRuleConfiguration();

			return processRule(ruleConfiguration, request);
		} else {
			Activator.log.warn("Expected RuleConfiguration as configuration type for : " + elementType);
		}

		return true;
	}



}
