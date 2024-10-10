/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtEObjectCreationPage;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.Messages;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.sirius.properties.common.utils.ContainerUtil;
import org.eclipse.swt.widgets.Composite;

/**
 * This page is used to create a new EObject for the given reference. It
 * overrides the EEF Wizard Page to modify the finish behavior.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class PapyrusEEFExtEObjectCreationPage extends EEFExtEObjectCreationPage {

	/**
	 * The user's choice, can be either Window.CANCEL or Window.OK.
	 */
	private int returnCode = Window.CANCEL;

	/**
	 * The object selected in the combo viewer.
	 */
	private EObject selectedEObject;

	/**
	 * The constructor.
	 * 
	 * @param target
	 *            the owner of the reference
	 * @param eReference
	 *            the reference to manage for which the object
	 *            will be created
	 * @param editingContextAdapter
	 *            the adapter used to modify model elements
	 */
	public PapyrusEEFExtEObjectCreationPage(EObject target, EReference eReference,
			EditingContextAdapter editingContextAdapter) {
		super(target, eReference, editingContextAdapter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Overridden to display a Properties edition page to edit the new created
	 * object.
	 */
	@Override
	public void performFinish(IProgressMonitor monitor) {
		this.returnCode = Window.OK;
	}

	@Override
	public boolean canFlipToNextPage() {
		return this.getEObject(this.eClassInstanceComboViewer) != null;
	}

	public int getReturnCode() {
		return returnCode;
	}

	@Override
	protected void createReferenceContentPage(Composite parent) {
		this.createEObjectEClassComboViewer(parent);
		initializeComboViewer();
	}

	/**
	 * Initialize comboViewer content.
	 */
	protected void initializeComboViewer() {
		ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		this.eClassInstanceComboViewer.setLabelProvider(new AdapterFactoryLabelProvider(composedAdapterFactory) {
			@Override
			public String getText(Object object) {
				String text = null;
				if (object instanceof EClass) {
					text = ((EClass) object).getName();
				} else if (object instanceof EObject) {
					text = ((EObject) object).eClass().getName();
				} else {
					text = super.getText(object);
				}
				return text;
			}
		});
		if (this.eReference.isContainment()) {
			super.initializeContainmentInput(this.target, this.eReference);
		} else {
			List<EClass> values = ContainerUtil.getSubclassesOf(eReference.getEReferenceType(), true);
			this.eClassInstanceComboViewer.setInput(values);
			if (values.size() > 0) {
				this.eClassInstanceComboViewer.setSelection(new StructuredSelection(values.get(0)));
			} else {
				this.eClassInstanceComboViewer.setSelection(new StructuredSelection());
			}
		}
	}

	@Override
	protected void determinePageCompletion() {
		this.setMessage(null);

		boolean isPageComplete = false;
		isPageComplete = this.isCompleteViewer(true, this.eClassInstanceComboViewer,
				Messages.ReferenceCreationWizardPage_missingEClassToCreate);
		this.setPageComplete(isPageComplete);
		this.selectedEObject = this.getEObject(this.eClassInstanceComboViewer);
	}

	/**
	 * Used to get the selected EObject in the combo viewer.
	 * 
	 * @return the selected EObject in the combo viewer
	 */
	public EObject getSelectedEObject() {
		return this.selectedEObject;
	}
}
