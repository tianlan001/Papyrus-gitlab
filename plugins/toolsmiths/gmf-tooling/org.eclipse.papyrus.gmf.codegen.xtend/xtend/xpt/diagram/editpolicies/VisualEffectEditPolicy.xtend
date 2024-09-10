/*******************************************************************************
 * Copyright (c) 2012-2020 Montages AG, CEA LIST, Artal
 * 
 * All rights reserved. This program && the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, && is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Svyatoslav Kovalsky - initial API and implementation
 *    Michael Golubev (Borland) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.diagram.editpolicies

import com.google.inject.Inject
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EReference
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenVisualEffect
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import org.eclipse.ocl.ecore.PrimitiveType
import org.eclipse.ocl.ecore.TupleType
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.expressions.OclTracker_qvto
import xpt.diagram.editparts.EditPartFactory

@com.google.inject.Singleton class VisualEffectEditPolicy {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension OclTracker_qvto;

	@Inject MetaModel xptMetaModel;
	@Inject CodeStyle xptCodeStyle;
	@Inject EditPartFactory xptEditPartFactory;

	def className(GenVisualEffect it) '''«lastSegment(it.editPolicyQualifiedClassName)»'''

	def packageName(GenVisualEffect it) '''«withoutLastSegment(it.editPolicyQualifiedClassName)»'''

	def qualifiedClassName(GenVisualEffect it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenVisualEffect it) '''«qualifiedClassName(it)»'''

	def VisualEffectEditPolicy(GenVisualEffect it) '''
«copyright(subject.diagram.editorGen)»
package «packageName(it)»;

«generatedClassComment»
public class «className(it)» «extendsList(it)» {

	«fields(it)»
	
	«constructor(it)»

	«setVisualEffectValue(it)»
	
	«getHostImpl(it)»
	
	«getExpressionBody(it)»
	
	«getContext(it)»
	
	«additions(it)»	
}
'''

	def extendsList(GenVisualEffect it) '''extends org.eclipse.gmf.tooling.runtime.edit.policies.effect.AbstractOclVisualEffectEditPolicy'''

	def fields(GenVisualEffect it) '''
		«generatedMemberComment»
		public static final String KEY = "«qualifiedClassName(it)»:KEY";
	'''

	def constructor(GenVisualEffect it) '''
		«generatedMemberComment»
		public «className(it)»() {
		«IF isForcedImpactAnalyzerKind(oclExpression)»
			super(org.eclipse.gmf.tooling.runtime.ocl.tracker.OclTrackerFactory.Type.IMPACT_ANALYZER);
		«ELSE»
			super();
		«ENDIF»
		}
	'''

	def setVisualEffectValue(GenVisualEffect it) '''
		«generatedMemberComment»
		
		«overrideAnnotationC(it)»
		protected void setVisualEffectValue(Object value) {
			«IF pinKind == 'ColorPin'»  
				«setVisualEffectValueOnColorPin(it)»
			«ELSEIF pinKind == 'VisiblePin'»
				«setVisualEffectValueOnCustomPin(it)»
			«ELSEIF pinKind == 'CustomPin'»
				«setVisualEffectValueOnCustomPin(it)»
			«ELSE»
				«ERROR('Unsupported Pin kind: ' + pinKind)»
			«ENDIF»
		}
	'''

	def getHostImpl(GenVisualEffect it) '''
		«generatedMemberComment»
		«overrideAnnotationC(it)»
		protected «xptEditPartFactory.getEditPartQualifiedClassName(subject)» getHostImpl() {
			return («xptEditPartFactory.getEditPartQualifiedClassName(subject)») super.getHostImpl();
		}
	'''

	def getExpressionBody(GenVisualEffect it) '''
		«generatedMemberComment»
		«overrideAnnotationC(it)»
		protected String getExpressionBody() {	
				return 
		«IF subject.oclIsKindOf(typeof(GenLink))»
			«expressionLinkEnds((subject as GenLink).modelFacet)»
		«ENDIF»   
			       «getOclExpressionString()»; «nonNLS(1)»
		}
	'''

	def dispatch expressionLinkEnds(LinkModelFacet it) ''''''

	def dispatch expressionLinkEnds(FeatureLinkModelFacet it) '''
		«IF metaFeature.ecoreFeature.upperBound == 1»
			«expressionLinkEnds(it, '', '.' + metaFeature.ecoreFeature.name)»
		«ELSE»
			«IF metaFeature.reverse !== null »
				«expressionLinkEnds(it, '.' + metaFeature.reverse.ecoreFeature.name, '')»				
			«ELSEIF (metaFeature.ecoreFeature as EReference).containment»
				«expressionLinkEnds(it, '.oclAsType(ecore::EObject).eContainer().oclAsType(' + sourceType.ecoreClass.name + ')', '')»
			«ELSE»
				/* unable to set up both link ends */			
			«ENDIF»
		«ENDIF»	
	'''

	def dispatch expressionLinkEnds(TypeLinkModelFacet it) '''
		«expressionLinkEnds(it, '.' + sourceMetaFeature.ecoreFeature.name, '.' + targetMetaFeature.ecoreFeature.name)»
	'''

	def expressionLinkEnds(LinkModelFacet it, String sourcePath, String targetPath) '''
		" let _src : «it.sourceType.ecoreClass.name» = self«sourcePath» in " //
				+ " let _dst : «it.targetType.ecoreClass.name» = self«targetPath» in " //
				+
	'''

	def callOperation(GenVisualEffect it) '''getHostImpl().getPrimaryShape().«operationName»'''

	def setVisualEffectValueOnColorPin(GenVisualEffect it) {
		var tupleType = getOperationRuntimeType() as TupleType
		'''
			«defineTupleParts(tupleType)»
			org.eclipse.swt.graphics.Color color = new org.eclipse.swt.graphics.Color(null, «enumerateTupleParts(tupleType)»);			
			«callOperation(it)»(color);
		'''
	}

	def setVisualEffectValueOnCustomPin(GenVisualEffect it) '''
		«setVisualEffectValueOfType(it.operationRuntimeType, it)»
	'''

	def dispatch setVisualEffectValueOfType(EClassifier it, GenVisualEffect visualEffect) '''
		«ERROR('Abstract template call setVisualEffectValueOfType for ' + it)»
	'''

	def dispatch setVisualEffectValueOfType(PrimitiveType it, GenVisualEffect visualEffect) '''
		«callOperation(visualEffect)»((«it.instanceTypeName»)value);
	'''

	def dispatch setVisualEffectValueOfType(TupleType it, GenVisualEffect visualEffect) '''
		«defineTupleParts(it)»
		«callOperation(visualEffect)»
		(«»
			«enumerateTupleParts(it)»
		);	
	'''

	def defineTupleParts(TupleType it) '''
		org.eclipse.ocl.util.Tuple<?, ?> tupleValue = (org.eclipse.ocl.util.Tuple<?, ?>) value;
		«FOR attribute : it.EAllAttributes»
			«attribute.EType.instanceTypeName» «attribute.name» = («attribute.EType.instanceTypeName»)tupleValue.getValue("«attribute.
			name»");
		«ENDFOR»
	'''

	def enumerateTupleParts(TupleType it) '''
		«FOR attribute : it.EAllAttributes SEPARATOR ','»
			«attribute.name»
		«ENDFOR»
	'''

	def getContext(GenVisualEffect it) '''
		«IF subject.oclIsKindOf(typeof(GenLink))»
			«getFacetContext((subject as GenLink).modelFacet)»
		«ENDIF»
	'''

	def dispatch getFacetContext(LinkModelFacet it) ''''''

	def dispatch getFacetContext(FeatureLinkModelFacet it) '''
		«generatedMemberComment»
		«xptCodeStyle.overrideC(it.eContainer as GenCommonBase)»
		protected org.eclipse.emf.ecore.EObject getContext() {
		«IF metaFeature.ecoreFeature.upperBound == 1»
			«getContextBody(it, 'source', 'target', getSourceType(), getTargetType(), metaFeature)»
		«ELSE»	
			«IF it.metaFeature.reverse !== null || (metaFeature.ecoreFeature as EReference).containment»
				«getContextBody(it, 'target', 'source', getTargetType(), getSourceType(), it.metaFeature.reverse)»
			«ELSE»
				// unable to pass both link ends 
				return null;			
			«ENDIF»
		«ENDIF»	
		}
	'''

	def getContextBody(FeatureLinkModelFacet it, String selfName, String oppositeName, //
	GenClass selfType, GenClass oppositeType, GenFeature selfFeature) '''
		org.eclipse.gmf.runtime.notation.Edge hostModel = (org.eclipse.gmf.runtime.notation.Edge) host().getModel();
		org.eclipse.gmf.runtime.notation.View targetModel = hostModel.getTarget();
		org.eclipse.gmf.runtime.notation.View sourceModel = hostModel.getSource();
		
		if (targetModel == null || sourceModel == null) { 
			return null;
		}
		
		org.eclipse.emf.ecore.EObject «selfName»SemanticModel = org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.resolveSemanticElement(«selfName»Model);
		// need to check actual opposite of the semantic element, since diagram opposite could be not up to date
		«/* (selfFeature = null) if link is multiplicity containment without opposite */IF selfFeature !== null »		 
			«xptMetaModel.QualifiedClassName(selfType)» «selfName»TypedModel = «xptMetaModel.CastEObject(selfType,
			selfName + 'SemanticModel')»;
		«ENDIF»
		org.eclipse.emf.ecore.EObject «oppositeName»SemanticModel =
			«IF selfFeature !== null »«xptMetaModel.getFeatureValue(selfFeature, selfName + 'TypedModel', oppositeType)»«ELSE»«selfName»SemanticModel.eContainer()«ENDIF»;
		
		if («oppositeName»SemanticModel == null) {
			return null;
		}
		
		return «selfName»SemanticModel; 
	'''

	def overrideAnnotationC(GenVisualEffect it) '''
		«xptCodeStyle.overrideC(it.eContainer as GenCommonBase)»
	'''

	def additions(GenVisualEffect it) ''''''

}
