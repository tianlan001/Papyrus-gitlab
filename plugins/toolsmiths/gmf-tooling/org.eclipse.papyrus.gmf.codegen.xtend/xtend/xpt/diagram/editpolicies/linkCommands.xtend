/*****************************************************************************
 * Copyright (c) 2007-2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Dmitry Stadnik (Borland) - creation logic was moved in commands
 * Michael Golubev (Borland) - [243151] explicit source/target for links
 *                              - #386838 - migrate to Xtend2
 * Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.editpolicies

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkEnd
import xpt.Common
import xpt.diagram.commands.CreateLinkCommand
import xpt.diagram.commands.ReorientLinkCommand
import xpt.editor.VisualIDRegistry
import xpt.providers.ElementTypes

/**
 * Start  		start of link creation. 
 *				User click to this editpart and start dragging with link tool.
 * Complete 	end of the command
 *				User points to this editpart as a link target and release mouse button.
 *
 * Outgoing 	the node is link source
 *				This element could be a source for this type of link.
 * Incoming		the node is link destination
 *				This element could be a target for this type of link.
 *
 * Parameters:
 *
 * 	diagram 	GenDiagram used to collect all defined links
 *
 *	this		Instance of GenLinkEnd for the element link could be creates to/from.
 *				This could be GenNode or GenLink in case of links to links, 
 *              in the latter case it is assumed that its a TypeLink (so its model facet is LinkTypeModelFacet), 
 *              because RefLinks don't have underlying semantic identity   
 *
*/
@Singleton class linkCommands {
	@Inject extension Utils_qvto;
	@Inject extension Common;

	@Inject ElementTypes xptElementTypes;
	@Inject ReorientLinkCommand xptReorientLinkCommand;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject CreateLinkCommand xptCreateLinkCommand;
	
	
	def linkCommands(GenLinkEnd it) '''
		«IF getAllPotentialLinks(it).size > 0»
			«createLinkCommands(it)»
		«ENDIF»
		«IF getReroutableTypeLinks(it).size > 0»
			«reorientTypeLinkCommands(it)»
		«ENDIF»
		«IF getReroutableRefLinks(it).size > 0»
			«reorientRefLinkCommands(it)»
		«ENDIF»
	'''

	def createLinkCommands(GenLinkEnd it) '''
		
		«IF it.eResource.allContents.filter(typeof (GenDiagram)).filter[genDiagram | genDiagram.usingElementTypeCreationCommand].size<1»

		«generatedMemberComment()»
		protected org.eclipse.gef.commands.Command getCreateRelationshipCommand(
				org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest req) {
			org.eclipse.gef.commands.Command command = req.getTarget() == null ?
			getStartCreateRelationshipCommand(req) : getCompleteCreateRelationshipCommand(req);
			return command != null ? command : super.getCreateRelationshipCommand(req);
		}
		«ENDIF»
		
		«generatedMemberComment()»
		protected org.eclipse.gef.commands.Command getStartCreateRelationshipCommand(
				org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest req) {
			org.eclipse.gmf.runtime.emf.type.core.IElementType requestElementType = req.getElementType();
			if(requestElementType == null) {
				return null;
			}
			org.eclipse.gmf.runtime.emf.type.core.IElementType baseElementType = requestElementType;
			
			«FOR l : getAllPotentialLinks(it)»
			«startLinkCommands(l, it)»
			«ENDFOR»
			return null;
		}
		
		«generatedMemberComment()»
		protected org.eclipse.gef.commands.Command getCompleteCreateRelationshipCommand(
				org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest req) {
			org.eclipse.gmf.runtime.emf.type.core.IElementType requestElementType = req.getElementType();
			if(requestElementType == null) {
				return null;
			}
			org.eclipse.gmf.runtime.emf.type.core.IElementType baseElementType = requestElementType;

			«FOR l : getAllPotentialLinks(it)»
			«completeLinkCommands(l, it)»
			«ENDFOR»
			return null;
		}
	'''

