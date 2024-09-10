/*******************************************************************************
 * Copyright (c) 2012-2013 Montages AG, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package impl.diagram.update

import com.google.inject.Inject
import xpt.Common
import xpt.Common_qvto
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef

@com.google.inject.Singleton class domain2notation {
	@Inject extension Common;
	@Inject extension Common_qvto;

	@MetaDef def DomainToNotationClassName(GenDiagram it) '''Domain2Notation'''

def DomainToNotationClass(GenDiagram it) '''
	«DomainToNotationClass_One2One(it)»
'''

def DomainToNotationClass_One2One_extendsList(GenDiagram it) '''extends java.util.HashMap<org.eclipse.emf.ecore.EObject, org.eclipse.gmf.runtime.notation.View>'''

def DomainToNotationClass_One2One(GenDiagram it) '''
    «generatedMemberComment»
	@SuppressWarnings("serial")
	protected static class «DomainToNotationClassName(it)» «DomainToNotationClass_One2One_extendsList(it)» {
	    «generatedMemberComment»
		public boolean containsDomainElement(org.eclipse.emf.ecore.EObject domainElement){
			return this.containsKey(domainElement);
		}
		
		«generatedMemberComment»
		public org.eclipse.gmf.runtime.notation.View getHinted(org.eclipse.emf.ecore.EObject domainEObject, String hint) {
			return this.get(domainEObject);
		}
		
		«generatedMemberComment»
		public void putView(org.eclipse.emf.ecore.EObject domainElement, org.eclipse.gmf.runtime.notation.View view) {
			«/**
			 * Before GMFT 3.0 the call to put() was guarded by the same check in order to store element only once and to prefer non-shortcuts to shortcuts
			 * As part of the #389368, we moved this guard to callee implementation, to optionally allow *_One2Many case 
			 */»if (!containsKey(view.getElement()) «IF containsShortcutsTo.notEmpty»|| !isShortcut(view)«ENDIF») {
				this.put(domainElement, view);
			}
		}
		
	}
'''

def DomainToNotationClass_One2Many(GenDiagram it) '''
    «generatedMemberComment»
	@SuppressWarnings({"rawtypes", "unchecked"})
	protected static class «DomainToNotationClassName(it)» {
	    «generatedMemberComment»
		private final java.util.HashMap myMap = new java.util.HashMap();
		
	    «generatedMemberComment»
		public boolean containsDomainElement(org.eclipse.emf.ecore.EObject domainElement){
			return myMap.containsKey(domainElement);
		}

	    «generatedMemberComment»
		public boolean containsKey(org.eclipse.emf.ecore.EObject domainElement){
			return containsDomainElement(domainElement);
		}
		
	    «generatedMemberComment»
		public void putView(org.eclipse.emf.ecore.EObject domainElement, org.eclipse.gmf.runtime.notation.View view){
			Object viewOrList = myMap.get(domainElement);
			if (viewOrList instanceof org.eclipse.gmf.runtime.notation.View){
				myMap.remove(domainElement);
				java.util.List<org.eclipse.gmf.runtime.notation.View> list = new java.util.LinkedList<org.eclipse.gmf.runtime.notation.View>();
				list.add((org.eclipse.gmf.runtime.notation.View)viewOrList);
				myMap.put(domainElement, list);
				list.add(view);
			} else if (viewOrList instanceof java.util.List){
				((java.util.List)viewOrList).add(view);
			} else {
				myMap.put(domainElement, view);
			}
		}
		
	    «generatedMemberComment»
		public org.eclipse.gmf.runtime.notation.View get(org.eclipse.emf.ecore.EObject domainEObject){
			Object viewOrList = myMap.get(domainEObject);
			if (viewOrList instanceof org.eclipse.gmf.runtime.notation.View){
				return (org.eclipse.gmf.runtime.notation.View)viewOrList;
			}
			if (viewOrList instanceof java.util.List){
				// preferring not-shortcut to shortcut -- important for cases when links arr to/from 
				// the element that is additionally shortcutted to the same diagram
				for (Object next : (java.util.List)viewOrList){
					org.eclipse.gmf.runtime.notation.View nextView = (org.eclipse.gmf.runtime.notation.View)next;
					if (nextView.getEAnnotation("Shortcut") == null) { «nonNLS(1)»
						return nextView;
					}
				}
				return (org.eclipse.gmf.runtime.notation.View)((java.util.List)viewOrList).get(0);
			}
			return null;
		}
		
	    «generatedMemberComment»
		public org.eclipse.gmf.runtime.notation.View getHinted(org.eclipse.emf.ecore.EObject domainEObject, String hint){
			if (hint == null){
				return get(domainEObject);
			}
			Object viewOrList = myMap.get(domainEObject);
			if (viewOrList instanceof org.eclipse.gmf.runtime.notation.View){
				//no choice, will return what we have
				return (org.eclipse.gmf.runtime.notation.View)viewOrList;
			}
			if (viewOrList instanceof java.util.List){ 
				for (Object next : (java.util.List)viewOrList){
					org.eclipse.gmf.runtime.notation.View nextView = (org.eclipse.gmf.runtime.notation.View)next;
					if (hint.equals(nextView.getType())){
						return nextView;
					}
				}
				//hint not found -- return what we have
				return (org.eclipse.gmf.runtime.notation.View)((java.util.List)viewOrList).get(0);
			}
			return null;
		}
	}
'''
	
}