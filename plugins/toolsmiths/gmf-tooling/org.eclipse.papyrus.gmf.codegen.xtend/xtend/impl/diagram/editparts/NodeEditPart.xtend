/*****************************************************************************
 * Copyright (c) 2006, 2014, 2021 Borland Software Corporation, Christian W. Damus, CEA LIST, Artal and others.
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
 * Patrick Tessier (CEA) - initial API and implementation
 * Thibault Landre (Atos Origin) - initial API and implementation
 * Vincent Lorenzo (CEA-LIST) - Bug 335987 [General][Enhancement] Show/Hide Connectors Labels and External Nodes Labels
 * Christian W. Damus - bug 451230
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Remove reference to gmfgraph and ModelViewMap
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers + missing nonNLS/@override
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 unsure all default super classes declare overridables
 *****************************************************************************/
package impl.diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.FigureViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildSideAffixedNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTopLevelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.InnerClassViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.ParentAssignedViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.RefreshHook
import org.eclipse.papyrus.gmf.codegen.gmfgen.SnippetViewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType
import utils.EditPartsUtils_qvto
import xpt.CodeStyle
import xpt.Common
import xpt.Common_qvto
import xpt.diagram.ViewmapAttributesUtils_qvto
import xpt.diagram.editparts.EditPartFactory
import xpt.diagram.editparts.Utils_qvto
import xpt.editor.VisualIDRegistry
import xpt.providers.ElementTypes

@Singleton class NodeEditPart {
	@Inject extension CodeStyle;
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension ViewmapAttributesUtils_qvto;
	@Inject extension Utils_qvto;
	@Inject extension xpt.diagram.Utils_qvto;
	@Inject extension VisualIDRegistry
	@Inject extension EditPartsUtils_qvto;
	@Inject xpt.diagram.editparts.Common xptEditpartsCommon;
	@Inject TextAware xptTextAware;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject ElementTypes xptElementTypes;
	@Inject EditPartFactory xptEditPartFactory;

	def className(GenNode it) '''«editPartClassName»'''

	def packageName(GenNode it) '''«getDiagram().editPartsPackageName»'''

	def dispatch extendsListContents(GenNode it) '''
		«IF superEditPart !== null»
			«superEditPart»
		«ELSE»
			org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart
		«ENDIF»
	'''

	def dispatch extendsListContents(GenChildSideAffixedNode it) '''
		«IF superEditPart !== null»
			«superEditPart»
		«ELSE»
			«IF hasBorderItems(it)»
				org.eclipse.papyrus.uml.diagram.common.editparts.BorderedBorderItemEditPart
			«ELSE»
				org.eclipse.papyrus.uml.diagram.common.editparts.AbstractBorderItemEditPart
			«ENDIF»
		«ENDIF»
	'''

	def constructor(GenNode it) '''
		«generatedMemberComment»
		public «className(it)»(org.eclipse.gmf.runtime.notation.View view) {
			super(view);
		}
	'''

	def createDefaultEditPoliciesBody(GenNode it) '''
		«installCreationRolePolicy(it)»
		super.createDefaultEditPolicies();
		«installPrimaryDragEditPolicy(it)»
		«xptEditpartsCommon.installSemanticEditPolicy(it)»
		«installGraphicalNodeEditPolicy(it)»
		«IF !childNodes.empty»
			installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.DRAG_DROP_ROLE, new org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy());
		«ENDIF»
		«xptEditpartsCommon.installCanonicalEditPolicy(it)»
		installEditPolicy(org.eclipse.gef.EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		«xptEditpartsCommon.behaviour(it)»
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	'''

	def installGraphicalNodeEditPolicy(GenNode it) '''
		installEditPolicy(org.eclipse.gef.EditPolicy.GRAPHICAL_NODE_ROLE, new org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultGraphicalNodeEditPolicy());
	'''

	def installCreationRolePolicy(GenNode it) '''
	«IF !childNodes.empty || hasChildrenInListCompartments(it)»
		«xptEditpartsCommon.installCreationEditPolicy(it)»
	«ENDIF»
	'''

