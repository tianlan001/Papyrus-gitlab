/*****************************************************************************
 * Copyright (c) 2007, 2009, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexander Shatalin (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.navigator

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import xpt.Commonimport xpt.CodeStyle

@Singleton class NavigatorAbstractNavigatorItem {
	@Inject extension Common;
	@Inject extension CodeStyle

	def className(GenNavigator it) '''«it.abstractNavigatorItemClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def NavigatorAbstractNavigatorItem(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public abstract class «className(it)» extends org.eclipse.core.runtime.PlatformObject {

			«IF null !== editorGen.propertySheet »
				«registerAdapterFactory(it)»
			«ENDIF»

			«attributes(it)»
			«constructor(it)»
			«getParent(it)»
		}
	'''

	def registerAdapterFactory(GenNavigator it) '''
		«generatedMemberComment()»
		static {
			@SuppressWarnings("rawtypes")
			final Class[] supportedTypes = new Class[] { org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor.class };
			final org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor propertySheetPageContributor = new org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor() {
				«overrideI»
				public String getContributorId() {
					return "«editorGen.plugin.ID»";  «nonNLS(1)»
				}
			};
			org.eclipse.core.runtime.Platform.getAdapterManager().registerAdapters(new org.eclipse.core.runtime.IAdapterFactory() {
				«overrideI»
				@SuppressWarnings({"rawtypes", "unchecked"})
				public Object getAdapter(Object adaptableObject, Class adapterType) {
					if (adaptableObject instanceof «qualifiedClassName(it)» && adapterType == org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor.class) {
						return propertySheetPageContributor;				
					}
					return null;
				}

				«overrideI»
				@SuppressWarnings({"rawtypes", "unchecked"})
				public Class[] getAdapterList() {
					return supportedTypes;
				}
			}, «qualifiedClassName(it)».class);
		}
	'''

	def attributes(GenNavigator it) '''
		«generatedMemberComment()»
		private Object myParent;
	'''

	def constructor(GenNavigator it) '''
		«generatedMemberComment()»
		protected «className(it)»(Object parent) {
			myParent = parent;
		}
	'''

	def getParent(GenNavigator it) '''
		«generatedMemberComment()»
		public Object getParent() {
			return myParent;
		}
	'''
}
