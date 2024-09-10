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
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Artem Tikhomirov (Borland) - [235113] alternative parser access
 *                                 [244419] custom parsers
 *                                 [138179] expression-backed labels
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up parsers
 *****************************************************************************/
package impl.parsers

import com.google.inject.Inject
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.CustomParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.DesignLabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.ExpressionLabelParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.ExternalParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParserImplementation
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParsers
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
import org.eclipse.papyrus.gmf.codegen.gmfgen.OclChoiceParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedEnumParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.PredefinedParser
import org.eclipse.papyrus.gmf.codegen.gmfgen.ValueExpression
import xpt.Common
import xpt.Common_qvto
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import xpt.editor.VisualIDRegistry
import xpt.expressions.OclTracker_qvto
import xpt.expressions.getExpression
import xpt.providers.ElementTypes
import xpt.providers.ParserUtils_qvto
import plugin.Activator
import xpt.CodeStyle

@com.google.inject.Singleton class ParserProvider {
	@Inject extension Common
	@Inject extension Common_qvto
	@Inject extension CodeStyle

	@Inject extension OclTracker_qvto
	@Inject extension ParserUtils_qvto
	@Inject extension expression_qvto

	@Inject extension parsers.ExpressionLabelParser;
	@Inject extension parsers.PredefinedParser;

	@Inject getExpression xptGetExpression;
	@Inject MetaModel xptMetaModel;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject ElementTypes xptElementTypes; 
	@Inject parsers.ParserProvider xptParsers;
	@Inject Activator xptActivator;

	def accessorMethod_delegate2providers(GenParsers it) '''
		«generatedMemberComment('Utility method that consults ParserService')»
		public static org.eclipse.gmf.runtime.common.ui.services.parser.IParser getParser(org.eclipse.gmf.runtime.emf.type.core.IElementType type, org.eclipse.emf.ecore.EObject object, String parserHint) {
			return org.eclipse.gmf.runtime.common.ui.services.parser.ParserService.getInstance().getParser(new HintAdapter(type, object, parserHint));
		}
	'''

	/**
	 * invokes method generated with accessorMethod_delegate2providers template
	 */
	@MetaDef def accessorCall_delegate2providers(GenCommonBase it, GenCommonBase elementTypeHolder, LabelModelFacet labelModelFacet, String parsedElement) // 
		'''«it.diagram.editorGen.labelParsers.qualifiedClassName».getParser(«xptElementTypes.accessElementType(elementTypeHolder)», «parsedElement», «IF labelModelFacet === null »«xptVisualIDRegistry.typeMethodCall(it)»«ELSE»«dispatch4_parserHint(labelModelFacet.parser, labelModelFacet, it)»«ENDIF»)'''

	protected def dispatch dispatch4_parserHint(GenParserImplementation it, LabelModelFacet labelFacet, GenCommonBase hintHolder) //
		'''«xptVisualIDRegistry.typeMethodCall(hintHolder)»'''

	/**
	 * ExternalParser may override hint
	 */
	protected def dispatch dispatch4_parserHint(ExternalParser it, LabelModelFacet labelFacet, GenCommonBase hintHolder) //
		'''«IF it.hint === null »«xptVisualIDRegistry.typeMethodCall(hintHolder)»«ELSE»«it.hint»«ENDIF»'''

	protected def dispatch dispatch4_parserHint(ExternalParser it, DesignLabelModelFacet labelFacet, GenCommonBase hintHolder) // 
		'''«IF it.hint === null »org.eclipse.gmf.runtime.common.ui.services.parser.CommonParserHint.DESCRIPTION«ELSE»«it.hint»«ENDIF»'''

	protected def dispatch dispatch4_parserHint(GenParserImplementation it, DesignLabelModelFacet labelFacet, GenCommonBase hintHolder) //
		'''org.eclipse.gmf.runtime.common.ui.services.parser.CommonParserHint.DESCRIPTION'''

	/**
	 * FIXME refactor static field to an instance registered within Activator
	 * Complementary method to accessorMethod_delegate2providers, although for direct access need an instance of this class
	 */
	def accessorMethod_direct(GenParsers it) '''
		private static «xptParsers.className(it)» ourInstance;

		public static «xptParsers.className(it)» get() {
			if (ourInstance == null) {
				ourInstance = new «xptParsers.qualifiedClassName(it)»();
			}
			return ourInstance;
		}
	'''

	/**
	 * XXX consider adding #getDescriptionParser() method to control that kind of parser access?
	 * XXX do I really need GenCommonBase elementTypeHolder, why not use elementType reference directly?
	 */
	def accessorCall_direct(GenCommonBase it, GenCommonBase elementTypeHolder, LabelModelFacet labelModelFacet, String parsedElement) '''
		«IF labelModelFacet === null || labelModelFacet.parser.oclIsKindOf(typeof(ExternalParser))»
			org.eclipse.gmf.runtime.common.ui.services.parser.ParserService.getInstance().getParser(new org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter(/*«xptElementTypes.accessElementType(elementTypeHolder)», */«parsedElement», «IF labelModelFacet === null »«xptVisualIDRegistry.typeMethodCall(it)»«ELSE»«dispatch4_parserHint(labelModelFacet.parser, labelModelFacet, it)»«ENDIF»))
		«ELSE»
			«xptParsers.qualifiedClassName(getDiagram().editorGen.labelParsers)».get().«parserAccessorName(it)»()
		«ENDIF»
	'''

	def provider_getParserMethod(GenParsers it) '''
		«generatedMemberComment()»
		«overrideI»
		public org.eclipse.gmf.runtime.common.ui.services.parser.IParser getParser(org.eclipse.core.runtime.IAdaptable hint) {
			String vid = hint.getAdapter(String.class);
			if (vid != null) {
				return getParser(«xptVisualIDRegistry.getVisualIDMethodCall(editorGen.diagram)»(vid));
			}
			org.eclipse.gmf.runtime.notation.View view =
					hint.getAdapter(org.eclipse.gmf.runtime.notation.View.class);
			if (view != null) {
				return getParser(«xptVisualIDRegistry.getVisualIDMethodCall(editorGen.diagram)»(view));
			}
			return null;
		}
	'''

	def provider_providesMethod(GenParsers it) '''
		«generatedMemberComment()»
		«overrideI»
		public boolean provides(org.eclipse.gmf.runtime.common.core.service.IOperation operation) {
			if (operation instanceof org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation) {
				org.eclipse.core.runtime.IAdaptable hint =
						((org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation) operation).getHint();
				if («xptElementTypes.qualifiedClassName(editorGen.diagram)».getElement(hint) == null) {
					return false;
				}
				return getParser(hint) != null;
			}
			return false;
		}
	'''

	def HintAdapterClass(GenParsers it) '''
		«generatedMemberComment()»
		private static class HintAdapter extends org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter {

			«generatedMemberComment()»
			private final org.eclipse.gmf.runtime.emf.type.core.IElementType elementType;

			«generatedMemberComment()»
			public HintAdapter(org.eclipse.gmf.runtime.emf.type.core.IElementType type, org.eclipse.emf.ecore.EObject object, String parserHint) {
				super(object, parserHint);
				«_assert('type != null')»
				elementType = type;
			}

			«generatedMemberComment()»
			«editorGen.diagram.overrideC»
			public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
				if (org.eclipse.gmf.runtime.emf.type.core.IElementType.class.equals(adapter)) {
					return elementType;
				}
				return super.getAdapter(adapter);
			}
		}
	'''

	def getParserByVisualIdMethod(GenParsers it)  '''
		«generatedMemberComment()»
		protected org.eclipse.gmf.runtime.common.ui.services.parser.IParser getParser(String visualID) {
			if (visualID != null) {
				switch (visualID) {
					«FOR node : editorGen.diagram.topLevelNodes»
						«dispatch_getParsers(node)»
					«ENDFOR»
					«FOR node : editorGen.diagram.childNodes»
						«dispatch_getParsers(node)»
					«ENDFOR»
					«FOR link : editorGen.diagram.links»
						«dispatch_getParsers(link)»
					«ENDFOR»
				}
			}
			return null;
		}
	'''

	def dispatch dispatch_getParsers(GenNode it) '''
		«FOR label : it.labels»
			«IF label.modelFacet !== null »
				«doGetParser(label.modelFacet.parser, label)»
			«ENDIF»
		«ENDFOR»
	'''

	def dispatch dispatch_getParsers(GenLink it) '''
		«FOR label : it.labels»
			«IF label.modelFacet !== null »
				«doGetParser(label.modelFacet.parser, label)»
			«ENDIF»
		«ENDFOR»
	'''

	def dispatch dispatch_getParsers(GenChildLabelNode it) {
		if(modelFacet !== null) {
			'''«doGetParser(it.labelModelFacet.parser, it)»'''
		}
	}
	
	def doGetParser(GenParserImplementation parser, GenCommonBase element) { 
		if (!( parser === null || parser.oclIsKindOf(typeof(ExternalParser)))) {
			return '''«xptVisualIDRegistry.caseVisualID(element)» return «parserAccessorName(element)»();'''
		}
	}

	def dispatch dispatch_parsers(GenNode it) ''' 
		«FOR label : it.labels»
			«IF label.modelFacet !== null »
				«dispatch_parser(label.modelFacet.parser, label.modelFacet, label)»
			«ENDIF»
		«ENDFOR»
	'''

	def dispatch dispatch_parsers(GenLink it) '''
		«FOR label : it.labels»
			«IF label.modelFacet !== null »
				«dispatch_parser(label.modelFacet.parser, label.modelFacet, label)»
			«ENDIF»
		«ENDFOR»
	'''
	def dispatch dispatch_parsers(GenChildLabelNode it) '''«dispatch_parser(it.labelModelFacet.parser, it.labelModelFacet, it)»'''

	def dispatch dispatch_parser(GenParserImplementation it, LabelModelFacet modelFacet, GenCommonBase element) '''«ERROR("Abstract template for " + it + ", element: " + element)»'''

	def dispatch dispatch_parser(CustomParser it, LabelModelFacet modelFacet, GenCommonBase element) '''
		«generatedMemberComment»
		private «it.qualifiedName» «parserFieldName(element)»;

		«generatedMemberComment()»
		«IF it.holder.extensibleViaService»private«ELSE»public«ENDIF» org.eclipse.gmf.runtime.common.ui.services.parser.IParser «parserAccessorName(element)»() {
			if («parserFieldName(element)» == null) {
				«parserFieldName(element)» = new «it.qualifiedName»();
			}
			return «parserFieldName(element)»;
		}	
	'''

	def dispatch dispatch_parser(ExternalParser it, LabelModelFacet modelFacet, GenCommonBase element) '''«/*NO-OP*/»'''

	def dispatch dispatch_parser(PredefinedParser it, FeatureLabelModelFacet modelFacet, GenCommonBase element) //
		'''«doPredefinedParser(it, modelFacet, element)»'''

	def dispatch dispatch_parser(PredefinedEnumParser it, FeatureLabelModelFacet modelFacet, GenCommonBase element) '''
		«doPredefinedParser(it, modelFacet, element)»
	'''

	def dispatch dispatch_parser(OclChoiceParser it, FeatureLabelModelFacet modelFacet, GenCommonBase element) '''
		«doPredefinedParser(it, modelFacet, element)»
	'''

	/**
	 * Intentionally modelFacet typed as general LMF, because ExpressionLabelModelFacet is merely a marker
	 **/
	def dispatch dispatch_parser(ExpressionLabelParser it, LabelModelFacet modelFacet, GenCommonBase element) '''
	«IF isParserViewExpressionDefinedAndOcl(it)»
		«generatedMemberComment()»
		«IF holder.extensibleViaService»private«ELSE»public«ENDIF» org.eclipse.gmf.runtime.common.ui.services.parser.IParser «parserAccessorName(element)»() {
			return new «qualifiedClassName(it)»();
		}
	«ELSE»
		«generatedMemberComment()»
		private «qualifiedClassName(it)» «parserFieldName(element)»;

		«generatedMemberComment()»
		«IF holder.extensibleViaService»private«ELSE»public«ENDIF» org.eclipse.gmf.runtime.common.ui.services.parser.IParser «parserAccessorName(element)»() {
			if («parserFieldName(element)» == null) {
				«parserFieldName(element)» = new «qualifiedClassName(it)»();
			}
			return «parserFieldName(element)»;
		}
	«ENDIF»	
	'''		

	def doPredefinedParser(GenParserImplementation it, FeatureLabelModelFacet modelFacet, GenCommonBase element) '''
		«generatedMemberComment()»
		private org.eclipse.gmf.runtime.common.ui.services.parser.IParser «parserFieldName(element)»;

		«generatedMemberComment()»
		«IF holder.extensibleViaService»private«ELSE»public«ENDIF» org.eclipse.gmf.runtime.common.ui.services.parser.IParser «parserAccessorName(element)»() {
			if («parserFieldName(element)» == null) {
				«dispatch_createPredefinedParser(it, modelFacet, 'parser')»		
				«parserFieldName(element)» = parser;
			}
			return «parserFieldName(element)»;
		}
	'''

	def dispatch dispatch_createPredefinedParser(GenParserImplementation it, FeatureLabelModelFacet modelFacet, String parserVar) '''
		«ERROR("PredefinedParser, PredefinedEnumParser or OclChoiceParser expected but occured: " + it)»
	'''

	def dispatch dispatch_createPredefinedParser(PredefinedParser it, FeatureLabelModelFacet modelFacet, String parserVar) '''	
			org.eclipse.emf.ecore.EAttribute[] features = new org.eclipse.emf.ecore.EAttribute[] {
				«FOR f : modelFacet.metaFeatures SEPARATOR ', '»
					«xptMetaModel.MetaFeature(f)»
				«ENDFOR» 
			};
			«IF modelFacet.editableMetaFeatures.size > 0»
				org.eclipse.emf.ecore.EAttribute[] editableFeatures = new org.eclipse.emf.ecore.EAttribute[] {
				«FOR f : modelFacet.editableMetaFeatures SEPARATOR ', '»
					«xptMetaModel.MetaFeature(f)»
				«ENDFOR» 
				};
			«ENDIF»
			«qualifiedClassName(it)» «parserVar» = new «qualifiedClassName(it)»(features«IF modelFacet.editableMetaFeatures.size > 0», editableFeatures«ENDIF»);
			«setPatterns(modelFacet, viewMethod, editMethod, parserVar)»
	'''

	def dispatch dispatch_createPredefinedParser(PredefinedEnumParser it, FeatureLabelModelFacet modelFacet, String parserVar) '''
		org.eclipse.emf.ecore.EAttribute editableFeature = «xptMetaModel.MetaFeature(notNullOf(modelFacet.editableMetaFeatures.head, modelFacet.metaFeatures.head))»;
		«it.qualifiedClassName» «parserVar» = new «it.qualifiedClassName»(editableFeature);
	'''

	def dispatch dispatch_createPredefinedParser(OclChoiceParser it, FeatureLabelModelFacet modelFacet, String parserVar) 
	'''
		«createOclChoiceParser(it, modelFacet, parserVar, modelFacet.editableMetaFeatures.head, modelFacet.editableMetaFeatures.head.genClass)»
	'''

	def createOclChoiceParser(OclChoiceParser it, FeatureLabelModelFacet modelFacet, String parserVar, GenFeature feature, GenClass context) '''
		org.eclipse.emf.ecore.EStructuralFeature editableFeature = «xptMetaModel.MetaFeature(feature)»;
		org.eclipse.gmf.tooling.runtime.parsers.ChoiceParserBase «parserVar» = «»
		«IF it.showExpression !== null »
			new org.eclipse.gmf.tooling.runtime.parsers.OclTrackerChoiceParser( //
				editableFeature, «safeItemExpression(it, feature)», «xptGetExpression.getExpressionBody(showExpression)», «itemProviderAdapterFactory(it)», «optionalOclTrackerFactoryTypeHint(showExpression)»);
		«ELSE»
			new org.eclipse.gmf.tooling.runtime.parsers.OclChoiceParser( //
				editableFeature, «safeItemExpression(it, feature)», null, «itemProviderAdapterFactory(it)»);
		«ENDIF»
	'''

	def itemProviderAdapterFactory(OclChoiceParser it) '''«xptActivator.qualifiedClassName(it.holder.editorGen.plugin)».getInstance().getItemProvidersAdapterFactory()''' 

	def safeItemExpression(OclChoiceParser it, GenFeature feature) 
		'''«IF itemsExpression === null »"«feature.ecoreFeature.EType.name».allInstances()"«ELSE»«xptGetExpression.getExpressionBody(itemsExpression)»«ENDIF»'''

	def optionalOclTrackerFactoryTypeHint(ValueExpression it) '''«IF isForcedImpactAnalyzerKind(body)», org.eclipse.gmf.tooling.runtime.ocl.tracker.OclTrackerFactory.Type.IMPACT_ANALYZER«ENDIF»'''

	def setPatterns(FeatureLabelModelFacet it, LabelTextAccessMethod viewMethod, LabelTextAccessMethod editMethod, String parserVar) '''
	«IF viewMethod != LabelTextAccessMethod::NATIVE»
		«IF !viewPattern.nullOrEmpty»
			«parserVar».setViewPattern("«viewPattern»"); «nonNLS(1)»
		«ENDIF»
		«IF !editorPattern.nullOrEmpty»
			«parserVar».setEditorPattern("«editorPattern»"); «nonNLS(1)»
		«ELSEIF !viewPattern.nullOrEmpty»
			«parserVar».setEditorPattern("«viewPattern»"); «nonNLS(1)»
		«ENDIF»
	«ENDIF»
	«IF editMethod != LabelTextAccessMethod::NATIVE»
		«IF !editPattern.nullOrEmpty»
			«parserVar».setEditPattern("«editPattern»"); «nonNLS(1)»
		«ELSEIF !viewPattern.nullOrEmpty»
			«parserVar».setEditPattern("«viewPattern»"); «nonNLS(1)»
		«ENDIF»
	«ENDIF»
	'''

}
