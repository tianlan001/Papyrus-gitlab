/*****************************************************************************
 * Copyright (c) 2007, 2017, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Svyatoslav Kovalsky (Montages) - #410477 "same-generated" code extracted to GMFT-runtime 
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
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

@com.google.inject.Singleton class ModelElementSelectionPage {

	@Inject extension CodeStyle;
	@Inject extension Common;
	@Inject extension ExternalizerUtils_qvto;

	@Inject Activator xptActivator;	
	@Inject Externalizer xptExternalizer;

	def className(GenDiagram it) '''ModelElementSelectionPage'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) '''extends org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part.DefaultModelElementSelectionPage'''

	def ModelElementSelectionPage(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment('Wizard page that allows to select element from model.')»
		public class «className(it)» «extendsList(it)» {

			«constructor(it)»
			«getSelectionTitle(it)»
		}
	'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment»
		public «className(it)»(String pageName) {
			super(«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getItemProvidersAdapterFactory(), pageName);
		}
	'''

	def getSelectionTitle(GenDiagram it) '''
		«generatedMemberComment('Override to provide custom model element description.')»
		«overrideC»
		protected String getSelectionTitle() {
			return «xptExternalizer.accessorCall(editorGen, messageKey(i18nKeyForModelElementSelectionPage(it)))»;
		}
	'''

	@Localization def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(messageKey(i18nKeyForModelElementSelectionPage(it)), 'Select model element:')»
	'''

	@Localization def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(messageKey(i18nKeyForModelElementSelectionPage(it)))»
	'''

	@Localization def String i18nKeyForModelElementSelectionPage(GenDiagram diagram) {
		return className(diagram).toString
	}
}
