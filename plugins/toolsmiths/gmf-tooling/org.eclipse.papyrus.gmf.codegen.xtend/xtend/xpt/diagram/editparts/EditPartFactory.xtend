/*****************************************************************************
 * Copyright (c) 2006, 2010, 2013, 2021 Borland Software Corporation, CEA LIST, Artal and others
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
package xpt.diagram.editparts

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.Common
import xpt.editor.VisualIDRegistry
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode
import diagram.editparts.ChildNodeLabelEditPart
import diagram.editparts.NodeEditPart
import diagram.editparts.LinkEditPart
import diagram.editparts.LinkLabelEditPart
import diagram.editparts.ExternalNodeLabelEditPart
import diagram.editparts.CompartmentEditPart
import diagram.editparts.NodeLabelEditPart
import diagram.editparts.DiagramEditPart
import xpt.CodeStyle

@com.google.inject.Singleton class EditPartFactory {

	@Inject extension Common;
	@Inject extension CodeStyle;
	
	@Inject ChildNodeLabelEditPart childNodeLabelEditPart;
	@Inject NodeEditPart nodeEditPart;
	@Inject LinkEditPart linkEditPart;
	@Inject LinkLabelEditPart linkLabelEditPart;
	@Inject ExternalNodeLabelEditPart externalNodeLabelEditPart;
	@Inject CompartmentEditPart compartmentEditPart;
	@Inject NodeLabelEditPart nodeLabelEditPart;
	@Inject DiagramEditPart diagramEditPart;

	@Inject VisualIDRegistry xptVisualIDRegistry;

	def className(GenDiagram it) '''«it.editPartFactoryClassName»'''

	def packageName(GenDiagram it) '''«it.editPartsPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def EditPartFactory(GenDiagram it) '''
		«copyright(getDiagram().editorGen)»
		package «packageName(it)»;

		«generatedClassComment()»
		public class «className(it)» implements org.eclipse.gef.EditPartFactory {

			«createEditPartMethod(it)»

			«createUnrecognizedEditPart(it)»

			«getTextCellEditorLocator(it)»
		}
	'''

	def createEditPartMethod(GenDiagram it) '''
		«generatedMemberComment()»
		«overrideI»
		public org.eclipse.gef.EditPart createEditPart(org.eclipse.gef.EditPart context, Object model) {
			if (model instanceof org.eclipse.gmf.runtime.notation.View) {
				org.eclipse.gmf.runtime.notation.View view = (org.eclipse.gmf.runtime.notation.View) model;
				switch («xptVisualIDRegistry.getVisualIDMethodCall(it)»(view)) {
				«createEditPart(it)»
				«FOR node : it.allNodes»
					«createEditPart(node)»
					«FOR label : node.labels»
						«createEditPart(label)»
					«ENDFOR»
				«ENDFOR»
				«FOR comp : it.compartments»
					«createEditPart(comp)»
				«ENDFOR»
				«FOR link : it.links»
					«createEditPart(link)»
					«FOR label : link.labels»
						«createEditPart(label)»
					«ENDFOR»
				«ENDFOR»
				}
			}
			return createUnrecognizedEditPart(context, model);
		}
	'''

	private def createEditPart(GenCommonBase it) '''
		«xptVisualIDRegistry.caseVisualID(it)»
			return new «getEditPartQualifiedClassName(it)»(view);
	'''

	def createUnrecognizedEditPart(GenDiagram it) '''
		«generatedMemberComment()»
		 private org.eclipse.gef.EditPart createUnrecognizedEditPart(org.eclipse.gef.EditPart context, Object model) {
		 	// Handle creation of unrecognized child node EditParts here
		 	return null;
		 }
	'''

	def getTextCellEditorLocator(GenDiagram it) '''
		«generatedMemberComment»
		public static org.eclipse.gef.tools.CellEditorLocator getTextCellEditorLocator(org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart source) {
			if (source.getFigure() instanceof org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure){
				return new MultilineCellEditorLocator((org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure) source.getFigure());
			}
			else {
				return org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit.locator.CellEditorLocatorAccess.INSTANCE.getTextCellEditorLocator(source);
			}
		}

		«generatedClassComment»
		static private class MultilineCellEditorLocator implements org.eclipse.gef.tools.CellEditorLocator {

			«generatedClassComment»
			private org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure multilineEditableFigure;

			«generatedClassComment»
			public MultilineCellEditorLocator(org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure figure) {
				this.multilineEditableFigure = figure;
			}

			«generatedClassComment»
			public org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure getMultilineEditableFigure() {
				return multilineEditableFigure;
			}

			«generatedClassComment»
			«overrideI»
			public void relocate(org.eclipse.jface.viewers.CellEditor celleditor) {
				org.eclipse.swt.widgets.Text text = (org.eclipse.swt.widgets.Text) celleditor.getControl();
				org.eclipse.draw2d.geometry.Rectangle rect = getMultilineEditableFigure().getBounds().getCopy();
				rect.x=getMultilineEditableFigure().getEditionLocation().x;
				rect.y=getMultilineEditableFigure().getEditionLocation().y;
				getMultilineEditableFigure().translateToAbsolute(rect);
				if (getMultilineEditableFigure().getText().length() > 0) {
					rect.setSize(new org.eclipse.draw2d.geometry.Dimension(text.computeSize(rect.width, org.eclipse.swt.SWT.DEFAULT)));
				}
				if (!rect.equals(new org.eclipse.draw2d.geometry.Rectangle(text.getBounds()))) {
					text.setBounds(rect.x, rect.y, rect.width, rect.height);
				}
			}
		}
	'''

	def dispatch CharSequence getEditPartQualifiedClassName(GenCommonBase it) ''''''
	def dispatch CharSequence getEditPartQualifiedClassName(GenNode it) '''«nodeEditPart.qualifiedClassName(it)»'''
	def dispatch CharSequence getEditPartQualifiedClassName(GenLink it) '''«linkEditPart.qualifiedClassName(it)»'''
	def dispatch CharSequence getEditPartQualifiedClassName(GenCompartment it) '''«compartmentEditPart.qualifiedClassName(it)»'''
	def dispatch CharSequence getEditPartQualifiedClassName(GenDiagram it) '''«diagramEditPart.qualifiedClassName(it)»'''
	def dispatch CharSequence getEditPartQualifiedClassName(GenExternalNodeLabel it) '''«externalNodeLabelEditPart.qualifiedClassName(it)»'''
	def dispatch CharSequence getEditPartQualifiedClassName(GenNodeLabel it) '''«nodeLabelEditPart.qualifiedClassName(it)»'''
	def dispatch CharSequence getEditPartQualifiedClassName(GenLinkLabel it) '''«linkLabelEditPart.qualifiedClassName(it)»'''
	def dispatch CharSequence getEditPartQualifiedClassName(GenChildLabelNode it) '''«childNodeLabelEditPart.qualifiedClassName(it)»'''

}
