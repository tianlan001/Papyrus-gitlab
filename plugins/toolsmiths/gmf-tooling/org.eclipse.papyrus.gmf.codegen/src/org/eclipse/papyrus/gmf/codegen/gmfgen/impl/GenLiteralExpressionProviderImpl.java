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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLanguage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLiteralExpressionProvider;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Literal Expression Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class GenLiteralExpressionProviderImpl extends GenExpressionProviderBaseImpl implements GenLiteralExpressionProvider {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenLiteralExpressionProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenLiteralExpressionProvider();
	}

	@Override
	public GenLanguage getLanguage() {
		return GenLanguage.LITERAL_LITERAL;
	}
} //GenLiteralExpressionProviderImpl
