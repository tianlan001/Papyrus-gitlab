/*****************************************************************************
 * Copyright (c) 2007, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/

package xpt.navigator

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigatorChildReference
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin
import xpt.Common
import xpt.editor.VisualIDRegistry
import plugin.Activator
import xpt.CodeStyle

@com.google.inject.Singleton class NavigatorLinkHelper {
	@Inject extension CodeStyle;
	@Inject extension Common;
	@Inject extension Utils_qvto;

	@Inject Activator xptActivator;
	@Inject getEditorInput xptGetEditorInput;
	@Inject NavigatorAbstractNavigatorItem abstractNavigatorItem;
	@Inject NavigatorGroup navigatorGroup;
	@Inject NavigatorItem xptNavigatorItem;

	def className(GenNavigator it) '''«it.linkHelperClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def NavigatorLinkHelper(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» implements org.eclipse.ui.navigator.ILinkHelper {

			«xptGetEditorInput.getEditorInput(editorGen)»

			«findSelection(it)»

			«activateEditor(it)»
		}
	'''

	def findSelection(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public org.eclipse.jface.viewers.IStructuredSelection findSelection(org.eclipse.ui.IEditorInput anInput) {
			«IF getDiagramTopReference(it) !==null »
			«defineDiagramDocument(editorGen.plugin)»
			«ENDIF»
			«findSelectionBody(it)»
		}
	'''

	def defineDiagramDocument(GenPlugin it) '''
		org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument document = «xptActivator.qualifiedClassName(it)».getInstance().getDocumentProvider().getDiagramDocument(anInput);
	'''

	def findSelectionBody(GenNavigator it) '''
		«IF getDiagramTopReference(it) !==null »
		«getDiagramSelection(getDiagramTopReference(it))»
		«ENDIF»
		return org.eclipse.jface.viewers.StructuredSelection.EMPTY;
	'''

	/**
	 * Linking with editor currently supported only for the navigators conteining top
	 * reference to the diagram. In this case diagram node will be selected in navigator.
 	*/
	def getDiagramSelection(GenNavigatorChildReference it) '''
		«getDiagram(navigator.editorGen)»
		if (diagram == null || diagram.eResource() == null) {
			return org.eclipse.jface.viewers.StructuredSelection.EMPTY;
		}
		org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			«IF isInsideGroup()»
				«navigatorGroup.qualifiedClassName(navigator)» parentGroup = new «navigatorGroup.
				qualifiedClassName(navigator)»("«groupName»", "«groupIcon»", «VisualIDRegistry::modelID(navigator.editorGen.diagram)», file);
			«ENDIF»
			«xptNavigatorItem.qualifiedClassName(navigator)» item = new «xptNavigatorItem.qualifiedClassName(navigator)»(diagram, «IF isInsideGroup()»parentGroup«ELSE»file«ENDIF», false);
			«IF isInsideGroup()»
				parentGroup.addChild(item);
			«ENDIF»
			return new org.eclipse.jface.viewers.StructuredSelection(«IF isInsideGroup()»parentGroup«ELSE»item«ENDIF»);
		}
	'''

	def getDiagram(GenEditorGenerator it) '''
		if (document == null) {
			return org.eclipse.jface.viewers.StructuredSelection.EMPTY;
		}
		org.eclipse.gmf.runtime.notation.Diagram diagram = document.getDiagram();
	'''

	def activateEditor(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideI»
		public void activateEditor(org.eclipse.ui.IWorkbenchPage aPage, org.eclipse.jface.viewers.IStructuredSelection aSelection) {
			if (aSelection == null || aSelection.isEmpty()) {
				return;
			}
			if (false == aSelection.getFirstElement() instanceof «abstractNavigatorItem.qualifiedClassName(it)») {
				return;
			}

			«abstractNavigatorItem.qualifiedClassName(it)» abstractNavigatorItem = («abstractNavigatorItem.qualifiedClassName(it)») aSelection.getFirstElement();
			org.eclipse.gmf.runtime.notation.View navigatorView = null;
			if (abstractNavigatorItem instanceof «xptNavigatorItem.qualifiedClassName(it)») {
				navigatorView = ((«xptNavigatorItem.qualifiedClassName(it)») abstractNavigatorItem).getView();
			} else if (abstractNavigatorItem instanceof «navigatorGroup.qualifiedClassName(it)») {
				«navigatorGroup.qualifiedClassName(it)» navigatorGroup = («navigatorGroup.qualifiedClassName(it)») abstractNavigatorItem;
				if (navigatorGroup.getParent() instanceof «xptNavigatorItem.qualifiedClassName(it)») {
					navigatorView = ((«xptNavigatorItem.qualifiedClassName(it)») navigatorGroup.getParent()).getView();
				}«getViewFromShortcut(it)»
			}
			if (navigatorView == null) {
				return;
			}
			org.eclipse.ui.IEditorInput editorInput = getEditorInput(navigatorView.getDiagram());
			org.eclipse.ui.IEditorPart editor = aPage.findEditor(editorInput);
			if (editor == null) {
				return;
			}
			aPage.bringToTop(editor);
			if (editor instanceof «diagramEditorClassFQName(it)») {
				«diagramEditorClassFQName(it)» diagramEditor = («diagramEditorClassFQName(it)») editor;
				org.eclipse.emf.ecore.resource.ResourceSet diagramEditorResourceSet = diagramEditor.getEditingDomain().getResourceSet();
				org.eclipse.emf.ecore.EObject selectedView = diagramEditorResourceSet.getEObject(org.eclipse.emf.ecore.util.EcoreUtil.getURI(navigatorView), true);	
				if (selectedView == null) {
					return;
				}
				org.eclipse.gef.GraphicalViewer graphicalViewer = (org.eclipse.gef.GraphicalViewer) diagramEditor.getAdapter(org.eclipse.gef.GraphicalViewer.class);
				org.eclipse.gef.EditPart selectedEditPart = (org.eclipse.gef.EditPart) graphicalViewer.getEditPartRegistry().get(selectedView);
				if (selectedEditPart != null) {
					graphicalViewer.select(selectedEditPart);
				}
			}
		}
	'''

	def getViewFromShortcut(GenNavigator it) '''
		«IF editorGen.diagram.generateShortcutIcon()»
			else if (navigatorGroup.getParent() instanceof org.eclipse.core.runtime.IAdaptable) {
				navigatorView = (org.eclipse.gmf.runtime.notation.View) ((org.eclipse.core.runtime.IAdaptable) navigatorGroup.getParent()).getAdapter(org.eclipse.gmf.runtime.notation.View.class);
			}
		«ENDIF»
	'''

	def diagramEditorClassFQName(GenNavigator it) '''org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor'''

}
