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

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.eef.common.ui.api.EEFWidgetFactory;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtReferenceUIPlugin;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.properties.ui.providers.FeatureContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFGraphicalContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.infra.ui.emf.utils.ProviderHelper;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLContainerContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * This page is used to choose the container and reference which will contain
 * the object to create.
 * This page is displayed when user click on "Create" button from a Papyrus reference widget on non-containment reference.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class PapyrusEEFExtContainerSelectionPage extends WizardPage {

	/**
	 * The empty string.
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * The label for the reference Composite.
	 */
	private static final String REFERENCE_LABEL = "Reference: "; //$NON-NLS-1$

	/**
	 * The label for the container Composite.
	 */
	private static final String CONTAINER_LABEL = "Container: "; //$NON-NLS-1$

	/**
	 * The name of this wizard page.
	 */
	private static final String PAGE_NAME = "Container and Reference Selection"; //$NON-NLS-1$

	/**
	 * The title of this wizard page.
	 */
	private static final String PAGE_TITLE = "Choose the container and reference"; //$NON-NLS-1$

	/**
	 * The description of this wizard page.
	 */
	private static final String PAGE_DESCRIPTION = "Select the container and reference which will contain the object to create"; //$NON-NLS-1$

	/**
	 * The title of the "Browse container" dialog.
	 */
	private static final String BROWSE_CONTAINER_DIALOG_TITLE = "Browse container"; //$NON-NLS-1$

	/**
	 * The title of the "Browse reference" dialog.
	 */
	private static final String BROWSE_REFERENCE_DIALOG_TITLE = "Browse reference"; //$NON-NLS-1$

	/**
	 * The owner of the reference.
	 */
	private EObject target;

	/**
	 * The user's choice, can be either Window.CANCEL or Window.OK.
	 */
	private int returnCode = Window.CANCEL;

	/**
	 * The widget factory used to ease the creation of some SWT widgets.
	 */
	private EEFWidgetFactory widgetFactory;

	/**
	 * The {@link EClass} selected in the previous wizard page which will be created.
	 */
	private EClass eClassToCreate;

	/**
	 * The container chosen by the user which will contain the object to create.
	 */
	private EObject eContainer;

	/**
	 * The feature of the container on which the object will be added.
	 */
	private EStructuralFeature eContainerFeature;

	/**
	 * The container composite used to display and select the container chosen by the user which will contain the object to create.
	 */
	private LineComposite containerComposite;

	/**
	 * The reference composite used to display and select the feature of the container on which the object will be added.
	 */
	private LineComposite referenceComposite;

	/**
	 * The container label provider used to display label of container with image and container name.
	 */
	private UMLFilteredLabelProvider containerLabelProvider;

	/**
	 * The reference label provider used to display name of the eContainerFeature.
	 */
	private EMFLabelProvider referenceLabelProvider;

	/**
	 * The constructor.
	 * 
	 * @param target
	 *            the owner of the reference
	 */
	public PapyrusEEFExtContainerSelectionPage(EObject target) {
		super(PAGE_NAME);
		this.target = target;
		this.widgetFactory = new EEFWidgetFactory();
		this.containerLabelProvider = new UMLFilteredLabelProvider();
		this.referenceLabelProvider = new EMFLabelProvider();

		this.setTitle(PAGE_TITLE);
		this.setDescription(PAGE_DESCRIPTION);
	}

	@Override
	public void createControl(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setControl(control);

		GridLayout gridLayout = new GridLayout(1, false);
		control.setLayout(gridLayout);

		createContainerComposite(control);
		createReferenceComposite(control);
	}

	/**
	 * Create Container composite to display and select the container of the object to create.
	 * 
	 * @param parentControl
	 *            the parent control which contain the reference composite
	 */
	private void createContainerComposite(Composite parentControl) {
		containerComposite = createLine(parentControl, CONTAINER_LABEL);

		containerComposite.removeButton.setEnabled(false);

		containerComposite.browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseContainerDialog();
				if (eContainer != null) {
					// Update reference composite
					updateEContainerFeature();

					updateMainComposite();
				}
				setPageComplete(isPageComplete());
			}
		});
		containerComposite.removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eContainer = null;
				eContainerFeature = null;
				updateMainComposite();
				setPageComplete(isPageComplete());
			}
		});
	}

	/**
	 * Create Reference composite to display and select the reference used for the object to create.
	 * 
	 * @param parentControl
	 *            the parent control which contain the reference composite
	 */
	private void createReferenceComposite(Composite parentControl) {
		referenceComposite = createLine(parentControl, REFERENCE_LABEL);

		referenceComposite.browseButton.setEnabled(false);
		referenceComposite.removeButton.setEnabled(false);

		referenceComposite.browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseReferenceDialog();
				updateMainComposite();
				setPageComplete(isPageComplete());
			}
		});
		referenceComposite.removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eContainerFeature = null;
				updateMainComposite();
				setPageComplete(isPageComplete());
			}
		});
	}

	/**
	 * Update the parent composite which contains the container and reference composites.
	 */
	private void updateMainComposite() {
		// update Container Composite
		if (eContainer == null) {
			containerComposite.cLabel.setText(EMPTY_STRING);
			containerComposite.cLabel.setImage(null);
			containerComposite.removeButton.setEnabled(false);
		} else {
			containerComposite.cLabel.setImage(containerLabelProvider.getImage(eContainer));
			containerComposite.cLabel.setText(containerLabelProvider.getText(eContainer));
			containerComposite.removeButton.setEnabled(true);
		}
		// update reference composite
		if (eContainerFeature == null) {
			referenceComposite.cLabel.setText(EMPTY_STRING);
			referenceComposite.removeButton.setEnabled(false);
		} else {
			referenceComposite.cLabel.setText(referenceLabelProvider.getText(eContainerFeature));
			referenceComposite.removeButton.setEnabled(true);
		}
	}

	/**
	 * Update eContainerFeature value according to the eContainer chosen.
	 */
	private void updateEContainerFeature() {
		FeatureContentProvider referenceContentProvider = new FeatureContentProvider(eClassToCreate);
		referenceContentProvider.inputChanged(null, null, eContainer);
		if (referenceContentProvider.getElements().length == 0) {
			eContainerFeature = null;
		} else if (referenceContentProvider.getElements().length == 1) {
			eContainerFeature = (EStructuralFeature) referenceContentProvider.getElements()[0];
		} else {
			if (!Arrays.asList(referenceContentProvider.getElements()).contains(eContainerFeature)) {
				eContainerFeature = null;
			}
		}
		// disable button if only one or none reference is found.
		referenceComposite.setReadOnly(referenceContentProvider.getElements().length < 2);
	}

	/**
	 * Invokes the Browse dialog to choose the container of the object to create.
	 */
	protected void browseContainerDialog() {
		TreeSelectorDialog dialog = new TreeSelectorDialog(Display.getCurrent().getActiveShell());

		// Set Label Provider
		dialog.setLabelProvider(containerLabelProvider);

		// Set Content Provider
		UMLContainerContentProvider containerContentProvider = new UMLContainerContentProvider(this.target, eClassToCreate);
		EMFGraphicalContentProvider graphicalContentProvider = ProviderHelper.encapsulateProvider(containerContentProvider,
				this.target.eResource().getResourceSet(), null);
		dialog.setContentProvider(graphicalContentProvider);

		dialog.setInitialElementSelections(Collections.singletonList(null));
		dialog.setTitle(BROWSE_CONTAINER_DIALOG_TITLE);
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1 && result[0] instanceof EObject) {
				this.eContainer = (EObject) result[0];
			}
		}
	}

	/**
	 * Invokes the Browse dialog to choose the reference to use for the object to create.
	 */
	protected void browseReferenceDialog() {
		TreeSelectorDialog dialog = new TreeSelectorDialog(Display.getCurrent().getActiveShell());

		// Set Label Provider
		dialog.setLabelProvider(referenceLabelProvider);
		dialog.setInput(eContainer);
		FeatureContentProvider referenceContentProvider = new FeatureContentProvider((EClass) eClassToCreate);
		referenceContentProvider.inputChanged(null, null, eContainer);
		dialog.setContentProvider(new EncapsulatedContentProvider(referenceContentProvider));

		dialog.setInitialElementSelections(Collections.singletonList(null));
		dialog.setTitle(BROWSE_REFERENCE_DIALOG_TITLE);
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1 && result[0] instanceof EStructuralFeature) {
				this.eContainerFeature = (EStructuralFeature) result[0];
			}
		}
	}

	/**
	 * When the user click on the Finish button, the object is created, added to its
	 * container and also to the eReference of the target.
	 * 
	 * @param monitor
	 *            the {@link IProgressMonitor}
	 */
	public void performFinish(IProgressMonitor monitor) {
		this.returnCode = Window.OK;
	}

	/**
	 * Creates a {@link LineComposite} to fill the content page.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param labelValue
	 *            the label of the composite
	 * @return the created composite
	 */
	protected LineComposite createLine(Composite parent, String labelValue) {
		LineComposite line = new LineComposite(parent, SWT.NONE);
		line.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		GridLayout gridLayout = new GridLayout(4, false);
		line.setLayout(gridLayout);

		Label label = this.widgetFactory.createLabel(line, labelValue, SWT.NONE);

		CLabel cLabel = this.widgetFactory.createCLabel(line, EMPTY_STRING, SWT.BORDER);
		GridData cLabelGridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		cLabel.setLayoutData(cLabelGridData);

		Button browseButton = new Button(line, SWT.NONE);
		Image browseImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.BROWSE_ICON_PATH));
		browseButton.setImage(browseImage);

		Button removeButton = new Button(line, SWT.NONE);
		Image removeImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.REMOVE_ICON_PATH));
		removeButton.setImage(removeImage);

		line.label = label;
		line.cLabel = cLabel;
		line.browseButton = browseButton;
		line.removeButton = removeButton;
		return line;
	}

	/**
	 * Get the return code.
	 * 
	 * @return the code to indicate if user click on "OK" or "Cancel".
	 */
	public int getReturnCode() {
		return returnCode;
	}

	/**
	 * Set the {@link EClass} selected in the previous page.
	 * 
	 * @param selectedEClass
	 *            the {@link EClass} selected to create
	 */
	public void setEClassToCreate(EClass selectedEClass) {
		this.eClassToCreate = selectedEClass;
	}

	/**
	 * Get the EClass of the object to create.
	 * 
	 * @return the eClassToCreate.
	 */
	public EClass getEClassToCreate() {
		return eClassToCreate;
	}

	/**
	 * Get the container of the object to create.
	 * 
	 * @return the eContainer
	 */
	public EObject getEContainer() {
		return eContainer;
	}

	/**
	 * Get the feature containment used to refers the new object.
	 * 
	 * @return the eContainerFeature.
	 */
	public EStructuralFeature getEContainerFeature() {
		return eContainerFeature;
	}

	@Override
	public boolean isPageComplete() {
		return this.eContainer != null && this.eContainerFeature != null;
	}

	/**
	 * Reset container values.
	 */
	public void reset() {
		this.eContainer = null;
		this.eContainerFeature = null;
		updateMainComposite();
	}

	/**
	 * This inner class is used to encapsulate the 4 controls available for each
	 * line.
	 * 
	 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
	 *
	 */
	protected class LineComposite extends Composite {

		/**
		 * The label of the line.
		 */
		protected Label label;

		/**
		 * The label of the chosen object.
		 */
		protected CLabel cLabel;

		/**
		 * The button which allows the user to choose the object to use.
		 */
		protected Button browseButton;

		/**
		 * The button to remove the object chosen.
		 */
		protected Button removeButton;

		/**
		 * Constructor.
		 * 
		 * @param parent
		 *            the parent composite
		 * @param style
		 *            the SWT style to apply
		 */
		LineComposite(Composite parent, int style) {
			super(parent, style);
		}

		/**
		 * Set the composite line to read only by disable buttons.
		 * 
		 * @param isReadOnly
		 *            <code>true</code> if the line composite should be set to read only, <code>false</code> other wise.
		 */
		public void setReadOnly(boolean isReadOnly) {
			browseButton.setEnabled(!isReadOnly);
			removeButton.setEnabled(!isReadOnly);
		}
	}
}