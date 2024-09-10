/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, Esterel Technologies SAS and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *  Christian W. Damus - bug 477384
 *  Sebastien Bordes (Esterel Technologies SAS) - bug 498357
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.dnd.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.DefaultActionHandler;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SelectAndExecuteCommand;
import org.eclipse.papyrus.infra.gmfdiag.dnd.Activator;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DefaultDropStrategy;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.MultipleDropStrategy;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.TransactionalCommandsDropStrategy;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * An EditPolicy to handle Drop in Papyrus diagrams.
 * This edit policy can be customized from an extension point. If a customization
 * is not available, it will delegate the behavior to the default Drop edit policy
 *
 * Update: it will enable to manage multiple commands per strategy
 *
 * @author Camille Letavernier
 *
 */
public class CustomizableDropEditPolicy extends DragDropEditPolicy {

	public static final String EXTENSION_ID = Activator.PLUGIN_ID + ".dropStrategy";

	protected EditPolicy defaultDropEditPolicy;

	protected EditPolicy defaultCreationEditPolicy;

	protected DropStrategy defaultDropStrategy;

	// FIXME: This comes from oep.uml.diagram.common.listener.DropTargetListener
	// This should be merged to oep.infra.gmfdiag.common, as this is not specific to UML
	public static final String EVENT_DETAIL = "EVENT_DETAIL";

	@Override
	public void activate() {
		// Nothing
	}

	/**
	 * Instantiates a new CustomizableDropEditPolicy
	 *
	 * @param defaultEditPolicy
	 *            The default editPolicy, to be called when no custom Drop strategy is available
	 */
	public CustomizableDropEditPolicy(EditPolicy defaultDropEditPolicy, EditPolicy defaultCreationEditPolicy) {
		this.defaultDropEditPolicy = defaultDropEditPolicy;
		this.defaultCreationEditPolicy = defaultCreationEditPolicy;
		this.defaultDropStrategy = new DefaultDropStrategy(defaultDropEditPolicy, defaultCreationEditPolicy);
	}

	@Override
	public Command getCommand(final Request request) {
		Command command;

		if (super.understandsRequest(request)) {
			// Drag & Drop request
			try {
				command = super.getCommand(request); // Will call this.getDropObjectsCommand() eventually
			} catch (Exception ex) {
				command = getCustomCommand(request);
			}
		} else if (this.understands(request)) {
			// Add or Move children request
			command = getCustomCommand(request);
		} else if (defaultCreationEditPolicy != null) {
			// Creation request
			if (defaultCreationEditPolicy.understandsRequest(request)) {
				EditPart defaultTargetEditPart = defaultCreationEditPolicy.getTargetEditPart(request);
				EditPart myTargetEditPart = super.getTargetEditPart(request);
				if (defaultTargetEditPart != myTargetEditPart) {
					command = defaultTargetEditPart.getCommand(request);
				} else {
					command = defaultCreationEditPolicy.getCommand(request);
				}
			} else {
				command = null;
			}
		} else {
			command = null;
		}

		if (command == null) {
			return null;
		}

		return command;
	}

	@Override
	public boolean understandsRequest(Request request) {
		return this.understands(request) || (defaultCreationEditPolicy != null && defaultCreationEditPolicy.understandsRequest(request)) || (defaultDropEditPolicy != null && defaultDropEditPolicy.understandsRequest(request)) || isCustomRequest(request);
	}

	protected boolean understands(Request request) {
		return org.eclipse.gef.RequestConstants.REQ_ADD.equals(request.getType()) || RequestConstants.REQ_MOVE_CHILDREN.equals(request.getType());
	}

	protected boolean isCustomRequest(Request request) {
		return !findStrategies(request).isEmpty();
	}

	/**
	 * @deprecated Use {@link #getCustomCommand(Request)}
	 */
	@Deprecated
	protected Command getCreationCommand(Request request) {
		return getCustomCommand(request);
	}

