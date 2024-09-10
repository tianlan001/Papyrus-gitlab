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

package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering;

/**
 * This factory is used to give an instance of the diagram expansion
 *
 */
public class DiagramExpansionSingleton  {

	protected  DiagramExpansionsRegistry diagramExpansionRegistry;

	protected static DiagramExpansionSingleton diagramExpansionSingleton= null;
	
	
	private DiagramExpansionSingleton(){
		diagramExpansionRegistry= new DiagramExpansionsRegistry();
	}
	
	/**
	 * 
	 * @return an instance of the DiagramExpansion Singleton
	 */
	public static  DiagramExpansionSingleton getInstance(){
		if( diagramExpansionSingleton==null){
			diagramExpansionSingleton= new DiagramExpansionSingleton();
		}
		return diagramExpansionSingleton;
	}
	/**
	 * get the diagram expansion associated to this singleton
	 * @return {@link DiagramExpansionsRegistry}
	 */
	public  DiagramExpansionsRegistry getDiagramExpansionRegistry() {
		return diagramExpansionRegistry;
	}


}
