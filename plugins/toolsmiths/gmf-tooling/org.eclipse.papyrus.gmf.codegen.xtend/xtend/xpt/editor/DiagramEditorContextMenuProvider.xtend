/*****************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up + missing NLS
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import plugin.Activator
import xpt.CodeStyle
import xpt.Common

//We remove the dependency with DeleteElementAction. Now this action is added to the popup menu with the extension point org.eclipse.ui.popup 
//in org.eclipse.papyrus.uml.diagram.common 
@Singleton class DiagramEditorContextMenuProvider {
	@Inject extension Common;
	@Inject extension CodeStyle

	@Inject Activator xptActivator;

	@MetaDef def className(GenDiagram it) '''DiagramEditorContextMenuProvider'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def DiagramEditorContextMenuProvider(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» extends org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider {

			«generatedMemberComment»
			private org.eclipse.ui.IWorkbenchPart part;

			«generatedMemberComment»
			public DiagramEditorContextMenuProvider(org.eclipse.ui.IWorkbenchPart part, org.eclipse.gef.EditPartViewer viewer) {
				super(part, viewer);
				this.part = part;
			}

			«generatedMemberComment»
			«overrideI»
			public void buildContextMenu(final org.eclipse.jface.action.IMenuManager menu) {
				getViewer().flush();
				try {
					org.eclipse.emf.transaction.util.TransactionUtil.getEditingDomain((org.eclipse.emf.ecore.EObject) getViewer().getContents().getModel()).runExclusive(new Runnable() {
						«overrideI(it.editorGen.diagram)»
						public void run() {
							org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.ContributionItemService.getInstance().contributeToPopupMenu(DiagramEditorContextMenuProvider.this, part);
							menu.remove(org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds.ACTION_DELETE_FROM_MODEL);
						}
					});
				} catch (Exception e) {
					«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Error building context menu", e); «nonNLS»
			}
			}
		}
	'''
}
