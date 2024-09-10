/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 
 *  CEA LIST- Initial API and implementation
 *  Inspired from GMF runtime internal class: 
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.utils;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;

/**
 * Caching the advices for a given eobject for a given context
 */
public final class RequestCacheEntries implements IRequestCacheEntries {

	private RequestCacheEntries() {
		//
	}

	public static final void initializeEObjCache(EObject eObj, Map map) throws ServiceException {
		IClientContext clientContext = TypeContext.getContext(eObj);
		map.put(Client_Context, clientContext);
		IElementType type = ElementTypeRegistry.getInstance().getElementType(
				eObj, clientContext);
		map.put(Element_Type, type);
		IEditHelperAdvice[] advices = ElementTypeRegistry.getInstance()
				.getEditHelperAdvice(eObj, clientContext);
		map.put(EditHelper_Advice, advices);
		
		map.put(IRequestCacheEntries.Dependent_Elements, new HashSet<Object>()); //The set will be populated on the fly
		map.put(IRequestCacheEntries.Checked_Elements, new HashSet<Object>()); //The set will be populated on the fly
	}

}