/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * 
 * This abstract class is used to create dialog for asking action to the model designer
 *
 * --------------------------------------------
 * Title
 * --------------------------------------------
 * Description Section (text)
 * --------------------------------------------
 * Extra section (define by sub classes)
 * --------------------------------------------
 * ButtonBar (Cancel | OK)
 * --------------------------------------------
 */
abstract public class AbstractProfileMigrationDialog extends FormDialog {

	private String title;

	private String prefConst;

	private Button checkBox;

	/**
	 * Constructor
	 * 
	 * @param shell
	 *            the active shell
	 * @param title
	 *            the title of the dialog
	 * @param description
	 *            the description of the problem
	 * @param prefConst
	 *            the preference constant for the "do not show the dialog again" checkbox
	 */
	public AbstractProfileMigrationDialog(Shell shell, String title, String prefConst) {
		super(shell);
		this.title = title;
		this.prefConst = prefConst;
	}

	/**
	 * @see org.eclipse.ui.forms.FormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm mform) {
		mform.getForm().setText(title);
		ScrolledForm scrolledForm = mform.getForm();
		FormToolkit toolkit = mform.getToolkit();
		Composite parent = scrolledForm.getBody();
		parent.setLayout(new GridLayout());
		createDescriptionSections(scrolledForm.getBody(), toolkit);
		createMigrationActionSections(scrolledForm.getBody(), toolkit);
		dontShowItAgainCheckbox(scrolledForm.getBody(), toolkit);
		hookListeners();
	}

	/**
	 * Create the description section
	 *
	 * @param body
	 *            the section's parent widget
	 * @param toolkit
	 *            the form toolkit
	 */
	protected void createDescriptionSections(Composite body, FormToolkit toolkit) {
		String lSectionTitle = getDecriptionSectionTitle();
		Section lSection = toolkit.createSection(body, ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);
		lSection.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		if (lSectionTitle != null) {
			lSection.setText(lSectionTitle);
		}
		ScrolledForm lInsideScrolledForm = toolkit.createScrolledForm(lSection);
		lInsideScrolledForm.setExpandHorizontal(true);
		lInsideScrolledForm.setExpandVertical(true);
		Composite lBody = lInsideScrolledForm.getBody();
		GridLayout lLayout = new GridLayout();
		lLayout.numColumns = 1;
		lBody.setLayout(lLayout);
		if (getDecription() != null) {
			toolkit.createLabel(lBody, getDecription(), SWT.WRAP);
		}
		lSection.setClient(lInsideScrolledForm);
	}

	/**
	 * Get the description section title
	 * 
	 * @return the description section title
	 */
	protected String getDecriptionSectionTitle() {
		return Messages.AbstractProfileMigrationDialog_description;
	}

	/**
	 * Get the description
	 * 
	 * @return the description
	 */
	protected abstract String getDecription();

	/**
	 * Create the migration action section
	 *
	 * @param body
	 *            the section's parent widget
	 * @param toolkit
	 *            the form toolkit
	 */
	protected void createMigrationActionSections(Composite body, FormToolkit toolkit) {
		String lSectionTitle = getMigrationActionSectionTitle();
		Section lSection = toolkit.createSection(body, ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);
		lSection.setLayoutData(new GridData(GridData.FILL_BOTH));
		if (lSectionTitle != null) {
			lSection.setText(lSectionTitle);
		}
		ScrolledForm lInsideScrolledForm = toolkit.createScrolledForm(lSection);
		lInsideScrolledForm.setExpandHorizontal(true);
		lInsideScrolledForm.setExpandVertical(true);
		Composite lBody = lInsideScrolledForm.getBody();
		FillLayout lLayout = new FillLayout();
		lLayout.type = SWT.HORIZONTAL;
		lBody.setLayout(lLayout);
		getMigrationActionSectionContent(lBody, toolkit);
		lInsideScrolledForm.reflow(true);
		lSection.setClient(lInsideScrolledForm);
	}

	/**
	 * Get the migration action section title
	 * 
	 * @return the migration action section title
	 */
	protected String getMigrationActionSectionTitle() {
		return Messages.AbstractProfileMigrationDialog_MigrateAction;
	}

	/**
	 * Create the content of the migration action section
	 * 
	 * @param body
	 *            the section's parent widget
	 * @param toolkit
	 *            the form toolkit
	 */
	protected abstract void getMigrationActionSectionContent(Composite body, FormToolkit toolkit);

	/**
	 * Create a checkbox for not displaying the popup again
	 * 
	 * @param body
	 * @param toolkit
	 * @return the checkbox
	 */
	protected Control dontShowItAgainCheckbox(Composite body, FormToolkit toolkit) {
		checkBox = toolkit.createButton(body, Messages.AbstractProfileMigrationDialog_DoNotShowItAgain, SWT.CHECK | SWT.LEFT);
		return checkBox;
	}

	/**
	 * Add listeners to widgets
	 */
	private void hookListeners() {
		SelectionListener selectCreateListener = new SelectionAdapter() {

			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.getSource() instanceof Button) {
					boolean doNotShow = ((Button) e.getSource()).getSelection();
					final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
					prefStore.putValue(prefConst, Boolean.toString(!doNotShow));
				}
			}
		};
		checkBox.addSelectionListener(selectCreateListener);
	}
}
