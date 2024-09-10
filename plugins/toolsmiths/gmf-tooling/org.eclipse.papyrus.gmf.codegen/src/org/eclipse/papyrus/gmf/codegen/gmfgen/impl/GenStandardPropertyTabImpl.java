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
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenStandardPropertyTab;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Standard Property Tab</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class GenStandardPropertyTabImpl extends GenPropertyTabImpl implements GenStandardPropertyTab {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenStandardPropertyTabImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenStandardPropertyTab();
	}

	@Override
	public String getLabel() {
		// kinda hack, but to allow reconcile this value
		// we'd better do not set it in PropertySheetHandler
		if (!"diagram".equals(getID())) {
			return super.getLabel();
		}
		// match original from the runtime, unless overriden by user
		String rv = getLabelGen();
		if (rv == null || rv.trim().length() == 0) {
			rv = "Rulers & Grid";
		}
		return rv;
	}
} //GenStandardPropertyTabImpl
