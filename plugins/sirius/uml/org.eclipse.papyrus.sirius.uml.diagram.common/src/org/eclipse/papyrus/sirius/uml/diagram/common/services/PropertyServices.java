/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.TypedElement;

/**
 * Services used to handle {@link Property}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class PropertyServices {

	/**
	 * Service that checks that no infinity loop exists starting from the current port.<br />
	 * A loop exists if the current port is reachable from its type, by navigation through its ports hierarchy. <br />
	 * For instance Port 1 is owned by Class1 and it is typed by Class2 that owns a port typed by Class1.
	 *
	 * @param port
	 *            the starting port.
	 * @param containerView
	 *            the container view in which the {@link Port} should be displayed.
	 * @return true if the given port is reachable from the current port type. False otherwise.
	 */
	public boolean noPortLoopDetected(Port port, final DSemanticDecorator containerView) {
		boolean loopDetected = false;
		Optional<Classifier> optionalPortType = Optional.ofNullable(port.getType()).filter(Classifier.class::isInstance).map(Classifier.class::cast);
		// We retrieve the target semantic element type of the containerView (in which the port should be displayed). Indeed, the graphical parent can be different from the semantic parent (Classifier with Generalization for instance).
		Optional<StructuredClassifier> optionalContainingClassifier = Optional.ofNullable(containerView.getTarget())
				.filter(TypedElement.class::isInstance)
				.map(typedElement -> ((TypedElement) typedElement).getType())
				.filter(StructuredClassifier.class::isInstance)
				.map(StructuredClassifier.class::cast);
		if (optionalContainingClassifier.isPresent() && optionalPortType.isPresent()) {
			loopDetected = this.detectLoopInPortTypeHierarchy(optionalContainingClassifier.get(), optionalPortType.get(), Set.of());
			if (loopDetected) {
				String containerTargetName = Optional.ofNullable(containerView.getTarget())
						.filter(NamedElement.class::isInstance)
						.map(NamedElement.class::cast)
						.map(NamedElement::getQualifiedName)
						.orElse(""); //$NON-NLS-1$
				Activator.log
						.warn(String.format("A potential infinity loop has been detected: The port '%s' that should be displayed in '%s' is typed by '%s' which directly or indirectly references '%s' (the parent container type) through ports type hierarchy.", //$NON-NLS-1$
								port.getQualifiedName(), containerTargetName, optionalPortType.get().getQualifiedName(), optionalContainingClassifier.get().getQualifiedName()));
			}
		}
		return !loopDetected;
	}



	private boolean detectLoopInPortTypeHierarchy(Classifier firstType, Classifier currentType, Set<Classifier> visitedTypes) {
		boolean loopDetected = false;
		// Simple case : loop is detected at first level: port type is equals to its parent.
		if (firstType.equals(currentType)) {
			loopDetected = true;
		}
		// Complex case : loop is detected in a indirect deep hierarchy.
		// A loop might exist in the sub-hierarchy. We check that the type has not been visited yet to avoid a stackoverflow.
		else if (!visitedTypes.contains(currentType)) {
			Set<Classifier> newVisitedTypes = new HashSet<>(visitedTypes);
			newVisitedTypes.add(currentType);
			// @formatter:off
			loopDetected = currentType.allAttributes().stream()
					.filter(Port.class::isInstance)
					.map(Port.class::cast)
					.map(Port::getType)
					.filter(Classifier.class::isInstance)
					.map(Classifier.class::cast)
					.filter(type -> this.detectLoopInPortTypeHierarchy(firstType, type, newVisitedTypes))
					.findFirst()
					.isPresent();
			// @formatter:on
		}
		return loopDetected;
	}

	/**
	 * Service that checks that no infinity loop exists starting from the current property.<br />
	 * A loop exists if the current property is reachable from its type, by navigation through its properties hierarchy. <br />
	 * For instance Property 1 is owned by Class1 and it is typed by Class2 that owns a property typed by Class1.
	 *
	 * @param property
	 *            the starting property.
	 * @param containerView
	 *            the container view in which the {@link Property} should be displayed.
	 * @return true if the given property is reachable from the current property type. False otherwise.
	 */
	public boolean noPropertyLoopDetected(Property property, final DSemanticDecorator containerView) {
		boolean loopDetected = false;
		Optional<StructuredClassifier> optionalPropertyType = Optional.ofNullable(property.getType()).filter(StructuredClassifier.class::isInstance).map(StructuredClassifier.class::cast);
		// We retrieve the target semantic element type of the containerView (in which the property should be displayed). Indeed, the graphical parent can be different from the semantic parent (Classifier with Generalization for instance).
		Optional<StructuredClassifier> optionalContainingClassifier = Optional.ofNullable(containerView.getTarget())
				.filter(TypedElement.class::isInstance)
				.map(typedElement -> ((TypedElement) typedElement).getType())
				.filter(StructuredClassifier.class::isInstance)
				.map(StructuredClassifier.class::cast);
		if (optionalContainingClassifier.isPresent() && optionalPropertyType.isPresent()) {
			loopDetected = this.detectLoopInPropertyTypeHierarchy(optionalContainingClassifier.get(), optionalPropertyType.get(), Set.of());
			if (loopDetected) {
				String containerTargetName = Optional.ofNullable(containerView.getTarget())
						.filter(NamedElement.class::isInstance)
						.map(NamedElement.class::cast)
						.map(NamedElement::getQualifiedName)
						.orElse(""); //$NON-NLS-1$
				Activator.log.warn(String.format(
						"A potential infinity loop has been detected: The property '%s' that should be displayed in '%s' is typed by '%s' which directly or indirectly references '%s' (the parent container type) through properties type hierarchy.", //$NON-NLS-1$
						property.getQualifiedName(), containerTargetName, optionalPropertyType.get().getQualifiedName(), optionalContainingClassifier.get().getQualifiedName()));
			}
		}
		return !loopDetected;
	}

	private boolean detectLoopInPropertyTypeHierarchy(StructuredClassifier firstType, StructuredClassifier currentType, Set<Classifier> visitedTypes) {
		boolean loopDetected = false;
		// Simple case : loop is detected at first level: property type is equals to its parent.
		if (firstType.equals(currentType)) {
			loopDetected = true;
		}
		// Complex case : loop is detected in a indirect deep hierarchy.
		// A loop might exist in the sub-hierarchy. We check that the type has not been visited yet to avoid a stackoverflow.
		else if (!visitedTypes.contains(currentType)) {
			Set<Classifier> newVisitedTypes = new HashSet<>(visitedTypes);
			newVisitedTypes.add(currentType);
			// @formatter:off
			loopDetected = currentType.allAttributes().stream()
					.map(Property::getType)
					.filter(StructuredClassifier.class::isInstance)
					.map(StructuredClassifier.class::cast)
					.filter(type -> this.detectLoopInPropertyTypeHierarchy(firstType, type, newVisitedTypes))
					.findFirst()
					.isPresent();
			// @formatter:on
		}
		return loopDetected;
	}

}
