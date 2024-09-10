/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.validators;

import java.util.Arrays;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sasheditor.api.IPageAddValidator;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.ui.architecture.dialogs.ArchitectureContextDialog;
import org.eclipse.papyrus.infra.ui.architecture.messages.Messages;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The page add validator for architecture framework.
 * In our case, this will check if the page to add is contained in the current viewpoint. If not, open a dialog to change the context/viewpoint.
 */
public class ArchitecturePageAddValidator implements IPageAddValidator {

	/**
	 * Constructor.
	 */
	public ArchitecturePageAddValidator() {
		// No nothing here
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.core.sasheditor.api.IPageAddValidator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(final Object pageIdentifier) {
		Object currentPageIdentifier = pageIdentifier instanceof PageRef ? ((PageRef) pageIdentifier).getPageIdentifier() : pageIdentifier;

		// We need to check if this is an EObject and not the welcome (for the welcome page)
		if (currentPageIdentifier instanceof EObject && !(currentPageIdentifier instanceof Welcome)) {
			final EObject eObject = (EObject) currentPageIdentifier;
			final ViewPrototype prototype = ViewPrototype.get(eObject);

			// Get the checker
			PolicyChecker checker = PolicyChecker.getFor(eObject);

			if (null != checker && null != prototype) {
				// Initialize with window.OK
				while (!checker.isInViewpoint(prototype.getRepresentationKind())) {
					// If the dialog result is not OK button value, return false
					// Otherwise, if the window returns OK, loop on while and re-open the dialog (if viewpoint is not the correct one) or return true
					if (Window.OK != changeArchitectureContextAndViewPoint(eObject)) {
						return false;
					} else {
						// Reload the policy checker after switch context/viewpoints
						checker = PolicyChecker.getFor(eObject);
					}
				}
				return true;
			}
		}
		return true;
	}

	/**
	 * This allows to change the context/viewpoint to manage the viewpoint for the selected diagram/table.
	 *
	 * @param eObject
	 *            The diagram or table.
	 * @return The dialog result.
	 */
	private int changeArchitectureContextAndViewPoint(final EObject eObject) {
		// Check the resource set as first
		if (null == eObject.eResource() || !(eObject.eResource().getResourceSet() instanceof ModelSet)) {
			// The initial result is the cancel fake
			return Window.CANCEL;
		}

		// Get the current contexts and viewpoints
		final ArchitectureDescriptionUtils helper = new ArchitectureDescriptionUtils((ModelSet) eObject.eResource().getResourceSet());
		final String[] contextIds = new String[] { helper.getArchitectureContextId() };
		final String[] viewpointIds = helper.getArchitectureViewpointIds().toArray(new String[0]);

		// Prepare the dialog
		final Shell shell = Display.getCurrent().getActiveShell();
		final ArchitectureContextDialog dialog = new ArchitectureContextDialog(shell, Messages.ArchitecturePageAddValidator_diagleTitle,
				Messages.ArchitecturePageAddValidator_dialogLabel);
		dialog.setSelectedContexts(contextIds);
		dialog.setSelectedViewpoints(viewpointIds);
		dialog.create();

		// Open the dialog
		int result = dialog.open();
		if (Window.OK == result) {
			// In this case, activate the context and viewpoints
			final TransactionalEditingDomain dom = helper.getModelSet().getTransactionalEditingDomain();
			final CompoundCommand cmd = new CompoundCommand("Change Architecture Viewpoint to open diagrams and tables"); //$NON-NLS-1$
			if (!Arrays.equals(dialog.getSelectedContextIds(), contextIds)) {
				cmd.append(helper.switchArchitectureContextId(dialog.getSelectedContextIds()[0]));
			}
			if (!Arrays.equals(dialog.getSelectedViewpointIds(), viewpointIds)) {
				cmd.append(helper.switchArchitectureViewpointIds(dialog.getSelectedViewpointIds()));
			}
			if (!cmd.isEmpty()) {
				dom.getCommandStack().execute(cmd);
			}
		}

		return result;
	}

}
