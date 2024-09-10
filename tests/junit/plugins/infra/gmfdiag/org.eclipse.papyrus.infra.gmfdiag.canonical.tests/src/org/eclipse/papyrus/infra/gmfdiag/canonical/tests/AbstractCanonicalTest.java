/*****************************************************************************
 * Copyright (c) 2015, 2016, 2021 Christian W. Damus and others.
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
 *   Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - bug 569174 - FIX Junit tests
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.canonical.tests;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTopLevelNode;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.TreeIterators;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SetCanonicalCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.DataTypeAttributeCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Before;
import org.junit.Rule;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Futures;

/**
 * Common implementation of canonical test cases.
 */
@SuppressWarnings("deprecation")
public class AbstractCanonicalTest extends AbstractPapyrusTest {
	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final AnnotationRule<Boolean> needUIEvents = AnnotationRule.createExists(NeedsUIEvents.class);

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	// Cache of information from the GMFGen model about what kind of view each visualID is
	private static Map<String, Class<? extends View>> visualIDKinds;

	private ComposedAdapterFactory adapterFactory;

	public AbstractCanonicalTest() {
		super();
	}

	@Before
	public void createAdapterFactory() {
		houseKeeper.setField("adapterFactory", new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}

	protected DiagramEditPart getDiagramEditPart() {
		return editor.getActiveDiagramEditor().getDiagramEditPart();
	}

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

	protected IGraphicalEditPart getClassAttributeCompartment(IGraphicalEditPart class_) {
		return getCompartment(class_, ClassAttributeCompartmentEditPart.VISUAL_ID);
	}

	protected IGraphicalEditPart getClassOperationCompartment(IGraphicalEditPart class_) {
		return getCompartment(class_, ClassOperationCompartmentEditPart.VISUAL_ID);
	}

	protected IGraphicalEditPart getClassNestedClassifierCompartment(IGraphicalEditPart class_) {
		return getCompartment(class_, ClassNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	protected IGraphicalEditPart getDataTypeAttributeCompartment(IGraphicalEditPart dataType) {
		IGraphicalEditPart result = getCompartment(dataType, DataTypeAttributeCompartmentEditPart.VISUAL_ID);
		if (result == null) {
			result = getCompartment(dataType, DataTypeAttributeCompartmentEditPartCN.VISUAL_ID);
		}
		return result;
	}

	protected IGraphicalEditPart getEnumerationLiteralCompartment(IGraphicalEditPart enumeration) {
		return getCompartment(enumeration, EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID);
	}

	protected IGraphicalEditPart getPackageContentsCompartment(IGraphicalEditPart package_) {
		return getCompartment(package_, PackagePackageableElementCompartmentEditPart.VISUAL_ID);
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

	protected void execute(Command command) {
		execute(GEFtoEMFCommandWrapper.wrap(command));
	}

	protected void execute(org.eclipse.emf.common.command.Command command) {
		assertThat("Cannot execute command", command.canExecute(), is(true));
		editor.getEditingDomain().getCommandStack().execute(command);
		waitForUIEvents();
	}

	protected final void waitForUIEvents() {
		// If we're running the tests in the IDE, we should see what's happening. Or, it could be that a
		// particular test actually needs UI events to be processed before proceeding
		if (!JUnitUtils.isAutomatedBuildExecution() || isNeedUIEvents()) {
			editor.flushDisplayEvents();
		}
	}

	protected final boolean isNeedUIEvents() {
		return needUIEvents.get();
	}

	protected void execute(final Runnable writeOperation) {
		execute(new RecordingCommand(editor.getEditingDomain()) {

			@Override
			protected void doExecute() {
				writeOperation.run();
			}
		});
	}

	protected <V> V execute(final Callable<V> writeOperation) {
		final FutureTask<V> result = new FutureTask<>(writeOperation);
		execute(result);
		return Futures.getUnchecked(result);
	}

	protected void undo() {
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		assertThat("Cannot undo", stack.canUndo(), is(true));
		stack.undo();
		waitForUIEvents();
	}

	protected void redo() {
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		assertThat("Cannot redo", stack.canRedo(), is(true));
		stack.redo();
		waitForUIEvents();
	}

	protected void setEditPartsCanonical(boolean canonical, Iterable<? extends EditPart> editParts) {
		final TransactionalEditingDomain domain = editor.getEditingDomain();

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
		final TransactionalEditingDomain domain = editor.getEditingDomain();

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
		execute(AddCommand.create(editor.getEditingDomain(), owner, feature, element));
	}

	/**
	 * Creates a new semantic element in the model with its notation view. So, not a canonical scenario, but intended to regression-test this behaviour
	 * in a canonical context.
	 */
	protected <T extends EObject> T createWithView(EObject owner, EClass metaclass, Class<T> type) {
		IGraphicalEditPart editPart = requireEditPart(owner);
		if (owner instanceof org.eclipse.uml2.uml.Package) {
			// Package edit parts don't implement getTargetEditPart API
			editPart = getPackageContentsCompartment(editPart);
		}

		Command command = findNodeCreationCommand(editPart, owner, metaclass);
		assertThat("No executable command provided to create " + label(metaclass), command, notNullValue());
		execute(command);

		return getNewObject(command, type);
	}

	private Command findNodeCreationCommand(EditPart editPart, EObject owner, EClass metaclass) {
		Command result = null;

		for (IElementType next : getClassDiagramElementTypes(metaclass)) {
			Command command = getNodeCreationCommand(editPart, owner, next);
			if ((command != null) && command.canExecute()) {
				result = command;
				break;
			}
		}

		return result;
	}

	private Command getNodeCreationCommand(EditPart editPart, EObject owner, IElementType elementType) {
		CreateElementRequestAdapter adapter = new CreateElementRequestAdapter(new CreateElementRequest(owner, elementType));
		String hint = (elementType instanceof IHintedType) ? ((IHintedType) elementType).getSemanticHint() : null;
		CreateViewAndElementRequest.ViewAndElementDescriptor descriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(adapter, Node.class, hint, editor.getPreferencesHint());
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(descriptor);

		EditPart targetEditPart = editPart.getTargetEditPart(request);
		return targetEditPart.getCommand(request);
	}

	private List<? extends IElementType> getClassDiagramElementTypes(EClass metaclass) {
		List<IElementType> result = Lists.newArrayListWithExpectedSize(3);
		IElementType base = ElementTypeRegistry.getInstance().getElementType(metaclass, ElementTypeUtils.getDefaultClientContext());

		// Filter for class diagram types matching the exact metaclass (e.g., no Usage for Dependency or Port for Property)
		for (IElementType next : ElementTypeRegistry.getInstance().getSpecializationsOf(base.getId())) {
			if ((next.getEClass() == metaclass) && UMLElementTypes.isKnownElementType(next)) {
				result.add(next);
			}
		}

		return result;
	}

	private <T extends EObject> T getNewObject(Command command, Class<T> type) {
		Iterator<ICommandProxy> proxies = Iterators.filter(leafCommands(command), ICommandProxy.class);

		Object adapter = Iterators.find(Iterators.transform(proxies, new Function<ICommandProxy, Object>() {
			@Override
			public Object apply(ICommandProxy input) {
				CommandResult result = input.getICommand().getCommandResult();
				Object resultValue = (result == null) ? null : result.getReturnValue();
				if (resultValue instanceof Iterable<?>) {
					for (Object next : (Iterable<?>) resultValue) {
						resultValue = AdapterUtils.adapt(next, EObject.class, null);
						if (resultValue != null) {
							break;
						}
					}
				} else {
					resultValue = AdapterUtils.adapt(resultValue, EObject.class, null);
				}
				return resultValue;
			}
		}), Predicates.notNull());

		T result;

		EObject eObject = AdapterUtils.adapt(adapter, EObject.class, null);
		if (eObject instanceof View) {
			result = type.cast(((View) eObject).getElement());
		} else {
			result = type.cast(eObject);
		}

		return result;
	}

	private Iterator<Command> leafCommands(Command command) {
		return new AbstractTreeIterator<>(command, true) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected Iterator<? extends Command> getChildren(Object object) {
				if (object instanceof CompoundCommand) {
					return ((Iterable<? extends Command>) ((CompoundCommand) object).getCommands()).iterator();
				} else {
					return Collections.emptyIterator();
				}
			}
		};
	}

	/**
	 * Adds a new association to the model, which is slightly more complex than owned directed relationships.
	 * Relies on canonical edit policy to create the notation view.
	 */
	protected Dependency addDependency(final NamedElement client, final NamedElement supplier) {
		return execute(new Callable<Dependency>() {

			@Override
			public Dependency call() throws Exception {
				return client.createDependency(supplier);
			}
		});
	}

	/**
	 * Adds a new association to the model, which is more complex than owned directed relationships.
	 * Relies on canonical edit policy to create the notation view.
	 */
	protected Association addAssociation(final Type end1, final Type end2) {
		return execute(new Callable<Association>() {

			@Override
			public Association call() throws Exception {
				String end1Name = end2.getName().substring(0, 1).toLowerCase() + end2.getName().substring(1);
				String end2Name = end1.getName().substring(0, 1).toLowerCase() + end1.getName().substring(1);
				return end1.createAssociation(true, AggregationKind.NONE_LITERAL, end1Name, 0, 1, end2,
						false, AggregationKind.NONE_LITERAL, end2Name, 0, LiteralUnlimitedNatural.UNLIMITED);
			}
		});
	}

	/**
	 * Creates a new dependency in the model with its notation view. So, not a canonical scenario, but intended to regression-test this behaviour
	 * in a canonical context.
	 */
	protected Dependency createDependencyWithView(NamedElement client, NamedElement supplier) {
		EditPart sourceEditPart = requireEditPart(client);
		EditPart targetEditPart = requireEditPart(supplier);

		Command command = findConnectionCreationCommand(sourceEditPart, targetEditPart, UMLPackage.Literals.DEPENDENCY);

		assertThat("No executable command provided to create dependency", command, notNullValue());
		execute(command);

		return getNewObject(command, Dependency.class);
	}

	private Command findConnectionCreationCommand(EditPart sourceEditPart, EditPart targetEditPart, EClass metaclass) {
		Command result = null;

		for (IElementType next : getClassDiagramElementTypes(metaclass)) {
			Command command = getConnectionCreationCommand(sourceEditPart, targetEditPart, next);
			if ((command != null) && command.canExecute()) {
				result = command;
				break;
			}
		}

		return result;
	}

	private Command getConnectionCreationCommand(EditPart sourceEditPart, EditPart targetEditPart, IElementType elementType) {
		Command result = null;

		String hint = (elementType instanceof IHintedType) ? ((IHintedType) elementType).getSemanticHint() : null;
		// Don't attempt to create relationship "nodes" like the DependencyNode or AssociationNode
		if ((hint != null) && isVisualIDKind(hint, Edge.class)) {
			CreateConnectionViewAndElementRequest request = new CreateConnectionViewAndElementRequest(elementType, hint, editor.getPreferencesHint());
			request.setType(RequestConstants.REQ_CONNECTION_START);
			request.setLocation(new Point(0, 0));

			Command command = sourceEditPart.getCommand(request);
			if ((command != null) && command.canExecute()) {
				request.setSourceEditPart(sourceEditPart);
				request.setTargetEditPart(targetEditPart);
				request.setType(RequestConstants.REQ_CONNECTION_END);
				request.setLocation(new Point(0, 0));

				result = targetEditPart.getCommand(request);
			}
		}

		return result;
	}

	/**
	 * Simply removes a semantic {@code element} from the model.
	 * Relies on canonical edit policy to remove the notation view.
	 */
	protected void remove(EObject element) {
		execute(RemoveCommand.create(editor.getEditingDomain(), element.eContainer(), element.eContainmentFeature(), element));
	}

	/**
	 * Uses the edit-service {@linkplain #DestroyElementRequest destroy} command to effect deletion of the semantic {@code element} and all of its views. So, not a canonical scenario, but intended to regression-test this behaviour
	 * in a canonical context.
	 */
	protected void removeWithView(EObject element) {
		IElementEditService service = ElementEditServiceUtils.getCommandProvider(element.eContainer());
		ICommand command = service.getEditCommand(new DestroyElementRequest(element, false));
		assertThat("No command provided to delete " + label(element), command, notNullValue());
		assertThat("Cannot execute command to delete " + label(element), command.canExecute(), is(true));
		execute(command);
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

	protected Edge requireEdge(View oneEnd, View otherEnd) {
		Edge result = getEdge(oneEnd, otherEnd);
		assertThat("No edge between " + label(oneEnd) + " and " + label(otherEnd), result, notNullValue());
		return result;
	}

	protected Edge getEdge(View oneEnd, View otherEnd) {
		Edge result = null;

		for (Edge next : Iterables.filter(oneEnd.getSourceEdges(), Edge.class)) {
			if (next.getTarget() == otherEnd) {
				result = next;
				break;
			}
		}

		if (result == null) {
			// Try the other way around
			for (Edge next : Iterables.filter(oneEnd.getTargetEdges(), Edge.class)) {
				if (next.getSource() == otherEnd) {
					result = next;
					break;
				}
			}
		}

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

	protected static boolean isVisualIDKind(String visualID, Class<? extends View> viewKind) {
		if (visualIDKinds == null) {
			initVisualIDKinds();
		}

		Class<? extends View> actualKind = visualIDKinds.get(visualID);
		return (actualKind != null) && viewKind.isAssignableFrom(actualKind);
	}

	private static void initVisualIDKinds() {
		ResourceSet rset = new ResourceSetImpl();
		final Map<String, Class<? extends View>> map = new HashMap<>();

		try {
			URI classdiagram = URI.createPlatformPluginURI(String.format("/%s/model/classdiagram.gmfgen", UMLDiagramEditorPlugin.ID), true);
			Resource res = rset.getResource(classdiagram, true);
			final TreeIterator<EObject> iter = res.getAllContents();
			while (iter.hasNext()) {
				final EObject base = iter.next();
				if (base instanceof GenCommonBase) {
					Class<? extends View> result = null;
					if (base instanceof GenDiagram) {
						result = Diagram.class;
					} else if (base instanceof GenTopLevelNode) {
						result = Node.class;
					} else if (base instanceof GenChildNode) {
						result = Node.class;
					} else if (base instanceof GenLink) {
						result = Edge.class;
					}
					if (result != null) {
						map.put(((GenCommonBase) base).getVisualIDOverride(), result);
					}
				}

			}
		} finally {
			EMFHelper.unload(rset);
		}

		visualIDKinds = map;
	}

	//
	// Nested types
	//

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	protected @interface NeedsUIEvents {
		// Empty
	}
}
