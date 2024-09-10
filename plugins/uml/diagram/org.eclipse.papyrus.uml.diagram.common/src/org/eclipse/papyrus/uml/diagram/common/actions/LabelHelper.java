/*******************************************************************************
 * Copyright (c) 2008 Conselleria de Infraestructuras y Transporte,
 * Generalitat de la Comunitat Valenciana .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Gabriel Merin (Prodevelop) - initial API implementation
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.actions;

import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;


/**
 * This helper class is used to get/set the name of a NamedElement EObject.
 *
 * Modified 21 september 2007
 *
 * @author <a href="mailto:gmerin@prodevelop.es">Gabriel Merin</a>
 * @author <a href="mailto:fjcano@prodevleop.es">Francisco Javier Cano Muñoz</a>
 *
 * @deprecated prefer {@link NamedElementUtil}
 */
@Deprecated
public class LabelHelper {

	/** The singleton. */
	public static final LabelHelper INSTANCE = new LabelHelper();

	/**
	 * Initialize the name of a child EObject contained in a parent EObject.
	 *
	 * @param parentEObject
	 *            the parent EObject to start searching
	 * @param childEObject
	 *            the child EObject whose name should be initialized
	 *
	 * @deprecated should not be used.
	 */
	// @unused
	@Deprecated
	public void initName(EObject parentEObject, EObject childEObject) {
		if ((childEObject != null) && (childEObject instanceof NamedElement)) {
			String name = findName(parentEObject, (NamedElement) childEObject);

			// Either delegate the call or return nothing.
			EAttribute att = null;
			att = UMLPackage.eINSTANCE.getNamedElement_Name();

			if (att != null && ((EDataType) att.getEType()).getInstanceClass().equals(String.class)) {
				childEObject.eSet(att, name);
			}
		}
	}

	/**
	 * This returns a name to give to given EObject.
	 *
	 * @param parentEObject
	 *            the parent EObject
	 * @param childEObject
	 *            the new EObject to add
	 *
	 * @return the name
	 *
	 * @deprecated
	 */
	@Deprecated
	public String findName(EObject parentEObject, NamedElement childEObject) {
		return findName(parentEObject, childEObject.eClass());
	}

	/**
	 * This returns a name to give to a new EObject.
	 *
	 * @param parentEObject
	 *            the parent EObject
	 * @param childEClass
	 *            the new EObject to add
	 *
	 * @return the name
	 *
	 * @deprecated use {@link NamedElementUtil#getDefaultNameWithIncrementFromBase(String, Collection)} instead.
	 */
	@Deprecated
	public String findName(EObject parentEObject, EClass childEClass) {
		return NamedElementUtil.getDefaultNameWithIncrementFromBase(childEClass.getName(), parentEObject.eContents());
		// int cpt = 1;
		// while(!isNameAvailable(parentEObject, childEClass, cpt)) {
		// cpt++;
		// }
		// return childEClass.getName() + cpt;
	}

}
