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
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.AbstractExecutionSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.InteractionEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.sequence.preferences.CustomDiagramGeneralPreferencePage;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.ISequenceDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.CreateSequenceDiagramCommand;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.ILinkTestProvider;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.TestLink;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.MessageEnd;
import org.junit.Ignore;
import org.junit.Test;

/**
 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=364828
 * When a message is deleted, its associated send and receive events are not
 * deleted. These events must be deleted because new events are systematically
 * created upon the creation of a new message.
 *
 * It fixes the deletion of the associated send MessageSend and receive
 * MessageRecv events for
 * - Message Sync
 * - Message Async
 * - Message Reply
 * - Message Create
 * - Message Delete
 *
 */
public class TestMessagesDeletion_364828 extends TestLink {

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

	@Override
	public CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target, ILinkTestProvider provider) {
		CreateConnectionViewRequest request = super.createConnectionViewRequest(type, source, target, provider);
		EObject container = getRootEditPart().resolveSemanticElement();
		request.getExtendedData().put(SOURCE_MODEL_CONTAINER, container);
		request.getExtendedData().put(TARGET_MODEL_CONTAINER, container);
		return request;
	}

	public void deleteMessageEvents(IElementType sourceType, IElementType targetType, IElementType linkType, ILinkTestProvider provider) {
		installEnvironment(sourceType, targetType, provider);
		Interaction interaction = (Interaction) getRootEditPart().resolveSemanticElement();

		// create message link
		CreateConnectionViewRequest req = createConnectionViewRequest(linkType, source, target, provider);
		Command command = target.getCommand(req);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		getDiagramCommandStack().execute(command);

		// delete message link
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, 1, provider.getEdgesSize());
		assertEquals(DESTROY_DELETION + INITIALIZATION_TEST, 2, getMessageEndCount(interaction.getFragments()));
		Request deleteViewRequest = new EditCommandRequestWrapper(new DestroyElementRequest(false));
		Command delCommand = ((ConnectionEditPart) source.getSourceConnections().get(0)).getCommand(deleteViewRequest);
		assertNotNull(DESTROY_DELETION + COMMAND_NULL, delCommand);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_IS_CREATED, delCommand != UnexecutableCommand.INSTANCE);
		assertTrue(DESTROY_DELETION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, delCommand.canExecute());
		getEMFCommandStack().execute(new GEFtoEMFCommandWrapper(delCommand));
		assertEquals(DESTROY_DELETION + TEST_THE_EXECUTION, 0, getMessageEndCount(interaction.getFragments()));

		getEMFCommandStack().undo();
		assertEquals(DESTROY_DELETION + TEST_THE_UNDO, 2, getMessageEndCount(interaction.getFragments()));

		getEMFCommandStack().redo();
		assertEquals(DESTROY_DELETION + TEST_THE_REDO, 0, getMessageEndCount(interaction.getFragments()));
	}

	private int getMessageEndCount(EList<InteractionFragment> fragments) {
		int count = 0;
		for (InteractionFragment f : fragments) {
			if (f instanceof MessageEnd) {
				count++;
			}
		}

		return count;
	}

	@Test
	@Ignore
	public void testMessageAsync() {
		IPreferenceStore store = UMLDiagramEditorPlugin.getInstance().getPreferenceStore();
		store.setValue(CustomDiagramGeneralPreferencePage.PREF_EXECUTION_SPECIFICATION_ASYNC_MSG, "CHOICE_NONE");
		deleteMessageEvents(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_AsynchEdge, lifelineProvider);
	}

	@Test
	public void testMessageSync() {
		IPreferenceStore store = UMLDiagramEditorPlugin.getInstance().getPreferenceStore();
		store.setValue(CustomDiagramGeneralPreferencePage.PREF_EXECUTION_SPECIFICATION_SYNC_MSG, "CHOICE_NONE");
		deleteMessageEvents(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_SynchEdge, lifelineProvider);
	}

	@Test
	@Ignore
	public void testMessageReply() {
		deleteMessageEvents(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_ReplyEdge, lifelineProvider);
	}

	@Test
	@Ignore
	public void testMessageDelete() {
		deleteMessageEvents(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_DeleteEdge, lifelineProvider);
	}

	@Test
	@Ignore
	public void testMessageCreate() {
		deleteMessageEvents(UMLElementTypes.Lifeline_Shape, UMLElementTypes.Lifeline_Shape, UMLElementTypes.Message_CreateEdge, lifelineProvider);
	}

	protected ILinkTestProvider executionProvider = new ILinkTestProvider() {


		public boolean hasSemanticChild() {
			return TestMessagesDeletion_364828.this.lifelineProvider.hasSemanticChild();
		}

		public Point getChildLocation(GraphicalEditPart parentEditPart) {
			return TestMessagesDeletion_364828.this.lifelineProvider.getChildLocation(parentEditPart);
		}

		public int getEditPartChildrenSize() {
			return TestMessagesDeletion_364828.this.lifelineProvider.getEditPartChildrenSize();
		}

		public Element getDropElement() {
			return null;
		}

		public int getSemanticChildrenSize() {
			return TestMessagesDeletion_364828.this.lifelineProvider.getSemanticChildrenSize() - 4; // ignore ActionExecution
		}

		public int getViewChildrenSize() {
			return TestMessagesDeletion_364828.this.lifelineProvider.getViewChildrenSize();
		}

		public GraphicalEditPart getParentEditPart() {
			return TestMessagesDeletion_364828.this.lifelineProvider.getParentEditPart();
		}

		public GraphicalEditPart getDestroyEditPart() {
			return null;
		}

		public int getEdgesSize() {
			return TestMessagesDeletion_364828.this.lifelineProvider.getEdgesSize();
		}

		public Point getConnectionSourceLocation(EditPart source) {
			return TestMessagesDeletion_364828.this.lifelineProvider.getConnectionSourceLocation(source);
		}

		public Point getConnectionTargetLocation(EditPart target) {
			return TestMessagesDeletion_364828.this.lifelineProvider.getConnectionTargetLocation(target);
		}

		public void setUp() {
			IElementType childNodeType = UMLElementTypes.ActionExecutionSpecification_Shape;
			source = createSubNode(source, childNodeType, getChildLocation(source));
			sourcePlayer = createSubNode(sourcePlayer, childNodeType, getChildLocation(sourcePlayer));

			target = createSubNode(target, childNodeType, getChildLocation(target).getTranslated(0, 50));
			targetPlayer = createSubNode(targetPlayer, childNodeType, getChildLocation(targetPlayer).getTranslated(0, 50));
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
			EList<Element> elems = ((Element) getRootEditPart().getNotationView().getElement()).getOwnedElements();
			for (Element f : elems) {
				if (!(f instanceof MessageEnd) && !(f instanceof ExecutionOccurrenceSpecification)) {
					count++;
				}
			}

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
			if (part instanceof LifelineEditPart) {
				IFigure f = ((LifelineEditPart) part).getPrimaryShape();
				return getCenter(f);
			}
			if (part instanceof AbstractExecutionSpecificationEditPart || part instanceof InteractionEditPart) { // found message
				return getAbsoluteBounds((IGraphicalEditPart) part).getTopLeft();
			}
			return new Point(0, 0);
		}

		public Point getConnectionTargetLocation(EditPart part) {
			if (part instanceof LifelineEditPart) {
				IFigure f = ((LifelineEditPart) part).getPrimaryShape();
				return getCenter(f);
			}
			if (part instanceof AbstractExecutionSpecificationEditPart || part instanceof InteractionEditPart) {
				Rectangle r = getAbsoluteBounds((IGraphicalEditPart) part);
				return r.getBottom();
			}
			return new Point(0, 0);
		}

		public boolean hasSemanticChild() {
			return true;
		}

		public Point getChildLocation(GraphicalEditPart parentEditPart) {
			IFigure f = ((LifelineEditPart) parentEditPart).getPrimaryShape();
			return getCenter(f).translate(0, 1);
		}

		public void setUp() {
		}
	};

	private Point getCenter(IFigure f) {
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point c = b.getCenter().getCopy();
		return c;
	}
}
