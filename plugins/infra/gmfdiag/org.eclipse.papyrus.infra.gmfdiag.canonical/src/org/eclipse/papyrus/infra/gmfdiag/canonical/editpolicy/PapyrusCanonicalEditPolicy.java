/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 433206, 420549, 472155, 471954, 477384, 499695
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.editpolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IResizableCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TopGraphicEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.CanonicalStyle;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Sorting;
import org.eclipse.gmf.runtime.notation.SortingStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.util.CommandTreeIterator;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.commands.wrappers.GEFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.canonical.internal.Activator;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.ISemanticChildrenStrategy;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.IVisualChildrenStrategy;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.SemanticChildrenStrategyRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.requests.RollingDeferredArrangeRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.EdgeWithNoSemanticElementRepresentationImpl;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IPapyrusCanonicalEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.service.visualtype.VisualTypeService;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * A specialization of the GMF {@link CanonicalEditPolicy} that alters the standard behaviour in a few key ways:
 * <ul>
 * <li>rather than using the View Service to create child views, delegate to the core drag-and-drop implementation generated for each diagram (by-passing the Papyrus exensible/pluggable drop strategies). This avoids the problems of determining a priori the
 * identifiers of child views to create in each compartment</li>
 * <li>treat incoming and outgoing edges on a top shape as child views for canonical synchronization</li>
 * <li>delegate determination of semantic children to pluggable strategies on an extension point</li>
 * </ul>
 */
public class PapyrusCanonicalEditPolicy extends CanonicalEditPolicy implements IPapyrusCanonicalEditPolicy {

	static final String FILTER_ID = "org.eclipse.papyrus.canonicalDependents"; //$NON-NLS-1$
	static final String SEMI_ACTIVE_FILTER_ID = "org.eclipse.papyrus.semiCanonical"; //$NON-NLS-1$

	private static final Set<View> createdByCanonical = Sets.newSetFromMap(new WeakHashMap<View, Boolean>());

	private final RollingDeferredArrangeRequest.IArrangementContext arrangeContext = new RollingDeferredArrangeRequest.IArrangementContext() {

		@Override
		public EditPart getHost() {
			return PapyrusCanonicalEditPolicy.this.getHost();
		}

		@Override
		public void execute(Command command) {
			executeCommand(command);
		}
	};

	private ISemanticChildrenStrategy semanticChildrenStrategy = null;

	@SuppressWarnings("deprecation")
	private org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.ICreationTargetStrategy creationTargetStrategy;

	private Collection<? extends EObject> dependents = null;

	private CanonicalStateListener canonicalStateListener;
	private CanonicalStateListener.Handler refreshHandler;

	private State state = State.INACTIVE;

	private boolean overrideEnabled;

	private ChildrenKindStack currentChildrenKind;

	@SuppressWarnings("deprecation")
	@Override
	public final void activate() {
		if (host().getNotationView() != null) {
			final SemanticChildrenStrategyRegistry reg = SemanticChildrenStrategyRegistry.getInstance();
			semanticChildrenStrategy = reg.getSemanticChildrenStrategy(getHost());
			creationTargetStrategy = org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.ICreationTargetStrategy.Safe.safe(reg.getCreationTargetStrategy(getHost()));

			hookCanonicalStateListener();
			if (isEnabled()) {
				doActivate();
			} else {
				transition(State.SEMIACTIVE);
			}
		}
	}

	protected final void basicActivate() {
		transition(State.ACTIVE);
		super.activate();
	}

	protected void doActivate() {
		basicActivate();

		if (semanticChildrenStrategy != null) {
			dependents = semanticChildrenStrategy.getCanonicalDependents(getSemanticHost(), host().getNotationView());
			hookDependentsListener(dependents);
		}
	}

	@Override
	public final void deactivate() {
		if (host().getNotationView() != null) {
			unhookCanonicalStateListener();
		}

		doDeactivate();

		transition(State.INACTIVE);
	}

	protected final void basicDeactivate() {
		super.deactivate();
		transition(State.SEMIACTIVE);
	}

	protected void doDeactivate() {
		basicDeactivate();
	}

