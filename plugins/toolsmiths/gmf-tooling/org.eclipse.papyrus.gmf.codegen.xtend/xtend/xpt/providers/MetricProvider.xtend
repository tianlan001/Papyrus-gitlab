/*******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Dmitry Stadnik (Borland) - initial API and implementation
 *	Artem Tikhomirov (Borland) - refactoring (https://bugs.eclipse.org/230014)
 * 	Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *	Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 */
package xpt.providers

import com.google.inject.Inject
import com.google.inject.Singleton
import metamodel.MetaModel
import org.eclipse.emf.codegen.ecore.genmodel.GenClass
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramElementTarget
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDomainElementTarget
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionInterpreter
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionProviderBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenJavaExpressionProvider
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMeasurable
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricContainer
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricRule
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNotationElementTarget
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import org.eclipse.papyrus.gmf.internal.common.codegen.Conversions
import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.editor.DiagramEditorUtil
import xpt.editor.VisualIDRegistry
import xpt.expressions.getExpression

@Singleton class MetricProvider {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Metrics_qvto;
	@Inject extension CodeStyle;

	@Inject Activator xptActivator;
	@Inject MetaModel xptMetaModel;
	@Inject MetricsResultView xptMetricsResultView;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject getExpression xptGetExpression;
	@Inject ElementTypes xptElementTypes;
	@Inject DiagramEditorUtil xptDiagramEditorUtil;

	@MetaDef def invokeCalcMethod(GenMetricRule it, String accessor, boolean isSpecific) //
	'''«qualifiedClassName(container.editorGen.diagram)».«calcMethodName(it)»(« //
	IF !isSpecific/*CastEObject would be better, however need GenClassifier*/»(«xptMetaModel.
		QualifiedClassName(target.getContext())») «ENDIF»«accessor»)'''

	def className(GenDiagram it) '''«it.metricProviderClassName»'''

	def packageName(GenDiagram it) '''«it.providersPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def MetricProvider(GenDiagram it) '''
	«copyright(editorGen)»
	package «packageName(it)»;

	«generatedClassComment»
	public class «className(it)» {

		«generatedMemberComment»
		public static class MetricsAction extends org.eclipse.jface.action.Action {

			«generatedMemberComment»
			private org.eclipse.ui.IWorkbenchPage page;

			«generatedMemberComment»
			public MetricsAction(org.eclipse.ui.IWorkbenchPage page) {
				setText("Metrics");
				this.page = page;
			}

			«generatedMemberComment»
			public void run() {
				org.eclipse.ui.IWorkbenchPart workbenchPart = page.getActivePart();
				org.eclipse.ui.IViewPart metricsView = null;
				try {
					metricsView = page.findView(«resultViewID(it)»);
					if (metricsView == null) {
						metricsView = page.showView(«resultViewID(it)»);
					} else {
						if (metricsView != null && workbenchPart instanceof org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart) {
							final org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart part = (org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart) workbenchPart;
							((ResultView) metricsView).setInput(part);
						}
						page.activate(metricsView);
					}
				} catch (org.eclipse.ui.PartInitException e) {
					«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Diagram metric view failure", e); «nonNLS(1)»
				}
			}
		}

	«calculateMetricsMethods(it)»

	«formatNotationElementNameMethod(it)»
	«formatSemanticElementNameMethod(it)»

	«metricsClasses(it)»

	«keysAndToolTipsMethods(editorGen.metrics)»

