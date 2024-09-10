/*****************************************************************************
 * Copyright (c) 2006, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Modified by Patrick Tessier (CEA LIST)
 * Emilien Perico (Atos Origin) - update template for GMF 2.2 compliance
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.navigator

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import xpt.editor.VisualIDRegistry
import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import xpt.providers.ElementTypes
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import parsers.ParserProvider
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import metamodel.MetaModel
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature
import org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.FigureViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.InnerClassViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.ParentAssignedViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.DesignLabelModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTopLevelNode
import plugin.Activator

@com.google.inject.Singleton class NavigatorLabelProvider {
	@com.google.inject.Inject extension xpt.CodeStyle;
	@com.google.inject.Inject extension xpt.Common;
	@com.google.inject.Inject extension xpt.Common_qvto;
	@com.google.inject.Inject extension xpt.navigator.Utils_qvto;

	@Inject Activator xptActivator;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject ElementTypes xptElementTypes;
	@Inject ParserProvider xptParserProvider;
	@Inject MetaModel xptMetaModel;
	@Inject NavigatorGroup navigatorGroup;
	@Inject NavigatorItem xptNavigatorItem;

	def className(GenNavigator it) '''«it.labelProviderClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def extendsList(GenNavigator it) '''extends org.eclipse.jface.viewers.LabelProvider'''
	def implementsList(GenNavigator it) '''implements org.eclipse.ui.navigator.ICommonLabelProvider, org.eclipse.jface.viewers.ITreePathLabelProvider'''

	def NavigatorLabelProvider(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {

			«staticInitializer(it)»
			«updateLabel(it)»
			«getImage(it)»
			«getViewImage(it)»
			«getImageByKey(it)»
			«getText(it)»
			«getViewText(it)»
			«FOR n : getNavigatorContainedNodes(it)»
				«getTextMethod(n)»
			«ENDFOR»
			«getUnknownElementText(it)»
			«getUnresolvedDomainElementProxyText(it)»
			«constructor(it)»
			«restoreState(it)»
			«saveState(it)»
			«getDescription(it)»
			«isOwnView(it)»
		}
	'''

	def staticInitializer(GenNavigator it) '''
		«generatedMemberComment()»
		static {
			«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getImageRegistry().put(«unknownElementKey()», org.eclipse.jface.resource.ImageDescriptor.getMissingImageDescriptor());  «nonNLS(1)»
			«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getImageRegistry().put(«notFoundElementKey()», org.eclipse.jface.resource.ImageDescriptor.getMissingImageDescriptor());  «nonNLS(1)»
		}
	'''

	def updateLabel(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void updateLabel(org.eclipse.jface.viewers.ViewerLabel label, org.eclipse.jface.viewers.TreePath elementPath) {
			Object element = elementPath.getLastSegment();
			if (element instanceof «xptNavigatorItem.qualifiedClassName(it)» && !isOwnView(((«xptNavigatorItem.qualifiedClassName(it)») element).getView())) {
				return;
			}
			label.setText(getText(element));
			label.setImage(getImage(element));
		}
	'''

	def getImage(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public org.eclipse.swt.graphics.Image getImage(Object element) {
			«getNavigatorGroupImage(it)»
			«getNavigatorItemImage(it)»
			«getAdaptableImage(it)»
			return super.getImage(element);
		}
	'''

	def getNavigatorGroupImage(GenNavigator it) '''
		if (element instanceof «navigatorGroup.qualifiedClassName(it)») {
			«navigatorGroup.qualifiedClassName(it)» group = («navigatorGroup.qualifiedClassName(it)») element;
			return «xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getBundledImage(group.getIcon());
		}
	'''

	def getNavigatorItemImage(GenNavigator it) '''
		if (element instanceof «xptNavigatorItem.qualifiedClassName(it)») {
			«xptNavigatorItem.qualifiedClassName(it)» navigatorItem = («xptNavigatorItem.qualifiedClassName(it)») element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}
	'''

	def getAdaptableImage(GenNavigator it) '''
		«IF editorGen.diagram.generateShortcutIcon()»
		// Due to plugin.xml content will be called only for "own" views
		if (element instanceof org.eclipse.core.runtime.IAdaptable) {
			org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) ((org.eclipse.core.runtime.IAdaptable) element).getAdapter(org.eclipse.gmf.runtime.notation.View.class);
			if (view != null && isOwnView(view)) {
				return getImage(view);
			}
		}
		«ENDIF»
	'''

	def getViewImage(GenNavigator it) '''
		«generatedMemberComment()»
		public org.eclipse.swt.graphics.Image getImage(org.eclipse.gmf.runtime.notation.View view) {
			«IF getNavigatorContainedNodes(it).notEmpty»
			switch («xptVisualIDRegistry.getVisualIDMethodCall(editorGen.diagram)»(view)) {
				«FOR n : getNavigatorContainedNodes(it)»
					«IF n !== null »
						«caseImage(n)»
					«ENDIF»
				«ENDFOR»
			}
			«ENDIF»
			return getImage(«unknownElementKey()», null);  «nonNLS(1)»
		}
	'''

	def caseImage(GenCommonBase it) '''
		case «VisualIDRegistry::visualID(it)»:
			return getImage("«key()»", «IF null !== elementType »«xptElementTypes.accessElementType(it)»«ELSE»null«ENDIF»); «nonNLS(1)»
	'''

	def getImageByKey(GenNavigator it) '''
		«generatedMemberComment()»
		private org.eclipse.swt.graphics.Image getImage(String key, org.eclipse.gmf.runtime.emf.type.core.IElementType elementType) {
			org.eclipse.jface.resource.ImageRegistry imageRegistry = «xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getImageRegistry();
			org.eclipse.swt.graphics.Image image = imageRegistry.get(key);
			if (image == null && elementType != null && «xptElementTypes.qualifiedClassName(editorGen.diagram)».isKnownElementType(elementType)) {
				image = «xptElementTypes.qualifiedClassName(editorGen.diagram)».getImage(elementType);
				imageRegistry.put(key, image);
			}

			if (image == null) {
				image = imageRegistry.get(«notFoundElementKey()»);  «nonNLS(1)»
				imageRegistry.put(key, image);
			}
			return image;
		}
	'''

	def dispatch CharSequence getText(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public String getText(Object element) {
			«getNavigatorGroupText(it)»
			«getNavigatorItemText(it)»
			«getAdaptableText(it)»
			return super.getText(element);
		}
	'''

	def getNavigatorGroupText(GenNavigator it) '''
		if (element instanceof «navigatorGroup.qualifiedClassName(it)») {
			«navigatorGroup.qualifiedClassName(it)» group = («navigatorGroup.qualifiedClassName(it)») element;
			return group.getGroupName();
		}
	'''

	def getNavigatorItemText(GenNavigator it) '''
		if (element instanceof «xptNavigatorItem.qualifiedClassName(it)») {
			«xptNavigatorItem.qualifiedClassName(it)» navigatorItem = («xptNavigatorItem.qualifiedClassName(it)») element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}
	'''

	def getAdaptableText(GenNavigator it) '''
		«IF editorGen.diagram.generateShortcutIcon()»
		// Due to plugin.xml content will be called only for "own" views
		if (element instanceof org.eclipse.core.runtime.IAdaptable) {
			org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) ((org.eclipse.core.runtime.IAdaptable) element).getAdapter(org.eclipse.gmf.runtime.notation.View.class);
			if (view != null && isOwnView(view)) {
				return getText(view);
			}
		}
		«ENDIF»
	'''

	def getViewText(GenNavigator it) '''
		«generatedMemberComment()»
		public String getText(org.eclipse.gmf.runtime.notation.View view) {
			if (view.getElement() != null && view.getElement().eIsProxy()) {
				return getUnresolvedDomainElementProxyText(view);
			}
			«IF getNavigatorContainedNodes(it).notEmpty»
			switch («xptVisualIDRegistry.getVisualIDMethodCall(editorGen.diagram)»(view)) {
				«FOR n : getNavigatorContainedNodes(it)»
					«caseText(n)»
				«ENDFOR»
			}
			«ENDIF»
			return getUnknownElementText(view);
		}
	'''

	def caseText(GenCommonBase it) '''
		case «VisualIDRegistry::visualID(it)»:
			return «getTextMethodName(it)»(view);
	'''

	@MetaDef def getTextMethodName(GenCommonBase it) '''get«stringUniqueIdentifier»Text'''

	def getTextMethod(GenCommonBase it) '''
		«generatedMemberComment()»
		private String «getTextMethodName(it)»(org.eclipse.gmf.runtime.notation.View view) {
			«getText(it)»
		}
	'''

	def dispatch CharSequence getText(GenCommonBase it) '''«ERROR('Incorrect GenCommonBase: ' + it)»'''

	def dispatch CharSequence getText(GenDiagram it) '''
		«getLabelFeatureText(it, it.domainDiagramElement)»
	'''

	def dispatch CharSequence getText(GenChildLabelNode it) '''
		«getDiagramLabelText(it, it, labelModelFacet)»
	'''

	def dispatch CharSequence getText(GenLinkLabel it) '''«getDiagramLabelText(it, link, modelFacet)»'''
	def dispatch CharSequence getText(GenNodeLabel it) '''«getDiagramLabelText(it, node, modelFacet)»'''

	def dispatch CharSequence getText(GenNode it) '''
	«IF labels.empty»
		«IF null !== modelFacet »
			«getLabelFeatureText(it, modelFacet.metaClass)»
		«ELSE»
			«returnEmptyString()»
		«ENDIF»
	«ELSE»
		«getDiagramLabelText(it, labels)»
	«ENDIF»
	'''

	def dispatch CharSequence getText(GenLink it) '''
	«IF it.labels.empty»
		«IF null !== modelFacet »
			«getText(it, modelFacet)»
		«ELSE»
			«returnEmptyString()»
		«ENDIF»
	«ELSE»
		«getDiagramLabelText(it, it.labels)»
	«ENDIF»
	'''

	def CharSequence getDiagramLabelText(GenCommonBase it, Iterable<? extends GenLabel> labels) '''
		«IF labels.empty»
			«ERROR('Empty list of labels passed')»
		«ENDIF»
		«getDiagramLabelText(labels.head, it, labels.head.modelFacet)»
	'''

	def dispatch CharSequence getText(GenLink it, LinkModelFacet modelFacet) '''
		«returnEmptyString()»
	'''

	def dispatch CharSequence getText(GenLink it, TypeLinkModelFacet modelFacet) '''
		«getLabelFeatureText(it, modelFacet.metaClass)»
	'''

	def dispatch CharSequence getText(GenCompartment it) '''
		return "«title»"; «nonNLS(1)»
	'''

	/**
	 * FIXME: [MG] Refactor. Need to reuse parts of TextAware.xpt 
	 *
	 * it - aka hintHolder, visual element to present a text
	 * elementTypeHolder - model element being displayed
	 * modelFacet - may be null 
	 */
	def CharSequence getDiagramLabelText(GenCommonBase it, GenCommonBase elementTypeHolder, LabelModelFacet labelModelFacet) '''
		org.eclipse.gmf.runtime.common.ui.services.parser.IParser parser = «xptParserProvider.accessorCall(it, elementTypeHolder, labelModelFacet, 'view.getElement() != null ? view.getElement() : view')»;
		if (parser != null) {
			return parser.getPrintString(new org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter(view.getElement() != null ? view.getElement() : view), org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions.NONE.intValue());
		} else {
			«xptActivator.qualifiedClassName(getDiagram().editorGen.plugin)».getInstance().logError("Parser was not found for label «stringVisualID»"); «nonNLS(1)»
			«returnEmptyString()»
		}
	'''

	def getLabelFeatureText(GenCommonBase it, GenClass genClass) '''
		«IF null !== genClass && null !== genClass.labelFeature »
			«xptMetaModel.DeclareAndAssign(genClass, 'domainModelElement', 'view.getElement()')»
			if (domainModelElement != null) {
				return «IF !isStringFeature(genClass.labelFeature)»String.valueOf(«ENDIF»UMLLabelInternationalization.getInstance().getLabel(domainModelElement)«IF !isStringFeature(genClass.labelFeature)»)«ENDIF»;
			} else {
				«xptActivator.qualifiedClassName(getDiagram().editorGen.plugin)».getInstance().logError("No domain element for view with visualID = «stringVisualID»");  «nonNLS(1)»
					«returnEmptyString()»
			}
		«ELSE»
			«returnEmptyString()»
		«ENDIF»
	'''

	def returnEmptyString() '''
		return ""; «nonNLS(1)»
	'''

	def getUnknownElementText(GenNavigator it) '''
		«generatedMemberComment()»
		private String getUnknownElementText(org.eclipse.gmf.runtime.notation.View view) {
			return "<UnknownElement Visual_ID = " + view.getType() + ">"; «nonNLS(1)» «nonNLS(2)»
		}
	'''

	def getUnresolvedDomainElementProxyText(GenNavigator it) '''
		«generatedMemberComment()»
		private String getUnresolvedDomainElementProxyText(org.eclipse.gmf.runtime.notation.View view) {
			return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; «nonNLS(1)» «nonNLS(2)»
		}
	'''

	def constructor(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void init(org.eclipse.ui.navigator.ICommonContentExtensionSite aConfig) {
		}
	'''

	def restoreState(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void restoreState(org.eclipse.ui.IMemento aMemento) {
		}
	'''

	def saveState(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void saveState(org.eclipse.ui.IMemento aMemento) {
		}
	'''

	def getDescription(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public String getDescription(Object anElement) {
			return null;
		}
	'''

	def isOwnView(GenNavigator it) '''
		«generatedMemberComment()»
		private boolean isOwnView(org.eclipse.gmf.runtime.notation.View view) {
			return «VisualIDRegistry::modelID(editorGen.diagram)».equals(«xptVisualIDRegistry.getModelIDMethodCall(editorGen.diagram)»(view));
		}
	'''

	def unknownElementKey() '''"«commonKeyPrefix()»UnknownElement"'''

	def notFoundElementKey() '''"«commonKeyPrefix()»ImageNotFound"'''

	def dispatch CharSequence key(GenCommonBase it) '''«ERROR('Incorrect GenCommonBase: ' + it)»'''

	def dispatch CharSequence key(GenDiagram it) '''«commonKeyPrefix()»Diagram?«IF it.domainDiagramElement !== null »«keyFragment(it.domainDiagramElement)»«ENDIF»'''

	def dispatch CharSequence key(GenNode it) '''«commonKeyPrefix()»«keyFragment(it)»«IF null !== modelFacet »«keyFragment(modelFacet.metaClass)»«ELSE»«keyFragment(viewmap)»«ENDIF»'''

	def dispatch CharSequence keyFragment(GenNode it) '''Node?'''

	def dispatch CharSequence keyFragment(GenTopLevelNode it) '''TopLevelNode?'''

	def dispatch CharSequence key(GenLink it) '''«commonKeyPrefix()»Link?«IF null !== modelFacet »«keyFragment(modelFacet)»«ELSE»«keyFragment(viewmap)»«ENDIF»'''

	def dispatch CharSequence keyFragment(LinkModelFacet it) '''«ERROR('Incorrect link model facet passed: ' + it)»'''

	def dispatch CharSequence keyFragment(TypeLinkModelFacet it) '''«keyFragment(metaClass)»'''

	def dispatch CharSequence keyFragment(FeatureLinkModelFacet it) '''«keyFragment(metaFeature)»'''

	def dispatch CharSequence key(GenCompartment it) '''«key(node)»?Compartment?«title»'''

	def dispatch CharSequence key(GenLabel it) '''«ERROR('Incorrect GenLabel:' + it)»'''

	def dispatch CharSequence key(GenNodeLabel it) '''«key(node)»«keyFragment(it)»'''

	def dispatch CharSequence key(GenLinkLabel it) '''«key(link)»«keyFragment(it)»'''

	def dispatch CharSequence keyFragment(GenLabel it) '''?Label?«keyFragment(it.modelFacet, it)»'''

	def dispatch CharSequence keyFragment(GenClass it)'''«genPackage.getEcorePackage.nsURI»?«ecoreClass.name»'''

	def dispatch CharSequence keyFragment(GenFeature it)'''«keyFragment(genClass)»?«ecoreFeature.name»'''

	def dispatch CharSequence keyFragment(Viewmap it) ''''''

	def dispatch CharSequence keyFragment(FigureViewmap it) '''«figureQualifiedClassName»'''

	def dispatch CharSequence keyFragment(InnerClassViewmap it) '''«className»'''

	def dispatch CharSequence keyFragment(ParentAssignedViewmap it) '''«figureQualifiedClassName»'''

	def dispatch CharSequence keyFragment(LabelModelFacet it, GenLabel label) '''«ERROR('Incorrect label model facet: ' + it)»'''

	def dispatch CharSequence keyFragment(FeatureLabelModelFacet it, GenLabel label) '''«FOR f:metaFeatures»«keyFragment(f)»«ENDFOR»'''

	def dispatch CharSequence keyFragment(DesignLabelModelFacet it, GenLabel label) '''«keyFragment(label.viewmap)»'''

	def commonKeyPrefix() '''Navigator?'''

}
