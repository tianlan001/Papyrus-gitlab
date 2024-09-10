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
 * Alexander Shatalin (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package impl.diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import metamodel.MetaModel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType
import xpt.Common
import xpt.diagram.commands.CreateShortcutDecorationsCommand
import xpt.diagram.editparts.Utils_qvto

/**
 * Revisit: [MG]: @Inject extension same-named-api-class -> template extends api-class?
 */
@Singleton class DiagramEditPart {
	@Inject extension Common;
	@Inject extension Utils_qvto;
	@Inject extension MetaModel;
	@Inject extension xpt.CodeStyle;

	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;
	@Inject CreateShortcutDecorationsCommand createShoutrtcutDecorationCommand;

	def className(GenDiagram it) '''«editPartClassName»'''

	def packageName(GenDiagram it) '''«getDiagram().editPartsPackageName»'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view) {
			super(view);
		}
	'''

	def createDefaultEditPoliciesBody(GenDiagram it) '''
		super.createDefaultEditPolicies();

		installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CREATION_ROLE, new org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy());
		installEditPolicy(org.eclipse.papyrus.uml.diagram.common.editpolicies.PasteEditPolicy.PASTE_ROLE, new org.eclipse.papyrus.uml.diagram.common.editpolicies.PasteEditPolicy());

		«xptEditpartsCommon.installSemanticEditPolicy(it)»
		«xptEditpartsCommon.installCanonicalEditPolicy(it)»
		«IF generateCreateShortcutAction() && null === editorGen.application»
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.DRAG_DROP_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy() {
				public org.eclipse.gef.commands.Command getDropObjectsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest dropRequest) {
					java.util.List<org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor> viewDescriptors = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor')»>();
					for (java.util.Iterator<?> it = dropRequest.getObjects().iterator(); it.hasNext();) {
						Object nextObject = it.next();
						if (false == nextObject instanceof org.eclipse.emf.ecore.EObject) {
							continue;
						}
						viewDescriptors.add(new org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor(new org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter((org.eclipse.emf.ecore.EObject) nextObject), org.eclipse.gmf.runtime.notation.Node.class, null, getDiagramPreferencesHint()));
					}
					return createShortcutsCommand(dropRequest, viewDescriptors);
				}

				private org.eclipse.gef.commands.Command createShortcutsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest dropRequest, java.util.List<org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor> viewDescriptors) {
					org.eclipse.gef.commands.Command command = createViewsAndArrangeCommand(dropRequest, viewDescriptors);
					if (command != null) {
						return command.chain(new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(new «createShoutrtcutDecorationCommand.qualifiedClassName(it)»(getEditingDomain(), (org.eclipse.gmf.runtime.notation.View) getModel(), viewDescriptors)));
					}
					return null;
				}
			});
		«ENDIF»
		«IF shouldGenerateDiagramViewmap(it)»
			// diagram figure does layout; need to install child editpolicy to show selection feedback
			installEditPolicy(org.eclipse.gef.EditPolicy.LAYOUT_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {
				protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
					final org.eclipse.gef.editpolicies.NonResizableEditPolicy p = new org.eclipse.gef.editpolicies.NonResizableEditPolicy();
					p.setDragAllowed(false);
					return p;
				}

				protected org.eclipse.gef.commands.Command getMoveChildrenCommand(org.eclipse.gef.Request request) {
					return null;
				}

				protected org.eclipse.gef.commands.Command getCreateCommand(org.eclipse.gef.requests.CreateRequest request) {
					return null;
				}
			});
		«ENDIF»
		«xptEditpartsCommon.behaviour(it)»
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
	'''

	def dragDropEditPolicy(GenDiagram it) '''
		installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.DRAG_DROP_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy() {
			public org.eclipse.gef.commands.Command getDropObjectsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest dropRequest) {
				java.util.ArrayList<org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor> viewDescriptors = new java.util.ArrayList<«diamondOp('org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor')»>();
				for (java.util.Iterator<?> it = dropRequest.getObjects().iterator(); it.hasNext();) {
					Object nextObject = it.next();
					if (false == nextObject instanceof org.eclipse.emf.ecore.EObject) {
						continue;
					}
					viewDescriptors.add(new org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor(new org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter((org.eclipse.emf.ecore.EObject) nextObject), org.eclipse.gmf.runtime.notation.Node.class, null, getDiagramPreferencesHint()));
				}
				return createShortcutsCommand(dropRequest, viewDescriptors);
			}

			private org.eclipse.gef.commands.Command createShortcutsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest dropRequest, java.util.List<org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor> viewDescriptors) {
				org.eclipse.gef.commands.Command command = createViewsAndArrangeCommand(dropRequest, viewDescriptors);
				if (command != null) {
					return command.chain(new org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy(new «createShoutrtcutDecorationCommand.qualifiedClassName(it)»(getEditingDomain(), (org.eclipse.gmf.runtime.notation.View) getModel(), viewDescriptors)));
				}
				return null;
			}
		});
	'''

	def layotEditPolicy(GenDiagram it) '''
		// diagram figure does layout; need to install child editpolicy to show selection feedback
		installEditPolicy(org.eclipse.gef.EditPolicy.LAYOUT_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {
			protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
				final org.eclipse.gef.editpolicies.NonResizableEditPolicy p = new org.eclipse.gef.editpolicies.NonResizableEditPolicy();
				p.setDragAllowed(false);
				return p;
			}
			protected org.eclipse.gef.commands.Command getMoveChildrenCommand(org.eclipse.gef.Request request) {
				return null;
			}
			protected org.eclipse.gef.commands.Command getCreateCommand(org.eclipse.gef.requests.CreateRequest request) {
				return null;
			}
		});
	'''

	def createFigure(GenDiagram it) '''
		«IF shouldGenerateDiagramViewmap(it)»
			«generatedMemberComment»
			protected org.eclipse.draw2d.IFigure createFigure() {
				org.eclipse.draw2d.FreeformLayer l = new org.eclipse.draw2d.FreeformLayer();
				l.setBorder(new org.eclipse.draw2d.MarginBorder(50));
				«initLayout(viewmap.layoutType, 'lm')»
				l.setLayoutManager(lm);
				return l;
			}
		«ENDIF»
	'''

	def initLayout(ViewmapLayoutType it, String varName) '''
		«IF it == ViewmapLayoutType::TOOLBAR_LAYOUT_LITERAL»
			org.eclipse.draw2d.ToolbarLayout «varName» = new org.eclipse.draw2d.ToolbarLayout();
			«varName».setSpacing(10);
		«ELSEIF it == ViewmapLayoutType::FLOW_LAYOUT_LITERAL»
			org.eclipse.draw2d.FlowLayout «varName» = new org.eclipse.draw2d.FlowLayout();
			«varName».setMajorSpacing(10);
			«varName».setMinorSpacing(10);
		«ELSE»
			org.eclipse.draw2d.LayoutManager «varName» = null; /*FIXME - unknown layout type*/
		«ENDIF»
	'''

	/* PapyrusGenCode
	 * computes super type of the link edit part in case the edit part manages a representation of a UML element */
	def extendsListContents(GenDiagram it)'''
		org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusDiagramEditPart
	'''

	def featureForMetaclass (GenNode it)'''
		if (« MetaClass(modelFacet.metaClass)».equals(class1)) {
			return «MetaFeature(modelFacet.containmentMetaFeature)»;
		}
	'''
}
