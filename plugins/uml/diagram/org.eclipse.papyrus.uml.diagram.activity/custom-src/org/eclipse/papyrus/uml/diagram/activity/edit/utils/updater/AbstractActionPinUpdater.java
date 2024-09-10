/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.papyrus.uml.diagram.common.util.Util;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.Type;

/**
 *
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 */
public abstract class AbstractActionPinUpdater<NodeType extends Action> implements IPinUpdater<NodeType> {

	/**
	 * Get the UML primitive type of the model corresponding to typeName
	 *
	 * @param typeName
	 * @param model
	 * @return the UML primitive type of the model
	 */
	protected PrimitiveType getUMLPrimitiveType(final String typeName, Model model) {
		// Retrieve the primitive type corresponding to the specified type name.
		// It is only possible to retrieve a primitive type if in the current model
		// context the primitive types library is available (i.e., is imported). In
		// any other case, null is returned.
		PrimitiveType primitiveType = null;
		// 1] check if primitive typed is already imported
		if (UpdaterPinUtils.isPrimitiveTypeLibraryImported(model)) {
			// 2] get UML primitive types package
			Package primitiveTypePackage = UpdaterPinUtils.getPrimitiveTypesPackage(model);
			Iterator<Type> ownedTypesIterator = primitiveTypePackage.getOwnedTypes().iterator();
			// 3] loop into package elements to get the required type
			while (ownedTypesIterator.hasNext()) {
				Type type = ownedTypesIterator.next();
				if (type instanceof PrimitiveType && type.getName().equals(typeName)) {
					primitiveType = (PrimitiveType) type;
				}
			}
		}
		return primitiveType;
	}

	/**
	 * The deletion of a pin has an impact on edges that are using as a source or a target.
	 * To ensure model consistency, the deletion of a pin implies deletion of edges referencing
	 * this pin either as a source or a target.
	 *
	 * @param pin
	 */
	protected static void deleteEdges(Pin pin) {
		// 1] get all incoming and outgoing edges
		List<ActivityEdge> edgeToDelete = new ArrayList<>(pin.getIncomings());
		edgeToDelete.addAll(pin.getOutgoings());
		Iterator<ActivityEdge> edgeToDeleteIterator = edgeToDelete.iterator();
		// 2] loop into all edges
		while (edgeToDeleteIterator.hasNext()) {
			ActivityEdge edge = edgeToDeleteIterator.next();
			Element owner = edge.getOwner();
			if (owner != null) {
				// 3] remove edges
				if (owner instanceof StructuredActivityNode) {
					((StructuredActivityNode) owner).getEdges().remove(edge);
				} else if (owner instanceof Activity) {
					((Activity) owner).getEdges().remove(edge);
				} else if (owner instanceof ActivityPartition) {
					((ActivityPartition) owner).getEdges().remove(edge);
				}
			}
		}
	}

	/**
	 * This method search the first classifier which is common of all classifiers
	 *
	 * @param classifiers
	 * @return return the first classifier which is common of all classifiers
	 */
	protected static Classifier getFirstCommonSuperClassifier(List<Classifier> classifiers) {
		// 1] get super class of the first classifier of the list
		List<Classifier> commonSuperClassifier = new ArrayList<>(Util.getAllSuperClasses(null, classifiers.get(0)));
		commonSuperClassifier.add(classifiers.get(0));
		Set<Classifier> nonCommonClassifier = new HashSet<>();
		// 2] loop into classifiers
		for (Classifier c : classifiers) {
			// 3] loop into the list of the commonSuperClass
			// put into nonCommonClassifier list all super classifier of c which is not in the commonSuperClass list
			for (Classifier c2 : commonSuperClassifier) {
				if (!(Util.getAllSuperClasses(null, c).contains(c2)) && (c != c2)) {
					nonCommonClassifier.add(c2);
				}
			}
		}

		// 4] remove all nonCommonClassifier from commonSuperClass
		commonSuperClassifier.removeAll(nonCommonClassifier);

		// 5] Get the classifier which inherited of all common super class
		for (Classifier classifier : commonSuperClassifier) {
			List<Classifier> inheritedClassifier = new ArrayList<>(Util.getAllSuperClasses(null, classifier));
			inheritedClassifier.add(classifier);
			if (inheritedClassifier.containsAll(commonSuperClassifier)) {
				return classifier;
			}
		}
		return null;
	}
}