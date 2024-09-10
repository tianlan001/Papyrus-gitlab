/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.activity.edit.commands.util.PinUpdateCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.PinUpdaterFactory;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 * Call pin derivation command on modification of a signal
 *
 * @since 3.0
 */
public class SignalEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * This method call command to synchronize pin
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterEditCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ICommand getAfterSetCommand(SetRequest request) {
		// 1] check if the setFeature is ownedAttribute
		if (request.getFeature().equals(UMLPackage.eINSTANCE.getSignal_OwnedAttribute())) {
			final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
			boolean synchronizePinPreference = false;
			CompositeCommand command = new CompositeCommand("Update signal"); //$NON-NLS-1$
			Signal signal = (Signal) request.getElementToEdit();
			Package root = PackageUtil.getRootPackage(signal);
			if (root != null) {
				// 2] check the preference for AcceptEventAction
				synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPTE_EVENT_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
				if (synchronizePinPreference) {
					// 3] get all AcceptEventAction which reference the signal
					// Signal -> SignalEvent (Reference) -> Trigger (Reference) -> AcceptEventAction (owned by)
					ECrossReferenceAdapter adapterSignal = ECrossReferenceAdapter.getCrossReferenceAdapter(signal);
					Collection<Setting> allReferencesOfSignal = adapterSignal.getInverseReferences(signal);
					for (Setting settingSignal : allReferencesOfSignal) {
						if (settingSignal.getEObject() instanceof SignalEvent) {
							ECrossReferenceAdapter adapterSignalEvent = ECrossReferenceAdapter.getCrossReferenceAdapter(settingSignal.getEObject());
							Collection<Setting> allReferencesOfSignalEvent = adapterSignalEvent.getInverseReferences(settingSignal.getEObject());
							for (Setting settingSignalEvent : allReferencesOfSignalEvent) {
								if (settingSignalEvent.getEObject() instanceof Trigger) {
									if (((Trigger) settingSignalEvent.getEObject()).getOwner() instanceof AcceptEventAction) {
										AcceptEventAction acceptEventAction = (AcceptEventAction) ((Trigger) settingSignalEvent.getEObject()).getOwner();
										IPinUpdater<AcceptEventAction> updater = PinUpdaterFactory.getInstance().instantiate(acceptEventAction);
										command.add(new PinUpdateCommand<>("Update accept event action pins", updater, acceptEventAction)); //$NON-NLS-1$
									}
								}
							}
						}
					}
				}
				if (!command.isEmpty()) {
					return command;
				}
			}
		}
		return super.getAfterSetCommand(request);
	}
}
