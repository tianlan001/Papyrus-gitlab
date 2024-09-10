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
import org.eclipse.papyrus.uml.diagram.activity.edit.commands.util.PinUpdateLinkEndDataCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdaterLinkEndData;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.PinUpdaterFactory;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.LinkEndCreationDataPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.LinkEndDataPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.LinkEndDestructionDataPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.LinkEndCreationData;
import org.eclipse.uml2.uml.LinkEndData;
import org.eclipse.uml2.uml.LinkEndDestructionData;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 * Call pin derivation command on modification of a property
 *
 * @since 3.0
 */
public class PropertyEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterEditCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ICommand getAfterSetCommand(SetRequest request) {
		// 1] check if the setFeature is Name, Type or Multiplicity
		if (request.getFeature().equals(UMLPackage.eINSTANCE.getTypedElement_Type()) ||
				request.getFeature().equals(UMLPackage.eINSTANCE.getMultiplicityElement_Lower()) ||
				request.getFeature().equals(UMLPackage.eINSTANCE.getMultiplicityElement_Upper()) ||
				request.getFeature().equals(UMLPackage.eINSTANCE.getNamedElement_Name())) {
			final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
			boolean synchronizePinPreference = false;
			CompositeCommand command = new CompositeCommand("Update pins on modification of a property"); //$NON-NLS-1$
			Property property = (Property) request.getElementToEdit();
			Package root = PackageUtil.getRootPackage(property);
			if (root != null) {
				Collection<Setting> allReferences = null;
				// 2] check the preference for AcceptEventAction
				synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ACCEPTE_EVENT_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
				if (synchronizePinPreference) {
					// 3] get all AcceptEventAction which reference the property
					// Property -> Signal (owned by) -> SignalEvent (Reference) -> Trigger (Reference) -> AcceptEventAction (owned by)
					if (property.getOwner() instanceof Signal) {
						ECrossReferenceAdapter adapterSignal = ECrossReferenceAdapter.getCrossReferenceAdapter(property.getOwner());
						Collection<Setting> allReferencesOfSignal = adapterSignal.getNonNavigableInverseReferences(property.getOwner());
						for (Setting settingSignal : allReferencesOfSignal) {
							if (settingSignal.getEObject() instanceof SignalEvent) {
								ECrossReferenceAdapter adapterSignalEvent = ECrossReferenceAdapter.getCrossReferenceAdapter(settingSignal.getEObject());
								Collection<Setting> allReferencesOfSignalEvent = adapterSignalEvent.getNonNavigableInverseReferences(settingSignal.getEObject());
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
				}
				// Pins of CreateLinkAction should be create and update automatically
				// 1] get the preference for CreateLinkAction
				synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.CREATE_LINK_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
				// 2] check preference
				if (synchronizePinPreference) {
					if (allReferences == null) {
						ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(property);
						allReferences = adapter.getNonNavigableInverseReferences(property);
					}
					// 3] get all LinkEndData which reference the property
					for (Setting setting : allReferences) {
						if (setting.getEObject() instanceof LinkEndCreationData) {
							LinkEndCreationData linkEndCreationData = (LinkEndCreationData) setting.getEObject();
							IPinUpdaterLinkEndData updater = new LinkEndCreationDataPinUpdater();
							command.add(new PinUpdateLinkEndDataCommand("Update link end data pins", updater, linkEndCreationData)); //$NON-NLS-1$

						}
					}
				}
				// Pins of DestroyLinkAction should be create and update automatically
				// 1] get the preference for DestroyLinkAction
				synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.DESTROY_LINK_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
				// 2] check preference
				if (synchronizePinPreference) {
					if (allReferences == null) {
						ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(property);
						allReferences = adapter.getNonNavigableInverseReferences(property);
					}
					// 3] get all LinkEndData which reference the property
					for (Setting setting : allReferences) {
						if (setting.getEObject() instanceof LinkEndDestructionData) {
							LinkEndDestructionData linkEndDestructionData = (LinkEndDestructionData) setting.getEObject();
							IPinUpdaterLinkEndData updater = new LinkEndDestructionDataPinUpdater();
							command.add(new PinUpdateLinkEndDataCommand("Update link end data pins", updater, linkEndDestructionData)); //$NON-NLS-1$
						}
					}
				}
				// Pins of ReadLinkAction should be create and update automatically
				// 1] get the preference for ReadLinkAction
				synchronizePinPreference = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.READ_LINK_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
				// 2] check preference
				if (synchronizePinPreference) {
					if (allReferences == null) {
						ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(property);
						allReferences = adapter.getNonNavigableInverseReferences(property);
					}
					// 3] get all LinkEndData which reference the property
					for (Setting setting : allReferences) {
						if (setting.getEObject() instanceof LinkEndData) {
							LinkEndData linkEndData = (LinkEndData) setting.getEObject();
							IPinUpdaterLinkEndData updater = new LinkEndDataPinUpdater();
							command.add(new PinUpdateLinkEndDataCommand("Update link end data pins", updater, linkEndData)); //$NON-NLS-1$
						}
					}
				}
				// check if the setFeature is not Name (only type and multiplicity is interesting)
				if (!request.getFeature().equals(UMLPackage.eINSTANCE.getNamedElement_Name())) {
					// Pins of ReadStructuralFeatureAction should be create and update automatically
					// 1] get the preference for ReadStructuralFeatureAction
					synchronizePinPreference = prefStore.getString(IAutomatedModelCompletionPreferencesConstants.READ_STRUCTURAL_FEATURE_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
					// 2] check preference
					if (synchronizePinPreference) {
						if (allReferences == null) {
							ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(property);
							allReferences = adapter.getNonNavigableInverseReferences(property);
						}
						// 3] get all ReadStructuralFeatureAction which reference the property
						for (Setting setting : allReferences) {
							if (setting.getEObject() instanceof ReadStructuralFeatureAction) {
								ReadStructuralFeatureAction readStructuralFeatureAction = (ReadStructuralFeatureAction) setting.getEObject();
								IPinUpdater<ReadStructuralFeatureAction> updater = PinUpdaterFactory.getInstance().instantiate(readStructuralFeatureAction);
								command.add(new PinUpdateCommand<>("Update read structural feature action pins", updater, readStructuralFeatureAction)); //$NON-NLS-1$
							}
						}
					}
					// Pins of AddStructuralFeatureValueAction should be create and update automatically
					// 1] get the preference for AddStructuralFeatureValueAction
					synchronizePinPreference = prefStore.getString(IAutomatedModelCompletionPreferencesConstants.ADD_STRUCTURAL_FEATURE_VALUE_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
					// 2] check preference
					if (synchronizePinPreference) {
						if (allReferences == null) {
							ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(property);
							allReferences = adapter.getNonNavigableInverseReferences(property);
						}
						// 3] get all AddStructuralFeatureValueAction which reference the property
						for (Setting setting : allReferences) {
							if (setting.getEObject() instanceof AddStructuralFeatureValueAction) {
								AddStructuralFeatureValueAction addStructuralFeatureValueAction = (AddStructuralFeatureValueAction) setting.getEObject();
								IPinUpdater<AddStructuralFeatureValueAction> updater = PinUpdaterFactory.getInstance().instantiate(addStructuralFeatureValueAction);
								command.add(new PinUpdateCommand<>("Update add structural feature value action pins", updater, addStructuralFeatureValueAction)); //$NON-NLS-1$
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
