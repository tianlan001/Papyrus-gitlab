/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 497865
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.ui;

import static org.eclipse.papyrus.infra.services.controlmode.ControlModeRequestParameters.CREATE_SHARD;

import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.swt.widgets.Shell;


/**
 * Interface for access to a {@link Dialog} to prompt the user for specification of the resource to create for a new model unit.
 * Implementations are accessed by adaptation of the contextual {@code ModelSet}. Where no adapter is provided, the {@linkplain #DEFAULT default provider} is used.
 */
public interface IControlModeFragmentDialogProvider {
	/**
	 * The default provider used when the {@link ModelSet} does not supply one, or in case a
	 * custom provider wishes to delegate responsibility. The default provider creates a dialog
	 * that lets the user browse the workspace and/or local filesystem for resource URIs.
	 */
	IControlModeFragmentDialogProvider DEFAULT = new IControlModeFragmentDialogProvider() {

		@Override
		public Dialog createDialog(Shell shell, Resource parentUnit, String defaultUnitName) {
			return new CreateModelFragmentDialog(shell, parentUnit, defaultUnitName);
		}

		@Override
		public Dialog createDialog(Shell shell, EObject objectToControl, String defaultUnitName) {
			return new CreateModelFragmentDialog(shell, objectToControl, defaultUnitName);
		}

		@Override
		public URI getSelectedURI(Dialog dialog) {
			return cast(dialog).map(CreateModelFragmentDialog::getURI).orElse(null);
		}

		Optional<CreateModelFragmentDialog> cast(Dialog dialog) {
			return Optional.of(dialog)
					.filter(d -> d.getReturnCode() == Window.OK)
					.filter(CreateModelFragmentDialog.class::isInstance)
					.map(CreateModelFragmentDialog.class::cast);
		}

		@Override
		public ControlModeRequest getControlRequest(Dialog dialog, TransactionalEditingDomain domain, EObject objectToControl) {
			ControlModeRequest result = IControlModeFragmentDialogProvider.super.getControlRequest(dialog, domain, objectToControl);

			cast(dialog).ifPresent(d -> result.setParameter(CREATE_SHARD, d.isCreateShard()));

			return result;
		}
	};

	/**
	 * Create a new dialog to solicit the new model unit's URI from the user. The new dialog is <em>not</em> opened.
	 *
	 * @param shell
	 *            the new dialog's parent shell
	 * @param parentUnit
	 *            the model unit from which a sub-unit is to be carved out
	 * @param defaultUnitName
	 *            the default suggested name of the new sub-unit. Will not be {@code null}
	 *
	 * @return the fragment resource URI selection dialog
	 * 
	 * @deprecated As of 1.3, use or implement the {@link #createDialog(Shell, EObject, String)} API, instead.
	 */
	@Deprecated
	Dialog createDialog(Shell shell, Resource parentUnit, String defaultUnitName);

	/**
	 * Create a new dialog to solicit the new model unit's URI from the user. The new dialog
	 * is <em>not</em> opened.
	 *
	 * @param shell
	 *            the new dialog's parent shell
	 * @param objectToControl
	 *            the model element that is to be carved out of its resource as a new model unit
	 * @param defaultUnitName
	 *            the default suggested name of the new sub-unit. Will not be {@code null}
	 *
	 * @return the fragment resource URI selection dialog
	 * 
	 * @since 1.3
	 */
	default Dialog createDialog(Shell shell, EObject objectToControl, String defaultUnitName) {
		return createDialog(shell, objectToControl.eResource(), defaultUnitName);
	}

	/**
	 * Obtains the model unit URI selected by the user in the given {@code dialog}. If that {@code dialog} was cancelled or was not
	 * created by this provider, then the result must be {@code null}.
	 *
	 * @param dialog
	 *            a dialog created by this provider that has been completed by the user
	 *
	 * @return the URI selected by the user, or {@code null} if cancelled or an unrecognized dialog
	 */
	URI getSelectedURI(Dialog dialog);

	/**
	 * Obtains the control request to execute from the given {@code dialog}.
	 * 
	 * @param dialog
	 *            a dialog created by this provider that has been completed by the user
	 * @param domain
	 *            the editing domain in which to perform the request
	 * @param objectToControl
	 *            the object to be controlled
	 * 
	 * @return the control request configured by the {@code dialog}
	 * 
	 * @since 1.3
	 */
	default ControlModeRequest getControlRequest(Dialog dialog, TransactionalEditingDomain domain, EObject objectToControl) {
		return ControlModeRequest.createUIControlModelRequest(domain, objectToControl, getSelectedURI(dialog));
	}
}
