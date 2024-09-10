/*****************************************************************************
 * Copyright (c) 2007-2015, 2021 Borland Software Corporation, Montages, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
*  Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Anatolyi Tischenko - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.diagram.commands

import com.google.inject.Inject
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import xpt.Common
import xpt.diagram.Utils_qvto
import xpt.diagram.editpolicies.BaseItemSemanticEditPolicy

@com.google.inject.Singleton class ReorientLinkUtils {

	@Inject extension Common;
	@Inject extension Utils_qvto;

	@Inject MetaModel xptMetaModel;
	@Inject BaseItemSemanticEditPolicy xptBaseItemSemanticEditPolicy;

	/**
	* Expands to all accessor methods for link and it's ends.
	**/
	def accessors(GenLink it) '''
		«linkAccessor(modelFacet)»
		«oldSourceAccessor(modelFacet)»
		«newSourceAccessor(modelFacet)»
		«oldTargetAccessor(modelFacet)»
		«newTargetAccessor(modelFacet)»
	'''

	def dispatch linkAccessor(LinkModelFacet xptSelf) ''''''

	def dispatch linkAccessor(TypeLinkModelFacet xptSelf) '''
		«generatedMemberComment()»
		protected «xptMetaModel.QualifiedClassName(xptSelf.metaClass)» getLink() {
			return «xptMetaModel.CastEObject(xptSelf.metaClass, 'getElementToEdit()')»;
		}
	'''

	def dispatch oldSourceAccessor(LinkModelFacet xptSelf) ''''''

	def dispatch oldSourceAccessor(TypeLinkModelFacet xptSelf) '''
		«generatedMemberComment()»
		protected «xptMetaModel.QualifiedClassName(xptSelf.sourceType)» getOldSource() {
			return «xptMetaModel.CastEObject(xptSelf.sourceType, 'oldEnd')»;
		}
	'''

	def dispatch oldSourceAccessor(FeatureLinkModelFacet xptSelf) '''
		«generatedMemberComment()»
		protected «xptMetaModel.QualifiedClassName(xptSelf.sourceType)» getOldSource() {
			return «xptMetaModel.CastEObject(xptSelf.sourceType, 'referenceOwner')»;
		}
	'''

	def newSourceAccessor(LinkModelFacet xptSelf) '''
		«generatedMemberComment()»
		protected «xptMetaModel.QualifiedClassName(xptSelf.sourceType)» getNewSource() {
			return «xptMetaModel.CastEObject(xptSelf.sourceType, 'newEnd')»;
		}
	'''

	def oldTargetAccessor(LinkModelFacet xptSelf) '''
		«generatedMemberComment()»
		protected «xptMetaModel.QualifiedClassName(xptSelf.targetType)» getOldTarget() {
		return «xptMetaModel.CastEObject(xptSelf.targetType, 'oldEnd')»; 
		}
	'''

	def newTargetAccessor(LinkModelFacet xptSelf) '''
		«generatedMemberComment()»
		protected «xptMetaModel.QualifiedClassName(xptSelf.targetType)» getNewTarget() {
			return «xptMetaModel.CastEObject(xptSelf.targetType, 'newEnd')»;
		}
	'''

	/**
	Generates canExecute() method for the command that reorients link.
	Implementation should perform all static checks that command can be executed.
	**/
	def canReorient(LinkModelFacet it, GenLink link) '''
		«generatedMemberComment()»
		public boolean canExecute() {
			«checkLinkValidity(it)»
			if (reorientDirection == org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest.REORIENT_SOURCE) {
				return canReorientSource();
			}
			if (reorientDirection == org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest.REORIENT_TARGET) {
				return canReorientTarget();
			}
			return false;
		}
		
		«generatedMemberComment()»
		protected boolean canReorientSource() {
			«checkSourceRequestValidity(it, link)»
		}
		
		«generatedMemberComment()»
		protected boolean canReorientTarget() {
			«checkTargetRequestValidity(it, link)»
		}
	'''

	def dispatch checkLinkValidity(LinkModelFacet it) ''''''

	def dispatch checkLinkValidity(TypeLinkModelFacet xptSelf) '''
		if («xptMetaModel.NotInstance(xptSelf.metaClass, 'getElementToEdit()')») {
			return false;
		}
	'''

	def dispatch checkLinkValidity(FeatureLinkModelFacet xptSelf) '''
		if («xptMetaModel.NotInstance(xptSelf.sourceType, 'referenceOwner')») {
			return false;
		}
	'''

	def dispatch checkSourceRequestValidity(LinkModelFacet xptSelf, GenLink link) ''''''

	def dispatch checkSourceRequestValidity(TypeLinkModelFacet it, GenLink link) '''
		if (!(«xptMetaModel.IsInstance(it.sourceType, 'oldEnd')» && «xptMetaModel.IsInstance(it.sourceType, 'newEnd')»)) {
			return false;
		}
		«extractFeatureWithCheck(it.targetMetaFeature, 'getLink()', it.metaClass, 'target', it.targetType)»
		«checkLinkConstraint(it, link, 'getNewSource()', 'target')»
	'''

	/**
	When feature source is being reoriented oldEnd is the link target.
**/
	def dispatch checkSourceRequestValidity(FeatureLinkModelFacet it, GenLink link) '''
		if (!(«xptMetaModel.IsInstance(it.targetType, 'oldEnd')» && «xptMetaModel.IsInstance(it.sourceType, 'newEnd')»)) {
			return false;
		}
		return «xptBaseItemSemanticEditPolicy.canExistCall(it, link, 'getNewSource()', 'getOldTarget()')»;
	'''

	def dispatch checkTargetRequestValidity(LinkModelFacet it, GenLink link) ''''''

	def dispatch checkTargetRequestValidity(TypeLinkModelFacet it, GenLink link) '''
		if (!(«xptMetaModel.IsInstance(it.targetType, 'oldEnd')» && «xptMetaModel.IsInstance(targetType, 'newEnd')»)) {
			return false;
		}
		«IF (it.sourceMetaFeature !== null) »
			«extractFeatureWithCheck(it.sourceMetaFeature, 'getLink()', metaClass, 'source', getSourceType())»
		«ELSE»
			if (!(«xptMetaModel.IsContainerInstance(it.sourceType, 'getLink()', metaClass)»)) {
				return false;
			}
			«xptMetaModel.DeclareAndAssignContainer(it.sourceType, 'source', 'getLink()', metaClass)»
		«ENDIF»
		«checkLinkConstraint(it, link, 'source', 'getNewTarget()')»
	'''

	def dispatch checkTargetRequestValidity(FeatureLinkModelFacet it, GenLink link) '''
		if (!(«xptMetaModel.IsInstance(it.targetType, 'oldEnd')» && «xptMetaModel.IsInstance(it.targetType, 'newEnd')»)) {
			return false;
		}
		return «xptBaseItemSemanticEditPolicy.canExistCall(it, link, 'getOldSource()', 'getNewTarget()')»;
	'''

	def extractFeatureWithCheck(GenFeature it, String containerVar, GenClass containerMetaClass, String _var,
		GenClass varMetaClass) '''
		«IF it.ecoreFeature.many»
			if («xptMetaModel.getFeatureValue(it, containerVar, containerMetaClass)».size() != 1) {
				return false;
			}
			«xptMetaModel.DeclareAndAssign2(varMetaClass, _var, containerVar, containerMetaClass, it, 'get(0)', true)»
		«ELSE»
			«xptMetaModel.DeclareAndAssign(varMetaClass, _var, containerVar, containerMetaClass, it)»
		«ENDIF»
	'''

	def checkLinkConstraint(TypeLinkModelFacet it, GenLink link, String sourceVar, String targetVar) '''
«IF hasContainerOtherThanSource(it)»
	if (!(«xptMetaModel.IsContainerInstance(containmentMetaFeature.genClass, 'getLink()', metaClass)»)) {
		return false;
	}
	«xptMetaModel.DeclareAndAssignContainer(containmentMetaFeature.genClass, 'container', 'getLink()', metaClass)»
«ENDIF»
	return «xptBaseItemSemanticEditPolicy.canExistCall(it, link, 'container', 'getLink()', sourceVar, targetVar)»;
'''

	/**
	Generates doExecuteWithResult() method for the command that reorients link.
	Implementation should throw ExecutionException if it can't execute the command.
**/
	def reorient(LinkModelFacet it) '''
		
		«generatedMemberComment()»
		protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(
				org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info)
				throws org.eclipse.core.commands.ExecutionException {
			if (!canExecute()) {
			throw new org.eclipse.core.commands.ExecutionException("Invalid arguments in reorient link command"); «nonNLS()»
			}
			if (reorientDirection == org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest.REORIENT_SOURCE) {
			return reorientSource();
			}
			if (reorientDirection == org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest.REORIENT_TARGET) {
			return reorientTarget();
			}
			throw new IllegalStateException();
		}
		
		«generatedMemberComment()»
		protected org.eclipse.gmf.runtime.common.core.command.CommandResult reorientSource() throws org.eclipse.core.commands.ExecutionException {
			«reorientSource(it)»
		}
		
		«generatedMemberComment()»
		protected org.eclipse.gmf.runtime.common.core.command.CommandResult reorientTarget() throws org.eclipse.core.commands.ExecutionException {
			«reorientTarget(it)»
		}
	'''

	def dispatch reorientSource(LinkModelFacet it) ''''''

	/**
	 * Shouldn't we change link container here?
	 * [artem] especially when there's explicit childMetaFeature and 
	 * we changed source to another, but didn't change the container. Perhaps,
	 * makes sense to deduceContainer() using new source?
	**/
	def dispatch reorientSource(TypeLinkModelFacet it) '''
		«IF (if( sourceMetaFeature === null ) containmentMetaFeature else sourceMetaFeature).ecoreFeature.changeable»
			«IF sourceMetaFeature !== null »
				«changeTarget(sourceMetaFeature, 'getLink()', metaClass, 'getOldSource()', 'getNewSource()')»
			«ELSE»
				«changeSource(if(hasExplicitChildFeature(it)) childMetaFeature else containmentMetaFeature, 'getLink()',
			'getOldSource()', 'getNewSource()', getSourceType())»
			«ENDIF»
			return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult(getLink());
		«ELSE»
			throw new UnsupportedOperationException();
		«ENDIF»
	'''

	/**
	 *	When feature source is being reoriented oldEnd is the link target.
	**/
	def dispatch reorientSource(FeatureLinkModelFacet it) '''
		«IF metaFeature.ecoreFeature.changeable»
			«changeSource(it.metaFeature, 'getOldTarget()', 'getOldSource()', 'getNewSource()', getSourceType())»
			return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult(referenceOwner);
		«ELSE»
			throw new UnsupportedOperationException();
		«ENDIF»
	'''

	def dispatch reorientTarget(LinkModelFacet it) ''''''

	def dispatch reorientTarget(TypeLinkModelFacet it) '''
		«IF targetMetaFeature.ecoreFeature.changeable»
			«changeTarget(targetMetaFeature, 'getLink()', metaClass, 'getOldTarget()', 'getNewTarget()')»
			return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult(getLink());
		«ELSE»
			throw new UnsupportedOperationException();
		«ENDIF»
	'''

	def dispatch reorientTarget(FeatureLinkModelFacet it) '''
		«IF metaFeature.ecoreFeature.changeable»
			«changeTarget(metaFeature, 'getOldSource()', getSourceType(), 'getOldTarget()', 'getNewTarget()')»
			return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult(referenceOwner);
		«ELSE»
			throw new UnsupportedOperationException();
		«ENDIF»
	'''

	/**
 *	Replace old target with the new one in the source.
 **/
	def changeTarget(GenFeature it, String sourceVar, GenClass sourceVarGenClass, String oldTargetVar,
		String newTargetVar) ///
	'''
		«xptMetaModel.replaceFeatureValue(it, sourceVar, sourceVarGenClass, oldTargetVar, newTargetVar)»
	'''

	/**
 *	Move target from old source to the new one.
 **/
	def changeSource(GenFeature it, String targetVar, String oldSourceVar, String newSourceVar,
		GenClass sourceVarGenClass) ///
	'''
		«xptMetaModel.moveFeatureValue(it, oldSourceVar, newSourceVar, sourceVarGenClass, targetVar)»
	'''

}