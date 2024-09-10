/*****************************************************************************
 * Copyright (c) 2007, 2010, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.diagram.commands

import com.google.inject.Inject
import com.google.inject.Singleton
import metamodel.MetaModel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeModelFacet
import xpt.Common
import xpt.OclMigrationProblems_qvto
import xpt.diagram.Utils_qvto
import xpt.providers.ElementInitializers

@Singleton class CreateNodeCommand {
	@Inject extension Common;
	@Inject extension MetaModel
	@Inject extension Utils_qvto;
	@Inject extension OclMigrationProblems_qvto;

	@Inject MetaModel xptMetaModel;
	@Inject ElementInitializers xptElementInitializers;

	def className(GenNode it) '''«it.createCommandClassName»'''

	def packageName(GenNode it) '''«it.getDiagram().editCommandsPackageName»'''

	def qualifiedClassName(GenNode it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNode it) '''«qualifiedClassName(it)»'''

	def CreateNodeCommand(GenNode it) '''
		«copyright(it.diagram.editorGen)»
		package «packageName(it)»;
		
		
		
		«generatedClassComment()»
		public class «className(it)» extends org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand {
			
			«IF ! it.modelFacet.isPhantomElement()»
				«generatedMemberComment()»
				private org.eclipse.gmf.runtime.notation.Diagram diagram = null;
			«ENDIF»
		
			«_constructor(it)»
		
			«getElementToEdit(it)»
		
			«canExecuteMethod(it)»
		
			«doExecuteWithResultMethod(it)»
		
			«doConfigureMethod(it)»
			
			«additions(it)»	
		}
	'''

	def _constructor(GenNode it)  '''
		«generatedMemberComment()»
		public «className(it)»(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest req, org.eclipse.gmf.runtime.notation.Diagram diagram) {
			super(req.getLabel(), null, req);
			«IF ! it.modelFacet.isPhantomElement()»
			this.diagram = diagram;
			«ENDIF»
		}
	'''

	/**
	 * TODO: either use setElementToEdit, or generate downcasted version (which may be troublesome if containment and child features point to a different parent) 
	 */
	def getElementToEdit(GenNode it) '''
			«generatedMemberComment('FIXME: replace with setElementToEdit()')»
		protected org.eclipse.emf.ecore.EObject getElementToEdit() {
			org.eclipse.emf.ecore.EObject container = ((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).getContainer();
			if (container instanceof org.eclipse.gmf.runtime.notation.View) {
				container = ((org.eclipse.gmf.runtime.notation.View) container).getElement();
			}
			return container;
		}
	'''

	def doExecuteWithResultMethod(GenNode it) '''
		«generatedMemberComment()»
		protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
			«IF it.modelFacet.metaClass.ecoreClass.abstract != true »
				«IF it.modelFacet.isPhantomElement()»
					«phantomElementCreation(it.modelFacet, it, 'newElement')»
				«ELSE»
					«normalElementCreation(it.modelFacet, it, 'newElement')»
				«ENDIF»
				«initialize(it.modelFacet, it, 'newElement')»
				«IF true/*FIXME boolean needsExternalConfiguration*/»
					doConfigure(newElement, monitor, info);
				«ENDIF»
				((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).setNewElement(«xptMetaModel.DowncastToEObject(it.modelFacet.metaClass, 'newElement')»);
				return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult(newElement);
		«ELSE»
			throw new UnsupportedOperationException("Unimplemented operation (abstract domain element).");
		«ENDIF»
		}
	'''

	/**
	 * Unlike original CreateElementCommand, we don't keep track of IStatus from configureCommand.execute,
	 * nor allow status setting from doDefaultCreation. The reason is ICommandProxy#execute implementation,
	 * which ignores any status from wrapped ICommand. Besides, both CommandResult and IStatus seems too much to me.
	 */
	def doConfigureMethod(GenNode it) '''
		«generatedMemberComment()»
		protected void doConfigure(«xptMetaModel.QualifiedClassName(it.modelFacet.metaClass)» newElement, org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
			org.eclipse.gmf.runtime.emf.type.core.IElementType elementType = ((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).getElementType();
			org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest configureRequest = new org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest(getEditingDomain(), «xptMetaModel.
			DowncastToEObject(it.modelFacet.metaClass, 'newElement')», elementType);
			configureRequest.setClientContext(((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).getClientContext());
			configureRequest.addParameters(getRequest().getParameters());
			org.eclipse.gmf.runtime.common.core.command.ICommand configureCommand = elementType.getEditCommand(configureRequest);
			if (configureCommand != null && configureCommand.canExecute()) {
				configureCommand.execute(monitor, info);
			}
		}
	'''

	def canExecuteMethod(GenNode it) '''
		«generatedMemberComment()»
		public boolean canExecute() {
		«IF modelFacet.isPhantomElement()»
			return true;
		«ELSE»
			«canExecute_Normal(it.modelFacet)»
		«ENDIF»
		}
	'''

	def canExecute_Normal(TypeModelFacet it) '''
	«IF containmentMetaFeature !== null »
		«IF  containmentMetaFeature.ecoreFeature !== null »
			«IF ! isUnbounded(containmentMetaFeature.ecoreFeature) || (childMetaFeature != containmentMetaFeature && ! isUnbounded(childMetaFeature.ecoreFeature))»
				«IF ! isUnbounded(containmentMetaFeature.ecoreFeature)»
				«DeclareAndAssign(containmentMetaFeature.genClass,'container', 'getElementToEdit()') »
						«IF isSingleValued(containmentMetaFeature.ecoreFeature)»
						if («getFeatureValue(containmentMetaFeature,'container', containmentMetaFeature.genClass) » != null) {
							«ELSE»
						if (« getFeatureValue(containmentMetaFeature,'container', containmentMetaFeature.genClass)».size() >= «containmentMetaFeature.ecoreFeature.upperBound») {
							«ENDIF»
							return false;
						}
						«ENDIF»
						«IF childMetaFeature != containmentMetaFeature && ! isUnbounded(childMetaFeature.ecoreFeature)»
						«IF isSingleValued(childMetaFeature.ecoreFeature)»
							if («getFeatureValue(childMetaFeature,'container', containmentMetaFeature.genClass)  » != null) {
								«ELSE»
								if («getFeatureValue(childMetaFeature,'container', containmentMetaFeature.genClass)  ».size() >= «childMetaFeature.ecoreFeature.upperBound») {
								«ENDIF»
								return false;
							}
						«ENDIF»
			«ENDIF»
		«ENDIF»
	«ENDIF»
	
	org.eclipse.emf.ecore.EObject target = getElementToEdit();
	org.eclipse.papyrus.infra.viewpoints.policy.ModelAddData data = org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker.getFor(target).getChildAddData(diagram, target.eClass(), «MetaClass(metaClass)»);
	return data.isPermitted();

	'''

	def phantomElementCreation(TypeModelFacet it, GenNode node, String varName) '''
		// Uncomment to put "phantom" objects into the diagram file.		
		// org.eclipse.emf.ecore.resource.Resource resource = 
		// 		((org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest) getRequest()).getContainer().eResource();
		// if (resource == null) {
		// 	return null;
		// }
		org.eclipse.emf.ecore.resource.Resource resource = getElementToEdit().eResource();
		«xptMetaModel.NewInstance(it.metaClass, varName)»
		resource.getContents().add(«xptMetaModel.DowncastToEObject(it.metaClass, varName)»);
	'''

	def normalElementCreation(TypeModelFacet it, GenNode node, String varName) '''
		«xptMetaModel.NewInstance(it.metaClass, varName)»
		
		org.eclipse.emf.ecore.EObject target = getElementToEdit();
				org.eclipse.papyrus.infra.viewpoints.policy.ModelAddData data = org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker.getFor(target).getChildAddData(diagram, target, «varName»);
				if (data.isPermitted()) {
					if (data.isPathDefined()) {
						if (!data.execute(target, «varName»)) {
							return org.eclipse.gmf.runtime.common.core.command.CommandResult.newErrorCommandResult("Failed to follow the policy-specified for the insertion of the new element");
						}
					} else {
		«IF containmentMetaFeature !== null »
			«xptMetaModel.DeclareAndAssign(it.containmentMetaFeature.genClass, 'qualifiedTarget', 'target')»
			«xptMetaModel.modifyFeature(containmentMetaFeature, 'qualifiedTarget', containmentMetaFeature.genClass, varName)»
		«ELSE»
			//
			// FIXME no containment feature found in the genmodel, toolsmith need to manually write code here to add «varName» to a parent
			//
		«ENDIF»
		
					}
				} else {
					return org.eclipse.gmf.runtime.common.core.command.CommandResult.newErrorCommandResult("The active policy restricts the addition of this element");
				}

		«IF hasExplicitChildFeature(it)»
			«xptMetaModel.DeclareAndAssign(it.childMetaFeature.genClass, 'childHolder', 'getElementToEdit()')»
			«xptMetaModel.modifyFeature(it.childMetaFeature, 'childHolder', childMetaFeature.genClass, varName)»
		«ENDIF»
	'''

	def initialize(TypeModelFacet it, GenNode node, String newElementVar) // 
		'''«xptElementInitializers.initMethodCall(node, it, newElementVar)»'''

	def additions(GenNode it) ''''''
}
