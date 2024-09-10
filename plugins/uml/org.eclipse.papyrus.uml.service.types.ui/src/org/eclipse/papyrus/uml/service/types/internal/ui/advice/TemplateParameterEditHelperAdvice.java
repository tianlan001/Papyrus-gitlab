/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.internal.ui.advice;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.uml.service.types.internal.ui.dialogs.TemplateParameterConfigurationDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.TemplateParameter;
import org.eclipse.uml2.uml.TemplateSignature;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

public class TemplateParameterEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterCreateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {
		return new CreateElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				TemplateParameter newElement = UMLFactory.eINSTANCE.createTemplateParameter();
				TemplateSignature owner = (TemplateSignature)getElementToEdit();
				owner.getOwnedParameters().add(newElement);
				TemplateSignature childHolder = (TemplateSignature)getElementToEdit();
				childHolder.getParameters().add(newElement);
				List<EClass> eclassnotWanted = new ArrayList<EClass>();
				eclassnotWanted.add(UMLPackage.eINSTANCE.getClassifier());
				eclassnotWanted.add(UMLPackage.eINSTANCE.getOperation());
				eclassnotWanted.add(UMLPackage.eINSTANCE.getConnectableElement());
				TemplateParameterConfigurationDialog configurationDialog = new TemplateParameterConfigurationDialog(new Shell(), SWT.APPLICATION_MODAL, UMLPackage.eINSTANCE.getPackageableElement(), eclassnotWanted);
				configurationDialog.setOwner(childHolder);
				configurationDialog.open();
				newElement.setParameteredElement(configurationDialog.getParameterableElement());
				newElement.setDefault(configurationDialog.getDefaultparameterableElement());
				return CommandResult.newOKCommandResult(newElement);
			}
		};
	}
}
