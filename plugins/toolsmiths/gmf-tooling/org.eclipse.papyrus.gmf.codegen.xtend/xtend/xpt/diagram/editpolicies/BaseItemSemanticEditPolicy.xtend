/*****************************************************************************
 * Copyright (c) 2007, 2014, 2021 Borland Software Corporation, Christian W. Damus, CEA LIST, Artal and others
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
 * Christian W. Damus - bug 451230
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up + The reverse var seems to be inverted for 'contains' call
 *****************************************************************************/
package xpt.diagram.editpolicies

import com.google.inject.Inject
import com.google.inject.Singleton
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionProviderBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenJavaExpressionProvider
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.ValueExpression
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.OclMigrationProblems_qvto
import xpt.QualifiedClassNameProvider
import xpt.editor.VisualIDRegistry
import xpt.expressions.getExpression
import xpt.providers.ElementTypes

@Singleton class BaseItemSemanticEditPolicy {
	@Inject extension CodeStyle;
	@Inject extension Common;
	@Inject extension VisualIDRegistry

	@Inject extension Common_qvto;
	@Inject extension xpt.diagram.Utils_qvto;
	@Inject extension OclMigrationProblems_qvto;
	@Inject extension Utils_qvto;
	@Inject extension QualifiedClassNameProvider;

	@Inject Activator xptPluginActivator;
	@Inject MetaModel xptMetaModel;
	@Inject getExpression xptGetExpression;
	@Inject ElementTypes xptElementTypes;

	def className(GenDiagram it) '''«it.baseItemSemanticEditPolicyClassName»'''

	def packageName(GenDiagram it) '''«it.getDiagram().editPoliciesPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def BaseItemSemanticEditPolicy(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» extends org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractBaseItemSemanticEditPolicy {

			«constructor(it)»

			«generatedMemberComment()»
			«overrideC»
			protected String getVisualIdFromView(org.eclipse.gmf.runtime.notation.View view) {
				return «getVisualIDMethodCall(it)»(view);
			}

			«semanticPart(it)»

			«IF links.exists(link|!link.sansDomain)»
				«linkConstraints(it)»
			«ENDIF»
		}
	'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment()»
		protected «className(it)»(org.eclipse.gmf.runtime.emf.type.core.IElementType elementType) {
			super(elementType);
		}
	'''

	def semanticPart(GenDiagram it) '''
		«getContextElementType(it)»		
		«getCreateRelationshipCommand(it)»		
		«getCreateSemanticServiceEditCommand(it)»
	'''

	def getContextElementType(GenDiagram it) '''
		«generatedMemberComment()»
		«overrideC»
		protected org.eclipse.gmf.runtime.emf.type.core.IElementType getContextElementType(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest request) {
			org.eclipse.gmf.runtime.emf.type.core.IElementType requestContextElementType = «xptElementTypes.qualifiedClassName(it)».getElementType(getVisualID(request));
			return requestContextElementType != null ? requestContextElementType : getBaseElementType();
		}
	'''

	def getCreateRelationshipCommand(GenDiagram it) '''
		«IF usingElementTypeCreationCommand»
			«generatedMemberComment()»
			«overrideC»
			protected org.eclipse.gef.commands.Command getCreateRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest req) {

				org.eclipse.papyrus.infra.services.edit.service.IElementEditService commandService = org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils.getCommandProvider(((org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart)getHost()).resolveSemanticElement());
				if(req.getElementType() != null) {
					commandService = org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils.getCommandProvider(req.getElementType(), req.getClientContext());
				}

				if(commandService == null) {
					return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
				}

				org.eclipse.gmf.runtime.common.core.command.ICommand semanticCommand = commandService.getEditCommand(req);

				if((semanticCommand != null) && (semanticCommand.canExecute())) {
					return getGEFWrapper(semanticCommand);
				} 
				return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
			}
		«ENDIF»
	'''

