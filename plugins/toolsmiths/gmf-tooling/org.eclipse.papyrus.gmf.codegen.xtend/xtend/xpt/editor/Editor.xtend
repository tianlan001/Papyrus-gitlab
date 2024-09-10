/*****************************************************************************
 * Copyright (c) 2006, 2017, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
 * Emilien Perico (Atos Origin) - add code to refactor some classes
 * Christian W. Damus (CEA) - bug 430648
 * Christian W. Damus (CEA) - bug 431023
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 512343
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/

package xpt.editor

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.List
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorView
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator
import org.eclipse.papyrus.gmf.codegen.gmfgen.Palette
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Externalizer
import xpt.ExternalizerUtils_qvto
import xpt.navigator.NavigatorItem
import xpt.navigator.NavigatorLinkHelper
import xpt.navigator.Utils_qvto

@Singleton class Editor {
	@Inject extension Common;
	@Inject extension CodeStyle;
	@Inject extension ExternalizerUtils_qvto;

	@Inject Externalizer xptExternalizer;
	@Inject Activator xptActivator;
	@Inject NavigatorLinkHelper xptNavigatorLinkHelper;
	@Inject NavigatorItem xptNavigatorItem;
	@Inject DiagramEditorContextMenuProvider xptDiagramEditorContextMenuProvider;

	@Inject extension Utils_qvto;

	def className(GenEditorView it) '''«it.className»'''

	def packageName(GenEditorView it) '''«it.packageName»'''

	def qualifiedClassName(GenEditorView it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenEditorView it) '''«qualifiedClassName(it)»'''

	def implementsList(GenEditorView it) '''«implementsList(buildImplementsList(it))»'''

	def getPreferencesHint(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint getPreferencesHint() {
		«/**
		 * seems better we use preference store directly (in configureGraphicalViewer) instead all these indirect ids 
		 */»return «xptActivator.preferenceHintAccess(editorGen)»;
		}
	'''

	def getContributorId(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		public String getContributorId() {
			return «xptActivator.qualifiedClassName(editorGen.plugin)».ID;
		}
	'''

	def checkEditorInput(GenEditorView it) '''«IF isIDEMode(it)»input instanceof org.eclipse.ui.IFileEditorInput || «ENDIF»input instanceof org.eclipse.emf.common.ui.URIEditorInput'''

	def gotoMarker(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		public void gotoMarker(org.eclipse.core.resources.IMarker marker) {
			org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService.getInstance().gotoMarker(this, marker);
		}
	'''

	def isSaveAsAllowed(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		public boolean isSaveAsAllowed() {
			return true;
		}
	'''

	def doSaveAs(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		public void doSaveAs() {
			performSaveAs(new org.eclipse.core.runtime.NullProgressMonitor());
		}
	'''

	def getShowInContext(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.ui.part.ShowInContext getShowInContext() {
			return new org.eclipse.ui.part.ShowInContext(getEditorInput(), «IF hasNavigator(it)»getNavigatorSelection()«ELSE»getGraphicalViewer().getSelection()«ENDIF»);
		}
	'''

	def configureGraphicalViewer(GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		protected void configureGraphicalViewer() {
			super.configureGraphicalViewer();
			«xptDiagramEditorContextMenuProvider.qualifiedClassName(it.editorGen.diagram)» provider =
					new «xptDiagramEditorContextMenuProvider.qualifiedClassName(it.editorGen.diagram)»(this, getDiagramGraphicalViewer());
			getDiagramGraphicalViewer().setContextMenu(provider);
			getSite().registerContextMenu(org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU, provider, getDiagramGraphicalViewer());
		}
	'''

	def controlLastClickPositionProviderService(GenEditorView it)'''
		«generatedMemberComment»
		protected void startupLastClickPositionProvider() {
			if (myLastClickPositionProvider == null) {
				myLastClickPositionProvider = new org.eclipse.gmf.tooling.runtime.part.LastClickPositionProvider(this);
				myLastClickPositionProvider.attachToService();
			}
		}

		«generatedMemberComment»
		protected void shutDownLastClickPositionProvider() {
			if (myLastClickPositionProvider != null) {
				myLastClickPositionProvider.detachFromService();
				myLastClickPositionProvider.dispose();
				myLastClickPositionProvider = null;
			}
		}
	'''

	def addDropTargetListener(GenEditorView it, String transferAccessor) '''
		getDiagramGraphicalViewer().addDropTargetListener(new DropTargetListener(getDiagramGraphicalViewer(), «transferAccessor») {

			protected Object getJavaObject(org.eclipse.swt.dnd.TransferData data) {
				return «transferAccessor».nativeToJava(data);
			}

		});
	'''

	def DropTargetListener(GenEditorView it) '''
		«generatedClassComment»
		private abstract class DropTargetListener extends org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener {

			«DTL_constructor(it)»

			«DTL_getObjectsBeingDropped(it)»

			«DTL_getJavaObject(it)»
		}
	'''

	def DTL_constructor(GenEditorView it) '''
		«generatedMemberComment»
		public DropTargetListener(org.eclipse.gef.EditPartViewer viewer, org.eclipse.swt.dnd.Transfer xfer) {
			super(viewer, xfer);
		}
	'''

	def DTL_getObjectsBeingDropped(GenEditorView it) '''
			«generatedMemberComment»
		protected java.util.List getObjectsBeingDropped() {
			org.eclipse.swt.dnd.TransferData data = getCurrentEvent().currentDataType;
			java.util.HashSet<org.eclipse.emf.common.util.URI> uris = new java.util.HashSet<«it.editorGen.diagram.diamondOp('org.eclipse.emf.common.util.URI')»>(); 

			Object transferedObject = getJavaObject(data);
			if (transferedObject instanceof org.eclipse.jface.viewers.IStructuredSelection) {
				org.eclipse.jface.viewers.IStructuredSelection selection = (org.eclipse.jface.viewers.IStructuredSelection) transferedObject;
				for (java.util.Iterator<?> it = selection.iterator(); it.hasNext();) {
					Object nextSelectedObject = it.next();
					«
		/*
				  * FIXME [MG]: move NavigatorItem to some place available in runtime and remove 
				  * "genEditor.getEditorGen().getNavigator() != null" test
				  */
		IF hasNavigator(it)»if (nextSelectedObject instanceof «xptNavigatorItem.qualifiedClassName(it.editorGen.navigator)») {
										org.eclipse.gmf.runtime.notation.View view = ((«xptNavigatorItem.qualifiedClassName(it.editorGen.navigator)») nextSelectedObject).getView();
										nextSelectedObject = view.getElement();
					} else «ENDIF»if (nextSelectedObject instanceof org.eclipse.core.runtime.IAdaptable) {
						org.eclipse.core.runtime.IAdaptable adaptable = (org.eclipse.core.runtime.IAdaptable) nextSelectedObject;
						nextSelectedObject = adaptable.getAdapter(org.eclipse.emf.ecore.EObject.class);
					}

					if (nextSelectedObject instanceof org.eclipse.emf.ecore.EObject) {
						org.eclipse.emf.ecore.EObject modelElement = (org.eclipse.emf.ecore.EObject) nextSelectedObject;
						uris.add(org.eclipse.emf.ecore.util.EcoreUtil.getURI(modelElement));	
					}
				}
			}

			java.util.ArrayList<org.eclipse.emf.ecore.EObject> result = new java.util.ArrayList<«it.editorGen.diagram.diamondOp('org.eclipse.emf.ecore.EObject')»>(uris.size());
			for (org.eclipse.emf.common.util.URI nextURI : uris) { 
				org.eclipse.emf.ecore.EObject modelObject = getEditingDomain().getResourceSet().getEObject(nextURI, true);
				result.add(modelObject);
			}
			return result;
		}
	'''

	def DTL_getJavaObject(GenEditorView it) '''
			«generatedMemberComment»
		protected abstract Object getJavaObject(org.eclipse.swt.dnd.TransferData data);
	'''

	@Localization def i18nValues(GenEditorView it) '''
		«xptExternalizer.messageEntry(i18nKeyForSavingDeletedFile(it), 'The original file \"{0}\" has been deleted.')»
		«xptExternalizer.messageEntry(titleKey(i18nKeyForSaveAsProblems(it)), 'Problem During Save As...')»
		«xptExternalizer.messageEntry(messageKey(i18nKeyForSaveAsProblems(it)), 'Save could not be completed. Target file is already open in another editor.')»
		«xptExternalizer.messageEntry(titleKey(i18nKeyForSaveProblems(it)), 'Save Problems')»
		«xptExternalizer.messageEntry(messageKey(i18nKeyForSaveProblems(it)), 'Could not save file.')»
	'''

	@Localization def i18nAccessors(GenEditorView it) '''
		«xptExternalizer.accessorField(i18nKeyForSavingDeletedFile(it))»
		«xptExternalizer.accessorField(titleKey(i18nKeyForSaveAsProblems(it)))»
		«xptExternalizer.accessorField(messageKey(i18nKeyForSaveAsProblems(it)))»
		«xptExternalizer.accessorField(titleKey(i18nKeyForSaveProblems(it)))»
		«xptExternalizer.accessorField(messageKey(i18nKeyForSaveProblems(it)))»
	'''

	@Localization def String i18nKeyForSavingDeletedFile(GenEditorView editor) {
		return i18nKeyForEditor(editor) + '.SavingDeletedFile'
	}

	@Localization def String i18nKeyForSaveAsProblems(GenEditorView editor) {
		return i18nKeyForEditor(editor) + '.SaveAsError'
	}

	@Localization def String i18nKeyForSaveProblems(GenEditorView editor) {
		return i18nKeyForEditor(editor) + '.SaveError'
	}

	@Localization def String i18nKeyForEditor(GenEditorView editor) {
		return '' + className(editor)
	}

	def Iterable<String> buildImplementsList(GenEditorView it) {
		var List<String> result = <String>newLinkedList();
		if (isIDEMode(it)) {
			result.add('org.eclipse.ui.ide.IGotoMarker');
		}
		if (hasPropertySheet(it) && it.editorGen.propertySheet.readOnly) {
			result.add('org.eclipse.gmf.runtime.diagram.ui.properties.views.IReadOnlyDiagramPropertySheetPageContributor');
		}
		return result;
	}

	def boolean isIDEMode(GenEditorView it) {
		return null === it.editorGen.application;
	}

	def boolean hasPropertySheet(GenEditorView it) {
		return it.editorGen.propertySheet !== null
	}

	def boolean hasNavigator(GenEditorView it) {
		return it.editorGen.navigator !== null
	}

	def extendsList(GenEditorView it) '''extends org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor'''

	def attributes(GenEditorView it) '''
	«generatedMemberComment»
	public static final String ID = "«ID»"; «nonNLS»

	«generatedMemberComment»
public static final String CONTEXT_ID = "«contextID»"; «nonNLS»

	«««	Documentation. adds listener for papyrus editors
	«generatedMemberComment»
	private org.eclipse.gef.KeyHandler paletteKeyHandler = null;

	«generatedMemberComment»
	private org.eclipse.swt.events.MouseListener paletteMouseListener = null;

	«««	Helps to handle correctly the dirty state
	«generatedMemberComment»
	private org.eclipse.papyrus.commands.util.OperationHistoryDirtyState dirtyState;

	«generatedMemberComment»
	private org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain;

	«generatedMemberComment»
    private org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider documentProvider;
	'''

	def constructor(GenEditorView it) '''
		«generatedMemberComment»
		public «className»(org.eclipse.papyrus.infra.core.services.ServicesRegistry servicesRegistry, org.eclipse.gmf.runtime.notation.Diagram diagram) throws org.eclipse.papyrus.infra.core.services.ServiceException{
		super(servicesRegistry, diagram);

		«««	Documentation. adds listener for papyrus palette service
		// adds a listener to the palette service, which reacts to palette customizations
		org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.getInstance().addProviderChangeListener(this);

		«««Share the same editing domain
		// Share the same editing provider
		editingDomain = servicesRegistry.getService(org.eclipse.emf.transaction.TransactionalEditingDomain.class);
		documentProvider = new org.eclipse.papyrus.infra.gmfdiag.common.GmfMultiDiagramDocumentProvider(editingDomain);

		// overrides editing domain created by super constructor
		setDocumentProvider(documentProvider);

		«««end of listeners addition
		}
	'''

		def getNavigatorSelection(GenNavigator it) '''

		«generatedMemberComment»
		private org.eclipse.jface.viewers.ISelection getNavigatorSelection() {
			«IF getDiagramTopReference(it) !==null »
			org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument document = getDiagramDocument();
			«ENDIF»
			«xptNavigatorLinkHelper.findSelectionBody(it)»
		}
	'''

	def createPaletteRoot (Palette it)'''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.gef.palette.PaletteRoot createPaletteRoot(org.eclipse.gef.palette.PaletteRoot existingPaletteRoot) {
			org.eclipse.gef.palette.PaletteRoot paletteRoot;
			if (existingPaletteRoot == null) {
				paletteRoot = org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.getInstance().createPalette(this, getDefaultPaletteContent());
			} else {
				org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.getInstance().updatePalette(existingPaletteRoot, this, getDefaultPaletteContent());
				paletteRoot = existingPaletteRoot;
			}
			applyCustomizationsToPalette(paletteRoot);
			return paletteRoot;
		}
	'''

//	FIXME - This has been overrided to comment the test on the palette tag in the gmfgen
	def Editor(GenEditorView it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		@SuppressWarnings({"deprecation", "restriction"})
		public class «className(it)» «extendsList(it)» «implementsList(it)» {

			«attributes(it)»
			«constructor(it)»
«««			«IF editorGen.diagram.palette != null»
				«createPaletteRoot(editorGen.diagram.palette)»
«««			«ENDIF»
			«getContextID(it)»
			«getPreferencesHint(it)»
			«getContributorId(it)»
			«getAdapter(it)»
			«getDocumentProvider(it)»
			«getEditingDomain(it)»
			«setDocumentProvider(it)»
			«IF isIDEMode(it)»
					«gotoMarker(it)»
					«isSaveAsAllowed(it)»
					«doSaveAs(it)»
					«performSaveAs(it)»
					«getShowInContext(it)»
					«IF hasNavigator(it)»
						«getNavigatorSelection(it.editorGen.navigator)»
					«ENDIF»
			«ENDIF»
			«configureGraphicalViewer(it)»
			«IF editorGen.diagram.generateCreateShortcutAction»
					«initializeGraphicalViewer(it)»
					«controlLastClickPositionProviderService»
					«dispose»
					«DropTargetListener(it)»
			«ENDIF»
			«additions(it)»
		}
	'''

	def createPaletteCustomizer (GenEditorView it)'''
		«generatedMemberComment»
		protected org.eclipse.gef.ui.palette.PaletteCustomizer createPaletteCustomizer() {
			return new org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteCustomizer(getPreferenceStore());
		}
	'''

	def additions (GenEditorView it)'''
		«createEditingDomain(it)»
		« configureDiagramEditDomain(it)»
		« doSave(it)»
		« getDirtyState(it)»
		« setUndoContext(it)»
		« isDirty(it)»
		«««Documentation. adds method to handle palette changes
		« handlePaletteChange(it)»
		« dispose(it)»
		« getPaletteViewer(it)»
		«««	Documentation: (RS) advanced customization abilities
		«««	« createPaletteCustomizer»
		« constructPaletteViewer(it)»
		« createPaletteviewerProvider(it)»
		«getGraphicalViewer(it)»
		«initializeGraphicalViewer(it)»
		«selectionChanged(it)»
	'''

	def handlePaletteChange (GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		public void providerChanged(org.eclipse.gmf.runtime.common.core.service.ProviderChangeEvent event) {
			// update the palette if the palette service has changed
			if (org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.getInstance().equals(event.getSource())) {
				org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.getInstance().updatePalette(getPaletteViewer().getPaletteRoot(), this, getDefaultPaletteContent());
			}
		}
	'''

	def constructPaletteViewer (GenEditorView it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.gef.ui.palette.PaletteViewer constructPaletteViewer() {
			return new org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteViewer();
		}
	'''

	def dispose(GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		public void dispose() {
			// remove palette service listener
			// remove preference listener
			org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.getInstance().removeProviderChangeListener(this);

			if(dirtyState != null) {
				dirtyState.dispose();
				dirtyState = null;
			}

			super.dispose();
		}
	'''

	def getPaletteViewer (GenEditorView it)'''
		«generatedMemberComment»
		protected org.eclipse.gef.ui.palette.PaletteViewer getPaletteViewer() {
			return getEditDomain().getPaletteViewer();
		}
	'''	

	def implementsList(Iterable<String> it)'''
		 implements org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener«IF !it.isEmpty», «FOR string : it SEPARATOR ', '»«implementsListEntry(string)»«ENDFOR»«ENDIF»
	'''

	def implementsListEntry (String it)'''«it»'''

	def createPaletteviewerProvider (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.gef.ui.palette.PaletteViewerProvider createPaletteViewerProvider() {
			getEditDomain().setPaletteRoot(createPaletteRoot(null));
			return new org.eclipse.gef.ui.palette.PaletteViewerProvider(getEditDomain()) {

				/**
				 * Override to provide the additional behavior for the tools. Will intialize with a
				 * PaletteEditPartFactory that has a TrackDragger that understand how to handle the
				 * mouseDoubleClick event for shape creation tools. Also will initialize the palette
				 * with a defaultTool that is the SelectToolEx that undestands how to handle the enter
				 * key which will result in the creation of the shape also.
				 */
				«overrideC»
				protected void configurePaletteViewer(org.eclipse.gef.ui.palette.PaletteViewer viewer) {
					super.configurePaletteViewer(viewer);

					// customize menu...
					viewer.setContextMenu(new org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteContextMenuProvider(viewer));

					viewer.getKeyHandler().setParent(getPaletteKeyHandler());
					viewer.getControl().addMouseListener(getPaletteMouseListener());

					// Add a transfer drag target listener that is supported on
					// palette template entries whose template is a creation tool.
					// This will enable drag and drop of the palette shape creation
					// tools.
					viewer.addDragSourceListener(new org.eclipse.gmf.runtime.diagram.ui.internal.parts.PaletteToolTransferDragSourceListener(viewer));
					viewer.setCustomizer(createPaletteCustomizer());
				}

				«overrideC»
				public org.eclipse.gef.ui.palette.PaletteViewer createPaletteViewer(org.eclipse.swt.widgets.Composite parent) {
					org.eclipse.gef.ui.palette.PaletteViewer pViewer = constructPaletteViewer();
					pViewer.createControl(parent);
					configurePaletteViewer(pViewer);
					hookPaletteViewer(pViewer);
					return pViewer;
				}

				/**
				 * @return Palette Key Handler for the palette
				 */
				private org.eclipse.gef.KeyHandler getPaletteKeyHandler() {

					if (paletteKeyHandler == null) {

						paletteKeyHandler = new org.eclipse.gef.KeyHandler() {

							/**
							 * Processes a <i>key released </i> event. This method is called by the Tool
							 * whenever a key is released, and the Tool is in the proper state. Override
							 * to support pressing the enter key to create a shape or connection
							 * (between two selected shapes)
							 *
							 * @param event
							 *            the KeyEvent
							 * @return <code>true</code> if KeyEvent was handled in some way
							 */
							«overrideC»
							public boolean keyReleased(org.eclipse.swt.events.KeyEvent event) {

								if (event.keyCode == org.eclipse.swt.SWT.Selection) {

								org.eclipse.gef.Tool tool = getPaletteViewer().getActiveTool().createTool();

									if (toolSupportsAccessibility(tool)) {

										tool.keyUp(event, getDiagramGraphicalViewer());

										// deactivate current selection
										getPaletteViewer().setActiveTool(null);

										return true;
									}

								}
								return super.keyReleased(event);
							}

						};

					}
					return paletteKeyHandler;
				}

				/**
				 * @return Palette Mouse listener for the palette
				 */
				private org.eclipse.swt.events.MouseListener getPaletteMouseListener() {

					if (paletteMouseListener == null) {

						paletteMouseListener = new org.eclipse.swt.events.MouseListener() {

							/**
							 * Flag to indicate that the current active tool should be cleared after a
							 * mouse double-click event.
							 */
							private boolean clearActiveTool = false;

							/**
							 * Override to support double-clicking a palette tool entry to create a
							 * shape or connection (between two selected shapes).
							 *
							 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
							 */
							«overrideI»
							public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
								org.eclipse.gef.Tool tool = getPaletteViewer().getActiveTool().createTool();

								if (toolSupportsAccessibility(tool)) {

									tool.setViewer(getDiagramGraphicalViewer());
									tool.setEditDomain(getDiagramGraphicalViewer().getEditDomain());
									tool.mouseDoubleClick(e, getDiagramGraphicalViewer());

									// Current active tool should be deactivated,
									// but if it is down here it will get
									// reactivated deep in GEF palette code after
									// receiving mouse up events.
									clearActiveTool = true;
								}
							}

							«overrideI»
							public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
								// do nothing
							}

							«overrideI»
							public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
								// Deactivate current active tool here if a
								// double-click was handled.
								if (clearActiveTool) {
									getPaletteViewer().setActiveTool(null);
									clearActiveTool = false;
								}

							}
						};

					}
					return paletteMouseListener;
				}

			};
		}
	'''

	//Not used
	def performSaveAs (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected void performSaveAs(org.eclipse.core.runtime.IProgressMonitor progressMonitor) {
			// Nothing
		}
	'''

	//Share the same editing domain
	def getEditingDomain (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.emf.transaction.TransactionalEditingDomain getEditingDomain() {
			return editingDomain;
		}	
	'''

	def createEditingDomain (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.emf.transaction.TransactionalEditingDomain createEditingDomain() {
			// Already configured
			return editingDomain;
		}
	'''

	def configureDiagramEditDomain (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected void configureDiagramEditDomain() {
			super.configureDiagramEditDomain();
			getDiagramEditDomain().getDiagramCommandStack().addCommandStackListener(new org.eclipse.gef.commands.CommandStackListener() {

				«overrideI»
				public void commandStackChanged(java.util.EventObject event) {
					if (org.eclipse.swt.widgets.Display.getCurrent() == null) { 
						org.eclipse.swt.widgets.Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								firePropertyChange(org.eclipse.ui.IEditorPart.PROP_DIRTY);
							}
						});
					} else {
						firePropertyChange(org.eclipse.ui.IEditorPart.PROP_DIRTY);	
					}
				}
			});
		}
	'''

	def doSave (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		public void doSave(org.eclipse.core.runtime.IProgressMonitor progressMonitor) {
			// The saving of the resource is done by the CoreMultiDiagramEditor
			getDirtyState().saved();
		}
	'''

	def getDirtyState (GenEditorView it)'''
		«generatedMemberComment»
		protected org.eclipse.papyrus.commands.util.OperationHistoryDirtyState getDirtyState() {
			if(dirtyState == null) {
				dirtyState = org.eclipse.papyrus.commands.util.OperationHistoryDirtyState.newInstance(getUndoContext(), getOperationHistory());
			}
			return dirtyState;
		}
	'''

	def setUndoContext (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected void setUndoContext(org.eclipse.core.commands.operations.IUndoContext context) {
			if(dirtyState != null) {
				dirtyState.dispose();
				dirtyState = null;
			}

			super.setUndoContext(context);
		}
	'''

	//Fix the dirty state
	def isDirty (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		public boolean isDirty() {
			return getDirtyState().isDirty();
		}
	'''

	//Code refactoring moved in UMLDiagramEditor
	def getDocumentProvider (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected final org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider getDocumentProvider(org.eclipse.ui.IEditorInput input) {
			return documentProvider;
		}
	'''

	def setDocumentProvider (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected final void setDocumentProvider(org.eclipse.ui.IEditorInput input) {
			// Already set in the constructor
		}
	'''

	def getGraphicalViewer (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.gef.GraphicalViewer getGraphicalViewer() {
			return super.getGraphicalViewer();
		}
	'''

	def initializeGraphicalViewer (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected void initializeGraphicalViewer() {
			super.initializeGraphicalViewer();

			// Enable Drop
			getDiagramGraphicalViewer().addDropTargetListener(new org.eclipse.papyrus.uml.diagram.common.listeners.DropTargetListener(getDiagramGraphicalViewer(), org.eclipse.jface.util.LocalSelectionTransfer.getTransfer()) {
				@Override
				protected Object getJavaObject(org.eclipse.swt.dnd.TransferData data) {
					// It is usual for the transfer data not to be set because it is available locally
					return LocalSelectionTransfer.getTransfer().getSelection();
				}

				@Override
				protected org.eclipse.emf.transaction.TransactionalEditingDomain getTransactionalEditingDomain() {
					return getEditingDomain();
				}
			});
		}
	'''

	def selectionChanged (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		public void selectionChanged(org.eclipse.ui.IWorkbenchPart part, org.eclipse.jface.viewers.ISelection selection) {
			if (getSite().getPage().getActiveEditor() instanceof org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor) {
				org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor editor = (org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor) getSite().getPage().getActiveEditor();
				// If not the active editor, ignore selection changed.
				if (this.equals(editor.getActiveEditor())) {
					updateActions(getSelectionActions());
					super.selectionChanged(part, selection);
				} else {
					super.selectionChanged(part, selection);
				}
			} else {
				super.selectionChanged(part, selection);
			}
			// from
			// org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor.selectionChanged(IWorkbenchPart,
			// ISelection)
			if (part == this) {
				rebuildStatusLine();
			}
		}
	'''

	def getContextID (GenEditorView it)'''
		«generatedMemberComment»
		«overrideC»
		protected String getContextID() {
			return CONTEXT_ID;
		}
	'''

	def getAdapter(GenEditorView it) '''
		«IF !hasPropertySheet(it) || hasNavigator(it)»
			«generatedMemberComment»
			«overrideC»
			@SuppressWarnings("rawtypes")
			public Object getAdapter(Class type) {
				«IF !hasPropertySheet(it)»
					if (type == org.eclipse.ui.views.properties.IPropertySheetPage.class) {
						return null;
					}
				«ENDIF»
				«IF hasNavigator(it)»
					if (type == org.eclipse.ui.part.IShowInTargetList.class) {
						return new org.eclipse.ui.part.IShowInTargetList() {

							«overrideI»
							public String[] getShowInTargetIds() {
								return new String[] { org.eclipse.ui.navigator.resources.ProjectExplorer.VIEW_ID };
							}
						};
					}
				«ENDIF»
				return super.getAdapter(type);
			}
		«ENDIF»
	'''

}
