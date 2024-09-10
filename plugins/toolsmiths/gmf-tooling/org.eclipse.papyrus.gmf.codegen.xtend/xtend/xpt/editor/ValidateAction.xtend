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
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import plugin.Activator
import xpt.CodeStyle
import xpt.Common
import xpt.Externalizer
import xpt.ExternalizerUtils_qvto
import xpt.GenAuditRoot_qvto
import xpt.providers.MarkerNavigationProvider
import xpt.providers.ValidationDecoratorProvider
import xpt.providers.ValidationProvider

@Singleton class ValidateAction {
	@Inject extension Common;
	@Inject extension CodeStyle

	@Inject DiagramEditorUtil xptDiagramEditorUtil;
	@Inject Activator xptActivator
	@Inject ValidationProvider xptValidationProvider

	@Inject extension GenAuditRoot_qvto;
	@Inject extension ExternalizerUtils_qvto;

	@Inject ValidationMarker xptValidationMarker;
	@Inject Externalizer xptExternalizer;
	@Inject MarkerNavigationProvider xptMarkerNavigationProvider;
	@Inject ValidationDecoratorProvider xptValidationDecoratorProvider;

	@MetaDef def className(GenDiagram it) '''ValidateAction'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def extendsList(GenDiagram it) '''extends org.eclipse.jface.action.Action'''

	def ValidateAction(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {

			«attributes(it)»
			«constructor(it)»
			«run(it)»
			«runValidation(it)»
			«runNonUIValidation(it)»
			«runValidationWithEP(it)»
			«runEMFValidator(it)»
			«validate(it)»
			«createMarkersForStatus(it)»
			«createMarkersForDiagnostic(it)»
			«addMarker(it)»
			«diagnosticToStatusSeverity(it)»
			«collectTargetElementsFromStatus(it)»
			«collectTargetElementsFromDiagnostic(it)»
		}
	'''

	def attributes(GenDiagram it) '''
		«generatedMemberComment»
		private org.eclipse.ui.IWorkbenchPage page;
	'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.ui.IWorkbenchPage page) {
			setText(«xptExternalizer.accessorCall(editorGen, messageKey(i18nKeyForValidateAction(it)))»);
			this.page = page;
		}
	'''

	def run(GenDiagram it) '''
		«generatedMemberComment»
		«overrideC»
		public void run() {
			org.eclipse.ui.IWorkbenchPart workbenchPart = page.getActivePart();
			if (workbenchPart instanceof org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart) {
				final org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart part =	(org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart) workbenchPart;
				try {
					«IF editorGen.application === null »new org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation(«ENDIF»new org.eclipse.jface.operation.IRunnableWithProgress() {
						«overrideI»
						public void run(org.eclipse.core.runtime.IProgressMonitor monitor) throws InterruptedException, java.lang.reflect.InvocationTargetException {
							runValidation(part.getDiagramEditPart(), part.getDiagram());
						}
					}«IF editorGen.application === null »)«ENDIF».run(new org.eclipse.core.runtime.NullProgressMonitor());			
				} catch (Exception e) {
					«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Validation action failed", e); «nonNLS(1)»
				}
			}
		}
	'''	

	def runValidation(GenDiagram it) '''
		«generatedMemberComment»
		public static void runValidation(org.eclipse.gmf.runtime.notation.View view) {
			try {
				if («xptDiagramEditorUtil.qualifiedClassName(it)».openDiagram(view.eResource())) {
					org.eclipse.ui.IEditorPart editorPart = org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					if (editorPart instanceof org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart) {
						runValidation(((org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart) editorPart).getDiagramEditPart(), view);
					} else {
						runNonUIValidation(view);
					}
				}
			} catch (Exception e) {
				«xptActivator.qualifiedClassName(editorGen.plugin)».getInstance().logError("Validation action failed", e); «nonNLS(1)»
			}
		}
	'''

	def runNonUIValidation(GenDiagram it)'''
		«generatedMemberComment»
		public static void runNonUIValidation(org.eclipse.gmf.runtime.notation.View view) {
			org.eclipse.swt.widgets.Shell shell = org.eclipse.swt.widgets.Display.getCurrent().getActiveShell();
			if (shell == null) {
				shell = new org.eclipse.swt.widgets.Shell();
			}
			org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart =
					org.eclipse.gmf.runtime.diagram.ui.OffscreenEditPartFactory.getInstance().createDiagramEditPart(view.getDiagram(),shell);
			runValidation(diagramEditPart, view);
		}		
	'''

