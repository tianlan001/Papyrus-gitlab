/*******************************************************************************
 * Copyright (c) 2009-2013 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package impl.diagram.commands

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import xpt.Common_qvto
import xpt.GenModelUtils_qvto

@com.google.inject.Singleton class DeleteLinkCommand {
	@Inject extension GenModelUtils_qvto;
	@Inject extension Common_qvto;

	def newInstance(GenLink it, String requestVar) //
	'''«newDeleteLinkCommand(it.modelFacet, it, requestVar)»'''

	def newRequest(GenLink it, String requestVar, String edgeVar) //
	'''«newDestroyRequest(it.modelFacet, it, requestVar, edgeVar)»'''

	def dispatch newDeleteLinkCommand(LinkModelFacet it, GenLink genLink, String requestVar) //
	'''«ERROR("Unsupported model facet: " + it + ", link: " + genLink)»'''

	def dispatch newDeleteLinkCommand(TypeLinkModelFacet it, GenLink genLink, String requestVar) //
	'''«newDeleteLinkWithClassCommand(it, genLink, requestVar)»'''

	def dispatch newDeleteLinkCommand(FeatureLinkModelFacet it, GenLink genLink, String requestVar) // 
	'''«newDeleteReferenceLinkCommand(it, genLink, requestVar)»'''

	def newDeleteReferenceLinkCommand(FeatureLinkModelFacet it, GenLink genLink, String destroyReferenceRequest) '''
		new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyReferenceCommand(«destroyReferenceRequest») «IF isContains(metaFeature)» {
			protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor progressMonitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
				org.eclipse.emf.ecore.EObject referencedObject = getReferencedObject();
				org.eclipse.emf.ecore.resource.Resource resource = referencedObject.eResource();
				org.eclipse.gmf.runtime.common.core.command.CommandResult result = super.doExecuteWithResult(progressMonitor, info);
				if (resource != null) {
					resource.getContents().add(referencedObject);
				}
				return result;
			}
		}«ENDIF»
	'''

	def newDeleteLinkWithClassCommand(TypeLinkModelFacet it, GenLink genLink, String destroyElementRequest) //
	'''new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand(«destroyElementRequest»)'''

	def dispatch newDestroyRequest(LinkModelFacet it, GenLink genLink, String requestVar, String edgeVar) '''«ERROR(
		"Unsupported model facet: " + it + ", link: " + genLink)»'''

	def dispatch newDestroyRequest(TypeLinkModelFacet it, GenLink genLink, String requestVar, String edgeVar) '''
org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest «requestVar» = new org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest(«edgeVar».getElement(), false);
'''

	/** 
	 * FIXME source not always container, target is not always referenced object. And containerReference is known at generation time
	 * */
	def dispatch newDestroyRequest(FeatureLinkModelFacet it, GenLink genLink, String requestVar, String edgeVar) '''
org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest «requestVar» = new org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest(«edgeVar».getSource().getElement(), null, «edgeVar».getTarget().getElement(), false);
'''
}
