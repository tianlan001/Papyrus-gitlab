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
package xpt.editor

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.Common
import xpt.CodeStyle

@Singleton class ResourceSetModificationListener {

	@Inject extension CodeStyle;
	@Inject extension Common;

	def extendsList(GenDiagram it) '''extends org.eclipse.emf.ecore.util.EContentAdapter'''

	/**
	 * Inner class of DocumentProvider
	 */
	def ResourceSetModificationListener(GenDiagram it) '''
		«generatedClassComment»	
		private class ResourceSetModificationListener «extendsList(it)» {

			«attributes(it)»

			«constructor(it)»

			«notifyChanged(it)»
		}
	'''

	def attributes(GenDiagram it) '''
		«generatedMemberComment»	
		private org.eclipse.emf.transaction.NotificationFilter myModifiedFilter;

		«generatedMemberComment»	
		private ResourceSetInfo myInfo;
	'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment»	
		public ResourceSetModificationListener(ResourceSetInfo info) {
			myInfo = info;
			myModifiedFilter = org.eclipse.emf.transaction.NotificationFilter.createEventTypeFilter(org.eclipse.emf.common.notify.Notification.SET).or(org.eclipse.emf.transaction.NotificationFilter.createEventTypeFilter(org.eclipse.emf.common.notify.Notification.UNSET)).and(org.eclipse.emf.transaction.NotificationFilter.createFeatureFilter(org.eclipse.emf.ecore.resource.Resource.class, org.eclipse.emf.ecore.resource.Resource.RESOURCE__IS_MODIFIED));
		}
	'''

	def notifyChanged(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
			if (notification.getNotifier() instanceof org.eclipse.emf.ecore.resource.ResourceSet) {
				super.notifyChanged(notification);
			}
			if (!notification.isTouch() && myModifiedFilter.matches(notification)) {
				if (notification.getNotifier() instanceof org.eclipse.emf.ecore.resource.Resource) {
					org.eclipse.emf.ecore.resource.Resource resource = (org.eclipse.emf.ecore.resource.Resource) notification.getNotifier();
					if (resource.isLoaded()) {
						boolean modified = false;
						for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = myInfo.getLoadedResourcesIterator(); it.hasNext() && !modified;) {
							org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
							if (nextResource.isLoaded()) {
								modified = nextResource.isModified();
							}
						}
						boolean dirtyStateChanged = false;
						synchronized (myInfo) {
							if (modified != myInfo.fCanBeSaved) {
								myInfo.fCanBeSaved = modified;
								dirtyStateChanged = true;
							}
							«IF null === editorGen.application »
								if (!resource.isModified()) {
									myInfo.setSynchronized(resource);
								}
							«ENDIF»
						}
						if (dirtyStateChanged) {
							fireElementDirtyStateChanged(myInfo.getEditorInput(), modified);
							if (!modified) {
								myInfo.setModificationStamp(computeModificationStamp(myInfo));
							}
						}
					}
				}
			}
		}
	'''
}
