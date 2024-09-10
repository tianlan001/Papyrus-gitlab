/*****************************************************************************
 * Copyright (c) 2011, 2014 CEA LIST and others.
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
 *		CEA LIST - Initial API and implementation
 *      Christian W. Damus (CEA) - bug 413703
 *      Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.fr - Bug 436954
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.modelexplorer.newchild;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.newchild.CreationMenuFactory;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder;
import org.eclipse.papyrus.infra.services.edit.utils.RequestCacheEntries;
import org.eclipse.swt.widgets.Menu;

/**
 * This class has in charge to create menu from elementCreationMenuModel
 */
public class ModelExplorerDynamicNewChild extends org.eclipse.papyrus.infra.newchild.ui.DynamicNewChild {

	/**
	 * Constructor.
	 */
	public ModelExplorerDynamicNewChild() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param id
	 */
	public ModelExplorerDynamicNewChild(String id) {
		super(id);
	}

	@Override
	public void fill(Menu menu, int index) {
		EObject eObject = getSelection();
		if (eObject != null) {

			// caches the advices used by the selected EObject, to avoid finding them for each menu item
			Map<?, ?> adviceCache = new HashMap<Object, Object>();
			try {
				RequestCacheEntries.initializeEObjCache(eObject, adviceCache);
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
			Map<Object, Object> cacheMap = new HashMap<Object, Object>();
			cacheMap.put(eObject, adviceCache);

			CreationMenuFactory creationMenuFactory = new ModelExplorerMenuFactory(editingDomain);
			List<Folder> folders = creationMenuRegistry.getRootFolder();
			Iterator<Folder> iterFolder = folders.iterator();
			while (iterFolder.hasNext()) {
				Folder currentFolder = iterFolder.next();
				boolean hasbeenBuild = creationMenuFactory.populateMenu(menu, currentFolder, eObject, index, cacheMap);
				if (hasbeenBuild) {
					index++;
				}
			}
		} else {
			super.fill(menu, index);
		}
	}

}
