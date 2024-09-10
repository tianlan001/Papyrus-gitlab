/*******************************************************************************
 * Copyright (c) 2007-2020 Borland Software Corporation, CEA LIST, Artal and others
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
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.diagram.commands

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.Common

@com.google.inject.Singleton class ReorientLinkViewCommand {
	@Inject extension Common;

	def className(GenDiagram it) '''«it.reorientConnectionViewCommandClassName»'''

	def packageName(GenDiagram it) '''«it.editCommandsPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def ReorientLinkViewCommand(GenDiagram it) '''
«copyright(editorGen)»
package «packageName(it)»;

«generatedClassComment()»
public class «className(it)»
		extends org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand {

	«generatedMemberComment()»
	private org.eclipse.core.runtime.IAdaptable edgeAdaptor;

	«generatedMemberComment()»
	public «className(it)»(
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain, String label) {
		super(editingDomain, label, null);
	}

	«generatedMemberComment()»
	public java.util.List getAffectedFiles() {
		org.eclipse.gmf.runtime.notation.View view =
				(org.eclipse.gmf.runtime.notation.View) edgeAdaptor.getAdapter(org.eclipse.gmf.runtime.notation.View.class);
		if (view != null) {
			return getWorkspaceFiles(view);
		}
		return super.getAffectedFiles();
	}

	«generatedMemberComment()»
	public org.eclipse.core.runtime.IAdaptable getEdgeAdaptor() {
		return edgeAdaptor;
	}

	«generatedMemberComment()»
	public void setEdgeAdaptor(org.eclipse.core.runtime.IAdaptable edgeAdaptor) {
		this.edgeAdaptor = edgeAdaptor;
	}

	«generatedMemberComment()»
	protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor progressMonitor, org.eclipse.core.runtime.IAdaptable info) {
		«_assert('null != edgeAdaptor : \"Null child in ' + className(it) + '\"')»
		org.eclipse.gmf.runtime.notation.Edge edge = (org.eclipse.gmf.runtime.notation.Edge) getEdgeAdaptor().getAdapter(org.eclipse.gmf.runtime.notation.Edge.class);
		«_assert('null != edge : \"Null edge in ' + className(it) + '\"')»
		org.eclipse.gmf.runtime.notation.View tempView = edge.getSource();
		edge.setSource(edge.getTarget());
		edge.setTarget(tempView);
		return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult();
	}
	
	«additions(it)»
}
'''
	
	def additions(GenDiagram it) ''''''

}
