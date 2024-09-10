/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.diagram.tests.synchronization;

import static org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil.isCanonical;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.TreeIterators;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SetCanonicalCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.DisplayUtils;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Before;
import org.junit.Rule;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Futures;

/**
 * Common implementation of synchronization test cases.
 */
public abstract class AbstractSynchronizationTest extends AbstractPapyrusTestCase {
	@Rule
	public final AnnotationRule<Boolean> needUIEvents = AnnotationRule.createExists(NeedsUIEvents.class);

	private ComposedAdapterFactory adapterFactory;

	public AbstractSynchronizationTest() {
		super();
	}

	@Before
	public void createAdapterFactory() {
		houseKeeper.setField("adapterFactory", new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}

	//
	// Test framework
	//

	@Before
	public void makeDiagramCanonical() {
		if (!isCanonical(getDiagramEditPart())) {
			setCanonical(true, getDiagramEditPart());
		}
	}

	protected void testSynchronizeTopNode(IElementType elementType, String expectedEditPart) {
		EObject element = createSemanticElement(elementType, getRootSemanticModel());

		assertThat("Wrong edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));

		undo();

		assertNoView(element);

		redo();

		assertThat("Wrong edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));
	}

	protected void testSynchronizeChildNode(IElementType parentElementType, IElementType childElementType, String expectedEditPart) {
		EObject parent = createSemanticElement(parentElementType, getRootSemanticModel());

		requireEditPart(parent);

		EObject element = createSemanticElement(childElementType, parent);

		assertThat("Wrong child edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));

		undo();

		assertNoView(element);

		redo();

		assertThat("Wrong child edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));
	}

	protected void testSynchronizeLink(IElementType sourceElementType, IElementType targetElementType, IElementType linkElementType, String expectedEditPart) {
		EObject source = createSemanticElement(sourceElementType, getRootSemanticModel());
		EObject target = createSemanticElement(targetElementType, getRootSemanticModel());

		requireEditPart(source);
		requireEditPart(target);

		EObject element = createSemanticLinkElement(linkElementType, source, target, findOwner(linkElementType, source));

		assertThat("Wrong link edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));

		undo();

		assertNoView(element);

		redo();

		assertThat("Wrong link edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));
	}

	protected EObject findOwner(IElementType elementType, EObject initial) {
		EObject result = null;
		final EClass eclass = elementType.getEClass();

		for (EObject next = initial; (next != null) && (result == null); next = next.eContainer()) {
			if (PackageUtil.canContain(next.eClass(), eclass, false)) {
				result = next;
			}
		}

		return result;
	}

	protected void testSynchronizeLinkInContainer(IElementType containerElementType, IElementType sourceElementType, IElementType targetElementType, IElementType linkElementType, String expectedEditPart) {
		EObject container = createSemanticElement(containerElementType, getRootSemanticModel());
		EObject source = createSemanticElement(sourceElementType, container);
		EObject target = createSemanticElement(targetElementType, container);

		requireEditPart(source);
		requireEditPart(target);

		EObject element = createSemanticLinkElement(linkElementType, source, target, findOwner(linkElementType, source));

		assertThat("Wrong link edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));

		undo();

		assertNoView(element);

		redo();

		assertThat("Wrong link edit part", requireEditPart(element).getNotationView().getType(), is(expectedEditPart));
	}

	protected void testSynchronizeLabelNode(IElementType parentElementType, IElementType childElementType, String expectedCompartment, String expectedEditPart) {
		EObject parent = createSemanticElement(parentElementType, getRootSemanticModel());

		requireEditPart(parent);

		EObject element = createSemanticElement(childElementType, parent);

		IGraphicalEditPart editPart = requireEditPart(element);
		assertThat("Label is in wrong compartment", ((IGraphicalEditPart) editPart.getParent()).getNotationView().getType(), is(expectedCompartment));
		assertThat("Wrong label edit part", editPart.getNotationView().getType(), is(expectedEditPart));

		undo();

		assertNoView(element);

		redo();

		editPart = requireEditPart(element);
		assertThat("Label is in wrong compartment", ((IGraphicalEditPart) editPart.getParent()).getNotationView().getType(), is(expectedCompartment));
		assertThat("Wrong label edit part", editPart.getNotationView().getType(), is(expectedEditPart));
	}

	protected void testSynchronizeLabelNode(IElementType topElementType, IElementType parentElementType, IElementType childElementType, String expectedCompartment, String expectedEditPart) {
		EObject top = createSemanticElement(topElementType, getRootSemanticModel());
		EObject parent = createSemanticElement(parentElementType, top);

		requireEditPart(parent);

		EObject element = createSemanticElement(childElementType, parent);

		IGraphicalEditPart editPart = requireEditPart(element);
		assertThat("Label is in wrong compartment", ((IGraphicalEditPart) editPart.getParent()).getNotationView().getType(), is(expectedCompartment));
		assertThat("Wrong label edit part", editPart.getNotationView().getType(), is(expectedEditPart));

		undo();

		assertNoView(element);

		redo();

		editPart = requireEditPart(element);
		assertThat("Label is in wrong compartment", ((IGraphicalEditPart) editPart.getParent()).getNotationView().getType(), is(expectedCompartment));
		assertThat("Wrong label edit part", editPart.getNotationView().getType(), is(expectedEditPart));
	}

	protected EObject createSemanticElement(IElementType elementType, EObject container) {
		EObject result = null;

		// Don't need to create the root behavior element if the behavior diagram creation already created it for us
		if (UMLPackage.Literals.BEHAVIOR.isSuperTypeOf(elementType.getEClass()) && (container.eContainer() == null)) {
			result = (EObject) EcoreUtil.getObjectByType(container.eContents(), elementType.getEClass());
		}
		// Also, we don't nest behaviors
		if (UMLPackage.Literals.BEHAVIOR.isSuperTypeOf(elementType.getEClass()) && (elementType.getEClass().isInstance(container))) {
			result = container;
		}

		if (result == null) {
			IElementEditService edit = ElementEditServiceUtils.getCommandProvider(container);
			assertThat("Unable to get edit service for " + label(container), edit, notNullValue());

			CreateElementRequest request = new CreateElementRequest(container, elementType);
			ICommand command = edit.getEditCommand(request);
			assertThat("Unable to get creation command for " + elementType, command, notNullValue());
			assertThat("Creation command not executable for " + elementType, command.canExecute(), is(true));

			execute(command);

			result = request.getNewElement();
		}

		return result;
	}

	protected EObject createSemanticLinkElement(IElementType linkElementType, EObject source, EObject target, EObject owner) {
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(owner);
		assertThat("Unable to get edit service for " + label(source), edit, notNullValue());

		CreateRelationshipRequest request = new CreateRelationshipRequest(source, target, linkElementType);
		request.setContainer(owner);
		ICommand command = edit.getEditCommand(request);
		assertThat("Unable to get creation command for " + linkElementType, command, notNullValue());
		assertThat("Creation command not executable for " + linkElementType, command.canExecute(), is(true));

		execute(command);

		return request.getNewElement();
	}

	//
	// Assertions and stuff
	//

	protected TreeIterator<IGraphicalEditPart> allContents(EditPart root, boolean includeRoot) {
		return TreeIterators.filter(DiagramEditPartsUtil.getAllContents(root, includeRoot), IGraphicalEditPart.class);
	}

	protected IGraphicalEditPart getEditPart(EObject element) {
		return getEditPart(element, getDiagramEditPart());
	}

	protected IGraphicalEditPart getEditPart(EObject element, IGraphicalEditPart scope) {
		IGraphicalEditPart result = null;

		for (Iterator<IGraphicalEditPart> iter = allContents(scope, true); iter.hasNext();) {
			IGraphicalEditPart next = iter.next();
			View view = next.getNotationView();
			if ((view != null) && (view.getElement() == element)) {
				result = next;
				break;
			}
		}

		return result;
	}

	protected IGraphicalEditPart requireEditPart(EObject element) {
		IGraphicalEditPart result = getEditPart(element, getDiagramEditPart());
		assertThat("No edit part for " + label(element), result, notNullValue());
		return result;
	}

	protected IGraphicalEditPart getConnectionEditPart(EObject element) {
		IGraphicalEditPart result = null;

		for (Iterator<IGraphicalEditPart> iter = Iterators.filter(getDiagramEditPart().getConnections().iterator(), IGraphicalEditPart.class); iter.hasNext();) {
			IGraphicalEditPart next = iter.next();
			View view = next.getNotationView();
			if ((view != null) && (view.getElement() == element)) {
				result = next;
				break;
			}
		}

		return result;
	}

	protected IGraphicalEditPart requireConnectionEditPart(EObject element) {
		IGraphicalEditPart result = getConnectionEditPart(element);
		assertThat("No connection edit part for " + label(element), result, notNullValue());
		return result;
	}

	protected View getView(EObject element, View scope) {
		View result = null;

		for (Iterator<View> iter = Iterators.filter(scope.eAllContents(), View.class); (result == null) && iter.hasNext();) {
			View next = iter.next();
			if (next.getElement() == element) {
				result = next;
			}
		}

		return result;
	}

	protected View requireView(EObject element, View scope) {
		View result = getView(element, scope);
		assertThat("View not found: " + label(element), result, notNullValue());
		return result;
	}

	protected void assertNoView(EObject element, View scope) {
		View view = getView(element, scope);
		assertThat("View exists: " + label(element), view, nullValue());
	}

	protected IGraphicalEditPart getCompartment(IGraphicalEditPart parent, String type) {
		return parent.getChildBySemanticHint(type);
	}

	protected <T extends DirectedRelationship> T getRelationship(NamedElement from, NamedElement to, Class<T> type) {
		T result = null;

		for (T next : Iterables.filter(from.getSourceDirectedRelationships(), type)) {
			if (next.getTargets().contains(to)) {
				result = next;
				break;
			}
		}

		return result;
	}

	protected void execute(ICommand command) {
		execute(GMFtoEMFCommandWrapper.wrap(command));
	}

	protected void execute(org.eclipse.emf.common.command.Command command) {
		assertThat("Cannot execute command", command.canExecute(), is(true));
		getCommandStack().execute(command);
		waitForUIEvents();
	}

	protected final void waitForUIEvents() {
		// If we're running the tests in the IDE, we should see what's happening. Or, it could be that a
		// particular test actually needs UI events to be processed before proceeding
		if (!JUnitUtils.isAutomatedBuildExecution() || isNeedUIEvents()) {
			DisplayUtils.flushEventLoop();
		}
	}

	protected final boolean isNeedUIEvents() {
		return needUIEvents.get();
	}

	protected void execute(final Runnable writeOperation) {
		execute(new RecordingCommand(getEditingDomain()) {

			@Override
			protected void doExecute() {
				writeOperation.run();
			}
		});
	}

	protected <V> V execute(final Callable<V> writeOperation) {
		final FutureTask<V> result = new FutureTask<V>(writeOperation);
		execute(result);
		return Futures.getUnchecked(result);
	}

	@Override
	protected void undo() {
		super.undo();
		waitForUIEvents();
	}

	@Override
	protected void redo() {
		super.redo();
		waitForUIEvents();
	}

	protected void setEditPartsCanonical(boolean canonical, Iterable<? extends EditPart> editParts) {
		final TransactionalEditingDomain domain = getEditingDomain();

		ICommand command = new CompositeTransactionalCommand(domain, "Toggle Synchronize with Model");
		for (EditPart editPart : editParts) {
			if (canonical != isCanonical(editPart)) {
				command = command.compose(new SetCanonicalCommand(domain, (View) editPart.getModel(), canonical));
			} else {
				command = UnexecutableCommand.INSTANCE;
				break;
			}
		}

		command = command.reduce();

		execute(command);
	}

	protected void setCanonical(boolean canonical, EditPart first, EditPart second, EditPart... rest) {
		setEditPartsCanonical(canonical, Lists.asList(first, second, rest));
	}

	protected void setCanonical(boolean canonical, EditPart editPart) {
		final TransactionalEditingDomain domain = getEditingDomain();

		ICommand command = UnexecutableCommand.INSTANCE;
		if (canonical != isCanonical(editPart)) {
			command = new SetCanonicalCommand(domain, (View) editPart.getModel(), canonical);
		}

		execute(command);
	}

	protected void setElementsCanonical(boolean canonical, Iterable<? extends EObject> elements) {
		List<EditPart> editParts = Lists.newArrayList();
		for (EObject next : elements) {
			editParts.add(requireEditPart(next));
		}
		setEditPartsCanonical(canonical, editParts);
	}

	protected void setCanonical(boolean canonical, EObject first, EObject second, EObject... rest) {
		setElementsCanonical(canonical, Lists.asList(first, second, rest));
	}

	protected void setCanonical(boolean canonical, EObject element) {
		setCanonical(canonical, requireEditPart(element));
	}

	/**
	 * Adds a new semantic {@code element} to the model.
	 * Relies on canonical edit policy to create the notation view.
	 */
	protected void add(EObject owner, EObject element, EReference feature) {
		execute(addCommand(owner, element, feature));
	}

	/**
	 * Obtains a command adding a new semantic {@code element} to the model.
	 * Relies on canonical edit policy to create the notation view.
	 */
	protected org.eclipse.emf.common.command.Command addCommand(EObject owner, EObject element, EReference feature) {
		return AddCommand.create(getEditingDomain(), owner, feature, element);
	}

	/**
	 * Simply removes a semantic {@code element} from the model.
	 * Relies on canonical edit policy to remove the notation view.
	 */
	protected void remove(EObject element) {
		execute(removeCommand(element));
	}

	/**
	 * Sets the value of some {@code feature} of an object.
	 */
	protected void set(EObject owner, Object value, EStructuralFeature feature) {
		execute(setCommand(owner, value, feature));
	}

	/**
	 * Obtains a command that sets the value of some {@code feature} of an object.
	 */
	protected org.eclipse.emf.common.command.Command setCommand(EObject owner, Object value, EStructuralFeature feature) {
		return SetCommand.create(getEditingDomain(), owner, feature, value);
	}

	/**
	 * Obtains a command removing a semantic {@code element} from the model.
	 * Relies on canonical edit policy to remove the notation view.
	 */
	protected org.eclipse.emf.common.command.Command removeCommand(EObject element) {
		return RemoveCommand.create(getEditingDomain(), element.eContainer(), element.eContainmentFeature(), element);
	}

	protected void delete(EditPart editPart) {
		Command command = editPart.getCommand(new GroupRequest(RequestConstants.REQ_DELETE));
		assertThat("No view deletion command provided", command, notNullValue());
		assertThat("Cannot execute view deletion command", command.canExecute(), is(true));
		execute(command);
	}

	protected String label(EObject object) {
		String result;

		if (object instanceof ENamedElement) {
			result = ((UMLUtil.getQualifiedName((ENamedElement) object, NamedElement.SEPARATOR)));
		} else {
			IItemLabelProvider labels = (IItemLabelProvider) adapterFactory.adapt(object, IItemLabelProvider.class);
			result = (labels == null) ? String.valueOf(object) : labels.getText(object);
		}

		return result;
	}

	protected View getView(EObject object) {
		IGraphicalEditPart editPart = getEditPart(object);
		if (editPart == null) {
			// Maybe it's an edge
			editPart = getConnectionEditPart(object);
		}
		return (editPart == null) ? null : editPart.getNotationView();
	}

	protected View requireView(EObject object) {
		View result = getView(object);
		assertThat("No view for " + label(object), result, notNullValue());
		return result;
	}

	protected void assertNoView(EObject object) {
		View view = getView(object);
		assertThat("View exists for " + label(object), view, nullValue());
	}

	protected Map<EObject, View> getViews(Iterable<? extends EObject> objects) {
		Map<EObject, View> result = Maps.newHashMap();

		for (EObject object : objects) {
			IGraphicalEditPart editPart = getEditPart(object);
			if (editPart == null) {
				// Maybe it's an edge
				editPart = getConnectionEditPart(object);
			}
			if ((editPart != null) && (editPart.getNotationView() != null)) {
				result.put(object, editPart.getNotationView());
			}
		}

		return result;
	}

	protected Map<EObject, View> getViews(EObject first, EObject second, EObject... rest) {
		return getViews(Lists.asList(first, second, rest));
	}

	protected Map<EObject, View> requireViews(Iterable<? extends EObject> objects) {
		Map<EObject, View> result = Maps.newHashMap();

		for (EObject object : objects) {
			result.put(object, requireView(object));
		}

		return result;
	}

	protected Map<EObject, View> requireViews(EObject first, EObject second, EObject... rest) {
		return requireViews(Lists.asList(first, second, rest));
	}

	protected void assertNoViews(Iterable<? extends EObject> objects) {
		Map<EObject, View> views = getViews(objects);

		if (!views.isEmpty()) {
			fail("View exists for " + label(Iterables.getFirst(views.keySet(), null)));
		}
	}

	protected void assertNoViews(EObject first, EObject second, EObject... rest) {
		assertNoViews(Lists.asList(first, second, rest));
	}

	protected void assertAttached(EObject element) {
		assertThat("Model does not contain " + label(element), element.eResource(), notNullValue());
	}

	protected void assertAttached(Iterable<? extends EObject> elements) {
		for (EObject next : elements) {
			assertAttached(next);
		}
	}

	protected void assertAttached(EObject first, EObject second, EObject... rest) {
		assertAttached(Lists.asList(first, second, rest));
	}

	protected void assertDetached(EObject element) {
		assertThat("Model must not contain " + label(element), element.eResource(), nullValue());
	}

	protected void assertDetached(Iterable<? extends EObject> elements) {
		for (EObject next : elements) {
			assertDetached(next);
		}
	}

	protected void assertDetached(EObject first, EObject second, EObject... rest) {
		assertDetached(Lists.asList(first, second, rest));
	}

	//
	// Nested types
	//

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	public @interface NeedsUIEvents {
		// Empty
	}
}