	/**
	 * @deprecated No longer used.
	 */
	@Deprecated
	protected Command getCanonicalDropObjectsCommand(Request request) {
		Command result = null;

		if ((defaultDropEditPolicy != null) && (request instanceof org.eclipse.papyrus.infra.gmfdiag.common.commands.requests.CanonicalDropObjectsRequest)) {
			result = defaultDropEditPolicy.getCommand(((org.eclipse.papyrus.infra.gmfdiag.common.commands.requests.CanonicalDropObjectsRequest) request).getDropObjectsRequest());
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command getDropObjectsCommand(DropObjectsRequest request) {
		Command dropCommand = getCustomCommand(request);

		if (dropCommand != null && dropCommand.canExecute() && request.getObjects().size() > 1) {
			return layoutDroppedObjects(dropCommand);
		}

		return dropCommand;
	}

	protected Command layoutDroppedObjects(final Command dropCommand) {
		AbstractTransactionalCommand spacingCommand = new AbstractTransactionalCommand((TransactionalEditingDomain) EMFHelper.resolveEditingDomain(getHost()), "Spacing elements", Collections.EMPTY_LIST) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				if (dropCommand instanceof ICommandProxy) {
					ICommand gmfCommand = ((ICommandProxy) dropCommand).getICommand();
					CommandResult previousCommandResult = gmfCommand.getCommandResult();
					if (previousCommandResult != null) {
						Object returnValue = previousCommandResult.getReturnValue();
						if (returnValue instanceof List<?>) {
							List<?> returnedElements = (List<?>) returnValue;

							int i = 0;
							for (Object returnedElement : returnedElements) {
								if (returnedElement instanceof CreateViewRequest.ViewDescriptor) {
									CreateViewRequest.ViewDescriptor newViewDescriptor = (CreateViewRequest.ViewDescriptor) returnedElement;
									Shape newShape = (Shape) newViewDescriptor.getAdapter(Shape.class);
									if (newShape != null) {
										LayoutConstraint constraint = newShape.getLayoutConstraint();
										if (constraint instanceof Bounds) {
											Bounds bounds = (Bounds) constraint;
											updateBounds(bounds, i);
											i++;
										}
									}
								}
							}
						}
					}
				}
				return CommandResult.newOKCommandResult();
			}

		};

		return dropCommand.chain(new ICommandProxy(spacingCommand));
	}

	protected void updateBounds(Bounds bounds, int position) {
		int x = bounds.getX();
		int y = bounds.getY();
		bounds.setX(x + 15 * position);
		bounds.setY(y + 15 * position);
	}

	/**
	 * Returns the command from a Custom DropStrategy
	 *
	 * @param request
	 * @return
	 */
	protected Command getCustomCommand(Request request) {
		// extendedMatchingStrategies match Strategy to List of Commands
		final Map<DropStrategy, List<Command>> extendedMatchingStrategies = findExtendedStrategies(request);

		// Reverting to a simple matchingStrategies a strategy to a command
		final Map<DropStrategy, Command> matchingStrategies = getStrategies(extendedMatchingStrategies);

		boolean useDefault = true;

		// FIXME: What's the exact semantic of EVENT_DETAIL=DND_COPY in Papyrus?
		// Currently, DND_COPY corresponds to Ctrl + Drag/Drop
		if (request.getExtendedData().containsKey(EVENT_DETAIL)) {
			int eventDetailValue = (Integer) request.getExtendedData().get(EVENT_DETAIL);
			if ((eventDetailValue & DND.DROP_COPY) != 0) {
				useDefault = false;
			}
		}

		// Search for a default strategy in case of basic DropStrategy
		if (useDefault) {
			DropStrategy dropStrategy = DropStrategyManager.instance.getDefaultDropStrategy(matchingStrategies.keySet());
			if (dropStrategy != null) {
				return getValidCommand(dropStrategy, request, extendedMatchingStrategies, matchingStrategies);
			}

		}

		// If there is no default choice, ask user (Open a choice List)
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		DefaultActionHandler handler = new DefaultActionHandler() {

			@Override
			public void defaultActionSelected(Command defaultCommand) {
				DropStrategy defaultStrategy = findExtendedStrategy(extendedMatchingStrategies, defaultCommand);
				if (defaultStrategy != null) {
					DropStrategyManager.instance.setDefaultDropStrategy(matchingStrategies.keySet(), defaultStrategy);
				}
			}

			@Override
			public String getLabel() {
				return "Change the default strategy";
			}
		};

		// Fill the proposalCommands to cover multiple drop strategies and normal drop strategies
		List<Command> proposalCommands = new ArrayList<>();
		Command initialValidCommand = null;
		for (List<Command> cs : extendedMatchingStrategies.values()) {
			for (Command c : cs) {
				if (c != null && c.canExecute()) {
					initialValidCommand = c;
					proposalCommands.add(c);
				}
			}
		}
		if (proposalCommands.size() == 0) {
			// Finally no command
			return null;
		} else if (proposalCommands.size() == 1) {
			// Finally one command
			return initialValidCommand;
		} else if (proposalCommands.size() > 1) {
			// Finally multiple matching commands
			Activator.log.debug("proposalCommands size: " + proposalCommands.size());
			SelectAndExecuteCommand command = new SelectAndExecuteCommand("Select drop", shell, new ArrayList<>(proposalCommands), handler);
			return new ICommandProxy(command);
		}

		// No matching strategy
		return null;
	}

