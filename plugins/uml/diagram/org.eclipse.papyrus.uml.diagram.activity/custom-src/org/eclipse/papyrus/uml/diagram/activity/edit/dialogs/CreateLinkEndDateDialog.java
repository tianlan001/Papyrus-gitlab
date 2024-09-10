/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
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
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * DialogBox in order to link association end to a LinkEndData
 *
 * @since 3.5
 */
public class CreateLinkEndDateDialog extends FormDialog {

	private Association association;
	private List<Button> associationEnds = new ArrayList<>();
	private List<Property> selectedProperties = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 * @param shell
	 */
	public CreateLinkEndDateDialog(Shell shell, Association association) {
		super(shell);
		this.association = association;
	}

	/**
	 * @see org.eclipse.ui.forms.FormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 *
	 * @param mform
	 */
	@Override
	protected void createFormContent(IManagedForm mForm) {
		mForm.getForm().setText("Select association End");
		ScrolledForm scrolledForm = mForm.getForm();
		FormToolkit toolkit = mForm.getToolkit();
		Composite parent = scrolledForm.getBody();
		parent.setLayout(new GridLayout());
		createAssociationSection(parent, toolkit);
		scrolledForm.reflow(true);
	}

	/**
	 * @param parent
	 * @param toolkit
	 */
	private void createAssociationSection(Composite parent, FormToolkit toolkit) {
		// create the section
		String lSectionTitle = "Association End";
		Section lSection = toolkit.createSection(parent, ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);
		lSection.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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

		// create content
		associationEnds.clear();
		Iterator<Property> memberEndsIterator = association.getMemberEnds().iterator();
		Image image = getTypeImage();
		while (memberEndsIterator.hasNext()) {
			Property memberEnd = memberEndsIterator.next();
			Button associationEndButton = toolkit.createButton(lBody, memberEnd.getQualifiedName() != null ? memberEnd.getQualifiedName() : memberEnd.getName(), SWT.CHECK);
			associationEndButton.setImage(image);
			associationEnds.add(associationEndButton);
		}
		lInsideScrolledForm.reflow(true);
		lSection.setClient(lInsideScrolledForm);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 *
	 */
	@Override
	protected void okPressed() {
		selectedProperties.clear();
		for (int i = 0; i < associationEnds.size(); i++) {
			Button memberEndbutton = associationEnds.get(i);
			if (memberEndbutton.getSelection()) {
				selectedProperties.add(association.getMemberEnds().get(i));
			}
		}
		super.okPressed();
	}

	private Image getTypeImage() {
		return UMLElementTypes.getImage(UMLPackage.eINSTANCE.getAssociation_MemberEnd());
	}

	/**
	 * @return the selectedProperties
	 */
	public List<Property> getSelectedProperties() {
		return selectedProperties;
	}

}
