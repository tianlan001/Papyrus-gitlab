/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.sections.duplicated;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.emf.ui.editor.factories.AbstractEStructuralFeatureDialogEditorFactory;
import org.eclipse.papyrus.uml.expressions.edit.internal.providers.UMLLabelProvider;
import org.eclipse.papyrus.uml.expressions.edit.internal.utils.ProfileUtils;

/**
 * Abstract dialog editor factory used to select UML Element from a Profile
 */
public abstract class AbstractUMLElementDialogEditorFactory extends AbstractEStructuralFeatureDialogEditorFactory {

	/**
	 * Constructor.
	 *
	 * @param propertyEditorFactoryURI
	 * @param editedFeature
	 */
	public AbstractUMLElementDialogEditorFactory(final URI propertyEditorFactoryURI, final EStructuralFeature editedFeature) {
		super(propertyEditorFactoryURI, editedFeature);
	}

	/**
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.AbstractEStructuralFeatureDialogEditorFactory#getDialogInput(org.eclipse.emf.ecore.EObject)
	 *
	 * @param editedElement
	 * @return
	 */
	@Override
	protected Collection<?> getDialogInput(final EObject editedElement) {
		return ProfileUtils.findAttachedRootProfiles(editedElement);
	}

	/**
	 * Creates a new label provider when required, and return the previously created one if it exits
	 *
	 * @return
	 *         the label provider to use in the dialog
	 */
	@Override
	public ILabelProvider getOrCreateLabelProvider() {
		if (this.labelProvider == null) {
			this.labelProvider = new UMLLabelProvider();
		}
		return this.labelProvider;
	}


}
