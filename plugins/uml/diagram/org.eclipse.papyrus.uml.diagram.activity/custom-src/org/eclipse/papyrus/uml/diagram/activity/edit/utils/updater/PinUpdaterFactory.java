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
 *   J�r�mie TATIBOUET (CEA LIST) - Initial API and implementation
 *   S�bastien REVOL (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater;

import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.AcceptCallActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.AcceptEventActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.AddStructuralFeatureValueActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.CreateLinkActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.CreateObjectActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.LinkActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.ReadLinkActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.ReadSelfActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.ReadStructuralFeatureActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.StartClassifierBehaviorActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.StartObjectBehaviorActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.TestIdentityActionPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.intermediateactions.ValueSpecificationActionPinUpdater;
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.CreateLinkAction;
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.DestroyLinkAction;
import org.eclipse.uml2.uml.ReadLinkAction;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.ValueSpecificationAction;

public class PinUpdaterFactory {

	/**
	 * Singleton reference
	 */
	private static PinUpdaterFactory INSTANCE;

	/**
	 * Constructor.
	 */
	private PinUpdaterFactory() {
	}

	/**
	 * Provide access to the singleton instance
	 *
	 * @return INSTANCE
	 *         the singleton
	 */
	public static PinUpdaterFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PinUpdaterFactory();
		}
		return INSTANCE;
	}

	/**
	 * Provide a pin updater for the given activity node. This update encapsulates
	 * logic to derive activity node pins.
	 *
	 * @param node
	 *            the activity node
	 * @return updater
	 *         the pin updater for the given activity node
	 */
	@SuppressWarnings("unchecked")
	public <T extends ActivityNode> IPinUpdater<T> instantiate(ActivityNode node) {
		IPinUpdater<T> updater = null;
		if (node instanceof CallBehaviorAction) {
			updater = (IPinUpdater<T>) new CallBehaviorActionPinUpdater();
		} else if (node instanceof CallOperationAction) {
			updater = (IPinUpdater<T>) new CallOperationActionPinUpdater();
		} else if (node instanceof SendSignalAction) {
			updater = (IPinUpdater<T>) new SendSignalActionPinUpdater();
		} else if (node instanceof AcceptCallAction) {
			updater = (IPinUpdater<T>) new AcceptCallActionPinUpdater();
		} else if (node instanceof AcceptEventAction) {
			updater = (IPinUpdater<T>) new AcceptEventActionPinUpdater();
		} else if (node instanceof ReadStructuralFeatureAction) {
			updater = (IPinUpdater<T>) new ReadStructuralFeatureActionPinUpdater();
		} else if (node instanceof AddStructuralFeatureValueAction) {
			updater = (IPinUpdater<T>) new AddStructuralFeatureValueActionPinUpdater();
		} else if (node instanceof CreateObjectAction) {
			updater = (IPinUpdater<T>) new CreateObjectActionPinUpdater();
		} else if (node instanceof ValueSpecificationAction) {
			updater = (IPinUpdater<T>) new ValueSpecificationActionPinUpdater();
		} else if (node instanceof TestIdentityAction) {
			updater = (IPinUpdater<T>) new TestIdentityActionPinUpdater();
		} else if (node instanceof CreateLinkAction) {
			updater = (IPinUpdater<T>) new CreateLinkActionPinUpdater();
		} else if (node instanceof DestroyLinkAction) {
			updater = (IPinUpdater<T>) new LinkActionPinUpdater();
		} else if (node instanceof ReadLinkAction) {
			updater = (IPinUpdater<T>) new ReadLinkActionPinUpdater();
		} else if (node instanceof StartClassifierBehaviorAction) {
			updater = (IPinUpdater<T>) new StartClassifierBehaviorActionPinUpdater();
		} else if (node instanceof StartObjectBehaviorAction) {
			updater = (IPinUpdater<T>) new StartObjectBehaviorActionPinUpdater();
		} else if (node instanceof ReadSelfAction) {
			updater = (IPinUpdater<T>) new ReadSelfActionPinUpdater();
		}
		return updater;
	}
}