	private State transition(State newState) {
		final State result = state;

		if (newState != state) {
			if (!state.validateTransition(newState)) {
				Activator.log.warn(String.format("PapyrusCanonicalEditPolicy forcing invalid transition %s => %s ", state, newState)); //$NON-NLS-1$
			}

			switch (newState) {
			case INACTIVE:
				removeListenerFilter(SEMI_ACTIVE_FILTER_ID);
				break;
			case SEMIACTIVE:
				// Enter a semi-active state in which we only delete obsolete views that we had
				// originally created canonically while we were active
				EObject semanticHost = getSemanticHost();
				if (semanticHost != null) {
					addListenerFilter(SEMI_ACTIVE_FILTER_ID, this, semanticHost);
				}
				break;
			case ACTIVE:
				removeListenerFilter(SEMI_ACTIVE_FILTER_ID);
				break;
			}

			state = newState;
		}

		return result;
	}

	protected final boolean isInState(State state) {
		return this.state == state;
	}

	@Override
	public void enableRefresh(boolean enable) {
		// Only set ourselves 'enabled' when at least semi-active
		if (!isInState(State.INACTIVE)) {
			super.enableRefresh(enable);
		}
	}

	@Override
	public void refreshActive() {
		if (canonicalStateListener != null) {
			if (!isActive()) {
				if (isCanonicalStyleEnabled()) {
					doActivate();

					// I will not get this poke
					canonicalStateListener.pokeHandlers(refreshHandler, true);
				}
			} else {
				if (!isCanonicalStyleEnabled()) {
					doDeactivate();

					// I will not get this poke
					canonicalStateListener.pokeHandlers(refreshHandler, false);
				}
			}
		}
	}

	/**
	 * For CSS support, the canonical style of a compartment is inherited from its parent shape.
	 */
	@Override
	protected CanonicalStyle getCanonicalStyle() {
		return (CanonicalStyle) canonicalHost().getNotationView().getStyle(NotationPackage.eINSTANCE.getCanonicalStyle());
	}

	/**
	 * For CSS support, the canonical style of a compartment is inherited from its parent shape.
	 */
	protected final IGraphicalEditPart canonicalHost() {
		IGraphicalEditPart result = host();
		if (result instanceof IResizableCompartmentEditPart) {
			result = result.getTopGraphicEditPart();
		}
		return result;
	}

	private void hookCanonicalStateListener() {
		class Activate implements Runnable {
			@Override
			public void run() {
				doActivate();
			}
		}
		class Deactivate implements Runnable {
			@Override
			public void run() {
				doDeactivate();
			}
		}

		refreshHandler = new CanonicalStateListener.Handler() {
			@Override
			public Runnable handleAdd(CanonicalStyle style) {
				Runnable result = null;

				if (style.isCanonical()) {
					if (!isActive()) {
						result = new Activate();
					}
				} else if (isActive()) {
					result = new Deactivate();
				}

				return result;
			}

			@Override
			public Runnable handleRemove(CanonicalStyle style) {
				Runnable result = null;

				if (isActive()) {
					result = new Deactivate();
				}

				return result;
			}
		};

		canonicalStateListener = CanonicalStateListener.getInstance(canonicalHost());
		canonicalStateListener.addCanonicalRefreshHandler(refreshHandler);
	}

	private void unhookCanonicalStateListener() {
		if (canonicalStateListener != null) {
			canonicalStateListener.removeCanonicalRefreshHandler(refreshHandler);
			canonicalStateListener.release();
			canonicalStateListener = null;
		}
	}

	protected void hookDependentsListener(Iterable<? extends EObject> dependents) {
		if (dependents != null) {
			for (EObject next : dependents) {
				addListenerFilter(FILTER_ID, this, next);
			}
		}
	}

	@Override
	public boolean isEnabled() {
		if (overrideEnabled) {
			return true;
		}

		// by default there is no synchronization
		if (getCanonicalStyle() == null) {
			return false;
		}
		return super.isEnabled();
	}

	/**
	 * Queries whether I have the {@linkplain CanonicalStyle canonical style} applied and that style
	 * is {@linkplain CanonicalStyle#isCanonical() enabled}.
	 */
	protected boolean isCanonicalStyleEnabled() {
		CanonicalStyle style = getCanonicalStyle();
		return (style != null) && style.isCanonical();
	}

	@Override
	protected List<EObject> getSemanticChildrenList() {
		List<EObject> result = getSemanticChildrenList(ChildrenKind.NODE);

		if (isManageConnections()) {
			result = concat(result, getSemanticChildrenList(ChildrenKind.CONNECTION));
		}

		return result;
	}

