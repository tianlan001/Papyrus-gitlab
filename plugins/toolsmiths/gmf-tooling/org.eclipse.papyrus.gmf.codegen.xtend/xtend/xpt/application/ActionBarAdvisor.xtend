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
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.application

import com.google.inject.Inject
import java.util.List
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenActionFactoryContributionItem
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenApplication
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContributionItem
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContributionManager
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorView
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenGroupMarker
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMenuManager
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenSeparator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenSharedContributionItem
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenToolBarManager
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Common_qvto
import xpt.Externalizer
import xpt.ExternalizerUtils_qvto
import xpt.editor.CreationWizard

@com.google.inject.Singleton class ActionBarAdvisor {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension ExternalizerUtils_qvto;

	@Inject Externalizer xptExternalizer;
	@Inject CreationWizard xptCreationWizard;
	@Inject WorkbenchWindowAdvisor xptWorkbenchWindowAdvisor
	
	def className(GenApplication it) '''«it.actionBarAdvisorClassName»'''

	def packageName(GenApplication it) '''«it.packageName»'''

	def qualifiedClassName(GenApplication it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenApplication it) '''«qualifiedClassName(it)»'''

	def ActionBarAdvisor(GenApplication it) '''
		«copyright(it.editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» extends org.eclipse.ui.application.ActionBarAdvisor {
		
			«generatedMemberComment»
			private org.eclipse.ui.actions.ActionFactory.IWorkbenchAction lockToolBarAction;
		
			«generatedMemberComment»
			   private org.eclipse.ui.actions.ActionFactory.IWorkbenchAction toggleCoolbarAction;
		
			«generatedMemberComment»
			public «className(it)»(org.eclipse.ui.application.IActionBarConfigurer configurer) {
				super(configurer);
			}
		
			«generatedMemberComment»
			private org.eclipse.ui.IWorkbenchWindow getWindow() {
				return getActionBarConfigurer().getWindowConfigurer().getWindow();
			}
		
			«generatedMemberComment»
			protected void makeActions(org.eclipse.ui.IWorkbenchWindow window) {
				toggleCoolbarAction = org.eclipse.ui.actions.ActionFactory.TOGGLE_COOLBAR.create(window);
				      register(toggleCoolbarAction);
				      lockToolBarAction = org.eclipse.ui.actions.ActionFactory.LOCK_TOOL_BAR.create(window);
				      register(lockToolBarAction);
				      «FOR ci : sharedContributionItems»
				      	«makeAction(ci, 'window')»
				      «ENDFOR»
			}
		
			«generatedMemberComment»
			protected void fillMenuBar(org.eclipse.jface.action.IMenuManager menu) {
				«fill(mainMenu, 'menu')»
			}
		
			«generatedMemberComment»
			protected void fillCoolBar(org.eclipse.jface.action.ICoolBarManager toolBar) {
				org.eclipse.jface.action.IMenuManager popUpMenu = new org.eclipse.jface.action.MenuManager();
				popUpMenu.add(new org.eclipse.jface.action.ActionContributionItem(lockToolBarAction));
				popUpMenu.add(new org.eclipse.jface.action.ActionContributionItem(toggleCoolbarAction));
				toolBar.setContextMenuManager(popUpMenu);
				«fill(mainToolBar, 'toolBar')»
			}
		
			«openEditor(editorGen.editor)»
		
			«actions(it)»
		
			«additions(it)»
		}
	'''

	def openEditor(GenEditorView it) '''
		«generatedMemberComment»
		private static boolean openEditor(org.eclipse.ui.IWorkbench workbench, org.eclipse.emf.common.util.URI fileURI) {
			org.eclipse.ui.IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			org.eclipse.ui.IWorkbenchPage page = workbenchWindow.getActivePage();
			org.eclipse.ui.IEditorDescriptor editorDescriptor =
				workbench.getEditorRegistry().getDefaultEditor(fileURI.toFileString());
			if (editorDescriptor == null) {
				org.eclipse.jface.dialogs.MessageDialog.openError(workbenchWindow.getShell(), 
				             «xptExternalizer.accessorCall(editorGen,
			titleKey(i18nKeyForDefaultFileEditorErrorDialog(editorGen.application)))»,
				org.eclipse.osgi.util.NLS.bind(
				    «xptExternalizer.accessorCall(editorGen,
			messageKey(i18nKeyForDefaultFileEditorErrorDialog(editorGen.application)))», 
				    fileURI.toFileString()));
				return false;
			} else {
				try {
					page.openEditor(new org.eclipse.emf.common.ui.URIEditorInput(fileURI), editorDescriptor.getId());
				} catch (org.eclipse.ui.PartInitException exception) {
					org.eclipse.jface.dialogs.MessageDialog.openError(
						workbenchWindow.getShell(), 
						«xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForDefaultEditorOpenErrorDialog(editorGen.application)))»,
						exception.getMessage());
					return false;
				}
			}
			return true;
		}
	'''

	def dispatch makeAction(GenContributionItem it, String windowVar) '''
		«ERROR('Can not make action for ' + it)»
	'''

	def dispatch makeAction(GenActionFactoryContributionItem it, String windowVar) '''
		register(org.eclipse.ui.actions.ActionFactory.«name».create(«windowVar»));
	'''

	def fill(GenContributionManager it, String managerVar) '''
		«FOR i : items»
			«contribute(i, managerVar)»
		«ENDFOR»
	'''

	def dispatch CharSequence contribute(GenContributionItem it, String managerVar) '''
		«ERROR('Can not contribute item: ' + it)»
	'''

	def dispatch CharSequence contribute(GenGroupMarker it, String managerVar) '''
		«managerVar».add(new org.eclipse.jface.action.GroupMarker(«groupName»));
	'''

	def dispatch CharSequence contribute(GenSeparator it, String managerVar) '''
		«managerVar».add(new org.eclipse.jface.action.Separator(«IF null !== groupName »«groupName»«ENDIF»));
	'''

	def dispatch CharSequence contribute(GenMenuManager it, String managerVar) '''
		«var menuVar = managerVar + 'X'»
		{
			org.eclipse.jface.action.IMenuManager «menuVar» = new  org.eclipse.jface.action.MenuManager(
			«IF null !== name »«xptExternalizer.accessorCall(it.editorGen, i18nKeyForMenu(it))»«ELSE»null«ENDIF»«IF null !== ID », «ID»«ENDIF»);
			«FOR i : it.items»
				«contribute(i, menuVar)»
			«ENDFOR»
			«managerVar».add(«menuVar»);
		}
	'''

	def dispatch CharSequence contribute(GenToolBarManager it, String managerVar) '''
		«var toolBarVar = managerVar + 'X'»
		{
			org.eclipse.jface.action.IToolBarManager «toolBarVar» = new  org.eclipse.jface.action.ToolBarManager();
			«FOR i : it.items»
				«contribute(i, toolBarVar)»
			«ENDFOR»
			«managerVar».add(new org.eclipse.jface.action.ToolBarContributionItem(«toolBarVar»«IF null !== ID », «ID»«ENDIF»));
		}
	'''

	def dispatch CharSequence contribute(GenSharedContributionItem it, String managerVar) '''
		«contributeShared(actualItem, managerVar)»
	'''

	def dispatch contributeShared(GenContributionItem it, String managerVar) '''
		«ERROR('Can not contribute shared item: ' + it)»
	'''

	def dispatch contributeShared(GenActionFactoryContributionItem it, String managerVar) '''
		«managerVar».add(getAction(org.eclipse.ui.actions.ActionFactory.«name».getId()));
	'''

	def actions(GenApplication it) '''
		«NewDiagramAction(it)»
		«OpenUriAction(it)»
		«OpenAction(it)»
		«AboutAction(it)»
	'''

	def NewDiagramAction(GenApplication it) '''
		«generatedClassComment»
		public static class NewDiagramAction extends org.eclipse.emf.common.ui.action.WorkbenchWindowActionDelegate {
		
			«generatedMemberComment»
			public void run(org.eclipse.jface.action.IAction action) {
				«xptCreationWizard.qualifiedClassName(editorGen.diagram)» wizard =
					new «xptCreationWizard.qualifiedClassName(editorGen.diagram)»();
				wizard.init(getWindow().getWorkbench(), org.eclipse.jface.viewers.StructuredSelection.EMPTY);
				org.eclipse.jface.wizard.WizardDialog wizardDialog =
					new org.eclipse.jface.wizard.WizardDialog(getWindow().getShell(), wizard);
				wizardDialog.open();
			}
		}
		
	'''

	def OpenUriAction(GenApplication it) '''
		«generatedClassComment»
		public static class OpenURIAction extends org.eclipse.emf.common.ui.action.WorkbenchWindowActionDelegate {
		
			«generatedMemberComment»
			public void run(org.eclipse.jface.action.IAction action) {
				org.eclipse.emf.edit.ui.action.LoadResourceAction.LoadResourceDialog loadResourceDialog =
					new org.eclipse.emf.edit.ui.action.LoadResourceAction.LoadResourceDialog(getWindow().getShell());
				if (org.eclipse.jface.dialogs.Dialog.OK == loadResourceDialog.open()) {
					for (java.util.Iterator i = loadResourceDialog.getURIs().iterator(); i.hasNext();) {
						openEditor(getWindow().getWorkbench(), (org.eclipse.emf.common.util.URI) i.next());
					}
				}
			}
		}
		
	'''

	def OpenAction(GenApplication it) '''
		«generatedClassComment»
		public static class OpenAction extends org.eclipse.emf.common.ui.action.WorkbenchWindowActionDelegate {
		
			«generatedMemberComment»
			public void run(org.eclipse.jface.action.IAction action) {
				org.eclipse.swt.widgets.FileDialog fileDialog =
					new org.eclipse.swt.widgets.FileDialog(getWindow().getShell(), org.eclipse.swt.SWT.OPEN);
				fileDialog.open();
				if (fileDialog.getFileName() != null && fileDialog.getFileName().length() > 0) {
					openEditor(getWindow().getWorkbench(), org.eclipse.emf.common.util.URI.createFileURI(
						fileDialog.getFilterPath() + java.io.File.separator + fileDialog.getFileName()));
				}
			}
		}
	'''

	def AboutAction(GenApplication it) '''
		«generatedClassComment»
		public static class AboutAction extends org.eclipse.emf.common.ui.action.WorkbenchWindowActionDelegate {
		
			«generatedMemberComment»
			public void run(org.eclipse.jface.action.IAction action) {
				org.eclipse.jface.dialogs.MessageDialog.openInformation(getWindow().getShell(), 
				             «xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForAboutDialog(it)))»,
				             «xptExternalizer.accessorCall(editorGen, messageKey(i18nKeyForAboutDialog(it)))»
				);
			}
		}
	'''

	def additions(GenApplication it) ''''''

	@Localization def i18nAccessors(GenApplication it) '''
		«xptExternalizer.accessorField(titleKey(i18nKeyForDefaultFileEditorErrorDialog(it)))»
		«xptExternalizer.accessorField(messageKey(i18nKeyForDefaultFileEditorErrorDialog(it)))»
		«xptExternalizer.accessorField(titleKey(i18nKeyForDefaultEditorOpenErrorDialog(it)))»
		«xptExternalizer.accessorField(titleKey(i18nKeyForAboutDialog(it)))»
		«xptExternalizer.accessorField(messageKey(i18nKeyForAboutDialog(it)))»
		«IF null !== mainMenu »
			«internal_i18nAccessors(mainMenu)»
			«FOR gmm : collectGenMenuManagers(mainMenu.items)»
				«internal_i18nAccessors(gmm)»
			«ENDFOR»
		«ENDIF»
		«FOR gmm : collectGenMenuManagers(sharedContributionItems)»
			«internal_i18nAccessors(gmm)»
		«ENDFOR»
	'''

	@Localization def internal_i18nAccessors(GenMenuManager it) '''
		«IF null !== name »«xptExternalizer.accessorField(i18nKeyForMenu(it))»«ENDIF»
	'''

	@Localization def i18nValues(GenApplication it) '''
		«xptExternalizer.messageEntry(titleKey(i18nKeyForDefaultFileEditorErrorDialog(it)), 'Error')»
		«xptExternalizer.messageEntry(messageKey(i18nKeyForDefaultFileEditorErrorDialog(it)),
			'There is no editor registered for the file \"{0}\"')»
		«xptExternalizer.messageEntry(titleKey(i18nKeyForDefaultEditorOpenErrorDialog(it)), 'Open Editor')»
		«xptExternalizer.messageEntry(titleKey(i18nKeyForAboutDialog(it)), 'About')»
		«xptExternalizer.messageEntry(messageKey(i18nKeyForAboutDialog(it)), editorGen.modelID + ' Diagram Editor')»
		«IF null !== mainMenu »
			«internal_i18nValues(mainMenu)»
			«FOR gmm : collectGenMenuManagers(mainMenu.items)»
				«internal_i18nValues(gmm)»
			«ENDFOR»
		«ENDIF»
		«FOR gmm : collectGenMenuManagers(sharedContributionItems)»
			«internal_i18nValues(gmm)»
		«ENDFOR»
	'''

	@Localization def internal_i18nValues(GenMenuManager it) '''
		«IF null !== name »«xptExternalizer.messageEntry(i18nKeyForMenu(it), name)»«ENDIF»
	'''

	protected def Iterable<GenMenuManager> collectGenMenuManagers(Iterable<GenContributionItem> allItems) {
		return collectAllContributionItems(allItems).filter(typeof(GenMenuManager))
	}

	protected def Iterable<GenContributionManager> collectAllContributionItems(Iterable<GenContributionItem> allItems) {
		return collectAllContributionItems(allItems, <GenContributionManager>newLinkedList())
	}

	protected def Iterable<GenContributionManager> collectAllContributionItems(Iterable<GenContributionItem> allItems,
		List<GenContributionManager> acc) {
		var managers = allItems.filter(typeof(GenContributionManager))
		acc.addAll(managers);
		for (m : managers) {
			collectAllContributionItems(m.items, acc);
		}
		return acc
	}

	@Localization protected def String i18nKeyForMenu(GenMenuManager menuManager) {
		return 'ApplicationMenuName.' + menuManager.name
	}

	@Localization protected def String i18nKeyForWindowTitle(GenApplication app) {
		return xptWorkbenchWindowAdvisor.className(app) + '.Title'
	}

	@Localization protected def String i18nKeyForAboutDialog(GenApplication app) {
		return className(app) + '.AboutDialog'
	}

	@Localization protected def String i18nKeyForDefaultFileEditorErrorDialog(GenApplication app) {
		return className(app) + '.DefaultFileEditor'
	}

	@Localization protected def String i18nKeyForDefaultEditorOpenErrorDialog(GenApplication app) {
		return className(app) + '.DefaultEditorOpenError'
	}

}
