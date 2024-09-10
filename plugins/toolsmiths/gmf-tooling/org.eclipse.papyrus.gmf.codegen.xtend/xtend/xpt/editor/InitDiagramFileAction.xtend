/*******************************************************************************
 * Copyright (c) 2007, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Dmitry Stadnik (Borland) - rewritten in xpand
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.InitDiagramAction
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Common_qvto
import xpt.Externalizer
import xpt.ExternalizerUtils_qvto
import plugin.Activator
import xpt.CodeStyle

@com.google.inject.Singleton class InitDiagramFileAction {

	@Inject extension CodeStyle;
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension ExternalizerUtils_qvto;

	@Inject Externalizer xptExternalizer;
	@Inject DiagramEditorUtil xptDiagramEditorUtil;
	@Inject Activator xptActivator
	@Inject NewDiagramFileWizard xptNewDiagramFileWizard;

	def className(GenDiagram it) '''«lastSegment(it.initDiagramFileActionQualifiedClassName)»'''

	def packageName(GenDiagram it) '''«withoutLastSegment(it.initDiagramFileActionQualifiedClassName)»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def InitDiagramFileAction(InitDiagramAction it, GenEditorGenerator editorGen) '''
		«InitDiagramFileAction(editorGen, it.qualifiedClassName)»
	'''

	/**
	 * HACK to deal with the InitDiagramAction instance being constructed at codegen time, * and Ant being capable of supplying (existing!) target object only
	 */
	def Hack(GenEditorGenerator it) '''
		«InitDiagramFileAction(it, diagram.initDiagramFileActionQualifiedClassName)»
	'''

	def implementsList(GenEditorGenerator it) // 
	'''«IF it.application === null »«implementsList_PDE(it)»«ELSE»«implementsList_RCP(it)»«ENDIF»'''

	def implementsList_PDE(GenEditorGenerator it) '''implements org.eclipse.ui.IObjectActionDelegate'''

	def implementsList_RCP(GenEditorGenerator it) '''implements org.eclipse.ui.IWorkbenchWindowActionDelegate'''

	def InitDiagramFileAction(GenEditorGenerator editorGen, String qualifiedClassName) '''
		«copyright(editorGen)»
		package «packageName(editorGen.diagram)»;

		«generatedClassComment»
		public class «className(editorGen.diagram)» «implementsList(editorGen)» {
			«IF editorGen.application === null »

				«classBody_PDE(editorGen.diagram)»
			«ELSE»
				«classBody_RCP(editorGen.diagram)»
			«ENDIF»
		}
	'''

	def classBody_RCP(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.ui.IWorkbenchWindow window;

		«generatedMemberComment»
		public void init(org.eclipse.ui.IWorkbenchWindow window) {
			this.window = window;
		}

		«generatedMemberComment»
		public void dispose() {
			window = null;
		}

		«generatedMemberComment»
		«overrideI»
		public void selectionChanged(org.eclipse.jface.action.IAction action, org.eclipse.jface.viewers.ISelection selection) {
		}

		«generatedMemberComment»
		private org.eclipse.swt.widgets.Shell getShell() {
			return window.getShell();
		}

		«generatedMemberComment»
		«overrideI»
		public void run(org.eclipse.jface.action.IAction action) {
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain =
				org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory.INSTANCE.createEditingDomain();
			org.eclipse.emf.ecore.resource.Resource resource =
					«xptDiagramEditorUtil.qualifiedClassName(editorGen.diagram)».openModel(getShell(), «xptExternalizer.accessorCall(editorGen, i18nKeyForInitDiagramOpenFileDialogTitle())», editingDomain);
			if (resource == null || resource.getContents().isEmpty()) {
				return;
			}
			org.eclipse.emf.ecore.EObject diagramRoot = (org.eclipse.emf.ecore.EObject) resource.getContents().get(0);
			org.eclipse.jface.wizard.Wizard wizard = new «xptNewDiagramFileWizard.qualifiedClassName(editorGen.diagram)»(resource.getURI(), diagramRoot, editingDomain);
			wizard.setWindowTitle(org.eclipse.osgi.util.NLS.bind(«xptExternalizer.accessorCall(editorGen, i18nKeyForInitDiagramFileWizardTitle())», «VisualIDRegistry::modelID(editorGen.diagram)»));
			«xptDiagramEditorUtil.qualifiedClassName(editorGen.diagram)».runWizard(getShell(), wizard, "InitDiagramFile"); «nonNLS(1)»
		}
	'''

	def classBody_PDE(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.ui.IWorkbenchPart targetPart;

		«generatedMemberComment»
		private org.eclipse.emf.common.util.URI domainModelURI;

		«generatedMemberComment»
		«overrideI»
		public void setActivePart(org.eclipse.jface.action.IAction action, org.eclipse.ui.IWorkbenchPart targetPart) {
			this.targetPart = targetPart;
		}

		«generatedMemberComment»
		«overrideI»
		public void selectionChanged(org.eclipse.jface.action.IAction action, org.eclipse.jface.viewers.ISelection selection) {
			domainModelURI = null;
			action.setEnabled(false);
			if (selection instanceof org.eclipse.jface.viewers.IStructuredSelection == false || selection.isEmpty()) {
				return;
			}
			org.eclipse.core.resources.IFile file =
				(org.eclipse.core.resources.IFile) ((org.eclipse.jface.viewers.IStructuredSelection) selection).getFirstElement();
			domainModelURI = org.eclipse.emf.common.util.URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			action.setEnabled(true);
		}

		«generatedMemberComment»
		private org.eclipse.swt.widgets.Shell getShell() {
			return targetPart.getSite().getShell();
		}

		«generatedMemberComment»
		«overrideI»
		public void run(org.eclipse.jface.action.IAction action) {
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain =
				org.eclipse.emf.workspace.WorkspaceEditingDomainFactory.INSTANCE.createEditingDomain();
			«IF editorGen.sameFileForDiagramAndModel»
				org.eclipse.emf.ecore.resource.ResourceSet resourceSet = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
			«ELSE»
				org.eclipse.emf.ecore.resource.ResourceSet resourceSet = editingDomain.getResourceSet();
			«ENDIF»
			org.eclipse.emf.ecore.EObject diagramRoot = null;
			try {
				org.eclipse.emf.ecore.resource.Resource resource = resourceSet.getResource(domainModelURI, true);
				diagramRoot = resource.getContents().get(0);
			} catch (org.eclipse.emf.common.util.WrappedException ex) {
				«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Unable to load resource: " + domainModelURI, ex); «nonNLS(1)»
			}
			if (diagramRoot == null) {
				org.eclipse.jface.dialogs.MessageDialog.openError(getShell(), «xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForInitDiagramFileResourceErrorDialog()))», «xptExternalizer.accessorCall(editorGen, messageKey(i18nKeyForInitDiagramFileResourceErrorDialog()))»);
				return;
			}
			org.eclipse.jface.wizard.Wizard wizard = new «editorGen.diagram.getNewDiagramFileWizardQualifiedClassName()»(domainModelURI, diagramRoot, editingDomain);
			wizard.setWindowTitle(org.eclipse.osgi.util.NLS.bind(«xptExternalizer.accessorCall(editorGen, i18nKeyForInitDiagramFileWizardTitle())», «VisualIDRegistry::modelID(editorGen.diagram)»));
			«xptDiagramEditorUtil.qualifiedClassName(editorGen.diagram)».runWizard(getShell(), wizard, "InitDiagramFile"); «nonNLS(1)»
		}
	'''
	def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(titleKey(i18nKeyForInitDiagramFileResourceErrorDialog()))»
		«xptExternalizer.accessorField(messageKey(i18nKeyForInitDiagramFileResourceErrorDialog()))»
		«xptExternalizer.accessorField(i18nKeyForInitDiagramFileWizardTitle())»
		«xptExternalizer.accessorField(i18nKeyForInitDiagramOpenFileDialogTitle())»
	'''

	def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(titleKey(i18nKeyForInitDiagramFileResourceErrorDialog()), 'Error')»
		«xptExternalizer.messageEntry(messageKey(i18nKeyForInitDiagramFileResourceErrorDialog()), 'Model file loading failed')»
		«xptExternalizer.messageEntry(i18nKeyForInitDiagramFileWizardTitle(), 'Initialize new {0} diagram file')»
		«xptExternalizer.messageEntry(i18nKeyForInitDiagramOpenFileDialogTitle(), 'Select domain model')»
	'''

	@Localization def String i18nKeyForInitDiagramFileResourceErrorDialog() {
		return 'InitDiagramFile.ResourceErrorDialog'
	}

	@Localization def String i18nKeyForInitDiagramFileWizardTitle() {
		return 'InitDiagramFile.WizardTitle'
	}

	@Localization def String i18nKeyForInitDiagramOpenFileDialogTitle() {
		return 'InitDiagramFile.OpenModelFileDialogTitle'
	}
}