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
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.providers

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.CodeStyle
import xpt.Common

@Singleton class MarkerNavigationProvider {

	@Inject extension Common;
	@Inject extension CodeStyle;

	def className(GenDiagram it) '''«it.markerNavigationProviderClassName»'''

	def packageName(GenDiagram it) '''«it.providersPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def  MarkerNavigationProvider(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» extends org.eclipse.gmf.runtime.emf.ui.providers.marker.AbstractModelMarkerNavigationProvider {

			«generatedMemberComment»
			public static final String MARKER_TYPE = «editorGen.plugin.activatorQualifiedClassName».ID + ".«getValidationDiagnosticMarkerType()»"; «nonNLS(1)»

			«generatedMemberComment»
			«overrideC»
			protected void doGotoMarker(org.eclipse.core.resources.IMarker marker) {
				String elementId = marker.getAttribute(org.eclipse.gmf.runtime.common.core.resources.IMarker.ELEMENT_ID, null);
				if (elementId == null || !(getEditor() instanceof org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor)) {
					return;
				}
				org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor editor =
						(org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor) getEditor();
				java.util.Map<?, ?> editPartRegistry = editor.getDiagramGraphicalViewer().getEditPartRegistry();
				org.eclipse.emf.ecore.EObject targetView = editor.getDiagram().eResource().getEObject(elementId);
				if (targetView == null) {
					return;
				}
				org.eclipse.gef.EditPart targetEditPart = (org.eclipse.gef.EditPart) editPartRegistry.get(targetView);
				if (targetEditPart != null) {
					«getDiagramEditorUtilQualifiedClassName()».selectElementsInDiagram(editor, java.util.Arrays.asList(new org.eclipse.gef.EditPart[] { targetEditPart }));
				}
			}

			«generatedMemberComment»
			public static void deleteMarkers(org.eclipse.core.resources.IResource resource) {
				try {
					resource.deleteMarkers(MARKER_TYPE, true, org.eclipse.core.resources.IResource.DEPTH_ZERO);
				} catch (org.eclipse.core.runtime.CoreException e) {
					«editorGen.plugin.activatorQualifiedClassName».getInstance().logError("Failed to delete validation markers", e); «nonNLS(1)»
				}
			}

			«generatedMemberComment»
			public static org.eclipse.core.resources.IMarker addMarker(org.eclipse.core.resources.IFile file, String elementId, String location, String message, int statusSeverity) {
			org.eclipse.core.resources.IMarker marker = null;
			try {
				marker = file.createMarker(MARKER_TYPE);
				marker.setAttribute(org.eclipse.core.resources.IMarker.MESSAGE, message);
				marker.setAttribute(org.eclipse.core.resources.IMarker.LOCATION, location);
				marker.setAttribute(org.eclipse.gmf.runtime.common.core.resources.IMarker.ELEMENT_ID, elementId);
				int markerSeverity = org.eclipse.core.resources.IMarker.SEVERITY_INFO;
				if (statusSeverity == org.eclipse.core.runtime.IStatus.WARNING) {
					markerSeverity = org.eclipse.core.resources.IMarker.SEVERITY_WARNING;
				} else if (statusSeverity == org.eclipse.core.runtime.IStatus.ERROR || statusSeverity == org.eclipse.core.runtime.IStatus.CANCEL) {
				markerSeverity = org.eclipse.core.resources.IMarker.SEVERITY_ERROR;
				}
				marker.setAttribute(org.eclipse.core.resources.IMarker.SEVERITY, markerSeverity);
			} catch (org.eclipse.core.runtime.CoreException e) {
				«editorGen.plugin.activatorQualifiedClassName».getInstance().logError("Failed to create validation marker", e); «nonNLS(1)»
			}
			return marker;
			}
		}
	'''
}
