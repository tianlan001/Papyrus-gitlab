/**
 * Copyright (c) 2012, 2017 CEA LIST.
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
 * 	Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 * 	Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr (umlification of the ecore model)
 */
package org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StylesheetsFactoryImpl extends EFactoryImpl implements StylesheetsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StylesheetsFactory init() {
		try {
			StylesheetsFactory theStylesheetsFactory = (StylesheetsFactory)EPackage.Registry.INSTANCE.getEFactory(StylesheetsPackage.eNS_URI);
			if (theStylesheetsFactory != null) {
				return theStylesheetsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StylesheetsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StylesheetsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case StylesheetsPackage.MODEL_STYLE_SHEETS: return createModelStyleSheets();
			case StylesheetsPackage.STYLE_SHEET_REFERENCE: return createStyleSheetReference();
			case StylesheetsPackage.WORKSPACE_THEMES: return createWorkspaceThemes();
			case StylesheetsPackage.THEME: return createTheme();
			case StylesheetsPackage.EMBEDDED_STYLE_SHEET: return createEmbeddedStyleSheet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelStyleSheets createModelStyleSheets() {
		ModelStyleSheetsImpl modelStyleSheets = new ModelStyleSheetsImpl();
		return modelStyleSheets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StyleSheetReference createStyleSheetReference() {
		StyleSheetReferenceImpl styleSheetReference = new StyleSheetReferenceImpl();
		return styleSheetReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WorkspaceThemes createWorkspaceThemes() {
		WorkspaceThemesImpl workspaceThemes = new WorkspaceThemesImpl();
		return workspaceThemes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Theme createTheme() {
		ThemeImpl theme = new ThemeImpl();
		return theme;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmbeddedStyleSheet createEmbeddedStyleSheet() {
		EmbeddedStyleSheetImpl embeddedStyleSheet = new EmbeddedStyleSheetImpl();
		return embeddedStyleSheet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StylesheetsPackage getStylesheetsPackage() {
		return (StylesheetsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StylesheetsPackage getPackage() {
		return StylesheetsPackage.eINSTANCE;
	}

} //StylesheetsFactoryImpl
