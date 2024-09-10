/*****************************************************************************
 * Copyright (c) 2018-2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 547864 (Remove useless tests)
 *   Christian W. Damus - bug 570716
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import static org.eclipse.papyrus.junit.matchers.DiagramMatchers.hasErrorDecorationThat;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThanOrEqual;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture.at;
import static org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture.sized;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IValidationListener;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.ocl.pivot.utilities.ThreadLocalExecutor;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.matchers.CommandMatchers;
import org.eclipse.papyrus.junit.matchers.DiagramMatchers;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.PreferenceRule;
import org.eclipse.papyrus.uml.diagram.sequence.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.sequence.preferences.CustomDiagramGeneralPreferencePage;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.util.SequenceUtil;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.hamcrest.CoreMatchers;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

/**
 * Regression tests specifically for {@link CombinedFragment}s in the sequence diagram
 * editor, especially those tracked under the umbrella of <a href="http://eclip.se/533670">bug 533670</a>.
 *
 * @author Christian W. Damus
 * @see <a href="http://eclip.se/533670">bug 533670</a>
 */
@ActiveDiagram("sequence")
@SuppressWarnings("nls")
public class CombinedFragmentRegressionTest extends AbstractPapyrusTest {

	// this needs to be a sequenceable rule so that it can be run before the editor
	private final TestRule validationPreference = PreferenceRule.create(
			UMLDiagramEditorPlugin.getInstance().getPreferenceStore(),
			CustomDiagramGeneralPreferencePage.PREF_TRIGGER_ASYNC_VALIDATION,
			true);

	private final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	// Ensure intiialization of the validation preference before the editor
	// because opening of the diagram needs to kick off validation via the
	// initial owner updates computed by activation of the grid management
	// edit policy
	@Rule
	public final RuleChain rules = RuleChain.outerRule(validationPreference)
			.around(editor);

	/**
	 * Initializes me.
	 */
	public CombinedFragmentRegressionTest() {
		super();
	}

	/**
	 * These tests rely on the OCL environment which since OCL 6.14 is maintained on a thread-local basis
	 * and can get broken when some test attempts to attach an environment factory for the thread when
	 * one is already attached. After that, no OCL validation works and tests fail.
	 */
	@After
	public void cleanUp() {
		ThreadLocalExecutor.reset();
	}

	/**
	 * Verify the creation and extent of a default interaction operand in a newly
	 * created combined fragment.
	 */
	@Test
	@PluginResource("resource/bugs/bug533673.di")
	public void defaultInteractionOperand_533673() {
		EditPart interactionEP = editor.findEditPart("DoIt", Interaction.class);
		EditPart interactionCompartment = editor.getShapeCompartment(interactionEP);

		EditPart combinedFragment = editor.createShape(interactionCompartment, UMLElementTypes.CombinedFragment_Shape, at(15, 60), sized(360, 200));

		assertThat(combinedFragment, DiagramMatchers.semanticThat(instanceOf(CombinedFragment.class)));
		CombinedFragment cf = (CombinedFragment) combinedFragment.getAdapter(EObject.class);

		assertThat("No interaction operand", cf.getOperands(), not(isEmpty()));

		InteractionOperand operand = cf.getOperands().get(0);
		EditPart operandEP = editor.requireEditPart(combinedFragment, operand);

		assertThat(operandEP, instanceOf(GraphicalEditPart.class));
		IFigure figure = ((GraphicalEditPart) operandEP).getFigure();

		Consumer<Rectangle> verifyBounds = bounds -> {
			// Account for margins
			assertThat("Width too small", bounds.width, greaterThan(350));
			// Account for some space for the combined fragment operator label
			// and some extra for font size variance across platforms (esp.
			// Linux and Windows, which seem to be bigger than MacOS)
			assertThat("Height too small", bounds.height, greaterThan(160));
		};

		verifyBounds.accept(figure.getBounds());

		editor.undo();

		operandEP = editor.findEditPart(operand);
		assertThat("Operand still present in the diagram", operandEP, nullValue());

		editor.redo();

		operandEP = editor.findEditPart(operand);
		assertThat(operandEP, instanceOf(GraphicalEditPart.class));
		figure = ((GraphicalEditPart) operandEP).getFigure();
		verifyBounds.accept(figure.getBounds());
	}

