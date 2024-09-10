/**
 * Copyright (c) 2015 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Benoit Maggi benoit.maggi@cea.fr - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.gmfdiag.representation.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule;
import org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule;
import org.eclipse.papyrus.infra.gmfdiag.representation.PathElement;

/**
 * Implementation of derived features and operations for the {@link PathElement} model class.
 */
public class PathElementCustomImpl extends PathElementImpl implements PathElement {
	
	
	/**
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.PathElementImpl#basicGetOrigin()
	 *
	 * @return
	 */
	@Override
	public EClass basicGetOrigin() {
		ChildRule rule = (ChildRule) this.eContainer();
		return basicGetOriginFrom(rule.getInsertionPath(), rule.getOrigin());
	}

	private EClass basicGetOriginFrom(EList<PathElement> list, EClass from) {
		EClass current = from;
		int index = 0;
		while ((current != null) && list.get(index) != this) {
			EReference feature = list.get(index).getFeature();
			if (!current.getEAllReferences().contains(feature))
				return null;
			current = feature.getEReferenceType();
			index++;
		}
		return current;
	}
	
	
	/**
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.PathElementImpl#basicGetTarget()
	 *
	 * @return
	 */
	@Override
	public EClass basicGetTarget() {
		EReference feature = this.getFeature();
		if (feature == null)
			return null;
		EClass origin = basicGetOrigin();
		if (origin == null)
			return null;
		if (!origin.getEAllReferences().contains(feature))
			return null;
		return feature.getEReferenceType();
	}
} 
