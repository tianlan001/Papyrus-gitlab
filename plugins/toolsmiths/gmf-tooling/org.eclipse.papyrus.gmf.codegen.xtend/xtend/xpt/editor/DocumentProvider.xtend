/*****************************************************************************
 * Copyright (c) 2007, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Externalizer

@Singleton class DocumentProvider {
	@Inject extension Common;
	@Inject extension CodeStyle
	@Inject Activator xptActivator;
	@Inject Externalizer xptExternalizer;
	@Inject ResourceSetInfo xptResourceSetInfo;
	@Inject ResourceSetModificationListener xptResourceSetModificationListener;
	@Inject DiagramEditorUtil xptDiagramEditorUtil;

	def className(GenDiagram it) '''«documentProviderClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) '''extends org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.AbstractDocumentProvider'''
	def implementsList(GenDiagram it) '''implements org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocumentProvider'''

	def DocumentProvider(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		@SuppressWarnings("restriction")
		public class «className(it)» «extendsList(it)» «implementsList(it)» {

			«createElementInfo(it)»

			«createDocument(it)»

			«setupDocument(it)»

			«computeModificationStamp(it)»

			«createEmptyDocument(it)»

			«createEditingDomain(it)»

			«setDocumentContent(it)»

			«getModificationStamp(it)»

			«isDeleted(it)»

			«getResourceSetInfo(it)»

			«disposeElementInfo(it)»

			«IF null === editorGen.application »
				«doValidateState(it)»
			«ENDIF»

			«isReadOnly(it)»

			«isModifiable(it)»

			«updateCache(it)»

			«doUpdateStateCache(it)»

			«isSynchronized(it)»

			«IF null === editorGen.application »
				«getResetRule(it)»
				«getSaveRule(it)»
				«getSynchronizeRule(it)»
				«getValidateStateRule(it)»
				«computeSchedulingRule(it)»
			«ENDIF»

			«doSynchronize(it)»

			«doSaveDocument(it)»

			«handleElementChanged(it)»

			«handleElementMoved(it)»

			«createInputWithEditingDomain(it)»

			«getDiagramDocument(it)»

			«getOperationRunner(it)»
			«IF null !== editorGen.application »

			«getFile(it)»
			«ENDIF»

			«xptResourceSetInfo.ResourceSetInfo(it)»

			«xptResourceSetModificationListener.ResourceSetModificationListener(it)»
		}
	'''

	def createElementInfo(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected ElementInfo createElementInfo(Object element) throws org.eclipse.core.runtime.CoreException {
			«checkEditorInputInstance(it)»
			org.eclipse.ui.IEditorInput editorInput = (org.eclipse.ui.IEditorInput) element;
			org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument document = (org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument) createDocument(editorInput);
			ResourceSetInfo info = new ResourceSetInfo(document, editorInput);
			info.setModificationStamp(computeModificationStamp(info));
			info.fStatus = null;
			return info;
		}
	'''

	def checkEditorInputInstance(GenDiagram it) '''
		if («IF null === it.editorGen.application »false == element instanceof «fileEditorInputClassFQName(it)» && «ENDIF»false == element instanceof «uriEditorInputClassFQName(it)») {
			«throwIncorrectInputException(it)»
		}
	'''

	def throwIncorrectInputException(GenDiagram it) '''throw new org.eclipse.core.runtime.CoreException(new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, «xptActivator.qualifiedClassName(editorGen.plugin)».ID, 0, org.eclipse.osgi.util.NLS.bind(«xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentProviderIncorrectInputError(it))»,
		new Object[] {element, «IF null === it.editorGen.application »"«fileEditorInputClassFQName(it)»", «ENDIF»"«uriEditorInputClassFQName(it)»"}), null)); «nonNLS(1)»«IF null === editorGen.application » «nonNLS(2)»«ENDIF»'''

	def fileEditorInputClassFQName(GenDiagram it) '''org.eclipse.ui.part.FileEditorInput'''

	def uriEditorInputClassFQName(GenDiagram it) '''org.eclipse.emf.common.ui.URIEditorInput'''

	def createDocument(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument createDocument(Object element) throws org.eclipse.core.runtime.CoreException {
			«checkEditorInputInstance(it)»
			org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument document = createEmptyDocument();
			setDocumentContent(document, (org.eclipse.ui.IEditorInput) element);
			setupDocument(element, document);
			return document;
		}
	'''

	def setupDocument(GenDiagram it) '''
		«generatedMemberComment('Sets up the given document as it would be provided for the given element. The\n' + 
			'content of the document is not changed. This default implementation is empty.\n' + 
			'Subclasses may reimplement.\n' + 
			'\n' + 
			'@param element\n'+
			'           the blue-print element\n' + 
			'@param document\n'+
			'           the document to set up'
		)»
		protected void setupDocument(Object element, org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument document) {
			// for subclasses
		}
	'''

	def computeModificationStamp(GenDiagram it) '''
		«generatedMemberComment»
		private long computeModificationStamp(ResourceSetInfo info) {
			int result = 0;
			for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
				org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
			«IF null === it.editorGen.application »
				org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(nextResource);
				if (file != null) {
					if (file.getLocation() != null) {
						result += file.getLocation().toFile().lastModified();
					} else {
						result += file.getModificationStamp();
					}
				}
			«ELSE»
				java.io.File file = getFile(nextResource);
				if (file != null && file.exists()) {
					result += file.lastModified();
				}
			«ENDIF»
			}
			return result;
		}
	'''

	def createEmptyDocument(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		protected org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument createEmptyDocument() {
			org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramDocument document = new org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramDocument();
			document.setEditingDomain(createEditingDomain());
			return document;
		}
	'''

	def createEditingDomain(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.emf.transaction.TransactionalEditingDomain createEditingDomain() {
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = org.eclipse.gmf.runtime.diagram.core.DiagramEditingDomainFactory.getInstance().createEditingDomain();
			editingDomain.setID("«editingDomainID»"); «nonNLS(1)»
			final org.eclipse.emf.transaction.NotificationFilter diagramResourceModifiedFilter = org.eclipse.emf.transaction.NotificationFilter.createNotifierFilter(editingDomain.getResourceSet()).and(org.eclipse.emf.transaction.NotificationFilter.createEventTypeFilter(org.eclipse.emf.common.notify.Notification.ADD)).and(org.eclipse.emf.transaction.NotificationFilter.createFeatureFilter(org.eclipse.emf.ecore.resource.ResourceSet.class, org.eclipse.emf.ecore.resource.ResourceSet.RESOURCE_SET__RESOURCES));
			editingDomain.getResourceSet().eAdapters().add(new org.eclipse.emf.common.notify.Adapter() {

				private org.eclipse.emf.common.notify.Notifier myTarger;

				«overrideI»
				public org.eclipse.emf.common.notify.Notifier getTarget() {
					return myTarger;
				}

				«overrideI»
				public boolean isAdapterForType(Object type) {
					return false;
				}

				«overrideI»
				public void notifyChanged(org.eclipse.emf.common.notify.Notification notification) {
					if (diagramResourceModifiedFilter.matches(notification)) {
						Object value = notification.getNewValue();
						if (value instanceof org.eclipse.emf.ecore.resource.Resource) {
							((org.eclipse.emf.ecore.resource.Resource) value).setTrackingModification(true);
						}
					}
				}

				«overrideI»
				public void setTarget(org.eclipse.emf.common.notify.Notifier newTarget) {
					myTarger = newTarget;
				}

			});	
			return editingDomain;
		}
	'''

	def setDocumentContent(GenDiagram it) '''
		«generatedMemberComment»
		protected void setDocumentContent(org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument document, org.eclipse.ui.IEditorInput element) throws org.eclipse.core.runtime.CoreException {
			org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument diagramDocument = (org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument) document;
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = diagramDocument.getEditingDomain();
			«IF null === it.editorGen.application »if (element instanceof «fileEditorInputClassFQName(it)») {
				org.eclipse.core.resources.IStorage storage = ((«fileEditorInputClassFQName(it)») element).getStorage();
				org.eclipse.gmf.runtime.notation.Diagram diagram = org.eclipse.gmf.runtime.diagram.ui.resources.editor.internal.util.DiagramIOUtil.load(domain, storage, true, getProgressMonitor());
				document.setContent(diagram);
			} else «ENDIF»if(element instanceof «uriEditorInputClassFQName(it)») {
				org.eclipse.emf.common.util.URI uri = ((«uriEditorInputClassFQName(it)») element).getURI();
				org.eclipse.emf.ecore.resource.Resource resource = null;
				try {
					resource = domain.getResourceSet().getResource(uri.trimFragment(), false);
					if (resource == null) {
						resource = domain.getResourceSet().createResource(uri.trimFragment());
					}
					if (!resource.isLoaded()) {
						try {
							@SuppressWarnings({ "rawtypes", "unchecked" })
							java.util.Map<?,?> options = new java.util.HashMap(org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory.getDefaultLoadOptions());
							// @see 171060
							// options.put(org.eclipse.emf.ecore.xmi.XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
							resource.load(options);
						} catch (java.io.IOException e) {
							resource.unload();
							throw e;
						}
					}
					if (uri.fragment() != null) {
						org.eclipse.emf.ecore.EObject rootElement = resource.getEObject(uri.fragment());
						if (rootElement instanceof org.eclipse.gmf.runtime.notation.Diagram) {
							document.setContent(rootElement);
							return;
						}
					} else {
						for (java.util.Iterator<org.eclipse.emf.ecore.EObject> it = resource.getContents().iterator(); it.hasNext();) {
							Object rootElement = it.next();
							if (rootElement instanceof org.eclipse.gmf.runtime.notation.Diagram) {
								document.setContent(rootElement);
								return;
							}
						}
					}
					throw new RuntimeException(«xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentProviderNoDiagramInResourceError(it))»);
				} catch (Exception e) {
					org.eclipse.core.runtime.CoreException thrownExcp = null;
					if (e instanceof org.eclipse.core.runtime.CoreException) {
						thrownExcp = (org.eclipse.core.runtime.CoreException) e;
					} else {
						String msg = e.getLocalizedMessage();
						thrownExcp = new org.eclipse.core.runtime.CoreException(new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, «xptActivator.qualifiedClassName(it.editorGen.plugin)».ID, 0, msg != null ? msg : «xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentProviderDiagramLoadingError(it))», e));
					}
					throw thrownExcp;
				}
			} else {
			«throwIncorrectInputException(it)»
			}	
		}
	'''

	def getModificationStamp(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		public long getModificationStamp(Object element) {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				return computeModificationStamp(info);
			}
			return super.getModificationStamp(element);
		}
	'''

	def isDeleted(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		public boolean isDeleted(Object element) {
			org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument document = getDiagramDocument(element);
			if (document != null) {
				org.eclipse.emf.ecore.resource.Resource diagramResource = document.getDiagram().eResource();
				if (diagramResource != null) {
			«IF null === editorGen.application »
					org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(diagramResource);
					return file == null || file.getLocation() == null || !file.getLocation().toFile().exists();
			«ELSE»
					java.io.File file = getFile(diagramResource);
					return file != null && !file.exists();
			«ENDIF»
				}
			}
			return super.isDeleted(element);
		}
	'''

	def getResourceSetInfo(GenDiagram it) '''
		«generatedMemberComment»
		public ResourceSetInfo getResourceSetInfo(Object editorInput) {
			return (ResourceSetInfo) super.getElementInfo(editorInput);
		}
	'''

	def disposeElementInfo(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected void disposeElementInfo(Object element, ElementInfo info) {
			if (info instanceof ResourceSetInfo) {
				ResourceSetInfo resourceSetInfo = (ResourceSetInfo) info;
				resourceSetInfo.dispose();
			}
			super.disposeElementInfo(element, info);
		}
	'''

	def doValidateState(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected void doValidateState(Object element, Object computationContext) throws org.eclipse.core.runtime.CoreException {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				java.util.LinkedList<org.eclipse.core.resources.IFile> files2Validate = new java.util.LinkedList<«diamondOp('org.eclipse.core.resources.IFile')»>();
				for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
					org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(nextResource);
					if (file != null && file.isReadOnly()) {
						files2Validate.add(file);
					}
				}
				org.eclipse.core.resources.ResourcesPlugin.getWorkspace().validateEdit(files2Validate.toArray(new org.eclipse.core.resources.IFile[files2Validate.size()]), computationContext);
			}

			super.doValidateState(element, computationContext);
		}
	'''

	def isReadOnly(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		public boolean isReadOnly(Object element) {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				«callUpdateCache(it)»
				return info.isReadOnly();
			}
			return super.isReadOnly(element);
		}
	'''

	def isModifiable(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		public boolean isModifiable(Object element) {
			if (!isStateValidated(element)) {
				if («IF null === editorGen.application »element instanceof «fileEditorInputClassFQName(it)» || «ENDIF»element instanceof «uriEditorInputClassFQName(it)») {
					return true;
				}
			}
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				«callUpdateCache(it)»
				return info.isModifiable();
			}
			return super.isModifiable(element);
		}
	'''

	def callUpdateCache(GenDiagram it) '''
		if (info.isUpdateCache()) {
			try {
				updateCache(element);
			} catch (org.eclipse.core.runtime.CoreException ex) {
				«xptActivator.qualifiedClassName(it.editorGen.plugin)».getInstance().logError(«xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentProviderIsModifiable(it))», ex);
				// Error message to log was initially taken from org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.internal.l10n.EditorMessages.StorageDocumentProvider_isModifiable
			}
		}
	'''

	def updateCache(GenDiagram it) '''
		«generatedMemberComment»
		protected void updateCache(Object element) throws org.eclipse.core.runtime.CoreException {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
			«IF null === editorGen.application »
					org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(nextResource);
					if (file != null && file.isReadOnly()) {
			«ELSE»
					java.io.File file = getFile(nextResource);
					if (file != null && file.exists() && !file.canWrite()) {
			«ENDIF»
						info.setReadOnly(true);
						info.setModifiable(false);
						return;
					}
				}
				info.setReadOnly(false);
				info.setModifiable(true);
				return;
			}
		}
	'''

	def doUpdateStateCache(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected void doUpdateStateCache(Object element) throws org.eclipse.core.runtime.CoreException {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				info.setUpdateCache(true);
			}
			super.doUpdateStateCache(element);
		}
	'''

	def isSynchronized(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		public boolean isSynchronized(Object element) {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				return info.isSynchronized();
			}
			return super.isSynchronized(element);
		}
	'''

	def getResetRule(GenDiagram it)'''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.core.runtime.jobs.ISchedulingRule getResetRule(Object element) {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				java.util.LinkedList<org.eclipse.core.runtime.jobs.ISchedulingRule> rules = new java.util.LinkedList<«diamondOp('org.eclipse.core.runtime.jobs.ISchedulingRule')»>();
				for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
					org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(nextResource);
					if (file != null) {
						rules.add(org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(file));
					}
				}
				return new org.eclipse.core.runtime.jobs.MultiRule(rules.toArray(new org.eclipse.core.runtime.jobs.ISchedulingRule[rules.size()]));
			}
			return null;
		}
	'''

	def getSaveRule(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.core.runtime.jobs.ISchedulingRule getSaveRule(Object element) {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				java.util.LinkedList<org.eclipse.core.runtime.jobs.ISchedulingRule> rules = new java.util.LinkedList<«diamondOp('org.eclipse.core.runtime.jobs.ISchedulingRule')»>();
				for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
					org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(nextResource);
					if (file != null) {
						rules.add(computeSchedulingRule(file));
					}
				}
				return new org.eclipse.core.runtime.jobs.MultiRule(rules.toArray(new org.eclipse.core.runtime.jobs.ISchedulingRule[rules.size()]));
			}
			return null;
		}
	'''

	def getSynchronizeRule(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.core.runtime.jobs.ISchedulingRule getSynchronizeRule(Object element) {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				java.util.LinkedList<org.eclipse.core.runtime.jobs.ISchedulingRule> rules = new java.util.LinkedList<«diamondOp('org.eclipse.core.runtime.jobs.ISchedulingRule')»>();
				for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
					org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(nextResource);
					if (file != null) {
						rules.add(org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRuleFactory().refreshRule(file));
					}
				}
				return new org.eclipse.core.runtime.jobs.MultiRule(rules.toArray(new org.eclipse.core.runtime.jobs.ISchedulingRule[rules.size()]));
			}
			return null;
		}
	'''

	def getValidateStateRule(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.core.runtime.jobs.ISchedulingRule getValidateStateRule(Object element) {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {«/*FIXME: [MG] bad copy paste here, files should be <IFile>, its a miracle that it does not fail in runtime at toArray stage */»
				java.util.LinkedList<org.eclipse.core.runtime.jobs.ISchedulingRule> files = new java.util.LinkedList<«diamondOp('org.eclipse.core.runtime.jobs.ISchedulingRule')»>();
				for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
					org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(nextResource);
					if (file != null) {
						files.add(file);
					}
				}
				return org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRuleFactory().validateEditRule(files.toArray(new org.eclipse.core.resources.IFile[files.size()]));
			}
			return null;
		}
	'''

	def computeSchedulingRule(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.core.runtime.jobs.ISchedulingRule computeSchedulingRule(org.eclipse.core.resources.IResource toCreateOrModify) {
			if (toCreateOrModify.exists()) {
				return org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(toCreateOrModify);
			}
			org.eclipse.core.resources.IResource parent = toCreateOrModify;
			do {«/*FIXME [MG] the bug is closed long ago, still need? */»
				/*
				 * XXX This is a workaround for
				 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=67601
				 * IResourceRuleFactory.createRule should iterate the hierarchy
				 * itself.
				 */
				toCreateOrModify = parent;
				parent = toCreateOrModify.getParent();
			} while (parent != null && !parent.exists());

			return org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRuleFactory().createRule(toCreateOrModify);
		}
	'''

	def doSynchronize(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected void doSynchronize(Object element, org.eclipse.core.runtime.IProgressMonitor monitor) throws org.eclipse.core.runtime.CoreException {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
					org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
					handleElementChanged(info, nextResource, monitor);	
				}
				return;
			}
			super.doSynchronize(element, monitor);
		}
	'''

	def handleElementChanged(GenDiagram it) '''
		«generatedMemberComment»
		protected void handleElementChanged(ResourceSetInfo info, org.eclipse.emf.ecore.resource.Resource changedResource, org.eclipse.core.runtime.IProgressMonitor monitor) {
			«IF null === editorGen.application »
			org.eclipse.core.resources.IFile file = org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(changedResource);
			if (file != null) {
				try {
					file.refreshLocal(org.eclipse.core.resources.IResource.DEPTH_INFINITE, monitor);
				} catch (org.eclipse.core.runtime.CoreException ex) {
					«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError(«xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentProviderHandleElementContentChanged(it))», ex);
					// Error message to log was initially taken from org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.internal.l10n.EditorMessages.FileDocumentProvider_handleElementContentChanged
				}
			}
			«ENDIF»
			changedResource.unload();

			fireElementContentAboutToBeReplaced(info.getEditorInput());
			removeUnchangedElementListeners(info.getEditorInput(), info);
			info.fStatus = null;
			try {
				setDocumentContent(info.fDocument, info.getEditorInput());
			} catch (org.eclipse.core.runtime.CoreException e) {
				info.fStatus = e.getStatus();
			}
			«/* TODO: Remove this if and call setModificationStamp only from doSaveDocument method 
			*/»if (!info.fCanBeSaved) {
				info.setModificationStamp(computeModificationStamp(info));
			}
			addUnchangedElementListeners(info.getEditorInput(), info);
			fireElementContentReplaced(info.getEditorInput());
		}
	'''

	def doSaveDocument(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected void doSaveDocument(org.eclipse.core.runtime.IProgressMonitor monitor, Object element, org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument document, boolean overwrite) throws org.eclipse.core.runtime.CoreException {
			ResourceSetInfo info = getResourceSetInfo(element);
			if (info != null) {
				if (!overwrite && !info.isSynchronized()) {
					throw new org.eclipse.core.runtime.CoreException(new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, «xptActivator.qualifiedClassName(editorGen.plugin)».ID, «IF null === editorGen.application »org.eclipse.core.resources.IResourceStatus.OUT_OF_SYNC_LOCAL«ELSE»org.eclipse.core.runtime.IStatus.ERROR«ENDIF», «xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentUnsynchronizedFileSaveError(it))», null));
				}
			«IF null === editorGen.application »
				info.stopResourceListening();
			«ENDIF»
				fireElementStateChanging(element);
				try {
					monitor.beginTask(«xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentSaveDiagramTask(it))», info.getResourceSet().getResources().size() + 1); // "Saving diagram"
					for (java.util.Iterator<org.eclipse.emf.ecore.resource.Resource> it = info.getLoadedResourcesIterator(); it.hasNext();) {
						org.eclipse.emf.ecore.resource.Resource nextResource = it.next();
						monitor.setTaskName(org.eclipse.osgi.util.NLS.bind(«xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentSaveNextResourceTask(it))», nextResource.getURI()));
						if (nextResource.isLoaded() && !info.getEditingDomain().isReadOnly(nextResource)) {
							try {
								nextResource.save(«xptDiagramEditorUtil.callGetSaveOptions(it)»);
							} catch (java.io.IOException e) {
								fireElementStateChangeFailed(element);
								throw new org.eclipse.core.runtime.CoreException(new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, «xptActivator.qualifiedClassName(editorGen.plugin)».ID, org.eclipse.gmf.runtime.diagram.ui.resources.editor.internal.EditorStatusCodes.RESOURCE_FAILURE, e.getLocalizedMessage(), null));
							}
						}
						monitor.worked(1);
					}
					monitor.done();
					info.setModificationStamp(computeModificationStamp(info));
				} catch (RuntimeException x) {
					fireElementStateChangeFailed(element);
					throw x;
				} «IF null === editorGen.application » finally {
					info.startResourceListening();
				} «ENDIF»
			} else {
				org.eclipse.emf.common.util.URI newResoruceURI;
				java.util.List<org.eclipse.core.resources.IFile> affectedFiles = null;
				«IF null === editorGen.application »if (element instanceof «fileEditorInputClassFQName(it)») {
					org.eclipse.core.resources.IFile newFile = ((«fileEditorInputClassFQName(it)») element).getFile();
					affectedFiles = java.util.Collections.singletonList(newFile);
					newResoruceURI = org.eclipse.emf.common.util.URI.createPlatformResourceURI(newFile.getFullPath().toString(), true);
				} else «ENDIF»if(element instanceof «uriEditorInputClassFQName(it)») {
					newResoruceURI = ((«uriEditorInputClassFQName(it)») element).getURI();
				} else {
					fireElementStateChangeFailed(element);
					«throwIncorrectInputException(it)»
				}
				if (false == document instanceof org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument) {
					fireElementStateChangeFailed(element);
					throw new org.eclipse.core.runtime.CoreException(new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, «xptActivator.qualifiedClassName(editorGen.plugin)».ID, 0, "Incorrect document used: " + document + " instead of org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument", null)); «nonNLS(1)» «nonNLS(2)»
				}
				org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument diagramDocument = (org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument) document;
				final org.eclipse.emf.ecore.resource.Resource newResource = diagramDocument.getEditingDomain().getResourceSet().createResource(newResoruceURI);
				final org.eclipse.gmf.runtime.notation.Diagram diagramCopy = org.eclipse.emf.ecore.util.EcoreUtil.copy(diagramDocument.getDiagram());
				try {
					new org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand(diagramDocument.getEditingDomain(), org.eclipse.osgi.util.NLS.bind(«xptExternalizer.accessorCall(editorGen, i18nKeyForDocumentSaveAs(it))», diagramCopy.getName()), affectedFiles) {
						«overrideC»
						protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
							newResource.getContents().add(diagramCopy);					
							return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult();
						}
					}.execute(monitor, null);
					newResource.save(«xptDiagramEditorUtil.callGetSaveOptions(it)»);
				} catch (org.eclipse.core.commands.ExecutionException e) {
					fireElementStateChangeFailed(element);
					throw new org.eclipse.core.runtime.CoreException(new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, «xptActivator.qualifiedClassName(editorGen.plugin)».ID, 0, e.getLocalizedMessage(), null));
				} catch (java.io.IOException e) {
					fireElementStateChangeFailed(element);
					throw new org.eclipse.core.runtime.CoreException(new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.IStatus.ERROR, «xptActivator.qualifiedClassName(editorGen.plugin)».ID, 0, e.getLocalizedMessage(), null));
				}
				newResource.unload();
			}
		}
	'''	

	def handleElementMoved(GenDiagram it) '''
		«generatedMemberComment»
		protected void handleElementMoved(org.eclipse.ui.IEditorInput input, org.eclipse.emf.common.util.URI uri) {
			«IF null === editorGen.application »if (input instanceof «fileEditorInputClassFQName(it)») {
				org.eclipse.core.resources.IFile newFile = org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().getFile(new org.eclipse.core.runtime.Path(org.eclipse.emf.common.util.URI.decode(uri.path())).removeFirstSegments(1));
				fireElementMoved(input, newFile == null ? null : new org.eclipse.ui.part.FileEditorInput(newFile));
				return;
			}
			«ELSE»
			«ENDIF»
			// TODO: append suffix to the URI! (use diagram as a parameter)
			fireElementMoved(input, new org.eclipse.emf.common.ui.URIEditorInput(uri));
		}
	'''

	def createInputWithEditingDomain(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public org.eclipse.ui.IEditorInput createInputWithEditingDomain(org.eclipse.ui.IEditorInput editorInput, org.eclipse.emf.transaction.TransactionalEditingDomain domain) {
			return editorInput;
		}
	'''

	def getDiagramDocument(GenDiagram it) '''
		«generatedMemberComment»
		«overrideI»
		public org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument getDiagramDocument(Object element) {
			org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument doc = getDocument(element);
			if (doc instanceof org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument) {
				return (org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument) doc;
			}
			return null;
		}
	'''

	def getOperationRunner(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.jface.operation.IRunnableContext getOperationRunner(org.eclipse.core.runtime.IProgressMonitor monitor) {
			return null;
		}
	'''

	def getFile(GenDiagram it) '''
		«generatedMemberComment»
		private static java.io.File getFile(org.eclipse.emf.ecore.resource.Resource resource) {
			org.eclipse.emf.common.util.URI resourceUri = resource.getURI();
			if (resourceUri != null && resourceUri.isFile()) {
				java.io.File file = new java.io.File(resourceUri.toFileString());
				if (!file.isDirectory()) {
					return file;
				}
			}
			return null;
		}
	'''

	@Localization def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(i18nKeyForDocumentProviderIsModifiable(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentProviderHandleElementContentChanged(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentProviderIncorrectInputError(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentProviderNoDiagramInResourceError(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentProviderDiagramLoadingError(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentUnsynchronizedFileSaveError(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentSaveDiagramTask(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentSaveNextResourceTask(it))»
		«xptExternalizer.accessorField(i18nKeyForDocumentSaveAs(it))»
	'''

	@Localization def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(i18nKeyForDocumentProviderIsModifiable(it), 'Updating cache failed')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentProviderHandleElementContentChanged(it), 'Failed to refresh hierarchy for changed resource')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentProviderIncorrectInputError(it), 'Incorrect element used: {0} instead of ' + if ( editorGen.application === null ) '{1} or {2}' else '{1}')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentProviderNoDiagramInResourceError(it), 'Diagram is not present in resource')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentProviderDiagramLoadingError(it), 'Error loading diagram')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentUnsynchronizedFileSaveError(it), 'The file has been changed on the file system')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentSaveDiagramTask(it), 'Saving diagram')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentSaveNextResourceTask(it), 'Saving {0}')»
		«xptExternalizer.messageEntry(i18nKeyForDocumentSaveAs(it), 'Saving {0} diagram as')»
	'''	

	@Localization def String i18nKeyForDocumentProvider(GenDiagram diagram) {
		return '' + className(diagram)
	}

	@Localization def String i18nKeyForDocumentProviderIsModifiable(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '_isModifiable'
	}

	@Localization def String i18nKeyForDocumentProviderHandleElementContentChanged(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '_handleElementContentChanged'
	}

	@Localization def String i18nKeyForDocumentProviderIncorrectInputError(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '.IncorrectInputError'
	}

	@Localization def String i18nKeyForDocumentProviderNoDiagramInResourceError(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '.NoDiagramInResourceError'
	}

	@Localization def String i18nKeyForDocumentProviderDiagramLoadingError(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '.DiagramLoadingError'
	}

	@Localization def String i18nKeyForDocumentUnsynchronizedFileSaveError(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '.UnsynchronizedFileSaveError'
	}

	@Localization def String i18nKeyForDocumentSaveDiagramTask(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '.SaveDiagramTask'
	}

	@Localization def String i18nKeyForDocumentSaveNextResourceTask(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '.SaveNextResourceTask'
	}

	@Localization def String i18nKeyForDocumentSaveAs(GenDiagram diagram) {
		return i18nKeyForDocumentProvider(diagram) + '.SaveAsOperation'
	}	
}