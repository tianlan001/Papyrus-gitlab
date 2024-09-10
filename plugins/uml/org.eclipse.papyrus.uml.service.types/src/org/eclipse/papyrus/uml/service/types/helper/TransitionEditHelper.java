/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 492089
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

public class TransitionEditHelper extends ElementEditHelper {

	protected boolean canCreate(EObject source, EObject target) {
		if (false == source instanceof Vertex) {
			return false;
		}
		if (target == null) {
			return true;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getCreateRelationshipCommand(final CreateRelationshipRequest req) {
		EObject source = req.getSource();
		EObject target = req.getTarget();
		if (false == source instanceof Vertex) {
			return UnexecutableCommand.INSTANCE;
		}
		if (null == target) {
			return IdentityCommand.INSTANCE;
		}
		if (false == target instanceof Vertex) {
			return UnexecutableCommand.INSTANCE;
		}
		Region container = TypeUtils.as(req.getContainer(), Region.class);
		if (container == null) {
			container = findRegionContainer((Vertex) source);
			if (null == container) {
				container = findRegionContainer((Vertex) target);
				if (null == container) {
					// If neither the source nor the Target are contained in a Region, create the transition in the first Region of StateMachine.
					container = getFirstRegionStateMachine((Vertex) source);
					if (null == container) {
						return UnexecutableCommand.INSTANCE;
					}
				}
			}
		}
		setContainerAndFeature(req, container);
		return super.getCreateRelationshipCommand(req);
	}

	private void setContainerAndFeature(CreateRelationshipRequest req, Region container) {
		req.setContainer(container);
		req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Transition());
	}

	private Region findRegionContainer(final Vertex source) {
		if (null == source || null == source.eContainer()) {
			return null;
		}
		if (source.eContainer() instanceof Vertex) {
			return findRegionContainer((Vertex) source.eContainer());
		}
		return source.eContainer() instanceof Region ? (Region) source.eContainer() : null;
	}

	/**
	 * Get the first Region of the StateMachine.
	 *
	 * @param source
	 *            The Vertex object which contained on the StateMachine.
	 * @return The first Region of the StateMachine.
	 */
	private Region getFirstRegionStateMachine(final Vertex source) {
		final EObject eContainer = source.eContainer();
		if (null != eContainer) {
			if (eContainer instanceof StateMachine) {
				final EList<Region> regions = ((StateMachine) eContainer).getRegions();
				if (!regions.isEmpty()) {
					return regions.get(0);
				}
			} else if (eContainer instanceof Vertex) {
				return findRegionContainer((Vertex) eContainer);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getConfigureCommand(final ConfigureRequest req) {
		ICommand configureCommand = new ConfigureElementCommand(req) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				EObject elementToConfigure = req.getElementToConfigure();
				if (!(elementToConfigure instanceof Transition)) {
					return CommandResult.newErrorCommandResult("This configure command should be run against a transition. element to configure was: " + elementToConfigure);
				}
				Transition transition = (Transition) elementToConfigure;

				Object source = req.getParameter(CreateRelationshipRequest.SOURCE);
				Object target = req.getParameter(CreateRelationshipRequest.TARGET);
				if (source instanceof Vertex) {
					transition.setSource((Vertex) source);
				}
				if (target instanceof Vertex) {
					transition.setTarget((Vertex) target);
				}
				return CommandResult.newOKCommandResult(transition);
			}
		};
		return CompositeCommand.compose(configureCommand, super.getConfigureCommand(req));
	}

	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		return new ReorientTransitionCommand(req);
	}

	private static class ReorientTransitionCommand extends EditElementCommand {

		private final EObject myNewEnd;

		private final int myReorientDirection;

		protected ReorientTransitionCommand(ReorientRelationshipRequest req) {
			super("Reorient Transition Command", req.getRelationship(), req); ////$NON-NLS-1$
			myNewEnd = req.getNewRelationshipEnd();
			myReorientDirection = req.getDirection();
		}

		@Override
		public boolean canExecute() {
			if (false == myNewEnd instanceof Vertex) {
				return false;
			}
			if (false == getElementToEdit() instanceof Transition) {
				return false;
			}
			if (myReorientDirection != ReorientRequest.REORIENT_SOURCE && myReorientDirection != ReorientRequest.REORIENT_TARGET) {
				return false;
			}
			return super.canExecute();
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			Transition link = (Transition) getElementToEdit();
			Vertex newLinkEnd = (Vertex) myNewEnd;
			if (myReorientDirection == ReorientRequest.REORIENT_SOURCE) {
				link.setSource(newLinkEnd);
			} else if (myReorientDirection == ReorientRequest.REORIENT_TARGET) {
				link.setTarget(newLinkEnd);
			} else {
				return CommandResult.newErrorCommandResult("Wrong direction");
			}
			return CommandResult.newOKCommandResult();
		}
	}
}
