/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.service.types.internal.ui.dialogs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.uml.service.types.internal.ui.Activator;
import org.eclipse.papyrus.uml.service.types.internal.ui.messages.Messages;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Association;

/**
 * This class can be launch is order to open a dialog used to choose an association
 */
public class AssociationSelectionDialog extends AbstractAssociationSelectionDialog {

	private static final String UNTYPED = Messages.AssociationSelectionDialog_0;

	/** The selected association. */
	protected Association selectedAssociation;

	/** The common associations. */
	protected Set<Association> commonAssociations;

	private ServicesRegistry serviceRegistry;

	private boolean isCanceled = true;


	/**
	 * Instantiates a new association selection dialog.
	 *
	 * @param parent
	 *            the parent shell
	 * @param style
	 *            the style
	 * @param commonAssociations
	 *            list of assocation in which we would like to llok for
	 */
	public AssociationSelectionDialog(Shell parent, int style, Set<Association> commonAssociations, ServicesRegistry serviceRegistry) {
		super(parent, style);
		this.commonAssociations = commonAssociations == null ? new HashSet<Association>() : commonAssociations;
		this.selectedAssociation = null;
		this.serviceRegistry = serviceRegistry;
	}

	private IBaseLabelProvider getLabelProvider() {
		try {
			return serviceRegistry.getService(LabelProviderService.class).getLabelProvider();
		} catch (ServiceException e) {
			Activator.log.error(e);
			return null;
		}
	}

	/**
	 * @see org.eclipse.papyrus.uml.service.types.ui.dialogs.clazz.custom.ui.AbstractAssociationSelectionDialog#createContents()
	 *
	 */
	@Override
	protected void createContents() {
		super.createContents();
		final IStructuredContentProvider associationContentProvider = new IStructuredContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				List<Object> result = new ArrayList<Object>();
				result.add(UNTYPED);
				result.addAll(commonAssociations);
				return result.toArray();
			}
		};
		final TableViewer tableViewer = new TableViewer(table);
		tableViewer.setLabelProvider(getLabelProvider());
		tableViewer.setContentProvider(associationContentProvider);
		tableViewer.setInput(commonAssociations);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				btnOk.setEnabled(true);

			}
		});
		btnOk.setEnabled(false);
		btnOk.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				ISelection selection = tableViewer.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object selectedItem = ((IStructuredSelection) selection).getFirstElement();
					selectedAssociation = selectedItem instanceof Association ? (Association) selectedItem : null;
					isCanceled = false;
					shlAssociationselection.close();
				}
			}

		});
		btnCancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				selectedAssociation = null;
				shlAssociationselection.close();

			}

		});
		btnCancel.setVisible(true);
	}

	/**
	 * Gets the selected association.
	 *
	 * @return the selected association
	 */
	public Association getSelectedAssociation() {
		return selectedAssociation;
	}

	/**
	 * @return if canceled button was clicked
	 */
	public boolean isCanceled() {
		return isCanceled;
	}
}
