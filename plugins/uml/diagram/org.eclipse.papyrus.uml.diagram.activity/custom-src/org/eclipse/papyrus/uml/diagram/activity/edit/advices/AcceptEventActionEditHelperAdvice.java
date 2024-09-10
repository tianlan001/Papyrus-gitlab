/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.advices;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.activity.edit.commands.util.ImportUMLPrimitiveTypePackageCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.commands.util.PinUpdateCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.PinUpdaterFactory;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.UpdaterPinUtils;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 *
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 * @since 3.0
 *
 */
public class AcceptEventActionEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * This method import UML primitive type package
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		// 1] get the preference for acceptEventAction or callEvent action
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		boolean synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPT_CALL_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION))
				&& (request.getElementToConfigure() instanceof AcceptCallAction);
		synchronizePinPreference |= (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPTE_EVENT_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION))
				&& (request.getElementToConfigure() instanceof AcceptEventAction);
		// 2] check preference
		if (synchronizePinPreference) {
			Package root = PackageUtil.getRootPackage((Element) request.getElementToConfigure());
			if (!UpdaterPinUtils.isPrimitiveTypeLibraryImported(root)) {
				// 3] call command to import c
				return new ImportUMLPrimitiveTypePackageCommand("Import UML primitive type package", root); //$NON-NLS-1$
			}
		}
		return null;
	}

	/**
	 * This method call command to synchronize pin
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		// 1] get the preference for acceptEventAction or callEvent action
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		boolean synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPT_CALL_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION))
				&& (request.getElementToConfigure() instanceof AcceptCallAction);
		synchronizePinPreference |= (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPTE_EVENT_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION))
				&& (request.getElementToConfigure() instanceof AcceptEventAction);
		// 2] check preference
		if (synchronizePinPreference) {
			AcceptEventAction acceptEventAction = (AcceptEventAction) request.getElementToConfigure();
			if (acceptEventAction != null) {
				// 3] call command to synchronize pin
				return new PinUpdateCommand<>("Update accept event action pins", PinUpdaterFactory.getInstance().instantiate(acceptEventAction), acceptEventAction); //$NON-NLS-1$
			}
		}
		return null;
	}

	/**
	 * This method call command to synchronize pin
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		// 1] check if the setFeature is trigger of isUnmarshall
		if (request.getFeature().equals(UMLPackage.eINSTANCE.getAcceptEventAction_Trigger()) || request.getFeature().equals(UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall())) {
			// 2] get the preference for acceptEventAction or callEvent action
			final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
			boolean synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPT_CALL_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION))
					&& (request.getElementToEdit() instanceof AcceptCallAction);
			synchronizePinPreference |= (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPTE_EVENT_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION))
					&& (request.getElementToEdit() instanceof AcceptEventAction);
			// 3] check preference
			if (synchronizePinPreference) {
				AcceptEventAction acceptEventAction = (AcceptEventAction) request.getElementToEdit();
				if (acceptEventAction != null) {
					// 4] call command to synchronize pin
					IPinUpdater<AcceptEventAction> updater = PinUpdaterFactory.getInstance().instantiate(acceptEventAction);
					return new PinUpdateCommand<>("Update accept event action pins", updater, acceptEventAction); //$NON-NLS-1$
				}
			}
		}
		return null;
	}

}
