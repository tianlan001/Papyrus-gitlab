/*******************************************************************************
 * Copyright (c) 2008, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package impl.preferences

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomPreferencePage
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPreference
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef

import xpt.Common

@com.google.inject.Singleton class CustomPage {
	@Inject extension Common;

	def protected String computePackageName(GenCustomPreferencePage it) {
		if (it.qualifiedClassName == className) {
			return it.diagram.preferencesPackageName;
		} else {
			return qualifiedClassName.substring(0, qualifiedClassName.length - className.length - ".".length)
		}
	}

	def className(GenCustomPreferencePage it) '''«getClassName()»'''

	def packageName(GenCustomPreferencePage it) '''«computePackageName(it)»'''

	def qualifiedClassName(GenCustomPreferencePage it) '''«packageName(it)».«className(it)»'''

	def Main(GenCustomPreferencePage it) '''
		«copyright(it.diagram.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsList(it)» {

			«FOR pref : preferences»
				«constant(pref)»
			«ENDFOR»

			«generatedMemberComment»
			public «className(it)»() {
				setPreferenceStore(«diagram.editorGen.plugin.activatorQualifiedClassName».getInstance().getPreferenceStore());
			}

			«methods(it)»
			«initDefaultsMethod(it)»
		}
	'''

	def extendsList(GenCustomPreferencePage it) '''extends org.eclipse.gmf.runtime.common.ui.preferences.AbstractPreferencePage'''

	def implementsList(GenCustomPreferencePage it) '''«/* no-op */»'''

	/**
	 * [artem]: the reason I didn't split this template up into two distinct, addFieldsMethod and initHelpMethod, is that
	 * using super class other than oe.gmf...AbstractPreferencePage may require implementation of completely different
	 * set of methods. Besides, there's not to much generated, anyway.
	 */
	def methods(GenCustomPreferencePage it) '''
		«generatedMemberComment»
		protected void addFields(org.eclipse.swt.widgets.Composite parent) {
			// TODO  Provide method implementation
			throw new UnsupportedOperationException();
		}

		«generatedMemberComment»
		protected void initHelp() {
			// TODO implement this method if needed, or leave as no-op
		}
	'''

	def initDefaultsMethod(GenCustomPreferencePage it) '''
		«generatedMemberComment»
		public static void initDefaults(org.eclipse.jface.preference.IPreferenceStore store) {
			«IF it.preferences.empty || it.preferences.exists[p|p.defaultValue === null]»
				// TODO this code is invoked during preference store initialization, please fill
				// the store passed with default preference values.
			«ENDIF»
			«FOR pref : it.preferences.filter[p|p.defaultValue !== null]»
				«setDefaultValue(pref, 'store')»
			«ENDFOR»
		}
	'''

	/**
	 * pair template to initDefaultsMethod, allows external templates invoke generated initDefaults method
	 * Note, CustomPage::Main is expected to be invoked only for templates with generateBoilerplate == true,
	 * hence no extra check when generating with initDefaultsMethod,
	 * however, call_initDefaults may get invoked for any CustomPreferencePage
	 */
	@MetaDef def call_initDefaults(GenCustomPreferencePage it, String storeVarName) '''
		«IF generateBoilerplate»«qualifiedClassName(it)».initDefaults(«storeVarName»);«ENDIF»
	'''

	def constant(GenPreference it) '''
		«generatedMemberComment»
		public static final String «name» = "«key»"; «nonNLS(1)»
	'''

	def setDefaultValue(GenPreference it, String store) '''
		«store».setDefault(«name», «defaultValue»);
	'''
}
