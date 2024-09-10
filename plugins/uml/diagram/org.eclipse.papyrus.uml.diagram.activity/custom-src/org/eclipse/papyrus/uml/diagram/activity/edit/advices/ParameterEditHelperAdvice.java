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
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 * Call pin derivation command on modification of a parameter
 *
 * @since 3.0
 */
public class ParameterEditHelperAdvice extends AbstractEditHelperAdvice {

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
		// 1] check if the setFeature is Name, Type, Direction or Multiplicity
		if (request.getFeature().equals(UMLPackage.eINSTANCE.getTypedElement_Type()) ||
				request.getFeature().equals(UMLPackage.eINSTANCE.getMultiplicityElement_Lower()) ||
				request.getFeature().equals(UMLPackage.eINSTANCE.getMultiplicityElement_Upper()) ||
				request.getFeature().equals(UMLPackage.eINSTANCE.getParameter_Direction()) ||
				request.getFeature().equals(UMLPackage.eINSTANCE.getNamedElement_Name())) {
			final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
			boolean synchronizePinPreference = false;
			CompositeCommand command = new CompositeCommand("Update parameter"); //$NON-NLS-1$
			Parameter parameter = (Parameter) request.getElementToEdit();
			Package root = PackageUtil.getRootPackage(parameter);
			if (root != null) {
				// 2] check the preference for AcceptCallEvent
				synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPT_CALL_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
				if (synchronizePinPreference) {
					// 3] get all AcceptCallAction referencing the parameter
					// Parameter -> Operation (owned by) -> CallEvent (Reference) -> Trigger (Reference) -> AcceptEventAction (owned by)
					if (parameter.getOwner() instanceof Operation) {
						ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(parameter.getOwner());
						Collection<Setting> allReferences = adapter.getNonNavigableInverseReferences(parameter.getOwner());
						for (Setting reference : allReferences) {
							if (reference.getEObject() instanceof CallEvent) {
								ECrossReferenceAdapter adapterCallEvent = ECrossReferenceAdapter.getCrossReferenceAdapter(reference.getEObject());
								Collection<Setting> allReferencesCallEvent = adapterCallEvent.getNonNavigableInverseReferences(reference.getEObject());
								for (Setting referenceCallEvent : allReferencesCallEvent) {
									if (referenceCallEvent.getEObject() instanceof Trigger) {
										if (((Trigger) referenceCallEvent.getEObject()).getOwner() instanceof AcceptCallAction) {
											// 4] call the command for the acceptCallAction whose trigger reference a callEvent which reference the operation
											AcceptCallAction acceptCallAction = (AcceptCallAction) ((Trigger) referenceCallEvent.getEObject()).getOwner();
											IPinUpdater<AcceptCallAction> updater = PinUpdaterFactory.getInstance().instantiate(acceptCallAction);
											return new PinUpdateCommand<>("Update accept event action pins", updater, acceptCallAction); //$NON-NLS-1$

										}
									}
								}
							}
						}
					}
				}
				// Pins of StartObjectBehaviorAction should be create and update automatically
				// 1] get the preference for StartObjectBehaviorAction
				synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.START_OBJECT_BEHAVIOR_ACTION).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
				// 2] check preference
				if (synchronizePinPreference) {
					// 3] get all StartObjectBehaviorAction referencing the parameter
					// parameter -> Behavior (owned by) -> StartObjectBehaviorAction (Reference)
					if (parameter.getOwner() instanceof Behavior) {
						ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(parameter.getOwner());
						Collection<Setting> allReferences = adapter.getNonNavigableInverseReferences(parameter.getOwner());
						for (Setting reference : allReferences) {
							if (reference.getEObject() instanceof InputPin) {
								if (((InputPin) reference.getEObject()).getOwner() instanceof StartObjectBehaviorAction) {
									// 4] call the command for the StartObjectBehaviorAction which has as behavior the current one
									StartObjectBehaviorAction startObjectBehaviorAction = (StartObjectBehaviorAction) ((InputPin) reference.getEObject()).getOwner();
									IPinUpdater<StartObjectBehaviorAction> updater = PinUpdaterFactory.getInstance().instantiate(startObjectBehaviorAction);
									command.add(new PinUpdateCommand<>("Update start object behavior action pins", updater, startObjectBehaviorAction)); //$NON-NLS-1$
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
		return super.getAfterSetCommand(request);
	}

}
