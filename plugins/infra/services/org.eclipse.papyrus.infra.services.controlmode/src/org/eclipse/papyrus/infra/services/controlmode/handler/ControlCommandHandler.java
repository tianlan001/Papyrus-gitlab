/*****************************************************************************
 * Copyright (c) 2013, 2016 Atos, CEA LIST, Christian W. Damus, and others.
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
 *  Arthur Daussy (Atos) arthur.daussy@atos.net - Initial API and implementation
 *  Christian W. Damus (CEA LIST) - pluggable providers of fragment-resource selection dialogs
 *  Christian W. Damus (CEA) - bug 410346
 *  Juan Cadavid <juan.cadavid@cea.fr> - bug 399877
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 436952
 *  Christian W. Damus - bug 497865
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.handler;

import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.ParameterValuesException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.ControlModePlugin;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.IControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.commands.ControlModeCommandParameterValues;
import org.eclipse.papyrus.infra.services.controlmode.commands.ResourceLocationParameterValues;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;
import org.eclipse.papyrus.infra.services.controlmode.ui.IControlModeFragmentDialogProvider;
import org.eclipse.papyrus.infra.services.controlmode.util.LabelHelper;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.widgets.toolbox.notification.builders.NotificationBuilder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Handler used to control an element
 *
 * @author adaussy
 */
public class ControlCommandHandler extends AbstractHandler {

	/** The Constant PARAMETER_ERROR. */
	private static final String PARAMETER_ERROR = Messages.getString("ControlCommandHandler.parameter.error"); //$NON-NLS-1$

	/** The Constant PARAMETER_VALUE_ERROR. */
	private static final String PARAMETER_VALUE_ERROR = Messages.getString("ControlCommandHandler.parameter.value.error"); //$NON-NLS-1$

	/** The Constant MODELSET_ERROR. */
	private static final String MODELSET_ERROR = Messages.getString("ControlCommandHandler.modelset.error"); //$NON-NLS-1$

	/** The Constant SHOW_DIALOG_PARAMETER. */
	private static final String SHOW_DIALOG_PARAMETER = "showDialog"; //$NON-NLS-1$

	/** The Constant NO_EDITING_DOMAIN_MESSAGE. */
	private static final String NO_EDITING_DOMAIN_MESSAGE = Messages.getString("ControlCommandHandler.editing.domain.error"); //$NON-NLS-1$

	/** The Constant EMPTY_SELECTION_MESSAGE. */
	private static final String EMPTY_SELECTION_MESSAGE = Messages.getString("ControlCommandHandler.empty.selection.message"); //$NON-NLS-1$

