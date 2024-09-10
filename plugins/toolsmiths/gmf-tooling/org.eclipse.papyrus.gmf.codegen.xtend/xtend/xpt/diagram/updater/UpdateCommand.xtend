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
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.diagram.updater

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater
import xpt.Common

@com.google.inject.Singleton class UpdateCommand {
	@Inject extension Common;

	def extendsList(GenDiagramUpdater it) ''''''

	def implementsList(GenDiagramUpdater it) '''implements org.eclipse.core.commands.IHandler'''

	def className(GenDiagramUpdater it) '''«updateCommandClassName»'''
	
	def packageName(GenDiagramUpdater it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagramUpdater it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagramUpdater it) '''«qualifiedClassName(it)»'''

	def UpdateCommand(GenDiagramUpdater it) '''
		«copyright(editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {
		
			«addHandlerListener(it)»
		
			«dispose(it)»
		
			«execute(it)»
		
			«isEnabled(it)»
		
			«isHandled(it)»
		
			«removeHandlerListener(it)»
		
			«additions(it)»
		}
	'''

	def addHandlerListener(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public void addHandlerListener(org.eclipse.core.commands.IHandlerListener handlerListener) {
		}
	'''

	def dispose(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public void dispose() {
		}
	'''

	def execute(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public Object execute(org.eclipse.core.commands.ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
			org.eclipse.jface.viewers.ISelection selection = org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			if (selection instanceof org.eclipse.jface.viewers.IStructuredSelection) {
				org.eclipse.jface.viewers.IStructuredSelection structuredSelection = (org.eclipse.jface.viewers.IStructuredSelection) selection;
				if (structuredSelection.size() != 1) {
					return null;
				}
				if (structuredSelection.getFirstElement() instanceof org.eclipse.gef.EditPart && ((org.eclipse.gef.EditPart) structuredSelection.getFirstElement()).getModel() instanceof org.eclipse.gmf.runtime.notation.View) {
					org.eclipse.emf.ecore.EObject modelElement = ((org.eclipse.gmf.runtime.notation.View) ((org.eclipse.gef.EditPart) structuredSelection.getFirstElement()).getModel()).getElement();
					java.util.List editPolicies = org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy.getRegisteredEditPolicies(modelElement);
					for (java.util.Iterator it = editPolicies.iterator(); it.hasNext();) {
						org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy nextEditPolicy = (org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy) it.next();
						nextEditPolicy.refresh();
					}
					
				}
			}
			return null;
		}
	'''

	def isEnabled(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public boolean isEnabled() {
			return true;
		}
	'''

	def isHandled(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public boolean isHandled() {
			return true;
		}
	'''

	def removeHandlerListener(GenDiagramUpdater it) '''
		«generatedMemberComment»
		public void removeHandlerListener(org.eclipse.core.commands.IHandlerListener handlerListener) {
		}
	'''

	def additions(GenDiagramUpdater it) ''''''
}
