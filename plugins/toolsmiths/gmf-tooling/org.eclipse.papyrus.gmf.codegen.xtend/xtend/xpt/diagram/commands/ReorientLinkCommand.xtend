/*******************************************************************************
 * Copyright (c) 2007-2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.diagram.commands

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import xpt.Common

@com.google.inject.Singleton class ReorientLinkCommand {
	@Inject extension Common;
	@Inject ReorientLinkUtils xptReorientLinkUtils;

	def className(GenLink it) '''«it.reorientCommandClassName»'''

	def packageName(GenLink it) '''«diagram.editCommandsPackageName»'''

	def qualifiedClassName(GenLink it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenLink it) '''«qualifiedClassName(it)»'''

	def ReorientLinkCommand(GenLink it) '''
«copyright(diagram.editorGen)»
package «packageName(it)»;

«generatedClassComment()»
public class «className(it)» extends org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand {

	«generatedMemberComment()»
	private final int reorientDirection;

	«generatedMemberComment()»
	private final org.eclipse.emf.ecore.EObject oldEnd;

	«generatedMemberComment()»
	private final org.eclipse.emf.ecore.EObject newEnd;

	«generatedMemberComment()»
	public «className(it)»(org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}
	
	«xptReorientLinkUtils.canReorient(it.modelFacet, it)»
	«xptReorientLinkUtils.reorient(it.modelFacet)»
	«xptReorientLinkUtils.accessors(it)»
«additions(it)»
}
'''
	
	def additions(GenLink it) ''''''

}