	«FOR m : editorGen.metrics.metrics.filter[m| m.rule !== null && m.target !== null && m.target.context !== null ]»
	«metricCalcMethod(m)»
	«ENDFOR»
		«xptMetricsResultView.MetricsResultView(it)»
	}
	'''

	def resultViewQualifiedClassName(GenDiagram it) '''«qualifiedClassName(it)».«xptMetricsResultView.
		className(it)»'''

	def resultViewID(GenDiagram it) '''«resultViewQualifiedClassName(it)».VIEW_ID'''

	//////////////////////////////////////////////////////////////////////////
	def calculateMetricsMethods(GenDiagram it) '''
		«generatedMemberComment»
		static java.util.List calculateMetrics(org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart diagramPart) {
			final org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart = diagramPart.getDiagramEditPart();
			try {
				return (java.util.List) diagramPart.getDiagramEditPart().getEditingDomain().runExclusive(new org.eclipse.emf.transaction.RunnableWithResult.Impl() {

			public void run() {
				org.eclipse.gmf.runtime.notation.Diagram diagram = diagramEditPart.getDiagramView();
				java.util.ArrayList<ElementMetrics> metrics = new java.util.ArrayList<«diamondOp('ElementMetrics')»>(50);
				«IF getNotationMetrics(editorGen.metrics).notEmpty»
					calculateNotationElementMetrics(diagram, metrics);
				«ENDIF»
				«IF getDiagramMetrics(editorGen.metrics).notEmpty»
					calculateDiagramElementMetrics(diagram, metrics);
				«ENDIF»
				«IF getDomainMetrics(editorGen.metrics).notEmpty»
					calculateSemanticElementMetrics(diagramEditPart, metrics);
				«ENDIF»
				setResult(metrics);
			}
					});
			} catch (InterruptedException e) {
			return java.util.Collections.EMPTY_LIST;
			}
		}

		«IF getNotationMetrics(editorGen.metrics).notEmpty»«calcNotationMetricsMethod(editorGen)»«ENDIF»
		«IF getDiagramMetrics(editorGen.metrics).notEmpty»«calcDiagramMetricsMethod(editorGen)»«ENDIF»
		«IF getDomainMetrics(editorGen.metrics).notEmpty»«calcDomainMetricsMethod(editorGen)»«ENDIF»
	'''

	def metricsClasses(GenDiagram it) '''
		«generatedMemberComment»
		private static class ElementMetrics {

			«generatedMemberComment»
			final Metric[] metrics;

			«generatedMemberComment»
			final String targetElementQName;

			«generatedMemberComment»
			final org.eclipse.swt.graphics.Image elementImage;

			«generatedMemberComment»
			String diagramElementID; «/* FIXME add specific constructor for View elements, set diagramElementID from there */»

			«generatedMemberComment»
			ElementMetrics(org.eclipse.emf.ecore.EObject target, String name, Metric[] metrics) {
			«_assert('metrics.length > 0')»
			«_assert('name != null')»
			this.metrics = metrics;
			this.targetElementQName = name;
			org.eclipse.emf.ecore.EClass imageTarget = target.eClass();
			if (target instanceof org.eclipse.gmf.runtime.notation.View) {
				org.eclipse.gmf.runtime.notation.View viewTarget = (org.eclipse.gmf.runtime.notation.View) target;
				if ("".equals(viewTarget.getType()) && viewTarget.getElement() != null) { «nonNLS(1)»
					imageTarget = viewTarget.getElement().eClass();
				}
			}
			this.elementImage = «getImageAccessor(it, 'imageTarget')»;
			}

			«generatedMemberComment»
			Metric getMetricByKey(String key) {
			for (int i = 0; i < metrics.length; i++) {
				if (metrics[i].key.equals(key)) {
					return metrics[i];
				}
			}
			return null;
			}
		}

		«generatedMemberComment»
		private static class Metric implements Comparable {

			«generatedMemberComment»
			final String key;

			«generatedMemberComment»
			final Double value;

			«generatedMemberComment»
			final Double lowLimit;

			«generatedMemberComment»
			final Double highLimit;

			«generatedMemberComment»
			final String displayValue;

			«generatedMemberComment»
			Metric(String key, Double value) {
			this(key, value, null, null);
			}

			«generatedMemberComment»
			Metric(String key, Double value, Double low, Double high) {
			«_assert('key != null')»
			this.key = key;
			this.value = value;
			this.lowLimit = low;
			this.highLimit = high;
			this.displayValue = (value != null) ? java.text.NumberFormat.getInstance().format(value) : "null"; //$NON-NLS-1$
			}

