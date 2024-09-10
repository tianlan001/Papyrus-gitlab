/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.canonical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.AbstractExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.sequence.preferences.CustomDiagramGeneralPreferencePage;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageEnd;
import org.junit.Test;

public class TestSequenceDiagramLink extends TestLink {

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateSequenceDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return ISequenceDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ISequenceDiagramTestsConstants.FILE_NAME;
	}
	
	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageGeneralOrdering() {
		testToManageLink(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.GeneralOrdering_Edge, executionProvider, false);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageMessageSync_4003() {
		testToManageLink(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_SynchEdge, executionProvider, false);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageMessageReply_4005() {
		testToManageLink(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_ReplyEdge, executionProvider, false);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageMessageFound_4009() {
		installEnvironment(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, lifelineProvider);
		source = (GraphicalEditPart)source.getParent().getParent();
		testToCreateALink(UMLElementTypes.Message_FoundEdge, lifelineProvider);

		testDestroy(UMLElementTypes.Message_FoundEdge, lifelineProvider);
		getEMFCommandStack().undo();
		testViewDeletion(UMLElementTypes.Message_FoundEdge, lifelineProvider);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageMessageLost_4008() {
		installEnvironment(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, lifelineProvider);

		target = (GraphicalEditPart)target.getParent().getParent();
		testToCreateALink(UMLElementTypes.Message_LostEdge, lifelineProvider);

		testDestroy(UMLElementTypes.Message_LostEdge, lifelineProvider);
		getEMFCommandStack().undo();
		testViewDeletion(UMLElementTypes.Message_LostEdge, lifelineProvider);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageCommentLink() {
		testToManageLink(UMLElementTypes.Comment_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Comment_AnnotatedElementEdge, lifelineProvider, true);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageConstraintLink() {
		testToManageLink(UMLElementTypes.Constraint_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Constraint_ConstrainedElementEdge, lifelineProvider, true);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageMessageAsync_4004() {
		IPreferenceStore store = UMLDiagramEditorPlugin.getInstance().getPreferenceStore();
		store.setValue(CustomDiagramGeneralPreferencePage.PREF_EXECUTION_SPECIFICATION_ASYNC_MSG, "CHOICE_NONE");
		testToManageLink(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_AsynchEdge, lifelineProvider, true);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageMessageDelete_4007() {
		testToManageLink(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_DeleteEdge, lifelineProvider, true);
	}

	@FailingTest ("To be erased or rewritten to take new architecture into account")
	@Test
	public void testToManageMessageCreate_4006() {
		testToManageLink(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_CreateEdge, lifelineProvider, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target, ILinkTestProvider provider) {
		CreateConnectionViewRequest request = super.createConnectionViewRequest(type, source, target, provider);

		EObject container = getRootEditPart().resolveSemanticElement();
		request.getExtendedData().put(SOURCE_MODEL_CONTAINER, container);
		request.getExtendedData().put(TARGET_MODEL_CONTAINER, container);
		return request;
	}

	private Point getCenter(IFigure f) {
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point c = b.getCenter().getCopy();
		return c;
	}

	protected ILinkTestProvider executionProvider = new ILinkTestProvider() {


		public boolean hasSemanticChild() {
			return TestSequenceDiagramLink.this.lifelineProvider.hasSemanticChild();
		}

		public Point getChildLocation(GraphicalEditPart parentEditPart) {
			return TestSequenceDiagramLink.this.lifelineProvider.getChildLocation(parentEditPart);
		}

		public int getEditPartChildrenSize() {
			return TestSequenceDiagramLink.this.lifelineProvider.getEditPartChildrenSize();
		}

		public Element getDropElement() {
			return null;
		}

		public int getSemanticChildrenSize() {
			return TestSequenceDiagramLink.this.lifelineProvider.getSemanticChildrenSize() - 4; // ignore ActionExecution
		}

		public int getViewChildrenSize() {
			return TestSequenceDiagramLink.this.lifelineProvider.getViewChildrenSize();
		}

		public GraphicalEditPart getParentEditPart() {
			return TestSequenceDiagramLink.this.lifelineProvider.getParentEditPart();
		}

		public GraphicalEditPart getDestroyEditPart() {
			return null;
		}

		public int getEdgesSize() {
			return TestSequenceDiagramLink.this.lifelineProvider.getEdgesSize();
		}

		public Point getConnectionSourceLocation(EditPart source) {
			return TestSequenceDiagramLink.this.lifelineProvider.getConnectionSourceLocation(source);
		}

		public Point getConnectionTargetLocation(EditPart target) {
			return TestSequenceDiagramLink.this.lifelineProvider.getConnectionTargetLocation(target);
		}

		public void setUp() {
			IElementType childNodeType = UMLElementTypes.ActionExecutionSpecification_Shape;
			source = createSubNode(source, childNodeType, getChildLocation(source));
			sourcePlayer = createSubNode(sourcePlayer, childNodeType, getChildLocation(sourcePlayer));

			target = createSubNode(target, childNodeType, getChildLocation(target).translate(0, 50));
			targetPlayer = createSubNode(targetPlayer, childNodeType, getChildLocation(targetPlayer).translate(0, 50));
		}

	};

	protected ILinkTestProvider lifelineProvider = new ILinkTestProvider() {

		public int getEdgesSize() {
			return getDiagramEditPart().getDiagramView().getEdges().size();
		}

		public int getEditPartChildrenSize() {
			return getRootEditPart().getChildren().size();
		}

		public int getSemanticChildrenSize() {
			int count = 0;
			EList<Element> elems = ((Element)getRootEditPart().getNotationView().getElement()).getOwnedElements();
			for(Element f : elems)
				if(!(f instanceof MessageEnd) && !(f instanceof ExecutionOccurrenceSpecification))
					count++;

			return count;
		}

		public int getViewChildrenSize() {
			return getRootEditPart().getNotationView().getChildren().size();
		}

		public Element getDropElement() {
			return null;
		}

		public GraphicalEditPart getParentEditPart() {
			return getRootEditPart();
		}

		public GraphicalEditPart getDestroyEditPart() {
			return null;
		}

		public Point getConnectionSourceLocation(EditPart part) {
			if(part instanceof LifelineEditPart) {
				IFigure f = ((LifelineEditPart)part).getPrimaryShape();
				return getCenter(f);
			}
			if(part instanceof AbstractExecutionSpecificationEditPart || part instanceof InteractionEditPart) { // found message
				return getAbsoluteBounds((IGraphicalEditPart)part).getTopLeft();
			}
			return new Point(0, 0);
		}

		public Point getConnectionTargetLocation(EditPart part) {
			if(part instanceof LifelineEditPart) {
				IFigure f = ((LifelineEditPart)part).getPrimaryShape();
				return getCenter(f);
			}
			if(part instanceof AbstractExecutionSpecificationEditPart || part instanceof InteractionEditPart) {
				Rectangle r = getAbsoluteBounds((IGraphicalEditPart)part);
				return r.getBottom();
			}
			return new Point(0, 0);
		}

		public boolean hasSemanticChild() {
			return true;
		}

		public Point getChildLocation(GraphicalEditPart parentEditPart) {
			IFigure f = ((LifelineEditPart)parentEditPart).getPrimaryShape();
			return getCenter(f).translate(0, 1);
		}

		public void setUp() {
		}
	};
}
