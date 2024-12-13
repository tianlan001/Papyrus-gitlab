/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateEdgeTests;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSiriusDiagramTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DEdgeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.GraphicalOwnerUtils;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.rules.SiriusDiagramEditorFixture;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.ContainmentFeatureHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.sirius.diagram.AbsoluteBoundsFilter;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.sequence.description.InstanceRoleMapping;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;

/**
 * Helper for Sequence Diagram tests.
 *
 * @author <a href="mailto:nicolas.peransin@obeo.fr">Nicolas Peransin</a>
 */
@SuppressWarnings("boxing")
public class SequenceTestHelper {

	/**
	 * Use-case for test suite to create nodes.
	 * <p>
	 * Provides the parameters for a test instance.
	 * </p>
	 *
	 * @param type
	 *            class of element to create
	 * @param location
	 *            indicates where the creation should happen
	 * @param createdElement
	 *            number of created elements
	 * @param createdView
	 *            number of created view elements
	 *
	 */
	public record NodeCreation(Class<? extends Element> type, Point location, int newElementNumber, int newViewNumber) {

		@Override
		public final String toString() {
			return getCreationTool();
		}

		/**
		 * Returns the VSM name of the creation tool.
		 *
		 * @return name of tool
		 */
		public String getCreationTool() {
			return CreationToolsIds.getCreationToolId(type);
		}

		/**
		 * Returns name of expected mapping of created element.
		 *
		 * @return name of mapping
		 */
		public String getMappingType() {
			return MappingTypes.getMappingType(type);
		}

		/**
		 * Returns the containment feature of created element.
		 *
		 * @return reference
		 */
		public EReference getContainment() {
			return ContainmentFeatureHelper.getContainmentFeature(type);
		}

		/**
		 * Creates a Creation Checker based this use-case.
		 *
		 * @param elementOwner
		 *            owner of created element
		 * @param viewContainer
		 *            container of created view
		 * @param helper
		 *            helper of test
		 * @return created checker
		 */
		public SemanticAndGraphicalCreationChecker createNodeChecker(SequenceTestHelper helper, EObject elementOwner, EObject viewContainer) {
			Diagram diagram = helper.context.getDiagram();
			DNodeCreationChecker viewChecker = new DNodeCreationChecker(diagram, viewContainer, getMappingType());
			viewChecker.setExpectedCreatedElements(newViewNumber);

			return createChecker(elementOwner, viewChecker);
		}

		private SemanticAndGraphicalCreationChecker createChecker(EObject elementOwner, IGraphicalRepresentationElementCreationChecker viewChecker) {
			SemanticNodeCreationChecker modelChecker = new SemanticNodeCreationChecker(elementOwner, getContainment(), type());
			modelChecker.setExpectedCreatedElements(newElementNumber());
			return new SemanticAndGraphicalCreationChecker(modelChecker, viewChecker);
		}

		public boolean applyTool(SequenceTestHelper helper, String toolId, EObject containerView) {
			SiriusDiagramEditorFixture fixture = helper.context.fixture;
			DDiagram diagram = helper.context.getDDiagram();
			if (location() != null) {
				return fixture.applyNodeCreationToolFromPalette(toolId, diagram, containerView, location, null);
			}
			return fixture.applyContainerCreationTool(toolId, diagram, containerView);
		}

	}

	/**
	 * Enumeration to access node using simple identification.
	 */
	public enum ViewKind {

		/** Lifeline end. */
		lifeline((context, index) -> context.getLifelineView(index)),

		/** Execution end. */
		execution((context, index) -> context.getExecutionView(index));

		final BiFunction<SequenceTestHelper, Integer, EObject> endProvider;

		ViewKind(BiFunction<SequenceTestHelper, Integer, EObject> provider) {
			endProvider = provider;
		}

		public EObject getView(SequenceTestHelper context, int index) {
			return endProvider.apply(context, index);
		}
	}

	private final AbstractSiriusDiagramTests context;
	private final int lifelineSize;

	/**
	 * Constructor.
	 *
	 * @param test
	 *            current context
	 */
	public SequenceTestHelper(AbstractSiriusDiagramTests test, int lineNumber) {
		context = Objects.requireNonNull(test);
		lifelineSize = lineNumber;
		test.setSynchronization(true);
	}

	/**
	 * Constructor where test has 2 lifelines.
	 *
	 * @param context
	 *            current test
	 */
	public SequenceTestHelper(AbstractSiriusDiagramTests context) {
		this(context, 2);
	}


	/**
	 * Returns the root Interaction.
	 *
	 * @return interaction
	 */
	public Interaction getRoot() {
		return (Interaction) context.getModel().getMembers().get(0);
	}


	/**
	 * Returns the view associated with this semantic element in the diagram.
	 *
	 * @param target
	 *            of the view
	 * @return the associated view
	 * @throws AssertionError
	 *             if view do not exists or several view are found
	 */
	public DSemanticDecorator getSingleView(EObject target) {
		return getSingleView(target, (DSemanticDiagram) context.getDDiagram());
	}

