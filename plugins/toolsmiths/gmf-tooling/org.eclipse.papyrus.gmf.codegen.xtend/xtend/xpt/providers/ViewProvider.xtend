/*****************************************************************************
 * Copyright (c) 2007, 2010, 2013, 2017, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Artem Tikhomirov (Borland) - [257119] Create views directly, not through ViewFactories
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Thibault Landre (Atos Origin) - initial API and implementation
 * Vincent Lorenzo (CEA-LIST) Add a line to initialize the display of the compartments to true
 * Vincent Lorenzo (CEA-LIST) - Add lines to initialize the display of the labels - Bug 335987 [General][Enhancement] Show/Hide Connectors Labels and External Nodes Labels
 * Vincent Lorenzo (CEA-LIST) - Bug 520733
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.6 declare node variable for description style
 *****************************************************************************/
package xpt.providers

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.gmf.runtime.notation.FillStyle
import org.eclipse.gmf.runtime.notation.FontStyle
import org.eclipse.gmf.runtime.notation.LineStyle
import org.eclipse.gmf.runtime.notation.Style
import org.eclipse.papyrus.gmf.codegen.gmfgen.ColorAttributes
import org.eclipse.papyrus.gmf.codegen.gmfgen.ElementType
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTopLevelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.MetamodelType
import org.eclipse.papyrus.gmf.codegen.gmfgen.NotationType
import org.eclipse.papyrus.gmf.codegen.gmfgen.SpecializationType
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.diagram.Utils_qvto
import xpt.diagram.ViewmapAttributesUtils_qvto
import xpt.diagram.editpolicies.LinkUtils_qvto
import xpt.diagram.views.ViewStyles
import xpt.editor.VisualIDRegistry
import org.eclipse.papyrus.gmf.codegen.gmfgen.DesignLabelModelFacet

@Singleton class ViewProvider {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension CodeStyle;

	@Inject extension Utils_qvto;
	@Inject extension LinkUtils_qvto;
	@Inject extension ViewmapAttributesUtils_qvto;
	@Inject extension VisualIDRegistry xptVisualIDRegistry;

	@Inject ViewStyles xptViewStyles;

	def className(GenDiagram it) '''«it.notationViewProviderClassName»'''

