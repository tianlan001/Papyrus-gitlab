/*****************************************************************************
 * Copyright (c) 2014, 2021 Borland Software Corporation, Montages, CEA LIST, Artal and others
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
 * Anatloyi Tischenko - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Externalizer
import xpt.ExternalizerUtils_qvto
import plugin.Activator
import xpt.CodeStyle

@com.google.inject.Singleton class CreationWizard {
	@Inject extension Common;
	@Inject extension CodeStyle;
	@Inject extension GenDiagram_qvto;

	@Inject extension ExternalizerUtils_qvto;
	@Inject Externalizer xptExternalizer;
	@Inject CreationWizardPage xptCreationWizardPage
	@Inject DiagramEditorUtil xptDiagramEditorUtil
	@Inject Activator xptActivator

	def className(GenDiagram it) '''«creationWizardClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) '''extends org.eclipse.jface.wizard.Wizard'''

	def implementsList(GenDiagram it) '''implements org.eclipse.ui.INewWizard'''

	def CreationWizard(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {

			«generatedMemberComment»
			private org.eclipse.ui.IWorkbench workbench;

			«generatedMemberComment»
			protected org.eclipse.jface.viewers.IStructuredSelection selection;

			«generatedMemberComment»
			protected «xptCreationWizardPage.qualifiedClassName(it)» diagramModelFilePage;

			«IF standaloneDomainModel(it)»
				«generatedMemberComment»
				protected «xptCreationWizardPage.qualifiedClassName(it)» domainModelFilePage;
			«ENDIF»

			«generatedMemberComment»
			protected org.eclipse.emf.ecore.resource.Resource diagram;

			«generatedMemberComment»
			private boolean openNewlyCreatedDiagramEditor = true;

			«generatedMemberComment»
			public org.eclipse.ui.IWorkbench getWorkbench() {
				return workbench;
			}

			«generatedMemberComment»
			public org.eclipse.jface.viewers.IStructuredSelection getSelection() {
				return selection;
			}

			«generatedMemberComment»
			public final org.eclipse.emf.ecore.resource.Resource getDiagram() {
				return diagram;
			}

			«generatedMemberComment»
			public final boolean isOpenNewlyCreatedDiagramEditor() {
				return openNewlyCreatedDiagramEditor;
			}

			«generatedMemberComment»
			public void setOpenNewlyCreatedDiagramEditor(boolean openNewlyCreatedDiagramEditor) {
				this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
			}

			«generatedMemberComment»
			«overrideC»
			public void init(org.eclipse.ui.IWorkbench workbench, org.eclipse.jface.viewers.IStructuredSelection selection) {
				this.workbench = workbench;
				this.selection = selection;
				setWindowTitle(«xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForCreationWizard(it)))»);
				setDefaultPageImageDescriptor(«xptActivator.qualifiedClassName(editorGen.plugin)».getBundledImageDescriptor("icons/wizban/New«IF domainDiagramElement !== null »«domainDiagramElement.genPackage.prefix»«ENDIF»Wizard.gif")); //$NON-NLS-1$
				setNeedsProgressMonitor(true);
			}

			«generatedMemberComment»
			«overrideC»
			public void addPages() {
				diagramModelFilePage = new «xptCreationWizardPage.qualifiedClassName(it)»("DiagramModelFile", getSelection(), "«editorGen.diagramFileExtension»"); //$NON-NLS-1$ //$NON-NLS-2$
				diagramModelFilePage.setTitle(«xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForCreationWizardDiagramPage(it)))»);
				diagramModelFilePage.setDescription(«xptExternalizer.accessorCall(editorGen, descriptionKey(i18nKeyForCreationWizardDiagramPage(it)))»);
				addPage(diagramModelFilePage);
				«IF standaloneDomainModel(it)»
					domainModelFilePage = new «xptCreationWizardPage.qualifiedClassName(it)»("DomainModelFile", getSelection(), "«editorGen.domainFileExtension»") { //$NON-NLS-1$ //$NON-NLS-2$
					«overrideC»
					public void setVisible(boolean visible) {
						if (visible) {
							String fileName = diagramModelFilePage.getFileName();
							fileName = fileName.substring(0, fileName.length() - ".«editorGen.diagramFileExtension»".length()); //$NON-NLS-1$
							setFileName(«xptDiagramEditorUtil.qualifiedClassName(it)».getUniqueFileName(getContainerFullPath(), fileName, "«editorGen.domainFileExtension»")); //$NON-NLS-1$
						}
						super.setVisible(visible);
					}
					};
					domainModelFilePage.setTitle(«xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForCreationWizardDomainPage(it)))»);
					domainModelFilePage.setDescription(«xptExternalizer.accessorCall(editorGen, descriptionKey(i18nKeyForCreationWizardDomainPage(it)))»);
					addPage(domainModelFilePage);
				«ENDIF»
			}

			«generatedMemberComment»
			«overrideC»
			public boolean performFinish() {
				org.eclipse.jface.operation.IRunnableWithProgress op = 
				«IF editorGen.application === null »
					new org.eclipse.ui.actions.WorkspaceModifyOperation(null) {
						«overrideC»
						protected void execute(org.eclipse.core.runtime.IProgressMonitor monitor) throws org.eclipse.core.runtime.CoreException, InterruptedException {
				«ELSE»
					new org.eclipse.jface.operation.IRunnableWithProgress() {
						«overrideI»
						public void run(org.eclipse.core.runtime.IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				«ENDIF»
					diagram = «xptDiagramEditorUtil.qualifiedClassName(it)».createDiagram(diagramModelFilePage.getURI(), «IF standaloneDomainModel(it)»domainModelFilePage.getURI(), «ENDIF»monitor);
					if (isOpenNewlyCreatedDiagramEditor() && diagram != null) {
							try {
								«xptDiagramEditorUtil.qualifiedClassName(it)».openDiagram(diagram);
							} catch (org.eclipse.ui.PartInitException e) {
								org.eclipse.jface.dialogs.ErrorDialog.openError(getContainer().getShell(), «xptExternalizer.accessorCall(editorGen, i18nKeyForCreationWizardOpenEditorError(it))», null, e.getStatus());
							}
						}
					}
				};
				try {
					getContainer().run(false, true, op);
				} catch (InterruptedException e) {
					return false;
				} catch (java.lang.reflect.InvocationTargetException e) {
					if (e.getTargetException() instanceof org.eclipse.core.runtime.CoreException) {
						org.eclipse.jface.dialogs.ErrorDialog.openError(getContainer().getShell(), «xptExternalizer.accessorCall(editorGen, i18nKeyForCreationWizardCreationError(it))», null, ((org.eclipse.core.runtime.CoreException) e.getTargetException()).getStatus());
					} else {
						«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Error creating diagram", e.getTargetException()); //$NON-NLS-1$
					}
					return false;
				}
				return diagram != null;
			}
		}
	'''

	def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(titleKey(i18nKeyForCreationWizard(it)), 'New ' + editorGen.modelID + ' Diagram')»
		«xptExternalizer.messageEntry(titleKey(i18nKeyForCreationWizardDiagramPage(it)), 'Create ' + editorGen.modelID + ' Diagram')»
		«xptExternalizer.messageEntry(descriptionKey(i18nKeyForCreationWizardDiagramPage(it)), (if(standaloneDomainModel(it)) 'Select file that will contain diagram model.' else 'Select file that will contain diagram and domain models.'))»
		«IF standaloneDomainModel(it)»
			«xptExternalizer.messageEntry(titleKey(i18nKeyForCreationWizardDomainPage(it)), 'Create ' + editorGen.modelID + ' Domain Model')»
			«xptExternalizer.messageEntry(descriptionKey(i18nKeyForCreationWizardDomainPage(it)), 'Select file that will contain domain model.')»
		«ENDIF»
		«xptExternalizer.messageEntry(i18nKeyForCreationWizardOpenEditorError(it), 'Error opening diagram editor')»
		«xptExternalizer.messageEntry(i18nKeyForCreationWizardCreationError(it), 'Creation Problems')»
	'''

	def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(titleKey(i18nKeyForCreationWizard(it)))»
		«xptExternalizer.accessorField(titleKey(i18nKeyForCreationWizardDiagramPage(it)))»
		«xptExternalizer.accessorField(descriptionKey(i18nKeyForCreationWizardDiagramPage(it)))»
		«IF standaloneDomainModel(it)»
			«xptExternalizer.accessorField(titleKey(i18nKeyForCreationWizardDomainPage(it)))»
			«xptExternalizer.accessorField(descriptionKey(i18nKeyForCreationWizardDomainPage(it)))»
		«ENDIF»
		«xptExternalizer.accessorField(i18nKeyForCreationWizardOpenEditorError(it))»
		«xptExternalizer.accessorField(i18nKeyForCreationWizardCreationError(it))»
	'''

	@Localization def String i18nKeyForCreationWizard(GenDiagram diagram) {
		return '' + className(diagram)
	}

	@Localization def String i18nKeyForCreationWizardDiagramPage(GenDiagram diagram) {
		return '' + className(diagram) + '.DiagramModelFilePage'
	}

	@Localization def String i18nKeyForCreationWizardDomainPage(GenDiagram diagram) {
		return '' + className(diagram) + '.DomainModelFilePage'
	}

	@Localization def String i18nKeyForCreationWizardOpenEditorError(GenDiagram diagram) {
		return '' + className(diagram) + 'OpenEditorError'
	}

	@Localization def String i18nKeyForCreationWizardCreationError(GenDiagram diagram) {
		return '' + className(diagram) + 'CreationError'
	}

}