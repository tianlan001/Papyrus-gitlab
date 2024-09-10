/*****************************************************************************
 * Copyright (c) 2018-2019 CEA LIST, Christian W. Damus and others.
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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 547864
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A {@link DestroyElementRequest} parameter tracking the deletion of {@link InteractionFragment}
 * containers in the context of an {@link Interaction}.
 *
 * @since 4.2
 */
public class InteractionContainerDeletionContext {

	private static String PARAMETER_NAME = "papyrus.uml.InteractionContainerDeletionContext"; //$NON-NLS-1$

	/**
	 * Set of {@link Interaction}s, {@link CombinedFragment}s, and
	 * {@link InteractionOperand}s being destroyed.
	 */
	private final Set<InteractionFragment> interactionContainersBeingDestroyed = new HashSet<>();

	/** Next insertion index of interaction fragments being added to a container. */
	private final Map<InteractionFragment, Integer> insertionIndexByContainer = new HashMap<>();

	private final TransactionalEditingDomain domain;

	/**
	 * Initializes me with the initial destroy request.
	 */
	private InteractionContainerDeletionContext(DestroyRequest request) {
		super();

		this.domain = request.getEditingDomain();
		request.setParameter(PARAMETER_NAME, this);
	}

	public static Optional<InteractionContainerDeletionContext> get(DestroyElementRequest request) {
		return get(request, request.getElementToDestroy());
	}

	static Optional<InteractionContainerDeletionContext> get(DestroyDependentsRequest request) {
		return get(request, request.getElementToDestroy());
	}

	private static Optional<InteractionContainerDeletionContext> get(DestroyRequest request, EObject elementToDestroy) {
		Optional<InteractionContainerDeletionContext> result = Optional.ofNullable(request.getParameter(PARAMETER_NAME))
				.filter(InteractionContainerDeletionContext.class::isInstance)
				.map(InteractionContainerDeletionContext.class::cast);

		if (isInteractionContainer(elementToDestroy)) {
			if (!result.isPresent()) {
				result = Optional.of(new InteractionContainerDeletionContext(request));
			}

			result.get().add(elementToDestroy);
		}

		return result;
	}

	static void deleting(DestroyElementRequest request) {
		if (isInteractionContainer(request.getElementToDestroy())) {
			// Ensure that we record that this object is being destroyed
			get(request);
		}
	}

	private static boolean isInteractionContainer(EObject object) {
		return object instanceof InteractionOperand
				|| object instanceof CombinedFragment
				|| object instanceof Interaction;
	}

	private void add(EObject elementToDestroy) {
		interactionContainersBeingDestroyed.add((InteractionFragment) elementToDestroy);
	}

	boolean isBeingDestroyed(Interaction interaction) {
		return interactionContainersBeingDestroyed.contains(interaction);
	}

	boolean isBeingDestroyed(CombinedFragment combinedFragment) {
		return interactionContainersBeingDestroyed.contains(combinedFragment);
	}

	boolean isBeingDestroyed(InteractionOperand operand) {
		return interactionContainersBeingDestroyed.contains(operand);
	}

	/**
	 * Query the new container into which a {@code fragment} should be moved instead of
	 * destroyed.
	 *
	 * @param fragment
	 *            a fragment for which destruction is requested
	 * @return the container, either an {@link Interaction} or an {@link InteractionOperand},
	 *         into which to move the {@code fragment}, or {@code null} if it should be destroyed
	 */
	InteractionFragment getNewContainerFor(InteractionFragment fragment) {
		// Interaction operands are, of course, always just deleted
		return (fragment instanceof InteractionOperand) ? null : doGetNewContainerFor(fragment);
	}

	/**
	 * Query the new container into which a {@code generalOrdering} should be moved instead of
	 * destroyed.
	 *
	 * @param generalOrdering
	 *            a general ordering for which destruction is requested
	 * @return the container, either an {@link Interaction} or an {@link InteractionOperand},
	 *         into which to move the {@code fragment}, or {@code null} if it should be destroyed
	 */
	InteractionFragment getNewContainerFor(GeneralOrdering generalOrdering) {
		return doGetNewContainerFor(generalOrdering);
	}

