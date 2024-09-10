/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.internal.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

/**
 * Enablement expression properties for the {@link ServicesRegistry} type. Supported properties are:
 * <ul>
 * <li>{@link #hasSemanticModel(ServicesRegistry) hasSemanticModel}: does any {@link ILanguage}
 * instantiated in the registry's {@link ModelSet} (if there is one) have a corresponding
 * {@link IModel} providing its content?</li>
 * </ul>
 */
public class ServiceRegistryPropertyTester extends PropertyTester {

	private static final String PROPERTY_HAS_SEMANTIC_MODEL = "hasSemanticModel"; //$NON-NLS-1$

	public ServiceRegistryPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result;

		switch (property) {
		case PROPERTY_HAS_SEMANTIC_MODEL:
			result = hasSemanticModel(TypeUtils.as(receiver, ServicesRegistry.class));
			break;
		default:
			result = false;
			break;
		}

		return result;
	}

	protected boolean hasSemanticModel(ServicesRegistry services) {
		boolean result = false;

		try {
			ModelSet modelSet = services.getService(ModelSet.class);
			result = !ILanguageService.getLanguageModels(modelSet).isEmpty();
		} catch (ServiceException e) {
			// Fine, there's no model-set
		}

		return result;
	}
}