	def dispatch installPrimaryDragEditPolicy(GenNode it) ''''''

	def dispatch installPrimaryDragEditPolicy(GenChildSideAffixedNode it) '''
		installEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE, getPrimaryDragEditPolicy());
	'''

	def createLayoutEditPolicy(GenNode it) '''
		«generatedMemberComment»
		protected org.eclipse.gef.editpolicies.LayoutEditPolicy createLayoutEditPolicy() {
			«createLayoutEditPolicyBody»
		}
	'''

	def createLayoutEditPolicyBody(GenNode it) '''
		«IF ViewmapLayoutType::XY_LAYOUT_LITERAL == getLayoutType()»
			«createLayoutEditPolicyBody_XY_LAYOUT(it)»
		«ELSEIF ViewmapLayoutType::TOOLBAR_LAYOUT_LITERAL == getLayoutType()»
			«createLayoutEditPolicyBody_TOOLBAR_LAYOUT(it)»
		«ELSEIF ViewmapLayoutType::FLOW_LAYOUT_LITERAL == getLayoutType()»
			«createLayoutEditPolicyBody_FLOW_LAYOUT(it)»
		«ELSE»
			«createLayoutEditPolicyBody_DEFAULT(it)»
		«ENDIF»
	'''

	def createLayoutEditPolicyBody_XY_LAYOUT(GenNode it) '''
			org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy() {

			«overrideC»
			protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
				«borderItemSelectionEditPolicy(it)»
				org.eclipse.gef.EditPolicy result = super.createChildEditPolicy(child);
				if (result == null) {
					return new org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy();
				}
				return result;
			}
		};
		return lep;
	'''	

	def createLayoutEditPolicyBody_TOOLBAR_LAYOUT(GenNode it) '''
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy() {

			«overrideC»
			protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
				«borderItemSelectionEditPolicy(it)»
				if (child.getEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE) == null) {
					if (child instanceof org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart) {
						return new org.eclipse.papyrus.uml.diagram.common.editpolicies.UMLTextSelectionEditPolicy();
					}
				}
				return super.createChildEditPolicy(child);
			}
		};
		return lep;
	'''

	def createLayoutEditPolicyBody_FLOW_LAYOUT(GenNode it) '''
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.FlowLayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.FlowLayoutEditPolicy() {
			«IF hasBorderItems(it)»
			«overrideC»
			protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
				«borderItemSelectionEditPolicy()»
				return super.createChildEditPolicy(child);
			}
			«ENDIF»

			«overrideC»
			protected org.eclipse.gef.commands.Command createAddCommand(org.eclipse.gef.EditPart child, org.eclipse.gef.EditPart after) {
				return null;
			}

			«overrideC»
			protected org.eclipse.gef.commands.Command createMoveChildCommand(org.eclipse.gef.EditPart child, org.eclipse.gef.EditPart after) {
				return null;
			}

			«overrideC»
			protected org.eclipse.gef.commands.Command getCreateCommand(org.eclipse.gef.requests.CreateRequest request) {
				return null;
			}
		};
		return lep;
	'''

	def createLayoutEditPolicyBody_DEFAULT(GenNode it) '''
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			«overrideC»
			protected org.eclipse.gef.EditPolicy createChildEditPolicy(org.eclipse.gef.EditPart child) {
				«borderItemSelectionEditPolicy(it)»
				org.eclipse.gef.EditPolicy result = child.getEditPolicy(org.eclipse.gef.EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new org.eclipse.gef.editpolicies.NonResizableEditPolicy();
				}
				return result;
			}

			«overrideC»
			protected org.eclipse.gef.commands.Command getMoveChildrenCommand(org.eclipse.gef.Request request) {
				return null;
			}

			«overrideC»
			protected org.eclipse.gef.commands.Command getCreateCommand(org.eclipse.gef.requests.CreateRequest request) {
				return null;
			}
		};
		return lep;
	'''

