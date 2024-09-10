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

@Singleton class NavigatorItem {

	@Inject extension Common;
	@Inject extension CodeStyle

	def className(GenNavigator it) '''«it.navigatorItemClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def extendsList(GenNavigator it) '''extends «it.abstractNavigatorItemQualifiedClassName»'''

	def NavigatorItem(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» «extendsList(it)» {

			«registerAdapterFactory(it)»

			«attributes(it)»

			«constructor(it)»

			«getView(it)»

			«isLeaf(it)»

			«equalsMethod(it)»

			«hashCode(it)»
		}
	'''

	def registerAdapterFactory(GenNavigator it) '''
		«generatedMemberComment()»
		static {
			@SuppressWarnings("rawtypes")
			final Class[] supportedTypes = new Class[] { org.eclipse.gmf.runtime.notation.View.class, org.eclipse.emf.ecore.EObject.class };
			org.eclipse.core.runtime.Platform.getAdapterManager().registerAdapters(new org.eclipse.core.runtime.IAdapterFactory() {

				«overrideI»
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Object getAdapter(Object adaptableObject, Class adapterType) {
					if (adaptableObject instanceof «qualifiedClassName(it)» && (adapterType == org.eclipse.gmf.runtime.notation.View.class || adapterType == org.eclipse.emf.ecore.EObject.class)) {
						return ((«qualifiedClassName(it)») adaptableObject).getView();
					}
					return null;
				}

				«overrideI»
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class[] getAdapterList() {
					return supportedTypes;
				}
			}, «qualifiedClassName(it)».class);
		}
	'''

	def attributes(GenNavigator it) '''
		«generatedMemberComment()»
		private org.eclipse.gmf.runtime.notation.View myView;

		«generatedMemberComment()»
		private boolean myLeaf = false;	
	'''

	def constructor(GenNavigator it) '''
		«generatedMemberComment()»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view, Object parent, boolean isLeaf) {
			super(parent);
			myView = view;
			myLeaf = isLeaf;
		}
	'''

	def getView(GenNavigator it) '''
		«generatedMemberComment()»
		public org.eclipse.gmf.runtime.notation.View getView() {
			return myView;
		}
	'''

	def isLeaf(GenNavigator it) '''
		«generatedMemberComment()»
		public boolean isLeaf() {
			return myLeaf;
		}
	'''

	def equalsMethod(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public boolean equals(Object obj) {
			if (obj instanceof «qualifiedClassName(it)») {
				return org.eclipse.emf.ecore.util.EcoreUtil.getURI(getView()).equals(org.eclipse.emf.ecore.util.EcoreUtil.getURI(((«getNavigatorItemQualifiedClassName()») obj).getView()));
			}
			return super.equals(obj);
		}
	'''

	def hashCode(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public int hashCode() {
			return org.eclipse.emf.ecore.util.EcoreUtil.getURI(getView()).hashCode();
		}
	'''

}
