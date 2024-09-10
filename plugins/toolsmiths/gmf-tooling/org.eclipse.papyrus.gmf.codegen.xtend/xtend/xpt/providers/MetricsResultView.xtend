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
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *	Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 */
package xpt.providers

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorView
import xpt.Common
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef

@com.google.inject.Singleton class MetricsResultView {

	@Inject extension Common;

	@MetaDef def className(GenDiagram it) '''ResultView'''

	def MetricsResultView(GenDiagram it) '''
		«generatedMemberComment»
		public static class «className(it)» «extendsList(it)» «implementsList(it)»{
			«generatedMemberComment»
			public static final String VIEW_ID = "«getMetricViewID()»"; «nonNLS(1)»

			«generatedMemberComment»
			private static int MAX_VISIBLE_KEY_CHAR_COUNT = 8;

			«viewerField(it)»

			«inputField(it)»

			«setInputMethod(it)»

			«adjustLayoutMethod(it)»

			«createPartControlMethod(it)»

			«handleOpenMethod(it)»

			«calcMetricMaxValueStrLenMapMethod(it)»

			«setFocusMethod(it)»

			«labelProviderClass(it)»
		}
	'''

	def extendsList(GenDiagram it) '''extends org.eclipse.ui.part.ViewPart'''

	def implementsList(GenDiagram it) '''implements org.eclipse.jface.viewers.IOpenListener'''

	def viewerField(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.jface.viewers.TableViewer viewer;
	'''

	def inputField(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.emf.ecore.resource.Resource diagramResource;
	'''

	def setInputMethod(GenDiagram it) '''
		«generatedMemberComment»
		void setInput(org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart diagramPart) {
			diagramResource = diagramPart.getDiagram().eResource();
			setTitleToolTip(diagramResource.getURI().path());
			java.util.List metrics = calculateMetrics(diagramPart);
			adjustLayout(metrics);
			viewer.setInput(metrics);
		}
	'''

	def adjustLayoutMethod(GenDiagram it) '''
		«generatedMemberComment»
		private void adjustLayout(java.util.List metricResultList) {
			java.util.Map maxValStrMap = calcMetricMaxValueStrLenMap(metricResultList);
			org.eclipse.swt.widgets.Table table = viewer.getTable();
			org.eclipse.jface.viewers.TableLayout layout = new org.eclipse.jface.viewers.TableLayout();
			org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC(table);

			gc.setFont(org.eclipse.jface.resource.JFaceResources.getDialogFont());
			int padding = gc.stringExtent("X").x * 2; «nonNLS(1)»
			for (int i = 0; i < getMetricKeys().length; i++) {
			final String nextKey = getMetricKeys()[i];
			String valueStr = (String) maxValStrMap.get(nextKey);
			int minWidth = valueStr != null ? gc.stringExtent(valueStr).x + padding : 20;
			layout.addColumnData(new org.eclipse.jface.viewers.ColumnPixelData(minWidth, true));
			}
			gc.dispose();

			layout.addColumnData(new org.eclipse.jface.viewers.ColumnWeightData(1, 50, true));
			viewer.getTable().setLayout(layout);
			viewer.getTable().layout(true, true);
		}
	'''

	def createPartControlMethod(GenDiagram it) '''
		«generatedMemberComment»
		public void createPartControl(org.eclipse.swt.widgets.Composite parent) {
			this.viewer = new org.eclipse.jface.viewers.TableViewer(parent, org.eclipse.swt.SWT.FULL_SELECTION);
			final org.eclipse.swt.widgets.Table table = viewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);

			for (int i = 0; i < getMetricKeys().length; i++) {
			org.eclipse.swt.widgets.TableColumn column = new org.eclipse.swt.widgets.TableColumn(table, org.eclipse.swt.SWT.NONE);
			column.setAlignment(org.eclipse.swt.SWT.RIGHT);
			column.setMoveable(true);
			column.setText(getMetricKeys()[i]);
			column.setToolTipText(getMetricToolTips()[i]);
			}

			org.eclipse.swt.widgets.TableColumn objectColumn = new org.eclipse.swt.widgets.TableColumn(table, org.eclipse.swt.SWT.NONE);
			objectColumn.setText("Element");
			objectColumn.setToolTipText("Measurement element");

			viewer.setLabelProvider(new Labels());
			viewer.setContentProvider(new org.eclipse.jface.viewers.ArrayContentProvider());
			viewer.addOpenListener(this);

			org.eclipse.swt.events.SelectionListener headerSelListener = new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				table.setSortColumn((org.eclipse.swt.widgets.TableColumn) e.getSource());
				table.setSortDirection((table.getSortDirection() != org.eclipse.swt.SWT.DOWN) ? org.eclipse.swt.SWT.DOWN : org.eclipse.swt.SWT.UP);
				viewer.refresh();
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
			};
			org.eclipse.swt.widgets.TableColumn[] columns = viewer.getTable().getColumns();
			for (int i = 0; i < columns.length; i++) {
			columns[i].addSelectionListener(headerSelListener);
			}

			viewer.setSorter(new org.eclipse.jface.viewers.ViewerSorter() {
			public int compare(org.eclipse.jface.viewers.Viewer viewer, Object e1, Object e2) {
				org.eclipse.swt.widgets.TableColumn c = table.getSortColumn();
				int result = 0;
				if (c != null) {
					Metric mc1 = ((ElementMetrics) e1).getMetricByKey(c.getText());
					Metric mc2 = ((ElementMetrics) e2).getMetricByKey(c.getText());
					result = (mc1 != null && mc2 != null) ?
						mc1.compareTo(mc2) : (mc1 == null ? -1 : 1);
				} else {
					result = ((ElementMetrics) e1).targetElementQName.compareTo(((ElementMetrics) e2).targetElementQName);
				}
				return table.getSortDirection() == org.eclipse.swt.SWT.DOWN ? result : -result;
			}
			});
			«createPartControlMethod_refreshInput(editorGen.editor)»
			}
	'''

	def createPartControlMethod_refreshInput(GenEditorView it) '''
		org.eclipse.ui.IEditorPart editor = getSite().getPage().getActiveEditor();
		if (editor != null && editor.getClass().equals(«editorGen.editor.getQualifiedClassName()».class)) {
			setInput((«editorGen.editor.getQualifiedClassName()») editor);
		}
	'''

	def handleOpenMethod(GenDiagram it) '''
		«generatedMemberComment»
		public void open(org.eclipse.jface.viewers.OpenEvent event) {
			try {
				org.eclipse.ui.IEditorPart editorPart = getSite().getPage().openEditor(new org.eclipse.ui.part.FileEditorInput(org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(diagramResource)), «editorGen.editor.getQualifiedClassName()».ID);
				if (editorPart == null) {
					return;
				}
				org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart diagramPart =
					editorPart.getAdapter(org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart.class);
				ElementMetrics selection = (ElementMetrics) ((org.eclipse.jface.viewers.IStructuredSelection) event.getSelection()).getFirstElement();
				String viewID = selection.diagramElementID;
				if (viewID != null) {
					org.eclipse.gmf.runtime.notation.View targetView = (org.eclipse.gmf.runtime.notation.View) diagramPart.getDiagram().eResource().getEObject(viewID);
					if (targetView != null) {
						org.eclipse.gef.EditPart targetEditPart = (org.eclipse.gef.EditPart) diagramPart.getDiagramGraphicalViewer().getEditPartRegistry().get(targetView);
						if (targetEditPart != null) {
							«getDiagramEditorUtilQualifiedClassName()».selectElementsInDiagram(diagramPart, java.util.Collections.singletonList(targetEditPart));
						}
					}
				}
			} catch (org.eclipse.ui.PartInitException e) {
				«editorGen.plugin.activatorQualifiedClassName».getInstance().logError("Can't open diagram editor", e); //$NON-NLS-1$
			}
		}
	'''

	def calcMetricMaxValueStrLenMapMethod(GenDiagram it) '''
		«generatedMemberComment»
		private static java.util.Map calcMetricMaxValueStrLenMap(java.util.List allMetrics) {
			java.util.Map metric2MaxStrLen = new java.util.HashMap();
			for (int i = 0; i < getMetricKeys().length; i++) {
				String nextKey = getMetricKeys()[i];
				int trimPos = Math.min(nextKey.length(), MAX_VISIBLE_KEY_CHAR_COUNT);
				metric2MaxStrLen.put(nextKey, nextKey.substring(0, trimPos));
			}
			for (java.util.Iterator it = allMetrics.iterator(); it.hasNext();) {
				ElementMetrics elementMetrics = (ElementMetrics) it.next();
				for (int i = 0; i < elementMetrics.metrics.length; i++) {
					Metric metric = elementMetrics.metrics[i];
					String valueStr = (String) metric2MaxStrLen.get(metric.key);
					if (valueStr == null || metric.displayValue.length() > valueStr.length()) {
						metric2MaxStrLen.put(metric.key, metric.displayValue);
					}
				}
			}
			return metric2MaxStrLen;
		}
	'''

	def setFocusMethod(GenDiagram it) '''
		«generatedMemberComment»
		public void setFocus() {
		}
	'''

	def labelProviderClass(GenDiagram it) '''
		«generatedMemberComment»
		private class Labels extends org.eclipse.jface.viewers.LabelProvider implements org.eclipse.jface.viewers.ITableLabelProvider, org.eclipse.jface.viewers.ITableColorProvider {

			«generatedMemberComment»
			private boolean isElementColumn(int columnIndex) {
				return columnIndex >= getMetricKeys().length;
			}

			«generatedMemberComment»
			public org.eclipse.swt.graphics.Image getColumnImage(Object element, int columnIndex) {
			return isElementColumn(columnIndex) ? ((ElementMetrics) element).elementImage : null;
			}

			«generatedMemberComment»
			public String getColumnText(Object element, int columnIndex) {
			ElementMetrics elementMetrics = (ElementMetrics) element;
			if (columnIndex == getMetricKeys().length) {
				return elementMetrics.targetElementQName;
			}
			final String key = getMetricKeys()[columnIndex];
			Metric metric = elementMetrics.getMetricByKey(key);
			return (metric != null) ? metric.displayValue : "-"; //$NON-NLS-1$
			}

			«generatedMemberComment»
			public org.eclipse.swt.graphics.Color getBackground(Object element, int columnIndex) {
			return null;
			}

			«generatedMemberComment»
			public org.eclipse.swt.graphics.Color getForeground(Object element, int columnIndex) {
			if (isElementColumn(columnIndex)) {
				return null;
			}
			ElementMetrics columnElement = (ElementMetrics) element;
			final String key = getMetricKeys()[columnIndex];
			Metric metric = columnElement.getMetricByKey(key);
			if (metric != null && metric.value != null) {
				if (metric.highLimit != null && metric.highLimit.longValue() < metric.value.longValue()) {
					return org.eclipse.draw2d.ColorConstants.red;
				} else if (metric.lowLimit != null && metric.lowLimit.longValue() > metric.value.longValue()) {
					return 'org.eclipse.draw2d.ColorConstants.blue;
				}
			}
			return null;
			}
		}
	'''
}
