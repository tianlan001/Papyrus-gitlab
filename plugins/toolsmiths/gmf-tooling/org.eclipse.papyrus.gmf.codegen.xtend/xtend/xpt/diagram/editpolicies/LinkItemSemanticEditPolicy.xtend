/*****************************************************************************
 * Copyright (c) 2007-2012, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexander Shatalin (Borland) - initial API and implementation
 * Michael Golubev (Borland) - [243151] explicit source/target for links
 *                           - #386838 - migrate to Xtend2
 * Vincent Lorenzo (CEA-LIST) - 
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.editpolicies

import com.google.inject.Inject
import impl.diagram.commands.DeleteLinkCommand
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import xpt.Common
import xpt.Common_qvto
import utils.UtilsItemSemanticEditPolicy


@com.google.inject.Singleton class LinkItemSemanticEditPolicy {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension LinkUtils_qvto;

	@Inject BaseItemSemanticEditPolicy xptBaseItemSemanticEditPolicy;
	@Inject linkCommands xptLinkCommands;
	@Inject DeleteLinkCommand xptDeleteLinkCommand;
	@Inject extension DeleteLinkCommand
	@Inject extension UtilsItemSemanticEditPolicy
	

	def className(GenLink it) '''«it.itemSemanticEditPolicyClassName»'''

	def packageName(GenLink it) '''«it.getDiagram().editPoliciesPackageName»'''

	def qualifiedClassName(GenLink it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenLink it) '''«qualifiedClassName(it)»'''

	def LinkItemSemanticEditPolicy(GenLink it) '''
		«copyright(diagram.editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment()»
		public class «className(it)» extends «xptBaseItemSemanticEditPolicy.qualifiedClassName(it.diagram)» {
		
			«xptBaseItemSemanticEditPolicy.defaultConstructor(it)»
		
			«classBody(it)»
			
			«additions(it)»
		}
	'''

	def classBody(GenLink it) '''
		«getDestroySemanticCommand(it.modelFacet, it)»
		«IF isTypeLink(it)»«xptLinkCommands.linkCommands(it)»«ENDIF»
	'''

	def dispatch getDestroySemanticCommand(LinkModelFacet it, GenLink genLink) '''
		«ERROR('Unsupported model facet: ' + it + ", for link: " + genLink)»
	'''

	def dispatch getDestroySemanticCommand(FeatureLinkModelFacet it, GenLink genLink) '''
		«generatedMemberComment()»
		protected org.eclipse.gef.commands.Command getDestroyReferenceCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest req) {
			return getGEFWrapper(«xptDeleteLinkCommand.newDeleteReferenceLinkCommand(it, genLink, 'req')»);
		}
	'''

	def dispatch getDestroySemanticCommand(TypeLinkModelFacet it, GenLink genLink) '''
		««« Test to know which delete command should be used in the generated code : "Traditional Delete Command" or the Delete Service

		«IF genLink.usingDeleteService»
		
			«generatedMemberComment»
			«getDestroyElementCommandByService(it)»
		«ELSE»
			«generatedMemberComment»
			protected org.eclipse.gef.commands.Command getDestroyElementCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest req) {
				org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand cmd = new org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand(getEditingDomain(), null);
				cmd.setTransactionNestingEnabled(true);
				java.util.List<org.eclipse.emf.ecore.EObject> todestroy=new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
				todestroy.add(req.getElementToDestroy());
				//cmd.add(new org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand(req));
				cmd.add(new org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper(new org.eclipse.emf.edit.command.DeleteCommand(getEditingDomain(),todestroy )));
				return getGEFWrapper(cmd.reduce());
				//return getGEFWrapper(«newDeleteLinkWithClassCommand(it,genLink, 'req')»);
			}
		«ENDIF»	
	'''

	def additions(GenLink it) ''''''

}
