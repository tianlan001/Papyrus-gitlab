/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.filters.FiltersPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Dialog to select the new kind of {@link Filter} to create
 */
public class NewFilterDialog extends SelectionDialog {

	private List<CommandParameter> newChildDescriptors;
	
	private CommandParameter result;

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 */
	public NewFilterDialog(Shell parentShell, List<CommandParameter> newChildDescriptors) {
		super(parentShell);
		this.newChildDescriptors = newChildDescriptors;
	}
	
	/**
	 * @see org.eclipse.ui.dialogs.SelectionDialog#configureShell(org.eclipse.swt.widgets.Shell)
	 *
	 * @param shell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Create a new filter");
	}
	
	/**
	 * @see org.eclipse.ui.dialogs.SelectionDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getButton(OK).setText("Create");
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite self = (Composite) super.createDialogArea(parent);
		
		Label description = new Label(self, SWT.WRAP);
		description.setText("Select the kind of Filter to create");
		
		ListViewer viewer = new ListViewer(self, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new NewFilterContentProvider());
		viewer.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				CommandParameter cp = (CommandParameter) element;
				EObject value = cp.getEValue();
				return value.eClass().getName();
			};
		});
		viewer.addSelectionChangedListener(event -> {
			result = (CommandParameter)event.getStructuredSelection().getFirstElement();
			updateButtons();
		});
		viewer.setInput(new Object());
		
		GridDataFactory.fillDefaults().grab(true, false).applyTo(description);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getList());
		
		return self;
	}

	private void updateButtons() {
		getButton(OK).setEnabled(result != null);
	}

	private class NewFilterContentProvider implements IStructuredContentProvider {

		/**
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 *
		 * @param inputElement
		 * @return
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			return newChildDescriptors.stream()
					.filter(cp -> cp.getEStructuralFeature() instanceof EReference)
					.filter(cp -> EMFHelper.isSuperType(FiltersPackage.Literals.FILTER, cp.getEReference().getEType()))
					.collect(Collectors.toList()).toArray();
		}

	}

	/**
	 * @return
	 */
	public CommandParameter getNewChild() {
		return result;
	}

}