	/**
	 * Verify that the deletion of an interaction operand does not delete the
	 * interaction fragments that it owns, but transfers them up to the next
	 * fragment container.
	 */
	@Test
	@PluginResource("resource/bugs/bug533682.di")
	public void deleteInteractionOperand() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(0);
		EditPart operandEP = editor.requireEditPart(combinedFragmentEP, operand);

		// We have at least four message ends and one execution specification
		InteractionFragment[] fragments = operand.getFragments().toArray(new InteractionFragment[0]);
		assumeThat("Lost fragments on opening editor", fragments.length, greaterThan(4));
		ExecutionSpecification exec = (ExecutionSpecification) operand.getFragment("exec");
		assumeThat("No execution specification", exec, notNullValue());

		editor.delete(operandEP);

		assertThat("Interaction fragments not retained", interaction.getFragments(), hasItems(fragments));
		assertThat("Execution specification lost from diagram", editor.findEditPart(exec), notNullValue());

		// Cannot test undo/redo because after Undo, the GridManagementEditPolicy
		// erroneously adds the combined fragment to the fragments of the operand
		// that is restored to its operands list, which is a containment cycle
		// that breaks EMF
	}

	/**
	 * Verify that the deletion of a combined fragment does not delete the
	 * interaction fragments that its operand owns, but transfers them up to the next
	 * fragment container.
	 */
	@Test
	@PluginResource("resource/bugs/bug533682.di")
	public void deleteCombinedFragment() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(0);

		// We have at least four message ends and one execution specification
		InteractionFragment[] fragments = operand.getFragments().toArray(new InteractionFragment[0]);
		assumeThat("Lost fragments on opening editor", fragments.length, greaterThan(4));
		ExecutionSpecification exec = (ExecutionSpecification) operand.getFragment("exec");
		assumeThat("No execution specification", exec, notNullValue());

		editor.delete(combinedFragmentEP);

		assertThat("Interaction fragments not retained", interaction.getFragments(), hasItems(fragments));
		assertThat("Execution specification lost from diagram", editor.findEditPart(exec), notNullValue());
	}

	/**
	 * Verify that the deletion of an interaction fragment inside of an operand
	 * is not impeded.
	 */
	@Test
	@PluginResource("resource/bugs/bug533682.di")
	public void deleteInteractionFragmentInOperand() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(0);

		// Find the execution specification
		EditPart execEP = editor.findEditPart("exec", ExecutionSpecification.class);
		ExecutionSpecification exec = (ExecutionSpecification) execEP.getAdapter(EObject.class);

		editor.delete(execEP);

		assertThat("Execution specification not deleted", exec.eResource(), nullValue());
		assertThat("Operand still has the execution specification", operand.getFragments(), not(hasItem(exec)));
		assertThat("Interaction has the execution specification", interaction.getFragments(), not(hasItem(exec)));

		execEP = editor.findEditPart("exec", ExecutionSpecification.class);
		assertThat("Execution specification still presented in diagram", execEP, nullValue());
	}

	/**
	 * Verify that the deletion of the interaction is not impeded.
	 */
	@Test
	@PluginResource("resource/bugs/bug533682.di")
	public void deleteInteraction() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		// Find the execution specification
		EditPart execEP = editor.findEditPart("exec", ExecutionSpecification.class);
		ExecutionSpecification exec = (ExecutionSpecification) execEP.getAdapter(EObject.class);

		EditPart interactionEP = editor.requireEditPart(editor.getActiveDiagram(), interaction);
		editor.delete(interactionEP);

		assertThat("Execution specification not deleted", exec.eResource(), nullValue());
		assertThat("Interaction not deleted", interaction.eResource(), nullValue());
	}

	/**
	 * Verify that the deletion of the only (or last remaining) interaction operand
	 * in a combined fragment results in deletion of the combined fragment, also.
	 *
	 * @see <a href="http://eclip.se/533683">bug 533683</a>
	 */
	@Test
	@PluginResource("resource/bugs/bug533682.di")
	public void deleteOnlyInteractionOperand() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(0);
		EditPart operandEP = editor.requireEditPart(combinedFragmentEP, operand);

		editor.delete(operandEP);

		combinedFragmentEP = editor.findEditPart(cfrag);

		assertThat("Combined fragment not deleted", cfrag.eResource(), nullValue());
		assertThat("Combined fragment still presented in diagram", combinedFragmentEP, nullValue());
	}

	/**
	 * Verify that the deletion of an interaction operand that leaves at least one
	 * remaining in a combined fragment does not result in deletion of the combined fragment.
	 *
	 * @see <a href="http://eclip.se/533683">bug 533683</a>
	 */
	@Test
	@PluginResource("resource/bugs/bug533683.di")
	public void deleteNotOnlyInteractionOperand() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(1);
		InteractionFragment deleteSend = operand.getFragment("delete-send");
		assumeThat("Lost the delete send event on editor open", deleteSend, notNullValue());
		InteractionFragment deleted = operand.getFragment("deleted");
		assumeThat("Lost the deletion occurrence on editor open", deleted, notNullValue());

		EditPart operandEP = editor.requireEditPart(combinedFragmentEP, operand);

		editor.delete(operandEP);

		editor.flushDisplayEvents();

		combinedFragmentEP = editor.findEditPart(cfrag);

		assertThat("Combined fragment was deleted", cfrag.getEnclosingInteraction(), is(interaction));
		assertThat("Combined fragment no longer presented in diagram", combinedFragmentEP, notNullValue());

		InteractionOperand firstOperand = cfrag.getOperands().get(0);
		// The CombinedFragment still covers the same area, so fragments should
		// now be owned by the other operand
		assertThat("Fragments of deleted operand not retained",
				firstOperand.getFragments(), hasItems(deleteSend, deleted));
	}

	/**
	 * Verify that the simultaneous deletion of all interaction operands in a combined
	 * fragment deletes the combined fragment.
	 *
	 * @see <a href="http://eclip.se/533683">bug 533683</a>
	 */
	@Test
	@PluginResource("resource/bugs/bug533683.di")
	public void deleteAllInteractionOperands() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand0 = cfrag.getOperands().get(0);
		InteractionOperand operand = cfrag.getOperands().get(1);
		InteractionFragment deleteSend = operand.getFragment("delete-send");
		assumeThat("Lost the delete send event on editor open", deleteSend, notNullValue());
		InteractionFragment deleted = operand.getFragment("deleted");
		assumeThat("Lost the deletion occurrence on editor open", deleted, notNullValue());

		EditPart operand0EP = editor.requireEditPart(combinedFragmentEP, operand0);
		EditPart operandEP = editor.requireEditPart(combinedFragmentEP, operand);

		editor.delete(operand0EP, operandEP);

		combinedFragmentEP = editor.findEditPart(cfrag);

		assertThat("Combined fragment not deleted", cfrag, isDeleted());
		assertThat("Combined fragment still presented in diagram", combinedFragmentEP, nullValue());

		assertThat("Fragments of deleted operand not retained", interaction.getFragments(), hasItems(deleteSend, deleted));
	}

	/**
	 * Verify the creation of a combined fragment by just dropping the tool on a
	 * lifeline.
	 */
	@Test
	@PluginResource("resource/bugs/bug533673.di")
	public void createCFragOnLifeline_533671() {
		GraphicalEditPart lifelineEP = (GraphicalEditPart) editor.findEditPart("a", Lifeline.class);

		// Null size to just drop the tool
		GraphicalEditPart combinedFragmentEP = editor.createShape(lifelineEP, UMLElementTypes.CombinedFragment_Shape, at(80, 80), null);

		Consumer<GraphicalEditPart> verifyCFrag = cfragEP -> {
			assertThat(cfragEP, DiagramMatchers.semanticThat(instanceOf(CombinedFragment.class)));
			assertThat("Combined fragment is not a peer of the lifeline", cfragEP.getParent(), is(lifelineEP.getParent()));
			Rectangle lifelineBounds = lifelineEP.getFigure().getBounds();
			Rectangle cfragBounds = cfragEP.getFigure().getBounds();
			assertThat("Combined fragment not bounded by the lifeline", lifelineBounds.contains(cfragBounds), is(true));
		};
		verifyCFrag.accept(combinedFragmentEP);

		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);

		editor.undo();

		combinedFragmentEP = (GraphicalEditPart) editor.findEditPart(cfrag);
		assertThat("Combined fragment still present in the diagram", combinedFragmentEP, nullValue());

		editor.redo();

		combinedFragmentEP = (GraphicalEditPart) editor.findEditPart(cfrag);
		verifyCFrag.accept(combinedFragmentEP);
	}

	/**
	 * Verify the creation of a combined fragment by drawing the tool out over
	 * a pair of lifelines.
	 */
	@Test
	@PluginResource("resource/bugs/bug533673.di")
	public void createCFragOverLifelines_533671() {
		GraphicalEditPart aEP = (GraphicalEditPart) editor.findEditPart("a", Lifeline.class);
		GraphicalEditPart bEP = (GraphicalEditPart) editor.findEditPart("b", Lifeline.class);
		GraphicalEditPart interactionCompartment = (GraphicalEditPart) aEP.getParent();

		GraphicalEditPart combinedFragmentEP = editor.createShape(
				interactionCompartment, UMLElementTypes.CombinedFragment_Shape,
				at(25, 80), sized(360, 200));

		Consumer<GraphicalEditPart> verifyCFrag = cfragEP -> {
			assertThat(cfragEP, DiagramMatchers.semanticThat(instanceOf(CombinedFragment.class)));
			assertThat("Combined fragment not contained in interaction compartment", cfragEP.getParent(), is(interactionCompartment));
			Rectangle aBounds = aEP.getFigure().getBounds();
			Rectangle bBounds = bEP.getFigure().getBounds();
			Rectangle cfragBounds = cfragEP.getFigure().getBounds();
			assertThat("Combined fragment does not extend east of lifeline a",
					aBounds.x(), greaterThan(cfragBounds.x()));
			assertThat("Combined fragment does not extend west of lifeline b",
					bBounds.right(), lessThan(cfragBounds.right()));
		};
		verifyCFrag.accept(combinedFragmentEP);

		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);

		editor.undo();

		combinedFragmentEP = (GraphicalEditPart) editor.findEditPart(cfrag);
		assertThat("Combined fragment still present in the diagram", combinedFragmentEP, nullValue());

		editor.redo();

		combinedFragmentEP = (GraphicalEditPart) editor.findEditPart(cfrag);
		verifyCFrag.accept(combinedFragmentEP);
	}

	/**
	 * Verify the creation of an interaction operand by dropping the tool over a lifeline
	 * that is covered by (but in the diagram actually occludes) a combined fragment.
	 */
	@Test
	@PluginResource("resource/bugs/bug533683.di")
	public void createOperandOnLifeline_533672() {
		GraphicalEditPart lifelineEP = (GraphicalEditPart) editor.findEditPart("a", Lifeline.class);
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		assumeThat("Combined fragment not found", combinedFragmentEP, notNullValue());

		GraphicalEditPart interactionOperandEP = editor.createShape(
				lifelineEP, UMLElementTypes.InteractionOperand_Shape,
				at(80, 80), null); // Null rectangle for the default size

		Consumer<GraphicalEditPart> verifyOperand = operandEP -> {
			assertThat(operandEP, DiagramMatchers.semanticThat(instanceOf(InteractionOperand.class)));
			EditPart cfragEP = SequenceUtil.getParentCombinedFragmentPart(operandEP);
			assertThat("No containing combined fragment", cfragEP, notNullValue());
			assertThat("Wrong combined fragment", cfragEP, is(combinedFragmentEP));

			Rectangle lifelineBounds = lifelineEP.getFigure().getBounds();
			Rectangle operandBounds = operandEP.getFigure().getBounds();
			Rectangle intersection = operandBounds.intersect(lifelineBounds);
			assertThat("Interaction operand does not span lifeline",
					intersection.width(), greaterThanOrEqual(lifelineBounds.width()));
		};
		verifyOperand.accept(interactionOperandEP);

		InteractionOperand operand = (InteractionOperand) interactionOperandEP.getAdapter(EObject.class);

		editor.undo();

		interactionOperandEP = (GraphicalEditPart) editor.findEditPart(operand);
		assertThat("Interaction operand still present in the diagram", interactionOperandEP, nullValue());

		editor.redo();

		interactionOperandEP = (GraphicalEditPart) editor.findEditPart(operand);
		verifyOperand.accept(interactionOperandEP);
	}

	/**
	 * Verify the creation of an interaction operand by drawing the tool over two lifelines
	 * that are covered by (but in the diagram actually occlude) a combined fragment.
	 */
	@Test
	@PluginResource("resource/bugs/bug533683.di")
	public void createOperandOverLifelines_533672() {
		GraphicalEditPart aEP = (GraphicalEditPart) editor.findEditPart("a", Lifeline.class);
		GraphicalEditPart bEP = (GraphicalEditPart) editor.findEditPart("b", Lifeline.class);
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		assumeThat("Combined fragment not found", combinedFragmentEP, notNullValue());

		GraphicalEditPart interactionOperandEP = editor.createShape(
				aEP, UMLElementTypes.InteractionOperand_Shape,
				at(80, 80), sized(220, 120)); // Extend to a point within lifeline b

		Consumer<GraphicalEditPart> verifyOperand = operandEP -> {
			assertThat(operandEP, DiagramMatchers.semanticThat(instanceOf(InteractionOperand.class)));
			EditPart cfragEP = SequenceUtil.getParentCombinedFragmentPart(operandEP);
			assertThat("No containing combined fragment", cfragEP, notNullValue());
			assertThat("Wrong combined fragment", cfragEP, is(combinedFragmentEP));

			Rectangle lifelineBounds = aEP.getFigure().getBounds().getUnion(
					bEP.getFigure().getBounds());
			Rectangle operandBounds = operandEP.getFigure().getBounds();
			Rectangle intersection = operandBounds.intersect(lifelineBounds);
			assertThat("Interaction operand does not span lifeline",
					intersection.width(), greaterThanOrEqual(lifelineBounds.width()));
		};
		verifyOperand.accept(interactionOperandEP);

		InteractionOperand operand = (InteractionOperand) interactionOperandEP.getAdapter(EObject.class);

		editor.undo();

		interactionOperandEP = (GraphicalEditPart) editor.findEditPart(operand);
		assertThat("Interaction operand still present in the diagram", interactionOperandEP, nullValue());

		editor.redo();

		interactionOperandEP = (GraphicalEditPart) editor.findEditPart(operand);
		verifyOperand.accept(interactionOperandEP);
	}

	/**
	 * Verify that an interaction operand cannot be created on a lifeline that is not
	 * covered by a combined fragment.
	 */
	@Test
	@PluginResource("resource/bugs/bug533673.di")
	public void noCreateOperandOnLifelineWithoutCFrag_533672() {
		IGraphicalEditPart lifelineEP = (IGraphicalEditPart) editor.findEditPart("a", Lifeline.class);

		CreateViewRequest request = CreateViewRequestFactory.getCreateShapeRequest(
				UMLElementTypes.InteractionOperand_Shape,
				lifelineEP.getDiagramPreferencesHint());

		request.setLocation(at(80, 80));
		// Default size

		EditPart target = lifelineEP.getTargetEditPart(request);
		assertThat("No target edit part", target, notNullValue());
		org.eclipse.gef.commands.Command command = target.getCommand(request);
		assertThat("Should not be able to create", command, not(CommandMatchers.GEF.canExecute()));
	}

	/**
	 * Verify that the deletion of a combined fragment deletes all of the interaction
	 * operands within it (this is different to {@link #deleteCombinedFragment()} which
	 * tests a combined fragment having only one operand).
	 */
	@Test
	@PluginResource("resource/bugs/bug533681.di")
	public void deleteCombinedFragmentMultipleOperands_533681() {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);

		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand alt1 = cfrag.getOperand("alt1");
		InteractionOperand alt2 = cfrag.getOperand("alt2");

		editor.delete(combinedFragmentEP);

		assertThat("Operands not deleted", Arrays.asList(alt1, alt2), everyItem(isDeleted()));
		assertThat("Combined fragment not deleted", cfrag, isDeleted());

		// Key interaction fragments still exist and are shown, and messages
		EditPart requestMessage = editor.findEditPart("request", Message.class);
		assertThat("Message went missing", requestMessage, notNullValue());
		EditPart execSpec = editor.findEditPart("exec", ExecutionSpecification.class);
		assertThat("Execution went missing", execSpec, notNullValue());
		EditPart destruction = editor.findEditPart("deleted", DestructionOccurrenceSpecification.class);
		assertThat("Destruction went missing", destruction, notNullValue());

		// But other contents of the interaction operands are, of course, gone
		List<Element> allInteractionContents = interaction.allOwnedElements();
		assertThat(allInteractionContents, everyItem(not(
				either(CoreMatchers.<Element> instanceOf(Constraint.class))
						.or(instanceOf(Comment.class)))));
	}

	/**
	 * Verify that the guard of an interaction operand cannot be moved.
	 */
	@Test
	@PluginResource("resource/bugs/bug533681.di")
	public void attemptToMoveOperandGuard_533699() {
		operandGuardUnchangeable("move", ep -> {
			Rectangle currentBounds = ep.getFigure().getBounds();
			ChangeBoundsRequest result = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
			Point moveDelta = new Point(5, 5); // Just a nudge
			Point newLoc = currentBounds.getLocation().getTranslated(moveDelta);
			result.setLocation(newLoc); // Within the constraint shape
			result.setMoveDelta(moveDelta);
			result.setConstrainedMove(false);
			result.setSnapToEnabled(false);
			return result;
		});
	}

	private void operandGuardUnchangeable(String action, Function<? super GraphicalEditPart, ? extends Request> requestFunction) {
		EditPart combinedFragmentEP = editor.findEditPart("cfrag", CombinedFragment.class);
		CombinedFragment cfrag = (CombinedFragment) combinedFragmentEP.getAdapter(EObject.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand alt1 = cfrag.getOperand("alt1");
		Constraint alt1Guard = alt1.getGuard();
		InteractionOperand alt2 = cfrag.getOperand("alt2");
		Constraint alt2Guard = alt2.getGuard();

		List<GraphicalEditPart> guardEPs = Stream.of(alt1Guard, alt2Guard)
				.map(g -> editor.requireEditPart(editor.getActiveDiagram(), g))
				.filter(GraphicalEditPart.class::isInstance).map(GraphicalEditPart.class::cast)
				.collect(Collectors.toList());
		assertThat(guardEPs.size(), greaterThanOrEqual(2));

		guardEPs.forEach(ep -> {
			Request request = requestFunction.apply(ep);

			EditPart target = ep.getTargetEditPart(request);
			if (target != null) {
				Command command = target.getCommand(request);
				assertThat("Can " + action + " guard", command, not(CommandMatchers.GEF.canExecute()));
			} // null target EP is a way to disable the request
		});
	}

	/**
	 * Verify that the guard of an interaction operand cannot be resized.
	 */
	@Test
	@PluginResource("resource/bugs/bug533681.di")
	public void attemptToResizeOperandGuard_533699() {
		operandGuardUnchangeable("resize", ep -> {
			Rectangle currentBounds = ep.getFigure().getBounds();
			ChangeBoundsRequest result = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
			result.setLocation(currentBounds.getBottomRight());
			result.setSizeDelta(new Dimension(5, 5)); // Just a tweak
			result.setResizeDirection(PositionConstants.SOUTH_WEST);
			result.setConstrainedResize(false);
			result.setCenteredResize(false);
			result.setSnapToEnabled(false);
			return result;
		});
	}

	/**
	 * Verify the validation of an interaction operand when it is resized.
	 */
	@FailingTest
	@Test
	@PluginResource("resource/bugs/bug533676.di")
	public void validateResizedInteractionOperand_533676() {

		GraphicalEditPart operandEP = (GraphicalEditPart) editor.findEditPart("opt", InteractionOperand.class);
		InteractionOperand operand = (InteractionOperand) operandEP.getAdapter(EObject.class);
		Interaction interaction = (Interaction) operand.eContainer().eContainer();

		editor.resize(operandEP, sized(100, 185));

		EditPart messageEP = editor.requireEditPart(editor.getActiveDiagram(), interaction.getMessage("async"));
		assertThat(messageEP, hasErrorDecorationThat(startsWith("Message crosses")));

		EditPart execEP = editor.requireEditPart(editor.getActiveDiagram(), operand.getFragment("exec2"));
		assertThat(execEP, hasErrorDecorationThat(startsWith("Execution specification crosses")));
	}

	/**
	 * Verify the validation of an interaction operand when it is created.
	 * Note that <em>what</em> is validated depends on how well the new interaction operand
	 * encloses and adopts interaction fragments. We do not test that here as that is an
	 * orthogonal concern to the fact of running validation.
	 */
	@Test
	@PluginResource("resource/bugs/bug533676a.di")
	public void validateCreatedInteractionOperand_533676() {

		EditPart interactionEP = editor.findEditPart("doIt", Interaction.class);
		EditPart interactionCompartment = editor.getShapeCompartment(interactionEP);

		boolean[] validationOccurred = { false };
		// Lots of live validations occur; we don't want those
		IValidationListener listener = event -> validationOccurred[0] |= (event.getEvaluationMode() == EvaluationMode.BATCH);

		ModelValidationService.getInstance().addValidationListener(listener);
		try {
			editor.createShape(interactionCompartment, UMLElementTypes.CombinedFragment_Shape, at(15, 40), sized(360, 200));
		} finally {
			ModelValidationService.getInstance().removeValidationListener(listener);
		}

		assertThat("No batch validation occurred", validationOccurred[0], is(true));
	}

	/**
	 * Verify that the creation of a combined fragment doesn't cause the interaction
	 * fragments that it encloses to move visually.
	 */
	@Test
	@PluginResource("resource/bugs/bug533679.di")
	public void createCFragDoesNotMoveExistingFragments_533679() {
		EditPart interactionEP = editor.findEditPart("DoIt", Interaction.class);
		EditPart interactionCompartment = editor.getShapeCompartment(interactionEP);
		Interaction interaction = interactionEP.getAdapter(Interaction.class);

		// Collect the geometries of existing interaction fragments and messages
		Map<EObject, Object> geometries = interaction.eContents().stream()
				.collect(Collectors.toMap(Function.identity(), this::getGeometry));

		editor.createShape(interactionCompartment, UMLElementTypes.CombinedFragment_Shape,
				at(40, 60), sized(360, 360));

		assumeThat(geometries.size(), greaterThanOrEqual(8));
		geometries.forEach((element, geometry) -> assertThat(getGeometry(element), equalGeometry(geometry)));
	}

	/**
	 * Verify that the creation of a combined fragment causes all enclosed interaction
	 * fragments to be owned by the initial operand.
	 */
	@Test
	@PluginResource("resource/bugs/bug533679.di")
	public void createCFragEnclosedFragments_533679() {
		EditPart interactionEP = editor.findEditPart("DoIt", Interaction.class);
		Interaction interaction = interactionEP.getAdapter(Interaction.class);

		// Collect the interaction fragments that should move to the new operand
		InteractionFragment[] fragments = interaction.getFragments().toArray(new InteractionFragment[0]);

		EditPart cfragEP = editor.createShape(UMLElementTypes.CombinedFragment_Shape,
				at(20, 60), sized(360, 360));

		// All preëxisting interaction fragments are contained now in the operand
		CombinedFragment cfrag = cfragEP.getAdapter(CombinedFragment.class);
		InteractionOperand operand = cfrag.getOperands().get(0);
		assertThat(operand.getFragments(), hasItems(fragments));
	}

	/**
	 * Verify the creation of a combined fragment that covers nothing at all.
	 */
	@Test
	@PluginResource("resource/bugs/bug533675.di")
	public void createCFragInFreeSpace_533675() {
		EditPart interactionEP = editor.findEditPart("DoIt", Interaction.class);
		EditPart interactionCompartment = editor.getShapeCompartment(interactionEP);

		GraphicalEditPart cfragEP = editor.createShape(interactionCompartment,
				UMLElementTypes.CombinedFragment_Shape,
				at(400, 80), sized(80, 80));
		GraphicalEditPart lifelineEP = (GraphicalEditPart) editor.findEditPart("b", Lifeline.class);

		Rectangle cfragBounds = cfragEP.getFigure().getBounds();
		Rectangle lifelineBounds = lifelineEP.getFigure().getBounds();
		Rectangle intersection = cfragBounds.getIntersection(lifelineBounds);

		assertThat("Combined fragment is in the wrong place", intersection,
				is(new Rectangle(0, 0, 0, 0)));
	}

	/**
	 * Verify the creation of a combined fragment that only partially
	 * covers a message.
	 */
	@Test
	@PluginResource("resource/bugs/bug533675.di")
	public void createCFragOverHalfMessage_533675() {
		EditPart interactionEP = editor.findEditPart("DoIt", Interaction.class);
		EditPart interactionCompartment = editor.getShapeCompartment(interactionEP);

		GraphicalEditPart cfragEP = editor.createShape(interactionCompartment,
				UMLElementTypes.CombinedFragment_Shape,
				at(15, 60), sized(140, 140));

		// TODO: Assert that the message crosses an interaction operand boundary
		// when the diagram is able to update the combined fragment ownership in
		// an undo-safe way using proper commands, not reacting post execution
		GraphicalEditPart messageEP = (GraphicalEditPart) editor.findEditPart("create", Message.class);
		// assertThat(messageEP, hasErrorDecorationThat(startsWith("Message crosses")));

		Rectangle cfragBounds = cfragEP.getFigure().getBounds();
		Rectangle messageBounds = messageEP.getFigure().getBounds();
		Rectangle intersection = cfragBounds.getIntersection(messageBounds);
		assertThat("Combined fragment does not overlap message", intersection,
				not(new Rectangle(0, 0, 0, 0)));
	}

	/**
	 * Verify the creation of a combined fragment within a combined fragment.
	 */
	@Test
	@PluginResource("resource/bugs/bug533683.di")
	public void createCFragInCFrag_533675() {
		EditPart topOperandEP = editor.findEditPart("alt1", InteractionOperand.class);
		GraphicalEditPart lifelineEP = (GraphicalEditPart) editor.findEditPart("a", Lifeline.class);

		GraphicalEditPart cfragEP = editor.createShape(topOperandEP,
				UMLElementTypes.CombinedFragment_Shape,
				at(40, 100), sized(140, 160));

		Rectangle cfragBounds = cfragEP.getFigure().getBounds();
		Rectangle lifelineBounds = lifelineEP.getFigure().getBounds();
		Rectangle intersection = cfragBounds.getIntersection(lifelineBounds);

		assertThat("Combined fragment does not span lifeline", intersection.width(),
				is(lifelineBounds.width()));
	}

	//
	// Test framework
	//

	static Matcher<EObject> isDeleted() {
		return new CustomTypeSafeMatcher<>("is deleted") {
			@Override
			protected boolean matchesSafely(EObject item) {
				return item.eResource() == null;
			}
		};
	}

	/**
	 * Work around the absence of an {@code equals} method in the {@link PointList} class.
	 *
	 * @param geometry
	 *            a geometry to test for equality with an actual observed geometry
	 * @return the geometry matcher
	 */
	static Matcher<Object> equalGeometry(Object geometry) {
		return new CustomTypeSafeMatcher<>("equals " + geometry) {
			@Override
			protected boolean matchesSafely(Object item) {

				return ((item instanceof PointList) && (geometry instanceof PointList))
						? Arrays.equals(((PointList) item).toIntArray(),
								((PointList) geometry).toIntArray())
						: Objects.equals(item, geometry);
			}
		};
	}

	/**
	 * Query the geometry of an interaction element in the diagram.
	 *
	 * @param interactionElement
	 *            an interaction element (interaction fragment or message)
	 *
	 * @return its geometry, either a {@link Rectangle}, {@link PointList}, or {@code null}
	 *         for elements that have no geometry of their own but would be implied by others (such
	 *         as execution occurrences)
	 */
	Object getGeometry(EObject interactionElement) {
		Object result = null;
		GraphicalEditPart editPart = Optional.ofNullable(editor.findEditPart(interactionElement))
				.filter(GraphicalEditPart.class::isInstance).map(GraphicalEditPart.class::cast)
				.orElse(null);

		// Some things don't have edit-parts, such as execution occurrences
		if (editPart != null) {
			IFigure figure = editPart.getFigure();
			result = (figure instanceof Connection)
					? ((Connection) figure).getPoints().getCopy()
					: figure.getBounds().getCopy();
		}

		return result;
	}
}