	def packageName(GenDiagram it) '''«it.providersPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) ''' extends org.eclipse.gmf.runtime.common.core.service.AbstractProvider'''
	def implementsList(GenDiagram it) ''' implements org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider'''

	def ViewProvider(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)»«implementsList(it)» {

		«generatedMemberComment»
		«overrideC»
		public final boolean provides(org.eclipse.gmf.runtime.common.core.service.IOperation operation) {
			if (operation instanceof org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation) {
				return provides((org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation) operation);
			}
			«_assert('operation instanceof org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewOperation')»
			/* we check this view provider is the good one for the currently edited diagram */
			if (operation instanceof org.eclipse.gmf.runtime.diagram.core.services.view.CreateChildViewOperation) {
				org.eclipse.gmf.runtime.notation.View container = ((org.eclipse.gmf.runtime.diagram.core.services.view.CreateChildViewOperation) operation).getContainerView();
				org.eclipse.gmf.runtime.notation.Diagram diagram = container.getDiagram();
				if (!getDiagramProvidedId().equals(diagram.getType())) {
					return false;
				}
			}
			if (operation instanceof org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation) {
				return provides((org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation) operation);
			} else if (operation instanceof org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation) {
				return provides((org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation) operation);
			} else if (operation instanceof org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation) {
				return provides((org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation) operation);
			}
			return false;
		}

		«generatedMemberComment»
		protected boolean provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation op) {
			// check Diagram Type should be the class diagram
			String modelID = «getModelIDMethodCall(it)»(op.getContainerView());
			if (!getDiagramProvidedId().equals(modelID)) {
				return false;
			}
			String visualID = «getVisualIDMethodCall(it)»(op.getSemanticHint());
			if (org.eclipse.gmf.runtime.notation.Node.class.isAssignableFrom(op.getViewKind())) {
				return «canCreateNodeMethodCall(it)»(op.getContainerView(), visualID);
			}
				return true;
		}

		«generatedMemberComment»
		protected String getDiagramProvidedId() {
			/*
			 * Indicates for which diagram this provider works for.
			 * <p>
			 * This method can be overloaded when diagram editor inherits from another one, but should never be <code>null</code>
			 * </p>
			 *
			 * @return the unique identifier of the diagram for which views are provided.
			 */
			return «VisualIDRegistry::modelID(it)»;
		}

		«generatedMemberComment»«/* When diagram domain element is null only diagram kind is checked */»
		protected boolean provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation op) {
			return «VisualIDRegistry::modelID(it)».equals(op.getSemanticHint())«IF domainDiagramElement !== null» && «xptVisualIDRegistry.getDiagramVisualIDMethodCall(it)»(getSemanticElement(op.getSemanticAdapter())) != null && !«xptVisualIDRegistry.getDiagramVisualIDMethodCall(it)»(getSemanticElement(op.getSemanticAdapter())).isEmpty()«ENDIF»;
		}

		«generatedMemberComment»
		protected boolean provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation op) {
			if (op.getContainerView() == null) {
				return false;
			}
			org.eclipse.gmf.runtime.emf.type.core.IElementType elementType = getSemanticElementType(op.getSemanticAdapter());
			org.eclipse.emf.ecore.EObject domainElement = getSemanticElement(op.getSemanticAdapter());
			String visualID;
			if (op.getSemanticHint() == null) {
				// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
				// In this situation there should be NO elementType, visualID will be determined
				// by VisualIDRegistry.getNodeVisualID() for domainElement.
				if (elementType != null || domainElement == null) {«/* XXX Perhaps, better to fix CanonicalEP instead of this hack? */»
					return false;
				}
				visualID = «xptVisualIDRegistry.getNodeVisualIDMethodCall(it)»(op.getContainerView(), domainElement);
			} else {
				visualID = «xptVisualIDRegistry.getVisualIDMethodCall(it)»(op.getSemanticHint());
				if (elementType != null) {«/*
					Semantic hint is specified together with element type.
					Both parameters should describe exactly the same diagram element.
					In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
					domainElement (if specified) is the same as in element type. */»
					if (!«getElementTypesQualifiedClassName()».isKnownElementType(elementType) || (!(elementType instanceof org.eclipse.gmf.runtime.emf.type.core.IHintedType))) {
						return false; // foreign element type
					}
					String elementTypeHint = ((org.eclipse.gmf.runtime.emf.type.core.IHintedType) elementType).getSemanticHint();
					if (!op.getSemanticHint().equals(elementTypeHint)) {
					return false; // if semantic hint is specified it should be the same as in element type
				}
			} else {«/*
				Element type is not specified. Domain element should be present (except pure design elements).
				This method is called with EObjectAdapter as parameter from:
				- ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint)
				- generated ViewFactory.decorateView() for parent element */»
				«IF ! getAllTypedElements(it).filter[e|e.elementType !== null].empty»
					if (!«VisualIDRegistry::modelID(it)».equals(«xptVisualIDRegistry.getModelIDMethodCall(it)»(op.getContainerView()))) {
						return false; // foreign diagram
					}
					if (visualID != null) {
						switch (visualID) {
						«IF getAllNodes().exists[e|e.elementType instanceof NotationType]»
							«FOR e : getAllNodes().map[e|e.elementType].filter(typeof(NotationType))»
								«localCaseVisualID(e)»
							«ENDFOR»
							break; // pure design element
						«ENDIF»
						«IF getAllNodes().exists[e|e.elementType instanceof MetamodelType || e.elementType instanceof SpecializationType]»
							«FOR e : getAllNodes().map[e|e.elementType].filter(typeof(MetamodelType))»
								«localCaseVisualID(e)»
							«ENDFOR»
							«FOR e : getAllNodes().map[e|e.elementType].filter(typeof(SpecializationType))»
								«localCaseVisualID(e)»
							«ENDFOR»
							if (domainElement == null || !visualID.equals(«xptVisualIDRegistry.getNodeVisualIDMethodCall(it)»(op.getContainerView(), domainElement))) {
								return false; // visual id in semantic hint should match visual id for domain element
							}
							break;«/*FIXME: Perhaps, can return true or false right away, without any further check?*/»
						«ENDIF»
						default:
							return false;
						}
					}
				«ELSE»
					return false;
				«ENDIF»
				}
			}
			return «canCreateNodeMethodCall(it)»(op.getContainerView(), visualID);
		}

		«generatedMemberComment»«/* XXX: unlike createNode, we don't check op.containerView() for null here. On purpose? */»
		protected boolean provides(org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation op) {
			org.eclipse.gmf.runtime.emf.type.core.IElementType elementType = getSemanticElementType(op.getSemanticAdapter());
			if (!«getElementTypesQualifiedClassName()».isKnownElementType(elementType) || (!(elementType instanceof org.eclipse.gmf.runtime.emf.type.core.IHintedType))) {
				return false; // foreign element type
			}
			String elementTypeHint = ((org.eclipse.gmf.runtime.emf.type.core.IHintedType) elementType).getSemanticHint();
			if (elementTypeHint == null || (op.getSemanticHint() != null && !elementTypeHint.equals(op.getSemanticHint()))) {
				return false; // our hint is visual id and must be specified, and it should be the same as in element type
			}
			return true; «««Does it make sense to check visualID here, like we did for nodes?
		}

		«generatedMemberComment»
		«overrideI»
		@SuppressWarnings("unchecked")
		public org.eclipse.gmf.runtime.notation.Diagram createDiagram(org.eclipse.core.runtime.IAdaptable semanticAdapter, String diagramKind, org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint preferencesHint) {
			org.eclipse.gmf.runtime.notation.Diagram diagram = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createDiagram();«/* FIXME instantiate diagramRunTimeClass instead */»
			org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils.stampCurrentVersion(diagram);«/* Papyrus specific diagram versioning */»
			diagram.getStyles().add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createDiagramStyle());
			«xptViewStyles.addCustomStyles(it, 'diagram.getStyles()')»
			diagram.setType(«VisualIDRegistry::modelID(it)»);
			diagram.setElement(«IF domainDiagramElement !== null»getSemanticElement(semanticAdapter)«ELSE»null«ENDIF»);
			«IF units !== null»
				diagram.setMeasurementUnit(org.eclipse.gmf.runtime.notation.MeasurementUnit.«units.toUpperCase»_LITERAL);
			«ENDIF»
			«IF ! styles.empty »
				// initializeFromPreferences
				org.eclipse.jface.preferences.IPreferenceStore store = (org.eclipse.jface.preferences.IPreferenceStore) preferencesHint.getPreferenceStore();
				«initializeStyles(it, 'diagram', 'store', false, false, false)»
			«ENDIF»
			return diagram;
		}

		«generatedMemberComment»
		«overrideI»
		public org.eclipse.gmf.runtime.notation.Node createNode(org.eclipse.core.runtime.IAdaptable semanticAdapter, org.eclipse.gmf.runtime.notation.View containerView, String semanticHint, int index, boolean persisted, org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint preferencesHint) {
			final org.eclipse.emf.ecore.EObject domainElement = getSemanticElement(semanticAdapter);
			final String visualID;
			if (semanticHint == null) {
				visualID = «xptVisualIDRegistry.getNodeVisualIDMethodCall(it)»(containerView, domainElement);
			} else {
				visualID = «xptVisualIDRegistry.getVisualIDMethodCall(it)»(semanticHint);
			}
			if (visualID != null) {
				switch(visualID) {
				«FOR n : getAllNodes()»
					«xptVisualIDRegistry.caseVisualID(n)»
						return create«n.stringUniqueIdentifier»(domainElement, containerView, index, persisted, preferencesHint);
				«ENDFOR»
				}
			}
			// can't happen, provided #provides(CreateNodeViewOperation) is correct
			return null;
		}

		«generatedMemberComment»
		«overrideI»
		public org.eclipse.gmf.runtime.notation.Edge createEdge(org.eclipse.core.runtime.IAdaptable semanticAdapter, org.eclipse.gmf.runtime.notation.View containerView, String semanticHint, int index, boolean persisted, org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint preferencesHint) {
			org.eclipse.gmf.runtime.emf.type.core.IElementType elementType = getSemanticElementType(semanticAdapter);
			String elementTypeHint = ((org.eclipse.gmf.runtime.emf.type.core.IHintedType) elementType).getSemanticHint();
			String vid = «xptVisualIDRegistry.getVisualIDMethodCall(it)»(elementTypeHint);
			if (vid != null) {
				switch (vid) {
				«FOR link : links»
					«xptVisualIDRegistry.caseVisualID(link)»
						return create«link.stringUniqueIdentifier»(«IF isTypeLink(link)» getSemanticElement(semanticAdapter), «ENDIF»containerView, index, persisted, preferencesHint);
				«ENDFOR»
				}
			}
			// can never happen, provided #provides(CreateEdgeViewOperation) is correct
			return null;
		}

		«FOR next : getAllNodes()»
			«createNodeMethod(next)»
		«ENDFOR»

		«FOR next : links»
			«createEdgeMethod(next)»
		«ENDFOR»

		«IF ! topLevelNodes.empty»
			«generatedMemberComment»
			protected void stampShortcut(org.eclipse.gmf.runtime.notation.View containerView, org.eclipse.gmf.runtime.notation.Node target) {
				if (!«VisualIDRegistry::modelID(it)».equals(«xptVisualIDRegistry.getModelIDMethodCall(it)»(containerView))) {
					«addShortcutAnnotation(it, 'target')»
				}
			}
		«ENDIF»

		«IF links.map[l|l.labels].flatten.notEmpty || topLevelNodes.map[n|n.labels].flatten.notEmpty || childNodes.map[n|n.labels].flatten.notEmpty»
			«generatedMemberComment»
			protected org.eclipse.gmf.runtime.notation.Node createLabel(org.eclipse.gmf.runtime.notation.View owner, String hint) {
				org.eclipse.gmf.runtime.notation.DecorationNode rv = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createDecorationNode();
				rv.setType(hint);
				org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.insertChildView(owner, rv, org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.APPEND, true);
				return rv;
			}
		«ENDIF»

		«IF getAllNodes().map[n|n.compartments].flatten.notEmpty»
			«generatedMemberComment»
			@SuppressWarnings("unchecked")
			protected org.eclipse.gmf.runtime.notation.Node createCompartment(org.eclipse.gmf.runtime.notation.View owner, String hint, boolean canCollapse, boolean hasTitle, boolean canSort, boolean canFilter) {
				org.eclipse.gmf.runtime.notation.Node rv = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createBasicCompartment();
				rv.setLayoutConstraint(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createBounds());
				if (hasTitle) {
					org.eclipse.gmf.runtime.notation.TitleStyle ts = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createTitleStyle();
					rv.getStyles().add(ts);
				}
				if (canSort) {
					rv.getStyles().add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createSortingStyle());
				}
				if (canFilter) {
					rv.getStyles().add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createFilteringStyle());
				}
				rv.setType(hint);
				org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.insertChildView(owner, rv, org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.APPEND, true);
				return rv;
			}
		«ENDIF»

		«getSemanticElementMethod(it)»

		«getSemanticElementTypeMethod(it)»
		}
	'''

