/*****************************************************************************
 * Copyright (c) 2007, 2017, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - #510281 change dependency to replace gmft-runtime
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import com.google.inject.Singleton
import metamodel.MetaModel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Externalizer
import xpt.ExternalizerUtils_qvto

@Singleton class DiagramEditorUtil {
	@Inject extension Common;
	@Inject extension CodeStyle;
	@Inject extension GenDiagram_qvto;
	@Inject extension ExternalizerUtils_qvto;

	@Inject Externalizer xptExternalizer;
	@Inject MetaModel xptMetaModel;
	@Inject Activator xptActivator;
	@Inject Editor xptEditor;

	@MetaDef def callSetCharset(GenDiagram it, String varName) '''
		«it.diagramEditorUtilQualifiedClassName».setCharset(«varName»);
	'''

	@MetaDef def callGetSaveOptions(GenDiagram it) '''«qualifiedClassName(it)».getSaveOptions()'''

	@Localization def String i18nKeyForDiagramEditorUtil(GenDiagram diagram) {
		return '' + className(diagram)
	}

	@Localization def String i18nKeyForOpenModelResourceErrorDialog(GenDiagram diagram) {
		return i18nKeyForDiagramEditorUtil(diagram) + '.OpenModelResourceErrorDialog'
	}

	@Localization def String i18nKeyForCreateDiagramProgressTask(GenDiagram diagram) {
		return i18nKeyForDiagramEditorUtil(diagram) + '.CreateDiagramProgressTask'
	}

	@Localization def String i18nKeyForCreateDiagramCommandLabel(GenDiagram diagram) {
		return i18nKeyForDiagramEditorUtil(diagram) + '.CreateDiagramCommandLabel'
	}

	def className(GenDiagram it) '''«diagramEditorUtilClassName»'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def DiagramEditorUtil(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		@SuppressWarnings("deprecation")
		public class «className(it)» {

			«saveOptions(it)»
			«openDiagramMethod(it)»
			«IF editorGen.application === null »
				«setCharsetMethods(it)»
			«ENDIF»
			«getUniqueFileNameMethod(it)»
			«IF editorGen.application !== null »
				«generatedMemberComment('Allows user to select file and loads it as a model.')»
				public static org.eclipse.emf.ecore.resource.Resource openModel(org.eclipse.swt.widgets.Shell shell, String description, org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain) {
				org.eclipse.swt.widgets.FileDialog fileDialog = new org.eclipse.swt.widgets.FileDialog(shell, org.eclipse.swt.SWT.OPEN);
				if (description != null) {
					fileDialog.setText(description);
				}
				fileDialog.open();
				String fileName = fileDialog.getFileName();
				if (fileName == null || fileName.length() == 0) {
					return null;
				}
				if (fileDialog.getFilterPath() != null) {
					fileName = fileDialog.getFilterPath() + java.io.File.separator + fileName;
				}
				org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createFileURI(fileName);
				org.eclipse.emf.ecore.resource.Resource resource = null;
				try {
					resource = editingDomain.getResourceSet().getResource(uri, true);
				} catch (org.eclipse.emf.common.util.WrappedException we) {
					«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Unable to load resource: " + uri, we);  «nonNLS(1)»
					org.eclipse.jface.dialogs.MessageDialog.openError(shell, «xptExternalizer.accessorCall(editorGen, titleKey(i18nKeyForOpenModelResourceErrorDialog(it)))», org.eclipse.osgi.util.NLS.bind(«xptExternalizer.accessorCall(editorGen, messageKey(i18nKeyForOpenModelResourceErrorDialog(it)))», fileName));
				}
				return resource;
				}
			«ENDIF»
			«generatedMemberComment('Runs the wizard in a dialog.')»
			public static void runWizard(org.eclipse.swt.widgets.Shell shell, org.eclipse.jface.wizard.Wizard wizard, String settingsKey) {
				org.eclipse.jface.dialogs.IDialogSettings pluginDialogSettings = «xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().getDialogSettings();
				org.eclipse.jface.dialogs.IDialogSettings wizardDialogSettings = pluginDialogSettings.getSection(settingsKey);
				if (wizardDialogSettings == null) {
					wizardDialogSettings = pluginDialogSettings.addNewSection(settingsKey);
				}
				wizard.setDialogSettings(wizardDialogSettings);
				org.eclipse.jface.wizard.WizardDialog dialog = new org.eclipse.jface.wizard.WizardDialog(shell, wizard);
				dialog.create();
				dialog.getShell().setSize(Math.max(500, dialog.getShell().getSize().x), 500);
				dialog.open();
			}
			«createDiagramMethod(it)»
			«IF domainDiagramElement !== null »
				«createInitialModelMethod(it)»
				«attachModelMethod(it)»
				«IF hasDocumentRoot(it)»«createDocumentRootMethod(it)»«ENDIF»
			«ENDIF»
			«selectElementsMethod(it)»
			«findElementsMethod(it)»
			«findViewMethod(it)»
		}
	'''

	def saveOptions(GenDiagram it) '''
		«generatedMemberComment»
		public static java.util.Map<?, ?> getSaveOptions() {
			java.util.HashMap<String, Object> saveOptions = new java.util.HashMap<«diamondOp('String', 'Object')»>(); 
			saveOptions.put(org.eclipse.emf.ecore.xmi.XMLResource.OPTION_ENCODING, "UTF-8");  «nonNLS(1)»
			saveOptions.put(org.eclipse.emf.ecore.resource.Resource.OPTION_SAVE_ONLY_IF_CHANGED, org.eclipse.emf.ecore.resource.Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
			return saveOptions;
		}
	'''

	def openDiagramMethod(GenDiagram it) '''
		«generatedMemberComment»
		public static boolean openDiagram(org.eclipse.emf.ecore.resource.Resource diagram) throws org.eclipse.ui.PartInitException {
		«IF editorGen.application === null »
			String path = diagram.getURI().toPlatformString(true);
			org.eclipse.core.resources.IResource workspaceResource = org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().findMember(new org.eclipse.core.runtime.Path(path));
			if (workspaceResource instanceof org.eclipse.core.resources.IFile) {
				org.eclipse.ui.IWorkbenchPage page = org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				return null != page.openEditor(new org.eclipse.ui.part.FileEditorInput((org.eclipse.core.resources.IFile) workspaceResource), «xptEditor.qualifiedClassName(editorGen.editor)».ID);
			}
			return false;
		«ELSE»
			org.eclipse.ui.IWorkbenchPage page = org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			page.openEditor(new org.eclipse.emf.common.ui.URIEditorInput(diagram.getURI()), «xptEditor.qualifiedClassName(editorGen.editor)».ID);
			return true;
		«ENDIF»
		}
	'''

	def setCharsetMethods(GenDiagram it) '''
		«generatedMemberComment»
		public static void setCharset(org.eclipse.core.resources.IFile file) {
			if (file == null) {
				return;
			}
			try {
				file.setCharset("UTF-8", new org.eclipse.core.runtime.NullProgressMonitor());  «nonNLS(1)»
			} catch (org.eclipse.core.runtime.CoreException e) {
				«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Unable to set charset for file " + file.getFullPath(), e);  «nonNLS(1)»
			}
		}	
	'''

	def getUniqueFileNameMethod(GenDiagram it) '''
		«generatedMemberComment»
		public static String getUniqueFileName(org.eclipse.core.runtime.IPath containerFullPath, String fileName, String extension) {
			return org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part.DefaultDiagramEditorUtil.getUniqueFileName(containerFullPath, fileName, extension, org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part.DefaultDiagramEditorUtil.«IF editorGen.application === null »EXISTS_IN_WORKSPACE«ELSE»EXISTS_AS_IO_FILE«ENDIF»);
		}
	'''

	def createDiagramMethod(GenDiagram it) '''
		«generatedMemberComment((if( editorGen.application === null ) 'This method should be called within a workspace modify operation since it creates resources.' else ''))»
		public static org.eclipse.emf.ecore.resource.Resource createDiagram(org.eclipse.emf.common.util.URI diagramURI,«IF standaloneDomainModel(it)» org.eclipse.emf.common.util.URI modelURI,«ENDIF» org.eclipse.core.runtime.IProgressMonitor progressMonitor) {
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = org.eclipse.emf.workspace.WorkspaceEditingDomainFactory.INSTANCE.createEditingDomain();
			progressMonitor.beginTask(«xptExternalizer.accessorCall(editorGen, i18nKeyForCreateDiagramProgressTask(it))», 3);
			final org.eclipse.emf.ecore.resource.Resource diagramResource = editingDomain.getResourceSet().createResource(diagramURI);
			«IF standaloneDomainModel(it)»
				final org.eclipse.emf.ecore.resource.Resource modelResource = editingDomain.getResourceSet().createResource(modelURI);
			«ELSEIF domainDiagramElement !== null && hasDocumentRoot(it)/*for standalone models, we assume its resourcefactory would be able to set extendedMetaData option*/»
				((org.eclipse.emf.ecore.xmi.XMLResource) diagramResource).getDefaultSaveOptions().put(org.eclipse.emf.ecore.xmi.XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
				((org.eclipse.emf.ecore.xmi.XMLResource) diagramResource).getDefaultLoadOptions().put(org.eclipse.emf.ecore.xmi.XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
			«ENDIF»
			final String diagramName = diagramURI.lastSegment();
			org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand command = new org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand(editingDomain, «xptExternalizer.accessorCall(editorGen, i18nKeyForCreateDiagramCommandLabel(it))», java.util.Collections.EMPTY_LIST) {
				«overrideC»
				protected org.eclipse.gmf.runtime.common.core.command.CommandResult doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor monitor, org.eclipse.core.runtime.IAdaptable info) throws org.eclipse.core.commands.ExecutionException {
					«IF domainDiagramElement !== null »
						«xptMetaModel.QualifiedClassName(domainDiagramElement)» model = createInitialModel();
						attachModelToResource(model, «IF standaloneDomainModel(it)»model«ELSE»diagram«ENDIF»Resource);
					«ENDIF»
					org.eclipse.gmf.runtime.notation.Diagram diagram = org.eclipse.gmf.runtime.diagram.core.services.ViewService.createDiagram(«IF domainDiagramElement !== null »«xptMetaModel.DowncastToEObject(domainDiagramElement, 'model')», «ENDIF»«VisualIDRegistry::modelID(it)», «xptActivator.preferenceHintAccess(editorGen)»);
					if (diagram != null) {
						diagramResource.getContents().add(diagram);
						diagram.setName(diagramName);
						«IF domainDiagramElement !== null »
							diagram.setElement(«xptMetaModel.DowncastToEObject(domainDiagramElement, 'model')»);
						«ENDIF»
					}
					try {
						«IF standaloneDomainModel(it)»modelResource.save(«callGetSaveOptions(it)»);«ENDIF»
						diagramResource.save(«callGetSaveOptions(it)»);
					} catch (java.io.IOException e) {
						«/*
						 * TODO CommandResult.newErrorCommandResult(e) would be better? Or even throw ExecutionEx?
						 * */
						 xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Unable to store model and diagram resources", e);  «nonNLS(1)»
					}
					return org.eclipse.gmf.runtime.common.core.command.CommandResult.newOKCommandResult();
				}
			};
			try {
				org.eclipse.core.commands.operations.OperationHistoryFactory.getOperationHistory().execute(command, new org.eclipse.core.runtime.SubProgressMonitor(progressMonitor, 1), null);
			} catch (org.eclipse.core.commands.ExecutionException e) {
				«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Unable to create model and diagram", e);  «nonNLS(1)»
			}
			«IF editorGen.application === null »
				«IF standaloneDomainModel(it)»setCharset(org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(modelResource));«ENDIF»
				setCharset(org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(diagramResource));
			«ENDIF»
			return diagramResource;
		}
	'''

	def createInitialModelMethod(GenDiagram it) '''
		«generatedMemberComment('Create a new instance of domain element associated with canvas.\n<!-- begin-user-doc -->\n<!-- end-user-doc -->')»
		private static «xptMetaModel.QualifiedClassName(domainDiagramElement)» createInitialModel() {
			return «xptMetaModel.NewInstance(domainDiagramElement)»;
		}
	'''

	def attachModelMethod(GenDiagram it) '''
		«generatedMemberComment('Store model element in the resource.\n<!-- begin-user-doc -->\n<!-- end-user-doc -->')»
		private static void attachModelToResource(«xptMetaModel.QualifiedClassName(domainDiagramElement)» model, org.eclipse.emf.ecore.resource.Resource resource) {
			resource.getContents().add(«IF hasDocumentRoot(it)»createDocumentRoot(model)«ELSE»«xptMetaModel.
			DowncastToEObject(domainDiagramElement, 'model')»«ENDIF»);
		}
	'''

	// invoke only when there's DocumentRoot in the domain model
	def createDocumentRootMethod(GenDiagram it) '''
		«generatedMemberComment»
		private static «xptMetaModel.QualifiedClassName(getDocumentRoot(it))» createDocumentRoot(«xptMetaModel.
			QualifiedClassName(domainDiagramElement)» model) {
			«IF domainDiagramElement == getDocumentRoot(it)»
				return model;
			«ELSE»
				«xptMetaModel.NewInstance(getDocumentRoot(it), 'docRoot')»
				«var rootFeature = getDocumentRootSetFeature(it)»
				«IF rootFeature === null »
					docRoot.set«domainDiagramElement.ecoreClass.name»(model); // FIXME name of the set method is pure guess
				«ELSE»
					«xptMetaModel.modifyFeature(rootFeature, 'docRoot', getDocumentRoot(it), 'model')»
				«ENDIF»
				return docRoot;
			«ENDIF»
		}
	'''

	/**
 * FIXME only metrics and audits use selectElementsInDiagram and findView (and hence LazyElement2ViewMap and findElementsInDiagramByID)
 * methods, thus it's reasonable to generate these methods iff Metrics or Audits are enabled.
 */
	def selectElementsMethod(GenDiagram it) '''
		«generatedMemberComment»
		public static void selectElementsInDiagram(org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart diagramPart, java.util.List<org.eclipse.gef.EditPart> editParts) {
			diagramPart.getDiagramGraphicalViewer().deselectAll();
			org.eclipse.gef.EditPart firstPrimary = null;
			for (org.eclipse.gef.EditPart nextPart : editParts) {
				diagramPart.getDiagramGraphicalViewer().appendSelection(nextPart);
				if(firstPrimary == null && nextPart instanceof org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart) {
					firstPrimary = nextPart;
				}
			}
			if(!editParts.isEmpty()) {
				diagramPart.getDiagramGraphicalViewer().reveal(firstPrimary != null ? firstPrimary : (org.eclipse.gef.EditPart)editParts.get(0));
			}
		}
	'''

	def findElementsMethod(GenDiagram it) '''
		«generatedMemberComment»
		private static int findElementsInDiagramByID(org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramPart, org.eclipse.emf.ecore.EObject element, java.util.List<org.eclipse.gef.EditPart> editPartCollector) {
			org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer viewer = (org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer) diagramPart.getViewer();
			final int intialNumOfEditParts = editPartCollector.size();
			if (element instanceof org.eclipse.gmf.runtime.notation.View) { // support notation element lookup
				org.eclipse.gef.EditPart editPart = (org.eclipse.gef.EditPart) viewer.getEditPartRegistry().get(element);
				if (editPart != null) {
					editPartCollector.add(editPart);
					return 1;
				}
			}
			String elementID = org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil.getProxyID(element);
			@SuppressWarnings("unchecked")
			java.util.List<org.eclipse.gef.EditPart> associatedParts = viewer.findEditPartsForElement(elementID, org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.class);
			// perform the possible hierarchy disjoint -> take the top-most parts only
			for (org.eclipse.gef.EditPart nextPart : associatedParts) {
				org.eclipse.gef.EditPart parentPart = nextPart.getParent();
				while (parentPart != null && !associatedParts.contains(parentPart)) {
					parentPart = parentPart.getParent();
				}
				if (parentPart == null) {
					editPartCollector.add(nextPart);
				}
			}
			if (intialNumOfEditParts == editPartCollector.size()) {
				if (!associatedParts.isEmpty()) {
					editPartCollector.add(associatedParts.get(0));
				} else {
					if (element.eContainer() != null) {
						return findElementsInDiagramByID(diagramPart, element.eContainer(), editPartCollector);
					}
				}
			}
			return editPartCollector.size() - intialNumOfEditParts;
		}
	'''

	def findViewMethod(GenDiagram it) '''
		«generatedMemberComment»
		public static org.eclipse.gmf.runtime.notation.View findView(org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart, org.eclipse.emf.ecore.EObject targetElement, LazyElement2ViewMap lazyElement2ViewMap) {
			boolean hasStructuralURI = false;						
			if(targetElement.eResource() instanceof org.eclipse.emf.ecore.xmi.XMLResource) {
				hasStructuralURI = ((org.eclipse.emf.ecore.xmi.XMLResource)targetElement.eResource()).getID(targetElement) == null;
			}
			org.eclipse.gmf.runtime.notation.View view = null;
			java.util.LinkedList<org.eclipse.gef.EditPart> editPartHolder = new java.util.LinkedList<«diamondOp('org.eclipse.gef.EditPart')»>();
			if(hasStructuralURI && !lazyElement2ViewMap.getElement2ViewMap().isEmpty()) {
				view = lazyElement2ViewMap.getElement2ViewMap().get(targetElement);
			} else if (findElementsInDiagramByID(diagramEditPart, targetElement, editPartHolder) > 0) {
				org.eclipse.gef.EditPart editPart = editPartHolder.get(0);
				view = editPart.getModel() instanceof org.eclipse.gmf.runtime.notation.View ? (org.eclipse.gmf.runtime.notation.View) editPart.getModel() : null;
			}
			return (view == null) ? diagramEditPart.getDiagramView() : view;
		}

		«generatedMemberComment('XXX This is quite suspicious code (especially editPartTmpHolder) and likely to be removed soon')»
		public static class LazyElement2ViewMap {
			«generatedMemberComment»
			private java.util.Map<org.eclipse.emf.ecore.EObject, org.eclipse.gmf.runtime.notation.View> element2ViewMap;

			«generatedMemberComment»
			private org.eclipse.gmf.runtime.notation.View scope;

			«generatedMemberComment»
			private java.util.Set<? extends org.eclipse.emf.ecore.EObject> elementSet;

			«generatedMemberComment»
			public LazyElement2ViewMap(org.eclipse.gmf.runtime.notation.View scope, java.util.Set<? extends org.eclipse.emf.ecore.EObject> elements) {
				this.scope = scope;
				this.elementSet = elements;
			}

			«generatedMemberComment»
			public final java.util.Map<org.eclipse.emf.ecore.EObject, org.eclipse.gmf.runtime.notation.View> getElement2ViewMap() {
				if(element2ViewMap == null) {
					element2ViewMap = new java.util.HashMap<«diamondOp('org.eclipse.emf.ecore.EObject', 'org.eclipse.gmf.runtime.notation.View')»>();
					// map possible notation elements to itself as these can't be found by view.getElement()
					for (org.eclipse.emf.ecore.EObject element : elementSet) {
						if(element instanceof org.eclipse.gmf.runtime.notation.View) {
							org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) element;
							if(view.getDiagram() == scope.getDiagram()) {
								element2ViewMap.put(element, view); // take only those that part of our diagram
							}
						}
					}
					buildElement2ViewMap(scope, element2ViewMap, elementSet);					
				}
				return element2ViewMap;
			}

			«generatedMemberComment»
			private static boolean buildElement2ViewMap(org.eclipse.gmf.runtime.notation.View parentView, java.util.Map<org.eclipse.emf.ecore.EObject, org.eclipse.gmf.runtime.notation.View> element2ViewMap, java.util.Set<? extends org.eclipse.emf.ecore.EObject> elements) {
				if (elements.size() == element2ViewMap.size()) {
					return true;
				}
				if(parentView.isSetElement() && !element2ViewMap.containsKey(parentView.getElement()) && elements.contains(parentView.getElement())) {
					element2ViewMap.put(parentView.getElement(), parentView);
					if (elements.size() == element2ViewMap.size()) {
						return true;
					}
				}
				boolean complete = false;
				for (java.util.Iterator<?> it = parentView.getChildren().iterator(); it.hasNext() && !complete;) {
					complete = buildElement2ViewMap((org.eclipse.gmf.runtime.notation.View) it.next(), element2ViewMap, elements);			
				}
				for (java.util.Iterator<?> it = parentView.getSourceEdges().iterator(); it.hasNext() && !complete;) {
					complete = buildElement2ViewMap((org.eclipse.gmf.runtime.notation.View) it.next(), element2ViewMap, elements);			
				}
				for (java.util.Iterator<?> it = parentView.getTargetEdges().iterator(); it.hasNext() && !complete;) {
					complete = buildElement2ViewMap((org.eclipse.gmf.runtime.notation.View) it.next(), element2ViewMap, elements);			
				}	
				return complete;
			}
		}// LazyElement2ViewMap
	'''

	@Localization def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(titleKey(i18nKeyForOpenModelResourceErrorDialog(it)))»
		«xptExternalizer.accessorField(messageKey(i18nKeyForOpenModelResourceErrorDialog(it)))»
		«xptExternalizer.accessorField(i18nKeyForCreateDiagramProgressTask(it))»
		«xptExternalizer.accessorField(i18nKeyForCreateDiagramCommandLabel(it))»
	'''

	@Localization def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(titleKey(i18nKeyForOpenModelResourceErrorDialog(it)), 'Error')»
		«xptExternalizer.messageEntry(messageKey(i18nKeyForOpenModelResourceErrorDialog(it)), 'Failed to load model file {0}')»
		«xptExternalizer.messageEntry(i18nKeyForCreateDiagramProgressTask(it), 'Creating diagram and model files')»
		«xptExternalizer.messageEntry(i18nKeyForCreateDiagramCommandLabel(it), 'Creating diagram and model')»
	'''

}