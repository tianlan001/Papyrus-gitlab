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
package org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.editablereference.internal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;
import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;
import org.eclipse.sirius.editor.internal.navigation.NavigationByKeyListener;
import org.eclipse.sirius.editor.properties.sections.common.AbstractTextWithButtonPropertySection;
import org.eclipse.sirius.editor.tools.api.assist.TypeContentProposalProvider;
import org.eclipse.sirius.editor.tools.internal.presentation.TextWithContentProposalDialog;
import org.eclipse.sirius.ui.tools.api.assist.ContentProposalClient;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * The property section for the Browse Expression text field of the Editable
 * Reference Widget.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@SuppressWarnings("restriction")
public class ExtEditableReferenceDescriptionBrowseExpressionPropertySection
		extends AbstractTextWithButtonPropertySection implements ContentProposalClient {

	@Override
	protected String getDefaultLabelText() {
		return "BrowseExpression"; //$NON-NLS-1$
	}

	@Override
	protected String getLabelText() {
		return super.getLabelText() + ":"; //$NON-NLS-1$
	}

	@Override
	public EAttribute getFeature() {
		return PropertiesAdvancedControlsPackage.eINSTANCE.getExtEditableReferenceDescription_BrowseExpression();
	}

	@Override
	protected Object getFeatureValue(String newText) {
		return newText;
	}

	@Override
	protected boolean isEqual(String newText) {
		return getFeatureAsText().equals(newText);
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		/*
		 * We set the color as it's a InterpretedExpression
		 */
		text.setBackground(SiriusEditor.getColorRegistry().get("yellow")); //$NON-NLS-1$

		TypeContentProposalProvider.bindPluginsCompletionProcessors(this, text);

		text.addKeyListener(new NavigationByKeyListener(this, text, eObject));
	}

	@Override
	protected SelectionListener createButtonListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TextWithContentProposalDialog dialog = new TextWithContentProposalDialog(composite.getShell(),
						ExtEditableReferenceDescriptionBrowseExpressionPropertySection.this, text.getText());
				dialog.open();
				text.setText(dialog.getResult());
				handleTextModified();
			}
		};
	}

	@Override
	protected String getPropertyDescription() {
		return ""; //$NON-NLS-1$
	}

}
