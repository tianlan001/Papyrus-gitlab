/*****************************************************************************
 * Copyright (c) 2007, 2009, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.editpolicies

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeModelFacet
import xpt.Common
import xpt.QualifiedClassNameProvider
import xpt.providers.ElementTypes
import metamodel.MetaModel

@Singleton class childContainerCreateCommand {
	
	@Inject extension Common;
	@Inject extension QualifiedClassNameProvider;
	
	@Inject ElementTypes xptElementTypes;
 	@Inject extension ElementTypes;
 	@Inject extension MetaModel;
 
 	def childContainerCreateCommand(Iterable<? extends GenNode> nodes) '''
	«IF ! nodes.empty»

	«generatedMemberComment()»
	protected org.eclipse.gef.commands.Command getCreateCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest req) {
				org.eclipse.gmf.runtime.emf.type.core.IElementType requestElementType = req.getElementType();
		if(requestElementType == null) {
			return super.getCreateCommand(req);
		}


	«FOR n : nodes»
		«IF !n.sansDomain»
			«childNodeCreateCommand(n.modelFacet, n)»
		«ENDIF»
	«ENDFOR»
		return super.getCreateCommand(req);
	}
	«ENDIF»
	'''


	def childNodeCreateCommand(GenNode node) '''
	if («xptElementTypes.accessElementType(node)» == req.getElementType()) {
		return getGEFWrapper(new «getCreateCommandQualifiedClassName(node)»(req));
	}
	'''

	def childNodeCreateCommand(TypeModelFacet it,GenNode node)'''
	if («accessElementType(node)» == requestElementType) {
		«IF it.eResource.allContents.filter(typeof (GenDiagram)).filter[genDiagram | genDiagram.usingElementTypeCreationCommand].size>0»
		// adjust the containment feature
		org.eclipse.emf.ecore.EReference containmentFeature = «MetaFeature(it.childMetaFeature)»;
		req.setContainmentFeature(containmentFeature);
		«ENDIF»

		«IF it.eResource.allContents.filter(typeof (GenDiagram)).filter[genDiagram | genDiagram.usingElementTypeCreationCommand].size>0»
		return getGEFWrapper(getSemanticCreationCommand(req));
		«ELSE»
		return getGEFWrapper(new «node.getCreateCommandQualifiedClassName()»(req, org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils.getDiagramFrom(getHost())));
		«ENDIF»
		
	}
	'''

}