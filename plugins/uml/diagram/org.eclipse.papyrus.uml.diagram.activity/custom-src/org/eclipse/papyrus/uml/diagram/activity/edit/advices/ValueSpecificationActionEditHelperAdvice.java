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
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecificationAction;

/**
 *
 * Pin of ValueSpecificationAction should be create and update automatically
 *
 * @since 3.0
 *
 */
public class ValueSpecificationActionEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		// 1] get the preference for ValueSpecificationAction
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		boolean synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.VALUE_SPECIFICATION_ACTION).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
		// 2] check preference
		if (synchronizePin) {
			Package root = PackageUtil.getRootPackage((Element) request.getElementToConfigure());
			if (!UpdaterPinUtils.isPrimitiveTypeLibraryImported(root)) {
				// 3] call the command to import the UML primitive types package
				return new ImportUMLPrimitiveTypePackageCommand("Import UML primitive type package", root); //$NON-NLS-1$
			}
		}

		return null;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		// 1] check if the setFeature is value
		if (request.getFeature().equals(UMLPackage.eINSTANCE.getValueSpecificationAction_Value())) {
			// 2] get the preference for ValueSpecificationAction
			final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
			boolean synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.VALUE_SPECIFICATION_ACTION).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
			// 3] check preference
			if (synchronizePin) {
				ValueSpecificationAction valueSpecificationAction = (ValueSpecificationAction) request.getElementToEdit();
				if (valueSpecificationAction != null && request.getFeature() == UMLPackage.eINSTANCE.getValueSpecificationAction_Value()) {
					// 4] call the command for the ValueSpecificationAction
					IPinUpdater<ValueSpecificationAction> updater = PinUpdaterFactory.getInstance().instantiate(valueSpecificationAction);
					return new PinUpdateCommand<>("Update value specification action pins", updater, valueSpecificationAction); //$NON-NLS-1$
				}
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		// 1] get the preference for ValueSpecificationAction
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		boolean synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.VALUE_SPECIFICATION_ACTION).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
		// 2] check preference
		if (synchronizePin) {
			ValueSpecificationAction valueSpecificationAction = (ValueSpecificationAction) request.getElementToConfigure();
			if (valueSpecificationAction != null) {
				// 3] call the command for the ValueSpecificationAction
				return new PinUpdateCommand<>("Update value specification action pins", PinUpdaterFactory.getInstance().instantiate(valueSpecificationAction), valueSpecificationAction); //$NON-NLS-1$
			}
		}
		return null;
	}
}