	/**
	 * Returns the view associated with this semantic
	 *
	 * @param target
	 *            target of the view
	 * @param container
	 *            container of the view
	 *
	 * @return the associated view
	 * @throws AssertionError
	 *             if view do not exists or several view are found
	 */
	public DSemanticDecorator getSingleView(EObject target, DSemanticDecorator container) {
		assertNotNull(target);

		List<DSemanticDecorator> views = container.eContents().stream()
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.filter(view -> view.getTarget() == target)
				.toList();
		assertEquals(1, views.size());
		return views.get(0);
	}


	/**
	 * Returns the lifeline view using its indexed name.
	 *
	 * @param index
	 *            of lifeline
	 * @return lifeline
	 * @throws AssertionError
	 *             if no found
	 */
	public DNode getLifelineView(int index) {
		AbstractDNode node = getNodeFromContainer("Lifeline" + index, //$NON-NLS-1$
				MappingTypes.getMappingType(Lifeline.class), context.getDDiagram());
		// We only care for lifeline body.
		return node.getOwnedBorderedNodes().get(0);
	}

	/**
	 * Returns the view of an ExecutionSpecification using its indexed name.
	 * <p>
	 * By convention
	 * </p>
	 *
	 * @param index
	 *            of lifeline
	 * @return lifeline
	 * @throws AssertionError
	 *             if not found
	 */
	public AbstractDNode getExecutionView(int index) {
		// There are only lifelineSize lifelines.
		// ExecutionSpecification index matches a modulo value.
		int lineIndex = index % lifelineSize;
		if (lineIndex == 0) {
			lineIndex = lifelineSize;
		}

		DNode owner = getLifelineView(lineIndex);
		return getNodeFromContainer("ExecutionSpecification" + index, //$NON-NLS-1$
				MappingTypes.getMappingType(ExecutionSpecification.class), owner);
	}

	/**
	 * Return a node from a container using it semantic name.
	 *
	 * @param elementName
	 *            name of the element
	 * @param mappingName
	 *            expected mapping name
	 * @param containerView
	 *            container view
	 * @return found node.
	 * @throws AssertionError
	 *             if not found
	 */
	public AbstractDNode getNodeFromContainer(String elementName, String mappingName, EObject containerView) {
		for (EObject children : GraphicalOwnerUtils.getChildren(containerView)) {
			if (children instanceof AbstractDNode node
					&& node.getMapping().getName().equals(mappingName)
					&& node.getSemanticElements().get(0) instanceof NamedElement value
					&& elementName.equals(value.getName())) {
				return node;
			}
		}

		// Not found
		String parentName = containerView.eClass().getName();
		if (containerView instanceof DRepresentation container) {
			parentName = container.getName();
		} else if (containerView instanceof DRepresentationElement container) {
			parentName = container.getName();
		}

		Assert.fail(MessageFormat.format(
				"No ''{0}'' element with mapping ''{1}'' in container ''{2}'' ", //$NON-NLS-1$
				elementName, mappingName, parentName));
		return null;
	}

	/**
	 * Create a point from element at provided vertical position.
	 *
	 * @param node
	 *            view element
	 * @param y
	 *            height in diagram
	 * @return 2D point
	 */
	public Point getPositionFromEnd(EdgeTarget node, int y) {
		if (node.eContainer() instanceof DNode parentNode
				&& parentNode.getMapping() instanceof InstanceRoleMapping) {
			return getTestPosition(parentNode, y);
		}
		return getTestPosition((DDiagramElement) node, y);
	}

	private static Point getTestPosition(DDiagramElement view, int y) {
		AbsoluteBoundsFilter bounds = (AbsoluteBoundsFilter) view.getGraphicalFilters().get(0);
		int xCenter = bounds.getX() + bounds.getWidth() / 2;
		return new Point(xCenter, y);
	}

	/**
	 * Creates a wrapping task to apply a task on the container of an element.
	 *
	 * @param rule
	 *            to apply on the container
	 * @return a derived rule
	 */
	public Consumer<EdgeTarget> onContainer(Consumer<? super EObject> rule) {
		return end -> rule.accept(end.eContainer());
	}

	/**
	 * Creates a verification rule expecting an element is the source of an edge creation.
	 * <p>
	 * Only valid if context is an {@link AbstractCreateEdgeTests}.
	 * </p>
	 *
	 * @return verification rule for {@link DEdgeCreationChecker}
	 */
	public Consumer<EObject> verifyIsSource() {
		return verifyExpectedEnd("source", AbstractCreateEdgeTests::getEdgeSource); //$NON-NLS-1$
	}

	/**
	 * Creates a verification rule expecting an element is the target of an edge creation.
	 * <p>
	 * Only valid if context is an {@link AbstractCreateEdgeTests}.
	 * </p>
	 *
	 * @return verification rule for {@link DEdgeCreationChecker}
	 */
	public Consumer<EObject> verifyIsTarget() {
		return verifyExpectedEnd("target", AbstractCreateEdgeTests::getEdgeTarget); //$NON-NLS-1$
	}

	private Consumer<EObject> verifyExpectedEnd(String endName, Function<AbstractCreateEdgeTests, EObject> endGetter) {
		// Must be AbstractCreateEdgeTests
		AbstractCreateEdgeTests test = (AbstractCreateEdgeTests) context;
		return end -> {
			EObject expectedEnd = endGetter.apply(test);
			Assert.assertEquals("End is not the " + endName, //$NON-NLS-1$
					expectedEnd, end);
		};
	}

}
