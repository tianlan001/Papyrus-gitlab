package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import static org.junit.Assert.assertEquals;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionActivityPartitionContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConditionalNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConditionalNodeStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ForkNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InterruptibleActivityRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.MergeNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.OpaqueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StructuredActivityNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ValueSpecificationActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityGroup;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.SequenceNode;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class test graphical and semantic part of {@link ActivityGroup} elements: {@link StructuredActivityNode} {@link ConditionalNode} {@link ExpansionRegion} {@link LoopNode} {@link SequenceNode} {@link InterruptibleActivityRegion} {@link ActivityPartition}
 *
 * For each {@link ActivityGroup} elements test:
 * 1) created child {@link ActivityNode} in {@link ActivityGroup} and drag-drop child to {@link Activity} 2) created child {@link ActivityNode} in {@link Activity} and drag-drop child to {@link ActivityGroup} element
 */
public class TestActivityGroup extends AbstractPapyrusTestCase {

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Test
	public void testFromInterruptibleActivityRegionToActivity() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, regionCompartmentEP);

		DragDropSequence dd = new BeetwenInterruptibleRegionAndActivity(regionCompartmentEP, opaqueEP, getActivityCompartmentEditPart());
		dd.doTest();
	}

	@Test
	//
	public void testFromInterruptibleActivityRegionToActivityPartion() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, regionCompartmentEP);

		DragDropSequence dd = new BeetwenActivityPartitionAndInterruptibleActivityRegion(regionCompartmentEP, opaqueEP, partitionCompartmentEP);
		dd.doTest();
	}

	@Test
	public void testFromInterruptibleActivityRegionToStructuredNode() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart structuredNode = createChild(StructuredActivityNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart structuredNodeCompartment = findChildBySemanticHint(structuredNode, StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, regionCompartmentEP);

		DragDropSequence dd = new BeetwenInterruptibleRegionAndContainmentActivityGroup(regionCompartmentEP, opaqueEP, structuredNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromInterruptibleActivityRegionToConditionalNode() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart conditionalNode = createChild(ConditionalNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart conditionalNodeCompartment = findChildBySemanticHint(conditionalNode, ConditionalNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, regionCompartmentEP);

		DragDropSequence dd = new BeetwenInterruptibleRegionAndContainmentActivityGroup(regionCompartmentEP, opaqueEP, conditionalNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromInterruptibleActivityRegionToLoopNode() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart loopNode = createChild(LoopNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart loopNodeCompartment = findChildBySemanticHint(loopNode, LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, regionCompartmentEP);

		DragDropSequence dd = new BeetwenInterruptibleRegionAndContainmentActivityGroup(regionCompartmentEP, opaqueEP, loopNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromInterruptibleActivityRegionToExpansionRegionNode() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart expansionRegionNode = createChild(ExpansionRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart expansionRegionNodeCompartment = findChildBySemanticHint(expansionRegionNode, ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, regionCompartmentEP);

		DragDropSequence dd = new BeetwenInterruptibleRegionAndContainmentActivityGroup(regionCompartmentEP, opaqueEP, expansionRegionNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromInterruptibleActivityRegionToSequenceNode() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart sequenceNode = createChild(SequenceNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart sequenceNodeCompartment = findChildBySemanticHint(sequenceNode, SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, regionCompartmentEP);

		DragDropSequence dd = new BeetwenInterruptibleRegionAndContainmentActivityGroup(regionCompartmentEP, opaqueEP, sequenceNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromActivityToInterruptibleActivityRegion() {
		IGraphicalEditPart regionEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart regionCompartmentEP = findChildBySemanticHint(regionEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, getActivityCompartmentEditPart());

		DragDropSequence dd = new BeetwenInterruptibleRegionAndActivity(getActivityCompartmentEditPart(), opaqueEP, regionCompartmentEP);
		dd.doTest();
	}

	@Test
	public void testFromActivityPartitionToActivity() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenPartitionAndActivity(partitionCompartmentEP, opaqueEP, getActivityCompartmentEditPart());
		dd.doTest();
	}

	@Test
	public void testFromActivityPartitionToInteruptibleActivtyRegion() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart interruptibleEP = createChild(InterruptibleActivityRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart interruptibleCompartmentEP = findChildBySemanticHint(interruptibleEP, InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenActivityPartitionAndInterruptibleActivityRegion(partitionCompartmentEP, opaqueEP, interruptibleCompartmentEP);
		dd.doTest();
	}

	@Test
	public void testFromActivityPartitionToStructuredNode() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart structuredNode = createChild(StructuredActivityNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart structuredNodeCompartment = findChildBySemanticHint(structuredNode, StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenPartitionAndContainmentActivityGroup(partitionCompartmentEP, opaqueEP, structuredNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromActivityPartitionToConditionalNode() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart conditionalNode = createChild(ConditionalNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart conditionalNodeCompartment = findChildBySemanticHint(conditionalNode, ConditionalNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenPartitionAndContainmentActivityGroup(partitionCompartmentEP, opaqueEP, conditionalNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromActivityPartitionToLoopNode() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart loopNode = createChild(LoopNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart loopNodeCompartment = findChildBySemanticHint(loopNode, LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenPartitionAndContainmentActivityGroup(partitionCompartmentEP, opaqueEP, loopNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromActivityPartitionToExpansionRegionNode() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart expansionRegionNode = createChild(ExpansionRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart expansionRegionNodeCompartment = findChildBySemanticHint(expansionRegionNode, ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenPartitionAndContainmentActivityGroup(partitionCompartmentEP, opaqueEP, expansionRegionNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromActivityPartitionToSequenceNode() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart sequenceNode = createChild(SequenceNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart sequenceNodeCompartment = findChildBySemanticHint(sequenceNode, SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenPartitionAndContainmentActivityGroup(partitionCompartmentEP, opaqueEP, sequenceNodeCompartment);
		dd.doTest();
	}

	@Test
	public void BeetwenNonContainmentActivityGroup() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart sequenceNode = createChild(SequenceNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart sequenceNodeCompartment = findChildBySemanticHint(sequenceNode, SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, partitionCompartmentEP);

		DragDropSequence dd = new BeetwenPartitionAndContainmentActivityGroup(partitionCompartmentEP, opaqueEP, sequenceNodeCompartment);
		dd.doTest();
	}

	@Test
	public void testFromActivityToActivityPartition() {
		IGraphicalEditPart partitionEP = createChild(ActivityPartitionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart partitionCompartmentEP = findChildBySemanticHint(partitionEP, ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueEP = createChild(OpaqueActionEditPart.VISUAL_ID, getActivityCompartmentEditPart());

		DragDropSequence dd = new BeetwenPartitionAndActivity(getActivityCompartmentEditPart(), opaqueEP, partitionCompartmentEP);
		dd.doTest();
	}

	@Test
	public void testFromStructuredNodeToActivity() {
		IGraphicalEditPart structuredEP = createChild(StructuredActivityNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart structuredCompartmentEP = findChildBySemanticHint(structuredEP, StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart loopNodeEP = createChild(LoopNodeEditPart.VISUAL_ID, structuredCompartmentEP);

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(structuredCompartmentEP, loopNodeEP, getActivityCompartmentEditPart());

		dd.doTest();
	}

	@Test
	public void testFromActivityToStructuredNode() {
		IGraphicalEditPart structuredEP = createChild(StructuredActivityNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart structuredCompartmentEP = findChildBySemanticHint(structuredEP, StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart decisionNodeEP = createChild(DecisionNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(getActivityCompartmentEditPart(), decisionNodeEP, structuredCompartmentEP);

		dd.doTest();
	}

	@Test
	public void testFromConditionalNodeToActivity() {
		IGraphicalEditPart conditionalEP = createChild(ConditionalNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart conditionalCompartmentEP = findChildBySemanticHint(conditionalEP, ConditionalNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart expansionRegionEP = createChild(ExpansionRegionEditPart.VISUAL_ID, conditionalCompartmentEP);

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(conditionalCompartmentEP, expansionRegionEP, getActivityCompartmentEditPart());

		dd.doTest();
	}

	@Test
	public void testFromActivityToConditionalNode() {
		IGraphicalEditPart conditionalEP = createChild(ConditionalNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart conditionalCompartmentEP = findChildBySemanticHint(conditionalEP, ConditionalNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart forkNodeEP = createChild(ForkNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(getActivityCompartmentEditPart(), forkNodeEP, conditionalCompartmentEP);

		dd.doTest();
	}

	@Test
	public void testFromExpansionRegionToActivity() {
		IGraphicalEditPart expansionRegionEP = createChild(ExpansionRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart expansionRegionCompartmentEP = findChildBySemanticHint(expansionRegionEP, ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart conditionalNodeEP = createChild(ConditionalNodeEditPart.VISUAL_ID, expansionRegionCompartmentEP);

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(expansionRegionCompartmentEP, conditionalNodeEP, getActivityCompartmentEditPart());

		dd.doTest();
	}

	@Test
	public void testFromActivityToExpansionRegion() {
		IGraphicalEditPart expansionRegionEP = createChild(ExpansionRegionEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart expansionRegionCompartmentEP = findChildBySemanticHint(expansionRegionEP, ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart valueSpecifivationActionEP = createChild(ValueSpecificationActionEditPart.VISUAL_ID, getActivityCompartmentEditPart());

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(getActivityCompartmentEditPart(), valueSpecifivationActionEP, expansionRegionCompartmentEP);

		dd.doTest();
	}

	@Test
	public void testFromLoopNodeToActivity() {
		IGraphicalEditPart loopEP = createChild(LoopNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart loopCompartmentEP = findChildBySemanticHint(loopEP, LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart structuredNodeEP = createChild(StructuredActivityNodeEditPart.VISUAL_ID, loopCompartmentEP);

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(loopCompartmentEP, structuredNodeEP, getActivityCompartmentEditPart());

		dd.doTest();
	}

	@Test
	public void testFromActivityToLoop() {
		IGraphicalEditPart loopEP = createChild(LoopNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart loopCompartmentEP = findChildBySemanticHint(loopEP, LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart mergeNodeEP = createChild(MergeNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(getActivityCompartmentEditPart(), mergeNodeEP, loopCompartmentEP);

		dd.doTest();
	}

	@Test
	public void testFromSequenceNodeToActivity() {
		IGraphicalEditPart sequenceNodeEP = createChild(SequenceNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart sequenceCompartmentEP = findChildBySemanticHint(sequenceNodeEP, SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart dataStoreNodeEP = createChild(LoopNodeEditPart.VISUAL_ID, sequenceCompartmentEP);

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(sequenceCompartmentEP, dataStoreNodeEP, getActivityCompartmentEditPart());

		dd.doTest();
	}

	@Test
	public void testFromActivityToSequenceNode() {
		IGraphicalEditPart sequenceNodeEP = createChild(SequenceNodeEditPart.VISUAL_ID, getActivityCompartmentEditPart());
		IGraphicalEditPart sequenceCompartmentEP = findChildBySemanticHint(sequenceNodeEP, SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID);
		IGraphicalEditPart opaqueActionEP = createChild(OpaqueActionEditPart.VISUAL_ID, getActivityCompartmentEditPart());

		DragDropSequence dd = new BeetwenContainmentNodeAndActivity(getActivityCompartmentEditPart(), opaqueActionEP, sequenceCompartmentEP);

		dd.doTest();
	}

	public abstract class DragDropSequence extends Assert {

		private final IGraphicalEditPart myParentEP;

		private final IGraphicalEditPart myChildEP;

		private final IGraphicalEditPart myTargetEP;

		public DragDropSequence(IGraphicalEditPart parentEP, IGraphicalEditPart childEP, IGraphicalEditPart targetEP) {
			assertNotNull(parentEP);
			assertNotNull(childEP);
			assertNotNull(targetEP);

			myParentEP = parentEP;
			myChildEP = childEP;
			myTargetEP = targetEP;

			assertNotEquals(getParentEP(), getChildEP());
			assertNotEquals(getParentEP(), getTargetEP());
			assertNotEquals(getTargetEP(), getChildEP());
		}

		public void checkBeforeDD() {
			checkGraphicalParent(getChildEP(), getParentEP());
			checkSemantic(getChildEP(), getParentEP());
		}

		protected abstract void checkSemantic(IGraphicalEditPart childEP, IGraphicalEditPart currentParent);

		public IGraphicalEditPart doDD() {
			String childVID = UMLVisualIDRegistry.getVisualID(getChildEP().getNotationView());
			Command ddCommand = createChangeBoundCommand(getChildEP(), getTargetEP());
			assertTrue(ddCommand.canExecute());
			executeOnUIThread(ddCommand);
			IGraphicalEditPart ddChild = findChildBySemanticHint(getTargetEP(), childVID);
			return ddChild;
		}

		public void checkAfterDD(IGraphicalEditPart newChildEP) {
			checkGraphicalParent(newChildEP, getTargetEP());
			checkSemantic(newChildEP, getTargetEP());
		}

		public void doTest() {
			checkBeforeDD();
			IGraphicalEditPart ddChild = doDD();
			checkAfterDD(ddChild);
		}

		public void checkSemanticContainer(IGraphicalEditPart childEP, EObject expectedParent) {
			assertEquals(childEP.getPrimaryView().getElement().eContainer(), expectedParent);
		}

		protected ActivityNode getActivityNodeSemantic(IGraphicalEditPart activityNodeEP) {
			EObject activityNode = getSemanticElement(activityNodeEP);
			assertTrue("ActivityNode expected", UMLPackage.eINSTANCE.getActivityNode().isSuperTypeOf(activityNode.eClass()));
			return (ActivityNode) activityNode;
		}

		protected EObject getSemanticElement(IGraphicalEditPart ep) {
			EObject activityNode = ep.resolveSemanticElement();
			assertNotNull("Primary view of " + ep.getNotationView() + " must have EObject element", activityNode);
			return activityNode;
		}

		private Command createChangeBoundCommand(IGraphicalEditPart child, IGraphicalEditPart newParent) {
			CompoundCommand c = new CompoundCommand();
			ChangeBoundsRequest changeBoundsRequest = new ChangeBoundsRequest(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_DRAG);
			changeBoundsRequest.setEditParts(child);
			changeBoundsRequest.setLocation(new Point(15, 15));
			c.add(child.getCommand(changeBoundsRequest));
			getDiagramEditPart().getEditingDomain().getCommandStack().execute(new GEFtoEMFCommandWrapper(c));
			changeBoundsRequest.setType(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_DROP);
			c.add(newParent.getCommand(changeBoundsRequest));
			return c;
		}

		public IGraphicalEditPart getParentEP() {
			return myParentEP;
		}

		public IGraphicalEditPart getChildEP() {
			return myChildEP;
		}

		public IGraphicalEditPart getTargetEP() {
			return myTargetEP;
		}
	}

	public static void checkGraphicalParent(IGraphicalEditPart child, IGraphicalEditPart expectedPArent) {
		assertEquals(child.getParent(), expectedPArent);
	}

	public class BeetwenInterruptibleRegionAndActivity extends DragDropSequence {

		public BeetwenInterruptibleRegionAndActivity(IGraphicalEditPart parentEP, IGraphicalEditPart childEP, IGraphicalEditPart targetEP) {
			super(parentEP, childEP, targetEP);
		}

		@Override
		protected void checkSemantic(IGraphicalEditPart childEP, IGraphicalEditPart parentEP) {
			ActivityNode activityNode = getActivityNodeSemantic(childEP);
			if (parentEP.resolveSemanticElement() instanceof InterruptibleActivityRegion) {
				assertEquals(1, activityNode.getInInterruptibleRegions().size());
			} else {
				assertEquals(0, activityNode.getInInterruptibleRegions().size());
			}
			assertTrue(UMLPackage.eINSTANCE.getActivity().isSuperTypeOf(activityNode.eContainer().eClass()));
		}
	}

	public class BeetwenActivityPartitionAndInterruptibleActivityRegion extends DragDropSequence {

		public BeetwenActivityPartitionAndInterruptibleActivityRegion(IGraphicalEditPart parentEP, IGraphicalEditPart childEP, IGraphicalEditPart targetEP) {
			super(parentEP, childEP, targetEP);
		}

		@Override
		protected void checkSemantic(IGraphicalEditPart childEP, IGraphicalEditPart parentEP) {
			ActivityNode activityNode = getActivityNodeSemantic(childEP);
			EObject parentSemantic = parentEP.resolveSemanticElement();
			if (parentSemantic instanceof InterruptibleActivityRegion) {
				assertEquals(1, activityNode.getInInterruptibleRegions().size());
				assertEquals(0, activityNode.getInPartitions().size());
			} else if (parentSemantic instanceof Activity) {
				assertEquals(0, activityNode.getInInterruptibleRegions().size());
				assertEquals(1, activityNode.getInPartitions().size());
			}
			assertTrue(UMLPackage.eINSTANCE.getActivity().isSuperTypeOf(activityNode.eContainer().eClass()));
		}
	}

	public class BeetwenInterruptibleRegionAndContainmentActivityGroup extends DragDropSequence {

		public BeetwenInterruptibleRegionAndContainmentActivityGroup(IGraphicalEditPart parentEP, IGraphicalEditPart childEP, IGraphicalEditPart targetEP) {
			super(parentEP, childEP, targetEP);
		}

		@Override
		protected void checkSemantic(IGraphicalEditPart childEP, IGraphicalEditPart parentEP) {
			ActivityNode activityNode = getActivityNodeSemantic(childEP);
			if (parentEP.resolveSemanticElement() instanceof InterruptibleActivityRegion) {
				assertEquals(1, activityNode.getInInterruptibleRegions().size());
				assertTrue(UMLPackage.eINSTANCE.getActivity().isSuperTypeOf(activityNode.eContainer().eClass()));
			} else {
				assertEquals(0, activityNode.getInInterruptibleRegions().size());
				assertEquals(parentEP.resolveSemanticElement(), activityNode.eContainer());
			}
		}
	}

	public class BeetwenPartitionAndActivity extends DragDropSequence {

		public BeetwenPartitionAndActivity(IGraphicalEditPart parentEP, IGraphicalEditPart childEP, IGraphicalEditPart targetEP) {
			super(parentEP, childEP, targetEP);
		}

		@Override
		protected void checkSemantic(IGraphicalEditPart childEP, IGraphicalEditPart parentEP) {
			ActivityNode activityNode = getActivityNodeSemantic(childEP);
			if (parentEP.resolveSemanticElement() instanceof ActivityPartition) {
				assertEquals(1, activityNode.getInPartitions().size());
			} else {
				assertEquals(0, activityNode.getInPartitions().size());
			}
			assertTrue(UMLPackage.eINSTANCE.getActivity().isSuperTypeOf(activityNode.eContainer().eClass()));
		}
	}

	public class BeetwenPartitionAndContainmentActivityGroup extends DragDropSequence {

		public BeetwenPartitionAndContainmentActivityGroup(IGraphicalEditPart parentEP, IGraphicalEditPart childEP, IGraphicalEditPart targetEP) {
			super(parentEP, childEP, targetEP);
		}

		@Override
		protected void checkSemantic(IGraphicalEditPart childEP, IGraphicalEditPart parentEP) {
			ActivityNode activityNode = getActivityNodeSemantic(childEP);
			if (parentEP.resolveSemanticElement() instanceof ActivityPartition) {
				assertEquals(1, activityNode.getInPartitions().size());
				assertTrue(UMLPackage.eINSTANCE.getActivity().isSuperTypeOf(activityNode.eContainer().eClass()));
			} else {
				assertEquals(0, activityNode.getInPartitions().size());
				assertEquals(parentEP.resolveSemanticElement(), activityNode.eContainer());
			}
		}
	}

	public class BeetwenContainmentNodeAndActivity extends DragDropSequence {

		public BeetwenContainmentNodeAndActivity(IGraphicalEditPart parentEP, IGraphicalEditPart childEP, IGraphicalEditPart targetEP) {
			super(parentEP, childEP, targetEP);
		}

		@Override
		protected void checkSemantic(IGraphicalEditPart childEP, IGraphicalEditPart currentParentEP) {
			ActivityNode activityNode = getActivityNodeSemantic(childEP);
			EObject currentParent = getSemanticElement(currentParentEP);
			assertEquals(activityNode.eContainer(), currentParent);
		}
	}
}
