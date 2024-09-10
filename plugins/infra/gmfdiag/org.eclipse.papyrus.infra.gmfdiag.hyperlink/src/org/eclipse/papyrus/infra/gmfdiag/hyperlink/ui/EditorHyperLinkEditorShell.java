/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 431397
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink.ui;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.helper.EditorHyperLinkHelper;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.messages.Messages;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.object.HyperLinkEditor;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.ui.AbstractEditHyperlinkShell;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistry;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * This shell is used to find the editors
 * 
 * @since 2.0
 *
 */
public class EditorHyperLinkEditorShell extends AbstractEditHyperlinkShell {

	/** The usedefault tooltip. */
	protected boolean usedefaultTooltip = true;

	/** The hyper link diagram. */
	protected HyperLinkEditor hyperLinkEditor;

	/** The editor registry. */
	private IPageIconsRegistry editorRegistry;

	/** The amodel. */
	protected final EObject amodel;

	/**
	 * Instantiates a new editor hyperlink diagram shell.
	 *
	 * @param editorFactoryRegistry
	 *            the editor factory registry
	 * @param model
	 *            the model
	 */
	public EditorHyperLinkEditorShell(Shell parentShell, IPageIconsRegistry editorFactoryRegistry, EObject model) {
		super(parentShell, true);

		this.amodel = model;
		this.editorRegistry = editorFactoryRegistry;
	}

	@Override
	protected void contentsCreated() {
		getObjectLabel().setText(Messages.EditorHyperLinkEditorShell_View); // TODO : where is it used?

		updateFields();

		// intialize "use default" check box
		getUseDefaultCheckBox().setSelection(usedefaultTooltip);
		getObjectLabelText().setEditable(false);
		if (usedefaultTooltip) {
			getTooltipInputText().setEditable(false);
			getTooltipInputText().setText(getObjectLabelText().getText());
		}
	}

	private void updateFields() {
		if (hyperLinkEditor != null) {
			if (getObjectLabelText() != null) {
				getObjectLabelText().setText(getLabel(hyperLinkEditor.getObject()));
			}
			if (getTooltipInputText() != null) {
				getTooltipInputText().setText(hyperLinkEditor.getTooltipText());
				if (getTooltipInputText().getText().equals(getObjectLabelText().getText())) {
					usedefaultTooltip = true;
				}
			}
		}

	}

	private String getLabel(Object object) {
		String result = null;

		// TODO : remove this dependency
		ILabelProvider labelProvider = null;

		try {
			try {
				labelProvider = ServiceUtilsForEObject.getInstance().getServiceRegistry(amodel).getService(LabelProviderService.class).getLabelProvider();
			} catch (ServiceException ex) {
				Activator.log.error(ex);
				labelProvider = new LabelProvider();
			}
			result = labelProvider.getText(hyperLinkEditor.getObject());
		} finally {
			if (labelProvider != null) {
				labelProvider.dispose();
			}
		}

		return result;
	}

	@Override
	protected void onSearch() {
		// launch a new editor to choose or create diagrams
		EditorLookForEditorShell editorLookForDiagram = new EditorLookForEditorShell(EditorHyperLinkEditorShell.this, editorRegistry, amodel);
		editorLookForDiagram.open();
		Object selection = editorLookForDiagram.getSelectedEditor();
		if (selection == null) {// cancelled or no selection
			// nothing to do
		} else {

			EditorHyperLinkHelper helper = new EditorHyperLinkHelper();
			hyperLinkEditor = helper.getHyperLinkObjectFor(selection);
			Assert.isNotNull(hyperLinkEditor, NLS.bind(Messages.EditorHyperLinkEditorShell_ICanFindTheHyperLinkEditorObject, selection));
			hyperLinkEditor.setObject(selection);

			getObjectLabelText().setText(getLabel(selection));
			if (usedefaultTooltip) {
				getTooltipInputText().setText(getObjectLabelText().getText());
			}
		}
	}

	@Override
	protected void onUseDefaultTooltip() {
		usedefaultTooltip = getUseDefaultCheckBox().getSelection();
		if (usedefaultTooltip) {
			getTooltipInputText().setEditable(false);
			getTooltipInputText().setText(getObjectLabelText().getText());
		} else {
			getTooltipInputText().setEditable(true);
		}
	}

	@Override
	protected void cancelPressed() {
		hyperLinkEditor = null;
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		if (hyperLinkEditor != null) {
			hyperLinkEditor.setTooltipText(getTooltipInputText().getText().trim());
			// if diagram is null, maybe bad selection or other it
			// return null!
			if (hyperLinkEditor.getObject() == null) {
				hyperLinkEditor = null;
			}
		}

		super.okPressed();
	}

	/**
	 * Gets the hyper link diagram.
	 *
	 * @return the hyperLinkEditor maybe null, if cancel or bad selection
	 */
	public HyperLinkEditor getHyperLinkEditor() {
		return hyperLinkEditor;
	}

	/**
	 * Sets the hyper link diagram.
	 *
	 * @param hyperLinkEditor
	 *            the hyperLinkEditor to set
	 */
	public void setHyperLinkEditor(HyperLinkEditor hyperLinkEditor) {
		this.hyperLinkEditor = hyperLinkEditor;
		updateFields();
	}
}
