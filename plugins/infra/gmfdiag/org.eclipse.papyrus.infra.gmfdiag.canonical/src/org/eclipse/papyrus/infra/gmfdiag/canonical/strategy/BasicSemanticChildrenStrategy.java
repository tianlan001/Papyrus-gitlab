/*****************************************************************************
 * Copyright (c) 2015 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 433206
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.gmf.runtime.notation.View;

import com.google.common.collect.Iterables;

/**
 * A default implementation of the semantic children strategy that is parameterized
 * by features providing children and
 */
public class BasicSemanticChildrenStrategy implements ISemanticChildrenStrategy {

	private EReference[] childReferences;
	private EReference[] connectionReferences;
	private EReference[] dependentReferences;

	public BasicSemanticChildrenStrategy(EReference[] childReferences, EReference[] connectionReferences, EReference[] dependentReferences) {
		super();

		this.childReferences = (childReferences == null) || (childReferences.length == 0) ? null
				: Arrays.copyOf(childReferences, childReferences.length);
		this.connectionReferences = (connectionReferences == null) || (connectionReferences.length == 0) ? null
				: Arrays.copyOf(connectionReferences, connectionReferences.length);
		this.dependentReferences = (dependentReferences == null) || (dependentReferences.length == 0) ? null
				: Arrays.copyOf(dependentReferences, dependentReferences.length);
	}

	public BasicSemanticChildrenStrategy(Iterable<? extends EReference> childReferences, Iterable<? extends EReference> connectionReferences, Iterable<? extends EReference> dependentReferences) {
		super();

		this.childReferences = (childReferences == null) || Iterables.isEmpty(childReferences) ? null
				: Iterables.toArray(childReferences, EReference.class);
		this.connectionReferences = (connectionReferences == null) || Iterables.isEmpty(connectionReferences) ? null
				: Iterables.toArray(connectionReferences, EReference.class);
		this.dependentReferences = (dependentReferences == null) || Iterables.isEmpty(dependentReferences) ? null
				: Iterables.toArray(dependentReferences, EReference.class);
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		return (childReferences == null)
				? ECollections.<EObject> emptyEList()
				: new EContentsEList<EObject>(semanticFromEditPart, childReferences);
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticConnections(EObject semanticFromEditPart, View viewFromEditPart) {
		return (connectionReferences == null)
				? ECollections.<EObject> emptyEList()
				: new EContentsEList<EObject>(semanticFromEditPart, connectionReferences);
	}

	@Override
	public Collection<? extends EObject> getCanonicalDependents(EObject semanticFromEditPart, View viewFromEditPart) {
		return (dependentReferences == null)
				? ECollections.<EObject> emptyEList()
				: new EContentsEList<EObject>(semanticFromEditPart, dependentReferences);
	}

}