	/**
	 * @param dropStrategy
	 *            a dropStrategy
	 * @param request
	 *            the request
	 * @param extendedMatchingStrategies
	 *            the matching drop strategies with their list of command
	 * @param matchingStrategies
	 *            a command in regard of each matching strategy
	 * @return
	 */
	private Command getValidCommand(DropStrategy dropStrategy, Request request, final Map<DropStrategy, List<Command>> extendedMatchingStrategies, final Map<DropStrategy, Command> matchingStrategies) {
		if (dropStrategy instanceof MultipleDropStrategy) {
			// the return strategy is a multiple command
			List<Command> proposalCommands = new ArrayList<>();
			List<Command> cs = ((MultipleDropStrategy) dropStrategy).getCommands(request, getHost());
			Command initialValideCommand = null;
			for (Command command : cs) {
				if (command != null && command.canExecute()) {
					initialValideCommand = command;
					proposalCommands.add(command);

				}
			}
			if (proposalCommands.size() == 0) {
				// Finally no matching command
				return null;
			} else if (proposalCommands.size() == 1) {
				// Finally one matching command
				return initialValideCommand;
			} else {
				// Finally more than one matching command
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

				DefaultActionHandler handler = new DefaultActionHandler() {

					@Override
					public void defaultActionSelected(Command defaultCommand) {
						// At present time, no default command for MultipleDropStrategy
						DropStrategy defaultStrategy = findExtendedStrategy(extendedMatchingStrategies, defaultCommand);
						if (defaultStrategy != null) {
							DropStrategyManager.instance.setDefaultDropStrategy(matchingStrategies.keySet(), defaultStrategy);
						}
					}

					@Override
					public String getLabel() {
						return "Change the default strategy";
					}
				};

				Activator.log.debug("proposalCommands size: " + proposalCommands.size());
				SelectAndExecuteCommand command = new SelectAndExecuteCommand("Select drop", shell, new ArrayList<>(proposalCommands), handler);
				return new ICommandProxy(command);
			}

		} else {
			// The return strategy is a simple strategy
			return matchingStrategies.get(dropStrategy);
		}
	}

	protected static DropStrategy findStrategy(Map<DropStrategy, Command> matchingStrategies, Command command) {
		for (Map.Entry<DropStrategy, Command> entry : matchingStrategies.entrySet()) {
			if (entry.getValue() == command) {
				return entry.getKey();
			}
		}
		return null;
	}

