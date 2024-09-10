/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL 
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

package org.eclipse.papyrus.infra.core.sashwindows.di.util;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiFactory;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageList;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.eclipse.papyrus.infra.core.sashwindows.di.Window;


/**
 * Set of utility methods
 * @author dumoulin
 */
public class DiUtils {

	/**
	 * Create a default SashModel with one window and one folder.
	 * Set the current folder.
	 * @param diResource
	 * @return
	 */
	static public SashModel createDefaultSashModel() {
	
		// SashModel
		SashModel sashModel = DiFactory.eINSTANCE.createSashModel();
		Window window = DiFactory.eINSTANCE.createWindow();
		sashModel.getWindows().add(window);
		
		TabFolder folder = DiFactory.eINSTANCE.createTabFolder();
		window.setPanel(folder);
		// Default folder
		sashModel.setCurrentSelection(folder);
		
		return sashModel;
	}

	/**
	 * Create a default SashWindowsMngr with one PageLit and one default SashModel.
	 * Set the current folder.
	 * @param diResource
	 * @return
	 */
	static public SashWindowsMngr createDefaultSashWindowsMngr() {
		SashWindowsMngr model;
		
		model = DiFactory.eINSTANCE.createSashWindowsMngr();
		
		// SashModel
		SashModel layout = createDefaultSashModel();
		
		model.setSashModel(layout);
		
		// PageList
		PageList pageList = DiFactory.eINSTANCE.createPageList();
		model.setPageList(pageList);
		return model;
	}

	/**
	 * Lookup for the SashModel object in the resource.
	 * @param diResource 
	 * @return The {@link DiSashModel} or null if not found.
	 */
	static public SashWindowsMngr lookupSashWindowsMngr(Resource diResource) {
		
		for( Object node : diResource.getContents() )
		{
			if( node instanceof SashWindowsMngr)
				return (SashWindowsMngr)node;
		}
		return null;
	}


}
