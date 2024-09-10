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

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.emf.ui.converters.IDisplayConverter;
import org.eclipse.papyrus.emf.ui.converters.IdentityDisplayConverter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * Custom DialogCellEditor for PropertyView
 */
public class CustomExtendedDialogCellEditor extends ExtendedDialogCellEditor {

	/**
	 * the content provider used by the dialog
	 */
	private ITreeContentProvider contentProvider;

	/**
	 * the input for the dialog
	 */
	private Collection<?> dialogInput;

	/**
	 * the validator used by the dialog
	 */
	private ISelectionStatusValidator validator;

	/**
	 * the edited property descriptor
	 */
	private IItemPropertyDescriptor itemPropertyDescriptor;

	/**
	 * the display converter used by the dialog
	 */
	private IDisplayConverter displayConverter;

	/**
	 * the title of the dialog
	 */
	private String dialogTitle;

	/**
	 * the message of the dialogF
	 */
	private String dialogMessage;

	/**
	 * the edited object
	 */
	private EObject editedObject;

	/**
	 * the edited feature
	 */
	private EStructuralFeature editedFeature;

	/**
	 * the image to use for the dialog;
	 */
	private Image image;

	/**
	 *
	 * Constructor.
	 *
	 * @param composite
	 *            the parent composite
	 * @param labelProvider
	 *            the label provider to use for the CellEditor
	 * @param itemPropertyDescriptor
	 *            the property descriptor
	 * @param editedObject
	 *            the edited EObject
	 * @param editedFeature
	 *            the edited feature of the EObject
	 */
	public CustomExtendedDialogCellEditor(final Composite composite, final ILabelProvider labelProvider, final IItemPropertyDescriptor itemPropertyDescriptor, final EObject editedObject, final EStructuralFeature editedFeature) {
		super(composite, labelProvider);
		Assert.isNotNull(itemPropertyDescriptor);
		this.itemPropertyDescriptor = itemPropertyDescriptor;
		Assert.isNotNull(editedObject);
		this.editedObject = editedObject;
		Assert.isNotNull(editedFeature);
		this.editedFeature = editedFeature;
	}

	/**
	 *
	 * @param dialogTitle
	 *            the title of the dialog
	 */
	public void setDialogTitle(final String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	/**
	 *
	 * @param dialogMessage
	 *            the message to display in the dialog
	 */
	public void setDialogMessage(final String dialogMessage) {
		this.dialogMessage = dialogMessage;
	}

	/**
	 *
	 * @param contentProvider
	 *            the content provider to use. It can't be <code>null</code>
	 */
	public void setContentProvider(final ITreeContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 *
	 * @param displayConverter
	 *            the display converter to use. It can't be <code>null</code>
	 */
	public void setDisplayConverter(final IDisplayConverter displayConverter) {
		this.displayConverter = displayConverter;
	}

	/**
	 *
	 * @param dialogInput
	 *            the input used by the dialog. It must not be <code>null</code> or empty
	 */
	public void setDialogInput(final Collection<?> dialogInput) {
		this.dialogInput = dialogInput;
	}

	/**
	 *
	 * @param validator
	 *            the validator to use
	 */
	public void setSelectionStatusValidator(final ISelectionStatusValidator validator) {
		this.validator = validator;
	}

	/**
	 *
	 * @param image
	 *            the image to use for the dialog
	 */
	public void setImage(final Image image) {
		this.image = image;
	}

	/**
	 * Before calling this method, several fields must be initialized using these methods:
	 * <ul>
	 * <li>{@link #setContentProvider(ITreeContentProvider)} (mandatory)</li>
	 * <li>{@link #setDialogInput(Collection)} (mandatory)</li>
	 * <li>{@link #setSelectionStatusValidator(ISelectionStatusValidator)} (mandatory)</li>
	 * <li>{@link #setDialogMessage(String)}</li>
	 * <li>{@link #setDialogTitle(String)}</li>
	 * <li>{@link #setDisplayConverter(IDisplayConverter)} (by default we used the {@link IdentityDisplayConverter}</li>
	 * <li></li>
	 * </ul>
	 *
	 * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
	 *
	 * @param cellEditorWindow
	 * @return
	 *         the selected element
	 */
	@Override
	protected Object openDialogBox(final Control cellEditorWindow) {
		checkInput();
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault().getActiveShell(), this.labelProvider, this.contentProvider);
		Object initialSelection = this.displayConverter.semanticToDisplayValue(editedObject.eGet(this.editedFeature), this.editedObject);
		Object[] selectedValue = { initialSelection };

		dialog.setSize(100, 50);
		dialog.setInput(this.dialogInput);
		if (null != this.validator) {
			dialog.setValidator(this.validator);
		}
		dialog.setComparator(new ViewerComparator());
		dialog.setAllowMultiple(isManyFeature());
		dialog.setInitialSelections(selectedValue);
		dialog.setTitle(this.dialogTitle);
		dialog.setMessage(this.dialogMessage);
		if (null != this.image) {
			dialog.setImage(image);
		}
		int userResponse = dialog.open();
		Object toReturn = null;
		if (userResponse == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result == null) {
				toReturn = this.itemPropertyDescriptor.getPropertyValue(null);
			} else {
				if (this.editedFeature.isMany()) {
					toReturn = Arrays.asList(result);
				} else {
					toReturn = result[0];
				}
			}
		} else {
			toReturn = this.itemPropertyDescriptor.getPropertyValue(editedObject);
		}

		toReturn = this.displayConverter.displayToSemanticValue(toReturn, this.editedObject);
		return toReturn;
	}

	/**
	 * This method checks the required field of the class
	 */
	private void checkInput() {
		if (null == this.displayConverter) {
			this.displayConverter = new IdentityDisplayConverter();
		}
		Assert.isNotNull(this.contentProvider, "The ITreeContentProvider has not been set"); //$NON-NLS-1$
		Assert.isNotNull(this.dialogInput, "The dialog input is null"); //$NON-NLS-1$
		Assert.isNotNull(this.validator, "The validator has not been set"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.jface.viewers.CellEditor#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		this.contentProvider = null;
		this.dialogInput.clear();
		this.dialogInput = null;
		this.displayConverter = null;
		this.editedFeature = null;
		this.editedObject = null;
		this.image = null;
		this.itemPropertyDescriptor = null;
		this.validator = null;
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the edited feature is multi-valued
	 */
	private boolean isManyFeature() {
		return this.editedFeature.isMany();
	}


}