	def dispatch createNodeMethod(GenNode it) '''
		«generatedMemberComment»
		public org.eclipse.gmf.runtime.notation.Node create«stringUniqueIdentifier»(org.eclipse.emf.ecore.EObject domainElement, org.eclipse.gmf.runtime.notation.View containerView, int index, boolean persisted, org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint preferencesHint) {
			«IF canUseShapeStyle(viewmap)»
			org.eclipse.gmf.runtime.notation.Shape node = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createShape();
			«ELSE»
			org.eclipse.gmf.runtime.notation.Node node = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createNode();
			node.getStyles().add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createDescriptionStyle());«/* FIXME Conditionally add this style, whether toolsmith needs Notes or not */»
			«xptViewStyles.addFontLineFillStylesConditionally(it.viewmap, 'node.getStyles()')»
			«ENDIF»
			«xptViewStyles.addLinkedDiagramStyle(it, 'node.getStyles()')»
			«xptViewStyles.addCustomStyles(it, 'node.getStyles()')»
			node.setLayoutConstraint(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createBounds());
			node.setType(«xptVisualIDRegistry.typeMethodCall(it)»);
			org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.insertChildView(containerView, node, index, persisted);
			node.setElement(domainElement);
			«IF it instanceof GenTopLevelNode»
			stampShortcut(containerView, node);
			«ENDIF»
			// initializeFromPreferences«/* XXX Perhaps, do init only once, for complete figure? */»
			final org.eclipse.jface.preference.IPreferenceStore prefStore = (org.eclipse.jface.preference.IPreferenceStore) preferencesHint.getPreferenceStore();
			«initializeStyles(it, 'node', 'prefStore', !isFixedForeground(viewmap), !isFixedBackground(viewmap), !isFixedFont(viewmap))»
			«FOR label : it.labels»
			«initLabel(label, 'node', 'prefStore')»
			«ENDFOR»
			«FOR comp : it.compartments»
			«initCompartment(comp, 'node', 'prefStore')»
			«ENDFOR»
			«««	BEGIN Papyrus Modification
			«IF compartments.size != 0»
				org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(«'node'», «'prefStore'», "«elementType.displayName»"); «nonNLS(1)»
			«ENDIF»
			«««	END Papyrus Modification
			«initLabelVisibility(it,'node', 'prefStore')»
			return node;
		}
	'''

