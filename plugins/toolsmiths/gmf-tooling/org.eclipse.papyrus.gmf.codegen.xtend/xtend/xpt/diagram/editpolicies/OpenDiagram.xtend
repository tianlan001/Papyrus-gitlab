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
 * Artem Tikhomirov (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.diagram.editpolicies

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.OpenDiagramBehaviour
import plugin.Activator
import xpt.Common
import xpt.Externalizer
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.editor.VisualIDRegistry
import xpt.navigator.getEditorInput
import xpt.editor.Editor
import xpt.editor.DiagramEditorUtil

@com.google.inject.Singleton class OpenDiagram {
	@Inject extension Common;

	@Inject Externalizer xptExternalizer;
	@Inject getEditorInput xptGetEditorInput;
	@Inject Activator xptActivator;
	@Inject Editor xptEditor
	@Inject DiagramEditorUtil xptDiagramEditorUtil;

	def className(OpenDiagramBehaviour it) '''«it.editPolicyClassName»'''

	def packageName(OpenDiagramBehaviour it) '''«it.subject.getDiagram().editPoliciesPackageName»'''

	def qualifiedClassName(OpenDiagramBehaviour it) '''«packageName(it)».«className(it)»'''

	def fullPath(OpenDiagramBehaviour it) '''«qualifiedClassName(it)»'''

	def OpenDiagram(OpenDiagramBehaviour it) '''
		«copyright(it.subject.diagram.editorGen)»
		package «packageName(it)»;
		
		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {
		
			«getOpenCommand(it)»
		
			«openCommandClass(it)»
		
			«additions(it)»
		}
	'''

	def extendsList(OpenDiagramBehaviour it) '''extends org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenEditPolicy'''

	def implementsList(OpenDiagramBehaviour it) ''''''

	def getOpenCommand(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected org.eclipse.gef.commands.Command getOpenCommand(org.eclipse.gef.Request request) {
			org.eclipse.gef.EditPart targetEditPart = getTargetEditPart(request);
			if (false == targetEditPart.getModel() instanceof org.eclipse.gmf.runtime.notation.View) {
				return null;
			}
			org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) targetEditPart.getModel();
			org.eclipse.gmf.runtime.notation.Style link = view.getStyle(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getHintedDiagramLinkStyle());
			if (false == link instanceof org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle) {
				return null;
			}
			return new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(new OpenDiagramCommand((org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle) link));
		}
	'''

	def additions(OpenDiagramBehaviour it) ''''''

	def openCommandClass(OpenDiagramBehaviour it) '''
			«generatedClassComment»
			private static class OpenDiagramCommand «openCommandClass_extendsList(it)» {
			
			«openCommandClass_fields(it)»
			
			«openCommandClass_constructor(it)»
		
				// FIXME canExecute if  !(readOnly && getDiagramToOpen == null), i.e. open works on ro diagrams only when there's associated diagram already
		
				«openCommandClass_doExecuteWithResult(it)»
				
				«openCommandClass_getDiagramToOpen(it)»
				
				«openCommandClass_intializeNewDiagram(it)»
				
				«openCommandClass_getDiagramDomainElement(it)»
				
				«openCommandClass_getPreferencesHint(it)»
		
				«openCommandClass_getDiagramKind(it)»
				
				«openCommandClass_getEditorID(it)»
				
				«openCommandClass_additions(it)»
			}
	'''

	def openCommandClass_extendsList(OpenDiagramBehaviour it) '''extends org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand'''

	def openCommandClass_fields(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		private final org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle diagramFacet;
	'''

	def openCommandClass_constructor(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		OpenDiagramCommand(org.eclipse.gmf.runtime.notation.HintedDiagramLinkStyle linkStyle) {
			// editing domain is taken for original diagram, 
			// if we open diagram from another file, we should use another editing domain
			super(org.eclipse.emf.transaction.util.TransactionUtil.getEditingDomain(linkStyle), «xptExternalizer.
			accessorCall(subject.diagram.editorGen, i18nKeyForOpenCommandName())», null);
			diagramFacet = linkStyle;
		}
	'''

	def openCommandClass_doExecuteWithResult(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
			try {
				org.eclipse.gmf.runtime.notation.Diagram diagram = getDiagramToOpen();
				if (diagram == null) {
					diagram = intializeNewDiagram();
				}
				«xptGetEditorInput.defineURIEditorInput(subject.diagram, 'diagram', 'editorInput')»
				org.eclipse.ui.IWorkbenchPage page = org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				page.openEditor(editorInput, getEditorID());
				return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult();
			} catch (Exception ex) {
				throw new org.eclipse.core.commands.ExecutionException("Can't open diagram", ex);
			}
		}
	'''

	def openCommandClass_getDiagramToOpen(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected org.eclipse.gmf.runtime.notation.Diagram getDiagramToOpen() {
			return diagramFacet.getDiagramLink();
		}
	'''

	def openCommandClass_intializeNewDiagram(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected org.eclipse.gmf.runtime.notation.Diagram intializeNewDiagram() throws org.eclipse.core.commands.ExecutionException {
			org.eclipse.gmf.runtime.notation.Diagram d = org.eclipse.gmf.runtime.diagram.core.services.ViewService.createDiagram(getDiagramDomainElement(), getDiagramKind(), getPreferencesHint());
			if (d == null) {
				throw new org.eclipse.core.commands.ExecutionException("Can't create diagram of '" + getDiagramKind() + "' kind");
			}
			diagramFacet.setDiagramLink(d);
			«_assert('diagramFacet.eResource() != null')»
			diagramFacet.eResource().getContents().add(d);
			org.eclipse.emf.ecore.EObject container = diagramFacet.eContainer();
			while (container instanceof org.eclipse.gmf.runtime.notation.View) {
				((org.eclipse.gmf.runtime.notation.View) container).persist();
				container = container.eContainer();
			}
			try {
			«IF null === subject.diagram.editorGen.application »
				new org.eclipse.ui.actions.WorkspaceModifyOperation() {
					protected void execute(org.eclipse.core.runtime.IProgressMonitor monitor) throws org.eclipse.core.runtime.CoreException, java.lang.reflect.InvocationTargetException, InterruptedException {
						try {
			«ENDIF»
			for (java.util.Iterator<?> it = diagramFacet.eResource().getResourceSet().getResources().iterator(); it.hasNext();) {
				org.eclipse.emf.ecore.resource.Resource nextResource = (org.eclipse.emf.ecore.resource.Resource) it.next();
				if (nextResource.isLoaded() && !getEditingDomain().isReadOnly(nextResource)) {
					nextResource.save(«xptDiagramEditorUtil.qualifiedClassName(subject.diagram)».getSaveOptions());
				}
			}
			«IF null === subject.diagram.editorGen.application »
				} catch (java.io.IOException ex) {
					throw new java.lang.reflect.InvocationTargetException(ex, "Save operation failed");
				}		
				}
				}.run(null);
				} catch (java.lang.reflect.InvocationTargetException e) {
					throw new org.eclipse.core.commands.ExecutionException("Can't create diagram of '" + getDiagramKind() + "' kind", e);
				} catch (InterruptedException e) {
					throw new org.eclipse.core.commands.ExecutionException("Can't create diagram of '" + getDiagramKind() + "' kind", e);
				}
			«ELSE»
				} catch (java.io.IOException ex) {
					throw new org.eclipse.core.commands.ExecutionException("Can't create diagram of '" + getDiagramKind() + "' kind", ex);
				}
			«ENDIF»
			return d;
		}
	'''


	def openCommandClass_getDiagramDomainElement(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected org.eclipse.emf.ecore.EObject getDiagramDomainElement() {
			// use same element as associated with EP
			return ((org.eclipse.gmf.runtime.notation.View) diagramFacet.eContainer()).getElement();
		}
	'''

	def openCommandClass_getPreferencesHint(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint getPreferencesHint() {
			// XXX prefhint from target diagram's editor?
			return «xptActivator.preferenceHintAccess(subject.diagram.editorGen)»;
		}
	'''

	def openCommandClass_getDiagramKind(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected String getDiagramKind() {
			return «IF diagramKind === null »«VisualIDRegistry::modelID(subject.diagram)»«ELSE»"«diagramKind»"«ENDIF»;
		}
	'''

	def openCommandClass_getEditorID(OpenDiagramBehaviour it) '''
		«generatedMemberComment»
		protected String getEditorID() {
			return «IF editorID === null »«xptEditor.qualifiedClassName(subject.diagram.editorGen.editor)».ID«ELSE»"«editorID»"«ENDIF»;
		}
	'''

	def openCommandClass_additions(OpenDiagramBehaviour it) ''''''

	@Localization def String i18nKeyForOpenCommandName() {
		return 'CommandName.OpenDiagram'
	}

	@Localization def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(i18nKeyForOpenCommandName())»
	'''

	@Localization def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(i18nKeyForOpenCommandName(), 'Open Diagram')»
	'''

}
