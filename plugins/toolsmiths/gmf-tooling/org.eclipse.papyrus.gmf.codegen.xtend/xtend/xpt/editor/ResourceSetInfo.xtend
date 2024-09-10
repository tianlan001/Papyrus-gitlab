/*****************************************************************************
 * Copyright (c) 2007, 2015, 2021 Borland Software Corporation, Montages, CEA LIST, Artal and others
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
 * Anatoliy Tischenko  - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import xpt.Common
import xpt.CodeStyle
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import com.google.inject.Singleton

@Singleton class ResourceSetInfo {

	@Inject extension Common;
	@Inject extension CodeStyle

	/**
	 * Inner class of DocumentProvider
	 */
	def ResourceSetInfo(GenDiagram it) '''
		«generatedClassComment»	
		protected class ResourceSetInfo extends ElementInfo {

			«attributes(it)»

			«constructor(it)»

			«getModificationStamp(it)»

			«setModificationStamp(it)»

			«getEditingDomain(it)»

			«getResourceSet(it)»

			«getLoadedResourcesIterator(it)»

			«getEditorInput(it)»

			«dispose(it)»

			«isSynchronized(it)»

		«IF null === editorGen.application »
			«setUnSynchronized(it)»

			«setSynchronized(it)»

			«stopResourceListening(it)»

			«startResourceListening(it)»

		«ENDIF»
			«isUpdateCache(it)»

			«setUpdateCache(it)»

			«isModifiable(it)»

			«setModifiable(it)»

			«isReadOnly(it)»

			«setReadOnly(it)»

		«IF null === editorGen.application »
			«SynchronizerDelegate(it)»
		«ENDIF»
		}
	'''

	def attributes(GenDiagram it) '''
		«generatedMemberComment»
		private long myModificationStamp = «IF null === editorGen.application »org.eclipse.core.resources.IResource.NULL_STAMP«ELSE»0«ENDIF»;

		«IF null === editorGen.application »
			«generatedMemberComment»
			private org.eclipse.emf.workspace.util.WorkspaceSynchronizer mySynchronizer;

			«generatedMemberComment»
			private java.util.LinkedList<org.eclipse.emf.ecore.resource.Resource> myUnSynchronizedResources = new java.util.LinkedList<«diamondOp('org.eclipse.emf.ecore.resource.Resource')»>();

		«ENDIF»
		«generatedMemberComment»
		private org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument myDocument;

		«generatedMemberComment»
		private org.eclipse.ui.IEditorInput myEditorInput;

		«generatedMemberComment»
		private boolean myUpdateCache = true;

		«generatedMemberComment»
		private boolean myModifiable = false;

		«generatedMemberComment»
		private boolean myReadOnly = true;

		«generatedMemberComment»
		private ResourceSetModificationListener myResourceSetListener;
	'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment»
		public ResourceSetInfo(org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument document, org.eclipse.ui.IEditorInput editorInput) {
			super(document);
			myDocument = document;
			myEditorInput = editorInput;
			«IF null === editorGen.application »
			startResourceListening();
			«ENDIF»
			myResourceSetListener = new ResourceSetModificationListener(this);
			getResourceSet().eAdapters().add(myResourceSetListener);
		}
	'''

	def getModificationStamp(GenDiagram it) '''
		«generatedMemberComment»
		public long getModificationStamp() {
			return myModificationStamp;
		}
	'''

	def setModificationStamp(GenDiagram it) '''
		«generatedMemberComment»
		public void setModificationStamp(long modificationStamp) {
			myModificationStamp = modificationStamp;
		}
	'''

	def getEditingDomain(GenDiagram it) '''
		«generatedMemberComment»
		public org.eclipse.emf.transaction.TransactionalEditingDomain getEditingDomain() {
			return myDocument.getEditingDomain();
		}
	'''

	def getResourceSet(GenDiagram it) '''
		«generatedMemberComment»
		public org.eclipse.emf.ecore.resource.ResourceSet getResourceSet() {
			return getEditingDomain().getResourceSet();
		}
	'''

	/**
	 * XXX Would be nice to change API and to return List instead of Iterator - there seems to be little sense using latter.
	 */
	def getLoadedResourcesIterator(GenDiagram it) '''
		«generatedMemberComment»
		public java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> getLoadedResourcesIterator() {
			return new java.util.ArrayList<«diamondOp('org.eclipse.emf.ecore.resource.Resource')»>(getResourceSet().getResources()).iterator();
		}
	'''

	def getEditorInput(GenDiagram it) '''
		«generatedMemberComment»
		public org.eclipse.ui.IEditorInput getEditorInput() {
			return myEditorInput;
		}
	'''

	def dispose(GenDiagram it) '''
		«generatedMemberComment»
		public void dispose() {
			«IF null === editorGen.application »
			stopResourceListening();
			«ENDIF»
			getResourceSet().eAdapters().remove(myResourceSetListener);
			for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = getLoadedResourcesIterator(); it.hasNext();) {
				org.eclipse.emf.ecore.resource.Resource resource = it.next();
				resource.unload();
			}«/* FIXME: Perhaps, should wrap unload into transaction, otherwise each unload triggers a lot of root transactions */»
			getEditingDomain().dispose();
		}
	'''

	def isSynchronized(GenDiagram it) '''
		«generatedMemberComment»
		public boolean isSynchronized() {
			«IF null === editorGen.application »
			return myUnSynchronizedResources.size() == 0;
			«ELSE»
			return getModificationStamp() == computeModificationStamp(this);
			«ENDIF»
		}
	'''

	def setUnSynchronized(GenDiagram it) '''
		«generatedMemberComment»
		public void setUnSynchronized(org.eclipse.emf.ecore.resource.Resource resource) {
			myUnSynchronizedResources.add(resource);
		}
	'''

	def setSynchronized(GenDiagram it) '''
		«generatedMemberComment»
		public void setSynchronized(org.eclipse.emf.ecore.resource.Resource resource) {
			myUnSynchronizedResources.remove(resource);
		}
	'''

	def stopResourceListening(GenDiagram it) '''
		«generatedMemberComment»
		public final void stopResourceListening() {
			mySynchronizer.dispose();
			mySynchronizer = null;
		}
	'''

	def startResourceListening(GenDiagram it) '''
		«generatedMemberComment»
		public final void startResourceListening() {
			mySynchronizer = new org.eclipse.emf.workspace.util.WorkspaceSynchronizer(getEditingDomain(), new SynchronizerDelegate());
		}
	'''

	def isUpdateCache(GenDiagram it) '''
		«generatedMemberComment»
		public boolean isUpdateCache() {
			return myUpdateCache;
		}
	'''

	def setUpdateCache(GenDiagram it) '''
		«generatedMemberComment»
		public void setUpdateCache(boolean update) {
			myUpdateCache = update;
		}
	'''

	def isModifiable(GenDiagram it) '''
		«generatedMemberComment»
		public boolean isModifiable() {
			return myModifiable;
		}
	'''

	def setModifiable(GenDiagram it) '''
		«generatedMemberComment»
		public void setModifiable(boolean modifiable) {
			myModifiable = modifiable;
		}
	'''

	def isReadOnly(GenDiagram it) '''
		«generatedMemberComment»
		public boolean isReadOnly() {
			return myReadOnly;
		}
	'''

	def setReadOnly(GenDiagram it) '''
		«generatedMemberComment»
		public void setReadOnly(boolean readOnly) {
			myReadOnly = readOnly;
		}
	'''

	def SynchronizerDelegate(GenDiagram it) '''
	«generatedClassComment»	
	private class SynchronizerDelegate implements org.eclipse.emf.workspace.util.WorkspaceSynchronizer.Delegate {

		«disposeSD(it)»

		«handleResourceChangedSD(it)»

		«handleResourceDeletedSD(it)»

		«handleResourceMovedSD(it)»
	}
	'''

	def disposeSD(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public void dispose() {
		}
	'''	

	def handleResourceChangedSD(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public boolean handleResourceChanged(final org.eclipse.emf.ecore.resource.Resource resource) {
			«updateSynchStateSD(it)»
			org.eclipse.swt.widgets.Display.getDefault().asyncExec(new java.lang.Runnable() {
				«overrideI»
				public void run() {
					handleElementChanged(ResourceSetInfo.this, resource, null);
				}
			});
			return true;
		}
	'''

	def handleResourceDeletedSD(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public boolean handleResourceDeleted(org.eclipse.emf.ecore.resource.Resource resource) {
			«updateSynchStateSD(it)»
			org.eclipse.swt.widgets.Display.getDefault().asyncExec(new java.lang.Runnable() {
				«overrideI»
				public void run() {
					fireElementDeleted(ResourceSetInfo.this.getEditorInput());
				}
			});
			return true;
		}
	'''

	def handleResourceMovedSD(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public boolean handleResourceMoved(org.eclipse.emf.ecore.resource.Resource resource, final org.eclipse.emf.common.util.URI newURI) {
			«updateSynchStateSD(it)»
			if (myDocument.getDiagram().eResource() == resource) {
				org.eclipse.swt.widgets.Display.getDefault().asyncExec(new java.lang.Runnable() {
					«overrideI»
					public void run() {
						handleElementMoved(ResourceSetInfo.this.getEditorInput(), newURI);
					}
				});
			} else {
				handleResourceDeleted(resource);
			}
			return true;
		}
	'''

	def updateSynchStateSD(GenDiagram it) '''
		synchronized (ResourceSetInfo.this) {
			if (ResourceSetInfo.this.fCanBeSaved) {
				ResourceSetInfo.this.setUnSynchronized(resource);
				return true;
			}
		}
	'''

	def additionsSD(GenDiagram it) ''''''

	def additions(GenDiagram it) ''''''	

}