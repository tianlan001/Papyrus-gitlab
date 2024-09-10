/******************************************************************************
 * Copyright (c) 2006, 2020 Eclipse.org, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.ui;

import java.util.Observable;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author dstadnik
 */
public class CreateNewModelExtension extends Observable implements ModelSelectionPageExtension {

	private final ModelSelectionPage page;

	private boolean createNewModel;

	public CreateNewModelExtension(ModelSelectionPage page) {
		this.page = page;
	}

	public final boolean shouldCreateNewModel() {
		return createNewModel;
	}

	protected void setCreateNewModel(boolean value) {
		if (createNewModel == value) {
			return;
		}
		this.createNewModel = value;
		page.setReadOnly(value);
		page.validatePage();
		setChanged();
		notifyObservers(Boolean.valueOf(createNewModel));
	}

	protected String getLabelText() {
		return Messages.CreateNewModelExtensionCreateNewModel;
	}

	public void createControl(Composite parent) {
		final Button btn = new Button(parent, SWT.CHECK);
		btn.setText(getLabelText());
		btn.setSelection(createNewModel);
		btn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				setCreateNewModel(btn.getSelection());
			}
		});
	}

	public void setResource(Resource resource) {
	}

	public void validatePage() {
		if (!page.isModelRequired()) {
			return;
		}
		if (createNewModel) {
			page.setPageComplete(true);
		} else {
			page.setPageComplete(page.getResource() != null);
		}
	}
}