	// Location as layoutConstraint, no children
	def dispatch createNodeMethod(GenChildLabelNode it) '''
		«generatedMemberComment»
		public org.eclipse.gmf.runtime.notation.Node create«stringUniqueIdentifier»(org.eclipse.emf.ecore.EObject domainElement, org.eclipse.gmf.runtime.notation.View containerView, int index, boolean persisted, org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint preferencesHint) {
			org.eclipse.gmf.runtime.notation.Node node = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createShape();
			node.setLayoutConstraint(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createLocation());«/* [artem] XXX not sure, why LabelNode needs location */»
			«xptViewStyles.addLinkedDiagramStyle(it, 'node.getStyles()')»
			«xptViewStyles.addCustomStyles(it, 'node.getStyles()')»
			node.setType(«xptVisualIDRegistry.typeMethodCall(it)»);
			org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.insertChildView(containerView, node, index, persisted);
			node.setElement(domainElement);
			final org.eclipse.jface.preference.IPreferenceStore prefStore = (org.eclipse.jface.preference.IPreferenceStore) preferencesHint.getPreferenceStore();
			«initFontFromPrefs(it,'node', 'prefStore')»
			return node;
		}
	'''

	// looks for Font/Line/Fill styles in customStyles and initializes, if any (may be forced with boolean params)
	// params has*Style are to force respective style initialization (when callee knows certain style was added)
	def initializeStyles(GenCommonBase it, String viewVar, String prefStoreVar, boolean hasLineStyle, boolean hasFillStyle, boolean hasFontStyle) {
		if (hasLineStyle || it.hasLineStyleInCustom()) {
			/* FIXME ColorAttributes is deprecated, should remove and clean the second branch of the disjunction */
			if (it.viewmap.isFixedForeground || it.viewmap.attributes.filter(typeof(ColorAttributes)).notEmpty) {
				return '''«xptViewStyles.foregroundColour(it.viewmap, viewVar)»'''
			}
		}
		if ((hasFontStyle || it.hasFontStyleInCustom()) && !viewmap.isFixedFont()) {
				return '''«initFontFromPrefs(it, viewVar, prefStoreVar)»'''
		}
		if (hasFillStyle || it.hasFillStyleInCustom()) {
			if (viewmap.isFixedBackground() || viewmap.attributes.filter(typeof(ColorAttributes)).notEmpty) {
				return '''«xptViewStyles.backgroundColour(it.viewmap, viewVar)»'''
			}
		}
	}