	static <E> List<E> concat(Iterable<? extends E> list1, Iterable<? extends E> list2) {
		List<E> result = null;

		if (list1 != null) {
			result = Lists.newArrayList(list1);
		}

		if (list2 != null) {
			if (result != null) {
				Iterables.addAll(result, list2);
			} else {
				result = Lists.newArrayList(list2);
			}
		}

		return (result == null) ? Collections.<E> emptyList() : result;
	}

	protected List<EObject> getSemanticChildrenList(ChildrenKind kind) {
		if (semanticChildrenStrategy != null) {
			ArrayList<EObject> ancestor = getAllEObjectAncestor();

			List<? extends EObject> children;
			switch (kind) {
			case NODE:
				children = semanticChildrenStrategy.getCanonicalSemanticChildren(getSemanticHost(), host().getNotationView());
				break;
			case CONNECTION:
				children = semanticChildrenStrategy.getCanonicalSemanticConnections(getSemanticHost(), host().getNotationView());
				break;
			default:
				throw new IllegalArgumentException(kind.name());
			}

			if (children != null) {
				for (EObject next : children) {
					if (ancestor.contains(next)) {
						return Collections.emptyList();
					}
				}
				return Collections.unmodifiableList(children);
			}
		}

		return Collections.emptyList();
	}


	/**
	 * This method is used to calculate if a semantic element has been already display in parent to avoid loop of display
	 *
	 * @return the list of all semantic element display by parents edipart.
	 */
	protected ArrayList<EObject> getAllEObjectAncestor() {
		ArrayList<EObject> ancestor = new ArrayList<>();
		EditPart currentEditPart = getHost();
		if (currentEditPart.getModel() instanceof View) {
			ancestor.add(((View) currentEditPart.getModel()).getElement());
		}
		while (currentEditPart.getParent() != null) {
			currentEditPart = currentEditPart.getParent();
			if (currentEditPart.getModel() instanceof View) {
				ancestor.add(((View) currentEditPart.getModel()).getElement());
			}
		}
		return ancestor;

	}

	@Override
	protected void handleNotificationEvent(Notification event) {
		// Override the enabled state to process deletions
		final boolean oldOverride = overrideEnabled;
		overrideEnabled = overrideEnabled || isInState(State.SEMIACTIVE);

		try {
			super.handleNotificationEvent(event);
			if (semanticChildrenStrategy != null) {
				if (dependents == null) {
					dependents = semanticChildrenStrategy.getCanonicalDependents(getSemanticHost(), host().getNotationView());
					hookDependentsListener(dependents);
				}
			}
		} finally {
			overrideEnabled = oldOverride;
		}
	}

