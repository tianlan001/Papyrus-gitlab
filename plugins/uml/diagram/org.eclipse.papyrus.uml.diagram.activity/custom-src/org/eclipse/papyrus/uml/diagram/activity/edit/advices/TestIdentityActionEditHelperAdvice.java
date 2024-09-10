/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.advices;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
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
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.TestIdentityAction;

/**
 *
 * Pins of TestIdentityAction should be create automatically
 *
 * @since 3.0
 *
 */
public class TestIdentityActionEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		// 1] get the preference for TestIdentityAction
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		boolean synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.TEST_IDENTITY_ACTION).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
		// 2] check preference
		if (synchronizePin) {
			TestIdentityAction testIdentityAction = (TestIdentityAction) request.getElementToConfigure();
			if (testIdentityAction != null) {
				// 3] call the command for the TestIdentityAction
				IPinUpdater<TestIdentityAction> updater = PinUpdaterFactory.getInstance().instantiate(testIdentityAction);
				return new PinUpdateCommand<>("Update test identity action pins", updater, testIdentityAction); //$NON-NLS-1$
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		// 1] get the preference for TestIdentityAction
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		boolean synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.TEST_IDENTITY_ACTION).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
		// 2] check preference
		if (synchronizePin) {
			Package root = PackageUtil.getRootPackage((Element) request.getElementToConfigure());
			if (!UpdaterPinUtils.isPrimitiveTypeLibraryImported(root)) {
				// 3] call the command to import UML primitive types package
				return new ImportUMLPrimitiveTypePackageCommand("Import UML primitive type package", root); //$NON-NLS-1$
			}
		}
		return null;
	}
}