	/**
 *		FIXME need to check constraint's provider to ensure we don't generate a field
 *		for e.g. Java (or Literal, which is unlikely, though) expressions
 * 
 * 		[Papyrus Quick Fix] Do not generate field when the expression is provided
 * 		by a GenJavaExpressionProvider.
 */
	def linkConstraints(GenDiagram it) '''
		«generatedMemberComment()»
		public static «getLinkCreationConstraintsClassName()» getLinkConstraints() {
			«getLinkCreationConstraintsClassName()» cached = «xptPluginActivator.instanceAccess(it.editorGen)».getLinkConstraints();
			if (cached == null) {
				«xptPluginActivator.instanceAccess(it.editorGen)».setLinkConstraints(cached = new «getLinkCreationConstraintsClassName()»());
			}
			return cached;
		}

		«generatedClassComment()»
		public static class «getLinkCreationConstraintsClassName()» {

			«generatedMemberComment»
			public «getLinkCreationConstraintsClassName()»() {«/*package-local for the BaseItemSemanticEditPolicy to instantiate. Perhaps, protected is better (i.e. if someone subclasses it?) */»
				// use static method #getLinkConstraints() to access instance
			}

			«FOR nextLink : it.links»
				«canCreate(nextLink)»
			«ENDFOR»

			«FOR nextLink : it.links»
				«canExist(nextLink)»
			«ENDFOR»
		}
	'''

	def canCreate(GenLink it) '''
		«generatedMemberComment()»
		public boolean canCreate«stringUniqueIdentifier()»(«IF !it.sansDomain»«canCreateParameters(it.modelFacet)»«ENDIF») {
			«IF !it.sansDomain»
				«checkEMFConstraints(it.modelFacet)»
			«ENDIF»
			return canExist«stringUniqueIdentifier()»(«IF !it.sansDomain»«canCreateValues(it.modelFacet)»«ENDIF»);
		}
	'''

	/**
	 * 	XXX for now, both constraints are injected into single method
	 * 		which may not be suitable for modification especially when mixing
	 * 		java and ocl constraints (former requires manual code).
	 * 	Better approach would be:
	 * 		if either is non-null and providers are not the same - introduce two methods, 
	 * 		to check source and target separately. Otherwize, do it inplace.
	 */
	def canExist(GenLink it) '''
		«generatedMemberComment()»
		public boolean canExist«stringUniqueIdentifier()»(«IF !it.sansDomain»«canExistParameters(it.modelFacet)»«ENDIF»	) {
			«IF creationConstraints !== null && creationConstraints.isValid() && it.diagram.editorGen.expressionProviders !== null»
				try {
					«IF creationConstraints.sourceEnd !== null»
						«checkAdditionalConstraint(creationConstraints.sourceEnd.provider, creationConstraints.sourceEnd, 'source', 'target', creationConstraints.getSourceEndContextClass(), creationConstraints.getTargetEndContextClass())»
					«ENDIF»
					«IF creationConstraints.targetEnd !== null»
						«checkAdditionalConstraint(creationConstraints.targetEnd.provider, creationConstraints.targetEnd, 'target', 'source', creationConstraints.getTargetEndContextClass(), creationConstraints.getSourceEndContextClass())»
					«ENDIF»
					return true;
				} catch(Exception e) {	
					«xptPluginActivator.instanceAccess(it.diagram.editorGen)».logError("Link constraint evaluation error", e); «nonNLS()»
					return false;
				}
			«ELSE»
				return true;
			«ENDIF»
		}
	'''

	/**
	 * FIXME XXX mark as private (_) and move to impl::<find proper place>::LinkConstraints.xpt
	 */
	def dispatch canCreateParameters(LinkModelFacet it) '''«sourceTargetParameters(it)»''' // source and target are reasonable defaults

	def dispatch canCreateParameters(
		TypeLinkModelFacet it) '''«IF hasContainerOtherThanSource(it)»«xptMetaModel.QualifiedClassName(it.containmentMetaFeature.genClass)» container, «ENDIF»«sourceTargetParameters(it)»'''

	def dispatch canExistParameters(LinkModelFacet it) '''«sourceTargetParameters(it)»''' // source and target are reasonable defaults

	def dispatch canExistParameters(
		TypeLinkModelFacet it) '''«IF hasContainerOtherThanSource(it)»«xptMetaModel.QualifiedClassName(it.containmentMetaFeature.genClass)» container, «ENDIF»«xptMetaModel.QualifiedClassName(metaClass)» linkInstance, «sourceTargetParameters(it)»'''

	def sourceTargetParameters(
		LinkModelFacet it) '''«xptMetaModel.QualifiedClassName(it.sourceType)» source, «xptMetaModel.QualifiedClassName(it.targetType)» target'''

	// these are in fact 'canExist' values
	def dispatch canCreateValues(LinkModelFacet it) '''source, target''' // defaults

	def dispatch canCreateValues(
		TypeLinkModelFacet it) '''«IF hasContainerOtherThanSource(it)»container, «ENDIF»null, source, target'''

	def dispatch checkEMFConstraints(LinkModelFacet it) '''«ERROR('Unrecognized link model facet in checkEMFConstraints: ' + it)»'''