	/**
	 * @precondition the {@code element} is owned by an {@link InteractionFragment}
	 */
	private InteractionFragment doGetNewContainerFor(Element element) {
		InteractionFragment owner = (InteractionFragment) element.getOwner();
		if (owner instanceof InteractionOperand && !isBeingDestroyed((InteractionOperand) owner)) {
			// This element is being destroyed on its own merits
			return null;
		} else if (owner instanceof Interaction && !isBeingDestroyed((Interaction) owner)) {
			// This element is being destroyed on its own merits
			return null;
		}

		InteractionFragment result = null;

		for (; result == null && owner != null; owner = (InteractionFragment) owner.getOwner()) {
			if (owner instanceof InteractionOperand && !isBeingDestroyed((InteractionOperand) owner)) {
				result = owner;
			} else if (owner instanceof Interaction) {
				if (!isBeingDestroyed((Interaction) owner)) {
					result = owner;
				} // else the interaction is being destroyed, so everything must be
				break;
			}
		}

		return result;
	}

	public ICommand getDestroyCommand(InteractionFragment fragment) {
		InteractionFragment container = getNewContainerFor(fragment);
		return container != null ? move(fragment, container) : null;
	}

	ICommand move(InteractionFragment fragment, InteractionFragment into) {
		// Find the index in the new container into which add the fragment
		Integer insertionIndex = insertionIndexByContainer.get(into);
		if (insertionIndex == null) {
			// Insert at the index of the nearest combined fragment that is not being deleted
			CombinedFragment nearestCF = null;
			for (InteractionFragment owner = (InteractionFragment) fragment.getOwner(); //
					nearestCF == null && owner != null && !(owner instanceof Interaction); //
					owner = (InteractionFragment) owner.getOwner()) {

				if (owner instanceof CombinedFragment) {
					CombinedFragment cf = (CombinedFragment) owner;
					if (!isBeingDestroyed(cf)) {
						nearestCF = cf;
					}
				}
			}

			if (nearestCF != null && into == nearestCF.getOwner()) {
				// Get the list of fragments containing this CF in its enclosing operand
				EList<InteractionFragment> fragments = nearestCF.getEnclosingOperand() != null
						? nearestCF.getEnclosingOperand().getFragments()
						: nearestCF.getEnclosingInteraction().getFragments();
				insertionIndex = fragments.indexOf(nearestCF) + 1;
				insertionIndexByContainer.put(into, insertionIndex);
			}
		}

		int index = -1;
		if (insertionIndex != null) {
			index = insertionIndex;
			insertionIndexByContainer.put(into, index + 1); // Increment for the next fragment
		}

		EReference containment = (into instanceof InteractionOperand)
				? UMLPackage.Literals.INTERACTION_OPERAND__FRAGMENT
				: UMLPackage.Literals.INTERACTION__FRAGMENT;
		return move(fragment, into, containment, index);
	}

	ICommand getDestroyCommand(GeneralOrdering generalOrdering) {
		InteractionFragment container = getNewContainerFor(generalOrdering);
		return container != null ? move(generalOrdering, container) : null;
	}

	ICommand move(GeneralOrdering generalOrdering, InteractionFragment into) {
		// Index doesn't matter because general orderings are, themselves, unordered
		return move(generalOrdering, into, UMLPackage.Literals.INTERACTION_FRAGMENT__GENERAL_ORDERING, -1);
	}

	<E extends Element> ICommand move(E element, InteractionFragment into, EReference containment, int index) {
		// Can't use edit-helpers for this because there is no support for insertion index in
		// the MoveRequest
		return new AbstractTransactionalCommand(domain, "Move Interaction Element", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				@SuppressWarnings("unchecked")
				EList<E> list = (EList<E>) into.eGet(containment);
				if (index < 0) {
					list.add(element);
				} else if (list.size() < index) {
					return CommandResult.newErrorCommandResult("index out of bounds: " + index); //$NON-NLS-1$
				} else {
					list.add(index, element);
				}
				return CommandResult.newOKCommandResult();
			}
		};
	}
}
