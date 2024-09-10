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
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeModelFacet
import xpt.Common
import xpt.diagram.Utils_qvto
import xpt.providers.ElementInitializers

@com.google.inject.Singleton class CreateLinkCommand {
	@Inject extension Common;
	@Inject extension Utils_qvto;

	@Inject CreateLinkUtils xptCreateLinkUtils;
	@Inject MetaModel xptMetaModel;
	@Inject ElementInitializers xptElementInitializers;

	def className(GenLink it) '''«it.createCommandClassName»'''

	def packageName(GenLink it) '''«it.diagram.editCommandsPackageName»'''

	def qualifiedClassName(GenLink it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenLink it) '''«qualifiedClassName(it)»'''

	private def GenFeature sourceOrContainmentFeature(TypeLinkModelFacet it) {
		if(sourceMetaFeature === null) containmentMetaFeature else sourceMetaFeature;
	}

	def CreateLinkCommand(GenLink it) '''«Main(it)»'''

	def Main(GenLink it) '''
		«copyright(diagram.editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment()»
		public class «className(it)» extends org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand {
			«xptCreateLinkUtils.fields(it.modelFacet)»
		
			«generatedMemberComment()»
			public «className(it)»(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest request, org.eclipse.emf.ecore.EObject source, org.eclipse.emf.ecore.EObject target) {
				super(request.getLabel(), null, request);
				«xptCreateLinkUtils.initAndDeduceContainerIfNeeded(it.modelFacet)»
			}
			«xptCreateLinkUtils.canCreate(it.modelFacet, it)»
		
			«doExecuteWithResultMethod(it)»
		
			«doConfigure(it.modelFacet, it)»
		
			«generatedMemberComment()»
			protected void setElementToEdit(org.eclipse.emf.ecore.EObject element) {
				throw new UnsupportedOperationException();
			}
			«xptCreateLinkUtils.accessors(it.modelFacet)»
			«additions(it)»
		}
	'''

	def doExecuteWithResultMethod(GenLink it) '''
		«generatedMemberComment()»
		protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
			if (!canExecute()) {
				throw new org.eclipse.core.commands.ExecutionException("Invalid arguments in create link command"); «nonNLS()»
			}
			
			«execute(it.modelFacet, it)»
			
		}
	'''

	def dispatch execute(LinkModelFacet it, GenLink link) ''''''

	def dispatch execute(TypeLinkModelFacet it, GenLink link) '''
		«IF (sourceOrContainmentFeature(it).ecoreFeature.changeable && targetMetaFeature.ecoreFeature.changeable)»
			«xptMetaModel.NewInstance(it.metaClass, 'newElement')»
			«xptMetaModel.modifyFeature(it.containmentMetaFeature,
			if(hasContainerOtherThanSource(it)) 'getContainer()' else 'getSource()', containmentMetaFeature.genClass,
			'newElement')»
			«IF sourceMetaFeature !== null »
				«xptMetaModel.modifyFeature(it.sourceMetaFeature, 'newElement', metaClass, 'getSource()')»
			«ENDIF»
			«xptMetaModel.modifyFeature(it.targetMetaFeature, 'newElement', metaClass, 'getTarget()')»
			«IF hasExplicitChildFeature(it)»
				«IF sourceMetaFeature !== null »
					«xptMetaModel.modifyFeature(it.childMetaFeature, 'getContainer()', containmentMetaFeature.genClass, 'newElement')»
				«ELSE»
					«xptMetaModel.modifyFeature(it.childMetaFeature, 'getSource()', getSourceType(), 'newElement')»
				«ENDIF»
			«ENDIF»
			«initialize(it, link, 'newElement')»
			doConfigure(newElement, monitor, info);
			((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).setNewElement(«xptMetaModel.
			DowncastToEObject(it.metaClass, 'newElement')»);
			return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult(newElement);
		«ELSE»
			throw new UnsupportedOperationException();
		«ENDIF»
	'''

	def dispatch execute(FeatureLinkModelFacet it, GenLink link) '''
		«IF metaFeature.ecoreFeature.changeable»
			if (getSource() != null && getTarget() != null) {
				«xptMetaModel.modifyFeature(it.metaFeature, 'getSource()', getSourceType(), 'getTarget()')»
			}
			return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult();
		«ELSE»
			throw new UnsupportedOperationException();
		«ENDIF»
	'''

	def initialize(TypeModelFacet it, GenLink link, String newElementVar) //
	'''«xptElementInitializers.initMethodCall(link, it, newElementVar)»'''

	def dispatch doConfigure(LinkModelFacet it, GenLink link) ''''''

	/**
	 * TODO invocation of configure command should be optional, and better yet, 
	 * configuration should rather happen from EditPolicy then from within creation command 
	 */
	def dispatch doConfigure(TypeLinkModelFacet it, GenLink link) '''
		«generatedMemberComment()»
		protected void doConfigure(«xptMetaModel.QualifiedClassName(it.metaClass)» newElement, org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
			org.eclipse.gmf.runtime.emf.type.core.IElementType elementType = ((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).getElementType();
			org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest configureRequest = new org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest(getEditingDomain(), «xptMetaModel.
			DowncastToEObject(it.metaClass, 'newElement')», elementType);
			configureRequest.setClientContext(((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).getClientContext());
			configureRequest.addParameters(getRequest().getParameters());
			configureRequest.setParameter(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest.SOURCE, getSource());
			configureRequest.setParameter(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest.TARGET, getTarget());
			org.eclipse.gmf.runtime.common.core.command.ICommand configureCommand = elementType.getEditCommand(configureRequest);
			if (configureCommand != null && configureCommand.canExecute()) {
				configureCommand.execute(monitor, info);
			}
		}
	'''

	def additions(GenLink it) ''''''

}