	def startLinkCommands(GenLink it, GenLinkEnd linkEnd) '''
		if («xptElementTypes.accessElementType(it)» == baseElementType) {
		«IF createStartLinkCommand(it, linkEnd)»

				return getGEFWrapper(new «xptCreateLinkCommand.qualifiedClassName(it)»(req,
					«IF createStartIncomingLinkCommand(it, linkEnd)»
						req.getTarget(), req.getSource()
					«ELSE»
						req.getSource(), req.getTarget()
					«ENDIF»
				));
			«ELSE»
				return null;
			«ENDIF»
	'''

	def completeLinkCommands(GenLink it, GenLinkEnd linkEnd) '''
		if («xptElementTypes.accessElementType(it)» == baseElementType) {
			«IF createCompleteLinkCommand(it, linkEnd)»

				return getGEFWrapper(new «xptCreateLinkCommand.qualifiedClassName(it)»(req,
					«IF createCompleteOutgoingLinkCommand(it, linkEnd)»
						req.getTarget(), req.getSource()
					«ELSE»
						req.getSource(), req.getTarget()
					«ENDIF»
				));
			«ELSE»
				return null;
			«ENDIF»
	'''

	def reorientTypeLinkCommands(GenLinkEnd it) '''
		«generatedMemberComment(
			'Returns command to reorient EClass based link. New link target or source\n' + 'should be the domain model element associated with this node.\n'
		)»
			protected org.eclipse.gef.commands.Command getReorientRelationshipCommand(
					org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest req) {
				String vid = getVisualID(req);
				if (vid != null) { 
					switch (vid) {
						«FOR link : getReroutableTypeLinks(it)»
						«reorientLinkCommandWithService(link) »
						«ENDFOR»
						«callReorientCommand(it)»
						«FOR link : getReroutableTypeLinks(it)»
						«reorientLinkCommandWithoutService(link) »
						«ENDFOR»
					}
				}
				return super.getReorientRelationshipCommand(req);
				}
		'''

	def reorientRefLinkCommands(GenLinkEnd it) '''
		
		«generatedMemberComment(
			'Returns command to reorient EReference based link. New link target or source\n' +
				'should be the domain model element associated with this node.\n'
		)»
		protected org.eclipse.gef.commands.Command getReorientReferenceRelationshipCommand(
				org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest req) {
			switch (getVisualID(req)) {
			«FOR l : getReroutableRefLinks(it)»
			«reorientLinkCommand(l)»
			«ENDFOR»
			}
			return super.getReorientReferenceRelationshipCommand(req);
		}
	'''

	def reorientLinkCommand(GenLink it) '''
		«xptVisualIDRegistry.caseVisualID(it)»
			return getGEFWrapper(new «xptReorientLinkCommand.qualifiedClassName(it)»(req));
	'''
	
	//This function writes only  : "case myLinkEditPart.VISUAL_ID:" 
	//for the link which uses the ReorientCommand provided by the EditService 
	def reorientLinkCommandWithService(GenLink it) '''
		«IF usingReorientService»
			«xptVisualIDRegistry.caseVisualID(it)»
		«ENDIF»
	'''

	// This function writes the code to call the ReorientCommand provided by the ReorientService
	def callReorientCommand(GenLinkEnd it) '''
		«var  views = getReroutableTypeLinks(it)»
		«IF views !== null && !views.empty»
			«IF !views.filter[view| view.usingReorientService].empty»
				org.eclipse.papyrus.infra.services.edit.service.IElementEditService provider =org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils.getCommandProvider(req.getRelationship());
				 if(provider == null) {
				           return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
				 }
				 // Retrieve re-orient command from the Element Edit service
				 org.eclipse.gmf.runtime.common.core.command.ICommand reorientCommand = provider.getEditCommand(req);
				          if(reorientCommand == null) {
				           return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
				          }
				 return getGEFWrapper(reorientCommand.reduce());
			«ENDIF»
		«ENDIF»
	'''

	// This function writes the code for the Links which uses their own ReorientCommand (the initial code)
	def reorientLinkCommandWithoutService(GenLink it) '''
		«IF !usingReorientService»
			«reorientLinkCommand(it)» 
		«ENDIF»
	'''

}