	def runValidationWithEP(GenDiagram it) '''
		«generatedMemberComment»
		public static void runValidation(org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart, org.eclipse.gmf.runtime.notation.View view) {
			final org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart fpart = diagramEditPart;
			final org.eclipse.gmf.runtime.notation.View fview = view;
			org.eclipse.emf.transaction.TransactionalEditingDomain txDomain = org.eclipse.emf.transaction.util.TransactionUtil.getEditingDomain(view);
			«xptValidationProvider.qualifiedClassName(it)».runWithConstraints(txDomain, new Runnable() {
				«overrideI»
				public void run() {
					validate(fpart, fview);
				}
			});
		}
	'''

	def runEMFValidator(GenDiagram it) '''
		«generatedMemberComment»
		private static org.eclipse.emf.common.util.Diagnostic runEMFValidator(org.eclipse.gmf.runtime.notation.View target) {
			if (target.isSetElement() && target.getElement() != null) {
			return new org.eclipse.emf.ecore.util.Diagnostician() {
			«overrideC»
			public String getObjectLabel(org.eclipse.emf.ecore.EObject eObject) {
				return org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil.getQualifiedName(eObject, true);
			}
				}.validate(target.getElement());
			}
			return org.eclipse.emf.common.util.Diagnostic.OK_INSTANCE;
		}
	'''

	def validate(GenDiagram it) '''
		«generatedMemberComment»
		private static void validate(org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart, org.eclipse.gmf.runtime.notation.View view) {
			«IF editorGen.application === null »
				org.eclipse.core.resources.IFile target = view.eResource() != null ? org.eclipse.emf.workspace.util.WorkspaceSynchronizer.getFile(view.eResource()) : null;
				if (target != null) {
					«xptMarkerNavigationProvider.qualifiedClassName(it)».deleteMarkers(target);
				}
			«ELSE»
				org.eclipse.gmf.runtime.notation.View target = view;
				«xptValidationMarker.qualifiedClassName(it)».removeAllMarkers(diagramEditPart.getViewer());
			«ENDIF»
			org.eclipse.emf.common.util.Diagnostic diagnostic = runEMFValidator(view);
			createMarkers(target, diagnostic, diagramEditPart);
			org.eclipse.emf.validation.service.IBatchValidator validator = (org.eclipse.emf.validation.service.IBatchValidator) org.eclipse.emf.validation.service.ModelValidationService.getInstance().newValidator(org.eclipse.emf.validation.model.EvaluationMode.BATCH);
			validator.setIncludeLiveConstraints(true);
			if (view.isSetElement() && view.getElement() != null) {
				org.eclipse.core.runtime.IStatus status = validator.validate(view.getElement());
				createMarkers(target, status, diagramEditPart);
			}
			«IF shouldRunValidateOnDiagram(editorGen.audits)»
				«IF hasDiagramElementTargetRule(editorGen.audits)»
					validator.setTraversalStrategy(«xptValidationProvider.qualifiedClassName(it)».getNotationTraversalStrategy(validator));
				«ENDIF»
				org.eclipse.core.runtime.IStatus status = validator.validate(view);
				createMarkers(target, status, diagramEditPart);
			«ENDIF»
			«IF editorGen.application !== null && validationDecorators»
				«xptValidationDecoratorProvider.qualifiedClassName(it)».refreshDecorators(view);
				for (java.util.Iterator it = view.eAllContents(); it.hasNext();) {
					org.eclipse.emf.ecore.EObject next = (org.eclipse.emf.ecore.EObject) it.next();
					if (next instanceof org.eclipse.gmf.runtime.notation.View) {
						«xptValidationDecoratorProvider.qualifiedClassName(it)».refreshDecorators((org.eclipse.gmf.runtime.notation.View) next);
					}
				}
			«ENDIF»
		}
	'''

