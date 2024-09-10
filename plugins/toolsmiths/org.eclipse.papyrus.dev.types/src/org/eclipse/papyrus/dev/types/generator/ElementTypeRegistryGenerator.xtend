/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Florian Noyrit - Initial API and implementation
 * 
 *****************************************************************************/
package org.eclipse.papyrus.dev.types.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.TreeIterator
import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration

class ElementTypeRegistryGenerator {

	static def Iterable<EObject> allContentsIterable(Resource resource) {
		var TreeIterator<EObject> _allContents = resource.getAllContents();
		return IteratorExtensions.<EObject>toIterable(_allContents);
	}
	
	static def String camelToUnderScore(String in) {
		var String regex = "([a-z])([A-Z])";
        var String replacement = "$1_$2";
        return in.replaceAll(regex, replacement); 
	}
	
	static def String safeName(String in) {
		var result = camelToUnderScore(in)
		result = result.replaceAll("[^A-Za-z0-9]", "_")
		result = result.replaceAll("_{2,}", "_")
		result = result.toUpperCase;
		result = result.replaceAll("UML_","");
		return result;
	}
	
	
	

	static def generateRegistry(Resource it,String outputType) '''
	/*****************************************************************************
	 * Copyright (c) 2014 CEA LIST.
	 *
	 * All rights reserved. This program and the accompanying materials
	 * are made available under the terms of the Eclipse Public License 2.0
	 * which accompanies this distribution, and is available at
	 * http://www.eclipse.org/legal/epl-2.0/
	 *
	 * SPDX-License-Identifier: EPL-2.0
	 *
	 * Contributors:
	 * 		CEA LIST - Initial API and implementation
	 *
	 *****************************************************************************/
	import org.eclipse.gmf.runtime.emf.type.core.AbstractElementTypeEnumerator;
	import org.eclipse.gmf.runtime.emf.type.core.IHintedType;

	public class «outputType» extends AbstractElementTypeEnumerator {
	
		/** Constant for UML nature */
		public static final String UML_NATURE = "UML_Nature";
		
		«FOR elementTypeConfiguration : allContentsIterable(it).filter(typeof(ElementTypeConfiguration))»
		public static final IHintedType «safeName(elementTypeConfiguration.name).toUpperCase» = (IHintedType)getElementType("«elementTypeConfiguration.identifier»"); //$NON-NLS-1$
		
		«ENDFOR»
	
	}
	'''
}