	def borderItemSelectionEditPolicy(GenNode it) '''
		«IF hasBorderItems(it)»
			org.eclipse.gmf.runtime.notation.View childView = (org.eclipse.gmf.runtime.notation.View) child.getModel();
			String vid = «getVisualIDMethodCall(getDiagram())»(childView);
			if (vid != null) {
				switch (vid) {
					«IF getExternalLabels(it).size > 0»
						«FOR nextLabel : getExternalLabels(it) »
							«caseVisualID(nextLabel)»
						«ENDFOR»
						return «borderItemSelectionEP(it)»;
					«ENDIF»
					«IF getSideAffixedChildren(it).size > 0»
						«FOR nextBorderItem : getSideAffixedChildren(it)»
							«caseVisualID(nextBorderItem)»
						«ENDFOR»«
						/* PapyrusGenCode
						 * The purprose is to add replace GMF edit prolicy by an new editPolicy that allows to resize BorderItem */» 
						return new org.eclipse.papyrus.uml.diagram.common.editpolicies.BorderItemResizableEditPolicy();
					«ENDIF»
				}
			}
		«ENDIF»
	'''

	def borderItemSelectionEP(GenNode it)'''
		new org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy() {

			«overrideC»
			protected java.util.List<?> createSelectionHandles() {
				org.eclipse.gef.handles.MoveHandle mh = new org.eclipse.gef.handles.MoveHandle((org.eclipse.gef.GraphicalEditPart) getHost());
				mh.setBorder(null);
				return java.util.Collections.singletonList(mh);
			}
		}
	'''

	/**
	 * FIXME: 
	 * 1. single generation of createNodeShape(), with inner body filled by polymorphic initPrimaryShape, same as in Lite RT
	 * 2. getPrimaryShape() for SnippetViewmap. Other templates use it regardless of Viewmap kind, perhaps need to add className to SnippetViewmap (with IFigure being default?)
	 * 3. Common (single and shared with Lite RT) condition when to force useLocalConstraints. Lite checks for compartments isEmpty, shouldn't we do the same?
	*/
	def dispatch createNodeShape(Viewmap it, GenNode node) '''
		«ERROR('Unknown viewmap: ' + it + " for node: " + node)»
	'''

	def dispatch createNodeShape(FigureViewmap it, GenNode node) {
		var fqn = if (it.figureQualifiedClassName === null) 'org.eclipse.draw2d.RectangleFigure' else figureQualifiedClassName;
		'''
			«generatedMemberComment»
			«overrideC»
			protected org.eclipse.draw2d.IFigure createNodeShape() {
				return primaryShape = new «fqn»()«forceUseLocalCoordinatesAnonymousClassBody(node)»;
			}

			«getPrimaryShapeMethod(fqn, node)»
		'''
	}

	def dispatch createNodeShape(SnippetViewmap it, GenNode node) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.draw2d.IFigure createNodeShape() {
			return «body»;
		}
	'''

	def dispatch createNodeShape(InnerClassViewmap it, GenNode node) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.draw2d.IFigure createNodeShape() {
			return primaryShape = new «className»()«forceUseLocalCoordinatesAnonymousClassBody(node)»;
		}

		«getPrimaryShapeMethod(className, node)»
	'''

	def forceUseLocalCoordinatesAnonymousClassBody(GenNode node) '''
		«IF !node.childNodes.empty && node.layoutType == ViewmapLayoutType::XY_LAYOUT_LITERAL»
		{
			protected boolean useLocalCoordinates() {
				return true;
			}
		}
		«ENDIF»
	'''

	def getPrimaryShapeMethod(String fqn, GenNode node) '''
		«generatedMemberComment(fqn)»
		«overrideC»
		public «fqn» getPrimaryShape() {
			return («fqn») primaryShape;
		}
	'''