	/**
	 * FIXME check if there's need for a variable name, see initCompartment for a hint
	 */
	def initLabel(GenLabel it, String nodeVar, String prefStoreVar) '''
		«var String labelVar = it.stringUniqueIdentifier.toFirstLower»
		«IF !styles.empty || it.oclIsKindOf(typeof(GenExternalNodeLabel)) || it.oclIsKindOf(typeof(GenLinkLabel) ) 
			// Bug 569174 : L1.6 declare node variable for description style
			|| modelFacet !== null && modelFacet.oclIsKindOf(typeof(DesignLabelModelFacet))
			/* generate var if used */»org.eclipse.gmf.runtime.notation.Node «labelVar» = «ENDIF»createLabel(«nodeVar», «xptVisualIDRegistry.typeMethodCall(it)»);
		«IF it.modelFacet !== null»
			«xptViewStyles.addTextStyle(it.modelFacet, labelVar + '.getStyles()')»
		«ENDIF»
		«xptViewStyles.addCustomStyles(it, labelVar + '.getStyles()')»
		«IF it.oclIsKindOf(typeof(GenExternalNodeLabel)) || it.oclIsKindOf(typeof(GenLinkLabel))»
			«labelVar».setLayoutConstraint(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createLocation());
			«xptViewStyles.offset(it, labelVar)»
		«ENDIF»
		«initializeStyles(it, labelVar, prefStoreVar, false, false, false)»
	'''

