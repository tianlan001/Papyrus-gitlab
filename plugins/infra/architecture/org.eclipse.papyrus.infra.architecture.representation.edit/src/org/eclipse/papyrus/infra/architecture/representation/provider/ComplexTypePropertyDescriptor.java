/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.architecture.representation.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.provider.SurrogateItemPropertyDescriptor;
import org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate;
import org.eclipse.papyrus.infra.architecture.representation.OwningRule;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;

/**
 * This is used for the ModelAutoCreate.target property because we may want to create stereotyped elements
 *
 * @author Laurent Wouters
 */
public class ComplexTypePropertyDescriptor extends SurrogateItemPropertyDescriptor {
	/**
	 * Constructor.
	 *
	 * @param inner
	 */
	public ComplexTypePropertyDescriptor(IItemPropertyDescriptor inner) {
		super(inner);
	}

	/**
	 * @see org.eclipse.papyrus.infra.architecture.representation.provider.SurrogateItemPropertyDescriptor#getChoiceOfValues(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public Collection<?> getChoiceOfValues(Object object) {
		ModelAutoCreate rule = (ModelAutoCreate) object;
		OwningRule owningRule = (OwningRule) rule.eContainer();
		PapyrusRepresentationKind repKind = (PapyrusRepresentationKind) owningRule.eContainer();
		ArchitectureDescriptionLanguage language = repKind.getLanguage();

		List<String> result = new ArrayList<>();
		IClientContext context = ClientContextManager.getInstance().getClientContext(language.getId());
		IElementType[] types = ElementTypeRegistry.getInstance().getElementTypes(context);
		if (types != null) {
			for (IElementType type : types) {
				result.add(type.getId());
			}
		}
		Collections.sort(result);
		return result;
	}
}