	def addFixedChild(GenNode it) '''
		«generatedMemberComment»
		protected boolean addFixedChild(org.eclipse.gef.EditPart childEditPart) {
			«FOR label:getInnerFixedLabels(it)»«var childViewmap = label.viewmap as ParentAssignedViewmap»
				if (childEditPart instanceof «xptEditPartFactory.getEditPartQualifiedClassName(label)») {
					((«xptEditPartFactory.getEditPartQualifiedClassName(label)») childEditPart).«xptTextAware.labelSetterName(childViewmap)»(getPrimaryShape().«childViewmap.getterName»());
					return true;
				}
			«ENDFOR»
			«FOR compartment : getPinnedCompartments(it)»«var childViewmap = compartment.viewmap as ParentAssignedViewmap»
				if (childEditPart instanceof «compartment.getEditPartQualifiedClassName()») {
					org.eclipse.draw2d.IFigure pane = getPrimaryShape().«childViewmap.getterName»();
					setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way
					pane.add(((«compartment.getEditPartQualifiedClassName()») childEditPart).getFigure());
					return true;
				}	
			«ENDFOR»
			«FOR  child:getSideAffixedChildren(it)»
				«
				/* PapyrusGencode : Adding IF else end in order to take in account the case where a specific locator is added*/
				IF child.locatorClassName !== null»
					«genSpecificLocator(child)»
				«ELSE /*END */»
					if (childEditPart instanceof «child.getEditPartQualifiedClassName()») {
						org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator locator = new org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator(getMainFigure(), org.eclipse.draw2d.PositionConstants.«child.preferredSideName»);
						getBorderedFigure().getBorderItemContainer().add(((«child.getEditPartQualifiedClassName()») childEditPart).getFigure(), locator);
						return true;
					}
				«ENDIF»
			«ENDFOR»
			return false;
		}
	'''

	def removeFixedChild(GenNode it) '''
		«generatedMemberComment»
		protected boolean removeFixedChild(org.eclipse.gef.EditPart childEditPart) {
			«FOR label : getInnerFixedLabels(it)»
				if (childEditPart instanceof «xptEditPartFactory.getEditPartQualifiedClassName(label)») {
					return true;
				}
			«ENDFOR»
			«FOR compartment : getPinnedCompartments(it)»
				«var childViewmap = compartment.viewmap as ParentAssignedViewmap»
				if (childEditPart instanceof «xptEditPartFactory.getEditPartQualifiedClassName(compartment)») {
					org.eclipse.draw2d.IFigure pane = getPrimaryShape().«childViewmap.getterName»();
					pane.remove(((«xptEditPartFactory.getEditPartQualifiedClassName(compartment)») childEditPart).getFigure());
					return true;
				}	
			«ENDFOR»
			«FOR child : getSideAffixedChildren(it)»
				if (childEditPart instanceof «xptEditPartFactory.getEditPartQualifiedClassName(child)») {
					getBorderedFigure().getBorderItemContainer().remove(((«xptEditPartFactory.getEditPartQualifiedClassName(child)») childEditPart).getFigure());
					return true;
				}
			«ENDFOR»
		return false;
		}
	'''

