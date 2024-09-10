/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ITabFolderModel;

/**
 * A class used to translate di models to {@link ISashWindowsContentProvider} models.
 * 
 * 
 * @author cedric dumoulin
 *
 */
public class SashContainerModels {

	protected Map<String, Object> diModelElements;
	
	protected Map<String, Object> cache = new HashMap<String, Object>();
	
	protected DiContentProvider diContentProvider;
	/**
	 * Constructor.
	 *
	 * @param diModelElements
	 */
	public SashContainerModels(DiContentProvider diContentProvider, Map<String, Object> diModelElements) {
		this.diModelElements = diModelElements;
		this.diContentProvider = diContentProvider;
	}


	/**
	 * @param string
	 * @return
	 */
	public ITabFolderModel getFolder(String name) {
		
		ITabFolderModel result = (ITabFolderModel)cache.get(name);
		if(result==null) {
			// Create container model and cache it
			result = (ITabFolderModel)diContentProvider.createChildSashModel(diModelElements.get(name));

			cache.put(name, result);
		}
		return result;
	}
}
