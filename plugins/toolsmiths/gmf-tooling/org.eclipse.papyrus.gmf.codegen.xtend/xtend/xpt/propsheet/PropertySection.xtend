/*****************************************************************************
 * Copyright (c) 2007, 2017, 2021 Borland Software Corporation, Montages, CEA LIST, Artal and others
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
 * Michael Golubev (Montages) - [407242] - common code extracted to gmft.runtime, 
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt.propsheet

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomPropertyTab
import xpt.CodeStyle
import xpt.Common

@com.google.inject.Singleton class PropertySection {
	@Inject extension Common;

	@Inject CodeStyle xptCodeStyle;

	def className(GenCustomPropertyTab it) '''«it.className»'''

	def packageName(GenCustomPropertyTab it) '''«it.sheet.packageName»'''

	def qualifiedClassName(GenCustomPropertyTab it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenCustomPropertyTab it) '''«qualifiedClassName(it)»'''

	def PropertySection(GenCustomPropertyTab it) '''
		«copyright(sheet.editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» «implementsClause(it)» {

			«IF sheet.readOnly»
				«createReadonlyControlsMethod(it)»
			«ENDIF»
			«transfromSelectionMethod(it)»

		}
	'''

	def extendsList(GenCustomPropertyTab it) '''extends org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.sheet.DefaultPropertySection'''

	def implementsClause(GenCustomPropertyTab it) '''implements org.eclipse.ui.views.properties.IPropertySourceProvider'''

	def createReadonlyControlsMethod(GenCustomPropertyTab it) '''
		«generatedMemberComment»
		public void createControls(org.eclipse.swt.widgets.Composite parent, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage aTabbedPropertySheetPage) {
			super.createControls(parent, aTabbedPropertySheetPage);
			forcePageReadOnly();
		}
	'''

	def transfromSelectionMethod(GenCustomPropertyTab it) '''
		«IF 'domain' == ID/*perhaps, override setInput should obey same condition?*/»
			«generatedMemberComment('Modify/unwrap selection.')»
			«xptCodeStyle.overrideC(it.sheet.editorGen.diagram)»
			protected Object transformSelection(Object selected) {
				«transfromSelectionMethodBodyDefault(it)»
				return selected;
			}
		«ENDIF»
	'''

	def transfromSelectionMethodBodyDefault(GenCustomPropertyTab it) '''
		selected = /* super. */transformSelectionToDomain(selected);
	'''
}
