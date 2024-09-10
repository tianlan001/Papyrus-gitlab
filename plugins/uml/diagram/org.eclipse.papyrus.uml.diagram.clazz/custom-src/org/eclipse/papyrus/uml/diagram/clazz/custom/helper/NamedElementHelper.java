/*****************************************************************************
 * Copyright (c) 2008, 2014 CEA LIST and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 440263
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.helper;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.Element;

/**
 * This singleton is used to find a new name of element
 *
 *
 * @deprecated Use the {@link NamedElementUtil} API, instead.
 */
@Deprecated // since 1.2.0
public class NamedElementHelper {

	public static NamedElementHelper EINSTANCE = new NamedElementHelper();

	private String baseString = "default"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 */
	public String getBaseString() {
		return baseString;
	}

	/**
	 * Generic method that returns a new unique name within a namespace.
	 *
	 * @param umlParent
	 *            the parent of the element to create
	 *
	 * @return a distinguisable name within the namespace of the umlParent
	 */
	public String getNewUMLElementName(Element umlParent, EClass eclass) {
		this.setBaseString(eclass.getName());
		return NamedElementUtil.getDefaultNameWithIncrementFromBase(getBaseString(), umlParent.eContents());
	}

	/**
	 * set the base string for the name
	 *
	 * @param baseString
	 *            a string that is the prefix
	 */
	public void setBaseString(String baseString) {
		this.baseString = baseString;
	}
}