	def addChildVisual(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void addChildVisual(org.eclipse.gef.EditPart childEditPart, int index) {
			if (addFixedChild(childEditPart)) {
				return;
			}
			super.addChildVisual(childEditPart, -1);
		}
	'''

	def removeChildVisual(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void removeChildVisual(org.eclipse.gef.EditPart childEditPart) {
			if (removeFixedChild(childEditPart)){
				return;
			}
			super.removeChildVisual(childEditPart);
		}
	'''

	def getContentPaneFor(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected org.eclipse.draw2d.IFigure getContentPaneFor(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart editPart) {
			«/* it is unclear what we should return for labels here */
			FOR compartment : getPinnedCompartments(it)»
			«var childViewmap = compartment.viewmap as ParentAssignedViewmap»
			if (editPart instanceof «xptEditPartFactory.getEditPartQualifiedClassName(compartment)») {
				return getPrimaryShape().«childViewmap.getterName»();
			}	
			«ENDFOR»
		«IF hasBorderItems(it)»
			if (editPart instanceof org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart) {
				return getBorderedFigure().getBorderItemContainer();
			}
		«ENDIF»
			return getContentPane();
		}
	'''

	def addBorderItem(GenNode it) '''
		«IF getExternalLabels(it).size > 0»
			«generatedMemberComment»
			«overrideC»
			protected void addBorderItem(org.eclipse.draw2d.IFigure borderItemContainer, org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart borderItemEditPart) {
			«IF getExternalLabelsWithoutSpecificLocator(it).size > 0»	
				if («FOR label : getExternalLabelsWithoutSpecificLocator(it) SEPARATOR ' || '»borderItemEditPart instanceof «label.getEditPartQualifiedClassName()»«ENDFOR») {
					org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator locator = new org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator(getMainFigure(), org.eclipse.draw2d.PositionConstants.SOUTH);
					locator.setBorderItemOffset(new org.eclipse.draw2d.geometry.Dimension(-20, -20));
					borderItemContainer.add(borderItemEditPart.getFigure(), locator);
				} else
			«ENDIF»
			«FOR label : getExternalLabelsWithSpecificLocator(it)»
				if (borderItemEditPart instanceof «label.getEditPartQualifiedClassName()») {
					org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator locator = new «getSpecificLocator(label)»(getMainFigure());
					borderItemContainer.add(borderItemEditPart.getFigure(), locator);
				} else
			«ENDFOR»
				{
					super.addBorderItem(borderItemContainer, borderItemEditPart);
				}
			}
		«ENDIF»
	'''

	def createNodePlate(GenNode it) '''
		«generatedMemberComment»
		«
		/*@deprecated
		 *	«IF nodePlateQualifiedName !== null»
		 *		protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure createNodePlate() {
		 *			«nodePlateQualifiedName» result = new «nodePlateQualifiedName»(«IF getDiagram().isPixelMapMode()»«defaultSizeWidth(viewmap, 40)», «defaultSizeHeight(viewmap, 40)»«ELSE»getMapMode().DPtoLP(«defaultSizeWidth(viewmap, 40)»), getMapMode().DPtoLP(«defaultSizeHeight(viewmap, 40)»)«ENDIF»);
		 *			«setupNodePlate»
		 *			return result;
		 *		}
		 *	 *END: BEGIN: PapyrusGenCode
		 *	«ELSE»
		 *	«super.createNodePlate(it)»
		 *	
		 *	By default node edit part are now RoundedRectangleNodePlateFigure */»
		«overrideC»
		protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure createNodePlate() {
			org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure result = new org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure(«IF getDiagram().isPixelMapMode()»«defaultSizeWidth(viewmap, 40)», «defaultSizeHeight(viewmap, 40)»«ELSE»getMapMode().DPtoLP(«defaultSizeWidth(viewmap, 40)»), getMapMode().DPtoLP(«defaultSizeHeight(viewmap, 40)»)«ENDIF»);
		return result;
		}
	'''

	def getPrimaryDragEditPolicy(GenNode it) '''
		«var rc = getResizeConstraints(it.viewmap)»
		«IF null !== primaryDragEditPolicyQualifiedClassName || null !== rc »
			«generatedMemberComment»
			«overrideC»
			public org.eclipse.gef.EditPolicy getPrimaryDragEditPolicy() {
				«IF null !== primaryDragEditPolicyQualifiedClassName»
					return new «primaryDragEditPolicyQualifiedClassName»();
				«ELSE /* rc != null */»
					org.eclipse.gef.EditPolicy result = super.getPrimaryDragEditPolicy();
					if (result instanceof org.eclipse.gef.editpolicies.ResizableEditPolicy) {
						org.eclipse.gef.editpolicies.ResizableEditPolicy ep = (org.eclipse.gef.editpolicies.ResizableEditPolicy) result;
						ep.setResizeDirections(«IF !rc.resizeHandleNames.empty»«FOR name : rc.resizeHandleNames SEPARATOR ' | '»org.eclipse.draw2d.PositionConstants.«name»«ENDFOR»«ELSE»org.eclipse.draw2d.PositionConstants.NONE«ENDIF»);
					}
					return result;
				«ENDIF»
			}
		«ENDIF»
	'''

	def createFigure(GenNode it) '''
		«generatedMemberComment('Creates figure for this edit part.\n' + 
			'\n' + 
			'Body of this method does not depend on settings in generation model\n' + 
			'so you may safely remove <i>generated</i> tag and modify it.')»
		«overrideC»
		protected org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure create«IF hasBorderItems(it)»Main«ELSE»Node«ENDIF»Figure() {
			«IF it instanceof GenChildSideAffixedNode»
				org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure figure = createNodePlate();
				figure.setLayoutManager(new org.eclipse.draw2d.StackLayout());
				org.eclipse.draw2d.IFigure shape = createNodeShape();
				figure.add(shape);
				contentPane = setupContentPane(shape);
				return figure;
			«ELSE»
				return new org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SelectableBorderedNodeFigure(createMainFigureWithSVG());
			«ENDIF»

		}
	'''

	def setupContentPane(GenNode it) '''
		«generatedMemberComment('Default implementation treats passed figure as content pane.\n' + 
			'Respects layout one may have set for generated figure.\n\n' + 
			'@param nodeShape\n' +
			'           instance of generated figure class'
			)»
		«overrideC»
		protected org.eclipse.draw2d.IFigure setupContentPane(org.eclipse.draw2d.IFigure nodeShape) {
			«IF !childNodes.empty || !compartments.empty || labels.exists[l|!l.oclIsKindOf(typeof(GenExternalNodeLabel))]»
				if (nodeShape.getLayoutManager() == null) {
					«IF it.layoutType == ViewmapLayoutType::XY_LAYOUT_LITERAL»
						nodeShape.setLayoutManager(new org.eclipse.draw2d.FreeformLayout() {

							public Object getConstraint(org.eclipse.draw2d.IFigure figure) {
								Object result = constraints.get(figure);
								if (result == null) {
									result = new org.eclipse.draw2d.geometry.Rectangle(0, 0, -1, -1);
								}
								return result;
							}
						});
					«ELSE»
						org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout layout =new org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout();
						layout.setSpacing(«IF diagram.isPixelMapMode()»5«ELSE»getMapMode().DPtoLP(5)«ENDIF»);
						nodeShape.setLayoutManager(layout);
					«ENDIF»
					}
			«ENDIF»
			return nodeShape; // use nodeShape itself as contentPane
		}
	'''

	def getContentPane(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.draw2d.IFigure getContentPane() {
			if (contentPane != null) {
				return contentPane;
			}
			return super.getContentPane();
		}
	'''

	def setForegroundColor(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void setForegroundColor(org.eclipse.swt.graphics.Color color) {
			if (primaryShape != null) {
				primaryShape.setForegroundColor(color);
			}
		}
	'''

	def setBackgroundColor(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void setBackgroundColor(org.eclipse.swt.graphics.Color color) {
			if (primaryShape != null) {
				primaryShape.setBackgroundColor(color);
			}
		}
	'''

	def setLineWidth(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void setLineWidth(int width) {«
			/* if (primaryShape instanceof org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure) {	
			 * ((org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure) primaryShape).setLineWidth(«IF getDiagram().isPixelMapMode()»width«ELSE»getMapMode().DPtoLP(width)«ENDIF»);
			 * }
			 */»
			super.setLineWidth(width);
		}
	'''

	def setLineStyle(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		protected void setLineType(int style) {
			if (primaryShape instanceof org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure) {	
				((org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure) primaryShape).setLineStyle(style);
			}
		}
	'''

	def getPrimaryChildEditPart(GenNode it) '''
		«IF !labels.empty»
			«generatedMemberComment»
			«overrideC»
			public org.eclipse.gef.EditPart getPrimaryChildEditPart() {
				return getChildBySemanticHint(«xptVisualIDRegistry.typeMethodCall(labels.head)»);
			}
		«ENDIF»
	'''

	def handleNotificationEventBody(GenTopLevelNode it) '''
		if (event.getNotifier() == getModel() && org.eclipse.emf.ecore.EcorePackage.eINSTANCE.getEModelElement_EAnnotations().equals(event.getFeature())) {
			handleMajorSemanticChange();
		} else {
			super.handleNotificationEvent(event);
		}
	'''

	def dispatch innerClassDeclaration(Viewmap it) ''''''

	def dispatch innerClassDeclaration(InnerClassViewmap it) '''	«classBody»'''

	def getTargetEditPartMethod(GenNode it) '''
		«generatedMemberComment»
		«overrideC»
		public org.eclipse.gef.EditPart getTargetEditPart(org.eclipse.gef.Request request) {
			if (request instanceof org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest) {
				org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter adapter = ((org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest) request).getViewAndElementDescriptor().getCreateElementRequestAdapter();
				org.eclipse.gmf.runtime.emf.type.core.IElementType type = (org.eclipse.gmf.runtime.emf.type.core.IElementType) adapter.getAdapter(org.eclipse.gmf.runtime.emf.type.core.IElementType.class);
		«FOR compartment : compartments»
			«IF listCompartmentHasChildren(compartment)»
				«FOR childNode : compartment.childNodes»
					if («xptElementTypes.className(it.diagram)».isKindOf(type, «xptElementTypes.accessElementType(childNode)»)) {
						return getChildBySemanticHint(«xptVisualIDRegistry.typeMethodCall(compartment)»);
					}
				«ENDFOR»
			«ENDIF»
		«ENDFOR»
			}
			return super.getTargetEditPart(request);
		}
	'''

	//---------
	//   GMF
	//---------

	//---------
	// PAPYRUS
	//---------

	def genSpecificLocator(GenChildSideAffixedNode it)'''«
		/*@depracated Papyrus Gencode :«locatorComment  */»
		if (childEditPart instanceof «it.getEditPartQualifiedClassName()») {
			org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator locator = new «locatorClassName»(getMainFigure(), org.eclipse.draw2d.PositionConstants.«preferredSideName»);
			getBorderedFigure().getBorderItemContainer().add(((«it.getEditPartQualifiedClassName()») childEditPart).getFigure(), locator);
			return true;
		}
	'''

	/**
	 * CreateGenerator to refresh figure by taking account of event of UML element or graphical element 
	 */
	def specificHandleNotificationEvent (GenNode it) '''
		«IF it.specificNotificationEvent »
			/**
			 * Papyrus codeGen
			 *
			 * @generated
			 **/
			«overrideC»
			protected void handleNotificationEvent(org.eclipse.emf.common.notify.Notification event) {
				«IF it.labels.filter(typeof(GenExternalNodeLabel)).size != 0» 
					/*
					 * when a node have external node labels, the methods refreshChildren() remove the EditPart corresponding to the Label from the EditPart
					 * Registry. After that, we can't reset the visibility to true (using the Show/Hide Label Action)!
					 */
					if(org.eclipse.gmf.runtime.notation.NotationPackage.eINSTANCE.getView_Visible().equals(event.getFeature())) {
						Object notifier = event.getNotifier();
						java.util.List<?> modelChildren = ((org.eclipse.gmf.runtime.notation.View)getModel()).getChildren();
						if (false == notifier instanceof org.eclipse.gmf.runtime.notation.Edge «/*  see Bug 46376 */»&& false == notifier instanceof org.eclipse.gmf.runtime.notation.BasicCompartment) {
							if(modelChildren.contains(event.getNotifier())) {
								return;
							}
						}
					}
				«ENDIF»
				super.handleNotificationEvent(event);
				«IF refreshHook !== null»
					«specificHandleNotificationEventBody(refreshHook)»
				«ENDIF»
			}
		«ENDIF»
	'''

	def specificHandleNotificationEventBody(RefreshHook it) '''
		if (resolveSemanticElement() != null) {
			if(«refreshCondition»){ «nonNLS(refreshCondition)»
				«refreshAction»; «nonNLS(refreshAction)»
				refreshVisuals();
			}
		}
	'''	
	
}