	/**
	 * [MG] extracted from LET statement, @see checkEMFConstraints(TypeLinkModelFacet)
	 */
	private def checkChildFeatureBounds(TypeLinkModelFacet it) {
		childMetaFeature != containmentMetaFeature && !isUnbounded(childMetaFeature.ecoreFeature)
	}

	def dispatch checkEMFConstraints(TypeLinkModelFacet it) '''
		«IF containmentMetaFeature.ecoreFeature !== null»
			«IF ! isUnbounded(containmentMetaFeature.ecoreFeature) || checkChildFeatureBounds(it)»
				if («getContainerVariable(it)» != null) {
					«checkEMFConstraints(containmentMetaFeature, it)»
					«IF checkChildFeatureBounds(it)»
						«checkEMFConstraints(childMetaFeature, it)»
					«ENDIF»
				}
			«ENDIF»
		«ENDIF»
	'''

	def checkEMFConstraints(GenFeature it, TypeLinkModelFacet modelFacet) '''
		«IF modelFacet.containmentMetaFeature.ecoreFeature !== null»
			«IF ! isUnbounded(ecoreFeature)»
				if («featureBoundComparator(it, getContainerVariable(modelFacet), modelFacet.getSourceType())») {
					return false;
				}
			«ENDIF»
		«ENDIF»
	'''

	def dispatch checkEMFConstraints(FeatureLinkModelFacet it) '''
		if (source != null) {
			if («featureBoundsConditionClause(it.metaFeature, 'source', 'target', getSourceType())») {
				return false;
			}
		«IF it.metaFeature.contains»
			if (source == target) {
				return false;
			}
		«ENDIF»	
		}
		«IF metaFeature.reverse !== null»
			if (target != null && («featureBoundsConditionClause(metaFeature.reverse, 'target', 'source', getTargetType())»)) {
				return false;
			}
		«ENDIF»
	'''

	def featureBoundsConditionClause(GenFeature it, String targetVar, String reverseVar /* Bug 569174 : The reverse var seems to be inverted */, GenClass targetType) '''
		«
		//Checking upper bounds if was specified in MM
		IF ecoreFeature !== null»
			«IF ! isUnbounded(ecoreFeature)»«featureBoundComparator(it, targetVar, targetType)»«ENDIF»
			«««	Checking uniqueness in addition if upper bounds != 1
			«IF ! isSingleValued(ecoreFeature) && ! isUnbounded(ecoreFeature)» || «ENDIF»
			«««	Checking uniqueness in if upper bounds !- 1
			«IF ! isSingleValued(ecoreFeature)»«featureUniquenessComparator(it, targetVar, reverseVar, targetType)»«ENDIF»
		«ENDIF»
	'''

	def featureBoundComparator(GenFeature it, String featureVar, GenClass featureVarGenClass) '''
		«xptMetaModel.getFeatureValue(it, featureVar, featureVarGenClass)»
		«IF ecoreFeature.upperBound == 1»
			!= null
		«ELSE»
			.size() >= «ecoreFeature.upperBound»
		«ENDIF»
	'''

	def featureUniquenessComparator(GenFeature it, String featureVar, String reverseVar, GenClass featureVarGenClass) '''«xptMetaModel.getFeatureValue(it, featureVar, featureVarGenClass)».contains(«reverseVar /* Bug 569174 : The reverse var seems to be inverted */»)'''

	def dispatch checkAdditionalConstraint(GenExpressionProviderBase it, ValueExpression valueExpr, String sourceEndVar, String targetEndVar, GenClass context, GenClass oppositeEndContext) '''
		«ERROR('Have no idea what extra constraints to check for ' + it)»
	'''

	def dispatch checkAdditionalConstraint(GenExpressionInterpreter it, ValueExpression valueExpr, String sourceEndVar, String targetEndVar, GenClass context, GenClass oppositeEndContext) '''
		if («sourceEndVar» == null) {
			return true;
		} else {«/** else is important here as it gives scope for the env variable */»
			java.util.Map<String, org.eclipse.emf.ecore.EClassifier> env = java.util.Collections.<String, org.eclipse.emf.ecore.EClassifier>singletonMap(«oppositeEndVariableNameValue(it)», «xptMetaModel.MetaClass(oppositeEndContext)»); «nonNLS()»
			Object «sourceEndVar»Val = «xptGetExpression.getExpression(it, valueExpr, context, 'env')».evaluate(«sourceEndVar», java.util.Collections.singletonMap(«oppositeEndVariableNameValue(it)», «targetEndVar»)); «nonNLS()»
			if (false == «sourceEndVar»Val instanceof Boolean || !((Boolean) «sourceEndVar»Val).booleanValue()) {
				return false;
			} // else fall-through
		}
	'''

