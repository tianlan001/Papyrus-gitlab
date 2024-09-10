/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.providers;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This contents provider allows to navigate until to find Properties (and inherited Properties) owned by a Stereotypes
 */
public class StereotypePropertyTreeContentProvider extends StereotypeTreeContentProvider {

	/**
	 * @see org.eclipse.papyrus.uml.expressions.edit.internal.providers.model2doc.uml.documentstructuretemplate.edit.internal.providers.StereotypeTreeContentProvider#hasChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Property) {
			return false;
		}
		if (element instanceof Stereotype) {
			return hasPropertyInHierarchy((Stereotype) element);
		}
		return super.hasChildren(element);
	}

	/**
	 *
	 * @param eClass
	 * @return
	 *         <code>true</code> if the EClass contains EReference
	 */
	protected final boolean hasPropertyInHierarchy(final Stereotype stereotype) {
		final Iterator<NamedElement> namedElementIter = stereotype.getMembers().iterator();
		while (namedElementIter.hasNext()) {
			final NamedElement current = namedElementIter.next();
			if (isValid(current)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param ePackage
	 * @return
	 *         <code>true</code> if the EPackage contains EClass contaning EReference
	 */
	protected final boolean hasPropertyInHierarchy(final Package profile) {
		final Iterator<NamedElement> namedElementIter = profile.getMembers().iterator();
		while (namedElementIter.hasNext()) {
			final NamedElement current = namedElementIter.next();
			if (isValid(current)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.uml.expressions.edit.internal.providers.model2doc.uml.documentstructuretemplate.edit.internal.providers.StereotypeTreeContentProvider#isValid(org.eclipse.emf.ecore.EObject)
	 *
	 * @param eobject
	 * @return
	 */
	@Override
	protected boolean isValid(final EObject eobject) {
		if (eobject instanceof Property) {
			return true;
		}
		if (eobject instanceof Stereotype) {
			return hasPropertyInHierarchy((Stereotype) eobject);
		}
		if (eobject instanceof Package) {
			return hasPropertyInHierarchy((Package) eobject);
		}
		return false;
	}

}
