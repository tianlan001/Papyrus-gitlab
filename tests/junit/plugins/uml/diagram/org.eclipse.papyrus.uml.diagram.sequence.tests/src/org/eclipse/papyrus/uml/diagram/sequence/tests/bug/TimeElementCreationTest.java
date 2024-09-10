/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus, CEA LIST, and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.matchers.CommandMatchers;
import org.eclipse.papyrus.junit.matchers.DiagramMatchers;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeObservation;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Regression tests for creation of {@link TimeObservation}s and {@link TimeConstraint}s
 * in the sequence diagram editor, especially those tracked under the umbrella of
 * <a href="http://eclip.se/537571">bug 537571</a>.
 *
 * @author Christian W. Damus
 * @see <a href="http://eclip.se/537571">bug 537571</a>
 */
@PluginResource("resource/bugs/bug537571-times.di")
@ActiveDiagram("scenario")
@RunWith(Parameterized.class)
public class TimeElementCreationTest extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	private final What what;

	static {
		// Kick the modeled element types registry that is needed by the tests
		ElementTypeSetConfigurationRegistry.getInstance();
	}

	/**
	 * Initializes me.
	 *
	 * @param what
	 *            what to create
	 */
	public TimeElementCreationTest(What what) {
		super();

		this.what = what;
	}

	@Test
	public void createOnMessageSendOnLifeline() {
		EditPart lifelineEP = editor.findEditPart("foo", Lifeline.class);
		EditPart messageEP = editor.findEditPart("message1", Message.class);
		PointList points = getGeometry(messageEP, PointList.class);

		EditPart timeEP = editor.createShape(lifelineEP, what.getElementType(),
				points.getFirstPoint(), null);
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not a message-end observed", event, instanceOf(MessageEnd.class));
		assertThat("Wrong message-end observed", event.getName(), is("message1-send"));
		what.assertOwner(this, timeElement);

		assertThat("Wrong parent edit-part", timeEP.getParent(), is(lifelineEP));
	}

	@Test
	public void createOnMessageReceiveOnLifeline() {
		EditPart lifelineEP = editor.findEditPart("foo", Lifeline.class);
		EditPart messageEP = editor.findEditPart("reply1", Message.class);
		PointList points = getGeometry(messageEP, PointList.class);

		EditPart timeEP = editor.createShape(lifelineEP, what.getElementType(),
				points.getLastPoint(), null);
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not a message-end observed", event, instanceOf(MessageEnd.class));
		assertThat("Wrong message-end observed", event.getName(), is("reply1-recv"));
		what.assertOwner(this, timeElement);

		assertThat("Wrong parent edit-part", timeEP.getParent(), is(lifelineEP));
	}

	@Test
	public void createOnMessageReceiveOnExecution() {
		EditPart execEP = editor.findEditPart("exec2", ExecutionSpecification.class);
		EditPart messageEP = editor.findEditPart("message4", Message.class);
		Rectangle bounds = getGeometry(execEP, Rectangle.class);
		PointList points = getGeometry(messageEP, PointList.class);

		EditPart timeEP = editor.createShape(execEP, what.getElementType(),
				new Point(bounds.getTop().x(), points.getLastPoint().y()), null);
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not a message-end observed", event, instanceOf(MessageEnd.class));
		assertThat("Wrong message-end observed", event.getName(), is("message4-recv"));
		what.assertOwner(this, timeElement);

		EditPart lifelineEP = editor.findEditPart("foo", Lifeline.class);
		assertThat("Wrong parent edit-part", timeEP.getParent(), is(lifelineEP));
	}

	@Test
	public void createOnDestructionOccurrence() {
		EditPart destructionEP = editor.findEditPart("destroy2", DestructionOccurrenceSpecification.class);
		Rectangle bounds = getGeometry(destructionEP, Rectangle.class);

		EditPart timeEP = editor.createShape(destructionEP, what.getElementType(),
				bounds.getCenter(), null);
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not a destruction occurrence observed", event, instanceOf(DestructionOccurrenceSpecification.class));
		assertThat("Wrong destruction occurrence observed", event.getName(), is("destroy2"));
		what.assertOwner(this, timeElement);

		assertThat("Wrong parent edit-part", timeEP.getParent(), is(destructionEP));
	}

	@Test
	public void createOnExecutionStart() {
		EditPart execEP = editor.findEditPart("exec2", ExecutionSpecification.class);
		Rectangle bounds = getGeometry(execEP, Rectangle.class);

		EditPart timeEP = editor.createShape(execEP, what.getElementType(),
				bounds.getTop().getTranslated(0, 7), null); // Within threshold below the top
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not an execution occurrence observed", event, instanceOf(ExecutionOccurrenceSpecification.class));
		assertThat("Wrong execution occurrence observed", event.getName(), is("exec2-start"));
		what.assertOwner(this, timeElement);

		assertThat("Wrong parent edit-part", timeEP.getParent(), is(execEP));
	}

	@Test
	public void createOnExecutionFinish() {
		EditPart execEP = editor.findEditPart("exec2", ExecutionSpecification.class);
		Rectangle bounds = getGeometry(execEP, Rectangle.class);

		EditPart timeEP = editor.createShape(execEP, what.getElementType(),
				bounds.getBottom().getTranslated(0, -7), null); // Above the bottom
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not an execution occurrence observed", event, instanceOf(ExecutionOccurrenceSpecification.class));
		assertThat("Wrong execution occurrence observed", event.getName(), is("exec2-finish"));
		what.assertOwner(this, timeElement);

		assertThat("Wrong parent edit-part", timeEP.getParent(), is(execEP));
	}

	/**
	 * Test placement of the time element on a lifeline creation where the creation message
	 * is incoming from the left.
	 */
	@Test
	public void createOnLifelineCreationLeftToRightCreateMessage() {
		EditPart lifelineEP = editor.findEditPart("thing", Lifeline.class);
		Point llTop = getGeometry(lifelineEP, Rectangle.class).getTop();

		EditPart timeEP = editor.createShape(lifelineEP, what.getElementType(),
				llTop.getTranslated(0, 7), null);
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not a message-end observed", event, instanceOf(MessageEnd.class));
		assertThat("Wrong message-end observed", event.getName(), is("create1-recv"));
		what.assertOwner(this, timeElement);

		assertThat("Wrong parent edit-part", timeEP.getParent(), is(lifelineEP));

		Rectangle timeBounds = getGeometry(timeEP, Rectangle.class);
		assertThat("Wrong side of lifeline head", timeBounds.getCenter().x(), greaterThan(llTop.x()));
	}

	/**
	 * Test placement of the time element on a lifeline creation where the creation message
	 * is incoming from the right.
	 */
	@Test
	public void createOnLifelineCreationRightToLeftCreateMessage() {
		EditPart lifelineEP = editor.findEditPart("whatsit", Lifeline.class);
		Point llTop = getGeometry(lifelineEP, Rectangle.class).getTop();

		EditPart timeEP = editor.createShape(lifelineEP, what.getElementType(),
				llTop.getTranslated(0, 7), null);
		Element timeElement = what.getSemanticElement(timeEP);

		NamedElement event = what.getEvent(timeElement);
		assertThat("Not a message-end observed", event, instanceOf(MessageEnd.class));
		assertThat("Wrong message-end observed", event.getName(), is("create2-recv"));
		what.assertOwner(this, timeElement);

		assertThat("Wrong parent edit-part", timeEP.getParent(), is(lifelineEP));

		Rectangle timeBounds = getGeometry(timeEP, Rectangle.class);
		assertThat("Wrong side of lifeline head", timeBounds.getCenter().x(), lessThan(llTop.x()));
	}

	/**
	 * Verify that a time observation cannot be created just anywhere on the lifeline
	 * (it applies only to an occurrence specification).
	 */
	@Test
	public void noCreateOnLifeline() {
		IGraphicalEditPart lifelineEP = (IGraphicalEditPart) editor.findEditPart("foo", Lifeline.class);
		EditPart messageEP = editor.findEditPart("message1", Message.class);
		PointList points = getGeometry(messageEP, PointList.class);

		CreateViewRequest request = CreateViewRequestFactory.getCreateShapeRequest(
				what.getElementType(),
				lifelineEP.getDiagramPreferencesHint());

		request.setLocation(points.getFirstPoint().getTranslated(0, -30));
		// Default size

		// The edit-parts depend on the tool setting this
		CreateElementRequest semanticRequest = null;
		if (!request.getViewDescriptors().isEmpty()) {
			semanticRequest = (CreateElementRequest) ((CreateElementRequestAdapter) request.getViewDescriptors().get(0).getElementAdapter()).getAdapter(CreateElementRequest.class);
			if (semanticRequest != null) {
				semanticRequest.setParameter(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION, request.getLocation().getCopy());
			}
		}

		EditPart target = lifelineEP.getTargetEditPart(request);
		assertThat("No target edit part", target, notNullValue());
		Command command = target.getCommand(request);
		assertThat("Should not be able to create", command, not(CommandMatchers.GEF.canExecute()));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Stream.of(What.values()).map(l -> new Object[] { l }).collect(Collectors.toList());
	}

	@Before
	public void maximizeEditor() {
		IEditorPart editor = this.editor.getEditor();
		IWorkbenchPage page = editor.getSite().getPage();
		page.setPartState(page.getReference(editor), IWorkbenchPage.STATE_MAXIMIZED);
		this.editor.flushDisplayEvents();
	}

	/**
	 * Work around the absence of an {@code equals} method in the {@link PointList} class.
	 *
	 * @param geometry
	 *            a geometry to test for equality with an actual observed geometry
	 * @return the geometry matcher
	 */
	static Matcher<Object> equalGeometry(Object geometry) {
		return new CustomTypeSafeMatcher<Object>("equals " + geometry) {
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
	 * @param type
	 *            the type, either {@link Rectangle} or {@link PointList}, of the geometry to get
	 *
	 * @return its geometry, either a {@link Rectangle}, {@link PointList}, or {@code null}
	 *         for elements that have no geometry of their own but would be implied by others (such
	 *         as execution occurrences)
	 *
	 * @throws IllegalArgumentException
	 *             if the geometry {@link type} is not recognized
	 */
	<T extends Translatable> T getGeometry(EObject interactionElement, Class<T> type) {
		T result = null;
		GraphicalEditPart editPart = Optional.ofNullable(editor.findEditPart(interactionElement))
				.filter(GraphicalEditPart.class::isInstance).map(GraphicalEditPart.class::cast)
				.orElse(null);

		// Some things don't have edit-parts, such as execution occurrences
		if (editPart != null) {
			result = getGeometry(editPart, type);
		}

		return result;
	}

	/**
	 * Query the geometry of an edit-part in the diagram.
	 *
	 * @param editPart
	 *            an edit-part
	 * @param type
	 *            the type, either {@link Rectangle} or {@link PointList}, of the geometry to get
	 *
	 * @return its geometry, either a {@link Rectangle}, {@link PointList}, or {@code null}
	 *         for elements that have no geometry of their own but would be implied by others (such
	 *         as execution occurrences)
	 *
	 * @throws IllegalArgumentException
	 *             if the geometry {@link type} is not recognized
	 */
	static <T extends Translatable> T getGeometry(EditPart editPart, Class<T> type) {
		if (!Rectangle.class.isAssignableFrom(type) && !PointList.class.isAssignableFrom(type)) {
			throw new IllegalArgumentException("unrecognized geometry type: " + type.getName());
		}

		Object result = null;

		// Some things don't have edit-parts, such as execution occurrences
		if (editPart instanceof IGraphicalEditPart) {
			IFigure figure = ((IGraphicalEditPart) editPart).getFigure();
			result = (figure instanceof Connection)
					? ((Connection) figure).getPoints().getCopy()
					: figure.getBounds().getCopy();
			figure.getParent().translateToAbsolute((Translatable) result);
		}

		return type.cast(result);
	}

	//
	// Nested types
	//

	enum What {
		OBSERVATION(TimeObservation.class, UMLElementTypes.TimeObservation_Shape, TimeObservation::getEvent), //
		CONSTRAINT(TimeConstraint.class, UMLElementTypes.TimeConstraint_Shape,
				con -> con.getConstrainedElements().stream().filter(NamedElement.class::isInstance)
						.map(NamedElement.class::cast).findFirst().orElse(null));

		private final Class<?> type;
		private final IElementType elementType;
		private final Function<? super Element, ? extends NamedElement> eventFunction;

		@SuppressWarnings("unchecked")
		<T> What(Class<T> type, IElementType elementType, Function<? super T, ? extends NamedElement> eventFunction) {
			this.type = type;
			this.elementType = elementType;
			this.eventFunction = (Function<? super Element, ? extends NamedElement>) eventFunction;
		}

		IElementType getElementType() {
			return elementType;
		}

		Element getSemanticElement(EditPart editPart) {
			assertThat(editPart, DiagramMatchers.semanticThat(instanceOf(type)));
			return (Element) editPart.getAdapter(EObject.class);
		}

		NamedElement getEvent(Element timeElement) {
			return eventFunction.apply(timeElement);
		}

		void assertOwner(TimeElementCreationTest test, Element timeElement) {
			Element expectedOwner;

			switch (this) {
			case CONSTRAINT:
				// The interaction
				expectedOwner = (Element) test.editor.getActiveDiagram().resolveSemanticElement();
				break;
			default:
				expectedOwner = test.editor.getModel();
				break;
			}

			assertThat("Wrong owner", timeElement.getOwner(), is(expectedOwner));
		}
	}
}