	/** The Constant CONTROLMODE_USE_DIALOG_PARAMETER. */
	public static final String CONTROLMODE_USE_DIALOG_PARAMETER = "org.eclipse.papyrus.infra.services.controlmode.useDialogParameter"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection) {
			if (selection.isEmpty() || ((IStructuredSelection) selection).size() > 1) {
				NotificationBuilder.createInfoPopup(EMPTY_SELECTION_MESSAGE).run();
				return null;
			}
			EObject eObjectToControl = EMFHelper.getEObject(((IStructuredSelection) selection).getFirstElement());
			try {
				TransactionalEditingDomain editingDomain = ServiceUtilsForEObject.getInstance().getTransactionalEditingDomain(eObjectToControl);
				if (getShowDialogParameterValue(event)) {
					IControlModeFragmentDialogProvider provider = getDialogProvider(eObjectToControl);
					Dialog dialog = provider.createDialog(Display.getCurrent().getActiveShell(), eObjectToControl, getDefaultLabelResource(eObjectToControl));
					if (dialog.open() == Window.OK) {
						ControlModeRequest controlRequest = provider.getControlRequest(dialog, editingDomain, eObjectToControl);
						IControlModeManager controlMng = ControlModeManager.getInstance();
						Diagnostic problems = controlMng.approveRequest(controlRequest);
						if ((problems != null) && (problems.getSeverity() >= Diagnostic.ERROR)) {
							IStatus status = BasicDiagnostic.toIStatus(problems);
							StatusManager.getManager().handle(status, StatusManager.SHOW);
						} else {
							ICommand controlCommand = controlMng.getControlCommand(controlRequest);
							editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(controlCommand));
						}
					}
				} else {
					URI resourceURI = getParameterisedURI(event);
					if (resourceURI == null) {
						// Use the default value as location
						resourceURI = computeDefaultURI(eObjectToControl.eResource(), getDefaultLabelResource(eObjectToControl));
					}
					ControlModeRequest controlRequest = ControlModeRequest.createUIControlModelRequest(editingDomain, eObjectToControl, resourceURI);
					IControlModeManager controlMng = ControlModeManager.getInstance();
					ICommand controlCommand = controlMng.getControlCommand(controlRequest);
					editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(controlCommand));
				}
			} catch (ServiceException e) {
				NotificationBuilder.createInfoPopup(NO_EDITING_DOMAIN_MESSAGE).run();
			}
		} else {
			NotificationBuilder.createInfoPopup(EMPTY_SELECTION_MESSAGE).run();
		}

		return null;
	}

	/**
	 * Gets the parameterised uri.
	 *
	 * @param event
	 *            the event
	 * @return the parameterised uri
	 */
	private URI getParameterisedURI(ExecutionEvent event) {
		ResourceLocationParameterValues parameterValues = null;
		Command command = event.getCommand();
		try {
			parameterValues = (ResourceLocationParameterValues) command.getParameter(ResourceLocationParameterValues.ID).getValues();
		} catch (ParameterValuesException e) {
			ControlModePlugin.log.error(e);
		} catch (NotDefinedException e) {
			ControlModePlugin.log.error(e);
		}
		return parameterValues.getResourceLocation();
	}

	public URI computeDefaultURI(Resource currentResource, String defaultName) {
		String ext = currentResource.getURI().fileExtension();
		URI uri = currentResource.getURI().trimSegments(1);
		uri = uri.appendSegment(defaultName).appendFileExtension(ext);
		return uri;
	}

	/**
	 *
	 * @param event
	 * @return true if the dialog will be displayed to the user, false if it will be bypassed and the default values will be used
	 */
	protected Boolean getShowDialogParameterValue(ExecutionEvent event) {
		Boolean showDialogValue = null;

		ControlModeCommandParameterValues parameterValues = null;
		try {
			Command command = event.getCommand();
			parameterValues = (ControlModeCommandParameterValues) command.getParameter(CONTROLMODE_USE_DIALOG_PARAMETER).getValues();
			showDialogValue = parameterValues.get(SHOW_DIALOG_PARAMETER);
			if (showDialogValue == null) {
				showDialogValue = true; // By default, the dialog is always displayed to the user
			}
		} catch (ParameterValuesException e) {
			ControlModePlugin.log.error(PARAMETER_VALUE_ERROR, e);
		} catch (NotDefinedException e) {
			ControlModePlugin.log.error(PARAMETER_ERROR, e);
		}
		return showDialogValue;
	}

	IControlModeFragmentDialogProvider getDialogProvider(EObject context) {
		try {
			ModelSet modelSet = ServiceUtilsForEObject.getInstance().getModelSet(context);
			return PlatformHelper.getAdapter(modelSet, IControlModeFragmentDialogProvider.class, IControlModeFragmentDialogProvider.DEFAULT);
		} catch (ServiceException e) {
			// can't get the model set? Odd
			ControlModePlugin.log.error(MODELSET_ERROR, e);
			return IControlModeFragmentDialogProvider.DEFAULT;
		}
	}

	/**
	 * Compute a default name for the new resource
	 * TODO plug in naming strategy
	 *
	 * @param eObject
	 * @return
	 */
	protected String getDefaultLabelResource(EObject eObject) {
		String defaultName = null;
		EStructuralFeature feature = eObject.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
		if (feature != null) {
			Object eGet = eObject.eGet(feature);
			if (eGet instanceof String) {
				defaultName = (String) eGet;
			}
		}
		if (defaultName == null) {
			defaultName = LabelHelper.getPrettyLabel(eObject);
			Pattern p = Pattern.compile("<<.*?>>|<.*?>"); //$NON-NLS-1$
			defaultName = p.matcher(defaultName).replaceAll("").trim(); //$NON-NLS-1$
		}
		StringBuilder b = new StringBuilder();
		for (Character c : defaultName.toCharArray()) {
			if (Character.isJavaIdentifierPart(c)) {
				b.append(c);
			} else {
				b.append("_"); //$NON-NLS-1$
			}
		}
		return b.toString();
	}

}