			«generatedMemberComment»
			public int compareTo(Object other) {
			Metric otherMetric = (Metric) other;
			if (value != null && otherMetric.value != null) {
				return (value.longValue() < otherMetric.value.longValue()) ? -1
						: (value.longValue() == otherMetric.value.longValue() ? 0 : 1);
			}
			return (value == null && otherMetric.value == null) ? 0
					: (value == null) ? -1 : 1;
			}
		}
	'''

	def getImageAccessor(GenDiagram it, String imageClassVar) '''«xptElementTypes.qualifiedClassName(it)».getImage(«imageClassVar»)'''

	def calcNotationMetricsMethod(GenEditorGenerator it) '''
		«generatedMemberComment»
		static void calculateNotationElementMetrics(org.eclipse.gmf.runtime.notation.Diagram diagram, java.util.List<ElementMetrics> metricsList) {
			ElementMetrics row = null;
			«var diagramMetrics = getNotationMetrics(metrics).filter[m|
			(m.target as GenNotationElementTarget).element.ecoreClass.name == 'Diagram']»
			«IF diagramMetrics.notEmpty»
				row = new ElementMetrics(diagram, formatViewName(diagram), new Metric[] {
					«FOR m : diagramMetrics SEPARATOR ',\n'»«metricResult(m, 'diagram', false)»«ENDFOR»
				});
				row.diagramElementID = diagram.eResource().getURIFragment(diagram);
				metricsList.add(row);
			«ENDIF»
			«var notationTargets = getNotationMetrics(metrics).map[m|(m.target as GenNotationElementTarget).element].toSet»
			for (java.util.Iterator it<org.eclipse.emf.ecore.EObjectzz> = diagram.eAllContents(); it.hasNext(); ) {
				Object next = it.next();
				«FOR nt : notationTargets.filter(typeof(GenClass))»
					if («xptMetaModel.IsInstance(nt, 'next')») {
						«xptMetaModel.DeclareAndAssign(nt, '_' + nt.ecoreClass.name.toLowerCase, 'next', true)»
						row = new ElementMetrics(«'_' + nt.ecoreClass.name.toLowerCase», formatViewName(«'_' + nt.ecoreClass.name.toLowerCase»), new Metric[] {«»
						«FOR m : getNotationMetrics(metrics).filter[m|(m.target as GenNotationElementTarget).element == nt] SEPARATOR ','»
							«metricResult(m, '_' + nt.ecoreClass.name.toLowerCase, false)»
						«ENDFOR»
						});
						row.diagramElementID = «'_' + nt.ecoreClass.name.toLowerCase».eResource().getURIFragment(«'_' +
			nt.ecoreClass.name.toLowerCase»);
						metricsList.add(row);
					}
		«ENDFOR»
			}
		}
	'''

	def calcDiagramMetricsMethod(GenEditorGenerator it) '''
		«generatedMemberComment»
		static void calculateDiagramElementMetrics(org.eclipse.gmf.runtime.notation.Diagram diagram, java.util.List<ElementMetrics> metricsList) {
			org.eclipse.emf.ecore.EObject next = diagram;
			java.util.Iterator/*<EObject>*/ it = diagram.eAllContents();
			do {
				if (next instanceof org.eclipse.gmf.runtime.notation.View) {
					org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) next;
					final int vid = «xptVisualIDRegistry.getVisualIDMethodCall(diagram)»(view);
					java.util.ArrayList/*<Metric>*/ res = new java.util.ArrayList/*<Metric>*/(5);
					switch (vid) {
					«FOR cb : getDiagramMetrics(metrics).map[m|(m.target as GenDiagramElementTarget).element].flatten»
						case «VisualIDRegistry::visualID(cb)» : {«»
						«FOR m : getDiagramMetrics(metrics).filter[mm|(mm.target as GenDiagramElementTarget).element.contains(cb)]»
							res.add(«metricResult(m, 'view', true)»);
						«ENDFOR»
						break;
						}
					«ENDFOR»
					}
					if (!res.isEmpty()) {
						ElementMetrics row = new ElementMetrics(view, formatViewName(view), (Metric[]) res.toArray(new Metric[res.size()]));
						row.diagramElementID = view.eResource().getURIFragment(view);
						metricsList.add(row);
					}
				}
				next = it.hasNext() ? (org.eclipse.emf.ecore.EObject) it.next() : null;
			} while (next != null);
		}
	'''

	/**
	 * FIXME:
	 * 		for now, keep approach from old implementation, i.e. iterate content
	 *		of element associated with diagram. Smarter approach would be
	 *		iteration over diagram elements, then accessing their respective
	 *		semantic elements (if set), and collecting metrics for them.
	 */
	def calcDomainMetricsMethod(GenEditorGenerator it) '''
		«generatedMemberComment('NOTE: metrics are being collected for domain elements contained in the semantic element associated with diagram view, actual diagram content (elements present there) is not taken into account.')»
		static void calculateSemanticElementMetrics(org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart, java.util.List<ElementMetrics> metricsList) {
			org.eclipse.gmf.runtime.notation.Diagram diagram = diagramEditPart.getDiagramView();
			org.eclipse.emf.ecore.EObject next = diagram.getElement();
			java.util.Iterator/*<EObject>*/ it = next != null ? next.eAllContents() : java.util.Collections.EMPTY_LIST.iterator();
			java.util.HashMap<org.eclipse.emf.ecore.EObject, ElementMetrics> target2row = new java.util.HashMap<org.eclipse.emf.ecore.EObject, ElementMetrics>();
			while (next != null) {
				java.util.ArrayList<Metric> res = new java.util.ArrayList<«diagram.diamondOp('Metric')»>(5);
				«FOR e : metrics.metrics.map[m|m.target].filter(typeof(GenDomainElementTarget)).map[t|t.element]»
					if («xptMetaModel.MetaClass(e)».isInstance(next)) {
					«FOR m : metrics.metrics.filter[m|m.target.context == e]»
						res.add(«metricResult(m, 'next', true)»);
					«ENDFOR»
					}
				«ENDFOR»
				if (!res.isEmpty()) {
					ElementMetrics row = new ElementMetrics(next, formatElementName(next), (Metric[]) res.toArray(new Metric[res.size()]));
					metricsList.add(row);
					target2row.put(next, row);
				}
				next = it.hasNext() ? (org.eclipse.emf.ecore.EObject) it.next() : null;
			}
			if (!target2row.isEmpty()) { // list was modified, need to process only semantic metrics
				// bind semantic elements to notation
				«xptDiagramEditorUtil.qualifiedClassName(diagram)».LazyElement2ViewMap element2ViewMap = new «
			xptDiagramEditorUtil.qualifiedClassName(diagram)».LazyElement2ViewMap(diagram, target2row.keySet());
				for (java.util.Iterator it2 = target2row.entrySet().iterator(); it2.hasNext();) {
					java.util.Map.Entry entry = (java.util.Map.Entry) it2.next();
					org.eclipse.emf.ecore.EObject semanticElement = (org.eclipse.emf.ecore.EObject) entry.getKey();
					org.eclipse.gmf.runtime.notation.View targetView = «xptDiagramEditorUtil.qualifiedClassName(diagram)».findView(diagramEditPart, semanticElement, element2ViewMap);
					ElementMetrics elementMetrics = (ElementMetrics) entry.getValue();
					elementMetrics.diagramElementID = targetView.eResource().getURIFragment(targetView);
				}
			}
		}
	'''

	def formatNotationElementNameMethod(GenDiagram it) '''
		«generatedMemberComment»
		private static String formatViewName(org.eclipse.gmf.runtime.notation.View viewTarget) {
			StringBuffer notationQNameBuf = new StringBuffer();
			notationQNameBuf.append(formatElementName(viewTarget));
			if (viewTarget.getElement() != null) {
				notationQNameBuf.append("->").append(formatElementName(viewTarget.getElement()));	«nonNLS(1)»
			}
			int visualID = «xptVisualIDRegistry.getVisualIDMethodCall(it)»(viewTarget);
			notationQNameBuf.append('[').append(visualID < 0 ? Integer.toString(System.identityHashCode(viewTarget)) : Integer.toString(visualID)).append(']');
			return notationQNameBuf.toString();
		}
	'''

	def formatSemanticElementNameMethod(GenDiagram it) '''
		«generatedMemberComment»
		private static String formatElementName(org.eclipse.emf.ecore.EObject object) {
			return org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil.getQualifiedName(object, true);
		}
	'''

	def metricCalcMethod(GenMetricRule it) '''
		«generatedMemberComment»
		public static«/* FIXME: (1) refactor to get rid of statics (2) 'public' only those referenced from audits */» Double «calcMethodName(it)»(«calcMethodArgs(it.target, it)») {
			«calcMethodBody(it.rule.provider, it)»
		}
	'''

	def dispatch calcMethodArgs(GenMeasurable it, GenMetricRule metric) '''«ERROR('Unrecognized metric target: ' + it)»'''

	def dispatch calcMethodArgs(GenDomainElementTarget it, GenMetricRule metric) '''«xptMetaModel.
		QualifiedClassName(element)» target'''

	/**
	 * We do check all elements to be of specific kind to provide most narrow type cast
	 * However, GenDiagramElementTargetImpl#getContext uses first element's notation class only
	 */
	def dispatch calcMethodArgs(GenDiagramElementTarget it, GenMetricRule metric) //
	'''org.eclipse.gmf.runtime.notation.«IF allOfType(typeof(GenNode))»Node«ELSEIF allOfType(typeof(GenLink))»Edge«ELSEIF allOfType(typeof(GenDiagram))»Diagram«ELSE»View«ENDIF» target'''

	def boolean allOfType(GenDiagramElementTarget it, Class<? extends GenCommonBase> type) {
		return it.element.forall[e|e.oclIsKindOf(type)]
	}

	/**
	 * Note, use of QualifiedClassName here assumes it always works the same for the notation model, regardless of 'dynamic model' use (i.e. always gives qName of oeg.runtime.notation.* Java class)
	 */
	def dispatch calcMethodArgs(GenNotationElementTarget it, GenMetricRule metric) '''«xptMetaModel.
		QualifiedClassName(element)» target'''

	def dispatch calcMethodBody(GenExpressionProviderBase it, GenMetricRule metric) '''«ERROR('No idea how to calculate metric\'s value for ' + it)»'''

	def dispatch calcMethodBody(GenExpressionInterpreter it, GenMetricRule metric) '''
		Object val = «xptGetExpression.getExpression(it, metric.rule, metric.target.getContext())».evaluate(target);
		if (val instanceof Number) {
			return val.getClass() == Double.class ? (Double) val : new Double(((Number) val).doubleValue());
		}
		return null;
	'''

	def dispatch calcMethodBody(GenJavaExpressionProvider it, GenMetricRule metric) '''
		«IF injectExpressionBody && !metric.rule.body.nullOrEmpty»
		«metric.rule.body»
		«ELSEIF throwException || (injectExpressionBody && metric.rule.body.nullOrEmpty)»
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new java.lang.UnsupportedOperationException("No user java implementation provided"); «nonNLS(1)»
		«ELSE»
		return new Double(Double.NaN);
		«ENDIF»
	'''

	/**
	 * Next two methods should return arrays of identical length, hence are placed into a single template
	 */
	def keysAndToolTipsMethods(GenMetricContainer it) '''
		«generatedMemberComment»
		private static String[] getMetricKeys() {
			return new String[] {
				«FOR m : it.metrics SEPARATOR ',\n'»«toStringLiteral(m.key)»«ENDFOR»
			};
		}

		«generatedMemberComment»
		private static String[] getMetricToolTips() {
			return new String[] {
				«FOR m : it.metrics SEPARATOR ',\n'»«singleMetricTooltip(m)»«ENDFOR»
			};
		}
	'''

	def singleMetricTooltip(GenMetricRule m)
	'''«toStringLiteral(nameOrKey(m))»«IF m.description !== null » + '\n' + «toStringLiteral(m.description)» + '\n'«ENDIF //
	»«IF null !== m.lowLimit » + «toStringLiteral('low: ' + m.lowLimit)»«ENDIF //
	»«IF null !== m.highLimit » + «toStringLiteral('high: ' + m.highLimit)»«ENDIF»'''

	def protected String nameOrKey(GenMetricRule metric) {
		return if(metric.name === null) metric.key else metric.name
	}

	def protected doubleOrNull(Double d) '''«IF d === null »null«ELSE»new Double(«d»)«ENDIF»'''

	def protected castIfNeeded(GenMetricRule it, String targetAccessor, boolean isJustEObject) '''«IF isJustEObject &&
		target.context.oclIsKindOf(typeof(GenClass))»«xptMetaModel.CastEObject(target.context as GenClass, targetAccessor)»«ELSE»«targetAccessor»«ENDIF»'''

	def String toStringLiteral(String key) {
		return Conversions::toStringLiteral(key)
	}

	def metricResult(GenMetricRule it, String targetAccessor, boolean isJustEObject) '''
		new Metric(«toStringLiteral(key)», «calcMethodName(it)»(«castIfNeeded(it, targetAccessor, isJustEObject)»), «doubleOrNull(it.lowLimit)», «doubleOrNull(it.highLimit)»)
	'''

}
