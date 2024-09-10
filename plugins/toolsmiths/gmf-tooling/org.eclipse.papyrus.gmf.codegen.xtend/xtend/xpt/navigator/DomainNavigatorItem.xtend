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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import xpt.Common
import xpt.CodeStyle

@com.google.inject.Singleton class DomainNavigatorItem {
	@Inject extension Common;

	@Inject NavigatorAbstractNavigatorItem xptAbstractNavigatorItem;
	@Inject extension CodeStyle

	def className(GenNavigator it) '''«it.domainNavigatorItemClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def DomainNavigatorItem(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» extends org.eclipse.core.runtime.PlatformObject {

			«registerAdapterFactory(it)»

			«xptAbstractNavigatorItem.attributes(it)»

			«attributes(it)»

			«constructor(it)»

			«xptAbstractNavigatorItem.getParent(it)»

			«getEObject(it)»

			«getPropertySourceProvider(it)»

			«equalsMethod(it)»

			«hashCode(it)»
		}
	'''

	def registerAdapterFactory(GenNavigator it) '''
		«generatedMemberComment()»
		static {
			@SuppressWarnings("rawtypes")
			final Class[] supportedTypes = new Class[] { org.eclipse.emf.ecore.EObject.class, org.eclipse.ui.views.properties.IPropertySource.class };
			org.eclipse.core.runtime.Platform.getAdapterManager().registerAdapters(new org.eclipse.core.runtime.IAdapterFactory() {

				«overrideI»
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Object getAdapter(Object adaptableObject, Class adapterType) {
					if (adaptableObject instanceof «qualifiedClassName(it)») {
						«qualifiedClassName(it)» domainNavigatorItem = («qualifiedClassName(it)») adaptableObject;
						org.eclipse.emf.ecore.EObject eObject = domainNavigatorItem.getEObject();
						if (adapterType == org.eclipse.emf.ecore.EObject.class) {
							return eObject;
						}
						if (adapterType == org.eclipse.ui.views.properties.IPropertySource.class) {
							return domainNavigatorItem.getPropertySourceProvider().getPropertySource(eObject);
						}
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
		private org.eclipse.emf.ecore.EObject myEObject;

		«generatedMemberComment()»
		private org.eclipse.ui.views.properties.IPropertySourceProvider myPropertySourceProvider;
	'''

	def constructor(GenNavigator it) '''
		«generatedMemberComment()»
		public «className(it)»(org.eclipse.emf.ecore.EObject eObject, Object parent, org.eclipse.ui.views.properties.IPropertySourceProvider propertySourceProvider) {
			myParent = parent;
			myEObject = eObject;
			myPropertySourceProvider = propertySourceProvider;
		}
	'''

	def getEObject(GenNavigator it) '''
		«generatedMemberComment()»
		public org.eclipse.emf.ecore.EObject getEObject() {
			return myEObject;
		}
	'''

	def getPropertySourceProvider(GenNavigator it) '''
		«generatedMemberComment()»
		public org.eclipse.ui.views.properties.IPropertySourceProvider getPropertySourceProvider() {
			return myPropertySourceProvider;
		}
	'''

	def equalsMethod(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public boolean equals(Object obj) {
			if (obj instanceof «qualifiedClassName(it)») {
				return org.eclipse.emf.ecore.util.EcoreUtil.getURI(getEObject()).equals(org.eclipse.emf.ecore.util.EcoreUtil.getURI(((«qualifiedClassName(it)») obj).getEObject()));
			}
			return super.equals(obj);
		}
	'''

	def hashCode(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public int hashCode() {
			return org.eclipse.emf.ecore.util.EcoreUtil.getURI(getEObject()).hashCode();
		}
	'''
}
