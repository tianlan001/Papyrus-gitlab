/*****************************************************************************
 * Copyright (c) 2010, 2014, 2018 CEA LIST and others.
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
 * 	Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 440263
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 530026
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;

/**
 * <pre>
 * This is an advice helper used to initialize namedElement.
 * 
 * In particular the name of such elements is initialized in order 
 * to have a unique name for a specific type of element per {@link Namespace}.
 * 
 * </pre>
 */
public class NamedElementInitializerHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		return new ConfigureElementCommand(request) {

			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

				NamedElement element = (NamedElement) request.getElementToConfigure();

				Object parameter = request.getParameter(RequestParameterConstants.NAME_TO_SET);
				String initializedName = null;
				if (parameter instanceof String) {
					initializedName = (String) parameter;
				} else {
					parameter = request.getParameter(RequestParameterConstants.BASE_NAME_TO_SET);
					if(null==parameter) {
						initializedName = NamedElementUtil.getDefaultNameWithIncrement(element, element.eContainer().eContents());
					}else if (parameter instanceof String && ((String)parameter).length()>0) {//a base name is provided
						//if(length==0) we do nothing, according to the documentation of RequestParameterConstants.BASE_NAME_TO_SET
						initializedName = NamedElementUtil.getDefaultNameWithIncrementFromBase((String)parameter, element.eContainer().eContents());
					} 
				}
				// Initialize the element name based on the created IElementType
				if (initializedName != null) {
					element.setName(initializedName);
				}

				return CommandResult.newOKCommandResult(element);
			}
		};
	}

}
