/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtEObjectCreationWizard;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.Activator;

/**
 * This Wizard is used to create a new value for the reference. It overrides the
 * EEF Wizard to modify the Wizard Pages and the finish behavior.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class PapyrusEEFExtEObjectCreationWizard extends EEFExtEObjectCreationWizard {

	/**
	 * The page used to create the new EObject.
	 */
	protected PapyrusEEFExtEObjectCreationPage eObjectCreationPage;

	/**
	 * The page used to choose the container of the created object.
	 */
	protected PapyrusEEFExtContainerSelectionPage containerSelectionPage;

	/**
	 * The adapter used to modify model elements.
	 */
	protected EditingContextAdapter editingContextAdapter;

	/**
	 * The owner of the reference.
	 */
	private EObject target;

	/**
	 * The reference to display and manage.
	 */
	private EReference eReference;

	/**
	 * The user's choice, can be either Window.CANCEL or Window.OK.
	 */
	private int returnCode = Window.CANCEL;

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
	public PapyrusEEFExtEObjectCreationWizard(EObject target, EReference eReference,
			EditingContextAdapter editingContextAdapter) {
		super(target, eReference, editingContextAdapter);
		this.target = target;
		this.eReference = eReference;
		this.editingContextAdapter = editingContextAdapter;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		this.eObjectCreationPage = new PapyrusEEFExtEObjectCreationPage(this.target, this.eReference, this.editingContextAdapter);
		this.addPage(this.eObjectCreationPage);
		if (!this.eReference.isContainment()) {
			this.containerSelectionPage = new PapyrusEEFExtContainerSelectionPage(this.target);
			this.addPage(this.containerSelectionPage);
		}
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage != null && nextPage == this.containerSelectionPage && page == eObjectCreationPage) {
			this.containerSelectionPage.reset();
			this.containerSelectionPage.setEClassToCreate(this.getSelectedType());
		}
		return nextPage;
	}

	/**
	 * Get the selected type name (selected in the eObjectCreationPage wizard page) of the new object to create.
	 * 
	 * @return the selected type name (selected in the eObjectCreationPage wizard page) of the new object to create.
	 */
	public String getSelectedTypeName() {
		return getSelectedType().getName();
	}

	public EClass getSelectedType() {
		EClass selectedType = null;
		if (this.eObjectCreationPage != null) {
			EObject eObjectToCreate = this.eObjectCreationPage.getSelectedEObject();
			if (eObjectToCreate instanceof EClass) {
				// case : non containment reference (object selected is EClass)
				selectedType = (EClass) eObjectToCreate;
			} else if (eObjectToCreate instanceof EObject) {
				// case : containment reference (object selected is EObject)
				selectedType = eObjectToCreate.eClass();
			}
		}
		return selectedType;
	}

	/**
	 * Get the selected container (selected in the containerSelectionPage wizard page) of the new object to create.
	 * 
	 * @return the selected container (selected in the containerSelectionPage wizard page) of the new object to create.
	 */
	public EObject getSelectedEContainer() {
		EObject container = null;
		if (this.containerSelectionPage != null) {
			container = this.containerSelectionPage.getEContainer();
		} else {
			container = this.target;
		}
		return container;
	}

	/**
	 * Get the selected feature (selected in the containerSelectionPage wizard page) which will refer the new object to create.
	 * 
	 * @return the selected feature (selected in the containerSelectionPage wizard page) which will refer the new object to create.
	 */
	public EStructuralFeature getSelectedEContainerFeature() {
		EStructuralFeature selectedEContainerFeature = null;
		if (this.containerSelectionPage != null) {
			selectedEContainerFeature = this.containerSelectionPage.getEContainerFeature();
		} else {
			selectedEContainerFeature = this.eReference;
		}
		return selectedEContainerFeature;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Overridden to display a Properties edition page to edit the new created
	 * object.
	 */
	@Override
	public boolean performFinish() {
		boolean finishedProperly = true;
		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				if (eReference.isContainment()) {
					eObjectCreationPage.performFinish(monitor);
				} else {
					containerSelectionPage.performFinish(monitor);
				}
			}
		};
		try {
			this.getContainer().run(false, false, runnableWithProgress);
		} catch (InvocationTargetException | InterruptedException e) {
			finishedProperly = false;
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage());
			Activator.getDefault().getLog().log(status);
		} finally {
			if (eReference.isContainment()) {
				this.returnCode = this.eObjectCreationPage.getReturnCode();
			} else {
				this.returnCode = this.containerSelectionPage.getReturnCode();
			}
		}
		return finishedProperly;
	}

	@Override
	public boolean canFinish() {
		boolean canFinish = this.eObjectCreationPage.isPageComplete();
		if (this.containerSelectionPage != null) {
			canFinish = canFinish && this.containerSelectionPage.isPageComplete();
		}
		return canFinish;
	}

	public int getReturnCode() {
		return this.returnCode;
	}
}
