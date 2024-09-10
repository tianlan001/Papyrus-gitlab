/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.GraphicalEditPolicyEx;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.tools.namereferences.NameReferencesHelper;

/**
 * Install listeners on NamedElements referenced by this EditPart
 *
 * @author Camille Letavernier
 *
 * @see {@link NameReferencesHelper}
 */
public abstract class AbstractNameReferencesListenerEditPolicy extends GraphicalEditPolicyEx implements Adapter {

	public static final String NAME_REFERENCES_LISTENER_ID = "nameReferencesListener";

	@Override
	public void activate() {
		super.activate();

		helper = new NameReferencesHelper(getModel().eResource());
		helper.addListener(this);
		helper.replaceReferences(getText());
	}

	protected EObject getModel() {
		return EMFHelper.getEObject(getHost());
	}

	protected abstract String getText();

	@Override
	public void deactivate() {
		super.deactivate();

		helper.removeListener(this);
		helper.dispose();
	}

	@Override
	public void refresh() {
		super.refresh();
		if (helper != null) {
			helper.replaceReferences(getText()); // Update listeners after a change on the contents
		}
	}

	private NameReferencesHelper helper;

	@Override
	public void notifyChanged(Notification notification) {
		if (getHost() != null && getHost().isActive()) {
			getHost().refresh();
		}
	}

	@Override
	public Notifier getTarget() {
		return null;
		// Ignore
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// Ignore
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return false;
		// Ignore
	}

}