	protected static DropStrategy findExtendedStrategy(Map<DropStrategy, List<Command>> extendedMatchingStrategies, Command command) {
		for (Map.Entry<DropStrategy, List<Command>> entry : extendedMatchingStrategies.entrySet()) {

			if (entry.getValue().contains(command)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Returns a map of DropStrategy / Command, from an initial Map that maps DropStrategy to List of commands. The map may be empty.
	 *
	 * @param Map<DropStrategy,
	 *            List<Command>>
	 * @return
	 */
	protected Map<DropStrategy, Command> getStrategies(Map<DropStrategy, List<Command>> extendedMatchingStrategies) {
		Map<DropStrategy, Command> matchingStrategies = new LinkedHashMap<>();
		for (DropStrategy d : extendedMatchingStrategies.keySet()) {
			Iterator<Command> i = extendedMatchingStrategies.get(d).iterator();
			if (i.hasNext()) {
				matchingStrategies.put(d, i.next());
			}
		}
		return matchingStrategies;
	}

	/**
	 * Returns a map of DropStrategy / Command, for each Strategy which can handle
	 * the given request. All the returned commands are executable. The map may be empty.
	 *
	 * @param request
	 * @return
	 */
	protected Map<DropStrategy, Command> findStrategies(Request request) {
		Map<DropStrategy, Command> matchingStrategies = new LinkedHashMap<>();

		for (DropStrategy strategy : DropStrategyManager.instance.getActiveStrategies()) {
			try { // Strategies are provided through extension points; we can't guarantee they won't crash

				if (strategy instanceof MultipleDropStrategy) {
					List<Command> commands = ((MultipleDropStrategy) strategy).getCommands(request, getHost());
					if (commands != null && commands.size() > 0) {
						// take the first one, and fill the map withit
						Command command = commands.get(0);
						if (command != null && command.canExecute()) {
							matchingStrategies.put(strategy, command);
						}
					}
				} else {

					Command command = strategy.getCommand(request, getHost());
					if (command != null && command.canExecute()) {
						matchingStrategies.put(strategy, command);
					}
				}
			} catch (Throwable t) {
				String message = String.format("An error occurred when trying to execute a custom Drop strategy: %s", strategy.getLabel());
				Activator.log.error(message, t);
			}
		}

		Command command = defaultDropStrategy.getCommand(request, getHost());
		if (command != null && command.canExecute()) {
			matchingStrategies.put(defaultDropStrategy, command);
		}

		return matchingStrategies;
	}

	/**
	 * Returns a map of DropStrategy / Command, for each Strategy which can handle
	 * the given request. All the returned commands are executable. The map may be empty.
	 *
	 * @param request
	 * @return
	 */
	protected Map<DropStrategy, List<Command>> findExtendedStrategies(Request request) {
		Map<DropStrategy, List<Command>> matchingStrategies = new LinkedHashMap<>();

		// Retrieve strategies
		for (DropStrategy strategy : DropStrategyManager.instance.getActiveStrategies()) {
			List<Command> selectedCommands = new ArrayList<>();


			if (strategy instanceof TransactionalCommandsDropStrategy) {
				List<Command> cs = ((TransactionalCommandsDropStrategy) strategy).getCommands(request, getHost());
				for (Command command : cs) {
					if (command != null && command.canExecute()) {
						selectedCommands.add(command);
					}
				}
			} else {
				Command command = strategy.getCommand(request, getHost());
				if (command != null && command.canExecute()) {
					selectedCommands.add(command);
				}
			}

			if (selectedCommands.size() > 0) {
				matchingStrategies.put(strategy, selectedCommands);
			}

		}

		// Retrieve defaultStrategy
		Command command = defaultDropStrategy.getCommand(request, getHost());
		if (command != null && command.canExecute()) {
			List<Command> selectedCommands = new ArrayList<>();
			selectedCommands.add(command);
			matchingStrategies.put(defaultDropStrategy, selectedCommands);
		}

		return matchingStrategies;
	}


	/**
	 * @see org.eclipse.gef.EditPolicy#showTargetFeedback(org.eclipse.gef.Request)
	 */
	@Override
	public void showTargetFeedback(Request request) {

		final Map<DropStrategy, List<Command>> matchingStrategies = findExtendedStrategies(request);

		boolean updateFeedback = true;
		for (Map.Entry<DropStrategy, List<Command>> dropEntry : matchingStrategies.entrySet()) {
			if (dropEntry.getKey().showTargetFeedback(request, getHost())) {
				updateFeedback = false;
			}
		}

		if (updateFeedback) {

			if (defaultCreationEditPolicy != null && defaultCreationEditPolicy.understandsRequest(request)) {
				defaultCreationEditPolicy.showTargetFeedback(request);
			}

			if (!(getHost() instanceof DiagramEditPart)) {
				super.showTargetFeedback(request);
			}
		}
	}

	@Override
	public void eraseTargetFeedback(Request request) {
		boolean updateFeedback = true;
		for (DropStrategy strategy : DropStrategyManager.instance.getActiveStrategies()) {
			if (strategy.eraseTargetFeedback(request, getHost())) {
				updateFeedback = false;
			}
		}

		if (updateFeedback) {
			if (defaultCreationEditPolicy != null) {
				defaultCreationEditPolicy.eraseTargetFeedback(request);
			}
			if (!(getHost() instanceof DiagramEditPart)) {
				super.eraseTargetFeedback(request);
			}
		}
	}

	@Override
	public void showSourceFeedback(Request request) {
		if (defaultCreationEditPolicy != null && defaultCreationEditPolicy.understandsRequest(request)) {
			defaultCreationEditPolicy.showSourceFeedback(request);
		}

		if (!(getHost() instanceof DiagramEditPart)) {
			super.showSourceFeedback(request);
		}
	}

	@Override
	public void eraseSourceFeedback(Request request) {
		if (defaultCreationEditPolicy != null) {
			defaultCreationEditPolicy.eraseSourceFeedback(request);
		}

		if (!(getHost() instanceof DiagramEditPart)) {
			super.eraseSourceFeedback(request);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		// when default creation edit policy is not overriden and request is a creation request, target edit part should be computed by the default edit policy itself
		if (!super.understandsRequest(request) && !this.understands(request) && (defaultCreationEditPolicy != null && defaultCreationEditPolicy.understandsRequest(request))) {
			return defaultCreationEditPolicy.getTargetEditPart(request);
		}
		return super.getTargetEditPart(request);
	}

	public EditPolicy getDefaultCreationPolicy() {
		return defaultCreationEditPolicy;
	}

}
