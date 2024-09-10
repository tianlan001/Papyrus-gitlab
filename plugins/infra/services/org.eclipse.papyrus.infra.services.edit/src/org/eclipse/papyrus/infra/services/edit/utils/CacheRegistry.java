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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.edit.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.emf.type.core.AdviceBindingAddedEvent;
import org.eclipse.gmf.runtime.emf.type.core.AdviceBindingRemovedEvent;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeAddedEvent;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRemovedEvent;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IElementTypeRegistryListener2;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;

/**
 * Cache to avoid getting too many times to go for a getMatchingAdvice(), which can be test costly
 * see bug on model explorer new child menu performances
 * Bug 463564 - [Performances] Opening a menu in the ModelExplorer takes several seconds (Mars M6)
 */
public class CacheRegistry implements IElementTypeRegistryListener2 {

	private static CacheRegistry instance;

	final private Map<IClientContext, Map<IElementType, IEditHelperAdvice[]>> clientContextToTypeAndAdvices;

	private CacheRegistry() {
		clientContextToTypeAndAdvices = new HashMap<IClientContext, Map<IElementType, IEditHelperAdvice[]>>();
	}

	public static synchronized CacheRegistry getInstance() {
		if (instance == null) {
			instance = new CacheRegistry();

			// add a listener on the ElementTypeRegistry
			ElementTypeRegistry.getInstance().addElementTypeRegistryListener(instance);
		}
		return instance;
	}

	public IEditHelperAdvice[] getMatchingAdvice(IClientContext context, IElementType type) {
		Map<IElementType, IEditHelperAdvice[]> typeToAdvices = clientContextToTypeAndAdvices.get(context);
		if (typeToAdvices == null) {
			typeToAdvices = new HashMap<IElementType, IEditHelperAdvice[]>();
			clientContextToTypeAndAdvices.put(context, typeToAdvices);
		}
		IEditHelperAdvice[] result = typeToAdvices.get(type);
		if (result == null) {
			result = ElementTypeRegistry.getInstance().getEditHelperAdvice(type, context);
			// update the map
			typeToAdvices.put(type, result);
		}
		return result;
	}

	/**
	 * @param clientContext
	 * @param iElementType
	 * @return
	 */
	public IEditHelperAdvice[] getEditHelperAdvice(IClientContext clientContext, IElementType type) {
		Map<IElementType, IEditHelperAdvice[]> typeToAdvices = clientContextToTypeAndAdvices.get(clientContext);
		if (typeToAdvices == null) {
			typeToAdvices = new HashMap<IElementType, IEditHelperAdvice[]>();
			clientContextToTypeAndAdvices.put(clientContext, typeToAdvices);
		}
		IEditHelperAdvice[] result = typeToAdvices.get(type);
		if (result == null) {
			result = ElementTypeRegistry.getInstance().getEditHelperAdvice(type, clientContext);
			// update the map
			typeToAdvices.put(type, result);
		}
		return result;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementTypeRegistryListener#elementTypeAdded(org.eclipse.gmf.runtime.emf.type.core.ElementTypeAddedEvent)
	 *
	 * @param elementTypeAddedEvent
	 */
	@Override
	public void elementTypeAdded(ElementTypeAddedEvent elementTypeAddedEvent) {
		flushCache();
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementTypeRegistryListener2#elementTypeRemoved(org.eclipse.gmf.runtime.emf.type.core.ElementTypeRemovedEvent)
	 *
	 * @param event
	 */
	@Override
	public void elementTypeRemoved(ElementTypeRemovedEvent event) {
		flushCache();
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementTypeRegistryListener2#adviceBindingAdded(org.eclipse.gmf.runtime.emf.type.core.AdviceBindingAddedEvent)
	 *
	 * @param event
	 */
	@Override
	public void adviceBindingAdded(AdviceBindingAddedEvent event) {
		flushCache();
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementTypeRegistryListener2#adviceBindingRemoved(org.eclipse.gmf.runtime.emf.type.core.AdviceBindingRemovedEvent)
	 *
	 * @param event
	 */
	@Override
	public void adviceBindingRemoved(AdviceBindingRemovedEvent event) {
		flushCache();
	}

	/**
	 * Flush the cache map. This should be done as soon as the registry is changed.
	 */
	protected void flushCache() {
		clientContextToTypeAndAdvices.clear();
	}

}
