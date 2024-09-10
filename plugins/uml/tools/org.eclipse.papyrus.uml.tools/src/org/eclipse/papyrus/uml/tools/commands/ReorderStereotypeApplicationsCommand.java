/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.commands;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * A command that sets the order of stereotype applications to match a prescribed ordering.
 * Note that this command is only enabled if all of the following preconditions hold:
 * <ul>
 * <li>the stereotype applications of the element are all contained in the same resource. Stereotype application ordering
 * is undefined across resources</li>
 * <li>the given ordering covers all of the applied stereotypes and includes no extraneous stereotypes. This command
 * cannot be used to (bulk) apply or unapply stereotypes</li>
 * </ul>
 */
public class ReorderStereotypeApplicationsCommand extends AbstractCommand {

	private final Element element;
	private final List<Stereotype> stereotypeOrdering;

	private EList<EObject> resourceContents;
	private int[] positions;
	private EList<EObject> oldOrdering;
	private EList<EObject> newOrdering;

	public ReorderStereotypeApplicationsCommand(Element element, List<? extends Stereotype> newOrdering) {
		super("Reorder Applied Stereotypes");

		this.element = element;
		this.stereotypeOrdering = List.copyOf(newOrdering);
	}

	@Override
	protected boolean prepare() {
		boolean result = true;

		oldOrdering = new BasicEList.FastCompare<>(element.getStereotypeApplications());
		if (stereotypeOrdering.size() != oldOrdering.size() || oldOrdering.stream().map(EObject::eResource).distinct().count() != 1) {
			result = false;
		} else {
			resourceContents = oldOrdering.get(0).eResource().getContents();
			newOrdering = stereotypeOrdering.stream().map(element::getStereotypeApplication)
					.filter(Objects::nonNull)
					.collect(Collectors.toCollection(BasicEList.FastCompare::new));

			if (newOrdering.size() != oldOrdering.size()) {
				result = false;
			} else {
				positions = oldOrdering.stream()
						.mapToInt(resourceContents::indexOf)
						.filter(index -> index >= 0)
						.sorted()
						.toArray();
			}
		}

		return result;
	}

	@Override
	public void execute() {
		// First, replace all of the stereotype applications with placeholders because when
		// we re-insert them in their new positions, we cannot repeat objects in the list
		EObject[] dummies = IntStream.range(0, oldOrdering.size())
				.mapToObj(__ -> EcoreFactory.eINSTANCE.createEObject())
				.toArray(EObject[]::new);
		for (int i = 0; i < dummies.length; i++) {
			resourceContents.set(positions[i], dummies[i]);
		}

		// Remove the stereotype applications from the inverse reference map because otherwise
		// they will be rediscovered in the original order via that map
		oldOrdering.forEach(sa -> org.eclipse.uml2.uml.util.UMLUtil.setBaseElement(sa, null));

		// Then, replace the dummies with the original stereotype applications in their new ordering
		for (int i = 0; i < positions.length; i++) {
			resourceContents.set(positions[i], newOrdering.get(i));

			// Restore the base element reference
			org.eclipse.uml2.uml.util.UMLUtil.setBaseElement(newOrdering.get(i), element);
		}
	}

	@Override
	public void undo() {
		// Swap the orderings and repeat
		swapOrderings();
		execute();
	}

	private void swapOrderings() {
		EList<EObject> swap = oldOrdering;
		oldOrdering = newOrdering;
		newOrdering = swap;
	}

	@Override
	public void redo() {
		// Swap the orderings and repeat
		swapOrderings();
		execute();
	}

	@Override
	public Collection<?> getAffectedObjects() {
		List<EObject> result = new BasicEList.FastCompare<>(newOrdering.size() + 1);
		result.add(element);
		result.addAll(newOrdering);
		return result;
	}

}
