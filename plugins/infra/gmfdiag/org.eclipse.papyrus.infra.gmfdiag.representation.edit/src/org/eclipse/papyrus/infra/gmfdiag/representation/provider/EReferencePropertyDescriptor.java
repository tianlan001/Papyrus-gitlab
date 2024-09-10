/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.representation.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.papyrus.infra.core.architecture.provider.SurrogateItemPropertyDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.representation.PathElement;

/**
 * Represents a descriptor for properties of type EReference
 *
 * @author Laurent Wouters
 */
public class EReferencePropertyDescriptor extends SurrogateItemPropertyDescriptor {
	private static final Collection<?> empty = new ArrayList<EReference>();

	public EReferencePropertyDescriptor(IItemPropertyDescriptor inner) {
		super(inner);
	}

	@Override
	public Collection<?> getChoiceOfValues(Object object) {
		if (object instanceof PathElement) {
			PathElement pe = (PathElement) object;
			EClass origin = pe.getOrigin();
			if (origin == null) {
				return empty;
			}
			return origin.getEAllReferences();
		}
		return empty;
	}
}