	def initCompartment(GenCompartment it, String nodeVar, String prefStoreVar) '''
		«var String compartmentVar= it.stringUniqueIdentifier.toFirstLower»
		«IF it.styles.notEmpty || isStoringChildPositions(it.layoutType)»org.eclipse.gmf.runtime.notation.Node «compartmentVar» = «ENDIF»createCompartment(«nodeVar», «xptVisualIDRegistry.typeMethodCall(it)», «canCollapse», «needsTitle», «listLayout», «listLayout»);
		«xptViewStyles.addCustomStyles(it, compartmentVar + '.getStyles()')»
		«IF isStoringChildPositions(it.layoutType)»
			«IF viewmap.canUseShapeStyle()»
				«compartmentVar».add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createShapeStyle());
			«ELSE /* Intentionally not adding Description style, as it deemed to be useless for compartments (can't avoid for ShapeStyle - benefits of a single style overwheight drawbacks of Description presence) */»
				«xptViewStyles.addFontLineFillStylesConditionally(it.viewmap, compartmentVar + '.getStyles()')»
			«ENDIF»
			«compartmentVar».setLayoutConstraint(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createBounds());
			«initializeStyles(it, compartmentVar, prefStoreVar, !viewmap.isFixedForeground(), !viewmap.isFixedBackground(), !viewmap.isFixedFont())»
		«ELSE»
			«initializeStyles(it, compartmentVar, prefStoreVar, false, false, false)»
		«ENDIF»
	'''

	def initFontFromPrefs(GenCommonBase it, String viewVar, String prefStoreVar) '''
		«specificInitFontFromPrefs(it,viewVar, prefStoreVar)»
	'''

	def createEdgeMethod(GenLink it) '''
		«generatedMemberComment»
		«IF !isFixedFont(viewmap) || isFixedForeground(viewmap)»@SuppressWarnings("unchecked")«ENDIF»
		public org.eclipse.gmf.runtime.notation.Edge create«stringUniqueIdentifier»(«IF isTypeLink(it)»org.eclipse.emf.ecore.EObject domainElement, «ENDIF»org.eclipse.gmf.runtime.notation.View containerView, int index, boolean persisted, org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint preferencesHint) {
		«IF isFixedForeground(viewmap)»
			org.eclipse.gmf.runtime.notation.Edge edge = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createEdge();
			edge.getStyles().add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createRoutingStyle());
		«ELSE»
			org.eclipse.gmf.runtime.notation.Connector edge = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createConnector();
		«ENDIF»
		«IF !isFixedFont(viewmap)»
			edge.getStyles().add(org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createFontStyle());
		«ENDIF»
			«xptViewStyles.addCustomStyles(it, 'edge.getStyles()')»
			org.eclipse.gmf.runtime.notation.RelativeBendpoints bendpoints = org.eclipse.gmf.runtime.notation.NotationFactory.eINSTANCE.createRelativeBendpoints();
			java.util.List<org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint> points = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint')»>(2);
			points.add(new org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint());
			points.add(new org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint());
			bendpoints.setPoints(points);
			edge.setBendpoints(bendpoints);
			org.eclipse.gmf.runtime.diagram.core.util.ViewUtil.insertChildView(containerView, edge, index, persisted);
			edge.setType(«xptVisualIDRegistry.typeMethodCall(it)»);
			edge.setElement(«IF isTypeLink(it)»domainElement«ELSE»null«ENDIF»);
			// initializePreferences
			«IF hasLabelVisibilityPreference(it) || (hasFontStyleInCustom || !viewmap.isFixedFont()) && !"UNDEFINED".equals(elementType.displayName.toUpperCase()) /*  Bug 569174 - avoid warning not used var*/ »
				final org.eclipse.jface.preference.IPreferenceStore prefStore = (org.eclipse.jface.preference.IPreferenceStore) preferencesHint.getPreferenceStore();
			«ENDIF»
			«initializeStyles(it, 'edge', 'prefStore', !viewmap.isFixedForeground(), false, !viewmap.isFixedFont())»
			«FOR label : it.labels»
				«initLabel(label, 'edge', 'prefStore')»
			«ENDFOR»
			«initLabelVisibility(it,'edge', 'prefStore')»
			return edge;
		}
	'''

