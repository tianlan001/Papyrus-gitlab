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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.InitDiagramAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Init Diagram Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class InitDiagramActionImpl extends GenActionImpl implements InitDiagramAction {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitDiagramActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getInitDiagramAction();
	}

	public String getQualifiedClassName() {
		if (GenCommonBaseImpl.isEmpty(getQualifiedClassNameGen())) {
			// FIXME > TEMP CODE
			if (getOwner() != null && getOwner().getEditorGen() != null && getOwner().getEditorGen().getDiagram() != null) {
				GenDiagram gd = getOwner().getEditorGen().getDiagram();
				if (gd.eIsSet(GMFGenPackage.eINSTANCE.getEditorCandies_InitDiagramFileActionClassName())) {
					return gd.getInitDiagramFileActionClassName();
				}
			}
			// FIXME < TEMP CODE
			String className = "InitDiagramAction";
			if (getOwner() != null && getOwner().getEditorGen() != null && getOwner().getEditorGen().getEditor() != null) {
				final String packageName = getOwner().getEditorGen().getEditor().getPackageName();
				if (GenCommonBaseImpl.isEmpty(packageName)) {
					return className;
				}
				return packageName + '.' + className;
			}
			return className;
		}
		return getQualifiedClassNameGen();
	}

	public String getName() {
		if (GenCommonBaseImpl.isEmpty(getNameGen())) {
			String fe = (getOwner() != null && getOwner().getEditorGen() != null) ? getOwner().getEditorGen().getDiagramFileExtension() : null;
			return String.format("Initialize %s diagram file", fe);
		}
		return getNameGen();
	}
} //InitDiagramActionImpl