	def createMarkersForStatus(GenDiagram it) '''
		«generatedMemberComment»
		private static void createMarkers(«IF editorGen.application === null »org.eclipse.core.resources.IFile«ELSE»org.eclipse.gmf.runtime.notation.View«ENDIF» target, org.eclipse.core.runtime.IStatus validationStatus, org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart) {
			if (validationStatus.isOK()) {
				return;
			}
			final org.eclipse.core.runtime.IStatus rootStatus = validationStatus;
			java.util.List<org.eclipse.emf.validation.model.IConstraintStatus> allStatuses = new java.util.ArrayList<«diamondOp('org.eclipse.emf.validation.model.IConstraintStatus')»>();
			«xptDiagramEditorUtil.qualifiedClassName(it)».LazyElement2ViewMap element2ViewMap = new «xptDiagramEditorUtil.qualifiedClassName(it)».LazyElement2ViewMap(diagramEditPart.getDiagramView(), collectTargetElements(rootStatus, new java.util.HashSet<«diamondOp('org.eclipse.emf.ecore.EObject')»>(), allStatuses));
			for (java.util.Iterator<org.eclipse.emf.validation.model.IConstraintStatus> it = allStatuses.iterator(); it.hasNext();) {
				org.eclipse.emf.validation.model.IConstraintStatus nextStatus = it.next();
				org.eclipse.gmf.runtime.notation.View view = «xptDiagramEditorUtil.qualifiedClassName(it)».findView(diagramEditPart, nextStatus.getTarget(), element2ViewMap);			
				addMarker(diagramEditPart.getViewer(), target, view.eResource().getURIFragment(view), org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil.getQualifiedName(nextStatus.getTarget(), true), nextStatus.getMessage(), nextStatus.getSeverity());
			}
		}
	'''

	def createMarkersForDiagnostic(GenDiagram it) '''
		«generatedMemberComment»
		private static void createMarkers(«IF editorGen.application === null »org.eclipse.core.resources.IFile«ELSE»org.eclipse.gmf.runtime.notation.View«ENDIF» target, org.eclipse.emf.common.util.Diagnostic emfValidationStatus, org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart diagramEditPart) {
			if (emfValidationStatus.getSeverity() == org.eclipse.emf.common.util.Diagnostic.OK) {
				return;
			}
			final org.eclipse.emf.common.util.Diagnostic rootStatus = emfValidationStatus;
			java.util.List<org.eclipse.emf.common.util.Diagnostic> allDiagnostics = new java.util.ArrayList<«diamondOp('org.eclipse.emf.common.util.Diagnostic')»>();
			«xptDiagramEditorUtil.qualifiedClassName(it)».LazyElement2ViewMap element2ViewMap = new «xptDiagramEditorUtil.qualifiedClassName(it)».LazyElement2ViewMap(diagramEditPart.getDiagramView(), collectTargetElements(rootStatus, new java.util.HashSet<«diamondOp('org.eclipse.emf.ecore.EObject')»>(), allDiagnostics));
			for (java.util.Iterator<org.eclipse.emf.common.util.Diagnostic> it = emfValidationStatus.getChildren().iterator(); it.hasNext();) {
				org.eclipse.emf.common.util.Diagnostic nextDiagnostic = it.next();
				java.util.List<?> data = nextDiagnostic.getData();
				if (data != null && !data.isEmpty() && data.get(0) instanceof org.eclipse.emf.ecore.EObject) {
					org.eclipse.emf.ecore.EObject element = (org.eclipse.emf.ecore.EObject) data.get(0);
					org.eclipse.gmf.runtime.notation.View view = «xptDiagramEditorUtil.qualifiedClassName(it)».findView(diagramEditPart, element, element2ViewMap);
					addMarker(diagramEditPart.getViewer(), target, view.eResource().getURIFragment(view), org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil.getQualifiedName(element, true), nextDiagnostic.getMessage(), diagnosticToStatusSeverity(nextDiagnostic.getSeverity()));
				}
			}
		}
	'''

