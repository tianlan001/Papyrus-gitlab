/*****************************************************************************
 * Copyright (c) 2011, 2016, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 506896
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 509357
 *****************************************************************************/
package org.eclipse.papyrus.uml.commands.handler;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.ui.command.AbstractCommandHandler;
import org.eclipse.papyrus.uml.commands.command.RenameNamedElementLabelCommand;
import org.eclipse.papyrus.uml.commands.messages.Messages;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.views.modelexplorer.commands.RenameElementCommand;
import org.eclipse.papyrus.views.modelexplorer.util.ModelExplorerEditionUtil;
import org.eclipse.uml2.uml.NamedElement;

/**
 * This handler implements renaming of {@link NamedElement}s.
 */
public class RenameNamedElementHandler extends AbstractCommandHandler {

	@Override
	protected Command getCommand(IEvaluationContext context) {
		Command result = UnexecutableCommand.INSTANCE;

		TransactionalEditingDomain editingDomain = getEditingDomain(context);
		EObject selectedElement = getSelectedElement();
		if (selectedElement instanceof NamedElement) {
			final NamedElement namedElement = (NamedElement) selectedElement;

			// If the label exists, modify the label instead of name
			final String label = UMLLabelInternationalization.getInstance().getLabelWithoutUML(namedElement);
			if (null != label && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(namedElement)) {
				AbstractTransactionalCommand cmd = new RenameNamedElementLabelCommand(editingDomain, "ChangeLabelCommand", namedElement, label, Messages.RenameNamedElementHandler_Label_DialogTitle); //$NON-NLS-1$
				result = new GMFtoEMFCommandWrapper(cmd);
			} else {
				final String currentName = namedElement.getName();

				if (currentName != null) {
					EStructuralFeature nameFeature = namedElement.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
					result = new RenameElementCommand(editingDomain, "Rename", namedElement, currentName, nameFeature, Messages.RenameNamedElementHandler_Name_DialogTitle, Messages.RenameNamedElementHandler_Name_DialogMessage); //$NON-NLS-1$
				}
			}
		}

		return result;
	}

	@Override
	protected boolean computeEnabled(IEvaluationContext context) {
		boolean enabled = super.computeEnabled(context);
		if (enabled) {
			List<EObject> selectedElements = getSelectedElements();
			EObject selection = selectedElements.get(0);
			enabled = !EMFHelper.isReadOnly(selection);
		}
		return enabled;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Overridden to inline edit a named element if it is handled by direct editor.
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		EObject selectedElement = getSelectedElement();

		// If the selected named element could be handled by direct editor
		if (ModelExplorerEditionUtil.isHandledByDirectEditor(selectedElement)) {
			// Call the edit element method from the model explorer to trigger DirectEditor
			ModelExplorerEditionUtil.editElement(selectedElement);
		} else {
			// Otherwise, show the model dialog to get user input
			super.execute(event);
		}

		return null;
	}
}
