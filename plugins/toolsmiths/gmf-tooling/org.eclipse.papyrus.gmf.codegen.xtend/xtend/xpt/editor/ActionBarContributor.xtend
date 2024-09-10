/*****************************************************************************
 * Copyright (c) 2007, 2009, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorView
import xpt.Common
import xpt.Common_qvto
import xpt.GenEditorGenerator_qvto
import xpt.providers.MetricProvider
import xpt.CodeStyle

@com.google.inject.Singleton class ActionBarContributor {
	@Inject extension CodeStyle;
	@Inject extension Common;
	@Inject extension Common_qvto;

	@Inject extension GenEditorGenerator_qvto;

	@Inject Editor xptEditor;
	@Inject ValidateAction xptValidateAction;
	@Inject MetricProvider xptMetricProvider;

	def className(GenEditorView it) '''«actionBarContributorClassName»'''

	def packageName(GenEditorView it) '''«it.packageName»'''

	def qualifiedClassName(GenEditorView it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenEditorView it) '''«qualifiedClassName(it)»'''

	def extendsList(GenEditorView it) '''extends org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor'''

	def ActionBarContributor(GenEditorView it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {

			«generatedMemberComment»
			«overrideC»
			protected Class<«xptEditor.qualifiedClassName(it)»> getEditorClass() {
				return «xptEditor.qualifiedClassName(it)».class;
			}

			«generatedMemberComment»
			«overrideC»
			protected String getEditorId() {
				return «xptEditor.qualifiedClassName(it)».ID;
			}
			«initMethod(it)»
		}
	'''

	def initMethod(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		public void init(org.eclipse.ui.IActionBars bars, org.eclipse.ui.IWorkbenchPage page) {
			super.init(bars, page);
			// print preview
			org.eclipse.jface.action.IMenuManager fileMenu = bars.getMenuManager().findMenuUsingPath(org.eclipse.ui.IWorkbenchActionConstants.M_FILE);
			«_assert('fileMenu != null')»
			«IF editorGen.plugin.printingEnabled»
				org.eclipse.jface.action.IAction printPreviewAction = new org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.RenderedPrintPreviewAction(new org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.EnhancedPrintActionHelper());
				fileMenu.insertBefore("print", printPreviewAction); «nonNLS(1)»
			«ELSE»
				fileMenu.remove("pageSetupAction"); «nonNLS(1)»
			«ENDIF»
			«IF editorGen.diagram.validationEnabled || hasAudits(editorGen) || ( editorGen.metrics !== null && editorGen.metrics.metrics.notEmpty)»
				org.eclipse.jface.action.IMenuManager editMenu = bars.getMenuManager().findMenuUsingPath(org.eclipse.ui.IWorkbenchActionConstants.M_EDIT);
				«_assert('editMenu != null')»
				if (editMenu.find("validationGroup") == null) { «nonNLS(1)»
					editMenu.add(new org.eclipse.jface.action.GroupMarker("validationGroup")); «nonNLS(1)»
				}
				«IF editorGen.diagram.validationEnabled || hasAudits(editorGen)»
					org.eclipse.jface.action.IAction validateAction = new «xptValidateAction.qualifiedClassName(editorGen.diagram)»(page);
					editMenu.appendToGroup("validationGroup", validateAction); «nonNLS(1)»
				«ENDIF»
				«IF editorGen.metrics !== null && editorGen.metrics.metrics.notEmpty»
					org.eclipse.jface.action.IAction metricsAction = new «xptMetricProvider.qualifiedClassName(editorGen.diagram)».MetricsAction(page);
					editMenu.appendToGroup("validationGroup", metricsAction); «nonNLS(1)»
				«ENDIF»
			«ENDIF/*hasAudits || hasMetrics */»
		}
	'''
}
