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
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.InstanceValue;
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
public class InstanceSpecificationEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterEditCommand(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public ICommand getAfterSetCommand(SetRequest request) {
		// 1] check if the setFeature is classifier
		if (request.getFeature().equals(UMLPackage.eINSTANCE.getInstanceSpecification_Classifier())) {
			final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
			boolean synchronizePin = false;
			CompositeCommand command = new CompositeCommand("Update inctanceSpecification"); //$NON-NLS-1$
			InstanceSpecification instanceSpecification = (InstanceSpecification) request.getElementToEdit();
			// 1] get the preference for ValueSpecificationAction
			synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.VALUE_SPECIFICATION_ACTION).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
			// 2] check preference
			if (synchronizePin) {
				Package root = PackageUtil.getRootPackage(instanceSpecification);
				if (root != null) {
					// 3] get all ValueSpecificationAction referencing the instance specification
					// instanceSpecification -> instanceValue (Reference) -> ValueSpecificationAction (owned by)
					ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(instanceSpecification);
					Collection<Setting> allReferences = adapter.getNonNavigableInverseReferences(instanceSpecification);
					for (Setting reference : allReferences) {
						if (reference.getEObject() instanceof InstanceValue) {
							if (((InstanceValue) reference.getEObject()).getOwner() instanceof ValueSpecificationAction) {
								ValueSpecificationAction valueSpecificationAction = (ValueSpecificationAction) ((InstanceValue) reference.getEObject()).getOwner();
								// 4] call the command for the ValueSpecificationAction whose value is an instanceValue which referenced the instance specification
								IPinUpdater<ValueSpecificationAction> updater = PinUpdaterFactory.getInstance().instantiate(valueSpecificationAction);
								command.add(new PinUpdateCommand<>("Update value specification action pins", updater, valueSpecificationAction)); //$NON-NLS-1$
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
