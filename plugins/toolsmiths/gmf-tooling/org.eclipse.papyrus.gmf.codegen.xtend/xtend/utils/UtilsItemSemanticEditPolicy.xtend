/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Artal and others
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
 * Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package utils

import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet

@Singleton class UtilsItemSemanticEditPolicy {

	//Command for the EditPart which use the Delete Service 
	def getDestroyElementCommandByService(GenNode i) '''
		protected org.eclipse.gef.commands.Command getDestroyElementCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest req) {
			org.eclipse.emf.ecore.EObject selectedEObject = req.getElementToDestroy();
			org.eclipse.papyrus.infra.services.edit.service.IElementEditService provider =org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils.getCommandProvider(selectedEObject);
			if(provider != null) {
				// Retrieve delete command from the Element Edit service
				org.eclipse.gmf.runtime.common.core.command.ICommand deleteCommand = provider.getEditCommand(req);
		
				if(deleteCommand != null) {
			return new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(deleteCommand);
				}
			}
			return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
			}
	'''

	def getDestroyElementCommandByService(TypeLinkModelFacet it) '''
		protected org.eclipse.gef.commands.Command getDestroyElementCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest req) {
			org.eclipse.emf.ecore.EObject selectedEObject = req.getElementToDestroy();
			org.eclipse.papyrus.infra.services.edit.service.IElementEditService provider =org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils.getCommandProvider(selectedEObject);
			if(provider != null) {
				// Retrieve delete command from the Element Edit service
				org.eclipse.gmf.runtime.common.core.command.ICommand deleteCommand = provider.getEditCommand(req);
		
				if(deleteCommand != null) {
			return new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(deleteCommand);
				}
			}
			return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
			}
	'''
}
