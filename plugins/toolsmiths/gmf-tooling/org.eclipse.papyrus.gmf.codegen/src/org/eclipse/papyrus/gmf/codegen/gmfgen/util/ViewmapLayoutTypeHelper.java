/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
package org.eclipse.papyrus.gmf.codegen.gmfgen.util;

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType;

public final class ViewmapLayoutTypeHelper {
	private static ViewmapLayoutTypeHelper ourInstance;
	
	public static final ViewmapLayoutTypeHelper getSharedInstance(){
		if (ourInstance == null){
			ourInstance = new ViewmapLayoutTypeHelper();
		}
		return ourInstance;
	}

	public boolean isStoringChildPositions(ViewmapLayoutType layoutType){
		//FIXME: 
		//This method is called from various templates to determine whether edit part should react to the changes of View's bounds
		//(that is, whether is should have bounds oriented view styles and whether is should install notation model listeners)
		//The goal is to avoid installing of editpolicies that may save wrong bounds into the View
		//After this we will be able to return true here and remove this helper.
		return ViewmapLayoutType.XY_LAYOUT_LITERAL.equals(layoutType);
	}
	
	public boolean isStoringChildPositions(GenNode genNode){
		return isStoringChildPositions(genNode.getLayoutType());
	}
	
}