	def addMarker(GenDiagram it) '''
		«generatedMemberComment»
		private static void addMarker(org.eclipse.gef.EditPartViewer viewer, «IF editorGen.application === null »org.eclipse.core.resources.IFile
				«ELSE»org.eclipse.gmf.runtime.notation.View«ENDIF» target, String elementId, String location, String message, int statusSeverity) {
			if (target == null) {
			return;
			}
			«IF editorGen.application === null »
				«xptMarkerNavigationProvider.qualifiedClassName(it)».addMarker(target, elementId, location, message, statusSeverity);
			«ELSE»
				new «xptValidationMarker.qualifiedClassName(it)»(location, message, statusSeverity).add(viewer, elementId);
			«ENDIF»
		}
	'''

	def diagnosticToStatusSeverity(GenDiagram it) '''
		«generatedMemberComment»
		private static int diagnosticToStatusSeverity(int diagnosticSeverity) {
			if (diagnosticSeverity == org.eclipse.emf.common.util.Diagnostic.OK) {
				return org.eclipse.core.runtime.IStatus.OK;
			} else if (diagnosticSeverity == org.eclipse.emf.common.util.Diagnostic.INFO) {
				return org.eclipse.core.runtime.IStatus.INFO;
			} else if (diagnosticSeverity == org.eclipse.emf.common.util.Diagnostic.WARNING) {
				return org.eclipse.core.runtime.IStatus.WARNING;
			} else if (diagnosticSeverity == org.eclipse.emf.common.util.Diagnostic.ERROR || diagnosticSeverity == org.eclipse.emf.common.util.Diagnostic.CANCEL) {
			return org.eclipse.core.runtime.IStatus.ERROR;
			}
			return org.eclipse.core.runtime.IStatus.INFO;
		}
	'''

	def collectTargetElementsFromStatus(GenDiagram it) '''
		«generatedMemberComment»
		private static java.util.Set<org.eclipse.emf.ecore.EObject> collectTargetElements(org.eclipse.core.runtime.IStatus status, java.util.Set<org.eclipse.emf.ecore.EObject> targetElementCollector, java.util.List<org.eclipse.emf.validation.model.IConstraintStatus> allConstraintStatuses) {
			if (status instanceof org.eclipse.emf.validation.model.IConstraintStatus) {
				targetElementCollector.add(((org.eclipse.emf.validation.model.IConstraintStatus) status).getTarget());
				allConstraintStatuses.add((org.eclipse.emf.validation.model.IConstraintStatus)status);
			}
			if (status.isMultiStatus()) {
				org.eclipse.core.runtime.IStatus[] children = status.getChildren();
				for (int i = 0; i < children.length; i++) {
					collectTargetElements(children[i], targetElementCollector, allConstraintStatuses);				
				}
			}
			return targetElementCollector;
		}
	'''

	def collectTargetElementsFromDiagnostic(GenDiagram it) '''
		«generatedMemberComment»
		private static java.util.Set<org.eclipse.emf.ecore.EObject> collectTargetElements(org.eclipse.emf.common.util.Diagnostic diagnostic, java.util.Set<org.eclipse.emf.ecore.EObject> targetElementCollector, java.util.List<org.eclipse.emf.common.util.Diagnostic> allDiagnostics) {
			java.util.List<?> data = diagnostic.getData();
			org.eclipse.emf.ecore.EObject target = null;
			if (data != null && !data.isEmpty() && data.get(0) instanceof org.eclipse.emf.ecore.EObject) {
				target = (org.eclipse.emf.ecore.EObject) data.get(0);
				targetElementCollector.add(target);	
				allDiagnostics.add(diagnostic);
			}
			if (diagnostic.getChildren() != null && !diagnostic.getChildren().isEmpty()) {
				for (java.util.Iterator<org.eclipse.emf.common.util.Diagnostic> it = diagnostic.getChildren().iterator(); it.hasNext();) {
					collectTargetElements(it.next(), targetElementCollector, allDiagnostics);
				}
			}
			return targetElementCollector;
		}
	'''

	@Localization def i18nValues(GenDiagram it) '''
		«xptExternalizer.messageEntry(messageKey(i18nKeyForValidateAction(it)), 'Validate')»
	'''

	@Localization def i18nAccessors(GenDiagram it) '''
		«xptExternalizer.accessorField(messageKey(i18nKeyForValidateAction(it)))»
	'''

	@Localization def String i18nKeyForValidateAction(GenDiagram diagram) {
		return className(diagram).toString
	}
}
