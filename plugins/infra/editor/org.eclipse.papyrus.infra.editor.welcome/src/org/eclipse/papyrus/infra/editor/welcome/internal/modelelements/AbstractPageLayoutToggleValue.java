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

import java.util.Objects;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.DiUtils;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.editor.welcome.internal.Activator;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.internal.commands.SashLayoutCommandFactory;
import org.eclipse.papyrus.infra.ui.services.SaveLayoutBeforeClose;

/**
 * Non-transactional boolean-valued observable properties of the page layout
 * management for Papyrus Editors.
 */
public abstract class AbstractPageLayoutToggleValue extends AbstractObservableValue<Boolean> {

	protected final EditingDomain domain;
	protected final SashModel sashModel;

	public AbstractPageLayoutToggleValue(WelcomeModelElement owner) {
		this(Realm.getDefault(), owner);
	}

	public AbstractPageLayoutToggleValue(Realm realm, WelcomeModelElement owner) {
		super(realm);

		domain = owner.getDomain();
		sashModel = SashModelUtils.getSashModel((ModelSet) domain.getResourceSet());
	}

	@Override
	public Object getValueType() {
		return Boolean.class;
	}

	protected SashWindowsMngr getSashWindowsMngr() {
		return getSashWindowsMngr(sashModel.getResource());
	}

	protected SashWindowsMngr getSashWindowsMngr(Resource resource) {
		return DiUtils.lookupSashWindowsMngr(resource);
	}

	protected abstract Command getToggleCommand(SashLayoutCommandFactory factory);

	@Override
	protected void doSetValue(Boolean value) {
		Boolean oldValue = getValue();
		if (!Objects.equals(oldValue, value)) {
			IMultiDiagramEditor editor;

			try {
				editor = ServiceUtilsForResourceSet.getInstance().getService(IMultiDiagramEditor.class, domain.getResourceSet());
				Command command = getToggleCommand(new SashLayoutCommandFactory(editor));
				if (command.canExecute()) {
					// Don't do this in an undoable command on the history
					try {
						TransactionHelper.run(domain, () -> command.execute());
						fireValueChange(Diffs.createValueDiff(oldValue, value));
					} catch (Exception e) {
						Activator.log.error("Failed to toggle editor page layout option", e); //$NON-NLS-1$
					}
				}
			} catch (ServiceException e) {
				throw new IllegalStateException("No editor available in the service registry", e); //$NON-NLS-1$
			}

			try {
				SaveLayoutBeforeClose saveLayout = editor.getServicesRegistry().getService(SaveLayoutBeforeClose.class);

				// This won't actually save if the editor is dirty, to avoid inconsistencies
				// (and saving the editor later will save the sash model, too)
				saveLayout.saveBeforeClose(editor);
			} catch (ServiceException e) {
				// We'll get it later
				Activator.log.error("Failed to save page layout", e); //$NON-NLS-1$
			}
		}
	}
}
