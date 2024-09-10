/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.navigator

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Externalizer
import xpt.editor.Editor
import xpt.editor.VisualIDRegistry

@Singleton class NavigatorActionProvider {
	@Inject extension CodeStyle;
	@Inject extension Common;

	@Inject Activator xptActivator;
	@Inject Externalizer xptExternalizer;  
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject getEditorInput xptGetEditorInput;
	@Inject NavigatorItem xptNavigatorItem;
	@Inject Editor xptEditor;

	def className(GenNavigator it) '''«it.actionProviderClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def NavigatorActionProvider(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)»  extends org.eclipse.ui.navigator.CommonActionProvider {

			«attributes(it)»
			«constructor(it)»
			«makeActions(it)»
			«fillActionBars(it)»
			«fillContextMenu(it)»
			«OpenDiagramAction(it)»
		}
	'''

	def attributes(GenNavigator it) '''
		«generatedMemberComment()»
		private boolean myContribute;

		«generatedMemberComment()»
		private OpenDiagramAction myOpenDiagramAction;
	'''

	def constructor(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public void init(org.eclipse.ui.navigator.ICommonActionExtensionSite aSite) {
			super.init(aSite);
			if (aSite.getViewSite() instanceof org.eclipse.ui.navigator.ICommonViewerWorkbenchSite) {
				myContribute = true;
				makeActions((org.eclipse.ui.navigator.ICommonViewerWorkbenchSite) aSite.getViewSite());
			} else {
				myContribute = false;
			}
		}
	'''

	def makeActions(GenNavigator it) '''
		«generatedMemberComment()»
		private void makeActions(org.eclipse.ui.navigator.ICommonViewerWorkbenchSite viewerSite) {
			myOpenDiagramAction = new OpenDiagramAction(viewerSite);
		}
	'''

	def fillActionBars(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public void fillActionBars(org.eclipse.ui.IActionBars actionBars) {
			if (!myContribute) {
				return;
			}
			org.eclipse.jface.viewers.IStructuredSelection selection = (org.eclipse.jface.viewers.IStructuredSelection) getContext().getSelection();
			myOpenDiagramAction.selectionChanged(selection);
			if (myOpenDiagramAction.isEnabled()) {
				actionBars.setGlobalActionHandler(org.eclipse.ui.navigator.ICommonActionConstants.OPEN, myOpenDiagramAction);
			}
		}
	'''

	def fillContextMenu(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public void fillContextMenu(org.eclipse.jface.action.IMenuManager menu) {
		«/**
	  	  * Put following code into the template to generate popup menu
		  *
				if (!myContribute || getContext().getSelection().isEmpty()) {
					return;
				}

				org.eclipse.jface.viewers.IStructuredSelection selection = (org.eclipse.jface.viewers.IStructuredSelection) getContext().getSelection();

				myOpenDiagramAction.selectionChanged(selection);
				if (myOpenDiagramAction.isEnabled()) {
					menu.insertAfter(org.eclipse.ui.navigator.ICommonMenuConstants.GROUP_OPEN, myOpenDiagramAction);
				}
		   */
		   »}
	'''

	def OpenDiagramAction(GenNavigator it) '''
		«generatedClassComment()»
		private static class OpenDiagramAction extends org.eclipse.jface.action.Action {

			«ODA_attributes(it)»

			«ODA_constructor(it)»

			«ODA_selectionChanged(it)»

			«ODA_run(it)»

			«ODA_getEditorInput(it)»
		}
	'''

	def ODA_attributes(GenNavigator it) '''
		«generatedMemberComment()»
		private org.eclipse.gmf.runtime.notation.Diagram myDiagram;

		«generatedMemberComment()»
		private org.eclipse.ui.navigator.ICommonViewerWorkbenchSite myViewerSite;
	'''

	def ODA_constructor(GenNavigator it) '''	
		«generatedMemberComment()»
		public OpenDiagramAction(org.eclipse.ui.navigator.ICommonViewerWorkbenchSite viewerSite) {
			super(«xptExternalizer.accessorCall(editorGen, i18nKeyForOpenDiagramActionName())»);
			myViewerSite = viewerSite;
		}
	'''

	def ODA_selectionChanged(GenNavigator it) '''
		«generatedMemberComment()»
		public final void selectionChanged(org.eclipse.jface.viewers.IStructuredSelection selection) {
			myDiagram = null;
			if (selection.size() == 1) {
				Object selectedElement = selection.getFirstElement();
				if (selectedElement instanceof «xptNavigatorItem.qualifiedClassName(it)») {
					selectedElement = ((«xptNavigatorItem.qualifiedClassName(it)») selectedElement).getView();
				} else if (selectedElement instanceof org.eclipse.core.runtime.IAdaptable) {
					selectedElement = ((org.eclipse.core.runtime.IAdaptable) selectedElement).getAdapter(org.eclipse.gmf.runtime.notation.View.class);
				}
				if (selectedElement instanceof org.eclipse.gmf.runtime.notation.Diagram) {
					org.eclipse.gmf.runtime.notation.Diagram diagram = (org.eclipse.gmf.runtime.notation.Diagram) selectedElement;
					if («VisualIDRegistry::modelID(editorGen.diagram)».equals(«xptVisualIDRegistry.getModelIDMethodCall(editorGen.diagram)»(diagram))) {
						myDiagram = diagram;
					}
				}
			}
			setEnabled(myDiagram != null);
		}
	'''

	def ODA_run(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public void run() {
			if (myDiagram == null || myDiagram.eResource() == null) {
				return;
			}

			org.eclipse.ui.IEditorInput editorInput = getEditorInput(myDiagram);
			org.eclipse.ui.IWorkbenchPage page = myViewerSite.getPage();
		 	try {
				page.openEditor(editorInput, «xptEditor.qualifiedClassName(editorGen.editor)».ID);
			} catch (org.eclipse.ui.PartInitException e) {
				«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Exception while openning diagram", e);  «nonNLS(1)»
			}
		}
	'''

	def ODA_getEditorInput(GenNavigator it) '''
		«xptGetEditorInput.getEditorInput(editorGen)»
	'''

	@Localization def i18nValues(GenNavigator it) '''
		«xptExternalizer.messageEntry(i18nKeyForOpenDiagramActionName(), 'Open Diagram')»
	'''

	@Localization def i18nAccessors(GenNavigator it) '''
		«xptExternalizer.accessorField(i18nKeyForOpenDiagramActionName())»
	'''

	@Localization def String i18nKeyForOpenDiagramActionName() {
		return 'NavigatorActionProvider.OpenDiagramActionName'
	}

}