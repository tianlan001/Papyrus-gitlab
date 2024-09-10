/*****************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 535055
 *****************************************************************************/

package org.eclipse.papyrus.uml.ui.editors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.utils.TextReferencesHelper;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.infra.widgets.editors.richtext.AbstractToolbarButton;
import org.eclipse.papyrus.infra.widgets.editors.richtext.GenericRichTextEditor;
import org.eclipse.papyrus.infra.widgets.editors.richtext.RichTextUtils;
import org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.ui.Activator;
import org.eclipse.papyrus.uml.ui.messages.Messages;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author Vincent Lorenzo
 *
 */
public class InsertReferenceToolbarButton extends AbstractToolbarButton {

	/**
	 * the name of the button
	 */
	private static final String BUTTON_NAME = "InsertReferenceButton"; //$NON-NLS-1$

	/**
	 * the name of the command
	 */
	private static final String BUTTON_COMMAND_NAME = "InsertReferenceCommand"; //$NON-NLS-1$

	/**
	 * the label of the button
	 */
	private static final String BUTTON_LABEL = Messages.InsertReferenceToolbarButton_Tooltip;

	/**
	 * the path of the icon used for the button
	 */
	private static final String ICON_PATH = "icons/hyperlink_16x16.gif"; //$NON-NLS-1$

	/**
	 * 
	 * Constructor.
	 * 
	 * @param richTextEditor
	 *            the richtext editor
	 */
	public InsertReferenceToolbarButton() {
		super(BUTTON_NAME, BUTTON_COMMAND_NAME, BUTTON_LABEL, RichTextUtils.TOOLBAR_GROUP_LINKS, org.eclipse.papyrus.infra.widgets.Activator.getDefault().getURL(ICON_PATH));
	}

	/**
	 * Opens the dialog to insert a reference in the rich text content
	 * 
	 * @see org.eclipse.nebula.widgets.richtext.toolbar.ToolbarButton#execute()
	 */
	@Override
	public Object execute() {
		if(null==this.richTextEditor) {
			Activator.log.warn("The insert references action cannot be executed, because the Richtext editor has not be registered for this action");//$NON-NLS-1$
		}else 
		if (!(this.richTextEditor instanceof UMLRichtextEditorWithReferences)) {
			Activator.log.warn("The insert references action cannot be executed, because the Richtext editor is not a UMLRichtextEditorWithReferences");//$NON-NLS-1$
		} else {
			UMLRichtextEditorWithReferences editor = (UMLRichtextEditorWithReferences) this.richTextEditor;
			final IStaticContentProvider referenceContentProvider = editor.getContentProvider();
			final ILabelProvider labelProvider = editor.getLabelProvider();
			final TextReferencesHelper referencesHelper = editor.getTextReferencesHelper();
			if (referenceContentProvider != null && labelProvider != null && referencesHelper != null) {
				TreeSelectorDialog dialog = new TreeSelectorDialog(Display.getDefault().getActiveShell());
				dialog.setContentProvider(new EncapsulatedContentProvider(referenceContentProvider));
				dialog.setLabelProvider(labelProvider);
				if (dialog.open() == Window.OK) {
					Object[] result = dialog.getResult();
					if (result.length == 0) {
						return null;
					}
					Object resultElement = result[0];
					if (!(resultElement instanceof EObject)) {
						return null;
					}
					EObject objectToReference = (EObject) resultElement;
					String newText = referencesHelper.insertReference(objectToReference, "", 0); //$NON-NLS-1$

					if (this.richTextEditor.isTagsEnabled()) {
						try {
							String realName = referencesHelper.replaceReferences(newText).replaceAll("\\<u\\>", "").replaceAll("\\<\\/u\\>", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
							String reference = newText.replaceAll("\\{\\@link", "").replaceAll("\\}", "").replaceAll("\\s", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
							this.richTextEditor.insertHTML("<a class=\"internal_ref\" href=\"" + reference + "\">" + realName + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						} catch (Exception e) {
							Activator.log.error(e);
							this.richTextEditor.insertText(newText);
						}
					} else {
						this.richTextEditor.insertText(newText);
					}
				}
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.editors.richtext.AbstractToolbarButton#setRichTextEditor(org.eclipse.papyrus.infra.widgets.editors.richtext.GenericRichTextEditor)
	 *
	 * @param editor
	 */
	@Override
	public void setRichTextEditor(final GenericRichTextEditor editor) {
		Assert.isTrue(editor instanceof UMLRichtextEditorWithReferences);
		super.setRichTextEditor(editor);
	}
}