	def dispatch checkAdditionalConstraint(GenJavaExpressionProvider it, ValueExpression valueExpr, String sourceEndVar, String targetEndVar, GenClass context, GenClass oppositeEndContext) '''
		«IF injectExpressionBody && valueExpr.body !== null && !valueExpr.body.empty»
			«valueExpr.body»
		«ELSEIF throwException || (injectExpressionBody && (valueExpr.body === null || valueExpr.body.empty))»
			// TODO: implement this method, using «sourceEndVar» and «targetEndVar» 
			// to access link source and target, respectively
			// Ensure that you remove @generated or mark it @generated NOT
			if (Boolean.TRUE.booleanValue()) {
				throw new java.lang.UnsupportedOperationException("No java implementation provided"); «nonNLS()»
			}
		«ELSE»
			if (Boolean.TRUE.booleanValue()) {«/*just in case there are two consecutive java expression with neither throw nor inject - avoid unreachable code.*/»
				return false;
			}
		«ENDIF»
	'''

	@MetaDef def canExistCall(FeatureLinkModelFacet xptSelf, GenLink link, String sourceVar, String targetVar) //
	'''«_accessLinkConstraints(link.diagram)».canExist«link.uniqueIdentifier»(«sourceVar», «targetVar»)'''

	/**
	 * NOTE, containerVar will be used only when link has other container than its source. It's safe to pass variable/method name that doesn't exist
	 */
	@MetaDef def canExistCall(TypeLinkModelFacet xptSelf, GenLink link, String containerVar, String linkVar,
		String sourceVar, String targetVar) //
	'''«_accessLinkConstraints(link.diagram)».canExist«link.uniqueIdentifier»(«IF hasContainerOtherThanSource(xptSelf)»«containerVar», «ENDIF»«linkVar», «sourceVar», «targetVar»)'''

	@MetaDef def canCreateCall(FeatureLinkModelFacet xptSelf, GenLink link, String sourceVar, String targetVar) //
	'''«_accessLinkConstraints(link.diagram)».canCreate«link.uniqueIdentifier»(«sourceVar», «targetVar»)'''

	/**
	 * NOTE, containerVar will be used only when link has other container than its source. It's safe to pass variable/method name that doesn't exist
	 * Yes, this is sorta hack, but no idea of better approach right now. Perhaps, CreateLinkUtils may always define getContainer() for TypeLinkModelFacet, and use getSource() by default?
	 */
	@MetaDef def canCreateCall(TypeLinkModelFacet xptSelf, GenLink link, String containerVar, String sourceVar,
		String targetVar) //
	'''«_accessLinkConstraints(link.diagram)».canCreate«link.uniqueIdentifier»(«IF hasContainerOtherThanSource(xptSelf)»«containerVar», «ENDIF»«sourceVar», «targetVar»)'''

	@MetaDef private def _accessLinkConstraints(
		GenDiagram xptSelf) '''«qualifiedClassName(xptSelf)».getLinkConstraints()'''

	def oppositeEndVariableNameValue(Object any) '''"oppositeEnd"'''

	def CharSequence defaultConstructor(GenCommonBase it) '''
		«generatedMemberComment()»
		public «getItemSemanticEditPolicyClassName(it)»() {
			«defaultConstructorBody(it)»
		}
	'''

	/**
	 * @param genCommon diagram, node or link, which MUST have an element type (genCommon.elementType != null)
	 */
	def defaultConstructorBody(GenCommonBase genCommon) '''
		«IF genCommon.elementType === null»
			«ERROR("No element type in the passed node. Only diagram, node or link are supported in this template: " + genCommon)»
		«ENDIF»
		super(«xptElementTypes.accessElementType(genCommon)»);
	'''

	/**
	 * Generate generic method if using semantic creation command based on element types framework.
	 * 
	 */
	def getCreateSemanticServiceEditCommand(GenDiagram it) '''
		«IF usingElementTypeCreationCommand»
			«generatedMemberComment»
			protected org.eclipse.gmf.runtime.common.core.command.ICommand getSemanticCreationCommand(org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest req) {
				org.eclipse.papyrus.infra.services.edit.service.IElementEditService commandService = org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils.getCommandProvider(req.getContainer());
				if(commandService == null) {
					return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
				}
				return commandService.getEditCommand(req);
			}
		«ENDIF»	
	'''
}
