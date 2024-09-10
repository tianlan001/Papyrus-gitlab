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

package org.eclipse.papyrus.emf.ui.editor.factories;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.EMFEditUIPropertyEditorFactory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.emf.ui.converters.IDisplayConverter;
import org.eclipse.papyrus.emf.ui.messages.Messages;
import org.eclipse.papyrus.emf.ui.providers.labelproviders.DelegatingToEMFLabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * Abstract EditorFactory to use to edit an EReference with a dialog
 */
public abstract class AbstractEStructuralFeatureDialogEditorFactory extends EMFEditUIPropertyEditorFactory {

	/**
	 * the feature for which this editor factory has been created
	 */
	protected final EStructuralFeature editedFeature;

	/**
	 * the label provider to use in the dialog
	 */
	protected ILabelProvider labelProvider;

	/**
	 * Constructor.
	 *
	 * @param propertyEditorFactoryURI
	 */
	public AbstractEStructuralFeatureDialogEditorFactory(final URI propertyEditorFactoryURI, final EStructuralFeature editedFeature) {
		super(propertyEditorFactoryURI);
		this.editedFeature = editedFeature;
	}

	/**
	 * @see org.eclipse.emf.edit.ui.provider.EMFEditUIPropertyEditorFactory#createEditor(java.lang.Object, org.eclipse.emf.edit.provider.IItemPropertyDescriptor, org.eclipse.swt.widgets.Composite)
	 *
	 * @param object
	 * @param propertyDescriptor
	 * @param composite
	 * @return
	 */
	@Override
	public final CellEditor createEditor(final Object object, final IItemPropertyDescriptor itemPropertyDescriptor, final Composite composite) {
		if (!itemPropertyDescriptor.canSetProperty(object)) {
			return null;
		}
		Assert.isTrue(itemPropertyDescriptor.getFeature(object) == this.editedFeature, NLS.bind("The edited feature is {0} instead of {1}.", itemPropertyDescriptor.getFeature(object), this.editedFeature)); //$NON-NLS-1$
		Assert.isTrue(object instanceof EObject, "The edited object is not an EObject"); //$NON-NLS-1$
		final EObject editedEObject = (EObject) object;
		final CustomExtendedDialogCellEditor cellEditor = new CustomExtendedDialogCellEditor(composite, getOrCreateLabelProvider(), itemPropertyDescriptor, editedEObject, this.editedFeature);
		cellEditor.setDialogInput(getDialogInput(editedEObject));
		configureCellEditor(object, cellEditor);
		return cellEditor;
	}


	/**
	 * This method must be overridden to define these fields:
	 * <ul>
	 * <li>dialog title</li>
	 * <li>dialog message</li>
	 * <li>{@link ISelectionStatusValidator}</li>
	 * <li>@link {@link IDisplayConverter} (not mandatory, see {@link CustomExtendedDialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control) documentation}</li>
	 * <li></li>
	 * <ul>
	 *
	 * @param editedObject
	 *            the edited object
	 * @param cellEditor
	 *            the cell editor to configure
	 */
	protected void configureCellEditor(final Object editedObject, final CustomExtendedDialogCellEditor cellEditor) {
		cellEditor.setDialogTitle(getDialogTitle());
		if (this.editedFeature.isMany()) {
			cellEditor.setDialogMessage(NLS.bind(Messages.AbstractEStructuralFeatureDialogEditorFactory_SelectOneOrSeveral, this.editedFeature.getEType().getName()));
		} else {
			cellEditor.setDialogMessage(NLS.bind(Messages.AbstractEStructuralFeatureDialogEditorFactory_SelectOne, this.editedFeature.getEType().getName()));
		}
	}

	/**
	 * 
	 * @return
	 *         the dialog title to use
	 */
	protected String getDialogTitle() {
		return NLS.bind(Messages.AbstractEStructuralFeatureDialogEditorFactory_PapyrusDialogTitle, this.editedFeature.getEContainingClass().getName(), this.editedFeature.getName());
	}

	/**
	 * @param eobject
	 *            the edited element
	 * @return
	 *         the input to use for the dialog
	 */
	protected abstract Collection<?> getDialogInput(final EObject editedElement);


	/**
	 * Creates a new label provider when required, and return the previously created one if it exits
	 *
	 * @return
	 *         the label provider to use in the dialog
	 */
	public ILabelProvider getOrCreateLabelProvider() {
		if (this.labelProvider == null) {
			this.labelProvider = DelegatingToEMFLabelProvider.INSTANCE;
		}
		return this.labelProvider;
	}

}
