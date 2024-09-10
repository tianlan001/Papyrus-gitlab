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
import xpt.CodeStyle
import xpt.Common

@com.google.inject.Singleton class NavigatorGroup{

	@Inject extension Common;
	@Inject extension CodeStyle;

	def className(GenNavigator it) '''«it.navigatorGroupClassName»'''

	def packageName(GenNavigator it) '''«it.packageName»'''

	def qualifiedClassName(GenNavigator it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenNavigator it) '''«qualifiedClassName(it)»'''

	def extendsList(GenNavigator it) '''extends «getAbstractNavigatorItemQualifiedClassName()»'''

	def NavigatorGroup(GenNavigator it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» «extendsList(it)» {

			«attributes(it)»

			«constructor(it)»

			«getGroupName(it)»

			«getIcon(it)»

			«getChildren(it)»

			«addChildren(it)»

			«addChild(it)»

			«isEmpty(it)»

			«equalsMethod(it)»

			«hashCode(it)»
		}
	'''

	def attributes(GenNavigator it) '''
		«generatedMemberComment()»
		private String myGroupName;

		«generatedMemberComment()»
		private String myIcon;

		«generatedMemberComment()»
		private java.util.Collection<java.lang.Object> myChildren = new java.util.LinkedList<«editorGen.diagram.diamondOp('java.lang.Object')»>();
	'''

	def constructor(GenNavigator it) '''
		«generatedMemberComment()»
		«className(it)»(String groupName, String icon, Object parent) {
			super(parent);
			myGroupName = groupName;
			myIcon = icon;
		}
	'''

	def getGroupName(GenNavigator it) '''
			«generatedMemberComment()»
		public String getGroupName() {
			return myGroupName;
		}
	'''

	def getIcon(GenNavigator it) '''
		«generatedMemberComment()»
		public String getIcon() {
			return myIcon;
		}
	'''

	def getChildren(GenNavigator it) '''
		«generatedMemberComment()»
		public Object[] getChildren() {
			return myChildren.toArray();
		}
	'''

	def addChildren(GenNavigator it)  '''
		«generatedMemberComment()»
		public void addChildren(java.util.Collection<java.lang.Object> children) {
			myChildren.addAll(children);
		}
	'''

	def addChild(GenNavigator it) '''
		«generatedMemberComment()»
		public void addChild(Object child) {
			myChildren.add(child);
		}
	'''

	def isEmpty(GenNavigator it) '''
		«generatedMemberComment()»
		public boolean isEmpty() {
			return myChildren.size() == 0;
		}
	'''

	def equalsMethod(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public boolean equals(Object obj) {
			if (obj instanceof «qualifiedClassName(it)») {
				«qualifiedClassName(it)» anotherGroup = («qualifiedClassName(it)») obj;
				if (getGroupName().equals(anotherGroup.getGroupName())) {
					return getParent().equals(anotherGroup.getParent());
				}
			}
			return super.equals(obj);
		}
	'''

	def hashCode(GenNavigator it) '''
		«generatedMemberComment()»
		«overrideC»
		public int hashCode() {
			return getGroupName().hashCode();
		}
	'''
}
