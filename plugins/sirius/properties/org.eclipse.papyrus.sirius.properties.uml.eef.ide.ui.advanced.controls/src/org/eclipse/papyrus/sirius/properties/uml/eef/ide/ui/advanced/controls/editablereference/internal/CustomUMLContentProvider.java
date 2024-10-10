/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.editablereference.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.widgets.editors.AbstractEditor;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;

/**
 * This custom {@link UMLContentProvider} avoid to check elementType APIs after user click on OK button in the Model selection popup.
 * This popup is display when user click on Browse button of the reference widget.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 *
 */
public class CustomUMLContentProvider extends UMLContentProvider {

	/**
	 * Constructor.
	 *
	 * @param target
	 *            The edited EObject
	 * @param feature
	 *            The edited EStructuralFeature
	 */
	public CustomUMLContentProvider(EObject target, EReference eReference) {
		super(target, eReference);
	}

	@Override
	public void commit(AbstractEditor editor) {
		// nothing to do
	}

}