	def getSemanticElementMethod(GenDiagram it) '''
		«generatedMemberComment»
		protected org.eclipse.emf.ecore.EObject getSemanticElement(org.eclipse.core.runtime.IAdaptable semanticAdapter) {
			if (semanticAdapter == null) {
				return null;
			}
			org.eclipse.emf.ecore.EObject eObject = semanticAdapter.getAdapter(org.eclipse.emf.ecore.EObject.class);
			if (eObject != null) {
				return org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil.resolve(org.eclipse.emf.transaction.util.TransactionUtil.getEditingDomain(eObject), eObject);
			}
			return null;
		}
	'''

	def getSemanticElementTypeMethod(GenDiagram it) '''
	«generatedMemberComment»
	protected org.eclipse.gmf.runtime.emf.type.core.IElementType getSemanticElementType(org.eclipse.core.runtime.IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return semanticAdapter.getAdapter(org.eclipse.gmf.runtime.emf.type.core.IElementType.class);
	}
	'''

	def localCaseVisualID(ElementType elementType) '''
		«IF elementType.diagramElement !== null»
		case «VisualIDRegistry::visualID(elementType.diagramElement)»:
		«ENDIF»
	'''

	def boolean hasFontStyleInCustom(GenCommonBase it) {
		return hasNotationStyleInCustomStyles(it, typeof(FontStyle));
	}

	def boolean hasLineStyleInCustom(GenCommonBase it) {
		return hasNotationStyleInCustomStyles(it, typeof(LineStyle));
	}

	def boolean hasFillStyleInCustom(GenCommonBase it) {
		return hasNotationStyleInCustomStyles(it, typeof(FillStyle));
	}

	/**
	 * check if there's notation::[styleType] among diagram element's custom styles
	 */
	def boolean hasNotationStyleInCustomStyles(GenCommonBase it, Class<? extends Style> styleType) {
		return it.styles.exists[s|s.ecoreClass.oclIsKindOf(styleType)];
	}

	def specificInitFontFromPrefs(GenCommonBase it,String viewVar, String prefStoreVar) '''
		«IF !"UNDEFINED".equals(elementType.displayName.toUpperCase())»
			org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper.initFontStyleFromPrefs(«viewVar», «prefStoreVar», "«elementType.displayName»"); «nonNLS(1)»
		«ENDIF»
	'''

	//write the line to initialize the property isVisible for the label of the link
	def initLabelVisibility(GenLink it,String viewVar, String prefStoreVar) '''
		«IF hasLabelVisibilityPreference(it)»
			org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(«viewVar», «prefStoreVar», "«elementType.displayName»"); «nonNLS(1)»
		«ENDIF»
	'''

	protected def boolean hasLabelVisibilityPreference(GenLink it) {
		it.labels.filter(typeof (GenLinkLabel)).exists[lbl  | lbl.labelVisibilityPreference !== null]
	}

	//write the line to initialize the property isVisible for the label of the link
	def initLabelVisibility(GenNode it,String viewVar, String prefStoreVar) '''
		«IF it.labels.filter(typeof (GenExternalNodeLabel)).exists[lbl  | lbl.labelVisibilityPreference !== null]»
			 org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(«viewVar», «prefStoreVar», "«elementType.displayName»"); «nonNLS(1)»
		«ENDIF»
	'''
}
