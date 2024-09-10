/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 434983
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.services;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.internal.commands.TogglePageLayoutStorageHandler;
import org.eclipse.papyrus.infra.ui.internal.preferences.EditorPreferences;
import org.eclipse.papyrus.infra.ui.internal.preferences.YesNo;
import org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent;
import org.eclipse.papyrus.infra.ui.lifecycleevents.ILifeCycleEventsProvider;
import org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider;

/**
 * This service automatically saves the current SashModel before closing the Papyrus editor
 *
 * This is useful, as modifications to the SashModel do not dirty the editor
 *
 * The save action is not executed if the editor is dirty when it is closed (To ensure model consistency)
 *
 * Bug 430976: [SashEditor] Editor layout is not exactly the same when reopening the model
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=430976
 *
 * @author Camille Letavernier
 * @since 1.2
 */
public class SaveLayoutBeforeClose implements IService {

	private ServicesRegistry registry;

	private EditorLifecycleManager lifecycleManager;

	private EditorLifecycleEventListener lifecycleListener;

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.registry = servicesRegistry;
	}

	@Override
	public void startService() throws ServiceException {
		installSaveOnClose();
	}

	protected void installSaveOnClose() {
		try {
			lifecycleManager = registry.getService(EditorLifecycleManager.class);
			if (lifecycleManager == null) {
				return;
			}
		} catch (ServiceException ex) {
			return;
		}

		lifecycleListener = new EditorLifecycleEventListener() {

			@Override
			public void postInit(IMultiDiagramEditor editor) {
				// Nothing
			}

			@Override
			public void postDisplay(IMultiDiagramEditor editor) {
				checkSharedLayout(editor);
			}

			@Override
			public void beforeClose(IMultiDiagramEditor editor) {
				saveBeforeClose(editor);
			}
		};

		lifecycleManager.addEditorLifecycleEventsListener(lifecycleListener);
	}

	public void saveBeforeClose(IMultiDiagramEditor editor) {
		if (editor.isDirty()) {
			return; // User explicitly quit without saving. Do nothing (And if user wants to save during exit, the sashmodel will be saved anyway)
		}

		ModelSet modelSet; // Required
		LifeCycleEventsProvider internalLifecycleEventsProvider = null; // Optional

		try {
			modelSet = registry.getService(ModelSet.class);
		} catch (ServiceException ex) {
			return;
		}

		try {
			ILifeCycleEventsProvider eventsProvider = registry.getService(ILifeCycleEventsProvider.class);
			if (eventsProvider instanceof LifeCycleEventsProvider) {
				internalLifecycleEventsProvider = (LifeCycleEventsProvider) eventsProvider;
			}
		} catch (ServiceException ex) {
			// Ignore: the service is optional
		}

		SashModel sashModel = (SashModel) modelSet.getModel(SashModel.MODEL_ID);

		try {
			// We need to send pre- and post-save events, but we can only do that with the internal LifecycleEventsProvider
			// The ISaveAndDirtyService can only save the whole model, but we just want to save the sash
			DoSaveEvent event = new DoSaveEvent(registry, editor, true);
			if (internalLifecycleEventsProvider != null) {
				internalLifecycleEventsProvider.fireAboutToDoSaveEvent(event);
				internalLifecycleEventsProvider.fireDoSaveEvent(event);
			}
			sashModel.saveModel();
			if (internalLifecycleEventsProvider != null) {
				internalLifecycleEventsProvider.firePostDoSaveEvent(event);
			}
		} catch (IOException ex) {
			Activator.log.error(ex);
		}
	}

	private void checkSharedLayout(IMultiDiagramEditor editor) {
		try {
			ModelSet modelSet = registry.getService(ModelSet.class);
			SashModel sashModel = (SashModel) modelSet.getModel(SashModel.MODEL_ID);

			if (sashModel.isLegacyMode()) {
				// Have we ever created the private sash model file?
				URI privateURI = sashModel.getPrivateResourceURI();
				if (!modelSet.getURIConverter().exists(privateURI, null)) {
					// Prompt the user
					promptToEnablePrivateStorage(editor);
				}
			}
		} catch (ServiceException ex) {
			// Shared layout doesn't matter if there's no model-set
		}
	}

	private void promptToEnablePrivateStorage(IMultiDiagramEditor editor) {
		YesNo preference = EditorPreferences.getInstance().getConvertSharedPageLayoutToPrivate();

		if (preference == YesNo.PROMPT) {
			MessageDialogWithToggle dlg = MessageDialogWithToggle.openYesNoCancelQuestion(editor.getSite().getShell(),
					Messages.SaveLayoutBeforeClose_0,
					Messages.SaveLayoutBeforeClose_1,
					Messages.SaveLayoutBeforeClose_2, false, null, null);

			switch (dlg.getReturnCode()) {
			case IDialogConstants.YES_ID:
				preference = YesNo.YES;
				break;
			case IDialogConstants.NO_ID:
				preference = YesNo.NO;
				break;
			default:
				// User cancelled
				preference = YesNo.PROMPT;
				break;
			}

			if (dlg.getToggleState()) {
				EditorPreferences.getInstance().setConvertSharedPageLayoutToPrivate(preference);
			}
		}

		switch (preference) {
		case YES:
			// Change the storage to private
			new TogglePageLayoutStorageHandler().togglePrivatePageLayout(editor);

			// And save the new layout scheme
			saveBeforeClose(editor);
			break;
		case NO:
			// Just create the empty resource and save it
			try {
				ModelSet modelSet = editor.getServicesRegistry().getService(ModelSet.class);
				SashModel sashModel = (SashModel) modelSet.getModel(SashModel.MODEL_ID);
				modelSet.createResource(sashModel.getPrivateResourceURI());
				saveBeforeClose(editor);
			} catch (ServiceException e) {
				// Without a model-set, much else is going wrong, so there's no need to deal
				// with this here
			}
			break;
		default:
			// User cancelled
			break;
		}
	}

	@Override
	public void disposeService() throws ServiceException {
		registry = null;
		if (lifecycleManager != null) {
			lifecycleManager.removeEditorLifecycleEventsListener(lifecycleListener);
			lifecycleListener = null;
			lifecycleManager = null;
		}
	}

}
