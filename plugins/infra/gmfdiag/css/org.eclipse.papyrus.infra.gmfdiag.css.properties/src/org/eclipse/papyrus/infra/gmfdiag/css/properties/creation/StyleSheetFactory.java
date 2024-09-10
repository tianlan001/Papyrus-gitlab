/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.properties.creation;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StylesheetsPackage;
import org.eclipse.papyrus.infra.properties.ui.creation.EcorePropertyEditorFactory;
import org.eclipse.papyrus.infra.widgets.creation.IAtomicOperationExecutor;
import org.eclipse.swt.widgets.Control;

/**
 * A Factory for creating StyleSheets directly in the resource. It is not
 * contained in an EObject.
 *
 * @author Camille Letavernier
 */
public class StyleSheetFactory extends EcorePropertyEditorFactory {

	protected View context;

	public StyleSheetFactory(View context) {
		super(NotationPackage.eINSTANCE.getEObjectListValueStyle_EObjectListValue());
		this.type = StylesheetsPackage.eINSTANCE.getStyleSheet();
		this.context = context;
	}

	@Override
	protected EObject createObjectInDifferentContainer(Control widget) {
		// The EObject is simply created ; it isn't stored anywhere (yet)
		// @see #validateObjects(Collection)
		return simpleCreateObject(widget);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.creation.PropertyEditorFactory#getOperationExecutor(java.lang.Object)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public IAtomicOperationExecutor getOperationExecutor(Object context) {
		if (context instanceof StyleSheet) {
			StyleSheet stylesheet = (StyleSheet) context;
			if (stylesheet.eResource() == null) {
				// Bug 468345: Use the current Notation::view instead, since we're creating the stylesheet in this object's resource
				// Ensure that further edition occurs in a proper nested transaction
				return super.getOperationExecutor(this.context);
			}
		}

		return super.getOperationExecutor(context);
	}

	/**
	 * Return the objects as-is. The factory is not responsible for storing them
	 */
	@Override
	public Collection<Object> validateObjects(Collection<Object> objectsToValidate) {
		return objectsToValidate;
	}
}
