/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.internal.modelelements;

import static org.eclipse.core.databinding.observable.Diffs.createValueDiff;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.emf.common.command.Command;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.ui.internal.commands.SashLayoutCommandFactory;

/**
 * Encapsulation of the "shared layout" property of the Papyrus Editor.
 */
public class PrivateLayoutValue extends AbstractPageLayoutToggleValue implements PropertyChangeListener {

	private SashModel sashModel;

	public PrivateLayoutValue(WelcomeModelElement owner) {
		this(Realm.getDefault(), owner);
	}

	public PrivateLayoutValue(Realm realm, WelcomeModelElement owner) {
		super(realm, owner);

		sashModel = SashModelUtils.getSashModel((ModelSet) owner.getDomain().getResourceSet());
		if (sashModel != null) {
			sashModel.addPropertyChangeListener(SashModel.PROPERTY_LEGACY_MODE, this);
		}
	}

	@Override
	public synchronized void dispose() {
		if (sashModel != null) {
			sashModel.removePropertyChangeListener(SashModel.PROPERTY_LEGACY_MODE, this);
			sashModel = null;
		}

		super.dispose();
	}

	@Override
	protected Boolean doGetValue() {
		return !sashModel.isLegacyMode();
	}

	@Override
	protected Command getToggleCommand(SashLayoutCommandFactory factory) {
		return factory.createTogglePrivateLayoutCommand();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == sashModel) {
			switch (evt.getPropertyName()) {
			case SashModel.PROPERTY_LEGACY_MODE:
				fireValueChange(createValueDiff(!(Boolean) evt.getOldValue(), !(Boolean) evt.getNewValue()));
				break;
			}
		}
	}
}
