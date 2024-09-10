/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.editor;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EObjectStructuredValueFactory;
import org.eclipse.papyrus.infra.services.edit.ui.databinding.PapyrusObservableValue;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.infra.tools.databinding.DelegatingObservable;
import org.eclipse.papyrus.infra.ui.emf.dialog.EObjectTreeReferenceValueEditor;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Dialog to display a EObjectTreeReferenceValueEditor
 * @since 3.0
 */
public class EObjectTreeDialog extends SelectionDialog {

	/**
	 * EObject to edit.
	 */
	protected EObject editedObject;

	/**
	 * Feature of the EObject to edit.
	 */
	protected EStructuralFeature feature;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parentShell
	 *            The parent shell.
	 * @param editedObject
	 *            The object to edit.
	 * @param feature
	 *            The structural feature.
	 */
	public EObjectTreeDialog(Shell parentShell, EObject editedObject, EStructuralFeature feature, String title) {
		super(parentShell);
		setTitle(title);
		setHelpAvailable(false);
		this.editedObject = editedObject;
		this.feature = feature;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		EObjectTreeReferenceValueEditor treeReferenceValueEditor = new EObjectTreeReferenceValueEditor(container, SWT.NONE);

		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(editedObject);

		PapyrusObservableValue papyrusObservableValue = new PapyrusObservableValue(editedObject, feature, editingDomain, GMFtoEMFCommandWrapper::wrap);

		IObservableValue wrap = (IObservableValue) DelegatingObservable.wrap(papyrusObservableValue);

		treeReferenceValueEditor.setModelObservable(wrap);
		treeReferenceValueEditor.setLabelProvider(getLabelProviderForEObject(editedObject));
		treeReferenceValueEditor.setProvidersTreeViewer();

		if (feature instanceof EReference) {
			treeReferenceValueEditor.setValueFactory(new EObjectStructuredValueFactory((EReference) feature));
		}

		treeReferenceValueEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		return container;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.dialogs.SelectionDialog#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(final Shell shell) {
		super.configureShell(shell);
		shell.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/papyrus.png")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(800, 600);
	}

	/**
	 * Returns the ILabelProvider for an EObject.
	 * 
	 * @param eObject
	 *            The object which one search the label provider.
	 * @return The ILabelProvider for an EObject.
	 */
	private ILabelProvider getLabelProviderForEObject(final EObject eObject) {
		LabelProviderService labelProviderService = new LabelProviderServiceImpl();
		try {
			labelProviderService.startService();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}

		return labelProviderService.getLabelProvider(eObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.dialogs.SelectionDialog#getResult()
	 */
	@Override
	public Object[] getResult() {
		// Return the editedObject for the result
		Object[] objects = new Object[1];
		objects[0] = editedObject;
		return objects;
	}
}
