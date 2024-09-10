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
 *    Dmitry Stadnik (Borland) - initial API and implementation
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up + missing NLS
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import xpt.Common
import xpt.Externalizer
import xpt.CodeStyle

@com.google.inject.Singleton class CreationWizardPage {

	@Inject extension CodeStyle;
	@Inject extension Common;

	@Inject Externalizer xptExternalizer;
	@Inject DiagramEditorUtil xptDiagramEditorUtil;

	def className(GenDiagram it) '''«creationWizardPageClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) '''extends « //
		IF editorGen.application === null »org.eclipse.ui.dialogs.WizardNewFileCreationPage« //
		ELSE»«editorGen.application.packageName».WizardNewFileCreationPage«ENDIF»'''

	def CreationWizardPage(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {

			«generatedMemberComment»
			private final String fileExtension;

			«generatedMemberComment»
			public «className(it)»(String pageName, org.eclipse.jface.viewers.IStructuredSelection selection, String fileExtension) {
			super(pageName, selection);
			this.fileExtension = fileExtension;
			}

			«generatedMemberComment('Override to create files with this extension.')» 
			protected String getExtension() {
				return fileExtension;
			}

			«generatedMemberComment»
			public org.eclipse.emf.common.util.URI getURI() {
			«IF editorGen.application === null »
				return org.eclipse.emf.common.util.URI.createPlatformResourceURI(getFilePath().toString(), false);
			«ELSE»
				return org.eclipse.emf.common.util.URI.createFileURI(getFilePath().toString());
			«ENDIF»
			}
			«IF editorGen.application === null »

				«generatedMemberComment»
				protected org.eclipse.core.runtime.IPath getFilePath() {
					org.eclipse.core.runtime.IPath path = getContainerFullPath();
					if (path == null) {
						path = new org.eclipse.core.runtime.Path(""); //$NON-NLS-1$
					}
					String fileName = getFileName();
					if (fileName != null) {
						path = path.append(fileName);
					}
					return path;
				}
			«ENDIF»

			«generatedMemberComment»
			«overrideC»
			public void createControl(org.eclipse.swt.widgets.Composite parent) {
				super.createControl(parent);
				setFileName(«xptDiagramEditorUtil.qualifiedClassName(it)».getUniqueFileName(getContainerFullPath(), getFileName(), getExtension()));
				setPageComplete(validatePage());
			}

			«generatedMemberComment»
			«overrideC»
			protected boolean validatePage() {
				if (!super.validatePage()) {
					return false;
				}
				String extension = getExtension();
				if (extension != null && !getFilePath().toString().endsWith("." + extension)) { «nonNLS»
					setErrorMessage(org.eclipse.osgi.util.NLS.bind(«xptExternalizer.accessorCall(editorGen, i18nKeyForCreationWizardPageExtensionError(it))», extension));
					return false;
				}
				return true;
			}
		}
	'''

	def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(i18nKeyForCreationWizardPageExtensionError(it), 'File name should have {0} extension.')»
	'''

	def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(i18nKeyForCreationWizardPageExtensionError(it))»
	'''

	@Localization def String i18nKeyForCreationWizardPageExtensionError(GenDiagram diagram) {
		return className(diagram) + 'ExtensionError'
	}

}
