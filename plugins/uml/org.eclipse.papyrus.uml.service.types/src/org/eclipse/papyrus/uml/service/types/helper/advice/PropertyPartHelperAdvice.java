/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

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
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;


public class PropertyPartHelperAdvice extends AbstractEditHelperAdvice {



	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {

		List<EClass> subs = EMFHelper.getSubclassesOf(UMLPackage.eINSTANCE.getProperty(), true);
		if (subs.contains(request.getElementType().getEClass())) {

			return new CreateElementCommand(request) {

				/**
				 * <pre>
				 * Checks if the Property can be created on the Property (means appearing graphically on the property).
				 * The Property must be typed by a StructuredClassifier (in other words, an element that
				 * can own Property). The new Property is owned be the type of the Property.
				 * 
				 * {@inheritDoc}
				 * </pre>
				 */
				@Override
				public boolean canExecute() {
					setElementToEdit(((CreateElementRequest) getRequest()).getContainer());
					Property target = (Property) getElementToEdit();

					if ((target.getType() != null) && (target.getType() instanceof StructuredClassifier)) {
						return true;
					} else {

						return false;
					}
				}

				/**
				 * <pre>
				 * Custom creation of the Property type :
				 * - resolve the Property type
				 * - add a new Property on this type.
				 * 
				 * {@inheritDoc}
				 * </pre>
				 */
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

					Property newElement = UMLFactory.eINSTANCE.createProperty();

					StructuredClassifier owner = (StructuredClassifier) ((Property) getElementToEdit()).getType();
					owner.getOwnedAttributes().add(newElement);


					((CreateElementRequest) getRequest()).setNewElement(newElement);
					return CommandResult.newOKCommandResult(newElement);
				}
			};
		}

		return super.getAfterCreateCommand(request);
	}



}