	@Override
	protected boolean shouldHandleNotificationEvent(Notification event) {
		boolean result = super.shouldHandleNotificationEvent(event);

		if (!result && isManagedListCompartment()) {
			// Also handle move events for reordering of semantic elements
			Object element = event.getNotifier();
			if ((element instanceof EObject) && !(element instanceof View)) {
				result = event.getEventType() == Notification.MOVE;
			}
		}

		return result;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy#refreshSemantic()
	 *      In order to connect the drop mechanism this method has be overloaded
	 */
	@Override
	protected void refreshSemantic() {
		// First, process all child nodes now
		List<IAdaptable> createdViews = refreshSemanticChildren(ChildrenKind.NODE);
		makeViewsImmutable(createdViews);

		// Then, repeat later with connections included, if appropriate
		if (isManageConnections()) {
			ListenableFuture<List<IAdaptable>> createdConnections = DiagramHelper.submit(getHost(), new Callable<List<IAdaptable>>() {
				@Override
				public List<IAdaptable> call() throws Exception {
					return refreshSemanticChildren(ChildrenKind.CONNECTION);
				}
			});
			Futures.addCallback(createdConnections, new FutureCallback<List<IAdaptable>>() {
				@Override
				public void onSuccess(List<IAdaptable> result) {
					makeViewsImmutable(result);
				}

				@Override
				public void onFailure(Throwable t) {
					Activator.log.error(t);
				}
			}, MoreExecutors.directExecutor()); // Added because of compilation error on the executor-less method call
		}
	}



	/**
	 * this method is extracted from refreshSemanticChildren
	 * but instead of using the service view it uses the drop
	 *
	 * @return
	 */
	protected List<IAdaptable> refreshSemanticChildren(ChildrenKind kind) {
		// -------------- code from superClass----------------
		// Don't try to refresh children if the semantic element
		// cannot be resolved.
		if (resolveSemanticElement() == null) {
			return Collections.emptyList();
		}

		if (isInState(State.SEMIACTIVE) && createdByCanonical.isEmpty()) {
			// No point in calculating anything because we have nothing to clean up
			return Collections.emptyList();
		}

		// current views
		List<View> viewChildren = getViewChildren(kind);
		List<EObject> allSemanticChildren = getSemanticChildrenList(kind);
		List<EObject> semanticChildren = new ArrayList<>(allSemanticChildren);

		boolean changed = false;

		if (!isInState(State.INACTIVE)) {
			//
			// delete all the remaining orphaned views
			List<View> orphaned = cleanCanonicalSemanticChildren(viewChildren, semanticChildren);
			if (!orphaned.isEmpty() && isInState(State.SEMIACTIVE)) {
				// Furthermore, if we're only semiactive, then we should only delete views that we
				// had created canonically while we were active
				filterCanonicallyCreated(orphaned);
			}
			if (!orphaned.isEmpty()) {
				// We may be asynchronously processing the deletion of a relationship that
				// requires deletion of edges. If so, we may be only semi-active (the edge
				// could have been created by the edit-part at the other end)
				changed = deleteViews(orphaned);
			}
		}

		//
		// create a view for each remaining semantic element, if requested.
		List<IAdaptable> createdViews = Collections.emptyList();
		if (isInState(State.ACTIVE)) {
			if (!semanticChildren.isEmpty()) {
				pushChildrenKind(kind);
				try {
					createdViews = createViews(semanticChildren);
				} finally {
					popChildrenKind();
				}
			}

			if (isManagedListCompartment()) {
				// Compute re-ordering, if necessary to match the semantics
				List<? extends View> newViewOrder = matchCanonicalOrdering(viewChildren, allSemanticChildren);
				if (newViewOrder != null) {
					setViewOrder(newViewOrder);
				}
			}
		}

		if (changed || !createdViews.isEmpty()) {
			postProcessRefreshSemantic(createdViews);
		}

		return createdViews;
	}

	private void pushChildrenKind(ChildrenKind kind) {
		currentChildrenKind = new ChildrenKindStack(kind);
	}

	private void popChildrenKind() {
		currentChildrenKind = currentChildrenKind.parent();
	}

	protected final ChildrenKind currentChildrenKind() {
		return (currentChildrenKind == null) ? null : currentChildrenKind.kind();
	}

	@Override
	protected boolean isOrphaned(Collection<EObject> semanticChildren, View view) {
		if (super.isOrphaned(semanticChildren, view)) {
			return isNoSemanticConnectorOrphaned(semanticChildren, view);
		}
		return false;
	}

	private boolean isNoSemanticConnectorOrphaned(Collection<EObject> semanticChildren, View view) {
		EObject element = view.getElement();
		if (element != null || false == view instanceof Connector) {
			return true;
		}
		EObject noSemanticConnector = createNoSemanticConnector((Connector) view);
		return !semanticChildren.contains(noSemanticConnector);
	}

	private EdgeWithNoSemanticElementRepresentationImpl createNoSemanticConnector(Connector connector) {
		return new EdgeWithNoSemanticElementRepresentationImpl(connector.getSource().getElement(), connector.getTarget().getElement(), connector.getType());
	}

	/**
	 * As {@link CanonicalEditPolicy#deleteViews(Iterator)}, deletes a list of views.
	 * The views will be deleted <tt>iff</tt> their semantic element has also been deleted.
	 *
	 * @param views
	 *            an iterator on a list of views.
	 * @return <tt>true</tt> if the host editpart should be refreshed; either one one of the supplied
	 *         views was deleted or has been reparented.
	 */
	protected boolean deleteViews(Iterable<? extends View> views) {
		// Copied from CanonicalEditPolicy::deleteViews because that method is final, and
		// we need to be able to delete views even when we are only semi-active
		if (!isEnabled() && isInState(State.INACTIVE)) {
			return false;
		}
		final CompoundCommand cc = new CompoundCommand(DiagramUIMessages.DeleteCommand_Label);
		for (View view : views) {
			if (shouldDeleteView(view)) {
				cc.add(getDeleteViewCommand(view));
			}
		}

		boolean doDelete = !cc.isEmpty() && cc.canExecute();
		if (doDelete) {
			executeCommand(cc);
		}
		return doDelete;
	}

	@Override
	protected ViewDescriptor getViewDescriptor(EObject element) {
		ViewDescriptor result;
		boolean isEdge = false;

		// Consult the visual type service to get the appropriate
		IGraphicalEditPart parentPart = host();
		IGraphicalEditPart sourcePart = null;
		IGraphicalEditPart targetPart = null;

		// Look for a node to create
		String viewType;

		switch (currentChildrenKind()) {
		case NODE:
			viewType = VisualTypeService.getInstance().getNodeType(parentPart.getNotationView(), element);
			break;
		case CONNECTION:
			isEdge = true;
			parentPart = DiagramEditPartsUtil.getDiagramEditPart(host());
			viewType = VisualTypeService.getInstance().getLinkType(host().getNotationView().getDiagram(), element);
			if (viewType != null) {
				// Identify the source and target
				sourcePart = semanticChildrenStrategy.resolveSourceEditPart(element, host());
				targetPart = semanticChildrenStrategy.resolveTargetEditPart(element, host());
			}
			break;
		default:
			viewType = null;
			break;
		}

		if ((viewType != null) && (!isEdge || ((sourcePart != null) && (targetPart != null)))) {
			IAdaptable elementAdapter = new SemanticElementAdapter(
					element,
					VisualTypeService.getInstance().getElementType(
							host().getNotationView().getDiagram(),
							viewType));

			int pos = getViewIndexFor(element);
			result = isEdge
					? new ChildConnectionDescriptor(elementAdapter, viewType, pos, sourcePart, targetPart)
					: new ChildNodeDescriptor(elementAdapter, viewType, pos, parentPart);
		} else {
			// i.e., return a view descriptor that is certain not to result in an useful command
			result = super.getViewDescriptor(element);
		}

		return result;
	}

	@Override
	protected Command getCreateViewCommand(CreateRequest request) {
		Command result;

		if (!(request instanceof CreateViewRequest)) {
			result = super.getCreateViewCommand(request);
		} else {
			// Obtain the view creation commands and decorate them
			CreateViewRequest createViewRequest = (CreateViewRequest) request;
			Iterable<? extends ViewDescriptor> descriptors = createViewRequest.getViewDescriptors();

			CompoundCommand compoundCommand = new CompoundCommand();
			for (ViewDescriptor viewDescriptor : descriptors) {
				EObject element = viewDescriptor.getElementAdapter().getAdapter(EObject.class);

				if (element != null) {
					CreateRequest newRequest = createCreateRequest(viewDescriptor);
					Command cmd;

					if (newRequest instanceof CreateConnectionViewRequest) {
						// Connection case
						EditPart sourceEditPart = ((CreateConnectionViewRequest) newRequest).getSourceEditPart();
						EditPart targetEditPart = ((CreateConnectionViewRequest) newRequest).getTargetEditPart();

						// Initialize the command
						newRequest.setType(RequestConstants.REQ_CONNECTION_START);
						sourceEditPart.getCommand(newRequest);
						newRequest.setType(RequestConstants.REQ_CONNECTION_END);

						// Get the command
						cmd = targetEditPart.getCommand(newRequest);
					} else {
						// Node case
						EditPart parentEditPart = (viewDescriptor instanceof ChildNodeDescriptor)
								? ((ChildNodeDescriptor) viewDescriptor).getParentEditPart()
								: host();

						@SuppressWarnings("deprecation")
						EditPart targetEditPart = creationTargetStrategy.getTargetEditPart(parentEditPart, element);

						cmd = targetEditPart.getCommand(newRequest);
					}

					if ((cmd != null) && cmd.canExecute()) {
						compoundCommand.add(cmd);
					}
				}
			}

			result = compoundCommand.unwrap();
			if (result.canExecute()) {
				result = tagAndArrange(result);
			}
		}

		return result;
	}

	protected CreateRequest createCreateRequest(ViewDescriptor viewDescriptor) {
		CreateRequest result;

		if (viewDescriptor instanceof CreateConnectionViewRequest.ConnectionViewDescriptor) {
			CreateConnectionViewRequest.ConnectionViewDescriptor cvd = (CreateConnectionViewRequest.ConnectionViewDescriptor) viewDescriptor;
			CreateConnectionViewRequest ccvr = new CreateConnectionViewRequest(cvd);

			if (cvd instanceof ChildConnectionDescriptor) {
				ChildConnectionDescriptor ccd = (ChildConnectionDescriptor) cvd;
				ccvr.setSourceEditPart(ccd.getSourceEditPart());
				ccvr.setTargetEditPart(ccd.getTargetEditPart());
			}

			// Unlike CreateViewRequest, this request's constructor doesn't
			// initialize the location
			ccvr.setLocation(new Point(-1, -1));

			result = ccvr;
		} else {
			result = new CreateViewRequest(viewDescriptor);
		}

		return result;
	}

	@Override
	protected List<View> getViewChildren() {
		List<View> result = getViewChildren(ChildrenKind.NODE);

		if (isManageConnections()) {
			result.addAll(getViewChildren(ChildrenKind.CONNECTION));
		}

		return result;
	}

	protected List<View> getViewChildren(ChildrenKind kind) {
		final IGraphicalEditPart host = host();
		final View view = host.getNotationView();

		IVisualChildrenStrategy strategy = SemanticChildrenStrategyRegistry.getInstance().getVisualChildrenStrategy(host);

		List<View> result;

		if (strategy == null) {
			strategy = IVisualChildrenStrategy.DEFAULT;
		}

		List<? extends View> childViews;
		switch (kind) {
		case NODE:
			childViews = strategy.getCanonicalChildren(host, view);
			break;
		case CONNECTION:
			childViews = strategy.getCanonicalEdges(host, view);
			break;
		default:
			throw new IllegalArgumentException(kind.name());
		}
		result = (childViews == null) ? Collections.<View> emptyList() : Lists.newArrayList(childViews);

		return result;
	}

	/**
	 * Queries whether I manage canonical connections. Edit parts such as top shapes and border items manage connections;
	 * other edit parts such as compartments manage contained views only.
	 *
	 * @return whether I manage canonical connections
	 */
	protected boolean isManageConnections() {
		final EditPart host = host();
		return (host instanceof TopGraphicEditPart) || (host instanceof IBorderItemEditPart);
	}

	private Command tagAndArrange(Command creationCommand) {
		return new PostCreationWrapperCommand<List<IAdaptable>>(creationCommand) {

			@Override
			protected List<IAdaptable> createAccumulator() {
				return Lists.newArrayList();
			}

			@Override
			protected void postProcessView(Object viewish, List<IAdaptable> accumulator) {
				if (viewish instanceof IAdaptable) {
					View view = ((IAdaptable) viewish).getAdapter(View.class);
					if (view != null) {
						// Arrange the parent of a label, not the label
						if (view instanceof DecorationNode) {
							view = (View) view.eContainer();
							viewish = new EObjectAdapter(view);
						}

					}
					accumulator.add((IAdaptable) viewish);
				}
				super.postProcessView(viewish, accumulator);
			}

			@Override
			protected void postProcessView(View view, List<IAdaptable> accumulator) {
				createdByCanonical.add(view);
			}

			@Override
			protected void complete(List<IAdaptable> accumulator) {
				if (!accumulator.isEmpty()) {
					RollingDeferredArrangeRequest.post(arrangeContext, accumulator);
				}
			}
		};
	}

	private <C extends Collection<? extends View>> C filterCanonicallyCreated(C views) {
		for (Iterator<? extends View> iter = views.iterator(); iter.hasNext();) {
			if (!createdByCanonical.contains(iter.next())) {
				iter.remove();
			}
		}

		return views;
	}

	protected boolean isManagedListCompartment() {
		// If the notation view is a ListCompartment, then we should rely on its SortingStyle to present
		// its contents in the appropriate order if it has sorting kind different from 'none'
		boolean result = false;

		if (host() instanceof ListCompartmentEditPart) {
			SortingStyle style = (SortingStyle) host().getNotationView().getStyle(NotationPackage.Literals.SORTING_STYLE);
			result = (style == null) || (style.getSorting() == Sorting.NONE_LITERAL);
		}

		return result;
	}

	/**
	 * Computes, if necessary, a new ordering of the {@code viewChildren} to match the ordering of the {@code semanticChildren}.
	 *
	 * @param viewChildren
	 *            the existing view children in the order in which they are currently presented
	 * @param semanticChildren
	 *            the semantic children that are now presented (we may have already created views for some)
	 *
	 * @return a new list presenting the {@code viewChildren} in semantic order, or {@code null} if no ordering changes are required
	 */
	protected List<? extends View> matchCanonicalOrdering(List<? extends View> viewChildren, final List<EObject> semanticChildren) {
		List<? extends View> result = Ordering.natural().onResultOf(new Function<View, Integer>() {
			@Override
			public Integer apply(View input) {
				return semanticChildren.indexOf(input.getElement());
			}
		}).sortedCopy(viewChildren);

		if (result.equals(viewChildren)) {
			// If the order is not different, don't try to change anything
			result = null;
		}

		return result;
	}

	protected void setViewOrder(List<? extends View> childViews) {
		final IGraphicalEditPart host = host();
		executeCommand(EMFtoGEFCommandWrapper.wrap(SetCommand.create(host.getEditingDomain(), host.getNotationView(), NotationPackage.Literals.VIEW__PERSISTED_CHILDREN, childViews)));
	}

	//
	// Nested types
	//

	protected enum State {
		INACTIVE, SEMIACTIVE, ACTIVE;

		boolean validateTransition(State next) {
			switch (this) {
			case INACTIVE:
			case SEMIACTIVE:
				return next != this;
			case ACTIVE:
				return next == SEMIACTIVE;
			default:
				throw new IllegalStateException("No such state: " + this); //$NON-NLS-1$
			}
		}
	}

	protected enum ChildrenKind {
		NODE, CONNECTION;
	}

	private final class ChildrenKindStack {
		private final ChildrenKind kind;
		private final ChildrenKindStack parent;

		ChildrenKindStack(ChildrenKind kind) {
			super();

			this.kind = kind;
			this.parent = currentChildrenKind;
		}

		ChildrenKind kind() {
			return kind;
		}

		ChildrenKindStack parent() {
			return parent;
		}
	}

	protected static abstract class PostCreationWrapperCommand<A> extends GEFCommandWrapper {
		public PostCreationWrapperCommand(Command command) {
			super(command);
		}

		@Override
		public void execute() {
			super.execute();

			A accumulator = createAccumulator();
			postProcessViews(getCommand(), accumulator);
			complete(accumulator);
		}

		protected A createAccumulator() {
			return null;
		}

		protected void complete(A accumulator) {
			// Pass
		}

		private void postProcessViews(Command command, A accumulator) {
			for (Iterator<ICommand> iter = CommandTreeIterator.iterateGMF(command); iter.hasNext();) {
				CommandResult result = iter.next().getCommandResult();
				if ((result != null) && ((result.getStatus() == null) || result.getStatus().isOK())) {
					Object returnValue = result.getReturnValue();
					if (returnValue instanceof Iterable<?>) {
						for (Object next : (Iterable<?>) returnValue) {
							postProcessView(next, accumulator);
						}
					} else if (returnValue != null) {
						postProcessView(returnValue, accumulator);
					}
				}
			}
		}

		protected void postProcessView(Object viewish, A accumulator) {
			View view = PlatformHelper.getAdapter(viewish, View.class);
			if (view != null) {
				postProcessView(view, accumulator);
			}
		}

		protected void postProcessView(View view, A accumulator) {
			// Pass
		}
	}

	private class ChildNodeDescriptor extends CreateViewRequest.ViewDescriptor {

		private final EditPart parentEditPart;

		public ChildNodeDescriptor(IAdaptable elementAdapter, String factoryHint, int index, EditPart parentEditPart) {
			super(elementAdapter, Node.class, factoryHint, index, host().getDiagramPreferencesHint());

			this.parentEditPart = parentEditPart;
		}

		EditPart getParentEditPart() {
			return parentEditPart;
		}
	}

	private class ChildConnectionDescriptor extends CreateConnectionViewRequest.ConnectionViewDescriptor {

		private final EditPart sourceEditPart;
		private final EditPart targetEditPart;

		public ChildConnectionDescriptor(IAdaptable elementAdapter, String factoryHint, int index, EditPart sourceEditPart, EditPart targetEditPart) {
			super(elementAdapter, factoryHint, index, host().getDiagramPreferencesHint());

			this.sourceEditPart = sourceEditPart;
			this.targetEditPart = targetEditPart;
		}

		EditPart getSourceEditPart() {
			return sourceEditPart;
		}

		EditPart getTargetEditPart() {
			return targetEditPart;
		}
	}
}
