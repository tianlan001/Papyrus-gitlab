/******************************************************************************
 * Copyright (c) 2010, 2020 Artem Tikhomirov, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (independent) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * @author artem
 */
public class Extras {

	/**
	 * This is a handy way to deal with EMF 2.6 M4 change (see bug #255469) - EObject is implicit
	 * superclass for any EClass, but EMF itself doesn't answer this question when asked on a metamodel level 
	 * @return true if the first candidate class is supertype of the second, with respect of implicit EObject supertype of any other EClass.
	 */
	public static boolean isSuperTypeOf(EClass superclassCandidate, EClass subclassCandidate) {
		return superclassCandidate.isSuperTypeOf(subclassCandidate) || superclassCandidate == EcorePackage.eINSTANCE.getEObject() || ("EObject".equals(superclassCandidate.getName()) && EcorePackage.eNS_URI.equals(superclassCandidate.getEPackage().getNsURI()));
	}
}
